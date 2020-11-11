package com.celonis.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Create by Amit on 11/10/20
 */
public class ProcessAnalyticsPage extends BasePage {

    static Logger logger = Logger.getLogger(ProcessAnalyticsPage.class.getName());
    private List<WebElement> analysesSections;
    private Map<String, WebElement> analysesSectionsMap = new HashMap<>();

    public void openProcessAnalytics(){
        logger.info("Opening Process Analytics tab.");
        WebElement processAnalyticsTab = driver.findElement(By.xpath("//li//span[contains(text(),'Process Analytics')]"));
        List<WebElement> analysesSections = driver.findElements(By.cssSelector("workspace-section.workspaces__section"));
        List<WebElement> actualAnalyses = new ArrayList<WebElement>();
        for (WebElement analysisSection: analysesSections ) {
            actualAnalyses.add(analysisSection.findElement(By.cssSelector("h3.no-margin")));
            String name = analysisSection.findElement(By.cssSelector("h3.no-margin")).getText();
            analysesSectionsMap.put(name,analysisSection);
        }
    }

    public void openAnalysis(String analysisName){
        logger.info("Opening analysis " + analysisName);
        // Verifying Demo analyses are visible in Analyses section
        WebElement myAnalysisSection = analysesSectionsMap.get(analysisName);
        myAnalysisSection.findElement(By.cssSelector(".ce-tile__link")).click();
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".cases-selected-statistics__percentage"))).isDisplayed());
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.cssSelector(".cases-selected-statistics__percentage")),"100%"));
    }

    public void addNewProcessOverview(){
        logger.info("Opening new Sheet.");
        addNewSheet();
        addNewComponent("Process Overview");
    }

    public void addNewProcessExplorer(){
        logger.info("Opening new Sheet.");
        addNewSheet();
        addNewComponent("Process Explorer");
    }

    private void addNewSheet() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("i[data-testing-uid='bottomTabsView-addSheet-icon']")));
        driver.findElement(By.cssSelector("i[data-testing-uid='bottomTabsView-addSheet-icon']")).click();
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Add new App')]"))).isDisplayed());
    }

    public void moveToSheet(String sheetName){
        driver.findElement(By.xpath("//div[@data-testing-uid=\"sheetTab-tab-bottomTabs\"]/div[contains(text(),'" + sheetName + "')]")).click();
        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace();}  // Thread sleep
    }

    public void deleteSheet(String sheetName) {
        List<WebElement> sheets = driver.findElements(By.xpath("//div[@data-testing-uid=\"sheetTab-tab-bottomTabs\"]"));
        for (WebElement sheet: sheets) {
            if(sheet.getText().contains(sheetName)){
                logger.info("Deleting sheet " + sheet.getText());
                actions.contextClick(sheet).perform();
                try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace();}  // Thread sleep
                driver.findElement(By.xpath("//a[contains(text(),'Delete Sheet')]")).click();
                driver.findElement(By.cssSelector("button[data-testing-uid='messageBox-confirm-button']")).click();
                return;
            }
        }
    }

    private void addNewComponent(String nameOfComponent){
        logger.info("Opening component " + nameOfComponent);
        List<WebElement> components =  driver.findElements(By.cssSelector(".app-launcher__tile__title"));
        for ( WebElement component : components ) {
            if(component.getText().contains(nameOfComponent)){
                component.click();
                break;
            }
        }
        Assert.assertTrue(isFooterTabPresent(nameOfComponent),"New component "+nameOfComponent+" is not opened correctly.");
    }

    public boolean isFooterTabPresent(String nameOfTab){
        logger.info("Verifying presence of Footer tab " + nameOfTab);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'"+nameOfTab+"')]")));
        boolean flag = false;
        try{
            driver.findElement(By.xpath("//div[contains(text(),'"+nameOfTab+"')]")).isDisplayed();
            flag = true;
        }catch (Exception e){
            flag = false;
        }
        return flag;
    }

    public void setActivitiesPercentageTo100(){
////div[@class="pe-standalone__control pe-standalone__control--activities flex-vertical"]//button[@title="Add next important activity to the chart"]
////div[@class="pe-standalone__control pe-standalone__control--activities flex-vertical"]//div[@class="ce-percentage-circle-content text-center"]
        String percentage = driver.findElement(By.xpath("//div[@class=\"pe-standalone__control pe-standalone__control--activities flex-vertical\"]//div[@class=\"ce-percentage-circle-content text-center\"]")).getText();
        int count = 0;

        while (count < 15 || (!percentage.equals("100%"))){
            driver.findElement(By.xpath("//div[@class=\"pe-standalone__control pe-standalone__control--activities flex-vertical\"]//button[@title=\"Add next important activity to the chart\"]")).click();
            try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace();}  // Thread sleep
            percentage = driver.findElement(By.xpath("//div[@class=\"pe-standalone__control pe-standalone__control--activities flex-vertical\"]//div[@class=\"ce-percentage-circle-content text-center\"]")).getText();
            logger.info("Current percentage = "+ percentage);
            count++;
        }
    }
}
