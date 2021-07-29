package shoppinglist.beam.products.pipelines;

import com.google.gson.Gson;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shoppinglist.beam.kafka.KafkaReadFactory;

import shoppinglist.beam.products.pojos.targetjson.Product;
import shoppinglist.beam.products.pojos.targetjson.TargetProduct;
import org.apache.beam.sdk.transforms.DoFn;
import shoppinglist.beam.products.transforms.JdbcProductInfoWrite;
import shoppinglist.beam.products.transforms.TargetProductInfoConvert;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author bordway@ihmc.us 6/14/2021
 */
public class TargetParse {
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
      PCollection<KV<String, KV<String, Product>>> products = pipeline.apply(KafkaReadFactory.readFromKafkaWithoutMetadata(options.getBootstrapServers(), Arrays.asList(options.getInputTopics().split(",")), kafkaConfig))
           .apply("From values", Values.create())
              .apply("As pojo", ParDo.of(new DoFn<String, KV<String, KV<String, Product>>>() {
                 @ProcessElement
                 public void processElement(ProcessContext c)
                 {
                    String input = c.element();
                    TargetProduct productPojo = new Gson().fromJson(input, TargetProduct.class);
                    List<Product> product = productPojo.getResult().getData().getSearch().getProducts();
                    for (Product product1 : product) {
                       c.output(KV.of(productPojo.getStore(), KV.of(productPojo.getProduct(), product1)));
                    }
                 }
              }));
      products.apply("Convert to ProductInfo", ParDo.of(new TargetProductInfoConvert()))
              .apply("Product stored proc", new JdbcProductInfoWrite());

      pipeline.run().waitUntilFinish();
   }

   private static final Logger _logger = LoggerFactory.getLogger(TargetParse.class);
}
