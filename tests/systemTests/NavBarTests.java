/**
*
* @author Kamil Kaygisiz kamil.kaygisiz1@ogr.sakarya.edu.tr
* @since 9.05.2023
* <p>
* Sayfadaki left navbarın duzgun calistigini teyit eder her method için ayrica
* aciklama ustlerine eklenmistir.
* </p>
*/

package systemTests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import org.openqa.selenium.JavascriptExecutor;

import java.time.Duration;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

class NavBarTests {

	  private WebDriver driver;
	  private WebDriverWait wait;


	    @BeforeEach
	    public void setUp() {
	        System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver.exe");
	        driver = new ChromeDriver();
	        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        driver.get("https://www.amazon.com/");
	        driver.manage().window().maximize();
	    }

	    @AfterEach
	    public void tearDown() {
	        // quit the browser
	        driver.quit();
	    }
	    
	/*
	 * Find the navbar and click Shop by interest
	 * it is just a basic test that dont have suboptions
	 */
	    @Test
	    public void testSlideOutNavigation() throws InterruptedException {
	    	Thread.sleep(500);
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
	    }
	    /*
	     * it was not clickable using only .click() 
	     * that is why i used js executor
	     */
	    @Test
	    public void testNestedNavigation() throws InterruptedException {
	    	Thread.sleep(500);
	      WebElement navBar = driver.findElement(By.id("nav-main"));
	      WebElement element = driver.findElement(By.xpath("//a[contains(text(),'Gift Cards')]"));
	      JavascriptExecutor executor = (JavascriptExecutor) driver;
	      executor.executeScript("arguments[0].click();", element);
	      wait.until(ExpectedConditions.titleContains("Gift Cards"));
	      assertTrue(driver.getTitle().contains("Gift Cards"));
	    }



}
