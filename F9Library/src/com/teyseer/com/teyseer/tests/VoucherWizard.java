package com.teyseer.com.teyseer.tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import com.focus.constants.LaunchApplication;
import com.focus.utils.XLutlitities;

public class VoucherWizard extends LaunchApplication
{
  @Test
  public void voucherWizardHybrid() throws IOException 
  {
	  XLutlitities xl=new XLutlitities();
	  String xlfile="\\\\DESKTOP-C918GTA\\Keywords\\VoucherWizard.xlsx";
	  FileInputStream fi = new FileInputStream(xlfile);
	  XSSFWorkbook wb = new XSSFWorkbook(fi);
	  String senariossheet="TestScenarios";
	  String casessheet="";
	  int shcount=wb.getNumberOfSheets();
	  int tccount;
	  int tscount;
	  int colcount;
	  int rowcount;
	  String tcexeflg,tcid,tstcid,keyword;
	  boolean res=false;
	  for(int sh=1; sh<shcount; sh++)
	  {
		  
	  }
	}
}
