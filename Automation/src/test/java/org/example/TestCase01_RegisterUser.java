package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestCase01_RegisterUser {
    WebDriver driver;

    @BeforeTest
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Browser drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void registerUser() throws InterruptedException {
        driver.get("https://automationexercise.com");
        Assert.assertTrue(driver.getCurrentUrl().contains("automationexercise.com"));

        driver.findElement(By.linkText("Signup / Login")).click();
        Thread.sleep(2000);

        WebElement newUserText = driver.findElement(By.xpath("//h2[text()='New User Signup!']"));
        Assert.assertTrue(newUserText.isDisplayed(), "New User Signup text is visible");

        String name = BaseTest.getRandomName();
        String email = BaseTest.getRandomEmail();

        driver.findElement(By.name("name")).sendKeys(name);
        driver.findElement(By.xpath("//input[@data-qa='signup-email']")).sendKeys(email);

        driver.findElement(By.xpath("//button[@data-qa='signup-button']")).click();
        Thread.sleep(2000);

        WebElement accountInfoText = driver.findElement(By.xpath("//b[text()='Enter Account Information']"));
        Assert.assertTrue(accountInfoText.isDisplayed(), "Enter Account Information is visible");

        driver.findElement(By.id("id_gender1")).click();
        driver.findElement(By.id("password")).sendKeys("123456");
        driver.findElement(By.id("days")).sendKeys("10");
        driver.findElement(By.id("months")).sendKeys("May");
        driver.findElement(By.id("years")).sendKeys("1990");

        driver.findElement(By.id("newsletter")).click();
        driver.findElement(By.id("optin")).click();

        driver.findElement(By.id("first_name")).sendKeys("First");
        driver.findElement(By.id("last_name")).sendKeys("Last");
        driver.findElement(By.id("company")).sendKeys("Company");
        driver.findElement(By.id("address1")).sendKeys("Address1");
        driver.findElement(By.id("address2")).sendKeys("Address2");
        driver.findElement(By.id("country")).sendKeys("Canada");
        driver.findElement(By.id("state")).sendKeys("State");
        driver.findElement(By.id("city")).sendKeys("City");
        driver.findElement(By.id("zipcode")).sendKeys("12345");
        driver.findElement(By.id("mobile_number")).sendKeys("1234567890");

        driver.findElement(By.xpath("//button[text()='Create Account']")).click();
        Thread.sleep(2000);

        WebElement accountCreated = driver.findElement(By.xpath("//b[text()='Account Created!']"));
        Assert.assertTrue(accountCreated.isDisplayed(), "Account Created message is visible");

        driver.findElement(By.xpath("//a[@data-qa='continue-button']")).click();
        Thread.sleep(2000);

        WebElement loggedInText = driver.findElement(By.xpath("//i[@class='fa fa-user']/parent::a"));
        Assert.assertTrue(loggedInText.getText().contains(name), "Logged in as " + name);

        driver.findElement(By.xpath("//a[contains(text(),'Delete Account')]")).click();
        Thread.sleep(2000);

        WebElement accountDeleted = driver.findElement(By.xpath("//b[text()='Account Deleted!']"));
        Assert.assertTrue(accountDeleted.isDisplayed(), "Account Deleted message is visible");

        System.out.println("Test Case 01: Register User - PASSED");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
