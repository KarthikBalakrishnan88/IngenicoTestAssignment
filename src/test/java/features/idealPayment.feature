@Regression
Feature: Ideal Payment
  As a user
  In order to make payment with iDeal
  I should be able to use the redirect URL from the merchant

  Background:
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

  Scenario: I should be able to make a successful iDeal payment
    Given I navigate to merchant payment page
    And I select the idealPayment Method
    When I select the bank as "Issuer Simulation V3 - RABO"
    And Click on Pay button
    And I confirm my transacion
    Then I should see the confirmation message

  Scenario: I should be able to ReTry my Payment when it fails
    Given I navigate to merchant payment page
    And I select the idealPayment Method
    When I select the bank as "ABN Amro"
    And Click on Pay button
    Then I should see error message for PaymentFailure
    And I should be able to retry my payment with "Issuer Simulation V3 - RABO"
    And I confirm my transacion
    And I should see the confirmation message


  Scenario: I should be able to ReTry with different payment type when it fails
    Given I navigate to merchant payment page
    And I select the idealPayment Method
    When I select the bank as "ABN Amro"
    And Click on Pay button
    Then I should see error message for PaymentFailure
    And I should be able to choose another method for payment
