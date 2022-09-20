package com.scripted.Pages.SauceLab;

import com.scripted.web.WebHandlers;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class SauceLabProductsPage {
    WebDriver driver;
    String selectedFilter;

    public SauceLabProductsPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = "//div[normalize-space()='Sauce Labs Backpack']")
    private WebElement div_TySauceLabsBack;
    @FindBy(id = "add-to-cart-sauce-labs-backpack")
    private WebElement button_addtocartsaucel;
    @FindBy(id = "remove-sauce-labs-backpack")
    private WebElement button_removesaucelabs;
    @FindBy(xpath = "//div[normalize-space()='$29.99']")
    private WebElement div_gN2999;

    @FindBy(xpath = "//div[normalize-space()='Test.allTheThings() T-Shirt (Red)']")
    private WebElement div_ISTestallTheThi;
    @FindBy(id = "add-to-cart-test.allthethings()-t-shirt-(red)")
    private WebElement button_addtocarttestal;
    @FindBy(id = "remove-test.allthethings()-t-shirt-(red)")
    private WebElement button_removetestallth;

    @FindBy(xpath = "//div[normalize-space()='Sauce Labs Bolt T-Shirt']")
    private WebElement div_IJSauceLabsBolt;
    @FindBy(xpath = "//div[normalize-space()='Sauce Labs Fleece Jacket']")
    private WebElement div_MvSauceLabsFlee;
    @FindBy(xpath = "//div[normalize-space()='Sauce Labs Bike Light']")
    private WebElement div_bsSauceLabsBike;
    @FindBy(xpath = "//div[normalize-space()='Sauce Labs Onesie']")
    private WebElement div_iHSauceLabsOnes;

    @FindBy(css = "div[class=\"inventory_item_description\"]")
    private List<WebElement> inventoryDescription;

    @FindBy(xpath = "//a[@class='shopping_cart_link']")
    private WebElement a_CqQIH;
    @FindBy(xpath = "//span[@class='select_container']")
    private WebElement span_goNameAtoZNameA;
    @FindBy(xpath = "//select[@class='product_sort_container']")
    private WebElement select_ISNameAtoZNameZ;
    @FindBy(css = "select[data-test='product_sort_container'] option")
    private WebElement dropDownFilterMenu;
    @FindBy(css = "span[class=\"active_option\"]")
    private WebElement activeDropDownFilter;
    @FindBy(css = "option[value='az']")
    private WebElement NameAtoZ;
    @FindBy(css = "option[value='za']")
    private WebElement NameZtoA;
    @FindBy(css = "option[value='lohi']")
    private WebElement PriceLowToHigh;
    @FindBy(css = "option[value='hilo']")
    private WebElement PriceHighToLow;

    @FindBy(id = "react-burger-cross-btn")
    private WebElement button_urgercrossbtn;
    @FindBy(id = "react-burger-menu-btn")
    private WebElement button_burgermenubtn;

    public List<WebElement> getInventoryDescription() {
        return inventoryDescription;
    }

    public void clickOnShoppingCart() {
        WebHandlers.click(a_CqQIH);
    }

    public void selectFilter(String filter) {
        WebHandlers.click(select_ISNameAtoZNameZ);
        WebHandlers.dropDownSetByVal(select_ISNameAtoZNameZ, filter); //Selection by Value
        System.out.println("Selected filter: " + filter);
        selectedFilter = filter;
    }

    public void validateSelectedFilter() {
        WebHandlers.compareText(activeDropDownFilter.getText().toLowerCase(), selectedFilter.toLowerCase());
    }
//    public void selectItems(String product) { //old code 😢
//        for (WebElement item : inventoryDescription) {
//            WebElement itemName = item.findElement(By.cssSelector(" div[class=\"inventory_item_name\"]"));
//            if (itemName.getText().equals(product)) {
//                WebElement addToCartButton = item.findElement(By.cssSelector(" button"));
//                WebHandlers.click(addToCartButton);
//                System.out.println("Successfully added " + product + " item to cart");
//                WebElement itemPrice = item.findElement(By.cssSelector(" div[class=\"inventory_item_price\"]"));
//
//            }
//        }
//    }
}
