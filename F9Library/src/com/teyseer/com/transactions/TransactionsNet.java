package com.teyseer.com.transactions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import com.focus.constants.LaunchApplication;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Ordering;

public class TransactionsNet<K, V> extends LaunchApplication
{
	TransactionFooter trf=new TransactionFooter();
	TransactionHeader trh=new TransactionHeader();
	TransactionBody trb=new TransactionBody();
	 public double actroundoffnet=0;
	 String netvalue;
	 public String netr;
	public static Object getKeyFromValue(Map hm, Object value) {
	    for (Object o : hm.keySet()) {
	      if (hm.get(o).equals(value)) {
	        return o;
	      }
	    }
	    return null;
	  }
  @Test
  public boolean netCalculation(String bodyformulas, String footerformulas, String operator) throws InterruptedException, ScriptException 
  {
	 
	 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='id_transactionentry_footer_panel_summary_value_net_group']/label[@id='id_transactionentry_footer_panel_summary_value_net']")));
	 WebElement  net=driver.findElement(By.xpath("//div[@id='id_transactionentry_footer_panel_summary_value_net_group']/label[@id='id_transactionentry_footer_panel_summary_value_net']"));
	 netvalue=net.getText();
	 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='id_transactionentry_body_section']/div[4]/div[1]/table[@id='id_transaction_entry_detail_table']")));
	 WebElement table;
	 table=driver.findElement(By.xpath("//div[@id='id_transactionentry_body_section']/div[4]/div[1]/table[@id='id_transaction_entry_detail_table']"));
	 List<WebElement> tbodyheaderlist=table.findElements(By.xpath("//div[@id='id_transactionentry_body_section']/div[4]/div[1]/table[@id='id_transaction_entry_detail_table']/thead[@id='id_transaction_entry_detail_table_head']/tr[@id='id_transaction_entry_detail_table_row_heading']/th"));
	 ArrayList<String> headernames=new ArrayList();
	 headernames.clear();
	 for(int header=0;header<tbodyheaderlist.size();header++)
	 {
		 String headername=	tbodyheaderlist.get(header).getAttribute("title");	 
		 headernames.add(headername);
	 }
	 String bodyformula=bodyformulas;
	 String footerformula=footerformulas;
	 ArrayList<String> bodybreakup= new ArrayList(Arrays.asList(bodyformula.split("(?<=[\\[|\\(|\\-|\\+|\\*|\\/|\\%|\\)|\\]])|(?=[\\[|\\(|\\-|\\+|\\*|\\/|\\%|\\)|\\]])")));
	 ArrayList<String> footerbreakup= new ArrayList(Arrays.asList(footerformula.split("(?<=[\\[|\\(|\\-|\\+|\\*|\\/|\\%|\\)|\\]])|(?=[\\[|\\(|\\-|\\+|\\*|\\/|\\%|\\)|\\]])")));
	 ArrayList<String> body= new ArrayList(Arrays.asList(bodyformula.split("\\[|\\(|\\-|\\+|\\*|\\/|\\%|\\)|\\]")));
	 ArrayList<String> footer= new ArrayList(Arrays.asList(footerformula.split("\\[|\\(|\\-|\\+|\\*|\\/|\\%|\\)|\\]")));
	 body.removeAll(Arrays.asList("",null));
	 ArrayList<String> befrembody= new ArrayList(Arrays.asList(bodyformula.split("\\[|\\(|\\-|\\+|\\*|\\/|\\%|\\)|\\]")));
	 befrembody.removeAll(Arrays.asList("",null));
	 footer.removeAll(Arrays.asList("",null));
	 ArrayList<String> befremfooter= new ArrayList(Arrays.asList(footerformula.split("\\[|\\(|\\-|\\+|\\*|\\/|\\%|\\)|\\]")));
	 befremfooter.removeAll(Arrays.asList("",null));
	 ArrayList<String> beforeremovefooter=new ArrayList();
	 beforeremovefooter.clear();
	 beforeremovefooter.addAll(footer);
	 ArrayList<Integer> bodyintvalue=new ArrayList();
	 double bodynet=0,footernet;
		
	 bodyintvalue.clear();
		for(String s: body)
		{
			try 
			{
			if(Integer.valueOf(s) != null)
			{
				bodyintvalue.add(Integer.valueOf(s));
			}
			}
			catch(Exception e)
			{
				
			}
		}
	
	
		
		 String bodydoubstr;
		
	 Map<String, String> bodymap=new HashMap<String, String>();
	 ArrayList<String> bodyrowvalues=new ArrayList();
	List<WebElement> tbody;
	tbody= table.findElements(By.cssSelector("div[id='id_transactionentry_body_section'] div:nth-of-type(4) div:nth-of-type(1) table[id='id_transaction_entry_detail_table'] tbody[id='id_transaction_entry_detail_table_body'] tr[class='fgridrow']"));
	 String bodyformulaattr;
	 double bodynetrow;
	 int totrows=0; 
	 for(int row=1;row<tbody.size();row++)
	 		 {
	 			 WebElement currow=table.findElement(By.cssSelector("div[id='id_transactionentry_body_section'] div:nth-of-type(4) div:nth-of-type(1) table[id='id_transaction_entry_detail_table'] tbody[id='id_transaction_entry_detail_table_body'] tr[class='fgridrow']:nth-of-type("+row+") td:nth-of-type("+2+")"));
	 			// logger.info("row "+row+" txt "+currow.getAttribute("textContent")+ "a "+currow.getAttribute("data-value"));
	 			 if(currow.getAttribute("data-value")==null)
	 			 {
	 				logger.info("Body size should be "+row);
	 				totrows=row-1;
	 			 }
	 		 }
	 		 logger.info("tot rows "+totrows);
	 for(int k=1;k<totrows;k++)
	 {
		 body.removeAll(bodyintvalue);
		 if(bodyintvalue.size()>0)
		 {
			 for(int d:bodyintvalue)
			 {
				 bodydoubstr=String.valueOf(d);
				String bodyvalue = String.valueOf(d);
				 bodymap.put(bodydoubstr, bodyvalue);
					String key=(String) getKeyFromValue(bodymap,bodydoubstr);
					for (String keyv: bodymap.keySet()) 
					{
						bodymap.get(key);
						
					}
					bodyrowvalues.add(bodymap.get(key));
			 }
		 }
		for(int j=0;j<befrembody.size();j++)
		{	
			bodyformulaattr=befrembody.get(j);
			if(headernames.contains(bodyformulaattr))
			{
				int colno=headernames.indexOf(bodyformulaattr)+1;
				WebElement elem=table.findElement(By.cssSelector("div[id='id_transactionentry_body_section'] div:nth-of-type(4) div:nth-of-type(1) table[id='id_transaction_entry_detail_table'] tbody[id='id_transaction_entry_detail_table_body'] tr[class='fgridrow']:nth-of-type("+k+") td:nth-of-type("+colno+")"));
				String values=elem.getText();
				bodymap.put(body.get(j), values);
				String key=(String) getKeyFromValue(bodymap,bodymap.get(bodyformulaattr));
				for (String keyv: bodymap.keySet()) 
				{
				    bodymap.get(key);
				}
				
				bodyrowvalues.add(bodymap.get(key));
					
			}
			
		}
		Map<String, String> bodysortedMap=new LinkedHashMap<String, String>();
		 ArrayList<String>bodykeyset=new ArrayList();
		 bodykeyset.clear();
		 bodykeyset.addAll(bodymap.keySet());
		 bodykeyset.addAll(body);
		 Collections.reverse(bodykeyset);
		 	for(String strbody: body)
		  	{
		  		bodykeyset.remove(bodykeyset.size()-1);
		  		for(String key:bodymap.keySet())
		  		{
		  			if(strbody.equals(key))
		  			{
		  				bodysortedMap.put(key, bodymap.get(key));
		  				break;
		  			}
		  		}
		  	}
		
	   bodyrowvalues.clear();
	     for(String bodyattr: body)
	     {
	     String key=(String) getKeyFromValue(bodysortedMap,bodysortedMap.get(bodyattr));
			for (String keyv: bodysortedMap.keySet()) 
			{
				bodysortedMap.get(key);
			}
	      
			bodyrowvalues.add(bodysortedMap.get(key));
	     }
	    bodynetrow= (double) calculation(body,bodyrowvalues,bodybreakup);
		bodynet=bodynet+bodynetrow;
		for(int i:bodyintvalue)
		{
			body.add(String.valueOf(i));
		}
	 }
	logger.info("Total calculated Body Net value is "+bodynet);
	
	
     /* Footer Calculation*/
	 Map<String, String> footermap=new HashMap<String, String>();
	 ArrayList<String> footerrowvalues=new ArrayList();
	 trf.getLabelNames();
	table = driver.findElement(By.xpath("//div[@id='id_transactionentry_footer']/div[@id='id_transactionentry_footer_panel_entry']"));
	 String footerformulaattr;
	 ArrayList<String> unmatchedfooter=new ArrayList();
	 unmatchedfooter.clear();
	 unmatchedfooter.addAll(footer);
	 unmatchedfooter.removeAll(trf.unmodifiedfooterlabels);
	 ArrayList<Integer> intvalue=new ArrayList();
		intvalue.clear();
		for(String s: footer)
		{
			try 
			{
			if(Integer.valueOf(s) != null)
			{
				intvalue.add(Integer.valueOf(s));
			}
			}
			catch(Exception e)
			{
				
			}
		}
	 footer.removeAll(unmatchedfooter);
	 footer.removeAll(intvalue);
	
	 String doubstr;
	 
	 for(String voucherattr: trf.unmodifiedfooterlabels)
	 {
		innerloop:
		 for(String formulaattr: footer)
		{	
			
			if(voucherattr.equalsIgnoreCase(formulaattr))
			{
				wait.until(ExpectedConditions.visibilityOf(table.findElement(By.xpath("//div/label[text()= '"+formulaattr+"']"))));
				String actid=table.findElement(By.xpath("//div/label[text()= '"+formulaattr+"']")).getAttribute("for");
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(actid)));
				WebElement elem=driver.findElement(By.id(actid));
				String values=elem.getAttribute("value");
				footermap.put(formulaattr, values);
				
				String key=(String) getKeyFromValue(footermap,footermap.get(formulaattr));
				for (String keyv: footermap.keySet()) 
				{
					footermap.get(key);
				}
				
				footerrowvalues.add(footermap.get(key));
				break;
					
			}
			
			
		}
	 }
	 if(unmatchedfooter.size()>0)
	 {
		 trh.getLabelNames();
		for(String s: unmatchedfooter)
		{
			if(trh.unmodifiablelabel.contains(s))
			{
				List<WebElement> tabs= driver.findElements(By.xpath("//div[@id='id_transactionentry_header']/div[1]/div[1]/div[@id='id_tab_container']/ul/li"));
				for(int tabno=1;tabno<tabs.size();tabno++)
				{
					try
					{
					driver.findElement(By.xpath("//div[@id='id_transactionentry_header']/div[1]/div[1]/div[@id='id_tab_container']/ul/li[@id='id_transactionentry_headertab_listelement"+tabno+"']")).click();
					}
					catch(Exception e)
					{
						driver.findElement(By.xpath("/html/body/section/div[2]/div/section[1]/div[1]/div/div[2]/div[1]/div[1]/div[1]/div[1]/div/div[2]/div/div/a/span")).click();
						Thread.sleep(1000);
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='dropdown-menu pull-right']/li[@data-tabnumber='"+tabno+"']")));
						driver.findElement(By.xpath("//ul[@class='dropdown-menu pull-right']/li[@data-tabnumber='"+tabno+"']")).click();
					}
					Thread.sleep(1000);
				
					List<WebElement> rows=driver.findElements(By.xpath("//div[@id='id_transactionentry_header_tabdetail_container']/div[@id='id_transactionentry_header"+tabno+"_section']/div[1]/div[@class='row']"));
					
				mainloop:
				for(int j=0;j<trh.unmodifiablelabel.size();j++)
				{
					
				headerrowloop:
				for(int headerrow=1;headerrow<=rows.size();headerrow++)
				{
					List<WebElement> cols=driver.findElements(By.xpath("//div[@id='id_transactionentry_header_tabdetail_container']/div[@id='id_transactionentry_header"+tabno+"_section']/div[1]/div[@class='row']["+headerrow+"]/div"));
					Labelloop:
					for(int labelattribute=1;labelattribute<cols.size();labelattribute++)
						{
						String actvalue = null;
							try 
							{
														
								actvalue=driver.findElement(By.xpath("//div[@id='id_transactionentry_header_tabdetail_container']/div[@id='id_transactionentry_header"+tabno+"_section']/div[1]/div[@class='row']["+headerrow+"]/div[@class]["+labelattribute+"]/label")).getText();
								
							}
							catch(Exception e)
							{
								break;
							}
							try {
							if(actvalue.equalsIgnoreCase(unmatchedfooter.get(j)))
							{
								int labelvalue=labelattribute+1;
								WebElement control=driver.findElement(By.xpath("//div[@id='id_transactionentry_header_tabdetail_container']/div[@id='id_transactionentry_header"+tabno+"_section']/div[1]/div[@class='row']["+headerrow+"]/div[@class]["+labelvalue+"]"));
								WebElement controltype;
								String elemtype,elemclass;
								controltype=control.findElement(By.cssSelector("div input:not([type='hidden']), div select:first-child"));
								elemclass=controltype.getAttribute("class");
								elemtype=controltype.getAttribute("type");
								String attr=control.findElement(By.cssSelector("div input:not([type='hidden'])[class='"+elemclass+"']")).getAttribute("id");
								String data = attr+"_data";
								wait.until(ExpectedConditions.visibilityOf(control.findElement(By.cssSelector("div input[id='"+attr+"']"))));
								WebElement ele=control.findElement(By.cssSelector("div input[id='"+attr+"'], div select[id='"+attr+"']"));
								String footervalue=ele.getAttribute("value");
								footermap.put(unmatchedfooter.get(j), footervalue);
								String key=(String) getKeyFromValue(footermap,footermap.get(unmatchedfooter.get(j)));
								for (String keyv: footermap.keySet()) 
								{
									footermap.get(key);
								}
								
								footerrowvalues.add(footermap.get(key));
							break;
							}
							else
							{
								break;
							}
							}
							catch(IndexOutOfBoundsException e)
							{
								
								break mainloop;
							}
							
						}
				}
				}
				}
				
			}
		}
		trb.getBodyLabelNames();
		table=driver.findElement(By.xpath("//div[@id='id_transactionentry_body_section']/div[4]/div[1]/table[@id='id_transaction_entry_detail_table']"));
		double totbodyvalue=0;
		for(String s: unmatchedfooter)
		{
			if(trb.unmodifiedbodylabels.contains(s))
			{
				 tbody= table.findElements(By.cssSelector("div[id='id_transactionentry_body_section'] div:nth-of-type(4) div:nth-of-type(1) table[id='id_transaction_entry_detail_table'] tbody[id='id_transaction_entry_detail_table_body'] tr[class='fgridrow']"));
				 for(int k=1;k<tbody.size();k++)
				 {
					for(int j=0;j<unmatchedfooter.size();j++)
					{	
						bodyformulaattr=unmatchedfooter.get(j);
						if(trb.unmodifiedbodylabels.contains(bodyformulaattr))
						{
							int colno=trb.unmodifiedbodylabels.indexOf(bodyformulaattr)+2;
							WebElement elem=table.findElement(By.cssSelector("div[id='id_transactionentry_body_section'] div:nth-of-type(4) div:nth-of-type(1) table[id='id_transaction_entry_detail_table'] tbody[id='id_transaction_entry_detail_table_body'] tr[class='fgridrow']:nth-of-type("+k+") td:nth-of-type("+colno+")"));
							String values=elem.getText();
							
							totbodyvalue=totbodyvalue+Double.parseDouble(values.replaceAll(",", ""));
							footermap.put(bodyformulaattr, String.valueOf(totbodyvalue));
							String key=(String) getKeyFromValue(footermap,footermap.get(bodyformulaattr));
							for (String keyv: footermap.keySet()) 
							{
								footermap.get(key);
								
							}
							
							footerrowvalues.add(footermap.get(key));
							
								
						}
						
						
					}
				 }
				
			}
		}
	 }
	 for(int d:intvalue)
	 {
		 doubstr=String.valueOf(d);
		 String footervalue = String.valueOf(d);
		 footermap.put(doubstr, footervalue);
			String key=(String) getKeyFromValue(footermap,doubstr);
			for (String keyv: footermap.keySet()) 
			{
				footermap.get(key);
				
			}
			footerrowvalues.add(footermap.get(key));
	 }
	 footer.addAll(unmatchedfooter);
	 ImmutableMap.copyOf(footermap);
	 footer=new ArrayList(befremfooter);
	 footer.clear();
	 footer.addAll(befremfooter);
	 Map<String, String> sortedMap=new LinkedHashMap<String, String>();
	 ArrayList<String>footerkeyset=new ArrayList();
	 footerkeyset.clear();
	 footerkeyset.addAll(footermap.keySet());
	 footerkeyset.addAll(footer);
	 Collections.reverse(footerkeyset);
	for(String strfooter: footer)
	  	{
	  		footerkeyset.remove(footerkeyset.size()-1);
	  		for(String key:footermap.keySet())
	  		{
	  			if(strfooter.equals(key))
	  			{
	  				sortedMap.put(key, footermap.get(key));
	  				break;
	  			}
	  		}
	  	}
	
     footerrowvalues.clear();
     for(String formulaattr: footer)
     {
     String key=(String) getKeyFromValue(sortedMap,sortedMap.get(formulaattr));
		for (String keyv: sortedMap.keySet()) 
		{
			sortedMap.get(key);
		}
      
		footerrowvalues.add(sortedMap.get(key));
     }
	
	footernet=(double) calculation(footer,footerrowvalues,footerbreakup);
	double roundoffbodynet,roundofffooternet;
	roundoffbodynet=(double) Math.round(bodynet * 100) / 100;
	roundofffooternet=(double) Math.round(footernet * 100) / 100;
	logger.info("Body Formula defined is "+bodynet+" & Footer Formula defined is "+footernet);
	double actnet;
	if(operator.equals("+"))
	{
	actnet=bodynet+footernet;
	}
	else
	{
	actnet=bodynet-footernet;	
	}
	actroundoffnet = (double) Math.round(actnet * 100) / 100;
	netr=netvalue.replace(",", "");
	if(Double.parseDouble(netr)==actroundoffnet)
	{
		logger.info("Actual Net from application is "+actroundoffnet+" & Expected Net is "+Double.parseDouble(netr));
		return true;
	}
	return false;
	
	
  }
  public boolean netCalculation(String bodyformulas, String actualnet) throws ScriptException
  {

		 
	  	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='id_transactionentry_footer_panel_summary_value_net_group']/label[@id='id_transactionentry_footer_panel_summary_value_net']")));
		 WebElement  net=driver.findElement(By.xpath("//div[@id='id_transactionentry_footer_panel_summary_value_net_group']/label[@id='id_transactionentry_footer_panel_summary_value_net']"));
		 String netvalue=net.getText();
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='id_transactionentry_body_section']/div[4]/div[1]/table[@id='id_transaction_entry_detail_table']")));
		 WebElement table;
		 table=driver.findElement(By.xpath("//div[@id='id_transactionentry_body_section']/div[4]/div[1]/table[@id='id_transaction_entry_detail_table']"));
		 List<WebElement> tbodyheaderlist=table.findElements(By.xpath("//div[@id='id_transactionentry_body_section']/div[4]/div[1]/table[@id='id_transaction_entry_detail_table']/thead[@id='id_transaction_entry_detail_table_head']/tr[@id='id_transaction_entry_detail_table_row_heading']/th"));
		 ArrayList<String> headernames=new ArrayList();
		 headernames.clear();
		 for(int header=0;header<tbodyheaderlist.size();header++)
		 {
			 String headername=	tbodyheaderlist.get(header).getAttribute("title");	 
			 headernames.add(headername);
		 }
		 String bodyformula=bodyformulas;
		 ArrayList<String> bodybreakup= new ArrayList(Arrays.asList(bodyformula.split("(?<=[\\[|\\(|\\-|\\+|\\*|\\/|\\%|\\)|\\]])|(?=[\\[|\\(|\\-|\\+|\\*|\\/|\\%|\\)|\\]])")));
		 ArrayList<String> body= new ArrayList(Arrays.asList(bodyformula.split("\\[|\\(|\\-|\\+|\\*|\\/|\\%|\\)|\\]")));
		 body.removeAll(Arrays.asList("",null));
		 ArrayList<String> befrembody= new ArrayList(Arrays.asList(bodyformula.split("\\[|\\(|\\-|\\+|\\*|\\/|\\%|\\)|\\]")));
		 befrembody.removeAll(Arrays.asList("",null));
		 ArrayList<Integer> bodyintvalue=new ArrayList();
		 double bodynet=0,footernet;
			
		 bodyintvalue.clear();
			for(String s: body)
			{
				try 
				{
				if(Integer.valueOf(s) != null)
				{
					bodyintvalue.add(Integer.valueOf(s));
				}
				}
				catch(Exception e)
				{
					
				}
			}
		
		
			
			 String bodydoubstr;
			
		 Map<String, String> bodymap=new HashMap<String, String>();
		 ArrayList<String> bodyrowvalues=new ArrayList();
		List<WebElement> tbody;
		tbody= table.findElements(By.cssSelector("div[id='id_transactionentry_body_section'] div:nth-of-type(4) div:nth-of-type(1) table[id='id_transaction_entry_detail_table'] tbody[id='id_transaction_entry_detail_table_body'] tr[class='fgridrow']"));
		 String bodyformulaattr;
		 double bodynetrow;
		 int totrows=0; 
		 for(int row=1;row<tbody.size();row++)
		 		 {
		 			 WebElement currow=table.findElement(By.cssSelector("div[id='id_transactionentry_body_section'] div:nth-of-type(4) div:nth-of-type(1) table[id='id_transaction_entry_detail_table'] tbody[id='id_transaction_entry_detail_table_body'] tr[class='fgridrow']:nth-of-type("+row+") td:nth-of-type("+2+")"));
		 			// logger.info("row "+row+" txt "+currow.getAttribute("textContent")+ "a "+currow.getAttribute("data-value"));
		 			 if(currow.getAttribute("data-value")==null)
		 			 {
		 				//logger.info("Body size should be "+row);
		 				totrows=row-1;
		 				break;
		 			 }
		 		 }
		 		 //logger.info("tot rows "+totrows);
		 for(int k=1;k<=totrows;k++)
		 {
			 body.removeAll(bodyintvalue);
			if(bodyintvalue.size()>0)
			 {
				 for(int d:bodyintvalue)
				 {
					 bodydoubstr=String.valueOf(d);
					String bodyvalue = String.valueOf(d);
					 bodymap.put(bodydoubstr, bodyvalue);
						String key=(String) getKeyFromValue(bodymap,bodydoubstr);
						for (String keyv: bodymap.keySet()) 
						{
							bodymap.get(key);
							
						}
						bodyrowvalues.add(bodymap.get(key));
				 }
			 }
			for(int j=0;j<befrembody.size();j++)
			{	
				bodyformulaattr=befrembody.get(j);
				if(headernames.contains(bodyformulaattr))
				{
					int colno=headernames.indexOf(bodyformulaattr)+1;
					WebElement elem=table.findElement(By.cssSelector("div[id='id_transactionentry_body_section'] div:nth-of-type(4) div:nth-of-type(1) table[id='id_transaction_entry_detail_table'] tbody[id='id_transaction_entry_detail_table_body'] tr[class='fgridrow']:nth-of-type("+k+") td:nth-of-type("+colno+")"));
					String values=elem.getText();
					bodymap.put(body.get(j), values);
					String key=(String) getKeyFromValue(bodymap,bodymap.get(bodyformulaattr));
					for (String keyv: bodymap.keySet()) 
					{
					    bodymap.get(key);
					}
					
					bodyrowvalues.add(bodymap.get(key));
						
				}
				
			}
			
			Map<String, String> bodysortedMap=new LinkedHashMap<String, String>();
			 ArrayList<String>bodykeyset=new ArrayList();
			 bodykeyset.clear();
			 bodykeyset.addAll(bodymap.keySet());
			 bodykeyset.addAll(body);
			 Collections.reverse(bodykeyset);
			 	for(String strbody: body)
			  	{
			  		bodykeyset.remove(bodykeyset.size()-1);
			  		for(String key:bodymap.keySet())
			  		{
			  			if(strbody.equals(key))
			  			{
			  				bodysortedMap.put(key, bodymap.get(key));
			  				break;
			  			}
			  		}
			  	}
			
		   bodyrowvalues.clear();
		     for(String bodyattr: body)
		     {
		     String key=(String) getKeyFromValue(bodysortedMap,bodysortedMap.get(bodyattr));
				for (String keyv: bodysortedMap.keySet()) 
				{
					bodysortedMap.get(key);
				}
		      
				bodyrowvalues.add(bodysortedMap.get(key));
		     }
		    bodynetrow= (double) calculation(body,bodyrowvalues,bodybreakup);
		    //logger.info("Bodynet rw "+bodynetrow);
			bodynet=bodynet+bodynetrow;
			for(int i:bodyintvalue)
			{
				body.add(String.valueOf(i));
			}
		 }
		
		 //logger.info("Total calculated Body Net value is "+bodynet);
		 actroundoffnet = (double) Math.round(bodynet * 100) / 100;
		 netr=netvalue.replace(",", "");
		if(Double.parseDouble(netr)==actroundoffnet)
		{
			logger.info("Actual Net from application is "+actroundoffnet+" & Expected Net is "+Double.parseDouble(netr));
			return true;
		}
		return false;
		
	  
  }
 
  
	public Object calculation(ArrayList<String> values, ArrayList<String> rowvalues, ArrayList<String>breakup) throws ScriptException
	{
	ArrayList<Double> bodyrownetvalues=new ArrayList();
	bodyrownetvalues.clear();
	ArrayList<Integer> intvalue=new ArrayList();
	intvalue.clear();
	for(String s: values)
	{
		try 
		{
		if(Integer.valueOf(s) != null)
		{
			
			intvalue.add(Integer.valueOf(s));
		}
		}
		catch(Exception e)
		{
			
		}
	}
	ArrayList<String> intstring=new ArrayList();
	for(int i:intvalue)
	{
		intstring.add(String.valueOf(i));
	}
	values.removeAll(intstring);
	HashSet<String> match = new HashSet<String>(Arrays.asList("(",")","=",";","{","}","[","]","+","-","*","/","&","!","%","^","|","<",">"));
	 ArrayList<String> ar=new ArrayList();
	 ar.clear();
	int count=0;
	/*Breakup of each value of the formula array and then combining it to perform calculation*/
	 for(int n=0;n<rowvalues.size();n+=count)
	 {
		 count=0;
		 for(int a=0;a<breakup.size();a++)
		 {	
			 if(match.contains(breakup.get(a)))
			 {
				ar.add(breakup.get(a));
			 }
			 else if(breakup.get(a).matches(".*\\d+.*"))
			 {
				 ar.add(breakup.get(a));
				 count++;
			 }
			else
			{
				int c=n+count;
				ar.add(rowvalues.get(n+count));
				count++;
			}
			 
		 }
		 if(count<rowvalues.size())
		 {
			 count=rowvalues.size();
		 }
	}
	String rowcommaseperated = String.join(",", ar);
	String rowevaluator=rowcommaseperated.replaceAll(",", "");
	ScriptEngineManager mgr = new ScriptEngineManager();
    ScriptEngine engine = mgr.getEngineByName("JavaScript");
   
    try 
    {
		//logger.info("Formula to be evaluated is "+rowevaluator+" & Evaluated value "+engine.eval(rowevaluator));
		return engine.eval(rowevaluator);
	} 
    catch (Exception e) 
    {
    	e.printStackTrace();
		logger.info("Script Exception "+e.getMessage());
	}
    return engine.eval(rowevaluator);
	
  }
}

