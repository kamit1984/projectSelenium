package com.celonis.testCases;

import com.celonis.pages.HomePage;
import com.celonis.pages.LoginPage;
import com.celonis.pages.ProcessAnalyticsPage;
import com.common.utilities.Utilities;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Create by Amit on 11/11/20
 */
public class PurchaseToPayTest {

    static Logger log = Logger.getLogger(PurchaseToPayTest.class.getName());
    private Utilities utilities;
    private LoginPage loginPage;

    @BeforeTest
    //    public void setup(String browser){
    public void setup(){
        log.info("In Setup");
        utilities = new Utilities();
    }

    @AfterTest
    public void tearDown() throws InterruptedException{
        log.info("In TearDown");
        loginPage.driver.close();
    }

    @Test(groups = {"Regression"})
    public void verifyProcessExplorerCreationFor_SAP_ECC_PurchaseToPay(){
        LoginPage loginPage = new LoginPage();
        loginPage.performLogin();
        try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace();}
        HomePage homePage = new HomePage();

        ProcessAnalyticsPage processAnalyticsPage = new ProcessAnalyticsPage();

        processAnalyticsPage.openProcessAnalytics();
        processAnalyticsPage.openAnalysis("--> SAP ECC - Purchase to Pay");
        processAnalyticsPage.addNewProcessExplorer();

        // Verifying different icons are displayed on Process Explorer
        Assert.assertTrue(utilities.isWebElementPresent(processAnalyticsPage.driver,By.cssSelector("#icon-frequency")),
                    "Icon icon-frequency is not displayed in Process Explorer");
        Assert.assertTrue(utilities.isWebElementPresent(processAnalyticsPage.driver,By.cssSelector("#icon-play")),
                "Icon icon-play is not displayed in Process Explorer");
        Assert.assertTrue(utilities.isWebElementPresent(processAnalyticsPage.driver,By.cssSelector("#icon-hidden-activities")),
                "Icon icon-hidden-activities is not displayed in Process Explorer");

        // Open Process Overview and do verification of default elements
        processAnalyticsPage.addNewProcessOverview();

        Assert.assertTrue(utilities.isWebElementPresent(processAnalyticsPage.driver,By.xpath("//div[contains(text(),'Cases per day')]")),
                "Cases per day KPI is not displayed");
        Assert.assertTrue(utilities.isWebElementPresent(processAnalyticsPage.driver,By.xpath("//div[contains(text(),'Events per day')]")),
                "Events per day KPI is not displayed");
        Assert.assertTrue(utilities.isWebElementPresent(processAnalyticsPage.driver,By.xpath("//div[contains(text(),'Throughput time')]")),
                "Throughput time KPI is not displayed");
        Assert.assertTrue(utilities.isWebElementPresent(processAnalyticsPage.driver,By.xpath("//div[contains(text(),'Daily cases per month')]")),
                "'Daily cases per month' graph is not displayed");
        Assert.assertTrue(utilities.isWebElementPresent(processAnalyticsPage.driver,By.xpath("//div[contains(text(),'Happy path in percentages')]")),
                "'Happy path in percentages' KPI is not displayed");
        Assert.assertTrue(utilities.isWebElementPresent(processAnalyticsPage.driver,By.xpath("//div[contains(text(),'Happy path in absolute numbers')]")),
                "'Happy path in absolute numbers' KPI is not displayed");
        Assert.assertTrue(utilities.isWebElementPresent(processAnalyticsPage.driver,By.xpath("//div[contains(text(),'Happy path throughput time')]")),
                "'Happy path throughput time' KPI is not displayed");
        Assert.assertTrue(utilities.isWebElementPresent(processAnalyticsPage.driver,By.xpath("//div[text()='Throughput times']")),
                "'Throughput times' tab is not displayed");
        Assert.assertTrue(utilities.isWebElementPresent(processAnalyticsPage.driver,By.xpath("//div[text()='Activities']")),
                "'Activities' tab is not displayed");

        // Verification in 'Throughput times' window
        processAnalyticsPage.driver.findElement(By.xpath("//div[text()='Throughput times']")).click();
        Assert.assertTrue(utilities.isWebElementPresent(processAnalyticsPage.driver,By.xpath("//div[@class=\"fullscreen-app__section__header\"]//div[contains(text(),'Throughput Time Search')]")),
                "'Throughput Time Search' graph is not displayed in 'Throughput times' section.");
        Assert.assertTrue(utilities.isWebElementPresent(processAnalyticsPage.driver,By.xpath("//div[@class=\"fullscreen-app__section__header\"]//div[contains(text(),'Bottlenecks')]")),
                "'Bottlenecks' information is not displayed in 'Throughput times' section.");
        // Verify that Bottlenecks are displayed

        try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace();}  // Thread sleep
        List<WebElement> bottlenecks = processAnalyticsPage.driver.findElements(By.xpath("//div[@ng-repeat=\"connection in bottlenecks.connections\"]"));

        Assert.assertTrue(bottlenecks.size() > 0,"No bottlenecks are displayed in 'Throughput times' section.");

        processAnalyticsPage.deleteSheet("Process Overview");
        processAnalyticsPage.deleteSheet("Process Explorer");
        homePage.goToCelonisHome();
        homePage.performLogout();
    }

    @Test(enabled = false)
    public void verifyAdditionOfFiltersAndNarrowingOfCases(){
        LoginPage loginPage = new LoginPage();
        loginPage.performLogin();
        try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace();}
        HomePage homePage = new HomePage();

        ProcessAnalyticsPage processAnalyticsPage = new ProcessAnalyticsPage();

        processAnalyticsPage.openProcessAnalytics();
        processAnalyticsPage.openAnalysis("--> SAP ECC - Purchase to Pay");
        processAnalyticsPage.addNewProcessExplorer();
        processAnalyticsPage.addNewProcessOverview();
        processAnalyticsPage.moveToSheet("Process Explorer");
        processAnalyticsPage.setActivitiesPercentageTo100();
    }

}
