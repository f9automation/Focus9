package com.teyseer.com.transactions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import java.util.List;
import java.util.Set;


import org.openqa.selenium.By;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.testng.annotations.Test;

import com.focus.constants.LaunchApplication;



public class TransactionSave extends LaunchApplication
{
	public  String actmsg;
	public double slowkeys=300;
	double exrt=1.0;
	public WebElement transSavePage;
	double calcbasecurrency;
	double actbasecurrency;
	ArrayList<String> basecurrcalattribs =new ArrayList();
	ArrayList<String> basecurrcalcvalues =new ArrayList();
	ArrayList<String> totresults= new ArrayList();
	public ArrayList<String> calcbasecurrvalue=new ArrayList();
	public ArrayList<String> actbasecurrvalue=new ArrayList<String>();
	public ArrayList<String> balamtvalues=new ArrayList();
  	public ArrayList<String> natcurrvalues=new ArrayList();
  	WebElement adjbills;
	TransactionHeader th=new TransactionHeader();
	TransactionBody tb=new TransactionBody();
	public boolean result;
	List<WebElement> adjbillsbodyrows;
	int docheader=0,adjamtheader=0, originalamtheader=0, balamtheader=0, natcurrheader=0, prevadjamtheader=0, currencyheader=0;
	
	/* METHOD TO GET EXCHANGE RATE */
	public void getExchangeRate(String exhrt)
	{
		exrt=Double.parseDouble(exhrt);
	}
	public void getExchangeRate(Set exhrt, ArrayList basecurrcalvalues)
	{
		basecurrcalattribs.clear();
		calcbasecurrvalue.clear();
		basecurrcalattribs.addAll(exhrt);
		basecurrcalcvalues.clear();
		basecurrcalcvalues.addAll(basecurrcalvalues);
		basecurrcalcvalues.removeAll(Arrays.asList("", null));
		
		/* TO ADD EXCHANGE RATE OF HEADER BESIDE EACH AMOUNT VALUE AND CALCULATE */
		if(!(basecurrcalattribs.contains("ExchangeRate")))
		{
			int index = 0;
			int size=basecurrcalcvalues.size();
			try {
			for(int i=0;i<size;i++)
			{
				index+=i+1;
				basecurrcalcvalues.add(index,String.valueOf(exrt));
				
			}
			}
			catch(IndexOutOfBoundsException ie)
			{
				basecurrcalcvalues.add(String.valueOf(exrt));
				
			}
			
				
		}
		
		double cal;
		double roundcalcvalue;
		try
		{
			cal=Double.parseDouble(basecurrcalcvalues.get(0))*Double.parseDouble(basecurrcalcvalues.get(1));
			for(int i=0;i<basecurrcalcvalues.size();i+=2)
			{
				roundcalcvalue=Math.round((Double.parseDouble(basecurrcalcvalues.get(i))*Double.parseDouble(basecurrcalcvalues.get(i+1)))*100.00)/100.00;
				calcbasecurrvalue.add(String.valueOf(roundcalcvalue));
				
			
			}
		}
		catch(IndexOutOfBoundsException ie)
		{
			if(transSavePage.isDisplayed())
			{
				logger.info("save is still displayed ");
				cal=0;
			}
			else
			{
				logger.info("save is not getting  displayed ");
				wait.until(ExpectedConditions.presenceOfElementLocated(By.id("txtblkAmounttobeadjust")));
				String balamt=driver.findElement(By.id("txtblkAmounttobeadjust")).getText();
				cal=Double.parseDouble(balamt)*exrt;
			
			}
			calcbasecurrvalue.add(String.valueOf(cal));
			logger.info("calcbasecurrvalue "+calcbasecurrvalue);
		}
		
	}
	
	/* METHOD TO SAVE TRANSACTIONS IF THERE ARE IS NO BILLWISE */
	public void transactionSave() throws InterruptedException
	{
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/section/div[2]/div/section[1]/div[1]/div/div[1]/nav/div[2]/div/div[6]/div[1]/span")));
		transSavePage= driver.findElement(By.xpath("/html/body/section/div[2]/div/section[1]/div[1]/div/div[1]/nav/div[2]/div/div[6]/div[1]/span"));
		transSavePage.click();
		Thread.sleep(3000);
		try
		{
			driver.switchTo().alert().accept();
		}
		catch(Exception e)
		{
		 		
		}
	}
	
	/* METHOD TO GET BASE CURRENCY VALUE BY CALCULATION*/
	public void basecurrencyCalculedValues() throws InterruptedException
	  {
		  Thread.sleep(1000);
		  wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtblkAmounttobeadjust")));
		  String amt=driver.findElement(By.id("txtblkAmounttobeadjust")).getText();
		  logger.info("Bal amt is "+amt+ "Exrt "+exrt);
		  calcbasecurrency=exrt*Double.parseDouble(amt);
		  logger.info("calculated Base currency from base cur calc is "+calcbasecurrency);
	  }
	
	/* METHOD TO GET ACTUAL BASE CURRENCY FROM APPLICAITON AND COMPARE IT WITH THE CALCULATED BASE CURRENCY */
 	public boolean basecurrency() throws InterruptedException
 	{
	  actbasecurrvalue.clear();
	  if(transSavePage.isDisplayed())
	  {
		  	actbasecurrvalue.add("0.0");
		  	return false;
	  }
	  else
		{
			shortwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='id_AdjustmentBills']/div")));
			WebElement adjbills=driver.findElement(By.xpath("//div[@id='id_AdjustmentBills']/div"));
			List<WebElement> adjbillscount=driver.findElements(By.xpath("//div[@id='id_AdjustmentBills']/div[not(contains(@style, 'display: none'))]"));
			totresults.clear();
			if(adjbillscount.size()>2)
				{
					WebElement billtable=driver.findElement(By.xpath("//div[@id='id_AdjustmentBills']/div[@id='id_AccountAmt']/table[@id='AccountAmount']"));
					List<WebElement> billamtbodyrows=billtable.findElements(By.xpath("//tbody[@id='AccountAmount_body']/tr"));
					
					/* GET BASE CURRENCY VALUE FROM APPLICATION, COMPARE IT WITH CALCULATED VALUE AND RETURN TRUE IF MATCHES */
					for(int i=1;i<=billamtbodyrows.size();i++)
					{
						billtable.findElement(By.xpath("//tbody[@id='AccountAmount_body']/tr["+i+"]")).click();
						Thread.sleep(1000);
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tbAmountToAdjustInBaseCurrencyValue")));
						String ele=driver.findElement(By.id("tbAmountToAdjustInBaseCurrencyValue")).getText();
						actbasecurrency=Double.parseDouble(ele);
						if((calcbasecurrvalue.get(i-1).equals(String.valueOf(actbasecurrency))))
				 		{
				 			totresults.add("true");
				 			actbasecurrvalue.add(String.valueOf(actbasecurrency));
				 				
				 		}
				 		else
				 		{
				 			logger.info("Calculated base currency "+calcbasecurrvalue.get(i-1)+" and captured Base curr amt "+actbasecurrency+" are not equal");
				 			totresults.add("False");
				 			actbasecurrvalue.add(String.valueOf(actbasecurrency));
				 		}
			 		
					}
			
					
				}
			else
			{
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tbAmountToAdjustInBaseCurrencyValue")));
				String ele=driver.findElement(By.id("tbAmountToAdjustInBaseCurrencyValue")).getText();
				actbasecurrency=Double.parseDouble(ele);
				if((calcbasecurrvalue.get(0).equals(String.valueOf(actbasecurrency))))
		 		{
	 				logger.info("Calculated base currency "+calcbasecurrvalue.get(0)+" and captured Base curr amt "+actbasecurrency+" are  equal");	
	 				totresults.add("true");
	 				actbasecurrvalue.add(String.valueOf(actbasecurrency));
		 		}
		 		else
		 		{
		 			logger.info("Calculated base currency "+calcbasecurrvalue.get(0)+" and captured Base curr amt "+actbasecurrency+" are not equal");
		 			totresults.add("False");
		 			actbasecurrvalue.add(String.valueOf(actbasecurrency));
		 		}
				
			}
			if(!(totresults.contains("False")))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	 
	  
  }
 	/* BALANCE AMOUNT, NATIVE CURRENCY CALCULATIONS */
  	public boolean billNoCalculations(String expmsg) throws InterruptedException
	  {
		  	String actbalamtvalue,actcurrencyvalue, actoriginalamtvalue, actnatcurrvalue, actadjamtvalue, actprevadjamtvalue,actbasecurrvalue; 	
		  	Double calcbalamtvalue,  calcnatcurrvalue = 0.0,  calcprevadjamtvalue;
		  	ArrayList<String> totresults=new ArrayList();
		  	balamtvalues.clear();
		  	natcurrvalues.clear();
		  	totresults.clear();
		  	WebElement billtable=driver.findElement(By.xpath("//div[@id='id_AdjustmentBills']/div[@id='id_AccountAmt']/table[@id='AccountAmount']"));
			List<WebElement> billamtbodyrows=billtable.findElements(By.xpath("//tbody[@id='AccountAmount_body']/tr"));
			for(int i=1;i<=billamtbodyrows.size();i++)
			{
				if(billamtbodyrows.size()>2)
				{
				wait.until(ExpectedConditions.visibilityOf(billtable.findElement(By.xpath("//tbody[@id='AccountAmount_body']/tr["+i+"]"))));
				billtable.findElement(By.xpath("//tbody[@id='AccountAmount_body']/tr["+i+"]")).click();
				}
				List<WebElement> billamtbodycols=billtable.findElements(By.xpath("//tbody[@id='AccountAmount_body']/tr["+i+"]/td"));
				Thread.sleep(1000);
				shortwait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[id='id_Adjustment_BillReference']")));
				List<WebElement> adjbillref=driver.findElements(By.xpath("//div[@id='id_Adjustment_BillReference']/div"));
				List<WebElement> adjbillsheads= driver.findElements(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/thead[@id='id_Adjustment_Grid_head']/tr[@id='id_Adjustment_Grid_row_heading']/th[not(contains(@style,'display: none'))]"));
	 	 		WebElement adjbillshead= driver.findElement(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/thead[@id='id_Adjustment_Grid_head']/tr[@id='id_Adjustment_Grid_row_heading']"));
	 	 		
	 	 		for(int header=2;header<=adjbillsheads.size();header++)
	 	 		{
	 	 			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/thead[@id='id_Adjustment_Grid_head']/tr[@id='id_Adjustment_Grid_row_heading']/th[not(contains(@style,'display: none'))]["+header+"]")));
	 	 			WebElement adjbillsheader= adjbillshead.findElement(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/thead[@id='id_Adjustment_Grid_head']/tr[@id='id_Adjustment_Grid_row_heading']/th[not(contains(@style,'display: none'))]["+header+"]"));
	 	 			String attr=adjbillsheader.getAttribute("data-sname");
	 	 			String name=adjbillsheader.getText();
	 	 			logger.info("Attr "+attr+" name "+name);
	 	 			/* TO GET THE RESPECTIVE COLUMN NUMBERS  */
	 	 		if(attr.equalsIgnoreCase("sReference"))
		 	 			{
		 	 				docheader=header;
		 	 			}
		 	 			if(name.equalsIgnoreCase("Adjustment Amount"))
		 	 			{
		 	 				adjamtheader=header;
		 	 			}
		 	 			if(name.equalsIgnoreCase("Original Amt"))
		 	 			{
		 	 				originalamtheader=header;
		 	 			}
		 	 			if(name.equalsIgnoreCase("Balance Amount"))
		 	 			{
		 	 				balamtheader=header;
		 	 			}
		 	 			if(attr.equalsIgnoreCase("mNativeCurrency"))
		 	 			{
		 	 				natcurrheader=header;
		 	 			}
		 	 			if(attr.equalsIgnoreCase("dPreviousAdjustedAmount"))
		 	 			{
		 	 				prevadjamtheader=header;
		 	 			}
		 	 			if(name.equalsIgnoreCase("Currency"))
		 	 			{
		 	 				currencyheader=header;
		 	 			}
	 	 			
	 	 		}
	 	 		
				adjbillsbodyrows=driver.findElements(By.cssSelector("div[id='id_Adjustment_Invoices'] div[id='id_Adjustment_Invoices_Grid'] table[id='id_Adjustment_Grid'] tbody[id='id_Adjustment_Grid_body'] tr, div[id='id_Adjustment_Invoices'] div[id='id_Adjustment_Invoices_Grid'] table[id='id_Adjustment_Grid'] tbody[id='id_Adjustment_Grid_body']"));
				if(adjbillsbodyrows.size()>1)
				{
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tbAmountToAdjustInTransCurrencyValue")));
					WebElement transcurrency=driver.findElement(By.id("tbAmountToAdjustInTransCurrencyValue"));
					String transcurrvalue=transcurrency.getText();
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tbAmountToAdjustInBaseCurrencyValue")));
					WebElement basecurrency=driver.findElement(By.id("tbAmountToAdjustInBaseCurrencyValue"));
					String basecurrvalue=basecurrency.getText();
					for(int row=1;row<adjbillsbodyrows.size();row++)
					{
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']/tr["+row+"]/td[not(contains(@style,'display: none'))]["+adjamtheader+"]")));
			 			logger.info("adjamt header "+adjamtheader);
						WebElement adjamt=driver.findElement(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']/tr["+row+"]/td[not(contains(@style,'display: none'))]["+adjamtheader+"]"));
			 			actadjamtvalue=adjamt.getText();
			 			if(Double.parseDouble(actadjamtvalue)>0)
				 		{
			 				/* BALANCE AMOUNT CALCULATION */
			 				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']/tr["+row+"]/td[not(contains(@style,'display: none'))]["+currencyheader+"]")));
				 			WebElement currency=driver.findElement(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']/tr["+row+"]/td[not(contains(@style,'display: none'))]["+currencyheader+"]"));
				 			currency.click();
				 			actcurrencyvalue=currency.getText();
				 			logger.info("Actcurr "+actcurrencyvalue );
				 			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id_infoPanel_lblAEDTranAmount")));
				 			WebElement amt=driver.findElement(By.id("id_infoPanel_lblAEDTranAmount"));
				 			String amtvalue=amt.getText().replaceAll("Amount","");
				 			amtvalue=amtvalue.replace('(', ' ');
				 			amtvalue=amtvalue.replace(')', ' ');
				 			amtvalue=amtvalue.trim();
				 			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']/tr["+row+"]/td[not(contains(@style,'display: none'))]["+balamtheader+"]")));
				 			WebElement balamt=driver.findElement(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']/tr["+row+"]/td[not(contains(@style,'display: none'))]["+balamtheader+"]"));
				 			actbalamtvalue=balamt.getText();
				 			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']/tr["+row+"]/td[not(contains(@style,'display: none'))]["+originalamtheader+"]")));
				 			WebElement originalamt=driver.findElement(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']/tr["+row+"]/td[not(contains(@style,'display: none'))]["+originalamtheader+"]"));
				 			actoriginalamtvalue=originalamt.getText();
				 			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']/tr["+row+"]/td[not(contains(@style,'display: none'))]["+natcurrheader+"]")));
				 			WebElement natcurr=driver.findElement(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']/tr["+row+"]/td[not(contains(@style,'display: none'))]["+natcurrheader+"]"));
				 			actnatcurrvalue=natcurr.getText();
				 			WebElement prevadjamt=driver.findElement(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']/tr["+row+"]/td[not(contains(@style,'display: none'))]["+prevadjamtheader+"]"));
				 			actprevadjamtvalue=prevadjamt.getText();
				 			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id_BillWise_IP_BaseCurrencyValue")));
				 			WebElement basecurr=driver.findElement(By.id("id_BillWise_IP_BaseCurrencyValue"));
				 			actbasecurrvalue=basecurr.getText();
				 			calcbalamtvalue=Double.parseDouble(actoriginalamtvalue)-Double.parseDouble(actprevadjamtvalue);
				 			/* NATIVE CURRENCY CALCULATION */
				 			if(!(Double.parseDouble(transcurrvalue)==Double.parseDouble(basecurrvalue)))
			 				{
				 				if(!(actcurrencyvalue.equalsIgnoreCase(amtvalue)))
				 				{
				 					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id_infoPanel_lblAEDBaseConversionValue")));
				 					WebElement asonentrybaseconv=driver.findElement(By.id("id_infoPanel_lblAEDBaseConversionValue"));
				 					String asonentrybaseconvalue=asonentrybaseconv.getText();
				 					calcnatcurrvalue=((Double.parseDouble(actadjamtvalue)*Double.parseDouble(asonentrybaseconvalue))/Double.parseDouble(actbasecurrvalue));
				 				}
				 				if(actcurrencyvalue.equalsIgnoreCase(amtvalue))
				 				{
				 					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id_BillWise_IP_BaseCurrencyValue")));
				 					WebElement convratebasecurr=driver.findElement(By.id("id_BillWise_IP_BaseCurrencyValue"));
				 					String convratebasecurrvalue=convratebasecurr.getText();
				 					calcnatcurrvalue=Double.parseDouble(actadjamtvalue)/Double.parseDouble(convratebasecurrvalue);
				 				}
				 			}
				 			if(Double.parseDouble(transcurrvalue)==Double.parseDouble(basecurrvalue))
			 				{
				 				if(actcurrencyvalue.equalsIgnoreCase(amtvalue))
				 				{
				 					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id_BillWise_IP_BaseCurrencyValue")));
				 					WebElement convratebasecurr=driver.findElement(By.id("id_BillWise_IP_BaseCurrencyValue"));
				 					String convratebasecurrvalue=convratebasecurr.getText();
				 					calcnatcurrvalue=(Double.parseDouble(actadjamtvalue)/Double.parseDouble(actbasecurrvalue));
				 				}
				 				if(!(actcurrencyvalue.equalsIgnoreCase(amtvalue)))
				 				{
				 					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id_infoPanel_lblAEDBaseConversionValue")));
				 					WebElement asonentrybaseconv=driver.findElement(By.id("id_infoPanel_lblAEDBaseConversionValue"));
				 					String asonentrybaseconvalue=asonentrybaseconv.getText();
				 					calcnatcurrvalue=((Double.parseDouble(actadjamtvalue)*Double.parseDouble(asonentrybaseconvalue))/Double.parseDouble(actbasecurrvalue));
				 				}
			 				}
				 			calcnatcurrvalue=Math.round (calcnatcurrvalue*100.0) / 100.0;
				 			balamtvalues.add("For Reference "+i+" whose Bill Row No "+row+": Calculated Amount is "+calcbalamtvalue+" & Actual Amount "+actbalamtvalue+", ");
				 			natcurrvalues.add("For Reference "+i+" whose Bill Row No "+row+": Calculated NativeCurrency is "+calcnatcurrvalue+" & Actual NativeCurrency "+actnatcurrvalue+", ");
				 			
				 			/* VERIFY AND RETURN TRUE IF CALCULATED VALUES ARE EQUAL TO ACTUAL VALUES */
				 			if((calcbalamtvalue==Double.parseDouble(actbalamtvalue))&&(calcnatcurrvalue==Double.parseDouble(actnatcurrvalue)))
			 				{
			 					totresults.add("true");
			 				}
				 			else
				 			{
				 				totresults.add("false");
				 			}
				 		}
					}
					}
				}
				
			
			
			result=finalOkClick(expmsg);
			if(!(totresults.contains("false")))
					{
						return true;
					}
			return false;
			
	  }
  	
  	/* METHOD TO ADJUST BILLS AGAINST REFERENCES BY PASSING THE BILL  NOS AND ITS RESPECTIVE VALUES AS PARAMETERS */
	public void adjustBillReferences(String billnos) throws InterruptedException
	{
		
			WebElement billtable=driver.findElement(By.xpath("//div[@id='id_AdjustmentBills']/div[@id='id_AccountAmt']/table[@id='AccountAmount']"));
			List<WebElement> billamtbodyrows=billtable.findElements(By.xpath("//tbody[@id='AccountAmount_body']/tr"));
			int rowno = 0;
			List<WebElement> adjbillsbodyrows;
			int docheader=0,adjamtheader=0, originalamtheader=0, balamtheader=0, natcurrheader=0;
			for(int i=1;i<=billamtbodyrows.size();i++)
			{
				rowno=i;
				billtable.findElement(By.xpath("//tbody[@id='AccountAmount_body']/tr["+i+"]")).click();
				List<WebElement> billamtbodycols=billtable.findElements(By.xpath("//tbody[@id='AccountAmount_body']/tr["+i+"]/td"));
				Thread.sleep(1000);
				shortwait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[id='id_Adjustment_BillReference']")));
				List<WebElement> adjbillref=driver.findElements(By.xpath("//div[@id='id_Adjustment_BillReference']/div"));
				ArrayList<String> adjbillvalue=new ArrayList(Arrays.asList(billnos.split("\\,|-|/")));
	 	 		for(int r=0;r<adjbillvalue.size();r+=3)
	 	 		{
	 	 			int docno=1,amtadjust=2, adjustchckbox=2;
	 	 			if(adjbillvalue.get(r).equals(String.valueOf(rowno)))
	 	 			{
	 	 				try
			 	 		{
				 	 		List<WebElement> adjbillsheads= driver.findElements(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/thead[@id='id_Adjustment_Grid_head']/tr[@id='id_Adjustment_Grid_row_heading']/th[not(contains(@style,'display: none'))]"));
				 	 		WebElement adjbillshead= driver.findElement(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/thead[@id='id_Adjustment_Grid_head']/tr[@id='id_Adjustment_Grid_row_heading']"));
				 	 		
				 	 		for(int header=2;header<=adjbillsheads.size();header++)
				 	 		{
				 	 			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/thead[@id='id_Adjustment_Grid_head']/tr[@id='id_Adjustment_Grid_row_heading']/th[not(contains(@style,'display: none'))]["+header+"]")));
				 	 			WebElement adjbillsheader= adjbillshead.findElement(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/thead[@id='id_Adjustment_Grid_head']/tr[@id='id_Adjustment_Grid_row_heading']/th[not(contains(@style,'display: none'))]["+header+"]"));
				 	 			String attr=adjbillsheader.getAttribute("data-sname");
				 	 			String name=adjbillsheader.getAttribute("textContent");
				 	 			if(attr.equalsIgnoreCase("sReference"))
				 	 			{
				 	 				docheader=header;
				 	 			}
				 	 			if(name.equalsIgnoreCase("Adjustment Amount"))
				 	 			{
				 	 				adjamtheader=header;
				 	 			}
				 	 			if(name.equalsIgnoreCase("OriginalAmountTC"))
				 	 			{
				 	 				originalamtheader=header;
				 	 			}
				 	 			if(name.equalsIgnoreCase("Balance Amount"))
				 	 			{
				 	 				balamtheader=header;
				 	 			}
				 	 			if(name.equalsIgnoreCase("Native Currency"))
				 	 			{
				 	 				natcurrheader=header;
				 	 			}
				 	 		}	
				 	 		List<WebElement> adjbillsbodyr=driver.findElements(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']"));
				 	 		WebElement adjbillbr=driver.findElement(By.cssSelector("div[id='id_Adjustment_Invoices'] div[id='id_Adjustment_Invoices_Grid'] table[id='id_Adjustment_Grid'] tbody[id='id_Adjustment_Grid_body']"));
				 	 		List<WebElement> childsc = adjbillbr.findElements(By.cssSelector("*"));
				 	 		
				 	 		if(childsc.size()>0)
					 	 	{
				 	 			adjbillsbodyrows=driver.findElements(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']/tr"));
					 	 		int div;
					 	 		if(adjbillvalue.size()>3)
					 	 		{
					 	 			div=3;
					 	 		}
					 	 		else
					 	 		{
					 	 			div=3;
					 	 		}
					 	 		for(int k=0;k<adjbillvalue.size()/div;k++)
					 	 		{
					 	 			for(int row=1;row<=adjbillsbodyrows.size();row++)
						 	 		{
						 	 			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']/tr["+row+"]/td[not(contains(@style,'display: none'))]["+docheader+"]")));
						 	 			WebElement adjbillbodydocno=driver.findElement(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']/tr["+row+"]/td[not(contains(@style,'display: none'))]["+docheader+"]"));
						 	 			String docnum=adjbillbodydocno.getText();
						 	 			if(adjbillvalue.get(docno+r).equalsIgnoreCase(docnum))
						 	 			{
						 	 				
						 	 				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']/tr["+row+"]/td[not(contains(@style,'display: none'))]["+adjamtheader+"]")));
						 	 	 			WebElement adjbillbodyamtadj=driver.findElement(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']/tr["+row+"]/td[not(contains(@style,'display: none'))]["+adjamtheader+"]"));
						 	 	 			adjbillbodyamtadj.click();
						 	 	 			String value=adjbillvalue.get(amtadjust+r);
						 	 	 			adjbillvalue.set(amtadjust+r, "");
						 	 	 			/* IF VALUE IS "0" FROM EXCEL THEN IT NEED TO CLICK ON THE RESPECTIVE BILL NO CHECKBOX */
						 	 	 			if(value.equalsIgnoreCase("0"))
						 	 	 			{
						 	 	 				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']/tr["+row+"]/td[not(contains(@style,'display: none'))]["+adjustchckbox+"]")));
						 	 	 	 			WebElement adjbillbodychckbox=driver.findElement(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']/tr["+row+"]/td[not(contains(@style,'display: none'))]["+adjustchckbox+"]"));
						 	 	 	 			adjbillbodychckbox.click();
						 	 	 	 			adjbillbodychckbox.click();
						 	 	 			}
						 	 	 			/* ELSE IT NEED TO ENTER THE AMOUNT TO BE ADJUSTED RESPECTIVELY WHICH IS SENT FROM EXCEL */
						 	 	 			else
							 	 	 		{
							 	 	 			Actions act=new Actions(driver);
							 	 	 			for(int l=0;l<value.length();l++)
							 	 	 			{
							 	 	 				char ch=value.charAt(l);
							 	 	 				act.sendKeys(String.valueOf(ch));
							 	 	 				act.build().perform();
							 	 	 				Thread.sleep((long)slowkeys);
							 	 	 			}
							 	 	 			act.sendKeys(Keys.TAB).build().perform();
							 	 	 			
							 	 	 		}
						 	 	 			Thread.sleep(1000);
						 	 	 			
						 	 	 			break;
						 	 			}
						 	 			
						 	 		}
					 	 			
					 	 			k++;
					 	 		}
					 	 		
					 	 	}
				 	 		
				 	 }
	 	 				catch(Exception ex)
	 	 				{
	 	 			
	 	 				}
	 	 		}
	 	 	}
	 	 		
	 	 		
		}
			
			
	}
	
	/* METHOD TO ADJUST BILLS AND AS WELL AS PARTIAL NEW REFERENCES BY PASSING THE BILL NOS WITH ITS VALUES AND NEW REFERENCE VALUES AS PARAMETERS */
	public void newReferenceAdjustBills(String billnos, String newrefvalues) throws InterruptedException
	{

		
		WebElement billtable=driver.findElement(By.xpath("//div[@id='id_AdjustmentBills']/div[@id='id_AccountAmt']/table[@id='AccountAmount']"));
		List<WebElement> billamtbodyrows=billtable.findElements(By.xpath("//tbody[@id='AccountAmount_body']/tr"));
		int rowno = 0;
		List<WebElement> adjbillsbodyrows;
		int docheader=0,adjamtheader=0, originalamtheader=0, balamtheader=0, natcurrheader=0;
		ArrayList<String> adjbillvalue=new ArrayList(Arrays.asList(billnos.split("\\,|-|/")));
		ArrayList<String> newrefvalue=new ArrayList(Arrays.asList(newrefvalues.split("\\,|-|/")));
		for(int i=1;i<=billamtbodyrows.size();i++)
		{
			rowno=i;
			/* IF THERE ARE MULTIPLE BILL ROWS AVAILABEL THEN CLICK ON EACH ROW  AND THEN ADJUST RESPECTIVE BILLS */
			billtable.findElement(By.xpath("//tbody[@id='AccountAmount_body']/tr["+i+"]")).click();
			Actions action=new Actions(driver);
			String newrefamt = "";
			for(int refdocno=0;refdocno<newrefvalue.size();refdocno+=3)
	 		{
	 			if(newrefvalue.get(refdocno).equals(String.valueOf(i)))
	 			{
	 				newrefamt =newrefvalue.get(refdocno+2);
	 				if(Double.parseDouble(newrefamt)==0)
	 				{
	 					break;
	 					
	 				}
	 				
	 				/* IF NEW REFERENCE PROVIDED WITH ITS RESPECTIVE VALUE, THEN ENTER THE RESPECTIVE VALUES */
	 				else
		 			{
		 				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtNewReference")));
		 				WebElement newReference=driver.findElement(By.id("txtNewReference"));
		 				action.moveToElement(newReference).build().perform();
		 				Thread.sleep(1000);
		 				action.sendKeys(Keys.CLEAR).build().perform();
		 				Thread.sleep(1000);
		 				for(int k = 0; k<newrefamt.length();k++)
						 {
		 					char ch=newrefamt.charAt(k);
		 					action.sendKeys(String.valueOf(ch));
						}
		 				action.sendKeys(Keys.TAB).build().perform();
		 				Thread.sleep(1000);
		 			}
	 			}
	 		}
	 		
			List<WebElement> billamtbodycols=billtable.findElements(By.xpath("//tbody[@id='AccountAmount_body']/tr["+i+"]/td"));
			Thread.sleep(1000);
			shortwait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[id='id_Adjustment_BillReference']")));
			List<WebElement> adjbillref=driver.findElements(By.xpath("//div[@id='id_Adjustment_BillReference']/div"));
			/* ADJUST TO RESPECTIVE BILLS WITH ITS VALUES PROVIDED  */
 	 		for(int r=0;r<adjbillvalue.size();r+=3)
 	 		{
 	 			int docno=1,amtadjust=2, adjustchckbox=2;
 	 			if(adjbillvalue.get(r).equals(String.valueOf(rowno)))
 	 			{
 	 				try
		 	 		{
			 	 		List<WebElement> adjbillsheads= driver.findElements(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/thead[@id='id_Adjustment_Grid_head']/tr[@id='id_Adjustment_Grid_row_heading']/th[not(contains(@style,'display: none'))]"));
			 	 		WebElement adjbillshead= driver.findElement(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/thead[@id='id_Adjustment_Grid_head']/tr[@id='id_Adjustment_Grid_row_heading']"));
			 	 		
			 	 		for(int header=2;header<=adjbillsheads.size();header++)
			 	 		{
			 	 			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/thead[@id='id_Adjustment_Grid_head']/tr[@id='id_Adjustment_Grid_row_heading']/th[not(contains(@style,'display: none'))]["+header+"]")));
			 	 			WebElement adjbillsheader= adjbillshead.findElement(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/thead[@id='id_Adjustment_Grid_head']/tr[@id='id_Adjustment_Grid_row_heading']/th[not(contains(@style,'display: none'))]["+header+"]"));
			 	 			String attr=adjbillsheader.getAttribute("data-sname");
			 	 			String name=adjbillsheader.getAttribute("textContent");
			 	 			if(attr.equalsIgnoreCase("sReference"))
			 	 			{
			 	 				docheader=header;
			 	 			}
			 	 			if(name.equalsIgnoreCase("Adjustment Amount"))
			 	 			{
			 	 				adjamtheader=header;
			 	 			}
			 	 			if(name.equalsIgnoreCase("OriginalAmountTC"))
			 	 			{
			 	 				originalamtheader=header;
			 	 			}
			 	 			if(name.equalsIgnoreCase("Balance Amount"))
			 	 			{
			 	 				balamtheader=header;
			 	 			}
			 	 			if(name.equalsIgnoreCase("Native Currency"))
			 	 			{
			 	 				natcurrheader=header;
			 	 			}
			 	 		}	
			 	 		List<WebElement> adjbillsbodyr=driver.findElements(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']"));
			 	 		WebElement adjbillbr=driver.findElement(By.cssSelector("div[id='id_Adjustment_Invoices'] div[id='id_Adjustment_Invoices_Grid'] table[id='id_Adjustment_Grid'] tbody[id='id_Adjustment_Grid_body']"));
			 	 		List<WebElement> childsc = adjbillbr.findElements(By.cssSelector("*"));
			 	 		if(childsc.size()>0)
				 	 	{
				 	 		adjbillsbodyrows=driver.findElements(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']/tr"));
				 	 		int div;
				 	 		if(adjbillvalue.size()>3)
				 	 		{
				 	 			div=4;
				 	 		}
				 	 		else
				 	 		{
				 	 			div=3;
				 	 		}
				 	 		for(int k=0;k<adjbillvalue.size()/div;k++)
				 	 		{
				 	 			for(int row=1;row<=adjbillsbodyrows.size();row++)
					 	 		{
					 	 			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']/tr["+row+"]/td[not(contains(@style,'display: none'))]["+docheader+"]")));
					 	 			WebElement adjbillbodydocno=driver.findElement(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']/tr["+row+"]/td[not(contains(@style,'display: none'))]["+docheader+"]"));
					 	 			String docnum=adjbillbodydocno.getText();
					 	 			
					 	 			if(adjbillvalue.get(docno+r).equalsIgnoreCase(docnum))
					 	 			{
					 	 				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']/tr["+row+"]/td[not(contains(@style,'display: none'))]["+adjamtheader+"]")));
					 	 	 			WebElement adjbillbodyamtadj=driver.findElement(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']/tr["+row+"]/td[not(contains(@style,'display: none'))]["+adjamtheader+"]"));
					 	 	 			adjbillbodyamtadj.click();
					 	 	 			String value=adjbillvalue.get(amtadjust+r);
					 	 	 			adjbillvalue.set(amtadjust+r, "");
					 	 	 			if(value.equalsIgnoreCase("0"))
					 	 	 			{
					 	 	 				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']/tr["+row+"]/td[not(contains(@style,'display: none'))]["+adjustchckbox+"]")));
					 	 	 	 			WebElement adjbillbodychckbox=driver.findElement(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']/tr["+row+"]/td[not(contains(@style,'display: none'))]["+adjustchckbox+"]"));
					 	 	 	 			adjbillbodychckbox.click();
					 	 	 	 		adjbillbodychckbox.click();
					 	 	 			}
					 	 	 			else
						 	 	 		{
					 	 	 				Actions act2=new Actions(driver);
						 	 	 			for(int l=0;l<value.length();l++)
						 	 	 			{
						 	 	 				char ch=value.charAt(l);
						 	 	 				act2.sendKeys(String.valueOf(ch));
						 	 	 				act2.build().perform();
						 	 	 				Thread.sleep((long)slowkeys);
						 	 	 			}
						 	 	 			act2.sendKeys(Keys.TAB).build().perform();
						 	 	 			
						 	 	 		}
					 	 	 			Thread.sleep(1000);
					 	 	 			break;
					 	 			}
					 	 			
					 	 		}
				 	 			
				 	 			k++;
				 	 		}
				 	 		
				 	 	}
			 	 		
			 	 }
 	 				catch(Exception ex)
 	 				{
 	 			
 	 				}
 	 		}
 	 	}
 	 		
 	 		/* IF THERE IS ONLY SINGLE BILL WHICH NEED TO BE ADJUSTED */
 	 		if(newrefamt.equalsIgnoreCase(""))
 	 		{
 	 			break;
 	 		}
 	 		if(Double.parseDouble(newrefamt)==0)
			{
 	 			Actions action2=new Actions(driver);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='txtblkAmounttobeadjust']")));
				newrefamt=driver.findElement(By.xpath("//*[@id='txtblkAmounttobeadjust']")).getText();
				wait.until(ExpectedConditions.elementToBeClickable(By.id("txtNewReference")));
				WebElement newReference=driver.findElement(By.id("txtNewReference"));
				newReference.click();
				newReference.sendKeys(Keys.CONTROL,"a",Keys.DELETE);
				Thread.sleep(1000);
				for(int k = 0; k<newrefamt.length();k++)
				{
					char ch=newrefamt.charAt(k);
					newReference.sendKeys(String.valueOf(ch));
					Thread.sleep((long)slowkeys);
				}
				action.sendKeys(Keys.TAB).build().perform();
				Thread.sleep(1000);
			}	
 	 		
	}
		
		

	}
	@Test
	
	/* METHOD TO SAVE THE TRASACTIONS BY PROVIDING THE MEHOD, BILL NOS, EXPECTED MESSAGE AND KEYWORDS AS PARAMETERS */
	public boolean transactionSave(String method,String billnos, String expmsg, ArrayList keywords) throws InterruptedException  
	  {
			try
			{
				/*IF IT IS SAVE METHOD*/
				if(method.equalsIgnoreCase("save"))
				{
					Thread.sleep(3000);
					logger.info("SAVE method is getting executed");
					try
				    {
					  /* TO VERIFY IF ANY GLOBAL ID IS DISPLAYING AND CLOSE THEM*/
					  	List<WebElement> globalidlist=driver.findElements(By.cssSelector("div[id='idGlobalError'] div, div[id='idGlobalError']"));
						if(globalidlist.size()>=1)
						  {
							WebElement popups=driver.findElement(By.xpath("//*[@id='idGlobalError']/div/table/tbody/tr/td[2]/div"));
					    	if(popups.findElement(By.xpath("//*[@id='idGlobalError']/div/table/tbody/tr/td[2]/div[2]")).isDisplayed())
					    		{
					    			logger.info("Yes displayed with i value "+" txt "+popups.getAttribute("text content"));
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
					
			 		logger.info("Message for Save method is "+actmsg);
			 		/* TO CLICK ON NEW BUTTON AFTER CLICKING ON SAVE */
			 		try
			 		{
			 		wait.until(ExpectedConditions.elementToBeClickable(By.id("id_transactionentry_new")));
				 	driver.findElement(By.id("id_transactionentry_new")).click();
			 		}
			 		catch(Exception e6)
			 		{
			 			logger.info("new click exception");
			 		}
				 	try
				 	{
				 		driver.switchTo().alert().accept();
				 	}
				 	catch(Exception e5)
				 	{
				 		
				 	}
				 	try
				 	{
					wait.until(ExpectedConditions.elementToBeClickable(By.id("id_transactionentry_close")));
					driver.findElement(By.id("id_transactionentry_close")).click();
				 	}
				 	catch(Exception e7)
				 	{
				 		logger.info("close exception");
				 	}
					try
				 	{
				 		driver.switchTo().alert().accept();
				 	}
				 	catch(Exception e)
				 	{
				 		
				 	}
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("All Vouchers")));
					 th.newClick();
					 if(actmsg.toUpperCase().contains(expmsg.toUpperCase()))
				 		{
				 			return true;
				 			
				 		}
		 			
				}
			
				/*IF IT PICK METHOD*/
				if(method.equalsIgnoreCase("Pick"))
				{
			 		logger.info("PICK method is getting executed");
			 		/* IF SAVE IS STILL DISPLAYING THEN CLICK ON IT */
			 		if(transSavePage.isDisplayed())
				 	{
				 		 try
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
				 			    			logger.info("Yes displayed with i value "+" txt "+popups.getAttribute("text content"));
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
				 			
						 	shortwait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id_transactionentry_new")));
						 	driver.findElement(By.id("id_transactionentry_new")).click();
						 	try
						 	{
						 		driver.switchTo().alert().accept();
						 	}
						 	catch(Exception e1)
						 	{
						 		
						 	} 
				 		}
				 		catch(Exception e)
						 {
						 
						 }
				 	}
			 		else
			 		{
			 		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='id_AdjustmentBills']/div")));
				 		WebElement adjbills=driver.findElement(By.xpath("//div[@id='id_AdjustmentBills']/div"));
				 		List<WebElement> adjbillscount=driver.findElements(By.xpath("//div[@id='id_AdjustmentBills']/div[not(contains(@style, 'display: none'))]"));
				 		/* IF THERE ARE MULTIPLE PENDING REFERENCES AVAIALBEL GO TO EACH REFERENCE AND CLICK ON PICK BUTTON FOR EACH ONE */
				 		if(adjbillscount.size()>2)
					 	{
				 			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='id_AdjustmentBills']/div[@id='id_AccountAmt']/table[@id='AccountAmount']")));
				 			WebElement billtable=driver.findElement(By.xpath("//div[@id='id_AdjustmentBills']/div[@id='id_AccountAmt']/table[@id='AccountAmount']"));
				 			List<WebElement> billamtbodyrows=billtable.findElements(By.xpath("//tbody[@id='AccountAmount_body']/tr"));
				 			for(int i=1;i<=billamtbodyrows.size();i++)
					 		{
					 			wait.until(ExpectedConditions.visibilityOf(billtable.findElement(By.xpath("//tbody[@id='AccountAmount_body']/tr["+i+"]"))));
					 			billtable.findElement(By.xpath("//tbody[@id='AccountAmount_body']/tr["+i+"]")).click();
					 			Thread.sleep(1000);
					 			wait.until(ExpectedConditions.elementToBeClickable(By.id("btnPick")));
						 		driver.findElement(By.id("btnPick")).click();
						 		Thread.sleep(1000);
					 		}
					 	}
				 		else
				 		{
				 			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnPick")));
					 		driver.findElement(By.id("btnPick")).click();
					 		Thread.sleep(1000);
				 		}
				 		
			}
		}
				
			/*IF IT IS ADJUST ON FIFO METHOD*/
				if(method.equalsIgnoreCase("Adjust on FIFO"))
			 	{
			 		logger.info("Adjust on FIFO method is getting executed");
			 		/* IF SAVE IS STILL DISPLAYED CLIC ON SAVE BUTTON */
			 		if(transSavePage.isDisplayed())
			 		{
	
				 		 try
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
				 			    			logger.info("Yes displayed with i value "+" txt "+popups.getAttribute("text content"));
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
				 			 
						 	shortwait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id_transactionentry_new")));
						 	driver.findElement(By.id("id_transactionentry_new")).click();
						 	try
						 	{
						 		driver.switchTo().alert().accept();
						 	}
						 	catch(Exception e1)
						 	{
						 		
						 	} 
				 		}
				 		catch(Exception e)
						 {
						 
						 }
				 	}
			 		else
			 		{
			 			shortwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='id_AdjustmentBills']/div")));
				 		WebElement adjbills=driver.findElement(By.xpath("//div[@id='id_AdjustmentBills']/div"));
				 		List<WebElement> adjbillscount=driver.findElements(By.xpath("//div[@id='id_AdjustmentBills']/div[not(contains(@style, 'display: none'))]"));
				 		/* IF THERE ARE MULTIPLE REFERENCES AVAILABLE GO TO EACH REFERENCE AND ADJUST ON FIFO FOR EACH */
				 		if(adjbillscount.size()>2)
					 	{
				 			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='id_AdjustmentBills']/div[@id='id_AccountAmt']/table[@id='AccountAmount']")));
				 			WebElement billtable=driver.findElement(By.xpath("//div[@id='id_AdjustmentBills']/div[@id='id_AccountAmt']/table[@id='AccountAmount']"));
				 			List<WebElement> billamtbodyrows=billtable.findElements(By.xpath("//tbody[@id='AccountAmount_body']/tr"));
				 			for(int i=1;i<=billamtbodyrows.size();i++)
					 		{
					 			wait.until(ExpectedConditions.visibilityOf(billtable.findElement(By.xpath("//tbody[@id='AccountAmount_body']/tr["+i+"]"))));
					 			billtable.findElement(By.xpath("//tbody[@id='AccountAmount_body']/tr["+i+"]")).click();
					 			Thread.sleep(1000);
					 			wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[value='Adjust On FIFO']")));
					 			driver.findElement(By.cssSelector("input[value='Adjust On FIFO']")).click();
					 		}
					 	}
				 		else
				 		{
				 			wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[value='Adjust On FIFO']")));
				 			driver.findElement(By.cssSelector("input[value='Adjust On FIFO']")).click();
				 		}
				 			
				 		
				 	}
			 	}
				
			/* IF IT IS ADJUST BILLS AGAINST REFERENCES METHOD */
		 	if(method.equalsIgnoreCase("Adjust Bills"))
		 	{
		 		if(transSavePage.isDisplayed())
		 		{
		 			try
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
		 				    			logger.info("Yes displayed with i value "+" txt "+popups.getAttribute("text content"));
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
			 			 
					 	shortwait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id_transactionentry_new")));
					 	driver.findElement(By.id("id_transactionentry_new")).click();
					 	try
					 	{
					 		driver.switchTo().alert().accept();
					 	}
					 	catch(Exception e1)
					 	{
					 		
					 	} 
			 		}
			 		catch(Exception e)
					 {
					 
					 }
			 	
		 			
		 		}
		 		/* MULTI CURRENCY, ADJUST BILLS WITH THE RESPECTIVE VALUES */
		 		else
		 		{
		 			shortwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='id_AdjustmentBills']/div")));
			 		 adjbills=driver.findElement(By.xpath("//div[@id='id_AdjustmentBills']/div"));
			 		List<WebElement> adjbillscount=driver.findElements(By.xpath("//div[@id='id_AdjustmentBills']/div[not(contains(@style, 'display: none'))]"));
			 		if(adjbillscount.size()>2)
			 		{
			 			adjustBillReferences(billnos);
			 		}
			 		else
			 		{
			 			ArrayList<String> adjbillvalue=new ArrayList(Arrays.asList(billnos.split("\\,|-|/")));
			 			
			 			try
				 	 	{
			 				logger.info("Adj bills method getting executed "+adjbillvalue);
				 	 		List<WebElement> adjbillsheads= driver.findElements(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/thead[@id='id_Adjustment_Grid_head']/tr[@id='id_Adjustment_Grid_row_heading']/th[not(contains(@style,'display: none'))]"));
				 	 		WebElement adjbillshead= driver.findElement(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/thead[@id='id_Adjustment_Grid_head']/tr[@id='id_Adjustment_Grid_row_heading']"));
				 	 		int docheader=0,adjamtheader=0, originalamtheader=0, balamtheader=0, natcurrheader=0;
				 	 		for(int header=2;header<=adjbillsheads.size();header++)
				 	 		{
				 	 			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/thead[@id='id_Adjustment_Grid_head']/tr[@id='id_Adjustment_Grid_row_heading']/th[not(contains(@style,'display: none'))]["+header+"]")));
				 	 			WebElement adjbillsheader= adjbillshead.findElement(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/thead[@id='id_Adjustment_Grid_head']/tr[@id='id_Adjustment_Grid_row_heading']/th[not(contains(@style,'display: none'))]["+header+"]"));
				 	 			String attr=adjbillsheader.getAttribute("data-sname");
				 	 			String name=adjbillsheader.getAttribute("textContent");
				 	 			if(attr.equalsIgnoreCase("sReference"))
				 	 			{
				 	 				docheader=header;
				 	 			}
				 	 			if(name.equalsIgnoreCase("Adjustment Amount"))
				 	 			{
				 	 				adjamtheader=header;
				 	 				
				 	 			}
				 	 			if(name.equalsIgnoreCase("OriginalAmountTC"))
				 	 			{
				 	 				originalamtheader=header;
				 	 			}
				 	 			if(name.equalsIgnoreCase("Balance Amount"))
				 	 			{
				 	 				balamtheader=header;
				 	 			}
				 	 			if(name.equalsIgnoreCase("Native Currency"))
				 	 			{
				 	 				natcurrheader=header;
				 	 			}
				 	 		}
				 	 		int docno=1,amtadjust=2, adjustchckbox=2;
				 	 		List<WebElement> adjbillsbodyrows=driver.findElements(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']/tr"));
				 	 		int div;
				 	 		if(adjbillvalue.size()>3)
				 	 		{
				 	 			div=3;
				 	 		}
				 	 		else
				 	 		{
				 	 			div=3;
				 	 		}
				 	 		for(int k=0;k<adjbillvalue.size()/div;k++)
					 	 	{
				 	 			for(int row=1;row<=adjbillsbodyrows.size();row++)
					 	 		{
					 	 			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']/tr["+row+"]/td[not(contains(@style,'display: none'))]["+docheader+"]")));
					 	 			WebElement adjbillbodydocno=driver.findElement(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']/tr["+row+"]/td[not(contains(@style,'display: none'))]["+docheader+"]"));
					 	 			String docnum=adjbillbodydocno.getAttribute("textContent");
					 	 			if(adjbillvalue.get(docno).equalsIgnoreCase(docnum))
					 	 			{
					 	 				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']/tr["+row+"]/td[not(contains(@style,'display: none'))]["+adjamtheader+"]")));
					 	 	 			WebElement adjbillbodyamtadj=driver.findElement(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']/tr["+row+"]/td[not(contains(@style,'display: none'))]["+adjamtheader+"]"));
					 	 	 			logger.info("2 adjamtheader "+adjamtheader);
					 	 	 			adjbillbodyamtadj.click();
					 	 	 			String value=adjbillvalue.get(amtadjust);
					 	 	 			if(value.equalsIgnoreCase("0"))
					 	 	 			{
					 	 	 				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']/tr["+row+"]/td[not(contains(@style,'display: none'))]["+adjustchckbox+"]")));
					 	 	 	 			WebElement adjbillbodychckbox=driver.findElement(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']/tr["+row+"]/td[not(contains(@style,'display: none'))]["+adjustchckbox+"]"));
					 	 	 	 			adjbillbodychckbox.click();
					 	 	 			}
					 	 	 			else
						 	 	 		{
					 	 	 				Actions act=new Actions(driver);
						 	 	 			for(int i=0;i<value.length();i++)
						 	 	 			{
						 	 	 				char ch=value.charAt(i);
						 	 	 				act.sendKeys(String.valueOf(ch));
						 	 	 				act.build().perform();
						 	 	 				Thread.sleep((long)slowkeys);
						 	 	 			}
						 	 	 			act.sendKeys(Keys.TAB).build().perform();
						 	 	 			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']/tr["+row+"]/td[not(contains(@style,'display: none'))]["+balamtheader+"]")));
						 	 	 			WebElement balamt=driver.findElement(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']/tr["+row+"]/td[not(contains(@style,'display: none'))]["+balamtheader+"]"));
						 	 	 			String balamtvalue=balamt.getText();
						 	 	 		}
					 	 	 			Thread.sleep(1000);
					 	 	 			docno+=3;
					 	 	 			amtadjust+=3;
					 	 	 			break;
					 	 			}
					 	 		}
					 	 			
					 	 	}
				 	 	}
			 	 		catch(Exception e)
			 	 		{
			 	 			logger.info("Total Results "+totresults);
			 	 		}
			 	}
			 		
			 	}
				
			 }
		 	
		 	/* IF IT IS NEW REFERENCE & ADJUST AGAINST REFERENCES METHOD */
			if(method.equals("New Reference,Adjust Bills"))
			{
				logger.info("New Reference,Adjust Bills method is getting executed");
				//SYNTAX FOR BILL NOS: "1-New Reference,20,1-IBF:75,10";
				/*MULTIPLE SPLIT CRITERIA*/
				ArrayList<String> splitbills=new ArrayList(Arrays.asList(billnos.split("\\,|-|/")));
				ArrayList<Integer> refnosIndices=new ArrayList();
				ArrayList<String> refnos=new ArrayList();
				refnosIndices.clear();
				refnos.clear();
				for(int i=0;i<splitbills.size();i++)
				{
					if(splitbills.get(i).equalsIgnoreCase("New Reference"))
					{
						refnosIndices.add(i-1);
						refnosIndices.add(i);
						refnosIndices.add(i+1);
					}
				}
				for(int i=0;i<refnosIndices.size();i++)
				{
					refnos.add(splitbills.get(refnosIndices.get(i)));
				}
				for(int i=0;i<refnosIndices.size();i++)
				{
					splitbills.set(refnosIndices.get(i), null);
				}
				splitbills.removeAll(Arrays.asList(null, " "));
				String newrefamt = "0";
				Double newrefvalue=Double.parseDouble(newrefamt);
				shortwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='id_AdjustmentBills']/div")));
		 		 adjbills=driver.findElement(By.xpath("//div[@id='id_AdjustmentBills']/div"));
		 		List<WebElement> adjbillscount=driver.findElements(By.xpath("//div[@id='id_AdjustmentBills']/div[not(contains(@style, 'display: none'))]"));
		 		if(adjbillscount.size()>2)
		 		{
		 			String bills ="";
		 			for(String s: splitbills)
		 			{
		 				bills+=s+",";
		 			}
		 			String refvalues = "";
		 			for(String s:refnos)
		 			{
		 				refvalues+=s+",";
		 			}
		 			
		 			newReferenceAdjustBills(bills, refvalues);
		 		}
		 		else
		 		{
		 			logger.info("New ref adj bills else loop getting executed");
		 			try
			 	 	{
			 	 		List<WebElement> adjbillsheads= driver.findElements(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/thead[@id='id_Adjustment_Grid_head']/tr[@id='id_Adjustment_Grid_row_heading']/th[not(contains(@style,'display: none'))]"));
			 	 		WebElement adjbillshead= driver.findElement(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/thead[@id='id_Adjustment_Grid_head']/tr[@id='id_Adjustment_Grid_row_heading']"));
			 	 		int docheader=0,adjamtheader=0, originalamtheader=0, balamtheader=0, natcurrheader=0;
			 	 		for(int header=2;header<=adjbillsheads.size();header++)
			 	 		{
			 	 			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/thead[@id='id_Adjustment_Grid_head']/tr[@id='id_Adjustment_Grid_row_heading']/th[not(contains(@style,'display: none'))]["+header+"]")));
			 	 			WebElement adjbillsheader= adjbillshead.findElement(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/thead[@id='id_Adjustment_Grid_head']/tr[@id='id_Adjustment_Grid_row_heading']/th[not(contains(@style,'display: none'))]["+header+"]"));
			 	 			String attr=adjbillsheader.getAttribute("data-sname");
			 	 			String name=adjbillsheader.getAttribute("textContent");
			 	 			logger.info("name "+name+" attr "+attr);
			 	 			if(attr.equalsIgnoreCase("sReference"))
			 	 			{
			 	 				docheader=header;
			 	 			}
			 	 			if(name.equalsIgnoreCase("Adjustment Amount"))
			 	 			{
			 	 				adjamtheader=header;
			 	 				logger.info("Adj amt header "+adjamtheader);
			 	 			}
			 	 			if(name.equalsIgnoreCase("OriginalAmountTC"))
			 	 			{
			 	 				originalamtheader=header;
			 	 			}
			 	 			if(name.equalsIgnoreCase("Balance Amount"))
			 	 			{
			 	 				balamtheader=header;
			 	 			}
			 	 			if(name.equalsIgnoreCase("Native Currency"))
			 	 			{
			 	 				natcurrheader=header;
			 	 			}
			 	 		}
			 	 		int docno=1,amtadjust=2, adjustchckbox=2;
			 	 		List<WebElement> adjbillsbodyrows=driver.findElements(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']/tr"));
			 	 		int div;
			 	 		if(splitbills.size()>3)
			 	 		{
			 	 			div=4;
			 	 		}
			 	 		else
			 	 		{
			 	 			div=3;
			 	 		}
			 	 		for(int k=0;k<splitbills.size()/div;k++)
				 	 	{
				 	 		for(int row=1;row<=adjbillsbodyrows.size();row++)
				 	 		{
				 	 			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']/tr["+row+"]/td[not(contains(@style,'display: none'))]["+docheader+"]")));
				 	 			WebElement adjbillbodydocno=driver.findElement(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']/tr["+row+"]/td[not(contains(@style,'display: none'))]["+docheader+"]"));
				 	 			String docnum=adjbillbodydocno.getText();
				 	 			if(splitbills.get(docno).equalsIgnoreCase(docnum))
				 	 			{
				 	 				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']/tr["+row+"]/td[not(contains(@style,'display: none'))]["+adjamtheader+"]")));
				 	 	 			WebElement adjbillbodyamtadj=driver.findElement(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']/tr["+row+"]/td[not(contains(@style,'display: none'))]["+adjamtheader+"]"));
				 	 	 			adjbillbodyamtadj.click();
				 	 	 			String value=splitbills.get(amtadjust);
				 	 	 			if(value.equalsIgnoreCase("0"))
				 	 	 			{
				 	 	 				logger.info("adjbillbodychckbox "+adjustchckbox);
				 	 	 				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']/tr["+row+"]/td[not(contains(@style,'display: none'))]["+adjustchckbox+"]")));
				 	 	 	 			Thread.sleep(1000);
				 	 	 				WebElement adjbillbodychckbox=driver.findElement(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']/tr["+row+"]/td[not(contains(@style,'display: none'))]["+adjustchckbox+"]"));
				 	 	 	 			adjbillbodychckbox.click();
				 	 	 	 			adjbillbodychckbox.click();
				 	 	 			}
				 	 	 			else
					 	 	 		{
				 	 	 				Actions act=new Actions(driver);
					 	 	 			for(int i=0;i<value.length();i++)
					 	 	 			{
					 	 	 				char ch=value.charAt(i);
					 	 	 				act.sendKeys(String.valueOf(ch));
					 	 	 				act.build().perform();
					 	 	 				Thread.sleep((long)slowkeys);
					 	 	 			}
					 	 	 			act.sendKeys(Keys.TAB).build().perform();
					 	 	 			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']/tr["+row+"]/td[not(contains(@style,'display: none'))]["+balamtheader+"]")));
					 	 	 			WebElement balamt=driver.findElement(By.xpath("//div[@id='id_Adjustment_Invoices']/div[@id='id_Adjustment_Invoices_Grid']/table[@id='id_Adjustment_Grid']/tbody[@id='id_Adjustment_Grid_body']/tr["+row+"]/td[not(contains(@style,'display: none'))]["+balamtheader+"]"));
					 	 	 			String balamtvalue=balamt.getText();
					 	 	 			logger.info("After entering Balance amount of row  "+row+" is "+balamtvalue);
					 	 	 		}
				 	 	 			Thread.sleep(1000);
				 	 	 			docno++;docno++;
				 	 	 			amtadjust++;amtadjust++;
				 	 	 			break;
				 	 			}
				 	 		}
				 	 			k++;
				 	 	}
			 	 	}
		 	 		catch(Exception e)
		 	 		{
		 	 			logger.info("Total Results "+totresults);
		 	 		}
		 	}
		 		
		 	
			
			}
		 
		 
		 	/* IF IT IS NEW REFERENCE METHOD */
		 	 if(method.equalsIgnoreCase("New Reference"))
		 	 {
		 		 logger.info("New Reference method is getting executed");
		 		 if(transSavePage.isDisplayed())
		 		 {
		 			 try
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
		 				    			logger.info("Yes displayed with i value "+" txt "+popups.getAttribute("text content"));
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
		 				 
					 	shortwait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id_transactionentry_new")));
					 	driver.findElement(By.id("id_transactionentry_new")).click();
					 	try
					 	{
					 		driver.switchTo().alert().accept();
					 	}
					 	catch(Exception e1)
					 	{
					 		
					 	} 
			 		}
			 		catch(Exception e)
					 {
					 
					 }
			 	 
		 		 }
		 		 else
		 		 {
		 			String balanceamt = null;
		 			shortwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='id_AdjustmentBills']/div")));
			 		WebElement adjbills=driver.findElement(By.xpath("//div[@id='id_AdjustmentBills']/div"));
			 		List<WebElement> adjbillscount=driver.findElements(By.xpath("//div[@id='id_AdjustmentBills']/div[not(contains(@style, 'display: none'))]"));
			 		if(adjbillscount.size()>2)
				 	{
			 			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='id_AdjustmentBills']/div[@id='id_AccountAmt']/table[@id='AccountAmount']")));
			 			WebElement billtable=driver.findElement(By.xpath("//div[@id='id_AdjustmentBills']/div[@id='id_AccountAmt']/table[@id='AccountAmount']"));
			 			List<WebElement> billamtbodyrows=billtable.findElements(By.xpath("//tbody[@id='AccountAmount_body']/tr"));
			 			for(int i=1;i<=billamtbodyrows.size();i++)
				 		{
				 			wait.until(ExpectedConditions.visibilityOf(billtable.findElement(By.xpath("//tbody[@id='AccountAmount_body']/tr["+i+"]"))));
				 			billtable.findElement(By.xpath("//tbody[@id='AccountAmount_body']/tr["+i+"]")).click();
				 			Thread.sleep(1000);
				 			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='txtblkAmounttobeadjust']")));
				 			balanceamt=driver.findElement(By.xpath("//*[@id='txtblkAmounttobeadjust']")).getText();
				 			Thread.sleep(1000);
				 			wait.until(ExpectedConditions.elementToBeClickable(By.id("txtNewReference")));
				 			WebElement newReference=driver.findElement(By.id("txtNewReference"));
				 			newReference.click();
				 			newReference.sendKeys(Keys.CONTROL,"a", Keys.DELETE);
				 			Thread.sleep(1000);
							 for(int c = 0; c<balanceamt.length();c++)
							 {
								  char ch=balanceamt.charAt(c);
								  newReference.sendKeys(String.valueOf(ch));
								  Thread.sleep((long)slowkeys);
							 }
						 Thread.sleep(1000);
		 			}
		 			
			 		}
			 		else
			 		{
			 			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='txtblkAmounttobeadjust']")));
			 			balanceamt=driver.findElement(By.xpath("//*[@id='txtblkAmounttobeadjust']")).getText();
			 			Thread.sleep(1000);
			 			wait.until(ExpectedConditions.elementToBeClickable(By.id("txtNewReference")));
			 			WebElement newReference=driver.findElement(By.id("txtNewReference"));
			 			newReference.click();
			 			newReference.sendKeys(Keys.CONTROL,"a", Keys.DELETE);
			 			Thread.sleep(1000);
						 for(int c = 0; c<balanceamt.length();c++)
						 {
							  char ch=balanceamt.charAt(c);
							  newReference.sendKeys(String.valueOf(ch));
							  Thread.sleep((long)slowkeys);
						 }
					 Thread.sleep(1000);
					
			 		}
			 	}
		 	 
				
			 }
			}
		 catch(Exception e)
		 {
			 logger.info("final exception");
			 try
			 {
				 driver.switchTo().alert().accept();
				 
			 }
			 catch(Exception e2)
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
					    			logger.info("Yes displayed with i value "+" txt "+popups.getAttribute("text content"));
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
				 
		 		logger.info("final catch actmsg "+actmsg);
			 		
			 }
			 if(actmsg.toUpperCase().contains(expmsg.toUpperCase()))
		 	 {
				 return true;
		 	 		 
		 	 }
			 try
			 {
			 	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id_transactionentry_new")));
			 	driver.findElement(By.id("id_transactionentry_new")).click();
			 	
			 }
			 catch(Exception e1)
			 {
				 wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/section/div[2]/div/section[1]/div[2]/div/div[1]/div/nav/div[2]/ul/li[3]/div/div[1]")));
			 	 driver.findElement(By.xpath("/html/body/section/div[2]/div/section[1]/div[2]/div/div[1]/div/nav/div[2]/ul/li[3]/div/div[1]")).click();
			 	 wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id_transactionentry_new")));
				 driver.findElement(By.id("id_transactionentry_new")).click();
			 	 try
			 	 {
			 		 driver.switchTo().alert().accept();
			 		 Thread.sleep(1000);
			 	 }
			 	 catch(Exception e2)
			 	 {
			 		
			 	 }
			 }
			 	
		 }
	 	
		/* CLOSING TRASACTION PAGE */
			
		finally 
	 	{
			boolean result;
			if(keywords.contains("BILLCALCULATIONS"))
			{
				//DO NOTHING IF KEYWORDS CONTAINS BILLCALCULATIONS
			}
			else
			{
				if(!(method.equalsIgnoreCase("save")))
				{
					result = finalOkClick(expmsg);
					return result;
				}
			}
	 	}
			return false;
		
	  }
	public boolean finalOkClick(String expmsg)  throws InterruptedException
	{
	 if(!(transSavePage.isDisplayed()))
		{
		wait.until(ExpectedConditions.elementToBeClickable(By.id("btnOk")));
		 driver.findElement(By.id("btnOk")).click();
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
		
	 	/*CLICKING ON CLOSE BUTTON OF BILLWISE POPUP, IF IT DIDNT GET SAVED*/
	 	if(!(transSavePage.isDisplayed()))
			{
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/section/div[2]/div/section[1]/div[2]/div/div[1]/div/nav/div[2]/ul/li[3]/div")));
				driver.findElement(By.xpath("/html/body/section/div[2]/div/section[1]/div[2]/div/div[1]/div/nav/div[2]/ul/li[3]/div")).click();
				
			}
		}
		
	 	wait.until(ExpectedConditions.elementToBeClickable(By.id("id_transactionentry_new")));
	 	driver.findElement(By.id("id_transactionentry_new")).click();
	 	try
	 	{
	 		driver.switchTo().alert().accept();
	 	}
	 	catch(Exception e5)
	 	{
	 		
	 	}
	 	try
	 	{
		wait.until(ExpectedConditions.elementToBeClickable(By.id("id_transactionentry_close")));
		driver.findElement(By.id("id_transactionentry_close")).click();
	 	}
	 	catch(Exception e2)
	 	{
	 		
	 	}
		try
	 	{
	 		driver.switchTo().alert().accept();
	 	}
	 	catch(Exception e)
	 	{
	 		
	 	}
		try
		{
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("All Vouchers")));
		 th.newClick();
		}
		catch(Exception e3)
		{
			
		}
		 if(actmsg.toUpperCase().contains(expmsg.toUpperCase()))
		 	{
		 		return true;
		 	}
		 return false;
		
  }
	/* METHOD TO CLOSE TRANSACTIONS */
  	public boolean transactionClose()
  	{
  		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id_transactionentry_close")));
  		driver.findElement(By.id("id_transactionentry_close")).click();
  		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='dvHomeTransClose']")));
  		driver.findElement(By.xpath("//*[@id='dvHomeTransClose']")).click();
  		if(driver.findElement(By.xpath("//*[@id='dashName']")).isDisplayed())
  		{
  			return true;
  		} 
  		
	  return false;
  }
}
