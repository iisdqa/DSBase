package com.dsbase.core.web.pages.drug;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.dsbase.core.web.CommonActions;
import com.dsbase.core.web.CustomMethods;
import com.dsbase.core.web.WebPage;
import com.dsbase.core.web.elements.Button;
import com.dsbase.core.web.elements.Custom;
import com.dsbase.core.web.elements.Text;
import com.dsbase.core.web.elements.TextInput;
import com.dsbase.core.web.pages.other.LogInPage;

public class DrugChangeAddEditPage extends WebPage<DrugChangeAddEditPage> 
{
private static final String PAGE_URL = BASE_URL + "/ChangesDrug/Add/";
	
	public DrugChangeAddEditPage(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public DrugChangeAddEditPage load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return new Main_Block().new Elements().getOrderNumber_Input().isAvailable();
	}
	
	//____________________________________________________ Основная часть ____________________________________________________________//
	
	public void mainPart_FillUp()
	{
		// № приказа
		new Main_Block().new Elements().getOrderNumber_Input().inputText(new Main_Block().new Values().orderNumber);
		
		// Дата приказа
		new Main_Block().new Elements().getOrderDate_Input().click();
		new Main_Block().new Elements().getOrderDate_Input().inputText(new Main_Block().new Values().orderDate);

		// Тип изменения
		new Main_Block().new Elements().getChangeType_Select().selectByVisibleText(new Main_Block().new Values().changeType);
		
		// Суть изменения
		new Main_Block().new Elements().getChangePoint_Input().inputText(new Main_Block().new Values().changePoint);
		
		// Торговое название препарата
		new Main_Block().new Elements().getTradeName_Input().inputText(new Main_Block().new Values().tradeName);
	
		// Форма выпуска
		new Main_Block().new Elements().getOutForm_Input().inputText(new Main_Block().new Values().outForm);
		
		// Фасовка
		new Main_Block().new Elements().getPacking_Input().inputText(new Main_Block().new Values().packing);
	
		// Условия отпуска
		new Main_Block().new Elements().getOutCondition_Input().inputText(new Main_Block().new Values().outCondition);
		
		// Вспомагательные вещества
		new Main_Block().new Elements().getAdditionalSubstance_Input().inputText(new Main_Block().new Values().additionalSubstance);
		
		// АТС код
		new Main_Block().new Elements().getAtcCode_Input().inputText(new Main_Block().new Values().atcCode);
		
		// АТС группа
		new Main_Block().new Elements().getAtcGroup_Input().inputText(new Main_Block().new Values().atcGroup);
		
		// Примечание
		new Main_Block().new Elements().getComment_Input().inputText(new Main_Block().new Values().comment);
	}
	
	public void mainPart_Edit()
	{
		// Дата приказа
		new Main_Block().new Elements().getOrderDate_Input().click();
		new Main_Block().new Elements().getOrderDate_Input().inputText(new Main_Block().new Values().editedOrderDate);
	}
	
	// Проверка всех полей, кроме 'Номер приказа', 'Дата приказа', 'Тип изменения' и блока 'Производитель'
	public void mainPart_Fields_Check()
	{
		// Суть изменения
		assertThat(new Main_Block().new Elements().getChangePoint_Input().getAttribute("value"), is(equalTo(new Main_Block().new Values().changePoint)));
		
		// Торговое название препарата
		assertThat(new Main_Block().new Elements().getTradeName_Input().getAttribute("value"), is(equalTo(new Main_Block().new Values().tradeName)));
		
		// Форма выпуска
		assertThat(new Main_Block().new Elements().getOutForm_Input().getAttribute("value"), is(equalTo(new Main_Block().new Values().outForm)));
		
		// Фасовка
		assertThat(new Main_Block().new Elements().getPacking_Input().getAttribute("value"), is(equalTo(new Main_Block().new Values().packing)));
	
		// Условия отпуска
		assertThat(new Main_Block().new Elements().getOutCondition_Input().getAttribute("value"), is(equalTo(new Main_Block().new Values().outCondition)));
		
		// Вспомагательные вещества
		assertThat(new Main_Block().new Elements().getAdditionalSubstance_Input().getAttribute("value"), is(equalTo(new Main_Block().new Values().additionalSubstance)));
		
		// АТС код
		assertThat(new Main_Block().new Elements().getAtcCode_Input().getAttribute("value"), is(equalTo(new Main_Block().new Values().atcCode)));
		
		// АТС группа
		assertThat(new Main_Block().new Elements().getAtcGroup_Input().getAttribute("value"), is(equalTo(new Main_Block().new Values().atcGroup)));
		
		// Примечание
		assertThat(new Main_Block().new Elements().getComment_Input().getAttribute("value"), is(equalTo(new Main_Block().new Values().comment)));
	}
	//________________________________________________________________________________________________________________________________//
	
	
	//____________________________________________________ Производитель _____________________________________________________________//
	
	public void manufacturer_Add()
	{		
		// Открытие поп-апа добавления производителя
		new Manufacterer_Block().getAddButton().click();
		simpleWait(2);
		waitUntilUnblocked((new Manufacterer_Block().getAddEditPopUp()));
		simpleWait(2);
		
		// Выбрать производителя + проверка подтягивания страны и адреса производителя
		new Manufacterer_Block().getManufacturerField().inputText(new Manufacterer_Block().new Values().manufactererName);
		simpleWait(2);
		new Manufacterer_Block().getAutocompletedManufacturer().click();
		simpleWait(2);
		assertThat(new Manufacterer_Block().getManufacturerNameEnField().getAttribute("value"), is(equalTo(new Manufacterer_Block().new Values().manufactererNameEn)));
		assertThat(new Manufacterer_Block().getManufacturerCountryField().getAttribute("value"), is(equalTo(new Manufacterer_Block().new Values().manufactererCountry)));
		assertThat(new Manufacterer_Block().getManufacturerAdressField().getAttribute("value"), is(equalTo(new Manufacterer_Block().new Values().manufactererAdress)));
		
		// Сохранение производителя
		new Manufacterer_Block().getSaveButton().click();
		simpleWait(2);
		waitUntilUnblocked((new Manufacterer_Block().getManufacturerGrid()));
	}
	
	public void addedManufacturer_check()
	{
		// Определение ожидаемых значений
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
				  						  new Manufacterer_Block().new Values().manufactererName,
				  						  new Manufacterer_Block().new Values().manufactererNameEn,
										  new Manufacterer_Block().new Values().manufactererCountry,
										  new Manufacterer_Block().new Values().manufactererAdress,
										  ""};
		
		// Определение актуальных значений
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(new Manufacterer_Block().getManufacturerGridBody());
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	public void manufacturer_Edit()
	{
		// Открытие поп-апа добавления производителя
		new Manufacterer_Block().getEditButton().click();
		simpleWait(2);
		waitUntilUnblocked((new Manufacterer_Block().getAddEditPopUp()));
		simpleWait(2);
		
		// Сохранение производителя
		new Manufacterer_Block().getSaveButton().click();
		simpleWait(2);
		waitUntilUnblocked((new Manufacterer_Block().getManufacturerGrid()));
	}
	
	public void manufacturer_Delete()
	{
		// Открытие поп-апа удаления 'Действущего вещества'
		new Manufacterer_Block().getDeleteButton().click();
		simpleWait(2);
		waitUntilUnblocked((new Deletion_PopUp().getDeletionPopUp()));
		simpleWait(2);
			
		// Удаление производителя
		new Deletion_PopUp().getDeletionAcceptButton().click();
		simpleWait(2);
		waitForBlockStatus(new Manufacterer_Block().getGridDownload_Div(), false);
	}
	
	public void deletedManufacturer_check()
	{
		// Проверка отсутствия значений в гриде 'Действущие вещества'
		new CustomMethods().elementExistenceCheck(new Manufacterer_Block().getManufacturerGridBody() , false);
	}
	//________________________________________________________________________________________________________________________________//
	
	public DrugChangesPage change_Save()
	{
		getSaveButton().click();
		return new DrugChangesPage(driver).waitUntilAvailable();
	}
	
	public LogInPage userOut()
	{
		// Выход из системы
		return new CommonActions().userOut(driver);
	}
	
	// Элементы основного блока
	private class Main_Block
	{
		// Элементы
		private class Elements
		{
			// Номер приказа
			private TextInput getOrderNumber_Input()
			{
				return new TextInput(driver, By.id("order_number"));
			}
			
			// Дата приказа
			private TextInput getOrderDate_Input()
			{
				return new TextInput(driver, By.id("order_date"));
			}
			
			// Тип изменения	
			private Select getChangeType_Select()
			{
				return new Select(driver.findElement(By.id("dic_changes_type_id")));
			}
			
			// Суть изменения
			private TextInput getChangePoint_Input()
			{
				return new TextInput(driver, By.id("change_essence"));
			}
			
			// Торговое название препарата
			private TextInput getTradeName_Input()
			{
				return new TextInput(driver, By.id("trade_name"));
			}
			
			// Форма выпуска препарата
			private TextInput getOutForm_Input()
			{
				return new TextInput(driver, By.id("form"));
			}
			
			// Фасовка
			private TextInput getPacking_Input()
			{
				return new TextInput(driver, By.id("packing"));
			}
			
			// Условия отпуска
			private TextInput getOutCondition_Input()
			{
				return new TextInput(driver, By.id("conditions"));
			}
			
			// Вспомагательные вещества
			private TextInput getAdditionalSubstance_Input()
			{
				return new TextInput(driver, By.id("excipient"));
			}
			
			// АТС код
			private TextInput getAtcCode_Input()
			{
				return new TextInput(driver, By.id("atc_raw"));
			}
			
			// АТС группа
			private TextInput getAtcGroup_Input()
			{
				return new TextInput(driver, By.id("cf_group"));
			}
			
			// Примечание
			private TextInput getComment_Input()
			{
				return new TextInput(driver, By.id("comment"));
			}
		}		
		
		// Значения для заполнения и проверок
		private class Values
		{	
			private String orderNumber = "111222";	  								// № приказа
			private String orderDate = "01.01.2012";								// Дата приказа
			private String editedOrderDate = "02.01.2012";						 	// Дата приказа(для редактирования)
			private String changeType = "Административные изменения";				// Тип изменения
			private String changePoint = "Тестовая";								// Суть изменения
			private String tradeName = "Тестовый";									// Торговое название препарата
			private String outForm = "Таблетки";	  								// Форма выпуска
			private String packing = "Нет";											// Фасовка
			private String outCondition = "Тестовые";						    	// Условия отпуска
			private String additionalSubstance = "Вода";	  					  	// Вспомагательные вещества
			private String atcCode = "333";											// АТС код
			private String atcGroup = "Есть";	  						    		// АТС группа
			private String comment = "Простой";	  						    		// Примечание
		}
	}
	//________________________________________________________________________________________________________________________________//
	
	
	// Элементы блока 'Производитель препарата'
	private class Manufacterer_Block
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
		
		// Грид
		private Custom getManufacturerGrid()
		{
			return new Custom(driver, By.id("list_manufacturer"));
		}
		
		// <tbody> грида
		private WebElement getManufacturerGridBody()
		{
			return driver.findElement(By.xpath("//*[@id='list_manufacturer']/tbody"));
		}
		
		// "Завантаження"
		private Custom getGridDownload_Div()
		{
			return new Custom(driver, By.id("load_list_manufacturer"));
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
		private TextInput getManufacturerNameEnField()
		{
			return new TextInput(driver, By.id("manufacturer_en"));
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
		
		// Кнопка редактирования
		private Button getEditButton()
		{
			return new Button(driver, By.xpath("//td[@aria-describedby='list_manufacturer_edit']/input"));
		}
		
		// Кнопка удаления
		private Button getDeleteButton()
		{
			return new Button(driver, By.xpath("//td[@aria-describedby='list_manufacturer_del']/input"));
		}
		
		// Значения для заполнения и проверок
		private class Values
		{
			private String addEditPopUpName = "Добавить производителя препарата";   // Название поп-апа добавления/редактирования производителя
			private String manufactererName = "Производитель для препарата";	  	// Название производителя
			private String manufactererNameEn = "For drugAdd";	  					// Название производителя на англ.
			private String manufactererCountry = "Катар";	  						// Страна производителя
			private String manufactererAdress = "ул. Тестовая, д. 1";	  			// Адрес производителя
		}
	}
		
	//________________________________________________________________________________________________________________________________//
	
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
	
	// Кнопка 'Сохранить'
	private Button getSaveButton()
	{
		return new Button(driver, By.xpath("//input[contains(@onclick,'SaveChanges')]"));
	}
}
