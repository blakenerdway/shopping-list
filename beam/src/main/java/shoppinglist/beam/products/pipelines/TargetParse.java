package shoppinglist.beam.products.pipelines;

import com.google.gson.Gson;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.jdbc.JdbcIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.checkerframework.checker.initialization.qual.Initialized;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.UnknownKeyFor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shoppinglist.beam.kafka.KafkaReadFactory;
import shoppinglist.beam.products.pojos.targetjson.Product;
import shoppinglist.beam.products.pojos.targetjson.TargetProduct;
import org.apache.beam.sdk.transforms.DoFn;

import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author bordway@ihmc.us 6/14/2021
 */
public class TargetParse {
   private static final Logger _logger = LoggerFactory.getLogger(TargetParse.class);

   public static void main(String[] args)
   {
      ProductKafkaOptions options =
              PipelineOptionsFactory.fromArgs(args).withValidation().as(ProductKafkaOptions.class);

      run(options);
   }

   public static void run(ProductKafkaOptions options)
   {
      Pipeline pipeline = Pipeline.create(options);
      Map<String, Object> kafkaConfig = new HashMap<>();
      kafkaConfig.put("group.id", "beam-target");
      // Create topics to listen to just in case they haven't been created already yet
      _logger.error("Starting Kafka-To-PubSub pipeline with parameters bootstrap servers: {} input topics: {}",
              options.getBootstrapServers(), options.getInputTopics());
      PCollection<KV<String, Product>> products = pipeline.apply(KafkaReadFactory.readFromKafkaWithoutMetadata(options.getBootstrapServers(), Arrays.asList(options.getInputTopics().split(",")), kafkaConfig))
              .apply("createValues", Values.create())
              .apply("createPojo", ParDo.of(new DoFn<String, KV<String, Product>>() {
                 @ProcessElement
                 public void processElement (ProcessContext c)
                 {
                    String input = c.element();
                    TargetProduct productPojo = new Gson().fromJson(input, TargetProduct.class);
                    List<Product> product = productPojo.getResult().getData().getSearch().getProducts();
                    _logger.error(input);
                    for (Product product1 : product) {
                       c.output(KV.of(productPojo.getProduct(), product1));
                    }
                 }
              }));

      products.apply("brand update", JdbcIO.<KV<String, Product>>write()
              .withDataSourceConfiguration(JdbcIO.DataSourceConfiguration.create(
                      "com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/shopping_list")
                      .withUsername("root")
                      .withPassword("password!"))
              // Insert into table only if the brand doesn't exist already
              .withStatement("INSERT INTO brands(name)" +
                      "SELECT ? FROM DUAL WHERE NOT EXISTS (SELECT * FROM brands WHERE name=? LIMIT 1)")
              .withPreparedStatementSetter(new JdbcIO.PreparedStatementSetter<KV<String, Product>>() {
                 @Override
                 public void setParameters(KV<String, Product> element, @UnknownKeyFor @NonNull @Initialized PreparedStatement query) throws Exception
                 {
                    // Update brands in the database
                    query.setString(1, element.getValue().getItem().getPrimaryBrand().getName());
                    query.setString(2, element.getValue().getItem().getPrimaryBrand().getName());
                 }
              }));

//      products.apply("product update", JdbcIO.<KV<String, Product>>write()
//              .withDataSourceConfiguration(JdbcIO.DataSourceConfiguration.create(
//                      "com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/shopping_list")
//                      .withUsername("root")
//                      .withPassword("password!"))
//              // TODO fix this!!!!!!!!!
//              .withStatement("insert into products (name, brand, size, price, store_id, supplier_name, url) values (?,?,?,?,?,?,?) " +
//                      "ON DUPLICATE KEY UPDATE " +
//                      "name = ?, brand = ?")
//              .withPreparedStatementSetter(new JdbcIO.PreparedStatementSetter<KV<String, Product>>() {
//                 @Override
//                 public void setParameters(KV<String, Product> element, @UnknownKeyFor @NonNull @Initialized PreparedStatement query) throws Exception
//                 {
//
//                 }
//              }));

      pipeline.run().waitUntilFinish();
   }
}
