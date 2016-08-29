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
	    // �������� ���������(�.�. �� ��������� �� �������������)
	    String DrugErrorMessage = DbQueries.DrugRegistryTests.DrugQueries.DeletionQueries.DrugDeletion.ErrorMessage;	  
	    String DrugDeletionStatement = DbQueries.DrugRegistryTests.DrugQueries.DeletionQueries.DrugDeletion.DrugDeletionStatement;    
	    new DbStatements().SimpleStatement(sqlConnection, DrugDeletionStatement, DrugErrorMessage);
		
	    // ����������� ������, ������� ����� ���������� � ������ ������� �������
	    String ErrorMessage = DbQueries.DictionaryTests.ManufacturersQueries.DeletionQueries.ManufacturerDeletion.ErrorMessage;
	    String DeletionStatement = DbQueries.DictionaryTests.ManufacturersQueries.DeletionQueries.ManufacturerDeletion.ManufacturerDeletionStatement;
	    new DbStatements().SimpleStatement(sqlConnection, DeletionStatement, ErrorMessage);
	}
	
	@Test(groups = { "Manufacturers_Test" })
	public void Manufacturers_TestMethod()
	{	
		// ������� �� �������
		LogInPage authorizationPage = new MainPage(driver).redirectToLogInPage();
		MainPage mainPage = authorizationPage.logInAs("admin_auto", "123456");
		mainPage.waitForPageReady();
		
		// ���������� �������������
		ManufacterersPage manufacterersPage = mainPage.new goTo().new DictionaryBlock().manufacterersPage();
		manufacterersPage.waitForGridUnblocked();
		manufacterersPage.manufactererAdd();
		manufacterersPage.addedManufacterer_Check();
		
		// �������������� �������������
		manufacterersPage.manufactererEdit();
		manufacterersPage.editedManufacterer_Check();
		
		// �������� �������������
		manufacterersPage.manufactererDelete();
		manufacterersPage.deletedManufacterer_Check();
		
		// ����� �� �������
		manufacterersPage.userOut();
	}
}
