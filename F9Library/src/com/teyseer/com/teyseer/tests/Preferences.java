package com.teyseer.com.teyseer.tests;

import static org.testng.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.focus.constants.HomePage;
import com.focus.constants.LaunchApplication;
import com.focus.constants.Menus;
import com.focus.library.MasterHomePage;
import com.focus.utils.XLutlitities;
import com.teyseer.com.library.PreferencesHomePage;


public class Preferences extends LaunchApplication
{
	XLutlitities xl=new XLutlitities();
	public String sheet="XLNames";
	public ArrayList<String> xlnames= new ArrayList<String>();
	int startingcolno=5;
	HomePage hp = new HomePage();
	Menus menu=new Menus();
	MasterHomePage mhp = new MasterHomePage();
	PreferencesHomePage ph=new PreferencesHomePage();
	SoftAssert softAssertion= new SoftAssert();
	@Parameters({ "parentxlfile" })
	@Test (priority=1)
	
	public void priceBookNames(String parentxlfile) throws IOException 
  {
		FileInputStream fi = new FileInputStream(parentxlfile);
		XSSFWorkbook wb = new XSSFWorkbook(fi);
		int rowcount=xl.getRowCount(parentxlfile, sheet);
		String name;
		
		for(int rowno=1; rowno<=rowcount;rowno++)
		{
			name=xl.getCellData(parentxlfile, sheet, rowno, 0);
			xlnames.add(name);
			
		}
		
  }
	  @Test (priority=2)
	  public void priceBook() throws Exception 
	  {
		   		logger.info("Workbook's of Masters which need to be executed are "+xlnames+" "+Thread.currentThread().getId());
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
												xl.setCellData(xlfile, casessheet, j, 7, hp.compmsg);
												
												break;
											}
											case("VERIFYPREFERENCESLIST"):
											{
												String xlprefslist=xl.getCellData(xlfile, casessheet, j, 5);
												ph.comparePreferenceItems(xlprefslist);
												xl.setCellData(xlfile, casessheet, j, 6, "Preferences list from application are "+ph.preflist);
												break;
											}
											case("VERIFYPREFERENCESCREEN"):
											{
												String xlprefscreen=xl.getCellData(xlfile, casessheet, j, 5);
												ph.clickPreference(xlprefscreen);
												xl.setCellData(xlfile, casessheet, j, 6, "Screen name which is captured from application is "+ph.screentitle);
												break;
											}
											case("UPDATETAGS"):
											{
												String xlvalue="";
												ArrayList<String> xllabels=new ArrayList();
												ArrayList<String> xlvalues=new ArrayList();
												xllabels.clear();
												xlvalues.clear();
												for(int c=startingcolno+2;c<colcount;c++)
												{
													while((xl.getCellData(xlfile, casessheet, j, c)+"").length()==0)
													{
														break;
													}
													xlvalue=xl.getCellData(xlfile, casessheet, j, c)+"";
													xllabels.add(xlvalue);
													while((xl.getCellData(xlfile, casessheet, j+1, c)+"").length()==0)
													{
														break;
													}
													xlvalue=xl.getCellData(xlfile, casessheet, j+1, c)+"";
													xlvalues.add(xlvalue);
												}
												ph.tagsDataEntry(xllabels, xlvalues);
												String expmsg=xl.getCellData(xlfile, casessheet, j+1, startingcolno);
												res=ph.updatePreferences(expmsg);
												xl.setCellData(xlfile, casessheet, j+1, startingcolno+1, ph.actmsg);
												break;
											}
											case("UPDATEACCOUNTS"):
											{

												String xlvalue="";
												ArrayList<String> xllabels=new ArrayList();
												ArrayList<String> xlvalues=new ArrayList();
												xllabels.clear();
												xlvalues.clear();
												for(int c=startingcolno+2;c<colcount;c++)
												{
													while((xl.getCellData(xlfile, casessheet, j, c)+"").length()==0)
													{
														break;
													}
													xlvalue=xl.getCellData(xlfile, casessheet, j, c)+"";
													xllabels.add(xlvalue);
													while((xl.getCellData(xlfile, casessheet, j+1, c)+"").length()==0)
													{
														break;
													}
													xlvalue=xl.getCellData(xlfile, casessheet, j+1, c)+"";
													xlvalues.add(xlvalue);
												}
												ph.accountsData(xllabels, xlvalues);
												String expmsg=xl.getCellData(xlfile, casessheet, j+1, startingcolno);
												res=ph.updatePreferences(expmsg);
												xl.setCellData(xlfile, casessheet, j+1, startingcolno+1, ph.actmsg);
												
												break;
											}
											case("UPDATEBUDGETS"):
											{


												String xlvalue="";
												ArrayList<String> xllabels=new ArrayList();
												ArrayList<String> xlvalues=new ArrayList();
												int abc=xl.getCellCount(xlfile, casessheet, j);
														logger.info("abc "+abc);
												xllabels.clear();
												xlvalues.clear();
												logger.info("col ocunt "+colcount+" row is "+j);
												for(int c=startingcolno+2;c<colcount;c++)
												{
													
													while((xl.getCellData(xlfile, casessheet, j, c)+"").length()==0)
													{
														break;
													}
													xlvalue=xl.getCellData(xlfile, casessheet, j, c)+"";
													xllabels.add(xlvalue);
													while((xl.getCellData(xlfile, casessheet, j+1, c)+"").length()==0)
													{
														break;
													}
													xlvalue=xl.getCellData(xlfile, casessheet, j+1, c)+"";
													xlvalues.add(xlvalue);
												}
												ph.budgetData(xllabels, xlvalues);
												String expmsg=xl.getCellData(xlfile, casessheet, j+1, startingcolno);
												res=ph.updatePreferences(expmsg);
												xl.setCellData(xlfile, casessheet, j+1, startingcolno+1, ph.actmsg);
												break;
											}
											case("UPDATEARAP"):
											{
												String xlvalue="";
												ArrayList<String> xllabels=new ArrayList();
												ArrayList<String> xlvalues=new ArrayList();
												int abc=xl.getCellCount(xlfile, casessheet, j);
												xllabels.clear();
												xlvalues.clear();
												//logger.info("col ocunt "+colcount+" row is "+j);
												for(int c=startingcolno+2;c<colcount;c++)
												{
													
													while((xl.getCellData(xlfile, casessheet, j, c)+"").length()==0)
													{
														break;
													}
													xlvalue=xl.getCellData(xlfile, casessheet, j, c)+"";
													xllabels.add(xlvalue);
													while((xl.getCellData(xlfile, casessheet, j+1, c)+"").length()==0)
													{
														break;
													}
													xlvalue=xl.getCellData(xlfile, casessheet, j+1, c)+"";
													xlvalues.add(xlvalue);
												}
												ph.aRAPData(xllabels, xlvalues);
												String expmsg=xl.getCellData(xlfile, casessheet, j+1, startingcolno);
												res=ph.updatePreferences(expmsg);
												xl.setCellData(xlfile, casessheet, j+1, startingcolno+1, ph.actmsg);
												break;
											}
											case("UPDATEMISCELLANEOUS"):
											{
												String xlvalue="";
												ArrayList<String> xllabels=new ArrayList();
												ArrayList<String> xlvalues=new ArrayList();
												int abc=xl.getCellCount(xlfile, casessheet, j);
												xllabels.clear();
												xlvalues.clear();
												//logger.info("col ocunt "+colcount+" row is "+j);
												for(int c=startingcolno+2;c<colcount;c++)
												{
													
													while((xl.getCellData(xlfile, casessheet, j, c)+"").length()==0)
													{
														break;
													}
													xlvalue=xl.getCellData(xlfile, casessheet, j, c)+"";
													xllabels.add(xlvalue);
													while((xl.getCellData(xlfile, casessheet, j+1, c)+"").length()==0)
													{
														break;
													}
													xlvalue=xl.getCellData(xlfile, casessheet, j+1, c)+"";
													xlvalues.add(xlvalue);
												}
												ph.miscellaneousData(xllabels, xlvalues);
												String expmsg=xl.getCellData(xlfile, casessheet, j+1, startingcolno);
												res=ph.updatePreferences(expmsg);
												xl.setCellData(xlfile, casessheet, j+1, startingcolno+1, ph.actmsg);
												break;
												
											}
											case("UPDATEPDC"):
											{
												String xlvalue="";
												ArrayList<String> xllabels=new ArrayList();
												ArrayList<String> xlvalues=new ArrayList();
												int abc=xl.getCellCount(xlfile, casessheet, j);
												xllabels.clear();
												xlvalues.clear();
												//logger.info("col ocunt "+colcount+" row is "+j);
												logger.info("UPDATE PDC EXECUTING "+j);
												for(int c=startingcolno+2;c<colcount;c++)
												{
													
													while((xl.getCellData(xlfile, casessheet, j, c)+"").length()==0)
													{
														break;
													}
													xlvalue=xl.getCellData(xlfile, casessheet, j, c)+"";
													xllabels.add(xlvalue);
													while((xl.getCellData(xlfile, casessheet, j+1, c)+"").length()==0)
													{
														break;
													}
													xlvalue=xl.getCellData(xlfile, casessheet, j+1, c)+"";
													xlvalues.add(xlvalue);
												}
												logger.info(xllabels);
												ph.pdcData(xllabels, xlvalues);
												String expmsg=xl.getCellData(xlfile, casessheet, j+1, startingcolno);
												res=ph.updatePreferences(expmsg);
												xl.setCellData(xlfile, casessheet, j+1, startingcolno+1, ph.actmsg);
												break;
											}
											
											case("CLOSECONFIGTRANS"):
											{
												res=ph.closeScreen();
												break;
											}
											case("LOGOUT"):
											{
												res=hp.Logout();
												break;
											}
											
										}
										String tsres=null;
										
										 Assert.assertEquals(res, true);
										if (res==true) 
										{
											tsres="Pass";
											xl.setCellData(xlfile, casessheet, j, 3, tsres);
											xl.fillGreenColor(xlfile, casessheet, j, 3);
											this.takeSnapShot(driver, "E://test.png") ; 
											
											//logger.info("Result which is getting printed for  Test Case ID "+tcid+" is "+tsres);
										} 
										else 
										{
											this.takeSnapShot(driver, "E:\\test.png");
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
