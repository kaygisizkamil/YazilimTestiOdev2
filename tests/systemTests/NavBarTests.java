/**
*
* @author Kamil Kaygisiz kamil.kaygisiz1@ogr.sakarya.edu.tr
* @since 9.05.2023
* <p>
* Sayfadaki left navbarin duzgun calistigini teyit eder her method icin ayrica
* aciklama ustlerine eklenmistir.
* </p>
*/

package systemTests;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.lang.reflect.Method;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.logging.ConsoleHandler;


import java.time.Duration;
import java.util.logging.Logger;
import java.util.logging.Level;

public class NavBarTests {

    private  WebDriver driver;
    private WebDriverWait wait;
    private static final Logger logger = Logger.getLogger(NavBarTests.class.getName());

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
	    
	/*
	 * Find the navbar and click Shop by interest
	 * it is just a basic test that dont have suboptions.
	 */
	    @Test
	    public void testSlideOutNavigation()  {
			System.out.print("\nSliding navbar icindeki testler basladi\n");
	    	driver.get("https://www.amazon.com");

	        // Find the 'All' button and hover over it to reveal the slide out navigation
	        WebElement allButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#nav-hamburger-menu")));
	        Actions actions = new Actions(driver);
	        actions.moveToElement(allButton).click().build().perform();

	        // Wait for the slide out navigation to appear
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        WebElement slideOutNavigation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("hmenu-content")));

	        // Click on a category link in the slide out navigation
	        WebElement categoryLink = slideOutNavigation.findElement(By.xpath("//a[contains(text(),'Shop By Interest')]"));
	        categoryLink.click();

	        // Wait for the page to load and assert that the correct category page is displayed
	        wait.until(ExpectedConditions.titleContains("Shop By Interest"));
	        String pageTitle = driver.getTitle();
	        assertEquals("Shop By Interest", pageTitle);
			Method currentTestMethod = new Object(){}.getClass().getEnclosingMethod();
			String testName = currentTestMethod.getName();
			logger.log(Level.INFO, "Test adı: " + testName+" basariyla tamamlandi");
	    }
	    /*
	     * it was not clickable using only .click() 
	     * that is why i used js executor
	     */
	    @Test
	    public void testNestedNavigation()   {
			System.out.println("\nNavbar yonlendirme testi basladi..\n");
	    	driver.get("https://www.amazon.com");
	      WebElement navBar = driver.findElement(By.id("nav-main"));
	      WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Gift Cards')]")));
	      JavascriptExecutor executor = (JavascriptExecutor) driver;
	      executor.executeScript("arguments[0].click();", element);
	      wait.until(ExpectedConditions.titleContains("Gift Cards"));
	      assertTrue(driver.getTitle().contains("Gift Cards"));
			Method currentTestMethod = new Object(){}.getClass().getEnclosingMethod();
			String testName = currentTestMethod.getName();
			logger.log(Level.INFO, "Test adı: " + testName+" basariyla tamamlandi");
	    }
	   



}
