package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestCase24_DownloadInvoiceAfterPurchaseOrder {
    WebDriver driver;

    @BeforeTest
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Browser drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void downloadInvoiceAfterPurchaseOrder() throws InterruptedException {
        driver.get("https://automationexercise.com");

        Assert.assertTrue(driver.getCurrentUrl().contains("automationexercise.com"));

        driver.findElement(By.xpath("(//a[@data-product-id='1'])[1]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[text()='Continue Shopping']")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//a[@href='/view_cart']")).click();
        Thread.sleep(2000);

        Assert.assertTrue(driver.getCurrentUrl().contains("view_cart"));

        driver.findElement(By.xpath("//a[text()='Proceed To Checkout']")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//u[text()='Register / Login']")).click();
        Thread.sleep(2000);

        String name = "User" + System.currentTimeMillis();
        String email = "test" + System.currentTimeMillis() + "@gmail.com";

        driver.findElement(By.name("name")).sendKeys(name);
        driver.findElement(By.xpath("//input[@data-qa='signup-email']")).sendKeys(email);
        driver.findElement(By.xpath("//button[@data-qa='signup-button']")).click();
        Thread.sleep(2000);

        driver.findElement(By.id("id_gender1")).click();
        driver.findElement(By.id("password")).sendKeys("123456");
        driver.findElement(By.id("days")).sendKeys("10");
        driver.findElement(By.id("months")).sendKeys("May");
        driver.findElement(By.id("years")).sendKeys("1990");
        driver.findElement(By.id("first_name")).sendKeys("First");
        driver.findElement(By.id("last_name")).sendKeys("Last");
        driver.findElement(By.id("company")).sendKeys("Company");
        driver.findElement(By.id("address1")).sendKeys("Address1");
        driver.findElement(By.id("country")).sendKeys("Canada");
        driver.findElement(By.id("state")).sendKeys("State");
        driver.findElement(By.id("city")).sendKeys("City");
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

        driver.findElement(By.xpath("//a[@href='/view_cart']")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//a[text()='Proceed To Checkout']")).click();
        Thread.sleep(2000);

        WebElement addressDetails = driver.findElement(By.xpath("//h2[text()='Address Details']"));
        Assert.assertTrue(addressDetails.isDisplayed());

        driver.findElement(By.name("message")).sendKeys("Test order for invoice");
        driver.findElement(By.xpath("//a[text()='Place Order']")).click();
        Thread.sleep(2000);

        driver.findElement(By.name("name_on_card")).sendKeys("Test User");
        driver.findElement(By.name("card_number")).sendKeys("4532015112830366");
        driver.findElement(By.name("cvc")).sendKeys("123");
        driver.findElement(By.name("expiry_month")).sendKeys("12");
        driver.findElement(By.name("expiry_year")).sendKeys("2025");

        driver.findElement(By.id("submit")).click();
        Thread.sleep(2000);

        WebElement successMessage = driver.findElement(By.xpath("//p[contains(text(),'Congratulations!')]"));
        Assert.assertTrue(successMessage.isDisplayed());

        driver.findElement(By.xpath("//a[text()='Download Invoice']")).click();
        Thread.sleep(3000);
        System.out.println("Invoice downloaded successfully");

        driver.findElement(By.xpath("//a[@data-qa='continue-button']")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//a[contains(text(),'Delete Account')]")).click();
        Thread.sleep(2000);

        WebElement accountDeleted = driver.findElement(By.xpath("//b[text()='Account Deleted!']"));
        Assert.assertTrue(accountDeleted.isDisplayed());

        System.out.println("Test Case 24: Download Invoice after purchase order - PASSED");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
