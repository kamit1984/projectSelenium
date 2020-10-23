package com.idnow.testcases;

import com.eqs.testcases.ChromeTests;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    public static WebDriver driver;
    static Logger log = Logger.getLogger(ChromeTests.class.getName());
    static Actions actions;
    static WebDriverWait wait;

    public static void main(String args[] ) throws Exception {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT */
        String testURL = "https://go.idnow.de/ihrebank/userdata";

        System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"/src/test/resources/executables/geckodriver");
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        actions = new Actions(driver);
        wait = new WebDriverWait(driver,15);

        driver.get(testURL);
        // Add proper wait
        Thread.sleep(5000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ember510"))).sendKeys("123456789");

        Select select = new Select(driver.findElement(By.id("ember439")));
        select.selectByVisibleText("Mr.");

        select = new Select(driver.findElement(By.id("ember442")));
        select.selectByVisibleText("Dr.");

        driver.findElement(By.id("ember540")).sendKeys("Amit");
        driver.findElement(By.id("ember542")).sendKeys("Kshirsagar");


        WebElement sortButton = driver.findElement(By.id("ember469"));
        actions.moveToElement(sortButton).build().perform();

        select = new Select(driver.findElement(By.id("ember469")));
        select.selectByIndex(8);

        select = new Select(driver.findElement(By.id("ember471")));
        select.selectByVisibleText("January");

        select = new Select(driver.findElement(By.id("ember473")));
        select.selectByVisibleText("1984");

        driver.findElement(By.id("ember848")).sendKeys("Pune");


        driver.findElement(By.cssSelector("input[placeholder='Street']")).sendKeys("Wasserburger Landstr.");
        driver.findElement(By.cssSelector("input[placeholder='House No.']")).sendKeys("204a");
        driver.findElement(By.cssSelector("input[placeholder='ZIP Code']")).sendKeys("81827");
        driver.findElement(By.cssSelector("input[placeholder='City']")).sendKeys("Munchen");
        select = new Select(driver.findElement(By.id("ember478")));
        select.selectByVisibleText("Germany");

        driver.findElement(By.id("ember1533")).sendKeys("kamit1984@gmail.com");
        driver.findElement(By.id("ember1536")).sendKeys("01514343434");
        driver.findElement(By.id("start-has-webcam")).click();
        driver.findElement(By.id("btn-proceed centered proceedButton")).click();


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("mobile-step")));
        String expectedToken = driver.findElement(By.className("transaction-token")).getText();
        System.out.println("Expected Token =  " + expectedToken);

    }
}
