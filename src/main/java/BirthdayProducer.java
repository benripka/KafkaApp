import java.io.IOException;

public class BirthdayProducer extends ContinualProducer<String>{

   private static final String TOPIC_NAME = "birthdays";
   private int messageCounter;

   public BirthdayProducer(Config config) throws IOException, InterruptedException {
      super(config, TOPIC_NAME);
      messageCounter = 0;
   }

   @Override
   protected String generateRecord() {
      messageCounter++;
      return "Message " + Integer.toString(messageCounter);
   }
}
