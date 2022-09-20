package com.scripted.WebStepDef.Facebook;

import com.scripted.Pages.Facebook.FacebookPage;
import com.scripted.reporting.AllureListener;
import com.scripted.web.BrowserDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.annotations.Listeners;

@Listeners({ AllureListener.class })
public class FacebookStepDef extends BrowserDriver{

    FacebookPage facebookPage = new FacebookPage(getDriver());

    @When("I enter username as {string} and password as {string}")
    public void i_enter_to_search(String username, String password) {
        facebookPage.enterUsername(username);
        facebookPage.enterPassword(password);
    }

    @Then("I click on log in button")
    public void I_click_on_log_in_button(){
        facebookPage.clickLogInButton();
    }

    @And("I should not login")
    public void i_should_login_successfully() throws InterruptedException {
        facebookPage.verifyLoginUnSuccess();
    }


}
