package com.automationpractice.testcases;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.awt.*;
import java.util.Random;

/**
 * Create by Amit on 9/18/20
 */
public class FirefoxTests extends BaseClass {

    public static WebDriver driver;
    private String testURL = "http://automationpractice.com/index.php";
    static Logger log = Logger.getLogger(ChromeTests.class.getName());
    Actions actions;
    WebDriverWait wait;
    Robot robot;

    @Parameters({"browser"})
    @BeforeTest
    public void setup(String browser) throws AWTException {
        log.info("Browser name passed = " + browser);
        log.info("In FirefoxTests Setup");

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
//        robot = new Robot();
    }

    @AfterTest
    public void tearDown() throws InterruptedException{
        log.info("In FirefoxTests TearDown");
        driver.quit();
        Thread.sleep(5000);
    }

    @Test
    public void VerifyProductNames() throws InterruptedException {
        driver.get(testURL);
        Thread.sleep(5000);
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

    @Test
    public void VerifyIDnowPage() throws InterruptedException {
        String testURL = "https://go.idnow.de/ihrebank/userdata";
        Random random = new Random();
        int number = random.nextInt(1000000000);

        driver.get(testURL);
        Thread.sleep(2000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ember510"))).sendKeys(String.valueOf(number));
        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("document.body.style.zoom = '50%';");

//        for(int i=0; i<3; i++){
//            robot.keyPress(KeyEvent.VK_CONTROL);
//            robot.keyPress(KeyEvent.VK_MINUS);
//            robot.keyRelease(KeyEvent.VK_CONTROL);
//            robot.keyRelease(KeyEvent.VK_MINUS);
//        }

//        js.executeAsyncScript("window.scrollBy(0,500)");
        js.executeScript("window.scrollBy(0,500)");


        Select select = new Select(driver.findElement(By.id("ember439")));
        select.selectByVisibleText("Mr.");

        select = new Select(driver.findElement(By.id("ember442")));
        select.selectByVisibleText("Dr.");

        driver.findElement(By.id("ember540")).sendKeys("Amit");
        driver.findElement(By.id("ember542")).sendKeys("Kshirsagar");

        select = new Select(driver.findElement(By.id("ember469")));
        select.selectByVisibleText("7");
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
        js.executeScript("window.scrollBy(0,1000)");

        driver.findElement(By.id("start-has-webcam")).click();
        driver.findElement(By.tagName("button")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("mobile-step")));
        String expectedToken = driver.findElement(By.className("transaction-token")).getText();
        System.out.println("Expected Token =  " + expectedToken);

    }
}
