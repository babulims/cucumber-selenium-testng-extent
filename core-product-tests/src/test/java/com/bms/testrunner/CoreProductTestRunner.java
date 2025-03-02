package com.bms.testrunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

@CucumberOptions(features = "src/test/java/com/bms/features",
glue = "com.bms.stepdefinitions", monochrome = true,
plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"})
public class CoreProductTestRunner extends AbstractTestNGCucumberTests {

    @BeforeClass
    @Parameters({"tags"})
    public void setUp(String tags) {
        //Setting tags dynamically during run time
        System.setProperty("cucumber.filter.tags", tags);
        System.out.println("Before Class in Runner file : " + tags);
    }

/*    @Override
    @DataProvider(parallel=true)
    public Object[][] scenarios() {
        return super.scenarios();
    }*/
}
