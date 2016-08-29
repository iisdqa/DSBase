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
			// Определение количества рядов
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			
			// Удаление скрытого ряда
			rows.remove(0);
			
			// Определение размера возвращаемого массива
			String[][] GridValues = new String[rows.size()][];
			
			// Перебираем ряды
			for(int rnum=0; rnum < rows.size(); rnum++)
			{
				// Определяем количество колонок
				List<WebElement> columns=rows.get(rnum).findElements(By.tagName("td"));
				int sizeBeforeCut = columns.size();
							
				// Отсеевание скрытых ячеек
				for(int cnum= (sizeBeforeCut - 1); cnum >= 0; cnum--)
				{
					if(columns.get(cnum).getCssValue("display").equals("none"))
					{
						columns.remove(cnum);
					}
				}
				
				// Определение размера массива с значениями ячеек
				String[] ColumnValues = new String[columns.size()];
				
				// Записываем значения ячеек в массив
				for(int cnum=0; cnum<columns.size(); cnum++)
				{
					ColumnValues[cnum] = columns.get(cnum).getText();
				}
				
				// Положить значения колонок в ряд 
				GridValues[rnum] = ColumnValues;
			}
			return GridValues;
		}
		
		public String[][] GetSpecificRows(WebElement table, int RowsToGet)
		{
			// Определение количества рядов
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			
			// Удаление скрытого ряда
			rows.remove(0);
			
			// Определение размера возвращаемого массива
			String[][] GridValues = new String[RowsToGet][];
			
			// Перебираем ряды
			for(int rnum=0; rnum < RowsToGet; rnum++)
			{
				// Определяем количество колонок
				List<WebElement> columns=rows.get(rnum).findElements(By.tagName("td"));
				int sizeBeforeCut = columns.size();
				
				// Отсеевание скрытых ячеек
				for(int cnum= (sizeBeforeCut - 1); cnum != 0; cnum--)
				{
					if(columns.get(cnum).getCssValue("display").equals("none"))
					{
						columns.remove(cnum);
					}
				}
				
				// Определение размера массива с значениями ячеек
				String[] ColumnValues = new String[columns.size()];
				
				// Записываем значения ячеек в массив
				for(int cnum=0; cnum<columns.size(); cnum++)
				{
					ColumnValues[cnum] = columns.get(cnum).getText();
				}
				
				// Положить значения колонок в ряд 
				GridValues[rnum] = ColumnValues;
			}
			return GridValues;
		}
		
		public void gridValuesEqualityCheck(String[][] ExpectedValues, String[][] ActualValues)
		{
			// Проверка на соответствие количества рядов
			if(ExpectedValues.length != ActualValues.length)
			{
				System.err.println("Ошибка во время проверки грида. Ожидаемое количество рядов != реальному количеству рядов." +
						   		   "\r\nОжидаемое количество = " + ExpectedValues.length +
						   		   "\r\nРеальное количество = " + ActualValues.length);
				Assert.fail();
			}
			
			// Перебираем ряды
			for(int rowNum = 0; rowNum < ActualValues.length; rowNum++)
			{
				// Если количество ожидаемых колонок в ряде != количеству реальных, то выводить сообщение 
				if(ExpectedValues[rowNum].length != ActualValues[rowNum].length)
				{
					System.err.println("Ошибка во время проверки грида. Ожидаемое количество колонок в проверяемом рядке != реальному количеству колонок." +
					   		   "\r\nОжидаемое количество = " + ExpectedValues[rowNum].length +
					   		   "\r\nРеальное количество = " + ActualValues[rowNum].length + 
					   		   "\r\nПроверяемый ряд = " + (rowNum + 1));
					Assert.fail();
				}
				
				// Перебираем колонки
				for(int colNum = 0; colNum < ActualValues[rowNum].length; colNum++)
				{
					// Если находим время, то обрезаем
					if(ActualValues[rowNum][colNum].contains(":") == true)
					{
						// Считаем количество знаков ":"
						int countOfCharacters = ActualValues[rowNum][colNum].length() - ActualValues[rowNum][colNum].replace(":", "").length();
						
						// Если количество знаков ":", то обрезаем время
						if(countOfCharacters == 2)
						{
							ActualValues[rowNum][colNum] = ActualValues[rowNum][colNum].substring(0, (ActualValues[rowNum][colNum].indexOf(" ")));
						}
					}

					// Проверка равенства ожидаемого значения и реального значения
					assertThat(ActualValues[rowNum][colNum], is(equalTo(ExpectedValues[rowNum][colNum])));
				}			
			}
		}
	}
	
	public class WorkWith_Excel
	{
		public String[][] GetHeadPart_ForExcel(WebElement thead)
		{
			// Определение количества рядов
			List<WebElement> rows = thead.findElements(By.tagName("tr"));
			
			// Определение размера возвращаемого массива
			String[][] GridValues = new String[rows.size()][];
			
			// Перебираем ряды
			for(int rnum=0; rnum < rows.size(); rnum++)
			{
				// Определяем количество колонок
				List<WebElement> columns=rows.get(rnum).findElements(By.tagName("th"));
				int sizeBeforeCut = columns.size();
							
				// Отсеевание скрытых ячеек
				for(int cnum= (sizeBeforeCut - 1); cnum >= 0; cnum--)
				{
					if(columns.get(cnum).getCssValue("display").equals("none"))
					{
						columns.remove(cnum);
					}
				}
				
				// Удаление лишних значений
				columns.remove(columns.size() - 1);
				columns.remove(0);
				
				// Определение размера массива с значениями ячеек
				String[] ColumnValues = new String[columns.size()];
				
				// Записываем значения ячеек в массив
				for(int cnum=0; cnum<columns.size(); cnum++)
				{
					// Взять текст из элемента
					ColumnValues[cnum] = columns.get(cnum).findElement(By.tagName("div")).getAttribute("textContent");   // Также можно пробовать атрибуты 'innerText', 'innerHTML'
				}
				
				// Положить значения колонок в ряд 
				GridValues[rnum] = ColumnValues;
			}
			return GridValues;
		}
		
		public String[][] GetAllRows_ForExcel(WebElement tbody)
		{
			// Определение количества рядов
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			
			// Удаление скрытого ряда
			rows.remove(0);
			
			// Определение размера возвращаемого массива
			String[][] GridValues = new String[rows.size()][];
			
			// Перебираем ряды
			for(int rnum=0; rnum < rows.size(); rnum++)
			{
				// Определяем количество колонок
				List<WebElement> columns=rows.get(rnum).findElements(By.tagName("td"));
				int sizeBeforeCut = columns.size();
							
				// Отсеевание скрытых ячеек
				for(int cnum= (sizeBeforeCut - 1); cnum >= 0; cnum--)
				{
					if(columns.get(cnum).getCssValue("display").equals("none"))
					{
						columns.remove(cnum);
					}
				}
				
				// Удаление лишних значений
				columns.remove(columns.size() - 1);
				columns.remove(0);
				
				// Определение размера массива с значениями ячеек
				String[] ColumnValues = new String[columns.size()];
				
				// Записываем значения ячеек в массив
				for(int cnum=0; cnum<columns.size(); cnum++)
				{
					ColumnValues[cnum] = columns.get(cnum).getText();
				}
				
				// Положить значения колонок в ряд 
				GridValues[rnum] = ColumnValues;
			}
			return GridValues;
		}
		
		public String[][] get_ExcelValues(Button DownloadButton, String ExpectedFileName)
		{
			// Скачать
			DownloadButton.click();	
			simpleWait(5);
			
			// Папка, куда должны попадать файлы
			String folderPath = "C:\\Selenium_TestData\\Other\\TempFiles\\";
			
			// Найти все файлы в папке
			File folderToScan = new File(folderPath); 
		    File[] listOfFiles = folderToScan.listFiles();
		    int filesCount = listOfFiles.length;
			
	    	// Если не удается найти файл по указанному линку - удалить все файлы и выдать ошибку
	    	if(filesCount != 1)
	    	{
	    		// Удалить все файлы, если они есть
	    		if(filesCount != 0)
	    		{
	        		for(int i = 0; i < filesCount; i++)
	        		{
	        			File tempFileVariable = listOfFiles[i];
	        			tempFileVariable.delete();
	        		}
	    		}
	    		
	    		// Вывести ошибку
	    		System.err.println("Ошибка загрузки файла Excel для проверки. Количество найденных файлов != ожидаемому." +
	 		   		   			   "\r\nОжидаемый файл = " + ExpectedFileName + 
	 		   		   			   "\r\nКоличество найденных файлов = " + filesCount);
	    		Assert.fail();
	    	}
	    	
	    	// Вытянуть файл из массива
	    	File file = listOfFiles[0];
	    	
	    	// Определение массива для возврата
	    	String[][] data = null;
	    	
	    	try
	    	{
	    		data = getExcelValues(file);
	    	}  	
			catch(Exception e)
			{
	    		System.err.println("Ошибка при считывании значений из Excel файла." +
	   		   			   		   "\r\nТекст ошибки = " + e.getMessage() +
	   		   			   		   "\r\nСтэк = " + e.getStackTrace());
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
	        	// Считывание файла
	            w = Workbook.getWorkbook(excelFile);
	            
	            // Взять первый лист
	            Sheet sheet = w.getSheet(0);    
	        
	            // Определение размера массива
	            data = new String[sheet.getRows()][sheet.getColumns()];
	            
	            // Перебор рядов
	            for (int i = 0; i <sheet.getRows(); i++) 
	            {
	            	//Перебор колонок + запись ячеек в массив
	                for (int j = 0; j < sheet.getColumns(); j++) 
	                {
	                	// Взять значение из ячейки
	                    Cell cell = sheet.getCell(j, i);
	                    String cellValue = cell.getContents();
	                    
	                    // Убрать лишние символы('-' и ';')
	                    cellValue.replace("-", "");
	                    cellValue.replace(";", "");
	                    cellValue = StringSpacesCut(cellValue);
	                    // Запись значения в массив
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
			// Переменная для результата
			Boolean actualCondition = true;
			
			// Переменная с значением атрибута
			String attributeValue = checkBox.getAttribute(attribute);
			
			// Если чекбокс не установлен - вернуть 'false'
			if(attributeValue == null)
			{
				actualCondition = false;
			}
			
			// Если реальное значение != ожидаемому, то выводим ошибку
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
			// Переменная для результата
			Boolean actualCondition = true;
			
			// Переменная с значением атрибута
			String attributeValue = textInput.getAttribute(attribute);
			
			// Если чекбокс не установлен - вернуть 'false'
			if(attributeValue == null)
			{
				actualCondition = false;
			}
			
			// Если реальное значение != ожидаемому, то выводим ошибку
			if(actualCondition != expectedCondition)
			{
				System.err.println("Ошибка во время проверки поля для ввода. Ожидаемое значение атрибута != реальному." +
				   		   		   "\r\nОжидаемое значение атрибута '" + attribute + "' = " + expectedCondition.toString() + 
				   		   		   "\r\nПоле для ввода = " + textInput.findByMethod.toString());
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
    	// Если null, то сделать пустым
    	if (StringToCut == null) StringToCut = "";
    	
    	// Если пустая строка, то ничего не делать
        if (!StringToCut.isEmpty())
        {
            // Обрезание пробелов после строки
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

            // Обрезание пробелов перед строкой
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
    	// Добавление объекта файла
    	File file = new File(filePath);
    	
    	// Если не удается найти файл по указанному линку - выдавать ошибку
    	if(file.exists() == true)
    	{
    		System.err.println("Ошибка во время загрузки файла. Неправильно указан путь к файлу." +
 		   		   			   "\r\nОжидаемый путь = " + filePath);
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
    		System.err.println("Ошибка во время загрузки файла. Проблемы с win окном загрузки файла." +
 		   			   		   "\r\nОшибка: = \r\n" + e.getMessage() + 
 		   			   		   "\r\nСтэк: = \r\n" + e.getStackTrace());
    		Assert.fail();
		}
    }*/
    
/*	public void file_Download(Button DownloadButton, String ExpectedFileName)
	{
		// Скачать
		DownloadButton.click();	
		try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}
		
		// Папка, куда должны попадать файлы
		String folderPath = "C:\\Selenium_TestData\\Other\\TempFiles\\";
		
    	// Добавление объекта файла
    	File file = new File(folderPath + ExpectedFileName);
		
    	// Если не удается найти файл по указанному линку - выдавать ошибку
    	if(file.exists() != true)
    	{
    		System.err.println("Ошибка загрузки файла. Файл отсутствует в указанной дериктории." +
 		   		   			   "\r\nОжидаемый файл = " + file.getAbsolutePath());
    		Assert.fail();
    	}
    	
    	// Удалить файл
    	else
    	{
    		file.delete();
    	}
	}*/
    
	public void fileDownload_Check(Button DownloadButton, String ExpectedFileName)
	{
		// Скачать
		DownloadButton.click();	
		try {Thread.sleep(3000);} catch (InterruptedException e) {e.printStackTrace();}
		
		// Папка, куда должны попадать файлы
		String folderPath = "C:\\Selenium_TestData\\Other\\TempFiles\\DownloadedFiles\\";
		
		// Найти все файлы в папке
		File folderToScan = new File(folderPath); 
	    File[] listOfFiles = folderToScan.listFiles();
	    int filesCount = listOfFiles.length;
		
    	// Если не удается найти файл по указанному линку - удалить все файлы и выдать ошибку
    	if(filesCount != 1)
    	{
    		// Удалить все файлы, если они есть
    		if(filesCount != 0)
    		{
        		for(int i = 0; i < filesCount; i++)
        		{
        			File tempFileVariable = listOfFiles[i];
        			tempFileVariable.delete();
        		}
    		}
    		
    		// Вывести ошибку
    		System.err.println("Ошибка загрузки файла. Количество найденных файлов != ожидаемому." +
 		   		   			   "\r\nОжидаемый файл = " + ExpectedFileName + 
 		   		   			   "\r\nКоличество найденных файлов = " + filesCount);
    		Assert.fail();
    	}
    	
    	// Вытянуть файл из массива
    	File file = listOfFiles[0];
    	
    	// Определение расширения файла
    	String fileFullName = file.getName();
    	int splitter = fileFullName.lastIndexOf('.');
    	String fileName = fileFullName.substring(0, splitter);
    	String fileExtension = fileFullName.substring(splitter + 1);
    	
    	try
    	{
        	// Если Excel файл, то проверить часть названия файла
        	if(fileExtension.equals("xls"))
        	{
            	splitter = ExpectedFileName.lastIndexOf('.');
            	String ExpectedFileName_Cut = ExpectedFileName.substring(0, splitter);
        		assertThat(fileName, startsWith(ExpectedFileName_Cut));
        	}
        	
        	// Для других проверить полное название файла
        	else
        	{
    			assertThat(fileFullName, is(equalTo(ExpectedFileName)));
        	}
    	}
    	
		catch(Exception e)
		{
    		System.err.println("Ошибка при проверке названия загруженного файла." +
   		   			   		   "\r\nТекст ошибки = " + e.getMessage() +
   		   			   		   "\r\nСтэк = " + e.getStackTrace());
		}
		finally
		{
    		file.delete();
		}
	}
	
	public void leaveWarningCheck(WebDriver driver, String browser)
	{
		// Кликнуть по линку перехода на главную
		driver.findElement(By.className("header_link")).click();
		try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}
		
		// Объявление переменной для текста 'алерта'
		String alertText = null;
		
		// Запись текста и закрытие алерта
		try
		{
		Alert alert = driver.switchTo().alert();
		alertText = alert.getText();
		alert.dismiss();
		}
		catch(Exception e)
		{
    		System.err.println("Ошибка при проверке 'алерта' о уходе из странички. Не удалось найти 'алерт'." +
 			   		   		   "\r\nОшибка: = \r\n" + e.getMessage() + 
 			   		   		   "\r\nСтэк: = \r\n" + e.getStackTrace());
    		Assert.fail();
		}
		
		// Проверка текста алерта
		if(browser.equals("chrome"))
		{
			assertThat("На странице остались несохраненные данные. Вернуться на страницу для сохранения внесенных данных?", is(equalTo(alertText)));
		}
		else if(browser.equals("firefox"))
		{
			assertThat("Эта страница просит вас подтвердить, что вы хотите уйти — при этом введённые вами данные могут не сохраниться.", is(equalTo(alertText)));
		}
		else if(browser.equals("ie"))
		{
			
		}
	}
	
	public void elementExistenceCheck(WebElement element, boolean Exists)
	{
		// Объявить переменную для проверки
		boolean elementExistence = false;
		
		// Проверка существования эелемента
		if(element.isDisplayed()) elementExistence = true;
		
		// Если ожидаемое значение не равно реальному - ошибка
		if(elementExistence != Exists)
		{
    		System.err.println("Ошибка при проверке существования элемента." +
	   		   		   "\r\nЭлемент: = \r\n" + element.toString() + 
	   		   		   "\r\nОжидаемое значение существования: = \r\n" + Exists + 
	   		   		   "\r\nТекущее значение существования: = \r\n" + elementExistence);
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
