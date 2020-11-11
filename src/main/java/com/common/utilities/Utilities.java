package com.common.utilities;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

/**
 * Create by Amit on 11/11/20
 */
public class Utilities {

//    private WebDriver driver;

    static Logger log = Logger.getLogger(Utilities.class.getName());

    public boolean isWebElementPresent(WebDriver driver, By byStatement){
//        this.driver = driver;
        try{
            driver.findElement(byStatement);
        }catch (NoSuchElementException e){
            log.error("Unable to find WebElement using Selector " + byStatement);
            return false;
        }
        return true;
    }
}
