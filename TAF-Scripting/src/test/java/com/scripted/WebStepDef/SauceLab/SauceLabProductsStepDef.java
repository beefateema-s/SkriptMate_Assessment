package com.scripted.WebStepDef.SauceLab;

import com.scripted.Pages.SauceLab.SauceLabProductsPage;
import com.scripted.web.BrowserDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SauceLabProductsStepDef extends BrowserDriver {
    SauceLabProductsPage sauceLabProductsPage = new SauceLabProductsPage(getDriver());

    @And("I view my shopping cart")
    public void iViewMyShoppingCart() {
        sauceLabProductsPage.clickOnShoppingCart();
    }

    @When("I select filter as {string}")
    public void iSelectFilter(String filter) {
        sauceLabProductsPage.selectFilter(filter);
    }

    @Then("I validate selected filter")
    public void iValidateSelectedFilter() {
        sauceLabProductsPage.validateSelectedFilter();
    }
}
