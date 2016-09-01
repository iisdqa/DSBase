package com.dsbase.other.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.dsbase.core.BaseTest;
import com.dsbase.core.web.CommonActions;
import com.dsbase.core.web.WebPage;
import com.dsbase.core.web.elements.Button;
import com.dsbase.core.web.pages.other.LogInPage;
import com.dsbase.core.web.pages.other.MainPage;
import com.dsbase.core.web.pages.other.TestPage;


public class TestMainPage extends BaseTest {

	@Test(groups = { "TestMainPage" })
	public void TestMainPage_TestMethod() 
	{
		/* логин */
		LogInPage authorizationPage = new MainPage(driver).redirectToLogInPage();
		MainPage mainPage = authorizationPage.logInAs("ulloph_rus", "ullophrus");
		mainPage.waitForPageReady();	
		//нажимаем на "Реестр ЛС"
		mainPage.new goTo().administrationPage();
	}
}


