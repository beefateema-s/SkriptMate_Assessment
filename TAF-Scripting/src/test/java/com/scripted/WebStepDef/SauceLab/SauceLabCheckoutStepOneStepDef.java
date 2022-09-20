package com.scripted.WebStepDef.SauceLab;

import com.scripted.Pages.SauceLab.SauceLabCheckoutStepOnePage;
import com.scripted.web.BrowserDriver;
import io.cucumber.java.en.And;

public class SauceLabCheckoutStepOneStepDef extends BrowserDriver {
    SauceLabCheckoutStepOnePage sauceLabCheckoutStepOnePage = new SauceLabCheckoutStepOnePage(getDriver());

    @And("I input my details as {string}, {string} and {string}")
    public void iInputMyDetails(String firstName, String lastName, String zipcode) {
        sauceLabCheckoutStepOnePage.enterInformation(firstName, lastName, zipcode);
    }

    @And("select continue")
    public void selectContinue() {
        sauceLabCheckoutStepOnePage.selectContinueButton();
    }
}
