package com.focus.constants;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

public class Menus extends LaunchApplication 
{
	
	public String menu1,menu2,menu3,menu4,value1,expname,actname;
	
	
  @Test
  /* METHOD TO NAVIGATE TO THE TREE MENU BY PROVIDING THE MENU'S LIST FROM EXCEL AS PARAMETERS */
  public boolean menuSelection(String menu1, String menu2, String menu3, String menu4,String expname) throws InterruptedException 
  {
	  String masterattribute;
	  int expandcollapsesize;
	  wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
	  wait.until(ExpectedConditions.elementToBeClickable(By.linkText(menu1)));
	  driver.findElement(By.linkText(menu1)).click();
	  Thread.sleep(1000);
	  {
		  wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(menu2)));
		  WebElement wmenu2=driver.findElement(By.linkText(menu2));
		  try
		  {
			  masterattribute=wmenu2.getAttribute("id");
			  expandcollapsesize=wmenu2.findElements(By.xpath("//a[@id='"+masterattribute+"']/i")).size();
		  }
		  catch(Exception e)
		  {
			  expandcollapsesize=0;
		  }
		  if(expandcollapsesize>0)
		  {
			 WebElement expcoll=wmenu2.findElement(By.cssSelector("i:first-of-type"));
			 String expcollattr=expcoll.getAttribute("class");
			 if(expcollattr.equalsIgnoreCase("fa fa-angle-down pull-right"))
			 {
				 //DO NOTHING
				
			 }
			 else
			 {
				 wmenu2.click();
				 Thread.sleep(1000);
				
			 }
	
		  }
	  }
	  {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(menu3)));
		WebElement wmenu3=driver.findElement(By.linkText(menu3));
		try
		{
			masterattribute=wmenu3.getAttribute("id");
			expandcollapsesize=wmenu3.findElements(By.xpath("//a[@id='"+masterattribute+"']/i")).size();
		}
		catch(Exception e)
		{
			expandcollapsesize=0;
		}
		if(expandcollapsesize>0)
		{
			WebElement expcoll=wmenu3.findElement(By.cssSelector("i:first-of-type"));
			String expcollattr=expcoll.getAttribute("class");
			if(expcollattr.equalsIgnoreCase("fa fa-angle-down pull-right"))
			{
				//DO NOTHING
			}
			else
			{ 
				wmenu3.click();
				Thread.sleep(1000);
			}
		}
	  }
	  {
		  wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(menu4)));
		  List<WebElement> li = driver.findElements(By.linkText(menu4));
		  WebElement wmenu4= driver.findElement(By.linkText(menu4));
		  if(li.size()>=1)
		  {
			  li.get(li.size()-1).click();
			  Thread.sleep(1000);
			  
		  }
		  else
		  {
			  li.get(0).click();
			  Thread.sleep(1000);
		  }
		  
		}
	  
	  	Thread.sleep(1000);
	  	String actname;
	  	/* CAPTURE THE VOUCHER/PAGE'S NAME */
	  	try
	  	{
	  		actname=driver.findElement(By.xpath("//div[@class='navText']/span[2]")).getText();
	  		logger.info(actname+" act ");
	  	}
		catch(Exception e)
	  	{
			actname=driver.findElement(By.xpath("/html/body/section/div[2]/div/section[1]/div/div[1]/nav/div[1]/a/span/span[2]")).getText();
			logger.info(actname+" catch act ");
		}
	  	/* VERIFY AND RETURN TRUE IF THE NAME WHICH IS SENT FROM EXCEL CONTAINS THE NAME WHICH IS CAPTURED FROM APPLICATION */
	  	try
	  	{
	  		logger.info("exp msg "+expname+" actmsg "+actname);
	  		
	  		if(expname.toLowerCase().equals(actname.toLowerCase()))
	  		{
			  return true;
	  		}
		  
	  	}
	  	catch(Exception e)
	  	{
	  		logger.info("Exception message is "+e.getMessage());
	  		return false;
	  	}
	  
	  return false;
  }
	 
  /* METHOD TO NAVIGATE TO THE TREE MENU BY PROVIDING THE MENU'S LIST FROM EXCEL AS PARAMETERS */
 public boolean menuSelection(String menu1, String menu2, String menu3,String expname) throws InterruptedException 
  {
	 String masterattribute;
	 int expandcollapsesize;
	 wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
	 wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(menu1)));
	 driver.findElement(By.linkText(menu1)).click();
	 Thread.sleep(1000);
	 {
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(menu2)));
		 WebElement wmenu2=driver.findElement(By.linkText(menu2));
		 try
		 {
			 masterattribute=wmenu2.getAttribute("id");
			 expandcollapsesize=wmenu2.findElements(By.xpath("//a[@id='"+masterattribute+"']/i")).size();
		 }
		 catch(Exception e)
		 {
			 expandcollapsesize=0;
		 }
		 if(expandcollapsesize>0)
		 {
			 WebElement expcoll=wmenu2.findElement(By.cssSelector("i"));
			 String expcollattr=expcoll.getAttribute("class"); 
			 if(expcollattr.equalsIgnoreCase("fa fa-angle-down pull-right"))
			 {
				//DO NOTHING
			 }
			 else
			 {
				 wmenu2.click();
			 }
		 }
	}
	{
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(menu3)));
		driver.findElement(By.linkText(menu3)).click();
		Thread.sleep(1000);
	}
	/* CAPTURE THE VOUCHER/PAGE'S NAME */
	String actname;
	try
	{
		actname=driver.findElement(By.xpath("//div[@class='navText']/span[2]")).getText();
	}
	catch(Exception e)
	{
		actname=driver.findElement(By.xpath("/html/body/section/div[2]/div/section[1]/div/div[1]/nav/div[1]/a/span/span[2]")).getText();
	}
	/* VERIFY AND RETURN TRUE IF THE NAME WHICH IS SENT FROM EXCEL CONTAINS THE NAME WHICH IS CAPTURED FROM APPLICATION */
	try
	{
		if(expname.toLowerCase().contains(actname.toLowerCase()))
		{
			  return true;
		}
		  
	 }
	 catch(Exception e)
	 {
		 logger.info("Exception message "+e.getMessage());
		 return false;
	 }
	  
	  return false;
  }
 /* METHOD TO NAVIGATE TO THE TREE MENU BY PROVIDING THE MENU'S LIST FROM EXCEL AS PARAMETERS */
 public boolean menuSelection(String menu1, String menu2, String expname) throws InterruptedException 
 {
	 String masterattribute;
	 int expandcollapsesize;
	 wait.until(ExpectedConditions.elementToBeClickable(By.linkText(menu1)));
	 driver.findElement(By.linkText(menu1)).click();
	 Thread.sleep(1000);
	 wait.until(ExpectedConditions.elementToBeClickable(By.linkText(menu2)));
	 driver.findElement(By.linkText(menu2)).click();
	 Thread.sleep(1000);
	 /* CAPTURE THE VOUCHER/PAGE'S NAME */
	 String actname;
	 try
	 {
		 actname=driver.findElement(By.xpath("//div[@class='navText']/span[2]")).getText();
	 }
	 catch(Exception e)
	 {
		 actname=driver.findElement(By.xpath("/html/body/section/div[2]/div/section[1]/div/div[1]/nav/div[1]/a/span/span[2]")).getText();
	 }
	 /* VERIFY AND RETURN TRUE IF THE NAME WHICH IS SENT FROM EXCEL CONTAINS THE NAME WHICH IS CAPTURED FROM APPLICATION */
	 try
	 {
		 if(expname.toLowerCase().contains(actname.toLowerCase()))
		  {
			  return true;
		  }
		  
	 }
	 catch(Exception e)
	 {
		  logger.info("Exception message "+e.getMessage());
		  return false;
	 }
	  
	  return false;
 }
}
