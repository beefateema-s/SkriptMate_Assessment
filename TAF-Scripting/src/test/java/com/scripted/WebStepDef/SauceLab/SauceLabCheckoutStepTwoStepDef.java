package com.scripted.WebStepDef.SauceLab;

import com.scripted.Pages.SauceLab.SauceLabCheckoutStepTwoPage;
import com.scripted.web.BrowserDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class SauceLabCheckoutStepTwoStepDef extends BrowserDriver {
    SauceLabCheckoutStepTwoPage sauceLabCheckoutStepTwoPage = new SauceLabCheckoutStepTwoPage(getDriver());

    @Then("I validate total price")
    public void iValidateTotalPrice() {
        sauceLabCheckoutStepTwoPage.validateSubTotalPrice();
        sauceLabCheckoutStepTwoPage.validateTotalPrice();
    }

    @And("I click on finish")
    public void iClickOnFinish() {
        sauceLabCheckoutStepTwoPage.clickOnFinish();
    }
}
