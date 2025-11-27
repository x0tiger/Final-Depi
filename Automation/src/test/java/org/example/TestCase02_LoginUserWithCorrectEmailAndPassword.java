package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestCase02_LoginUserWithCorrectEmailAndPassword {
    WebDriver driver;

    @BeforeTest
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Browser drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void loginUserWithCorrectCredentials() throws InterruptedException {
        driver.get("https://automationexercise.com");

        Assert.assertTrue(driver.getCurrentUrl().contains("automationexercise.com"));

        driver.findElement(By.linkText("Signup / Login")).click();
        Thread.sleep(2000);

        WebElement loginText = driver.findElement(By.xpath("//h2[text()='Login to your account']"));
        Assert.assertTrue(loginText.isDisplayed(), "Login to your account is visible");

        // Note: ط§ط³طھط®ط¯ظ… email ظˆ password ظ…ظ† ط­ط³ط§ط¨ ظ…ظˆط¬ظˆط¯ ظ…ط³ط¨ظ‚ط§ظ‹
        driver.findElement(By.xpath("//input[@data-qa='login-email']")).sendKeys("elhussienysabry@example.com");
        driver.findElement(By.xpath("//input[@data-qa='login-password']")).sendKeys("123456");

        driver.findElement(By.xpath("//button[@data-qa='login-button']")).click();
        Thread.sleep(2000);

        WebElement loggedInText = driver.findElement(By.xpath("//i[@class='fa fa-user']/parent::a"));
        Assert.assertTrue(loggedInText.isDisplayed(), "Logged in as username is visible");

        driver.findElement(By.xpath("//a[contains(text(),'Delete Account')]")).click();
        Thread.sleep(2000);

        WebElement accountDeleted = driver.findElement(By.xpath("//b[text()='Account Deleted!']"));
        Assert.assertTrue(accountDeleted.isDisplayed(), "Account Deleted message is visible");

        System.out.println("Test Case 02: Login User with correct email and password - PASSED");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
