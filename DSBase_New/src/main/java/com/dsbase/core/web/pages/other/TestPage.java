package com.dsbase.core.web.pages.other;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dsbase.core.web.WebPage;
import com.dsbase.core.web.elements.Button;
import com.dsbase.core.web.elements.Custom;
import com.dsbase.core.web.pages.dictionary.DeclarersPage;

public class TestPage extends WebPage<TestPage> 
{
	private static final String PAGE_URL = BASE_URL + "/Test";
	
	public TestPage(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public TestPage load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return new Elements().add_Button().isAvailable();
	}

	/* Actions */
	public void action1()
	{
		
	}
	
	
	
	/* Element */

	private class Elements
	{
		private Button add_Button()
		{
			return new Button(driver, By.xpath("//input[@type='submit' and @value='Войти']"));
		}
	}
}
