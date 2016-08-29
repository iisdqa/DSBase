package com.dsbase.drug_registry.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.dsbase.core.BaseTest;
import com.dsbase.core.database.DbQueries;
import com.dsbase.core.database.DbStatements;
import com.dsbase.core.web.pages.dictionary.DrugReferentsRegistryPage;
import com.dsbase.core.web.pages.drug.DrugPassportPage;
import com.dsbase.core.web.pages.drug.DrugReferentsPage;
import com.dsbase.core.web.pages.other.DrugRegistryPage;
import com.dsbase.core.web.pages.other.LogInPage;
import com.dsbase.core.web.pages.other.MainPage;

public class DrugReferents_Test extends BaseTest
{
	@Test(groups = {"DrugReferents_Test"})
	public void DrugReferents_TestMethod()
	{	
		// ������� �� �������

		LogInPage authorizationPage = new MainPage(driver).redirectToLogInPage();
		MainPage mainPage = authorizationPage.logInAs("admin_auto", "123456");
		mainPage.waitForPageReady();

		// ������� �� ��������� ���. ��
		DrugReferentsRegistryPage refDrugRegistryPage = mainPage.new goTo().new DictionaryBlock().refDrugsPage();
		refDrugRegistryPage.waitForGridReady();
		refDrugRegistryPage.refDrugAdd();
		refDrugRegistryPage.refDrug_Search();
		refDrugRegistryPage.addedRefDrug_Check();
		refDrugRegistryPage.refDrug_Edit();
		refDrugRegistryPage.refDrug_Search();
		
		// ����� ���������

		DrugRegistryPage drugRegistryPage = mainPage.new goTo().drugRegistryPage();
		drugRegistryPage.WaitForPageReady();
		drugRegistryPage.drug_Search();
		drugRegistryPage.foundDrug_Check();

		// ������� � ��������� ����������

		DrugPassportPage passport = drugRegistryPage.passport_Open();
		DrugReferentsPage referents = passport.new GoTo().referentsPage();

		// �������� ������� ������������� ������������ ���. ��
		// ��������������(+�������� �����������) � �������� ������������� ������������ ���. ��

		referents.added_AutoReferent_Check();
		referents.autoReferent_Edit();
		referents.edited_AutoReferent_Check();
		referents.autoReferent_View();

		// ������ ��������/�������������� ���. ��
		// �������� ������� � ��������������� ���. ��

		referents.handReferent_Add();
		referents.added_HandReferent_Check();
		referents.handReferent_Edit();
		referents.edited_HandReferent_Check();
		referents.handReferent_Delete();
		referents.edited_AutoReferent_Check();
		referents.autoReferent_Delete();
		referents.added_AutoReferent_Check();

		referents.userOut();
	}

	@AfterMethod(alwaysRun = true, groups = {"DrugReferents_Test"})
	public void DeletionViaDatabase() throws Exception
	{
	    // �������� ���������
	    String DrugErrorMessage = DbQueries.DrugRegistryTests.DrugQueries.DeletionQueries.ReferentDrugsDeletion.ErrorMessage;
	    String DrugDeletionStatement = DbQueries.DrugRegistryTests.DrugQueries.DeletionQueries.ReferentDrugsDeletion.RefDrugsDeletionStatement;
	    new DbStatements().SimpleStatement(sqlConnection, DrugDeletionStatement, DrugErrorMessage);
	}
}
