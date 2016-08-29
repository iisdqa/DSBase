package com.dsbase.drug_registry.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.dsbase.core.BaseTest;
import com.dsbase.core.database.DbQueries;
import com.dsbase.core.database.DbStatements;
import com.dsbase.core.web.pages.drug.DrugChangeAddEditPage;
import com.dsbase.core.web.pages.drug.DrugChangesPage;
import com.dsbase.core.web.pages.drug.DrugPassportPage;
import com.dsbase.core.web.pages.other.DrugRegistryPage;
import com.dsbase.core.web.pages.other.LogInPage;
import com.dsbase.core.web.pages.other.MainPage;

public class DrugChanges_Test extends BaseTest
{	
	@Test(groups = {"DrugChanges_Test"})
	public void DrugChanges_TestMethod()
	{	
		// Переход на главную
		
		LogInPage authorizationPage = new MainPage(driver).redirectToLogInPage();
		MainPage mainPage = authorizationPage.logInAs("admin_auto", "123456");
		mainPage.waitForPageReady();
		
		// Поиск препарата
		
		DrugRegistryPage drugRegistryPage = mainPage.new goTo().drugRegistryPage();
		drugRegistryPage.WaitForPageReady();
		drugRegistryPage.drug_Search();
		drugRegistryPage.foundDrug_Check();

		// Переход к страничке инструкций
		
		DrugPassportPage passport = drugRegistryPage.passport_Open();
		DrugChangesPage changes = passport.new GoTo().changesPage();
		
		// Добавление изменения
		
		DrugChangeAddEditPage addEditPage = changes.changeAdd_Open();
		addEditPage.mainPart_FillUp();
		addEditPage.manufacturer_Add();
		addEditPage.addedManufacturer_check();
		changes = addEditPage.change_Save();
		changes.addedChange_Check();
		
		// Редактирование изменения
		
		addEditPage = changes.changeEdit_Open();
		addEditPage.mainPart_Edit();
		addEditPage.addedManufacturer_check();
		addEditPage.manufacturer_Edit();
		addEditPage.addedManufacturer_check();
		addEditPage.manufacturer_Delete();
		addEditPage.deletedManufacturer_check();
		changes = addEditPage.change_Save();
		changes.editedChange_Check();

		// Проверка изменения на страничке паспорта препарата
		passport = changes.back_ToPassport_Page();
		passport.new For_Other_Tests().new DrugChanges_Test().added_drugChanges_Check();
		addEditPage = passport.new For_Other_Tests().new DrugChanges_Test().drugChange_Edit();
		addEditPage.deletedManufacturer_check();
		addEditPage.mainPart_Fields_Check();
		changes = addEditPage.change_Save();
		changes.editedChange_Check();
		
		// Удаление изменения + выход из системы
		changes.change_Delete();
		changes.deletedChange_Check();
		
		changes.userOut();
	}
	
	@AfterMethod(alwaysRun = true, groups = {"DrugChanges_Test"})
	public void DeletionViaDatabase() throws Exception
	{
	    // Удаление изменения
	    String DrugErrorMessage = DbQueries.DrugRegistryTests.DrugQueries.DeletionQueries.ChangeDeletion.ErrorMessage;	  
	    String DrugDeletionStatement = DbQueries.DrugRegistryTests.DrugQueries.DeletionQueries.ChangeDeletion.ChangeDeletionStatement;    
	    new DbStatements().SimpleStatement(sqlConnection, DrugDeletionStatement, DrugErrorMessage);
	}
}
