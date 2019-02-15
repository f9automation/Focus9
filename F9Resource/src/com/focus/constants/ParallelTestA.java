package com.focus.constants;
import java.net.MalformedURLException;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.focus.constants.Browser;

public class ParallelTestA  extends Browser{
	
	
	//public static  WebDriverWait wait = new WebDriverWait(driver, 20);
	//public static WebDriverWait shortwait=new WebDriverWait(driver,5);
	
	@BeforeClass
	@Parameters({ "browser" })
	public void setUp(String browser) throws MalformedURLException {
		logger.info("*******************");
		driver = Browser.getDriver(browser);
		driver.manage().window().maximize();
	}
	
	@Test(dependsOnMethods="setUp")
	public void testGooglePageTitleInFirefox() {
		driver.navigate().to(appURL);
		String strPageTitle = driver.getTitle();
		logger.info("page title "+strPageTitle);
		
		Assert.assertTrue(strPageTitle.equalsIgnoreCase("Focus"), "Page title doesn't match");
		
	}

	@AfterClass
	public void tearDown() {
		//if(driver!=null) {
			logger.info("Closing browser "+Thread.currentThread().getId());
			//driver.quit();
		//}
	}
	
}