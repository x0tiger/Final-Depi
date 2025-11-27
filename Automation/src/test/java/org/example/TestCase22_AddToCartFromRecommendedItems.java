package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class TestCase22_AddToCartFromRecommendedItems {
    WebDriver driver;

    @BeforeTest
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Browser drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void addToCartFromRecommendedItems() throws InterruptedException {
        driver.get("https://automationexercise.com");

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        Thread.sleep(2000);

        WebElement recommendedSection = driver.findElement(By.xpath("//h2[text()='recommended items']"));
        Assert.assertTrue(recommendedSection.isDisplayed(), "RECOMMENDED ITEMS are visible");

        driver.findElement(By.xpath("(//div[@class='item active']//a[@data-product-id='1'])[1]")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//u[text()='View Cart']")).click();
        Thread.sleep(2000);

        WebElement cartProduct = driver.findElement(By.xpath("//a[@href='/product_details/1']"));
        Assert.assertTrue(cartProduct.isDisplayed(), "Recommended product is displayed in cart");

        System.out.println("Test Case 22: Add to cart from Recommended items - PASSED");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
