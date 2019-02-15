package com.focus.constants;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;


import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;


	
	import java.net.MalformedURLException;
	import java.net.URL;

	import org.openqa.selenium.remote.DesiredCapabilities;
	import org.openqa.selenium.remote.RemoteWebDriver;

	public class Browser {
		public static RemoteWebDriver driver1;
		public static WebDriver driver;
		public static String appURL = "http://192.168.5.29/focus8w#";
		public static Logger logger=Logger.getLogger("Google");
		public static RemoteWebDriver getDriver(String browser) throws MalformedURLException {
			
			return new RemoteWebDriver(new URL("http://192.168.5.29:4444/wd/hub"), getBrowserCapabilities(browser));
		}
		
		private static DesiredCapabilities getBrowserCapabilities(String browserType) {
			switch (browserType) {
			case "firefox":
				logger.info("Opening firefox driver");
				return DesiredCapabilities.firefox();
			case "chrome":
				logger.info("Opening chrome driver "+Thread.currentThread().getId());
				return DesiredCapabilities.chrome();
			case "IE":
				logger.info("Opening IE driver");
				return DesiredCapabilities.internetExplorer();
			default:
				logger.info("browser : " + browserType + " is invalid, Launching Firefox as browser of choice..");
				return DesiredCapabilities.firefox();
			}
		}
	
	 }

	
	
	
	
	

