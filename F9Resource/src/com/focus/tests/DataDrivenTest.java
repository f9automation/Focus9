package com.focus.tests;

import java.io.IOException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.focus.constants.LaunchApplication;
import com.focus.constants.HomePage;
import com.focus.utils.XLutlitities;

public class DataDrivenTest extends LaunchApplication
{
	
		XLutlitities xl=new XLutlitities();
		public String xlfile="E:\\Eclipse Workspace\\TestProject170118\\src\\Keywords\\DataDrivenTest.xlsx";
		public String xlsheet="TCSheet";
		public int j=1;
	  
		
 @Test (dataProvider="login")
  public void login(String username, String password, String compid) throws InterruptedException, IOException
  { 
	 System.out.println(username+" "+password+" "+compid+" ");
	 boolean res;
	 HomePage hp= new HomePage();
	 res=hp.LoginApp(username, password, compid);
	 
	 if (res) 
		{
			xl.setCellData(xlfile, xlsheet, j, 3, "Pass");
		} 
	 else 
		{
			xl.setCellData(xlfile, xlsheet, j, 3, "Fail");
		}
		j++;
	 
  }
 
 @DataProvider(name="login")
 public Object[][] logindata() throws IOException
 {
	  
	  int rc,cc;
	  rc=xl.getRowCount(xlfile,xlsheet);
	  cc=xl.getCellCount(xlfile, xlsheet, 1);
	  Object[][] data=new Object[rc][cc];
	  for (int i = 1; i <=rc; i++) 
	  {
		  data[i-1][0]=xl.getCellData(xlfile, xlsheet, i, 0);
		  data[i-1][1]=xl.getCellData(xlfile, xlsheet, i, 1);
		  data[i-1][2]=xl.getCellData(xlfile, xlsheet, i, 2);
	  }
	
	  return data; 

 }
}
