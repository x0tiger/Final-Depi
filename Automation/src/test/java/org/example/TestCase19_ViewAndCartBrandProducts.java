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

public class TestCase19_ViewAndCartBrandProducts {
    WebDriver driver;

    @BeforeTest
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Browser drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void viewAndCartBrandProducts() throws InterruptedException {
        driver.get("https://automationexercise.com");

        driver.findElement(By.xpath("//a[@href='/products']")).click();
        Thread.sleep(2000);

        WebElement brandsSection = driver.findElement(By.xpath("//h2[text()='Brands']"));
        Assert.assertTrue(brandsSection.isDisplayed(), "Brands are visible");

        driver.findElement(By.xpath("//a[@href='/brand_products/Polo']")).click();
        Thread.sleep(2000);

        Assert.assertTrue(driver.getCurrentUrl().contains("brand_products"));
        WebElement brandTitle = driver.findElement(By.xpath("//h2[@class='title text-center']"));
        Assert.assertTrue(brandTitle.getText().contains("BRAND"), "Brand page is displayed");

        driver.findElement(By.xpath("//a[@href='/brand_products/Madame']")).click();
        Thread.sleep(2000);

        Assert.assertTrue(driver.getCurrentUrl().contains("brand_products/Madame"));
        WebElement madameBrandTitle = driver.findElement(By.xpath("//h2[@class='title text-center']"));
        Assert.assertTrue(madameBrandTitle.getText().contains("BRAND"), "Madame brand page is displayed");

        System.out.println("Test Case 19: View & Cart Brand Products - PASSED");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
