package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestCase05_RegisterUserWithExistingEmail {
    WebDriver driver;

    @BeforeTest
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Browser drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void registerUserWithExistingEmail() throws InterruptedException {
        driver.get("https://automationexercise.com");

        Assert.assertTrue(driver.getCurrentUrl().contains("automationexercise.com"));

        driver.findElement(By.linkText("Signup / Login")).click();
        Thread.sleep(2000);

        WebElement newUserText = driver.findElement(By.xpath("//h2[text()='New User Signup!']"));
        Assert.assertTrue(newUserText.isDisplayed(), "New User Signup text is visible");

        driver.findElement(By.name("name")).sendKeys("Existing User");
        driver.findElement(By.xpath("//input[@data-qa='signup-email']")).sendKeys("elhussienysabry@example.com");

        driver.findElement(By.xpath("//button[@data-qa='signup-button']")).click();
        Thread.sleep(2000);

        WebElement errorMessage = driver.findElement(By.xpath("//p[text()='Email Address already exist!']"));
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message 'Email Address already exist!' is visible");

        System.out.println("Test Case 05: Register User with existing email - PASSED");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
