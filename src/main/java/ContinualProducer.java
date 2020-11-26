import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public abstract class ContinualProducer<T> extends Thread {
   private String topic;
   private Producer<String, T> birthdayProducer;

   public ContinualProducer(Config config, String topic) throws IOException, InterruptedException {
      final Properties props = loadConfig(config);
      this.topic = topic;

      props.put(ProducerConfig.ACKS_CONFIG, "all");
      props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
      props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

      this.birthdayProducer = new KafkaProducer<String, T>(props);
   }

   public void run() {
      while(true) {
         T record = generateRecord();

         birthdayProducer.send(new ProducerRecord<String, T>(topic, record), new Callback() {
            @Override
            public void onCompletion(RecordMetadata m, Exception e) {
               if (e != null) {
                  e.printStackTrace();
               } else {
                  System.out.printf("Produced record to topic %s partition [%d] @ offset %d%n", m.topic(), m.partition(), m.offset());
               }
            }
         });

         birthdayProducer.flush();
         System.out.println(String.format("A message was produced to topic %s", topic));

         try {
            sleep(1000);
         } catch (InterruptedException e) {
            e.printStackTrace();
            break;
         }
      }
   }

   protected abstract T generateRecord();

   private static Properties loadConfig(Config config) throws IOException {
      final String configFile = config.ConfluentConfigPath;

      if (!Files.exists(Paths.get(configFile))) {
         throw new IOException(configFile + " not found.");
      }
      final Properties cfg = new Properties();
      try (InputStream inputStream = new FileInputStream(configFile)) {
         cfg.load(inputStream);
      }
      return cfg;
   }
}
