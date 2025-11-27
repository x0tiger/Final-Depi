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

public class TestCase26_VerifyScrollUpWithoutArrowButtonAndScrollDownFunctionality {
    WebDriver driver;

    @BeforeTest
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Browser drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void verifyScrollUpWithoutArrowButtonAndScrollDownFunctionality() throws InterruptedException {
        driver.get("https://automationexercise.com");

        Assert.assertTrue(driver.getCurrentUrl().contains("automationexercise.com"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        Thread.sleep(2000);

        WebElement subscriptionText = driver.findElement(By.xpath("//h2[text()='Subscription']"));
        Assert.assertTrue(subscriptionText.isDisplayed(), "SUBSCRIPTION text is visible");

        js.executeScript("window.scrollTo(0, 0)");
        Thread.sleep(2000);

        WebElement headerText = driver.findElement(By.xpath("//h2[text()='Full-Fledged practice website for Automation Engineers']"));
        Assert.assertTrue(headerText.isDisplayed(), "Header text is visible after scroll up");

        System.out.println("Test Case 26: Verify Scroll Up without 'Arrow' button and Scroll Down functionality - PASSED");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
