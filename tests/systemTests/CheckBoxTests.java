/**
*
* @author Kamil Kaygisiz kamil.kaygisiz1@ogr.sakarya.edu.tr
* @since 9.05.2023
* <p>
* Sayfadaki left navbara tiklayinca acilan checkboxlarin islevselligi , duzgun calistigini teyit 
* </p>
*/

package systemTests;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;
//@ExtendWith(TestResultLogger.class)
@TestInstance(Lifecycle.PER_CLASS)
public class CheckBoxTests {

	private int  pageHeight;
  
  
    private  WebDriver driver;
    private WebDriverWait wait;
    private static final Logger logger = Logger.getLogger(CheckBoxTests.class.getName());


    @BeforeAll
    public  void setUp() {
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
    public  void tearDown() {
        driver.quit();
    }

	@Test
    public void testCheckbox() {
	 driver.get("https://www.amazon.com");
	 JavascriptExecutor executor = (JavascriptExecutor) driver;
	 pageHeight = driver.manage().window().getSize().getHeight();
	  // Click on Gift Cards link in the navbar
	 WebElement navBar = driver.findElement(By.id("nav-main"));
	 WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Gift Cards')]")));
	 executor.executeScript("arguments[0].click();", element);
         wait.until(ExpectedConditions.titleContains("Gift Cards"));
	 System.out.println("Checkbox click testi basladi");
    	System.out.println();
    	logger.log(Level.INFO, "Click to checkbox");

    	String urlToBeReturned="https://www.amazon.com/gp/browse.html?node=2238192011&ref_=nav_em_hmc_gc_allgc_0_2_27_2";
        driver.get("https://www.amazon.com/gp/browse.html?node=2238192011&ref_=nav_em_hmc_gc_allgc_0_2_27_2");

        // Click on Delivery Type section
        WebElement deliveryTypeSection = driver.findElement(By.xpath("//span[contains(text(), 'Delivery Type')]"));
        deliveryTypeSection.click();

        // Wait for the checkboxes to be visible
        By eGiftCardCheckboxLocator = By.xpath("//body/div[@id='a-page']/div[2]/div[2]/div[2]/div[1]/div[1]/div[1]/ul[1]/li[1]/span[1]/a[1]/span[1]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(eGiftCardCheckboxLocator));
        // Click on eGift Card checkbox
        WebElement eGiftCardCheckbox = driver.findElement(eGiftCardCheckboxLocator);
        eGiftCardCheckbox.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body/div[@id='a-page']/div[@id='search']/span[1]")));
        driver.get("https://www.amazon.com/s?i=gift-cards&bbn=2238192011&rh=n%3A2238192011%2Cp_n_format_browse-bin%3A2740964011&dc&ds=v1%3AzD7MwieAJFvsVUZxGKGcffaMRUoIJZja17r2%2FDIdidA&qid=1683763332&ref=sr_ex_n_1");        
       WebElement checkboxOnNewPage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body[1]/div[1]/div[2]/div[1]/div[2]/div[1]/div[3]/span[1]/div[1]/div[1]/div[1]/div[5]/ul[1]/span[1]/li[1]/span[1]/a[1]/div[1]/label[1]/i[1]")));
       // assertTrue(checkboxOnNewPage.isSelected());
        // Go back to All Gift Cards page
        driver.navigate().to(urlToBeReturned);
        // Click on Mail checkbox
        By mailCardCheckboxLocator = By.xpath("//body/div[@id='a-page']/div[2]/div[2]/div[2]/div[1]/div[1]/div[1]/ul[1]/li[2]/span[1]/a[1]/div[1]/label[1]/i[1]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(mailCardCheckboxLocator));
        WebElement mailCardCheckbox = driver.findElement(mailCardCheckboxLocator);
        mailCardCheckbox.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body/div[@id='a-page']/div[@id='search']/span[1]")));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, " + pageHeight/4 + ")");
        driver.get("https://www.amazon.com/s?bbn=2238192011&rh=n%3A2238192011%2Cp_n_format_browse-bin%3A2740967011&dc&qid=1683765089&rnid=2740963011&ref=lp_2864120011_nr_p_n_format_browse-bin_1");        
        WebElement checkboxOnNewPage2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body[1]/div[1]/div[2]/div[1]/div[2]/div[1]/div[3]/span[1]/div[1]/div[1]/div[1]/div[5]/ul[1]/span[2]/li[1]/span[1]/a[1]/div[1]/label[1]/i[1]")));
       // assertTrue(checkboxOnNewPage.isSelected());
        // Go back to All Gift Cards page
        driver.navigate().to("https://www.amazon.com");
        System.out.println("Test done successfully");

    }
	/*Scenario:After clickin the Gifts get the num of total gifts
	 * Click first amazon get the num of total amazon gifts compare them
	 * should not be equal
	 * 
	 */
    @Test
    public void testNestedCheckbox(){
    	driver.get("https://www.amazon.com");
	JavascriptExecutor executor = (JavascriptExecutor) driver;
	pageHeight = driver.manage().window().getSize().getHeight();
	           // Click on Gift Cards link in the navbar
         WebElement navBar = driver.findElement(By.id("nav-main"));
   	 WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Gift Cards')]")));
   	 executor.executeScript("arguments[0].click();", element);
   	 wait.until(ExpectedConditions.titleContains("Gift Cards"));
    	System.out.println("Nested Checkbox testi basladi");
    	//logger.log(Level.INFO, "Click to checkbox twice (nested)");

    	System.out.println();
        driver.get("https://www.amazon.com/s?i=gift-cards&bbn=2238192011&rh=n%3A2238192011%2Cp_n_format_browse-bin%3A2740964011&dc&ds=v1%3AzD7MwieAJFvsVUZxGKGcffaMRUoIJZja17r2%2FDIdidA&qid=1683763332&ref=sr_ex_n_1");        
        By resultPath=By.xpath("//body/div[@id='a-page']/div[@id='search']/span[1]/div[1]/h1[1]/div[1]/div[1]/div[1]/div[1]/span[1]");
        WebElement howMany = wait.until(ExpectedConditions.visibilityOfElementLocated(resultPath));
        String resultText = howMany.getText();
        int resultCount = Integer.parseInt(resultText.split(" ")[3].replace(",", ""));
        
        WebElement amazonCheckBox=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body[1]/div[1]/div[2]/div[1]/div[2]/div[1]/div[3]/span[1]/div[1]/div[1]/div[1]/div[3]/ul[1]/span[1]/li[1]/span[1]/a[1]/div[1]/label[1]/i[1]")));
        amazonCheckBox.click();
        WebElement howMany2=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body/div[@id='a-page']/div[@id='search']/span[1]/div[1]/h1[1]/div[1]/div[1]/div[1]")));
        String resultText2 = howMany2.getText();
        int resultCount2 = Integer.parseInt(resultText2.split(" ")[2].replace(",", ""));
        
        assertNotEquals(resultCount,resultCount2);
            	
    }
    
	
	
	
}
 

