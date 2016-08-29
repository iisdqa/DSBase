package com.dsbase.core.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.testng.Assert;

public class DbQueries 
{	
	private static String queriesPath = "C:\\Selenium_TestData\\SQL\\SQL_Queries\\";
	
	public static class DrugRegistryTests
	{
		public static class DrugQueries
		{
			public static class DeletionQueries
			{
				public static class DrugDeletion
				{
					// Определение ошибки, которую будем выводить в случае падения запроса
				    public static final String ErrorMessage = "\r\n\r\nПроизошла ошибка при попытке удаления препарата.\r\nТекст ошибки:\r\n";
	 
				    // Определение текста запроса
				    public static String DrugDeletionStatement = readFile(queriesPath + "drug_deletion.sql");
				}
				
				public static class InstructionDeletion
				{
					// Определение ошибки, которую будем выводить в случае падения запроса
				    public static final String ErrorMessage = "\r\n\r\nПроизошла ошибка при попытке удаления инструкции препарата.\r\nТекст ошибки:\r\n";
	 
				    // Определение текста запроса
				    public static String InstructionDeletionStatement = readFile(queriesPath + "instruction_deletion.sql");
				}
				
				public static class ChangeDeletion
				{
					// Определение ошибки, которую будем выводить в случае падения запроса
				    public static final String ErrorMessage = "\r\n\r\nПроизошла ошибка при попытке удаления изменения препарата.\r\nТекст ошибки:\r\n";
	 
				    // Определение текста запроса
				    public static String ChangeDeletionStatement = readFile(queriesPath + "drug_change_deletion.sql");
				}
				
				public static class ReferentDrugsDeletion
				{
					// Определение ошибки, которую будем выводить в случае падения запроса
				    public static final String ErrorMessage = "\r\n\r\nПроизошла ошибка при попытке удаления референтных ЛС.\r\nТекст ошибки:\r\n";
	 
				    // Определение текста запроса
				    public static String RefDrugsDeletionStatement = readFile(queriesPath + "reference_drugs_deletion.sql");
				}
			}
		}
	}
	
	public static class DictionaryTests
	{
		public static class ManufacturersQueries
		{
			public static class DeletionQueries
			{
				public static class ManufacturerDeletion
				{
					// Определение ошибки, которую будем выводить в случае падения запроса
				    public static String ErrorMessage = "\r\n\r\nПроизошла ошибка при попытке удаления производителя.\r\nТекст ошибки:\r\n";
	 
				    // Определение текста запроса
				    public static String ManufacturerDeletionStatement = "delete from [dbo].[drug_manufacturer] " +
				    						   				      		 "where manufacturer_en = 'For automation'";
				    
				    // Определение текста запроса
				    public static String DrugManufacturerDeletionStatement = "delete from [dbo].[drug_manufacturer] " +
				    						   				      		 "where manufacturer_en = 'For drugAdd'";
				}
			}
		}
		public static class DeclarersQueries
		{
			public static class DeletionQueries
			{
				public static class DeclarerDeletion
				{
					// Определение ошибки, которую будем выводить в случае падения запроса
				    public static String ErrorMessage = "\r\n\r\nПроизошла ошибка при попытке удаления заявителя.\r\nТекст ошибки:\r\n";
	 
				    // Определение текста запроса
				    public static String DeclarerDeletionStatement = "delete from [dict].[dic_declarer] " +
				    						   				      	 "where declarer_en = 'For automation'";
				    
				    // Определение текста запроса
				    public static String DrugDeclarerDeletionStatement = "delete from [dict].[dic_declarer] " +
				    						   				      	 	 "where declarer_en = 'For drugAdd'";
				}
			}
		}
	}
	
	public static class AdministrationTests
	{
		public static class UsersAndAuditQueries
		{
			public static class DeletionQueries
			{
				// Определение ошибки, которую будем выводить в случае падения запроса
			    public static final String ErrorMessage = "\r\n\r\nПроизошла ошибка при попытке удаления пользователя.\r\nТекст ошибки:\r\n";
 			 			    
			    // Определение текста запроса
			    public static String UserDeletionStatement = readFile(queriesPath+ "user_deletion.sql");
			}
		}
	}
	
	// Прочитать текст из файла
	private static String readFile(String path)
	{
		File file = new File(path);
		Reader input = null;
		StringWriter output = new StringWriter();
		try 
		{
			input = new FileReader(file);
		} 
		catch (FileNotFoundException e1) 
		{
			e1.printStackTrace();
			Assert.fail("Не удалось найти файл при попытке определения SQL запроса.");
		}
		try 
		{
		  IOUtils.copy(input, output);
		  input.close();
		}	  
		catch (Exception e) 
	    {
			e.printStackTrace();
	    }
		String fileContents = output.toString();
		return fileContents;
	}
}
