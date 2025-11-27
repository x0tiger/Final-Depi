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

public class TestCase21_AddReviewOnProduct {
    WebDriver driver;

    @BeforeTest
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Browser drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void addReviewOnProduct() throws InterruptedException {
        driver.get("https://automationexercise.com");

        driver.findElement(By.xpath("//a[@href='/products']")).click();
        Thread.sleep(2000);

        Assert.assertTrue(driver.getCurrentUrl().contains("products"));

        driver.findElement(By.xpath("//a[@href='/product_details/1']")).click();
        Thread.sleep(2000);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)");
        Thread.sleep(2000);
        
        WebElement reviewSection = driver.findElement(By.xpath("//a[@href='#reviews']"));
        Assert.assertTrue(reviewSection.isDisplayed(), "Write Your Review is visible");

        driver.findElement(By.id("name")).sendKeys("Test User");
        driver.findElement(By.id("email")).sendKeys("testuser@gmail.com");
        driver.findElement(By.id("review")).sendKeys("This is a great product! Highly recommended.");

        driver.findElement(By.id("button-review")).click();
        Thread.sleep(2000);

        WebElement successMessage = driver.findElement(By.xpath("//span[contains(text(),'Thank you for your review')]"));
        Assert.assertTrue(successMessage.isDisplayed(), "Success message is visible");

        System.out.println("Test Case 21: Add review on product - PASSED");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
