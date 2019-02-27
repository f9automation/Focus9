package com.focus.library;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.SendKeysAction;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import com.focus.constants.HomePage;
import com.focus.constants.LaunchApplication;
import com.focus.constants.Menus;



public class MasterHomePage extends LaunchApplication {
  
	public String expcode, actmsg;
	/* METHOD TO CLICK ON NEW ICON FROM THE MASTER SCREEN */
	public boolean masterNew() throws InterruptedException
	{
		wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnNew")));
		driver.findElement(By.id("btnNew")).click();
		Thread.sleep(5000);
		/* TO VERIFY AND RETURN TRUE IF SAVE ICON IS DISPLAYED */
		wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[id='btnMasterSaveClick']")));
		driver.findElement(By.cssSelector("button[id='btnMasterSaveClick']"));
		if(driver.findElement(By.cssSelector("button[id='btnMasterSaveClick']")).isDisplayed())
		{
			return true;
		}
		return false;
	}
	/* METHOD TO CLICK ON ADD NEW GROPU ICON FROM THE MASTER SCREEN */
	public boolean masterAddGroup() throws InterruptedException
	{
		wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnAddGroup")));
		driver.findElement(By.id("btnAddGroup")).click();
		Thread.sleep(5000);
		wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
		/* TO VERIFY AND RETURN TRUE IF SAVE ICON IS DISPLAYED */
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[id='btnMasterSaveClick']")));
		driver.findElement(By.cssSelector("button[id='btnMasterSaveClick']"));
		if(driver.findElement(By.cssSelector("button[id='btnMasterSaveClick']")).isDisplayed())
		{
			return true;
		}
		return false;
		
	}
	/* METHOD TO CLOSE THE MASTER SCREEN */
	public boolean masterClose() throws InterruptedException
	{
		wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("span[id='btnClose']")));
		driver.findElement(By.cssSelector("span[id='btnClose']")).click();
		wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
		/* TO VERIFY AND RETURN TRUE IF DASHBOARD ICON IS DISPLAYED AFTER CLOSING THE MASTER */
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@id='dashName']"))); 
		wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
		if(driver.findElement(By.xpath("//*[@id='dashName']")).isDisplayed())
		{
			return true;
		}
		  return false;
		
	}
	
	/* METHOD TO SELECT THE MASTER BASED ON CODE, BY PASSING THE CODE VALUE AS MASTER FROM EXCEL */
	public boolean masterSelection(String expcode) throws InterruptedException
	{
		wait.until(ExpectedConditions.elementToBeClickable(By.id("btnSearchAcc1")));
		driver.findElement(By.id("btnSearchAcc1")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Search on")));
		driver.findElement(By.linkText("Search on")).click();
		
		/* SELECT CODE TO FILTER ON IT FROM SEARCH ON OPTION */
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ddlSelectFilter")));
	      WebElement mySelectElement = driver.findElement(By.id("ddlSelectFilter"));
	      Thread.sleep(1000);
	      Select dropdown= new Select(mySelectElement);
	      List<WebElement> list = dropdown.getOptions();
	      List<String> text = new ArrayList<>();
	     for(int k=0; k<list.size(); k++) 
	      {
	    	  list.get(k).getText();
	    	  String optionName = list.get(k).getText();
	    	if(optionName.equals("="))
	    	  {
	    		list.get(k).click();
	    	  }
	     }
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='divSearchMenu']/div[3]/div/ol/li[3]/label/input")));
		WebElement codeselect=driver.findElement(By.xpath("//div[@id='divSearchMenu']/div[3]/div/ol/li[3]/label/input"));
		if(!(codeselect.isSelected()))
		{
			codeselect.click();
		}
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/section/div[2]/div/section[1]/div[1]/div/div[1]/div[2]/div[4]/div[4]/button")));
		WebElement okclick=driver.findElement(By.xpath("/html/body/section/div[2]/div/section[1]/div[1]/div/div[1]/div[2]/div[4]/div[4]/button"));
		Actions acta=new Actions(driver);
		acta.moveToElement(okclick).click();
		
		/* SEARCHING FOR THE CODE AND THEN SELECT THE MATCHED ONE */
		wait.until(ExpectedConditions.elementToBeClickable(By.id("txtsrch-term")));
		WebElement search=driver.findElement(By.id("txtsrch-term"));
		search.clear();
		Thread.sleep(1000);
		search.click();
		search.sendKeys(expcode,Keys.ENTER);
		Thread.sleep(3000);

		/* ONCE ENTERED DATA IN SEARCH FIELD AND CLICKED ENTER, GO TO EACH ROW AND OBSERVE THE CODE AND THEN SELECT THE MATCHED ONE  */
		int code=0, selectchckbox=0;
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table[id='landgridData']")));
		WebElement mastertable=driver.findElement(By.cssSelector("table[id='landgridData']"));
		List<WebElement> headerlist=mastertable.findElements(By.cssSelector("table[id='landgridData'] thead tr th"));
		WebElement headertable=mastertable.findElement(By.cssSelector("table[id='landgridData'] thead tr th"));
		for(int col=2;col<=headerlist.size();col++)
		{
			wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table[id='landgridData'] thead tr th:nth-of-type("+col+") span")));
			WebElement header=driver.findElement(By.cssSelector("table[id='landgridData'] thead tr th:nth-of-type("+col+") span"));
			String headername=header.getText();
			if(headername.equalsIgnoreCase("code"))
			{
				code=col;
			}
			if(headername.equalsIgnoreCase("Select.."))
			{
				selectchckbox=col;
			}
			
		}
		List<WebElement> bodylist=mastertable.findElements(By.cssSelector("table[id='landgridData'] tbody tr"));
		for(int row=1;row<=bodylist.size();row++)
		{
			wait.until(ExpectedConditions.visibilityOf(mastertable.findElement(By.xpath("//table[@id='landgridData']/tbody/tr["+row+"]/td[not(contains(@class, 'hidden'))]["+code+"]"))));
			WebElement  bodyelems=mastertable.findElement(By.xpath("//table[@id='landgridData']/tbody/tr["+row+"]/td[not(contains(@class, 'hidden'))]["+code+"]"));
			String mastercode=bodyelems.getText();
			if(mastercode.equalsIgnoreCase(expcode))
			{
				wait.until(ExpectedConditions.visibilityOf(mastertable.findElement(By.xpath("//table[@id='landgridData']/tbody/tr["+row+"]/td[not(contains(@class, 'hidden'))]["+selectchckbox+"]"))));
				mastertable.findElement(By.xpath("//table[@id='landgridData']/tbody/tr["+row+"]/td[not(contains(@class, 'hidden'))]["+selectchckbox+"]")).click();
				return true;
			}
			
		}
		return false;
		
	}
	
	//@Test
	
	/* METHOD TO EDIT THE SELECTED MASTER */
	public boolean masterEdit(String expcode) throws InterruptedException
	{
		
		boolean res;
		res=masterSelection(expcode);
		/* IF MASTER IS SELECTED THEN IT WILL EDIT THE RESPECTIVE MASTER */
		if(res==true)
		{
			wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnMasterEdit")));
			driver.findElement(By.id("btnMasterEdit")).click();
			wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
			/* VERIFY AND RETURN TRUE IF SAVE BUTTON IS DISPLAYED AFTER CLICKING ON EDIT ICON */
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnMasterSaveClick")));
			try
			{
				if(driver.findElement(By.id("btnMasterSaveClick")).isDisplayed())
				 {
					return true;
					
				 }
			}
			catch(Exception e)
			 {
				return false;
			 }
		}
		/* IF THE RESPECTIVE MASTER WHICH WE ARE SEARCHING IS NOT DISPLAYED THEN IT WILL CLOSE THE MASTER'S SEARCH CRITERIA */
		else
		{
			wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnClose")));
			driver.findElement(By.id("btnClose")).click();
			wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
			return false;
		}
		return false;
	}
	
	/* METHOD TO UPDATE THE MASTER BY PASSING THE EXPECTED MESSAGE AFTER SAVING AS PARAMETER FROM EXCEL */
	public boolean masterUpdate(String expmsg) throws InterruptedException
	{
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[id='btnMasterSaveClick']")));
		WebElement save=driver.findElement(By.cssSelector("button[id='btnMasterSaveClick']"));
		save.click();
		try
	    {
		  /* TO VERIFY IF ANY GLOBAL ID IS DISPLAYING AND CLOSE THEM */
		  	List<WebElement> globalidlist=driver.findElements(By.cssSelector("div[id='idGlobalError'] div, div[id='idGlobalError']"));
			if(globalidlist.size()>1)
			  {
				WebElement popups=driver.findElement(By.xpath("//*[@id='idGlobalError']/div/table/tbody/tr/td[2]/div"));
		    	if(popups.findElement(By.xpath("//*[@id='idGlobalError']/div/table/tbody/tr/td[2]/div[2]")).isDisplayed())
		    		{
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='idGlobalError']/div/table/tbody/tr/td[2]/div[2]")));
		    	 		actmsg=driver.findElement(By.xpath("//*[@id='idGlobalError']/div/table/tbody/tr/td[2]/div[2]")).getText();
		    	 		driver.findElement(By.xpath("//*[@id='idGlobalError']/div/table/tbody/tr/td[3]")).click();
		    		}
		    	 
		    	
			}
	     }
	     catch(Exception e1)
	     {
	    	 
	     }
		
		
		Thread.sleep(3000);
		/* AFTER UPDATING CLOSE THE UPDATE SCREEN BY CLICKING ON CLOSE BUTTON */
		try
		{
			if((save.isDisplayed()))
			{
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[title='Close']")));
				driver.findElement(By.cssSelector("button[title='Close']")).click();
				wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/section/div[2]/div/section[1]/div[1]/div/div[1]/div[2]/div[2]/div[1]/div/div/div[2]/div/div/div/div[3]/ul/li/a[2]")));
				driver.findElement(By.xpath("/html/body/section/div[2]/div/section[1]/div[1]/div/div[1]/div[2]/div[2]/div[1]/div/div/div[2]/div/div/div/div[3]/ul/li/a[2]")).click();
				wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
			 }
		 }
		catch(Exception e)
		{
			//logger.info("Stale element exception ");
		}
		if(actmsg.toLowerCase().contains(expmsg.toLowerCase()))
		{
			return true;
		}
		return false;
	}
	
	/* METHOD TO SAVE THE MASTER BY PASSING THE EXPECTED MESSAGE AFTER SAVING AS PARAMETER FROM EXCEL */
	public boolean masterSave(String expmsg) throws InterruptedException 
	{
		wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[id='btnMasterSaveClick']")));
		driver.findElement(By.cssSelector("button[id='btnMasterSaveClick']")).click();
		try
	    {
		  /* TO VERIFY IF ANY GLOBAL ID IS DISPLAYING AND CLOSE THEM IF SO */
		  	List<WebElement> globalidlist=driver.findElements(By.cssSelector("div[id='idGlobalError'] div, div[id='idGlobalError']"));
			if(globalidlist.size()>1)
			  {
				WebElement popups=driver.findElement(By.xpath("//*[@id='idGlobalError']/div/table/tbody/tr/td[2]/div"));
		    	if(popups.findElement(By.xpath("//*[@id='idGlobalError']/div/table/tbody/tr/td[2]/div[2]")).isDisplayed())
		    		{
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='idGlobalError']/div/table/tbody/tr/td[2]/div[2]")));
		    	 		actmsg=driver.findElement(By.xpath("//*[@id='idGlobalError']/div/table/tbody/tr/td[2]/div[2]")).getText();
		    	 		driver.findElement(By.xpath("//*[@id='idGlobalError']/div/table/tbody/tr/td[3]")).click();
		    		}
		    	 
		    	
			}
	     }
	     catch(Exception e1)
	     {
	    	 
	     }
		
	     Thread.sleep(3000);
	     wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
	     
	     /* AFTER SAVING/UPDATING THE MASTER, CLOSE THE MASTER SAVE/UPDATE SCREEN BY CLICKING ON CLOSE BUTTON */
	     wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[title='Close']")));
	     driver.findElement(By.cssSelector("button[title='Close']")).click();
	     wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
	    try
	      {
	      if(actmsg.toLowerCase().contains(expmsg.toLowerCase()))
	      {
	    	  return true;
	      }
	      
	      }
	      catch(Exception e)
	      {
	    	 return false;
	      }
	      
	      return false;
	     
	
	}
	/* METHOD TO NAVIGATE TO THE NODE IN WHICH THE TREE IS PROVIDED AS ARRAYLIST PARAMETER FROM EXCEL */
	public boolean masterNode(ArrayList<String> nodes) throws InterruptedException
	{
		for(int i=0;i<nodes.size();i++)
		{
			/* FILTER ON CODE IN SEARCH DIALOG */
			wait.until(ExpectedConditions.elementToBeClickable(By.id("btnSearchAcc1")));
			driver.findElement(By.id("btnSearchAcc1")).click();
			wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Search on")));
			driver.findElement(By.linkText("Search on")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ddlSelectFilter")));
		    WebElement mySelectElement = driver.findElement(By.id("ddlSelectFilter"));
		    Thread.sleep(1000);
		    Select dropdown= new Select(mySelectElement);
		    List<WebElement> list = dropdown.getOptions();
		    List<String> text = new ArrayList<>();
		    for(int k=0; k<list.size(); k++) 
		    {
		    	list.get(k).getText();
		    	String optionName = list.get(k).getText();
		    	if(optionName.equals("="))
		    	{
		    		list.get(k).click();
		    	}
		      
		    }
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='divSearchMenu']/div[3]/div/ol/li[3]/label/input")));
			WebElement codeselect=driver.findElement(By.xpath("//div[@id='divSearchMenu']/div[3]/div/ol/li[3]/label/input"));
			if(!(codeselect.isSelected()))
			{
				codeselect.click();
			}
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/section/div[2]/div/section[1]/div[1]/div/div[1]/div[2]/div[4]/div[4]/button")));
			WebElement okclick=driver.findElement(By.xpath("/html/body/section/div[2]/div/section[1]/div[1]/div/div[1]/div[2]/div[4]/div[4]/button"));
			Actions acta=new Actions(driver);
			acta.moveToElement(okclick).click();
			/* SELECT THE ROW WHICH IS BEING SEARCHED AND MATCHED WITH THE CODE PROVIDED */
			wait.until(ExpectedConditions.elementToBeClickable(By.id("txtsrch-term")));
			WebElement search=driver.findElement(By.id("txtsrch-term"));
			search.clear();
			Thread.sleep(1000);
			search.click();
			String valfound = "";
			search.sendKeys(nodes.get(i),Keys.ENTER);
			Thread.sleep(3000);
			/* ONCE AFTER ENTERING THE CODE TO SEARCH FOR AND CLICKED ON ENTER, SELECT THE ROW WHICH MATCHES WITH THE PROVIDED CODE */
			int code=0, selectchckbox=0;
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table[id='landgridData']")));
			WebElement mastertable=driver.findElement(By.cssSelector("table[id='landgridData']"));
			List<WebElement> headerlist=mastertable.findElements(By.cssSelector("table[id='landgridData'] thead tr th"));
			WebElement headertable=mastertable.findElement(By.cssSelector("table[id='landgridData'] thead tr th"));
			for(int col=2;col<=headerlist.size();col++)
			{
				wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table[id='landgridData'] thead tr th:nth-of-type("+col+") span")));
				WebElement header=driver.findElement(By.cssSelector("table[id='landgridData'] thead tr th:nth-of-type("+col+") span"));
				String headername=header.getText();
				if(headername.equalsIgnoreCase("code"))
				{
					code=col;
				}
				if(headername.equalsIgnoreCase("Select.."))
				{
					selectchckbox=col;
				}
				
			}
			
			List<WebElement> bodylist=mastertable.findElements(By.cssSelector("table[id='landgridData'] tbody tr"));
			for(int row=1;row<=bodylist.size();row++)
			{
				wait.until(ExpectedConditions.visibilityOf(mastertable.findElement(By.xpath("//table[@id='landgridData']/tbody/tr["+row+"]/td[not(contains(@class, 'hidden'))]["+code+"]"))));
				WebElement  bodyelems=mastertable.findElement(By.xpath("//table[@id='landgridData']/tbody/tr["+row+"]/td[not(contains(@class, 'hidden'))]["+code+"]"));
				String mastercode=bodyelems.getText();
				if(mastercode.equalsIgnoreCase(nodes.get(i)))
				{
					wait.until(ExpectedConditions.visibilityOf(mastertable.findElement(By.xpath("//table[@id='landgridData']/tbody/tr["+row+"]"))));
					WebElement maingroup=mastertable.findElement(By.xpath("//table[@id='landgridData']/tbody/tr["+row+"]"));
					Actions actb=new Actions(driver);
					actb.doubleClick(maingroup).build().perform();
					
				}
			}
		}
		return false;
		
	}
	/* METHOD TO DELETE THE SELECTED MASTER BY PASSING THE EXPECTED MESSAGE AS PARAMTER FROM EXCEL */
	public boolean masterDelete(String expmsg)
	{
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span[id='btnDelete']")));
		driver.findElement(By.cssSelector("span[id='btnDelete']")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[id='btnOkForDelete']")));
		driver.findElement(By.cssSelector("button[id='btnOkForDelete']")).click();
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
		    	 		driver.findElement(By.xpath("//*[@id='idGlobalError']/div/table/tbody/tr/td[3]")).click();
		    		}
		    }
	     }
	     catch(Exception e1)
	     {
	    	 
	     }
		
	    wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
	    /* VERIFY AND RETURN TRUE IF THE MESSAGE WHICH IS CAPTURED FROM APPLICATION CONTAINS MESSAGE WHICH IS SENT FROM EXCEL */
	    if(actmsg.toLowerCase().contains(expmsg.toLowerCase()))
	    {
	    	return true;
	    }
		return false;
	}
}
	
