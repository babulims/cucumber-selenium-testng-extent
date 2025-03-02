package com.bms.pageobjects;

import com.bms.pageobjects.locators.CoreProductPageLocators;
import com.bms.testcontext.BaseTestContext;
import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CoreProductHomePage extends BaseTestContext {
    private static final Logger logger = LoggerFactory.getLogger(CoreProductHomePage.class);

    /**
     * Constructor initializer for Home page
     *
     * @param pageUrlPath actual page URL path
     * @throws IOException I/O exception.
     */
    public CoreProductHomePage(String pageUrlPath) throws IOException {
        super(pageUrlPath);
    }

    @FindBy(xpath = CoreProductPageLocators.SHOP_MENU)
    private WebElement shopMenu;

    @FindBy(xpath = CoreProductPageLocators.MENS)
    private WebElement mensSubmenu;

    @FindBy(id = CoreProductPageLocators.COOKIE_ACCEPT_BUTTON)
    private WebElement cookie;

    @FindBy(xpath = CoreProductPageLocators.TICKET_ACCESS_CLOSE)
    private WebElement ticketAccess;

    @FindBy(css = CoreProductPageLocators.JACKETS_FILTER)
    private WebElement jacketsFilter;

    @FindBy(css = CoreProductPageLocators.JACKETS_COUNT)
    private WebElement jacketsCount;

    @FindBy(css = CoreProductPageLocators.DOT_MENU)
    private WebElement dotMenu;

    @FindBy(xpath = CoreProductPageLocators.NEWS_FEATURES)
    private WebElement newsFeatures;

    @FindBy(css = CoreProductPageLocators.FEATURED_VIDEO)
    private WebElement featuredVideo;

    @FindBy(xpath = CoreProductPageLocators.VIDEO_AGE)
    private List<WebElement> allVideos;

    public int countVideoFeeds() {
        webElementActions.waitForAllElementsToBeVisible(driver, allVideos, 20);
        return featuredVideo !=null ? allVideos.size() + 1 : allVideos.size();
    }

    public int countVideoFeeds(int videoAge) {
        webElementActions.waitForAllElementsToBeVisible(driver, allVideos, 20);
        List<Integer> filteredVideos = allVideos.stream()
                .filter(element -> element.getText().endsWith("d"))
                .map(element -> Integer.parseInt(element.getText().replaceAll("[^0-9]", "")))
                .filter(integer -> integer >= videoAge)
                .toList();

        return filteredVideos.size();
    }


    public void hoverOverDotMenu() {
        webElementActions.waitForElementToBeVisible(driver, dotMenu, 30);
        webElementActions.hoverOver(driver, dotMenu);
    }

    public void clickOnNewsAndFeatures() {
        webElementActions.waitForElementToBeClickable(driver, newsFeatures, 30);
        webElementActions.hoverOver(driver, newsFeatures);
        webElementActions.clickOnWithRetry(newsFeatures,1);
    }

    public void hoverOverShopMenu() {
        webElementActions.waitForElementToBeVisible(driver, shopMenu, 30);
        webElementActions.hoverOver(driver, shopMenu);
    }

    public void clickOnMensFromSubMenu() {
        webElementActions.waitForElementToBeClickable(driver, mensSubmenu, 30);
        webElementActions.clickOnWithRetry(mensSubmenu, 1);
    }

    public void handleCookieAdsPopUps() {
        webElementActions.handleCookies(driver, cookie, 30);
    }

    public void handleTicketAccessPopUp() {
        webElementActions.waitForElementToBeClickable(driver, ticketAccess, 30);
        webElementActions.clickOnWithRetry(ticketAccess, 1);
    }

    public void switchToNewWindow() {
        webElementActions.switchToNewTabOpened(driver);
    }

    public void filterJackets() {
        webElementActions.waitForElementToBeClickable(driver, jacketsFilter, 20);
        webElementActions.clickOnWithRetry(jacketsFilter, 1);
    }

    public int getJacketsCount() {
        String itemCount = webElementActions.getElementText(jacketsCount);
        Pattern pattern = Pattern.compile("of (\\d+)");
        Matcher matcher = pattern.matcher(itemCount);
        if (matcher.find()) {
            itemCount = matcher.group(1);
        }

        return Integer.parseInt(itemCount);
    }

    public void storeAllJacketDetails() throws JsonProcessingException {
        String jacketDetailsJson = findAllJacketDetailsInJsonFormat();

        try {
            // Write JSON string to the file
            String jacketDetailsFilePath = "src/test/resources/jacket_details.txt";
            Files.write(Paths.get(jacketDetailsFilePath), jacketDetailsJson.getBytes());
            logger.info("JSON written to file successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String findAllJacketDetailsInJsonFormat() throws JsonProcessingException {
        List<Jacket> jacketDetails = new ArrayList<>();

        boolean hasNextPage = true;
        while (hasNextPage) {
            List<WebElement> jacketsElements = webElementActions.findAllElementsByCssSelector(driver,
                    CoreProductPageLocators.JACKET_ELEMENTS);

            webElementActions.waitForAllElementsToBeVisible(driver, jacketsElements, 10);
            jacketsElements.forEach(element -> {
                jacketDetails.add(new Jacket(
                        webElementActions.findElementByCssSelector(element, CoreProductPageLocators.JACKET_TITLE)
                                .getText(),
                        webElementActions.findElementByCssSelector(element, CoreProductPageLocators.JACKET_PRICE)
                                .getText()
                ));
            });

            List<WebElement> nextPageButtons = webElementActions.findAllElementsByCssSelector(driver,
                    CoreProductPageLocators.PAGINATION_NEXT);
            if (!nextPageButtons.isEmpty() && !nextPageButtons.get(0)
                    .getDomAttribute("aria-disabled").equalsIgnoreCase("true")) {
                webElementActions.clickOnWithRetry(nextPageButtons.get(0), 1);
            } else {
                hasNextPage = false;
            }
        }

        return  new ObjectMapper().writeValueAsString(jacketDetails);
    }

    /**
     * Method for shutting down driver session.
     */
    public void endDriverSession() {
        driver.quit();
    }

    /**
     * Jackets DTO for storing title and price
     */
    static class Jacket {
        String title;
        String price;

        public Jacket(String title, String price) {
            this.title = title;
            this.price = price;
        }

        public String getTitle() {
            return title;
        }

        public String getPrice() {
            return price;
        }
    }

}
