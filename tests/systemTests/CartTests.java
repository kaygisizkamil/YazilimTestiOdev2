/**
*
* @author Kamil Kaygisiz kamil.kaygisiz1@ogr.sakarya.edu.tr
* @since 19.05.2023
* <p>
* Here i tested cart with different approaches  like empty ,add different product etc.
* </p>
*/
package systemTests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import static org.junit.Assert.*;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class CartTests {
    
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testAddToCart() {
        driver.get("https://www.amazon.com/Placemat-Protect-Resistant-Wipeable-Accessories/dp/B0BGMJXTBX/ref=sr_1_1?crid=IBML6MYDLJ4A&keywords=Dinnerware%2B%26%2Baccessories&pd_rd_r=17d7a2a0-faba-423f-ac87-ebcb4d293285&pd_rd_w=l6K9T&pd_rd_wg=USyFS&pf_rd_p=c9097eb6-837b-4ba7-94d7-51428f6e8d2a&pf_rd_r=WK6ZJCDRGWY1BWGC84TA&qid=1684440349&sprefix=dinnerware%2B%26%2Baccessorie%2Caps%2C190&sr=8-1&th=1");
        WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-button")));
        addToCartButton.click();
        WebElement cartCountElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-cart-count")));
        int cartCount = Integer.parseInt(cartCountElement.getText());
        assertEquals(1, cartCount);
    }

    @Test
    public void testRemoveFromCart() {
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
        driver.get("https://www.amazon.com/Placemat-Protect-Resistant-Wipeable-Accessories/dp/B0BGMJXTBX/ref=sr_1_1?crid=IBML6MYDLJ4A&keywords=Dinnerware%2B%26%2Baccessories&pd_rd_r=17d7a2a0-faba-423f-ac87-ebcb4d293285&pd_rd_w=l6K9T&pd_rd_wg=USyFS&pf_rd_p=c9097eb6-837b-4ba7-94d7-51428f6e8d2a&pf_rd_r=WK6ZJCDRGWY1BWGC84TA&qid=1684440349&sprefix=dinnerware%2B%26%2Baccessorie%2Caps%2C190&sr=8-1&th=1");

        WebElement quantityInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("quantity")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", quantityInput);
        quantityInput.sendKeys("2");

        assertEquals("2", quantityInput.getAttribute("value"));
    }
    
    @Test
    public void testEmptyCart() {
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

        driver.quit();
    }
}
