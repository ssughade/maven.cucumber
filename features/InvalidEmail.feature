#Author: Siddharth.Ughade@gmail.com
#Keywords Summary :
#Feature: Login with unregister user
Feature: Login with unregister user

  Background: 
    Given Open ecommerce website
      | WebURL                        | WebPageTitle |
      | http://automationpractice.com | My Store     |
    Then Register user with valid email
      | Email           | Password |
      | ssughade@abc.om | Abc1234  |

  @InvalidEmail
  Scenario: Verify Invalid Register users
    Then Ecommer page should shows error and should nog logged in
      | Authentication failed. |
    And Close Browser
