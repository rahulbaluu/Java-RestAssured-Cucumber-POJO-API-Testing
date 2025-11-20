Feature: Product API

  Scenario: Get all products list
    Given Send GET request to products list
    When Should receive the list of all products
    Then The status code should be 200

  Scenario: POST to all products list
    Given Send the POST to products list
    When The status code should be 200
    Then The status message we received

  Scenario Outline: POST to search product
    Given Send the POST to Search product with keyword "<dress>"
    When Should receive the list of searched products
    Then The status code should be 200
    Examples:
      | dress  |
      | tshirt |

  Scenario: POST to search product without search product
    Given Send the POST request without search parameter
    When Should receive the error message for missing search parameter
    Then The status code should be 400
