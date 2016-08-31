package com.dsbase.administration.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dsbase.core.BaseTest;
import com.dsbase.core.database.DbQueries;
import com.dsbase.core.database.DbStatements;
import com.dsbase.core.web.pages.administration.AdministrationPage;
import com.dsbase.core.web.pages.administration.AuditPage;
import com.dsbase.core.web.pages.administration.UserAddPage;
import com.dsbase.core.web.pages.administration.UserEditPage;
import com.dsbase.core.web.pages.administration.UserViewPage;
import com.dsbase.core.web.pages.administration.UsersPage;
import com.dsbase.core.web.pages.other.DrugRegistryPage;
import com.dsbase.core.web.pages.other.LogInPage;
import com.dsbase.core.web.pages.other.MainPage;

public class UsersAndAudit_Test extends BaseTest 
{
	@BeforeMethod(alwaysRun = true, dependsOnMethods = {"setUp"})
/*	public void DeletionViaDatabase() throws Exception
	{
	    // ����������� ������, ������� ����� ���������� � ������ ������� �������
	    String ErrorMessage = DbQueries.AdministrationTests.UsersAndAuditQueries.DeletionQueries.ErrorMessage;
	    
	    // ����������� ������ �������
	    String DeletionStatement = DbQueries.AdministrationTests.UsersAndAuditQueries.DeletionQueries.UserDeletionStatement;
	    
	    // ���������� �������
	    new DbStatements().SimpleStatement(sqlConnection, DeletionStatement, ErrorMessage);
	}*/
	
	@Test(groups = { "UsersAndAudit_Test" })
	public void UsersAndAudit_TestMethod()
	{	
			// ������� �� �������
			LogInPage authorizationPage = new MainPage(driver).redirectToLogInPage();
			MainPage mainPage = authorizationPage.logInAs("admin_auto", "123456");
			mainPage.waitForPageReady();
			
			// ������� �� ��������� �������������
			AdministrationPage administrationPage = mainPage.new goTo().administrationPage();
			UsersPage usersPage = administrationPage.new goTo().UsersPage();
			usersPage.WaitForGridReady();
			
			// ���������� ������������
			UserAddPage addPage = usersPage.AddButtonClick();
			addPage.OptionalFieldsFill();
			addPage.ValidatorsCheck();
			addPage.RequiredFieldsFill();
			usersPage = addPage.UserSave();
			usersPage.WaitForGridReady();
			
			// �������� ������������ ������������
			// �������������� ������������
			// �������� ������������
		
			usersPage.UserSearch();
			usersPage.AddedUserCheck();
			UserEditPage editPage = usersPage.EdidButtonClick();
			editPage.UserPositionChange();
			usersPage = editPage.UserUpdate();
			usersPage.WaitForGridReady();
			usersPage.UserSearch();
			usersPage.EditedUserCheck();
			UserViewPage viewPage = usersPage.ViewButtonClick();
			viewPage.ViewPageCheck();
			authorizationPage = viewPage.userOut();
			
			// ���� ��� ��������� ������
			
			DrugRegistryPage drugRegistryPage = authorizationPage.logInToDrugsPage("auto_user", "auto_user");
			drugRegistryPage.WaitForPageReady();
			drugRegistryPage.userNameCheck("�������� ������� ��������");
			authorizationPage = drugRegistryPage.userOut();
			
			// �������� ������
			
			drugRegistryPage = authorizationPage.logInToDrugsPage("admin_auto", "123456");
			drugRegistryPage.WaitForPageReady();
			administrationPage = drugRegistryPage.new goTo().administrationPage();
			AuditPage auditPage = administrationPage.new goTo().AuditPage();
			auditPage.operationsSearch();
			auditPage.operationsCheck();
			
			// �������� ������������
			
			administrationPage = auditPage.BackToAdministrationPage();
			administrationPage.new goTo().UsersPage();
			usersPage.WaitForGridReady();
			usersPage.UserSearch();
			usersPage.EditedUserCheck();
			usersPage.UserDelete();
			usersPage.UserDeletionCheck();
			authorizationPage = usersPage.userOut();
	}
}
