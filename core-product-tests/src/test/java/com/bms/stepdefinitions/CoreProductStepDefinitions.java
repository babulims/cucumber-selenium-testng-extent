package com.bms.stepdefinitions;

import com.bms.pageobjects.CoreProductHomePage;
import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.io.IOException;

public class CoreProductStepDefinitions {
    private static final Logger logger = LoggerFactory.getLogger(CoreProductStepDefinitions.class);
    private final CoreProductHomePage coreProductHomePage;

    public CoreProductStepDefinitions(CoreProductsHooks coreProductsHooks) {
        coreProductHomePage = coreProductsHooks.getCoreProductHomePage();
    }

    @Given("CP home page is loaded")
    public void cp_home_page_is_loaded() throws IOException {
        // Load the home page and initialize home page elements
        logger.info("Start of the scenario test");
        //Handle cookies pop up
        logger.info("Handling Cookies");
        coreProductHomePage.handleCookieAdsPopUps();
        //Handle Ticket access ad
        logger.info("Ticket Access Popup");
        coreProductHomePage.handleTicketAccessPopUp();
    }

    @When("Navigate to Shop Menu >> Men's")
    public void navigate_to_shop_menu_men_s() {
        logger.info("hoverOverShopMenu");
        //Hover over Shop menu
        coreProductHomePage.hoverOverShopMenu();
        //Click on Jersey Options from the submenu
        coreProductHomePage.clickOnMensFromSubMenu();
    }

    @Then("Find all jackets from all paginated pages and validate the total jacket count is greater than {int}")
    public void find_all_jackets_from_all_paginated_pages(int expectedJacketCount) throws InterruptedException {
        Thread.sleep(15000);
        //Switch to new window
        coreProductHomePage.switchToNewWindow();
        //Filter all jackets
        coreProductHomePage.filterJackets();

        int jacketCount = coreProductHomePage.getJacketsCount();
        logger.info("Jackets Count : {}", jacketCount);
        Assert.assertTrue(jacketCount > expectedJacketCount);
    }

    @Then("Store each Jacket price & title to a text file and attach the text file to the report")
    public void store_each_jacket_price_title_to_a_text_file_and_attach_the_text_file_to_the_report() throws JsonProcessingException {
        //Find All jackets from paginated pages and store
        coreProductHomePage.storeAllJacketDetails();
    }

    @When("Hover over ... menu item and navigate to News & Features page")
    public void hover_over_menu_item_and_navigate_to_page() {
        coreProductHomePage.hoverOverDotMenu();
        logger.info("Hovering over menu");
        coreProductHomePage.clickOnNewsAndFeatures();
        logger.info("Clicked on News and Features");
    }

    @Then("Count and validate total number of video feeds are more than {int}")
    public void count_total_number_of_video_feeds_and_validate_against_expected_test_data(int expectedNoOfVideos) {
        int videoFeeds = coreProductHomePage.countVideoFeeds();
        logger.info("Number of Video Feeds : {}", videoFeeds);
        Assert.assertTrue(videoFeeds > expectedNoOfVideos);
    }

    @Then("Count and validate the video feeds which are more than {int} days old are more than {int}")
    public void count_the_news_feeds_which_are_more_than_days_old_and_validate_against_expected_test_data(
            int videoAge, int expectedCount) {
        int filteredVideoFeeds = coreProductHomePage.countVideoFeeds(videoAge);
        logger.info("Number of videos which are more than {} days : {}", videoAge, filteredVideoFeeds);
        Assert.assertTrue(filteredVideoFeeds > expectedCount);
    }
}
