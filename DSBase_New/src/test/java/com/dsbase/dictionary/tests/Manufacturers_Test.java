package com.dsbase.dictionary.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dsbase.core.BaseTest;
import com.dsbase.core.database.DbQueries;
import com.dsbase.core.database.DbStatements;
import com.dsbase.core.web.pages.dictionary.ManufacterersPage;
import com.dsbase.core.web.pages.other.LogInPage;
import com.dsbase.core.web.pages.other.MainPage;

public class Manufacturers_Test extends BaseTest 
{
	@BeforeMethod(alwaysRun = true, dependsOnMethods = {"setUp"})
	public void DeletionViaDatabase() throws Exception
	{
	    // Удаление препарата(т.к. он ссылается на производителя)
	    String DrugErrorMessage = DbQueries.DrugRegistryTests.DrugQueries.DeletionQueries.DrugDeletion.ErrorMessage;	  
	    String DrugDeletionStatement = DbQueries.DrugRegistryTests.DrugQueries.DeletionQueries.DrugDeletion.DrugDeletionStatement;    
	    new DbStatements().SimpleStatement(sqlConnection, DrugDeletionStatement, DrugErrorMessage);
		
	    // Определение ошибки, которая будет появляться в случае падения запроса
	    String ErrorMessage = DbQueries.DictionaryTests.ManufacturersQueries.DeletionQueries.ManufacturerDeletion.ErrorMessage;
	    String DeletionStatement = DbQueries.DictionaryTests.ManufacturersQueries.DeletionQueries.ManufacturerDeletion.ManufacturerDeletionStatement;
	    new DbStatements().SimpleStatement(sqlConnection, DeletionStatement, ErrorMessage);
	}
	
	@Test(groups = { "Manufacturers_Test" })
	public void Manufacturers_TestMethod()
	{	
		// Переход на главную
		LogInPage authorizationPage = new MainPage(driver).redirectToLogInPage();
		MainPage mainPage = authorizationPage.logInAs("admin_auto", "123456");
		mainPage.waitForPageReady();
		
		// Добавление производителя
		ManufacterersPage manufacterersPage = mainPage.new goTo().new DictionaryBlock().manufacterersPage();
		manufacterersPage.waitForGridUnblocked();
		manufacterersPage.manufactererAdd();
		manufacterersPage.addedManufacterer_Check();
		
		// Редактирование производителя
		manufacterersPage.manufactererEdit();
		manufacterersPage.editedManufacterer_Check();
		
		// Удаление производителя
		manufacterersPage.manufactererDelete();
		manufacterersPage.deletedManufacterer_Check();
		
		// Выход из системы
		manufacterersPage.userOut();
	}
}
