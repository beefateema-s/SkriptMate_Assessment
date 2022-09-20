package com.scripted.Pages.SauceLab;

import com.scripted.web.WebHandlers;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SauceLabCheckoutCompletePage {
    WebDriver driver;

    public SauceLabCheckoutCompletePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = "//h2[normalize-space()='THANK YOU FOR YOUR ORDER']")
    private WebElement h2_cBTHANKYOUFORYO;
    @FindBy(id = "back-to-products")
    private WebElement button_backtoproducts;

    public void verifySuccessfulOrderMessage() {
        WebHandlers.verifyText(h2_cBTHANKYOUFORYO, "THANK YOU FOR YOUR ORDER");
    }
}
