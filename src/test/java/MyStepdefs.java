import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import static org.junit.Assert.*;

public class MyStepdefs {
    private WebDriver driver;
    private ChromeOptions Ch_options;
    private EdgeOptions Ed_options;
    private boolean the_surname = false;
    private boolean the_password = false;
    private boolean the_checkbox = false;
    public MyStepdefs() {
        Ch_options = new ChromeOptions();
        Ed_options = new EdgeOptions();
        Ch_options.addArguments("--incognito");
        Ed_options.addArguments("--inprivate");
    }

    @Given("I choose {string}")
    public void iChoose(String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions Ch_options = new ChromeOptions();
            Ch_options.addArguments("--incognito");
            driver = new ChromeDriver(Ch_options);
        } else if (browser.equalsIgnoreCase("edge")) {
            Ed_options = new EdgeOptions();
            Ed_options.addArguments("--inprivate");
            driver = new EdgeDriver(Ed_options);
        } else {
            System.out.println("Wrong edited example in featurefile, please choose supported EDGE or CHROME: " + browser);
        }
        driver.get("https://membership.basketballengland.co.uk/NewSupporterAccount");
        driver.manage().window().setSize(new Dimension(1762, 927));
    }

    @Given("I enter my date of birth {string}")
    public void iEnterMyDateOfBirth(String bday) {
        driver.findElement(By.id("dp")).click();
        driver.findElement(By.id("dp")).sendKeys(bday);
    }

    @And("I enter firstname {string}")
    public void iEnterFirstname(String fn) {
        driver.findElement(By.id("member_firstname")).click();
        driver.findElement(By.id("member_firstname")).sendKeys(fn);
    }

    @And("I enter lastname {string}")
    public void iEnterLastname(String surname) {
        driver.findElement(By.id("member_lastname")).sendKeys(surname);
        if (surname.isEmpty()) {
            System.out.println("Surname is missing");
            the_surname = false;
        } else {
            System.out.println("Surname has been provided");
            the_surname = true;
        }
    }

    @And("I enter email & confirm email {string}")
    public void iEnterEmailConfirmEmail(String generatedEmail) {
        String email = EmailGenerator.generateRandomEmail();
        WebElement fieldEmail = driver.findElement(By.id("member_emailaddress"));
        WebElement fieldEmailConfirm = driver.findElement(By.id("member_confirmemailaddress"));
        fieldEmail.click();
        fieldEmail.sendKeys(email);
        fieldEmailConfirm.click();
        fieldEmailConfirm.sendKeys(email);
    }

    @And("I enter password & confirm password {string}")
    public void iEnterPasswordConfirmPassword(String pass) {
        String defaultPassword = "Password12345";
        WebElement passwordField = driver.findElement(By.id("signupunlicenced_password"));
        passwordField.sendKeys(defaultPassword);
        WebElement confirmPasswordField = driver.findElement(By.id("signupunlicenced_confirmpassword"));
        confirmPasswordField.sendKeys(pass);
        the_password = defaultPassword.equals(pass);
        System.out.println("Passwords match: " + the_password);
    }

    @And("I have terms & conditions {string}")
    public void iHaveTermsConditions(String checkBoxStatus) {
        System.out.println("Checkbox status received: " + checkBoxStatus);
        WebElement LABEL = driver.findElement(By.cssSelector(".md-checkbox > .md-checkbox:nth-child(1) > label"));
       // WebElement BOX_CLICKED = driver.findElement(By.cssSelector(".md-checkbox > .md-checkbox:nth-child(1) .box"));
       // WebElement UNCLICKED = driver.findElement(By.cssSelector(".md-checkbox > .md-checkbox:nth-child(1) .check"));
        boolean isChecked = LABEL.getAttribute("class").contains("checked");
        if (checkBoxStatus.equalsIgnoreCase("checked") && !isChecked) {
            LABEL.click();
            System.out.println("Checkbox clicked to check.");
        }
        if (checkBoxStatus.equalsIgnoreCase("unchecked") && isChecked) {
            LABEL.click();
            System.out.println("Checkbox clicked to uncheck.");
        }
        the_checkbox = checkBoxStatus.equalsIgnoreCase("checked");
    }

    @When("I am Over 18 & code of conduct checked")
    public void iHaveOverCodeOfConductChecked() {
        driver.findElement(By.cssSelector(".md-checkbox:nth-child(2) > label")).click();
        driver.findElement(By.cssSelector(".md-checkbox:nth-child(7) > label")).click();
    }

    @When("I press join button")
    public void iPressJoinButton() throws InterruptedException {
        driver.findElement(By.name("join")).click();
        Thread.sleep(2000);
    }

    @Then("I am registered")
    public void iAmRegistered() {
        if (!the_checkbox) {
            System.out.println("The CHECKBOX is missing, REGISTRATION will fail");
            WebElement check_actual = driver.findElement(By.xpath("//form[@id='signup_form']/div[11]/div/div[2]/div/span/span"));
            String actual = check_actual.getText();
            String expected = "You must confirm that you have read and accepted our Terms and Conditions";
            assertEquals(expected, actual);
        } else if (!the_surname) {
            System.out.println("The SURNAME is missing, REGISTRATION will fail");
            WebElement surname_actual = driver.findElement(By.cssSelector("#signup_form > div:nth-child(6) > div:nth-child(2) > div > span"));
            String actual = surname_actual.getText();
            String expected = "Last Name is required";
            assertEquals(expected, actual);
        } else if (!the_password) {
            System.out.println("The PASSWORD is missing, REGISTRATION will fail");
            WebElement password_actual = driver.findElement(By.xpath("//form[@id='signup_form']/div[8]/div/div[2]/div[2]/div/span/span"));
            String actual = password_actual.getText();
            String expected = "Password did not match";
            assertEquals(expected, actual);
        } else {
            String actual = driver.findElement(By.cssSelector(".bold:nth-child(1)")).getText();
            String expected = "THANK YOU FOR CREATING AN ACCOUNT WITH BASKETBALL ENGLAND";
            System.out.println("YOUR REGISTRATION is SUCCESSFUL: " + actual);
            assertEquals(expected, actual);
        }
        driver.quit();
    }
}
