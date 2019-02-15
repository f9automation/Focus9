package com.focus.constants;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
public class BasicExtentReport  
{
	 static ExtentReports report;
	 static ExtentTest test;
	 public void f()
	 {
		 report = new ExtentReports(System.getProperty("user.dir")+"\\ExtentReportResults.html");
		 test = report.startTest("ExtentDemo");
	 
	
}
}