@Regression @API
Feature: Generate Redirect URL using hostedCheckOutAPI
  As a merchant
  Inorder to redirect my consumers to payment
  I should be able to generate redirect URL

  Scenario: I should be able to generate redirect url successfully with Valid Inputs
    Given I generate the authorisation headers
      | contentType                     | messageId                            | requestID                            | path                     |
      | application/json; charset=UTF-8 | 6480071e-039d-4dca-a966-4ce3c1bc201b | 1cc6daff-a305-4d7b-94b0-c580fd5ba6b4 | /v1/2706/hostedcheckouts |
    And I set the order details as
      | currencyCode | amount | countryCode | merchantCustomerId |
      | EUR          | 100    | NL          | 103                |
    And I set the hostedCheckoutSpecificInput as
      | variant     | locale |
      | testVariant | en_GB  |
    When I post the  request to hostedCheckOutAPI
    Then I should be have the valid redirect URL


  Scenario: I should get 403 when I try to authenticate with Invalid credentials
    Given I generate the authorisation headers
      | contentType                     | messageId                            | requestID                            | path                     |
      | application/json; charset=UTF-8 | 6480071e-039d-4dca-a966-4ce3c1bc201b | 1cc6daff-a305-4d7b-94b0-c580fd5ba6b4 | /v1/2709/hostedcheckouts |
    And I set the order details as
      | currencyCode | amount | countryCode | merchantCustomerId |
      | EUR          | 100    | NL          | 103                |
    And I set the hostedCheckoutSpecificInput as
      | variant     | locale |
      | testVariant | en_GB  |
    When I post the  request to hostedCheckOutAPI
    Then I should get the response code as "403"

