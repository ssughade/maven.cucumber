package com.runner;

import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.*;

@RunWith(Cucumber.class)
@CucumberOptions(features = "features",
	glue = {"com.stepDefinition"},
	plugin={"html:taget/cucumber-html-report"},
	//tags = {"~@ValidEmail","~@SortingGrid","~@MegaMenus"},
	//tags = {"~@ValidEmail","~@InvalidEmail","~@MegaMenus"},
	//tags = {"~@ValidEmail","~@InvalidEmail","~@SortingGrid"},
	dryRun=false)

public class TestRunner {
	public WebDriver driver;
}
