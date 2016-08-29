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
import com.dsbase.core.web.elements.TextInput;
import com.dsbase.core.web.pages.other.LogInPage;

public class DrugInstructionsPage extends WebPage<DrugInstructionsPage> 
{
private static final String PAGE_URL = BASE_URL + "/Instructions/List/";
	
	public DrugInstructionsPage(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public DrugInstructionsPage load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return new Instructions_Block().getAdd_Button().isAvailable();
	}
	
	//________________________________________________________ Инструкции _____________________________________________________________//
	public void instruction_Add()
	{		
		// Открытие поп-апа добавления производителя
		new Instructions_Block().getAdd_Button().click();
		simpleWait(1);
		waitUntilUnblocked(new Instructions_Block().new AddEdit_PopUps_Elements().getAddPopUp());
		simpleWait(1);
		
		// Заполнение инструкции
		new Instructions_Block().instruction_FillUp();
		
		// Сохранение производителя
		new Instructions_Block().new AddEdit_PopUps_Elements().getSave_Button().click();
		simpleWait(2);
		waitForBlockStatus(new Instructions_Block().getGridDownload_Div(), false);
	}
	
	public void addedInstruction_Check()
	{
		// Определение ожидаемых значений
		String approvalDate = new Instructions_Block().new Values().approvalDate;								// Дата утверждения
		String changeNumber = new Instructions_Block().new Values().changeNumber;								// № изменения
		String changePoint = new Instructions_Block().new Values().changePoint;									// Суть изменения
		String orderNumber = new Instructions_Block().new Values().orderNumber;									// № приказа
		String orderDate = new Instructions_Block().new Values().orderDate;										// Дата приказа
		
		// Запись значений в массив
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  approvalDate, 
										  changeNumber, 
										  changePoint, 
										  orderNumber,
										  orderDate,
										  ""};
		
		// Вытянуть последнее значения из грида
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(new Instructions_Block().getGridBody());
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	public void instruction_Edit()
	{
		// Открытие поп-апа редактирования производителя
		new Instructions_Block().getEdit_Button().click();
		simpleWait(1);
		waitUntilUnblocked(new Instructions_Block().new AddEdit_PopUps_Elements().getEditPopUp());
		simpleWait(1);
		
		// Изменение № приказа
		new Instructions_Block().new AddEdit_PopUps_Elements().getOrderNumber().clear();
		new Instructions_Block().new AddEdit_PopUps_Elements().getOrderNumber().inputText(new Instructions_Block().new Values().orderNumber_edited);
		
		// Проверка сохранения радиобаттона 'Изменение безопасности'
		String ActualValue = new Instructions_Block().new AddEdit_PopUps_Elements().getChangeSecurity_Yes_RadioButton().getAttribute("checked");
		assertThat(ActualValue, is(equalTo("true")));
		
		// Сохранение производителя
		new Instructions_Block().new AddEdit_PopUps_Elements().getSave_Button().click();
		simpleWait(2);
		waitForBlockStatus(new Instructions_Block().getGridDownload_Div(), false);
	}
	
	public void editedInstruction_Check()
	{
		// Определение ожидаемых значений
		String approvalDate = new Instructions_Block().new Values().approvalDate;								// Дата утверждения
		String changeNumber = new Instructions_Block().new Values().changeNumber;								// № изменения
		String changePoint = new Instructions_Block().new Values().changePoint;									// Суть изменения
		String orderNumber = new Instructions_Block().new Values().orderNumber_edited;						    // № приказа
		String orderDate = new Instructions_Block().new Values().orderDate;										// Дата приказа
		
		// Запись значений в массив
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  approvalDate, 
										  changeNumber, 
										  changePoint, 
										  orderNumber,
										  orderDate,
										  ""};
		
		// Вытянуть последнее значения из грида
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(new Instructions_Block().getGridBody());
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	public void instruction_Delete()
	{
		new Instructions_Block().getDelete_Button().click();
		simpleWait(1);
		waitUntilUnblocked(new Deletion_PopUp().getDeletionPopUp());
		new Deletion_PopUp().getDeletionAcceptButton().click();
		simpleWait(2);
		waitForBlockStatus(new Instructions_Block().getGridDownload_Div(), false);
	}
	
	public void deletedInstruction_Check()
	{
		// Проверка отсутствия значений в гриде 'МНН'
		new CustomMethods().elementExistenceCheck(new Instructions_Block().getGridBody() , false);
	}
	
	public void instruction_Set()
	{
		simpleWait(2);
		waitForBlockStatus(new Instructions_Block().getGridDownload_Div(), false);
		
		new Instructions_Block().getChangeNumber_Cell().click();		
		simpleWait(2);
	}
	
	//________________________________________________________________________________________________________________________________//
	
	
	//________________________________________________________ Документы _____________________________________________________________//

	public void doc_Add()
	{		
		// Открытие поп-апа добавления документа
		new Docs_Block().getAddButton().click();
		simpleWait(1);
		waitUntilUnblocked((new Docs_Block().new AddEdit_PopUps_Elements().getAddPopUp()));
		simpleWait(2);
		
		// Дата
		new Docs_Block().new AddEdit_PopUps_Elements().getDate_Field().click();
		simpleWait(1);
		new Docs_Block().new AddEdit_PopUps_Elements().getDate_Field().inputText(new Docs_Block().new Values().date);
		
		// Название документа
		new Docs_Block().new AddEdit_PopUps_Elements().getName_Field().inputText(new Docs_Block().new Values().name);
		
		// Описание документа
		new Docs_Block().new AddEdit_PopUps_Elements().getDescription_Field().inputText(new Docs_Block().new Values().docDescription);
		
		// Тип документа
		new Docs_Block().new AddEdit_PopUps_Elements().getDocType_Field().selectByVisibleText(new Docs_Block().new Values().docType);
		
		// Добавление файл + проверка подстановки в текстовое поле
		new Docs_Block().new AddEdit_PopUps_Elements().getFileUpload_Button().inputText(new Docs_Block().new Values().fileWay);
		simpleWait(2);
		assertThat(new Docs_Block().new AddEdit_PopUps_Elements().getFile_Field().getAttribute("value"), is(equalTo(new Docs_Block().new Values().fileName)));
		
		// Ссылка на файл
		new Docs_Block().new AddEdit_PopUps_Elements().getFileLink_Field().inputText(new Docs_Block().new Values().fileLink);
		
		// Сохранение документа
		new Docs_Block().new AddEdit_PopUps_Elements().getSaveButton().click();
		simpleWait(2);
		waitForBlockStatus(new Docs_Block().getGridDownload_Div(), false);
	}
	
	public void addedDoc_Check()
	{
		// Определение ожидаемых значений
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
				  						  new Docs_Block().new Values().date,
										  new Docs_Block().new Values().name,
										  new Docs_Block().new Values().docDescription,
										  new Docs_Block().new Values().docType,
										  new Docs_Block().new Values().fileLink,
										  "",
										  ""};
		
		// Определение актуальных значений
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(new Docs_Block().getDocsGridBody());
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	public void fileUnload_Check()
	{
		new CustomMethods().fileDownload_Check(new Docs_Block().getFileDownloadButton(), "ForDocAdd.txt");
	}
	
	public void doc_Edit()
	{
		// Открытие поп-апа добавления документа
		new Docs_Block().getEdit_Button().click();
		simpleWait(1);
		waitUntilUnblocked((new Docs_Block().new AddEdit_PopUps_Elements().getEditPopUp()));
		simpleWait(2);
		
		// Изменить имя
		new Docs_Block().new AddEdit_PopUps_Elements().getName_Field().clear();
		simpleWait(1);
		new Docs_Block().new AddEdit_PopUps_Elements().getName_Field().inputText(new Docs_Block().new Values().editedName);
		
		// Сохранение документ
		new Docs_Block().new AddEdit_PopUps_Elements().getSaveButton().click();
		simpleWait(2);
		waitForBlockStatus(new Docs_Block().getGridDownload_Div(), false);
	}
	
	public void editedDoc_Check()
	{
		// Определение ожидаемых значений
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
				  						  new Docs_Block().new Values().date,
				  						  new Docs_Block().new Values().editedName,
				  						  new Docs_Block().new Values().docDescription,
				  						  new Docs_Block().new Values().docType,
				  						  new Docs_Block().new Values().fileLink,
				  						  "",
				  						  ""};
		
		// Определение актуальных значений
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(new Docs_Block().getDocsGridBody());
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	public void doc_Delete()
	{
		// Открытие поп-апа удаления
		new Docs_Block().getDelete_Button().click();
		simpleWait(1);
		waitUntilUnblocked(new Deletion_PopUp().getDeletionPopUp());
		simpleWait(2);
			
		// Удаление документа
		new Deletion_PopUp().getDeletionAcceptButton().click();
		simpleWait(2);
		waitForBlockStatus(new Docs_Block().getGridDownload_Div(), false);
	}
	
	public void deletedDoc_check()
	{
		// Проверка отсутствия значений в гриде 'Документы'
		new CustomMethods().elementExistenceCheck(new Docs_Block().getDocsGridBody() , false);
	}
	//________________________________________________________________________________________________________________________________//
	
	
	//________________________________________________________ Содержание инструкции _____________________________________________________________//

	public void instructionStructure_Add()
	{		
		// Открытие поп-апа добавления 
		new InstructionStructure_Block().getAddButton().click();
		simpleWait(1);
		waitUntilUnblocked((new InstructionStructure_Block().new AddEdit_PopUps_Elements().getAddEditPopUp()));
		simpleWait(2);
		
		// Название раздела
		new InstructionStructure_Block().new AddEdit_PopUps_Elements().getSectionName_SelectField().selectByIndex(2);
		
		// Текст раздела
		new InstructionStructure_Block().new AddEdit_PopUps_Elements().getSectionText_Field().inputText(new InstructionStructure_Block().new Values().sectionText);
		
		// Сохранение содержания инструкции
		new InstructionStructure_Block().new AddEdit_PopUps_Elements().getSaveButton().click();
		simpleWait(2);
		waitForBlockStatus(new InstructionStructure_Block().getGridDownload_Div(), false);
	}
	
	public void addedInstructionStructure_Check()
	{
		// Определение ожидаемых значений
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
				  						  new InstructionStructure_Block().new Values().sectionNumber,
				  						  new InstructionStructure_Block().new Values().sectionName,
										  new InstructionStructure_Block().new Values().sectionText,
										  ""};
		
		// Определение актуальных значений
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(new InstructionStructure_Block().getGridBody());
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	public void instructionStructure_Edit()
	{
		// Открытие поп-апа редактирования содержания инструкции
		new InstructionStructure_Block().getEdit_Button().click();
		simpleWait(2);
		waitUntilUnblocked((new InstructionStructure_Block().new AddEdit_PopUps_Elements().getAddEditPopUp()));
		simpleWait(2);
		
		// Изменить текст
		new InstructionStructure_Block().new AddEdit_PopUps_Elements().getSectionText_Field().clear();
		simpleWait(1);
		new InstructionStructure_Block().new AddEdit_PopUps_Elements().getSectionText_Field().inputText(new InstructionStructure_Block().new Values().sectionText_Edited);
		
		// Сохранение содержание инструкции
		new InstructionStructure_Block().new AddEdit_PopUps_Elements().getSaveButton().click();
		simpleWait(2);
		waitForBlockStatus(new Docs_Block().getGridDownload_Div(), false);
	}
	
	public void editedInstructionStructure_Check()
	{
		// Определение ожидаемых значений
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
				  						  new InstructionStructure_Block().new Values().sectionNumber,
				  						  new InstructionStructure_Block().new Values().sectionName,
										  new InstructionStructure_Block().new Values().sectionText_Edited,
										  ""};
		
		// Определение актуальных значений
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(new InstructionStructure_Block().getGridBody());
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	public void instructionStructure_Delete()
	{
		// Открытие поп-апа удаления
		new InstructionStructure_Block().getDelete_Button().click();
		simpleWait(2);
		waitUntilUnblocked(new Deletion_PopUp().getDeletionPopUp());
		simpleWait(2);
			
		// Удаление Содержания инструкции
		new Deletion_PopUp().getDeletionAcceptButton().click();
		simpleWait(2);
		waitForBlockStatus(new InstructionStructure_Block().getGridDownload_Div(), false);
	}
	
	public void deletedInstructionStructure_Check()
	{
		// Проверка отсутствия значений в гриде 'Содержание инструкции'
		new CustomMethods().elementExistenceCheck(new InstructionStructure_Block().getGridBody() , false);
	}
	//________________________________________________________________________________________________________________________________//
	
	public LogInPage userOut()
	{
		// Выход из системы
		return new CommonActions().userOut(driver);
	}
	
	public void addButtonsDisability_Check()
	{
		// Обновить страничку
		driver.navigate().refresh();
		simpleWait(2);
		waitForBlockStatus(new InstructionStructure_Block().getGridDownload_Div(), false);
		
		// Проверка кнопки добавления документа
		assertThat(new Docs_Block().getAddButton().getAttribute("disabled"), is(equalTo("true")));
		
		// Проверка кнопки добавления содержания инструкции
		assertThat(new InstructionStructure_Block().getAddButton().getAttribute("disabled"), is(equalTo("true")));
	}
	
	// Элементы блока 'Инструкции'
	private class Instructions_Block
	{
		// Кнопка добавления
		private Button getAdd_Button()
		{
			return new Button(driver, By.xpath("//input[contains(@onclick, 'AddEditInstruction')]"));
		}
		
		// Тело таблицы
		private WebElement getGridBody()
		{
			return driver.findElement(By.xpath("//*[@id='list_instructions']/tbody"));
		}
		
		// "Завантаження"
		private Custom getGridDownload_Div()
		{
			return new Custom(driver, By.id("load_list_instructions"));
		}
		
		// Кнопка редактирования
		private Button getEdit_Button()
		{
			return new Button(driver, By.xpath("//td[@aria-describedby='list_instructions_edit']/input"));
		}
		
		// Кнопка удаления
		private Button getDelete_Button()
		{
			return new Button(driver, By.xpath("//td[@aria-describedby='list_instructions_del']/input"));
		}
		
		// Кнопка удаления
		private Custom getChangeNumber_Cell()
		{
			return new Custom(driver, By.xpath("//td[@aria-describedby='list_instructions_change_number']"));
		}
		
		private void instruction_FillUp()
		{
			// Дата утверждения
			new AddEdit_PopUps_Elements().getApprovalDate().click();
			new AddEdit_PopUps_Elements().getApprovalDate().inputText(new Values().approvalDate);
			
			// № изменения
			new AddEdit_PopUps_Elements().getChangeNumber().inputText(new Values().changeNumber);
			
			// Основания изменения
			new AddEdit_PopUps_Elements().getChangeReason().inputText(new Values().changeReason);
			
			// Дата заявления
			new AddEdit_PopUps_Elements().getApplicationDate().click();
			new AddEdit_PopUps_Elements().getApplicationDate().inputText(new Values().applicationDate);
			
			// Суть изменения
			new AddEdit_PopUps_Elements().getChangePoint().inputText(new Values().changePoint);
			
			// № приказа
			new AddEdit_PopUps_Elements().getOrderNumber().inputText(new Values().orderNumber);
			
			// Дата приказа
			new AddEdit_PopUps_Elements().getOrderDate().click();
			new AddEdit_PopUps_Elements().getOrderDate().inputText(new Values().orderDate);
			
			// Установка радиобаттона - 'Изменение безопасности' -> Да
			new AddEdit_PopUps_Elements().getChangeSecurity_Yes_RadioButton().click();
			
			// Суть изменений в сводных данных по безопасности
			new AddEdit_PopUps_Elements().getChangeSecurityPoint().inputText(new Values().changeSecurityPoint);
			
			// Неотраженные изменения
			new AddEdit_PopUps_Elements().getHideChanges().inputText(new Values().hideChanges);		
		}
		
		private class AddEdit_PopUps_Elements
		{
			private Custom getAddPopUp()
			{
				return new Custom(driver, By.xpath("//span[text() = '"+ new Values().addPopUp_Name +"']"));
			}
			
			private Custom getEditPopUp()
			{
				return new Custom(driver, By.xpath("//span[text() = '"+ new Values().editPopUp_Name +"']"));
			}
			
			private TextInput getApprovalDate()
			{
				return new TextInput(driver, By.id("date_of_approval"));
			}
			
			private TextInput getChangeNumber()
			{
				return new TextInput(driver, By.id("change_number"));
			}
			
			private TextInput getChangeReason()
			{
				return new TextInput(driver, By.id("change_essence"));
			}
			
			private TextInput getApplicationDate()
			{
				return new TextInput(driver, By.id("application_date"));
			}
			
			private TextInput getChangePoint()
			{
				return new TextInput(driver, By.id("change_reasons"));
			}
			
			private TextInput getOrderNumber()
			{
				return new TextInput(driver, By.id("order_number"));
			}
			
			private TextInput getOrderDate()
			{
				return new TextInput(driver, By.id("order_date"));
			}
			
			private Button getChangeSecurity_Yes_RadioButton()
			{
				return new Button(driver, By.xpath("//input[@id='is_change_security_id'][1]"));
			}
			
/*			private Button getChangeSecurity_No_RadioButton()
			{
				return new Button(driver, By.xpath("//input[@id='is_change_security_id'][2]"));
			}*/
			
			private TextInput getChangeSecurityPoint()
			{
				return new TextInput(driver, By.id("change_security_essence"));
			}
			
			private TextInput getHideChanges()
			{
				return new TextInput(driver, By.id("change_unrecognised"));
			}
			
			private Button getSave_Button()
			{
				return new Button(driver, By.xpath("//input[contains(@onclick, 'SaveInstruction')]"));
			}
		}
		
		// Значения для заполнения и проверок
		private class Values
		{
			private String addPopUp_Name = "Добавление инструкции";					// Название поп-апа добавления
			private String editPopUp_Name = "Изменение инструкции";					// Название поп-апа редактирования
			
			private String approvalDate = "01.01.2012";								// Дата утверждения
			private String changeNumber = "111";	  								// № изменения
			private String changeReason = "Тестовые";								// Основания изменения
			private String applicationDate = "02.01.2012";						    // Дата заявления
			private String changePoint = "Тестовая";								// Суть изменения
			private String orderNumber = "222";	  									// № приказа
			private String orderNumber_edited = "333";	  					  		// № приказа для редактирования
			private String orderDate = "03.01.2012";								// Дата приказа
/*			private String changeSecurity_Yes = "Да";	  						    // Изменение безопасности -> Да
			private String changeSecurity_No = "Нет";	  						    // Изменение безопасности -> Нет
*/			private String changeSecurityPoint = "Тестовая безопасность";			// Суть изменений в сводных данных по безопасности
			private String hideChanges = "Нет";	  						    		// Неотраженные изменения
		}
	}
	
	// Элементы блока 'Документы'
	private class Docs_Block
	{
		// Кнопка добавления
		private Button getAddButton()
		{
			return new Button(driver, By.id("add_edit_file_ins"));
		}
		
		// <tbody> грида
		private WebElement getDocsGridBody()
		{
			return driver.findElement(By.xpath("//*[@id='list_file_load_ins']/tbody"));
		}
		
		// "Завантаження"
		private Custom getGridDownload_Div()
		{
			return new Custom(driver, By.id("load_list_file_load_ins"));
		}
		
		// Кнопка редактирования
		private Button getEdit_Button()
		{
			return new Button(driver, By.xpath("//td[@aria-describedby='list_file_load_ins_edit']/input"));
		}
		
		// Кнопка удаления
		private Button getDelete_Button()
		{
			return new Button(driver, By.xpath("//td[@aria-describedby='list_file_load_ins_del']/input"));
		}
		
		// Кнопка выгрузки файла
		private Button getFileDownloadButton()
		{
			return new Button(driver, By.xpath("//td[@aria-describedby='list_file_load_ins_load']/input"));
		}
		
		private class AddEdit_PopUps_Elements
		{
			// Поп-ап добавления
			private Custom getAddPopUp()
			{
				return new Custom(driver, By.xpath("//span[text() = '"+ new Values().addPopUp_Name +"']"));
			}
			
			// Поп-ап редактирования
			private Custom getEditPopUp()
			{
				return new Custom(driver, By.xpath("//span[text() = '"+ new Values().editPopUp_Name +"']"));
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
		}
		
		private class Values
		{
			private String addPopUp_Name = "Добавление документа";					// Название поп-апа добавления
			private String editPopUp_Name = "Редактирование документа";				// Название поп-апа редактирования
			
			private String date = "05.01.2012";	  									// Дата
			private String name = "Фай";						     				// Название документа
			private String editedName = "Файл";										// Название документа после редактирования
			private String docDescription = "Тестовое";						    	// Описание документа
			private String docType = "Инструкция";						     		// Инструкция
			private String fileWay = "C:\\Selenium_TestData\\Other\\ForDocAdd.txt";	// Ссылка на файл(реальная)
			private String fileName = "ForDocAdd.txt";								// Название файла
			private String fileLink = "www.getFile.com/get";			     		// Ссылка на файл
		}
	}
	
	// Элементы блока 'Документы'
		private class InstructionStructure_Block
		{
			// Кнопка добавления
			private Button getAddButton()
			{
				return new Button(driver, By.id("add_edit_ins_struct"));
			}
			
			// <tbody> грида
			private WebElement getGridBody()
			{
				return driver.findElement(By.xpath("//*[@id='list_ins_struct']/tbody"));
			}
			
			// "Завантаження"
			private Custom getGridDownload_Div()
			{
				return new Custom(driver, By.id("load_list_ins_struct"));
			}
			
			// Кнопка редактирования
			private Button getEdit_Button()
			{
				return new Button(driver, By.xpath("//td[@aria-describedby='list_ins_struct_edit']/input"));
			}
			
			// Кнопка удаления
			private Button getDelete_Button()
			{
				return new Button(driver, By.xpath("//td[@aria-describedby='list_ins_struct_del']/input"));
			}
			
			private class AddEdit_PopUps_Elements
			{
				// Поп-ап добавления/редактирования
				private Custom getAddEditPopUp()
				{
					return new Custom(driver, By.xpath("//span[text() = '"+ new Values().addEditPopUp_Name +"']"));
				}
			
				// Название раздела
				private Select getSectionName_SelectField()
				{
					return new Select(driver.findElement(By.id("dic_section_names_id")));
				}
				
				// Текст раздела
				private TextInput getSectionText_Field()
				{
					return new TextInput(driver, By.id("section_text"));
				}
						
				// Кнопка 'Сохранить'
				private Button getSaveButton()
				{
					return new Button(driver, By.id("save_btn"));
				}
			}
			
			private class Values
			{
				private String addEditPopUp_Name = "Содержание инструкции";					// Название поп-апа добавления/редактирования
				
				private String sectionNumber = "03";										// Номер раздела
				private String sectionName = "Активные вещества";							// Название раздела
				private String sectionText = "Текст";										// Текст раздела
				private String sectionText_Edited = "Текст раздела";						// Текст раздела, для редактирования
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
