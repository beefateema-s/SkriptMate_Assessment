package com.scripted.runners;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.scripted.configurations.SkriptmateConfigurations;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "Features/Web/",
        plugin = {"json:target/cucumber.json",
                "io.qameta.allure.cucumber4jvm.AllureCucumber4Jvm"},
        glue = {"com.scripted.WebStepDef"},
        monochrome = true,
        tags = {"@webTest"})

public class WebTestRunner extends AbstractTestNGCucumberTests {
    @BeforeSuite
    public void setup() {
        SkriptmateConfigurations.beforeSuite();
    }

    @AfterSuite
    public void teardown() {
        SkriptmateConfigurations.afterSuite();
    }
}
