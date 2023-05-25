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


import org.openqa.selenium.support.ui.ExpectedConditions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;
@ExtendWith(TestResultLogger.class)
public class LinkTests {

		
	    private static WebDriver driver;
	    private static WebDriverWait wait;
	    private static final Logger logger = Logger.getLogger(LinkTests.class.getName());
	
	
	    @BeforeAll
	    public static void setUp() {
	    	  ChromeOptions options = new ChromeOptions();
		        options.addArguments("--no-sandbox"); // Required for running in Docker
		        options.addArguments("--headless");
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
	    public void testSignInLink() {
	    	System.out.println("Sign in butonuna tiklandiginda yonlendirme dogru link mi testi basladi..");
	    	System.out.println();
	    	logger.log(Level.INFO, "Signin click redirected....");

	        driver.get("https://www.amazon.com/");
	        WebElement signInLink = driver.findElement(By.cssSelector("#nav-link-accountList-nav-line-1"));
	        signInLink.click();
	        String expectedUrl = "https://www.amazon.com/ap/signin?openid.pape.max_auth_age=0&openid.return_to=https%3A%2F%2Fwww.amazon.com%2F%3Fref_%3Dnav_ya_signin&openid.identity=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.assoc_handle=usflex&openid.mode=checkid_setup&openid.claimed_id=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0&";
	        wait.until(ExpectedConditions.urlToBe(expectedUrl));
	        String actualUrl = driver.getCurrentUrl();
	        assertEquals(expectedUrl, actualUrl);
	   
	    }

	    @Test
	    public void testCartLink() {
	       	System.out.println("Sepet butonuna tiklandiginda yonlendirme dogru link mi testi basladi..");
	    	System.out.println();
	    	logger.log(Level.INFO, "Cart click redirected....");

	        driver.get("https://www.amazon.com/");
	        WebElement cartLink = driver.findElement(By.id("nav-cart"));
	        cartLink.click();
	        String expectedUrl = "https://www.amazon.com/gp/cart/view.html?ref_=nav_cart";
	        wait.until(ExpectedConditions.urlToBe(expectedUrl));
	        String actualUrl = driver.getCurrentUrl();
	        assertEquals(expectedUrl, actualUrl);
	    }

	    @Test
	    public void testHelpLink() {
	       	System.out.println("Help  butonuna tiklandiginda yonlendirme dogru link mi testi basladi..");
	    	System.out.println();
	    	logger.log(Level.INFO, "Help click redirected....");
	        driver.get("https://www.amazon.com/");
	        WebElement helpLink = driver.findElement(By.linkText("Help"));
	        helpLink.click();
	        String expectedUrl = "https://www.amazon.com/gp/help/customer/display.html?nodeId=508510&ref_=footer_gw_m_b_he";
	        wait.until(ExpectedConditions.urlToBe(expectedUrl));
	        String actualUrl = driver.getCurrentUrl();
	        assertEquals(expectedUrl, actualUrl);
	    }

	    @Test
	    public void testSearchLink() {
	       	System.out.println("Arama butonu tiklandiginda yonlendirme dogru link mi testi basladi..");
	    	System.out.println();
	    	logger.log(Level.INFO, "Search click redirected....");
	        driver.get("https://www.amazon.com/");
	        WebElement searchButton = driver.findElement(By.cssSelector(".nav-search-submit .nav-input"));
	        searchButton.click();
	        String expectedUrl = "https://www.amazon.com/";
	        wait.until(ExpectedConditions.urlContains(expectedUrl));
	        String actualUrl = driver.getCurrentUrl();
	        assertTrue(actualUrl.startsWith(expectedUrl));
	    }

	    @Test
	    public void testOrdersLink() {
	       	System.out.println("Siparislerim  butonu tiklandiginda yonlendirme dogru link mi testi basladi..");
	    	System.out.println();
	    	logger.log(Level.INFO, "Order click redirected....");
	        driver.get("https://www.amazon.com/");
	        WebElement ordersLink = driver.findElement(By.id("nav-orders"));
	        ordersLink.click();
	        String expectedUrl = "https://www.amazon.com/ap/signin?_encoding=UTF8&accountStatusPolicy=P1&openid.assoc_handle=usflex&openid.claimed_id=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.identity=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.mode=checkid_setup&openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0&openid.ns.pape=http%3A%2F%2Fspecs.openid.net%2Fextensions%2Fpape%2F1.0&openid.pape.max_auth_age=0&openid.return_to=https%3A%2F%2Fwww.amazon.com%2Fgp%2Fcss%2Forder-history%3Fie%3DUTF8%26ref_%3Dnav_orders_first&pageId=webcs-yourorder&showRmrMe=1";
	        wait.until(ExpectedConditions.urlToBe(expectedUrl));
	        String actualUrl = driver.getCurrentUrl();
	        assertEquals(expectedUrl, actualUrl);
	    }

	    @Test
	    public void testNavRecursiveLink() {
	       	System.out.println("Ayni sayfada kalmayı garanti etmesi gereken link calisiyor mu testi basladi..");
	    	System.out.println();
	    	logger.log(Level.INFO, "Recursive link click redirected....");
	        driver.get("https://www.amazon.com/");
	        WebElement primeLink = driver.findElement(By.xpath("//a[@id='nav-logo-sprites']"));
	        primeLink.click();
	        String expectedUrl = "https://www.amazon.com/ref=nav_logo";
	        wait.until(ExpectedConditions.urlToBe(expectedUrl));
	        String actualUrl = driver.getCurrentUrl();
	        assertEquals(expectedUrl, actualUrl);
	    }

	    @Test
	    public void testDealsLink() {
	       	System.out.println("Deals linki testi basladi..");
	    	System.out.println();
	    	logger.log(Level.INFO, "Who got the deal....");
	        driver.get("https://www.amazon.com/");
	        By todayDealsLink = By.linkText("Today's Deals");
	        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(todayDealsLink));
	        JavascriptExecutor js = (JavascriptExecutor) driver;
	        js.executeScript("arguments[0].click();", element);
	        String expectedUrl = "https://www.amazon.com/gp/goldbox";
	        wait.until(ExpectedConditions.urlContains(expectedUrl));
	        assertTrue(driver.getCurrentUrl().startsWith(expectedUrl));
	    }
	}