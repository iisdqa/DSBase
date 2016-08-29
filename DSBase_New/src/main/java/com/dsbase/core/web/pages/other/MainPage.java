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

public class MainPage extends WebPage<MainPage> 
{
	private static final String PAGE_URL = BASE_URL;
	
	public MainPage(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public MainPage load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return getAdministrationButton().isAvailable()
			&& getDrugRegistryButton().isAvailable();
	}
	
	public LogInPage redirectToLogInPage()
	{
		load();
		return new LogInPage(driver).waitUntilAvailable();
	}
	
	public void waitForPageReady()
	{
		// Ожидание прогрузки грида
		waitForBlockStatus(getGridDownload_Div(), false);
		simpleWait(1);
	}
	
	public class goTo
	{
		public DrugRegistryPage drugRegistryPage()
		{
			getDrugRegistryButton().click();
			return new DrugRegistryPage(driver).waitUntilAvailable();
		}
		
		public DrugRegistrationPage drugRegistrationPage()
		{
			getDrugRegistrationButton().click();
			return new DrugRegistrationPage(driver).waitUntilAvailable();
		}
		
		public class DictionaryBlock
		{
			public ManufacterersPage manufacterersPage()
			{            
			    Actions builder = new Actions(driver); 
		        builder.moveToElement(new DictionaryBlock_Elements().getDictionaryButton()).build().perform();
		        driver.get(BASE_URL + "/Manufacturer/List");
		        
			    return new ManufacterersPage(driver).waitUntilAvailable();
			}
			
			public DeclarersPage declarersPage()
			{            
			    Actions builder = new Actions(driver); 
		        builder.moveToElement(new DictionaryBlock_Elements().getDictionaryButton()).build().perform();
		        driver.get(BASE_URL + "/Declarer/List");
		        
			    return new DeclarersPage(driver).waitUntilAvailable();
			}
			
			public DrugReferentsRegistryPage refDrugsPage()
			{            
			    Actions builder = new Actions(driver); 
		        builder.moveToElement(new DictionaryBlock_Elements().getDictionaryButton()).build().perform();
		        driver.get(BASE_URL + "/ReferenceDrug/Search");
		        
			    return new DrugReferentsRegistryPage(driver).waitUntilAvailable();
			}
		}
		
		public AdministrationPage administrationPage()
		{
			getAdministrationButton().click();
			return new AdministrationPage(driver).waitUntilAvailable();
		}
		
		public DrugPassportPage customPage()
		{
			driver.get("http://pais:8500/Drugs/View/10961/180");
			simpleWait(2);
			return new DrugPassportPage(driver).waitUntilAvailable();
		}
	}
		
	public LogInPage userOut()
	{
		// Выход из системы
		return new CommonActions().userOut(driver);
	}
	
	private Button getDrugRegistryButton()
	{
		return new Button(driver, By.xpath("//a[@href='/Drugs/List']"));
	}
	
	private Button getDrugRegistrationButton()
	{
		return new Button(driver, By.xpath("//a[@href='/Drugs/Registration']"));
	}
	
	private class DictionaryBlock_Elements
	{
		private WebElement getDictionaryButton()
		{
			WebElement element = driver.findElement(By.xpath("//span[text() = 'НСИ']"));
			return element;
		}
	}
	
	private Button getAdministrationButton()
	{
		return new Button(driver, By.xpath("//a[@href='/User']"));
	}
	
	// "Завантаження"
	private Custom getGridDownload_Div()
	{
		return new Custom(driver, By.id("load_list_search"));
	}
}

