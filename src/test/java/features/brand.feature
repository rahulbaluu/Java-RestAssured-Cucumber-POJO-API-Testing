Feature: Brand API

  Scenario: Get all brand list
    Given Send GET request to brand list
    When Should receive the list of all brand
    Then The status code should 200

  Scenario: Update the brand list
    Given Send PUT request to brand list
    When Should receive the error message
    Then The status message for PUT brand is received
