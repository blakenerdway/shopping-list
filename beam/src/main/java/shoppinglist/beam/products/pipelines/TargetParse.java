package shoppinglist.beam.products.pipelines;

import com.google.gson.Gson;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.jdbc.JdbcIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.*;
import org.checkerframework.checker.initialization.qual.Initialized;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.UnknownKeyFor;
import shoppinglist.beam.products.pojos.targetjson.TargetProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shoppinglist.beam.kafka.KafkaReadFactory;

import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author bordway@ihmc.us 6/14/2021
 */
public class TargetParse {
   public static void main(String[] args) {
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
      pipeline.apply(KafkaReadFactory.readFromKafkaWithoutMetadata(options.getBootstrapServers(), Arrays.asList(options.getInputTopics().split(",")), kafkaConfig))
              .apply("createValues", Values.create())
              .apply("createPojo", MapElements.via(new SimpleFunction<String, TargetProduct>() {
                 @Override
                 public TargetProduct apply(String input)
                 {
                    TargetProduct productPojo = new Gson().fromJson(input, TargetProduct.class);
                    String store = productPojo.getStore();
                    String p = productPojo.getProduct();

                    _logger.error(input);
                    return productPojo;
                 }
              }))
              .apply(JdbcIO.<TargetProduct>write()
                      .withDataSourceConfiguration(JdbcIO.DataSourceConfiguration.create(
                              // TODO check port and database endpoint
                              "com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/products")
                              // TODO check username
                              .withUsername("username")
                              .withPassword("password!"))
                      // TODO fix this?
                      .withStatement("insert into products values(?, ?)")
                      .withPreparedStatementSetter(new JdbcIO.PreparedStatementSetter<TargetProduct>() {
                         @Override
                         public void setParameters(TargetProduct element, @UnknownKeyFor @NonNull @Initialized PreparedStatement query) throws Exception
                         {

                         }
                      }));

      pipeline.run().waitUntilFinish();
   }

   private static final Logger _logger = LoggerFactory.getLogger(TargetParse.class);
}
