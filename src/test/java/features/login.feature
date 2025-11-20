Feature: Login API

  Scenario Outline: POST to verify login with valid details
    Given Send the post request with the "<email>" and "<password>" parameters
    When Should receive this "<message>" and 200
    Then Verify the response code is 200
  Examples:
    | email         | password | message      |
    | 178@gmail.com | 178      | User exists! |

  Scenario Outline: POST without email to verify error
    Given Send the post request with the "<password>" parameters
    When Should receive this error "<message>" and 400
    Then Verify the response code is 200
  Examples:
    | password | message                                                              |
    | 178      | Bad request, email or password parameter is missing in POST request. |

  Scenario Outline: DELETE to verify login
    Given Send the delete request to delete the user account
    When Should receive this "<message>" and 405
    Then Verify the response code is 200
  Examples:
    | message                               |
    | This request method is not supported. |

  Scenario Outline: POST to verify login with valid details
    Given Send the post request with the "<email>" and "<password>" parameters
    When Should receive this "<message>" and 404
    Then Verify the response code is 200
    Examples:
      | email         | password | message         |
      | 179@gmail.com | 179      | User not found! |