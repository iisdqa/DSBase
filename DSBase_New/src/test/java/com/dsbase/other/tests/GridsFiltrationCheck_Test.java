package com.dsbase.other.tests;

import org.testng.annotations.Test;

import com.dsbase.core.BaseTest;
import com.dsbase.core.web.pages.other.DrugRegistryPage;
import com.dsbase.core.web.pages.other.LogInPage;
import com.dsbase.core.web.pages.other.MainPage;

public class GridsFiltrationCheck_Test extends BaseTest
{	
	@Test(groups = { "GridsFiltrationCheck_Test" })
	public void GridsFiltrationCheck_TestMethod()
	{	
		// Переход на главную	
		LogInPage authorizationPage = new MainPage(driver).redirectToLogInPage();
		MainPage mainPage = authorizationPage.logInAs("admin_auto", "123456");
		mainPage.waitForPageReady();
		
		// Поиск препарата
		DrugRegistryPage drugRegistryPage = mainPage.new goTo().drugRegistryPage();
		drugRegistryPage.WaitForPageReady();
		drugRegistryPage.wholeFiltration_Check();
	}
}
