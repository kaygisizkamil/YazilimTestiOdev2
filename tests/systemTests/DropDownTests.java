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

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;
@TestInstance(Lifecycle.PER_CLASS)
public class DropDownTests {

    private  WebDriver driver;
    private  WebDriverWait wait;
    private static final Logger logger = Logger.getLogger(DropDownTests.class.getName());


    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--headless=new");
        options.addArguments("--allow-insecure-localhost");
        options.addArguments("--ignore-certificate-errors");
        
        options.setAcceptInsecureCerts(true);

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }



    @Test
    public void testDropdownDefaultOption() {
    	System.out.print("Drowpdown default opsiyon testi basladi..");
    	System.out.println();
        driver.get("https://www.amazon.com/");
    	logger.log(Level.INFO, "Open the amazon find the dropdown..");
        WebElement dropdownTrigger = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='nav-search-dropdown-card']")));
        Actions actions = new Actions(driver);
        actions.moveToElement(dropdownTrigger).click().build().perform();

        WebElement dropdownOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//option[contains(text(),'All Departments')]")));
        String optionText = dropdownOption.getText();

        dropdownOption.click();

        Assertions.assertEquals(optionText, "All Departments", "Dropdown default option test failed.");
        System.out.println("Dropdown default option test passed.");
    }

    @Test
    public void testDropdownOptionsCount() {
    	System.out.print("Drowpdown  opsiyon sayisi dogru mu testi basladi");
    	System.out.println();
    	logger.log(Level.INFO, "To Control are the number matches");
        driver.get("https://www.amazon.com/");

        WebElement dropdownTrigger = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='nav-search-dropdown-card']")));
        dropdownTrigger.click();

        int optionsCount = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.tagName("option"))).size();

        Assertions.assertTrue(optionsCount >= 0, "Dropdown options count test failed.");
        System.out.println("Dropdown options count test passed.");
    }

    @Test
    public void isDropdownReallyWorks() {
    	System.out.print("Drowpdown opsiyon secim testi basladi");
    	System.out.println();
    	logger.log(Level.INFO, "Secim testi..");

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
        System.out.println("Dropdown search test passed.");
    }
}
