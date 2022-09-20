package com.scripted.Pages.Facebook;

import com.scripted.web.WebHandlers;
import com.scripted.web.WebWaitHelper;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FacebookPage {

    WebDriver driver;

    public FacebookPage(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.driver =driver;
    }

    @FindBy(name = "email")
    private WebElement fbUsername;
    @FindBy(id = "pass")
    private WebElement fbPassword;
    @FindBy(name = "login")
    private WebElement loginButton;

    public void enterUsername(String username){
        WebWaitHelper.waitForElement(fbUsername);
        WebHandlers.enterText(fbUsername, username);
    }

    public void enterPassword(String password){
        WebWaitHelper.waitForElement(fbPassword);
        WebHandlers.enterText(fbPassword, password);
    }

    public void clickLogInButton(){
        WebWaitHelper.waitForElement(loginButton);
        WebHandlers.click(loginButton);
    }

    public void verifyLoginUnSuccess(){
        Assert.assertTrue(driver.findElements(By.xpath("//*[@class='_4-i2 _pig _9kpl _50f4']")).size()>0);
    }


}
