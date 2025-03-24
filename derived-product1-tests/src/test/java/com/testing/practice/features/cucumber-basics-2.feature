Feature: This is a basic cucumber practice playground

 @StringOutline
 Scenario Outline: Testing with outline Strings Feature2
   Given User is on some page
   When user enters some "<value>" in a textbox
   Then validate all values are entered

   Examples:
   | value |
   | My Apple |
   | My Oranges |

  @IntegerOutline
  Scenario Outline: Testing with outline Integers Feature2
    Given User is on some page
    When user enters some <value> in a textbox
    Then validate all values are entered

    Examples:
      | value |
      | 10 |
      | 20 |

  @StringMultiDataStep
  Scenario: Testing with Strings Feature2
    Given User is on some page
    When user enters some value in a textbox
    | Apples |
    | Oranges |
    Then validate all values are entered

  @IntegerMultiDataStep
  Scenario: Testing with Integers Feature2
    Given User is on some page
    When user enters some value in a textbox
      | 20 |
      | 30 |
    Then validate all values are entered