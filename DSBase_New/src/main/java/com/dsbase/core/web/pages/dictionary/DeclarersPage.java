package com.dsbase.core.web.pages.dictionary;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.dsbase.core.web.CommonActions;
import com.dsbase.core.web.CustomMethods;
import com.dsbase.core.web.WebPage;
import com.dsbase.core.web.elements.Button;
import com.dsbase.core.web.elements.Custom;
import com.dsbase.core.web.elements.TextInput;
import com.dsbase.core.web.pages.other.LogInPage;

public class DeclarersPage extends WebPage<DeclarersPage> 
{
private static final String PAGE_URL = BASE_URL + "/Declarer/List";
	
	public DeclarersPage(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public DeclarersPage load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return getDeclarerAddButton().isAvailable();
	}
	
	public void waitForGridUnblocked()
	{
		// Ждем 1 сек
		simpleWait(1);
		
		// Ожидание прогрузки грида
		waitForBlockStatus(getGridDownload_Div(), false);
		simpleWait(1);
	}
	
	public void declarerAdd()
	{		
		// Открытие поп-апа добавления заявителя
		getDeclarerAddButton().click();
		simpleWait(2);
		waitUntilUnblocked(new AddPopUpElements().getAddPopUp());
		simpleWait(2);
		
		// Заполнение полей
		declarerInfo_FillUp();
		
		// Сохранение заявителя
		new AddPopUpElements().getDeclarerSaveButton().click();
		waitForGridUnblocked();
	}
	
	public void drugDeclarerAdd()
	{		
		// Открытие поп-апа добавления заявителя
		getDeclarerAddButton().click();
		simpleWait(2);
		waitUntilUnblocked(new AddPopUpElements().getAddPopUp());
		simpleWait(2);
		
		// Заполнение полей
		drug_DeclarerInfo_FillUp();
		
		// Сохранение заявителя
		new AddPopUpElements().getDeclarerSaveButton().click();
		waitForGridUnblocked();
	}
	
	private void declarerInfo_FillUp()
	{
		// Определение страны
		String country = "Катар";
		
		// Название производителя
		new AddPopUpElements().getDeclarer().inputText("Тестовый");
		
		// Название производителя на английском
		new AddPopUpElements().getDeclarerEn().inputText("For automation");
		
		// Выбор страны
		getCountryFromDictionary(country);
		
		// Проверка выбора страны и заполнение адреса
		String chosenCountry = new AddPopUpElements().getDeclarerCountry().getAttribute("value");
		assertThat(chosenCountry, is(equalTo(country)));
		
		new AddPopUpElements().getDeclarerAdress().inputText("ул. Тестовая, д. 2");
	}
	
	private void drug_DeclarerInfo_FillUp()
	{
		// Определение страны
		String country = "Катар";
		
		// Название производителя
		new AddPopUpElements().getDeclarer().inputText("Заявитель для препарата");
		
		// Название производителя на английском
		new AddPopUpElements().getDeclarerEn().inputText("For drugAdd");
		
		// Выбор страны
		getCountryFromDictionary(country);
		
		// Проверка выбора страны и заполнение адреса
		String chosenCountry = new AddPopUpElements().getDeclarerCountry().getAttribute("value");
		assertThat(chosenCountry, is(equalTo(country)));
		
		new AddPopUpElements().getDeclarerAdress().inputText("ул. Тестовая, д. 2");
	}
	
	private void getCountryFromDictionary(String country)
	{
		// Открыть словарь стран
		new AddPopUpElements().getCountryDicOpener().click();
		simpleWait(2);
		
		// Ожидание прогрузки грида
		waitForBlockStatus(new AddPopUpElements().new CountriesPopUp().getGridDownload_Div(), false);
		simpleWait(1);
		
		// Поиск страны
		new AddPopUpElements().new CountriesPopUp().getCountryName().inputText(country);
		new AddPopUpElements().new CountriesPopUp().getCountrySearchButton().click();
		simpleWait(1);
		
		// Ожидание прогрузки грида
		waitForBlockStatus(new AddPopUpElements().new CountriesPopUp().getGridDownload_Div(), false);
		simpleWait(1);
		
		// Кликнуть по ячейке 'Катар'(были проблемы с мозилой)
		Actions action = new Actions(driver);
		action.click((new AddPopUpElements().new CountriesPopUp().getQatarCell())).perform();
		simpleWait(2);
		
		// Выбрать
		new AddPopUpElements().new CountriesPopUp().getCountryAcceptButton().click();
		simpleWait(2);
		waitUntilUnblocked(new AddPopUpElements().getAddPopUp());
	}
	
	public void addedDeclarer_Check()
	{
		// Определение ожидаемых значений
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  "Тестовый", 
								  		  "For automation", 
								  		  "Катар", 
								  		  "ул. Тестовая, д. 2",
										  ""};
		
		// Вытянуть последнее значения из грида
		String[][] ActualValues = getLastRowInGrid();
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	public void addedDrugDeclarer_Check()
	{
		// Определение ожидаемых значений
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  "Заявитель для препарата", 
								  		  "For drugAdd", 
								  		  "Катар", 
								  		  "ул. Тестовая, д. 2",
										  ""};
		
		// Вытянуть последнее значения из грида
		String[][] ActualValues = getLastRowInGrid();
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	public void declarerEdit()
	{
		// Открытие поп-апа редактирования заявителя
		getLastEditButton().click();
		simpleWait(2);
		waitUntilUnblocked(new AddPopUpElements().getAddPopUp());
		simpleWait(2);
		
		// Название заявителя
		new AddPopUpElements().getDeclarer().clear();
		simpleWait(1);
		new AddPopUpElements().getDeclarer().inputText("Тестовый заявитель");
		
		// Сохранение заявителя
		new AddPopUpElements().getDeclarerSaveButton().click();
		waitForGridUnblocked();
	}
	
	public void editedDeclarer_Check()
	{
		// Определение ожидаемых значений
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  "Тестовый заявитель", 
								  		  "For automation", 
								  		  "Катар", 
								  		  "ул. Тестовая, д. 2",
										  ""};
		
		// Вытянуть последнее значения из грида
		String[][] ActualValues = getLastRowInGrid();
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	public void declarerDelete()
	{
		getLastDeleteButton().click();
		waitUntilUnblocked(getDeletionPopUp());
		getDeletionAcceptButton().click();
		waitForGridUnblocked();
	}
	
	public void deletedDeclarer_Check()
	{
		// Определение ожидаемых значений
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  "Тестовый производитель", 
								  		  "For automation", 
								  		  "Катар", 
								  		  "ул. Тестовая, д. 1",
										  ""};
		
		// Вытянуть последнее значения из грида
		String[][] ActualValues = getLastRowInGrid();
		
		// Проверка значений грида
		gridValuesUnequalityCheck(ExpectedValues, ActualValues);
	}
	
	public LogInPage userOut()
	{
		// Выход из системы
		return new CommonActions().userOut(driver);
	}
	
	private String[][] getLastRowInGrid()
	{
		// Определить общее количество страниц грида
		int gridPagesCount = Integer.parseInt(getGridPagesCount().getText());
		
		// Если страниц больше, чем одна, то перейти к последней
		if(gridPagesCount > 1)
		{
			getGridLastPage().click();
			waitForGridUnblocked();
		}
		
		// Вытянуть все значения из грида
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(getGridBody());
		List<String[]> valuesList = Arrays.asList(ActualValues);
		int listSize = valuesList.size();
		
		
		// Определение последнего ряда
		String[][] LastRowValues = new String [1][];
		LastRowValues[0] = valuesList.get(listSize - 1);
		return LastRowValues;
	}
	
	private void gridValuesUnequalityCheck(String[][] ActualValues, String[][] ExpectedValues)
	{	
		// Проверка неравенства ожидаемого значения и реального значения
		assertThat(ActualValues[0][1], not(equalTo((ExpectedValues[0][1]))));
	}
	
	// "Завантаження"
	private Custom getGridDownload_Div()
	{
		return new Custom(driver, By.id("load_list_declarer"));
	}
	
	private Button getDeclarerAddButton()
	{
		return new Button(driver, By.xpath("//input[@title='Добавить заявителя препарата']"));
	}
		
	private class AddPopUpElements
	{
		private Custom getAddPopUp()
		{
			return new Custom(driver, By.xpath("//span[text() = 'Добавить заявителя препарата']"));
		}
		
		private TextInput getDeclarer()
		{
			return new TextInput(driver, By.id("declarer"));
		}
		
		private TextInput getDeclarerEn()
		{
			return new TextInput(driver, By.id("declarer_en"));
		}
		
		private Button getCountryDicOpener()
		{
			return new Button(driver, By.id("countries_dict_open"));
		}
		
		private class CountriesPopUp
		{
			
			private TextInput getCountryName()
			{
				return new TextInput(driver, By.id("_ISO_COUNTRIES-dictionary-txttext"));
			}
			
			private Button getCountrySearchButton()
			{
				return new Button(driver, By.id("_ISO_COUNTRIES-dictionary-Search"));
			}
			
			// "Завантаження"
			private Custom getGridDownload_Div()
			{
				return new Custom(driver, By.id("load__ISO_COUNTRIES-dictionary-tree"));
			}
			
			private WebElement getQatarCell()
			{
				return driver.findElement(By.xpath("//td[@aria-describedby='_ISO_COUNTRIES-dictionary-tree_name_ua']"));
			}
			
			private Button getCountryAcceptButton()
			{
				return new Button(driver, By.id("_ISO_COUNTRIES-dictionary-OK"));
			}
		}
		private TextInput getDeclarerCountry()
		{
			return new TextInput(driver, By.id("country"));
		}
		
		private TextInput getDeclarerAdress()
		{
			return new TextInput(driver, By.id("declarer_address"));
		}
		
		private Button getDeclarerSaveButton()
		{
			return new Button(driver, By.xpath("//input[@value='Сохранить']"));
		}
	}
	
	private WebElement getGridBody()
	{
		return driver.findElement(By.xpath("//*[@id='list_declarer']/tbody"));
	}
	
	private Custom getGridPagesCount()
	{
		return new Custom(driver, By.id("sp_1_pager_declarer"));
	}
	
	private Custom getGridLastPage()
	{
		return new Custom(driver, By.id("last_pager_declarer"));
	}
	
	private WebElement getLastEditButton()
	{
		// Определить общее количество страниц грида
		int gridPagesCount = Integer.parseInt(getGridPagesCount().getText());
		
		// Если страниц больше, чем одна, то перейти к последней
		if(gridPagesCount > 1)
		{
			getGridLastPage().click();
			waitForGridUnblocked();
		}
		
		// Определение всех кнопок редактирования на страничке
		List<WebElement> buttons = driver.findElements(By.xpath("//input[@title='Редактирование']"));
		
		// Определение размера списка
		int listSize = buttons.size();
		
		// Взять последний элемент редактирования
		WebElement lastButton = buttons.get(listSize-1);
		return lastButton;
	}
	
	private WebElement getLastDeleteButton()
	{
		// Определить общее количество страниц грида
		int gridPagesCount = Integer.parseInt(getGridPagesCount().getText());
		
		// Если страниц больше, чем одна, то перейти к последней
		if(gridPagesCount > 1)
		{
			getGridLastPage().click();
			waitForGridUnblocked();
		}
		
		// Определение всех кнопок редактирования на страничке
		List<WebElement> buttons = driver.findElements(By.xpath("//input[@title='Удаление']"));
		
		// Определение размера списка
		int listSize = buttons.size();
		
		// Взять последний элемент редактирования
		WebElement lastButton = buttons.get(listSize-1);
		return lastButton;
	}
	
	private Custom getDeletionPopUp()
	{
		return new Custom(driver, By.id("attention_delete"));
	}
	
	private Custom getDeletionAcceptButton()
	{
		return new Custom(driver, By.xpath("//span[text() = 'Да']"));
	}
}
