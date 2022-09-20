package com.scripted.Pages.Common;

import com.scripted.utils.WebEnvironment;
import com.scripted.web.BrowserDriver;
import com.scripted.web.WebHandlers;
import com.scripted.web.WebWaitHelper;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BrowserDriver {

    WebDriver driver;
    WebEnvironment webEnvironment = ConfigFactory.create(WebEnvironment.class);
    public String url = null;

    @FindBy(css = "input[type='email']")
    private WebElement input_email;
    @FindBy(css = "input[type='submit']")
    private WebElement next_button;
    @FindBy(css = "input[value=\"I Accept\"]")
    private WebElement accept_button;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void launchUrl() {
        url = webEnvironment.getFbUrl();
        launchWebURL(url);
    }

    public void launchSauceUrl() {
        url = webEnvironment.getSauceUrl();
        launchWebURL(url);
        this.ustIdLogin();
    }

    public void ustIdLogin() {
        /* UST Id login */
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("https://login.microsoftonline.com/")) {
            WebHandlers.enterText(input_email, webEnvironment.getUstId());
            WebHandlers.click(next_button);
        }
        WebWaitHelper.waitForElement(accept_button);
        WebHandlers.click(accept_button);
    }
}
