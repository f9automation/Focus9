/*
package com.focus.library;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import com.focus.constants.HomePage;
import com.focus.constants.LaunchApplication;
import com.focus.constants.Menus;

public class MasterActions extends LaunchApplication
{
	public String name,code,optioncontrol,optioncontrolname,expmsg;
	
  
  public boolean masterSave(String name,String code, String optioncontrol, String optioncontrolname, String expmsg) throws InterruptedException 
  {
	  
	  WebElement sname=driver.findElement(By.id("sName"));
	  sname.clear();
	  sname.sendKeys(name);
	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sCode")));
	  WebElement scode=driver.findElement(By.id("sCode"));
	  scode.clear();
	  scode.sendKeys(code);
	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(optioncontrol)));
	  WebElement mySelectElement = driver.findElement(By.id(optioncontrol));
      Thread.sleep(1000);
      Select dropdown= new Select(mySelectElement);
      List<WebElement> list = dropdown.getOptions();
      List<String> text = new ArrayList<>();
      System.out.println("before loop"+text);
      for(int i=1; i<list.size(); i++) 
      {
    	  
    	  list.get(i).getText();
    	  String optionName = list.get(i).getText();
    	  System.out.println(optionName);
    	  if(optionName.contains(optioncontrolname))
    	  {
    		 
    		  list.get(i).click();
    		  
    	  }
       

      }
      
      /*wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Details")));
      List<WebElement> li = driver.findElements(By.linkText("Details"));
      if(li.size()>1)
      {
    	  li.get(1).click();
      }
      else
      {
    	  li.get(0).click();
      }
     
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnMasterSaveClick")));
      driver.findElement(By.id("btnMasterSaveClick")).click();
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/section/div[4]/div/table/tbody/tr/td[2]")));
      String actmsg=driver.findElement(By.xpath("/html/body/section/div[4]/div/table/tbody/tr/td[2]")).getText();
     try
      {
     	  //driver.findElement(By.xpath("/html/body/section/div[4]/div/table/tbody/tr/td[2]")).clear();
     	 
     	 WebElement popups=driver.findElement(By.xpath("/html/body/section/div[4]/div/table/tbody/tr/td[*]"));
     	 for(int i=3; i<=7; i++)
     	 {
     		 if(popups.findElement(By.xpath("/html/body/section/div[4]/div/table/tbody/tr/td["+i+"]/span")).isDisplayed())
     		 {
     		 driver.findElement(By.xpath("/html/body/section/div[4]/div/table/tbody/tr/td["+i+"]/span")).click();
     		 System.out.println("pop up closed by app");
     		 }
     	 
     	 }
     	  
      }
      catch(Exception e)
      {
     	 System.out.println("Popup already closed");
      }
      
      	Thread.sleep(3000);
      try
      {
       wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/section/div[2]/div/section[1]/div[1]/div/div[1]/div[2]/div[2]/div[2]/div[1]/div/div[1]/div[2]/div/ul/li[2]/button[4]")));
       driver.findElement(By.xpath("/html/body/section/div[2]/div/section[1]/div[1]/div/div[1]/div[2]/div[2]/div[2]/div[1]/div/div[1]/div[2]/div/ul/li[2]/button[4]")).click();
      }
      catch(Exception e)
      {
     	 System.out.println("Button not available");
     	 try
         {
        	  //driver.findElement(By.xpath("/html/body/section/div[4]/div/table/tbody/tr/td[2]")).clear();
        	 
        	 WebElement popups=driver.findElement(By.xpath("/html/body/section/div[4]/div/table/tbody/tr/td[*]"));
        	 for(int i=3; i<=7; i++)
        	 {
        		 if(popups.findElement(By.xpath("/html/body/section/div[4]/div/table/tbody/tr/td["+i+"]/span")).isDisplayed())
        		 {
        		 driver.findElement(By.xpath("/html/body/section/div[4]/div/table/tbody/tr/td["+i+"]/span")).click();
        		 System.out.println("pop up closed by app");
        		 }
        	 
        	 }
        	  
         }
         catch(Exception ex)
         {
        	 System.out.println("Popup already closed");
         }
      }
       try
       {
       if(actmsg.toLowerCase().contains(expmsg.toLowerCase()))
       {
     	  return true;
       }
       
       }
       catch(Exception e)
       {
     	  System.out.println(actmsg);
     	  return false;
       }
      
       return false;
   }
   

  public boolean masterSave(String name,String code, String expmsg) throws InterruptedException 
  {
	  //masterNew();
	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sName")));
	  WebElement sname=driver.findElement(By.id("sName"));
	  sname.clear();
	  sname.sendKeys(name);
	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sCode")));
	  WebElement scode=driver.findElement(By.id("sCode"));
	  scode.clear();
	  scode.sendKeys(code);
	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnMasterSaveClick")));
      driver.findElement(By.id("btnMasterSaveClick")).click();
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/section/div[4]/div/table/tbody/tr/td[2]")));
      String actmsg=driver.findElement(By.xpath("/html/body/section/div[4]/div/table/tbody/tr/td[2]")).getText();
     try
     {
    	 WebElement popups=driver.findElement(By.xpath("/html/body/section/div[4]/div/table/tbody/tr/td[*]"));
    	 for(int i=3; i<=7; i++)
    	 {
    		 if(popups.findElement(By.xpath("/html/body/section/div[4]/div/table/tbody/tr/td["+i+"]/span")).isDisplayed())
    		 {
    		 driver.findElement(By.xpath("/html/body/section/div[4]/div/table/tbody/tr/td["+i+"]/span")).click();
    		 System.out.println("pop up closed by app");
    		 }
    	 
    	 }
    	  
     }
     catch(Exception e)
     {
    	 System.out.println("Popup already closed");
     }
     
     	Thread.sleep(3000);
     try
     {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/section/div[2]/div/section[1]/div[1]/div/div[1]/div[2]/div[2]/div[2]/div[1]/div/div[1]/div[2]/div/ul/li[2]/button[4]")));
      driver.findElement(By.xpath("/html/body/section/div[2]/div/section[1]/div[1]/div/div[1]/div[2]/div[2]/div[2]/div[1]/div/div[1]/div[2]/div/ul/li[2]/button[4]")).click();
     }
     catch(Exception e)
     {
    	 System.out.println("Button not available");
    	 try
         {
        	  //driver.findElement(By.xpath("/html/body/section/div[4]/div/table/tbody/tr/td[2]")).clear();
        	 
        	 WebElement popups=driver.findElement(By.xpath("/html/body/section/div[4]/div/table/tbody/tr/td[*]"));
        	 for(int i=3; i<=7; i++)
        	 {
        		 if(popups.findElement(By.xpath("/html/body/section/div[4]/div/table/tbody/tr/td["+i+"]/span")).isDisplayed())
        		 {
        		 driver.findElement(By.xpath("/html/body/section/div[4]/div/table/tbody/tr/td["+i+"]/span")).click();
        		 System.out.println("pop up closed by app");
        		 }
        	 
        	 }
        	  
         }
         catch(Exception ex)
         {
        	 System.out.println("Popup already closed");
         }
     }
      try
      {
      if(actmsg.toLowerCase().contains(expmsg.toLowerCase()))
      {
    	  return true;
      }
      
      }
      catch(Exception e)
      {
    	  System.out.println(actmsg);
    	  return false;
      }
      
      return false;
  }
  
   
  
}
*/
