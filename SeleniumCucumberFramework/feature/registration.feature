Feature: User Registration

  @datatable
  Scenario: Successful Register a new user using data table
    Given the user navigate to Register Page
    And the user enters the following details
      | firstName | Peter      |
      | lastName  | Parker     |
      | telePhone | 1234567890 |
    And the user selects Privacy Policy
    When the user clicks on Continue button
    Then the user should able to the see the confirmation text

  @data-driven
  Scenario Outline: Successful Register a new user through data driven
    Given the user navigate to Register Page
    And the user enters first name as "<firstName>" and last name as "<lastName>"
    And the user selects Privacy Policy
    When the user clicks on Continue button
    Then the user should able to the see the confirmation text

    Examples: 
      | firstName | lastName |
      | Peter     | Parker   |
      | Sam       | Jerold   |
      | Chris     | Evens    |
      | Robert    | Downey   |

  @data-driven @excel
  Scenario Outline: Successful Register a new user through data driven using excel data
    Given the user navigate to Register Page
    And the user enters first name and last name with excel row "<row_index>"
    And the user selects Privacy Policy
    When the user clicks on Continue button
    Then the user should able to the see the confirmation text

    Examples: 
      | row_index |
      |         1 |
      |         2 |
      |         3 |
      |         4 |
      |         5 |
      |         6 |
