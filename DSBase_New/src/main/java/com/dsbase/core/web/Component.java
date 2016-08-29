package com.dsbase.core.web;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.dsbase.core.web.elements.Custom;

public abstract class Component <T extends Component<T>>
{
	protected WebDriver driver;
	
	private static final int WAIT_TIMEOUT = 10000;	
	private static final int WAIT_DELAY = 500;
	
	public Component(WebDriver driver)
	{
		this.driver = driver;
	}
	
	public abstract boolean isAvailable();
	
	@SuppressWarnings("unchecked")
	public T waitUntilAvailable()
	{
		return new Wait<T>().forComponent((T) this).toBeAvailable();
	}
	
	// Ожидание исчезновения элемента
	public void waitUntilUnblocked(Custom element)
	{
		// Определение необходимых переменных
		boolean clickResult = false;
		int timePassed = 0;
		
		// Само ожидание 
		while(!clickResult && (timePassed < WAIT_TIMEOUT))
		{
			try
			{
				element.click();
				clickResult = true;
			}
			catch(Exception e)
			{
				clickResult = false;
			}
			timePassed = timePassed + delay();
		}
		
		if(!clickResult)
		{
			System.err.println("Время ожидания разблокировки элемента превысило " + WAIT_TIMEOUT/1000 + "сек." +
							   "\r\n Элемент: " + element.getWebElement().toString());
			Assert.fail();
		}
	}
	
	// Ожидание определенного статуса блокировки элемента
	public void waitForBlockStatus(Custom element, boolean ExpectedBlockStatus)
	{
		// Объявление переменной для получения реального статуса блокировки 
		boolean realBlockStatus = !ExpectedBlockStatus;
		
		// Определение необходимых переменных
		int timePassed = 0;
		
		// Само ожидание 
		while((ExpectedBlockStatus != realBlockStatus) && (timePassed < WAIT_TIMEOUT))
		{
			// Временная переменная
			boolean tempBlockStatus = false;
			
			// Если заблокированно, то присвоить true временной переменной
			if(element.getCssValue("display").equals("block"))
			{
				tempBlockStatus = true;
			}
			timePassed = timePassed + delay();
			
			// Присвоить значение временной переменной для сравняемой переменной
			realBlockStatus = tempBlockStatus;
		}
		
		// Если все же ожидаемый статус != реальному, то выводить ошибку
		if(ExpectedBlockStatus != realBlockStatus)
		{
			System.err.println("Время ожидания определенного состояния блокировки элемента превысило " + WAIT_TIMEOUT/1000 + "сек." +
							   "\r\n Элемент: " + element.getWebElement().toString() + 
							   "\r\n Ожидаемое состояние блокировки: " + ExpectedBlockStatus + 
							   "\r\n Реальное состояние блокировки: " + realBlockStatus);
			Assert.fail();
		}
	}
	
	public void simpleWait(int Seconds)
	{
		try
		{
			Thread.sleep(Seconds * 1000);
		}
		catch(InterruptedException e){throw new RuntimeException(e);}
	}
	
	private int delay()
	{
		try
		{
			Thread.sleep(WAIT_DELAY); 
			return WAIT_DELAY;
		}
		catch(InterruptedException e){throw new RuntimeException(e);}
	}
}
