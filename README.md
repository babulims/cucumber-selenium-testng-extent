# UI Test Automation Framework 

## Overview

The **cucumber-selenium-testng-extent** project is a multi-module Maven-based test automation framework designed to facilitate automated testing for various web pages using **Java**, **Selenium**, **Cucumber**, **TestNG** and **Maven** The framework supports UI testing with extensive reusability and scalability across different product lines.

## Project Structure

The framework follows a **multi-module Maven architecture** with the following key modules:

### 1. Root Maven Project (`bms-veeva-tech-assessment`)

The root project serves as the parent module, driving all common Maven dependencies and plugins. The `pom.xml` in this module manages:

- Common dependency versions
- Plugin configurations
- Module aggregation

**It does not contain any test execution logic.**

### 2. **automation-framework**

This module provides the core utilities and reusable components for test execution across all products.

#### Package Structure

```
main
└─ java
   └─ com.bms.genericutils
       ├─ TestDataConstants.java        # Test data constant variables
       ├─ WebElementActions.java        # Wrapper methods for Selenium WebDriver actions
   └─ com.bms.testconfigs
       └─ TestDataConfigResource.java   # Test Data config dto
   └─ com.bms.testcontext
       └─ BaseTestContext.java          # Initializes drivers, configs, and page elements

    resources
        └─ application.properties       # Global configuration properties
        └─ extent.properties            # Extent report configurations
```

#### Responsibilities

- Common utilities for data handling
- Web driver actions and waits
- Test context initialization
- Configuration management

### 3. **core-product-tests**

This module contains automated tests for the **Core Product** home page using Cucumber feature files.

#### Package Structure

```
src
└─ test
   └─ java
      └─ com.bms.features
          └─ CoreProductFeatures.feature          # Cucumber feature file for core product
      └─ com.bms.pageobjects
          ├─ CoreProductHomePage.java             # Page Object class for Home Page, contains wrapper methods for webelement actions and pageutils for test validations
          └─ locators
              └─ CoreProductPageLocators.java     # Locators for Home Page elements
          └─ stepdefinitions
              ├─ CoreProductsHooks.java           # Cucumber Hooks for setup/teardown
              └─ CoreProductStepDefinitions.java  # Step definition methods
          └─ testrunner
              └─ CoreProductTestRunner.java       # Cucumber Test Runner with TestNG

      resources
      └─ extent reports                           # Extent HTML Reports
      └─ test data output                         # Output data from test execution
      
└─ testng.xml                           
```

#### Responsibilities

- Define core product feature files
- Implement page object model
- Write step definitions
- Manage test execution and reporting

### 4. **derived-product1-tests**

** No Tests added as of now**

This module follows the same structure as **core-product-tests** but contains tests specific to **Derived Product 1**.

### 5. **derived-product2-tests**

** No Tests added as of now**

This module follows the same structure as **core-product-tests** but contains tests specific to **Derived Product 2**.

## Execution Flow

1. Test execution starts from the **TestNG.xml** file or individual Cucumber runners.
2. The **BaseTestContext** initializes the driver, configuration, and page object contexts.
3. The WebDrivers are initialized dynamically based on browser type.
4. Cucumber features are executed using **Step Definitions** and **Hooks**.
5. Web actions are performed using methods from **WebElementActions**.
6. Test execution results are logged using **ExtentReports**.
7. Test data output is generated for validation and reporting.

## Reporting

The framework uses **ExtentReports** for generating detailed HTML reports, including:

- Test case execution status
- Time taken for execution

## Test Data Management

Test data is stored in **application.properties** and dynamically read at runtime and loaded to the **TestDataConfigResource** Dto.

## Pre-requisites

- JDK 11+
- Maven 3.6+
- ChromeDriver / EdgeDriver (for UI tests)
- TestNG
- Cucumber
- ExtentReports

## How to Run

1. You can run manually from any IDE using **testng.xml** OR CucumberTestRunner.
2. Execute using Maven commands from command line. (You need to have maven installed in the machine to run commands from terminal)

## Conclusion

The **cucumber-selenium-testng-extent** framework provides a scalable, reusable, and maintainable solution for automated testing across multiple product lines. Its modular architecture, combined with Cucumber and TestNG, ensures clean separation of concerns, better code organization, and seamless test execution.

