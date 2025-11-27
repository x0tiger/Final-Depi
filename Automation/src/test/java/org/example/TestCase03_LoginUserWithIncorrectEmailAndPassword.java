package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestCase03_LoginUserWithIncorrectEmailAndPassword {
    WebDriver driver;

    @BeforeTest
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Browser drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void loginUserWithIncorrectCredentials() throws InterruptedException {
        driver.get("https://automationexercise.com");

        Assert.assertTrue(driver.getCurrentUrl().contains("automationexercise.com"));

        driver.findElement(By.linkText("Signup / Login")).click();
        Thread.sleep(2000);

        WebElement loginText = driver.findElement(By.xpath("//h2[text()='Login to your account']"));
        Assert.assertTrue(loginText.isDisplayed(), "Login to your account is visible");

        driver.findElement(By.xpath("//input[@data-qa='login-email']")).sendKeys("wrongemail@gmail.com");
        driver.findElement(By.xpath("//input[@data-qa='login-password']")).sendKeys("wrongpassword");

        driver.findElement(By.xpath("//button[@data-qa='login-button']")).click();
        Thread.sleep(2000);

        WebElement errorMessage = driver.findElement(By.xpath("//p[text()='Your email or password is incorrect!']"));
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message is visible");

        System.out.println("Test Case 03: Login User with incorrect email and password - PASSED");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
