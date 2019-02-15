package com.teyseer.com.transactions;

import java.awt.MouseInfo;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.poi.ss.formula.functions.TimeFunc;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.focus.constants.HomePage;
import com.focus.constants.LaunchApplication;
import com.focus.constants.Menus;
import com.focus.library.MasterHomePage;
//import com.jacob.com.LibraryLoader;


import autoitx4java.AutoItX;


public class TransactionHeader extends LaunchApplication 
{
	
	HomePage hp = new HomePage();
	Menus menu= new Menus();
	MasterHomePage mhp = new MasterHomePage();
	public static int bodyrownoh=1;
	public ArrayList<String> label = new ArrayList<String>();
	public ArrayList<String> labelunmodified = new ArrayList<String>();
	public ArrayList<String> excelattridlist = new ArrayList<String>();
	public ArrayList<String> totallabels= new ArrayList();
	double slowkeys= 300;
	public String exchrt;
	 List<String> unmodifiablelabel;
	 ArrayList<String> totlabels= new ArrayList();
	 WebElement othermaster;
@Test
/* METHOD TO CLICK ON NEW ICON OF THE TRNSACTION SCREEN */
public boolean newClick () throws InterruptedException
{
	new WebDriverWait(driver, 10).until(
	          webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
	Thread.sleep(2000);
	wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/section/div[2]/div/section[1]/div[1]/div/div[1]/nav/div[2]/div/div[1]")));
	Thread.sleep(1000);
	WebElement newclick=driver.findElement(By.xpath("/html/body/section/div[2]/div/section[1]/div[1]/div/div[1]/nav/div[2]/div/div[1]"));
	Actions actions = new Actions(driver);
	actions.moveToElement(newclick).perform();
	actions.click().build().perform();;
	/* ACCEPT THE ALERT IF ANY APPEARS WHILE CLICKING ON NEW  */
	try
	 	{
	 		driver.switchTo().alert().accept();
	 	}
	 	catch(Exception e)
	 	{
	 		
	 	}
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.tab_button_text:nth-child(1)")));
	WebElement table=driver.findElement(By.cssSelector("div.tab_button_text:nth-child(1)"));
	/* VERIFY AND RETURN TRUE IF TRANSACTION TABLE IS DISPLAYED */
	if(table.isDisplayed())
	{
		return true;
	}
	return false;
}	

/* METHOD TO GET ALL THE HEADER LABEL NAMES */
public void getLabelNames() throws InterruptedException

{
	try
 	{
 		driver.switchTo().alert().accept();
 	}
 	catch(Exception e)
 	{
 		
 	}
	/* EXPAND HEADER SECTION  */
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#id_transactionentry_header > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > span:nth-child(2)")));
	WebElement headerexpand=driver.findElement(By.cssSelector("#id_transactionentry_header > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > span:nth-child(2)"));
	String headerexpcoll=headerexpand.getAttribute("class");
	if(!headerexpcoll.equalsIgnoreCase("col-xs-6 icon-collepse icon-font6 no_padding_left_right theme_color-inverse"))
	{
		headerexpand.click();	
	}
	/* GET ALL THE TABS OF THE HEADER AND GO TO EACH TAB AND GET ALL THE LABEL NAMES AND STORE IT INTO AN ARRAYLIST */
	List<WebElement> tabs= driver.findElements(By.xpath("//div[@id='id_transactionentry_header']/div[1]/div[1]/div[@id='id_tab_container']/ul/li"));
	WebElement tab= driver.findElement(By.xpath("//div[@id='id_transactionentry_header']/div[1]/div[1]/div[@id='id_tab_container']/ul/li"));
	totallabels.clear();
	totlabels.clear();
	for(int tabno=1;tabno<tabs.size();tabno++)
	{
		try
		{
		driver.findElement(By.xpath("//div[@id='id_transactionentry_header']/div[1]/div[1]/div[@id='id_tab_container']/ul/li[@id='id_transactionentry_headertab_listelement"+tabno+"']")).click();
		}
		catch(Exception e)
		{
			driver.findElement(By.xpath("/html/body/section/div[2]/div/section[1]/div[1]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[2]/div/div/a/span")).click();
			Thread.sleep(1000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='dropdown-menu pull-right']/li[@data-tabnumber='"+tabno+"']")));
			driver.findElement(By.xpath("//ul[@class='dropdown-menu pull-right']/li[@data-tabnumber='"+tabno+"']")).click();
		}
		Thread.sleep(1000);
	
	List<WebElement> rows=driver.findElements(By.xpath("//div[@id='id_transactionentry_header_tabdetail_container']/div[@id='id_transactionentry_header"+tabno+"_section']/div[1]/div[@class='row']"));
	label.clear();
	labelunmodified.clear();
	rowloop:
	for(int j=0;j<rows.size();j++)
	{
			for(int headerrow=1;headerrow<=rows.size();headerrow++)
			{
				List<WebElement> cols=driver.findElements(By.xpath("//div[@id='id_transactionentry_header_tabdetail_container']/div[@id='id_transactionentry_header"+tabno+"_section']/div[1]/div[@class='row']["+headerrow+"]/div"));
				for(int labelattribute=1;labelattribute<cols.size();labelattribute++)
				{
					String actvalue=driver.findElement(By.xpath("//div[@id='id_transactionentry_header_tabdetail_container']/div[@id='id_transactionentry_header"+tabno+"_section']/div[1]/div[@class='row']["+headerrow+"]/div[@class]["+labelattribute+"]/label")).getText();
					labelattribute++;
					label.add(actvalue);
					labelunmodified.add(actvalue);
					
				}
		
			}
	
		break rowloop;
	}
	totlabels.addAll(labelunmodified);
	unmodifiablelabel= Collections.unmodifiableList(totlabels);
	totallabels.addAll(labelunmodified);
	
	
	}
}

/* METHOD TO ENTER DATA TO THE HEADER FIELDS BY PASSING THE VALUES FROM EXCEL AS PARAMETERS  */
public void TransactionHeader(ArrayList<String> arrvalues) throws InterruptedException 
{
	
	ArrayList<String> value=new ArrayList();
	value=arrvalues;
	List<WebElement> tabs= driver.findElements(By.xpath("//div[@id='id_transactionentry_header']/div[1]/div[1]/div[@id='id_tab_container']/ul/li"));
	for(int tabno=1;tabno<tabs.size();tabno++)
	{
		try
		{
		driver.findElement(By.xpath("//div[@id='id_transactionentry_header']/div[1]/div[1]/div[@id='id_tab_container']/ul/li[@id='id_transactionentry_headertab_listelement"+tabno+"']")).click();
		}
		catch(Exception e)
		{
			driver.findElement(By.xpath("/html/body/section/div[2]/div/section[1]/div[1]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[2]/div/div/a/span")).click();
			Thread.sleep(1000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='dropdown-menu pull-right']/li[@data-tabnumber='"+tabno+"']")));
			driver.findElement(By.xpath("//ul[@class='dropdown-menu pull-right']/li[@data-tabnumber='"+tabno+"']")).click();
		}
		Thread.sleep(1000);
	
		List<WebElement> rows=driver.findElements(By.xpath("//div[@id='id_transactionentry_header_tabdetail_container']/div[@id='id_transactionentry_header"+tabno+"_section']/div[1]/div[@class='row']"));
		//LOOP THROUGH EACH LABEL NAME OF THE TRANSACTION HEADER 
		for(int j=0;j<unmodifiablelabel.size();j++)
			{
			//LOOP THROUGH EACH ROW OF THE TRANSACTION HEADER	
			headerrowloop:
			for(int headerrow=1;headerrow<=rows.size();headerrow++)
			{
				List<WebElement> cols=driver.findElements(By.xpath("//div[@id='id_transactionentry_header_tabdetail_container']/div[@id='id_transactionentry_header"+tabno+"_section']/div[1]/div[@class='row']["+headerrow+"]/div"));
				//FOR EACH ROW LOOP THROUGH EACH COLUMN
				Labelloop:
				for(int labelattribute=1;labelattribute<cols.size();labelattribute++)
					{
						String actvalue=driver.findElement(By.xpath("//div[@id='id_transactionentry_header_tabdetail_container']/div[@id='id_transactionentry_header"+tabno+"_section']/div[1]/div[@class='row']["+headerrow+"]/div[@class]["+labelattribute+"]/label")).getText();
						//IFTHE LABEL NAME WHICH IS CAPTURED FROM APPLICATION EQUAL TO THAT OF THE 
						if(actvalue.equalsIgnoreCase(unmodifiablelabel.get(j)))
						{
							
							int excelindex=excelattridlist.indexOf(actvalue);
							//logger.info(excelindex+ " & excelattridlist "+excelattridlist);
							int labelvalue=labelattribute+1;
							WebElement control=driver.findElement(By.xpath("//div[@id='id_transactionentry_header_tabdetail_container']/div[@id='id_transactionentry_header"+tabno+"_section']/div[1]/div[@class='row']["+headerrow+"]/div[@class]["+labelvalue+"]"));
							WebElement controltype;
							String elemtype,elemclass;
							controltype=control.findElement(By.cssSelector("div input:not([type='hidden']), div select:first-child"));
							elemclass=controltype.getAttribute("class");
							elemtype=controltype.getAttribute("type");
							if(excelindex>=0)
							{
								//IF VALUE WHICH IS SENT FROM EXCEL IS NOT EMPTY 
								if(!(value.get(excelindex).length()==0))
								{
									//IF THE FIELD IS AN OPTION CONTROL 
									if(elemclass.startsWith("FOptionControl"))
									{
										WebElement ele = null;
										String attr=control.findElement(By.cssSelector("div input:not([type='hidden'])[class='"+elemclass+"']")).getAttribute("id");
										String data = attr+"_data";
										wait.until(ExpectedConditions.visibilityOf(control.findElement(By.cssSelector("div input[id='"+attr+"']"))));
										ele=control.findElement(By.cssSelector("div input[id='"+attr+"'], div select[id='"+attr+"']"));
										ele.clear();
										Thread.sleep(1000);
										ele.sendKeys(Keys.SPACE);
										Thread.sleep(3000);
										//TO SLOW DOWN SENDING OF KEYS
										for (int i=0; i<value.get(excelindex).length(); i++)
										{	
											char ch=value.get(excelindex).charAt(i);
											ele.sendKeys(String.valueOf(ch));
											Thread.sleep((long)slowkeys);
											
										}
										Thread.sleep(1000);
										ele.click();
										Thread.sleep(1000);
										// CLICK TAB AFTER ENTERING THE GALUE
										ele.sendKeys(Keys.TAB);
										Thread.sleep(3000);
										List<WebElement> li= driver.findElements(By.xpath("//section[@id='mainDiv']/div[@id='divMasterPreview']/div"));
										if(li.get(0).getAttribute("class").equals(""))
										{
											wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/section/div[5]/div[1]/div/div/div/div/div[1]/div/div[2]/div[2]/div[2]/div/ul/li[2]/button[5]")));
											driver.findElement(By.xpath("/html/body/section/div[5]/div[1]/div/div/div/div/div[1]/div/div[2]/div[2]/div[2]/div/ul/li[2]/button[5]")).click();
										}
										
										break Labelloop;
									}
									// IF IT IS A TEXT FIELD 
									else if(elemtype.equalsIgnoreCase("text"))
									{
										String attr=control.findElement(By.cssSelector("div input:not([type='hidden'])[type='"+elemtype+"']")).getAttribute("id");
										wait.until(ExpectedConditions.visibilityOf(control.findElement(By.cssSelector("div input[id='"+attr+"']"))));
										WebElement ele=control.findElement(By.cssSelector("div input[id='"+attr+"']"));
										ele.clear();
										Thread.sleep(1000);
										ele.sendKeys(value.get(excelindex));
										Thread.sleep(1000);
										//IF IT IS AN EXCHANGE RATE FIELD 
										if(actvalue.equalsIgnoreCase("Exchange Rate"))
										{
											
											wait.until(ExpectedConditions.visibilityOf(control.findElement(By.cssSelector("div input[id='"+attr+"']"))));
											 ele=control.findElement(By.cssSelector("div input[id='"+attr+"']"));
											 exchrt=ele.getAttribute("value");
											break;
										}	
										break Labelloop;
									}
									
									//IF IT IS A CHECKBOX FIELD 
									else if(elemtype.equalsIgnoreCase("checkbox")&&elemclass.isEmpty())
									{
										String attr=control.findElement(By.cssSelector("div input:not([type='hidden'])[type='"+elemtype+"']")).getAttribute("id");
										wait.until(ExpectedConditions.visibilityOf(control.findElement(By.cssSelector("div input[id='"+attr+"']"))));
										WebElement ele=control.findElement(By.cssSelector("div input[id='"+attr+"']"));
										if(value.get(excelindex).equalsIgnoreCase("false"))
										{
											if(ele.isSelected())
											{
												ele.click();
											}
											else
											{
												
											}
										}
										else if(value.get(excelindex).equalsIgnoreCase("true"))
										{
											
											if(!ele.isSelected())
											{
												Thread.sleep(1000);
												wait.until(ExpectedConditions.elementToBeClickable(ele));
												ele.click();
											}
											else
											{
												
											}
											
										}
										Thread.sleep(1000);
										break Labelloop;
									}
									
									//IF IT IS A SELECT FIELD 
									else if(elemtype.equalsIgnoreCase("select-one"))
									{
										String attr=control.findElement(By.cssSelector("div select:first-child")).getAttribute("id");
										String data = attr+"_data";
										wait.until(ExpectedConditions.visibilityOf(control.findElement(By.cssSelector("div input[id='"+attr+"'], div select[id='"+attr+"']"))));
										WebElement ele=control.findElement(By.cssSelector("div input[id='"+attr+"'], div select[id='"+attr+"']"));
										ele.click();
										Thread.sleep(1000);
										Select dropdown= new Select(ele);
										List<WebElement> list = dropdown.getOptions();
										for(int selectelem=0; selectelem<list.size();selectelem++)
										{
											String listelements = list.get(selectelem).getText();
						        			if(listelements.contains(value.get(excelindex)))
						        				{
						        					list.get(selectelem).click();
						        				}
										}
										Thread.sleep(1000);
										break Labelloop;
									}
									
									// IF IT IS A DATE CONTROL FIELD 
									else if (elemclass.startsWith("datectrl"))
									{
										String attr=control.findElement(By.cssSelector("div input:not([type='hidden'])[class='"+elemclass+"']")).getAttribute("id");
										StringBuffer sb = new StringBuffer(attr); 
										sb.delete(attr.lastIndexOf("_"), attr.length()); 
										wait.until(ExpectedConditions.visibilityOf(control.findElement(By.cssSelector("div input[id='"+sb+"']"))));
										WebElement ele=control.findElement(By.cssSelector("div input[id='"+sb+"']"));
										ele.click();
										Thread.sleep(1000);
										ele.sendKeys(Keys.ARROW_LEFT,Keys.ARROW_LEFT,Keys.ARROW_LEFT,Keys.ARROW_LEFT);
										for (int i=0; i<value.get(excelindex).length(); i++)
										{	
											char ch=value.get(excelindex).charAt(i);
											ele.sendKeys(String.valueOf(ch));
											Thread.sleep((long)slowkeys);
										}
										Thread.sleep(1000);
										break Labelloop;
										
									}
									
									// IF IT IS A DOCUENT UPLOAD FIELD 
									else if (elemtype.equalsIgnoreCase("file"))
									{
										String attr=control.findElement(By.cssSelector("div input:not([type='hidden'])[type='"+elemtype+"']")).getAttribute("id");
										StringBuffer sb = new StringBuffer(attr); 
										sb.delete(attr.lastIndexOf("_"), attr.length());
										String browseattr=sb+"_browse_image";
										wait.until(ExpectedConditions.visibilityOf(control.findElement(By.cssSelector("div td[id='"+browseattr+"']"))));
										WebElement ele=control.findElement(By.cssSelector("div td[id='"+browseattr+"']"));
										ele.click();
										Thread.sleep(1000);
										wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div[class='font-6']")));
										WebElement BrowseFile= driver.findElements(By.cssSelector("div[class='font-6']")).get(0);
										BrowseFile.click();
										Thread.sleep(20000);
										String fileName = value.get(excelindex);
										//AUTOIT TO UPLOAD FILE FROM WINDOWS CONTROL 
										String autoITExecutable = "E:\\AutoIT\\FileUpload.exe " + fileName;
										Thread.sleep(1000);
										try 
										{
											Runtime.getRuntime().exec(autoITExecutable);
											Thread.sleep(10000);
											try 
											{
												
												String autoITExecutable2 = "E:\\AutoIT\\CloseFunc.exe ";
												Runtime.getRuntime().exec(autoITExecutable);
												break;
											}
											catch(IOException e1)
											{
												
											}
											break;
											
										} 
										catch (IOException e) 
										{
											e.printStackTrace();
										}
										ele.sendKeys(Keys.TAB);
										break Labelloop;
									}
									Thread.sleep(1000);
									break headerrowloop;
								}
								
							}
							// IF THE VALUE EQUALS EXCHANGE RATE
							if(actvalue.equalsIgnoreCase("Exchange Rate"))
							{
								String attr=control.findElement(By.cssSelector("div input:not([type='hidden'])[type='"+elemtype+"']")).getAttribute("id");
								wait.until(ExpectedConditions.visibilityOf(control.findElement(By.cssSelector("div input[id='"+attr+"']"))));
								WebElement ele=control.findElement(By.cssSelector("div input[id='"+attr+"']"));
								exchrt=ele.getAttribute("value");
								break;
							}
							
						else
						{
							Thread.sleep(1000);
							break;
						}
						}
				
						labelattribute++;
					
					}
				}
			}

	}
	
}

}
