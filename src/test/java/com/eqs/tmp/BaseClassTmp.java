package com.eqs.tmp;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;

/**
 * Create by Amit on 9/18/20
 */
public class BaseClassTmp {

//    public static Logger log = Logger.getLogger("MyLogger");
//    static Logger log = Logger.getLogger(BaseClass.class.getName());
    WebDriverWait wait ;
    public String testURL = "http://automationpractice.com/index.php";

    public void VerifyProductNamesBaseClass(WebDriver driver) throws InterruptedException {
        System.out.println("Inside base class");
//        log.info("Inside base class");
        String title = driver.getTitle();
        Assert.assertEquals(title,"My Store");

        WebElement productGrid = driver.findElement(By.id("homefeatured"));
        List<WebElement> products = productGrid.findElements(By.className("product-container"));

//        log.info("Found below products on the landing Page.");

        for ( WebElement product : products){
            System.out.println(product.findElement(By.className("product-name")).getText());
        }

        for ( WebElement product : products){
            Assert.assertTrue(product.isDisplayed());
        }
    }

    public  void VerifyChangingProductColorsBase(WebDriver driver) throws  InterruptedException{
        System.out.println("Inside base class");
        WebElement colorPicker = driver.findElement(By.cssSelector("ul[id=\"color_to_pick_list\"]"));
        Assert.assertTrue(colorPicker.isDisplayed());
        Actions actions = new Actions(driver);
        actions.moveToElement(colorPicker).build().perform();
        Thread.sleep(5000);

        // Get number of color objects
        List<WebElement> colors = colorPicker.findElements(By.cssSelector("a.color_pick"));
        Assert.assertNotNull(colors);

        // mapping color names to underlying image names. This will be used for assertions
        HashMap<String,String> colorToImage = new HashMap<String,String>();
        colorToImage.put("black","15-large_default");
        colorToImage.put("orange","14-large_default");
        colorToImage.put("blue","13-large_default");
        colorToImage.put("yellow","12-large_default");

        for (WebElement color: colors){
            String colorName = color.getAttribute("name");
            System.out.println("colorName = " + colorName);
            color.click();
            Thread.sleep(1000);

            WebElement dressImage = driver.findElement(By.cssSelector("img#bigpic"));
            String source = dressImage.getAttribute("src");
            System.out.println("Source value = " + source);
            System.out.println("Value of source from map = " + colorToImage.get(colorName.toLowerCase()));
            Assert.assertTrue(source.toLowerCase().contains(colorToImage.get(colorName.toLowerCase())));
        }
    }

    public void VerifyLogoNavigationBase(WebDriver driver) throws  InterruptedException{
        // Opening non-testURL
        driver.get("http://automationpractice.com/index.php?id_product=5&controller=product");
//        Thread.sleep(2000);

        wait = new WebDriverWait(driver,15);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("img#bigpic")));
        WebElement bigImage = driver.findElement(By.cssSelector("img#bigpic"));

        WebElement logo = driver.findElement(By.cssSelector("img.logo"));
        logo.click();
        wait.until(ExpectedConditions.stalenessOf(bigImage));

        String landingURL = testURL;
        Assert.assertEquals(landingURL,driver.getCurrentUrl());
    }

    public void VerifyAddingDiscountedProductToCartBase(WebDriver driver) throws InterruptedException{
        driver.get(testURL);
        clearShoppingCartContents(driver);
        WebElement productGrid = driver.findElement(By.id("homefeatured"));
        List<WebElement> products = productGrid.findElements(By.className("product-container"));

        WebElement productToSelect = null ;
        for ( WebElement product : products){
//          String discountedProduct;
            WebElement discountInfo;
            System.out.println(product.findElement(By.className("product-name")).getText());
            try{
                discountInfo = product.findElement(By.className("price-percent-reduction"));
            }catch (NoSuchElementException e){
                continue;
            }
            if (discountInfo != null){
                String discountAmount =   discountInfo.getAttribute("innerHTML");

                System.out.println("discountAmount = " + discountAmount);
                if(discountAmount.contains("-20")){
                    productToSelect = product;
                }
            }
        }
        if(productToSelect != null){
            System.out.println("discountedProductName = " + productToSelect.findElement(By.className("product-name")).getText());
        }else{
            System.out.println("Could not find Product with 20% discount.");
            Assert.assertTrue(false);
        }

        // Open the product page
        productToSelect.click();
        Thread.sleep(5000);

        String currentPrice = driver.findElement(By.cssSelector("span#our_price_display")).getText().replaceAll("\\$","");
        System.out.println("currentPrice = " + currentPrice);

        Double expectedTotalPrice = Double.valueOf(currentPrice) ;
        System.out.println("expectedTotalPrice = " + expectedTotalPrice);

        //Set Required quantity and add to cart
        driver.findElement(By.cssSelector("input#quantity_wanted")).clear();
        driver.findElement(By.cssSelector("input#quantity_wanted")).sendKeys("1");
        Thread.sleep(5000);

        driver.findElement(By.cssSelector("button[name=\"Submit\"]")).click();
        Thread.sleep(5000);

        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.layer_cart_product")));

        Double actualTotalPrice = Double.valueOf(driver.findElement(By.cssSelector(".ajax_block_products_total")).getText().replaceAll("\\$",""));
        System.out.println("actualTotalPrice = " + actualTotalPrice);

        Assert.assertEquals(actualTotalPrice,expectedTotalPrice);

        // Verifying Success Message and Icon
        WebElement tickMark = driver.findElement(By.cssSelector(".icon-ok"));
        Assert.assertNotNull(tickMark);

        WebElement cartMessage = tickMark.findElement(By.xpath("./.."));
        Assert.assertEquals(cartMessage.getText().trim(),"Product successfully added to your shopping cart");
    }

    public  void VerifyCartCalculationsBase(WebDriver driver) throws  InterruptedException{
        Integer quantityToOrder = new Integer(5);
        driver.get(testURL);
        clearShoppingCartContents(driver);
        WebElement productGrid = driver.findElement(By.id("homefeatured"));
        List<WebElement> products = productGrid.findElements(By.className("product-container"));

        WebElement productToSelect = null ;
        for ( WebElement product : products){
//          String discountedProduct;
            WebElement discountInfo;
            System.out.println(product.findElement(By.className("product-name")).getText());
            try{
                discountInfo = product.findElement(By.className("price-percent-reduction"));
            }catch (NoSuchElementException e){
                continue;
            }
            if (discountInfo != null){
                String discountAmount =   discountInfo.getAttribute("innerHTML");

                System.out.println("discountAmount = " + discountAmount);
                if(discountAmount.contains("-20")){
                    productToSelect = product;
                }
            }
        }
        if(productToSelect != null){
            System.out.println("discountedProductName = " + productToSelect.findElement(By.className("product-name")).getText());
        }else{
            System.out.println("Could not find Product with 20% discount.");
            Assert.assertTrue(false);
        }

        // Open the product page
        productToSelect.click();
        Thread.sleep(5000);

        String currentPrice = driver.findElement(By.cssSelector("span#our_price_display")).getText().replaceAll("\\$","");
        System.out.println("currentPrice = " + currentPrice);

        Double expectedTotalPrice = Double.valueOf(currentPrice) * quantityToOrder;
        System.out.println("expectedTotalPrice = " + expectedTotalPrice);

        //Set Required quantity and add to cart
        driver.findElement(By.cssSelector("input#quantity_wanted")).clear();
        driver.findElement(By.cssSelector("input#quantity_wanted")).sendKeys(quantityToOrder.toString());
        Thread.sleep(5000);

        driver.findElement(By.cssSelector("button[name=\"Submit\"]")).click();
        Thread.sleep(5000);

        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.layer_cart_product")));

        String cartMessage = driver.findElement(By.cssSelector(".icon-ok")).getAttribute("innerHTML");
        System.out.println("cartMessage = " + cartMessage);

        Double actualTotalPrice = Double.valueOf(driver.findElement(By.cssSelector(".ajax_block_products_total")).getText().replaceAll("\\$",""));
        System.out.println("actualTotalPrice = " + actualTotalPrice);

        Assert.assertEquals(actualTotalPrice,expectedTotalPrice);
    }

    public void clearShoppingCartContents(WebDriver driver) throws  InterruptedException {
        if(driver.findElement(By.cssSelector("span.ajax_cart_no_product")).isDisplayed()){
            System.out.println("Cart is Empty");
            return;
        }
        if(driver.findElement(By.cssSelector("span.ajax_cart_product_txt")).isDisplayed()){
            System.out.println("Cart contains few products");
        }

        driver.findElement(By.xpath("//div[contains(@class, 'shopping_cart')]/a")).click();
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1#cart_title")));

        //        a.cart_quantity_delete
        List<WebElement> deleteButtons = driver.findElements(By.cssSelector("a.cart_quantity_delete"));
        System.out.println("Number of different Products = " + deleteButtons.size());

        for (WebElement button : deleteButtons) {
            button.click();
            Thread.sleep(2000);
        }
        driver.get(testURL);
    }

}
