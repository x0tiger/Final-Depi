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

public class TestCase12_AddProductsInCart {
    WebDriver driver;

    @BeforeTest
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Browser drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void addProductsInCart() throws InterruptedException {
        driver.get("https://automationexercise.com");

        Assert.assertTrue(driver.getCurrentUrl().contains("automationexercise.com"));

        driver.findElement(By.xpath("//a[@href='/products']")).click();
        Thread.sleep(2000);

        WebElement firstProduct = driver.findElement(By.xpath("(//div[@class='product-image-wrapper'])[1]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(firstProduct).perform();
        driver.findElement(By.xpath("(//a[@data-product-id='1'])[1]")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//button[text()='Continue Shopping']")).click();
        Thread.sleep(2000);

        WebElement secondProduct = driver.findElement(By.xpath("(//div[@class='product-image-wrapper'])[2]"));
        actions.moveToElement(secondProduct).perform();
        driver.findElement(By.xpath("(//a[@data-product-id='2'])[1]")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//u[text()='View Cart']")).click();
        Thread.sleep(2000);

        WebElement product1 = driver.findElement(By.xpath("//a[@href='/product_details/1']"));
        WebElement product2 = driver.findElement(By.xpath("//a[@href='/product_details/2']"));
        Assert.assertTrue(product1.isDisplayed(), "Product 1 is in cart");
        Assert.assertTrue(product2.isDisplayed(), "Product 2 is in cart");

        WebElement price1 = driver.findElement(By.xpath("(//td[@class='cart_price'])[1]"));
        WebElement price2 = driver.findElement(By.xpath("(//td[@class='cart_price'])[2]"));
        Assert.assertTrue(price1.isDisplayed(), "Price 1 is visible");
        Assert.assertTrue(price2.isDisplayed(), "Price 2 is visible");

        System.out.println("Test Case 12: Add Products in Cart - PASSED");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
