package com.scripted.WebStepDef.SauceLab;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import com.scripted.Pages.SauceLab.SauceLabCartPage;
import com.scripted.web.BrowserDriver;

import java.util.List;

public class SauceLabCartStepDef extends BrowserDriver {
    SauceLabCartPage sauceLabCartPage = new SauceLabCartPage(getDriver());

    @And("I click on checkout")
    public void iClickOnCheckout() {
        sauceLabCartPage.clickOnCheckoutButton();
    }

    @And("I remove item from cart")
    public void iRemoveItemFromCart(DataTable dataTable) {
        /* Loop to get the product from data table */
        List<List<String>> rows = dataTable.cells();
        for (List<String> row : rows) {
            String parameter = row.get(0);
            System.out.println("Product to remove: " + parameter);
            sauceLabCartPage.remove_item(parameter);
        }
    }
}
