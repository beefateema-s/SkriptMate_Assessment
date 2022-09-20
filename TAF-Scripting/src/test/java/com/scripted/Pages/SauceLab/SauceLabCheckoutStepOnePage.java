package com.scripted.Pages.SauceLab;

import com.scripted.web.WebHandlers;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SauceLabCheckoutStepOnePage {
    WebDriver driver;

    public SauceLabCheckoutStepOnePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(id = "continue")
    private WebElement input_Continue;
    @FindBy(id = "postal-code")
    private WebElement input_ZipPostalCode;
    @FindBy(id = "react-burger-menu-btn")
    private WebElement button_burgermenubtn;
    @FindBy(id = "first-name")
    private WebElement input_FirstName;
    @FindBy(xpath = "//a[@class='shopping_cart_link']")
    private WebElement a_vQ1;
    @FindBy(id = "last-name")
    private WebElement input_LastName;
    @FindBy(id = "cancel")
    private WebElement button_cancel;

    public void enterInformation(String firstName, String lastName, String zipcode) {
        WebHandlers.enterText(input_FirstName, firstName);
        WebHandlers.enterText(input_LastName, lastName);
        WebHandlers.enterText(input_ZipPostalCode, zipcode);
    }

    public void selectContinueButton() {
        WebHandlers.click(input_Continue);
    }
}
