package com.dsbase.core.web.pages.drug;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dsbase.core.web.CommonActions;
import com.dsbase.core.web.CustomMethods;
import com.dsbase.core.web.WebPage;
import com.dsbase.core.web.elements.Button;
import com.dsbase.core.web.elements.Custom;
import com.dsbase.core.web.pages.other.LogInPage;


public class DrugChangesPage extends WebPage<DrugChangesPage> 
{
private static final String PAGE_URL = BASE_URL + "/ChangesDrug/List/";
	
	public DrugChangesPage(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public DrugChangesPage load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return getAdd_Button().isAvailable();
	}
	
	// Открытие странички добавления изменения
	public DrugChangeAddEditPage changeAdd_Open()
	{		
		getAdd_Button().click();
		return new DrugChangeAddEditPage(driver).waitUntilAvailable();
	}
	
	public void addedChange_Check()
	{
		// Ожидание прогрузки грида
		simpleWait(1);
		waitForBlockStatus(getGridDownload_Div(), false);
		
		// Определение ожидаемых значений
		String orderNumber = new Values().orderNumber;									// № приказа
		String orderDate = new Values().orderDate;										// Дата приказа
		String changeType = new Values().changeType;									// Тип изменения
		
		// Запись значений в массив
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  orderNumber,
										  orderDate,
										  changeType,
										  ""};
		
		// Вытянуть последнее значения из грида
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(getGridBody());
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	// Открытие странички добавления изменения
	public DrugChangeAddEditPage changeEdit_Open()
	{		
		getEdit_Button().click();
		return new DrugChangeAddEditPage(driver).waitUntilAvailable();
	}
	
	public void editedChange_Check()
	{
		// Ожидание прогрузки грида
		simpleWait(1);
		waitForBlockStatus(getGridDownload_Div(), false);
		
		// Определение ожидаемых значений
		String orderNumber = new Values().orderNumber;									// № приказа
		String orderDate = new Values().editedOrderDate;								// Дата приказа
		String changeType = new Values().changeType;									// Тип изменения
		
		// Запись значений в массив
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  orderNumber,
										  orderDate,
										  changeType,
										  ""};
		
		// Вытянуть последнее значения из грида
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(getGridBody());
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	public DrugPassportPage back_ToPassport_Page()
	{
		get_GoToPassport_Button().click();
		simpleWait(1);
		return new DrugPassportPage(driver).waitUntilAvailable();
	}
	
	public void change_Delete()
	{
	    getDelete_Button().click();
		simpleWait(1);
		waitUntilUnblocked(new Deletion_PopUp().getDeletionPopUp());
		new Deletion_PopUp().getDeletionAcceptButton().click();
		simpleWait(2);
		waitForBlockStatus(getGridDownload_Div(), false);
	}
	
	public void deletedChange_Check()
	{
		// Проверка отсутствия значений в гриде 'МНН'
		new CustomMethods().elementExistenceCheck(getGridBody() , false);
	}
	//________________________________________________________________________________________________________________________________//
	
	public LogInPage userOut()
	{
		// Выход из системы
		return new CommonActions().userOut(driver);
	}
	
	// Кнопка добавления
	private Button getAdd_Button()
	{
		return new Button(driver, By.xpath("//input[contains(@onclick, 'AddRecord')]"));
	}
	
	// Тело таблицы
	private WebElement getGridBody()
	{
		return driver.findElement(By.xpath("//*[@id='list_drug_changes']/tbody"));
	}
	
	// "Завантаження"
	private Custom getGridDownload_Div()
	{
		return new Custom(driver, By.id("load_list_drug_changes"));
	}
	
	// Кнопка редактирования
	private Button getEdit_Button()
	{
		return new Button(driver, By.xpath("//td[@aria-describedby='list_drug_changes_edit']/input"));
	}
	
	// Кнопка удаления
	private Button get_GoToPassport_Button()
	{
		return new Button(driver, By.id("PasportPreparata"));
	}
	
	// Значения для заполнения и проверок
	private class Values
	{		
		private String orderNumber = "111222";	  						  		// № приказа
		private String orderDate = "01.01.2012";								// Дата приказа
		private String editedOrderDate = "02.01.2012";							// Дата приказа после редактирования
		private String changeType = "Административные изменения";				// Тип изменения
	}
	
	// Кнопка удаления
	private Button getDelete_Button()
	{
		return new Button(driver, By.xpath("//td[@aria-describedby='list_drug_changes_del']/input"));
	}
	
	// Поп-ап удаления 
	private class Deletion_PopUp
	{
		private Custom getDeletionPopUp()
		{
			return new Custom(driver, By.id("attention_delete"));
		}
		
		private Custom getDeletionAcceptButton()
		{
			return new Custom(driver, By.xpath("//span[text() = 'Да']"));
		}
	}
}
