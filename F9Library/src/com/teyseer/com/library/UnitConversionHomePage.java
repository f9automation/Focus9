package com.teyseer.com.library;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;

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
import com.google.common.collect.ImmutableMap;


public class UnitConversionHomePage extends LaunchApplication 
{
	HomePage hp=new HomePage();
	Menus mp=new Menus();
	ArrayList<String> headernames=new ArrayList();
	ArrayList<String> bodynames=new ArrayList();
	ArrayList<String> unmodifiedbodynames=new ArrayList();
	public String actmsg="";
	long slowkeys=300;
	 HashMap<Integer,ArrayList> rowcolmap=new HashMap<Integer,ArrayList>();
	/*
	 @Test
	 public void login() throws InterruptedException
	  {
		 hp.LoginApp("su", "su", "A0407");
			boolean res;
			mp.menuSelection("Home", "Masters", "Product", "Unit Conversion", "Unit Conversion");
			ArrayList<String> labelnames=new ArrayList(Arrays.asList("Base Unit", "Product", "Load From", "Unit Name"));
			ArrayList<String> labelvalues=new ArrayList(Arrays.asList("Unit1","", "",""));
			unitConvHeaderDetails(labelnames, labelvalues);
			ArrayList<String> exlbodynames =new ArrayList(Arrays.asList("Unit Name", "XFactor", "Additional Quantity"));
			ArrayList<String> exlbodyvalues=new ArrayList(Arrays.asList("Unit", "", "2"));
			unitConvBodyDetails(exlbodynames, exlbodyvalues);
			unitConvSave();
			//driver.quit();
			
	  }
 */
	 
	 /* METHOD TO ENTER DATA TO UNIT CONVERSION HEADER TAB BY PASSING LABEL NAMES AND ITS VALUES AS PARAMETERS FROM EXCEL */
  public void unitConvHeaderDetails(ArrayList labelnames, ArrayList labelvalues) throws InterruptedException 
  {
	 headernames.clear(); 
	  ArrayList<String> headerlabelnames=new ArrayList();
	  ArrayList<String> xlnames=new ArrayList();
	  xlnames.addAll(labelnames);
	  ArrayList<String> xlvalues=new ArrayList();
	  xlvalues.addAll(labelvalues);
	  wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
	  List<WebElement> headers=driver.findElements(By.xpath("//*[@id='page_Content']/div[2]/div[1]/div"));
	 int elemid;
	  for(String xlname: xlnames)
		  Labelloop:
	  for(int i=1;i<=headers.size();i+=2)
	  {
		  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='page_Content']/div[2]/div[1]/div["+i+"]/label")));
		  WebElement headerelem=driver.findElement(By.xpath("//*[@id='page_Content']/div[2]/div[1]/div["+i+"]/label"));
		  String headerelement=headerelem.getText();
		 headernames.add(headerelement);
		  elemid=i+1;
		  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='page_Content']/div[2]/div[1]/div["+elemid+"]")));
		  if(xlname.equalsIgnoreCase(headerelement))
			{
			  String value=xlvalues.get(xlnames.indexOf(xlname));
			  WebElement headelem= driver.findElement(By.xpath("//*[@id='page_Content']/div[2]/div[1]/div["+elemid+"]"));
			  Thread.sleep(1000);
			  Actions act=new Actions(driver);
			  Action actionslist=act.
						moveToElement(headelem).click()
						.doubleClick(headelem)
						.build();
						actionslist.perform();
			  if(value.length()>0)
			  {
			  act.moveToElement(headelem).click();
			  
			  for(int a=0;a<value.length();a++)
			  {
				  char ch=value.charAt(a);
				  act.sendKeys(String.valueOf(ch));
				  act.build().perform();
				  Thread.sleep(slowkeys);
			  }
			  
			  act.sendKeys(Keys.TAB).build().perform();
			  }
			  break Labelloop;
			}
		  
	  }
	  Thread.sleep(1000);
	  
	  
  }
  
  /* METHOD TO ENTER DATA TO UNIT CONVERSION BODY TAB BY PASSING LABEL NAMES AND ITS VALUES, ROW NO AS PARAMETERS FROM EXCEL */
 public void unitConvBodyDetails(ArrayList<String> xlnames, ArrayList<String> xlvalues, String xlrowno, boolean togetxlrow ) throws InterruptedException
 {
	
	 ArrayList<String> xlbodynames =new ArrayList(xlnames.subList(3, xlnames.size()));
	 ArrayList<String> unmodifiedxlbodynames= new ArrayList(xlnames.subList(3, xlnames.size()));
	 ArrayList<String> xlbodyvalues=new ArrayList(xlvalues.subList(3, xlvalues.size()));
	 //logger.info("xlrowno is "+xlrowno);
	 int rowno=1;
	 unmodifiedbodynames.clear();
	 bodynames.clear();
	 wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;")); 
	 List<WebElement> bodyheaders=driver.findElements(By.xpath("//div[@id='mytableUnitConversion']/table[@id='myTagsTable']/thead[@id='myTagsTable_head']/tr[@id='myTagsTable_row_heading']/th[not(contains(@style, 'display: none'))]"));
	 for(int i=2;i<=bodyheaders.size();i++)
	 {
		 wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='mytableUnitConversion']/table[@id='myTagsTable']/thead[@id='myTagsTable_head']/tr[@id='myTagsTable_row_heading']/th[not(contains(@style, 'display: none'))]["+i+"]/div")));
		 String bodyvalue=driver.findElement(By.xpath("//div[@id='mytableUnitConversion']/table[@id='myTagsTable']/thead[@id='myTagsTable_head']/tr[@id='myTagsTable_row_heading']/th[not(contains(@style, 'display: none'))]["+i+"]/div")).getText();
		 bodynames.add(bodyvalue);
		 unmodifiedbodynames.add(bodyvalue);
	 }
	List<WebElement> bodyrows=driver.findElements(By.xpath("//div[@id='mytableUnitConversion']/table[@id='myTagsTable']/tbody[@id='myTagsTable_body']/tr"));
	List<WebElement> bodycols=driver.findElements(By.xpath("//div[@id='mytableUnitConversion']/table[@id='myTagsTable']/tbody[@id='myTagsTable_body']/tr[1]/td[not(contains(@style, 'display: none'))]"));
	 if(togetxlrow==false)
	 {
		 RowLoop:
	for(int r=1;r<bodyrows.size();r++)
	 {
		int lastcol=bodycols.size();
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='mytableUnitConversion']/table[@id='myTagsTable']/tbody[@id='myTagsTable_body']/tr["+r+"]/td[not(contains(@style, 'display: none'))]["+lastcol+"]")));
		 WebElement lastcolelem=driver.findElement(By.xpath("//div[@id='mytableUnitConversion']/table[@id='myTagsTable']/tbody[@id='myTagsTable_body']/tr["+r+"]/td[not(contains(@style, 'display: none'))]["+lastcol+"]"));
		 Thread.sleep(1000);
		 try
		 {
		 String lastcoltxtattr=lastcolelem.getAttribute("textContent");
		 if(lastcolelem.getText().length()==0)
		 {
			 rowno=r;
			 break RowLoop; 
		 }
		 }
		catch(Exception ne)
		 {
			 rowno=r;
			 logger.info("row no "+rowno);
			 break RowLoop;
		 }
	 }
	 }
	 else
	 {
		 rowno=Integer.parseInt(xlrowno);
	 }
	 xlbodynames.addAll(bodynames);
		 Collections.reverse(xlbodynames);
		 xlbodynames.remove(xlbodynames.size()-1);
		 for(String vouchername: bodynames)
		 {
			 for(String xlname:xlbodynames)
			 {
				if(vouchername.equalsIgnoreCase(xlname)) 
				{
					
					int col=unmodifiedbodynames.indexOf(xlname)+2;
					int valuelength;
					try
					{
						valuelength = xlbodyvalues.get(unmodifiedxlbodynames.indexOf(xlname)).length();
					}
					catch(IndexOutOfBoundsException ie)
					{
						valuelength=0;
					}
					WebElement bodyelem=driver.findElement(By.xpath("//div[@id='mytableUnitConversion']/table[@id='myTagsTable']/tbody[@id='myTagsTable_body']/tr["+rowno+"]/td[not(contains(@style, 'display: none'))]["+col+"]"));
					bodyelem.click();
					if(valuelength>0)
					{
						String value=xlbodyvalues.get(unmodifiedxlbodynames.indexOf(xlname));
						Actions act2=new Actions(driver);
						Action actionslist=act2.
								moveToElement(bodyelem).click()
								.doubleClick(bodyelem)
								.build();
								actionslist.perform();
						for(int i=0;i<value.length();i++)
						{
							char ch=value.charAt(i);
							act2.sendKeys(String.valueOf(ch));
							act2.build().perform();
							Thread.sleep(slowkeys);
						}
						act2.sendKeys(Keys.TAB).perform();
						Thread.sleep(3000);
					}
					
					break;
				}
		}
	}
 }
 
 /* METHOD TO SAVE UNIT CONVERSION BY PASSING THE EXPECTED MESSAGE AS PARAMETER FROM EXCEL */
 public boolean unitConvSave(String expmsg)
 {
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
		    			//logger.info("Yes displayed with i value "+" txt "+popups.getAttribute("text content"));
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='idGlobalError']/div/table/tbody/tr/td[2]/div[2]")));
		    	 		actmsg=driver.findElement(By.xpath("//*[@id='idGlobalError']/div/table/tbody/tr/td[2]/div[2]")).getText();
		    	 		//logger.info("Actmsg "+actmsg);
		    			driver.findElement(By.xpath("//*[@id='idGlobalError']/div/table/tbody/tr/td[3]")).click();
		    		}
		    	 
		    	
			}
	     }
	     catch(Exception e1)
	     {
	    	 
	     }
	 wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
	 wait.until(ExpectedConditions.elementToBeClickable(By.id("btnSave")));
	 driver.findElement(By.id("btnSave")).click();
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
		    			//logger.info("Yes displayed with i value "+" txt "+popups.getAttribute("text content"));
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='idGlobalError']/div/table/tbody/tr/td[2]/div[2]")));
		    	 		actmsg=driver.findElement(By.xpath("//*[@id='idGlobalError']/div/table/tbody/tr/td[2]/div[2]")).getText();
		    	 		logger.info("Actmsg "+actmsg);
		    			driver.findElement(By.xpath("//*[@id='idGlobalError']/div/table/tbody/tr/td[3]")).click();
		    		}
		    	 
		    	
			}
	     }
	     catch(Exception e1)
	     {
	    	 
	     }
	 wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
	 if(actmsg.contains(expmsg))
	 {
		 return true;
	 }
	return false;
	 
 }
 /* METHOD TO CLEARE UNIT CONVERSION DATA */
 public void unitConvClear() throws InterruptedException
 {
	 wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
	 wait.until(ExpectedConditions.elementToBeClickable(By.id("btnClearUC")));
	 driver.findElement(By.id("btnClearUC")).click();
	 wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
	 //wait.until(ExpectedConditions.textToBePresentInElementValue(driver.findElement(By.id("optionBaseUnit")).getAttribute("data-focustext"), ""));
	 Thread.sleep(1000);
 }
 
 /* METHOD TO DELETE UNIT CONVERSION */
 public boolean unitConvDelete(String expmsg)
 {
	 
	 wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
	 try
	    {
		  /* TO VERIFY IF ANY GLOBAL ID IS DISPLAYING AND CLOSE THEM*/
		  	List<WebElement> globalidlist=driver.findElements(By.cssSelector("div[id='idGlobalError'] div, div[id='idGlobalError']"));
		  	logger.info("globalist "+globalidlist.size());
			if(globalidlist.size()>1)
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
	     }
	     catch(Exception e1)
	     {
	    	 
	     }
	 try
	 {
	 wait.until(ExpectedConditions.elementToBeClickable(By.id("btnDelete")));
	 driver.findElement(By.id("btnDelete")).click();
	 }
	 catch(Exception e)
	 {
		 logger.info("elem not clickable");
	 }
	 try
	 {
		 driver.switchTo().alert().accept();
	 }
	 catch(Exception e)
	 {
		 logger.info("Alert not found exception");
	 }
	 try
	    {
		  /* TO VERIFY IF ANY GLOBAL ID IS DISPLAYING*/
		  	List<WebElement> globalidlist=driver.findElements(By.cssSelector("div[id='idGlobalError'] div, div[id='idGlobalError']"));
			logger.info("globalid list size "+globalidlist.size());
			if(globalidlist.size()>=1)
			  {
				WebElement popups=driver.findElement(By.xpath("//*[@id='idGlobalError']/div/table/tbody/tr/td[2]/div"));
		    	if(popups.findElement(By.xpath("//*[@id='idGlobalError']/div/table/tbody/tr/td[2]/div[2]")).isDisplayed())
		    		{
		    			//logger.info("Yes displayed with i value "+" txt "+popups.getAttribute("text content"));
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='idGlobalError']/div/table/tbody/tr/td[2]/div[2]")));
		    	 		actmsg=driver.findElement(By.xpath("//*[@id='idGlobalError']/div/table/tbody/tr/td[2]/div[2]")).getText();
		    	 		logger.info("Actmsg "+actmsg);
		    			driver.findElement(By.xpath("//*[@id='idGlobalError']/div/table/tbody/tr/td[3]")).click();
		    		}
		    	 
		    	
			  }
	     }
	     catch(Exception e1)
	     {
	    	 
	     }
	 wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
	 if(actmsg.contains(expmsg))
	 {
		 return true;
	 }
	
	 return false;
 }
 /* METHOD TO CLOSE UNIT CONVERSION */
 public boolean unitConvClose()
 {
	 wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
	 wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='ucCancel']/div")));
	driver.findElement(By.xpath("//*[@id='ucCancel']/div")).click();
	 wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
	 try
	 {
	  if(driver.findElement(By.xpath("//*[@id='dashName']")).isDisplayed())
	  {
		  logger.info("true");
		  return true;
		  
	  } 
	 }
  	catch(Exception e)
  	{
	  return false;
  	}
return false;
	 
 }
 
 /* METHOD TO CLICK ON LOAD FROM AND COMPARE THE RESPECTIVE VALUES WHICH ARE LOADED BY PASSING THE LABEL NAMES AND ITS VALUES AS PARAMETERS  */
 public boolean checkUnitConvLoadFrom(ArrayList xlheadernames, ArrayList xlheadervalues) throws InterruptedException
 {
	ArrayList<String> unmodifiedxlheadervalues=new ArrayList(xlheadervalues);
	HashMap<Integer,ArrayList> aftloadrowcolmap=new HashMap();
	HashMap<Integer,ArrayList> befloadrowcolmap=new HashMap();
	xlheadervalues.set(1, "");
	xlheadervalues.set(0,xlheadervalues.get(2));
	xlheadervalues.set(2, "");
	unitConvHeaderDetails(xlheadernames, xlheadervalues);
	getUnitConvLoadFromData();
	befloadrowcolmap.putAll(rowcolmap);
	unitConvClear();
	unitConvHeaderDetails(xlheadernames, unmodifiedxlheadervalues);
	getUnitConvLoadFromData();
	aftloadrowcolmap.putAll(rowcolmap);
	//logger.info(aftloadrowcolmap);
	befloadrowcolmap.entrySet().removeIf(entry -> entry.getValue().contains(unmodifiedxlheadervalues.get(0)));
	int count=0;
	//logger.info(befloadrowcolmap);
	/* TO COMPARE EACH VALUE OF BEFORE LOAD FROM HASHMAP WITH HASHMAP VALUES AFTER LOAD FROM*/
	for(ArrayList before: befloadrowcolmap.values())
	{
		before.set(before.size()-1, "");
		for(ArrayList after: aftloadrowcolmap.values())
		{
			after.set(before.size()-1, "");
			boolean result = Arrays.equals(after.toArray(),before.toArray());
			if(Arrays.equals(after.toArray(),before.toArray()))
			{
				count++;
				break;
			}
		}
	}
	//logger.info("count "+count+" size "+aftloadrowcolmap.size());
	if(count==aftloadrowcolmap.size())
	{
		//logger.info("true will b retured ");
		return true;
		
	}
	
	return false;
	
	 
 }

 /* METHOD TO GET THE RESPECTIVE VALUES BY CLICKING ON LOAD FROM  */
public void getUnitConvLoadFromData() throws InterruptedException
 {
	 List<WebElement> bodyrows=driver.findElements(By.xpath("//div[@id='mytableUnitConversion']/table[@id='myTagsTable']/tbody[@id='myTagsTable_body']/tr"));
	 List<WebElement> bodycols=driver.findElements(By.xpath("//div[@id='mytableUnitConversion']/table[@id='myTagsTable']/tbody[@id='myTagsTable_body']/tr[1]/td[not(contains(@style, 'display: none'))]"));
	 int lastrowno = 1;
	 for(int r=1;r<bodyrows.size();r++)
	 {
		 int lastcolno=bodycols.size();
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='mytableUnitConversion']/table[@id='myTagsTable']/tbody[@id='myTagsTable_body']/tr["+r+"]/td[not(contains(@style, 'display: none'))]["+lastcolno+"]")));
		 WebElement lastcolelem=driver.findElement(By.xpath("//div[@id='mytableUnitConversion']/table[@id='myTagsTable']/tbody[@id='myTagsTable_body']/tr["+r+"]/td[not(contains(@style, 'display: none'))]["+lastcolno+"]"));
		 Thread.sleep(1000);
		
		 try
		 {
			 if(lastcolelem.getText().length()==0)
			 {
				 lastrowno=r;
				 break; 
			 }
		 }
		 catch(Exception ne)
		 {
			 lastrowno=r;
			 break;
		 }
	 }
	
	 rowcolmap.clear();
	 int row = 0;
	 for(row=1;row<lastrowno;row++)
	 {
		ArrayList<String> cellsdata=new ArrayList();
		cellsdata.clear();
		for(int c=2;c<=bodycols.size();c++)
		 {
		    WebElement celldata=driver.findElement(By.xpath("//div[@id='mytableUnitConversion']/table[@id='myTagsTable']/tbody[@id='myTagsTable_body']/tr["+row+"]/td[not(contains(@style, 'display: none'))]["+c+"]"));
			cellsdata.add(celldata.getAttribute("textContent"));
			
		 }
		rowcolmap.put(row, cellsdata);
	}
	}
}
