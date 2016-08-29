package com.dsbase.core.web.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.dsbase.core.web.WebComponent;

public class Button extends WebComponent<Button>
{
	public Button(WebDriver driver, By findByMethod) 
	{
		super(driver, findByMethod);
	}
}
