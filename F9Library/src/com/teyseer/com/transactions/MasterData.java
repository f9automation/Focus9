
package com.teyseer.com.transactions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import com.focus.constants.HomePage;
import com.focus.constants.LaunchApplication;
import com.focus.constants.Menus;
import com.focus.library.MasterHomePage;

public class MasterData extends LaunchApplication
{
	HomePage hp = new HomePage();
	Menus menu= new Menus();
	MasterHomePage mhp = new MasterHomePage();
	ArrayList<String> headercolnos=new ArrayList<String>();
	ArrayList<String> xllables=new ArrayList<String>(Arrays.asList("Date Day Current","Payment Terms", "Name", "Code", "Debit / Credit proposal", "Exchange Adjustment Gain A/C", "Account Type", "Driving License Expiry Date", "Boolean", "Credit Limit", "Credit Days", "Market Segments", "Number List", "Tiny number", "small number", "Bignumber", "Address", "Salesman", "Bank Name", "Bank PO Box", "Customer Category", "Tax Card No Expiry Date", "Confirmation Instruction", "Activity-2", "Credit Card No", "Contact Address3", "Contact Person", "Send Email to customer", "Voucher Type", "Print Layout", "Department"));
	ArrayList<String> unmodifiedxllables=new ArrayList<String>(Arrays.asList("Date Day Current","Payment Terms", "Name", "Code", "Debit / Credit proposal", "Exchange Adjustment Gain A/C", "Account Type", "Driving License Expiry Date", "Boolean", "Credit Limit", "Credit Days", "Market Segments",  "Number List", "Tiny number", "small number", "Bignumber", "Address", "Salesman", "Bank Name", "Bank PO Box", "Customer Category", "Tax Card No Expiry Date", "Confirmation Instruction", "Activity-2", "Credit Card No", "Contact Address3", "Contact Person", "Send Email to customer", "Voucher Type", "Print Layout", "Department"));
	ArrayList<String> values= new ArrayList<String>(Arrays.asList("11/09/2018","60 Days Credit From Date Of Invoice","abc", "Abc code", "Credit","Abdul Aziz Abdullah Ameen",  "Cash", "29/03/2018", "true", "2.35", "3", "AutomotiveServices:Garages",  "two", "23", "456", "0235", "text addr", "Emerson Catacutan Manuel", "BANK OF TOKYO-MITSUBISHI UFJ", "test po box", "Installment", "12/04/2018", "Confirmation Instruction test","Equipment Services & Repairs", "123456", "Contact Address3 test", "Contact Person ananya", "true", "Budget,Cash Invoice - VSP", "APD _29012018,APD _29012018", "General"));
	public ArrayList<String> labelnames=new ArrayList<String>();
	double slowkeys=250;
	@Test
	
	
	/* METHOD TO MAKE DATA ENTRY TO THE MASTER BY PROVIDING THE LABEL NAMES WITH ITS RESPECTIVE VALUES FROM EXCEL AS PARAMETERS */
	public void masterdataentry(ArrayList<String> xllables, ArrayList<String> unmodifiedxllables, ArrayList<String> values ) throws InterruptedException
	{
		List<WebElement> tabs=driver.findElements(By.xpath("//div[@id='divTabContentGenerateMaster']/div"));
		String tabname=null;
		String labelname = null;
		labelnames.clear();
		
		// GENERALTABNAMES REFERES TO THE TAB WHICH DOESNOT HAVE ANY GRID
		ArrayList<String> generaltabnames=new ArrayList<String>();
		// GRIDTABNAMES REFERS TO THE TABS WHICH HAS GENERAL LABEL NAMES ALONG WITH GRID LABEL NAMES
		ArrayList<String> gridtabnames=new ArrayList<String>();
		WebElement tab=driver.findElement(By.xpath("//div[@id='divTabContentGenerateMaster']/div"));
		List<WebElement> hrefs=driver.findElements(By.cssSelector("a[href^='#newMasterDiv']"));
		/* GET ALL THE TABS AND LOOP THROUGH EACH OF IT */
		for(int tabno=0;tabno<tabs.size();tabno++)
		{
			//GET THE TAB NAME
			tabname=tabs.get(tabno).getAttribute("data-tabname");
			// GO THROUGH THIS LOOP IF THE TAB NAME NOT EQUALS OUTLET
			if(!(tabname.equalsIgnoreCase("outlet")))
			{
				//int tabnonew=tabno+1;
				if(tabs.size()>1)
				{
					
					try 
					{
					hrefs.get(tabno).click();
					}
					catch(Exception elem)
					{
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/section/div[2]/div/section[1]/div[1]/div/div[1]/div[2]/div[2]/div[2]/div[1]/div/div[2]/div[1]/ul/li[9]/div/div/a/span")));
						driver.findElement(By.xpath("/html/body/section/div[2]/div/section[1]/div[1]/div/div[1]/div[2]/div[2]/div[2]/div[1]/div/div[2]/div[1]/ul/li[9]/div/div/a/span")).click();
						hrefs.get(tabno).click();
					}
				}
				Thread.sleep(3000);
				int currenttabno=tabno+1;
				/* TO GET THE LIST OF ELEMENTS of EACH TAB */
					List<WebElement> tabelements= driver.findElements(By.xpath("//div[@id='divTabContentGenerateMaster']/div[@data-tabname='"+tabname+"']/div[@class='container-fluid']/div[not(contains(@style,'display:none'))]"));
					WebElement currenttab=driver.findElement(By.xpath("//div[@id='divTabContentGenerateMaster']/div[@data-tabname='"+tabname+"']/div[@class='container-fluid']"));
					generaltabnames.clear();
					headercolnos.clear();
					int indexvalue;
					String classattr, typeattr;
					for(int label=1;label<=tabelements.size();label++)
					{
						int count=0;
						WebElement labelnames=currenttab.findElement(By.xpath("//div[@id='divTabContentGenerateMaster']/div[@data-tabname='"+tabname+"']/div[@class='container-fluid']/div[not(contains(@style,'display:none'))]["+label+"]"));
						
						String divattr=labelnames.getAttribute("id");
						/* TO GET ALL THE LABEL NAMES FROM THE CURRENT TAB AND STORE IT INTO AN ARRAYLIST */
						if(divattr.length()==0)
							{
							try
							{
								
								wait.until(ExpectedConditions.visibilityOf(labelnames.findElement(By.cssSelector("div label a, div:nth-of-type(2) div label"))));
								labelname=labelnames.findElement(By.cssSelector("div label a, div:nth-of-type(2) div label")).getText();
								if(labelname.length()==0)
								{
									labelname=labelnames.findElement(By.cssSelector("div label a, div:nth-of-type(2) input:last-of-type")).getAttribute("name");
								}
							}
							
							catch(Exception e)
							{
								WebElement chckboxelem=currenttab.findElement(By.xpath("//div[@id='divTabContentGenerateMaster']/div[@data-tabname='"+tabname+"']/div[@class='container-fluid']/div[not(contains(@style,'display:none'))]["+label+"]/div[2]"));
								labelname=chckboxelem.findElement(By.cssSelector("input:last-of-type")).getAttribute("id");
									
							}
							generaltabnames.add(labelname);
							}
						if(divattr.length()>0)
						{
							List<WebElement> tableheads= currenttab.findElements(By.xpath("//div[@id='divForTblNewMasterGenerator']/div/table[@id='tblNewMasterGenerator']/thead/tr/th[not(contains(@style,'display:none'))]"));
							int headercol;
							gridtabnames.clear();
							for(int col=2;col<=tableheads.size();col++)
							{
								WebElement tableheaders = null;
								try
								{
								 tableheaders=currenttab.findElement(By.xpath("//div[@id='divForTblNewMasterGenerator']/div/table[@id='tblNewMasterGenerator']/thead/tr/th[not(contains(@style,'display:none'))]["+col+"]"));
								}
								catch(Exception e)
								{
									break;
									
								}
								wait.until(ExpectedConditions.presenceOfElementLocated((By.cssSelector("label"))));
								WebElement tableheader= tableheaders.findElement(By.cssSelector("label"));
								String colname=tableheader.getText();
								if(!(colname.length()==0))
								{
									headercol=col;
									labelname=colname;
									gridtabnames.add(labelname);
									headercolnos.add(String.valueOf(headercol));
								}
								
							}	
						}
						
					}
					labelnames.addAll(generaltabnames);
					labelnames.addAll(gridtabnames);
					/* IF IT IS A GENERALTAB */
					if(generaltabnames.size()>0)
						{
						try
						{
							//TO COMPARE EACH LABEL NAME OF ARRAYLIST WHICH IS SENT FROM EXCEL WITH THE NAME WHICH IS CAPTURED AND STORED IN AN ARRAYLIST FROM APPLICATION 
							xllables.addAll(generaltabnames);
							Collections.reverse(xllables);
							xllables.remove(xllables.size()-1);
							int labelno;
							int xlindex;
							try
							{
							
								for(String masterattribute: generaltabnames)
								{
								for(String xlattribute: xllables)
								{
									// COMPARE AND GO TO THE LOOP IF THE LABEL NAME OF APPLICATION MATCHES WITH NAME WHICH IS SENT FROM APPLICATION
									if(masterattribute.equalsIgnoreCase(xlattribute))
									{
										try
										{
											
										labelno= generaltabnames.indexOf(masterattribute)+1;
										xlindex=unmodifiedxllables.indexOf(masterattribute);
										//MOVE TO THE MATCHED LABELS VALUE ELEMENT (WHERE THE DATA CAN BE ENTERED)
										wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='divTabContentGenerateMaster']/div[@data-tabname='"+tabname+"']/div[@class='container-fluid']/div[not(contains(@style,'display:none'))]["+labelno+"]/div[2]")));
										WebElement currentvalue=currenttab.findElement(By.xpath("//div[@id='divTabContentGenerateMaster']/div[@data-tabname='"+tabname+"']/div[@class='container-fluid']/div[not(contains(@style,'display:none'))]["+labelno+"]/div[2]"));
										WebElement currentattr=currentvalue.findElement(By.cssSelector("input:not([type='hidden']), select:last-of-type, span:last-of-type"));
										classattr=currentattr.getAttribute("class");
										typeattr=currentattr.getAttribute("data-type");
										// GET THE VALUE WHICH NEED TO BE ENTERED BY PASSING THE INDEX OF THE LABEL AS INDEX TO THAT OF THE VALUES ARRAYLIST
										String actvalue=values.get(xlindex);
										// ENTER TO THE LOOP ONLY IF THE VALUE WHICH NEED TO BE ENTERED IS NOT EMPTY OR NULL
										if(actvalue.length()>0)
											{
											// PERFORM THIS IF IT IS AN OPTION CONTROL FIELD
											if(classattr.startsWith("FOptionControl"))
											{
												currentvalue.click();
												Actions act=new Actions(driver);
												act.moveToElement(currentvalue).click().perform();
												act.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL);
												act.sendKeys(Keys.SPACE).build().perform();
												act.sendKeys(Keys.DELETE);
												act.build().perform();
												act.sendKeys(Keys.SPACE).perform();
												Thread.sleep(3000);
												for(int i=0; i<values.get(xlindex).length(); i++)
												{
													char ch=actvalue.charAt(i);
													act.sendKeys(String.valueOf(ch));
													act.build().perform();
													Thread.sleep((long) slowkeys);
													
												}
												String  name, code,text;
												String divId= "_div_data";
							        			String tableID="_table_data";
												List<WebElement> inputelementscount= currentvalue.findElements(By.cssSelector("div[id$='_div_data'] table[id$='_table_data'] tbody tr"));
							        			for(int k=1; k<=inputelementscount.size();k++)
							    		        {
							    		    	  WebElement inputvalues;
							    		    	  inputvalues=currentvalue.findElement(By.cssSelector("div[id$='_div_data'] table[id$='_table_data'] tbody tr:nth-of-type("+k+")"));
							    		        	text=inputvalues.getText();
							    		        	code=inputvalues.getAttribute("data-scode");
							    		        	name=inputvalues.getAttribute("data-sname");
							    		        	if(code==null)
							    		        	{
							    		        		break;
							    		        	}
							    		        
							    		        	else if(code.equalsIgnoreCase(actvalue))
							    		        	{
							    		        		wait.until(ExpectedConditions.elementToBeClickable(currentvalue.findElement(By.cssSelector("div[id$='_div_data'] table[id$='_table_data'] tbody tr:nth-of-type("+k+")"))));
						    		        			inputvalues.click();
						    		        			Thread.sleep(1000);
						        						break;
							    		        	}
							    		        	
							    		        	if(name.equalsIgnoreCase(actvalue))
							    		        	{
							    		        		wait.until(ExpectedConditions.elementToBeClickable(currentvalue.findElement(By.cssSelector("div[id$='_div_data'] table[id$='_table_data'] tbody tr:nth-of-type("+k+")"))));
						    		        			inputvalues.click();
						    		        			Thread.sleep(1000);
						        						break;
							    		        		
							    		        	}
							    		        	else if(actvalue.equalsIgnoreCase(actvalue))
							    		        	{
							    		        		wait.until(ExpectedConditions.elementToBeClickable(currentvalue.findElement(By.cssSelector("div[id$='_div_data'] table[id$='_table_data'] tbody tr:nth-of-type("+k+")"))));
						    		        			inputvalues.click();
						    		        			Thread.sleep(1000);
						        						 break;
							    		        	}
							    		        }
							    		        	
												break;
											}
											
											//PERFORM THIS IF IT IS A SELECT OPTION CONTROL FIELD 
											if(classattr.startsWith("Fselect"))
											{
												wait.until(ExpectedConditions.visibilityOf(currentvalue.findElement(By.cssSelector("select"))));
								        		if(currentvalue.findElement(By.cssSelector("select")).isDisplayed())
								        		{
								        		WebElement mySelectElement = currentvalue.findElement(By.cssSelector("select"));
								        		Thread.sleep(1000);
								        		Select dropdown= new Select(mySelectElement);
								        		dropdown.selectByVisibleText(actvalue);
								        		
								        		}
								        		
												break;	
											}
											//PERFORM THIS IF IT IS A DATE CONTROL FIELD 
											if(classattr.equalsIgnoreCase("datectrl_checkbox"))
											{
												wait.until(ExpectedConditions.visibilityOf(currentvalue.findElement(By.cssSelector("input[id$='_checkbox']"))));
												WebElement datechckbox=currentvalue.findElement(By.cssSelector("input[id$='_checkbox']"));
												if(!(datechckbox.isSelected()))
												{
													datechckbox.click();
												}
												wait.until(ExpectedConditions.visibilityOf(currentvalue.findElement(By.cssSelector("input[class^='dateinput']"))));
												WebElement datecrtl=currentvalue.findElement(By.cssSelector("input[class^='dateinput']"));
												datecrtl.click();
												Actions act2=new Actions(driver);
												act2.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL);
												for(int i=0; i<values.get(xlindex).length(); i++)
												{
													char ch=actvalue.charAt(i);
													act2.sendKeys(String.valueOf(ch));
													act2.build().perform();
													Thread.sleep((long) slowkeys);
													
												}
												
												break;
											}
											
											/* PEROFM THIS IF THE ATTRIBUTE MATCHES TEXT FIELD */
											if(classattr.contains("Ftxtarea")||(classattr.contains("form-control-feedback"))||(classattr.equalsIgnoreCase("Ftxtbox"))||(typeattr.equalsIgnoreCase("textbox")))
											{
												currentvalue.click();
												Actions act=new Actions(driver);
												act.moveToElement(currentvalue).click().perform();
												act.sendKeys(Keys.chord(Keys.CONTROL, "a"));
												act.sendKeys(Keys.DELETE);
												act.build().perform();
												for(int i=0; i<values.get(xlindex).length(); i++)
												{
													char ch=actvalue.charAt(i);
													act.sendKeys(String.valueOf(ch));
													act.build().perform();
													Thread.sleep((long) slowkeys);
													
												}
												break;
											}
											/*
											if(classattr.contains("form-control-feedback"))
											{
												currentvalue.click();
												Actions act=new Actions(driver);
												act.moveToElement(currentvalue).click().perform();
												act.sendKeys(Keys.chord(Keys.CONTROL, "a"));
												act.sendKeys(Keys.DELETE);
												act.build().perform();
												for(int i=0; i<values.get(xlindex).length(); i++)
												{
													char ch=actvalue.charAt(i);
													act.sendKeys(String.valueOf(ch));
													act.build().perform();
													Thread.sleep((long) slowkeys);
													
												}
												break;
											}
											
											if(classattr.equalsIgnoreCase("Ftxtbox"))
											{

												currentvalue.click();
												Actions act=new Actions(driver);
												act.moveToElement(currentvalue).click().perform();
												act.sendKeys(Keys.chord(Keys.CONTROL, "a"));
												act.sendKeys(Keys.DELETE);
												act.build().perform();
												for(int i=0; i<values.get(xlindex).length(); i++)
												{
													char ch=actvalue.charAt(i);
													act.sendKeys(String.valueOf(ch));
													act.build().perform();
													Thread.sleep((long) slowkeys);
													
												}
												break;
											}
											
											if(typeattr.equalsIgnoreCase("textbox"))
												{
													currentvalue.click();
													Actions act=new Actions(driver);
													act.moveToElement(currentvalue).click().perform();
													act.sendKeys(Keys.chord(Keys.CONTROL, "a"));
													act.sendKeys(Keys.DELETE);
													act.build().perform();
													for(int i=0; i<values.get(xlindex).length(); i++)
													{
														char ch=actvalue.charAt(i);
														act.sendKeys(String.valueOf(ch));
														act.build().perform();
														Thread.sleep((long) slowkeys);
														
													}
													break;
												}
											*/
												// PEROFM THIS IF IT IS A NUMBER FIELD 
												if(typeattr.equalsIgnoreCase("number-text")||(typeattr.equalsIgnoreCase("fraction"))||(typeattr.equalsIgnoreCase("bigNumber"))||(typeattr.equalsIgnoreCase("smallNumber")||(typeattr.equalsIgnoreCase("tinyNumber"))))
												{
													currentvalue.click();
													Actions act=new Actions(driver);
													act.moveToElement(currentvalue).click().perform();
													act.sendKeys(Keys.chord(Keys.CONTROL, "a"));
													act.sendKeys(Keys.DELETE);
													act.build().perform();
													for(int i=0; i<values.get(xlindex).length(); i++)
													{
														char ch=actvalue.charAt(i);
														act.sendKeys(String.valueOf(ch));
														act.build().perform();
														Thread.sleep((long) slowkeys);
														
													}
			
													break;
												}
												//PEROFM THIS IF IT IS A FRACTION FIELD 
												/*
												if(typeattr.equalsIgnoreCase("fraction"))
												{
													currentvalue.click();
													Actions act=new Actions(driver);
													act.moveToElement(currentvalue).click().perform();
													act.sendKeys(Keys.chord(Keys.CONTROL, "a"));
													act.sendKeys(Keys.DELETE);
													act.build().perform();
													for(int i=0; i<values.get(xlindex).length(); i++)
													{
														char ch=actvalue.charAt(i);
														act.sendKeys(String.valueOf(ch));
														act.build().perform();
														Thread.sleep((long) slowkeys);
														
													}
			
													break;
												}*/
												//PERFORM THIS IF IT IS A CHECKBOX FIELD
												if(typeattr.equalsIgnoreCase("checkbox"))
												{
													if(actvalue.equalsIgnoreCase("true"))
													{
														WebElement chckbox=currentvalue.findElement(By.cssSelector("input[data-type='checkbox']"));
														if(!(chckbox.isSelected()))
														{
															chckbox.click();
														}
														
													}
													else
													{
														WebElement chckbox=currentvalue.findElement(By.cssSelector("input[data-type='checkbox']"));
														if((chckbox.isSelected()))
														{
															chckbox.click();
														}
													}
													break;
												}
												//PEROFM THIS IF IT IS A DOCUMENT UPLOAD FIELD 
												if(typeattr.equalsIgnoreCase("uploadFile"))
												{
													//System.out.println("upload file loop");
													wait.until(ExpectedConditions.visibilityOf(currentvalue.findElement(By.cssSelector("div[class^='fileUpload']"))));
													currentvalue.findElement(By.cssSelector("div[class^='fileUpload']")).click();
													Thread.sleep(1000);
													String fileName = actvalue;   
													//USE AUTOIT FILE TO UPLOAD A FILE USING WINDOWS OPTION CONTROL 
													String autoITExecutable = "E:\\AutoIT\\FileUpload.exe " + fileName;
													Thread.sleep(1000);
													try 
													{
														Runtime.getRuntime().exec(autoITExecutable);
														Thread.sleep(5000);
													} 
													catch (IOException e) 
													{
														
														e.printStackTrace();
													}
													
													break;
												}
												// PERFORM THIS IF IT IS AN IMAGE UPLOAD FIELD
												if(typeattr.equalsIgnoreCase("image"))
												{
													wait.until(ExpectedConditions.visibilityOf(currentvalue.findElement(By.cssSelector("div[class^='fileUpload']"))));
													currentvalue.findElement(By.cssSelector("div[class^='fileUpload']")).click();
													Thread.sleep(1000);
													String fileName = actvalue;   
													//USE AUTOIT FILE TO UPLOAD A FILE USING WINDOWS OPTION CONTROL 
													String autoITExecutable = "E:\\AutoIT\\FileUpload.exe " + fileName;
													Thread.sleep(1000);
													try 
													{
														Runtime.getRuntime().exec(autoITExecutable);
														Thread.sleep(5000);
													} 
													catch (IOException e) 
													{
														
														e.printStackTrace();
													}
													
													break;
												}
												//PERFORM THIS IF IT IS A BIGNUMBER FIELD
												/*
												if(typeattr.equalsIgnoreCase("bigNumber"))
												{
													currentvalue.click();
													Actions act=new Actions(driver);
													act.moveToElement(currentvalue).click().perform();
													act.sendKeys(Keys.chord(Keys.CONTROL, "a"));
													act.sendKeys(Keys.DELETE);
													act.build().perform();
													for(int i=0; i<values.get(xlindex).length(); i++)
													{
														char ch=actvalue.charAt(i);
														act.sendKeys(String.valueOf(ch));
														act.build().perform();
														Thread.sleep((long) slowkeys);
														
													}
			
													break;
												}
												//PERFORM THIS IF IT IS A SMALL NUMBER FIELD 
												if(typeattr.equalsIgnoreCase("smallNumber"))
												{
													currentvalue.click();
													Actions act=new Actions(driver);
													act.moveToElement(currentvalue).click().perform();
													act.sendKeys(Keys.chord(Keys.CONTROL, "a"));
													act.sendKeys(Keys.DELETE);
													act.build().perform();
													for(int i=0; i<values.get(xlindex).length(); i++)
													{
														char ch=actvalue.charAt(i);
														act.sendKeys(String.valueOf(ch));
														act.build().perform();
														Thread.sleep((long) slowkeys);
														
													}
			
													break;
												}
												//PERFORM THIS IF IT IS A TINY NUMBER FIELD 
												if(typeattr.equalsIgnoreCase("tinyNumber"))
												{
													currentvalue.click();
													Actions act=new Actions(driver);
													act.moveToElement(currentvalue).click().perform();
													act.sendKeys(Keys.chord(Keys.CONTROL, "a"));
													act.sendKeys(Keys.DELETE);
													act.build().perform();
													for(int i=0; i<values.get(xlindex).length(); i++)
													{
														char ch=actvalue.charAt(i);
														act.sendKeys(String.valueOf(ch));
														act.build().perform();
														Thread.sleep((long) slowkeys);
														
													}
			
													break;
													
												}
											*/
											break;
											}
										break;
										}
										catch(Exception e)
										{
											break;
										}
									
									}
								}
							}
							}
							catch(Exception e)
							{
															}
						}
				
				catch(Exception e)
				{
					
				}
			}
				
				/* IF IT IS A GRID TAB */	
				if(gridtabnames.size()>0)	
				{
				try
				{
					//TO COMPARE EACH LABEL NAME OF ARRAYLIST WHICH IS SENT FROM EXCEL WITH THE NAME WHICH IS CAPTURED AND STORED IN AN ARRAYLIST FROM APPLICATION 
					xllables.addAll(gridtabnames);
					Collections.reverse(xllables);
					xllables.remove(xllables.size()-1);
					int labelno;
					int xlindex;
					try
					{
						for(String masterattribute: gridtabnames)
						{
						for(String xlattribute: xllables)
							{
							
							// COMPARE AND GO TO THE LOOP IF THE LABEL NAME OF APPLICATION MATCHES WITH NAME WHICH IS SENT FROM APPLICATION
							if(masterattribute.equalsIgnoreCase(xlattribute))
								{
									try
									{
										xlindex=unmodifiedxllables.indexOf(masterattribute);
										String actvalue=values.get(xlindex);
										ArrayList<String> splitactvalue= new ArrayList<String>(Arrays.asList(actvalue.split(",")));
										int colno=gridtabnames.indexOf(masterattribute);
										for(int bodyrow=1;bodyrow<=splitactvalue.size();bodyrow++)
										{
											String rowvalue=splitactvalue.get(bodyrow-1);
											if(rowvalue.length()>0)
											{
												String bodycol= headercolnos.get(colno);
												wait.until(ExpectedConditions.visibilityOf(currenttab.findElement(By.cssSelector("div table[id='tblNewMasterGenerator'] tbody tr:nth-of-type("+bodyrow+") td:nth-of-type("+bodycol+")"))));
												WebElement bodyelem=currenttab.findElement(By.cssSelector("div table[id='tblNewMasterGenerator'] tbody tr:nth-of-type("+bodyrow+") td:nth-of-type("+bodycol+")"));
												bodyelem.click();
												Actions act=new Actions(driver);
												for(int i=0; i<rowvalue.length(); i++)
												{
													char ch=rowvalue.charAt(i);
													act.sendKeys(String.valueOf(ch));
													act.build().perform();
													Thread.sleep((long) slowkeys);
												}
												act.sendKeys(Keys.TAB).perform();
												Thread.sleep(2000);
											}
										}
									}
								
								catch(Exception e1)
								{
									break;
								}
									break;
								}
							}
					}
					}
					catch(Exception e2)
					{
						
					}
					
					
					
					}
				
				
				catch(Exception e)
				{
				}
			}
			}
	}
		
	
	}
	
	
}