Feature: Automate OrangeHRM Page

  Scenario: User successfully login with valid credentials
    Given User navigate to OrangeHRM Login screen
    And User enters Username and Password
    When User clicks on Login button
    Then User should be successfully logged into OrangeHRM and Dashbord text should be displayed
    And Clicks on Logout button
    And Verify that successfully the user is logged out

  @Login
  Scenario: Successfully update personal details for an employee
    Given the user is logged in and navigates to the personal details page for employee
    And the user updates the following details:
      | FirstName           | John       |
      | MiddleName          | M          |
      | LastName            | Doe        |
      | EmployeeId          |     401410 |
      | OtherId             |      23392 |
      | DriverLicenceNumber | NCCLT12345 |
      | LicenseExpiryDate   | 10/10/2026 |
      | Nationality         | American   |
      | MaritalStatus       | Single     |
      | DateofBirth         | 01/26/1998 |
      | Gender              | Male       |
    And the user clicks the first Save button
    #And the user updates the following details:
      #| BloodType  | AB-   |
      #| Test_Field |  1234 |
    #And the user clicks the second Save button
    #And the user attach the file and add comment
    #And the user clicks the third Save button
    #Then the updated personal details should be displayed correctly on the personal details page
