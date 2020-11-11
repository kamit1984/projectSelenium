package com.celonis.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Create by Amit on 11/10/20
 */
public class BasePage {

    public static WebDriver driver;
    public static WebDriverWait wait;
    public static Actions actions;

    public BasePage() {

        String browser = "Firefox";
        if(driver == null){
            if(browser.toLowerCase().contains("firefox")){
                System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"/src/test/resources/executables/geckodriver");
                driver = new FirefoxDriver();
            }else if(browser.toLowerCase().contains("chrome")){
                System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"/src/test/resources/executables/chromedriver");
                driver = new ChromeDriver();
            }
            driver.manage().window().maximize();
            actions = new Actions(driver);
            wait = new WebDriverWait(driver,20);
        }
    }

}
