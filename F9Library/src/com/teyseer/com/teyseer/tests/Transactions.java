package com.teyseer.com.teyseer.tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.script.ScriptException;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import com.focus.constants.HomePage;
import com.focus.constants.LaunchApplication;
import com.focus.constants.Menus;
//import com.focus.library.MasterActions;
import com.focus.library.MasterHomePage;
import com.focus.utils.XLutlitities;
import com.teyseer.com.library.TransactionIcons;
import com.teyseer.com.transactions.TransactionBody;
import com.teyseer.com.transactions.TransactionFooter;
import com.teyseer.com.transactions.TransactionHeader;
import com.teyseer.com.transactions.TransactionSave;
import com.teyseer.com.transactions.TransactionWorkflow;
import com.teyseer.com.transactions.TransactionsNet;

import autoitx4java.AutoItX;

public class Transactions extends LaunchApplication
{
	XLutlitities xl=new XLutlitities();
	public String parentxlfile="\\\\DESKTOP-C918GTA\\Keywords\\Vouchers Names Sheet.xlsx";
	public String sheet="XLNames";
	public ArrayList<String> xlnames= new ArrayList<String>();
	int xlvouchersavemsg=1;
	int xlnetformulas=3;
	int xlbasecurrency=5;
	int xlbillcalcs=7;
	int xlheaderlabelnames=8;
	int xlbodylabelnames=14;
	int xlwrkflow=13;
	int xlfooterlabelnames=10;
	int startingcolno=5;
	int xlrescolno=3;
	int voucherrowno;
	
	HomePage hp = new HomePage();
	Menus menu=new Menus();
	//MasterActions ms = new MasterActions();
	MasterHomePage mhp = new MasterHomePage();
	TransactionHeader trh=new TransactionHeader();
	TransactionBody trb=new TransactionBody();
	TransactionFooter trf=new TransactionFooter();
	TransactionSave trs=new TransactionSave();
	TransactionWorkflow trw=new TransactionWorkflow();
	TransactionsNet trn=new TransactionsNet();
	TransactionIcons tri=new TransactionIcons();
  Transactions() throws IOException 
  
  {
	  	FileInputStream fi = new FileInputStream(parentxlfile);
		XSSFWorkbook wb = new XSSFWorkbook(fi);
		int rowcount=xl.getRowCount(parentxlfile, sheet);
		String names;
		
		for(int rowno=1; rowno<=rowcount;rowno++)
		{
			names=xl.getCellData(parentxlfile, sheet, rowno, 0);
			xlnames.add(names);
			
		}
		
  }
  
  @Test (priority=3)
  public void voucherSaveTest() throws IOException, InterruptedException, ScriptException
  {
	 String currentfile = "", prevfile;
	 logger.info("Excel sheets to be executed are: "+xlnames);
	 for(String xlname: xlnames)
	 {
		 ArrayList<String> keywordorder=new ArrayList<String>(Arrays.asList("VOUCHERWORKFLOW","VOUCHERHEADERDATA", "VOUCHERBODYDATA", "VOUCHERFOOTERDATA", "VOUCHERNET", "BASECURRENCY","VOUCHERSAVE", "NEWREFERENCE SAVE"));
		 ArrayList<String> actkeywords=new ArrayList<String>();
		 String xlfile="\\\\DESKTOP-C918GTA\\Keywords\\Automation Test Cases\\"+xlname;
		 String senariosheet="TestScenarios";
		 String casessheet;
		 int sheetno;
		 FileInputStream fi = new FileInputStream(xlfile);
		 XSSFWorkbook wb = new XSSFWorkbook(fi);
		 int shcount =wb.getNumberOfSheets();
		 int tccount;
		 int tscount;
		 int colcount;
		 int rowcount;
		 String tcexeflg,tcid,tstcid,keyword;
		 boolean res=false;
		for(int sh=1; sh<shcount; sh++)
		 {
			 casessheet=wb.getSheetName(sh);
			// logger.info("sheet no is "+sh);
			 logger.info("Worksheet "+casessheet+" is getting executed of Workbook"+xlfile);
			 tccount=xl.getRowCount(xlfile, senariosheet);
			 tscount=xl.getRowCount(xlfile, casessheet);
			 for (int i = 1; i <=7; i++) 
			 {	
				tcexeflg=xl.getCellData(xlfile, senariosheet, i, 2);
				if (tcexeflg.equalsIgnoreCase("Y")) 
				{
					String tcres="";
					tcid=xl.getCellData(xlfile, senariosheet, i, 0);
					for (int j = 1; j <=tscount ; j++) 
					{
						colcount=xl.getCellCount(xlfile, casessheet, j);
						rowcount=xl.getRowCount(xlfile, casessheet);
						tstcid=xl.getCellData(xlfile, casessheet, j, 0);
						if (tcid.equalsIgnoreCase(tstcid)) 
						{
							keyword=xl.getCellData(xlfile, casessheet, j, 4);
							switch (keyword.toUpperCase()) 
							{
							case("COMPANYCREATION"):
							{
								hp.compname=xl.getCellData(xlfile, casessheet, j, 5);
								hp.password=xl.getCellData(xlfile, casessheet, j, 6);
								res=hp.companyCreation(hp.compname, hp.password);
								break;
							}
							case("LOGIN"):
							{
								hp.username=xl.getCellData(xlfile, casessheet, j, 5);
								hp.password=xl.getCellData(xlfile, casessheet, j, 6);
								hp.compname=xl.getCellData(xlfile, casessheet, j, 7);
								logger.info("User Name: "+hp.username);
								logger.info("Password: "+hp.password);
								logger.info("Company: "+hp.compname);
								res=hp.LoginApp(hp.username, hp.password, hp.compname);
								break;
							}
							case("LOGOUT"):
							{
								res=hp.Logout();
								break;
							}
							case("TWOTREE"):
							{
								menu.menu1=xl.getCellData(xlfile, casessheet, j, 5);
								menu.menu2=xl.getCellData(xlfile, casessheet, j, 6);
								menu.expname=xl.getCellData(xlfile, casessheet, j, 7);
								res=menu.menuSelection(menu.menu1, menu.menu2, menu.expname);
								break;
							}
							case("THREETREE"):
							{
								menu.menu1=xl.getCellData(xlfile, casessheet, j, 5);
								menu.menu2=xl.getCellData(xlfile, casessheet, j, 6);
								menu.menu3=xl.getCellData(xlfile, casessheet, j, 7);
								menu.expname=xl.getCellData(xlfile, casessheet, j, 8);
								res=menu.menuSelection(menu.menu1, menu.menu2, menu.menu3, menu.expname);
								break;
							}
								
							case("FOURTREE"):
							{
								menu.menu1=xl.getCellData(xlfile, casessheet, j, 5);
								menu.menu2=xl.getCellData(xlfile, casessheet, j, 6);
								menu.menu3=xl.getCellData(xlfile, casessheet, j, 7);
								menu.menu4=xl.getCellData(xlfile, casessheet, j, 8);
								menu.expname=xl.getCellData(xlfile, casessheet, j, 9);
								res=menu.menuSelection(menu.menu1, menu.menu2, menu.menu3,menu.menu4, menu.expname);
								break;
								
								
							}
							case("VOUCHERNEW"):
							{
								res=trh.newClick();
								break;
							}
							case("VOUCHERHEADERLABELS"):
							{
								ArrayList<String> hlabels=new ArrayList<String>();
								hlabels.clear();
								trh.getLabelNames();
								hlabels.addAll(trh.totallabels);
								logger.info("Label names of Header are: "+hlabels);
								for(int label=0;label<hlabels.size();label++)
								{
									xl.setCellData(xlfile, casessheet, j, label+5, hlabels.get(label));
									
								}
								break;
															
							}
							case("VOUCHERBODYLABELS"):
							{
								ArrayList<String> blabels=new ArrayList<String>();
								blabels.clear();
								trb.getBodyLabelNames();
								blabels.addAll(trb.bodylabels);
								logger.info("Label names of Body are: "+blabels);
								for(int label=0;label<blabels.size();label++)
								{
									xl.setCellData(xlfile, casessheet, j, label+5, blabels.get(label));
									
								}
								break;
													
							}
							case("VOUCHERFOOTERLABELS"):
							{
								ArrayList<String> flabels=new ArrayList<String>();
								flabels.clear();
								trf.getLabelNames();
								flabels.addAll(trf.footerlabels);
								logger.info("Label names of Footer are: "+flabels);
								for(int label=0;label<flabels.size();label++)
								{
									xl.setCellData(xlfile, casessheet, j, label+5, flabels.get(label));
								}
								break;
															
							}
							case("HOMEPAGEICONS"):
							{
								while(((xl.getCellData(xlfile, casessheet, j, startingcolno)).toString()).length()==0)
								{
									break;
								}
								String homepagenames=xl.getCellData(xlfile, casessheet, j, startingcolno)+"";
								res=tri.transactionHomeIcons(homepagenames);
								break;
							}
							case("NEWDASHBOARDICONS"):
							{

								while(((xl.getCellData(xlfile, casessheet, j, startingcolno)).toString()).length()==0)
								{
									break;
								}
								
								String newdbnames=xl.getCellData(xlfile, casessheet, j, startingcolno)+"";
								res=tri.newDashboardIcons(newdbnames);
								break;
							
							}
							case("MENULIST"):
							{
								while(((xl.getCellData(xlfile, casessheet, j, 5)).toString().length())==0)
								{
									break;
								}
								String excelmenu=xl.getCellData(xlfile, casessheet, j, 5)+"";
								
								res=hp.menuDisplay(excelmenu);
								String menus="Application menu names are: ";
								for(String s:hp.menunames)
								{
									menus+=s+", ";
								}
								xl.setCellData(xlfile, casessheet, j, 6, menus);
								break;
							}
							}
							
							
							String tsres=null;
							
							if (res==true) 
							{
								tsres="Pass";
								xl.setCellData(xlfile, casessheet, j, 3, tsres);
								xl.fillGreenColor(xlfile, casessheet, j, 3);
								logger.info("Result to be printed for Test Case: "+tcid+" is "+tsres);
							} 
							else 
							{
								tsres="Fail";							
								xl.setCellData(xlfile, casessheet, j, 3, tsres);
								xl.fillRedColor(xlfile, casessheet, j, 3);
								logger.info("Result to be printed for Test Case: "+tcid+" is "+tsres);
							}
							
							try
							{
							if (!tcres.equalsIgnoreCase("FAIL")) 
							{
								xl.setCellData(xlfile, senariosheet, i, 3, "");
								
								tcres=tsres;
								
							}
							}
							catch(Exception e)
							{
								logger.info("Exception from Excel "+e.getMessage());
							}
							
						}
					}
					if(tcres.equalsIgnoreCase("Pass"))
					{
						xl.setCellData(xlfile, senariosheet, i, 3, tcres);
						xl.fillGreenColor(xlfile, senariosheet, i, 3);
					}
					else if(tcres.equalsIgnoreCase("Fail"))
					{
						xl.setCellData(xlfile, senariosheet, i, 3, tcres);
						xl.fillRedColor(xlfile, senariosheet, i, 3);
					}
				}
				else 
				{
					xl.setCellData(xlfile, senariosheet, i, 3, "Blocked");
				}
		
			}
		for (int i = 8; i <tccount; i++) 
		{
			tcexeflg=xl.getCellData(xlfile, senariosheet, i, 2);
			if (tcexeflg.equalsIgnoreCase("Y")) 
			{
				tcid=xl.getCellData(xlfile, senariosheet, i, 0);
				for (int j = 1; j <tscount ; j++) 
				{
					colcount=xl.getCellCount(xlfile, casessheet, j);
					rowcount=xl.getRowCount(xlfile, casessheet);
					while((xl.getCellData(xlfile, casessheet, j, 0)+"").toString().length()==0)
					{
						break;
					}
					tstcid=xl.getCellData(xlfile, casessheet, j, 0)+"";
					if (tcid.equalsIgnoreCase(tstcid)) 
					{
						try
						{
						while((xl.getCellData(xlfile, casessheet, j, 4)+"").toString().length()==0)
						{
							break;
						}
						keyword=xl.getCellData(xlfile, casessheet, j, 4)+"";
						}
						catch(NullPointerException ne) 
						{
							logger.info("null pointer exception");
						}
						actkeywords.clear();
						int cols;
						int a=xlbodylabelnames+1;
						//logger.info("rowcount "+rowcount+" xlbodylabelnames+1 "+a);
						if(rowcount<xlbodylabelnames+1)
						{
							cols=rowcount;
						}
						else
						{
							cols=xlbodylabelnames+1;
						}
						//logger.info("cols is "+cols);
						for(int keywordrow=1; keywordrow<=cols; keywordrow++)
						{
							String keyworddata = "";
						try
						{
							//logger.info("keyword row "+keywordrow);
							while((xl.getCellData(xlfile, casessheet, keywordrow, 4)+"").toString().length()==0)
							{
								break;
							}
							 keyworddata=xl.getCellData(xlfile, casessheet, keywordrow, 4)+"";
						}
						catch(NullPointerException ne)
						{
							logger.info("ne");
						}
							 actkeywords.add(keyworddata);
							keywordrow++;
						}
						String actkeyword=actkeywords.toString();
					logger.info("Keywords order from Excel is "+actkeyword);
						ArrayList<String>  unmodifiedkeywordorder= new ArrayList<String>();
						switch(actkeyword)
						{
						
						case("[VOUCHERSAVE, , , , VOUCHERHEADERDATA, , VOUCHERWORKFLOW, ]"):
							{
								keywordorder.remove("VOUCHERFOOTERDATA");
								keywordorder.remove("VOUCHERNET");
								keywordorder.remove("BASECURRENCY");
								keywordorder.remove("BILLCALCULATIONS");
								unmodifiedkeywordorder.clear();
								unmodifiedkeywordorder.addAll(Arrays.asList("VOUCHERWORKFLOW", "VOUCHERHEADERDATA", "VOUCHERBODYDATA", "VOUCHERSAVE"));
								break;
							}
							case("[VOUCHERSAVE, , , BILLCALCULATIONS, VOUCHERHEADERDATA, , VOUCHERWORKFLOW, ]"):
							{
								keywordorder.remove("VOUCHERFOOTERDATA");
								keywordorder.remove("VOUCHERNET");
								keywordorder.remove("BASECURRENCY");
								unmodifiedkeywordorder.clear();
								unmodifiedkeywordorder.addAll(Arrays.asList("VOUCHERWORKFLOW", "VOUCHERHEADERDATA", "VOUCHERBODYDATA", "VOUCHERSAVE","BILLCALCULATIONS"));
								break;
							}
							case("[VOUCHERSAVE, , BASECURRENCY, , VOUCHERHEADERDATA, , VOUCHERWORKFLOW, ]"):
							{
								keywordorder.remove("VOUCHERFOOTERDATA");
								keywordorder.remove("VOUCHERNET");
								keywordorder.remove("BILLCALCULATIONS");
								unmodifiedkeywordorder.clear();
								unmodifiedkeywordorder.addAll(Arrays.asList("VOUCHERWORKFLOW", "VOUCHERHEADERDATA", "VOUCHERBODYDATA", "BASECURRENCY", "VOUCHERSAVE"));
								break;
							}
							case("[VOUCHERSAVE, , BASECURRENCY, BILLCALCULATIONS, VOUCHERHEADERDATA, , VOUCHERWORKFLOW, ]"):
							{
								keywordorder.remove("VOUCHERFOOTERDATA");
								keywordorder.remove("VOUCHERNET");
								unmodifiedkeywordorder.clear();
								unmodifiedkeywordorder.addAll(Arrays.asList("VOUCHERWORKFLOW", "VOUCHERHEADERDATA", "VOUCHERBODYDATA", "BASECURRENCY", "VOUCHERSAVE", "BILLCALCULATIONS"));
								break;
							}
							case("[VOUCHERSAVE, VOUCHERNET, , , VOUCHERHEADERDATA, , VOUCHERWORKFLOW, ]"):
							{
								keywordorder.remove("VOUCHERFOOTERDATA");
								keywordorder.remove("BASECURRENCY");
								keywordorder.remove("BILLCALCULATIONS");
								unmodifiedkeywordorder.clear();
								unmodifiedkeywordorder.addAll(Arrays.asList("VOUCHERWORKFLOW", "VOUCHERHEADERDATA", "VOUCHERBODYDATA", "VOUCHERSAVE"));
								break;
							}
							case("[VOUCHERSAVE, VOUCHERNET, , BILLCALCULATIONS, VOUCHERHEADERDATA, , VOUCHERWORKFLOW, ]"):
							{
								keywordorder.remove("VOUCHERFOOTERDATA");
								keywordorder.remove("BASECURRENCY");
								unmodifiedkeywordorder.clear();
								unmodifiedkeywordorder.addAll(Arrays.asList("VOUCHERWORKFLOW", "VOUCHERHEADERDATA", "VOUCHERBODYDATA", "VOUCHERSAVE", "BILLCALCULATIONS"));
								break;
							}
							case("[VOUCHERSAVE, VOUCHERNET, BASECURRENCY, , VOUCHERHEADERDATA, , VOUCHERWORKFLOW, ]"):
							{
								keywordorder.remove("VOUCHERFOOTERDATA");
								keywordorder.remove("BILLCALCULATIONS");
								unmodifiedkeywordorder.clear();
								unmodifiedkeywordorder.addAll(Arrays.asList("VOUCHERWORKFLOW", "VOUCHERHEADERDATA", "VOUCHERBODYDATA", "VOUCHERNET", "BASECURRENCY", "VOUCHERSAVE"));
								break;
							}
							case("[VOUCHERSAVE, VOUCHERNET, BASECURRENCY, BILLCALCULATIONS, VOUCHERHEADERDATA, , VOUCHERWORKFLOW, ]"):
							{
								keywordorder.remove("VOUCHERFOOTERDATA");
								unmodifiedkeywordorder.clear();
								unmodifiedkeywordorder.addAll(Arrays.asList("VOUCHERWORKFLOW", "VOUCHERHEADERDATA", "VOUCHERBODYDATA", "VOUCHERNET", "BASECURRENCY", "VOUCHERSAVE", "BILLCALCULATIONS"));
								break;
							}
							
							case("[VOUCHERSAVE, VOUCHERNET, BASECURRENCY, , VOUCHERHEADERDATA, VOUCHERFOOTERDATA, VOUCHERWORKFLOW, VOUCHERBODYDATA]"):
							{
								
								unmodifiedkeywordorder.clear();
								keywordorder.remove("BILLCALCULATIONS");
								unmodifiedkeywordorder.addAll(Arrays.asList("VOUCHERWORKFLOW", "VOUCHERHEADERDATA", "VOUCHERBODYDATA","VOUCHERNET", "BASECURRENCY","VOUCHERSAVE"));
								break;
							}
							case("[VOUCHERSAVE, VOUCHERNET, BASECURRENCY, BILLCALCULATIONS, VOUCHERHEADERDATA, VOUCHERFOOTERDATA, VOUCHERWORKFLOW, VOUCHERBODYDATA]"):
							{
								
								unmodifiedkeywordorder.clear();
								unmodifiedkeywordorder.addAll(Arrays.asList("VOUCHERWORKFLOW", "VOUCHERHEADERDATA", "VOUCHERBODYDATA","VOUCHERNET", "BASECURRENCY","VOUCHERSAVE", "BILLCALCULATIONS"));
								break;
							}
							case("[VOUCHERSAVE, VOUCHERNET, , , VOUCHERHEADERDATA, VOUCHERFOOTERDATA, VOUCHERWORKFLOW, VOUCHERBODYDATA]"):
							{
								keywordorder.remove("BASECURRENCY");
								keywordorder.remove("BILLCALCULATIONS");
								unmodifiedkeywordorder.clear();
								unmodifiedkeywordorder.addAll(Arrays.asList("VOUCHERWORKFLOW", "VOUCHERHEADERDATA", "VOUCHERBODYDATA","VOUCHERNET","VOUCHERSAVE"));
								break;
							}
							case("[VOUCHERSAVE, VOUCHERNET, , BILLCALCULATIONS, VOUCHERHEADERDATA, VOUCHERFOOTERDATA, VOUCHERWORKFLOW, VOUCHERBODYDATA]"):
							{
								keywordorder.remove("BASECURRENCY");
								unmodifiedkeywordorder.clear();
								unmodifiedkeywordorder.addAll(Arrays.asList("VOUCHERWORKFLOW", "VOUCHERHEADERDATA", "VOUCHERBODYDATA","VOUCHERNET","VOUCHERSAVE", "BILLCALCULATIONS"));
								break;
							}
							
							case("[VOUCHERSAVE, , , , VOUCHERHEADERDATA, , , VOUCHERBODYDATA]"):
							{
								keywordorder.remove("VOUCHERFOOTERDATA");
								keywordorder.remove("VOUCHERWORKFLOW");
								keywordorder.remove("VOUCHERNET");
								keywordorder.remove("BASECURRENCY");
								keywordorder.remove("BILLCALCULATIONS");
								unmodifiedkeywordorder.clear();
								unmodifiedkeywordorder.addAll(Arrays.asList( "VOUCHERHEADERDATA", "VOUCHERBODYDATA", "VOUCHERSAVE"));
								break;
							}
							case("[VOUCHERSAVE, , , BILLCALCULATIONS, VOUCHERHEADERDATA, , , VOUCHERBODYDATA]"):
							{
								keywordorder.remove("VOUCHERFOOTERDATA");
								keywordorder.remove("VOUCHERWORKFLOW");
								keywordorder.remove("VOUCHERNET");
								keywordorder.remove("BASECURRENCY");
								unmodifiedkeywordorder.clear();
								unmodifiedkeywordorder.addAll(Arrays.asList( "VOUCHERHEADERDATA", "VOUCHERBODYDATA", "VOUCHERSAVE","BILLCALCULATIONS"));
								break;
							}
							case("[VOUCHERSAVE, , BASECURRENCY, , VOUCHERHEADERDATA, , , VOUCHERBODYDATA]"):
							{
								keywordorder.remove("VOUCHERFOOTERDATA");
								keywordorder.remove("VOUCHERWORKFLOW");
								keywordorder.remove("VOUCHERNET");
								keywordorder.remove("BILLCALCULATIONS");
								unmodifiedkeywordorder.addAll(Arrays.asList( "VOUCHERHEADERDATA", "VOUCHERBODYDATA", "BASECURRENCY", "VOUCHERSAVE"));
								break;
							}
							case("[VOUCHERSAVE, , BASECURRENCY, BILLCALCULATIONS, VOUCHERHEADERDATA, , , VOUCHERBODYDATA]"):
							{
								keywordorder.remove("VOUCHERFOOTERDATA");
								keywordorder.remove("VOUCHERWORKFLOW");
								keywordorder.remove("VOUCHERNET");
								unmodifiedkeywordorder.addAll(Arrays.asList( "VOUCHERHEADERDATA", "VOUCHERBODYDATA", "BASECURRENCY", "VOUCHERSAVE","BILLCALCULATIONS"));
								break;
							}
							case("[VOUCHERSAVE, VOUCHERNET, BASECURRENCY, , VOUCHERHEADERDATA, , , VOUCHERBODYDATA]"):
							{
								keywordorder.remove("VOUCHERFOOTERDATA");
								keywordorder.remove("VOUCHERWORKFLOW");
								keywordorder.remove("BILLCALCULATIONS");
								unmodifiedkeywordorder.clear();
								unmodifiedkeywordorder.addAll(Arrays.asList( "VOUCHERHEADERDATA", "VOUCHERBODYDATA", "VOUCHERNET", "BASECURRENCY", "VOUCHERSAVE"));
								break;
							}
							case("[VOUCHERSAVE, VOUCHERNET, BASECURRENCY, BILLCALCULATIONS, VOUCHERHEADERDATA, , , VOUCHERBODYDATA]"):
							{
								keywordorder.remove("VOUCHERFOOTERDATA");
								keywordorder.remove("VOUCHERWORKFLOW");
								unmodifiedkeywordorder.clear();
								unmodifiedkeywordorder.addAll(Arrays.asList( "VOUCHERHEADERDATA", "VOUCHERBODYDATA", "VOUCHERNET", "BASECURRENCY", "VOUCHERSAVE", "BILLCALCULATIONS"));
								break;
							}
							case("[VOUCHERSAVE, VOUCHERNET, , , VOUCHERHEADERDATA, , , VOUCHERBODYDATA]"):
							{
								keywordorder.remove("VOUCHERFOOTERDATA");
								keywordorder.remove("VOUCHERWORKFLOW");
								keywordorder.remove("BASECURRENCY");
								keywordorder.remove("BILLCALCULATIONS");
								unmodifiedkeywordorder.clear();
								unmodifiedkeywordorder.addAll(Arrays.asList( "VOUCHERHEADERDATA", "VOUCHERBODYDATA", "VOUCHERNET", "VOUCHERSAVE"));
								break;
							}
							case("[VOUCHERSAVE, VOUCHERNET, , BILLCALCULATIONS, VOUCHERHEADERDATA, , , VOUCHERBODYDATA]"):
							{
								keywordorder.remove("VOUCHERFOOTERDATA");
								keywordorder.remove("VOUCHERWORKFLOW");
								keywordorder.remove("BASECURRENCY");
								unmodifiedkeywordorder.clear();
								unmodifiedkeywordorder.addAll(Arrays.asList( "VOUCHERHEADERDATA", "VOUCHERBODYDATA", "VOUCHERNET", "VOUCHERSAVE", "BILLCALCULATIONS"));
								break;
							}
							case("[VOUCHERSAVE, VOUCHERNET, , , VOUCHERHEADERDATA, VOUCHERFOOTERDATA, , VOUCHERBODYDATA]"):
							{
								
								keywordorder.remove("VOUCHERWORKFLOW");
								keywordorder.remove("BASECURRENCY");
								keywordorder.remove("BILLCALCULATIONS");
								unmodifiedkeywordorder.clear();
								unmodifiedkeywordorder.addAll(Arrays.asList( "VOUCHERHEADERDATA", "VOUCHERBODYDATA","VOUCHERFOOTERDATA", "VOUCHERNET",  "VOUCHERSAVE"));
								break;
							}
							case("[VOUCHERSAVE, VOUCHERNET, , BILLCALCULATIONS, VOUCHERHEADERDATA, VOUCHERFOOTERDATA, , VOUCHERBODYDATA]"):
							{
								
								keywordorder.remove("VOUCHERWORKFLOW");
								keywordorder.remove("BASECURRENCY");
								unmodifiedkeywordorder.clear();
								unmodifiedkeywordorder.addAll(Arrays.asList( "VOUCHERHEADERDATA", "VOUCHERBODYDATA","VOUCHERFOOTERDATA", "VOUCHERNET",  "VOUCHERSAVE", "BILLCALCULATIONS"));
								break;
							}
							case("[VOUCHERSAVE, VOUCHERNET, BASECURRENCY, , VOUCHERHEADERDATA, VOUCHERFOOTERDATA, , VOUCHERBODYDATA]"):
							{
								
								keywordorder.remove("VOUCHERWORKFLOW");
								keywordorder.remove("BILLCALCULATIONS");
								unmodifiedkeywordorder.clear();
								unmodifiedkeywordorder.addAll(Arrays.asList( "VOUCHERHEADERDATA", "VOUCHERBODYDATA","VOUCHERFOOTERDATA", "VOUCHERNET", "BASECURRENCY", "VOUCHERSAVE"));
								break;
							}
							case("[VOUCHERSAVE, VOUCHERNET, BASECURRENCY, BILLCALCULATIONS, VOUCHERHEADERDATA, VOUCHERFOOTERDATA, , VOUCHERBODYDATA]"):
							{
								
								keywordorder.remove("VOUCHERWORKFLOW");
								unmodifiedkeywordorder.clear();
								unmodifiedkeywordorder.addAll(Arrays.asList( "VOUCHERHEADERDATA", "VOUCHERBODYDATA","VOUCHERFOOTERDATA", "VOUCHERNET", "BASECURRENCY", "VOUCHERSAVE", "BILLCALCULATIONS"));
								break;
							}
							case("[VOUCHERSAVE, , , , VOUCHERHEADERDATA, VOUCHERFOOTERDATA, , VOUCHERBODYDATA]"):
							{
								
								keywordorder.remove("VOUCHERWORKFLOW");
								keywordorder.remove("VOUCHERNET");
								keywordorder.remove("BASECURRENCY");
								keywordorder.remove("BILLCALCULATIONS");
								unmodifiedkeywordorder.clear();
								unmodifiedkeywordorder.addAll(Arrays.asList( "VOUCHERHEADERDATA", "VOUCHERBODYDATA","VOUCHERFOOTERDATA",  "VOUCHERSAVE"));
								break;
							}
							case("[VOUCHERSAVE, , , BILLCALCULATIONS, VOUCHERHEADERDATA, VOUCHERFOOTERDATA, , VOUCHERBODYDATA]"):
							{
								
								keywordorder.remove("VOUCHERWORKFLOW");
								keywordorder.remove("VOUCHERNET");
								keywordorder.remove("BASECURRENCY");
								unmodifiedkeywordorder.clear();
								unmodifiedkeywordorder.addAll(Arrays.asList( "VOUCHERHEADERDATA", "VOUCHERBODYDATA","VOUCHERFOOTERDATA",  "VOUCHERSAVE", "BILLCALCULATIONS"));
								break;
							}
							case("[VOUCHERSAVE, , BASECURRENCY, , VOUCHERHEADERDATA, VOUCHERFOOTERDATA, , VOUCHERBODYDATA]"):
							{
								
								keywordorder.remove("VOUCHERWORKFLOW");
								keywordorder.remove("VOUCHERNET");
								keywordorder.remove("BILLCALCULATIONS");
								unmodifiedkeywordorder.clear();
								unmodifiedkeywordorder.addAll(Arrays.asList( "VOUCHERHEADERDATA", "VOUCHERBODYDATA","VOUCHERFOOTERDATA", "BASECURRENCY", "VOUCHERSAVE"));
								break;
							}
							case("[VOUCHERSAVE, , BASECURRENCY, BILLCALCULATIONS, VOUCHERHEADERDATA, VOUCHERFOOTERDATA, , VOUCHERBODYDATA]"):
							{
								
								keywordorder.remove("VOUCHERWORKFLOW");
								keywordorder.remove("VOUCHERNET");
								unmodifiedkeywordorder.clear();
								unmodifiedkeywordorder.addAll(Arrays.asList( "VOUCHERHEADERDATA", "VOUCHERBODYDATA","VOUCHERFOOTERDATA", "BASECURRENCY", "VOUCHERSAVE", "BILLCALCULATIONS"));
								break;
							}
						}
						String tsres=null;
						logger.info("Order of the keywords which need to be executed for the voucher is "+unmodifiedkeywordorder);
						keywordorder.addAll(actkeywords);
						Collections.reverse(keywordorder);
						keywordorder.remove(keywordorder.size()-1);
						excelkeywords:
							for(String excelkeyword:unmodifiedkeywordorder)
							{
								hybridkeywords:
									for(String hybridkeyword: keywordorder)
									{
										if(excelkeyword.equalsIgnoreCase(hybridkeyword))
										{
											logger.info("Keyword which is getting executed is "+excelkeyword+" of Workbooksheet "+casessheet);
											keyword=excelkeyword;
											switch (keyword.toUpperCase()) 
											{
											case("VOUCHERHEADERDATA"):
											{
												
												ArrayList<String> mylist = new ArrayList<String>();
												String value = null;
												trh.excelattridlist.clear();
												for(int k=5; k<colcount; k++)
												{
													String attrid=xl.getCellData(xlfile, casessheet, xlheaderlabelnames, k);
													//logger.info("attr id "+attrid);
													trh.excelattridlist.add(attrid);
												}
												prevfile=currentfile;
												currentfile=xlfile;
												if(prevfile.equals(currentfile))
												{
													
												}
												else
												{
												trh.getLabelNames();
												}
												//Logic to compare each excel attribute with voucher attribute
												trh.totallabels.addAll(trh.excelattridlist);
												Collections.reverse(trh.totallabels);
												for (String excelattribute : trh.excelattridlist) 
												{
													trh.totallabels.remove(trh.totallabels.size()-1);
													voucherlist:
														for (String voucherattribute : trh.totallabels) 
														{
															if(excelattribute.equalsIgnoreCase(voucherattribute))
															{
																int kvalue=trh.excelattridlist.indexOf(excelattribute)+5;
																while((xl.getCellData(xlfile, casessheet, xlheaderlabelnames+1, kvalue)+"").toString().length()==0)
																{
																	break;
																}
																value=xl.getCellData(xlfile, casessheet, xlheaderlabelnames+1, kvalue)+"";
																mylist.add(value);
																break voucherlist;
															}
		        	
														}
												}
												
												
												trh.TransactionHeader(mylist);
												break;
											}
		
											case("VOUCHERBODYDATA"):
											{
												
												trb.rowno=0;
												ArrayList<String> mylist = new ArrayList<String>();
												String value = null;
												List<Integer> rownos=new ArrayList<Integer>();
												ArrayList<Integer> currentrowno=new ArrayList<Integer>();
												trb.excelattribs.clear();
												trb.unmodifiableexcelattribs.clear();
												for(int k=startingcolno; k<colcount; k++)
												{
													while((xl.getCellData(xlfile, casessheet, xlbodylabelnames, k)+"").toString().length()==0)
													{
														break ;
													}
													String attrid=xl.getCellData(xlfile, casessheet, xlbodylabelnames, k);
													trb.excelattribs.add(attrid);
													trb.unmodifiableexcelattribs.add(attrid);
												}
												trb.excelattribs.removeAll(Arrays.asList(null,""));
												trb.unmodifiableexcelattribs.removeAll(Arrays.asList(null,""));
												currentrowno.clear();
												
												for(int rows=xlbodylabelnames+1;rows<=rowcount;rows++)
												{
													currentrowno.add(rows);
												}
												trb.getBodyLabelNames();
												trb.bodylabels.addAll(trb.excelattribs);
												Collections.reverse(trb.bodylabels);
												trb.bodylabels.remove(trb.bodylabels.size()-1);
												//int crow=0;
												for(int row: currentrowno)
												{
													excellist:
														for (String excelattribute : trb.excelattribs) 
														{
															voucherlist:
																for (String voucherattribute : trb.bodylabels) 
																{
																	if(excelattribute.equalsIgnoreCase(voucherattribute))
																	{
																		int kvalue=trb.excelattribs.indexOf(excelattribute)+startingcolno;
																		while((xl.getCellData(xlfile, casessheet, row, kvalue)+"").toString().length()==0)
																		{
																			break ;
																		}
																		value=xl.getCellData(xlfile, casessheet, row, kvalue)+"";
																		mylist.add(value);
																		break voucherlist;
																	}
																}
														}
												}
												int total=mylist.size()/currentrowno.size();
												int vindex=0;
												int loopcount=1;
												for(int ind=0; ind<currentrowno.size();ind++)
												{
													List<String>[] arrayOfList = new List[currentrowno.size()];
													arrayOfList[ind] = mylist.subList(vindex, vindex+total);
													List<String> listset = arrayOfList[ind];
													trb.rowno=trb.rowno+1;
													trb.transactionBody(trb.voucherno, trb.excelattribs, trb.unmodifiableexcelattribs, listset, loopcount,trb.rowno);
													vindex=vindex+total;
													loopcount++;
												}
												trb.getBaseCurrencyCalcValues();
												break;	
	
											}
											case("VOUCHERFOOTERDATA"):
											{
												ArrayList<String> excelattributes=new ArrayList<String>();
												ArrayList<String> unmodifiedexcelattributes=new ArrayList<String>();
												ArrayList<String> excelvalues=new ArrayList<String>();
												excelattributes.clear();
												for(int k=startingcolno;k<colcount;k++)
												{
													while((xl.getCellData(xlfile, casessheet, xlfooterlabelnames, k)+"").toString().length()==0)
													{
														break;
													}
													String attrib=xl.getCellData(xlfile, casessheet, xlfooterlabelnames, k)+"";
													excelattributes.add(attrib);
												}
												try
												{
												unmodifiedexcelattributes.clear();
												unmodifiedexcelattributes.addAll(excelattributes);
												excelattributes.removeAll(Arrays.asList(null,""));
												unmodifiedexcelattributes.removeAll(Arrays.asList(null,""));
												trf.getLabelNames();
												trf.footerlabels.addAll(excelattributes);
												Collections.reverse(trf.footerlabels);
												trf.footerlabels.remove(trf.footerlabels.size()-1);
												excelvalues.clear();
												excellist:
													for(String excelattribute : excelattributes)
													{
														voucherlist:
															for(String voucherattribute: trf.footerlabels)
															{
																if(excelattribute.equalsIgnoreCase(voucherattribute))
																{	
																	int kvalue=excelattributes.indexOf(excelattribute)+startingcolno;
																	while((xl.getCellData(xlfile, casessheet, xlfooterlabelnames+1, kvalue)+"").toString().length()==0)
																	{
																		break;

																	}
																	String value=xl.getCellData(xlfile, casessheet, xlfooterlabelnames+1, kvalue)+"";
																	excelvalues.add(value);
																	break voucherlist;
																}
															}
													}
												//excelvalues.removeAll(Arrays.asList(null,""));
												trf.transactionFooter(unmodifiedexcelattributes, unmodifiedexcelattributes, excelvalues);
												}
												catch(Exception e)
												{
													logger.info("Exception of no footer available "+e.getMessage());
												}
												break;
											}
	
											case("VOUCHERSAVE"):
											{
												 
												
												if(!(unmodifiedkeywordorder.contains("BASECURRENCY")))
												{
													trs.transactionSave();
												}
												while((xl.getCellData(xlfile, casessheet, xlvouchersavemsg, 5)+"").toString().length()==0)
												{
													break;
												}
												String xlmethod=xl.getCellData(xlfile, casessheet, xlvouchersavemsg, 5)+"";
												
												while((xl.getCellData(xlfile, casessheet, xlvouchersavemsg, 6)+"").toString().length()==0)
												{
													break;
												}
												String billno=xl.getCellData(xlfile, casessheet, xlvouchersavemsg, 6)+"";
												while((xl.getCellData(xlfile, casessheet, xlvouchersavemsg, 7)+"").toString().length()==0)
												{
													break;
												}
												String xlmsg=xl.getCellData(xlfile, casessheet, xlvouchersavemsg, 7)+"";
												logger.info("xl method is "+xlmethod+" billno "+billno);
												res=trs.transactionSave(xlmethod,billno,xlmsg,unmodifiedkeywordorder);
												String expmsg=trs.actmsg;
												xl.setCellData(xlfile, casessheet, xlvouchersavemsg, 8,expmsg );
												logger.info("While saving voucher, actual message which is from application is "+expmsg+" and the messsage which is sent from excel is "+xlmsg+" & res is "+res);
												if(res==true)	
												{
													tsres="Pass";
													xl.setCellData(xlfile, casessheet, xlvouchersavemsg, xlrescolno, tsres);
													xl.fillGreenColor(xlfile, casessheet, xlvouchersavemsg, xlrescolno);
												}
												else
												{
													tsres="Fail";
													xl.setCellData(xlfile, casessheet, xlvouchersavemsg, 3, tsres);
													xl.fillRedColor(xlfile, casessheet, xlvouchersavemsg, 3);
												}
												break;
											}
											case("BILLCALCULATIONS"):
											{
												while((xl.getCellData(xlfile, casessheet, xlvouchersavemsg, 7)+"").toString().length()==0)
												{
													break;
												}
												String xlmsg=xl.getCellData(xlfile, casessheet, xlvouchersavemsg, 7)+"";
												boolean res2;
												res2=trs.billNoCalculations(xlmsg);
												res=trs.result;
												String expmsg=trs.actmsg;
												xl.setCellData(xlfile, casessheet, xlvouchersavemsg, 8,expmsg );
												String xlbalamtvalues = "";
												//logger.info("trs.balamtvalues "+trs.balamtvalues);
												for(String s:trs.balamtvalues)
												{
													xlbalamtvalues+=s;
												}
												xl.setCellData(xlfile, casessheet, xlbillcalcs, xlrescolno+2, xlbalamtvalues);
												//logger.info("xlbalamtvalues "+xlbalamtvalues);
												String xlnatcurrvalue = "";
												for(String s:trs.natcurrvalues)
												{
													xlnatcurrvalue+=s;
												}
												xl.setCellData(xlfile, casessheet, xlbillcalcs, xlrescolno+3, xlnatcurrvalue);
												String xlactnatcurrtvalue = "";
												
												//logger.info("results res2 "+res2+" res "+res);
												if(res==true)	
												{
													tsres="Pass";
													xl.setCellData(xlfile, casessheet, xlvouchersavemsg, xlrescolno, tsres);
													xl.fillGreenColor(xlfile, casessheet, xlvouchersavemsg, xlrescolno);
												}
												else
												{
													tsres="Fail";
													xl.setCellData(xlfile, casessheet, xlvouchersavemsg, 3, tsres);
													xl.fillRedColor(xlfile, casessheet, xlvouchersavemsg, 3);
												}
												if(res2==true)	
												{
													tsres="Pass";
													xl.setCellData(xlfile, casessheet, xlbillcalcs, xlrescolno, tsres);
													xl.fillGreenColor(xlfile, casessheet, xlbillcalcs, xlrescolno);
												}
												else
												{
													tsres="Fail";
													xl.setCellData(xlfile, casessheet, xlbillcalcs, xlrescolno, tsres);
													xl.fillRedColor(xlfile, casessheet, xlbillcalcs, xlrescolno);
												}
												break;
												
											}
											case("VOUCHERNET"):
											{
												while((xl.getCellData(xlfile, casessheet, xlnetformulas, 5)+"").toString().length()==0)
												{
													break;
												}
												String xlbodyformula=xl.getCellData(xlfile, casessheet, xlnetformulas, 5)+"";
												
												while((xl.getCellData(xlfile, casessheet, xlnetformulas, 6)+"").toString().length()==0)
												{
													break;
												}
												String xlfooterformula=xl.getCellData(xlfile, casessheet, xlnetformulas, 6)+"";
												
												while((xl.getCellData(xlfile, casessheet, xlnetformulas, 7)+"").toString().length()==0)
												{
													break;
												}
												String xloperator=xl.getCellData(xlfile, casessheet, xlnetformulas, 7)+"";
												
												if(xlfooterformula.length()==0)
												{
													//logger.info("Calling net calc ");
													res=trn.netCalculation(xlbodyformula,xloperator);
												}
												
												else
												{
												res=trn.netCalculation(xlbodyformula,xlfooterformula,xloperator);
												}
												//res=trn.transactionSave(xlmethod,billno,xlmsg);
												String actnet=String.valueOf(trn.actroundoffnet);
												//logger.info("Actnet "+actnet);
												xl.setCellData(xlfile, casessheet, xlnetformulas, 8,actnet);
												String expnet=trn.netr;
												//logger.info("expnet "+expnet);
												xl.setCellData(xlfile, casessheet, xlnetformulas, 9,expnet);
												if(res==true)	
												{
													tsres="Pass";
													xl.setCellData(xlfile, casessheet, xlnetformulas, xlrescolno, tsres);
													xl.fillGreenColor(xlfile, casessheet, xlnetformulas, xlrescolno);
												}
												else
												{
													tsres="Fail";
													xl.setCellData(xlfile, casessheet, xlnetformulas, xlrescolno, tsres);
													xl.fillRedColor(xlfile, casessheet, xlnetformulas, xlrescolno);
												}
												
												break;
											}
											case("BASECURRENCY"):
											{
												try
												{
													trs.getExchangeRate(trh.exchrt);
													logger.info("exchange rate calculated is "+trh.exchrt);
													//console.snapshot([label]);
													
												}
												catch(NullPointerException ex)
												{
													
												}
												trs.transactionSave();
												trs.getExchangeRate(trb.basecurrcalcattibs, trb.basecurrcalvalues);
												res=trs.basecurrency();
												int colno=startingcolno;
												try
												{
												for(int col=0;col<trs.calcbasecurrvalue.size();col++)
												{
													int cc=colno+col+1;
													xl.setCellData(xlfile, casessheet, xlbasecurrency, colno+col, String.valueOf(trs.calcbasecurrvalue.get(col)));
													xl.setCellData(xlfile, casessheet, xlbasecurrency, colno+col+1, String.valueOf(trs.actbasecurrvalue.get(col)));
													colno++;
												}
												}
												catch(Exception ex)
												{

													tsres="Fail";
													xl.setCellData(xlfile, casessheet, xlbasecurrency, xlrescolno, tsres);
													xl.fillRedColor(xlfile, casessheet, xlbasecurrency, xlrescolno);
												
												}
												if(res==true)	
												{
													tsres="Pass";
													xl.setCellData(xlfile, casessheet, xlbasecurrency, xlrescolno, tsres);
													xl.fillGreenColor(xlfile, casessheet, xlbasecurrency, xlrescolno);
												}
												else
												{
													tsres="Fail";
													xl.setCellData(xlfile, casessheet, xlbasecurrency, xlrescolno, tsres);
													xl.fillRedColor(xlfile, casessheet, xlbasecurrency, xlrescolno);
												}
												
												break;
											}
											case("VOUCHERWORKFLOW"):
											{
												while((xl.getCellData(xlfile, casessheet, xlwrkflow, 5)+"").toString().length()==0)
												{
													break;
												}
												String xlwrkflowname=xl.getCellData(xlfile, casessheet, xlwrkflow, 5)+"";
												
												while((xl.getCellData(xlfile, casessheet, xlwrkflow, 6)+"").toString().length()==0)
												{
													break;
												}
												String voucherno=xl.getCellData(xlfile, casessheet, xlwrkflow, 6)+"";
												trw.workflow(xlwrkflowname, voucherno);
												break;
											}
											
											}
											break hybridkeywords;	
										}
									}
								}
						 		String tcres="";
								
						 		ArrayList<String> voucherresults=new ArrayList();
						 		voucherresults.clear();
						 		for(int resrow=1;resrow<=tscount;resrow++)
						 		{
						 			
						 			
						 			while(((xl.getCellData(xlfile, casessheet, resrow, xlrescolno))+"").toString().length()==0)
						 			{
						 				break;
						 			}
						 			String voucherres=xl.getCellData(xlfile, casessheet, resrow, xlrescolno)+"";
						 			voucherresults.add(voucherres);
						 		}
						 		
						 		if(voucherresults.contains("Fail"))
						 		{
						 			tsres="Fail";
						 		}
								try
								{
								if (!tcres.equalsIgnoreCase("FAIL")) 
								{
									xl.setCellData(xlfile, senariosheet, i, 3, "");
									tcres=tsres;
									
								}
								}
								catch(Exception e)
								{
									logger.info("Exception from excel "+e.getMessage());
								}
								if(tcres.equalsIgnoreCase("Pass"))
								{
									xl.setCellData(xlfile, senariosheet, i, 3, tcres);
									xl.fillGreenColor(xlfile, senariosheet, i, 3);
								}
								else 
								{
									xl.setCellData(xlfile, senariosheet, i, 3, tcres);
									xl.fillRedColor(xlfile, senariosheet, i, 3);
								}
								
							}
						}
					}
					else 
					{
						xl.setCellData(xlfile, senariosheet, i, 3, "Blocked");
					}
				}
		for(int i=tccount;i<=tccount;i++)
		{
			tcexeflg=xl.getCellData(xlfile, senariosheet, i, 2);
			if (tcexeflg.equalsIgnoreCase("Y")) 
			{
				String tcres="";
				tcid=xl.getCellData(xlfile, senariosheet, i, 0);
				for (int j = 1; j <=tscount ; j++) 
				{
					colcount=xl.getCellCount(xlfile, casessheet, j);
					rowcount=xl.getRowCount(xlfile, casessheet);
					tstcid=xl.getCellData(xlfile, casessheet, j, 0);
					if (tcid.equalsIgnoreCase(tstcid)) 
					{
						keyword=xl.getCellData(xlfile, casessheet, j, 4);
						switch (keyword.toUpperCase()) 
						{
						case("VOUCHERCLOSE"):
						{
							res=trs.transactionClose();
							break;
						}
						case("LOGOUT"):
						{
							res=hp.Logout();
							break;
						}
					}
						String tsres=null;
						
						if (res==true) 
						{
							tsres="Pass";
							xl.setCellData(xlfile, casessheet, j, 3, tsres);
							xl.fillGreenColor(xlfile, casessheet, j, 3);
							logger.info("Result to be printed for Test Case "+tcid+" is "+tsres);
						} 
						else 
						{
							tsres="Fail";							
							xl.setCellData(xlfile, casessheet, j, 3, tsres);
							xl.fillRedColor(xlfile, casessheet, j, 3);
							logger.info("Result to be printed for Test Case "+tcid+" is "+tsres);
						}
						
						try
						{
						if (!tcres.equalsIgnoreCase("FAIL")) 
						{
							xl.setCellData(xlfile, senariosheet, i, 3, "");
							
							tcres=tsres;
							
						}
						}
						catch(Exception e)
						{
							logger.info("Exception from Excel "+e.getMessage());
						}
						
					}
				}
				if(tcres.equalsIgnoreCase("Pass"))
				{
					xl.setCellData(xlfile, senariosheet, i, 3, tcres);
					xl.fillGreenColor(xlfile, senariosheet, i, 3);
					
				}
				else if(tcres.equalsIgnoreCase("Fail"))
				{
					xl.setCellData(xlfile, senariosheet, i, 3, tcres);
					xl.fillRedColor(xlfile, senariosheet, i, 3);
					
				}
			}
			else 
			{
				xl.setCellData(xlfile, senariosheet, i, 3, "Blocked");
			}
				}
			}
			
						
		
		 	

	 	}
	 		//driver.quit();
	 		
	}
 
}