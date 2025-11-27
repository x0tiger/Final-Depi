package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class TestCase09_SearchProduct {
    WebDriver driver;

    @BeforeTest
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Browser drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void searchProduct() throws InterruptedException {
        driver.get("https://automationexercise.com");

        Assert.assertTrue(driver.getCurrentUrl().contains("automationexercise.com"));

        driver.findElement(By.xpath("//a[@href='/products']")).click();
        Thread.sleep(2000);

        Assert.assertTrue(driver.getCurrentUrl().contains("products"));
        WebElement allProductsText = driver.findElement(By.xpath("//h2[@class='title text-center']"));
        Assert.assertTrue(allProductsText.getText().contains("ALL PRODUCTS"));

        driver.findElement(By.id("search_product")).sendKeys("Top");
        driver.findElement(By.id("submit_search")).click();
        Thread.sleep(2000);

        WebElement searchedProductsText = driver.findElement(By.xpath("//h2[@class='title text-center']"));
        Assert.assertTrue(searchedProductsText.getText().contains("SEARCHED PRODUCTS"));

        List<WebElement> searchResults = driver.findElements(By.className("productinfo"));
        Assert.assertTrue(searchResults.size() > 0, "Search results are visible");

        System.out.println("Test Case 09: Search Product - PASSED");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
