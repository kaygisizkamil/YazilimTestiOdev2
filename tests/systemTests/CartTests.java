/**
*
* @author Kamil Kaygisiz kamil.kaygisiz1@ogr.sakarya.edu.tr
* @since 19.05.2023
* <p>
* Here i tested cart with different approaches  like empty ,add different product etc.
* </p>
*/
package systemTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;



public class CartTests {
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static final Logger logger = Logger.getLogger(CartTests.class.getName());


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
    public void testAddToCart() {
    	System.out.print("Sepete ekleme ozelligi testi basladi..");
    	System.out.println();
    	logger.log(Level.INFO, "Add 1 to cart");
        driver.get("https://www.amazon.com/Placemat-Protect-Resistant-Wipeable-Accessories/dp/B0BGMJXTBX/ref=sr_1_1?crid=IBML6MYDLJ4A&keywords=Dinnerware%2B%26%2Baccessories&pd_rd_r=17d7a2a0-faba-423f-ac87-ebcb4d293285&pd_rd_w=l6K9T&pd_rd_wg=USyFS&pf_rd_p=c9097eb6-837b-4ba7-94d7-51428f6e8d2a&pf_rd_r=WK6ZJCDRGWY1BWGC84TA&qid=1684440349&sprefix=dinnerware%2B%26%2Baccessorie%2Caps%2C190&sr=8-1&th=1");
        WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-button")));
        addToCartButton.click();
        WebElement cartCountElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-cart-count")));
        int cartCount = Integer.parseInt(cartCountElement.getText());
        assertEquals(1, cartCount);
    }

    @Test
    public void testRemoveFromCart() {
    	System.out.println("Sepetten urun silme testi basladi..");
    	System.out.println();
    	logger.log(Level.INFO, "Remove from cart");
        driver.get("https://www.amazon.com/Placemat-Protect-Resistant-Wipeable-Accessories/dp/B0BGMJXTBX/ref=sr_1_1?crid=IBML6MYDLJ4A&keywords=Dinnerware%2B%26%2Baccessories&pd_rd_r=17d7a2a0-faba-423f-ac87-ebcb4d293285&pd_rd_w=l6K9T&pd_rd_wg=USyFS&pf_rd_p=c9097eb6-837b-4ba7-94d7-51428f6e8d2a&pf_rd_r=WK6ZJCDRGWY1BWGC84TA&qid=1684440349&sprefix=dinnerware%2B%26%2Baccessorie%2Caps%2C190&sr=8-1&th=1");
        WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-button")));
        addToCartButton.click();
        WebElement cartButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-cart")));
        cartButton.click();
        WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Delete']")));
        deleteButton.click();
        WebElement emptyCartMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='sc-active-cart']//h1")));
        String actualMessage = emptyCartMessage.getText();
        assertEquals("Shopping Cart", actualMessage);
    }

    @Test
    public void testAddMultipleProductsToCart() {
    	System.out.println("Sepete coklu urun ekleme testi basladi..");
    	System.out.println();
    	logger.log(Level.INFO, "Add multiple");

        driver.get("https://www.amazon.com/Placemat-Protect-Resistant-Wipeable-Accessories/dp/B0BGMJXTBX/ref=sr_1_1?crid=IBML6MYDLJ4A&keywords=Dinnerware%2B%26%2Baccessories&pd_rd_r=17d7a2a0-faba-423f-ac87-ebcb4d293285&pd_rd_w=l6K9T&pd_rd_wg=USyFS&pf_rd_p=c9097eb6-837b-4ba7-94d7-51428f6e8d2a&pf_rd_r=WK6ZJCDRGWY1BWGC84TA&qid=1684440349&sprefix=dinnerware%2B%26%2Baccessorie%2Caps%2C190&sr=8-1&th=1");
        WebElement addToCartButton1 = wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-button")));
        addToCartButton1.click();

        driver.get("https://www.amazon.com/dp/B0BGMLR5GM/ref=sbl_dpx_dining-linens-placemats_B0BGMJXTBX_0");
        WebElement addToCartButton2 = wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-button")));
        addToCartButton2.click();

        WebElement cartButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-cart")));
        cartButton.click();

        WebElement product1Title = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class, 'sc-product-title')]")));
        String actualProduct1Title = product1Title.getText();
        assertTrue(actualProduct1Title.contains("Home Genie Placemat Set of 6 Protect Surface Heat"));

        WebElement product2Title = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body/div[@id='a-page']/div[2]/div[3]/div[4]/div[1]/div[2]/div[1]/div[1]/form[1]/div[2]/div[4]/div[4]/div[1]/div[2]/ul[1]/li[1]/span[1]/a[1]/span[1]")));
        String actualProduct2Title = product2Title.getText();
        assertTrue(actualProduct2Title.contains("Home Genie Placemat Set of 4 Protect Surface Heat"));
    }

    @Test
    public void testUpdateProductQuantityInCart() {
    	System.out.print("Sepetteki urun miktarini degistirme testi");
    	System.out.println();
    	logger.log(Level.INFO, "Update");

        driver.get("https://www.amazon.com/Placemat-Protect-Resistant-Wipeable-Accessories/dp/B0BGMJXTBX/ref=sr_1_1?crid=IBML6MYDLJ4A&keywords=Dinnerware%2B%26%2Baccessories&pd_rd_r=17d7a2a0-faba-423f-ac87-ebcb4d293285&pd_rd_w=l6K9T&pd_rd_wg=USyFS&pf_rd_p=c9097eb6-837b-4ba7-94d7-51428f6e8d2a&pf_rd_r=WK6ZJCDRGWY1BWGC84TA&qid=1684440349&sprefix=dinnerware%2B%26%2Baccessorie%2Caps%2C190&sr=8-1&th=1");

        WebElement quantityInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("quantity")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", quantityInput);
        quantityInput.sendKeys("2");

        assertTrue(Integer.parseInt(quantityInput.getAttribute("value")) > 1);
    }
    
    @Test
    public void testEmptyCart() {
    	System.out.print("Sepetten urun silme testi basladi");
    	System.out.println();
    	logger.log(Level.INFO, "Empty cart");

        driver.get("https://www.amazon.com/Placemat-Protect-Resistant-Wipeable-Accessories/dp/B0BGMJXTBX/ref=sr_1_1?crid=IBML6MYDLJ4A&keywords=Dinnerware%2B%26%2Baccessories&pd_rd_r=17d7a2a0-faba-423f-ac87-ebcb4d293285&pd_rd_w=l6K9T&pd_rd_wg=USyFS&pf_rd_p=c9097eb6-837b-4ba7-94d7-51428f6e8d2a&pf_rd_r=WK6ZJCDRGWY1BWGC84TA&qid=1684440349&sprefix=dinnerware%2B%26%2Baccessorie%2Caps%2C190&sr=8-1&th=1");

        WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-button")));
        addToCartButton.click();

        WebElement cartButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-cart")));
        cartButton.click();

        WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Delete']")));
        deleteButton.click();

        WebElement emptyCartMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='sc-active-cart']//h1")));
        String actualMessage = emptyCartMessage.getText();
        assertEquals("Shopping Cart", actualMessage);

    }
}/*
ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", "./GerekliDosyalar/selenium-server-4.9.1.jar", "-role", "standalone");
try {
	processBuilder.start();
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
Thread.sleep(5);
java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
java.util.logging.Logger.getLogger("webdriver.chrome").setLevel(Level.OFF);
System.setProperty("webdriver.chrome.silentOutput", "true");

// Set the URL of the remote WebDriver server on localhost

URL remoteUrl;
try {
   remoteUrl = new URL("http://localhost:4444/wd/hub");

} catch (MalformedURLException e) {
   throw new RuntimeException("Invalid remote URL", e);
}

ChromeOptions options=new ChromeOptions();
options.addArguments("silent");
options.addArguments("--log-level=3");

driver =new RemoteWebDriver(remoteUrl, options);
wait = new WebDriverWait(driver, Duration.ofSeconds(10));
*/