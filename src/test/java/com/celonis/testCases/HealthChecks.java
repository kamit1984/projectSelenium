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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Create by Amit on 11/11/20
 */
public class HealthChecks {

    static Logger log = Logger.getLogger(HealthChecks.class.getName());
    Utilities utilities;

    @BeforeTest
    //    public void setup(String browser){
    public void setup(){
//        log.info("Browser name passed = " + browser);
            log.info("In Setup");
           utilities = new Utilities();
    }

    @AfterTest
    public void tearDown() throws InterruptedException{
        log.info("In TearDown");
    }


    @Test
    public void LoginLogoutWithPageObjectsTest(){
        LoginPage loginPage = new LoginPage();
        loginPage.performLogin();
        try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace();}
        HomePage homePage = new HomePage();

        WebElement tabs = homePage.driver.findElement(By.cssSelector("ul.ce-app-switcher__list"));
        WebElement processAnalyticsTab = tabs.findElement(By.xpath("//li//span[contains(text(),'Process Analytics')]"));
        WebElement eventCollectionTab = tabs.findElement(By.xpath("//span[contains(text(),'Event Collection')]"));
        WebElement machineLearningTab = tabs.findElement(By.xpath("//span[contains(text(),'Machine Learning')]"));
        WebElement processAutomationTab = tabs.findElement(By.xpath("//span[contains(text(),'Process Automation')]"));
        WebElement businessViewsTab = tabs.findElement(By.xpath("//span[contains(text(),'Business Views')]"));
        WebElement studioTab = tabs.findElement(By.xpath("//span[contains(text(),'Studio')]"));

        Assert.assertTrue(processAnalyticsTab.isDisplayed(),"Login into Celonis Cloud is not successful.");
        Assert.assertTrue(eventCollectionTab.isDisplayed(),"Login into Celonis Cloud is not successful.");
        Assert.assertTrue(machineLearningTab.isDisplayed(),"Login into Celonis Cloud is not successful.");
        Assert.assertTrue(processAutomationTab.isDisplayed(),"Login into Celonis Cloud is not successful.");
        Assert.assertTrue(businessViewsTab.isDisplayed(),"Login into Celonis Cloud is not successful.");
        Assert.assertTrue(studioTab.isDisplayed(),"Login into Celonis Cloud is not successful.");

        homePage.performLogout();
    }

    @Test
    public void VerifyPresenceOfDemoAnalyses() {
        LoginPage loginPage = new LoginPage();
        loginPage.performLogin();
        try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace();}
        HomePage homePage = new HomePage();

        ProcessAnalyticsPage processAnalyticsPage = new ProcessAnalyticsPage();

        List<WebElement> headerTitles = processAnalyticsPage.driver.findElements(By.cssSelector("h2.ce-header__title"));
        Assert.assertTrue(headerTitles.get(0).getText().contains("Workspaces"),"Workspaces title is not present");
        Assert.assertTrue(headerTitles.get(1).getText().contains("Analyses"),"Analyses title is not present");

        List<String> expectedDemoAnalyses = new ArrayList<>();
        expectedDemoAnalyses.addAll(Arrays.asList("--> SAP ECC - Order to Cash", "--> SAP ECC - Purchase to Pay", "--> ServiceNow Ticketing" ));

        List<WebElement> analyses = processAnalyticsPage.driver.findElements(By.cssSelector("div.ce-item-group__label"));
        List<String> actualWorkspaces = new ArrayList<>();
        for (WebElement element: analyses) {
            String actualName = element.getText();
            actualWorkspaces.add(actualName);
        }

        for ( String expectedDemoName : expectedDemoAnalyses) {
            Assert.assertTrue(actualWorkspaces.contains(expectedDemoName),"Analysis " + expectedDemoName + " is not present in the Workspaces");
        }

        // Verifying Demo analyses are visible in Analyses section
        List<WebElement> analysesSections = processAnalyticsPage.driver.findElements(By.cssSelector("workspace-section.workspaces__section"));
        List<WebElement> actualAnalyses = new ArrayList<WebElement>();
        for (WebElement analysisSection: analysesSections ) {
            actualAnalyses.add(analysisSection.findElement(By.cssSelector("h3.no-margin")));
        }

        List<String> actualDemoAnalyses = new ArrayList<>();
        for (WebElement element: actualAnalyses) {
            String actualName = element.getText();
            actualDemoAnalyses.add(actualName);
        }

        for ( String expectedDemoName : expectedDemoAnalyses) {
            Assert.assertTrue(actualDemoAnalyses.contains(expectedDemoName),"Analysis " + expectedDemoName + " is not present in the Analyses Section");
        }

        homePage.performLogout();
        homePage.driver.quit();
    }


}
