import java.util.Random;
import java.util.Random;
public class EmailGenerator {
    public static String generateRandomEmail() {
        Random random = new Random();
        int uniqueNumb = random.nextInt(1001);
        String emailName = "Elias_" + uniqueNumb; // The emailName + random generated number.
        String domainName = "NBAplayer.com"; // The Domain for emailName
        return emailName + "@" + domainName; // Retuns the final the email address
    }
}

