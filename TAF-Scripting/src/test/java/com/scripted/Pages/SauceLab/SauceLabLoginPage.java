package com.scripted.Pages.SauceLab;

import com.scripted.utils.WebEnvironment;
import com.scripted.web.WebHandlers;
import com.scripted.web.WebWaitHelper;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SauceLabLoginPage {
    WebDriver driver;
    WebEnvironment webEnvironment = ConfigFactory.create(WebEnvironment.class);

    public SauceLabLoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(id = "password")
    private WebElement input_Password;
    @FindBy(id = "user-name")
    private WebElement input_Username;
    @FindBy(id = "login-button")
    private WebElement input_Login;
    @FindBy(css = "h3[data-test=\"error\"]")
    private WebElement error_Message;

    public void enterUserName(String username) {
        WebWaitHelper.waitForElement(input_Username);
        WebHandlers.enterText(input_Username, username);
    }

    public void enterUserName() {
        WebWaitHelper.waitForElement(input_Username);
        WebHandlers.enterText(input_Username, webEnvironment.getUsername());
    }

    public void enterPassword(String password) {
        WebWaitHelper.waitForElement(input_Password);
        WebHandlers.enterText(input_Password, password);
    }

    public void enterPassword() {
        WebWaitHelper.waitForElement(input_Password);
        WebHandlers.enterText(input_Password, webEnvironment.getPassword());
    }

    public void SauceLabClickLogInButton() {
        WebWaitHelper.waitForElement(input_Login);
        WebHandlers.click(input_Login);
    }

    public void validateErrorMessage() {
        WebHandlers.verifyText(error_Message, "Epic sadface: Username and password do not match any user in this service");
    }
}
