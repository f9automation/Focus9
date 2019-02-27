package com.teyseer.com.library;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.focus.constants.HomePage;
import com.focus.constants.LaunchApplication;
import com.focus.constants.Menus;
import com.teyseer.com.transactions.TransactionHeader;

public class TransactionIcons extends LaunchApplication 
{
	HomePage hp = new HomePage();
	Menus menu= new Menus();

TransactionHeader th= new TransactionHeader();
/*
@Test

	public void newClick() throws InterruptedException 
	  {
		String workflow = null, voucherno = null;
		String names="New,Save,Delete,Previous,Next,Print,Authorization,Suspend,Close";
		String homenames="New, Edit, Print, Delete, Authorize, Reject, Print Barcode, Export To_XML, Settings, Close";
		String menunames=" Home , HRMS, ESS, Financials, Inventory, Fixed Asset, Production, Quality Control, Settings";
		hp.LoginApp("su", "focus", "TEYSEER MOTORS CO W.L.L. [040]");
		boolean resl=hp.menuDisplay(menunames);
		logger.info("res "+resl);
		boolean resls;
		resls=transactionHomeIcons(homenames);
		th.newClick();
		boolean result;
		result=newDashboardIcons(names);
		th.getLabelNames();
		driver.quit();
			
	  }
	*/

/* METHOD TO GET ALL THE ICONS AFTER CLICKING ON NEW ICON FROM TRANSACTION AND COMPARING IT WITH THE ONES WHICH ARE SETN AS PARAMETERS FROM EXCEL */
  public boolean newDashboardIcons(String excelname) throws InterruptedException 
  {
	  Thread.sleep(1000);
	  List<WebElement> navbar=driver.findElements(By.xpath("//div[@class='nav navbar-nav navbar-right']/div"));
	  ArrayList<String> excelnames= new ArrayList(Arrays.asList((excelname.replaceAll(" ", "")).split(",")));
	  ArrayList<String>inames=new ArrayList();
	  inames.clear();
	  for(int i=4;i<navbar.size()-2;i++)
	  {
		  String attribute= navbar.get(i).getAttribute("onclick");
		  String attrib= attribute.replaceAll("TRANSACTION_ENTRY.TOOLBAR.on", "");
		  ArrayList<String> ar=new ArrayList(Arrays.asList(attrib.split("_")));
		  String iname=ar.get(0).trim();
		  inames.add(iname);
		  
	  }
	 inames.removeAll(Arrays.asList("", null));
	 logger.info("Dashboard nmaes "+inames);
	 if(excelnames.equals(inames))
	 {
		  return true;
	 }
	return false;
	
  }
  
  /* METHOD TO GET ALL THE ICON NAMES OF THE TRANSACTION HOME PAGE AND COMPARE IT WITH THE ONES WHICH ARE SENT FROM EXCEL AS PARAMETERS */
  public boolean transactionHomeIcons(String excelname) throws InterruptedException
  {

	  Thread.sleep(1000);
	  List<WebElement> navbar=driver.findElements(By.xpath("//div[@id='myNavbar']/div[@class='nav navbar-nav navbar-right']/div[not(contains(@style,'display:none;'))]"));
	  ArrayList<String> excelnames= new ArrayList(Arrays.asList((excelname.replaceAll(" ", "")).split(",")));
	  ArrayList<String>inames=new ArrayList();
	  inames.clear();
	  for(int i=0;i<navbar.size()-2;i++)
	  {
		  String attribute;
		  
		   attribute= navbar.get(i).getAttribute("onclick");
		 
		  String attrib;
		  try
		  {
		   attrib= attribute.replaceAll("TRANSACTION_MAINSCREEN.", "");
		  }
		  catch(Exception e)
		  {
			  break;
		  }
		  //logger.info("Attrib "+attrib);
		  ArrayList<String> ar=new ArrayList(Arrays.asList(attrib.split("_")));
		  String iname=ar.get(0).trim();
		  attrib=ar.get(0).trim();
		  if(attrib.startsWith("on"))
		  {
			  attrib=attrib.substring(2, attrib.length());
		  }
		  if(attrib.endsWith("Voucher"))
		  {
			  attrib=attrib.replace("Voucher", "");
		  }
		  if(attrib.equals("ExportToXML()"))
		  {
			  attrib=attrib.replace("ExportToXML()", "ExportTo_XML");
		  }
		  attrib=attrib.replaceAll("()","");
		  inames.add(attrib);
		  
	  }
	  logger.info("before Home page names "+inames);
	 inames.removeAll(Arrays.asList("", null));
	 logger.info("Home page names "+inames);
	 if(excelnames.equals(inames))
	 {
		  return true;
	 }
	 return false;
	}
	
}
