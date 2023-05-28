/**
*
* @author Kamil Kaygisiz kamil.kaygisiz1@ogr.sakarya.edu.tr
* @since 18.05.2023
* <p>
* Page layout farkli cihazlar icin genel responsiveness testi 
* </p>
*/
package systemTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Dimension;
import java.lang.reflect.Method;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.logging.ConsoleHandler;


import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.logging.Logger;
import java.util.logging.Level;

public class PageLayoutTests {
    private  WebDriver driver;
    private WebDriverWait wait;
    private  final Logger logger = Logger.getLogger(PageLayoutTests.class.getName());

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
    public void testPageLayoutOnDifferentScreenSizes() {
        System.out.println("\nPage layout test started.\n");
        driver.get("https://www.amazon.com");

        Dimension desktopSize = new Dimension(1366, 768);
        driver.manage().window().setSize(desktopSize);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-logo-sprites")));

        verifyPageLayout();

        Dimension tabletSize = new Dimension(768, 1024);
        driver.manage().window().setSize(tabletSize);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-logo-sprites")));

        verifyPageLayout();

        Dimension mobileSize = new Dimension(360, 640);
        driver.manage().window().setSize(mobileSize);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-logo-sprites")));

        verifyPageLayout();
        Method currentTestMethod = new Object(){}.getClass().getEnclosingMethod();
        String testName = currentTestMethod.getName();
        logger.log(Level.INFO, "Test adı: " + testName+" basariyla tamamlandi");    }

    public void verifyPageLayout() {
        assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-logo-sprites"))).isDisplayed(), "Logo is not displayed");
        assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-search"))).isDisplayed(), "Search bar is not displayed");
        assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-xshop"))).isDisplayed(), "Navigation menu is not displayed");
    }

     @Test
    public void testNavigationMenuOnDifferentScreenSizes() {
        System.out.println("Navigation menu test started.");

        driver.get("https://www.amazon.com");

        Dimension[] screenSizes = {
                new Dimension(1366, 768), // Desktop size
                new Dimension(768, 1024), // Tablet size
                new Dimension(360, 640) // Mobile size
        };

        for (Dimension screenSize : screenSizes) {
            driver.manage().window().setSize(screenSize);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-logo-sprites")));

            expandNavigationMenu();
            collapseNavigationMenu();
        }

         Method currentTestMethod = new Object(){}.getClass().getEnclosingMethod();
         String testName = currentTestMethod.getName();
         logger.log(Level.INFO, "Test adı: " + testName+" basariyla tamamlandi");
    }
   @Test
    public void testAmazonImageResponsiveness() {
        System.out.println("Image responsiveness test started.");

        driver.get("https://www.amazon.com");

        Dimension[] screenSizes = {
                new Dimension(1366, 768), // Desktop size
                new Dimension(768, 1024), // Tablet size
                new Dimension(360, 640) // Mobile size
        };

        for (Dimension screenSize : screenSizes) {
            driver.manage().window().setSize(screenSize);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("img")));

            WebElement image = driver.findElement(By.tagName("img"));
            verifyImageResponsiveness(image);
        }

       Method currentTestMethod = new Object(){}.getClass().getEnclosingMethod();
       String testName = currentTestMethod.getName();
       logger.log(Level.INFO, "Test adı: " + testName+" basariyla tamamlandi");
    }

    public void verifyImageResponsiveness(WebElement image) {

        try {
            assertTrue(image.isDisplayed(), "Image is not displayed");
        } catch (AssertionError e) {
            System.out.println("Image is not displayed: " + image.getAttribute("src"));
            return;
        }

        assertTrue(verifyImageLoaded(image), "Image failed to load");
    }

    public boolean verifyImageLoaded(WebElement image) {
        boolean isLoaded = (Boolean) ((JavascriptExecutor) driver).executeScript(
            "return arguments[0].complete",
            image
        );
        return isLoaded;
    }
    public void expandNavigationMenu() {
        WebElement navigationMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-hamburger-menu")));
        navigationMenu.click();
    }

    public  void collapseNavigationMenu() {
        WebElement closeButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body/div[@id='hmenu-container']/div[@id='hmenu-canvas-background']/div[1]")));
        closeButton.click();
    }


}

