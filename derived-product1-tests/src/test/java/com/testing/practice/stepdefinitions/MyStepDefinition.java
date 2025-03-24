package com.testing.practice.stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

public class MyStepDefinition {

    @Given("User is on some page")
    public void user_is_on_some_page() {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("In Given");
    }

    @When("user enters some {string} in a textbox")
    public void user_enters_some_in_a_textbox(String string) {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("In When with argument: " + string);
    }

    @Then("validate all values are entered")
    public void validate_all_values_are_entered() {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("In Then");
    }

    @When("user enters some {int} in a textbox")
    public void user_enters_some_in_a_textbox(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("In When with value: " + int1);
    }

    @When("user enters some value in a textbox")
    public void user_enters_some_value_in_a_textbox(DataTable dataTable) {
        List<String> values = dataTable.values();
        values.forEach(System.out::println);
    }
}
