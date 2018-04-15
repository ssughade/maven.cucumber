#Author: Siddharth.Ughade@gmail.com
#Keywords Summary :
#Feature: Login with register user
Feature: Login with register user

  Background: 
    Given Open ecommerce website
      | WebURL                        | WebPageTitle |
      | http://automationpractice.com | My Store     |
    Then Register user with valid email
      | Email           | Password |
      | ssughade@abc.om | Abc123   |

  @ValidEmail
  Scenario: Login with registered email
    Then Register should be registered successfully
      | Siddharth Ughade |
    Then Sign Out
    Then Close Browser