package com.stepDefinition;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import com.UtilityFunctions.CommonUtility;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.runtime.junit.Assertions;

public class ECommerceWeb {

	WebDriver driver;
	String sBrowserType = "FireFox",
			sLocation = "C:\\Selenium\\Driver\\chromedriver.exe",
			sURL = "http://automationpractice.com",
			sExpectedPageTitle = "My Store";
	int iMaxWaitInSeconds=120;	//Default value
	
	//Creating Object for common Utility class which
	//contain common functions like EnterTextValue, LaunchBrowser etc
	CommonUtility commUtil = new CommonUtility();
	
	String sLocator ="";
	
	//Reading dynamic working direcotyr folder 
	String sCurrentFolder = System.getProperty("user.dir");
	String sObjecMapFile = sCurrentFolder + "\\ObjectMap.xml" ;
	String sEnvFile = sCurrentFolder + "\\EnvReg.xml" ;
	String sDriverExecutablePath = sCurrentFolder + "\\SeleniumDrivers";
	
	
	@Given("^Open ecommerce website$")
	public void open_ecommerce_website(DataTable sURLData) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		
		//Reading parameters
		List<Map<String,String>> data = sURLData.asMaps(String.class,String.class);

		sURL = data.get(0).get("WebURL");
		sExpectedPageTitle = data.get(0).get("WebPageTitle");
		
		//Verifying if the URL and Expected page is null
		if (sURL == null)
		{
			org.junit.Assert.fail("Application Web URL is blank");
		}
		
		//Here I am reading the Parameter from maven
		//Default kept it as Chrome
		//From command line user can execute the same maven project by using below command
		//mvn install test -DBrowserName=FireFox
		sBrowserType = System.getProperty("BrowserName");
		if (sBrowserType == null )
		{
			sBrowserType= "Chrome";
		}
		//Here I am going to get the Driver executable from 
		//
		if (sBrowserType.trim().equalsIgnoreCase("Chrome"))
		{
			sLocation = sDriverExecutablePath + "\\chromedriver.exe" ;
		} else if (sBrowserType.trim().equalsIgnoreCase("FireFox"))
		{
			sLocation = sDriverExecutablePath + "\\geckodriver.exe" ;
		} else if (sBrowserType.trim().equalsIgnoreCase("IE"))
		{
			sLocation = sDriverExecutablePath + "\\IEDriverServer.exe" ;
		} else {
			org.junit.Assert.fail("Incorrect Browser type mention: " + sBrowserType + "', it can be eihther 'Chrome' or 'FireFox' or 'IE'");
		}
		
		driver = commUtil.LaunchBrowser(sBrowserType, sLocation, sURL,
				sExpectedPageTitle);
		if (driver == null) {
			System.out.println("Browser not launch succesfully");
			org.junit.Assert.fail("Unable to launch browser");
		}
		//System.out.println(sCurrentFolder);
	}

	@Given("^Register user with valid email$")
	public void register_user_with_valid_email(DataTable paramData) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		// String sLocator = "Link=Sign in";
		// Log in to your customer account
		int iMaxWaitInSeconds = 120;
		//sLocator = "//a[@title='Log in to your customer account']";
		List<Map<String,String>> data = paramData.asMaps(String.class,String.class);

		String sEmailAddress = data.get(0).get("Email");
		String sPassword = data.get(0).get("Password");
		
		//Below locator value reading from EnvReg.xml file for Signin variable
		sLocator = commUtil.ReturnEnvValue(sObjecMapFile,"Signin");
		
		//Below value reading from EnvReg.xml file for WaitTime variable
		//As function is going to return string value so convert it into int
		iMaxWaitInSeconds = Integer.parseInt(commUtil.ReturnEnvValue(sEnvFile,"WaitTime"));
		boolean result = commUtil.waitForElement(driver, sLocator,iMaxWaitInSeconds);
		
		if (!result) {
			System.out.println("Sign In link does not appear within "
					+ iMaxWaitInSeconds + " seconds");
			org.junit.Assert.fail("Sign In link does not appear within " + iMaxWaitInSeconds + " seconds");
		}
		result = commUtil.ClickElement(driver, sLocator);
		if (!result) {
			System.out.println("Error - Click on Sign In link");
			org.junit.Assert.fail("Error - Click on Sign In link " + commUtil.sReasonFailed);
		} else {
			//sLocator = "id=email";
			sLocator = commUtil.ReturnEnvValue(sObjecMapFile,"Emailaddress");
			
			result = commUtil.EnterTextValue(driver, sLocator, sEmailAddress);

			if (!result) {
				System.out.println("Error - While entering Email address");
				org.junit.Assert.fail("Error - While entering Email address " + commUtil.sReasonFailed);
			} else {
				System.out.println("Entered Email address successfully");
			}

			//sLocator = "id=passwd";
			sLocator = commUtil.ReturnEnvValue(sObjecMapFile,"Password");
			
			result = commUtil.EnterTextValue(driver, sLocator,sPassword);
			if (!result) {
				System.out.println("Error - While entering Password");
				org.junit.Assert.fail("Error - While entering Password " + commUtil.sReasonFailed);
			} else {
				System.out.println("Entered Password successfully");
			}

			//sLocator = "id=SubmitLogin";
			//Getting locator for 'SignInButton' from ObjectMap.xml file
			sLocator = commUtil.ReturnEnvValue(sObjecMapFile,"SignInButton");
			result = commUtil.ClickElement(driver, sLocator);
			if (!result) {
				System.out.println("Error - Click on Sign in button");
				org.junit.Assert.fail("Error - Click on Sign in button " + commUtil.sReasonFailed);
			} else {
				System.out.println("Click on Sign in button");
			}

		} // if (!result)
	}

	@Then("^Register should be registered successfully$")
	public void register_should_be_registered_successfully(DataTable sLoggedInName) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		int iMaxWaitInSeconds = 120;
		String sUserName = "Siddharth Ughade";
		
		List<List<String>> data = sLoggedInName.raw();
		sUserName = data.get(0).get(0);
		
		sLocator = "//span[text()='" + sUserName.trim() + "']";
		boolean result = commUtil.waitForElement(driver, sLocator,
				iMaxWaitInSeconds);
		if (!result) {
			System.out.println("Logged In User name does not appear "
					+ iMaxWaitInSeconds + " seconds");
			org.junit.Assert.fail("Logged In User name does not appear "
					+ iMaxWaitInSeconds + " seconds");
		}
		boolean blnUserloggedIn = commUtil.elementPresent(driver, sLocator);
		if (!blnUserloggedIn) {
			System.out.println("Error - User does not logged");
			org.junit.Assert.fail("Error - User does not logged");
		} else {
			System.out.println("User logged in successfully '" + sUserName
					+ "'");
		}

	}

	@Then("^Ecommer page should shows error and should nog logged in$")
	public void ecommer_page_should_shows_error_and_should_nog_logged_in(DataTable sErrorMessage)
			throws Throwable {

		//Reading the parameters
		List<List<String>> data = sErrorMessage.raw();
		String eExpectedErrorMessage = data.get(0).get(0);
		
		// Here first I am findout the parent element
		// which is Div tag with class - alert alert-danger
		// When we use cssSelector we can specifiy the it locator by giving
		// parent with . and also if class name has spaces then we
		// have to use the . in place of blank space. If more then one blank
		// space
		// present then also we need to give single .
		//String sLocator = "cssSelector|div.alert.alert-danger";
		//Getting locator for 'ErrorMessage' from ObjectMap.xml file
		sLocator = commUtil.ReturnEnvValue(sObjecMapFile,"ErrorMessage");
		
		WebElement eleError = commUtil.getWebElement(driver, sLocator);
		if (eleError != null) {
			// Getting the 1st child element with the tag 'p' which contain the
			// header
			// i.e 'There is 1 error'
			WebElement eleErrHeader = eleError.findElement(By.tagName("p"));
			if (eleErrHeader != null) {
				System.out.println(eleErrHeader.getText());
			}
			// Getting the 1st child element with the tag 'li' which contain the
			// header
			// i.e 'Authentication failed.'
			WebElement eleErrMessage = eleError.findElement(By.tagName("li"));
			if (eleErrHeader != null) {
				// System.out.println(eleErrMessage.getText());
				//
				//if (eleErrMessage.getText().equals("Authentication failed.")) {
				if (eleErrMessage.getText().equals(eExpectedErrorMessage)) {
					System.out
							.println("Expected message found '" + eExpectedErrorMessage + "'");
					
				} else {
					System.out
							.println("Expected message is '" + eExpectedErrorMessage + "' Actual is '"
									+ eleErrMessage.getText());
					org.junit.Assert.assertTrue(eExpectedErrorMessage.trim().equalsIgnoreCase(eleErrMessage.getText()));
				}
			}
		}

	}
	
	@Then("^Verify Mega Menus$")
	public void verify_Mega_Menus(DataTable sMenuToNavigate) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    System.out.println("I am in verify Menus");
	    //String sLocator = "//a[@title='Dresses']";
	    //String sLocator = "Link=Dresses";
	    
	    //
	    boolean mainMenuFound=false,subMenuFound=false;
	    //String sMenuName = "DRESSES";
	    //String sSubMenu = "SUMMER DRESSES";
	    //String sPageTitle = "Summer Dresses - My Store";
	    
	    //Reading parameters
		List<Map<String,String>> data = sMenuToNavigate.asMaps(String.class,String.class);

		String sMenuName = data.get(0).get("MainMenu");
		String sSubMenu = data.get(0).get("SubMenu");
		String sLinkTitle = data.get(0).get("LinkTitle");	//This Link title used to click on link using @title property
		String sPageTitle = data.get(0).get("PageTitle");
	    
	    //sLocator = "cssSelector|ul.sf-menu.clearfix.menu-content.sf-js-enabled.sf-arrows";
	    
		//Getting locator for 'MainMenu' from ObjectMap.xml file
		sLocator = commUtil.ReturnEnvValue(sObjecMapFile,"MainMenu");
	    
	    boolean result = commUtil.waitForElement(driver, sLocator, iMaxWaitInSeconds);
	    org.junit.Assert.assertTrue(result);
	    WebElement eleMenu = commUtil.getWebElement(driver, sLocator);
	    if (eleMenu!=null)
	    {
	    	
	    	/* Initially tried to focus then click using java script
	    	 * somehow it didn't work as expected so change approach
	    	 * ((JavascriptExecutor)driver).executeScript("arguments[0].focus()", eleMenu);
	    	Actions actions = new Actions(driver);
	    	Action action = actions.moveToElement(eleMenu).build();
	    	action.perform();*/
	    	
	    	List<WebElement> allliElements = eleMenu.findElements(By.tagName("li"));
	    	for(WebElement menu:allliElements)
	    	{
	    		//System.out.println(menu.getText().trim().contains("DRESSES"));
	    		
	    		//if (menu.getText().trim().contains("DRESSES"))
	    		if (menu.getText().trim().contains(sMenuName))
	    		{
	    			//System.out.println("About to click");
	    			Actions actions = new Actions(driver);
	    	    	Action action = actions.moveToElement(menu).build();
	    	    	action.perform();
	    	    	Thread.sleep(5000);  //Found that while executing on firefox it needs some time to populate sub-manu
	    	    	mainMenuFound=true;
	    	    	break;
	    		}
	    	}
	    	
	    	
 	    	if (mainMenuFound)
 	    	{
 	    		/* Initially tried to find by using xpath but element was not found
 	    		 * so change the approach
 	    		 * sLocator = "//li[text()='SUMMER DRESSES']";
 	    		 */
 	    		
 	    		//Getting locator for 'MainMenu' from ObjectMap.xml file
 	    		//Here for SubMenu I am storing only css locator path
 	    		sLocator = commUtil.ReturnEnvValue(sObjecMapFile,"SubMenu");
 	    		
 	    		//List<WebElement> allSubMenu = driver.findElements(By.cssSelector("ul.submenu-container.clearfix.first-in-line-xs"));
 	    		List<WebElement> allSubMenu = driver.findElements(By.cssSelector(sLocator));
 	    		for(WebElement subMenu:allSubMenu)
 	    		{
 	    			//System.out.println(subMenu.getTagName());
 	    			
 	    			List<WebElement> allSubliElements = subMenu.findElements(By.tagName("li"));
			    	for(WebElement SubMenu:allSubliElements)
			    	{
			    		System.out.println(SubMenu.getText());
			    		//sSubMenu
			    		//if (SubMenu.getText().equalsIgnoreCase("SUMMER DRESSES"))
			    		if (SubMenu.getText().equalsIgnoreCase(sSubMenu))
			    		{
			    			SubMenu.click();
			    			subMenuFound=true;
			    			break;
			    		}
			    	}
 	    			
			    	if (subMenuFound)
			    	{
			    		break;
			    	}
 	    		} 
 	   	    
 	    		if (mainMenuFound && subMenuFound)
 	    		{
 	    			String sActualTitle = driver.getTitle();
 	    			if (sActualTitle.trim().equalsIgnoreCase(sPageTitle))
 	    			{
 	    				System.out.println("Navigated successfully " + sMenuName  +"=>" + sSubMenu );
 	    			} else {
 	    				org.junit.Assert.assertTrue(sActualTitle.trim().equalsIgnoreCase(sPageTitle));
 	    			}
 	    		} else {
 	    			
 	    			//I observed that Mouse Over does not work as expected
 	    			//on firefox, so script should not get failed so added this code
 	    			//where it will left click on Main Menu then select sub-menu
 	    			
					for(WebElement menu:allliElements)
					{
						//System.out.println(menu.getText().trim().contains("DRESSES"));
					
						//if (menu.getText().trim().contains("DRESSES"))
						if (menu.getText().trim().contains(sMenuName))
						{
							//System.out.println("About to click");
							menu.click();
					    	mainMenuFound=true;
					    	break;
						}
					}
					
					//Then click on Sub-menu
					//sLocator = "//a[contains(@title,'perfect dress for summer')]";
					//
					sLocator = "//a[contains(@title,'" + sLinkTitle + "')]";
					result = commUtil.waitForElement(driver, sLocator, iMaxWaitInSeconds);
 	    			if (result)
 	    			{
 	    				result = commUtil.ClickElement(driver, sLocator);
 	    				subMenuFound=true;
 	    				
 	    				String sActualTitle = driver.getTitle();
 	 	    			if (sActualTitle.trim().equalsIgnoreCase(sPageTitle))
 	 	    			{
 	 	    				System.out.println("Navigated successfully " + sMenuName  +"=>" + sSubMenu );
 	 	    			} else {
 	 	    				System.out.println("Page title mismatch expected is '" + sActualTitle.trim() +"' Actual is '" + sPageTitle + "'" );
 	 	    				org.junit.Assert.assertTrue(sActualTitle.trim().equalsIgnoreCase(sPageTitle));
 	 	    			}
 	    				
 	    			} else {					
 	    				org.junit.Assert.fail("Sub Menu not found " + sSubMenu);
 	    			}
 	    		}
 	    	} else {
 	    		org.junit.Assert.fail("Main Menu not found " + sMenuName);
 	    	}
	    	
 	    	
	    	
	    } else {
	    	System.out.println("Element not found");
	    	org.junit.Assert.fail("Element not found " + sLocator);
	    }
	    
	}
	
	
	@Then("^Select Sort By$")
	public void select_Sort_By(DataTable arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
	    // E,K,V must be a scalar (String, Integer, Date, enum etc)
		
		List<List<String>> data = arg1.raw();
		String sValue = data.get(0).get(0);
		String sOrderBy = data.get(0).get(1);
		
		ArrayList<Double> unsortedArray = new ArrayList<Double>();
		//Locator for ProductSort
		String sLocator = "id=selectProductSort";
		
		//Getting locator for 'SortBy' from ObjectMap.xml file
		String sPriceLocator = commUtil.ReturnEnvValue(sObjecMapFile,"Price");
		
		//Below code will going to extract the default result of grid in un-sorted format
		//List<WebElement> allItemsPrices = driver.findElements(By.xpath("//span[@itemprop='price']"));
		List<WebElement> allItemsPrices = driver.findElements(By.xpath(sPriceLocator));
		for(WebElement itemPrice:allItemsPrices)
		{
			System.out.println(itemPrice.getText());
			if (itemPrice.getText().trim().length() > 0)
			{
				String sPriceValue = itemPrice.getText().trim().replace("$", "");
				double dPrice = Double.parseDouble(sPriceValue);
				unsortedArray.add(dPrice);
			}
		}
		
		//Here I am checking for items added in ArrayList
		//I Observed that for some pages like DRESSES => EVENING DRESSES 
		//Dress items are only one, so in this condition
		//No need to perform sorting so here verifies it if ArrayList is less <=1 then 
		//exit from function
		
		if (unsortedArray.size() <=1)
		{
			System.out.println("Exit becuase this page does not contain more then one item");
			return;
		}
		
		
		//Based on parameter extract un-sorted array going to order either by Ascending or Descending
		if (sOrderBy.trim().equalsIgnoreCase("Ascending"))
		{
			/* Sorting of arraylist using Collections.sort*/
			Collections.sort(unsortedArray);
		} else {
			// Sorting String Array in descending order
			Collections.reverse(unsortedArray);
		}

		//Selecting Sort By based on argument
		//Getting locator for 'SortBy' from ObjectMap.xml file
		sLocator = commUtil.ReturnEnvValue(sObjecMapFile,"SortBy");
	    boolean result = commUtil.SelectListValue(driver, sLocator, sValue);
	    Thread.sleep(6000);	//Given 6 seconds wait becuase page get refresh after select value
	    
	    if (!result)
	    {
	    	org.junit.Assert.fail("Unable to select Sort By '" + sValue + "'");
	    }

	    //Below code from Price removing '$' sign and storing in array list
	    //Below array list is going to hold the sorted result given by application
	    
	    ArrayList<Double> sortedArray = new ArrayList<Double>();
		//allItemsPrices = driver.findElements(By.xpath("//span[@itemprop='price']"));
	    allItemsPrices = driver.findElements(By.xpath(sPriceLocator));
		for(WebElement itemPrice:allItemsPrices)
		{
			System.out.println(itemPrice.getText());
			if (itemPrice.getText().trim().length() > 0)
			{
				String sPriceValue = itemPrice.getText().trim().replace("$", "");
				double dPrice = Double.parseDouble(sPriceValue);
				sortedArray.add(dPrice);
			}
		}
		
	    boolean blnSortedSuccessfully=true;
		//Compare both Output Sorted ArrayList and Actual data stored in array
		for (int i=0;i<unsortedArray.size();i++)
		{
			System.out.println(unsortedArray.get(i) + " " + sortedArray.get(i) );
			
			//if (unsortedArray.get(i) == sortedArray.get(i))
			if (Double.compare(unsortedArray.get(i), sortedArray.get(i)) != 0)
			{
				blnSortedSuccessfully=false;
				break;
			} 
		}
		//here expected result both array should be same
		//org.junit.Assert.assertTrue(blnSortedSuccessfully);
		if (!blnSortedSuccessfully)
		{
			org.junit.Assert.fail("Sorting failed for '" + sValue + "' for '" + sOrderBy +"'" );
		}
	}
	
	@Then("^Close Browser$")
	public void close_Browser() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		//Observed the error on firefox it does not quit browser 
		//so kept code in try catch
		try {
			driver.close();
			driver.quit();
		} catch(Exception e) {
			
			//Added code which will kill the browser executable in case of any error
			
			
			if (sBrowserType.trim().equalsIgnoreCase("Chrome"))
			{
				Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
			} else if (sBrowserType.trim().equalsIgnoreCase("FireFox"))
			{
				Runtime.getRuntime().exec("taskkill /F /IM geckodriver.exe");
			} else if (sBrowserType.trim().equalsIgnoreCase("IE"))
			{
				Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
			}
			
		}
	    System.out.println("Close Browser");
	}

	@Then("^Sign Out$")
	public void sign_Out() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		//Below locator value reading from EnvReg.xml file for SignOut variable
	    sLocator = commUtil.ReturnEnvValue(sObjecMapFile,"SignOut");
		boolean result = commUtil.ClickElement(driver, sLocator);
		if (!result)
		{
			org.junit.Assert.fail("Error - Unable to click on Sign Out link " + commUtil.sReasonFailed);
		} {
			System.out.println("Sign Out successfully");
		}
	}
}
