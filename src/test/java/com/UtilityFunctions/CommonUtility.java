package com.UtilityFunctions;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CommonUtility {
	public String sReasonFailed ="";
	public WebDriver LaunchBrowser(String sBrowserType, String sLocation, String sURL, String sExpectedPageTitle ) throws InterruptedException
	{
		//Chrome
		WebDriver driver = null;
		if (sBrowserType.equalsIgnoreCase("Chrome"))
		{
			//System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\Driver\\chromedriver.exe");
			System.setProperty("webdriver.chrome.driver", sLocation);
			driver = new ChromeDriver();
		} else if (sBrowserType.endsWith("IE"))
		{
			//System.setProperty("webdriver.ie.driver", "C:\\Selenium\\Driver\\IEDriverServer.exe");
			System.setProperty("webdriver.ie.driver", sLocation);
			
			DesiredCapabilities ieCap = new DesiredCapabilities().internetExplorer();
			ieCap.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
			ieCap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, false);
			
			driver = new InternetExplorerDriver(ieCap);
		} else if (sBrowserType.endsWith("FireFox"))
		{
			//System.setProperty("webdriver.gecko.driver", "C:\\Selenium\\Driver\\geckodriver.exe");
			System.setProperty("webdriver.gecko.driver", sLocation);
			 driver = new FirefoxDriver(); 
		}
		
		//Page refresh/load
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.MINUTES);

		//Implicit Wait example which going to applicable all .FindElement statements
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//Thread.sleep(20000);
		
		driver.get(sURL);
		Thread.sleep(10000);
		driver.manage().window().maximize();
		Thread.sleep(10000);
		
		if (driver.getTitle().equals(sExpectedPageTitle))
		{
			System.out.println("Page title matched " + driver.getTitle());
		} else {
			System.out.println("Page title does not matched - Expected '" + sExpectedPageTitle + "', Actual :" + driver.getTitle() );
		}
		return driver;
	}

	public boolean ClickElement(WebDriver driver, String sLocator)
	{
		boolean blnClicked=false;
		
		WebElement element = getWebElement(driver, sLocator);
		if (element!=null)
		{
			if (element.isDisplayed() && element.isEnabled())
			{
				element.click();
				blnClicked=true;
			}
		}
		return blnClicked;
	}
	
	public boolean waitForElement(WebDriver driver, String sLocator, int iMaxWaitInSeconds)
	{
		boolean blnWait=false;
		
		WebDriverWait driverWait = new WebDriverWait(driver, iMaxWaitInSeconds);
		
		try {
			WebElement element = getWebElement(driver, sLocator);
			if (element!=null)
			{
				driverWait.until(ExpectedConditions.visibilityOf(element));
				blnWait=true;
			}
			
		} catch(Exception e) {}
		
		return blnWait;
	}
	
	
	//EnterTextValue keyword
	public  boolean EnterTextValue(WebDriver driver, String sLocator, String sValueToBeEntered)
	{
		boolean blnEnterValue=false;
		
		if (sLocator.trim().length() == 0)
		{
			//sReasonFailed = "FAILED - Locator can't be empty";
			return blnEnterValue;
		}
		
		WebElement eleTextField = getWebElement(driver,sLocator);
		System.out.println(sLocator);
		//WebElement eleClick = driver.findElement(By.name(sName));
		if (eleTextField!=null){
			if (eleTextField.isDisplayed() && eleTextField.isEnabled())
			{
				eleTextField.sendKeys(sValueToBeEntered);
				blnEnterValue=true;
			}
		} else {
			//sReasonFailed = "FAILED - Unable to find element '" + sLocator +"'";
			return blnEnterValue;
		}
		blnEnterValue=true;
		//sReasonFailed = "PASSED - Successfully Enter value in '" + sLocator +"'";
		return blnEnterValue;
	}
	
	public boolean elementPresent(WebDriver driver, String sLocator)
	{
		boolean blnElementFound=false;
		WebElement element = getWebElement(driver,sLocator);
		if (element!=null)
		{
			if (element.isDisplayed())
			{
				blnElementFound=true;
			}
		} 
		
		return blnElementFound;
	}
	
	public WebElement getWebElement(WebDriver driver, String sLocator)
	{
		WebElement webElement = null;
		if (sLocator.startsWith("name"))
		{
			String [] sLocatorArray = sLocator.split("=");
			String sName = sLocatorArray[1];
			webElement = driver.findElement(By.name(sName));
			
		} else if (sLocator.startsWith("id"))
		{
			String [] sLocatorArray = sLocator.split("=");
			String sID = sLocatorArray[1];
			webElement = driver.findElement(By.id(sID));
		} else if (sLocator.startsWith("//"))
		{
			//String [] sLocatorArray = sLocator.split("=");
			//String xPath = sLocatorArray[1];
			webElement = driver.findElement(By.xpath(sLocator));
		}else if (sLocator.startsWith("cssSelector"))
		{
			String [] sLocatorArray = sLocator.split("\\|");
			String sCSSSelector = sLocatorArray[1];
			webElement = driver.findElement(By.cssSelector(sCSSSelector));
		}else if (sLocator.startsWith("Link"))
		{
			String [] sLocatorArray = sLocator.split("=");
			String sLinkText = sLocatorArray[1];
			webElement = driver.findElement(By.linkText(sLinkText));
		}
		
		
		return webElement;
	}
	
	public boolean SelectListValue(WebDriver driver, String sLocator, String sValueToBeSelect)
	{
		boolean blnSelected=false;
		
		WebElement eleSelect = getWebElement(driver, sLocator);
		Select eleSelectList = new Select(eleSelect);
		if (eleSelect==null)
		{
			sReasonFailed = "FAILED = Element does not found '" + sLocator + "'";
		}
		if (eleSelectList!=null)
		{
			boolean blnItemSelected=false;
			System.out.println("Already value selected " +eleSelectList.getFirstSelectedOption().getText());
			blnSelected=eleSelectList.getFirstSelectedOption().getText().equals(sValueToBeSelect);
			//if (eleSelectList.getFirstSelectedOption().getText().equals(sValueToBeSelect));
			if (blnSelected)
			{
				blnSelected=true;
				sReasonFailed = "PASSED - Item already selected '" + sValueToBeSelect + "'";
				blnItemSelected=true;
			}
			if (!blnItemSelected) {
				try {
					//eleSelectList.deselectAll();  // In case Multi select Select item - 1st de-select all option
					eleSelectList.selectByVisibleText(sValueToBeSelect);
					//eleSelectList.selectByVisibleText("Item1");		//In case of multi select item 1
					//eleSelectList.selectByVisibleText("Item2");		//In case of multi select item 2
					
					//eleSelectList.selectByValue("71");
					//eleSelectList.selectByIndex(2);
					blnSelected=true;
					sReasonFailed = "PASSED - Item selected '" + sValueToBeSelect + "' successfully";
				} catch(Exception e)
				{
					sReasonFailed = "FAILED = Item not found in list '" + sValueToBeSelect + "' Error : " + e.getMessage();
					blnSelected=false;
				}
			}
		}
		
		return blnSelected;
	}

	//Below functions will help us to read value from
	public String ReturnEnvValue(String XMLFileWithPath,String EnvVarName)
	{
		String VarName = null, VarValue=null;
		try {

			File envFile = new File(XMLFileWithPath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(envFile);
			doc.getDocumentElement().normalize();
			
			System.out.println("root of xml file" + doc.getDocumentElement().getNodeName());
			NodeList nodes = doc.getElementsByTagName("Variable");
			System.out.println("==========================");
			
			for (int i = 0; i < nodes.getLength(); i++) 
			{
				Node node = nodes.item(i);
				
				if (node.getNodeType() == Node.ELEMENT_NODE) 
				{
					Element element = (Element) node;
					/*System.out.println("Environment - Name: " + getValue("Name", element));
					System.out.println("Environment - Value: " + getValue("Value", element));
					System.out.println("Environment - Description: " + getValue("Description", element));*/
					VarName = getValue("Name", element);
					if (VarName.equalsIgnoreCase(EnvVarName))
					{
						VarValue = getValue("Value", element);
					}
				}
			}
			} catch (Exception ex) {
				ex.printStackTrace();
		}
		return VarValue;
	}

	public static String getValue(String tag, Element element) {
		NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodes.item(0);
		return node.getNodeValue();
	}

	
}
