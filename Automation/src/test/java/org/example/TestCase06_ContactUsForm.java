package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestCase06_ContactUsForm {
    WebDriver driver;

    @BeforeTest
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Browser drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void contactUsForm() throws InterruptedException {
        driver.get("https://automationexercise.com");

        Assert.assertTrue(driver.getCurrentUrl().contains("automationexercise.com"));

        driver.findElement(By.xpath("//a[contains(text(),'Contact us')]")).click();
        Thread.sleep(2000);

        WebElement getInTouchText = driver.findElement(By.xpath("//h2[text()='Get In Touch']"));
        Assert.assertTrue(getInTouchText.isDisplayed(), "GET IN TOUCH is visible");

        driver.findElement(By.xpath("//input[@data-qa='name']")).sendKeys("Test User");
        driver.findElement(By.xpath("//input[@data-qa='email']")).sendKeys("testuser@gmail.com");
        driver.findElement(By.xpath("//input[@data-qa='subject']")).sendKeys("Test Subject");
        driver.findElement(By.xpath("//textarea[@data-qa='message']")).sendKeys("This is a test message");

        String filePath = System.getProperty("user.dir") + "\\pom.xml";
        driver.findElement(By.xpath("//input[@name='upload_file']")).sendKeys(filePath);

        driver.findElement(By.xpath("//input[@data-qa='submit-button']")).click();
        Thread.sleep(2000);

        driver.switchTo().alert().accept();
        Thread.sleep(2000);

        WebElement successMessage = driver.findElement(By.xpath("//div[@class='status alert alert-success']"));
        Assert.assertTrue(successMessage.getText().contains("Success!"), "Success message is visible");

        driver.findElement(By.xpath("//span[text()=' Home']")).click();
        Thread.sleep(2000);
        Assert.assertTrue(driver.getCurrentUrl().contains("automationexercise.com"));

        System.out.println("Test Case 06: Contact Us Form - PASSED");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
