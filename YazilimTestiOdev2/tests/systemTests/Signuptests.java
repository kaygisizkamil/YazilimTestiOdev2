/**
*
* @author Kamil Kaygisiz kamil.kaygisiz1@ogr.sakarya.edu.tr
* @since 9.05.2023
* <p>
* Sayfadaki sign up test edilir.Fake mail hizmeti veren siteler bile kullanilsa sitede ekstra koruma oldugu icin
* hardcoded daha once kayitli mailler ile test edildi.
* </p>
*/

package systemTests;

import java.lang.reflect.Method;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.ConsoleHandler;

//some of this test inside test class will be failed intentionally
//to test my internet connection ,because there are no wait statements 
//it will be added
public class Signuptests {
    private  WebDriver driver;
    private WebDriverWait wait;
    private static final Logger logger = Logger.getLogger(Signuptests.class.getName());


    @BeforeEach
    public  void setUp() {
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.ALL);
    	    ChromeOptions options = new ChromeOptions();
	        options.addArguments("--no-sandbox"); // Required for running in Docker
	        options.addArguments("--headless=new");
	        options.addArguments("--allow-insecure-localhost");
	        options.addArguments("--ignore-certificate-errors");
	        options.setAcceptInsecureCerts(true);
	      //  options.addArguments("--shm-size=2g");
	        driver = new ChromeDriver(options);
            Logger.getLogger("org.openqa.selenium").setLevel(Level.SEVERE);
            System.setProperty("webdriver.chrome.silentOutput", "true");//it still prints errors not completely silent
            logger.addHandler(consoleHandler);

             wait=new WebDriverWait(driver,Duration.ofSeconds(10));
    }
    @AfterEach
    public  void tearDown() {
        driver.quit();
    }
    
 @Test
    public void testSuccessfulSignUp() {
        System.out.println("\nSign-up test started..\n");
        navigateToSignUpPage();

        String email = "example@example.com";
        fillRegistrationForm("Kygsz kygsz", email, "yourpassword", "yourpassword");
        submitForm();
        wait.until(ExpectedConditions.urlToBe("https://www.amazon.com/ap/register"));

        String expectedUrl = "https://www.amazon.com/ap/register";
        String actualUrl = driver.getCurrentUrl();
        Assertions.assertEquals(expectedUrl, actualUrl, "Signup failed. Expected URL: " + expectedUrl + ", Actual URL: " + actualUrl);

        System.out.println("Sign-up test completed.");
         Method currentTestMethod = new Object(){}.getClass().getEnclosingMethod();
         String testName = currentTestMethod.getName();
         logger.log(Level.INFO, "Test adı: " + testName+" basariyla tamamlandi");
    }

    @Test
    public void testWeakPassword() {
        System.out.println("\nWeak password test started..\n");
        navigateToSignUpPage();

        String email = "example2@example.com";
        fillRegistrationForm("Kygsz kygsz", email, "weak", "weak");
        submitForm();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement passwordError = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("auth-password-invalid-password-alert")));

        String expectedErrorMessage = "Minimum 6 characters required";
        String actualErrorMessage = passwordError.getText().trim();
        Assertions.assertEquals(expectedErrorMessage, actualErrorMessage, "Invalid error message. Expected: " + expectedErrorMessage + ", Actual: " + actualErrorMessage);

        Method currentTestMethod = new Object(){}.getClass().getEnclosingMethod();
        String testName = currentTestMethod.getName();
        logger.log(Level.INFO, "Test adı: " + testName+" basariyla tamamlandi");    }

    @Test
    public void testInvalidEmail() {
        System.out.println("\nInvalid email test started..\n");
        navigateToSignUpPage();

        fillRegistrationForm("Kygsz kygsz", "invalidemail", "yourpassword", "yourpassword");
        submitForm();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement emailError = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='auth-email-invalid-claim-alert']")));

        String expectedErrorMessage = "Wrong or Invalid email address or mobile phone number. Please correct and try again.";
        String actualErrorMessage = emailError.getText().trim();
        Assertions.assertEquals(expectedErrorMessage, actualErrorMessage, "Invalid error message. Expected: " + expectedErrorMessage + ", Actual: " + actualErrorMessage);

        Method currentTestMethod = new Object(){}.getClass().getEnclosingMethod();
        String testName = currentTestMethod.getName();
        logger.log(Level.INFO, "Test adı: " + testName+" basariyla tamamlandi");    }

    @Test
    public void testExistingEmail() {
        System.out.println("\nExisting email test started..\n");
        navigateToSignUpPage();

        fillRegistrationForm("Kygsz kygsz", "existingemail@example.com", "yourpassword", "yourpassword");
        submitForm();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String currentUrl = driver.getCurrentUrl();
        String registrationPageUrl = "https://www.amazon.com/ap/register";

        if (currentUrl.equals(registrationPageUrl)) {
            WebElement emailError = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//body/div[@id='a-page']/div[1]/div[2]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]")));

            String expectedErrorMessage = "An account with this email address already exists.";
            String actualErrorMessage = emailError.getText().trim();
            Assertions.assertEquals(expectedErrorMessage, actualErrorMessage, "Invalid error message. Expected: " + expectedErrorMessage + ", Actual: " + actualErrorMessage);
        } else {
            String protectYourAccountUrl = "https://www.amazon.com/ap/cvf/request?arb=e3bc0d4f-3e40-429b-a605-66de88845a8f";
            Assertions.assertTrue(currentUrl.contains("https://www.amazon.com/ap/cvf/request?arb="), "Redirected to unexpected URL: " + currentUrl);
        }

        Method currentTestMethod = new Object(){}.getClass().getEnclosingMethod();
        String testName = currentTestMethod.getName();
        logger.log(Level.INFO, "Test adı: " + testName+" basariyla tamamlandi");    }

    public void navigateToSignUpPage() {
        driver = new ChromeDriver();
        driver.get("https://www.amazon.com");

        WebElement signInLink = driver.findElement(By.id("nav-link-accountList"));
        signInLink.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement createAccountButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("createAccountSubmit")));
        createAccountButton.click();
    }

    public void fillRegistrationForm(String name, String email, String password, String confirmPassword) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement nameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("ap_customer_name")));
        nameField.sendKeys(name);

        WebElement emailField = driver.findElement(By.id("ap_email"));
        emailField.sendKeys(email);

        WebElement passwordField = driver.findElement(By.id("ap_password"));
        passwordField.sendKeys(password);

        WebElement reEnterPasswordField = driver.findElement(By.id("ap_password_check"));
        reEnterPasswordField.sendKeys(confirmPassword);
    }

    public void submitForm() {
        WebElement createAccountSubmit = driver.findElement(By.id("continue"));
        createAccountSubmit.click();
    }
   

}
