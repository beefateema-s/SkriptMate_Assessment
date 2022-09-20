package com.scripted.WebStepDef.SauceLab;

import com.scripted.Pages.SauceLab.SauceLabLoginPage;
import com.scripted.web.BrowserDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SauceLabLoginStepDef extends BrowserDriver {

    SauceLabLoginPage sauceLabLoginPage = new SauceLabLoginPage(getDriver());

    @When("I enter username as {string}")
    public void iEnterUsernameAs(String username) {
        sauceLabLoginPage.enterUserName(username);
    }

    @And("I enter password as {string}")
    public void iEnterPasswordAs(String password) {
        sauceLabLoginPage.enterPassword(password);
    }

    @Then("I click on sauce lab log in button")
    public void iClickOnSauceLabLogInButton() {
        sauceLabLoginPage.SauceLabClickLogInButton();
    }

    @Then("I validate login error message")
    public void iValidateLoginErrorMessage() {
        sauceLabLoginPage.validateErrorMessage();
    }

    @When("I enter username")
    public void iEnterUsername() {
        sauceLabLoginPage.enterUserName();
    }

    @And("I enter password")
    public void iEnterPassword() {
        sauceLabLoginPage.enterPassword();
    }
}
