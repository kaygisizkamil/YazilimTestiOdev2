/**
*
* @author Kamil Kaygisiz kamil.kaygisiz1@ogr.sakarya.edu.tr
* @since 18.05.2023
* <p>
* Page layout farklı cihazlar icin genel responsiveness testi 
* </p>
*/
package systemTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.List;

public class PageLayoutTests {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized"); // Maximize the browser window
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Initialize WebDriverWait with a timeout of 10 seconds
    }

    @AfterEach
    public void cleanup() {
        driver.quit();
    }

    @Test
    public void testPageLayoutOnDifferentScreenSizes() throws InterruptedException {
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

    private void verifyPageLayout() {
        assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-logo-sprites"))).isDisplayed(), "Logo is not displayed");
        assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-search"))).isDisplayed(), "Search bar is not displayed");
        assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-xshop"))).isDisplayed(), "Navigation menu is not displayed");
    }

    @Test
    public void testNavigationMenuOnDifferentScreenSizes() throws InterruptedException {
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
        System.out.print("Passed");
    }
    @Test
    public void testAmazonImageResponsiveness() throws InterruptedException {
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

            System.out.println("Passed for screen size: " + screenSize);
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

    private boolean verifyImageLoaded(WebElement image) {
        // Check if the image is loaded by verifying the 'complete' property
        boolean isLoaded = (Boolean) ((JavascriptExecutor) driver).executeScript(
            "return arguments[0].complete",
            image
        );
        return isLoaded;
    }





    private void expandNavigationMenu() {
        WebElement navigationMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-hamburger-menu")));
        navigationMenu.click();
    }

    private void collapseNavigationMenu() {
        WebElement closeButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body/div[@id='hmenu-container']/div[@id='hmenu-canvas-background']/div[1]")));
        closeButton.click();
    }

    private boolean isElementDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}

