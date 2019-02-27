package com.teyseer.com.library;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import com.focus.constants.HomePage;
import com.focus.constants.LaunchApplication;
import com.focus.constants.Menus;


public class ExchangeRateDefinition  extends LaunchApplication
{
	
	Menus mp=new Menus();
	HomePage hp=new HomePage();
	long slowkeys=200;
	 public String actmsg = "null";
	/*
  @Test
  public void login() throws InterruptedException
  {
	 hp.LoginApp("su", "su", "A0407");
		boolean res;
		res=mp.menuSelection("Home", "Masters", "Currency", "Exchange Rate", "Exchange Rate Definition");
		ArrayList<String> xlheadernames = new ArrayList(Arrays.asList("Base Currency", "With Effective Date"));
		ArrayList<String> xlheadervalues= new ArrayList(Arrays.asList("ANG", ""));
		exchRateHeader(xlheadernames, xlheadervalues);
		logger.info("res is "+res);
		ArrayList<String> xlbodynames = new ArrayList(Arrays.asList("Currency Name", "Defined As", "Rate", "Description"));
		ArrayList<String> xlbodyrvalues= new ArrayList(Arrays.asList("AED", "ANG", "6.45", ""));
		ArrayList<String> xlbodyrvalues2= new ArrayList(Arrays.asList("CHF", "CHF", "8.65", ""));
		exchRateBody("1", xlbodynames, xlbodyrvalues);
		exchRateBody("", xlbodynames, xlbodyrvalues2);
		exchrateSave("Exchange Rate saved successfully");
		driver.quit();
		
  }
  */
	/* METHOD TO ENTER DATA TO EXCHANGE RATE HEADER DEFINITION BY PASSING LABEL NAMES AND ITS VALUES AS PARAMETERS */
  public void exchRateHeader(ArrayList<String> xllabelnames, ArrayList<String> xlvalues) throws InterruptedException
  {
	 List<WebElement> headerrows=driver.findElements(By.xpath("//div[@id='formid']/div[2]/div"));
	 logger.info("Total rows are "+headerrows.size());
	 /* GO TO EACH ROW AND GET THE LABEL NAMES AND COMPARE IT WITH THE ONE WHICH IS SENT FROM EXCEL */
	 for(String xlname:xllabelnames)
	 {
	 for(int r=1; r<=headerrows.size();r++)
	 {
		 List<WebElement> headercols=driver.findElements(By.xpath("//div[@id='formid']/div[2]/div["+r+"]/div"));
		for(int c=1;c<headercols.size()-1;c+=2)
		 {
			 int colval=c+1;
			 WebElement headerelem=driver.findElement(By.xpath("//div[@id='formid']/div[2]/div["+r+"]/div["+c+"]/label"));
			 String headerelemname=headerelem.getAttribute("textContent");
			 //IF THE NAME WHCIH IS CAPTURED FROM APPLICATION EQUALS TO THE ONE WHICH IS SENT FROM EXCEL 
			 if(xlname.equalsIgnoreCase(headerelemname))
			 {
				 String value=xlvalues.get(xllabelnames.indexOf(xlname));
				 //THEN SEND THE RESPECTIVE VALUES TO THE FIELDS 
				 if(value.length()>0)
				 {
					 WebElement valuecol=driver.findElement(By.xpath("//div[@id='formid']/div[2]/div["+r+"]/div["+colval+"]"));
					 Thread.sleep(2000);
					 WebElement valueelem=valuecol.findElement(By.cssSelector("input:not([type='checkbox']):not([type='hidden'])"));
					 Thread.sleep(3000);
					 Actions act=new Actions(driver);
					  Action actionslist=act.
								moveToElement(valueelem).click()
								.doubleClick(valueelem)
								.build();
								actionslist.perform();
								//valueelem.click();
					for(int i=0;i<value.length();i++)
					 {
						char ch=value.charAt(i);
						 act.sendKeys(String.valueOf(ch)).build().perform();
						Thread.sleep(slowkeys);
					
					 }
					 act.sendKeys(Keys.TAB).build().perform();
					 Thread.sleep(1000);
					 break;
				 }
			 }
		 }
		 
	 }
	 }
	  
  }
  
  /* METHOD TO ENTER BODY TO THE EXCHANGE RATE BODY BY PASSING ROW NUMBER, LABEL NAMES AND ITS VALUES AS PARAMETERS */
  public void exchRateBody(String rowno, ArrayList<String> xllabelnames, ArrayList<String> xllabelvalues) throws InterruptedException
  {
	  List<WebElement> bodyelemslist = driver.findElements(By.xpath("//div[@id='divExchangeTable']/table[@id='ExchangeTable']/thead[@id='ExchangeTable_head']/tr[@id='ExchangeTable_row_heading']/th"));
	  String row=rowno;
	  RowLoop:
		  //IF THE ROW NO WHCIH IS SENT FROM EXCEL IS EMPTY, GET THE ROW NUMBER WHICH NEED TO BE ENTERED FROM APPLICATION
		  if(row.length()==0)
		  {
			List<WebElement> totbodyrows=driver.findElements(By.xpath("//div[@id='divExchangeTable']/table[@id='ExchangeTable']/tbody[@id='ExchangeTable_body']/tr"));
			for(int r=1;r<=totbodyrows.size();r++)
			{
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='divExchangeTable']/table[@id='ExchangeTable']/tbody[@id='ExchangeTable_body']/tr["+r+"]/td[2]")));
				WebElement bodyrow=driver.findElement(By.xpath("//div[@id='divExchangeTable']/table[@id='ExchangeTable']/tbody[@id='ExchangeTable_body']/tr["+r+"]/td[2]"));
				String firstcolvalue=bodyrow.getText();
				row=String.valueOf(r);
				if((firstcolvalue.length()==0)&&(bodyrow.getAttribute("textContent").trim().length()>=0))
				{
					if(!(bodyrow.getAttribute("textContent").trim().startsWith("sCode")))
					{
						break RowLoop;
					}
					
				}
				
			}
			
		  }
	 
	  /* GO TO EACH COLUMN AND GET THE LABEL NAMES AND COMPARE IT WITH THE ONE WHICH IS SENT FROM EXCEL */
	  for(String xllabelname: xllabelnames)
	  {
		  String value =xllabelvalues.get(xllabelnames.indexOf(xllabelname));
		 for(int c=2;c<=bodyelemslist.size();c++)
		  {
			  WebElement firstbodyelem= driver.findElement(By.xpath("//div[@id='divExchangeTable']/table[@id='ExchangeTable']/thead[@id='ExchangeTable_head']/tr[@id='ExchangeTable_row_heading']/th["+c+"]/div"));
			  String colname= firstbodyelem.getAttribute("textContent");
			 //IF THE LABEL NAME WHICH IS SENT FROM EXCEL EQUALS TO THE ONE WHICH IS CAPTURED FROM APPLICATION
			  if(colname.equalsIgnoreCase(xllabelname))
			  {
				  //THEN ENTER THE RESPECTIVE VALUE TO THE FIELD
				  if(value.length()>0)
				  {
				  	WebElement bodyelem=driver.findElement(By.xpath("//div[@id='divExchangeTable']/table[@id='ExchangeTable']/tbody[@id='ExchangeTable_body']/tr["+row+"]/td["+c+"]"));
				  	bodyelem.click();
					Actions act2=new Actions(driver);
					act2.moveToElement(bodyelem).doubleClick().perform();
					for(int i=0;i<value.length();i++)
					{
						char ch=value.charAt(i);
						act2.sendKeys(String.valueOf(ch)).build().perform();
						Thread.sleep(slowkeys);
					 }
					Thread.sleep(1000);
				  	break;  
					  
				  }
			 }
	 }
  }
}
  
  /* METHOD TO CLEAR EXCHANGE RATE DATA BY CLICKING ON CLEAR ICON */
  public void exchrateClear() throws InterruptedException
  {
	  wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
	  wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='divExchangeRate']/ul/li/span[3]")));
	  driver.findElement(By.xpath("//*[@id='divExchangeRate']/ul/li/span[3]")).click();
	 Thread.sleep(1000);
	  wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
	  List<WebElement> headerrows=driver.findElements(By.xpath("//div[@id='formid']/div[2]/div"));
		 logger.info("Total rows are "+headerrows.size());
		 for(int r=1; r<=headerrows.size();r++)
		 {
			 List<WebElement> headercols=driver.findElements(By.xpath("//div[@id='formid']/div[2]/div["+r+"]/div"));
			for(int c=1;c<headercols.size()-1;c+=2)
			 {
				 int colval=c+1;
				 WebElement headerelem=driver.findElement(By.xpath("//div[@id='formid']/div[2]/div["+r+"]/div["+c+"]/label"));
				 String headerelemname=headerelem.getAttribute("textContent");
				 WebElement valuecol=driver.findElement(By.xpath("//div[@id='formid']/div[2]/div["+r+"]/div["+colval+"]"));
				 Thread.sleep(2000);
				 WebElement valueelem=valuecol.findElement(By.cssSelector("input:not([type='checkbox']):not([type='hidden'])"));
				 Thread.sleep(3000);
				 logger.info(" valueelem "+valueelem.getAttribute("data-focustext"));
			 }
		 }
	  
  }
  
  /* METHOD TO SAVE EXCHANGE RATE DATA */
  public boolean exchrateSave(String expmsg) throws InterruptedException
  {
	  wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
	  Thread.sleep(3000);
	  wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@id='SaveData']"))));
	  driver.findElement(By.xpath("//*[@id='SaveData']")).click();
	  Thread.sleep(3000);
	  wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
	
	  try
	    {
		  /* TO VERIFY IF ANY GLOBAL ID IS DISPLAYING AND CLOSE THEM*/
		  	List<WebElement> globalidlist=driver.findElements(By.cssSelector("div[id='idGlobalError'] div, div[id='idGlobalError']"));
			logger.info("globalidlist "+globalidlist.size());
		  	if(globalidlist.size()>=1)
			  {
				WebElement popups=driver.findElement(By.xpath("//*[@id='idGlobalError']/div/table/tbody/tr/td[2]/div"));
		    	if(popups.findElement(By.xpath("//*[@id='idGlobalError']/div/table/tbody/tr/td[2]/div[2]")).isDisplayed())
		    		{
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='idGlobalError']/div/table/tbody/tr/td[2]/div[2]")));
		    	 		actmsg=driver.findElement(By.xpath("//*[@id='idGlobalError']/div/table/tbody/tr/td[2]/div[2]")).getText();
		    	 		driver.findElement(By.xpath("//*[@id='idGlobalError']/div/table/tbody/tr/td[3]/span")).click();
		    		}
		    	 
		    	
			}
	     }
	     catch(Exception e1)
	     {
	    	 
	     }
	 wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
	 logger.info("act msg "+actmsg);
	 if(actmsg.contains(expmsg))
	 {
		 logger.info("true");
		 return true;
	 }
	 logger.info("false");
	 return false;
	 
  }
  
  /* METHOD TO DELETE EXCHANGE RATE DATA BY PASSING THE EXPECTED MESSAGE WHICH NEED TO BE DISPLAYED AS PARAMETER */
  public boolean exchrateDelete(String expmsg)
  {
	   wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
	   wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("DeleteData"))));
	   driver.findElement(By.id("DeleteData")).click();
	   try
	   {
		   driver.switchTo().alert().accept();
	   }
	   catch(Exception e)
	   {
		   logger.info("exec");
	   }
	   try
	    {
		  /* TO VERIFY IF ANY GLOBAL ID IS DISPLAYING AND CLOSE THEM*/
		  	List<WebElement> globalidlist=driver.findElements(By.cssSelector("div[id='idGlobalError'] div, div[id='idGlobalError']"));
			logger.info("globalidlist "+globalidlist.size());
		  	if(globalidlist.size()>=1)
			  {
				WebElement popups=driver.findElement(By.xpath("//*[@id='idGlobalError']/div/table/tbody/tr/td[2]/div"));
		    	if(popups.findElement(By.xpath("//*[@id='idGlobalError']/div/table/tbody/tr/td[2]/div[2]")).isDisplayed())
		    		{
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='idGlobalError']/div/table/tbody/tr/td[2]/div[2]")));
		    	 		actmsg=driver.findElement(By.xpath("//*[@id='idGlobalError']/div/table/tbody/tr/td[2]/div[2]")).getText();
		    	 		driver.findElement(By.xpath("//*[@id='idGlobalError']/div/table/tbody/tr/td[3]/span")).click();
		    		}
		    	 
		    	
			}
	     }
	     catch(Exception e1)
	     {
	    	 
	     }
	   if(actmsg.equalsIgnoreCase(expmsg))
	   {
		   logger.info("true ");
		   return true;
	   }
	   return false;
  }
  
}
