package shoppinglist.beam.kafka;

import java.util.List;
import java.util.Map;
import org.apache.beam.sdk.io.kafka.KafkaIO;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PBegin;
import org.apache.beam.sdk.values.PCollection;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.joda.time.Duration;


/**
 * @author bordway@ihmc.us 6/14/2021
 */
public class KafkaReadFactory {

   /**
    * Configures Kafka consumer.
    *
    * @param bootstrapServers Kafka servers to read from
    * @param topicsList       Kafka topics to read from
    * @param kafkaConfig      configuration for the Kafka consumer
    * @return configured reading from Kafka
    */
   public static PTransform<PBegin, PCollection<KV<String, String>>> readFromKafkaWithoutMetadata(
           String bootstrapServers,
           List<String> topicsList,
           Map<String, Object> kafkaConfig)
   {
      return KafkaIO.<String, String>read()
              .withBootstrapServers(bootstrapServers)
              .withTopics(topicsList)  // use withTopics(List<String>) to read from multiple topics.
              .withKeyDeserializer(StringDeserializer.class)
              .withValueDeserializer(StringDeserializer.class)

              // Above four are required configuration. returns PCollection<KafkaRecord<Long, String>>

              // Rest of the settings are optional :

              // you can further customize KafkaConsumer used to read the records by adding more
              // settings for ConsumerConfig. e.g :
              .withConsumerConfigUpdates(kafkaConfig)

              // set event times and watermark based on 'LogAppendTime'. To provide a custom
              // policy see withTimestampPolicyFactory(). withProcessingTime() is the default.
              // Use withCreateTime() with topics that have 'CreateTime' timestamps.
              .withCreateTime(Duration.millis(1000))

              // restrict reader to committed messages on Kafka (see method documentation).
              .withReadCommitted()

              // offset consumed by the pipeline can be committed back.
              .commitOffsetsInFinalize().withoutMetadata();

   }
}
