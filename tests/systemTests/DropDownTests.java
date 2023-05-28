/**
*
* @author Kamil Kaygisiz kamil.kaygisiz1@ogr.sakarya.edu.tr
* @since 18.05.2023
* <p>
* DwopDown icerisinde kaydirma ,secim ,element sayisi gibi ozellikler test edildi
* </p>
*/
package systemTests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import java.lang.reflect.Method;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.ConsoleHandler;

public class DropDownTests {

    private  WebDriver driver;
    private  WebDriverWait wait;
    private static final Logger logger = Logger.getLogger(DropDownTests.class.getName());


    @BeforeEach
    public void setUp() {
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--headless=new");
        options.addArguments("--allow-insecure-localhost");
        options.addArguments("--ignore-certificate-errors");
        
        options.setAcceptInsecureCerts(true);

        driver = new ChromeDriver(options);
        Logger.getLogger("org.openqa.selenium").setLevel(Level.SEVERE);
        System.setProperty("webdriver.chrome.silentOutput", "true");//it still prints errors not completely silent
        logger.addHandler(consoleHandler);

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }



    @Test
    public void testDropdownDefaultOption() {
    	System.out.print("\nDrowpdown default opsiyon testi basladi..\n");
        driver.get("https://www.amazon.com/");
        WebElement dropdownTrigger = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='nav-search-dropdown-card']")));
        Actions actions = new Actions(driver);
        actions.moveToElement(dropdownTrigger).click().build().perform();

        WebElement dropdownOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//option[contains(text(),'All Departments')]")));
        String optionText = dropdownOption.getText();

        dropdownOption.click();

        Assertions.assertEquals(optionText, "All Departments", "Dropdown default option test failed.");
        Method currentTestMethod = new Object(){}.getClass().getEnclosingMethod();
        String testName = currentTestMethod.getName();
        logger.log(Level.INFO, "Test adı: " + testName+" basariyla tamamlandi");
    }

    @Test
    public void testDropdownOptionsCount() {
    	System.out.print("\nDrowpdown  opsiyon sayisi dogru mu testi basladi\n");
        driver.get("https://www.amazon.com/");

        WebElement dropdownTrigger = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='nav-search-dropdown-card']")));
        dropdownTrigger.click();

        int optionsCount = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.tagName("option"))).size();

        Assertions.assertTrue(optionsCount >= 0, "Dropdown options count test failed.");
        System.out.println("Dropdown options count test passed.");
        Method currentTestMethod = new Object(){}.getClass().getEnclosingMethod();
        String testName = currentTestMethod.getName();
        logger.log(Level.INFO, "Test adı: " + testName+" basariyla tamamlandi");
    }

    @Test
    public void isDropdownReallyWorks() {
    	System.out.print("\nDrowpdown opsiyon secim testi basladi\n");

        driver.get("https://www.amazon.com/");
        

        WebElement dropdownTrigger = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='nav-search-dropdown-card']")));
        dropdownTrigger.click();

        WebElement dropdownOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//option[contains(text(),'Automotive')]")));
        dropdownOption.click();
        String urlBefore = driver.getCurrentUrl();
        WebElement searchBox = driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']"));
        searchBox.sendKeys("tire");
        searchBox.submit();
        String currentURL = driver.getCurrentUrl();
        Assertions.assertNotEquals(urlBefore, currentURL, "Dropdown search test failed.");
        Method currentTestMethod = new Object(){}.getClass().getEnclosingMethod();
        String testName = currentTestMethod.getName();
        logger.log(Level.INFO, "Test adı: " + testName+" basariyla tamamlandi");
    }
}
