package com.dsbase.core.web;

import org.openqa.selenium.WebDriver;

import com.dsbase.core.EnvironmentConfiguration;
import com.dsbase.core.YamlEnvironment;
import com.dsbase.core.web.pages.other.MainPage;

public abstract class WebPage<T extends WebPage<T>> extends Component<T>
{
	private static final EnvironmentConfiguration CONFIG = EnvironmentConfiguration.getConfig();
	private static final YamlEnvironment ENVIRONMENT = CONFIG.getEnvironmentSettings();
	protected static final String BASE_URL = ENVIRONMENT.scheme + "://" + 
			       						     ENVIRONMENT.host + ":" + 
			       						     ENVIRONMENT.port;
	
	public WebPage(WebDriver driver)
	{
		super(driver);
	}
	
	public abstract T load();
	
	public T loadAndWaitUntilAvailable()
	{
		load();
		return waitUntilAvailable();
	}
	
	// Возврат на главную страничку
	public MainPage backToMainPage()
	{
		return new CommonActions().backToMainPage(driver);
	}
}
