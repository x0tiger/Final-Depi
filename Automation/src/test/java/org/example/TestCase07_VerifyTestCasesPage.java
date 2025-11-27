package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestCase07_VerifyTestCasesPage {
    WebDriver driver;

    @BeforeTest
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Browser drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void verifyTestCasesPage() throws InterruptedException {
        driver.get("https://automationexercise.com");

        Assert.assertTrue(driver.getCurrentUrl().contains("automationexercise.com"));

        driver.findElement(By.xpath("//a[contains(text(),'Test Cases')]")).click();
        Thread.sleep(2000);

        Assert.assertTrue(driver.getCurrentUrl().contains("test_cases"));
        WebElement testCasesText = driver.findElement(By.xpath("//h2[@class='title text-center']"));
        Assert.assertTrue(testCasesText.isDisplayed(), "Test Cases page is visible");

        System.out.println("Test Case 07: Verify Test Cases Page - PASSED");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
