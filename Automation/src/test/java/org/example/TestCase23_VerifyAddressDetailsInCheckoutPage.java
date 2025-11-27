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

import java.util.Random;

public class TestCase23_VerifyAddressDetailsInCheckoutPage {
    WebDriver driver;

    @BeforeTest
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Browser drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void verifyAddressDetailsInCheckoutPage() throws InterruptedException {
        driver.get("https://automationexercise.com");

        Assert.assertTrue(driver.getCurrentUrl().contains("automationexercise.com"));

        driver.findElement(By.linkText("Signup / Login")).click();
        Thread.sleep(2000);

        String name = "User" + new Random().nextInt(10000);
        String email = "test" + new Random().nextInt(10000) + "@gmail.com";

        driver.findElement(By.name("name")).sendKeys(name);
        driver.findElement(By.xpath("//input[@data-qa='signup-email']")).sendKeys(email);
        driver.findElement(By.xpath("//button[@data-qa='signup-button']")).click();
        Thread.sleep(2000);

        driver.findElement(By.id("id_gender1")).click();
        driver.findElement(By.id("password")).sendKeys("123456");
        driver.findElement(By.id("days")).sendKeys("10");
        driver.findElement(By.id("months")).sendKeys("May");
        driver.findElement(By.id("years")).sendKeys("1990");
        driver.findElement(By.id("first_name")).sendKeys("FirstName");
        driver.findElement(By.id("last_name")).sendKeys("LastName");
        driver.findElement(By.id("company")).sendKeys("TestCompany");
        driver.findElement(By.id("address1")).sendKeys("123 Test Street");
        driver.findElement(By.id("address2")).sendKeys("Apt 456");
        driver.findElement(By.id("country")).sendKeys("Canada");
        driver.findElement(By.id("state")).sendKeys("Ontario");
        driver.findElement(By.id("city")).sendKeys("Toronto");
        driver.findElement(By.id("zipcode")).sendKeys("12345");
        driver.findElement(By.id("mobile_number")).sendKeys("1234567890");
        driver.findElement(By.xpath("//button[text()='Create Account']")).click();
        Thread.sleep(2000);

        WebElement accountCreated = driver.findElement(By.xpath("//b[text()='Account Created!']"));
        Assert.assertTrue(accountCreated.isDisplayed());
        driver.findElement(By.xpath("//a[@data-qa='continue-button']")).click();
        Thread.sleep(2000);

        WebElement loggedInText = driver.findElement(By.xpath("//i[@class='fa fa-user']/parent::a"));
        Assert.assertTrue(loggedInText.isDisplayed());

        driver.findElement(By.xpath("(//a[@data-product-id='1'])[1]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[text()='Continue Shopping']")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//a[@href='/view_cart']")).click();
        Thread.sleep(2000);

        Assert.assertTrue(driver.getCurrentUrl().contains("view_cart"));

        driver.findElement(By.xpath("//a[text()='Proceed To Checkout']")).click();
        Thread.sleep(2000);

        WebElement deliveryAddress = driver.findElement(By.id("address_delivery"));
        Assert.assertTrue(deliveryAddress.getText().contains("FirstName LastName"));
        Assert.assertTrue(deliveryAddress.getText().contains("TestCompany"));
        Assert.assertTrue(deliveryAddress.getText().contains("123 Test Street"));
        Assert.assertTrue(deliveryAddress.getText().contains("Apt 456"));
        Assert.assertTrue(deliveryAddress.getText().contains("Toronto Ontario 12345"));

        WebElement billingAddress = driver.findElement(By.id("address_invoice"));
        Assert.assertTrue(billingAddress.getText().contains("FirstName LastName"));
        Assert.assertTrue(billingAddress.getText().contains("TestCompany"));
        Assert.assertTrue(billingAddress.getText().contains("123 Test Street"));
        Assert.assertTrue(billingAddress.getText().contains("Apt 456"));
        Assert.assertTrue(billingAddress.getText().contains("Toronto Ontario 12345"));

        driver.findElement(By.xpath("//a[contains(text(),'Delete Account')]")).click();
        Thread.sleep(2000);

        WebElement accountDeleted = driver.findElement(By.xpath("//b[text()='Account Deleted!']"));
        Assert.assertTrue(accountDeleted.isDisplayed());

        System.out.println("Test Case 23: Verify address details in checkout page - PASSED");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
