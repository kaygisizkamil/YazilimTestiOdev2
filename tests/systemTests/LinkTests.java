/**
*
* @author Kamil Kaygisiz kamil.kaygisiz1@ogr.sakarya.edu.tr
* @since 18.05.2023
* <p>
* Sayfadaki linklerin tiklama sonrasi dogru calistigi-dogru url'e yonlendirip
* yonlendirmedigi test edildi.Sayfaya guncelleme gelirse testler de guncellenmeli .
* </p>
*/
package systemTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.NoSuchElementException;

class LinkTests {

	
	    private WebDriver driver;
	    private WebDriverWait wait;


	    @BeforeEach
	    public void setUp() {
	        // Set up WebDriver and navigate to Amazon
	        System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver.exe");
	        driver = new ChromeDriver();
	        driver.get("https://www.amazon.com/");
	        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    }

	    @AfterEach
	    public void tearDown() {
	        // Quit the WebDriver after each test
	        driver.quit();
	    }

	    @Test
	    public void testSignInLink() {
	        WebElement signInLink = driver.findElement(By.cssSelector("#nav-link-accountList-nav-line-1"));
	        signInLink.click();
	        String expectedUrl = "https://www.amazon.com/ap/signin?openid.pape.max_auth_age=0&openid.return_to=https%3A%2F%2Fwww.amazon.com%2F%3Fref_%3Dnav_ya_signin&openid.identity=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.assoc_handle=usflex&openid.mode=checkid_setup&openid.claimed_id=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0&";
	        String actualUrl = driver.getCurrentUrl();
	        assertEquals(expectedUrl, actualUrl);
	    }

	    @Test
	    public void testCartLink() {
	        WebElement cartLink = driver.findElement(By.id("nav-cart"));
	        cartLink.click();
	        String expectedUrl = "https://www.amazon.com/gp/cart/view.html?ref_=nav_cart";
	        String actualUrl = driver.getCurrentUrl();
	        assertEquals(expectedUrl, actualUrl);
	    }

	    @Test
	    public void testHelpLink() {
	        WebElement helpLink = driver.findElement(By.linkText("Help"));
	        helpLink.click();
	        String expectedUrl = "https://www.amazon.com/gp/help/customer/display.html?nodeId=508510&ref_=footer_gw_m_b_he";
	        String actualUrl = driver.getCurrentUrl();
	        assertEquals(expectedUrl, actualUrl);
	    }

	    @Test
	    public void testSearchLink() {
	        WebElement searchButton = driver.findElement(By.cssSelector(".nav-search-submit .nav-input"));
	        searchButton.click();
	        String expectedUrl = "https://www.amazon.com/";
	        String actualUrl = driver.getCurrentUrl();
	        System.out.print(actualUrl);
	        assertTrue(actualUrl.startsWith(expectedUrl));
	    }
	    

	    @Test
	    public void testOrdersLink() throws InterruptedException {
	    	Thread.sleep(1000);
	        WebElement ordersLink = driver.findElement(By.id("nav-orders"));
	        ordersLink.click();
	        String expectedUrl = "https://www.amazon.com/ap/signin?_encoding=UTF8&accountStatusPolicy=P1&openid.assoc_handle=usflex&openid.claimed_id=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.identity=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.mode=checkid_setup&openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0&openid.ns.pape=http%3A%2F%2Fspecs.openid.net%2Fextensions%2Fpape%2F1.0&openid.pape.max_auth_age=0&openid.return_to=https%3A%2F%2Fwww.amazon.com%2Fgp%2Fcss%2Forder-history%3Fie%3DUTF8%26ref_%3Dnav_orders_first&pageId=webcs-yourorder&showRmrMe=1";
	        String actualUrl = driver.getCurrentUrl();
	        assertEquals(expectedUrl, actualUrl);
	    }

	    @Test
	    public void testNavRecursiveLink() throws InterruptedException {
	    	Thread.sleep(1000);
	        WebElement primeLink = driver.findElement(By.xpath("//a[@id='nav-logo-sprites']"));
	        primeLink.click();
	        String expectedUrl = "https://www.amazon.com/ref=nav_logo";
	        String actualUrl = driver.getCurrentUrl();
	        assertEquals(expectedUrl, actualUrl);
	    }
	    @Test
	    public void testDealsLink() {
	    	By todayDealsLink = By.linkText("Today's Deals");
	    	WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(todayDealsLink));
	    	JavascriptExecutor js = (JavascriptExecutor) driver;
	    	js.executeScript("arguments[0].click();", element);
	    	assertTrue(driver.getCurrentUrl().startsWith("https://www.amazon.com/gp/goldbox"));

	    }
	}