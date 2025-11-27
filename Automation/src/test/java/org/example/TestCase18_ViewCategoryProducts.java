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

public class TestCase18_ViewCategoryProducts {
    WebDriver driver;

    @BeforeTest
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Browser drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void viewCategoryProducts() throws InterruptedException {
        driver.get("https://automationexercise.com");

        WebElement categoriesSection = driver.findElement(By.xpath("//h2[text()='Category']"));
        Assert.assertTrue(categoriesSection.isDisplayed(), "Categories are visible");

        driver.findElement(By.xpath("//a[@href='#Women']")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//a[@href='/category_products/1']")).click();
        Thread.sleep(2000);

        Assert.assertTrue(driver.getCurrentUrl().contains("category_products"));
        WebElement categoryTitle = driver.findElement(By.xpath("//h2[@class='title text-center']"));
        Assert.assertTrue(categoryTitle.getText().contains("WOMEN"), "Category page is displayed");

        driver.findElement(By.xpath("//a[@href='#Men']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//a[@href='/category_products/3']")).click();
        Thread.sleep(2000);

        Assert.assertTrue(driver.getCurrentUrl().contains("category_products/3"));
        WebElement menCategoryTitle = driver.findElement(By.xpath("//h2[@class='title text-center']"));
        Assert.assertTrue(menCategoryTitle.getText().contains("MEN"), "Men category page is displayed");

        System.out.println("Test Case 18: View Category Products - PASSED");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
