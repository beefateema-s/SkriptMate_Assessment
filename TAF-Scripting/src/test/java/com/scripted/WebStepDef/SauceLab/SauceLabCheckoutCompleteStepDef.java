package com.scripted.WebStepDef.SauceLab;

import com.scripted.Pages.SauceLab.SauceLabCheckoutCompletePage;
import com.scripted.web.BrowserDriver;
import io.cucumber.java.en.Then;

public class SauceLabCheckoutCompleteStepDef extends BrowserDriver {
    SauceLabCheckoutCompletePage sauceLabCheckoutCompletePage = new SauceLabCheckoutCompletePage(getDriver());

    @Then("I validate order successful message")
    public void iValidateOrderSuccessfulMessage() {
        sauceLabCheckoutCompletePage.verifySuccessfulOrderMessage();
    }
}
