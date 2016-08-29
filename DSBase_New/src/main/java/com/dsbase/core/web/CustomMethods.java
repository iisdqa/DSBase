package com.dsbase.core.web;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.dsbase.core.web.elements.Button;
import com.dsbase.core.web.elements.CheckBox;
import com.dsbase.core.web.elements.TextInput;

public class CustomMethods 
{
	public class Grid
	{
		public String[][] GetAllRows(WebElement tbody)
		{
			// ����������� ���������� �����
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			
			// �������� �������� ����
			rows.remove(0);
			
			// ����������� ������� ������������� �������
			String[][] GridValues = new String[rows.size()][];
			
			// ���������� ����
			for(int rnum=0; rnum < rows.size(); rnum++)
			{
				// ���������� ���������� �������
				List<WebElement> columns=rows.get(rnum).findElements(By.tagName("td"));
				int sizeBeforeCut = columns.size();
							
				// ���������� ������� �����
				for(int cnum= (sizeBeforeCut - 1); cnum >= 0; cnum--)
				{
					if(columns.get(cnum).getCssValue("display").equals("none"))
					{
						columns.remove(cnum);
					}
				}
				
				// ����������� ������� ������� � ���������� �����
				String[] ColumnValues = new String[columns.size()];
				
				// ���������� �������� ����� � ������
				for(int cnum=0; cnum<columns.size(); cnum++)
				{
					ColumnValues[cnum] = columns.get(cnum).getText();
				}
				
				// �������� �������� ������� � ��� 
				GridValues[rnum] = ColumnValues;
			}
			return GridValues;
		}
		
		public String[][] GetSpecificRows(WebElement table, int RowsToGet)
		{
			// ����������� ���������� �����
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			
			// �������� �������� ����
			rows.remove(0);
			
			// ����������� ������� ������������� �������
			String[][] GridValues = new String[RowsToGet][];
			
			// ���������� ����
			for(int rnum=0; rnum < RowsToGet; rnum++)
			{
				// ���������� ���������� �������
				List<WebElement> columns=rows.get(rnum).findElements(By.tagName("td"));
				int sizeBeforeCut = columns.size();
				
				// ���������� ������� �����
				for(int cnum= (sizeBeforeCut - 1); cnum != 0; cnum--)
				{
					if(columns.get(cnum).getCssValue("display").equals("none"))
					{
						columns.remove(cnum);
					}
				}
				
				// ����������� ������� ������� � ���������� �����
				String[] ColumnValues = new String[columns.size()];
				
				// ���������� �������� ����� � ������
				for(int cnum=0; cnum<columns.size(); cnum++)
				{
					ColumnValues[cnum] = columns.get(cnum).getText();
				}
				
				// �������� �������� ������� � ��� 
				GridValues[rnum] = ColumnValues;
			}
			return GridValues;
		}
		
		public void gridValuesEqualityCheck(String[][] ExpectedValues, String[][] ActualValues)
		{
			// �������� �� ������������ ���������� �����
			if(ExpectedValues.length != ActualValues.length)
			{
				System.err.println("������ �� ����� �������� �����. ��������� ���������� ����� != ��������� ���������� �����." +
						   		   "\r\n��������� ���������� = " + ExpectedValues.length +
						   		   "\r\n�������� ���������� = " + ActualValues.length);
				Assert.fail();
			}
			
			// ���������� ����
			for(int rowNum = 0; rowNum < ActualValues.length; rowNum++)
			{
				// ���� ���������� ��������� ������� � ���� != ���������� ��������, �� �������� ��������� 
				if(ExpectedValues[rowNum].length != ActualValues[rowNum].length)
				{
					System.err.println("������ �� ����� �������� �����. ��������� ���������� ������� � ����������� ����� != ��������� ���������� �������." +
					   		   "\r\n��������� ���������� = " + ExpectedValues[rowNum].length +
					   		   "\r\n�������� ���������� = " + ActualValues[rowNum].length + 
					   		   "\r\n����������� ��� = " + (rowNum + 1));
					Assert.fail();
				}
				
				// ���������� �������
				for(int colNum = 0; colNum < ActualValues[rowNum].length; colNum++)
				{
					// ���� ������� �����, �� ��������
					if(ActualValues[rowNum][colNum].contains(":") == true)
					{
						// ������� ���������� ������ ":"
						int countOfCharacters = ActualValues[rowNum][colNum].length() - ActualValues[rowNum][colNum].replace(":", "").length();
						
						// ���� ���������� ������ ":", �� �������� �����
						if(countOfCharacters == 2)
						{
							ActualValues[rowNum][colNum] = ActualValues[rowNum][colNum].substring(0, (ActualValues[rowNum][colNum].indexOf(" ")));
						}
					}

					// �������� ��������� ���������� �������� � ��������� ��������
					assertThat(ActualValues[rowNum][colNum], is(equalTo(ExpectedValues[rowNum][colNum])));
				}			
			}
		}
	}
	
	public class WorkWith_Excel
	{
		public String[][] GetHeadPart_ForExcel(WebElement thead)
		{
			// ����������� ���������� �����
			List<WebElement> rows = thead.findElements(By.tagName("tr"));
			
			// ����������� ������� ������������� �������
			String[][] GridValues = new String[rows.size()][];
			
			// ���������� ����
			for(int rnum=0; rnum < rows.size(); rnum++)
			{
				// ���������� ���������� �������
				List<WebElement> columns=rows.get(rnum).findElements(By.tagName("th"));
				int sizeBeforeCut = columns.size();
							
				// ���������� ������� �����
				for(int cnum= (sizeBeforeCut - 1); cnum >= 0; cnum--)
				{
					if(columns.get(cnum).getCssValue("display").equals("none"))
					{
						columns.remove(cnum);
					}
				}
				
				// �������� ������ ��������
				columns.remove(columns.size() - 1);
				columns.remove(0);
				
				// ����������� ������� ������� � ���������� �����
				String[] ColumnValues = new String[columns.size()];
				
				// ���������� �������� ����� � ������
				for(int cnum=0; cnum<columns.size(); cnum++)
				{
					// ����� ����� �� ��������
					ColumnValues[cnum] = columns.get(cnum).findElement(By.tagName("div")).getAttribute("textContent");   // ����� ����� ��������� �������� 'innerText', 'innerHTML'
				}
				
				// �������� �������� ������� � ��� 
				GridValues[rnum] = ColumnValues;
			}
			return GridValues;
		}
		
		public String[][] GetAllRows_ForExcel(WebElement tbody)
		{
			// ����������� ���������� �����
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			
			// �������� �������� ����
			rows.remove(0);
			
			// ����������� ������� ������������� �������
			String[][] GridValues = new String[rows.size()][];
			
			// ���������� ����
			for(int rnum=0; rnum < rows.size(); rnum++)
			{
				// ���������� ���������� �������
				List<WebElement> columns=rows.get(rnum).findElements(By.tagName("td"));
				int sizeBeforeCut = columns.size();
							
				// ���������� ������� �����
				for(int cnum= (sizeBeforeCut - 1); cnum >= 0; cnum--)
				{
					if(columns.get(cnum).getCssValue("display").equals("none"))
					{
						columns.remove(cnum);
					}
				}
				
				// �������� ������ ��������
				columns.remove(columns.size() - 1);
				columns.remove(0);
				
				// ����������� ������� ������� � ���������� �����
				String[] ColumnValues = new String[columns.size()];
				
				// ���������� �������� ����� � ������
				for(int cnum=0; cnum<columns.size(); cnum++)
				{
					ColumnValues[cnum] = columns.get(cnum).getText();
				}
				
				// �������� �������� ������� � ��� 
				GridValues[rnum] = ColumnValues;
			}
			return GridValues;
		}
		
		public String[][] get_ExcelValues(Button DownloadButton, String ExpectedFileName)
		{
			// �������
			DownloadButton.click();	
			simpleWait(5);
			
			// �����, ���� ������ �������� �����
			String folderPath = "C:\\Selenium_TestData\\Other\\TempFiles\\";
			
			// ����� ��� ����� � �����
			File folderToScan = new File(folderPath); 
		    File[] listOfFiles = folderToScan.listFiles();
		    int filesCount = listOfFiles.length;
			
	    	// ���� �� ������� ����� ���� �� ���������� ����� - ������� ��� ����� � ������ ������
	    	if(filesCount != 1)
	    	{
	    		// ������� ��� �����, ���� ��� ����
	    		if(filesCount != 0)
	    		{
	        		for(int i = 0; i < filesCount; i++)
	        		{
	        			File tempFileVariable = listOfFiles[i];
	        			tempFileVariable.delete();
	        		}
	    		}
	    		
	    		// ������� ������
	    		System.err.println("������ �������� ����� Excel ��� ��������. ���������� ��������� ������ != ����������." +
	 		   		   			   "\r\n��������� ���� = " + ExpectedFileName + 
	 		   		   			   "\r\n���������� ��������� ������ = " + filesCount);
	    		Assert.fail();
	    	}
	    	
	    	// �������� ���� �� �������
	    	File file = listOfFiles[0];
	    	
	    	// ����������� ������� ��� ��������
	    	String[][] data = null;
	    	
	    	try
	    	{
	    		data = getExcelValues(file);
	    	}  	
			catch(Exception e)
			{
	    		System.err.println("������ ��� ���������� �������� �� Excel �����." +
	   		   			   		   "\r\n����� ������ = " + e.getMessage() +
	   		   			   		   "\r\n���� = " + e.getStackTrace());
			}
			finally
			{
	    		file.delete();
			}
	    	
	    	return data;
		}
		
		private String[][] getExcelValues(File excelFile)
		{
			Workbook w;
		    String[][] data = null;

	        try 
	        {
	        	// ���������� �����
	            w = Workbook.getWorkbook(excelFile);
	            
	            // ����� ������ ����
	            Sheet sheet = w.getSheet(0);    
	        
	            // ����������� ������� �������
	            data = new String[sheet.getRows()][sheet.getColumns()];
	            
	            // ������� �����
	            for (int i = 0; i <sheet.getRows(); i++) 
	            {
	            	//������� ������� + ������ ����� � ������
	                for (int j = 0; j < sheet.getColumns(); j++) 
	                {
	                	// ����� �������� �� ������
	                    Cell cell = sheet.getCell(j, i);
	                    String cellValue = cell.getContents();
	                    
	                    // ������ ������ �������('-' � ';')
	                    cellValue.replace("-", "");
	                    cellValue.replace(";", "");
	                    cellValue = StringSpacesCut(cellValue);
	                    // ������ �������� � ������
	                    data[i][j] = cellValue;
	                }
	            }

	        } 
	        
	        catch (BiffException e) {e.printStackTrace();} 
	        catch (IOException e) {e.printStackTrace();}
	        
	        return data;
		}
	}
	
	public class Attribute_Checkers
	{
		public void checkBoxAttribute_Check(CheckBox checkBox, String attribute, Boolean expectedCondition)
		{
			// ���������� ��� ����������
			Boolean actualCondition = true;
			
			// ���������� � ��������� ��������
			String attributeValue = checkBox.getAttribute(attribute);
			
			// ���� ������� �� ���������� - ������� 'false'
			if(attributeValue == null)
			{
				actualCondition = false;
			}
			
			// ���� �������� �������� != ����������, �� ������� ������
			if(actualCondition != expectedCondition)
			{
				System.err.println("������ �� ����� �������� ��������. ��������� �������� �������� != ���������." +
		   		   		   		   "\r\n��������� �������� �������� '" + attribute + "' = " + expectedCondition.toString() + 
				   		   		   "\r\n���-���� = " + checkBox.findByMethod.toString());
				Assert.fail();
			}
		}
		
		public void textInput_Attribute_Check(TextInput textInput, String attribute, Boolean expectedCondition)
		{
			// ���������� ��� ����������
			Boolean actualCondition = true;
			
			// ���������� � ��������� ��������
			String attributeValue = textInput.getAttribute(attribute);
			
			// ���� ������� �� ���������� - ������� 'false'
			if(attributeValue == null)
			{
				actualCondition = false;
			}
			
			// ���� �������� �������� != ����������, �� ������� ������
			if(actualCondition != expectedCondition)
			{
				System.err.println("������ �� ����� �������� ���� ��� �����. ��������� �������� �������� != ���������." +
				   		   		   "\r\n��������� �������� �������� '" + attribute + "' = " + expectedCondition.toString() + 
				   		   		   "\r\n���� ��� ����� = " + textInput.findByMethod.toString());
				Assert.fail();
			}
		}
	}
	
	public String getCurrentDate()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Date date = new Date();
		String currentDate = dateFormat.format(date);
		return currentDate;
	}
	
    public String StringSpacesCut(String StringToCut)
    {
    	// ���� null, �� ������� ������
    	if (StringToCut == null) StringToCut = "";
    	
    	// ���� ������ ������, �� ������ �� ������
        if (!StringToCut.isEmpty())
        {
            // ��������� �������� ����� ������
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

            // ��������� �������� ����� �������
            String firstChar = Character.toString(StringToCut.charAt(0));
            for (int i = 0; firstChar.equals(" ") && (i < 5); i++)
            {
                StringToCut = StringToCut.substring(1);
                firstChar = Character.toString(StringToCut.charAt(0));
            }
        }
        return StringToCut;
    }
    
/*    public void file_Upload(String filePath, TextInput uploadButton)
    {
    	// ���������� ������� �����
    	File file = new File(filePath);
    	
    	// ���� �� ������� ����� ���� �� ���������� ����� - �������� ������
    	if(file.exists() == true)
    	{
    		System.err.println("������ �� ����� �������� �����. ����������� ������ ���� � �����." +
 		   		   			   "\r\n��������� ���� = " + filePath);
    		Assert.fail();
    	}

        //open upload window
    	uploadButton.click();

        //put path to your image in a clipboard
        StringSelection ss = new StringSelection(filePath);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);

        //imitate mouse events like ENTER, CTRL+C, CTRL+V
		try {
			Robot robot = new Robot();
	        robot.keyPress(KeyEvent.VK_ENTER);
	        robot.keyRelease(KeyEvent.VK_ENTER);
	        robot.keyPress(KeyEvent.VK_CONTROL);
	        robot.keyPress(KeyEvent.VK_V);
	        robot.keyRelease(KeyEvent.VK_V);
	        robot.keyRelease(KeyEvent.VK_CONTROL);
	        robot.keyPress(KeyEvent.VK_ENTER);
	        robot.keyRelease(KeyEvent.VK_ENTER);
		}
		catch (Exception e)
		{	
    		System.err.println("������ �� ����� �������� �����. �������� � win ����� �������� �����." +
 		   			   		   "\r\n������: = \r\n" + e.getMessage() + 
 		   			   		   "\r\n����: = \r\n" + e.getStackTrace());
    		Assert.fail();
		}
    }*/
    
/*	public void file_Download(Button DownloadButton, String ExpectedFileName)
	{
		// �������
		DownloadButton.click();	
		try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}
		
		// �����, ���� ������ �������� �����
		String folderPath = "C:\\Selenium_TestData\\Other\\TempFiles\\";
		
    	// ���������� ������� �����
    	File file = new File(folderPath + ExpectedFileName);
		
    	// ���� �� ������� ����� ���� �� ���������� ����� - �������� ������
    	if(file.exists() != true)
    	{
    		System.err.println("������ �������� �����. ���� ����������� � ��������� ����������." +
 		   		   			   "\r\n��������� ���� = " + file.getAbsolutePath());
    		Assert.fail();
    	}
    	
    	// ������� ����
    	else
    	{
    		file.delete();
    	}
	}*/
    
	public void fileDownload_Check(Button DownloadButton, String ExpectedFileName)
	{
		// �������
		DownloadButton.click();	
		try {Thread.sleep(3000);} catch (InterruptedException e) {e.printStackTrace();}
		
		// �����, ���� ������ �������� �����
		String folderPath = "C:\\Selenium_TestData\\Other\\TempFiles\\DownloadedFiles\\";
		
		// ����� ��� ����� � �����
		File folderToScan = new File(folderPath); 
	    File[] listOfFiles = folderToScan.listFiles();
	    int filesCount = listOfFiles.length;
		
    	// ���� �� ������� ����� ���� �� ���������� ����� - ������� ��� ����� � ������ ������
    	if(filesCount != 1)
    	{
    		// ������� ��� �����, ���� ��� ����
    		if(filesCount != 0)
    		{
        		for(int i = 0; i < filesCount; i++)
        		{
        			File tempFileVariable = listOfFiles[i];
        			tempFileVariable.delete();
        		}
    		}
    		
    		// ������� ������
    		System.err.println("������ �������� �����. ���������� ��������� ������ != ����������." +
 		   		   			   "\r\n��������� ���� = " + ExpectedFileName + 
 		   		   			   "\r\n���������� ��������� ������ = " + filesCount);
    		Assert.fail();
    	}
    	
    	// �������� ���� �� �������
    	File file = listOfFiles[0];
    	
    	// ����������� ���������� �����
    	String fileFullName = file.getName();
    	int splitter = fileFullName.lastIndexOf('.');
    	String fileName = fileFullName.substring(0, splitter);
    	String fileExtension = fileFullName.substring(splitter + 1);
    	
    	try
    	{
        	// ���� Excel ����, �� ��������� ����� �������� �����
        	if(fileExtension.equals("xls"))
        	{
            	splitter = ExpectedFileName.lastIndexOf('.');
            	String ExpectedFileName_Cut = ExpectedFileName.substring(0, splitter);
        		assertThat(fileName, startsWith(ExpectedFileName_Cut));
        	}
        	
        	// ��� ������ ��������� ������ �������� �����
        	else
        	{
    			assertThat(fileFullName, is(equalTo(ExpectedFileName)));
        	}
    	}
    	
		catch(Exception e)
		{
    		System.err.println("������ ��� �������� �������� ������������ �����." +
   		   			   		   "\r\n����� ������ = " + e.getMessage() +
   		   			   		   "\r\n���� = " + e.getStackTrace());
		}
		finally
		{
    		file.delete();
		}
	}
	
	public void leaveWarningCheck(WebDriver driver, String browser)
	{
		// �������� �� ����� �������� �� �������
		driver.findElement(By.className("header_link")).click();
		try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}
		
		// ���������� ���������� ��� ������ '������'
		String alertText = null;
		
		// ������ ������ � �������� ������
		try
		{
		Alert alert = driver.switchTo().alert();
		alertText = alert.getText();
		alert.dismiss();
		}
		catch(Exception e)
		{
    		System.err.println("������ ��� �������� '������' � ����� �� ���������. �� ������� ����� '�����'." +
 			   		   		   "\r\n������: = \r\n" + e.getMessage() + 
 			   		   		   "\r\n����: = \r\n" + e.getStackTrace());
    		Assert.fail();
		}
		
		// �������� ������ ������
		if(browser.equals("chrome"))
		{
			assertThat("�� �������� �������� ������������� ������. ��������� �� �������� ��� ���������� ��������� ������?", is(equalTo(alertText)));
		}
		else if(browser.equals("firefox"))
		{
			assertThat("��� �������� ������ ��� �����������, ��� �� ������ ���� � ��� ���� �������� ���� ������ ����� �� �����������.", is(equalTo(alertText)));
		}
		else if(browser.equals("ie"))
		{
			
		}
	}
	
	public void elementExistenceCheck(WebElement element, boolean Exists)
	{
		// �������� ���������� ��� ��������
		boolean elementExistence = false;
		
		// �������� ������������� ���������
		if(element.isDisplayed()) elementExistence = true;
		
		// ���� ��������� �������� �� ����� ��������� - ������
		if(elementExistence != Exists)
		{
    		System.err.println("������ ��� �������� ������������� ��������." +
	   		   		   "\r\n�������: = \r\n" + element.toString() + 
	   		   		   "\r\n��������� �������� �������������: = \r\n" + Exists + 
	   		   		   "\r\n������� �������� �������������: = \r\n" + elementExistence);
    		Assert.fail();
		}
	}
	
	private void simpleWait(int Seconds)
	{
		try
		{
			Thread.sleep(Seconds * 1000);
		}
		catch(InterruptedException e){throw new RuntimeException(e);}
	}
}
