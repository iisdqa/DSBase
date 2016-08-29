package com.dsbase.drug_registry.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dsbase.core.BaseTest;
import com.dsbase.core.database.DbQueries;
import com.dsbase.core.database.DbStatements;
import com.dsbase.core.web.CommonActions;
import com.dsbase.core.web.CustomMethods;
import com.dsbase.core.web.pages.dictionary.DeclarersPage;
import com.dsbase.core.web.pages.dictionary.ManufacterersPage;
import com.dsbase.core.web.pages.drug.DrugEditPage;
import com.dsbase.core.web.pages.drug.DrugPassportPage;
import com.dsbase.core.web.pages.drug.DrugRegistrationPage;
import com.dsbase.core.web.pages.other.DrugRegistryPage;
import com.dsbase.core.web.pages.other.LogInPage;
import com.dsbase.core.web.pages.other.MainPage;

public class DrugRegistration_Test extends BaseTest
{
	@BeforeMethod(alwaysRun = true, dependsOnMethods = {"setUp"})
	public void DeletionViaDatabase() throws Exception
	{
	    // �������� ���������
	    String DrugErrorMessage = DbQueries.DrugRegistryTests.DrugQueries.DeletionQueries.DrugDeletion.ErrorMessage;	  
	    String DrugDeletionStatement = DbQueries.DrugRegistryTests.DrugQueries.DeletionQueries.DrugDeletion.DrugDeletionStatement;    
	    new DbStatements().SimpleStatement(sqlConnection, DrugDeletionStatement, DrugErrorMessage);	   	  
		
	    // �������� ���������
	    String ErrorMessage = DbQueries.DictionaryTests.DeclarersQueries.DeletionQueries.DeclarerDeletion.ErrorMessage;
	    String DeletionStatement = DbQueries.DictionaryTests.DeclarersQueries.DeletionQueries.DeclarerDeletion.DrugDeclarerDeletionStatement;
	    new DbStatements().SimpleStatement(sqlConnection, DeletionStatement, ErrorMessage);
	    
	    // �������� �������������
	    String ManufacturerErrorMessage = DbQueries.DictionaryTests.ManufacturersQueries.DeletionQueries.ManufacturerDeletion.ErrorMessage;	  
	    String ManufacturerDeletionStatement = DbQueries.DictionaryTests.ManufacturersQueries.DeletionQueries.ManufacturerDeletion.DrugManufacturerDeletionStatement;    
	    new DbStatements().SimpleStatement(sqlConnection, ManufacturerDeletionStatement, ManufacturerErrorMessage);
	}
	
	@Test(groups = { "DrugRegistration_Test" })
	public void DrugRegistration_TestMethod()
	{	
		// ������� �� �������
		LogInPage authorizationPage = new MainPage(driver).redirectToLogInPage();
		MainPage mainPage = authorizationPage.logInAs("admin_auto", "123456");
		mainPage.waitForPageReady();
		
		// ���������� �������������
		ManufacterersPage manufacterersPage = mainPage.new goTo().new DictionaryBlock().manufacterersPage();
		manufacterersPage.waitForGridUnblocked();
		manufacterersPage.drugManufactererAdd();
		manufacterersPage.addedDrugManufacterer_Check();

		// ���������� ���������
		
		mainPage = 	manufacterersPage.backToMainPage();
		mainPage.waitForPageReady();
		DeclarersPage declarersPage = mainPage.new goTo().new DictionaryBlock().declarersPage();
		declarersPage.waitForGridUnblocked();
		declarersPage.drugDeclarerAdd();
		declarersPage.addedDrugDeclarer_Check();

		// ������� � ��������� ���������� ���������
		// ���������� �������� �����
		// �������� ���������������� ��������� ��� ������� ���� �� ���������
		// ���������� ���
		// ����������/�������������� �������������� ���������
		
		mainPage = 	declarersPage.backToMainPage();
		mainPage.waitForPageReady();
		DrugRegistrationPage registrationPage = mainPage.new goTo().drugRegistrationPage();
		registrationPage.mainFields_FillUp();
		new CustomMethods().leaveWarningCheck(driver, browser);
		registrationPage.mnn_Add();
		registrationPage.addedMnn_check();
		registrationPage.manufacturer_Add();
		registrationPage.addedManufacturer_check();
		
		// ���������� ������������ ��������, ��������� � ����������
		
		registrationPage.substance_Add();
		registrationPage.addedSubstance_check();
		registrationPage.doc_Add();
		registrationPage.addedDoc_check();	
		registrationPage.fileUnload_check();
		registrationPage.employee_Add();
		registrationPage.addedEmployee_check();
		
		// ���������� ���������
		// �������� �������� ���������
		// ������� � �������������� ���������
		DrugPassportPage passport = registrationPage.drug_Save();	
		passport.addedPassport_Check();
		DrugEditPage editPage = passport.drug_Edit();
		
		// �������������� ��� � �������������
		
		editPage.addedMnn_check();
		editPage.mnn_Edit();
		editPage.editedMnn_check();
		editPage.addedManufacturer_check();
		editPage.manufacturer_Edit();
		editPage.addedManufacturer_check();
		
		// �������������� ������������ ��������, ��������� � ����������
		// ���������� ��������� + �������� ���������
		
		editPage.addedSubstance_check();
		editPage.substance_Edit();
		editPage.editedSubstance_check();
		editPage.addedDoc_check();
		editPage.doc_Edit();
		editPage.editedDoc_check();
		editPage.addedEmployee_check();
		editPage.employee_Edit();
		editPage.editedEmployee_check();
		passport = editPage.drug_Save();
		passport.editedPassport_Check();
		
		// ����� ��������� � ������� ��
		mainPage = new CommonActions().backToMainPage(driver);
		mainPage.waitForPageReady();
		DrugRegistryPage drugRegistryPage = mainPage.new goTo().drugRegistryPage();
		drugRegistryPage.WaitForPageReady();
		drugRegistryPage.drug_Search();
		drugRegistryPage.foundDrug_Check();
		drugRegistryPage.excelUnload_Check();
		
		drugRegistryPage.userOut();
	}
}
