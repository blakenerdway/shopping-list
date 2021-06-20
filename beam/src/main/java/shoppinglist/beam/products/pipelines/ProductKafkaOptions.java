package shoppinglist.beam.products.pipelines;

import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.Validation;

/**
 * @author bordway@ihmc.us 6/14/2021
 */
public interface ProductKafkaOptions extends PipelineOptions {
   @Description(
           "Comma Separated list of Kafka Bootstrap Servers (e.g: server1:[port],server2:[port]).")
   @Validation.Required
   String getBootstrapServers();

   void setBootstrapServers(String value);

   @Description(
           "Comma Separated list of Kafka topic(s) to read the input from (e.g: topic1,topic2).")
   @Validation.Required
   String getInputTopics();

   void setInputTopics(String value);
}
