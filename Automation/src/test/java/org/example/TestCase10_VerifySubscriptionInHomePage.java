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

public class TestCase10_VerifySubscriptionInHomePage {
    WebDriver driver;

    @BeforeTest
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Browser drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void verifySubscriptionInHomePage() throws InterruptedException {
        driver.get("https://automationexercise.com");

        Assert.assertTrue(driver.getCurrentUrl().contains("automationexercise.com"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        Thread.sleep(2000);

        WebElement subscriptionText = driver.findElement(By.xpath("//h2[text()='Subscription']"));
        Assert.assertTrue(subscriptionText.isDisplayed(), "SUBSCRIPTION text is visible");

        driver.findElement(By.id("susbscribe_email")).sendKeys("testuser@gmail.com");
        driver.findElement(By.id("subscribe")).click();
        Thread.sleep(2000);

        WebElement successMessage = driver.findElement(By.xpath("//div[@class='alert-success alert']"));
        Assert.assertTrue(successMessage.getText().contains("successfully subscribed"), "Success message is visible");

        System.out.println("Test Case 10: Verify Subscription in home page - PASSED");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
