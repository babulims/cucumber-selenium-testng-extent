package com.bms.testconfigs;

/**
 * This class would contain all the configs which needs to be initialized once before all the test begins.
 */
public class TestDataConfigResource {
    private final String TEST_BASE_URL;
    private final String DRIVERS_BASE_PATH;
    private final String BROWSER_TYPE;

    /**
     * Constructor for test data config resource.
     *
     * @param url base test url
     * @param driverBasePath path to all the drivers folder
     * @param browserType type of browser.
     */
    public TestDataConfigResource(String url, String driverBasePath, String browserType) {
        this.TEST_BASE_URL = url;
        this.DRIVERS_BASE_PATH = driverBasePath;
        this.BROWSER_TYPE = browserType;
    }

    public String getTestUrl() {
        return this.TEST_BASE_URL;
    }

    public String getDriversBasePath() {
        return this.DRIVERS_BASE_PATH;
    }

    public String getBrowserType() {
        return this.BROWSER_TYPE;
    }
}
