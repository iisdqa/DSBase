package com.dsbase.core.web.pages.other;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.dsbase.core.web.WebPage;
import com.dsbase.core.web.elements.Button;
import com.dsbase.core.web.elements.TextInput;

public class LogInPage extends WebPage<LogInPage> 
{
	private static final String PAGE_URL = BASE_URL + "/login";
	
	public LogInPage(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public LogInPage load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return getUserNameInput().isAvailable() && 
			   getPasswordInput().isAvailable() && 
			   getSubmitButton().isAvailable();
	}

	public MainPage logInAs(String UserName, String Password)
	{
		getUserNameInput().inputText(UserName);
		getPasswordInput().inputText(Password);
		getSubmitButton().click();
		return new MainPage(driver).waitUntilAvailable();
	}
	
	public DrugRegistryPage logInToDrugsPage(String UserName, String Password)
	{
		getUserNameInput().inputText(UserName);
		getPasswordInput().inputText(Password);
		getSubmitButton().click();
		return new DrugRegistryPage(driver).waitUntilAvailable();
	}
	
	private TextInput getUserNameInput()
	{
		return new TextInput(driver, By.id("UserName"));
	}
		
	private TextInput getPasswordInput()
	{
		return new TextInput(driver, By.id("Password"));
	}
	
	private Button getSubmitButton()
	{
		return new Button(driver, By.xpath("//input[@type='submit' and @value='Войти']"));
	}
}

