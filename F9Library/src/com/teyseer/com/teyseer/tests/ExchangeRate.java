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
import com.focus.utils.XLutlitities;
import com.teyseer.com.library.ExchangeRateDefinition;
import com.teyseer.com.library.UnitConversionHomePage;

public class ExchangeRate extends LaunchApplication
{
	XLutlitities xl=new XLutlitities();
	public String sheet="XLNames";
	public ArrayList<String> xlnames= new ArrayList<String>();
	int startingcolno=5;
	HomePage hp = new HomePage();
	Menus menu=new Menus();
	ExchangeRateDefinition erd=new ExchangeRateDefinition();
	@Parameters({ "parentxlfile" })
	@Test (priority=1)
	public void exchRateNames(String parentxlfile) throws IOException
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
  public void exchRate() throws IOException, InterruptedException 
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
								case("EXCHRTDELETE"):
								{
									erd.exchrateClear();
									ArrayList<String> xlexrtnames = new ArrayList();
									ArrayList<String> xlexrtvalues= new ArrayList();
									xlexrtnames.clear();
									xlexrtvalues.clear();
									String value;
									for(int k=startingcolno+3;k<colcount;k++)
									{
										while((xl.getCellData(xlfile, casessheet, j, k)+"").length()==0)
										{
											break;
										}
										value = xl.getCellData(xlfile, casessheet, j, k)+"";
										xlexrtnames.add(value);
									}
									
									for(int k=startingcolno+3;k<colcount;k++)
									{
										while((xl.getCellData(xlfile, casessheet, j+1, k)+"").length()==0)
										{
											break;
										}
										value=xl.getCellData(xlfile, casessheet, j+1, k);
										xlexrtvalues.add(value);
										
									}
									erd.exchRateHeader(xlexrtnames, xlexrtvalues);
									String expmsg;
									while((xl.getCellData(xlfile, casessheet, j+1, startingcolno).length())==0)
									{
										break;
										
									}
									expmsg= xl.getCellData(xlfile, casessheet, j+1, startingcolno)+"";
									res= erd.exchrateDelete(expmsg);
									break;
								}
								case("EXCHRTSAVE"):
								{
									erd.exchrateClear();
									ArrayList<String> xlexrtnames = new ArrayList();
									ArrayList<String> xlexrtvalues= new ArrayList();
									xlexrtnames.clear();
									xlexrtvalues.clear();
									String value;
									for(int k=startingcolno+3;k<colcount;k++)
									{
										while((xl.getCellData(xlfile, casessheet, j, k)+"").length()==0)
										{
											break;
										}
										value = xl.getCellData(xlfile, casessheet, j, k)+"";
										xlexrtnames.add(value);
									}
									
									for(int k=startingcolno+3;k<colcount;k++)
									{
										while((xl.getCellData(xlfile, casessheet, j+1, k)+"").length()==0)
										{
											break;
										}
										value=xl.getCellData(xlfile, casessheet, j+1, k);
										xlexrtvalues.add(value);
										
									}
									erd.exchRateHeader(xlexrtnames, xlexrtvalues);
									logger.info("Header names "+xlexrtnames +" vals "+xlexrtvalues);
									xlexrtvalues.clear();
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
											xlexrtvalues.add(value);
											
										}
										//logger.info("xlexrtnames.size()-1 "+xlexrtnames.size()+" startingcolno+xlexrtnames.size()-1)) "+startingcolno+ " xlexrtnames "+xlexrtnames);
										ArrayList<String> xlexrtbodynames=new ArrayList(xlexrtnames.subList(2, xlexrtnames.size()));
										ArrayList<String> xlexrtbodyvalues=new ArrayList(xlexrtvalues.subList(2, xlexrtvalues.size()));
										erd.exchRateBody(rowno, xlexrtbodynames, xlexrtbodyvalues);
										//logger.info("Body details "+xlunitconvheadernames+" "+xlunitconvheadervalues+" xlrow "+xlrowno);
										xlexrtvalues.clear();
									}
									String expmsg;
									while((xl.getCellData(xlfile, casessheet, j+1, startingcolno).length())==0)
									{
										break;
										
									}
									expmsg= xl.getCellData(xlfile, casessheet, j+1, startingcolno)+"";
									res=erd.exchrateSave(expmsg);
									xl.setCellData(xlfile, casessheet, j+1, startingcolno+1, erd.actmsg);
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
