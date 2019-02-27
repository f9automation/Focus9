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
	/* METHOD TO GET ALL THE EXCEL FILE NAMES WHICH NEED TO BE EXECUTED BY PASSING THE FILE NAME AS PARENTXLFILE PARAMETER */
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
		   		logger.info("Workbook's of Masters which need to be executed are "+xlnames);
				 for(String xlname: xlnames)
				 {
					//ORDER OF THE KEYWORDS WHICH NEED TO BE EXEUCTED W.R.T TRANSACTIONS
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
					 //LOOP TO GO TO EACH SHEET OF THE EXCEL WORKBOOK
					 for(int sh=1; sh<shcount; sh++)
					 {
						 casessheet=wb.getSheetName(sh);
						 logger.info("Checking In Workbook sheet "+casessheet+" of Workbook "+xlfile);
						 tccount=xl.getRowCount(xlfile, senariossheet);
						 tscount=xl.getRowCount(xlfile, casessheet);
						 // FOR THE FIRST SCENARIOS SHEET GO TO THE "EXECUTE" COLUMN AND GET THE INFORMATION
						 for (int i = 1; i <=tccount; i++) 
						 {
							tcexeflg=xl.getCellData(xlfile, senariossheet, i, 2);
							/* IF THE ID WHICH IS CAPTURED IN TEST SCENARIO IS EQUAL TO THAT OF THE TEST CASES ID THEN GET THE KEYWORD FOR THE RESPECTIVE TEST CASE ROW */
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
										/* GO TO RESPECTIVE SWITCH CASE WHICH MATCHES WITH THE KEYWORD WHICH IS SENT FROM EXCEL */
										switch (keyword.toUpperCase()) 
										{
										//LOGIN TO THE APPLICATION	
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
										//TO NAVIGATE TO THE MENU WHICH HAS TWO TREE STRUCTURE
											case("TWOTREE"):
											{
												menu.menu1=xl.getCellData(xlfile, casessheet, j, 5);
												menu.menu2=xl.getCellData(xlfile, casessheet, j, 6);
												menu.expname=xl.getCellData(xlfile, casessheet, j, 7);
												res=menu.menuSelection(menu.menu1, menu.menu2, menu.expname);
												break;
											}
											//TO NAVIGATE TO THE MENU WHICH HAS THREE TREE STRUCTURE
											case("THREETREE"):
											{
												menu.menu1=xl.getCellData(xlfile, casessheet, j, 5);
												menu.menu2=xl.getCellData(xlfile, casessheet, j, 6);
												menu.menu3=xl.getCellData(xlfile, casessheet, j, 7);
												menu.expname=xl.getCellData(xlfile, casessheet, j, 8);
												res=menu.menuSelection(menu.menu1, menu.menu2, menu.menu3, menu.expname);
												break;
											}
											//TO NAVIGATE TO THE MENU WHICH HAS FOUR TREE STRUCTURE
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
											//COMPANY CREATION
											case("COMPANYCREATION"):
											{
												hp.compname=xl.getCellData(xlfile, casessheet, j, 5);
												hp.password=xl.getCellData(xlfile, casessheet, j, 6);
												res=hp.companyCreation(hp.compname, hp.password);
												xl.setCellData(xlfile, casessheet, j, 7, hp.compmsg);
												
												break;
											}
											//CREATE NEW PRICE BOOK
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
											//UPDATE PRICE BOOK
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
											//CLEAR PRICE BOOK
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
												break;
											}
											//VERIFY AUTHORIZE WINDOW DISPLAY
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
											//COPY A PRICE BOOK VALUES AND PASTE IT TO OTHERS AND SAVE IT
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
											
											//COPY A PRICE BOOK VALUES AND PASTE IT TO OTHERS AND VERIFY THE VALUES
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
											//DELETE THE PRICE BOOK
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
												xl.setCellData(xlfile, casessheet, j+1, startingcolno+1, pb.actmsg);
												
												break;
											}
											//REMOVE CUSTOMIZATION AND VERIFY THE COLUMNS
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
											//REMOVE THE PRICE BOOK CUSTOMIZATION AND SAVE
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
													pb.priceBookCustomization(selectcustomizelems, false);
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
													break;
											
											
											}
											
											//ADD CUSTOMIZATION AND VERIFY THE COLUMNS 
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
														logger.info("colcount "+colcount+" & col no is "+l);
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
													break;
											
											
											}
											//ADD CUSTOMIZATION AND SAVE 
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
													for(String selectcustomize: selectcustomizelems)
													{
														res=pb.customizeVerification(selectcustomize, true);
														totresults.add(String.valueOf(res));
														xl.setCellData(xlfile, casessheet, j+1, rescol, pb.customizecolres);
														rescol+=2;
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
													
													break;
											
											
											}
											
											//CLOSE THE PRICE BOOK 
											case("PRICEBOOKCLOSE"):
											{
												pb.clearPriceBook();
												res=pb.closePriceBook();
												break;
											}
											//LOGOUT FROM THE APPLICATION
											case("LOGOUT"):
											{
												res=hp.Logout();
												break;
											}
											
										}
										String tsres=null;
										
										/* PRINT EACH TEST CASE RESULT AND ITS RESPECTIVE COLORS */
										if (res==true) 
										{
											tsres="Pass";
											xl.setCellData(xlfile, casessheet, j, 3, tsres);
											xl.fillGreenColor(xlfile, casessheet, j, 3);
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
								/* IF ANY OF THE TEST CASE WHICH IS LINKED WITH TEST SCENARIO ID IS A FAIL, THEN FAIL THAT RESPECTIVE TEST SCENARIO  AND FILL THE RESPECTIVE COLORS */
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
