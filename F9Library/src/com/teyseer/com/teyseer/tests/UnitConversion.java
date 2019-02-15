package com.teyseer.com.teyseer.tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.focus.constants.HomePage;
import com.focus.constants.LaunchApplication;
import com.focus.constants.Menus;

import com.focus.utils.XLutlitities;
import com.teyseer.com.library.PriceBookHomePage;
import com.teyseer.com.library.UnitConversionHomePage;

public class UnitConversion  extends LaunchApplication
{
	XLutlitities xl=new XLutlitities();
	public String sheet="XLNames";
	public ArrayList<String> xlnames= new ArrayList<String>();
	int startingcolno=5;
	HomePage hp = new HomePage();
	Menus menu=new Menus();
	UnitConversionHomePage uhp=new UnitConversionHomePage();
	@Parameters({ "parentxlfile" })
	@Test (priority=1)
	public void UnitConversionNames(String parentxlfile) throws IOException
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
	
@Test(priority=2)
  public void unitConversion() throws IOException, InterruptedException 
  {

		logger.info("Workbook's of Masters which need to be executed are "+xlnames);
	 for(String xlname: xlnames)
	 {
		 ArrayList<String> keywordorder=new ArrayList<String>(Arrays.asList("VOUCHERWORKFLOW","VOUCHERHEADERDATA", "VOUCHERBODYDATA", "VOUCHERFOOTERDATA", "VOUCHERSAVE", "NEWREFERENCE SAVE"));
		 ArrayList<String> actkeywords=new ArrayList<String>();
		 String xlfile="F:\\Focus9Automation\\Focus9Automation\\Resources\\src\\Keywords\\Automation Test Cases\\"+xlname;
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
								
								case("LOGOUT"):
								{
									res=hp.Logout();
									break;
								}
								case("UNITCONVSAVE"):
								{
									uhp.unitConvClear();
									String value;
									ArrayList<String> xlunitconvnames= new ArrayList();
									ArrayList<String> xlunitconvvalues=new ArrayList();
									xlunitconvnames.clear();
									xlunitconvvalues.clear();
									for(int k=startingcolno+3;k<colcount;k++)
									{
										while((xl.getCellData(xlfile, casessheet, j, k)+"").length()==0)
										{
											break;
										}
										value = xl.getCellData(xlfile, casessheet, j, k)+"";
										xlunitconvnames.add(value);
									}
									
									for(int k=startingcolno+3;k<colcount;k++)
									{
										while((xl.getCellData(xlfile, casessheet, j+1, k)+"").length()==0)
										{
											break;
										}
										value=xl.getCellData(xlfile, casessheet, j+1, k);
										xlunitconvvalues.add(value);
										
									}
									uhp.unitConvHeaderDetails(xlunitconvnames, xlunitconvvalues);
									//logger.info("rowcount "+rowcount+" j value "+j);
									//if(rowcount==j+1)
									xlunitconvvalues.clear();
									for(int xlrowno=j+1; xlrowno<=rowcount;xlrowno++)
									{
										while((xl.getCellData(xlfile, casessheet, xlrowno, startingcolno+2)+"").length()==0)
										{
											break;
										}
										String rowno = xl.getCellData(xlfile, casessheet, xlrowno, startingcolno+2)+"";
										boolean togetxlrowno = false;
										if(rowno.length()>0)
										{
											togetxlrowno=true;
										}
										for(int k=startingcolno+3;k<colcount;k++)
										{
											while((xl.getCellData(xlfile, casessheet, xlrowno, k)+"").length()==0)
											{
												break;
											}
											value=xl.getCellData(xlfile, casessheet, xlrowno, k);
											xlunitconvvalues.add(value);
											
										}
										uhp.unitConvBodyDetails(xlunitconvnames, xlunitconvvalues, rowno, togetxlrowno);
										//logger.info("Body details "+xlunitconvheadernames+" "+xlunitconvheadervalues+" xlrow "+xlrowno);
										xlunitconvvalues.clear();
									}
									String expmsg;
									while((xl.getCellData(xlfile, casessheet, j+1, startingcolno).length())==0)
									{
										break;
										
									}
									expmsg= xl.getCellData(xlfile, casessheet, j+1, startingcolno)+"";
									res=uhp.unitConvSave(expmsg);
									xl.setCellData(xlfile, casessheet, j+1, startingcolno+1, uhp.actmsg);
									break;
								}
								case("UNITCONVDELETE"):
								{
									uhp.unitConvClear();
									String value;
									ArrayList<String> xlunitconvheadernames= new ArrayList();
									ArrayList<String> xlunitconvheadervalues=new ArrayList();
									xlunitconvheadernames.clear();
									xlunitconvheadervalues.clear();
									for(int k=startingcolno+3;k<colcount;k++)
									{
										while((xl.getCellData(xlfile, casessheet, j, k)+"").length()==0)
										{
											break;
										}
										value = xl.getCellData(xlfile, casessheet, j, k)+"";
										xlunitconvheadernames.add(value);
									}
									
									for(int k=startingcolno+3;k<colcount;k++)
									{
										while((xl.getCellData(xlfile, casessheet, j+1, k)+"").length()==0)
										{
											break;
										}
										value=xl.getCellData(xlfile, casessheet, j+1, k);
										xlunitconvheadervalues.add(value);
										
									}
									uhp.unitConvHeaderDetails(xlunitconvheadernames, xlunitconvheadervalues);
									String expmsg;
									while((xl.getCellData(xlfile, casessheet, j+1, startingcolno).length())==0)
									{
										break;
										
									}
									expmsg= xl.getCellData(xlfile, casessheet, j+1, startingcolno)+"";
									res=uhp.unitConvDelete(expmsg);
									xl.setCellData(xlfile, casessheet, j+1, startingcolno+1, uhp.actmsg);
									break;
								}
								case("UNITCONVCLOSE"):
								{
									uhp.unitConvClose();
									//driver.quit();
									break;
								}
								case("LOADFROMVERIFY"):
								{
									uhp.unitConvClear();
									String value;
									ArrayList<String> xlunitconvheadernames= new ArrayList();
									ArrayList<String> xlunitconvheadervalues=new ArrayList();
									xlunitconvheadernames.clear();
									xlunitconvheadervalues.clear();
									for(int k=startingcolno+3;k<colcount;k++)
									{
										while((xl.getCellData(xlfile, casessheet, j, k)+"").length()==0)
										{
											break;
										}
										value = xl.getCellData(xlfile, casessheet, j, k)+"";
										xlunitconvheadernames.add(value);
									}
									
									for(int k=startingcolno+3;k<colcount;k++)
									{
										while((xl.getCellData(xlfile, casessheet, j+1, k)+"").length()==0)
										{
											break;
										}
										value=xl.getCellData(xlfile, casessheet, j+1, k);
										xlunitconvheadervalues.add(value);
										
									}
									res=uhp.checkUnitConvLoadFrom(xlunitconvheadernames, xlunitconvheadervalues);
									
									break;
								}
								case("LOADFROMSAVE"):
								{

									uhp.unitConvClear();
									String value;
									ArrayList<String> xlunitconvheadernames= new ArrayList();
									ArrayList<String> xlunitconvheadervalues=new ArrayList();
									xlunitconvheadernames.clear();
									xlunitconvheadervalues.clear();
									for(int k=startingcolno+3;k<colcount;k++)
									{
										while((xl.getCellData(xlfile, casessheet, j, k)+"").length()==0)
										{
											break;
										}
										value = xl.getCellData(xlfile, casessheet, j, k)+"";
										xlunitconvheadernames.add(value);
									}
									
									for(int k=startingcolno+3;k<colcount;k++)
									{
										while((xl.getCellData(xlfile, casessheet, j+1, k)+"").length()==0)
										{
											break;
										}
										value=xl.getCellData(xlfile, casessheet, j+1, k);
										xlunitconvheadervalues.add(value);
										
									}
									res=uhp.checkUnitConvLoadFrom(xlunitconvheadernames, xlunitconvheadervalues);
									if(res==true)
									{
										/*
										uhp.unitConvClear();
										xlunitconvheadernames.clear();
										xlunitconvheadervalues.clear();
										for(int k=startingcolno+3;k<colcount;k++)
										{
											while((xl.getCellData(xlfile, casessheet, j, k)+"").length()==0)
											{
												break;
											}
											value = xl.getCellData(xlfile, casessheet, j, k)+"";
											xlunitconvheadernames.add(value);
										}
										
										for(int k=startingcolno+3;k<colcount;k++)
										{
											while((xl.getCellData(xlfile, casessheet, j+1, k)+"").length()==0)
											{
												break;
											}
											value=xl.getCellData(xlfile, casessheet, j+1, k);
											xlunitconvheadervalues.add(value);
											
										}
										uhp.unitConvHeaderDetails(xlunitconvheadernames, xlunitconvheadervalues);
										logger.info("Xl headername s"+xlunitconvheadernames+" xl values "+xlunitconvheadervalues);
										//String value;
										//ArrayList<String> xlunitconvheadernames= new ArrayList();
										//ArrayList<String> xlunitconvheadervalues=new ArrayList();
										uhp.unitConvHeaderDetails(xlunitconvheadernames, xlunitconvheadervalues);
										//logger.info("rowcount "+rowcount+" j value "+j);
										//if(rowcount==j+1)
										 * */
										 
										xlunitconvheadervalues.clear();
										for(int xlrowno=j+1; xlrowno<=rowcount;xlrowno++)
										{
											while((xl.getCellData(xlfile, casessheet, xlrowno, startingcolno+2)+"").length()==0)
											{
												break;
											}
											String rowno = xl.getCellData(xlfile, casessheet, xlrowno, startingcolno+2)+"";
											boolean togetxlrowno = false;
											if(rowno.length()>0)
											{
												togetxlrowno=true;
											}
											for(int k=startingcolno+3;k<colcount;k++)
											{
												while((xl.getCellData(xlfile, casessheet, xlrowno, k)+"").length()==0)
												{
													break;
												}
												value=xl.getCellData(xlfile, casessheet, xlrowno, k);
												xlunitconvheadervalues.add(value);
												
											}
											uhp.unitConvBodyDetails(xlunitconvheadernames, xlunitconvheadervalues, rowno, togetxlrowno);
											xlunitconvheadervalues.clear();
										}
										String expmsg;
										while((xl.getCellData(xlfile, casessheet, j+1, startingcolno).length())==0)
										{
											break;
											
										}
										expmsg= xl.getCellData(xlfile, casessheet, j+1, startingcolno)+"";
										res=uhp.unitConvSave(expmsg);
										xl.setCellData(xlfile, casessheet, j+1, startingcolno+1, uhp.actmsg);
										break;
									
									}
									else
									{
										res=false;
										uhp.unitConvClear();
									}
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
