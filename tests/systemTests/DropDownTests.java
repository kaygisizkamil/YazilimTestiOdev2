package systemTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
	public class DropDownTests {

	    private WebDriver driver;
	    private WebDriverWait wait;

	    @BeforeEach
	    public void setUp() {
	        ChromeOptions options = new ChromeOptions();
	        options.addArguments("--start-maximized");
	        driver = new ChromeDriver(options);
	        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    }

	    @AfterEach
	    public void tearDown() {
	        driver.quit();
	    }


	    @Test
	    public void testDropdownDefaultOption() {
	        driver.get("https://www.amazon.com/");

	        WebElement dropdownTrigger = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='nav-search-dropdown-card']")));
	        
	        Actions actions = new Actions(driver);
	        actions.moveToElement(dropdownTrigger).click().build().perform();
	        
	        WebElement dropdownOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//option[contains(text(),'All Departments')]")));
	        String optionText = dropdownOption.getText();
	        
	        dropdownOption.click();

	        assertEquals(optionText, "All Departments");
	    }	

	    @Test
	    public void testDropdownOptionsCount() {
	        driver.get("https://www.amazon.com/");

	        WebElement dropdownTrigger = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='nav-search-dropdown-card']")));
	        dropdownTrigger.click();

	      //  WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("select")));
	       int optionsCount =wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy((By.tagName("option")))).size();

	        assertTrue(optionsCount >= 0);
	    }
	    @Test
	    public void isDropdownReallyWorks() {
		    driver.get("https://www.amazon.com/");
	
	        WebElement dropdownTrigger = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='nav-search-dropdown-card']")));
	        dropdownTrigger.click();
	
	        WebElement dropdownOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//option[contains(text(),'Automotive')]")));
	        dropdownOption.click();
	        String urlBefore=driver.getCurrentUrl();
	        WebElement searchBox = driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']"));
	        searchBox.sendKeys("tire");
	        searchBox.submit();
	        String currentURL = driver.getCurrentUrl();
	        assertNotEquals(urlBefore, currentURL);

	    }
	
	    
	    
	
}