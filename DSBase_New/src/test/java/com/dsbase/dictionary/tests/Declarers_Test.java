package com.dsbase.dictionary.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dsbase.core.BaseTest;
import com.dsbase.core.database.DbQueries;
import com.dsbase.core.database.DbStatements;
import com.dsbase.core.web.pages.dictionary.DeclarersPage;
import com.dsbase.core.web.pages.other.LogInPage;
import com.dsbase.core.web.pages.other.MainPage;

public class Declarers_Test extends BaseTest 
{
	@BeforeMethod(alwaysRun = true, dependsOnMethods = {"setUp"})
	public void DeletionViaDatabase() throws Exception
	{
	    // �������� ���������(�.�. �� ��������� �� ���������)
	    String DrugErrorMessage = DbQueries.DrugRegistryTests.DrugQueries.DeletionQueries.DrugDeletion.ErrorMessage;	  
	    String DrugDeletionStatement = DbQueries.DrugRegistryTests.DrugQueries.DeletionQueries.DrugDeletion.DrugDeletionStatement;    
	    new DbStatements().SimpleStatement(sqlConnection, DrugDeletionStatement, DrugErrorMessage);
		
	    // �������� ���������
	    String ErrorMessage = DbQueries.DictionaryTests.DeclarersQueries.DeletionQueries.DeclarerDeletion.ErrorMessage;
	    String DeletionStatement = DbQueries.DictionaryTests.DeclarersQueries.DeletionQueries.DeclarerDeletion.DeclarerDeletionStatement;
	    new DbStatements().SimpleStatement(sqlConnection, DeletionStatement, ErrorMessage);
	}
	
	@Test(groups = { "Declarers_Test" })
	public void Declarers_TestMethod()
	{	
		// ������� �� �������
		LogInPage authorizationPage = new MainPage(driver).redirectToLogInPage();
		MainPage mainPage = authorizationPage.logInAs("admin_auto", "123456");
		mainPage.waitForPageReady();
		
		// ���������� �������������
		DeclarersPage declarersPage = mainPage.new goTo().new DictionaryBlock().declarersPage();
		declarersPage.waitForGridUnblocked();
		declarersPage.declarerAdd();
		declarersPage.addedDeclarer_Check();
		
		// �������������� �������������
		declarersPage.declarerEdit();
		declarersPage.editedDeclarer_Check();
		
		// �������� �������������
		declarersPage.declarerDelete();
		declarersPage.deletedDeclarer_Check();
		
		// ����� �� �������
		declarersPage.userOut();
	}
}
