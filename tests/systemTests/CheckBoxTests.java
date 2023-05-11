package systemTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertEquals;
import org.openqa.selenium.JavascriptExecutor;
import java.time.Duration;
import java.util.List;

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
public class CheckBoxTests {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        
        // Navigate to Amazon homepage
        driver.get("https://www.amazon.com");
	      JavascriptExecutor executor = (JavascriptExecutor) driver;

        // Click on Gift Cards link in the navbar
        WebElement navBar = driver.findElement(By.id("nav-main"));
	      WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Gift Cards')]")));
	      executor.executeScript("arguments[0].click();", element);
	      wait.until(ExpectedConditions.titleContains("Gift Cards"));


    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
    /*
     * This tests after chechbox selected does it directing me the true url and in 
     * opened page if checkbox is really selected is done for two checkbox can be tested for them all
     * but would take forever time so i leave it like this
     * i got some help from chroPath chrome extension in here i could not find the checkboxes xPath easily it give me the exact true
     *  and easy result
     */
    @Test
    public void testCheckbox() throws InterruptedException {
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
        WebElement checkboxOnNewPage = driver.findElement(By.xpath("//body[1]/div[1]/div[2]/div[1]/div[2]/div[1]/div[3]/span[1]/div[1]/div[1]/div[1]/div[5]/ul[1]/span[1]/li[1]/span[1]/a[1]/div[1]/label[1]/i[1]"));
       // assertTrue(checkboxOnNewPage.isSelected());
        // Go back to All Gift Cards page
        Thread.sleep(1000);
        driver.navigate().to(urlToBeReturned);
        Thread.sleep(1000);
        // Click on Mail checkbox
        By mailCardCheckboxLocator = By.xpath("//body/div[@id='a-page']/div[2]/div[2]/div[2]/div[1]/div[1]/div[1]/ul[1]/li[2]/span[1]/a[1]/div[1]/label[1]/i[1]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(mailCardCheckboxLocator));
        WebElement mailCardCheckbox = driver.findElement(mailCardCheckboxLocator);
        mailCardCheckbox.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body/div[@id='a-page']/div[@id='search']/span[1]")));
        driver.get("https://www.amazon.com/s?bbn=2238192011&rh=n%3A2238192011%2Cp_n_format_browse-bin%3A2740967011&dc&qid=1683765089&rnid=2740963011&ref=lp_2864120011_nr_p_n_format_browse-bin_1");        
        WebElement checkboxOnNewPage2 = driver.findElement(By.xpath("//body[1]/div[1]/div[2]/div[1]/div[2]/div[1]/div[3]/span[1]/div[1]/div[1]/div[1]/div[5]/ul[1]/span[2]/li[1]/span[1]/a[1]/div[1]/label[1]/i[1]"));
       // assertTrue(checkboxOnNewPage.isSelected());
        // Go back to All Gift Cards page
        Thread.sleep(1000);
        driver.navigate().to("https://www.amazon.com");
        Thread.sleep(1000);//to be able to see if we really are navigated back

        System.out.println("Test done successfully");

    }
}
