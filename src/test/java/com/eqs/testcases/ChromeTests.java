package com.eqs.testcases;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * Create by Amit on 9/18/20
 */
public class ChromeTests extends BaseClass {

    public static WebDriver driver;
    private String testURL = "http://automationpractice.com/index.php";
    static Logger log = Logger.getLogger(ChromeTests.class.getName());
    Actions actions;
    WebDriverWait wait;

    @Parameters({"browser"})
    @BeforeTest
    public void setup(String browser){
        log.info("Browser name passed = " + browser);
        log.info("In ChromeTests Setup");

        if(browser.toLowerCase().contains("firefox")){
            System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"/src/test/resources/executables/geckodriver");
            driver = new FirefoxDriver();
        }else if(browser.toLowerCase().contains("chrome")){
            System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"/src/test/resources/executables/chromedriver");
            driver = new ChromeDriver();
        }
        driver.manage().window().maximize();
        actions = new Actions(driver);
        wait = new WebDriverWait(driver,15);
    }

    @AfterTest
    public void tearDown() throws InterruptedException{
        log.info("In ChromeTests TearDown");
        driver.quit();
        Thread.sleep(5000);
    }

    @Test
    public void VerifyProductNames() throws InterruptedException {
        driver.get(testURL);
        Thread.sleep(5000);
//        base.VerifyProductNamesBaseClass(driver);
        VerifyProductNamesBaseClass(driver);
    }

    @Test
    public  void VerifyChangingProductColors() throws  InterruptedException{
        driver.get("http://automationpractice.com/index.php?id_product=5&controller=product");
        Thread.sleep(5000);
        VerifyChangingProductColorsBase(driver);
    }

    @Test
    public void VerifyLogoNavigation() throws  InterruptedException {
        // Opening non-testURL
        driver.get("http://automationpractice.com/index.php?id_product=5&controller=product");
        Thread.sleep(2000);
        VerifyLogoNavigationBase(driver);
    }

    @Test
    public void VerifyAddingDiscountedProductToCart() throws InterruptedException{
        driver.get(testURL);
        Thread.sleep(2000);
        VerifyAddingDiscountedProductToCartBase(driver);
    }

    @Test
    public  void VerifyCartCalculations() throws  InterruptedException{
        driver.get(testURL);
        Thread.sleep(2000);
        VerifyCartCalculationsBase(driver);
    }

    @Test(enabled = false)
    public void VerifySortingOfProducts() throws  InterruptedException{
        driver.get("http://automationpractice.com/index.php?id_category=3&controller=category");
        Thread.sleep(3000);

        Actions actions = new Actions(driver);

        WebElement sortButton = driver.findElement(By.id("selectProductSort"));
        actions.moveToElement(sortButton).build().perform();

        Select select = new Select(sortButton);
        select.selectByVisibleText("Price: Lowest first");
        WebDriverWait wait = new WebDriverWait(driver,5);

        // TO-DO
        // Unable to complete this test as Sorting action is not finishing properly.
        // It continues to sort even after waiting for 20 mins.

    }


}
