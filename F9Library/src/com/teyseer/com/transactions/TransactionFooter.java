package com.teyseer.com.transactions;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import com.focus.constants.HomePage;
import com.focus.constants.LaunchApplication;
import com.focus.constants.Menus;
import com.focus.library.MasterHomePage;

public class TransactionFooter extends LaunchApplication 
{
	HomePage hp = new HomePage();
	Menus menu= new Menus();
	MasterHomePage mhp = new MasterHomePage();
	public ArrayList<String> footerlabels= new ArrayList<String>();
	ArrayList<String> footerids= new ArrayList<String>();
	ArrayList<String> unmodifiedfooterlabels= new ArrayList<String>();
	WebElement table ;
	
  public void getLabelNames() throws InterruptedException 
  {
	try
	{
	  
	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.col-xs-2:nth-child(3) > div:nth-child(1) > span:nth-child(2)")));
	  WebElement footerexpand=driver.findElement(By.cssSelector("div.col-xs-2:nth-child(3) > div:nth-child(1) > span:nth-child(2)"));
	  String footerexpcoll=footerexpand.getAttribute("class");
	  
	  //System.out.println(" last exp footerexpcoll "+footerexpcoll);
	  if(!footerexpcoll.equalsIgnoreCase("col-xs-6 icon-expand icon-font6 no_padding_left_right theme_color-inverse"))
	  {
		  footerexpand.click();
	  }
	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#id_transactionentry_footer > div:nth-child(2) > div:nth-child(1) > span:nth-child(2)")));
	  WebElement footermenuexpand=driver.findElement(By.cssSelector("#id_transactionentry_footer > div:nth-child(2) > div:nth-child(1) > span:nth-child(2)"));
	  String footermenuexpcoll=footermenuexpand.getAttribute("class");
	  //System.out.println(" footermenuexpcoll is "+footermenuexpcoll);
	  if(footermenuexpcoll.equalsIgnoreCase("col-xs-6 icon-font6 no_padding_left_right theme_color-inverse icon-expand"))
	  {
		  footermenuexpand.click();
	  }
	  List<WebElement> litable = driver.findElements(By.xpath("//div[@id='id_transactionentry_footer']/div[@id='id_transactionentry_footer_panel_entry']/div"));
	  int footerrows=litable.size();
	  String name;
	  String id;
	  table = driver.findElement(By.xpath("//div[@id='id_transactionentry_footer']/div[@id='id_transactionentry_footer_panel_entry']"));
	  footerlabels.clear();
	  footerids.clear();
	  for(int row=1;row<=footerrows;row++)
	  {
		  List<WebElement> cols=table.findElements(By.cssSelector(" div[class='row']:nth-child("+row+") div"));
		  int footercols;
		  footercols=cols.size();
		  if(footercols>4)
		  {
			  footercols=4;
		  }
		  //System.out.println("col size "+footercols);
		  for(int label=1;label<footercols;label++)
		  {
			  //System.out.println(footercols+" LABEL AND ROW ARE "+label+" : "+row);
			  WebElement labelname;
		  int label2=label+1;
			  try {
				 // System.out.println("try");
			  wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(" div[class='row']:nth-child("+row+") div[class^='col-vsm-12']:nth-child("+label+") label[class='Flabel'], div[class='row']:nth-child("+row+") div[class^='col-vsm-12']:nth-child("+label2+") label[class='Flabel']")));
			  labelname=table.findElement(By.cssSelector(" div[class='row']:nth-child("+row+") div[class^='col-vsm-12']:nth-child("+label+") label[class='Flabel'],div[class='row']:nth-child("+row+") div[class^='col-vsm-12']:nth-child("+label2+") label[class='Flabel']"));
			  //System.out.println("try end");
			  }
			  catch(Exception e)
			  { //System.out.println("catch");
			  label++;
				  wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(" div[class='row']:nth-child("+row+") div[class^='col-vsm-12']:nth-child("+label2+") label[class='Flabel']")));
				  labelname=table.findElement(By.cssSelector(" div[class='row']:nth-child("+row+") div[class^='col-vsm-12']:nth-child("+label2+") label[class='Flabel']"));  
				  //System.out.println("catch end");
				 // break;
			  }
		// System.out.println("finally");
		  Thread.sleep(1000);
		  name=labelname.getText();
		  id=labelname.getAttribute("for");
		  //System.out.println("name "+name);
		 // System.out.println(" id "+id);
		  footerlabels.add(name);
		  footerids.add(id);
		  label++;
		  
		  }
		  unmodifiedfooterlabels.clear();
		  unmodifiedfooterlabels.addAll(footerlabels);
	  }
	}
	catch(Exception ex)
	{
		System.out.println("No footer is available");
	}
  }
  
  public void transactionFooter(ArrayList<String> excelattributes, ArrayList<String> unmodifiedexcelattributes,ArrayList<String> excelvales) throws InterruptedException
  {
	  ArrayList<String> excelattribs=new ArrayList<String>();
	  ArrayList<String> values=new ArrayList<String>();
	  ArrayList<String> unmodifiableexcelattribs= new ArrayList<String>();
	  unmodifiableexcelattribs.clear();
	  unmodifiableexcelattribs.addAll(unmodifiedexcelattributes);
	  excelattribs=excelattributes;
	  values=excelvales;
	  double slowkeys=10;
	  try
	  {
	  table.click();
	  excelattribs.addAll(unmodifiedfooterlabels);
	  Collections.reverse(excelattribs);
	  
	  voucherlist:
	  for (String voucherattribute : unmodifiedfooterlabels) 
		{
		  excelattribs.remove(excelattribs.size()-1);
			excellist:
			//use unmodifiable excelattribs list in case of to click only excelattributes
			for (String excelattribute : excelattribs) 
	        {
				ifloop:
				if(voucherattribute.equalsIgnoreCase(excelattribute))
	        	{
					try {
					
					int excelindex=unmodifiableexcelattribs.indexOf(excelattribute);
					//System.out.println("excel index "+values);
					
					wait.until(ExpectedConditions.visibilityOf(table.findElement(By.xpath("//div/label[text()= '"+voucherattribute+"']"))));
					String actid=table.findElement(By.xpath("//div/label[text()= '"+voucherattribute+"']")).getAttribute("for");
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(actid)));
					WebElement actcontrol=driver.findElement(By.id(actid));
					actcontrol.click();
					if(values.get(excelindex).length()>0)
					{
					actcontrol.clear();
					}
					//System.out.println("act id "+actid+" attr "+voucherattribute+" value "+values.get(excelindex));
					for (int i=0; i<values.get(excelindex).length(); i++)
					{	
						char ch=values.get(excelindex).charAt(i);
						actcontrol.sendKeys(String.valueOf(ch));
						Thread.sleep((long)slowkeys);
						
					}
					
					actcontrol.sendKeys(Keys.TAB);
					}
					//actcontrol.sendKeys(Keys.TAB);
				
					catch(Exception e)
					{
						//System.out.println("val not available");
					}
					break;
	        	}
	        }
		}

  }
  
  catch(Exception e)
  {
	  System.out.println("Footer method not getting executed");
  }
  }
}
