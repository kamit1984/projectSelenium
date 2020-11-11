package com.celonis.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

/**
 * Create by Amit on 11/10/20
 */
public class HomePage extends BasePage {

//    WebDriver driver;

    public void performLogout(){
        driver.findElement(By.cssSelector("button[data-testing-uid='userMenu-avatar-button']")).click();
        driver.findElement(By.cssSelector("button[data-testing-uid='userMenu-logout-button']")).click();

        // Verify Logged out is successful.
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(),'Sign in to your team')]"))).isDisplayed());
    }
}
