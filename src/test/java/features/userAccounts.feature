Feature: User Account

  Scenario: Post to Create/Register user Account
    Given Read the user registration data from
      | Sheet_name          | rowNum |
      | Registration_Detail | 1      |
    When Send post request to create account
    Then Verify user is created successfully

  Scenario: Update the user account
    Given Read the user credentials from
      | Sheet_name                  | rowNum |
      | Updated_Registration_Detail | 2      |
    When Send put request to update account
    Then Verify account is updated successfully

  Scenario: Get user account detail by email
    Given Read the user credentials from
      | Sheet_name          | rowNum |
      | Registration_Detail | 1      |
    When Send GET request to get user detail
    Then Verify user detail is retrieved successfully


  Scenario: Delete a registered user account
    Given Read the user credentials from
      | Sheet_name          | rowNum |
      | Registration_Detail | 1      |
    When Send delete request to delete account
    Then Verify account is deleted successfully

