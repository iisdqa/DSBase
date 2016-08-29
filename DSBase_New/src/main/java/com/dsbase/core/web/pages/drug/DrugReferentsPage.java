package com.dsbase.core.web.pages.drug;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dsbase.core.web.CommonActions;
import com.dsbase.core.web.CustomMethods;
import com.dsbase.core.web.WebPage;
import com.dsbase.core.web.elements.Button;
import com.dsbase.core.web.elements.CheckBox;
import com.dsbase.core.web.elements.Custom;
import com.dsbase.core.web.elements.Link;
import com.dsbase.core.web.elements.TextInput;
import com.dsbase.core.web.pages.other.LogInPage;


public class DrugReferentsPage extends WebPage<DrugReferentsPage> 
{
private static final String PAGE_URL = BASE_URL + "/ReferenceDrug/List/";
	
	public DrugReferentsPage(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public DrugReferentsPage load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return getAdd_Button().isAvailable();
	}
	
	public void added_AutoReferent_Check()
	{
		// Ожидание прогрузки грида
		simpleWait(1);
		waitForBlockStatus(getGridDownload_Div(), false);
		
		// Определение ожидаемых значений
		String activeSubstance = new Values().activeSubstance;								// Действующее вещество
		
		// Запись значений в массив
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  "",
										  activeSubstance,
										  " ",
										  " ",
										  " ",
										  "",
										  ""};
		
		// Вытянуть последнее значения из грида
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(getGridBody());
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
		
		// Проверка отсутствия кнопок просмотра и удаления
		new CustomMethods().elementExistenceCheck(getView_Button(), false);
		new CustomMethods().elementExistenceCheck(getDelete_Button(1), false);
	}
	
	public void autoReferent_Edit()
	{
		// Открытие редактирования
		getEdit_Button(1).click();
		waitUntilUnblocked(new AddEditView_PopUp().getAddEdit_PopUp(new Values().editPopUp_Name));
	
		// Нажать кнопку сохранить(для проверки всех валидаторов)
		new AddEditView_PopUp().getSave_Button().click();
		simpleWait(1);
		
		// Проверка грида дейсвтующих веществ + Проверка наличия чек-бокса
		activeSubstance_Grid_Check();
		new CustomMethods().new Attribute_Checkers().checkBoxAttribute_Check(new AddEditView_PopUp().getSelect_CheckBox(), "checked", true);
		
		// Внести неправильную ссылку + отжать чек-бокс и нажать кнопку сохранить(для проверка валидаторов)
		new AddEditView_PopUp().getSelect_CheckBox().click();
		new AddEditView_PopUp().getLink_Field().inputText("s");
		new AddEditView_PopUp().getSave_Button().click();
		simpleWait(1);
		new AddEditView_PopUp().getSave_Button().click();
		simpleWait(1);
		
		// Проверка на наличие валидаторов
		new CustomMethods().elementExistenceCheck(new AddEditView_PopUp().getTradeName_Validator(), true);
		new CustomMethods().elementExistenceCheck(new AddEditView_PopUp().getOutForm_Validator(), true);
		new CustomMethods().elementExistenceCheck(new AddEditView_PopUp().getLink_Validator(), true);
		new CustomMethods().elementExistenceCheck(new AddEditView_PopUp().getSubstance_Validator(), true);
		
		// Торговое название
		new AddEditView_PopUp().getTradeName_Field().inputText(new Values().firstTradeName);
		
		// Форма выпуска
		new AddEditView_PopUp().getOutForm_Field().inputText(new Values().outForm);
		
		// Производитель
		new AddEditView_PopUp().getManufacturer_Field().inputText(new Values().manufacturer);
		
		// Ссылка на сайт реф. ЛС
		new AddEditView_PopUp().getLink_Field().clear();
		new AddEditView_PopUp().getLink_Field().inputText(new Values().link);
		
		// Установить чек-бокс для действующего вещества
		new AddEditView_PopUp().getSelect_CheckBox().click();
		simpleWait(1);
		
		// Сохранить
		new AddEditView_PopUp().getSave_Button().click();
		simpleWait(2);
		waitForBlockStatus(getGridDownload_Div(), false);
	}
	
	public void edited_AutoReferent_Check()
	{
		// Ожидание прогрузки грида
		simpleWait(1);
		waitForBlockStatus(getGridDownload_Div(), false);
		
		// Определение ожидаемых значений
		String activeSubstance = new Values().activeSubstance;								// Действующее веществово
		String firstTradeName = new Values().firstTradeName;								// Действующее вещество
		String outForm = new Values().outForm;												// Форма выпуска
		String manufacturer = new Values().manufacturer;									// Производитель
		String link = new Values().link;													// Ссылка на сайт реф. ЛС
		
		// Запись значений в массив
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  "",
										  activeSubstance,
										  firstTradeName,
										  outForm,
										  manufacturer,
										  link,
										  ""};
		
		// Вытянуть последнее значения из грида
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(getGridBody());
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
		
		// Проверка наличия ссылки
		new CustomMethods().elementExistenceCheck(getLink_Link(), true);
	}
	
	public void autoReferent_View()
	{
		// Открытие просмотр
		getView_Button().click();
		waitUntilUnblocked(new AddEditView_PopUp().getAddEdit_PopUp(new Values().viewPopUp_Name));
		simpleWait(1);
		
		// Торговое название
		assertThat(new AddEditView_PopUp().getTradeName_Field().getAttribute("value"), is(equalTo(new Values().firstTradeName)));
		new CustomMethods().new Attribute_Checkers().textInput_Attribute_Check(new AddEditView_PopUp().getTradeName_Field(), "readonly", true);
		
		// Форма выпуска
		assertThat(new AddEditView_PopUp().getOutForm_Field().getAttribute("value"), is(equalTo(new Values().outForm)));
		new CustomMethods().new Attribute_Checkers().textInput_Attribute_Check(new AddEditView_PopUp().getOutForm_Field(), "readonly", true);
		
		// Производитель
		assertThat(new AddEditView_PopUp().getManufacturer_Field().getAttribute("value"), is(equalTo(new Values().manufacturer)));
		new CustomMethods().new Attribute_Checkers().textInput_Attribute_Check(new AddEditView_PopUp().getManufacturer_Field_by_xpath(), "readonly", true);
		
		// Ссылка на сайт реф. ЛС
		assertThat(new AddEditView_PopUp().getLink_Link().getText(), is(equalTo(new Values().link)));
		new CustomMethods().elementExistenceCheck(new AddEditView_PopUp().getLink_Field_by_xpath(), false);
		
		// Проверка грида дейсвтующих веществ + Проверка наличия и недоступности чек-бокса
		activeSubstance_Grid_CheckAtView();
		new CustomMethods().new Attribute_Checkers().checkBoxAttribute_Check(new AddEditView_PopUp().getSelect_CheckBox_forView(), "checked", true);
		new CustomMethods().new Attribute_Checkers().checkBoxAttribute_Check(new AddEditView_PopUp().getSelect_CheckBox_forView(), "disabled", true);
		
		// Закрыть просмотр
		new AddEditView_PopUp().getCancel_Button().click();
		simpleWait(2);
	}
	
	public void handReferent_Add()
	{
		// Открытие редактирования
		getAdd_Button().click();
		waitUntilUnblocked(new AddEditView_PopUp().getAddEdit_PopUp(new Values().addPopUp_Name));
		
		// Проверка грида дейсвтующих веществ
		activeSubstance_Grid_Check();
			
		// Торговое название
		new AddEditView_PopUp().getTradeName_Field().inputText(new Values().secondTradeName);
		
		// Форма выпуска
		new AddEditView_PopUp().getOutForm_Field().inputText(new Values().outForm);
		
		// Производитель
		new AddEditView_PopUp().getManufacturer_Field().inputText(new Values().manufacturer);
		
		// Ссылка на сайт реф. ЛС
		new AddEditView_PopUp().getLink_Field().inputText(new Values().link);
		
		// Установить чек-бокс для действующего вещества
		new AddEditView_PopUp().getSelect_CheckBox().click();
		simpleWait(1);
		
		// Сохранить
		new AddEditView_PopUp().getSave_Button().click();
		simpleWait(2);
		waitForBlockStatus(getGridDownload_Div(), false);
	}
	
	public void added_HandReferent_Check()
	{
		// Ожидание прогрузки грида
		simpleWait(1);
		waitForBlockStatus(getGridDownload_Div(), false);
		
		// Определение ожидаемых значений
		String activeSubstance = new Values().activeSubstance;								// Действующее веществово
		String firstTradeName = new Values().firstTradeName;								// Действующее вещество
		String secondTradeName = new Values().secondTradeName;								// Действующее вещество(для второго ряда
		String outForm = new Values().outForm;												// Форма выпуска
		String manufacturer = new Values().manufacturer;									// Производитель
		String link = new Values().link;													// Ссылка на сайт реф. ЛС
		
		// Запись значений в массив
		String[][] ExpectedValues = new String [2][];
		ExpectedValues[0] = new String[] {"",
										  "",
										  activeSubstance,
										  firstTradeName,
										  outForm,
										  manufacturer,
										  link,
										  ""};
		ExpectedValues[1] = new String[] {"",
				  						  "",
				  						  activeSubstance,
				  						  secondTradeName,
				  						  outForm,
				  						  manufacturer,
				  						  link,
				  						  ""};
		
		// Вытянуть последнее значения из грида
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(getGridBody());
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	public void handReferent_Edit()
	{
		// Открытие редактирования
		getEdit_Button(2).click();
		waitUntilUnblocked(new AddEditView_PopUp().getAddEdit_PopUp(new Values().editPopUp_Name));
				
		// Производитель
		new AddEditView_PopUp().getManufacturer_Field().clear();
		new AddEditView_PopUp().getManufacturer_Field().inputText(new Values().editedManufacturer);
		
		// Сохранить
		new AddEditView_PopUp().getSave_Button().click();
		simpleWait(2);
		waitForBlockStatus(getGridDownload_Div(), false);
	}
	
	public void edited_HandReferent_Check()
	{
		// Ожидание прогрузки грида
		simpleWait(1);
		waitForBlockStatus(getGridDownload_Div(), false);
		
		// Определение ожидаемых значений
		String activeSubstance = new Values().activeSubstance;								// Действующее веществово
		String firstTradeName = new Values().firstTradeName;								// Действующее вещество
		String secondTradeName = new Values().secondTradeName;								// Действующее вещество(для второго ряда)
		String outForm = new Values().outForm;												// Форма выпуска
		String manufacturer = new Values().manufacturer;									// Производитель
		String editedManufacturer = new Values().editedManufacturer;								// Производитель(для второго ряда)
		String link = new Values().link;													// Ссылка на сайт реф. ЛС
		
		// Запись значений в массив
		String[][] ExpectedValues = new String [2][];
		ExpectedValues[0] = new String[] {"",
										  "",
										  activeSubstance,
										  firstTradeName,
										  outForm,
										  manufacturer,
										  link,
										  ""};
		ExpectedValues[1] = new String[] {"",
				  						  "",
				  						  activeSubstance,
				  						  secondTradeName,
				  						  outForm,
				  						  editedManufacturer,
				  						  link,
				  						  ""};
		
		// Вытянуть последнее значения из грида
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(getGridBody());
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	public void handReferent_Delete()
	{
	    getDelete_Button(2).click();
		simpleWait(1);
		waitUntilUnblocked(new Deletion_PopUp().getDeletionPopUp());
		new Deletion_PopUp().getDeletionAcceptButton().click();
		simpleWait(2);
		waitForBlockStatus(getGridDownload_Div(), false);
	}
	
	public void autoReferent_Delete()
	{
	    getDelete_Button(1).click();
		simpleWait(1);
		waitUntilUnblocked(new Deletion_PopUp().getDeletionPopUp());
		new Deletion_PopUp().getDeletionAcceptButton().click();
		simpleWait(2);
		waitForBlockStatus(getGridDownload_Div(), false);
	}
	
	public LogInPage userOut()
	{
		// Выход из системы
		return new CommonActions().userOut(driver);
	}
	//________________________________________________________________________________________________________________________________//
	
	
	private void activeSubstance_Grid_Check()
	{
		// Определение ожидаемых значений
		String activeSubstance = new Values().activeSubstance;								// Действующее веществово
		
		// Запись значений в массив
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  activeSubstance};
		
		// Вытянуть последнее значения из грида
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(new AddEditView_PopUp().getSubstance_GridBody());
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	private void activeSubstance_Grid_CheckAtView()
	{
		// Определение ожидаемых значений
		String activeSubstance = new Values().activeSubstance;								// Действующее веществово
		
		// Запись значений в массив
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  activeSubstance};
		
		// Вытянуть последнее значения из грида
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(new AddEditView_PopUp().getSubstance_GridBody_forView());
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	// Кнопка добавления
	private Button getAdd_Button()
	{
		return new Button(driver, By.xpath("//input[contains(@onclick, 'AddEditReferenceDrug')]"));
	}
	
	// Тело таблицы
	private WebElement getGridBody()
	{
		return driver.findElement(By.xpath("//*[@id='list_referenceDrug']/tbody"));
	}
	
	// "Завантаження"
	private Custom getGridDownload_Div()
	{
		return new Custom(driver, By.id("load_list_referenceDrug"));
	}
	
	// Кнопка просмотра
	private WebElement getView_Button()
	{
		return driver.findElement(By.xpath("//td[@aria-describedby='list_referenceDrug_view']/input"));
	}
	
/*	// Кнопка редактирования
	private Button getEdit_Button()
	{
		return new Button(driver, By.xpath("//td[@aria-describedby='list_referenceDrug_edit']/input"));
	}*/
	
	// Кнопка редактирования
	private Button getEdit_Button(int ButtonNumber)
	{
		return new Button(driver, By.xpath("(//td[@aria-describedby='list_referenceDrug_edit']/input)[" + Integer.toString(ButtonNumber) + "]"));
	}
	
	// Значения для заполнения и проверок
	private class Values
	{
		private String addPopUp_Name = "Добавление референтного ЛС";	  				// Название поп-апа добавления
		private String editPopUp_Name = "Изменения референтного ЛС";	  				// Название поп-апа редактирования
		private String viewPopUp_Name = "Просмотр референтного ЛС";	  					// Название поп-апа просмотра
		
		private String activeSubstance = "Тестинин";	  						  		// Действующее вещество
		private String firstTradeName = "Первый";										// Тороговое название
		private String secondTradeName = "Второй";										// Тороговое название(2 ряд)
		private String outForm = "Тестовая";											// Форма выпуска	
		private String manufacturer = "Тестовый";										// Производитель
		private String editedManufacturer = "Тестовый 2";								// Производитель(2 ряд)
		private String link = "http://first_drug.com";									// Ссылка 
	}
	
	// Ссылка в гриде
	private WebElement getLink_Link()
	{
		return driver.findElement(By.xpath("//td[@aria-describedby='list_referenceDrug_UrlForRefDrug']/a"));
	}
	
	// Кнопка удаления
	private WebElement getDelete_Button(int ButtonNumber)
	{
		return driver.findElement(By.xpath("(//td[@aria-describedby='list_referenceDrug_del']/input)[" + Integer.toString(ButtonNumber) + "]"));
	}
	
	private class AddEditView_PopUp
	{
		// Поп-ап добавления, редактирования и просмотра
		private Custom getAddEdit_PopUp(String popUp_Name)
		{
			return new Custom(driver, By.xpath("//span[text() = '" + popUp_Name + "']"));
		}
		
		// Торговое название
		private TextInput getTradeName_Field()
		{
			return new TextInput(driver, By.id("TradeName"));
		}
		 
		// Торговое название(валидатор)
		private WebElement getTradeName_Validator()
		{
			return driver.findElement(By.id("TradeName-error"));
		}
		
		// Форма выпуска
		private TextInput getOutForm_Field()
		{
			return new TextInput(driver, By.id("ReleaseForm"));
		}
		
		// Форма выпуска(валидатор)
		private WebElement getOutForm_Validator()
		{
			return driver.findElement(By.id("ReleaseForm-error"));
		}
		
		// Производитель
		private TextInput getManufacturer_Field()
		{
			return new TextInput(driver, By.id("Manufacturer"));
		}
		
		// Производитель(для проверки просмотра)
		private TextInput getManufacturer_Field_by_xpath()
		{
			return new TextInput(driver, By.xpath("//input[@id='Manufacturer' and @type='text']"));
		}
		
		// Ссылка на сайт реф. ЛС
		private TextInput getLink_Field()
		{
			return new TextInput(driver, By.id("UrlForRefDrug"));
		}
		
		// Производитель(для проверки просмотра)
		private WebElement getLink_Field_by_xpath()
		{
			return driver.findElement(By.xpath("//input[@id='UrlForRefDrug' and @type='hidden']"));
		}
		
		// Ссылка на сайт реф. ЛС
		private Link getLink_Link()
		{
			return new Link(driver, By.xpath("//a[@title='"+ new Values().link +"']"));
		}
		
		// Ссылка на сайт реф. ЛС(валидатор)
		private WebElement getLink_Validator()
		{
			return driver.findElement(By.id("UrlForRefDrug-error"));
		}
		
		// Тело таблицы
		private WebElement getSubstance_GridBody()
		{
			return driver.findElement(By.xpath("//*[@id='list_activSubs']/tbody"));
		}
		
		// Тело таблицы(для проверки просмотра)
		private WebElement getSubstance_GridBody_forView()
		{
			return driver.findElement(By.xpath("//*[@id='list_activSubs_']/tbody"));
		}
		
		// Чек-бокс выбора действующего вещества
		private CheckBox getSelect_CheckBox()
		{
			return new CheckBox(driver, By.xpath("//td[@aria-describedby='list_activSubs_SelectFlag']/input"));
		}
		
		// Чек-бокс выбора действующего вещества
		private CheckBox getSelect_CheckBox_forView()
		{
			return new CheckBox(driver, By.xpath("//td[@aria-describedby='list_activSubs__SelectFlag']/input"));
		}
		
		// Действующее вещество(валидатор)
		private WebElement getSubstance_Validator()
		{
			return driver.findElement(By.id("ActiveSubsId-error"));
		}
		
		// Кнопка сохранения
		private Button getSave_Button()
		{
			return new Button(driver, By.id("save_btn"));
		}
		
		// Кнопка отмены
		private Button getCancel_Button()
		{
			return new Button(driver, By.xpath("//span[text() = 'close']"));
		}
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
