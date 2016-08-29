package com.dsbase.core;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.opera.core.systems.OperaDriver;

public class DriverMaster 
{
	private DriverMaster(){};
	
	private static WebDriver driver;
		
	public static WebDriver getDriver(String driverKey)
	{
		BrowserType browser = BrowserType.get(driverKey);

		switch (browser)
		{
			case FIREFOX:
				FirefoxProfile profile = new FirefoxProfile();
				profile.setPreference("network.proxy.type", 0);
				
				// Для загрузки файлов
			    profile.setPreference("browser.download.folderList", 2);
				profile.setPreference("browser.download.manager.showWhenStarting", false);
				profile.setPreference("browser.download.dir", "C:\\Selenium_TestData\\Other\\TempFiles\\DownloadedFiles");
				profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/plain, application/octet-stream," + //applications/octet-stram сработало для *.txt
									  "application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel"); //для Excel файлов
				profile.setPreference("browser.helperApps.alwaysAsk.force", false);
				profile.setPreference("browser.download.manager.showAlertOnComplete", false);
				
				DesiredCapabilities FirefoxCaps = DesiredCapabilities.firefox();
				FirefoxCaps.setJavascriptEnabled(true);
				FirefoxCaps.setCapability(FirefoxDriver.PROFILE, profile);
				
				driver = new FirefoxDriver(FirefoxCaps);
				driver.manage().window().maximize();
				break;
				
			case CHROME:
				DesiredCapabilities ChromeCaps = DesiredCapabilities.chrome();
				ChromeOptions options = new ChromeOptions();
				
				// Сохранение файлов в определенную дерикторию
			    Map<String, Object> prefs = new HashMap<String, Object>();
			    prefs.put("download.default_directory", "C:\\Selenium_TestData\\Other\\TempFiles\\DownloadedFiles");
			    options.setExperimentalOption("prefs", prefs);
			    
			    // Разворачивать окно браузера до макс.
				options.addArguments("test-type", "start-maximized", "no-default-browser-check");
				
				// Запись опций + инициализация самого драйвера
				ChromeCaps.setCapability(ChromeOptions.CAPABILITY, options); 
				System.setProperty("webdriver.chrome.driver", "C:\\Selenium_TestData\\WebDrivers\\chromedriver.exe");
				driver = new ChromeDriver(ChromeCaps);
				break;
				
			case OPERA:
				driver = new OperaDriver();
				driver.manage().window().maximize();
				break;
				
			case IE:
				DesiredCapabilities IeCaps = DesiredCapabilities.internetExplorer();
				IeCaps.setCapability("ignoreZoomSetting", true);
				IeCaps.setCapability("version", "9");
				System.setProperty("webdriver.ie.driver", "C:\\Selenium_TestData\\WebDrivers\\IEDriverServer.exe");
				driver = new InternetExplorerDriver(IeCaps);
				driver.manage().window().maximize();
				break;
				
			case REMOTE_IE:
				DesiredCapabilities Remote_IeCaps = DesiredCapabilities.internetExplorer();
				Remote_IeCaps.setCapability("ignoreZoomSetting", true);
				Remote_IeCaps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				Remote_IeCaps.setCapability("platform", "WINDOWS");
				Remote_IeCaps.setCapability("browserName", "internet explorer");
				Remote_IeCaps.setCapability("version", "9");
				try {driver = new RemoteWebDriver(new URL("http://192.168.128.47:4444/wd/hub"), Remote_IeCaps);} 
				catch (MalformedURLException e) {e.printStackTrace();}
				driver.manage().window().maximize();
				break;
		}
		return driver;
	}
}

