package com.scripted.WebStepDef.Common;

import com.scripted.Pages.Common.LoginPage;
import com.scripted.reporting.AllureListener;
import com.scripted.selfhealing.SMWebHealer;
import com.scripted.web.BrowserDriver;
import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.Listeners;


@Listeners({AllureListener.class})
public class CommonStepdef extends BrowserDriver {

    WebDriver driver;
    LoginPage login;
    //Accessibility access = new Accessibility(getDriver());
    ThreadLocal<String> testCaseName = new ThreadLocal<>();

    @Before("@Web")
    public void driverSetup() {
        getCuPalDriver();
        driver = getDriver();
        login = new LoginPage(driver);
    }

    @Given("self healing started")
    public void beforeMethod(ITestResult result) {
        testCaseName.set(result.getMethod().getMethodName());
        SMWebHealer sm = new
                SMWebHealer();
        sm.setTstCseNmeTstNG(testCaseName.get());
    }

    @Given("I launched facebook application")
    public void I_launched_facebook_application(){
        login.launchUrl();
    }

    @Given("I launched sauce lab application")
    public void I_launched_sauce_lab_application(){
        login.launchSauceUrl();
    }

    @After
    public void afterScenario(Scenario scenario) {
        if
        (scenario.isFailed()) {
            TakesScreenshot scrShot = ((TakesScreenshot)
                    driver);
            byte[] screenshot =
                    scrShot.getScreenshotAs(OutputType.BYTES);
            scenario.embed(screenshot,
                    "image/png");
        }
        //access.generateOverallAccessibilityReport();
        driver.quit();
    }
}
