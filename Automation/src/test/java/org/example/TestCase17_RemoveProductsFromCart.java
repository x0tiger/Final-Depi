package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestCase17_RemoveProductsFromCart {
    WebDriver driver;

    @BeforeTest
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Browser drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void removeProductsFromCart() throws InterruptedException {
        driver.get("https://automationexercise.com");

        Assert.assertTrue(driver.getCurrentUrl().contains("automationexercise.com"));

        driver.findElement(By.xpath("(//a[@data-product-id='1'])[1]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[text()='Continue Shopping']")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//a[@href='/view_cart']")).click();
        Thread.sleep(2000);

        Assert.assertTrue(driver.getCurrentUrl().contains("view_cart"));

        driver.findElement(By.className("cart_quantity_delete")).click();
        Thread.sleep(2000);

        WebElement emptyCart = driver.findElement(By.xpath("//b[text()='Cart is empty!']"));
        Assert.assertTrue(emptyCart.isDisplayed(), "Product is removed and cart is empty");

        System.out.println("Test Case 17: Remove Products From Cart - PASSED");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
