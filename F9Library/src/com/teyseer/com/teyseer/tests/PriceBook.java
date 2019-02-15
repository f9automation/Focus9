package com.teyseer.com.teyseer.tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.focus.constants.HomePage;
import com.focus.constants.LaunchApplication;
import com.focus.constants.Menus;
//import com.focus.library.MasterActions;
import com.focus.library.MasterHomePage;
import com.focus.utils.XLutlitities;
import com.teyseer.com.library.PriceBookHomePage;
import com.teyseer.com.transactions.HomePageClose;
import com.teyseer.com.transactions.MasterData;
import com.teyseer.com.transactions.TransactionBody;
import com.teyseer.com.transactions.TransactionFooter;
import com.teyseer.com.transactions.TransactionHeader;
import com.teyseer.com.transactions.TransactionSave;
import com.teyseer.com.transactions.TransactionWorkflow;

public class PriceBook extends LaunchApplication
{
	XLutlitities xl=new XLutlitities();
	public String sheet="XLNames";
	public ArrayList<String> xlnames= new ArrayList<String>();
	int startingcolno=5;
	HomePage hp = new HomePage();
	Menus menu=new Menus();
	MasterHomePage mhp = new MasterHomePage();
	PriceBookHomePage pb=new PriceBookHomePage();
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
	  public void priceBook() throws IOException, InterruptedException 
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
											case("NEWPRICEBOOK"):
											{
												pb.clearPriceBook();
												ArrayList<String> xlpricebooknames=new ArrayList();
												ArrayList<String> xlpricebookvalues=new ArrayList();
												xlpricebooknames.clear();
												
												String value;
												for(int k=startingcolno+2;k<colcount;k++)
												{
													while((xl.getCellData(xlfile, casessheet, j, k)+"").length()==0)
													{
														break;
													}
													value=xl.getCellData(xlfile, casessheet, j, k)+"";
													xlpricebooknames.add(value);
												}
												int totbodyrows=tscount-j;
												for(int bodyrow=1;bodyrow<=totbodyrows;bodyrow++)
												{
													xlpricebookvalues.clear();
													for(int l=startingcolno+2;l<colcount;l++)
													{
														while((xl.getCellData(xlfile, casessheet, j+bodyrow, l)+"").length()==0)
														{
															break;
														}
														value=xl.getCellData(xlfile, casessheet, j+bodyrow, l)+"";
														xlpricebookvalues.add(value);
													}
													
													pb.priceBookHeaderData(xlpricebooknames, xlpricebookvalues);
													pb.priceBookBodyData(xlpricebooknames, xlpricebookvalues, bodyrow);
												}
												String expmsg;
												while((xl.getCellData(xlfile, casessheet, j+1, startingcolno)+"").length()==0)
												{
													break;
												}
												expmsg=xl.getCellData(xlfile, casessheet, j+1, startingcolno)+"";
												res=pb.savePriceBook(expmsg);
												xl.setCellData(xlfile, casessheet, j+1, startingcolno+1, pb.actmsg);
												
												break;
											}
											case("UPDATEPRICEBOOK"):
											{
												pb.clearPriceBook();

												ArrayList<String> xlpricebooknames=new ArrayList();
												ArrayList<String> xlpricebookvalues=new ArrayList();
												xlpricebooknames.clear();
												
												String value;
												for(int k=startingcolno+2;k<colcount;k++)
												{
													while((xl.getCellData(xlfile, casessheet, j, k)+"").length()==0)
													{
														break;
													}
													value=xl.getCellData(xlfile, casessheet, j, k)+"";
													xlpricebooknames.add(value);
												}
												
												int totbodyrows=tscount-j;
												for(int xlbodyrow=1;xlbodyrow<=totbodyrows;xlbodyrow++)
												{
													xlpricebookvalues.clear();
													for(int l=startingcolno+2;l<colcount;l++)
													{
														while((xl.getCellData(xlfile, casessheet, j+xlbodyrow, l)+"").length()==0)
														{
															break;
														}
														value=xl.getCellData(xlfile, casessheet, j+xlbodyrow, l)+"";
														xlpricebookvalues.add(value);
													}
													pb.priceBookHeaderData(xlpricebooknames, xlpricebookvalues);
													if(xlbodyrow==1)
													{
														pb.filterAndLoad();
													}
													pb.priceBookBodyData(xlpricebooknames, xlpricebookvalues, xlbodyrow);
												}
												String expmsg;
												while((xl.getCellData(xlfile, casessheet, j+1, startingcolno)+"").length()==0)
												{
													break;
												}
												expmsg=xl.getCellData(xlfile, casessheet, j+1, startingcolno)+"";
												res=pb.savePriceBook(expmsg);
												xl.setCellData(xlfile, casessheet, j+1, startingcolno+1, pb.actmsg);
												break;
												
											}
											case("PRICEBOOKCLEAR"):
											{
												pb.clearPriceBook();
												ArrayList<String> xlpricebooknames=new ArrayList();
												ArrayList<String> xlpricebookvalues=new ArrayList();
												xlpricebooknames.clear();
												
												String value;
												for(int k=startingcolno;k<colcount;k++)
												{
													while((xl.getCellData(xlfile, casessheet, j, k)+"").length()==0)
													{
														break;
													}
													value=xl.getCellData(xlfile, casessheet, j, k)+"";
													xlpricebooknames.add(value);
												}
												
												int totbodyrows=tscount-j;
												xlpricebookvalues.clear();
													for(int l=startingcolno;l<colcount;l++)
													{
														while((xl.getCellData(xlfile, casessheet, j+1, l)+"").length()==0)
														{
															break;
														}
														value=xl.getCellData(xlfile, casessheet, j+1, l)+"";
														xlpricebookvalues.add(value);
													}
													pb.priceBookHeaderData(xlpricebooknames, xlpricebookvalues);
													pb.filterAndLoad();
													res=pb.clearPriceBook();
													logger.info("REsul tis "+res);
													
												
												break;
											}
											case("AUTHORIZEWINDOWDISPLAY"):
											{
												pb.clearPriceBook();
												ArrayList<String> xlpricebooknames=new ArrayList();
												ArrayList<String> xlpricebookvalues=new ArrayList();
												xlpricebooknames.clear();
												
												String value;
												for(int k=startingcolno+2;k<colcount;k++)
												{
													while((xl.getCellData(xlfile, casessheet, j, k)+"").length()==0)
													{
														break;
													}
													value=xl.getCellData(xlfile, casessheet, j, k)+"";
													xlpricebooknames.add(value);
												}
												
												int totbodyrows=tscount-j;
												xlpricebookvalues.clear();
													for(int l=startingcolno+2;l<colcount;l++)
													{
														while((xl.getCellData(xlfile, casessheet, j+1, l)+"").length()==0)
														{
															break;
														}
														value=xl.getCellData(xlfile, casessheet, j+1, l)+"";
														xlpricebookvalues.add(value);
													}
													pb.priceBookHeaderData(xlpricebooknames, xlpricebookvalues);
													pb.filterAndLoad();
													res=pb.authorizeScreenDisplayPriceBook();
													logger.info("REsul tis "+res);
													
												
												break;
											
											}
											case("COPYPASTEPRICEBOOKSAVE"):
											{

												pb.clearPriceBook();
												ArrayList<String> xlpricebooknames=new ArrayList();
												ArrayList<String> xlpricebookvalues=new ArrayList();
												xlpricebooknames.clear();
												
												String value;
												for(int k=startingcolno+2;k<startingcolno+4;k++)
												{
													while((xl.getCellData(xlfile, casessheet, j, k)+"").length()==0)
													{
														break;
													}
													value=xl.getCellData(xlfile, casessheet, j, k)+"";
													xlpricebooknames.add(value);
												}
												
												int totbodyrows=tscount-j;
												xlpricebookvalues.clear();
													for(int l=startingcolno+2;l<startingcolno+4;l++)
													{
														while((xl.getCellData(xlfile, casessheet, j+1, l)+"").length()==0)
														{
															break;
														}
														value=xl.getCellData(xlfile, casessheet, j+1, l)+"";
														xlpricebookvalues.add(value);
													}
													logger.info("xlbooknames "+xlpricebooknames+" xl values "+xlpricebookvalues);
													pb.priceBookHeaderData(xlpricebooknames, xlpricebookvalues);
													pb.filterAndLoad();
													xlpricebooknames.clear();
													for(int k=startingcolno+4;k<colcount;k++)
													{
														while((xl.getCellData(xlfile, casessheet, j, k)+"").length()==0)
														{
															break;
														}
														value=xl.getCellData(xlfile, casessheet, j, k)+"";
														xlpricebooknames.add(value);
													}
													
													xlpricebookvalues.clear();
														for(int l=startingcolno+4;l<colcount;l++)
														{
															while((xl.getCellData(xlfile, casessheet, j+1, l)+"").length()==0)
															{
																break;
															}
															value=xl.getCellData(xlfile, casessheet, j+1, l)+"";
															xlpricebookvalues.add(value);
														}
														res=pb.pasteData(xlpricebooknames, xlpricebookvalues);
														boolean res2;
														String expmsg;
														while((xl.getCellData(xlfile, casessheet, j+1, startingcolno)+"").length()==0)
														{
															break;
														}
														expmsg=xl.getCellData(xlfile, casessheet, j+1, startingcolno)+"";
														res2=pb.savePriceBook(expmsg);
														if(res==true&&res2==true)
														{
															res=true;
														}
														else
														{
															res=false;
														}
												xl.setCellData(xlfile, casessheet, j+1, startingcolno+1, pb.actmsg);
												break;
											}
											case("COPYPASTEPRICEBOOK"):
											{
												pb.clearPriceBook();
												ArrayList<String> xlpricebooknames=new ArrayList();
												ArrayList<String> xlpricebookvalues=new ArrayList();
												xlpricebooknames.clear();
												
												String value;
												for(int k=startingcolno;k<startingcolno+2;k++)
												{
													while((xl.getCellData(xlfile, casessheet, j, k)+"").length()==0)
													{
														break;
													}
													value=xl.getCellData(xlfile, casessheet, j, k)+"";
													xlpricebooknames.add(value);
												}
												
												int totbodyrows=tscount-j;
												xlpricebookvalues.clear();
													for(int l=startingcolno;l<startingcolno+2;l++)
													{
														while((xl.getCellData(xlfile, casessheet, j+1, l)+"").length()==0)
														{
															break;
														}
														value=xl.getCellData(xlfile, casessheet, j+1, l)+"";
														xlpricebookvalues.add(value);
													}
													pb.priceBookHeaderData(xlpricebooknames, xlpricebookvalues);
													pb.filterAndLoad();
													xlpricebooknames.clear();
													for(int k=startingcolno+2;k<colcount;k++)
													{
														while((xl.getCellData(xlfile, casessheet, j, k)+"").length()==0)
														{
															break;
														}
														value=xl.getCellData(xlfile, casessheet, j, k)+"";
														xlpricebooknames.add(value);
													}
													
													xlpricebookvalues.clear();
														for(int l=startingcolno+2;l<colcount;l++)
														{
															while((xl.getCellData(xlfile, casessheet, j+1, l)+"").length()==0)
															{
																break;
															}
															value=xl.getCellData(xlfile, casessheet, j+1, l)+"";
															xlpricebookvalues.add(value);
														}
														res=pb.pasteData(xlpricebooknames, xlpricebookvalues);
														break;
											}
											case("PRICEBOOKDELETE"):
											{
												pb.clearPriceBook();
												ArrayList<String> xlpricebooknames=new ArrayList();
												ArrayList<String> xlpricebookvalues=new ArrayList();
												xlpricebooknames.clear();
												
												String value;
												for(int k=startingcolno+2;k<colcount;k++)
												{
													while((xl.getCellData(xlfile, casessheet, j, k)+"").length()==0)
													{
														break;
													}
													value=xl.getCellData(xlfile, casessheet, j, k)+"";
													xlpricebooknames.add(value);
												}
												int totbodyrows=tscount-j;
												xlpricebookvalues.clear();
													for(int l=startingcolno+2;l<colcount;l++)
													{
														while((xl.getCellData(xlfile, casessheet, j+1, l)+"").length()==0)
														{
															break;
														}
														value=xl.getCellData(xlfile, casessheet, j+1, l)+"";
														xlpricebookvalues.add(value);
													}
													pb.priceBookHeaderData(xlpricebooknames, xlpricebookvalues);
													pb.filterAndLoad();
												
												String expmsg;
												while((xl.getCellData(xlfile, casessheet, j+1, startingcolno)+"").length()==0)
												{
													break;
												}
												expmsg=xl.getCellData(xlfile, casessheet, j+1, startingcolno)+"";
												res=pb.deletePriceBook(expmsg);
												//logger.info("res of delete is "+res);
												xl.setCellData(xlfile, casessheet, j+1, startingcolno+1, pb.actmsg);
												
												break;
											}
											case("REMOVEPRICEBOOKCUSTOMIZEVIEW"):
											{

												pb.clearPriceBook();
												ArrayList<String> xlpricebooknames=new ArrayList();
												ArrayList<String> xlpricebookvalues=new ArrayList();
												xlpricebooknames.clear();
												
												String value;
												for(int k=startingcolno;k<colcount;k++)
												{
													while((xl.getCellData(xlfile, casessheet, j, k)+"").length()==0)
													{
														break;
													}
													value=xl.getCellData(xlfile, casessheet, j, k)+"";
													xlpricebooknames.add(value);
												}
												
												int totbodyrows=tscount-j;
												xlpricebookvalues.clear();
													for(int l=startingcolno;l<colcount;l++)
													{
														while((xl.getCellData(xlfile, casessheet, j+1, l)+"").length()==0)
														{
															break;
														}
														value=xl.getCellData(xlfile, casessheet, j+1, l)+"";
														xlpricebookvalues.add(value);
													}
													//logger.info("xlbooknames "+xlpricebooknames+" xl values "+xlpricebookvalues);
													pb.priceBookHeaderData(xlpricebooknames, xlpricebookvalues);
													pb.filterAndLoad();
													ArrayList<String> selectcustomize=new ArrayList();
													selectcustomize.clear();
													for(int l=startingcolno+1;l<colcount;l++)
													{
														while((xl.getCellData(xlfile, casessheet, j+1, l)+"").length()==0)
														{
															break;
														}
														value=xl.getCellData(xlfile, casessheet, j+1, l)+"";
														selectcustomize.add(value);
													}
													res=pb.priceBookCustomization(selectcustomize, false);
													//logger.info("REsul tis "+res);
													
												break;
											
											
											}
											case("REMOVEPRICEBOOKCUSTOMIZESAVE"):
											{

												pb.clearPriceBook();
												ArrayList<String> xlpricebooknames=new ArrayList();
												ArrayList<String> xlpricebookvalues=new ArrayList();
												xlpricebooknames.clear();
												
												String value;
												for(int k=startingcolno+2;k<startingcolno+4;k++)
												{
													while((xl.getCellData(xlfile, casessheet, j, k)+"").length()==0)
													{
														break;
													}
													value=xl.getCellData(xlfile, casessheet, j, k)+"";
													xlpricebooknames.add(value);
												}
												
												int totbodyrows=tscount-j;
												xlpricebookvalues.clear();
													for(int l=startingcolno+2;l<startingcolno+4;l++)
													{
														while((xl.getCellData(xlfile, casessheet, j+1, l)+"").length()==0)
														{
															break;
														}
														value=xl.getCellData(xlfile, casessheet, j+1, l)+"";
														xlpricebookvalues.add(value);
													}
													//logger.info("xlbooknames "+xlpricebooknames+" xl values "+xlpricebookvalues);
													pb.priceBookHeaderData(xlpricebooknames, xlpricebookvalues);
													pb.filterAndLoad();
													ArrayList<String> selectcustomizelems=new ArrayList();
													selectcustomizelems.clear();
													for(int l=startingcolno+4;l<colcount;l+=2)
													{
														while((xl.getCellData(xlfile, casessheet, j+1, l)+"").length()==0)
														{
															break;
														}
														value=xl.getCellData(xlfile, casessheet, j+1, l)+"";
														selectcustomizelems.add(value);
													}
													//logger.info("selectcustmize elems are "+selectcustomizelems);
													pb.priceBookCustomization(selectcustomizelems, true);
													String expmsg;
													while((xl.getCellData(xlfile, casessheet, j+1, startingcolno)+"").length()==0)
													{
														break;
													}
													expmsg=xl.getCellData(xlfile, casessheet, j+1, startingcolno)+"";
													pb.savePriceBook(expmsg);
													xl.setCellData(xlfile, casessheet, j+1, startingcolno+1, pb.actmsg);
													pb.clearPriceBook();
													pb.priceBookHeaderData(xlpricebooknames, xlpricebookvalues);
													pb.filterAndLoad();
													ArrayList<String> totresults=new ArrayList();
													totresults.clear();
													int rescol=10;
													int resind=0;
													//logger.info("tot results "+pb.customizecolresults);
													for(String selectcustomize: selectcustomizelems)
													{
														res=pb.customizeVerification(selectcustomize, false);
														totresults.add(String.valueOf(res));
														xl.setCellData(xlfile, casessheet, j+1, rescol, pb.customizecolres);
														rescol+=2;
														//resind+=1;
														
													}
													if(totresults.contains("false"))
													{
														res=false;
													}
													else
													{
														res=true;
													}
													//logger.info("REsul tis "+res);
													
													//Thread.sleep(5000);
												break;
											
											
											}
											case("ADDPRICEBOOKCUSTOMIZEVIEW"):
											{

												pb.clearPriceBook();
												ArrayList<String> xlpricebooknames=new ArrayList();
												ArrayList<String> xlpricebookvalues=new ArrayList();
												xlpricebooknames.clear();
												
												String value;
												for(int k=startingcolno;k<colcount;k++)
												{
													while((xl.getCellData(xlfile, casessheet, j, k)+"").length()==0)
													{
														break;
													}
													value=xl.getCellData(xlfile, casessheet, j, k)+"";
													xlpricebooknames.add(value);
												}
												
												int totbodyrows=tscount-j;
												xlpricebookvalues.clear();
													for(int l=startingcolno;l<colcount;l++)
													{
														while((xl.getCellData(xlfile, casessheet, j+1, l)+"").length()==0)
														{
															break;
														}
														value=xl.getCellData(xlfile, casessheet, j+1, l)+"";
														xlpricebookvalues.add(value);
													}
													logger.info("xlbooknames "+xlpricebooknames+" xl values "+xlpricebookvalues);
													pb.priceBookHeaderData(xlpricebooknames, xlpricebookvalues);
													pb.filterAndLoad();
													ArrayList<String> selectcustomize=new ArrayList();
													selectcustomize.clear();
													for(int l=startingcolno+2;l<colcount;l++)
													{
														while((xl.getCellData(xlfile, casessheet, j+1, l)+"").length()==0)
														{
															break;
														}
														value=xl.getCellData(xlfile, casessheet, j+1, l)+"";
														selectcustomize.add(value);
													}
													res=pb.priceBookCustomization(selectcustomize, true);
													//logger.info("REsul tis "+res);
													
												break;
											
											
											}
											case("ADDPRICEBOOKCUSTOMIZESAVE"):
											{

												pb.clearPriceBook();
												ArrayList<String> xlpricebooknames=new ArrayList();
												ArrayList<String> xlpricebookvalues=new ArrayList();
												xlpricebooknames.clear();
												
												String value;
												for(int k=startingcolno+2;k<startingcolno+4;k++)
												{
													while((xl.getCellData(xlfile, casessheet, j, k)+"").length()==0)
													{
														break;
													}
													value=xl.getCellData(xlfile, casessheet, j, k)+"";
													xlpricebooknames.add(value);
												}
												
												int totbodyrows=tscount-j;
												xlpricebookvalues.clear();
													for(int l=startingcolno+2;l<startingcolno+4;l++)
													{
														while((xl.getCellData(xlfile, casessheet, j+1, l)+"").length()==0)
														{
															break;
														}
														value=xl.getCellData(xlfile, casessheet, j+1, l)+"";
														xlpricebookvalues.add(value);
													}
													//logger.info("xlbooknames "+xlpricebooknames+" xl values "+xlpricebookvalues);
													pb.priceBookHeaderData(xlpricebooknames, xlpricebookvalues);
													pb.filterAndLoad();
													ArrayList<String> selectcustomizelems=new ArrayList();
													selectcustomizelems.clear();
													for(int l=startingcolno+4;l<colcount;l+=2)
													{
														while((xl.getCellData(xlfile, casessheet, j+1, l)+"").length()==0)
														{
															break;
														}
														value=xl.getCellData(xlfile, casessheet, j+1, l)+"";
														selectcustomizelems.add(value);
													}
													logger.info("selectcustmize elems are "+selectcustomizelems);
													pb.priceBookCustomization(selectcustomizelems, true);
													String expmsg;
													while((xl.getCellData(xlfile, casessheet, j+1, startingcolno)+"").length()==0)
													{
														break;
													}
													expmsg=xl.getCellData(xlfile, casessheet, j+1, startingcolno)+"";
													pb.savePriceBook(expmsg);
													xl.setCellData(xlfile, casessheet, j+1, startingcolno+1, pb.actmsg);
													pb.clearPriceBook();
													pb.priceBookHeaderData(xlpricebooknames, xlpricebookvalues);
													pb.filterAndLoad();
													ArrayList<String> totresults=new ArrayList();
													totresults.clear();
													int rescol=10;
													int resind=0;
													//logger.info("tot results "+pb.customizecolresults);
													for(String selectcustomize: selectcustomizelems)
													{
														res=pb.customizeVerification(selectcustomize, true);
														totresults.add(String.valueOf(res));
														xl.setCellData(xlfile, casessheet, j+1, rescol, pb.customizecolres);
														rescol+=2;
														//resind+=1;
														
													}
													if(totresults.contains("false"))
													{
														res=false;
													}
													else
													{
														res=true;
													}
													logger.info("REsul tis "+res);
													
													//Thread.sleep(5000);
												break;
											
											
											}
											case("PRICEBOOKCLOSE"):
											{
												pb.clearPriceBook();
												res=pb.closePriceBook();
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
											//logger.info("Result which is getting printed for  Test Case ID "+tcid+" is "+tsres);
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
