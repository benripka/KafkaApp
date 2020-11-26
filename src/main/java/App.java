import java.io.IOException;

public class App {


   public static void main(String[] args) throws IOException, InterruptedException {

      Config config = new Config();

      BirthdayProducer birthdayProducer = new BirthdayProducer(config);
      BirthdayConsumer birthdayConsumer = new BirthdayConsumer(config);

      birthdayProducer.start();
      birthdayConsumer.start();
   }
}
