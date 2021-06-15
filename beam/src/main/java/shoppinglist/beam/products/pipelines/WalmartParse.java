package shoppinglist.beam.products.pipelines;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.PipelineResult;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.transforms.Values;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PDone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shoppinglist.beam.kafka.KafkaReadFactory;

import java.util.Arrays;
import java.util.HashMap;
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
      _logger.info("Starting Kafka-To-PubSub pipeline with parameters bootstrap servers: {} input topics: {} output topics: {}",
              options.getBootstrapServers(), options.getInputTopics(), options.getOutputTopic());
      pipeline.apply(KafkaReadFactory.readFromKafkaWithoutMetadata(options.getBootstrapServers(), Arrays.asList(options.getInputTopics().split(",")), kafkaConfig))
              .apply("createValues", Values.create())
              .apply(MapElements.via(new SimpleFunction<String, String>() {
                 @Override
                 public String apply(String input)
                 {
                    _logger.info(input);
                    return input;
                 }
              }));

      pipeline.run().waitUntilFinish();
   }
      private static final Logger _logger = LoggerFactory.getLogger(WalmartParse.class);
}
