import java.util.Random;
import java.util.Random;
public class EmailGenerator {
    public static String generateRandomEmail() {
        // Emailname + generated number from random method, and then add the domain name to it, and returns
        // the finaly of the email adress.
        Random random = new Random();
        int uniqueNumb = random.nextInt(1001);
        String emailName = "Elias_" + uniqueNumb;
        String domainName = "NBAplayer.com";
        return emailName + "@" + domainName;
    }
}

