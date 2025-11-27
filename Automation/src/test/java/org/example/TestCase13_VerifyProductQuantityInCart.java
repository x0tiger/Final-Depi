package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestCase13_VerifyProductQuantityInCart {
    WebDriver driver;

    @BeforeTest
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Browser drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void verifyProductQuantityInCart() throws InterruptedException {
        driver.get("https://automationexercise.com");

        Assert.assertTrue(driver.getCurrentUrl().contains("automationexercise.com"));

        driver.findElement(By.xpath("//a[@href='/product_details/1']")).click();
        Thread.sleep(2000);

        Assert.assertTrue(driver.getCurrentUrl().contains("product_details"));

        WebElement quantityField = driver.findElement(By.id("quantity"));
        quantityField.clear();
        quantityField.sendKeys("4");

        driver.findElement(By.xpath("//button[@type='button']")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//u[text()='View Cart']")).click();
        Thread.sleep(2000);

        WebElement quantity = driver.findElement(By.className("cart_quantity"));
        Assert.assertEquals(quantity.getText(), "4", "Product quantity is 4");

        System.out.println("Test Case 13: Verify Product quantity in Cart - PASSED");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
