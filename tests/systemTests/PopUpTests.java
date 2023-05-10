/**
*
* @author Kamil Kaygisiz kamil.kaygisiz1@ogr.sakarya.edu.tr
* @since 9.05.2023
* <p>
* Sayfadaki bazı popUpların duzgun calistigini teyit eder her method için ayrica
* aciklama ustlerine eklenmistir.
* </p>
*/


package systemTests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.interactions.Actions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.jupiter.api.*;



/*<div id="parent">
  <h1 id="nav-link-accountList-nav-line-1">Title</h1>
  <span id="nav-link-accountList-nav-line-1">Content</span>
</div>

WebElement signInButton = driver.findElement(By.cssSelector("#parent #nav-link-accountList-nav-line-1:nth-of-type(2)"));
*/
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PopUpTests {
    private WebDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
        // quit the browser
        driver.quit();
    }
    
    /*test the signup pop-up enter wrong password  after,it is supposed to stay in same page
     * with the right account info it's still testable but the thing is there is a captcha
     * so there is no way to pass that without ai, i left it as it is did not try with
     * that approach
     */
    @Order(3)
    @Test
    public void signInPopUpTest() throws InterruptedException {
        // Create a WebDriverWait object with a timeout of 10 seconds
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Navigate to the Amazon homepage
        driver.get("https://www.amazon.com/");

        // Move the mouse to the "Hello, Sign in" button and click on it
        WebElement signInButton = driver.findElement(By.cssSelector("#nav-link-accountList-nav-line-1"));
        Actions actions = new Actions(driver);
        actions.moveToElement(signInButton).perform();

        // Wait for the sign-in popup button to be clickable and click on it
        WebElement signInPopupButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#nav-al-signin .nav-action-inner")));
        signInPopupButton.click();

        // Fill in email and click on the "Continue" button
        WebElement emailInput = driver.findElement(By.id("ap_email"));
        emailInput.sendKeys("example@email.com");
        WebElement signInPopUp = driver.findElement(By.id("continue"));
        signInPopUp.click();

        // Wait for the password field to be visible and fill it in, then click on the "Sign In" button
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ap_password")));
        passwordInput.sendKeys("password");
        WebElement signInSubmitButton = driver.findElement(By.id("signInSubmit"));
        signInSubmitButton.click();

        // Wait for the URL to be "https://www.amazon.com/ap/signin"
        String expectedUrl = "https://www.amazon.com/ap/signin";
        wait.until(ExpectedConditions.urlToBe(expectedUrl));

        // Get the actual URL and compare it with the expected URL using TestNG assertion
        String actualUrl = driver.getCurrentUrl();
        assertEquals(actualUrl, expectedUrl);
        //since the account info's are not right it needs to stay in the same page
    }
    /*
     * in the search area enter a keyword wait till suggestion ul-li comes after 
     * it is visible clik the first one and then control if it is displayed or not
     */
    @Order(2)
    @Test
    public void searchSuggestionPopUpTest() {
        // Navigate to the Amazon homepage
        driver.get("https://www.amazon.com/");

        // Enter a search query in the search bar
        WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
        searchBox.sendKeys("laptop");

        // Verify that the search suggestion pop-up appears with relevant search suggestions
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement searchSuggestionPopUp = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-flyout-searchAjax")));
        assertTrue(searchSuggestionPopUp.isDisplayed());

        List<WebElement> suggestionLinks = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='s-suggestion-container']")));
        System.out.println(suggestionLinks.size());
	     // Check that there is at least one suggestion link present
	     assertTrue(suggestionLinks.size() > 0);
	     wait.until(ExpectedConditions.elementToBeClickable(suggestionLinks.get(0))).click();
	
		  // Wait for the search suggestion pop-up to disappear
		  wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("search-suggestions")));
	
		  // Wait for the search results to be visible
		  WebElement searchResults = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='sg-col-inner']")));
	
		  // Assert that the search results are displayed
		  assertTrue(searchResults.isDisplayed());
    }
    /*
     * it is for testing feedback about products after hovering mouse to popup 
     * we click to review button(it is a link actually) and in the opening page 
     * we control a one comment header if clicking really direct us to that page
     * it can cause an error if user deletes the comment or product gets deleted but 
     * it looks like it has been 3 years comment is still in there
     */
    @Order(1)
    @Test
    public void feedbackPopUpTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        // Navigate to a product page on Amazon
        driver.get("https://www.amazon.com/dp/B08L6PCZTR");
        // Click on the "Write a customer review" button       
        WebElement reviewsPopUp = driver.findElement(By.cssSelector("#acrCustomerReviewLink"));
        reviewsPopUp.click();
        Thread.sleep(1000);
        WebElement feedbackResult = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Bought at 570')]")));
        assertTrue(feedbackResult.isDisplayed());
        assertEquals(feedbackResult.getText(),"Bought at 570");
    }
    /*this popups may not appear in every page opening, so i used that niside try catch
     * also showing implicit-explicit wait i used a manual sleep in here 
     */
    @Order(4)
    @Test
    public void closeAdressPopUp() {
        driver.get("https://www.amazon.com/");
    	 try {
             Thread.sleep(5000); // Wait for 5 seconds for the pop-up to appear
             WebElement popUpCloseBtn = driver.findElement(By.cssSelector(".a-button-inner"));
             popUpCloseBtn.click();
             System.out.println("Pop-up closed successfully.");
         } catch (InterruptedException e) {
             e.printStackTrace();
         }

    }

    
  
}