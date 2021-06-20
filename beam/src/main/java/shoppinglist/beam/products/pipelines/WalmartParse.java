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
import shoppinglist.beam.products.pojos.products.ProductInfo;
import shoppinglist.beam.products.pojos.targetjson.Product;
import shoppinglist.beam.products.pojos.targetjson.TargetProduct;
import shoppinglist.beam.products.transforms.JdbcProductInfoWrite;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author bordway@ihmc.us 6/14/2021
 */
public class WalmartParse {
   public static void main(String[] args) {
      ProductKafkaOptions options =
              PipelineOptionsFactory.fromArgs(args).withValidation().as(ProductKafkaOptions.class);

      run(options);
   }
   public static void run(ProductKafkaOptions options)
   {
      Pipeline pipeline = Pipeline.create(options);
      Map<String, Object> kafkaConfig = new HashMap<>();
      kafkaConfig.put("group.id", "beam-walmart");
      // Create topics to listen to just in case they haven't been created already yet
      kafkaConfig.put("auto.create.topics.enable", true);
      _logger.info("Starting Kafka-To-PubSub pipeline with parameters bootstrap servers: {} input topics: {}",
              options.getBootstrapServers(), options.getInputTopics());
      PCollection<String> products = pipeline.apply(KafkaReadFactory.readFromKafkaWithoutMetadata(options.getBootstrapServers(), Arrays.asList(options.getInputTopics().split(",")), kafkaConfig))
              .apply("From values", Values.create())
              .apply("As pojo", ParDo.of(new DoFn<String, String>() {
                 @ProcessElement
                 public void processElement(ProcessContext c)
                 {
                    String input = c.element();
//                    TargetProduct productPojo = new Gson().fromJson(input, TargetProduct.class);
//                    List<Product> product = productPojo.getResult().getData().getSearch().getProducts();
                    _logger.error(input);
                    c.output(c.element());
                 }
              }));
//      products.apply("Convert to ProductInfo", MapElements.via(new SimpleFunction<KV<String, KV<String, Product>>, ProductInfo>() {
//         @Override
//         public ProductInfo apply(KV<String, KV<String, Product>> input)
//         {
//            String storeID = input.getKey();
//            String searchTerm = input.getValue().getKey();
//
//            Product result = input.getValue().getValue();
//            String productName = result.getItem().getProductDescription().getTitle();
////            String productID = result.getTcin();
//            String supplier = "Walmart";
//            String brand = "UNKNOWN";
//            try {
//               brand = result.getItem().getPrimaryBrand().getName();
//
//            } catch (NullPointerException e) {
//               _logger.debug("Unknown brand for product name: {}", result.getItem().getProductDescription().getTitle());
//            }
//            double price = result.getPrice().getCurrentRetail();
//
//            return new ProductInfo(storeID, supplier, productName, brand, searchTerm, price);
//         }
//      }))
//              .apply("Product stored proc", new JdbcProductInfoWrite());

      pipeline.run().waitUntilFinish();
   }
      private static final Logger _logger = LoggerFactory.getLogger(WalmartParse.class);
}
