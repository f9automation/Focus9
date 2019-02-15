/*
package com.focus.tests;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import com.focus.constants.HomePage;
import com.focus.constants.Menus;
import com.focus.library.MasterActions;
import com.focus.library.MasterHomePage;
import com.focus.utils.XLutlitities;

public class LoginHybridTest extends HomePage {
	
	XLutlitities xl=new XLutlitities();
	public String xlfile="E:\\Eclipse Workspace\\TestProject170118\\src\\Keywords\\CompanyCreation.xlsx";
	public String tcsheet="TestScenarios";
	public String tssheet="";
	HomePage hp = new HomePage();
	Menus menu=new Menus();
	MasterActions ms = new MasterActions();
	MasterHomePage mhp = new MasterHomePage();
  @Test
  public void hybridTest() throws IOException, InterruptedException 
  {
	 
	    int tccount;
		int tscount;
		String tcexeflg,tcid,tstcid,keyword;
		boolean res=false;
		String tcres="";
		FileInputStream fi = new FileInputStream(xlfile);
		 XSSFWorkbook wb = new XSSFWorkbook(fi);
		 int shcount =wb.getNumberOfSheets();
		 logger.info("No of sheets "+shcount);
		 for(int sh=1; sh<shcount; sh++)
		 {
		tssheet=wb.getSheetName(sh);
		
		tccount=xl.getRowCount(xlfile, tcsheet);
		tscount=xl.getRowCount(xlfile, tssheet);
		
		for (int i = 1; i <=tccount; i++) 
		{
			System.out.println("tccount"+tccount);
			tcexeflg=xl.getCellData(xlfile, tcsheet, i, 2);
			if (tcexeflg.equalsIgnoreCase("Y")) 
			{
				tcid=xl.getCellData(xlfile, tcsheet, i, 0);
				for (int j = 1; j <=tscount ; j++) 
				{
					System.out.println("tscount"+tscount);
					tstcid=xl.getCellData(xlfile, tssheet, j, 0);
					System.out.println(tstcid);
					if (tcid.equalsIgnoreCase(tstcid)) 
					{
						keyword=xl.getCellData(xlfile, tssheet, j, 4);
						switch (keyword.toUpperCase()) 
						{
						case "COMPCREATION":
						{
							hp.compname=xl.getCellData(xlfile, tssheet, j, 5);
							hp.password=xl.getCellData(xlfile, tssheet, j, 6);
							res=hp.companyCreation(hp.compname, hp.password);
							break;
						}
										
						
						case("LOGIN"):
						{
							hp.username=xl.getCellData(xlfile, tssheet, j, 5);
							hp.password=xl.getCellData(xlfile, tssheet, j, 6);
							hp.compname=xl.getCellData(xlfile, tssheet, j, 7);
							System.out.println(hp.username);
							System.out.println(hp.password);
							System.out.println(hp.compname);
							res=hp.LoginApp(hp.username, hp.password, hp.compname);
							break;
						}
						
						case("THREETREE"):
						{
							menu.menu1=xl.getCellData(xlfile, tssheet, j, 5);
							menu.menu2=xl.getCellData(xlfile, tssheet, j, 6);
							menu.menu3=xl.getCellData(xlfile, tssheet, j, 7);
							menu.value1=xl.getCellData(xlfile, tssheet, j, 8);
							menu.expmsg=xl.getCellData(xlfile, tssheet, j, 9);
							res=menu.menuSelection(menu.menu1, menu.menu2, menu.menu3, menu.value1,menu.expmsg);
							break;
						}
						case("MASTERCREATE"):
						{
							ms.name=xl.getCellData(xlfile, tssheet, j, 5);
							ms.code=xl.getCellData(xlfile, tssheet, j, 6);
							ms.optioncontrol=xl.getCellData(xlfile, tssheet, j, 7);
							ms.optioncontrolname=xl.getCellData(xlfile, tssheet, j, 8);
							ms.expmsg=xl.getCellData(xlfile, tssheet, j, 9);
							mhp.masterNew();
							if(ms.optioncontrol=="" || ms.optioncontrolname=="")
							{
								res=ms.masterSave(ms.name, ms.code, ms.expmsg);
							}
							else
							{
							res=ms.masterSave(ms.name, ms.code, ms.optioncontrol, ms.optioncontrolname, ms.expmsg);
							}
							break;
						}
						
						case("MASTERUPDATE"):
						{
							
							mhp.master=xl.getCellData(xlfile, tssheet, j, 5);
							mhp.expcode=xl.getCellData(xlfile, tssheet, j,6);
							ms.name=xl.getCellData(xlfile, tssheet, j, 7);
							ms.code=xl.getCellData(xlfile, tssheet, j, 8);
							ms.optioncontrol=xl.getCellData(xlfile, tssheet, j, 9);
							ms.optioncontrolname=xl.getCellData(xlfile, tssheet, j, 10);
							ms.expmsg=xl.getCellData(xlfile, tssheet, j, 11);
							mhp.masterEdit( mhp.expcode);
							if(ms.optioncontrol=="" || ms.optioncontrolname=="")
							{
								res=ms.masterSave(ms.name, ms.code, ms.expmsg);
							}
							else
							{
							res=ms.masterSave(ms.name, ms.code, ms.optioncontrol, ms.optioncontrolname, ms.expmsg);
							}
							break;
						}
						
						case("LOGOUT"):
						{
							res=hp.Logout();
							break;
						}
						
						case("FOURTREE"):
						{
							menu.menu1=xl.getCellData(xlfile, tssheet, j, 5);
							menu.menu2=xl.getCellData(xlfile, tssheet, j, 6);
							menu.menu3=xl.getCellData(xlfile, tssheet, j, 7);
							menu.menu4=xl.getCellData(xlfile, tssheet, j, 8);
							menu.value1=xl.getCellData(xlfile, tssheet, j, 9);
							menu.expmsg=xl.getCellData(xlfile, tssheet, j, 10);
							System.out.println("menu1:"+menu.menu1+" menu2: "+menu.menu2+" menu3: "+menu.menu3+" menu4: "+menu.menu4);
							res=menu.menuSelection(menu.menu1, menu.menu2, menu.menu3,menu.menu4, menu.value1,menu.expmsg);
							break;
							
						}
						case("MASTERSELECTION"):
						{
							mhp.master=xl.getCellData(xlfile, tssheet, j, 5);
							mhp.expcode=xl.getCellData(xlfile, tssheet, j, 6);
							System.out.println("master name: "+mhp.master+"exp code: "+mhp.expcode);
							//res=mhp.masterSelection(mhp.master, mhp.expcode);
							break;
						}
							
						}
						String tsres=null;
						
						if (res==true) 
						{
							tsres="Pass";
							xl.setCellData(xlfile, tssheet, j, 3, tsres);
							System.out.println(tsres);
						} 
						else 
						{
							tsres="Fail";							
							xl.setCellData(xlfile, tssheet, j, 3, tsres);
							System.out.println(tsres);
						}
						
						try
						{
						if (!tcres.equalsIgnoreCase("FAIL")) 
						{
							//xl.getCellData(xlfile, xlsheet, rownum, colnum)
							xl.setCellData(xlfile, tcsheet, i, 3, "");
							
							tcres=tsres;
							System.out.println("tcres"+tcres);
						}
						}
						catch(Exception e)
						{
							System.out.println("exception from excel");
						}
					}
				}
				
				xl.setCellData(xlfile, tcsheet, i, 3, tcres);
			} 
			else 
			{
				xl.setCellData(xlfile, tcsheet, i, 3, "Blocked");
			}
			
		}
		 }
	}
	

  }

*/