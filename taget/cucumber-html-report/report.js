$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("InvalidEmail.feature");
formatter.feature({
  "comments": [
    {
      "line": 1,
      "value": "#Author: Siddharth.Ughade@gmail.com"
    },
    {
      "line": 2,
      "value": "#Keywords Summary :"
    },
    {
      "line": 3,
      "value": "#Feature: Login with unregister user"
    }
  ],
  "line": 4,
  "name": "Login with unregister user",
  "description": "",
  "id": "login-with-unregister-user",
  "keyword": "Feature"
});
formatter.uri("RegisterWithValidEmail.feature");
formatter.feature({
  "comments": [
    {
      "line": 1,
      "value": "#Author: Siddharth.Ughade@gmail.com"
    },
    {
      "line": 2,
      "value": "#Keywords Summary :"
    },
    {
      "line": 3,
      "value": "#Feature: Login with register user"
    }
  ],
  "line": 4,
  "name": "Login with register user",
  "description": "",
  "id": "login-with-register-user",
  "keyword": "Feature"
});
formatter.uri("SortingGrid.feature");
formatter.feature({
  "comments": [
    {
      "line": 1,
      "value": "#Author: Siddharth.Ughade@gmail.com"
    },
    {
      "line": 2,
      "value": "#Keywords Summary : Verify Mega Menus"
    },
    {
      "line": 3,
      "value": "#Feature: List of scenarios."
    }
  ],
  "line": 4,
  "name": "Sorting on Price",
  "description": "",
  "id": "sorting-on-price",
  "keyword": "Feature"
});
formatter.background({
  "line": 6,
  "name": "",
  "description": "",
  "type": "background",
  "keyword": "Background"
});
formatter.step({
  "line": 7,
  "name": "Open ecommerce website",
  "rows": [
    {
      "cells": [
        "WebURL",
        "WebPageTitle"
      ],
      "line": 8
    },
    {
      "cells": [
        "http://automationpractice.com",
        "My Store"
      ],
      "line": 9
    }
  ],
  "keyword": "Given "
});
formatter.step({
  "line": 10,
  "name": "Register user with valid email",
  "rows": [
    {
      "cells": [
        "Email",
        "Password"
      ],
      "line": 11
    },
    {
      "cells": [
        "ssughade@abc.om",
        "Abc123"
      ],
      "line": 12
    }
  ],
  "keyword": "Then "
});
formatter.step({
  "line": 13,
  "name": "Verify Mega Menus",
  "rows": [
    {
      "cells": [
        "MainMenu",
        "SubMenu",
        "LinkTitle",
        "PageTitle"
      ],
      "line": 14
    },
    {
      "cells": [
        "DRESSES",
        "EVENING DRESSES",
        "unforgettable evening",
        "Evening Dresses - My Store"
      ],
      "line": 15
    }
  ],
  "keyword": "And "
});
formatter.match({
  "location": "ECommerceWeb.open_ecommerce_website(DataTable)"
});
formatter.result({
  "duration": 39502490528,
  "status": "passed"
});
formatter.match({
  "location": "ECommerceWeb.register_user_with_valid_email(DataTable)"
});
formatter.result({
  "duration": 19811158069,
  "status": "passed"
});
formatter.match({
  "location": "ECommerceWeb.verify_Mega_Menus(DataTable)"
});
formatter.result({
  "duration": 11595941573,
  "status": "passed"
});
formatter.scenario({
  "line": 18,
  "name": "Verify Grid Sorting",
  "description": "",
  "id": "sorting-on-price;verify-grid-sorting",
  "type": "scenario",
  "keyword": "Scenario",
  "tags": [
    {
      "line": 17,
      "name": "@SortingGrid"
    }
  ]
});
formatter.step({
  "line": 19,
  "name": "Select Sort By",
  "rows": [
    {
      "cells": [
        "Price: Lowest first",
        "Ascending"
      ],
      "line": 20
    }
  ],
  "keyword": "And "
});
formatter.step({
  "line": 21,
  "name": "Select Sort By",
  "rows": [
    {
      "cells": [
        "Price: Highest first",
        "Descending"
      ],
      "line": 22
    }
  ],
  "keyword": "And "
});
formatter.step({
  "line": 23,
  "name": "Sign Out",
  "keyword": "Then "
});
formatter.step({
  "line": 24,
  "name": "Close Browser",
  "keyword": "Then "
});
formatter.match({
  "location": "ECommerceWeb.select_Sort_By(DataTable)"
});
formatter.result({
  "duration": 96028420,
  "status": "passed"
});
formatter.match({
  "location": "ECommerceWeb.select_Sort_By(DataTable)"
});
formatter.result({
  "duration": 46451166,
  "status": "passed"
});
formatter.match({
  "location": "ECommerceWeb.sign_Out()"
});
formatter.result({
  "duration": 2280509064,
  "status": "passed"
});
formatter.match({
  "location": "ECommerceWeb.close_Browser()"
});
formatter.result({
  "duration": 1666870888,
  "status": "passed"
});
formatter.uri("VerifyMegaMenus.feature");
formatter.feature({
  "comments": [
    {
      "line": 1,
      "value": "#Author: Siddharth.Ughade@gmail.com"
    },
    {
      "line": 2,
      "value": "#Keywords Summary :"
    },
    {
      "line": 3,
      "value": "#Feature: List of scenarios. - Verify Mega Menus"
    }
  ],
  "line": 4,
  "name": "Verify Mega Menus",
  "description": "",
  "id": "verify-mega-menus",
  "keyword": "Feature"
});
});