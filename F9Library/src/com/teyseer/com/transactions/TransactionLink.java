package com.teyseer.com.transactions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;import org.junit.rules.ExpectedException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import com.focus.constants.LaunchApplication;

public class TransactionLink extends LaunchApplication
{
  //@Test
  public void transactionLinkLoad(String documentno) throws InterruptedException 
  {
	
	  List<WebElement> linkheaders= driver.findElements(By.xpath("//div[@id='id_transactionentry_workflow_popup_modalcontent']/div[@id='id_transactionentry_workflow_popup_body']/div[@id='id_transaction_workflow_popup_detail']/div[@id='id_transaction_workflow_popup_detail_area_grid']/table[@id='id_transaction_entry_detail_workflow_popup']/thead[@id='id_transaction_entry_detail_workflow_popup_head']/tr[@id='id_transaction_entry_detail_workflow_popup_row_heading']/th[not(contains(@style, 'display: none'))]"));
	  int vouchernocol=0;
	  for(int header=1;header<=linkheaders.size();header++)
		{
		  	wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='id_transactionentry_workflow_popup_modalcontent']/div[@id='id_transactionentry_workflow_popup_body']/div[@id='id_transaction_workflow_popup_detail']/div[@id='id_transaction_workflow_popup_detail_area_grid']/table[@id='id_transaction_entry_detail_workflow_popup']/thead[@id='id_transaction_entry_detail_workflow_popup_head']/tr[@id='id_transaction_entry_detail_workflow_popup_row_heading']/th[not(contains(@style, 'display: none'))]["+header+"]")));
			WebElement linkheadernames=driver.findElement(By.xpath("//div[@id='id_transactionentry_workflow_popup_modalcontent']/div[@id='id_transactionentry_workflow_popup_body']/div[@id='id_transaction_workflow_popup_detail']/div[@id='id_transaction_workflow_popup_detail_area_grid']/table[@id='id_transaction_entry_detail_workflow_popup']/thead[@id='id_transaction_entry_detail_workflow_popup_head']/tr[@id='id_transaction_entry_detail_workflow_popup_row_heading']/th[not(contains(@style, 'display: none'))]["+header+"]"));
			String linkheadername=linkheadernames.getText();
			if(linkheadername.equalsIgnoreCase("Voucher No"))
			{
				vouchernocol=header;
				break;
			}
		}
	  ArrayList<String> linkelems=new ArrayList<String>();
	  linkelems.clear();
	  ArrayList<String> linkelem=new ArrayList<String>();
	  List<WebElement> linkbodys=driver.findElements(By.xpath("//div[@id='id_transactionentry_workflow_popup_modalcontent']/div[@id='id_transactionentry_workflow_popup_body']/div[@id='id_transaction_workflow_popup_detail']/div[@id='id_transaction_workflow_popup_detail_area_grid']/table[@id='id_transaction_entry_detail_workflow_popup']/tbody[@id='id_transaction_entry_detail_workflow_popup_body']/tr"));
	  for(int row=1;row<=linkbodys.size();row++)
	  {
		  WebElement linkbodyrow=driver.findElement(By.xpath("//div[@id='id_transactionentry_workflow_popup_modalcontent']/div[@id='id_transactionentry_workflow_popup_body']/div[@id='id_transaction_workflow_popup_detail']/div[@id='id_transaction_workflow_popup_detail_area_grid']/table[@id='id_transaction_entry_detail_workflow_popup']/tbody[@id='id_transaction_entry_detail_workflow_popup_body']/tr["+row+"]/td[not(contains(@style, 'display: none'))]["+vouchernocol+"]"));
		  
		  String linkbodyelem=linkbodyrow.getText();
		  linkelem=new ArrayList<String>(Arrays.asList(linkbodyelem.split(":")));
		  String splitelem= linkelem.get(1);
		  linkelems.add(splitelem);
		  /*
		  if(documentno.equalsIgnoreCase(linkelem.get(1)))
		  {
			  shortwait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='id_transactionentry_workflow_popup_modalcontent']/div[@id='id_transactionentry_workflow_popup_body']/div[@id='id_transaction_workflow_popup_detail']/div[@id='id_transaction_workflow_popup_detail_area_grid']/table[@id='id_transaction_entry_detail_workflow_popup']/tbody[@id='id_transaction_entry_detail_workflow_popup_body']/tr[\"+row+\"]/td[not(contains(@style, 'display: none'))][2]")));
			  driver.findElement(By.xpath("//div[@id='id_transactionentry_workflow_popup_modalcontent']/div[@id='id_transactionentry_workflow_popup_body']/div[@id='id_transaction_workflow_popup_detail']/div[@id='id_transaction_workflow_popup_detail_area_grid']/table[@id='id_transaction_entry_detail_workflow_popup']/tbody[@id='id_transaction_entry_detail_workflow_popup_body']/tr["+row+"]/td[not(contains(@style, 'display: none'))][2]")).click();
			  break;
		  }
		  */
		  
	  }
	  System.out.println("linkelems "+linkelems);
	  if(linkelems.contains(documentno))
	  {
		 int elemrow= linkelems.indexOf(documentno)+1;
		 System.out.println("row is "+elemrow);
		 shortwait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='id_transactionentry_workflow_popup_modalcontent']/div[@id='id_transactionentry_workflow_popup_body']/div[@id='id_transaction_workflow_popup_detail']/div[@id='id_transaction_workflow_popup_detail_area_grid']/table[@id='id_transaction_entry_detail_workflow_popup']/tbody[@id='id_transaction_entry_detail_workflow_popup_body']/tr["+elemrow+"]/td[not(contains(@style, 'display: none'))][2]")));
		 driver.findElement(By.xpath("//div[@id='id_transactionentry_workflow_popup_modalcontent']/div[@id='id_transactionentry_workflow_popup_body']/div[@id='id_transaction_workflow_popup_detail']/div[@id='id_transaction_workflow_popup_detail_area_grid']/table[@id='id_transaction_entry_detail_workflow_popup']/tbody[@id='id_transaction_entry_detail_workflow_popup_body']/tr["+elemrow+"]/td[not(contains(@style, 'display: none'))][2]")).click();
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/section/div[2]/div/section[1]/div[1]/div/div[2]/div[2]/div/div/div/div/div/div[2]/div[1]/div/ul/li[3]/div/div[2]")));
		 driver.findElement(By.xpath("/html/body/section/div[2]/div/section[1]/div[1]/div/div[2]/div[2]/div/div/div/div/div/div[2]/div[1]/div/ul/li[3]/div/div[2]")).click(); 
		 Thread.sleep(1000);
		 //break;
	  }
	  else
	  {
		  	System.out.println("executing final close of links");
		  	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id_transactionentry_workflow_popup_close")));
			driver.findElement(By.id("id_transactionentry_workflow_popup_close")).click();
			Thread.sleep(1000);
	  }
	  
  }
}
