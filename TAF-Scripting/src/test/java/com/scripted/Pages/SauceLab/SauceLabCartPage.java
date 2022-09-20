package com.scripted.Pages.SauceLab;

import com.scripted.web.WebHandlers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class SauceLabCartPage {
    WebDriver driver;

    public SauceLabCartPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(id = "checkout")
    private WebElement button_checkout;
    @FindBy(xpath = "//div[@class='inventory_item_price']")
    private WebElement div_nP2999;
    @FindBy(id = "continue-shopping")
    private WebElement button_continueshoppin;
    @FindBy(xpath = "//div[normalize-space()='Test.allTheThings() T-Shirt (Red)']")
    private WebElement div_CJTestallTheThi;
    @FindBy(xpath = "//div[normalize-space()='$15.99']")
    private WebElement div_SQ1599;
    @FindBy(id = "remove-test.allthethings()-t-shirt-(red)")
    private WebElement button_removetestallth;
    @FindBy(xpath = "//div[normalize-space()='Sauce Labs Backpack']")
    private WebElement div_AXSauceLabsBack;
    @FindBy(xpath = "//div[normalize-space()='$29.99']")
    private WebElement div_nF2999;
    @FindBy(id = "remove-sauce-labs-backpack")
    private WebElement button_removesaucelabs;

    @FindBy(css = "div[class=\"cart_item\"]")
    private List<WebElement> CartList;
    @FindBy(css = "div[class=\"cart_item\"] div[class=\"inventory_item_name\"]")
    private List<WebElement> CartItemName;
    @FindBy(css = "div[class=\"cart_item\"] div[class=\\\"inventory_item_price\\\"]")
    private List<WebElement> CartItemPrice;

    public void clickOnCheckoutButton() {
        WebHandlers.click(button_checkout);
    }

    public List<WebElement> getCartList() {
        return CartList;
    }

    /* Remove item from cart
     * - Loop through Cart list
     * - Get the item name, check if it matches product name
     * - Yes, then click remove button */
    public void remove_item(String product) {
        for (WebElement item : CartList) {
            WebElement itemName = item.findElement(By.cssSelector(" div[class=\"inventory_item_name\"]"));
            if (itemName.getText().equals(product)) {
                WebElement removeButton = item.findElement(By.cssSelector(" button"));
                WebHandlers.click(removeButton);
                System.out.println("Successfully removed " + product + " item from cart");
            }
        }
    }

// old code 😢
//    public List<WebElement> getCartItemName() {
//        return CartItemName;
//    }
//
//    public List<WebElement> getCartItemPrice() {
//        return CartItemPrice;
//    }
//
//    public void verifyItemsInCart(String product) {
//        ArrayList<String> itemList = new ArrayList<>();
//        for (WebElement item : CartItemName) {
//            itemList.add(item.getText());
//        }
//        if (itemList.contains(product)) {
//            System.out.println("Successfully verified: " + product);
//        }
//
//    }
}
