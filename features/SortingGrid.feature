#Author: Siddharth.Ughade@gmail.com
#Keywords Summary : Verify Mega Menus
#Feature: List of scenarios.
Feature: Sorting on Price

  Background: 
    Given Open ecommerce website
      | WebURL                        | WebPageTitle |
      | http://automationpractice.com | My Store     |
    Then Register user with valid email
      | Email           | Password |
      | ssughade@abc.om | Abc123   |
    And Verify Mega Menus
      | MainMenu | SubMenu         | LinkTitle             | PageTitle                 |
      | DRESSES  | EVENING DRESSES | unforgettable evening | Evening Dresses - My Store|

  @SortingGrid
  Scenario: Verify Grid Sorting
    And Select Sort By
      | Price: Lowest first | Ascending |
    And Select Sort By
      | Price: Highest first | Descending |
    Then Sign Out
    Then Close Browser
