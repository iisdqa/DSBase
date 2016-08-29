package com.dsbase.core.web.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.dsbase.core.web.WebComponent;

public class Wait extends WebComponent<Wait>
{
	public Wait(WebDriver driver, By findByMethod) 
	{
		super(driver, findByMethod);
	}
}
