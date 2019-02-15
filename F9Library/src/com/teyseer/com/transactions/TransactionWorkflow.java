package com.teyseer.com.transactions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import com.focus.constants.HomePage;
import com.focus.constants.LaunchApplication;
import com.focus.constants.Menus;

public class TransactionWorkflow extends LaunchApplication {
	HomePage hp = new HomePage();
	Menus menu= new Menus();
	TransactionHeader th= new TransactionHeader();
	/*
	@Test
	public void newClick() throws InterruptedException 
	  {
		  	String workflow = null, voucherno = null;
			hp.LoginApp("su", "focus", "TEYSEER MOTORS CO W.L.L. [0B0]");
			menu.menuSelection("Financials", "Transactions", "Purchases","Purchases Returns", "//*[@id='dv_sVoucherName']", "purchases returns");
			//menu.menuSelection("Inventory", "Stores", "Store Receipt Voucher","//*[@id='dv_sVoucherName']", "Store Receipt Voucher");
			//menu.menuSelection("Home", "Masters_old", "Product","Product","//*[@id=\"spnHeaderText\"]", "product");
			
			th.newClick();
			workflow("Supplier/Insurance Claim","88");
	  }
	
  */
  public void workflow(String workflowname, String vouchernum) throws InterruptedException 
  {
	  
	  ArrayList<String> vouchernos=new ArrayList(Arrays.asList(vouchernum.split(",")));
	  try
	  {
	  wait.until(ExpectedConditions.elementToBeClickable(By.id("id_transaction_entry_workflow_togglebutton")));
	  driver.findElement(By.id("id_transaction_entry_workflow_togglebutton")).click();
	  
	  List<WebElement> wrkflow=driver.findElements(By.cssSelector("div[id='id_transactionentry_workflow'] select[id='id_transactionentry_workflow_dropdown']"));
	  WebElement wrkflowdropdown=driver.findElement(By.cssSelector("div[id='id_transactionentry_workflow'] select[id='id_transactionentry_workflow_dropdown']"));
	  Select dropdown= new Select(wrkflowdropdown);
	  List<WebElement> list = dropdown.getOptions();
	  for(int li=0;li<wrkflow.size();li++)
	  {
		  String listvalue=list.get(li).getText();
		  if(listvalue.toUpperCase().equals(workflowname.toUpperCase()))
		  {
			 
			  list.get(li).click();
		  }
	  }
	 int vouchernocol=0, chckbox=2;
	 List<WebElement> wrkflowheaders= driver.findElements(By.xpath("//div[@id='id_transaction_entry_workflow_detail_area_grid']/table[@id='id_transaction_entry_detail_workflow']/thead[@id='id_transaction_entry_detail_workflow_head']/tr[@id='id_transaction_entry_detail_workflow_row_heading']/th[not(contains(@style,'display: none'))]"));
	 for(int header=2;header<=wrkflowheaders.size();header++)
	 {
		WebElement wrkflowheader=driver.findElement(By.xpath("//div[@id='id_transaction_entry_workflow_detail_area_grid']/table[@id='id_transaction_entry_detail_workflow']/thead[@id='id_transaction_entry_detail_workflow_head']/tr[@id='id_transaction_entry_detail_workflow_row_heading']/th[not(contains(@style,'display: none'))]["+header+"]"));
		String headername=wrkflowheader.getText();
		if(headername.equalsIgnoreCase("Voucher No"))
		{
			vouchernocol=header;
		}
	 }
	 List<WebElement> wrkflowbodys=driver.findElements(By.xpath("//div[@id='id_transaction_entry_workflow_detail_area_grid']/table[@id='id_transaction_entry_detail_workflow']/tbody[@id='id_transaction_entry_detail_workflow_body']/tr"));
	 for(String vouchno: vouchernos )
	 {
		 for(int row=1;row<=wrkflowbodys.size();row++)
		 {
			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='id_transaction_entry_workflow_detail_area_grid']/table[@id='id_transaction_entry_detail_workflow']/tbody[@id='id_transaction_entry_detail_workflow_body']/tr["+row+"]/td[not(contains(@style,'display: none'))]["+vouchernocol+"]")));
			 WebElement wrkflowbodyrows=driver.findElement(By.xpath("//div[@id='id_transaction_entry_workflow_detail_area_grid']/table[@id='id_transaction_entry_detail_workflow']/tbody[@id='id_transaction_entry_detail_workflow_body']/tr["+row+"]/td[not(contains(@style,'display: none'))]["+vouchernocol+"]"));
			 String wrkflowbodyvouchernos=wrkflowbodyrows.getAttribute("data-value");
			 ArrayList<String> wrkflowbodyvoucherno=new ArrayList<String>(Arrays.asList(wrkflowbodyvouchernos.split(":")));
			 if(vouchno.equalsIgnoreCase(wrkflowbodyvoucherno.get(1)))
			 {
				 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='id_transaction_entry_workflow_detail_area_grid']/table[@id='id_transaction_entry_detail_workflow']/tbody[@id='id_transaction_entry_detail_workflow_body']/tr["+row+"]/td[not(contains(@style,'display: none'))]["+chckbox+"]")));
				 driver.findElement(By.xpath("//div[@id='id_transaction_entry_workflow_detail_area_grid']/table[@id='id_transaction_entry_detail_workflow']/tbody[@id='id_transaction_entry_detail_workflow_body']/tr["+row+"]/td[not(contains(@style,'display: none'))]["+chckbox+"]")).click();
			 }
		 }
	 }
  
 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/section/div[2]/div/section[1]/div[1]/div/div[2]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[2]/table/tbody/tr/td[5]/div/div[2]")));
 driver.findElement(By.xpath("/html/body/section/div[2]/div/section[1]/div[1]/div/div[2]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[2]/table/tbody/tr/td[5]/div/div[2]")).click();
 wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id_transaction_entry_workflow_togglebutton")));
 String wrkflwtoggle=driver.findElement(By.id("id_transaction_entry_workflow_togglebutton")).getAttribute("class");
 if(wrkflwtoggle.endsWith("icon-collepse"))
 {
	 driver.findElement(By.id("id_transaction_entry_workflow_togglebutton")).click();
	 
 }
 wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='id_transactionentry_body_section']/div[4]/div[1]/table[@id='id_transaction_entry_detail_table']/thead[@id='id_transaction_entry_detail_table_head']/tr[@id='id_transaction_entry_detail_table_row_heading']/th[1]")));
 
}
  catch(Exception e)
  {
	  
  }
	  
	  Thread.sleep(3000);
}
}
