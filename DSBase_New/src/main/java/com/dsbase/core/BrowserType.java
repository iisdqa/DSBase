package com.dsbase.core;

import java.util.HashMap;
import java.util.Map;

public enum BrowserType 
{
	FIREFOX("firefox"),
	CHROME("chrome"),
	OPERA("opera"),
	IE("ie"),
	REMOTE_IE("remote_ie");
	
	private String browserKey;
	
	private static Map<String, BrowserType> browserMap = new HashMap<String, BrowserType>();

	static
	{
		for(BrowserType bt: BrowserType.values())
		{
			browserMap.put(bt.key(), bt);
		}
	}
	
	private BrowserType(String key)
	{
		browserKey = key;
	}
	
	private String key()
	{
		return this.browserKey;
	}
	
	public static BrowserType get(String key)
	{
		if(browserMap.containsKey(key))
		{
			return browserMap.get(key);
		}
		else
		{
			ErrorMessage(key);
		}
		return null;
	}
	
	private static void ErrorMessage(String browserKey)
	{
		if (browserKey.equals(""))
		{
			System.err.println("Ошибка при попытке установки драйвера браузера!\r\n" +
					   "Установленный браузер отутствует в настройках.");
		}
		else
		{
			System.err.println("Ошибка при попытке установки драйвера браузера!\r\n" +
					   "Установленный браузер не поддерживается.\r\n" + 
					   "Установленный браузер - '" + browserKey + "'.");
		}
	}
}

