package com.focus.constants;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.TimeUnit;


import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.tools.ant.util.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.google.common.io.Files;
import com.relevantcodes.extentreports.ExtentReports;

public class LaunchApplication extends BasicExtentReport
{
	
	public static Logger logger=Logger.getLogger("Google");
    public static WebDriver driver= new ChromeDriver();
   	public static String url = "http://localhost/focus9";
	public static  WebDriverWait wait = new WebDriverWait(driver, 20);
	public static WebDriverWait shortwait=new WebDriverWait(driver,5);
   
	
	
	
	/* STATIC METHOD TO LAUNCH CHROME BROWSER AND NAVIGATE TO THE FOCUS9 APPLICATION */
	
	 @BeforeSuite
	 public  static void LaunchApp() throws MalformedURLException, InterruptedException
	 {
		//Getting log4j properties to log files 
		 PropertyConfigurator.configure("D:\\Eclipse-Focus9\\Focus9\\F9Resource\\log\\log4j.properties");
		 logger.info("Browser Opened");
		 Set<String> allwindow=driver.getWindowHandles() ;
		 int i=1;
		 String lastwindow=driver.getWindowHandle();
		 for (String eachwindow : allwindow)

		 {

		driver.switchTo().window(eachwindow);
		lastwindow=eachwindow;
		 i++;

		 }
		 logger.info(i);
		 	
		 driver.switchTo().window(lastwindow);
		
		 driver.navigate().to(url);
		 logger.info("Application launched");
		 driver.manage().window().maximize();
		 logger.info("Browser Maximized");
		 driver.manage().timeouts().implicitlyWait(70, TimeUnit.SECONDS);
		 
		
	 }
	 
	 /* METHOD TO TAKE SCREENSHOT OF THE CURRENT WEBDRIVER AND SAVING .PNG FILE, BY PASSING THE DRIVER AND THE PATH TO THE LOCATION WHERE THE FILE NEED TO BE SAVED */
	
	 public  static void takeSnapShot(WebDriver webdriver,String fileWithPath) throws Exception
	 {
		 //Convert web driver object to TakeScreenshot
		 TakesScreenshot scrShot =((TakesScreenshot)webdriver);
		 //Call getScreenshotAs method to create image file
		 File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
		 //Move image file to new destination
		 File DestFile=new File(fileWithPath);
		 //Copy file at destination
		 Files.copy(SrcFile, DestFile);
	              
	 }
	 

}


	
	
	
	
	

