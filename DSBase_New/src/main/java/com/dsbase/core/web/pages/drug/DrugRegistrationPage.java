package com.dsbase.core.web.pages.drug;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.dsbase.core.web.CustomMethods;
import com.dsbase.core.web.WebPage;
import com.dsbase.core.web.elements.Button;
import com.dsbase.core.web.elements.Custom;
import com.dsbase.core.web.elements.Text;
import com.dsbase.core.web.elements.TextInput;

public class DrugRegistrationPage extends WebPage<DrugRegistrationPage> 
{
private static final String PAGE_URL = BASE_URL + "/Drugs/Registration";
	
	public DrugRegistrationPage(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public DrugRegistrationPage load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return new Main_Elements().getBirthday().isAvailable();
	}
	
	private void marketingCountry_Set()
	{
		String country = new Main_Elements().new Values().marketingCountry;
		
		// Страна маркетирования
		new Main_Elements().getMarketingCoutry().selectByVisibleText("<Другая страна>");
		
		// Ожидание прогрузки грида
		waitForBlockStatus(new Main_Elements().new CountriesPopUp().getGridDownload_Div(), false);
		simpleWait(1);
		
		// Поиск страны
		new Main_Elements().new CountriesPopUp().getCountryName().inputText(country);
		new Main_Elements().new CountriesPopUp().getCountrySearchButton().click();
		simpleWait(1);
		
		// Ожидание прогрузки грида
		waitForBlockStatus(new Main_Elements().new CountriesPopUp().getGridDownload_Div(), false);
		simpleWait(1);
		
		// Кликнуть по ячейке 'Страна'(были проблемы с мозилой)
		Actions action = new Actions(driver);
		action.click((new Main_Elements().new CountriesPopUp().getQatarCell())).perform();
		simpleWait(2);
		
		// Выбрать
		new Main_Elements().new CountriesPopUp().getCountryAcceptButton().click();
		simpleWait(2);
		
		// Проверка выбора страны и заполнение адреса
		String chosenCountry = new Main_Elements().getMarketingCoutry().getFirstSelectedOption().getText();
		assertThat(chosenCountry, is(equalTo(country)));
	}
	
	public void mainFields_FillUp()
	{
		// Страна маркетирования
		marketingCountry_Set();
		
	    // Международная дата рождения
		new Main_Elements().getBirthday().click();
		new Main_Elements().getBirthday().inputText(new Main_Elements().new Values().Birthday);
		
		// Проверка подтягивания страны и адреса заявителя при выборе заявителя
		new Main_Elements().getDeclarer().inputText(new Main_Elements().new Values().Declarer);
		simpleWait(3);
		new Main_Elements().getAutocompletedDeclarer().click();
		simpleWait(2);
		assertThat(new Main_Elements().getDeclarerCountry().getAttribute("value"), is(equalTo(new Main_Elements().new Values().DeclarerCountry)));
		assertThat(new Main_Elements().getDeclarerAdress().getAttribute("value"), is(equalTo(new Main_Elements().new Values().DeclarerAdress)));

		// Торговое название препарата
		new Main_Elements().getTradeName().inputText(new Main_Elements().new Values().DrugTradeName);
		
		// Форма выпуска препарата
		new Main_Elements().getOutputForm().inputText(new Main_Elements().new Values().DrugOutputForm);
		
		// Фасовка
		new Main_Elements().getPacking().inputText(new Main_Elements().new Values().Packing);
		
		// Условия отпуска
		new Main_Elements().getLeaveCondition().inputText(new Main_Elements().new Values().DrugLeaveCondition);
		
		// Условия отпуска
		new Main_Elements().getInjectionWay().selectByVisibleText(new Main_Elements().new Values().InjectionWay);
		
		// Вспомогательные вещества
		new Main_Elements().getAdditionalSubstance().inputText(new Main_Elements().new Values().AdditionalSubstance);
		
		// АТС код
		new Main_Elements().getAtcCode().inputText(new Main_Elements().new Values().AtcCode);
		
		// АТС группа
		new Main_Elements().getAtcGroup().inputText(new Main_Elements().new Values().AtcGroup);
		
		// Тип заявки при регистрации
		new Main_Elements().getApplicationType().inputText(new Main_Elements().new Values().ApplicationType);
		
		// Дата регистрации
		new Main_Elements().getRegistrationDate().click();
		new Main_Elements().getRegistrationDate().inputText(new Main_Elements().new Values().RegistrationDate);
		
		// № РУ
		new Main_Elements().getRegistrationNumber().inputText(new Main_Elements().new Values().RegistrationNumber);
		
		// Дата окончания РУ
		new Main_Elements().getRegistrationEndDate().click();
		new Main_Elements().getRegistrationEndDate().inputText(new Main_Elements().new Values().RegistrationEndDate);
		
		// Состояние по препарату
		new Main_Elements().getDrugCondition().selectByVisibleText(new Main_Elements().new Values().DrugCondition);
		
		// № приказа
		new Main_Elements().getOrderNumber().inputText(new Main_Elements().new Values().OrderNumber);
		
		// Проверка подтягивания запланированной даты предоставления документов на перерегистрацию
		assertThat(new Main_Elements().getReRegPlannedDate().getAttribute("value"), is(equalTo(new Main_Elements().new Values().ReRegPlannedDate)));
	}
	//________________________________________________________________________________________________________________________________//
	
	
	//___________________________________________________________ МНН ________________________________________________________________//
	
	public void mnn_Add()
	{		
		// Открытие поп-апа добавления МНН
		new Mnn_Elements().getAddButton().click();
		simpleWait(2);
		waitUntilUnblocked((new Mnn_Elements().getAddEditPopUp()));
		simpleWait(2);
		
		// Внести МНН
		new Mnn_Elements().getMnnField().inputText(new Mnn_Elements().new Values().mnn);
		
		// Сохранение МНН
		new Mnn_Elements().getSaveButton().click();
		simpleWait(1);
		
		// Ожидание прогрузки грида
		waitForBlockStatus(new Mnn_Elements().getGridDownload_Div(), false);
		simpleWait(1);
	}
	
	public void addedMnn_check()
	{
		// Определение ожидаемых значений
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  new Mnn_Elements().new Values().mnn,
										  ""};
		
		// Вытянуть последнее значения из грида
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(new Mnn_Elements().getMnnGridBody());
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	//________________________________________________________________________________________________________________________________//
	
	
	//____________________________________________________ Производитель _____________________________________________________________//
	
	public void manufacturer_Add()
	{		
		// Открытие поп-апа добавления производителя
		new Manufacterer_Elements().getAddButton().click();
		simpleWait(2);
		waitUntilUnblocked((new Manufacterer_Elements().getAddEditPopUp()));
		simpleWait(2);
		
		// Выбрать производителя + проверка подтягивания страны и адреса производителя
		new Manufacterer_Elements().getManufacturerField().inputText(new Manufacterer_Elements().new Values().manufactererName);
		simpleWait(2);
		new Manufacterer_Elements().getAutocompletedManufacturer().click();
		simpleWait(2);
		assertThat(new Manufacterer_Elements().getManufacturerCountryField().getAttribute("value"), is(equalTo(new Manufacterer_Elements().new Values().manufactererCountry)));
		assertThat(new Manufacterer_Elements().getManufacturerAdressField().getAttribute("value"), is(equalTo(new Manufacterer_Elements().new Values().manufactererAdress)));
		
		// Сохранение производителя
		new Manufacterer_Elements().getSaveButton().click();
		simpleWait(1);
		
		// Ожидание прогрузки грида
		waitForBlockStatus(new Manufacterer_Elements().getGridDownload_Div(), false);
		simpleWait(1);
	}
	
	public void addedManufacturer_check()
	{
		// Определение ожидаемых значений
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
				  						  new Manufacterer_Elements().new Values().manufactererName,
										  new Manufacterer_Elements().new Values().manufactererCountry,
										  new Manufacterer_Elements().new Values().manufactererAdress,
										  ""};
		
		// Определение актуальных значений
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(new Manufacterer_Elements().getManufacturerGridBody());
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	//________________________________________________________________________________________________________________________________//

	
	//___________________________________________________ Действующее вещество _______________________________________________________//
	
	public void substance_Add()
	{		
		// Открытие поп-апа добавления действующего вещества
		new Substance_Elements().getAddButton().click();
		simpleWait(2);
		waitUntilUnblocked((new Substance_Elements().getAddEditPopUp()));
		simpleWait(2);
		
		// Внести действующее вещество
		new Substance_Elements().getSubstanceField().inputText(new Substance_Elements().new Values().substance);
		
		// Сохранение действующее вещество
		new Substance_Elements().getSaveButton().click();
		simpleWait(1);
	
		// Ожидание прогрузки грида
		waitForBlockStatus(new Substance_Elements().getGridDownload_Div(), false);
		simpleWait(1);
	}
	
	public void addedSubstance_check()
	{
		// Определение ожидаемых значений
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  new Substance_Elements().new Values().substance,
										  ""};
		
		// Определение актуальных значений
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(new Substance_Elements().getSubstancesGridBody());
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	//________________________________________________________________________________________________________________________________//
	
	
	//________________________________________________________ Документы _____________________________________________________________//
	
	public void doc_Add()
	{		
		// Открытие поп-апа добавления действующего вещества
		new Docs_Elements().getAddButton().click();
		simpleWait(2);
		waitUntilUnblocked((new Docs_Elements().getAddEditPopUp()));
		simpleWait(2);
		
		// Дата
		new Docs_Elements().getDate_Field().click();
		simpleWait(1);
		new Docs_Elements().getDate_Field().inputText(new Docs_Elements().new Values().date);
		
		// Название документа
		new Docs_Elements().getName_Field().inputText(new Docs_Elements().new Values().name);
		
		// Описание документа
		new Docs_Elements().getDescription_Field().inputText(new Docs_Elements().new Values().docDescription);
		
		// Тип документа
		new Docs_Elements().getDocType_Field().selectByVisibleText(new Docs_Elements().new Values().docType);
		
		// Добавление файл + проверка подстановки в текстовое поле
		new Docs_Elements().getFileUpload_Button().inputText(new Docs_Elements().new Values().fileWay);
		simpleWait(2);
		assertThat(new Docs_Elements().getFile_Field().getAttribute("value"), is(equalTo(new Docs_Elements().new Values().fileName)));
		
		// Ссылка на файл
		new Docs_Elements().getFileLink_Field().inputText(new Docs_Elements().new Values().fileLink);
		
		// Сохранение действующее вещество
		new Docs_Elements().getSaveButton().click();
		simpleWait(1);
		
		// Ожидание прогрузки грида
		waitForBlockStatus(new Docs_Elements().getGridDownload_Div(), false);
		simpleWait(1);
	}
	
	public void addedDoc_check()
	{
		// Определение ожидаемых значений
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
				  						  new Docs_Elements().new Values().date,
										  new Docs_Elements().new Values().name,
										  new Docs_Elements().new Values().docDescription,
										  new Docs_Elements().new Values().docType,
										  new Docs_Elements().new Values().fileLink,
										  "",
										  ""};
		
		// Определение актуальных значений
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(new Docs_Elements().getDocsGridBody());
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	public void fileUnload_check()
	{
		new CustomMethods().fileDownload_Check(new Docs_Elements().getFileDownloadButton(), "ForDocAdd.txt");
	}
	//________________________________________________________________________________________________________________________________//
	
	
	//___________________________________________________ Сотрудники _______________________________________________________//
	
	public void employee_Add()
	{		
		// Открытие поп-апа добавления действующего вещества
		new Employee_Elements().getAddButton().click();
		simpleWait(2);
		waitUntilUnblocked((new Employee_Elements().getAddEditPopUp()));
		simpleWait(2);
		
		// Внести ФИО
		new Employee_Elements().getFioField().inputText(new Employee_Elements().new Values().fio);
		
		// Внести должность
		new Employee_Elements().getPositionField().inputText(new Employee_Elements().new Values().position);
		
		// Сохранение действующее вещество
		new Employee_Elements().getSaveButton().click();
		simpleWait(1);
		
		// Ожидание прогрузки грида
		waitForBlockStatus(new Employee_Elements().getGridDownload_Div(), false);
		simpleWait(1);
	}
	
	public void addedEmployee_check()
	{
		// Определение ожидаемых значений
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  new Employee_Elements().new Values().fio,
										  new Employee_Elements().new Values().position,
										  ""};
		
		// Определение актуальных значений
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(new Employee_Elements().getEmployeeGridBody());
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	//________________________________________________________________________________________________________________________________//
	
	public DrugPassportPage drug_Save()
	{
			new Main_Elements().getSaveButton().click();
			return new DrugPassportPage(driver).waitUntilAvailable();
	}
	

	
	private class Main_Elements
	{
		// 'Страна маркетирования'
		private Select getMarketingCoutry()
		{
			return new Select(driver.findElement(By.id("Country_drug_identification_marketing_country_id")));
		}
		
		// 'Международная дата рождения'
		private TextInput getBirthday()
		{
			return new TextInput(driver, By.id("World_drug_identification_drug_birthday"));
		}
		
		// 'Заявитель'
		private TextInput getDeclarer()
		{
			return new TextInput(driver,By.id("Dic_declarer_declarer"));
		}
		
		// 'Заявитель' автокомплит
		private Text getAutocompletedDeclarer()
		{
			return new Text(driver,By.xpath("//strong[contains(text(), '" + new Values().Declarer + "')]"));
		}
		
		// 'Страна заявителя'
		private TextInput getDeclarerCountry()
		{
			return new TextInput(driver,By.id("Dic_declarer_declarer_country"));
		}
		
		// 'Страна заявителя'
		private TextInput getDeclarerAdress()
		{
			return new TextInput(driver, By.id("Dic_declarer_declarer_address"));
		}
		
		// 'Торговое название препарата'
		private TextInput getTradeName()
		{
			return new TextInput(driver, By.id("Drug_registration_trade_name"));
		}
		
		// 'Форма выпуска препарата'
		private TextInput getOutputForm()
		{
			return new TextInput(driver, By.id("Drug_registration_form"));
		}
		
		// 'Фасовка'
		private TextInput getPacking()
		{
			return new TextInput(driver, By.id("Drug_registration_packing"));
		}
		
		// 'Условия отпуска'
		private TextInput getLeaveCondition()
		{
			return new TextInput(driver, By.id("Drug_registration_conditions"));
		}
		
		// 'Способ введения'
		private Select getInjectionWay()
		{
			return new Select(driver.findElement(By.id("Drug_registration_introduction_mode")));
		}
		
		// 'Вспомогательные вещества'
		private TextInput getAdditionalSubstance()
		{
			return new TextInput(driver, By.id("Drug_registration_excipient"));
		}
		
		// 'ATC код'
		private TextInput getAtcCode()
		{
			return new TextInput(driver, By.id("Drug_registration_atc_raw"));
		}
		
		// 'ATC группа'
		private TextInput getAtcGroup()
		{
			return new TextInput(driver, By.id("Drug_registration_cf_group"));
		}
		
		// 'Тип заявки при регистрации'
		private TextInput getApplicationType()
		{
			return new TextInput(driver, By.id("Country_drug_identification_application_type"));
		}
		
		// 'Дата регистрации'
		private TextInput getRegistrationDate()
		{
			return new TextInput(driver, By.id("Country_drug_identification_first_reg_date"));
		}
		
		// '№ РУ'
		private TextInput getRegistrationNumber()
		{
			return new TextInput(driver, By.id("Country_drug_identification_first_reg_number"));
		}
		
		// 'Дата окончания РУ'
		private TextInput getRegistrationEndDate()
		{
			return new TextInput(driver, By.id("Drug_registration_reg_date_end"));
		}
		
		// 'Состояние по препарату'
		private Select getDrugCondition()
		{
			return new Select(driver.findElement(By.id("Drug_registration_dic_drug_state_id")));
		}
		
		// '№ приказа'
		private TextInput getOrderNumber()
		{
			return new TextInput(driver, By.id("Drug_registration_order_number"));
		}
		
		// 'Дата предоставления документов на перерегистрацию' -> 'Запланированная'
		private TextInput getReRegPlannedDate()
		{
			return new TextInput(driver, By.id("planned_date_reregistration"));
		}
		
		// Кнопка 'Сохранить'
		private Button getSaveButton()
		{
			return new Button(driver, By.xpath("//input[contains(@onclick,'Save()')]"));
		}
		
		private class Values
		{
			private String marketingCountry = "Катар";     								// Международная дата рождения
			private String Birthday = "01.01.2012";     								// Международная дата рождения
			private String Declarer = "Заявитель для препарата";     					// Заявитель
			private String DeclarerCountry = "Катар";     								// Страна заявителя
			private String DeclarerAdress = "ул. Тестовая, д. 2";     					// Адрес заявителя
			private String DrugTradeName = "Тестовый препарат";     				    // Торговое название препарата
			private String DrugOutputForm = "Таблетки";     							// Форма выпуска препарата
			private String Packing = "5г";     											// Фасовка
			private String DrugLeaveCondition = "Нет";     							    // Условия отпуска
			private String InjectionWay = "в ухо";     									// Способ введения
			private String AdditionalSubstance = "Вода";     			        		// Вспомогательное вещество
			private String AtcCode = "111";     										// АТС код
			private String AtcGroup = "Тестовая";     									// АТС группа
			private String ApplicationType = "Тестовый";     				    		// Тип заявки при регистрации
			private String RegistrationDate = "02.01.2012";     	    			    // Дата регистрации
			private String RegistrationNumber = "222";     							    // № РУ
			private String RegistrationEndDate = "01.01.2014";     					    // Дата окончания регистрации
			private String DrugCondition = "в стадии разработки";     				    // Состояние по препарату
			private String OrderNumber = "333";     							    	// № приказа		
			private String ReRegPlannedDate = "01.01.2013";     						// Запланированная дата предоставления документов на перерегистрацию	
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
	}
	//________________________________________________________________________________________________________________________________//
	
	// Элементы блока 'МНН'
	private class Mnn_Elements
	{
		// Кнопка добавления
		private Button getAddButton()
		{
			return new Button(driver, By.xpath("//input[@onclick='AddEditMnn()']"));
		}
		
		// Поп-ап добавления
		private Custom getAddEditPopUp()
		{
			return new Custom(driver, By.xpath("//span[text() = '" + new Values().addEditPopUpName + "']"));
		}
		
		// "Завантаження"
		private Custom getGridDownload_Div()
		{
			return new Custom(driver, By.id("load_list_Mnn"));
		}
		
		// <tbody> грида
		private WebElement getMnnGridBody()
		{
			return driver.findElement(By.xpath("//*[@id='list_Mnn']/tbody"));
		}
		
		// Мнн
		private TextInput getMnnField()
		{
			return new TextInput(driver, By.id("mnn_text"));
		}
		
		// Кнопка 'Сохранить'
		private Button getSaveButton()
		{
			return new Button(driver, By.xpath("//input[contains(@onclick,'SaveMnn()')]"));
		}
		
		private class Values
		{
			private String addEditPopUpName = "Добавить МНН";						// Название поп-апа добавления/редактирования МНН
			private String mnn = "11122233344";	  									// МНН
		}
	}
	//________________________________________________________________________________________________________________________________//
	
	// Элементы блока 'Производитель препарата'
	private class Manufacterer_Elements
	{
		// Кнопка добавления
		private Button getAddButton()
		{
			return new Button(driver, By.xpath("//input[@onclick='AddEditManufacturer()']"));
		}
		
		// Поп-ап добавления
		private Custom getAddEditPopUp()
		{
			return new Custom(driver, By.xpath("//span[text() = '" + new Values().addEditPopUpName + "']"));
		}
		
		// "Завантаження"
		private Custom getGridDownload_Div()
		{
			return new Custom(driver, By.id("load_list_manufacturer"));
		}
		
		// <tbody> грида
		private WebElement getManufacturerGridBody()
		{
			return driver.findElement(By.xpath("//*[@id='list_manufacturer']/tbody"));
		}
		
		// Производитель препарта
		private TextInput getManufacturerField()
		{
			return new TextInput(driver, By.id("manufacturer"));
		}
		
		// 'Заявитель' автокомплит
		private Text getAutocompletedManufacturer()
		{
			return new Text(driver,By.xpath("//strong[contains(text(), '" + new Values().manufactererName + "')]"));
		}
		
		// Страна производителя
		private TextInput getManufacturerCountryField()
		{
			return new TextInput(driver, By.id("manufacturer_country"));
		}
		
		// Адрес производителя
		private TextInput getManufacturerAdressField()
		{
			return new TextInput(driver, By.id("manufacturer_address"));
		}
		
		// Кнопка 'Сохранить'
		private Button getSaveButton()
		{
			return new Button(driver, By.xpath("//input[contains(@onclick,'OnSave()')]"));
		}
		
		private class Values
		{
			private String addEditPopUpName = "Добавить производителя препарата";   // Название поп-апа добавления/редактирования производителя
			private String manufactererName = "Производитель для препарата";	  	// Название производителя
			private String manufactererCountry = "Катар";	  						// Страна производителя
			private String manufactererAdress = "ул. Тестовая, д. 1";	  			// Адрес производителя
		}
	}
	
	//________________________________________________________________________________________________________________________________//
	
	// Элементы блока 'Действующие вещества'
	private class Substance_Elements
	{
		// Кнопка добавления
		private Button getAddButton()
		{
			return new Button(driver, By.xpath("//input[@onclick='AddEditSubstances()']"));
		}
		
		// Поп-ап добавления
		private Custom getAddEditPopUp()
		{
			return new Custom(driver, By.xpath("//span[text() = '" + new Values().addEditPopUpName + "']"));
		}
		
		// "Завантаження"
		private Custom getGridDownload_Div()
		{
			return new Custom(driver, By.id("load_list_Substances"));
		}
		
		// <tbody> грида
		private WebElement getSubstancesGridBody()
		{
			return driver.findElement(By.xpath("//*[@id='list_Substances']/tbody"));
		}
		
		// Действующее вещество
		private TextInput getSubstanceField()
		{
			return new TextInput(driver, By.id("active_substance"));
		}
		
		// Кнопка 'Сохранить'
		private Button getSaveButton()
		{
			return new Button(driver, By.xpath("//input[contains(@onclick,'SaveSubstances()')]"));
		}
		
		private class Values
		{
			private String addEditPopUpName = "Добавить действующее вещество";		// Название поп-апа добавления/редактирования действующего вещества
			private String substance = "Тест";	  									// Действующее вещество
		}
	}
	//________________________________________________________________________________________________________________________________//
	
	
	// Элементы блока 'Документы'
		private class Docs_Elements
		{
			// Кнопка добавления
			private Button getAddButton()
			{
				return new Button(driver, By.xpath("//input[@onclick='AddEditFile()']"));
			}
			
			// Поп-ап добавления
			private Custom getAddEditPopUp()
			{
				return new Custom(driver, By.xpath("//span[text() = '" + new Values().addEditPopUpName + "']"));
			}
			
			// "Завантаження"
			private Custom getGridDownload_Div()
			{
				return new Custom(driver, By.id("load_list_file_load"));
			}
			
			// <tbody> грида
			private WebElement getDocsGridBody()
			{
				return driver.findElement(By.xpath("//*[@id='list_file_load']/tbody"));
			}
			
			// Дата
			private TextInput getDate_Field()
			{
				return new TextInput(driver, By.id("Date"));
			}
			
			// Название документа
			private TextInput getName_Field()
			{
				return new TextInput(driver, By.id("Title"));
			}
			
			// Описание документа
			private TextInput getDescription_Field()
			{
				return new TextInput(driver, By.id("Description"));
			}
			
			// Описание документа
			private Select getDocType_Field()
			{
				return new Select(driver.findElement(By.id("TypeId")));
			}
			
			// Файл
			private TextInput getFile_Field()
			{
				return new TextInput(driver, By.id("FileName"));
			}
			
			private TextInput getFileUpload_Button()
			{
				return new TextInput(driver, By.id("file_source"));
			}
			
			// Ссылка на файл
			private TextInput getFileLink_Field()
			{
				return new TextInput(driver, By.id("FileLink"));
			}
			
			// Кнопка 'Сохранить'
			private Button getSaveButton()
			{
				return new Button(driver, By.id("save_file_btn"));
			}
			
			// Кнопка выгрузки файла
			private Button getFileDownloadButton()
			{
				return new Button(driver, By.xpath("//td[@aria-describedby='list_file_load_load']/input"));
			}
			
			private class Values
			{
				private String addEditPopUpName = "Добавление документа";				// Название поп-апа добавления/редактирования документа
				private String date = "05.01.2012";	  									// Дата
				private String name = "Фай";						     				// Название документа
				private String docDescription = "Тестовое";						    	// Описание документа
				private String docType = "Инструкция";						     		// Инструкция
				private String fileWay = "C:\\Selenium_TestData\\Other\\ForDocAdd.txt";	// Ссылка на файл(реальная)
				private String fileName = "ForDocAdd.txt";								// Название файла
				private String fileLink = "www.getFile.com/get";			     		// Ссылка на файл
			}
		}
		
		// Элементы блока 'Сотрудники'
		private class Employee_Elements
		{
			// Кнопка добавления
			private Button getAddButton()
			{
				return new Button(driver, By.xpath("//input[@onclick='AddEditPerson()']"));
			}
			
			// Поп-ап добавления
			private Custom getAddEditPopUp()
			{
				return new Custom(driver, By.xpath("//span[text() = '" + new Values().addEditPopUpName + "']"));
			}
			
			// "Завантаження"
			private Custom getGridDownload_Div()
			{
				return new Custom(driver, By.id("load_list_staff"));
			}
			
			// <tbody> грида
			private WebElement getEmployeeGridBody()
			{
				return driver.findElement(By.xpath("//*[@id='list_staff']/tbody"));
			}
			
			// ФИО
			private TextInput getFioField()
			{
				return new TextInput(driver, By.id("full_name"));
			}
			
			// Сфера ответственности
			private TextInput getPositionField()
			{
				return new TextInput(driver, By.id("responsibilities"));
			}
			
			// Кнопка 'Сохранить'
			private Button getSaveButton()
			{
				return new Button(driver, By.xpath("//input[contains(@onclick,'SavePerson()')]"));
			}
			
			private class Values
			{
				private String addEditPopUpName = "Добавить сотрудников, которые ведут ЛС";		// Название поп-апа добавления/редактирования сотрудника
				private String fio = "Автоматичний Петро Васильович";	  						// ФИО
				private String position = "Начальникэ";						     				// Сфера ответственности
			}
		}
		//________________________________________________________________________________________________________________________________//
}