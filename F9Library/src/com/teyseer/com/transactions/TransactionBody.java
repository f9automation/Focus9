package com.teyseer.com.transactions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Reporter;

import com.focus.constants.HomePage;
import com.focus.constants.LaunchApplication;
import com.focus.constants.Menus;
import com.focus.library.MasterHomePage;

public class TransactionBody extends LaunchApplication 
{
	HomePage hp = new HomePage();
	Menus menu= new Menus();
	MasterHomePage mhp = new MasterHomePage();
	TransactionLink trl=new TransactionLink();
	public WebElement table;
	public ArrayList<String> bodylabels = new ArrayList<String>();
	public ArrayList<String> latestunmodifiedbodylabels = new ArrayList<String>();
	public ArrayList<String> unmodifiedbodylabels = new ArrayList<String>();
	public ArrayList<String> excelattribs = new ArrayList<String>();
	public ArrayList<String> unmodifiableexcelattribs= new ArrayList<String>();
	public Set<String> basecurrcalcattibs = new HashSet();
	public ArrayList<String> basecurrcalvalues=new ArrayList();
	int rownonew =1;
	public int rowno;
	public ArrayList<String> voucherno = new ArrayList<>();
	int voucherrowno = 1; 
	double slowkeys= 250;
	private WebElement unmodifiedlabels;
	List<String> unmodifiedlabellist;
  
/* METHOD TO GET ALL THE BODY LABEL NAMES */
  public void getBodyLabelNames()
  {
	  table=driver.findElement(By.xpath("//div[@id='id_transactionentry_body_section']/div[4]/div[1]/table[@id='id_transaction_entry_detail_table']"));
	  List<WebElement> theaderlist=table.findElements(By.xpath("//div[@id='id_transactionentry_body_section']/div[4]/div[1]/table[@id='id_transaction_entry_detail_table']/thead[@id='id_transaction_entry_detail_table_head']/tr[@id='id_transaction_entry_detail_table_row_heading']/th[not(contains(@style, 'display: none'))]"));
	  bodylabels.clear();
	  for(int i=1;i<theaderlist.size();i++)
	  {
		  String actvalue=theaderlist.get(i).getAttribute("title");
		  bodylabels.add(actvalue);
	  }  
	  unmodifiedbodylabels.clear();
	  unmodifiedbodylabels.addAll(bodylabels);
  }
  
/* METHOD TO ENTER DATA TO THE TRANSACTION BODY BY PASSING THE ROW NUMBER, LABEL NAMES, LABEL VALUES,   */
  public void transactionBody(ArrayList<String> vouchernos,ArrayList<String> arrattribs,ArrayList<String> unmodifiedarrattribs, List<String> arrvalues, int voucherrownof, int xlrowno) throws InterruptedException, IOException
  {
	  	String attr = null;
	  	List<String> value=new ArrayList();
	  	value=arrvalues;
	  	unmodifiableexcelattribs=unmodifiedarrattribs;
	  	ArrayList<String> excelattribs=new ArrayList();
	  	excelattribs=arrattribs;
	  	voucherno=vouchernos;
	  	int voucherrownonew = 1;
	  	WebElement firstcol;
	  	rowno=xlrowno;
	  	/* GET THE COLUMN NUMBER OF PRODUCT FIELD */
	  	int prodcolno = 2;
	  	List<WebElement> tbodyheaderlist=table.findElements(By.xpath("//div[@id='id_transactionentry_body_section']/div[4]/div[1]/table[@id='id_transaction_entry_detail_table']/thead[@id='id_transaction_entry_detail_table_head']/tr[@id='id_transaction_entry_detail_table_row_heading']/th[not(contains(@style, 'display: none'))]"));
	  	
	  	try
	  	{
	  		for(int header=1;header<=tbodyheaderlist.size();header++)
	  		{
		  		WebElement headernames=table.findElement(By.xpath("//div[@id='id_transactionentry_body_section']/div[4]/div[1]/table[@id='id_transaction_entry_detail_table']/thead[@id='id_transaction_entry_detail_table_head']/tr[@id='id_transaction_entry_detail_table_row_heading']/th["+header+"]"));
		  		String headername=headernames.getText();
		  		if(headername.equalsIgnoreCase("Product"))
		  		{
		  			prodcolno=header;
		  			break;
		  		}
		  	}
	  	}
	  	catch(Exception ex)
	  	{
	  		prodcolno=2;
	  	}
	  	
	  	/* COMPARE EACH LABEL NAME OF EXCEL WITH THE NAME WHICH IS CAPTURED FROM APPLICATION */
	  	excelattribs.addAll(unmodifiedbodylabels);
	  	Collections.reverse(excelattribs);
		excelattribs.remove(excelattribs.size()-1);
		List<WebElement> tbody= table.findElements(By.cssSelector("div[id='id_transactionentry_body_section'] div:nth-of-type(4) div:nth-of-type(1) table[id='id_transaction_entry_detail_table'] tbody[id='id_transaction_entry_detail_table_body'] tr[class='fgridrow']"));
		try
		{
			if(rowno>rownonew)
			{
				rowno=rownonew;
				firstcol=table.findElement(By.cssSelector("div[id='id_transactionentry_body_section'] div:nth-of-type(4) div:nth-of-type(1) table[id='id_transaction_entry_detail_table'] tbody[id='id_transaction_entry_detail_table_body'] tr[class='fgridrow']:nth-of-type("+rowno+") td:nth-of-type("+prodcolno+")"));
			}
			else
			{
				
				firstcol=table.findElement(By.cssSelector("div[id='id_transactionentry_body_section'] div:nth-of-type(4) div:nth-of-type(1) table[id='id_transaction_entry_detail_table'] tbody[id='id_transaction_entry_detail_table_body'] tr[class='fgridrow']:nth-of-type("+rowno+") td:nth-of-type("+prodcolno+")"));
			}
			
		}
		catch(Exception firstcolexe)
		{
			shortwait.until(ExpectedConditions.visibilityOf(table.findElement(By.cssSelector("div[id='id_transactionentry_body_section'] div:nth-of-type(4) div:nth-of-type(1) table[id='id_transaction_entry_detail_table'] tbody[id='id_transaction_entry_detail_table_body'] tr:nth-of-type("+voucherrownonew+") td:nth-of-type(1), div[id='id_transactionentry_body_section'] div:nth-of-type(4) divnth-of-type(1) table[id='id_transaction_entry_detail_table'] tbody[id='id_transaction_entry_detail_table_body'] tr:nth-of-type("+voucherrowno+") td:nth-of-type(1)"))));
			firstcol=table.findElement(By.cssSelector("div[id='id_transactionentry_body_section'] div:nth-of-type(4) div:nth-of-type(1) table[id='id_transaction_entry_detail_table'] tbody[id='id_transaction_entry_detail_table_body'] tr:nth-of-type("+voucherrownonew+") td:nth-of-type(1), div[id='id_transactionentry_body_section'] div:nth-of-type(4) divnth-of-type(1) table[id='id_transaction_entry_detail_table'] tbody[id='id_transaction_entry_detail_table_body'] tr:nth-of-type("+voucherrowno+") td:nth-of-type(1)"));
			voucherrowno=voucherrownof;
		}
		attr=firstcol.getAttribute("data-scode");
		whileloop:
		while(!(attr==null))
		{
			voucherrownonew=voucherrowno+1;
			logger.info("Donot enter any body values as the product column has value "+attr+" of row no "+rowno);
			Actions act=new Actions(driver);
			voucherlist:
			for (String voucherattribute : unmodifiedbodylabels) 
			{
				excellist:
				//use unmodifiable excelattribs list in case of to click only excelattributes
				for (String excelattribute : excelattribs) 
		        {
					ifloop:
					if(voucherattribute.equalsIgnoreCase(excelattribute))
		        	{
						int excelindex=unmodifiableexcelattribs.indexOf(excelattribute);
		        		int colno=unmodifiedbodylabels.indexOf(excelattribute)+2;
		        		try
		        		{
		        			wait.until(ExpectedConditions.visibilityOf(table.findElement(By.xpath("//div[@id='id_transactionentry_body_section']/div[4]/div[1]/table[@id='id_transaction_entry_detail_table']/tbody[@id='id_transaction_entry_detail_table_body']/tr[@class='fgridrow']["+rowno+"]/td[not(contains(@style, 'display: none'))]["+colno+"]"))));
		        			WebElement gridcol=table.findElement(By.xpath("//div[@id='id_transactionentry_body_section']/div[4]/div[1]/table[@id='id_transaction_entry_detail_table']/tbody[@id='id_transaction_entry_detail_table_body']/tr[@class='fgridrow']["+rowno+"]/td[not(contains(@style, 'display: none'))]["+colno+"]"));
		        			
	    					act.moveToElement(gridcol).click().perform();
	    					Thread.sleep(1000);
		        			try
		        			{
		        				int actvalue;
		        			
		        				try
		        				{
		        					actvalue=value.get(excelindex).length();
		        				}
		        				catch(Exception e)
		        				{
		        					actvalue=0;
		        				}
		        				actvalue=0;
		        				if(colno==2)
	        					{
	        						attr=gridcol.getAttribute("data-scode");
	        					}
	        					if(!(attr==null))
        						{
        							actvalue=0;
        						}
	        					if((actvalue==0))
	        					{
	        						if(voucherattribute.equalsIgnoreCase("Batch"))
	        						{
        								Thread.sleep(1000);
	        							if(driver.findElement(By.xpath("//div[@id='MBatch_Content']/div")).isDisplayed())
	        							{
	        								List<WebElement> headersec=driver.findElements(By.xpath("//div[@id='MBatch_Content']/div[@class='modal-body']/div[1]/div[1]/div[1]/div"));
	        								String adjqty=driver.findElement(By.xpath("//div[@id='MBatch_Content']/div[@class='modal-body']/div[1]/div[1]/div[1]/div[6]/label")).getText();
	        								String balanceqty=driver.findElement(By.xpath("//div[@id='MBatch_Content']/div[@class='modal-body']/div[1]/div[1]/div[1]/div[8]/label")).getText();
	        								if(Double.valueOf(balanceqty)!=0.00)
											{
	        									batchpoupPick();
												break;
											}
											else
											{
												//System.out.println("Batch OK getting executed");
												batchpopupOk();
												break;
											}
	        							}
	        							
	        							}
		        						if(voucherattribute.startsWith("L-"))
			        					{
		        							if(gridcol.getAttribute("data-voucherno")==null)
		        							{
		        								List<WebElement> wrkflowtables=driver.findElements(By.cssSelector("div[class='panel-body '] div[id='id_transactionentry_container_body_others'] div, div[class='panel-body '] div[id='id_transactionentry_container_body_others']"));
			        							for(int a=1;a<wrkflowtables.size();a++)
			        							{
			        								String workflowtable=wrkflowtables.get(a).getAttribute("id");
				        						}
			        							if(wrkflowtables.size()>1)
			        							{
			        								String workflowtable=wrkflowtables.get(5).getAttribute("id");
			        								if(workflowtable.equalsIgnoreCase("id_transactionentry_workflow_popup_modalcontent"))
				        							{
				        								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id_transactionentry_workflow_popup_close")));
						        						driver.findElement(By.id("id_transactionentry_workflow_popup_close")).click();
				        							}
				        							else
				        							{
				        								break;
				        							}
			        							
			        							}
			        						}
		        							
			        					}
		        							
		        						break;
		        					}
		        					else if(!(actvalue==0))
		        					{
		        						Thread.sleep(1000);
		        						act.sendKeys(Keys.chord(Keys.CONTROL, "a")).build().perform();
		        						act.sendKeys(Keys.SPACE).perform();
		        						Thread.sleep(3000);
		        						for (int i=0; i<value.get(excelindex).length(); i++)
		        						{	
		        							char ch=value.get(excelindex).charAt(i);
		        							act.sendKeys(String.valueOf(ch));
		        							act.build().perform();
		        							Thread.sleep((long)slowkeys);
		        						}
		        						Thread.sleep(2000);
		        					}
		        				}
		        				catch(Exception e)
		        				{
		        					
		        				}
		        				Thread.sleep(1000);
		        		}
		        			catch(Exception ex)
			        		{
			        				break;
			        		}
		        		act.sendKeys(Keys.TAB).build().perform();
		    			List<WebElement> li= driver.findElements(By.xpath("//section[@id='mainDiv']/div[@id='divMasterPreview']/div"));
		    			logger.info("size "+li.size());
		    			logger.info("id "+li.get(0).getAttribute("class"));
		    			if(li.get(0).getAttribute("class").equals(""))
		    			{
		    				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/section/div[5]/div[1]/div/div/div/div/div[1]/div/div[2]/div[2]/div[2]/div/ul/li[2]/button[5]")));
		    				logger.info("elemet is located");
		    				driver.findElement(By.xpath("/html/body/section/div[5]/div[1]/div/div/div/div/div[1]/div/div[2]/div[2]/div[2]/div/ul/li[2]/button[5]")).click();
		    				logger.info("clicked on close");
		    			}
		        		break;	
		        	}
		        }
			}
			voucherrowno++;
			int nxtrw=rowno+1;
			WebElement nxtcol=table.findElement(By.cssSelector("div[id='id_transactionentry_body_section'] div:nth-of-type(4) div:nth-of-type(1) table[id='id_transaction_entry_detail_table'] tbody[id='id_transaction_entry_detail_table_body'] tr[class='fgridrow']:nth-of-type("+nxtrw+") td:nth-of-type("+prodcolno+")"));
			attr=nxtcol.getAttribute("data-scode");
			rowno++;
			rownonew=rowno;
			
		}
		tbody= table.findElements(By.cssSelector("div[id='id_transactionentry_body_section'] div:nth-of-type(4) div:nth-of-type(1) table[id='id_transaction_entry_detail_table'] tbody[id='id_transaction_entry_detail_table_body'] tr[class='fgridrow']"));
		/*If the row has no product data entered*/
		if(attr==null)
		{
			Actions act2=new Actions(driver);
			logger.info("Entering voucher's Body data to row "+rowno+" whose values are "+value);
			List<WebElement> listyle=table.findElements(By.cssSelector("div[id='id_transactionentry_body_section'] div:nth-of-type(4) div:nth-of-type(1) table[id='id_transaction_entry_detail_table'] tbody[id='id_transaction_entry_detail_table_body'] tr[class='fgridrow']:nth-of-type("+rowno+") td:not([style*='display: none'])"));
			if(!(value.size()==0))
			{
				voucherlist:
				for (String voucherattribute : unmodifiedbodylabels) 
				{
					excellist:
					for (String excelattribute : excelattribs) 
			        {
						ifloop:
						if(voucherattribute.equalsIgnoreCase(excelattribute))
			        	{
							int excelindex=unmodifiableexcelattribs.indexOf(excelattribute);
			        		int colno=unmodifiedbodylabels.indexOf(excelattribute)+2;
			        		try
			        		{
			        			wait.until(ExpectedConditions.visibilityOf(table.findElement(By.xpath("//div[@id='id_transactionentry_body_section']/div[4]/div[1]/table[@id='id_transaction_entry_detail_table']/tbody[@id='id_transaction_entry_detail_table_body']/tr[@class='fgridrow']["+rowno+"]/td[not(contains(@style, 'display: none'))]["+colno+"]"))));
			        			WebElement gridcol=table.findElement(By.xpath("//div[@id='id_transactionentry_body_section']/div[4]/div[1]/table[@id='id_transaction_entry_detail_table']/tbody[@id='id_transaction_entry_detail_table_body']/tr[@class='fgridrow']["+rowno+"]/td[not(contains(@style, 'display: none'))]["+colno+"]"));
			        			Thread.sleep(1000);
			        			try
			        			{
			        				int actvalue = 0;
			        				
			        				if(excelindex==-1)
			        				{
			        					act2.moveToElement(gridcol).click().perform();
			        					Thread.sleep(1000);
			        					actvalue = 0;
			        				}
			        				else
			        				{
			        					act2.moveToElement(gridcol).click().perform();
			        					Thread.sleep(1000);
			        					actvalue=value.get(excelindex).length();
			        				}
			        				if((!(actvalue==0))&&(voucherattribute.startsWith("L-")))
		        					{
		        						trl.transactionLinkLoad(value.get(excelindex));
		        					}
			        				if((actvalue==0)&&(voucherattribute.startsWith("L-")))
		        					{
		        						if(gridcol.getAttribute("data-voucherno")==null)
		        						{
		        							List<WebElement> wrkflowtables=driver.findElements(By.cssSelector("div[class='panel-body '] div[id='id_transactionentry_container_body_others'], div[class='panel-body '] div[id='id_transactionentry_container_body_others'] div"));
		        							for(int a=0;a<wrkflowtables.size();a++)
		        							{
		        								String workflowtable=wrkflowtables.get(a).getAttribute("id");
			        						}
		        							if(wrkflowtables.size()>1)
			        						{
			        							String workflowtable=wrkflowtables.get(5).getAttribute("id");
			        							if(workflowtable.equalsIgnoreCase("id_transactionentry_workflow_popup_modalcontent"))
			        							{
			        								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id_transactionentry_workflow_popup_close")));
					        						driver.findElement(By.id("id_transactionentry_workflow_popup_close")).click();
			        							}
			        							else
			        							{
			        								break;
			        							}
			        							
			        						}
		        						}
		        					}
			        						
			        				if((actvalue==0)&&(voucherattribute.equalsIgnoreCase("Batch")))
		        					{
	        							Thread.sleep(1000);
	        							if(driver.findElement(By.xpath("//div[@id='MBatch_Content']/div")).isDisplayed())
	        							{
	        								List<WebElement> headersec=driver.findElements(By.xpath("//div[@id='MBatch_Content']/div[@class='modal-body']/div[1]/div[1]/div[1]/div"));
	        								String adjqty=driver.findElement(By.xpath("//div[@id='MBatch_Content']/div[@class='modal-body']/div[1]/div[1]/div[1]/div[6]/label")).getText();
	        								String balanceqty=driver.findElement(By.xpath("//div[@id='MBatch_Content']/div[@class='modal-body']/div[1]/div[1]/div[1]/div[8]/label")).getText();
	        								if(Double.valueOf(adjqty)<Double.valueOf(balanceqty))
											{
	        									batchpoupPick();
												break;
											}
											else
											{
												batchpopupOk();
												break;
											}
	        							}
	        							
	        						}
			        				if((!(actvalue==0))&&(voucherattribute.equalsIgnoreCase("Batch"))&&(value.get(excelindex).contains(",")))
		        					{
			        					Thread.sleep(1000);
		        						if(driver.findElement(By.xpath("//div[@id='MBatch_Content']/div")).isDisplayed())
	        							{
	        								String bvalue=value.get(excelindex);
			        						ArrayList<String> bvaluelist=new ArrayList<String>(Arrays.asList(bvalue.split(",")));
			        						batchpopup(bvaluelist);
			        						batchpopupOk();
			        						break;
	        							}
		        							
		        						break;
		        					}
			        				if(!(actvalue==0))
		        					{
		        						Thread.sleep(1000);
		        						act2.sendKeys(Keys.chord(Keys.CONTROL, "a")).build().perform();
		        						act2.sendKeys(Keys.SPACE).perform();
		        						Thread.sleep(3000);
		        						for (int i=0; i<value.get(excelindex).length(); i++)
		        						{	
		        							char ch=value.get(excelindex).charAt(i);
		        							act2.sendKeys(String.valueOf(ch));
		        							act2.build().perform();
		        							Thread.sleep((long)slowkeys);
		        						}
		        						Thread.sleep(1000);
		        					}
			        			}
			        			catch(Exception e)
		        				{
			        				if(voucherattribute.equalsIgnoreCase("Batch"))
			        				{
	        							Thread.sleep(1000);
	        							try
	        							{
	        								if(driver.findElement(By.xpath("//div[@id='MBatch_Content']/div")).isDisplayed())
	        								{
	        								
		        								batchpoupPick();
		        								break;
	        								}
	        							}
	        							catch(Exception e1)
	        							{
	        								
	        							}
	        							
	        						
			        				}
		        					if(voucherattribute.startsWith("L-"))
		        					{
		        						if(gridcol.getAttribute("data-voucherno")==null)
		        						{
		        							if(gridcol.findElement(By.tagName("table")) != null)
			        						{
			        							String testg=	gridcol.findElement(By.tagName("table")).getAttribute("id");
			        							if(testg=="id_transaction_entry_detail_workflow_popup")
				        						{
				        							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id_transactionentry_workflow_popup_close")));
					        						driver.findElement(By.id("id_transactionentry_workflow_popup_close")).click();
					        						break;
				        						}
			        							else
			        							{
			        								break;
			        							}
			        						}
		        						}
		        					}
		        				}
			        				Thread.sleep(1000);
			        				
			        		}
			        		
			        		catch(Exception ex)
			        		{
			        				break;
			        		}
			        		act2.sendKeys(Keys.TAB).build().perform();
			        		Thread.sleep(3000);
			        		List<WebElement> li= driver.findElements(By.xpath("//section[@id='mainDiv']/div[@id='divMasterPreview']/div"));
							if(li.get(0).getAttribute("class").equals(""))
							{
								wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/section/div[5]/div[1]/div/div/div/div/div[1]/div/div[2]/div[3]/div[2]/div/ul/li[2]/button[5]")));
								//logger.info("elemet is located");
								driver.findElement(By.xpath("/html/body/section/div[5]/div[1]/div/div/div/div/div[1]/div/div[2]/div[3]/div[2]/div/ul/li[2]/button[5]")).click();
								//logger.info("clicked on close");
							}
			        		break;	
			        	}
			        }
				}
		rowno++;
				rownonew=rowno;
				voucherrowno++;
				
			}
		}
	}
 /* METHOD TO GET ALL THE BASE CURRENCY RELATED VALUES LIKE AMOUNT, EXCHANGE RATE OF BODY GRID */ 
  public void getBaseCurrencyCalcValues()
	{
	  
	  	basecurrcalvalues.clear();
	  	getBodyLabelNames();
	  	List<WebElement> tbodyheaderlist=table.findElements(By.xpath("//div[@id='id_transactionentry_body_section']/div[4]/div[1]/table[@id='id_transaction_entry_detail_table']/thead[@id='id_transaction_entry_detail_table_head']/tr[@id='id_transaction_entry_detail_table_row_heading']/th[not(contains(@style, 'display: none'))]"));
	  	List<WebElement> tbody= table.findElements(By.cssSelector("div[id='id_transactionentry_body_section'] div:nth-of-type(4) div:nth-of-type(1) table[id='id_transaction_entry_detail_table'] tbody[id='id_transaction_entry_detail_table_body'] tr[class='fgridrow']"));
		ArrayList<String> basecurrcalattribs=new ArrayList();
		for(int row=1; row<tbody.size();row++)
		{
			ArrayList<String> compareattribs=new ArrayList(Arrays.asList("Amount", "ExchangeRate"));
			compareattribs.addAll(unmodifiedbodylabels);
		  	Collections.reverse(compareattribs);
		  	compareattribs.remove(compareattribs.size()-1);
		  	for (String voucherattribute : unmodifiedbodylabels) 
			{
				excellist:
				for (String compareattrib : compareattribs) 
		        {
					int colno=unmodifiedbodylabels.indexOf(compareattrib)+2;
					ifloop:
					if(voucherattribute.equalsIgnoreCase(compareattrib))
					{
						if(voucherattribute.equalsIgnoreCase("Amount")|| voucherattribute.equalsIgnoreCase("ExchangeRate"))
						{
							wait.until(ExpectedConditions.visibilityOf(table.findElement(By.xpath("//div[@id='id_transactionentry_body_section']/div[4]/div[1]/table[@id='id_transaction_entry_detail_table']/tbody[@id='id_transaction_entry_detail_table_body']/tr[@class='fgridrow']["+row+"]/td[not(contains(@style, 'display: none'))]["+colno+"]"))));
							WebElement gridcol=table.findElement(By.xpath("//div[@id='id_transactionentry_body_section']/div[4]/div[1]/table[@id='id_transaction_entry_detail_table']/tbody[@id='id_transaction_entry_detail_table_body']/tr[@class='fgridrow']["+row+"]/td[not(contains(@style, 'display: none'))]["+colno+"]"));
		        			String value=gridcol.getText();
		        			basecurrcalvalues.add(value);
		        			basecurrcalattribs.add(voucherattribute);
	        			
						}
						break;
					}
					
				}
			}
		  }
			basecurrcalcattibs.addAll(basecurrcalattribs);
	}
		
  /* TO CLICK ON PICK ICON OF BATCH POPUP*/
  public void batchpoupPick() throws InterruptedException
	{
	  	wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@id='MBatch_Content']/div[starts-with(@class,'modal-footer')]/div[@class='row']/div[2]/div[1]"))));
		driver.findElement(By.xpath("//div[@id='MBatch_Content']/div[starts-with(@class,'modal-footer')]/div[@class='row']/div[2]/div[1]")).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@id='MBatch_Content']/div[starts-with(@class,'modal-footer')]/div[@class='row']/div[2]/div[2]"))));
		driver.findElement(By.xpath("//div[@id='MBatch_Content']/div[starts-with(@class,'modal-footer')]/div[@class='row']/div[2]/div[2]")).click();
		Thread.sleep(1000);
	}
	   
  /* TO CLICK ON OK BUTTON OF BATCH POPUP*/
  public void batchpopupOk() throws InterruptedException
	{
		
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@id='MBatch_Content']/div[starts-with(@class,'modal-footer')]/div[@class='row']/div[2]/div[2]"))));
		driver.findElement(By.xpath("//div[@id='MBatch_Content']/div[starts-with(@class,'modal-footer')]/div[@class='row']/div[2]/div[2]")).click();
		Thread.sleep(1000);
		try
		{
			driver.switchTo().alert().accept();
		}
		catch(Exception e)
		{
			
		}
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id='MBatch_Content']/div")));
	}
  
  /* BATCHPOP UP GRID SELECTION AND DATA ENTRY*/
	public void batchpopup(ArrayList<String> batchvalues) throws InterruptedException
	{
		List<WebElement> batchpopupcount=driver.findElements(By.xpath("//div[@id='MBatch_Content']/div"));
		WebElement batchpopup=driver.findElement(By.xpath("//div[@id='MBatch_Content']/div"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='MBatch_Content']/div[starts-with(@class,'modal-header')]/div[1]")));
		WebElement batchpopupheader=batchpopup.findElement(By.xpath("//div[@id='MBatch_Content']/div[starts-with(@class,'modal-header')]/div[1]"));
		wait.until(ExpectedConditions.visibilityOf(batchpopup.findElement(By.xpath("//div[@id='MBatch_Content']/div[@class='modal-body']/div[@class='row']/div[@id='MBatch_Model_Body']/div[1]/table[@id='Id_MRPBatch_Grid']/thead[@id='Id_MRPBatch_Grid_head']/tr[@id='Id_MRPBatch_Grid_row_heading']/th"))));
		List<WebElement> batchpopupbodyths=batchpopup.findElements(By.xpath("//div[@id='MBatch_Content']/div[@class='modal-body']/div[@class='row']/div[@id='MBatch_Model_Body']/div[1]/table[@id='Id_MRPBatch_Grid']/thead[@id='Id_MRPBatch_Grid_head']/tr[@id='Id_MRPBatch_Grid_row_heading']/th"));
		int th=1;
		int qth = 0,bth = 0;
		for(int header=1;header<=batchpopupbodyths.size();header++)
		{
			WebElement batchpopupbodyth=batchpopup.findElement(By.xpath("//div[@id='MBatch_Content']/div[@class='modal-body']/div[@class='row']/div[@id='MBatch_Model_Body']/div[1]/table[@id='Id_MRPBatch_Grid']/thead[@id='Id_MRPBatch_Grid_head']/tr[@id='Id_MRPBatch_Grid_row_heading']/th["+header+"]"));
			if(batchpopupbodyth.getText().equalsIgnoreCase("Batch No"))
			{
				bth=th;
				
			}
			if(batchpopupbodyth.getText().equalsIgnoreCase("Qty Adjusted"))
			{
				qth=th;
				
			}
			th++;
		}
		int i=0,j=1,l=0;
		List<WebElement> batchpopupbodytrs=batchpopup.findElements(By.xpath("//div[@id='MBatch_Content']/div[@class='modal-body']/div[@class='row']/div[@id='MBatch_Model_Body']/div[1]/table[@id='Id_MRPBatch_Grid']/tbody[@id='Id_MRPBatch_Grid_body']/tr"));
		for(int k=0;k<batchvalues.size();k++)
		{
			forloop:
			for(int row=1;row<=batchpopupbodytrs.size();row++)
			{
				WebElement bthrow=batchpopup.findElement(By.xpath("//div[@id='MBatch_Content']/div[@class='modal-body']/div[@class='row']/div[@id='MBatch_Model_Body']/div[1]/table[@id='Id_MRPBatch_Grid']/tbody[@id='Id_MRPBatch_Grid_body']/tr["+row+"]/td["+bth+"]"));
				WebElement qthrow=batchpopup.findElement(By.xpath("//div[@id='MBatch_Content']/div[@class='modal-body']/div[@class='row']/div[@id='MBatch_Model_Body']/div[1]/table[@id='Id_MRPBatch_Grid']/tbody[@id='Id_MRPBatch_Grid_body']/tr["+row+"]/td["+qth+"]"));
				String value=batchvalues.get(j);
				String batchno=bthrow.getText();
				String qtyadjusted= qthrow.getText();
				Actions actb=new Actions(driver);
				
				ifloop:
				if(batchvalues.get(i).equalsIgnoreCase(batchno))
				{
					if(qtyadjusted.equalsIgnoreCase("0"))
					{
						actb.moveToElement(batchpopup.findElement(By.xpath("//div[@id='MBatch_Content']/div[@class='modal-body']/div[@class='row']/div[@id='MBatch_Model_Body']/div[1]/table[@id='Id_MRPBatch_Grid']/tbody[@id='Id_MRPBatch_Grid_body']/tr["+row+"]/td["+qth+"]"))).click().perform();
						for (int v=0; v<value.length(); v++)
						{	
							char ch=value.charAt(v);
							actb.sendKeys(String.valueOf(ch));
							actb.build().perform();
							Thread.sleep((long)slowkeys);
						}
						i++;i++;
						j++;j++;
						break;
					}
				}
				
			}
			k++;
		}
	}
}


	 
 
  

