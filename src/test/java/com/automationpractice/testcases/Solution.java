package com.automationpractice.testcases;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.*;

public class Solution {
    public static WebDriver driver;
    static Logger log = Logger.getLogger(Solution.class.getName());
    static Actions actions;
    static WebDriverWait wait;

    public static void main(String args[] ) throws Exception {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT */
        String testURL = "https://go.idnow.de/ihrebank/userdata";
        Random random = new Random();
        int number = random.nextInt(1000000000);

        System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"/src/test/resources/executables/geckodriver");
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        actions = new Actions(driver);
        wait = new WebDriverWait(driver,15);

        driver.get(testURL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ember510"))).sendKeys(String.valueOf(number));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)");

        Select select = new Select(driver.findElement(By.id("ember439")));
        select.selectByVisibleText("Mr.");

        select = new Select(driver.findElement(By.id("ember442")));
        select.selectByVisibleText("Dr.");


        driver.findElement(By.cssSelector("input[placeholder*='All given names']")).sendKeys("Amit");
        driver.findElement(By.cssSelector("input[placeholder*='surname']")).sendKeys("Kshirsagar");

        select = new Select(driver.findElement(By.id("ember469")));
        select.selectByVisibleText("7");
        select = new Select(driver.findElement(By.id("ember471")));
        select.selectByVisibleText("January");

        select = new Select(driver.findElement(By.id("ember473")));
        select.selectByVisibleText("1984");

        driver.findElement(By.cssSelector("input[placeholder*='place of birth']")).sendKeys("Pune");
        driver.findElement(By.cssSelector("input[placeholder='Street']")).sendKeys("Wasserburger Landstr.");
        driver.findElement(By.cssSelector("input[placeholder='House No.']")).sendKeys("204a");
        driver.findElement(By.cssSelector("input[placeholder='ZIP Code']")).sendKeys("81827");
        driver.findElement(By.cssSelector("input[placeholder='City']")).sendKeys("Munchen");

        select = new Select(driver.findElement(By.id("ember478")));
        select.selectByVisibleText("Germany");

        driver.findElement(By.id("ember1533")).sendKeys("kamit1984@gmail.com");
        driver.findElement(By.id("ember1536")).sendKeys("01514343434");
        js.executeScript("window.scrollBy(0,1000)");

        driver.findElement(By.id("start-has-webcam")).click();
        driver.findElement(By.tagName("button")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("mobile-step")));
        String expectedToken = driver.findElement(By.className("transaction-token")).getText();
        assert(expectedToken != null);
        System.out.println("Expected Token =  " + expectedToken);
    }
}
