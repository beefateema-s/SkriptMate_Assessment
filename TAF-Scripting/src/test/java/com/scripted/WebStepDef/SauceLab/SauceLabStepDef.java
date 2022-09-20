package com.scripted.WebStepDef.SauceLab;

import com.scripted.Pages.SauceLab.*;
import com.scripted.web.BrowserDriver;
import com.scripted.web.WebHandlers;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SauceLabStepDef extends BrowserDriver {

    SauceLabLoginPage sauceLabLoginPage = new SauceLabLoginPage(getDriver());
    SauceLabProductsPage sauceLabProductsPage = new SauceLabProductsPage(getDriver());
    SauceLabCartPage sauceLabCartPage = new SauceLabCartPage(getDriver());

    SauceLabCheckoutStepOnePage sauceLabCheckoutStepOnePage = new SauceLabCheckoutStepOnePage(getDriver());
    SauceLabCheckoutStepTwoPage sauceLabCheckoutStepTwoPage = new SauceLabCheckoutStepTwoPage(getDriver());
    SauceLabCheckoutCompletePage sauceLabCheckoutCompletePage = new SauceLabCheckoutCompletePage(getDriver());

    Map<String, String> ItemPricePair = new HashMap<>();

    /* Adding items to cart
     * parameter - Name of the product to be added to cart (case-sensitive) */
    @When("I select items")
    public void iSelectItems(DataTable dataTable) {
        /* Loop to get the product from data table (feature file) */
        List<List<String>> rows = dataTable.cells();
        for (List<String> row : rows) {
            String parameter = row.get(0);
            System.out.println("Product: " + parameter);
//            sauceLabProductsPage.selectItems(parameter);   //old code 😢
            /* - Loop through inventory description
             * - Get the item name, check if it matches product name
             * - Yes, then click add to cart button
             * - Store item price in map --> key: product name, value: product price */
            for (WebElement item : sauceLabProductsPage.getInventoryDescription()) {
                WebElement itemName = item.findElement(By.cssSelector(" div[class=\"inventory_item_name\"]"));
                if (itemName.getText().equals(parameter)) {
                    WebElement addToCartButton = item.findElement(By.cssSelector(" button"));
                    WebHandlers.click(addToCartButton);
                    System.out.println("Successfully added " + parameter + " item to cart");
                    WebElement itemPrice = item.findElement(By.cssSelector(" div[class=\"inventory_item_price\"]"));
                    ItemPricePair.put(parameter, itemPrice.getText());
                }
            }
        }
    }

    /* Validate items in cart
     * parameter - Name of the product to be validated in the cart (case-sensitive) */
    @Then("I validate items in cart")
    public void validateItemsInCart(DataTable dataTable) {
        /* Loop to get the product from data table */
        List<List<String>> rows = dataTable.cells();
        for (List<String> row : rows) {
            String parameter = row.get(0);
            System.out.println("Product to verify: " + parameter);
            /* - Loop through cart item list
             * - Check if item name matches product name
             * - Yes, then get the item price
             * - Check if item price matches product price from map */
            for (WebElement item : sauceLabCartPage.getCartList()) {
                WebElement itemName = item.findElement(By.cssSelector(" div[class=\"inventory_item_name\"]"));
                if (itemName.getText().equals(parameter)) {
                    System.out.println("Successfully verified: " + parameter);
                    WebElement itemPrice = item.findElement(By.cssSelector(" div[class=\"inventory_item_price\"]"));
                    WebHandlers.verifyText(itemPrice, ItemPricePair.get(parameter));
                    System.out.println("Successfully verified Price: " + ItemPricePair.get(parameter));
                }
            }
        }
    }
}
