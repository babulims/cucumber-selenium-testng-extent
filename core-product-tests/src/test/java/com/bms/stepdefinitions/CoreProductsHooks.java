package com.bms.stepdefinitions;

import com.bms.pageobjects.CoreProductHomePage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CoreProductsHooks {
    private static final Logger logger = LoggerFactory.getLogger(CoreProductsHooks.class);
    private CoreProductHomePage coreProductHomePage;

    /**
     * method to return the page instance in order to not create again in step definitions
     *
     * @return CoreProductHomePage instance
     */
    public CoreProductHomePage getCoreProductHomePage() {
        return coreProductHomePage;
    }

    @Before
    public void setUp() throws IOException {
        logger.info("Setting Up Test Contexts, Page Objects, Elements, Drivers");
        String CORE_PAGE_URL_PATH = "/warriors";
        coreProductHomePage = new CoreProductHomePage(CORE_PAGE_URL_PATH);
    }

    @After("@StoreJacketScenario")
    public void afterScenario(Scenario scenario) throws IOException {
        logger.info("Executing After Scenario for Jackets");
        byte[] fileContent = FileUtils.readFileToByteArray(new File("src/test/resources/jacket_details.txt"));
        logger.info("FileContent Check : {}", new String(fileContent, StandardCharsets.UTF_8));
        logger.info("Scenario name : {}", scenario.getName());
        scenario.attach(fileContent, "text/plain", "jackets_details.txt");
    }

    @After
    public void tearDown() {
        logger.info("Generic After method for all scenarios");
        coreProductHomePage.endDriverSession();
    }
}
