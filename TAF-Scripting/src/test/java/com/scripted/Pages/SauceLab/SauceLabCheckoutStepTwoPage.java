package com.scripted.Pages.SauceLab;

import com.scripted.web.WebHandlers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static java.lang.Float.parseFloat;

public class SauceLabCheckoutStepTwoPage {
    WebDriver driver;
    float calculatedPrice = 0;

    public SauceLabCheckoutStepTwoPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(css = ".summary_subtotal_label")
    private WebElement div_vdItemtotal2999;
    @FindBy(css = ".inventory_item_name")
    private WebElement div_qlSauceLabsBack;
    @FindBy(css = "button[data-test='finish']")
    private WebElement button_finish;
    @FindBy(xpath = "//div[@class='summary_total_label']")
    private WebElement div_TpTotal3239;
    @FindBy(name = "summary_value_label")
    private WebElement div_KfFREEPONYEXPRE;
    @FindBy(css = ".inventory_item_price")
    private WebElement div_da2999;
    @FindBy(css = ".summary_tax_label")
    private WebElement div_jmTax240;
    @FindBy(id = "cancel")
    private WebElement button_cancel;

    @FindBy(css = "div[class=\"summary_total_label\"]")
    private WebElement total_label;
    @FindBy(css = "div[class=\"cart_item\"]")
    private List<WebElement> cartList;

    /* Loop through summary cart list to calculate subtotal price */
    public void validateSubTotalPrice() {
        for (WebElement item : cartList) {
            WebElement itemPrice = item.findElement(By.cssSelector(" div[class=\"inventory_item_price\"]"));
            calculatedPrice += parseFloat(itemPrice.getText().replace("$", ""));
        }

        String[] getSubtotalPriceText = getText(div_vdItemtotal2999);
        String SubtotalPriceText = getSubtotalPriceText[2].replace("$", "");
        System.out.println("Calculated Subtotal: " + calculatedPrice + " and Subtotal text: " + SubtotalPriceText);
        WebHandlers.compareText(String.valueOf(calculatedPrice), SubtotalPriceText);
    }

    /* - Add tax to subtotal to get the total
     * - Compare calculated total and total text in the site */
    public void validateTotalPrice() {

        String[] getTaxText = getText(div_jmTax240);
        float taxValue = parseFloat(getTaxText[1].replace("$", ""));
        calculatedPrice += taxValue;

        String[] getTotalPriceText = getText(total_label);
        String totalPriceText = getTotalPriceText[1].replace("$", "");
        System.out.println("Calculated Total: " + String.format("%.2f", calculatedPrice) + " and Total text: " + totalPriceText);
        WebHandlers.compareText(String.format("%.2f", calculatedPrice), totalPriceText);
    }

    /* split string into list*/
    public String[] getText(WebElement element) {
        String elementText = element.getText();
        return elementText.split(" ");
    }

    public void clickOnFinish() {
        WebHandlers.click(button_finish);
    }
}
