package com.bms.genericutils;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

/**
 * This is the common class having all the actions to be performed for any kind of web element
 */
public class WebElementActions {
    private static final Logger logger = LoggerFactory.getLogger(WebElementActions.class);

    /**
     * Method for waiting for web element to be visible.
     *
     * @param driver web driver
     * @param element web element
     * @param secondsToWait time to wait in seconds
     */
    public void waitForElementToBeVisible(WebDriver driver, WebElement element, int secondsToWait) {

        FluentWait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(secondsToWait))
                    .pollingEvery(Duration.ofSeconds(2))
                    .ignoring(NoSuchElementException.class);
        logger.info("waiting for element to be visible");
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Method for waiting for list of web elements to be visible.
     *
     * @param driver web driver
     * @param elements web elements
     * @param secondsToWait time to wait in seconds
     */
    public void waitForAllElementsToBeVisible(WebDriver driver, List<WebElement> elements, int secondsToWait) {

        FluentWait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(secondsToWait))
                .pollingEvery(Duration.ofSeconds(2))
                .ignoring(NoSuchElementException.class);
        logger.info("waiting for all elements to be visible");
        wait.until(ExpectedConditions.visibilityOfAllElements(elements));
    }

    /**
     * Method for waiting for web element to be clickable.
     *
     * @param driver web driver
     * @param element web element
     * @param secondsToWait time to wait in seconds
     */
    public void waitForElementToBeClickable(WebDriver driver, WebElement element, int secondsToWait) {

        FluentWait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(secondsToWait))
                .pollingEvery(Duration.ofSeconds(2))
                .ignoring(NoSuchElementException.class);
        logger.info("waiting for element to be clickable");
        WebElement elementPostWait = wait.until(ExpectedConditions.elementToBeClickable(element));
        // Ensure the page has finished loading or performing transitions
        wait.until((WebDriver d) ->
                ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
    }

    /**
     * Method for waiting for web element to be visible.
     *
     * @param element web element
     */
    public void clickOnWithRetry(WebElement element, int retryCount) {
        while(retryCount >= 0) {
            try {
                element.click();
                break;
            } catch (ElementClickInterceptedException e) {
                logger.warn("Element click interrupted, retrying....");
                --retryCount;
                try {
                    // Wait for a short duration before retrying
                    Thread.sleep(3000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            } catch (ElementNotInteractableException e) {
                logger.warn("Element Not interactable, retrying....");
                --retryCount;
                try {
                    // Wait for a short duration before retrying
                    Thread.sleep(3000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    /**
     * Method for waiting for web element to be visible.
     *
     * @param driver web driver
     * @param element web element
     */
    public void hoverOver(WebDriver driver, WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }


    /**
     * custom method for handling cookies. Ignore if cookies pop up does not appear.
     *
     * @param driver WebDriver
     * @param cookie Cookie Web Element
     * @param timeout timeout in seconds
     */
    public void handleCookies(WebDriver driver, WebElement cookie, long timeout) {
        try {
            FluentWait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(timeout))
                    .pollingEvery(Duration.ofSeconds(2))
                    .ignoring(NoSuchElementException.class);
            logger.info("waiting for cookie pop up to be clickable");
            wait.until(ExpectedConditions.elementToBeClickable(cookie));
            cookie.click();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Switch to the new tab/window opened.
     *
     * @param driver WebDriver
     */
    public void switchToNewTabOpened(WebDriver driver) {
        String currentWindow = driver.getWindowHandle();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        Optional<String> newWindow = driver.getWindowHandles().stream()
                .filter(s -> !s.equalsIgnoreCase(currentWindow))
                .findFirst();
        driver.switchTo().window(newWindow.orElseThrow());
        logger.info("Switched to new window");
    }

    /**
     * Method for returning text of an element.
     *
     * @param element WebElement
     * @return text.
     */
    public String getElementText(WebElement element) {
        return element.getText();
    }

    /**
     * WebElement.findElementByCssSelector
     *
     * @param element WebElement
     * @param cssSelector selector string
     * @return WebElement
     */
    public WebElement findElementByCssSelector(WebElement element, String cssSelector) {
        return element.findElement(By.cssSelector(cssSelector));
    }

    /**
     * WebDriver.findElementByCssSelector
     *
     * @param driver WebDriver
     * @param cssSelector Css Selector
     * @return WebElement
     */
    public List<WebElement> findAllElementsByCssSelector(WebDriver driver, String cssSelector) {
        return driver.findElements(By.cssSelector(cssSelector));
    }
}
