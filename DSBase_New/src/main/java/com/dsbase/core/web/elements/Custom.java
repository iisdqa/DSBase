package com.dsbase.core.web.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.dsbase.core.web.WebComponent;

public class Custom extends WebComponent<Custom>
{
	public Custom(WebDriver driver, By findByMethod) 
	{
		super(driver, findByMethod);
	}
}
