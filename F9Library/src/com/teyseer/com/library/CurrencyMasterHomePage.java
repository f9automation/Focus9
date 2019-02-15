package com.teyseer.com.library;

import java.util.ArrayList;
import java.util.Arrays;
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

public class CurrencyMasterHomePage extends LaunchApplication
{
	HomePage hp=new HomePage();
	Menus mp=new Menus();
	 long slowkeys=500;
	 String actmsg;
  @Test
  public void login() throws InterruptedException
  {
	  hp.LoginApp("su", "su", "A0407");
		boolean res;
		res=mp.menuSelection("Home", "Masters", "Currency", "Currency Master", "Currency Master");
		//logger.info("res "+res);
		ArrayList<String> xlcurrencyDetailsnames = new ArrayList(Arrays.asList("ISO Currency Code", "Symbol","Coins Name", "No Of Decimals","General Round Off", "Currency Unit", "Currency SubUnit", "Connector", "Rounding Type"));
		ArrayList<String> xlcurrencyDetailsvalues = new ArrayList(Arrays.asList("AOA", "", "bAfd" ,"6","0.214", "2.15", "12.5445", "", "Down"));
		currencyDetails(xlcurrencyDetailsnames, xlcurrencyDetailsvalues);
		//ArrayList<String> xlroundOffDetailsnames = new ArrayList(Arrays.asList());
		//ArrayList<String> xlroundOffDetailsvalues = new ArrayList(Arrays.asList());
		roundingOffDetails(xlcurrencyDetailsnames, xlcurrencyDetailsvalues);
		Thread.sleep(3000);
		//saveCurrency();
		clearData();
		Thread.sleep(3000);
		driver.quit();
  }
  public void currencyDetails(ArrayList<String> xllabelnames , ArrayList<String> xllabelvalues) throws InterruptedException 
  {
	  ArrayList<String> currencyDet=new ArrayList(Arrays.asList("General Round Off", "Currency Unit", "Currency SubUnit", "Connector", "Rounding Type"));
	  ArrayList<String> labelnames =new ArrayList(xllabelnames);
	  ArrayList<String> labelvalues=new ArrayList(xllabelvalues);
	  logger.info("xllabelnames "+xllabelnames+" xlvalues "+xllabelvalues);
	  ArrayList<Integer> toremoveindicies=new ArrayList();
	  toremoveindicies.clear();
	  for(String val: xllabelnames)
	  {
		 if(currencyDet.contains(val))
		 {
			 //logger.info("index "+xllabelnames.indexOf(val));
			 toremoveindicies.add(xllabelnames.indexOf(val));
			 
		 }
	  }
	  for(int ind: toremoveindicies)
	  {
		  labelvalues.set(ind, "null");
	  }
	  labelvalues.removeAll(Arrays.asList("null"));
	 // labelvalues.removeAll(labelnames.indexOf("General Round Off", "Currency Unit", "Currency SubUnit", "Connector", "Rounding Type"));
	  labelnames.removeAll(Arrays.asList("General Round Off", "Currency Unit", "Currency SubUnit", "Connector", "Rounding Type"));
	  
	  logger.info("labelnames are "+labelnames);
	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//fieldset[@class='clsFieldset']")));
	  driver.findElement(By.xpath("//fieldset[@class='clsFieldset']"));
	  List<WebElement> totrows=driver.findElements(By.xpath("//fieldset[@class='clsFieldset']/div/div"));
	  int col;
	  String value;
	  for(String xllabelname: labelnames)
	  {
		  for(int r=1;r<=totrows.size();r++)
		  {
			  List<WebElement> totcols=driver.findElements(By.xpath("//fieldset[@class='clsFieldset']/div/div["+r+"]/div"));
			  for (int c=1;c<=totcols.size();c+=2)
			  {		
				  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//fieldset[@class='clsFieldset']/div[1]/div["+r+"]/div["+c+"]/label | //fieldset[@class='clsFieldset']/div[1]/div["+r+"]/div["+c+"]")));
				  WebElement label=driver.findElement(By.xpath("//fieldset[@class='clsFieldset']/div[1]/div["+r+"]/div["+c+"]/label | //fieldset[@class='clsFieldset']/div[1]/div["+r+"]/div["+c+"]"));
				  Thread.sleep(1000);
				  try
				  {
					  String labelname=label.getAttribute("textContent").trim();
					  col=c+1;
					  if(!(labelname.startsWith("var")))
					  {
						 if(labelname.equalsIgnoreCase(xllabelname))
						  {
							 wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//fieldset[@class='clsFieldset']/div[1]/div["+r+"]/div["+col+"]")));
							 WebElement elem=driver.findElement(By.xpath("//fieldset[@class='clsFieldset']/div[1]/div["+r+"]/div["+col+"]"));
							 elem.click();
							
							  try
							  {
								  value= labelvalues.get(labelnames.indexOf(labelname));
								  Actions act=new Actions(driver);
								  act.moveToElement(elem).doubleClick().perform();
								  Thread.sleep(1000);
								  if(value.length()>0)
								  {
									  for(int i=0;i<value.length();i++)
									  {
										  char ch= value.charAt(i);
										  act.sendKeys(String.valueOf(ch)).build().perform();
										  Thread.sleep(slowkeys);
									  }
								  }
								  	act.sendKeys(Keys.TAB).build().perform();
								  	
								  	break;
								 }
							  catch(Exception e)
							  {
								  logger.info("catch msg");
								  break;
							  }
						  }
					  }
				  }
				  catch(Exception e)
				  {
					  logger.info("exe");
					  break;
				  }
			  }
			 
		 }
	  }
	  
  }
  public void roundingOffDetails(ArrayList<String> xllabelnames, ArrayList<String> xllabelvalues) throws InterruptedException
  {
	  ArrayList<String> roundOffDet=new ArrayList(Arrays.asList("ISO Currency Code", "Symbol","Coins Name", "No Of Decimals"));
	  ArrayList<String> labelnames =new ArrayList(xllabelnames);
	  ArrayList<String> labelvalues=new ArrayList(xllabelvalues);
	  logger.info("xllabelnames "+xllabelnames+" xlvalues "+xllabelvalues);
	  ArrayList<Integer> toremoveindicies=new ArrayList();
	  toremoveindicies.clear();
	  for(String val: xllabelnames)
	  {
		 if(roundOffDet.contains(val))
		 {
			 logger.info("index "+xllabelnames.indexOf(val));
			 toremoveindicies.add(xllabelnames.indexOf(val));
			 
		 }
	  }
	  for(int ind: toremoveindicies)
	  {
		  labelvalues.set(ind, "null");
	  }
	  labelvalues.removeAll(Arrays.asList("null"));
	  labelnames.removeAll(Arrays.asList("ISO Currency Code", "Symbol","Coins Name", "No Of Decimals"));
	  //logger.info("xllabelnames "+labelnames);
	  
	  try
	  {
		  driver.switchTo().alert().accept();
	  }
	  catch(Exception e)
	  {
		  logger.info("No alert found");
		  
	  }
	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='currencyMasterWidth']/div[1]/div[1]")));
	  List<WebElement> totrows=driver.findElements(By.xpath("//*[@id='currencyMasterWidth']/div[1]/div[1]/div"));
	  for(String xllabelname: labelnames)
	  {
		  for(int r=1;r<=totrows.size();r++)
		  {
			  List<WebElement> totcols= driver.findElements(By.xpath("//*[@id='currencyMasterWidth']/div[1]/div[1]/div["+r+"]/div"));
			  float div=totcols.size()%2;
			  int c=1;
			  if(!(div==0))
			  {
				 c=2;
				  
			  }
			  for(c=c;c<=totcols.size();c+=2)
			  {
				  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='currencyMasterWidth']/div[1]/div[1]/div["+r+"]/div["+c+"]/label")));
				  WebElement label=driver.findElement(By.xpath("//*[@id='currencyMasterWidth']/div[1]/div[1]/div["+r+"]/div["+c+"]/label"));
				  String labelname=label.getAttribute("textContent");
				  if(labelname.equalsIgnoreCase(xllabelname))
				  {
					  int col=c+1;
					 // logger.info("labelvalues "+labelvalues+" labelnames.indexOf(labelname) " +labelnames.indexOf(labelname));
					  String value=labelvalues.get(labelnames.indexOf(labelname));
					  WebElement child= driver.findElement(By.xpath("//*[@id='currencyMasterWidth']/div[1]/div[1]/div["+r+"]/div["+col+"]"));
					  Thread.sleep(1000);
					  WebElement followingSibling = child.findElement(By.xpath(".//*"));
					  if(followingSibling.getTagName().equalsIgnoreCase("select"))
					  {
						  WebElement mySelectElement =driver.findElement(By.xpath("//*[@id='currencyMasterWidth']/div[1]/div[1]/div["+r+"]/div["+col+"]/select"));
						  mySelectElement.click();
						  Select dropdown= new Select(mySelectElement);
					      List<WebElement> list = dropdown.getOptions();
					      for(int li=0; li<list.size(); li++) 
					      {
					    	  String optionName = list.get(li).getText();
					    	  if(value.equalsIgnoreCase(optionName))
					    	  {
					    		  list.get(li).click();
					    		  Thread.sleep(1000);
					    		  break;
					    	  }
					    	}
					  }
					  else
					  {
						  WebElement elem= driver.findElement(By.xpath("//*[@id='currencyMasterWidth']/div[1]/div[1]/div["+r+"]/div["+col+"]"));
						  elem.click();
						  Actions act=new Actions(driver);
						  act.moveToElement(elem).doubleClick().perform();
						  for(int i=0;i<value.length();i++)
						  {
							  char ch=value.charAt(i);
							  act.sendKeys(String.valueOf(ch)).build().perform();
						  }
						  Thread.sleep(1000);
						  
					  }
				  }
			  }
		  }
	  }
  }
  public void saveCurrency()
  {
	  wait.until(ExpectedConditions.elementToBeClickable(By.id("btnSave")));
	  driver.findElement(By.id("btnSave")).click();
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
  }
  public void clearData()
  {
	  //wait.until(ExpectedConditions.elementToBeClickable(By.id("clear_Currency")));
	  //driver.findElement(By.id("clear_Currency")).click();
	  List<WebElement> totrows=driver.findElements(By.xpath("//fieldset[@class='clsFieldset']/div/div"));
	  for(int r=1;r<=totrows.size();r++)
	  {
		  List<WebElement> totcols= driver.findElements(By.xpath("//*[@id='currencyMasterWidth']/div[1]/div[1]/div["+r+"]/div"));
		  //logger.info("cols "+totcols.size());
		  for(int c=1;c<=totcols.size();c+=2)
		  {
			  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//fieldset[@class='clsFieldset']/div[1]/div["+r+"]/div["+c+"]/label | //fieldset[@class='clsFieldset']/div[1]/div["+r+"]/div["+c+"]")));
			  WebElement label=driver.findElement(By.xpath("//fieldset[@class='clsFieldset']/div[1]/div["+r+"]/div["+c+"]/label | //fieldset[@class='clsFieldset']/div[1]/div["+r+"]/div["+c+"]"));
			  int col=c+1;
			  wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//fieldset[@class='clsFieldset']/div[1]/div["+r+"]/div["+col+"]")));
			  WebElement elem=driver.findElement(By.xpath("//fieldset[@class='clsFieldset']/div[1]/div["+r+"]/div["+col+"]"));
			  //elem.click();
			  WebElement element;
			 try
			 {
				element = elem.findElement(By.cssSelector("input"));
			 }
			 catch(Exception e )
			 {
				 logger.info("exec");
				 element = driver.findElement(By.xpath("//fieldset[@class='clsFieldset']/div[1]/div["+r+"]/div["+col+"]"));
			 }
			
			 JavascriptExecutor executor = (JavascriptExecutor) driver;
			 Object aa=executor.executeScript("var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;", element);
			 logger.info("tag naem "+element.getTagName()+" & "+aa.toString());
			 logger.info("v "+element.getAttribute("value"));
			
		  }
	  }
	  
	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='currencyMasterWidth']/div[1]/div[1]")));
	  List<WebElement> totalrows=driver.findElements(By.xpath("//*[@id='currencyMasterWidth']/div[1]/div[1]/div"));
	  for(int r=1;r<=totrows.size();r++)
	  {
		  List<WebElement> totcols= driver.findElements(By.xpath("//*[@id='currencyMasterWidth']/div[1]/div[1]/div["+r+"]/div"));
		  float div=totcols.size()%2;
		  int c=1;
		  if(!(div==0))
		  {
			 c=2;
			  
		  }
		  for(c=c;c<=totcols.size();c+=2)
		  {
			  int col=c+1;
			  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='currencyMasterWidth']/div[1]/div[1]/div["+r+"]/div["+col+"]")));
			  WebElement labelcol=driver.findElement(By.xpath("//*[@id='currencyMasterWidth']/div[1]/div[1]/div["+r+"]/div["+col+"]"));
			  WebElement label=labelcol.findElement(By.cssSelector("input, select"));
			  String labelname=label.getAttribute("textContent");
			  logger.info("toudorff val "+labelname);
			  JavascriptExecutor executor = (JavascriptExecutor) driver;
			  Object aa=executor.executeScript("var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;", label);
			  logger.info("tag naem "+label.getTagName()+" & "+aa.toString());
			  
		  }
	  
  }
}
}