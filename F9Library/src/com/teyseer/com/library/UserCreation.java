package com.teyseer.com.library;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import com.focus.constants.LaunchApplication;
import com.focus.constants.Menus;

public class UserCreation extends LaunchApplication {
	double slowkeys=200;
	public String actmsg;
	//Menus menu=new Menus();
  @Test
  public boolean createUSer(String name, String password, String securityans, String email, String expmsg) throws InterruptedException 
  {
	// menu.menuSelection("Home", "Security", ", expmsg)
	  wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
	  wait.until(ExpectedConditions.elementToBeClickable(By.id("createUserBtn")));
	  driver.findElement(By.id("createUserBtn")).click();
	  wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
	  wait.until(ExpectedConditions.elementToBeClickable(By.id("loginName")));
	  WebElement loginname=driver.findElement(By.id("loginName"));
	  loginname.click();
	  for (int i=0; i<name.length(); i++)
		{	
			char ch=name.charAt(i);
			loginname.sendKeys(String.valueOf(ch));
			
			Thread.sleep((long)slowkeys);
		}
	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ERPRoles")));
	  WebElement ele=driver.findElement(By.id("ERPRoles"));
	  ele.click();
	  Select dropdown= new Select(ele);
		List<WebElement> list = dropdown.getOptions();
		for(int selectelem=0; selectelem<list.size();selectelem++)
		{
			String listelements = list.get(selectelem).getText();
			if(listelements.contains("Accounts Manager"))
				{
					list.get(selectelem).click();
				}
		}
		
	  wait.until(ExpectedConditions.elementToBeClickable(By.id("password")));
	  WebElement pwd=driver.findElement(By.id("password"));
	  pwd.click();
	  for (int i=0; i<password.length(); i++)
		{	
			char ch=password.charAt(i);
			pwd.sendKeys(String.valueOf(ch));
			
			Thread.sleep((long)slowkeys);
		}
	  wait.until(ExpectedConditions.elementToBeClickable(By.id("confirmpassword")));
	  WebElement cnfpwd=driver.findElement(By.id("confirmpassword"));
	  cnfpwd.click();
	  for (int i=0; i<password.length(); i++)
		{	
			char ch=password.charAt(i);
			cnfpwd.sendKeys(String.valueOf(ch));
			
			Thread.sleep((long)slowkeys);
		}
	  wait.until(ExpectedConditions.elementToBeClickable(By.id("Username")));
	  WebElement Username=driver.findElement(By.id("Username"));
	  Username.click();
	  for (int i=0; i<name.length(); i++)
		{	
			char ch=name.charAt(i);
			Username.sendKeys(String.valueOf(ch));
			
			Thread.sleep((long)slowkeys);
		}
	  wait.until(ExpectedConditions.elementToBeClickable(By.id("lginAbbr")));
	  WebElement loginAbbr=driver.findElement(By.id("lginAbbr"));
	  loginAbbr.click();
	  for (int i=0; i<name.length(); i++)
		{	
			char ch=name.charAt(i);
			loginAbbr.sendKeys(String.valueOf(ch));
			
			Thread.sleep((long)slowkeys);
		}
	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("SecurityQues")));
	  WebElement ele2=driver.findElement(By.id("SecurityQues"));
	  ele2.click();
	  Select dropdown2= new Select(ele2);
		List<WebElement> list2 = dropdown2.getOptions();
		for(int selectelem=0; selectelem<list2.size();selectelem++)
		{
			String listelements = list2.get(selectelem).getText();
			if(listelements.contains("What was your childhood nickname"))
				{
					list2.get(selectelem).click();
				}
		}
	  
		wait.until(ExpectedConditions.elementToBeClickable(By.id("SecurityAns")));
		  WebElement SecurityAns=driver.findElement(By.id("SecurityAns"));
		  SecurityAns.click();
		  for (int i=0; i<securityans.length(); i++)
			{	
				char ch=securityans.charAt(i);
				SecurityAns.sendKeys(String.valueOf(ch));
				
				Thread.sleep((long)slowkeys);
			}
	  wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/section/div[2]/div/section[1]/div[1]/div[2]/div[2]/ul/li[2]/a/span")));
	  driver.findElement(By.xpath("/html/body/section/div[2]/div/section[1]/div[1]/div[2]/div[2]/ul/li[2]/a/span")).click();
	  wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
	  
	  wait.until(ExpectedConditions.elementToBeClickable(By.id("emailId")));
	  WebElement emailId=driver.findElement(By.id("emailId"));
	  emailId.click();
	  for (int i=0; i<email.length(); i++)
		{	
			char ch=email.charAt(i);
			emailId.sendKeys(String.valueOf(ch));
			
			Thread.sleep((long)slowkeys);
		}
	  
	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("typeOfUser")));
	  WebElement ele3=driver.findElement(By.id("typeOfUser"));
	  ele3.click();
	  Select dropdown3= new Select(ele3);
		List<WebElement> list3 = dropdown3.getOptions();
		for(int selectelem=0; selectelem<list3.size();selectelem++)
		{
			String listelements = list3.get(selectelem).getText();
			if(listelements.contains("Employee"))
				{
					list3.get(selectelem).click();
				}
		}
	  
	  Thread.sleep(1000);
	  wait.until(ExpectedConditions.elementToBeClickable(By.id("btnSave")));
	  driver.findElement(By.id("btnSave")).click();
	  wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
	  Thread.sleep(3000);
	  try
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/section/div[4]/div/table/tbody/tr/td[2]")));
	 		 actmsg=driver.findElement(By.xpath("/html/body/section/div[4]/div/table/tbody/tr/td[2]")).getText();
	 		logger.info("Actmsg "+actmsg);
	 	}
		catch(Exception ee2)
		{
			
		}
		try
	    {
			WebElement popups=driver.findElement(By.xpath("/html/body/section/div[4]/div/table/tbody/tr/td[*]"));
	    	for(int i=3; i<=7; i++)
	    	{
	    		if(popups.findElement(By.xpath("/html/body/section/div[4]/div/table/tbody/tr/td["+i+"]/span")).isDisplayed())
	    		{
	    			driver.findElement(By.xpath("/html/body/section/div[4]/div/table/tbody/tr/td["+i+"]/span")).click();
	    		}
	    	 
	    	}
	    	  
	     }
	     catch(Exception e1)
	     {
	    	 
	     }
		Thread.sleep(5000);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("CancelCreateUser")));
		Thread.sleep(5000);
		if(actmsg.contains(expmsg))
		{
			return true;
		}
		return false;
	  
  }
}
