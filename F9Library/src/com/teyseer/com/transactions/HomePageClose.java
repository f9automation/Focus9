package com.teyseer.com.transactions;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import com.focus.constants.LaunchApplication;

public class HomePageClose extends LaunchApplication {
  @Test
  
  /* METHOD TO CLOSE THE TRANSACTION SCREEN */
 public void transactionClose()
	  {
		List<WebElement> pane= driver.findElements(By.cssSelector("div[id='dv_TranHomeHeading'] nav[class='navbar'] div[id='myNavbar'] div[class='nav navbar-nav navbar-right'] div"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[id='dv_TranHomeHeading'] nav[class='navbar'] div[id='myNavbar'] div[class='nav navbar-nav navbar-right'] div:last-child")));
		driver.findElement(By.cssSelector("div[id='dv_TranHomeHeading'] nav[class='navbar'] div[id='myNavbar'] div[class='nav navbar-nav navbar-right'] div:last-child")).click();
	
		 }
	  
  
}
