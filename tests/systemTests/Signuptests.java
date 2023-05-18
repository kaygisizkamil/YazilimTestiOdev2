/**
*
* @author Kamil Kaygisiz kamil.kaygisiz1@ogr.sakarya.edu.tr
* @since 9.05.2023
* <p>
* Sayfadaki sign up test edilir.Fake mail hizmeti veren siteler bile kullanılsa sitede ekstra koruma oldugu icin
* hardcoded daha once kayitli mailler ile test edildi.
* </p>
*/

package systemTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import org.junit.runners.MethodSorters;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Signuptests {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.amazon.com/");
    }

    @AfterEach
    public void cleanup() {
        driver.quit();
    }

    @Test
    public void testSuccessfulSignUp() {
        navigateToSignUpPage();

        String email = "example@example.com"; 
        fillRegistrationForm("Kygsz kygsz", email, "yourpassword", "yourpassword");
        submitForm();

        String expectedUrl = "https://www.amazon.com/ap/register";
        String actualUrl = driver.getCurrentUrl();
        Assertions.assertEquals(expectedUrl, actualUrl, "Signup failed. Expected URL: " + expectedUrl + ", Actual URL: " + actualUrl);
    }

    @Test
    public void testWeakPassword() {
        navigateToSignUpPage();

        String email = "example2@example.com"; 
        fillRegistrationForm("Kygsz kygsz", email, "weak", "weak");
        submitForm();

        String expectedErrorMessage = "Minimum 6 characters required";
        WebElement passwordError = driver.findElement(By.id("auth-password-invalid-password-alert"));
        String actualErrorMessage = passwordError.getText().trim();
        Assertions.assertEquals(expectedErrorMessage, actualErrorMessage, "Invalid error message. Expected: " + expectedErrorMessage + ", Actual: " + actualErrorMessage);
    }

    @Test
    public void testInvalidEmail() {
        navigateToSignUpPage();

        fillRegistrationForm("Kygsz kygsz", "invalidemail", "yourpassword", "yourpassword");
        submitForm();

        String expectedErrorMessage = "Wrong or Invalid email address or mobile phone number. Please correct and try again.";
        WebElement emailError = driver.findElement(By.xpath("//div[@id='auth-email-invalid-claim-alert']"));
        String actualErrorMessage = emailError.getText().trim();
        Assertions.assertEquals(expectedErrorMessage, actualErrorMessage, "Invalid error message. Expected: " + expectedErrorMessage + ", Actual: " + actualErrorMessage);
    }

    @Test
    public void testExistingEmail() {
        navigateToSignUpPage();

        fillRegistrationForm("Kygsz kygsz", "existingemail@example.com", "yourpassword", "yourpassword");
        submitForm();

        String currentUrl = driver.getCurrentUrl();
        String registrationPageUrl = "https://www.amazon.com/ap/register";

        if (currentUrl.equals(registrationPageUrl)) {
            WebElement emailError = driver.findElement(By.xpath("//body/div[@id='a-page']/div[1]/div[2]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]"));
            String expectedErrorMessage = "An account with this email address already exists.";
            String actualErrorMessage = emailError.getText().trim();
            Assertions.assertEquals(expectedErrorMessage, actualErrorMessage, "Invalid error message. Expected: " + expectedErrorMessage + ", Actual: " + actualErrorMessage);
        }else {
            String protectYourAccountUrl = "https://www.amazon.com/ap/cvf/request?arb=e3bc0d4f-3e40-429b-a605-66de88845a8f";
            Assertions.assertTrue(currentUrl.contains("https://www.amazon.com/ap/cvf/request?arb="), "Redirected to unexpected URL: " + currentUrl);           
        }
    }


    private void navigateToSignUpPage() {
        WebElement signInLink = driver.findElement(By.id("nav-link-accountList"));
        signInLink.click();

        WebElement createAccountButton = driver.findElement(By.id("createAccountSubmit"));
        createAccountButton.click();
    }

    private void fillRegistrationForm(String name, String email, String password, String confirmPassword) {
        WebElement nameField = driver.findElement(By.id("ap_customer_name"));
        nameField.sendKeys(name);

        WebElement emailField = driver.findElement(By.id("ap_email"));
        emailField.sendKeys(email);

        WebElement passwordField = driver.findElement(By.id("ap_password"));
       
        passwordField.sendKeys(password);

        WebElement reEnterPasswordField = driver.findElement(By.id("ap_password_check"));
        reEnterPasswordField.sendKeys(confirmPassword);
    }

    private void submitForm() {
        WebElement createAccountSubmit = driver.findElement(By.id("continue"));
        createAccountSubmit.click();
    }


}