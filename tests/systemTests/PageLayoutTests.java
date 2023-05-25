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
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Dimension;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

public class PageLayoutTests {
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static final Logger logger = Logger.getLogger(PageLayoutTests.class.getName());

    @BeforeAll
    public static void setUp() {
    	  ChromeOptions options = new ChromeOptions();
	        options.addArguments("--no-sandbox"); // Required for running in Docker
	        options.addArguments("--headless=new");
	        options.addArguments("--allow-insecure-localhost");
	        options.addArguments("--ignore-certificate-errors");
	        options.setAcceptInsecureCerts(true);
	      //  options.addArguments("--shm-size=2g");
	        driver = new ChromeDriver(options);
	       wait=new WebDriverWait(driver,Duration.ofSeconds(10));
    }
    @AfterAll
    public static void tearDown() {
        driver.quit();
    }
	     

    @Test
    public void testPageLayoutOnDifferentScreenSizes() throws InterruptedException {
    	driver.get("https://www.amazon.com");

    	System.out.print("Page-layout farkli ekran boyutlari ile test basladi.");
    	System.out.println();
        // Open Amazon website
        driver.get("https://www.amazon.com/");

        Dimension desktopSize = new Dimension(1366, 768);
        driver.manage().window().setSize(desktopSize);
        Thread.sleep(500);

        verifyPageLayout();

        Dimension tabletSize = new Dimension(768, 1024);
        driver.manage().window().setSize(tabletSize);
        Thread.sleep(500);

        verifyPageLayout();

        Dimension mobileSize = new Dimension(360, 640);
        driver.manage().window().setSize(mobileSize);
        Thread.sleep(500);

        verifyPageLayout();
    }

    public void verifyPageLayout() {
        assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-logo-sprites"))).isDisplayed(), "Logo is not displayed");
        assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-search"))).isDisplayed(), "Search bar is not displayed");
        assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-xshop"))).isDisplayed(), "Navigation menu is not displayed");
    }

    @Test
    public void testNavigationMenuOnDifferentScreenSizes() throws InterruptedException {
    	driver.get("https://www.amazon.com");

    	System.out.print("Nav-menu farkli ekran boyutlari ile test basladi.");
        // Open Amazon website
        driver.get("https://www.amazon.com/");

        Dimension[] screenSizes = {
            new Dimension(1366, 768), // Desktop size
            new Dimension(768, 1024), // Tablet size
            new Dimension(360, 640) // Mobile size
        };

        for (Dimension screenSize : screenSizes) {
            driver.manage().window().setSize(screenSize);
            Thread.sleep(500);

            expandNavigationMenu();
            collapseNavigationMenu();
        }
    }
    @Test
    public void testAmazonImageResponsiveness() throws InterruptedException {
    	driver.get("https://www.amazon.com");

    	System.out.print("Image responsiveness testi basladi..");

        // Open Amazon website
        driver.get("https://www.amazon.com/");

        Dimension[] screenSizes = {
            new Dimension(1366, 768), // Desktop size
            new Dimension(768, 1024), // Tablet size
            new Dimension(360, 640) // Mobile size
        };

        for (Dimension screenSize : screenSizes) {
            driver.manage().window().setSize(screenSize);
            Thread.sleep(1000);

            WebElement image = driver.findElement(By.tagName("img"));
            verifyImageResponsiveness(image);
        }
    }

    public void verifyImageResponsiveness(WebElement image) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

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

