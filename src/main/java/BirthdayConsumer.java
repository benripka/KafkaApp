import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.log4j.BasicConfigurator;

import java.util.Properties;

public class BirthdayConsumer extends ContinualConsumer {

   private final static String BIRTHDAY_STREAM = "birthdays";

   public BirthdayConsumer(Config config) {
      BasicConfigurator.configure();

      Properties props = new Properties();
      props.put(StreamsConfig.APPLICATION_ID_CONFIG, "birthday-stream");
      props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, config.BrokerHost);
      props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
      props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

      StreamsBuilder builder = new StreamsBuilder();

      // Get a handle on the the birthdays topic stream
      KStream<String, String> birthdays = builder.stream(BIRTHDAY_STREAM);

      birthdays.to("new-birthdays");

      stream = new KafkaStreams(builder.build(), props);
   }
}
