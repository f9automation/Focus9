package com.focus.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;

import com.focus.constants.LaunchApplication;

public class HomePage extends LaunchApplication
{
	
	public String username,password,compname,compmsg;
	Alert al;
	WebDriverWait wait = new WebDriverWait(driver, 30);
	String expmsg="success";
	public ArrayList<String> menunames=new ArrayList(); 
	
	/*METHOD TO LOGIN TO THE APPLICATION BY PASSING USERNAME, PASSWORD AND COMPANY NAME FROM EXCEL AS PARAMETERS*/
	public boolean LoginApp(String username, String password, String compname) throws InterruptedException 
	{
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtUsername")));
		WebElement uname = driver.findElement(By.id("txtUsername"));
		uname.sendKeys(username);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtPassword")));
		WebElement pwd = driver.findElement(By.id("txtPassword"));
		Thread.sleep(1000);
		pwd.click();
		Thread.sleep(1000);
		pwd.sendKeys(password);
		Thread.sleep(1000);
		pwd.click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ddlCompany")));
		WebElement mySelectElement = driver.findElement(By.id("ddlCompany"));
		Thread.sleep(1000);
		Select dropdown= new Select(mySelectElement);
		/* GETTING LIST OF  ELEMENTS OF COMPANY DROPDOWN AND THEN SELECTING THE MATCHED ONE WHICH IS SENT AS A PARAMETER */
		List<WebElement> list = dropdown.getOptions();
		List<String> text = new ArrayList<>();
		for(int i=0; i<list.size(); i++) 
		{
    	  list.get(i).getText();
    	  String optionName = list.get(i).getText();
    	  if(optionName.toUpperCase().startsWith(compname.toUpperCase()))
    	  {
    		  list.get(i).click();
    	  }
       

      }
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSignin")));
      WebElement SignIN= driver.findElement(By.id("btnSignin"));
      SignIN.click();
      Thread.sleep(3000);
      /* VERIFY IF  IT IS DISPLAYING HOME PAGE ONCE LOGGED INTO THE APPLICATION */
      try
      {
    	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Home")));
    	  if(driver.findElement(By.linkText("Home")).isDisplayed())
    	  {
    		  /* IF HOME PAGE IS DISPALYED, VERIFY IF CREATE DASHBOARD IS DISPLAYED AND THEN CLICK AND CLOSE THE SCREEN AND RETURN TRUE */
    		  Thread.sleep(1000);
    		  wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Dashboard_AddDash")));
    		  driver.findElement(By.id("Dashboard_AddDash")).click();
    		  Thread.sleep(1000);
    		  wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
    		  wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Create_Dash_Close")));
    		  driver.findElement(By.id("Create_Dash_Close")).click();
    		  Thread.sleep(1000);
    		  
    		  return true;
    	  }
      }
      catch(Exception e)
      {
    	  logger.info("Exception occured "+e.getMessage() );
    	  return false;
      }
	return false;
    
  }
	/* METHOD TO VERIFY THE MAIN MENUS ONCE AFTER LOGIN TO THE APPLICATION BY PASSING THE MENUNAMES FROM EXCEL AS PARAMETERS */
	public boolean menuDisplay(String excelname)
	{
		//SPLIT THE NAMES WHICH ARE SENT FROM EXCEL AS COMMA SEPERATED VALUES AND STORE IT TO AN ARRAYLIST
		ArrayList<String> excelnames=new ArrayList(Arrays.asList(excelname.split(",")));
		excelnames.replaceAll(String::trim);
		/* GETTING ALL THE MENU NAMES FROM APPLICATION INTO AN ARRAYLIST */
		menunames.clear();
		List<WebElement> menulist=driver.findElements(By.xpath("//ul[@id='navigation_menu']/li"));
		for(int i=1;i<=menulist.size();i++)
		{
			WebElement li=driver.findElement(By.xpath("//ul[@id='navigation_menu']/li["+i+"]"));
			WebElement div=li.findElement(By.cssSelector("div:nth-of-type(1)"));
			String menuname=div.getText();
			menunames.add(menuname);
		}
		/* COMPARE AND RETURN TRUE IF THE NAMES WHICH ARE SENT FROM EXCEL EQUALS WITH THE APPLICATION MENU NAMES */
		if(menunames.equals(excelnames))
		{
			return true;
		}
		return false;
	}

	/* METHOD TO CREATE NEW COMPANY BY PASSING COMPANYNAME AND PASSWORD FROM EXCEL AS PARAMETERS */
	public boolean companyCreation(String compname1, String password1) throws InterruptedException
	{
		expmsg="Company Created Successfully";
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("CompanyName")));
		long millis=System.currentTimeMillis();  
		java.sql.Date date=new java.sql.Date(millis);  
		driver.findElement(By.id("CompanyName")).sendKeys(compname1+date);
		driver.findElement(By.id("SUserPassword")).sendKeys(password1);
		driver.findElement(By.name("btnOk")).click();
		/* WAIT UNTILL ALERT POPS UP AND THEN GET THE ALERT TEXT AND ACCEPT THE ALERT */
		new WebDriverWait(driver, 800)
        .ignoring(NoAlertPresentException.class)
        .until(ExpectedConditions.alertIsPresent());
		al=driver.switchTo().alert();
		String actmsg=al.getText();
		compmsg=actmsg;
		al.accept();
		Thread.sleep(1000);
		/* TO VERIFY IF STILL COMPANY CREATION SCREEN IS GETTING DISPLAYED, IF SO CLOSE THAT SCREEN AND CONTINUE */
		try
		{
			if(driver.findElement(By.id("btnSignin")).isDisplayed())
			{
			
			}
			else
			{
			if(driver.findElement(By.linkText("Create Company")).isDisplayed())
			{
				logger.info("Yes displayed");
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='btn_common_header']/div[2]")));
				driver.findElement(By.xpath("//*[@id='btn_common_header']/div[2]")).click();
				Thread.sleep(1000);
			}
			}
		}
		catch(Exception e) 
		{
			logger.info("Not displayed");
		}
		/* TO VERIFY AND RETURN TRUE IF THE ALERT MESSAGE WHICH IS CAPTURED FROM APPLICATION CONTAINS EXPECTED MESSAGE AND RETURN TRUE IF SO */
		logger.info("acc "+actmsg + " exp "+expmsg+" ret "+actmsg.toLowerCase().contains(expmsg.toLowerCase())+" & eq "+actmsg.equals(expmsg));
		try 
		{
			if(actmsg.toLowerCase().contains(expmsg.toLowerCase()))
			{
				
				return true;
			
			}
		}
		catch(Exception e)
		{
			logger.info("No alert found Exception "+e.getMessage());
		}
		
		 return false;

	}
	/* METHOD TO LOGOUT FROM THE APPLICATION */
	public boolean Logout() throws InterruptedException
	{
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='mainHeader_MainLayout']/nav/div/ul/li[5]/a/img")));
		driver.findElement(By.xpath("//*[@id='mainHeader_MainLayout']/nav/div/ul/li[5]/a/img")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='userprofile']/li/span[2]")));
		driver.findElement(By.xpath("//*[@id='userprofile']/li/span[2]")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSignin")));
		/* VERIFY IF SIGIN SCREEN IS GETTING DISPLAYED AFTER LOGOUT FROM THE SCREEN AND RETURN TRUE IF SO */
		WebElement signIn = driver.findElement(By.id("btnSignin"));
		if(signIn.isDisplayed())
		{
			return true;
			
		}
		return false;
		
	}
	
	

}
