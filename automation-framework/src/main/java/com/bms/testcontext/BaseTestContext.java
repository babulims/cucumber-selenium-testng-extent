package com.bms.testcontext;

import com.bms.genericutils.TestDataConstants;
import com.bms.genericutils.WebElementActions;
import com.bms.testconfigs.TestDataConfigResource;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This is the common class which will initialize all test contexts for the entire automation framework.
 */
public class BaseTestContext {

    protected WebDriver driver;
    protected TestDataConfigResource testDataConfigResource;
    protected WebElementActions webElementActions;

    /**
     * Constructor initializer
     *
     * @param pageUrlPath actual page URL path
     * @throws IOException I/O exception.
     */
    public BaseTestContext(String pageUrlPath) throws IOException {;
        // Initializing the test data config resource from properties file and run time variable
        testDataConfigResource = initializeTestDataConfigResource();
        //Initialize driver instance
        driver = initializeWebDriver(testDataConfigResource.getBrowserType(),
                testDataConfigResource.getDriversBasePath());
        //Launch respective page url with driver instance
        driver.get(testDataConfigResource.getTestUrl() + pageUrlPath);
        //initialize page elements using the above driver
        PageFactory.initElements(driver, this);
        //initialize the common WebElement actions class
        this.webElementActions = new WebElementActions();
    }

    /**
     * Returns an instance of test config resource.
     *
     * @return Test data config resource object.
     */
    private TestDataConfigResource initializeTestDataConfigResource() throws IOException {
        if (testDataConfigResource != null) {
            return testDataConfigResource;
        }

        try (InputStream fis = getClass().getClassLoader().getResourceAsStream(TestDataConstants.APPLICATION_PROPERTIES_FILE_PATH)) {
            Properties properties = new Properties();
            properties.load(fis);
            String runTimeBrowserType =  System.getProperty(TestDataConstants.SYS_BROWSER_TYPE);
            String browserType = runTimeBrowserType != null ?
                    runTimeBrowserType : properties.getProperty(TestDataConstants.BROWSER_TYPE);
            return new TestDataConfigResource(
                    properties.getProperty(TestDataConstants.TEST_BASE_URL),
                    properties.getProperty(TestDataConstants.DRIVERS_BASE_PATH), browserType);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Initialize and return the webdriver instance required as per the parameters.
     *
     * @param browserType type of the browser
     * @param driversBasePath base file path of the driver.exe file
     * @return WebDriver instance.
     */
    private WebDriver initializeWebDriver(String browserType, String driversBasePath) {
        if (driver != null) {
            return driver;
        }

        return switch (browserType.toLowerCase()) {
            case TestDataConstants.CHROME -> {
                System.setProperty(TestDataConstants.CHROME_PROPERTY_KEY,
                        driversBasePath + TestDataConstants.CHROME_DRIVER_NAME);
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--disable-popup-blocking");
                options.addArguments("--start-maximized");
                yield new ChromeDriver(options);
            }

            case TestDataConstants.EDGE -> {
                System.setProperty(TestDataConstants.EDGE_PROPERTY_KEY,
                        driversBasePath + TestDataConstants.EDGE_DRIVER_NAME);
                EdgeOptions options = new EdgeOptions();
                options.addArguments("--disable-popup-blocking");
                options.addArguments("--start-maximized");
                yield new EdgeDriver(options);
            }

            case TestDataConstants.FIREFOX -> {
                System.setProperty(TestDataConstants.GECKO_PROPERTY_KEY,
                        driversBasePath + TestDataConstants.GECKO_DRIVER_NAME);
                yield new FirefoxDriver();
            }

            default -> throw new UnsupportedCommandException("Unsupported Browser Type");
        };
    }
}
