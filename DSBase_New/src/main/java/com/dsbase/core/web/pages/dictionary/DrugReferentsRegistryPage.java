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
import com.dsbase.core.web.CustomMethods.Grid;
import com.dsbase.core.web.elements.Button;
import com.dsbase.core.web.elements.Custom;
import com.dsbase.core.web.elements.Text;
import com.dsbase.core.web.elements.TextInput;
import com.dsbase.core.web.pages.other.LogInPage;

public class DrugReferentsRegistryPage extends WebPage<DrugReferentsRegistryPage> 
{
private static final String PAGE_URL = BASE_URL + "/ReferenceDrug/Search";
	
	public DrugReferentsRegistryPage(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public DrugReferentsRegistryPage load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return getReferentAdd_Button().isAvailable();
	}
	
	public void waitForGridReady()
	{
		// Ждем 1 сек
		simpleWait(1);
		
		// Ожидание прогрузки грида
		waitForBlockStatus(getGridDownload_Div(), false);
		simpleWait(1);
	}
	
	public void refDrugAdd()
	{		
		// Открытие поп-апа добавления реф. ЛС
		getReferentAdd_Button().click();
		simpleWait(1);
		//waitUntilUnblocked(new AddEditPage_Elements().getDrugAdd_Button());
		new AddEditPage_Elements().getDrugAdd_Button().waitUntilAvailable();
		
		// Проверка валидаторов
		drug_validatorsCheck();
		
		// Выбрать ЛС
		drug_Set();
		
		// Заполнить другие поля
		otherFields_Set();
	
		// Сохранение реф. ЛС
		new AddEditPage_Elements().getSave_Button().click();
		getReferentAdd_Button().waitUntilAvailable();
		waitForGridReady();
	}
	
	private void drug_validatorsCheck()
	{
		// Определение значений валидаторов
		String tradeName = new AddEditPage_Elements().new Validators().new Values().tradeName;
		String releaseForm = new AddEditPage_Elements().new Validators().new Values().releaseForm;
		String link = new AddEditPage_Elements().new Validators().new Values().link;
		String activeSubstance = new AddEditPage_Elements().new Validators().new Values().activeSubstance;	
		
		// Внести не валидную ссылку
		new AddEditPage_Elements().getLink().inputText("123");
		
		// Нажать на кнопку 'Сохранить'
		new AddEditPage_Elements().getSave_Button().click();
		simpleWait(2);
		
		// Проверка валидаторов
		assertThat(new AddEditPage_Elements().new Validators().getTradeName_Validator().getText(), is(equalTo(tradeName)));	
		assertThat(new AddEditPage_Elements().new Validators().getReleaseForm_Validator().getText(), is(equalTo(releaseForm)));
		assertThat(new AddEditPage_Elements().new Validators().getLink_Validator().getText(), is(equalTo(link)));	
		assertThat(new AddEditPage_Elements().new Validators().getSubstance_Validator().getText(), is(equalTo(activeSubstance)));
	}
	
	private void drug_Set()
	{		
		// Открытие поп-апа добавления ЛС
		new AddEditPage_Elements().getDrugAdd_Button().click();
		simpleWait(1);
		waitUntilUnblocked(new AddEditPage_Elements().new DrugAdd_PopUp().getDrugAdd_PopUp());
		simpleWait(2);
		
		// Найти ЛС
		String drugName = new AddEditPage_Elements().new DrugAdd_PopUp().new Values().drugName;
		new AddEditPage_Elements().new DrugAdd_PopUp().getDrugName().inputText(drugName);
		new AddEditPage_Elements().new DrugAdd_PopUp().getDrugSearchButton().click();
		simpleWait(1);
		
		// Ожидание прогрузки грида
		waitForBlockStatus(new AddEditPage_Elements().new DrugAdd_PopUp().getGridDownload_Div(), false);
		simpleWait(1);
		
		// Кликнуть по ячейке 'Страна'(были проблемы с мозилой)
		Actions action = new Actions(driver);
		action.click((new AddEditPage_Elements().new DrugAdd_PopUp().getDrugNameCell())).perform();
		simpleWait(2);
		
		// Сохранение ЛС
		new AddEditPage_Elements().new DrugAdd_PopUp().getDrugAcceptButton().click();
		simpleWait(2);
	}
	
	private void otherFields_Set()
	{
		String tradeName = new AddEditPage_Elements().new Values().tradeName;
		String releaseForm = new AddEditPage_Elements().new Values().releaseForm;
		String manufacturer = new AddEditPage_Elements().new Values().manufacturer;
		String link = new AddEditPage_Elements().new Values().link;
		
		// Торговое название 
		new AddEditPage_Elements().getTradeName().inputText(tradeName);
		
		// Форма выпуска
		new AddEditPage_Elements().getReleaseForm().inputText(releaseForm);
		
		// Производитель
		new AddEditPage_Elements().getManufacturer().inputText(manufacturer);
		
		// Ссылка на сайт Реф. ЛС
		new AddEditPage_Elements().getLink().clear();
		new AddEditPage_Elements().getLink().inputText(link);
	}
	
	public void addedRefDrug_Check()
	{
		// Определение ожидаемых значений
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  "",
										  new AddEditPage_Elements().new Values().tradeName, 
										  new AddEditPage_Elements().new Values().releaseForm, 
										  new AddEditPage_Elements().new Values().manufacturer, 
								  		  "дописать",
								  		  "дописать",
								  		  new AddEditPage_Elements().new Values().link,
										  ""};
		
		// Вытянуть последнее значения из грида
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(getGridBody());
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	public void refDrug_Search()
	{
	
	}
	
	public void refDrug_Edit()
	{
		// Открытие формы редактирования реф. ЛС
		getReferentEdit_Button().click();
		simpleWait(1);
		new AddEditPage_Elements().getDrugAdd_Button().waitUntilAvailable();
		
		// Форма выпуска
		new AddEditPage_Elements().getReleaseForm().clear();
		simpleWait(1);
		new AddEditPage_Elements().getReleaseForm().inputText(new AddEditPage_Elements().new Values().editedReleaseForm);
		
		// Сохранение реф. ЛС
		new AddEditPage_Elements().getSave_Button().click();
		getReferentAdd_Button().waitUntilAvailable();
		waitForGridReady();
	}

	/*
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
	}*/
	
	public LogInPage userOut()
	{
		// Выход из системы
		return new CommonActions().userOut(driver);
	}
	
	// "Завантаження"
	private Custom getGridDownload_Div()
	{
		return new Custom(driver, By.id("load_list_declarer"));
	}
	
	private Button getReferentAdd_Button()
	{
		return new Button(driver, By.xpath("//input[contains(@onclick,'AddEditReferenceDru')]"));
	}
	
	private Button getReferentEdit_Button()
	{
		return new Button(driver, By.xpath("//td[@aria-describedby='list_search_edit']"));
	}	
	
	private class AddEditPage_Elements
	{
		private Custom getDrugAdd_Button()
		{
			return new Custom(driver, By.id("open_trade_names"));
		}
		
		private class DrugAdd_PopUp
		{	
			// Поп-ап добавления
			private Custom getDrugAdd_PopUp()
			{
				return new Custom(driver, By.xpath("//span[text() = '" + new Values().drugAddEditPopUpName + "']"));
			}
			
			private TextInput getDrugName()
			{
				return new TextInput(driver, By.id("TradeNameControl_Name"));
			}
			
			private Button getDrugSearchButton()
			{
				return new Button(driver, By.id("TradeNameControl_Find"));
			}
			
			// "Завантаження"
			private Custom getGridDownload_Div()
			{
				return new Custom(driver, By.id("load_list_tradenames"));
			}
			
			private WebElement getDrugNameCell()
			{
				return driver.findElement(By.xpath("//td[@aria-describedby='list_tradenames_TradeName']"));
			}
			
			private Button getDrugAcceptButton()
			{
				return new Button(driver, By.id("TradeNameControl_Choose"));
			}
			
			private class Values
			{
				private String drugAddEditPopUpName = "Выбор ЛС";   			// Название поп-апа добавления ЛС
				private String drugName = "Тестовый препарат";	  				// Название ЛС
			}
		}
		
		// Валидаторы
		private class Validators
		{	
			private Text getTradeName_Validator()
			{
				return new Text(driver, By.id("TradeName-error-error"));
			}
			
			private Text getReleaseForm_Validator()
			{
				return new Text(driver, By.id("ReleaseForm-error"));
			}
			
			private Text getLink_Validator()
			{
				return new Text(driver, By.id("UrlForRefDrug-error"));
			}
			
			private Text getSubstance_Validator()
			{
				return new Text(driver, By.id("ListFlagsOnString-error"));
			}
			
			private class Values
			{
				private String tradeName = "Должен быть выбран хотя бы один препарат.";	  											// Название Реф. ЛС
				private String releaseForm = "Поле «Торговое название» обязательное для заполнения.";	  							// Форма выпуска
				private String link = "Поле «Форма выпуска» обязательное для заполнения.";	  										// Ссылка на сайт Реф. ЛС
				private String activeSubstance = "Неверный формат ссылки. Ссылка должна начинаться с \"http://\" или \"https://\"";	// Ссылка на сайт Реф. ЛС
			}
		}
		
		private TextInput getTradeName()
		{
			return new TextInput(driver, By.id("TradeName"));
		}
		
		private TextInput getReleaseForm()
		{
			return new TextInput(driver, By.id("ReleaseForm"));
		}
		
		private TextInput getManufacturer()
		{
			return new TextInput(driver, By.id("Manufacturer"));
		}
		
		private TextInput getLink()
		{
			return new TextInput(driver, By.id("UrlForRefDrug"));
		}
		
		// Грид препаратов 
		private Custom getDrugs_Grid()
		{
			return new Custom(driver, By.id("list_activSubs"));
		}
		
		private Button getSave_Button()
		{
			return new Button(driver, By.xpath("//input[contains(@onclick,'SaveRefD')]"));
		}
		
		private class Values
		{
			private String tradeName = "Тестовый реф. ЛС";	  			// Название Реф. ЛС
			private String releaseForm = "Тест";	  					// Форма выпуска
			private String editedReleaseForm = "Тестовая";	  			// Форма выпуска(для редактирования)
			private String manufacturer = "Тестовый";	  				// Производитель
			private String link = "http://google.com";	  				// Ссылка на сайт Реф. ЛС
		}
	}
	
	private WebElement getGridBody()
	{
		return driver.findElement(By.xpath("//*[@id='list_search']/tbody"));
	}
	
	private Custom getGridPagesCount()
	{
		return new Custom(driver, By.id("sp_1_pager_declarer"));
	}
	
	private Custom getGridLastPage()
	{
		return new Custom(driver, By.id("last_pager_declarer"));
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
