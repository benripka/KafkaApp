import org.apache.kafka.streams.KafkaStreams;

public class ContinualConsumer {
   protected KafkaStreams stream;

   public void start() {
      try {
         stream.start();
      } catch (NullPointerException e) {
         System.out.println("The consumer stream was not properly initialized before starting.");
      }
   }
}
