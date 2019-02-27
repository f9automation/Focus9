package com.teyseer.com.library;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
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

public class PriceBookHomePage extends LaunchApplication
{
	
	HomePage hp=new HomePage();
	long slowkeys=250;
	Menus mp=new Menus();
	public String actmsg;
	ArrayList<String> pricebookfields= new ArrayList();
	public String customizecolres;
	public ArrayList<String> customizecolresults=new ArrayList();
	
 
	/* TO GET  SYSTEM USAGE (TRIAL) */
  private static void getSystemUsage() 
  {
	  OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
	  for (Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) 
	  {
		  method.setAccessible(true);
		  if (method.getName().startsWith("get") && Modifier.isPublic(method.getModifiers()))
		  {
			  Object value;
			  String name = null;
			  long ram = 0;
			  try 
			  {
				  value = method.invoke(operatingSystemMXBean);
				  logger.info(method.getName() + " = " + value);
				  name=operatingSystemMXBean.getArch();
				  logger.info("name "+name);
				  ram = Long.valueOf(value.toString()) / 1024 / 1024;
				  logger.info("ram "+ram);
				  } 
			  catch (Exception e) 
			  {
				  value = e;
			  }
		  logger.info(method.getName() + " = " + value+" name "+name+" ram "+ram);
	
		  }
	}
  }
  
  /* HEADER DETAILS OF THE PRICEBOOK */
	public void priceBookHeaderData(ArrayList<String> pricebooknames, ArrayList<String> pricebookvalues) throws InterruptedException
	{
		 wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
		  String value;
		  List<WebElement> headers=driver.findElements(By.xpath("//div[@id='headerRow']/div"));
		  int pricebookno, abbrevationno;
		for(int i=1;i<=headers.size();i+=2)
		  {
			  Actions action=new Actions(driver);
			  WebElement headerlabel=driver.findElement(By.xpath("//div[@id='headerRow']/div["+i+"]/label"));
			  String labelname=headerlabel.getText();
			  if(labelname.equalsIgnoreCase("Price Book"))
			  {
				  if(pricebooknames.get(0).equalsIgnoreCase(labelname))
				  {
					  value=pricebookvalues.get(pricebooknames.indexOf(labelname));
					  if(value.length()>0)
					  {
						  pricebookno=i+1;
						  WebElement pricebookelem=driver.findElement(By.cssSelector("div[id='headerRow'] div:nth-of-type("+pricebookno+") input:not([type*='hidden'])"));
						 pricebookelem.click();
						 Thread.sleep(1000);
						  pricebookelem.clear();
						  for(int j=0;j<value.length();j++)
						  {
							  char ch=value.charAt(j);
							  pricebookelem.sendKeys(String.valueOf(ch));
							  Thread.sleep(slowkeys);
						  }
						 pricebookelem.sendKeys(Keys.TAB);
					}
				  }
				  
			  }
			  if(labelname.equalsIgnoreCase("Abbreviation"))
			  {
				  if(pricebooknames.get(1).equalsIgnoreCase(labelname))
				  {
					  value=pricebookvalues.get(pricebooknames.indexOf(labelname));
					  if(value.length()>0)
					  {
						  abbrevationno=i+1;
						  WebElement abbrevatoinelem=driver.findElement(By.cssSelector("div[id='headerRow'] div:nth-of-type("+abbrevationno+") input:not([type*='hidden'])"));
						  abbrevatoinelem.click();
						  Thread.sleep(1000);
						  abbrevatoinelem.clear();
						  for(int j=0;j<value.length();j++)
						  {
							  char ch=value.charAt(j);
							  abbrevatoinelem.sendKeys(String.valueOf(ch));
							  Thread.sleep(slowkeys);
						  }
						  abbrevatoinelem.sendKeys(Keys.TAB);
					  }
					  		  
				}
				  
			  }
			  action.sendKeys(Keys.TAB);
		  }
	}
	/* BODY DATA DETAILS */
  public void priceBookBodyData(ArrayList<String> pricebooknames, ArrayList<String> pricebookvalues, int bodyrow) throws InterruptedException 
  {
	  Thread.sleep(3000);
	  boolean excelrowno=true;
	  String rowno=pricebookvalues.get(2);
	 /* GETTING ROWNO FROM EXCEL AND IF NOT PROVIDED THEN ENTER DATA TO THE ROW WHICH IS EMPTY */ 
	  if(!(rowno.length()==0))
	  {
		  bodyrow=Integer.parseInt(rowno);
		  excelrowno=false;
	  }
	  ArrayList<String> unmodifiedpricebookbodynames=new ArrayList(pricebooknames.subList(3, pricebooknames.size()));
	  ArrayList<String> pricebookbodynames=new ArrayList(pricebooknames.subList(3, pricebooknames.size()));
	  ArrayList<String> pricebookbodyvalues=new ArrayList(pricebookvalues.subList(3, pricebooknames.size()));
	  ArrayList<String> pricebookbodyvalues2=new ArrayList(pricebookvalues.subList(4, pricebooknames.size()));
	  ArrayList<String> avalue=new ArrayList();
	  /* TO COMPARE IF ALL THE BODY VALUES OF PRICE BOOK ARE EMPTY, IF SO THEN SKIP ADDING EMPTY DATA */
	  for(String a:pricebookbodyvalues2)
	  {
		 if(a.length()==0)
		  {
			  avalue.add("a");
		  }
	  }
	  boolean allEqual = false;
	 if(avalue.size()==pricebookbodyvalues2.size())
	  {
	   allEqual = avalue.stream().distinct().limit(2).count() <= 1;
	  }
	 if(!(allEqual==true))
	 {
		  List<WebElement> pricebooktableheaders=driver.findElements(By.xpath("//table[@id='PriceBookWebGrid']/thead[@id='PriceBookWebGrid_head']/tr[@id='PriceBookWebGrid_row_heading']/th[not(contains(@style,'display: none;'))]/div[1]"));
		  ArrayList<String> bodylabelnames=new ArrayList();
		  bodylabelnames.clear();
		  ArrayList<String> unmodifiedbodylabelnames=new ArrayList();
		  unmodifiedbodylabelnames.clear();
		  ArrayList<Integer> bodycolnos=new ArrayList();
		  bodycolnos.clear();
		  for(int i=0;i<pricebooktableheaders.size();i++)
		  {
			  String bodylabel=pricebooktableheaders.get(i).getAttribute("textContent");
			  bodylabelnames.add(bodylabel);
			  unmodifiedbodylabelnames.add(bodylabel);
			  bodycolnos.add(i+2);
		  }
		  List<WebElement> pricebooktablebodyrows=driver.findElements(By.xpath("//table[@id='PriceBookWebGrid']/tbody[@id='PriceBookWebGrid_body']/tr"));
		  int emptyrowno;
		  /* TO GET ALL THE BODY ROW'S FIRST COLUMN DATA AND KNOW THE EMPTY ROW NO */
		  for(emptyrowno=1;emptyrowno<=pricebooktablebodyrows.size();emptyrowno++)
		  {
			 WebElement firstcol=driver.findElement(By.xpath("//table[@id='PriceBookWebGrid']/tbody[@id='PriceBookWebGrid_body']/tr["+emptyrowno+"]/td[not(contains(@style,'display: none;'))][2]"));
			 String firstcoldata=firstcol.getAttribute("data-value");
			 try
			 {
				if(firstcoldata.equalsIgnoreCase(null))
					 {
						break;
					 }
			 }
			 catch(NullPointerException e)
			 {
				 break;
			 }
			 
		 }
		if(excelrowno==true)
		 {
			 bodyrow=emptyrowno;
		 }
		/* COMPARING EACH LABEL NAME FROM EXCEL TO THE LABEL NAMES FROM APPLICATION AND ENTER DATA TO THE MATCHED VALUE */
		  pricebookbodynames.addAll(bodylabelnames);
		  Collections.reverse(pricebookbodynames);
		  Actions action=new Actions(driver);
		  for (String voucherattribute : bodylabelnames) 
			  {
				 
				  pricebookbodynames.remove(pricebookbodynames.size()-1);
				  for(String excelattribute : pricebookbodynames) 
				       {
							if(voucherattribute.equalsIgnoreCase(excelattribute))
							{
								
								try
								{
									wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//table[@id='PriceBookWebGrid']/tbody[@id='PriceBookWebGrid_body']/tr["+bodyrow+"]/td[not(contains(@style,'display: none;'))]["+bodycolnos.get(unmodifiedbodylabelnames.indexOf(voucherattribute))+"]|.//table[@id='PriceBookWebGrid']/tbody[@id='PriceBookWebGrid_body']/tr["+bodyrow+"]/td[not(contains(@style,'display: none;'))]["+bodycolnos.get(unmodifiedbodylabelnames.indexOf(voucherattribute))+"]/input")));
									WebElement bodyelem=driver.findElement(By.xpath(".//table[@id='PriceBookWebGrid']/tbody[@id='PriceBookWebGrid_body']/tr["+bodyrow+"]/td[not(contains(@style,'display: none;'))]["+bodycolnos.get(unmodifiedbodylabelnames.indexOf(voucherattribute))+"]|.//table[@id='PriceBookWebGrid']/tbody[@id='PriceBookWebGrid_body']/tr["+bodyrow+"]/td[not(contains(@style,'display: none;'))]["+bodycolnos.get(unmodifiedbodylabelnames.indexOf(voucherattribute))+"]/input"));
									Action actionslist1=action.
											moveToElement(bodyelem).click()
											.build();
											actionslist1.perform();
									String bodyvalue=pricebookbodyvalues.get(unmodifiedpricebookbodynames.indexOf(excelattribute));
									if(bodyvalue.length()>0)
									{
										Action actionslist=action.
										moveToElement(bodyelem).click()
										.doubleClick(bodyelem)
										.build();
										actionslist.perform();
										for(int i=0;i<bodyvalue.length();i++)
										{
											char ch=bodyvalue.charAt(i);
											action.sendKeys(String.valueOf(ch));	
											action.build().perform();
											Thread.sleep(slowkeys);
										}
									
										   
									}
									
								}
								catch(IndexOutOfBoundsException ie)
								{
									
								}
								finally 
								{
									action.sendKeys(Keys.TAB).build().perform();
									wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
									Thread.sleep(1000);
								}
							}
				       }
				  
			  }
		 }
  }
  /* TO VERIFY if AUTHORIZE SCREEN IS DISPLAYING */
  public boolean authorizeScreenDisplayPriceBook() throws InterruptedException
  {
	  wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
	  wait.until(ExpectedConditions.elementToBeClickable(By.id("btnAuthorize")));
	  driver.findElement(By.id("btnAuthorize")).click();
	  Thread.sleep(1000);
	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='myAuthorizePopup']/div/div/div[1]/div[1]")));
	  WebElement authorizewindow=driver.findElement(By.xpath("//*[@id='myAuthorizePopup']/div/div/div[1]/div[1]"));
	  if(authorizewindow.isDisplayed())
	  {
		  wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/section/div[2]/div/section[1]/div/div[14]/div/div/div[1]/div[2]/span/i")));
		  driver.findElement(By.xpath("/html/body/section/div[2]/div/section[1]/div/div[14]/div/div/div[1]/div[2]/span/i")).click();
		  return true;
	  }
	  
	  return false;
  }
  /* CLOSING OF PRICE BOOK  */
  public boolean closePriceBook() throws InterruptedException
  {
	  wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
	  wait.until(ExpectedConditions.elementToBeClickable(By.id("btnClose")));
	  driver.findElement(By.id("btnClose")).click();
	  wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
	  Thread.sleep(1000);
	  /* VERIFY AND RETURN TRUE IF CLOSING OF PRICEBOOK DISPLAYS DASHBOARD */
	  try
		 {
		  if(driver.findElement(By.xpath("//*[@id='dashName']")).isDisplayed())
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
  /* SAVING PRICEBOOK */
  public boolean savePriceBook(String expmsg) throws InterruptedException
  {
	  wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
	  Thread.sleep(5000);
	  try
	    {
		  /* TO VERIFY IF ANY GLOBAL ID IS DISPLAYING, IF SO CLOSE THE POPUP */
		  	List<WebElement> globalidlist=driver.findElements(By.cssSelector("div[id='idGlobalError'] div, div[id='idGlobalError']"));
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
	  	wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
	  	wait.until(ExpectedConditions.elementToBeClickable(By.id("btnSave")));
	  	driver.findElement(By.id("btnSave")).click();
	  	 try
		    {
			  /* TO VERIFY IF ANY GLOBAL ID IS DISPLAYING AND CLOSE THEM*/
			  	List<WebElement> globalidlist=driver.findElements(By.cssSelector("div[id='idGlobalError'] div, div[id='idGlobalError']"));
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
	  	
		if(actmsg.toUpperCase().contains(expmsg.toUpperCase()))
	 		{
	 			return true;
	 			
	 		}
		return false;
  }
  
 /* CLICK ON FILTER AND LOAD ICON */
  public void filterAndLoad()
  {
	 wait.until(ExpectedConditions.elementToBeClickable(By.id("btnLoad")));
	  driver.findElement(By.id("btnLoad")).click();
	  wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
	  
	  
  }
  
  /* CLEARING OF PRICEBOOK */
  public boolean clearPriceBook() throws InterruptedException
  {
	 
	  wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
	  wait.until(ExpectedConditions.elementToBeClickable(By.id("btnClear")));
	  driver.findElement(By.id("btnClear")).click();
	  wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
	  Thread.sleep(3000);
	  String idvalue=driver.findElement(By.id("ctrlOptionProPriceBookH_data")).getAttribute("data-spricebookname");
	  List<WebElement> pricebooktablebodyrows=driver.findElements(By.xpath("//table[@id='PriceBookWebGrid']/tbody[@id='PriceBookWebGrid_body']/tr"));
	  for(int r=1;r<=pricebooktablebodyrows.size();r++)
		 {
			 WebElement firstcol=driver.findElement(By.xpath("//table[@id='PriceBookWebGrid']/tbody[@id='PriceBookWebGrid_body']/tr["+r+"]/td[not(contains(@style,'display: none;'))][2]"));
			 String firstcoldata=firstcol.getText();
			 if((!(firstcoldata.length()>0))&&(idvalue==null))
			 {
				 return true;
			 }
		}
	return false;
	  
  }
  
 /* DELETING OF PRICEBOOK */
  public boolean deletePriceBook(String expmsg)
  {
	  wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
	  wait.until(ExpectedConditions.elementToBeClickable(By.id("btnDelete")));
	  driver.findElement(By.id("btnDelete")).click();
	  try
	  {
		 driver.switchTo().alert().accept();
		 }
	  catch(Exception e)
	  {
		  
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
		    	 		driver.findElement(By.xpath("//*[@id='idGlobalError']/div/table/tbody/tr/td[3]")).click();
		    		}
		    	 
		    	
			}
	     }
	     catch(Exception e1)
	     {
	    	 
	     }
	  
		wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
		logger.info("after capturing actmsg "+actmsg);
		 if(actmsg.toUpperCase().contains(expmsg.toUpperCase()))
	 		{
	 			return true;
	 			
	 		}
		return false;
  }
  /* PRICEBOOK CUSTOMIZATION */
  public boolean priceBookCustomization(ArrayList<String> toselectelems, boolean tobedisplayed) throws InterruptedException
  {
	  logger.info("to seelect elems are "+toselectelems+ "& to be selcted "+tobedisplayed);
	  boolean result = false;
	  wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
	  wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/section/div[2]/div/section[1]/div/div[4]/div[2]/span/i")));
	  driver.findElement(By.xpath("/html/body/section/div[2]/div/section[1]/div/div[4]/div[2]/span/i")).click();
	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ul[id='ddlMenu'] li")));
	  List<WebElement> totcustomizelist=driver.findElements(By.cssSelector("ul[id='ddlMenu'] li"));
	for(int i=1;i<=totcustomizelist.size();i++)
	  {
		  WebElement customizelist=driver.findElement(By.cssSelector("ul[id='ddlMenu'] li:nth-of-type("+i+") label input"));
		  String customizelistelem=customizelist.getAttribute("name");
		  WebElement customizelist1=driver.findElement(By.cssSelector("ul[id='ddlMenu'] li:nth-of-type("+i+") label"));
		  String customizelistelem1=customizelist1.getText();
		 Thread.sleep(1000);
		 for(String toselectelem:toselectelems)
		 {
		if(customizelistelem1.trim().equalsIgnoreCase(toselectelem)||customizelistelem.equalsIgnoreCase(toselectelem))
		  {
			logger.info("toselect elem "+toselectelem);
			 if(tobedisplayed==true) 
			 {
				logger.info("true");
				 if(!customizelist.isSelected())
				 {
					 logger.info("to click");
				 customizelist.click();
				 }
			 }
			 if(tobedisplayed==false)
			 {
				 if(customizelist.isSelected())
				 {
					 customizelist.click();
				 }
			 }
			  Thread.sleep(1000);
			 }
		 }
	  }
	
	 wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/section/div[2]/div/section[1]/div/div[4]/div[2]/span/i")));
	  driver.findElement(By.xpath("/html/body/section/div[2]/div/section[1]/div/div[4]/div[2]/span/i")).click();
	  wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
	  Thread.sleep(1000);
	  
	  ArrayList<String> results= new ArrayList();
	  results.clear();
	  for(String toselectelem: toselectelems)
	  {
		  if(toselectelem.length()>0)
		  {
		  result=customizeVerification(toselectelem, tobedisplayed);
		  results.add(String.valueOf(result));
		  }
  		}
	  if(!results.contains("false"))
	  {
		  return true;
	  }
	return false;
	  
	
  }
  /* VERIFY IF THE CUSTOMIZED VALUES ARE DISPLAYING/NOT DISPLAYING IN THE GRID */
  public boolean customizeVerification(String toselectelem, boolean tobedisplayed)
  {
	  
	 // logger.info("elem to be seelcted "+toselectelem);
	  customizecolresults.clear();
	  List<WebElement> pricebooktableheaders=driver.findElements(By.xpath("//table[@id='PriceBookWebGrid']/thead[@id='PriceBookWebGrid_head']/tr[@id='PriceBookWebGrid_row_heading']/th[not(contains(@style,'display: none;'))]/div[1]"));
	  ArrayList<String> bodyelemslist=new ArrayList();
	  bodyelemslist.clear();
	  String bodylabel;
	  if(tobedisplayed==true)
		{
		  for(int i=0;i<pricebooktableheaders.size();i++)
		  {
			 bodylabel=pricebooktableheaders.get(i).getAttribute("textContent");
			 //logger.info("body labe "+bodylabel);
			 if(toselectelem.equalsIgnoreCase("Date Range")||toselectelem.equalsIgnoreCase("DateRange"))
				  {
					  if(bodylabel.equalsIgnoreCase("Starting date"))
					  {
						  bodylabel=pricebooktableheaders.get(i+1).getAttribute("textContent");
						  if(bodylabel.equalsIgnoreCase("Ending date"))
						  {
							  customizecolres=toselectelem+" is displayed";
							  logger.info(customizecolres);
							  customizecolresults.add(customizecolres);
							  return true;
							}
						  
					  }
					
				  }
				  if(toselectelem.equalsIgnoreCase("Qty Range")||toselectelem.equalsIgnoreCase("QtyRange"))
				  {
					  if(bodylabel.equalsIgnoreCase("Min Qty"))
					  {
						  bodylabel=pricebooktableheaders.get(i+1).getAttribute("textContent");
						  if(bodylabel.equalsIgnoreCase("Max Qty"))
						  {
							  customizecolres=toselectelem+" is displayed";
							 customizecolresults.add(customizecolres);
							  return true;
						  }
						 
					  }
					}
				  if(toselectelem.equalsIgnoreCase("customer"))
				  {
					  if(bodylabel.equalsIgnoreCase("vendor")||bodylabel.equalsIgnoreCase("customer"))
					  {
						  return true;
					  }
					  
				  }
				  if(bodylabel.equalsIgnoreCase(toselectelem))
				  {
					  customizecolres=toselectelem+" is displayed";
					customizecolresults.add(customizecolres);
					  return true;
				  }
				 
				
			  }
			}
	  if(tobedisplayed==false)
	  {
		  customizecolres=toselectelem+" is not getting displayed";
		  
		  for(int i=0;i<pricebooktableheaders.size();i++)
		  {
			  bodylabel=pricebooktableheaders.get(i).getAttribute("textContent");
			  bodyelemslist.add(bodylabel);
		  }
		 if(toselectelem.equalsIgnoreCase("Date Range")||toselectelem.equalsIgnoreCase("DateRange"))
		 {
				if(bodyelemslist.contains("Starting date")||bodyelemslist.contains("Ending date")) 
				{
					return false;
				}
				else
				{
					return true;
				}
		 }
		 if(toselectelem.equalsIgnoreCase("Qty Range")||toselectelem.equalsIgnoreCase("QtyRange"))
		  {
			 if(bodyelemslist.contains("Min Qty")||bodyelemslist.contains("Max Qty")) 
				{
					return false;
				}
				else
				{
					return true;
				}
		  }
		  if(toselectelem.equalsIgnoreCase("customer"))
		  {
			  if(bodyelemslist.contains("Vendor")||bodyelemslist.contains("Customer"))
			  {
				  return false;
			  }
			  else
			  {
				  return true;
			  }
		  }
		  if(!bodyelemslist.contains(toselectelem))
		  {
			  return true;
		  }
	  }
	  return false;
  }
  /* TO GET BODY DATA OF A PRICEBOOK IN ORDER TO COPY THE DATA */
  public HashMap<Integer,ArrayList> bodyDataSet()
  {
	  wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
		 
	  List<WebElement> pricebooktablebodyrows=driver.findElements(By.xpath("//table[@id='PriceBookWebGrid']/tbody[@id='PriceBookWebGrid_body']/tr"));
	  List<WebElement> pricebooktablebodycols=driver.findElements(By.xpath("//table[@id='PriceBookWebGrid']/tbody[@id='PriceBookWebGrid_body']/tr[1]//td[not(contains(@style,'display: none;'))]"));
	  ArrayList<String> cellsdata=new ArrayList();
	  ArrayList<Integer> rows=new ArrayList();
	  HashMap<Integer,ArrayList> rowcolmap=new HashMap();
	  rowcolmap.clear();
	  rows.clear();
	  cellsdata.clear();
	  for(int r=1;r<pricebooktablebodyrows.size();r++)
	  {
		 
		  WebElement firstcoldata=driver.findElement(By.xpath("//table[@id='PriceBookWebGrid']/tbody[@id='PriceBookWebGrid_body']/tr["+r+"]/td[not(contains(@style,'display: none;'))][2]"));

		  if(firstcoldata.getAttribute("textContent").length()>0)
		  {
			  cellsdata.clear();
			  //hm.clear();
			  for(int c=2;c<=pricebooktablebodycols.size();c++)
			  {
			    WebElement celldata=driver.findElement(By.xpath("//table[@id='PriceBookWebGrid']/tbody[@id='PriceBookWebGrid_body']/tr["+r+"]/td[not(contains(@style,'display: none;'))]["+c+"]"));
				cellsdata.add(celldata.getAttribute("textContent"));
				  
			  }
			  rows.add(r);
			  rowcolmap.put(r, cellsdata);
		  }
		  
	  }
	
	return rowcolmap;
	 
	 
	 
  }
  /* PASTE THE DATA FROM ONE PRICE BOOK TO OTHER WHICH IS COPIED */
  public boolean pasteData(ArrayList pricebooknames, ArrayList pricebookvalues) throws InterruptedException
  {
	  	HashMap<Integer,ArrayList> rowcolmap=new HashMap();
	  	HashMap<Integer,ArrayList> pastehm=new HashMap();
	  	rowcolmap.clear();
	  	pastehm.clear();
	  	/* TO GET  DATA FROM PRICEBOOK INTO A HASHMAP WITH KEY AS ROWNO & VALUE AS ROW DATA */
	  	rowcolmap= bodyDataSet();
	  	wait.until(ExpectedConditions.elementToBeClickable(By.id("btnCopy")));
		driver.findElement(By.id("btnCopy")).click();
		clearPriceBook();
		priceBookHeaderData(pricebooknames, pricebookvalues);
		filterAndLoad();
		wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("btnPaste")));
		driver.findElement(By.id("btnPaste")).click();
		wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
		pastehm=bodyDataSet();
		Boolean res=rowcolmap.equals(pastehm);
		return res;
  }
}

