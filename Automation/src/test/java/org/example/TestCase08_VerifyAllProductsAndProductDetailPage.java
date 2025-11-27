package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestCase08_VerifyAllProductsAndProductDetailPage {
    WebDriver driver;

    @BeforeTest
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Browser drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void verifyAllProductsAndProductDetailPage() throws InterruptedException {
        driver.get("https://automationexercise.com");

        Assert.assertTrue(driver.getCurrentUrl().contains("automationexercise.com"));

        driver.findElement(By.xpath("//a[@href='/products']")).click();
        Thread.sleep(2000);

        Assert.assertTrue(driver.getCurrentUrl().contains("products"));
        WebElement allProductsText = driver.findElement(By.xpath("//h2[@class='title text-center']"));
        Assert.assertTrue(allProductsText.getText().contains("ALL PRODUCTS"), "ALL PRODUCTS page is visible");

        WebElement productsList = driver.findElement(By.className("features_items"));
        Assert.assertTrue(productsList.isDisplayed(), "Products list is visible");

        driver.findElement(By.xpath("//a[@href='/product_details/1']")).click();
        Thread.sleep(2000);

        Assert.assertTrue(driver.getCurrentUrl().contains("product_details"));

        WebElement productName = driver.findElement(By.xpath("//h2[contains(text(),'Blue Top')]"));
        Assert.assertTrue(productName.isDisplayed(), "Product name is visible");

        WebElement category = driver.findElement(By.xpath("//p[contains(text(),'Category')]"));
        Assert.assertTrue(category.isDisplayed(), "Category is visible");

        WebElement price = driver.findElement(By.xpath("//span[contains(text(),'Rs.')]"));
        Assert.assertTrue(price.isDisplayed(), "Price is visible");

        WebElement availability = driver.findElement(By.xpath("//b[text()='Availability:']"));
        Assert.assertTrue(availability.isDisplayed(), "Availability is visible");

        WebElement condition = driver.findElement(By.xpath("//b[text()='Condition:']"));
        Assert.assertTrue(condition.isDisplayed(), "Condition is visible");

        WebElement brand = driver.findElement(By.xpath("//b[text()='Brand:']"));
        Assert.assertTrue(brand.isDisplayed(), "Brand is visible");

        System.out.println("Test Case 08: Verify All Products and product detail page - PASSED");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
