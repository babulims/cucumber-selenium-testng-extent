Feature: Retrieval of Jacket details and validation of video feeds and news feeds

  @StoreJacketScenario
  Scenario: Filter all Jackets and store titles and prices
    Given CP home page is loaded
    When Navigate to Shop Menu >> Men's
    Then Find all jackets from all paginated pages and validate the total jacket count is greater than 410
    And Store each Jacket price & title to a text file and attach the text file to the report

  @VideoFeeds
  Scenario: Validation of count of video feeds and news feeds older than 3 days
    Given CP home page is loaded
    When Hover over ... menu item and navigate to News & Features page
    Then Count and validate total number of video feeds are more than 20
    And Count and validate the video feeds which are more than 2 days old are more than 10

