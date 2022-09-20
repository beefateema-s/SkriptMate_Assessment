package com.scripted.runners;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.scripted.configurations.SkriptmateConfigurations;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "Features/Webservices",
        plugin = {"json:target/cucumber.json", "html:target/cucumber.html",
                "io.qameta.allure.cucumber4jvm.AllureCucumber4Jvm"},
        glue = {
                "com.scripted.apistepdefs"},
        monochrome = true,
        tags = "@apiTest"
)
public class APITestRunner extends AbstractTestNGCucumberTests {
    /*Enable the below code only if you are not using SkriptMateRunner
    Also provide the configuration details in SkriptMateConfigurations\SkriptMateConfig.Properties*/
    @BeforeSuite
    public void setup() {
        SkriptmateConfigurations.beforeSuite();
    }

    @AfterSuite
    public void teardown() {
        SkriptmateConfigurations.afterSuite();
    }
}
