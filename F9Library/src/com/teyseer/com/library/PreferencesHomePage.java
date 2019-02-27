package com.teyseer.com.library;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.print.attribute.standard.MediaSize.NA;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.focus.constants.HomePage;
import com.focus.constants.LaunchApplication;
import com.focus.constants.Menus;

//@Listeners(value=Reporter.class)
public class PreferencesHomePage extends LaunchApplication
{
	HomePage hp=new HomePage();
	Menus menu=new Menus();
	public String preflist = "" ;
	public String screentitle="";
	public String actmsg;  
	/*
	@Test
	public void login() throws InterruptedException 
	{ 
		  hp.LoginApp("su", "su", "AUTO11018 (010)");
		  boolean res;
		  res=menu.menuSelection("Settings", "Configure Transactions", "fgsdfg");
		  String prefitems="Tags ,Accounts  ,Budgets  ,AR/AP  ,Miscellaneous  ,PDC ,Inventory  ,Batch  ,RMA  ,Bins  ,Hire Purchase  ,Quotation Analysis  ,RFID  ,Auto Indent  ,ABC Analysis ,Print ,Internet  ,Mail Settings ,Masters ,External Modules ,Info ,Production ,Fixed Assets ,VAT ,POS ,Warehouse Management ,Letter of Credit";
		  res=comparePreferenceItems(prefitems);
		  
		  res=clickPreference("Tags");
		  ArrayList<String> labels=new ArrayList(Arrays.asList("Accounting", "Payroll site", "Payroll cost center", "Inventory"));
		  ArrayList<String> values=new ArrayList(Arrays.asList("Department", "", "Tax Code", "Capacity"));
		  tagsDataEntry(labels,values);
		  updatePreferences("saved succ");
		  clickPreference("Accounts");
		  ArrayList<String> labels=new ArrayList(Arrays.asList("Assets Group", "Cash and Bank Group", "Income taxes A/C", "Expenses Group", "Check Negative Cash Balance", "Check Negative cash by Tag", "Warn and Allow", "Stop", "Do not balance stock transfers when posting to FA"));
		  ArrayList<String> values=new ArrayList(Arrays.asList("CONTROL ACCOUNTS", "Cash & bank", "Cost of goods sold - Computers", "Administrative Expenses", "true", "true", "kjlkjk", "true", "true", "true"));
		  accountsData(labels, values);
		
		  res=clickPreference("Budgets");
		  ArrayList<String> labels=new ArrayList(Arrays.asList("By account by Product by tag", "Budget planning","Tag2","Tag3","Check budgets in data entry", "Annual", "Warn and Allow", "Stop"));
		  ArrayList<String>	values=new ArrayList(Arrays.asList("true", "Capacity","Process","Insurance","true", "true", "true","true"));
		  budgetData(labels,values);
		   
		  
		  res=clickPreference("PDC");
		 
		 //SoftAssert
		  //ArrayList<String> labels=new ArrayList(Arrays.asList("Tag","Enable Credit Limit check", "Stop", "Enable Overdue check", "stop", "Tag", "Depends on maintain bill wise for AR/AP", "Maintain AR Transaction Currencies"));
		 // ArrayList<String> values=new ArrayList(Arrays.asList("Maintain AR by Tag, true, GiftVoucherDefinition", "true", "true", "true","true", "Maintain AP by Tag, true, KitchenDisplaySystem", "true", "true"));
		  //aRAPData(labels,values);
		  /*
		  ArrayList<String> labels=new ArrayList(Arrays.asList("Add free items on a new line", "Calculate due-date from LR-date", "Create Customer Profile Fields", "Dialog based entry in vouchers", "Do not refresh description with Account/Product in document", "Include un-committed transactions in reports", "Maintain links for only one side of stock transfer", "Show status message in popup also", "Prefix location code while importing from different location", "Show Transaction data in FIFO order", "Enable local currency", "Local currency", "Do not show opening balances in nominal ledgers", "Do not store dates of entries", "Show exchange rate difference in ledger", "Enable hijri date", "Enable profitability check by product", "Do not Load document in exlusive mode", "Create master in transaction entry", "Go to next voucher number after saving voucher", "Open search if master not found", "Default calendar", "Default Currency", "Image format", "Numeric separator"));
		  ArrayList<String> values=new ArrayList(Arrays.asList("true", "true", "true", "true", "false", "true", "true", "true", "true", "true","true", "Aruba Guilders (also called Florins)", "true", "false", "true", "false", "true", "true", "true", "true", "false", "dfdfsd", "El Salvador Colones", "JPEG", "#,###,###,000"));
		  miscellaneousData(labels, values);
		 ArrayList<String> labels=new ArrayList(Arrays.asList("Post Dated Cheques", "Display in ledgers and trial balance", "PDC Discounted Account"));
		 ArrayList<String> values=new ArrayList(Arrays.asList("true", "Display in ledgers and trial balance", "Loan from associate company"));
		  pdcData(labels, values);
		  Thread.sleep(3000);
		  res=clickPreference("Inventory");
		  Thread.sleep(3000);
		  inventoryData();
		  driver.quit();
		 
	
	}*/
	
	/* METHOD TO GET ALL THE PREFERENCES LIST */
	public void prefernceItemsList()
	{
		ArrayList<String> preflistelems=new ArrayList();
		preflistelems.clear();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("preferenceUL")));
		List<WebElement> preferencelist=driver.findElements(By.xpath("//ul[@id='preferenceUL']/li"));
		for(int li=0;li<preferencelist.size();li++)
		{
			String elem=preferencelist.get(li).getText();
			if(!(li==preferencelist.size()))
			{
			elem=elem.replaceAll("\\n", "  ,");
			preflistelems.add(elem);
		}
			
			
		}
		preflist="";
		for(String p: preflistelems)
		{
			
			preflist+=p+" ,";
		}
		preflist=preflist.substring(0, preflist.length()-2);
	}
	
	/* METHOD TO COMPARE PREFERENCE ITEMS LIIST BY PASSING THE PREFERNCES LIST FROM EXCEL AS PARAMETERS */
	public boolean comparePreferenceItems(String xlpreflist)
	{
		prefernceItemsList();
		if(preflist.equals(xlpreflist))
		{
			
			return true;
		}
		return false;
		
	}
	
	/* METHOD TO CLICK PREFERENCE ITEM BY PASSING THE ITEM TO BE CLICKED AS A PARAMETER FROM EXCEL */
	public boolean clickPreference(String xlprefitem) 
	{
		prefernceItemsList();
		ArrayList<String> preferenceitems=new ArrayList(Arrays.asList(preflist.split(",")));
		for(String prefitem: preferenceitems)
		{
			if(xlprefitem.equalsIgnoreCase(prefitem.trim()))
			{
				wait.until(ExpectedConditions.elementToBeClickable(By.linkText(xlprefitem)));
				List<WebElement> matchelems= driver.findElements(By.linkText(xlprefitem));
				int totmacthelems=matchelems.size();
				logger.info("totmatch "+totmacthelems);
				matchelems.get(totmacthelems-1).click();
				wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;")); 
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ScreenTitle")));
				screentitle=driver.findElement(By.id("ScreenTitle")).getText();
				if(screentitle.startsWith(xlprefitem))
				{
					return true;
				}
				else
				{
					return false;
				}
			}
		}
		return false;
		
	}
	/* METHOD TO CLOSE PREFERENCE SCREEN */
	public boolean closeScreen() throws InterruptedException
	{
		wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='configTransMenu']/ul/li[2]/span[2]")));
		driver.findElement(By.xpath("//*[@id='configTransMenu']/ul/li[2]/span[2]")).click();
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
	
	/* METHOD TO ENTER DATA BY PASSING THE LABEL NAMES AND THE RESPECTIVE VALUES AS PARAMETERS FROM EXCEL */
	public void tagsDataEntry(ArrayList<String> xllabels, ArrayList<String> xlvalues) throws Exception
	{
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dvTag")));
		List<WebElement> rows=driver.findElements(By.xpath("//div[@id='dvTag']/div"));
		for(String xllabel: xllabels)
		{	
			for(int r=1;r<=rows.size();r++)
			{
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='dvTag']/div["+r+"]/label")));
				String label=driver.findElement(By.xpath("//div[@id='dvTag']/div["+r+"]/label")).getText();
				if(xllabel.equalsIgnoreCase(label))
				{
					String value=xlvalues.get(xllabels.indexOf(xllabel));
					if(value.length()>0)
					{
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='dvTag']/div["+r+"]/div")));
						WebElement myselectelem=driver.findElement(By.xpath("//div[@id='dvTag']/div["+r+"]/div/select"));
						myselectelem.click();
						Select dropdown=new Select(myselectelem);
						List<WebElement> list=dropdown.getOptions();
						for(int li=0;li<list.size();li++)
						{
							String optionname = list.get(li).getText();
							if(optionname.equalsIgnoreCase(value))
							{
								list.get(li).click();
								Thread.sleep(1000);
								
								break;
							}
						}
					}
					break;
				}
			}
		}
	}
	
	/* METHOD TO UPDATE PREFERENCES BY PASSING THE EXPECTED MESSAGE AS PARAMETER FROM EXCEL */
	public boolean updatePreferences(String expmsg) throws InterruptedException
	{
		wait.until(ExpectedConditions.elementToBeClickable(By.id("updateButton")));
		driver.findElement(By.id("updateButton")).click();
		Thread.sleep(3000);
		try
		{
			driver.switchTo().alert().accept();
		}
		catch(Exception e)
		{
			logger.info("Alert not found");
		}
		try
		{
			driver.switchTo().alert().accept();
		}
		catch(Exception e)
		{
			logger.info("Alert not found");
		}
		wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;")); 
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
			    	 		logger.info("Actmsg "+actmsg);
			    			driver.findElement(By.xpath("//*[@id='idGlobalError']/div/table/tbody/tr/td[3]")).click();
			    		}
			    	 
			    	
				}
		     }
		     catch(Exception e1)
		     {
		    	 
		     }
		finally
		{
			if(actmsg.contains(expmsg))
			{
				return true;
			}
		}
		return false;
		
	}
	/* METHOD TO SELECT ELEMENT WHICH IS A CHECKBOX ELEMENT BY PASSING THE LABEL NAME AND ITS VALUE AS PARAMETERS */
	public void checkboxSelection(WebElement checkboxelem, String xlvalue) throws InterruptedException
	{
		if(xlvalue.equalsIgnoreCase("true"))
		{
			if(!(checkboxelem.isSelected()))
			{
				Thread.sleep(1000);
				Actions action=new Actions(driver);
				Action actionslist=action.
				moveToElement(checkboxelem).click()
				.build();
				actionslist.perform();
				Thread.sleep(3000);
				
			}
		}
	}
	/* METHOD TO SELECT ELEMENT WHICH IS A RADIO ELEMENT BY PASSING THE LABEL NAME AND ITS VALUE AS PARAMETERS */
	public void radioSelection(WebElement radioelem, String xlvalue) throws InterruptedException
	{
		if(xlvalue.equalsIgnoreCase("true"))
		{
			if(!(radioelem.isSelected()))
			{
				Thread.sleep(1000);
				Actions action=new Actions(driver);
				Action actionslist=action.
				moveToElement(radioelem).doubleClick()
				.build();
				actionslist.perform();
				Thread.sleep(3000);
			}
		}
	}
	
	/* METHOD TO ENTER DATA TO ACCOUNTS TAG BY PASSING LABEL NAMES AND ITS VALUES AS PARAMETES FROM EXCEL */
	public void accountsData(ArrayList<String> xllabels, ArrayList<String> xlvalues) throws InterruptedException
	{
		List<WebElement> groups=driver.findElements(By.xpath("//div[@id='dvAccount']/div"));
		for(int grp=2;grp<=groups.size();grp++)
		{
			List<WebElement> rows=driver.findElements(By.xpath("//div[@id='dvAccount']/div["+grp+"]/div"));
			
			rowloop:
			for(int row=1;row<=rows.size();row++)
			{
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='dvAccount']/div["+grp+"]/div["+row+"]")));
				WebElement label=driver.findElement(By.xpath("//div[@id='dvAccount']/div["+grp+"]/div["+row+"]"));
				WebElement child = label;
				String style=child.getAttribute("textContent").trim();
				if(style.equalsIgnoreCase("General Info"))
				{
					//logger.info("this is style loop");
				}
				else
				{
				try
				{
				 child=label.findElement(By.xpath(".//*"));
				}
				catch(Exception e)
				{
					
				}
				if(!(child.getTagName().equalsIgnoreCase("hr")))
				{
					String labelname;
					String inputtype;
					
					if(child.getTagName().equals("div"))
					{
						
						List<WebElement> childdivsold =driver.findElements(By.xpath("//div[@id='dvAccount']/div["+grp+"]/div["+row+"]/div"));
						List<WebElement> childdivs=driver.findElements(By.cssSelector("div[id='dvAccount'] div:nth-of-type("+grp+") div:nth-of-type("+row+") div, div[id='dvAccount'] div:nth-of-type("+grp+") div:nth-of-type("+row+") div"));
						if(childdivs.size()>0)
						{
							WebElement childdivelem=driver.findElement(By.xpath("//div[@id='dvAccount']/div["+grp+"]/div["+row+"]/div"));
							List<WebElement> labelelems=childdivelem.findElements(By.cssSelector("label:last-child"));
							for(int i=0;i<labelelems.size();i++)
							{
								List<WebElement> inputelems=childdivelem.findElements(By.cssSelector("input"));
								labelname=labelelems.get(i).getText().trim();
								for(String xllabel: xllabels)
								{
									if(xllabel.equalsIgnoreCase(labelname))
									{
										
										//logger.info("labelname "+labelname);
										inputtype=inputelems.get(i).getAttribute("type");
										WebElement labelvalelem=inputelems.get(i);
										String bodyvalue=xlvalues.get(xllabels.indexOf(xllabel));
										if(bodyvalue.length()>0)
										{
											//logger.info("if loop ");
											if(inputtype.equalsIgnoreCase("checkbox"))
											{
												checkboxSelection(labelvalelem, bodyvalue);
												
											}
											if(inputtype.equalsIgnoreCase("radio"))
											{
												radioSelection(labelvalelem, bodyvalue);
											}
										}
										xllabels.set(xllabels.indexOf(xllabel), "");
										break;
									}
								}
								
							}
						}
						
					}
					else
					{
						
						
						labelname=label.findElement(By.cssSelector("label")).getText();
						for(String xllabel: xllabels)
						{
							
							if(xllabel.equalsIgnoreCase(labelname))
							{
								//logger.info("labelname "+labelname);
								WebElement labelvalelem=label.findElement(By.cssSelector("input:last-child"));
								inputtype=labelvalelem.getAttribute("type");
								//logger.info(labelname +" and its input type "+inputtype);
								String bodyvalue=xlvalues.get(xllabels.indexOf(xllabel));
								if(bodyvalue.length()>0)
								{
									if(inputtype.equalsIgnoreCase("checkbox"))
									{
										//logger.info("will select checkbox");
										checkboxSelection(labelvalelem, bodyvalue);
										
										
									}
									else
									{
										if(inputtype.equalsIgnoreCase("radio"))
										{
											//logger.info("will select radio");
											radioSelection(labelvalelem, bodyvalue);
											
											
										}
										else
										{
										Thread.sleep(1000);
										Actions action=new Actions(driver);
										Action actionslist=action.
										moveToElement(labelvalelem).click().doubleClick(labelvalelem)
										.build();
										actionslist.perform();
										action.sendKeys(Keys.chord(Keys.CONTROL, "a")).build().perform();
										action.sendKeys(Keys.SPACE).perform();
										Thread.sleep(3000);
										for(int i=0;i<bodyvalue.length();i++)
										{
											char ch=bodyvalue.charAt(i);
											action.sendKeys(String.valueOf(ch));	
											action.build().perform();
											Thread.sleep(250);
										}
										action.sendKeys(Keys.TAB);
										action.build().perform();
										Thread.sleep(3000);
										}
									}
									break;
								
								}
								xllabels.set(xllabels.indexOf(xllabel), "");
								break;
							}
						}
						
						
					}
					
				}
				
				}
			}
		}
		
	}
	/* METHOD TO ENTER DATA TO BUDGET TAG BY PASSING LABEL NAMES AND ITS VALUES AS PARAMETES FROM EXCEL */
	public void budgetData(ArrayList<String> xllabels, ArrayList<String> xlvalues) throws InterruptedException
	{
		logger.info("xl labels are "+xllabels +" & values are "+xlvalues);
		Thread.sleep(3000);
		List<WebElement> grps=driver.findElements(By.xpath("//div[@id='dvBudget']/div"));
		//logger.info("grps size "+grps.size());
		for(int grp=1;grp<=grps.size();grp++)
		{
			String label;
			String value;
			for(String xllabel: xllabels)
			{
			List<WebElement> tabs=driver.findElements(By.xpath("//div[@id='dvBudget']/div["+grp+"]/div"));
			for(int tabno=1;tabno<=tabs.size();tabno++)
			{
				WebElement elemtype=driver.findElement(By.xpath("//div[@id='dvBudget']/div["+grp+"]/div["+tabno+"]"));
				List<WebElement> elemtypes=elemtype.findElements(By.xpath(".//*"));
				if(elemtypes.get(0).getTagName().equalsIgnoreCase("label"))
				{
					//logger.info("label "+elemtypes.get(0).getText());
					label=elemtypes.get(0).getText();
					if(!(elemtypes.get(0).getText()).equalsIgnoreCase("Budget"))
					{
						if(label.equalsIgnoreCase(xllabel))
						{
							//logger.info("label is "+label+ "& "+xlvalues.get(xllabels.indexOf(xllabel)));
							
							value=xlvalues.get(xllabels.indexOf(xllabel));
							if(value.equalsIgnoreCase("true"))
							{
								if(!(elemtypes.get(0).findElement(By.cssSelector("input")).isSelected()))
								elemtypes.get(0).findElement(By.cssSelector("input")).click();
							}
						
							break;
						}
					}
				}
				if(elemtypes.get(0).getTagName().equalsIgnoreCase("select"))
				{
					if(xllabel.equalsIgnoreCase("Budget planning"))
					{
						label=elemtypes.get(0).findElement(By.xpath("//select[contains(@data-fieldname, 'BudgetPlanning')]/preceding::label[contains(@id, 'BudgetPlanning')]")).getText();
					}
					else
					{
						label=elemtypes.get(0).getAttribute("data-fieldname");
					}
					if(label.equalsIgnoreCase(xllabel))
					{
						value=xlvalues.get(xllabels.indexOf(xllabel));
						WebElement myselectelem=elemtypes.get(0);
						WebElement abc=elemtypes.get(0).findElement(By.xpath("//select[contains(@data-fieldname, 'BudgetPlanning')]/preceding::label[contains(@id, 'BudgetPlanning')]"));
						//logger.info("abc "+abc.getText());
						myselectelem.click();
						Select dropdown=new Select(myselectelem);
						List<WebElement> list=dropdown.getOptions();
						for(int li=0;li<list.size();li++)
						{
							String optionname = list.get(li).getText();
							if(optionname.equalsIgnoreCase(value))
							{
								list.get(li).click();
								Thread.sleep(1000);
								break;
							}
						}
						break;
					}
					
				}
				if(elemtypes.get(0).getTagName().equalsIgnoreCase("div"))
				{
					ArrayList<String> elems=new ArrayList();
					elems.clear();
					for(int i=1;i<elemtypes.size();i++)
					{
						label=elemtypes.get(i).getText().trim();
						if(!(label.contains("\n")))
						{
							
							if(!(label.equalsIgnoreCase("")))
							{
								if(!(label.equalsIgnoreCase("When budget is exceeded")))
								{
									if(label.equalsIgnoreCase(xllabel))
									{
											value=xlvalues.get(xllabels.indexOf(label));
											if(value.equalsIgnoreCase("true"))
											{
												if(!(elemtypes.get(i).findElement(By.cssSelector("input")).isSelected()))
												{
													elemtypes.get(i).findElement(By.cssSelector("input")).click();
													Thread.sleep(3000);
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	/* METHOD TO ENTER DATA TO AR/AP TAG BY PASSING LABEL NAMES AND ITS VALUES AS PARAMETES FROM EXCEL */
	public void aRAPData(ArrayList<String> xllabels, ArrayList<String> xlvalues) throws InterruptedException
	{
		List<WebElement> grps=driver.findElements(By.xpath("//div[@id='dvARAP']/div"));
		String label, value;
		stringloop:
		for(String xllabel:xllabels)
		{
			grploop:
			for(int grp=1; grp<=grps.size();grp++)
			{
				List<WebElement> rows=driver.findElements(By.xpath("//div[@id='dvARAP']/div["+grp+"]/div"));
				rowloop:
				for(int row=1;row<=rows.size();row++)
				{
					WebElement labeltags=driver.findElement(By.xpath("//div[@id='dvARAP']/div["+grp+"]/div["+row+"]"));
					WebElement nextelem=labeltags.findElement(By.xpath(".//*"));
					List<WebElement> nextelems=labeltags.findElements(By.xpath(".//*"));
					nxtelemrow:
					for(int nxtelemno=1;nxtelemno<2;nxtelemno++)
					{
						List<WebElement> labelslist= labeltags.findElements(By.cssSelector("label"));
						labnorow:
						for(int labno=0;labno<labelslist.size();labno++)
						{
							label=labelslist.get(labno).getText().trim();
							if(label.length()>0)
							{
								
								if(label.contains("tag".toUpperCase())&&xllabel.equalsIgnoreCase("tag"))
								{
									xllabel=label;
								}
								if(xllabel.equalsIgnoreCase(label))
								{
									value = xlvalues.get(xllabels.indexOf(xllabel));
									if(label.equalsIgnoreCase("tag")||label.equalsIgnoreCase("warn and allow")||label.equalsIgnoreCase("stop")||label.equalsIgnoreCase("Product"))
									{
										if(value.length()>0)
										{
											int taggrp=1;
											String id;
											ArrayList<String> valueelems=new ArrayList(Arrays.asList(value.split(",")));
											if(valueelems.get(0).trim().equalsIgnoreCase("Maintain AR by"))
											{	
												taggrp=1;
												id="optARTag";
											}
											else
											{
												taggrp=2;
												id="optAPTag";
											}
												
												labeltags=driver.findElement(By.xpath("//div[@id='dvARAP']/div["+taggrp+"]/div["+row+"]"));
												if(valueelems.get(1).trim().equalsIgnoreCase("true"))
												{
													labelslist= labeltags.findElements(By.cssSelector("label"));
													try
													{
														labelslist.get(labno).findElement(By.cssSelector("input"));
													}
													catch(Exception e)
													{
														
														if(labno>labelslist.size())
															row+=row;
														break;
													}
													if(!(labelslist.get(labno).findElement(By.cssSelector("input")).isSelected()))
													{
														labelslist.get(labno).findElement(By.cssSelector("input")).click();
														Thread.sleep(3000);
													}
													if(label.equalsIgnoreCase("tag"))
													{
													Thread.sleep(1000);
													WebElement taginputelem=labeltags.findElement(By.cssSelector("input[id="+id+"]"));
													taginputelem.click();
													Thread.sleep(1000);
													Actions action=new Actions(driver);
													Action actionslist=action.
													moveToElement(taginputelem).click().doubleClick(taginputelem)
													.build();
													actionslist.perform();
													action.sendKeys(Keys.chord(Keys.CONTROL, "a")).build().perform();
													action.sendKeys(Keys.SPACE).perform();
													Thread.sleep(3000);
													for(int c=0;c<valueelems.get(2).trim().length();c++)
													{
														char ch= valueelems.get(2).trim().charAt(c);
														action.sendKeys(String.valueOf(ch)).build().perform();
														Thread.sleep(150);
													}
													action.sendKeys(Keys.TAB).build().perform();
													}
												}
												else
												{
													labelslist= labeltags.findElements(By.cssSelector("label"));
													//logger.info(labelslist.size());
													
													try
													{
														labelslist.get(labno).findElement(By.cssSelector("input"));
													}
													catch(Exception e)
													{
														
														if(labno>labelslist.size())
															row+=row;
														break;
													}
													if(labelslist.get(labno).findElement(By.cssSelector("input")).isSelected())
													{
														labelslist.get(labno).findElement(By.cssSelector("input")).click();
														Thread.sleep(3000);
													}
												}
												
											}
									}
									
									else
									{
										
										if(value.equalsIgnoreCase("true")) 
										{
											labeltags=driver.findElement(By.xpath("//div[@id='dvARAP']/div["+grp+"]/div["+row+"]"));
											labelslist= labeltags.findElements(By.cssSelector("label"));
											if(!(labelslist.get(labno).findElement(By.cssSelector("input")).isSelected()))
											{
												labelslist.get(labno).click();
												Thread.sleep(3000);
											}
										}
									}
										
									xllabels.set(xllabels.indexOf(xllabel), "");
									break grploop;
								}
								
									
								}
							}
						}
					}
				}
			}
		}
	/* METHOD TO ENTER DATA TO MISCELLANEOUS TAG BY PASSING LABEL NAMES AND ITS VALUES AS PARAMETES FROM EXCEL */
	public void miscellaneousData(ArrayList<String> xllabels, ArrayList<String> xlvalues) throws InterruptedException
	{
		List<WebElement> grps=driver.findElements(By.xpath("//div[@id='dvMisc']/div"));
		for(String xllabel: xllabels)
		{
			for(int grp=1;grp<=grps.size();grp++)
			{
				List<WebElement> totdivs=driver.findElements(By.xpath("//div[@id='dvMisc']/div["+grp+"]/div"));
				for(int rowno=1;rowno<=totdivs.size();rowno++)
				{
					WebElement divrow= driver.findElement(By.xpath("//div[@id='dvMisc']/div["+grp+"]/div["+rowno+"]"));
					WebElement labelelem=divrow.findElement(By.cssSelector("label"));
					String label= labelelem.getText();
					String value=xlvalues.get(xllabels.indexOf(xllabel));
					if(xllabel.equalsIgnoreCase(label))
					{
					if(value.length()>0)
					{
						Actions action=new Actions(driver);
						if(divrow.getAttribute("class").equalsIgnoreCase("Fcheckbox"))
						{
							if(value.equalsIgnoreCase("true"))
							{
								if(!(labelelem.isSelected()))
								{
									if(!(labelelem.findElement(By.cssSelector("input")).isSelected()))
											{
										logger.info("true anmd is not seleceted "+labelelem.getTagName()+" & "+labelelem.getText());
										labelelem.click();
										Thread.sleep(3000);
										break;
											}
								}
							}
							else
							{
								if(labelelem.isSelected())
								{
									labelelem.click();
									Thread.sleep(3000);
									break;
								}
							}
							
						}
						
						else
						{
							List<WebElement> li=divrow.findElements(By.xpath(".//*"));
							WebElement lname= divrow.findElement(By.cssSelector("input:last-of-type, select"));
							logger.info("tag is "+lname.getTagName());
							lname.click();
							if(lname.getTagName().equalsIgnoreCase("select"))
							{
								Select selectelem= new Select(lname);
								List<WebElement> list=selectelem.getOptions();
								for(int lis=0;lis<list.size();lis++)
								{
									String optionname = list.get(lis).getText();
									if(optionname.equalsIgnoreCase(value))
									{
										list.get(lis).click();
										Thread.sleep(1000);
										break;
									}
								}
								
							}
							
							Action actionslist=action.
									moveToElement(lname).click().doubleClick(lname)
									.build();
									actionslist.perform();
									action.sendKeys(Keys.chord(Keys.CONTROL, "a")).build().perform();
									action.sendKeys(Keys.SPACE).perform();
									Thread.sleep(3000);
									for(int i=0;i<value.length();i++)
									{
										char ch=value.charAt(i);
										action.sendKeys(String.valueOf(ch));	
										action.build().perform();
										Thread.sleep(250);
									}
									action.sendKeys(Keys.TAB);
									action.build().perform();
							Thread.sleep(3000);
							break;
							
						}
						
					}
				}
				}
			}
		}
	}
	/* METHOD TO ENTER DATA TO PDC TAG BY PASSING LABEL NAMES AND ITS VALUES AS PARAMETES FROM EXCEL */
	public void pdcData(ArrayList<String> xllabels, ArrayList<String> xlvalues) throws InterruptedException
	{
		WebElement mainele=driver.findElement(By.xpath("//div[@id='dvPDC']/div"));
		for(String xllabel: xllabels)
		{
			List<WebElement> elemslist=driver.findElements(By.xpath("//div[@id='dvPDC']/div"));
			for(int li=1;li<=elemslist.size();li++)
			{
				WebElement ele= driver.findElement(By.xpath("//div[@id='dvPDC']/div["+li+"]"));
				List<WebElement> totlabels=ele.findElements(By.cssSelector("label"));
				List<WebElement> totinputs =ele.findElements(By.cssSelector("input:not([type='hidden'])"));
				int nextelem=li;
				innerforloop:
				for(int i=0;i<totlabels.size();i++)
				{
					String label=totlabels.get(i).getText();
					if(xllabel.equalsIgnoreCase(label))
					{
						logger.info(totlabels.get(i).getText()+ " & " +totinputs.get(i).getAttribute("type"));
						String value=xlvalues.get(xllabels.indexOf(xllabel));
						if(value.length()>0)
						{
							if(totinputs.get(i).getAttribute("type").equalsIgnoreCase("checkbox"))
							{
								if(value.equalsIgnoreCase("true"))
								{
									if(!(totinputs.get(i).isSelected()))
									{
										totinputs.get(i).click();
										Thread.sleep(3000);
									}
								}
								else
								{
									if(totinputs.get(i).isSelected())
									{
										totinputs.get(i).click();
										Thread.sleep(3000);
									}
								}
							}
							else
							{
								Actions action=new Actions(driver);
								Action actionslist=action.
										moveToElement(totinputs.get(i)).click().doubleClick(totinputs.get(i))
										.build();
										actionslist.perform();
										action.sendKeys(Keys.chord(Keys.CONTROL, "a")).build().perform();
										action.sendKeys(Keys.SPACE).perform();
										Thread.sleep(3000);
										for(int len=0;len<value.length();len++)
										{
											char ch=value.charAt(len);
											action.sendKeys(String.valueOf(ch));	
											action.build().perform();
											Thread.sleep(250);
										}
										action.sendKeys(Keys.TAB);
										action.build().perform();
								Thread.sleep(3000);
							}
							 break;
						}
					}
					
				}
				
			}
		}
		
	}
	
	/* METHOD TO ENTER DATA TO INVENTORY TAG BY PASSING LABEL NAMES AND ITS VALUES AS PARAMETES FROM EXCEL */
	public void inventoryData() throws InterruptedException
	{
		List<WebElement> totgrps=driver.findElements(By.xpath("//div[@id='dvInventory']/div"));
		//logger.info(totgrps.size());
		for(int grp=1;grp<=totgrps.size();grp++)
		{
			List<WebElement> totdivs=driver.findElements(By.xpath("//div[@id='dvInventory']/div["+grp+"]/div"));
			logger.info(totdivs.size());
			for(int labs=0;labs<totdivs.size();labs++)
			{
				List<WebElement> nxtelem=totdivs.get(labs).findElements(By.xpath(".//*"));
				for(int elm=0;elm<nxtelem.size();elm++)
				{
					logger.info(nxtelem.get(elm).getTagName());
					if(nxtelem.get(elm).getTagName().equalsIgnoreCase("label"))
					{
				
				List<WebElement> totlabs=totdivs.get(labs).findElements(By.cssSelector("label"));
				List<WebElement> totinputs=totdivs.get(labs).findElements(By.cssSelector("input, select"));
				logger.info(totlabs.size()+ "& "+totinputs.size());
				if(totinputs.size()>0)
				{
				for(int lab=0;lab<totlabs.size();lab++)
				{
					logger.info(totlabs.get(lab).getText()+ " or "+totlabs.get(lab).getAttribute("id")+" & "+ totlabs.get(lab).getTagName()+" text "+totlabs.get(lab).getText());
					if(!(totlabs.get(lab).getText().length()==0))
					{
					if(!(totlabs.get(lab).getText().equalsIgnoreCase("Reservation")))
					{
						if(!(totinputs.get(lab).isSelected()))
						{
							totinputs.get(lab).click();
						}
						
					}
					Thread.sleep(3000);
				}
				}
			}
				break;
				}
				}
			}
			
		}
	}

}

