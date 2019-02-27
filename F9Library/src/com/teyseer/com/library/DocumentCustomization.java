package com.teyseer.com.library;

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
import org.openqa.selenium.support.ui.WebDriverWait;
import com.focus.constants.HomePage;
import com.focus.constants.LaunchApplication;
import com.focus.constants.Menus;

public class DocumentCustomization extends LaunchApplication
{
	HomePage hp=new HomePage();
	long slowkeys=250;
	Menus mp=new Menus();
	ArrayList<String> bodyfieldnames= new ArrayList();
	/*
	@Test
public void	DocumentCustomization() throws InterruptedException
	{
		hp.LoginApp("su", "su", "A0407");
		boolean res;
		res=mp.menuSelection("Home", "Masters", "Product", "Unit Conversion", "Unit Conversion");
		logger.info("res "+res);
		voucherDocCustomization("Purchases Vouchers");
		voucherTabs("Edit Screen");
		editScreenTabs("Footer");
		getEditScreenBodyFieldnames();
		ArrayList<String> excelfieldnames=new ArrayList(Arrays.asList("Tool Tip Text","No Of Decimals","Preload", "Caption", "Restrict formula", "Minimum Value", "Default Value", "Banner text", "Round Offs", "Roundoff To", "Add to net","Add to Stock", "Position", "Value in base currency", "Column Width","Post to Account","Account", "Account-2", "Hide net from summary"));
		ArrayList<String> excelfieldvalies=new ArrayList(Arrays.asList("toolrip","2","abdfc", "Ffield1", "gr*qty", "2", "3.02", "banner", "Lowest", "2", "Deduct", "Deduct", "Between Quantity and Rate", "true", "180","true","vendor a", "bcd", "true"));
		addEditScreenBodyField(excelfieldnames, excelfieldvalies);
		saveBodyField();
		//driver.quit();
	
	}
	*/
	
/* METHOD TO CLICK ON THE DOCUMENT CUSTOMIZATION OF THE RESPECTIVE VOUCHER WHICH IS SENT FROM EXCEL */	
public void voucherDocCustomization(String vouchername) throws InterruptedException 
  {
	wait.until(ExpectedConditions.elementToBeClickable(By.id("ToggleDocCustomIcon")));
	driver.findElement(By.id("ToggleDocCustomIcon")).click();
	wait.until(ExpectedConditions.elementToBeClickable(By.id("doc_Searchbox")));
	WebElement searchbox=driver.findElement(By.id("doc_Searchbox"));
	searchbox.click();
	for(int i=0;i<vouchername.length();i++)
	{
		char ch=vouchername.charAt(i);
		searchbox.sendKeys(String.valueOf(ch));
		Thread.sleep((long)slowkeys);
	}
	
	List<WebElement> searchelems=driver.findElements(By.xpath("//div[@id='docCustomizationUL']/div/li"));
	int listno=0;
	for(int i=0;i<searchelems.size();i++)
	{
		if(vouchername.equalsIgnoreCase(searchelems.get(i).getText()))
		{
			listno=i+1;
			break;
		}
	}
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='docCustomizationUL']/div/li["+listno+"]/a")));
	driver.findElement(By.xpath("//div[@id='docCustomizationUL']/div/li["+listno+"]/a")).click();
	
	/*TO WAIT UNTILL THE SCROLL BAR DISAPPEARS*/
	wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
	
  }

/* METHOD TO CLICK ON THAT PARTICULAR TAB WHICH IS SENT FROM EXCEL AS A PARAMETER */
  public void voucherTabs(String tabname) throws InterruptedException
  {
	 List<WebElement> tabs=driver.findElements(By.cssSelector("ul[id='navigationTabs'] li"));
	 for(int i=0;i<tabs.size();i++)
	  {
		String voutabname=tabs.get(i).getText();
		if(tabs.get(i).getText().equalsIgnoreCase(""))
		 {
			 if(!(tabs.get(i).getAttribute("id").equalsIgnoreCase("navigationtab15")))
			 {
				
				 wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/section/div[2]/div/section[1]/div/div[2]/div[2]/div[2]/div/div/div[1]/div[2]/a/span")));
				 driver.findElement(By.xpath("/html/body/section/div[2]/div/section[1]/div/div[2]/div[2]/div[2]/div/div/div[1]/div[2]/a/span")).click();
				 List<WebElement> drpdwnelems=driver.findElements(By.xpath("//div[@id='tabsHideDiv']/ul/li"));
				 WebElement lis=driver.findElement(By.xpath("//div[@id='tabsHideDiv']/ul/li"));
				 for(int j=0;j<drpdwnelems.size();j++)
				 {
					int k=j+1;
					WebElement drpdwn=lis.findElement(By.xpath("//div[@id='tabsHideDiv']/ul/li["+k+"]/span[2]"));
					voutabname=drpdwn.getText();
					if(tabname.equalsIgnoreCase(voutabname))
					{	
						drpdwn.click();
						wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
						break;
					}
				 }
				 break;
			 }
			
		 }
		 else
		 {
			 if(tabname.equalsIgnoreCase(voutabname))
			 {
				 tabs.get(i).click();
				 wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
				 break;
			 }
		 }
	  }
	  
  }
  
  /* METHOD TO GET ALL THE LABEL NAMES OF THE EDIT SCREEN BODY FIELDS  */
  public void getEditScreenBodyFieldnames() throws InterruptedException
  {
	  wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
	  Thread.sleep(3000);
	  wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#editScreen_tabContent > div:nth-child(2) > span:nth-child(1)")));
	  driver.findElement(By.cssSelector("#editScreen_tabContent > div:nth-child(2) > span:nth-child(1)")).click();
	  wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
	  List<WebElement> rows=driver.findElements(By.xpath("//div[@id='editScreen_FieldsCustomization_FieldDetails']/div"));
	  //GO TO EACH ROW AND GET ALL THE LABEL NAMES AND STORE IT INTO AN ARRAYLIST
	  for(int row=1;row<rows.size();row++)
	  {

		  WebElement rowelem=driver.findElement(By.cssSelector("div[id='editScreen_FieldsCustomization_FieldDetails'] div:nth-of-type("+row+") label"));
		  String labelname=rowelem.getText();
		  Thread.sleep(1000);
		  WebElement label=driver.findElement(By.cssSelector("div[id='editScreen_FieldsCustomization_FieldDetails'] div:nth-of-type("+row+") div input, div[id='editScreen_FieldsCustomization_FieldDetails'] div:nth-of-type("+row+") div select, div[id='editScreen_FieldsCustomization_FieldDetails'] div:nth-of-type("+row+") div select, div[id='editScreen_FieldsCustomization_FieldDetails'] div:nth-of-type("+row+") div input"));
		  String labeltype=label.getAttribute("type");
		  String labelclass=label.getAttribute("class");
		  String labelplaceholder=label.getAttribute("placeholder");
		  bodyfieldnames.add(labelname);
	  }
	  logger.info("Body field names"+bodyfieldnames);
  }
  /* METHOD TO GET THE TAB NAMES OF THE EDIT SCREEN TAB AND CLICK ON THE RESPECTIVE HEADER/BODY TAB WHICH IS SENT FROM EXCEL */
  public void editScreenTabs(String tabname)
  {
	 if(tabname.equalsIgnoreCase("Body"))
	 {
		  wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Body")));
		  driver.findElement(By.linkText("Body")).click();
		  wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
		  
	 }
	 else
	 {
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Footer")));
		 driver.findElement(By.linkText("Footer")).click();
		 wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
	 }
  }
  /* METHOD TO ENTER DATA TO EDIT SCREEN BODY FIELD BY SENDING LABEL NAMES AND ITS RESPECTIVE VALUES AS PARAMETERS */
  public void  addEditScreenBodyField(ArrayList excelfieldnames, ArrayList excelfieldvalues) throws InterruptedException
  {
	  List<WebElement> rows=driver.findElements(By.xpath("//div[@id='editScreen_FieldsCustomization_FieldDetails']/div"));
	  ArrayList<String> excelattribs=new ArrayList();
	  excelattribs.clear();
	  excelattribs.addAll(excelfieldnames);
	  ArrayList<String> unmodifiedexcelattribs=new ArrayList();
	  unmodifiedexcelattribs.clear();
	  unmodifiedexcelattribs.addAll(excelfieldnames);
	  ArrayList<String> excelvalues=new ArrayList();
	  excelvalues.clear();
	  excelvalues.addAll(excelfieldvalues);
	  WebElement label;
	  int chckbboxrow=1;
	  /* GO TO EACH ROW AND GET THE LABEL NAMES AND COMPARE IT WITH THE EXCEL LABEL NAMES */
	  rowloop:
		  for(int row=1;row<rows.size();row++)
		  {
			  logger.info("srat row "+row);
			  WebElement rowelem=driver.findElement(By.cssSelector("div[id='editScreen_FieldsCustomization_FieldDetails'] div:nth-of-type("+row+") label"));
			  List<WebElement> li1 =driver.findElements(By.cssSelector("div[id='editScreen_FieldsCustomization_FieldDetails'] div:nth-of-type("+row+") div"));
				logger.info("li1 size "+li1.size());
				for(int l=0;l<li1.size();l++)
				{
					logger.info(li1.get(l).getAttribute("class"));
					
				}
				
			  String labelname=rowelem.getText();
			  excelattribs.addAll(bodyfieldnames);
			  Collections.reverse(excelattribs);
			  bodyloop: 
				  for (String voucherattribute : bodyfieldnames) 
				  {
					  excelattribs.remove(excelattribs.size()-1);
					  excellist: 
						  for(String excelattribute : excelattribs) 
					       {
							  /* IF THE LABEL NAME WHICH IS SENT FROM EXCEL EQUALS TO THE ONE WHICH IS FROM THE APPLICATION */
							  
								if(labelname.equalsIgnoreCase(excelattribute))
								{
									if(unmodifiedexcelattribs.indexOf(labelname)>=0)
									{
										WebElement bodyelem;
										
										
										String value=excelvalues.get(unmodifiedexcelattribs.indexOf(labelname));
										
										bodyelem=driver.findElement(By.cssSelector("div[id='editScreen_FieldsCustomization_FieldDetails'] div:nth-of-type("+row+") div"));
										
										bodyelem.click();
										Thread.sleep(1000);
										Actions act=new Actions(driver);
										/* CODE IF THERE ARE MULTIPLE CHECK BOXES IN A ROW */
										if((li1.get(0).getAttribute("class").startsWith("Fcheckbox"))&&(li1.get(0).getAttribute("class").startsWith("Fcheckbox")))
										{
											chckbboxrow++;
											logger.info("cuurent chckbod rw "+chckbboxrow);
											if(chckbboxrow==2)
											{
												row--;
												logger.info("current row "+row);
												//need to be changed
												label=driver.findElement(By.cssSelector("div[id='editScreen_FieldsCustomization_FieldDetails'] div:nth-of-type("+row+") div input, div[id='editScreen_FieldsCustomization_FieldDetails'] div:nth-of-type("+row+") div select, div[id='editScreen_FieldsCustomization_FieldDetails'] div:nth-of-type("+row+") div select, div[id='editScreen_FieldsCustomization_FieldDetails'] div:nth-of-type("+row+") div input"));
												
											}
										}
										
										label=driver.findElement(By.cssSelector("div[id='editScreen_FieldsCustomization_FieldDetails'] div:nth-of-type("+row+") div input, div[id='editScreen_FieldsCustomization_FieldDetails'] div:nth-of-type("+row+") div select, div[id='editScreen_FieldsCustomization_FieldDetails'] div:nth-of-type("+row+") div select, div[id='editScreen_FieldsCustomization_FieldDetails'] div:nth-of-type("+row+") div input"));
										String labeltype=label.getAttribute("type");
										String labelclass=label.getAttribute("class");
										String labelplaceholder=label.getAttribute("placeholder");
										logger.info("Field name "+labelname+" , type "+labeltype+" , class "+labelclass+" , placeholder "+labelplaceholder);
										try
										{
											//IF IT IS A FORMULA CONTROL
											if(labelplaceholder.equalsIgnoreCase("Formulacontrol"))
											{
												List<WebElement> formuladialoglist= driver.findElements(By.cssSelector("div[class='pull-right'] button"));
												WebElement formulacontrol;
												if(labelname.equalsIgnoreCase("PreLoad"))
												{
													act.moveToElement(bodyelem).perform();
													act.sendKeys(value);
													act.build().perform();
													wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='pull-right']/button[@id='"+formuladialoglist.get(15).getAttribute("id")+"']")));
													formulacontrol=driver.findElement(By.xpath("//div[@class='pull-right']/button[@id='"+formuladialoglist.get(15).getAttribute("id")+"']"));
													formulacontrol.click();
													Thread.sleep(1000);
												}
												else
												{
													act.moveToElement(bodyelem).perform();
													act.sendKeys(value);
													act.build().perform();
													wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='pull-right']/button[@id='"+formuladialoglist.get(17).getAttribute("id")+"']")));
													formulacontrol=driver.findElement(By.xpath("//div[@class='pull-right']/button[@id='"+formuladialoglist.get(17).getAttribute("id")+"']"));
													formulacontrol.click();
													Thread.sleep(1000);
												}
											}
											
											else
											{
												//IF IT IS A POST TO ACCOUNT FIELD
												if(labelname.equalsIgnoreCase("Post to Account"))
												{
													WebElement ele=driver.findElement(By.id("editScreen_FieldsCustomization_chkPostToAcc"));
													if(value.equalsIgnoreCase("true"))
													{
														if(!(ele.isSelected()))
														{
															ele.click();
														}
													}
												}
												//IF IT IS A TEXT FIELD
												if(((labeltype.equalsIgnoreCase("text"))&&((labelclass.equalsIgnoreCase("Ftxtbox-M"))||(labelclass.equalsIgnoreCase("Ftxtbox"))||(labelclass.equalsIgnoreCase("Ftxtbox text-right")))))
												{
													
													act.moveToElement(bodyelem).perform();
													act.doubleClick();
													act.sendKeys(value);
													act.build().perform();
													
												}
												// IF IT IS A NUMBER FIELD
												if(labeltype.equalsIgnoreCase("number"))
												{
													act.moveToElement(bodyelem).perform();
													act.doubleClick();
													act.sendKeys(value);
													act.build().perform();
												}
												//IF IT IS A SELECT FIELD 
												if(labeltype.equalsIgnoreCase("select-one"))
												{
													logger.info("inside select method of else loopp");
													Select dropdown= new Select(bodyelem);
													List<WebElement> list = dropdown.getOptions();
													logger.info("list elems size "+list.size());
													for(int selectelem=0; selectelem<list.size();selectelem++)
													{
														String listelements = list.get(selectelem).getText();
														logger.info("list elemes "+listelements);
									        			if(listelements.contains(value))
									        				{
									        					list.get(selectelem).click();
									        				}
													}
												}
												//IF IT IS A CHECKBOX FIELD
												if(labeltype.equalsIgnoreCase("checkbox"))
												{
													WebElement selectelement=driver.findElement(By.cssSelector("div[id='editScreen_FieldsCustomization_FieldDetails'] div:nth-of-type("+row+") input"));
													if(value.equalsIgnoreCase("true"))
													{
														if(!(selectelement.isSelected()))
														{
															selectelement.click();
														}
																
													}
													
													
												}
												
											}
											
										}
										catch(NullPointerException ne)
										{
											  logger.info("Pointing null ");
											  if(labeltype.equalsIgnoreCase("select-one"))
												{
													WebElement selectelement=driver.findElement(By.cssSelector("div[id='editScreen_FieldsCustomization_FieldDetails'] div:nth-of-type("+row+") select"));
													Select dropdown= new Select(selectelement);
													List<WebElement> list = dropdown.getOptions();
													for(int selectelem=0; selectelem<list.size();selectelem++)
													{
														String listelements = list.get(selectelem).getText();
														if(listelements.contains(value))
									        				{
									        					list.get(selectelem).click();
									        				}
													}
												}
											 
										 }
										
								  break bodyloop;
								  
									}
								}
					       }
				  	}
			  
		  		}
  	}
  /* METHOD TO SAVE BODY FIELD DATA  */
  public void saveBodyField()
  {
	 wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#editScreen_FieldsCustomization_btns > span:nth-child(1)"))); 
	 driver.findElement(By.cssSelector("#editScreen_FieldsCustomization_btns > span:nth-child(1)")).click();
	 String actmsg = "";
	 try
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/section/div[4]/div/table/tbody/tr/td[2]")));
	 		actmsg=driver.findElement(By.xpath("/html/body/section/div[4]/div/table/tbody/tr/td[2]")).getText();
	 	}
		catch(Exception ee2)
		{
			
		}
		try
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
	     catch(Exception e1)
	     {
	    	 
	     }
		logger.info("Message for Save method is "+actmsg);
	 wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.cssSelector("section[id='mainDiv']")), "style", "pointer-events: auto;"));
	 
  }
}
