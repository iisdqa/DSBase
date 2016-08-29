package com.dsbase.core.web;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.dsbase.core.web.custom_classes.Excell_Parser;
import com.dsbase.core.web.custom_classes.GridCheck;
import com.dsbase.core.web.custom_classes.WorkWith_Files;
import com.dsbase.core.web.elements.CheckBox;
import com.dsbase.core.web.elements.TextInput;

public class CustomMethods 
{
	// Base maven directory
	public String BASE_DIR = System.getProperties().get("basedir").toString();
	
	// Actions with 'Grids'
	public class Grid extends GridCheck {}
	
	// Managing text files
	public class WorkWith_TextFiles extends WorkWith_Files {}
	
	// Work with Excell files
	public class WorkWith_Excel extends Excell_Parser {}
	
	// Elements attribute checkers
	public class Attribute_Checkers
	{
		public void checkBoxAttribute_Check(CheckBox checkBox, String attribute, Boolean expectedCondition)
		{
			// Result variable
			Boolean actualCondition = true;
			
			// Variable with attribute value
			String attributeValue = checkBox.getAttribute(attribute);
			
			// If checkbox unchecked - return 'false'
			if(attributeValue == null)
			{
				actualCondition = false;
			}
			
			// If real vulue != expected - return error
			if(actualCondition != expectedCondition)
			{
				System.err.println("Ошибка во время проверки чекбокса. Ожидаемое значение чекбокса != реальному." +
		   		   		   		   "\r\nОжидаемое значение атрибута '" + attribute + "' = " + expectedCondition.toString() + 
				   		   		   "\r\nЧек-бокс = " + checkBox.findByMethod.toString());
				Assert.fail();
			}
		}
		
		public void textInput_Attribute_Check(TextInput textInput, String attribute, Boolean expectedCondition)
		{
			// Result variable
			Boolean actualCondition = true;
			
			// Variable with attribute value
			String attributeValue = textInput.getAttribute(attribute);
			
			// If checkbox unchecked - return 'false'
			if(attributeValue == null)
			{
				actualCondition = false;
			}
			
			// If real vulue != expected - return error
			if(actualCondition != expectedCondition)
			{
				System.err.println("Ошибка во время проверки поля для ввода. Ожидаемое значение атрибута != реальному." +
				   		   		   "\r\nОжидаемое значение атрибута '" + attribute + "' = " + expectedCondition.toString() + 
				   		   		   "\r\nПоле для ввода = " + textInput.findByMethod.toString());
				Assert.fail();
			}
		}
	}
	
	// Simple wait method
	public void simpleWait(int Seconds)
	{
		try
		{
			Thread.sleep(Seconds * 1000);
		}
		catch(InterruptedException e){throw new RuntimeException(e);}
	}
	
	// Get current date
	public String getCurrentDate()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Date date = new Date();
		String currentDate = dateFormat.format(date);
		return currentDate;
	}
	
	// Get current date +- days of 'changer''
	public String getChangedDate(int changer)
	{
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int daysToChange = changer;
		cal.add(Calendar.DATE, daysToChange);
		date = cal.getTime();
		
		String changedDate = dateFormat.format(date);
		return changedDate;
	}
	
	// Cut spaces(before and after) in string variables
    public String StringSpacesCut(String StringToCut)
    {
    	// If null - mage empty
    	if (StringToCut == null) StringToCut = "";
    	
    	// If empty - do nothing
        if (!StringToCut.isEmpty())
        {
            // Cut spaces after string
            int lastIndex = StringToCut.length() - 1;
            String lastChar = Character.toString(StringToCut.charAt(lastIndex));
            for (int i = 0; lastChar.equals(" ") && (i < 5); i++)
            {      	
            	StringBuilder sb = new StringBuilder(StringToCut);
            	sb.deleteCharAt(lastIndex);
            	StringToCut = sb.toString();
            	lastIndex = StringToCut.length() - 1;
            	lastChar = Character.toString(StringToCut.charAt(lastIndex));
            }

            // Cut spaces before string
            String firstChar = Character.toString(StringToCut.charAt(0));
            for (int i = 0; firstChar.equals(" ") && (i < 5); i++)
            {
                StringToCut = StringToCut.substring(1);
                firstChar = Character.toString(StringToCut.charAt(0));
            }
        }
        return StringToCut;
    }
	
    // Check, if page leaving warning appears
	public void leaveWarningCheck(WebDriver driver, String browser)
	{
		// Click to main page icon
		driver.findElement(By.className("header_link")).click();
		simpleWait(2);
		
		// Alert text variable
		String alertText = null;
		
		// Write alert text and close alert
		try
		{
		Alert alert = driver.switchTo().alert();
		alertText = alert.getText();
		alert.dismiss();
		}
		catch(Exception e)
		{
    		System.err.println("Error while alert check in page leaving case! Can't find alert." +
 			   		   		   "\r\nError: = \r\n" + e.getMessage() + 
 			   		   		   "\r\nStack: = \r\n" + e.getStackTrace());
    		Assert.fail();
		}
		
		// Check alert text
		if(browser.equals("chrome"))	assertThat("На странице остались несохраненные данные. Вернуться на страницу для сохранения внесенных данных?", 
									    is(equalTo(alertText)));
		else if(browser.equals("firefox"))	assertThat("Эта страница просит вас подтвердить, что вы хотите уйти — при этом введённые вами данные могут не сохраниться.", 
									        is(equalTo(alertText)));
		else if(browser.equals("ie")){}
	}
	
	// Check element existence
	public void elementExistenceCheck(WebElement element, boolean Exists)
	{
		// Some variable
		boolean elementExistence = false;	   
		
		// Check element existence
		if(element.isDisplayed()) elementExistence = true;
		
		// If expected values != actual - print error
		if(elementExistence != Exists)
		{
    		System.err.println("Error while element existence check!" +
    						   "\r\nElement: = \r\n" + element.toString() + 
    						   "\r\nExpected existence value: = \r\n" + Exists + 
    						   "\r\nActual existence value: = \r\n" + elementExistence);
    		Assert.fail();
		}
	}
	
	// Managing JS actions
	public class Js_Actions
	{
		public void webElement_Click(WebDriver driver, WebElement element)
		{
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].click();", element);
		}
	}
	
	// For working with text redactor
	public class WorkWith_TextEditor
	{
		public String getTextValue(WebDriver driver, WebElement frame)
		{
			driver.switchTo().frame(frame);
			WebElement body = driver.findElement(By.cssSelector("body"));
			String value = body.getText();
			driver.switchTo().defaultContent();
			
			return value;
		}
	}
}
