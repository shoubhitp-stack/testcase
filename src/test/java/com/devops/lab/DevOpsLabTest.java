package com.devops.lab;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

public class DevOpsLabTest {

    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
    }

    @Test
    public void testWelcomeMessage() {
        // Resolve absolute path from project root
        File htmlFile = new File(System.getProperty("user.dir"), "index.html");

        // Fallback check if index.html is located in src/main/resources
        if (!htmlFile.exists()) {
            htmlFile = new File(System.getProperty("user.dir"), "src/main/resources/index.html");
        }

        System.out.println("Target file path: " + htmlFile.getAbsolutePath());
        System.out.println("File exists on disk: " + htmlFile.exists());

        Assert.assertTrue(htmlFile.exists(), "Could not find index.html at " + htmlFile.getAbsolutePath());

        // Navigate to the local file using absolute URI
        driver.get(htmlFile.toURI().toString());

        // Locate heading by ID (or fallback to h1 tag if ID is missing)
        WebElement heading;
        if (!driver.findElements(By.id("welcome-text")).isEmpty()) {
            heading = driver.findElement(By.id("welcome-text"));
        } else {
            heading = driver.findElement(By.tagName("h1"));
        }

        String actualText = heading.getText().trim();
        Assert.assertEquals(actualText, "Welcome to devops lab", "The text did not match the expected message.");
        System.out.println("Test Passed: Verified heading '" + actualText + "' successfully.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}