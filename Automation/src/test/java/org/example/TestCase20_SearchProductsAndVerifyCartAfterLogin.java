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

import java.util.List;

public class TestCase20_SearchProductsAndVerifyCartAfterLogin {
    WebDriver driver;

    @BeforeTest
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Browser drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void searchProductsAndVerifyCartAfterLogin() throws InterruptedException {
        driver.get("https://automationexercise.com");

        driver.findElement(By.xpath("//a[@href='/products']")).click();
        Thread.sleep(2000);

        Assert.assertTrue(driver.getCurrentUrl().contains("products"));

        driver.findElement(By.id("search_product")).sendKeys("Top");
        driver.findElement(By.id("submit_search")).click();
        Thread.sleep(2000);

        WebElement searchedProductsText = driver.findElement(By.xpath("//h2[@class='title text-center']"));
        Assert.assertTrue(searchedProductsText.getText().contains("SEARCHED PRODUCTS"));

        List<WebElement> searchResults = driver.findElements(By.className("productinfo"));
        Assert.assertTrue(searchResults.size() > 0);

        driver.findElement(By.xpath("(//a[@data-product-id='1'])[1]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[text()='Continue Shopping']")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("(//a[@data-product-id='3'])[1]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[text()='Continue Shopping']")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//a[@href='/view_cart']")).click();
        Thread.sleep(2000);
        WebElement cartProduct1 = driver.findElement(By.xpath("//a[@href='/product_details/1']"));
        Assert.assertTrue(cartProduct1.isDisplayed(), "Product 1 is in cart");

        driver.findElement(By.linkText("Signup / Login")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//input[@data-qa='login-email']")).sendKeys("elhussienysabry@example.com");
        driver.findElement(By.xpath("//input[@data-qa='login-password']")).sendKeys("123456");
        driver.findElement(By.xpath("//button[@data-qa='login-button']")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//a[@href='/view_cart']")).click();
        Thread.sleep(2000);

        WebElement cartProductAfterLogin = driver.findElement(By.xpath("//a[@href='/product_details/1']"));
        Assert.assertTrue(cartProductAfterLogin.isDisplayed(), "Products still visible in cart after login");

        System.out.println("Test Case 20: Search Products and Verify Cart After Login - PASSED");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
