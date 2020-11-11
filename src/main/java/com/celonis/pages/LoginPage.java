package com.celonis.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Create by Amit on 11/10/20
 */
public class LoginPage extends BasePage{

//    WebDriver driver;
//    WebDriverWait wait;
    private String testURL = "https://applications.eu-1.celonis.cloud/";


    public void performLogin(){
        driver.get(testURL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#ce-input-0"))).isDisplayed();
        driver.findElement(By.cssSelector("input[data-testing-uid='login-form-username-input']")).sendKeys("kamit1984@gmail.com");
        driver.findElement(By.cssSelector("input[data-testing-uid='login-form-password-input']")).sendKeys("Dc6$fb08");
        driver.findElement(By.cssSelector("button[cetestinguid='login-form-submit-button']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Process Analytics')]"))).isDisplayed();
    }

    public void registerForCelonisWorldTour(){

    }
}
