#Author: Siddharth.Ughade@gmail.com
#Keywords Summary :
#Feature: List of scenarios. - Verify Mega Menus
Feature: Verify Mega Menus

  Background: 
    Given Open ecommerce website
      | WebURL                        | WebPageTitle |
      | http://automationpractice.com | My Store     |
    Then Register user with valid email
      | Email           | Password |
      | ssughade@abc.om | Abc123   |

  @MegaMenus
  Scenario: Verify ecommer web Mega Menus
    And Verify Mega Menus
      | MainMenu | SubMenu         | LinkTitle             | PageTitle                 |
      | DRESSES  | CASUAL DRESSES  | dress for every day   | Casual Dresses - My Store |
      | DRESSES  | EVENING DRESSES | unforgettable evening | Evening Dresses - My Store|
      | DRESSES  | SUMMER DRESSES  | dress for summer      | Summer Dresses - My Store |
    Then Sign Out
    Then Close Browser
