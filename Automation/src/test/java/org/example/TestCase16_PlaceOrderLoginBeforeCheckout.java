package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestCase16_PlaceOrderLoginBeforeCheckout {
    WebDriver driver;

    @BeforeTest
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Browser drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void placeOrderLoginBeforeCheckout() throws InterruptedException {
        driver.get("https://automationexercise.com");

        Assert.assertTrue(driver.getCurrentUrl().contains("automationexercise.com"));

        driver.findElement(By.linkText("Signup / Login")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//input[@data-qa='login-email']")).sendKeys("elhussienysabry@example.com");
        driver.findElement(By.xpath("//input[@data-qa='login-password']")).sendKeys("123456");
        driver.findElement(By.xpath("//button[@data-qa='login-button']")).click();
        Thread.sleep(2000);

        WebElement loggedInText = driver.findElement(By.xpath("//i[@class='fa fa-user']/parent::a"));
        Assert.assertTrue(loggedInText.isDisplayed());

        WebElement firstProduct = driver.findElement(By.xpath("(//div[@class='product-image-wrapper'])[1]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(firstProduct).perform();
        driver.findElement(By.xpath("(//a[@data-product-id='1'])[1]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[text()='Continue Shopping']")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//a[@href='/view_cart']")).click();
        Thread.sleep(2000);

        Assert.assertTrue(driver.getCurrentUrl().contains("view_cart"));

        driver.findElement(By.xpath("//a[text()='Proceed To Checkout']")).click();
        Thread.sleep(2000);

        WebElement addressDetails = driver.findElement(By.xpath("//h2[text()='Address Details']"));
        Assert.assertTrue(addressDetails.isDisplayed());

        WebElement reviewOrder = driver.findElement(By.xpath("//h2[text()='Review Your Order']"));
        Assert.assertTrue(reviewOrder.isDisplayed());

        driver.findElement(By.name("message")).sendKeys("Test order comment");
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

        System.out.println("Test Case 16: Place Order: Login before Checkout - PASSED");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
