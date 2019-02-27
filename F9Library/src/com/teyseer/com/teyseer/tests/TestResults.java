package com.teyseer.com.teyseer.tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import com.focus.constants.LaunchApplication;
import com.focus.utils.XLutlitities;

public class TestResults extends LaunchApplication
{
	XLutlitities xl=new XLutlitities();
	
	
  @Test(priority=3)
  /* METHOD TO UPDATE TEST RESULTS SHEET BY GATHERING THE RESULTS FROM THE RESPECTIVE EXCELS */
  public void testResults() throws IOException
  {
	  String resultsfile="\\\\DESKTOP-C918GTA\\Keywords\\Automation Test Cases\\Test Results.xlsx";
    	String Vouchersheet="Vouchers";
    	ArrayList<String> vouchernames=new ArrayList<String>();
    	ArrayList<String> ressheetnames=new ArrayList<String>();
    	ArrayList<String> reststcids=new ArrayList<String>();
    	ArrayList<String> vshtsids=new ArrayList<String>();
    	ArrayList<String> vshtstcids=new ArrayList<String>();
    	ArrayList<String> vsheetnames=new ArrayList<String>();
    	FileInputStream fi = new FileInputStream(resultsfile);
    	XSSFWorkbook wb = new XSSFWorkbook(fi);
    	int resrowcount=xl.getRowCount(resultsfile, Vouchersheet);
    	int shcount =wb.getNumberOfSheets();
    	logger.info("No of sheets "+shcount);
    	for(int sh=1;sh<shcount;sh++)
    	{
    		String shname=wb.getSheetName(sh);
    		//logger.info("sheet names are "+shname);
    		ressheetnames.add(shname);
    	}
    	for(int vouchersrow=1;vouchersrow<=resrowcount;vouchersrow++)
    	{
    		String vname=xl.getCellData(resultsfile, Vouchersheet, vouchersrow, 0);
    		vouchernames.add(vname);
    	}
    	ressheetnames.addAll(vouchernames);
    	Collections.reverse(ressheetnames);
    	for(String voucher : vouchernames)
    	{
    		try
    		{
    		//GET ALL THE SHEET NAMES WHICH ARE MENTIONED IN THE 1ST SHEET OF THE RESULTS SHEET
    		String voucherwb=voucher+".xlsx";
    		String voucherfile="\\DESKTOP-C918GTA\\Keywords\\Automation Test Cases\\"+voucherwb;
    		String testscenariossheet="TestScenarios";
    		FileInputStream vfi = new FileInputStream(voucherfile);
    		XSSFWorkbook vwb = new XSSFWorkbook(vfi);
    		int vshcount=vwb.getNumberOfSheets();
    		vsheetnames.clear();
    		for(int vsh=0;vsh<vshcount;vsh++)
    		{
    			String vshname=vwb.getSheetName(vsh);
    			logger.info("sheet names are "+vshname);
    			vsheetnames.add(vshname);
    		}
    		logger.info("Sheet names of file "+voucherfile+" are "+vsheetnames);
    		int rowcount=xl.getRowCount(voucherfile, testscenariossheet);
    		logger.info("Row count of vouches is "+rowcount);
    		for(int vouchersrow=1;vouchersrow<=rowcount;vouchersrow++)
    		{
    			String tsids=xl.getCellData(voucherfile, testscenariossheet, vouchersrow, 0);
    			vshtsids.add(tsids);
    		}
    		logger.info("test scenario id's of "+voucherwb+" are "+vshtsids);
    		for(String resshsheet: ressheetnames)
    				{
    					/* IF THE SHEET IS AVAILABEL GO TO THAT PARTICUAL EXCEL AND GET THE RESULTS OF EACH TEST CASE & TEST SCENARIO AND PRINT THE SAME TO THE RESPECTIVE RESULTS SHEET */
    					if(resshsheet.toUpperCase().contains(voucher.toUpperCase()))
    					{
    						try
    						{
    							int shrowcount=xl.getRowCount(resultsfile, resshsheet);
    							reststcids.clear();
    							for(int resshrow=1;resshrow<=shrowcount;resshrow++)
    							{
    								String restsid=xl.getCellData(resultsfile, resshsheet, resshrow, 0);
    								reststcids.add(restsid);
    							}
    							//logger.info("Test scenario/Test case ids's of sheet "+resshsheet+" are "+reststcids);
    							int rescount=0;
    							for(String vsheetname: vsheetnames)
    							{
    								//logger.info("vsheet is "+vsheetname);
    								int endindex=vsheetname.indexOf(" ");
    								if(endindex>0)
    								{
    								vsheetname.substring(0, endindex);
    								//logger.info("end index "+endindex+" latest is "+vsheetname.substring(0, endindex)+"Vsheet names "+vsheetnames+" vsheet name "+vsheetname.toUpperCase().substring(0,endindex)+" sh sheet ");
    								if(resshsheet.toUpperCase().contains(vsheetname.toUpperCase().substring(0,endindex)))
    								{
    									
    									//logger.info("sh sheet is "+resshsheet +" vssheet is "+vsheetname);
    									int vrowcount=xl.getRowCount(voucherfile, vsheetname);
    									vshtstcids.clear();
    									for(int vrow=1;vrow<=vrowcount;vrow++)
    									{
    										String vshtstcid=xl.getCellData(voucherfile, vsheetname, vrow, 0);
    										vshtstcids.add(vshtstcid);
    									}
    									reststcids.removeAll(Arrays.asList("TestCase Id"));
    									vshtstcids.removeAll(Arrays.asList("TestCase Id"));
    									//logger.info(" reststcids "+reststcids+" vshtstscids "+vshtstcids);
    									reststcids.addAll(vshtstcids);
    									Collections.reverse(reststcids);
    									
    									rowloop:
    									for(int vrow=1;vrow<=vrowcount;vrow++)
    									{
    									vslist:
    										for(String vshtstcid: vshtstcids)
    										{
    											reststcids.remove(reststcids.size()-1);
    											reslist:
    											for(String reststcid: reststcids)
    											{
    												//logger.info("row loop is "+vrow);
    												if(vshtstcid.length()>0)
    												{
    												if(reststcid.equalsIgnoreCase(vshtstcid))
    												{
    													String vshres;
    													rescount++;
    													//logger.info("vrow "+vrow+" Sheet "+vsheetname+" reststcid "+reststcid);
    													 vshres=xl.getCellData(voucherfile, vsheetname, vrow, 3);
    													if(vshres.toUpperCase().startsWith("TEST CASE RESULTS"))
    													{
    														vrow++;
    														vshres=xl.getCellData(voucherfile, vsheetname, vrow, 3);
    													}
    													//logger.info("row count for cases is "+rescount+" result is "+vshres);
    													if(vshres.equalsIgnoreCase("pass"))
    													{
    														xl.setCellData(resultsfile, resshsheet, rescount, 3, vshres);
    														xl.fillGreenColor(resultsfile, resshsheet, rescount, 3);
    													}
    													else if(vshres.equalsIgnoreCase("fail"))
    													{
    														xl.setCellData(resultsfile, resshsheet, rescount, 3, vshres);
    														xl.fillRedColor(resultsfile, resshsheet, rescount, 3);
    													}
    													else
    													{
    														logger.info(" res count "+rescount);
    														xl.setCellData(resultsfile, resshsheet, rescount, 3, "Blocked");
    													}
    													//logger.info("Result is "+vshres);
    													vrow++;
    													break reslist;
    												}
    												}
    												else
    												{
    													vrow++;
    													break reslist;
    												}
    											}
    										}
    									}
    								}
    							}
    							else
    							{
    								if(resshsheet.toUpperCase().contains(vsheetname.toUpperCase()))
    								{
    									
    									//logger.info("end index "+endindex+" latest is "+vsheetname.substring(0, endindex)+"Vsheet names "+vsheetnames+" vsheet name "+vsheetname.toUpperCase().substring(0,endindex)+" sh sheet ");
    									//logger.info("sh sheet is "+resshsheet +" vssheet is "+vsheetname);
    										int vrowcount=xl.getRowCount(voucherfile, vsheetname);
    										vshtstcids.clear();
    										for(int vrow=1;vrow<=vrowcount;vrow++)
    										{
    											String vshtstcid=xl.getCellData(voucherfile, vsheetname, vrow, 0);
    											vshtstcids.add(vshtstcid);
    										}
    										reststcids.addAll(vshtstcids);
    										Collections.reverse(reststcids);
    										int counta=1;
    										rowloop:
    										for(int vrow=1;vrow<=vrowcount;vrow++)
    										{
    										vslist:
    											for(String vshtstcid: vshtstcids)
    											{
    												reststcids.remove(reststcids.size()-1);
    												reslist:
    												for(String reststcid: reststcids)
    												{
    													
    													//logger.info("row loop is "+vrow);
    													if(vshtstcid.length()>0)
    													{
    														if(reststcid.equalsIgnoreCase(vshtstcid))
    														{
    															rescount++;
    															//logger.info("vrow "+vrow+" Sheet "+vsheetname+" reststcid "+reststcid);
    															String vshres=xl.getCellData(voucherfile, vsheetname, vrow, 3);
    															//logger.info("Count is "+rescount);
    															if(vshres.equalsIgnoreCase("pass"))
    																{
    																	xl.setCellData(resultsfile, resshsheet, rescount, 3, vshres);
    																	xl.fillGreenColor(resultsfile, resshsheet, rescount, 3);
    																}
    																else if(vshres.equalsIgnoreCase("fail"))
    																{
    																	xl.setCellData(resultsfile, resshsheet, rescount, 3, vshres);
    																	xl.fillRedColor(resultsfile, resshsheet, rescount, 3);
    																}
    																else 
    																{
    																	xl.setCellData(resultsfile, resshsheet, rescount, 3, "Blocked");
    																}
    																//logger.info("Result is "+vshres);
    																vrow++;
    																break reslist;
    															}
    														}
    													
    													else
    													{
    														vrow++;
    														break reslist;
    													}
    												
    												}
    											}
    										}
    									
    								
    										
    								}
    							}
    								//count++;
    						}
    					}
    						catch(Exception e)
    						{
    							logger.info("Sheet with name "+testscenariossheet+" is not available for "+voucherfile);
    						}
    						
    					}
    				}
    			}
    		
    		catch(Exception e)
    		{
    			logger.info("No sheet avaialble");
    		}
    		}
    	//logger.info("All voucher names are "+vouchernames);
    }
}
