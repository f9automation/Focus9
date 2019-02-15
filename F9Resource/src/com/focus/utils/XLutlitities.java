package com.focus.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLutlitities 
{
	/* DECLARING EXCEL FILE VARIABLES */
	public FileInputStream fi;
	public FileOutputStream fo;
	public XSSFWorkbook wb;
	public XSSFSheet ws;
	public XSSFRow row;
	public XSSFCell cell;
	public XSSFCellStyle style;	

	
	/* METHOD TO COUNT NO OF ROWS IN A SHEET IN EXCEL BY PASSING EXEL FILE NAME AND SHEET NAME AS PARAMETERS */
	public int getRowCount(String xlfile,String xlsheet) throws IOException
	{		
		int rowcount;
		fi=new FileInputStream(xlfile);
		wb=new XSSFWorkbook(fi);
		ws=wb.getSheet(xlsheet);
		//TO GET LAST ROW NUMBER OF THAT SHEET
		rowcount=ws.getLastRowNum();
		return rowcount;		
	}
	
	/* METHOD TO COUNT NO OF COLUMNS OF A PARTICULAR ROW IN EXCEL SHEET BY PASSING EXCEL FILE NAME, SHEET NAME AND ROWNO */
	public int getCellCount(String xlfile,String xlsheet,int rownum) throws IOException
	{
		fi=new FileInputStream(xlfile);
		wb=new XSSFWorkbook(fi);
		ws=wb.getSheet(xlsheet);
		row=ws.getRow(rownum);
		//TO GET LAST COLUMN NUMBER OF THAT PARTICUAL ROW
		int cellcount=row.getLastCellNum();
		return cellcount;
	}
	
	/* METHOD TO GET DATA OF A CELL BY PASSING EXCEL NAME, SHEET NAME, ROW NUMBER AND COLUMN NUMBER AS PARAMETERS */
	public String getCellData(String xlfile,String xlsheet,int rownum,int colnum) throws IOException
	{
		fi=new FileInputStream(xlfile);
		wb=new XSSFWorkbook(fi);
		ws=wb.getSheet(xlsheet);
		row=ws.getRow(rownum);
		cell=row.getCell(colnum);
		//TO GET CELL VALUE OF THAT PARTICULA ROW AND COLUMN 
		String data=cell.getStringCellValue();
		//IF THAT PARTICULAR CELL VALUE IS EMPTY OR NULL THEN INCREASE THE COLUMN NUMBER 
		if(data==" " || data=="null")
		{
			row.cellIterator();
			
		}	
		return data;
	}
	
	
	/* METHOD TO WRITE DATA TO A CELL BY PASSING INPUT EXCEL NAME, SHEET NAME, ROW NUMBER, COLUMN NUMBER, DATA TO WRITE AND THE OUTPUT FILE AS PARAMETERS */
	public void setCellData(String inputxlfile,String xlsheet,
			     int rownum,int colnum,String data,String outputxlfile) throws IOException
	{
		fi=new FileInputStream(inputxlfile);
		wb=new XSSFWorkbook(fi);
		ws=wb.getSheet(xlsheet);
		row=ws.getRow(rownum);
		//TO CREATE AN EMPTY CELL AT THE GIVEN COLUMN NUMBER
		cell=row.createCell(colnum);
		cell.setCellValue(data);
		//WRITE THE DATA TO THE OUTPUT FILE
		fo=new FileOutputStream(outputxlfile);
		wb.write(fo);
		wb.close();
		fi.close();
		fo.close();
		
	}
	
	/* METHOD TO WRITE DATA TO A CELL BY PASSING THE OUTPUT FILENAME, SHEET NAME, ROW NUMBER , COLUMN NUMBER AND THE DATA AS PARAMETERS */
	public void setCellData(String xlfile,String xlsheet,int rownum,int colnum,String data) throws IOException
	{
		fi=new FileInputStream(xlfile);
		wb=new XSSFWorkbook(fi);
		ws=wb.getSheet(xlsheet);
		row=ws.getRow(rownum);
		//TO CREATE AN EMPTY CELL AT THE GIVEN COLUMN NUMBER
		cell=row.createCell(colnum);
		cell.setCellValue(data);
		fo=new FileOutputStream(xlfile);
		wb.write(fo);
		wb.close();
		fi.close();
		fo.close();
		
	}
	
	/* METHOD TO FILL GREEN COLOR TO A PARTICUAL CELL BY PASSING EXCEL FILE NAME, SHEET NAME, ROW NUMBER AND COLUMN NUMBER AS PARAMETERS */
	public void fillGreenColor(String xlfile,String xlsheet,int rownum,int colnum) throws IOException
	{
		fi=new FileInputStream(xlfile);
		wb=new XSSFWorkbook(fi);
		ws=wb.getSheet(xlsheet);
		row=ws.getRow(rownum);
		cell=row.getCell(colnum);
		//TO FILL FOREGROUND COLOR OF THAT PARTICUAL CELL AS GREEN
		style=wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		//TO FILL THE PATTERN AS A SOLID COLOR
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND); 
		cell.setCellStyle(style);
		fo=new FileOutputStream(xlfile);
		wb.write(fo);
		wb.close();
		fi.close();
		fo.close();
	}
	
	/* METHOD TO FILL RED COLOR TO A PARTICUAL CELL BY PASSING EXCEL FILE NAME, SHEET NAME, ROW NUMBER AND COLUMN NUMBER AS PARAMETERS */
	public void fillRedColor(String xlfile,String xlsheet,int rownum,int colnum) throws IOException
	{
		fi=new FileInputStream(xlfile);
		wb=new XSSFWorkbook(fi);
		ws=wb.getSheet(xlsheet);
		row=ws.getRow(rownum);
		cell=row.getCell(colnum);
		//TO FILL FOREGROUND COLOR OF THAT PARTICUAL CELL AS RED
		style=wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.RED.getIndex());
		//TO FILL THE PATTERN AS A SOLID COLOR
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND); 
		cell.setCellStyle(style);		
		fo=new FileOutputStream(xlfile);
		wb.write(fo);
		wb.close();
		fi.close();
		fo.close();
	}
	/* 
	public XSSFRow getRowCount(String xlfile,String xlsheet,int rownum) throws IOException 
			{
		fi=new FileInputStream(xlfile);
		wb=new XSSFWorkbook(fi);
		ws=wb.getSheet(xlsheet);
		XSSFRow rowno=ws.getRow(rownum);
		return rowno;
			
			}
	*/
}
