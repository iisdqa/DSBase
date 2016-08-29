package com.dsbase.drug_registry.tests;

import org.testng.annotations.Test;

import com.dsbase.core.BaseTest;
import com.dsbase.core.web.CommonActions;
import com.dsbase.core.web.pages.drug.DrugEditPage;
import com.dsbase.core.web.pages.drug.DrugPassportPage;
import com.dsbase.core.web.pages.other.DrugRegistryPage;
import com.dsbase.core.web.pages.other.LogInPage;
import com.dsbase.core.web.pages.other.MainPage;

public class DrugDeletion_Test extends BaseTest
{
	
	@Test(groups = { "DrugDeletion_Test" })
	public void DrugDeletion_TestMethod()
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

		// Переход к редактированию препарата
		
		DrugPassportPage passport = drugRegistryPage.passport_Open();
		DrugEditPage editPage = passport.drug_Edit();
		editPage.waitForPageReady();
		
		// Удаление МНН и производителя
		
		editPage.editedMnn_check();
		editPage.mnn_Delete();
		editPage.deletedMnn_check();
		editPage.addedManufacturer_check();
		editPage.manufacturer_Delete();
		editPage.deletedManufacturer_check();
		
		// Удаление действующего вещества, документа и сотрудника
		// Сохранение изменений + проверка изменений
		
		editPage.editedSubstance_check();
		editPage.substance_Delete();
		editPage.deletedSubstance_check();
		editPage.editedDoc_check();
		editPage.doc_Delete();
		editPage.deletedDoc_check();
		editPage.editedEmployee_check();
		editPage.employee_Delete();
		editPage.deletedEmployee_check();
		passport = editPage.drug_Save();
		passport.deletedParts_Check();
		
		
		// Удаление ЛС
		mainPage = new CommonActions().backToMainPage(driver);
		mainPage.waitForPageReady();
		drugRegistryPage = mainPage.new goTo().drugRegistryPage();
		drugRegistryPage.WaitForPageReady();
		drugRegistryPage.drug_Search();
		drugRegistryPage.foundBeforeDeletionDrug_Check();
		drugRegistryPage.drug_Delete();
		drugRegistryPage.deletedDrug_check();
		
		drugRegistryPage.userOut();
	}
}
