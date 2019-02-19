package com.teyseer.com.teyseer.tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.focus.constants.HomePage;
import com.focus.constants.LaunchApplication;
import com.focus.constants.Menus;
//import com.focus.library.MasterActions;
import com.focus.library.MasterHomePage;
import com.focus.utils.XLutlitities;
import com.teyseer.com.library.UserCreation;
import com.teyseer.com.transactions.HomePageClose;
import com.teyseer.com.transactions.MasterData;
import com.teyseer.com.transactions.TransactionBody;
import com.teyseer.com.transactions.TransactionFooter;
import com.teyseer.com.transactions.TransactionHeader;
import com.teyseer.com.transactions.TransactionSave;
import com.teyseer.com.transactions.TransactionWorkflow;

public class Masters extends LaunchApplication
{
	XLutlitities xl=new XLutlitities();
	public String sheet="XLNames";
	public ArrayList<String> xlnames= new ArrayList<String>();
	int xlvouchersavemsg=1;
	int xlheaderlabelnames=2;
	int xlbodylabelnames=8;
	int xlwrkflow=7;
	int xlfooterlabelnames=4;
	int startingcolno=5;
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
	MasterData md=new MasterData();
	HomePageClose hpc=new HomePageClose();
	UserCreation uc=new UserCreation();
	@Parameters({ "parentxlfile" })
	@Test (priority=1)
	public void Masters(String parentxlfile) throws IOException 
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
 @Test (priority=2)
  public void masterSave() throws IOException, InterruptedException 
  {
	 logger.info("Workbook's of Masters which need to be executed are "+xlnames);
		 for(String xlname: xlnames)
		 {
			 ArrayList<String> keywordorder=new ArrayList<String>(Arrays.asList("VOUCHERWORKFLOW","VOUCHERHEADERDATA", "VOUCHERBODYDATA", "VOUCHERFOOTERDATA", "VOUCHERSAVE", "NEWREFERENCE SAVE"));
			 ArrayList<String> actkeywords=new ArrayList<String>();
			 String xlfile="\\\\DESKTOP-C918GTA\\Keywords\\Automation Test Cases\\"+xlname;
			 String senariossheet="TestScenarios";
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
				 logger.info("Executing Workbook sheet "+casessheet+" of Workbook "+xlfile);
				 tccount=xl.getRowCount(xlfile, senariossheet);
				 tscount=xl.getRowCount(xlfile, casessheet);
				 for (int i = 1; i <=tccount; i++) 
				 {
					tcexeflg=xl.getCellData(xlfile, senariossheet, i, 2);
					if (tcexeflg.equalsIgnoreCase("Y")) 
					{
						String tcres="";
						tcid=xl.getCellData(xlfile, senariossheet, i, 0);
						for (int j = 1; j <=tscount ; j++) 
						{
							colcount=xl.getCellCount(xlfile, casessheet, j);
							rowcount=xl.getRowCount(xlfile, casessheet);
							tstcid=xl.getCellData(xlfile, casessheet, j, 0);
							if (tcid.equalsIgnoreCase(tstcid)) 
							{
								keyword=xl.getCellData(xlfile, casessheet, j, 4);
								logger.info("Keyword which is getting exeucted is "+keyword+" whose Test Case ID is "+tcid);
								switch (keyword.toUpperCase()) 
								{
								case("LOGIN"):
								{
									hp.username=xl.getCellData(xlfile, casessheet, j, 5);
									hp.password=xl.getCellData(xlfile, casessheet, j, 6);
									hp.compname=xl.getCellData(xlfile, casessheet, j, 7);
									logger.info("User Name: "+hp.username);
									logger.info("Password: "+hp.password);
									logger.info("Company Name: "+hp.compname);
									res=hp.LoginApp(hp.username, hp.password, hp.compname);
									
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
									//logger.info(("Menu are "+menu.menu1+menu.expmsg+ res));
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
								case("COMPANYCREATION"):
								{
									hp.compname=xl.getCellData(xlfile, casessheet, j, 5);
									hp.password=xl.getCellData(xlfile, casessheet, j, 6);
									res=hp.companyCreation(hp.compname, hp.password);
									logger.info("msg "+hp.compmsg);
									xl.setCellData(xlfile, casessheet, j, 7, hp.compmsg);
									
									break;
								}
								case("USERCREATE"):
								{
									ArrayList<String> uservalues=new ArrayList();
									String uservalue;
								
									for(int k=startingcolno;k<colcount;k++)
									{
										while((xl.getCellData(xlfile, casessheet, j, k)+"").length()==0)
										{
											break;
										}
										uservalue=xl.getCellData(xlfile, casessheet, j, k)+"";
										uservalues.add(uservalue);
									}
									res=uc.createUSer(uservalues.get(2), uservalues.get(3), uservalues.get(4), uservalues.get(5),uservalues.get(0));
									xl.setCellData(xlfile, casessheet, j, startingcolno+1, uc.actmsg);
									break;
								}
								case("MASTERNEW"):
								{
									res=mhp.masterNew();
									break;
								}
								case("MASTERADDGROUP"):
								{
									res=mhp.masterAddGroup();
									break;
								}
								case("MASTERNODE"):
								{
									String nodevalues;
									ArrayList<String> nodes=new ArrayList();
									for(int k=startingcolno;k<colcount;k++)
									{
										while((xl.getCellData(xlfile, casessheet, j, k)+"").length()==0)
										{
											break;
										}
										nodevalues=xl.getCellData(xlfile, casessheet, j, k)+"";
										nodes.add(nodevalues);
									}
									nodes.removeAll(Arrays.asList(""));
									mhp.masterNode(nodes);
									break;
								}
								case("GETMASTERLABELNAMES"):
								{
									ArrayList<String> xlattribs = new ArrayList<String>();
									ArrayList<String> unmodifiedxlattribs = new ArrayList<String>();
									ArrayList<String> xlvalues = new ArrayList<String>();
									String value;
									xlattribs.clear();
									unmodifiedxlattribs.clear();
									xlvalues.clear();
									for(int k=startingcolno;k<colcount;k++)
									{
										while((xl.getCellData(xlfile, casessheet, 0, k)+"").length()==0)
										{
											break;
										}
										value=xl.getCellData(xlfile, casessheet, 0, k)+"";
										xlattribs.add(value);
										unmodifiedxlattribs.add(value);
										
									}
									for(int val=startingcolno; val<colcount; val++)
									{
										while((xl.getCellData(xlfile, casessheet, j, val)+"").length()==0)
										{
											break;
										}
										value=xl.getCellData(xlfile, casessheet, j, val)+"";
										xlvalues.add(value);
									}
									md.masterdataentry(xlattribs, unmodifiedxlattribs, xlvalues);
									for(int labelindex=0;labelindex<md.labelnames.size();labelindex++)
									{
										xl.setCellData(xlfile, casessheet, j, 5+labelindex, md.labelnames.get(labelindex));
									}
									break;
								}
								case("MASTERSELECTION"):
								{
									String code;
									while((xl.getCellData(xlfile, casessheet, j, startingcolno)+"").length()==0)
									{
										break;
									}
									code=xl.getCellData(xlfile, casessheet, j, startingcolno)+"";
									res=mhp.masterSelection(code);
									break;
								}
								case("MASTERDELETE"):
								{
									String expmsg;
									while((xl.getCellData(xlfile, casessheet, j, startingcolno)+"").length()==0)
									{
										break;
									}
									expmsg=xl.getCellData(xlfile, casessheet, j, startingcolno)+"";
									res=mhp.masterDelete(expmsg);
									xl.setCellData(xlfile, casessheet, j, startingcolno+1, mhp.actmsg);
									break;
									
								}
								case("MASTERDATA"):
								{
									ArrayList<String> xlattribs = new ArrayList<String>();
									ArrayList<String> unmodifiedxlattribs = new ArrayList<String>();
									ArrayList<String> xlvalues = new ArrayList<String>();
									String value;
									xlattribs.clear();
									unmodifiedxlattribs.clear();
									xlvalues.clear();
									for(int k=startingcolno;k<colcount;k++)
									{
										while((xl.getCellData(xlfile, casessheet, 0, k)+"").length()==0)
										{
											break;
										}
										value=xl.getCellData(xlfile, casessheet, 0, k)+"";
										xlattribs.add(value);
										unmodifiedxlattribs.add(value);
										
									}
									for(int val=startingcolno; val<colcount; val++)
									{
										while((xl.getCellData(xlfile, casessheet, j, val)+"").length()==0)
										{
											break;
										}
										value=xl.getCellData(xlfile, casessheet, j, val)+"";
										xlvalues.add(value);
									}
									md.masterdataentry(xlattribs, unmodifiedxlattribs, xlvalues);
									break;
								}
								case("MASTEREDIT"):
								{
									String code = "";
									while((xl.getCellData(xlfile, casessheet, j, startingcolno)+"").length()==0)
										{
											break;
										}
										code=xl.getCellData(xlfile, casessheet, j, startingcolno)+"";
										res=mhp.masterEdit(code);
									
									break;
								}
								case("MASTERUPDATE"):
								{
									String expmsg;
									while((xl.getCellData(xlfile, casessheet, j, startingcolno)+"").length()==0)
									{
										break;
									}
									expmsg=xl.getCellData(xlfile, casessheet, j, startingcolno)+"";
									res=mhp.masterUpdate(expmsg);
									xl.setCellData(xlfile, casessheet, j, startingcolno+1, mhp.actmsg);
									break;
								}
								case("MASTERSAVE"):
								{
									String expmsg;
									while((xl.getCellData(xlfile, casessheet, j, startingcolno)+"").length()==0)
									{
										break;
									}
									expmsg=xl.getCellData(xlfile, casessheet, j, startingcolno)+"";
									res=mhp.masterSave(expmsg);
									xl.setCellData(xlfile, casessheet, j, startingcolno+1, mhp.actmsg);
									break;
								}
								case("LOGOUT"):
								{
									res=hp.Logout();
									break;
								}
								case("MASTERCLOSE"):
								{
									res=mhp.masterClose();
									break;
								}
								}
								String tsres=null;
								
								
								if (res==true) 
								{
									tsres="Pass";
									xl.setCellData(xlfile, casessheet, j, 3, tsres);
									xl.fillGreenColor(xlfile, casessheet, j, 3);
									logger.info("Result which is getting printed for  Test Case ID "+tcid+" is "+tsres);
								} 
								else 
								{
									tsres="Fail";							
									xl.setCellData(xlfile, casessheet, j, 3, tsres);
									xl.fillRedColor(xlfile, casessheet, j, 3);
									logger.info("Result which is getting printed for  Test Case ID "+tcid+" is "+tsres);
								}
								
								try
								{
								if (!tcres.equalsIgnoreCase("FAIL")) 
								{
									xl.setCellData(xlfile, senariossheet, i, 3, "");
									
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
							xl.setCellData(xlfile, senariossheet, i, 3, tcres);
							xl.fillGreenColor(xlfile, senariossheet, i, 3);
						}
						else if(tcres.equalsIgnoreCase("Fail"))
						{
							xl.setCellData(xlfile, senariossheet, i, 3, tcres);
							xl.fillRedColor(xlfile, senariossheet, i, 3);
						}
					}
					else 
					{
						xl.setCellData(xlfile, senariossheet, i, 3, "Blocked");
					}
			
				}
			 }
		 }
		
  	}
  
  }
   


