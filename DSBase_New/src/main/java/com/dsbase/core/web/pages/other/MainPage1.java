package com.dsbase.core.web.pages.other;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.dsbase.core.web.CommonActions;
import com.dsbase.core.web.WebPage;
import com.dsbase.core.web.elements.Button;
import com.dsbase.core.web.elements.Custom;
import com.dsbase.core.web.pages.administration.AdministrationPage;
import com.dsbase.core.web.pages.dictionary.DeclarersPage;
import com.dsbase.core.web.pages.dictionary.DrugReferentsRegistryPage;
import com.dsbase.core.web.pages.dictionary.ManufacterersPage;
import com.dsbase.core.web.pages.drug.DrugPassportPage;
import com.dsbase.core.web.pages.drug.DrugRegistrationPage;

public class MainPage1 extends WebPage<MainPage1>
{
    private static final String PAGE_URL = BASE_URL;
	
	public MainPage1(WebDriver driver) 
	{
		super(driver);}
		@Override
		public boolean isAvailable()
		{
			return getAdministrationButton().isAvailable();
		}
		
		public LogInPage redirectToLogInPage()
		{
			load();
			return new LogInPage(driver).waitUntilAvailable();
		}
		@Override
		public MainPage1 load() {
			// TODO Auto-generated method stub
			return null;
			}
			private Button getAdministrationButton()
			{
				return new Button(driver, By.xpath("//a[@href='/User']"));
		}
		
		public class elements {
		
		}
	
}
