package shoppinglist.beam.products.pipelines;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shoppinglist.beam.kafka.KafkaReadFactory;
import shoppinglist.beam.products.pojos.products.ProductInfo;
import shoppinglist.beam.products.pojos.targetjson.Product;
import shoppinglist.beam.products.pojos.targetjson.TargetProduct;
import shoppinglist.beam.products.pojos.walmartjson.OthersItem;
import shoppinglist.beam.products.pojos.walmartjson.ProductsItem;
import shoppinglist.beam.products.pojos.walmartjson.ValuesItem;
import shoppinglist.beam.products.pojos.walmartjson.WalmartProduct;
import shoppinglist.beam.products.transforms.JdbcProductInfoWrite;
import shoppinglist.beam.products.transforms.WalmartProductInfoConvert;

import java.lang.reflect.Type;
import java.util.*;

/**
 * @author bordway@ihmc.us 6/14/2021
 */
public class WalmartParse {
   public static void main(String[] args) {
      ProductKafkaOptions options =
              PipelineOptionsFactory.fromArgs(args).withValidation().as(ProductKafkaOptions.class);

      run(options);
   }
   public static void run(ProductKafkaOptions options) {
       Pipeline pipeline = Pipeline.create();
       Map<String, Object> kafkaConfig = new HashMap<>();
       kafkaConfig.put("group.id", "beam-target");
       // Create topics to listen to just in case they haven't been created already yet
       _logger.error("Starting Kafka-To-PubSub pipeline with parameters bootstrap servers: {} input topics: {}",
               options.getBootstrapServers(), options.getInputTopics());

       PCollection<WalmartProduct> asPojo = pipeline.apply(KafkaReadFactory.readFromKafkaWithoutMetadata(options.getBootstrapServers(), Arrays.asList(options.getInputTopics().split(",")), kafkaConfig))
               .apply("From values", Values.create())
               .apply("Clean json", ParDo.of(new DoFn<String, String>()
               {
                   @ProcessElement
                   public void processElement (ProcessContext c) {
                       Type type = new TypeToken<Map<String, Object>>()
                       {
                       }.getType();
                       Map<String, Object> data = new Gson().fromJson(c.element(), type);

                       for (Iterator<Map.Entry<String, Object>> it = data.entrySet().iterator(); it.hasNext(); ) {
                           Map.Entry<String, Object> entry = it.next();
                           if (entry.getValue() == null) {
                               _logger.error("Removing key: {}", entry.getKey());
                               it.remove();
                           }
                           else if (entry.getValue() instanceof List) {
                               if (((List<?>) entry.getValue()).isEmpty()) {
                                   _logger.error("Removing key: {}", entry.getKey());
                                   it.remove();
                               }
                           }
                       }
                       String json = new GsonBuilder().create().toJson(data);
                       c.output(json);
                   }
               }))
               .apply("As pojo", ParDo.of(new DoFn<String, WalmartProduct>()
               {
                   @ProcessElement
                   public void processElement (ProcessContext c) {
                       String input = c.element();
                       WalmartProduct productPojo = new GsonBuilder().serializeNulls().create().fromJson(input, WalmartProduct.class);
                       c.output(productPojo);
                   }
               }));

       asPojo.apply("Convert to ProductInfo", ParDo.of(new WalmartProductInfoConvert()))
               .apply("Log", MapElements.via(new SimpleFunction<ProductInfo, ProductInfo>()
               {
                   @Override
                   public ProductInfo apply (ProductInfo input) {
                       _logger.error(input.toString());
                       return input;
                   }
               }))
               .apply("JDBC transform", new JdbcProductInfoWrite());

       pipeline.run().waitUntilFinish();
   }
      private static final Logger _logger = LoggerFactory.getLogger(WalmartParse.class);
}
