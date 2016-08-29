package com.dsbase.core.web.pages.drug;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dsbase.core.web.CustomMethods;
import com.dsbase.core.web.WebPage;
import com.dsbase.core.web.elements.Button;
import com.dsbase.core.web.elements.Link;
import com.dsbase.core.web.elements.Text;


public class DrugPassportPage extends WebPage<DrugPassportPage> 
{
private static final String PAGE_URL = BASE_URL + "/Drugs/View/";
	
	public DrugPassportPage(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public DrugPassportPage load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return getDrugPassportButton().isAvailable();
	}
	
	public class GoTo
	{
		public DrugInstructionsPage instructionsPage()
		{
			new DrugRegistration_Elements().getInstructions_Link().click();
			simpleWait(1);
			return new DrugInstructionsPage(driver).waitUntilAvailable();
		}
		
		public DrugChangesPage changesPage()
		{
			new DrugRegistration_Elements().getChanges_Link().click();
			simpleWait(1);
			return new DrugChangesPage(driver).waitUntilAvailable();
		}
		
		public DrugReferentsPage referentsPage()
		{
			new DrugRegistration_Elements().getRefDrug_Link().click();
			simpleWait(1);
			return new DrugReferentsPage(driver).waitUntilAvailable();
		}
	}
			
	
	public void addedPassport_Check()
	{
		// Проверка тайтла
		drugPassportTitle_Check();
		
		// Проверка первой части текстовых полей
		firstPart_Check();
		
		// Проверка блока МНН
		mnnPart_check("Add");
		
		// Проверка блока производители
		manufacturerPart_check();
		
		// Проверка блока заявителя
		declarerPart_Check();
		
		// Проверка части 'Состав ЛС'
		drugStructurePart_check("Add");
		
		// Проверка части 'АТС'
		atcPart_Check();
		
		// Проверка части 'Регистрация ЛС'
		drugRegistrationPart_check();
		
		// Проверка грида 'Перерегистрация ЛС'
		drugReRegistrationPart_check();
		
		// Проверка грида 'Изменения'
		drugChanges_check();
		
		// Проверка блока 'Дата предоставления документов на перерегистрацию' и близлежащих
		LastPart_check();
	
		// Проверка грида 'Сотрудники'
		employeesPart_check("Add");
		
		// Проверка грида 'Страны маркетирования'
		drugOtherCountries_check();
	}

	public DrugEditPage drug_Edit()
	{
		getDrugEdit_Button().click();
		simpleWait(2);
		return new DrugEditPage(driver).waitUntilAvailable();
	}
	
	public void editedPassport_Check()
	{
		// Проверка тайтла
		drugPassportTitle_Check();
		
		// Проверка первой части текстовых полей
		firstPart_Check();
		
		// Проверка грида МНН
		mnnPart_check("Edit");
		
		// Проверка грида производители
		manufacturerPart_check();
		
		// Проверка блока заявителя
		declarerPart_Check();
		
		// Проверка части 'Состав ЛС'
		drugStructurePart_check("Edit");
		
		// Проверка части 'АТС'
		atcPart_Check();
		
		// Проверка части 'Регистрация ЛС'
		drugRegistrationPart_check();
		
		// Проверка грида 'Перерегистрация ЛС'
		drugReRegistrationPart_check();
		
		// Проверка грида 'Изменения'
		drugChanges_check();
		
		// Проверка блока 'Дата предоставления документов на перерегистрацию' и близлежащих
		LastPart_check();
	
		// Проверка грида 'Сотрудники'
		employeesPart_check("Edit");
		
		// Проверка грида 'Страны маркетирования'
		drugOtherCountries_check();
	}
	
	public void deletedParts_Check()
	{
		// Проверка грида МНН
		new Deletion_Check().deletedMnn_check();
		
		// Проверка грида 'Производители'
		new Deletion_Check().deletedManufacturer_check();
		
		// Проверка грида 'Действущее вещество'
		new Deletion_Check().deletedSubstance_check();
		
		// Проверка грида 'Сотрудники'
		new Deletion_Check().deletedEmployee_check();
	}
	
	private void drugPassportTitle_Check()
	{
		// Определение тайтла
		String expectedTitle = "Паспорт препарата: Тестовый препарат, Таблетки, 02.01.2012, 222";
		String actualTitle = new CustomMethods().StringSpacesCut(getDrugPassport_Title().getText());
		
		// Проверка соответствия
		assertThat(actualTitle, is(equalTo(expectedTitle)));
	}
	
	private void firstPart_Check()
	{
		// Определение ожидаемых значений
		String[][] ExpectedValues = new String [12][];
		ExpectedValues[0] = new String[] {"Страна маркетирования:"};
		ExpectedValues[1] = new String[] {new First_Part_Elements().new Values().DeclarerCountry};
		ExpectedValues[2] = new String[] {"Торговое название препарата:"};
		ExpectedValues[3] = new String[] {new First_Part_Elements().new Values().DrugTradeName};
		ExpectedValues[4] = new String[] {"Форма выпуска препарата:"};
		ExpectedValues[5] = new String[] {new First_Part_Elements().new Values().DrugOutputForm};
		ExpectedValues[6] = new String[] {"Фасовка:"};
		ExpectedValues[7] = new String[] {new First_Part_Elements().new Values().Packing};
		ExpectedValues[8] = new String[] {"Условия отпуска:"};
		ExpectedValues[9] = new String[] {new First_Part_Elements().new Values().DrugLeaveCondition};
		ExpectedValues[10] = new String[] {"Способ введения"};
		ExpectedValues[11] = new String[] {new First_Part_Elements().new Values().InjectionWay};
		
		// Вытянуть значения из грида
		String[][] ActualValues = new First_Part_Elements().getValuesForFirstPart(new First_Part_Elements().getGridBody());
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	private void mnnPart_check(String CheckType)
	{
		// Объявление переменной
		String Mnn = "";
		
		// Присвоение значения переменной в зависимости от типа проверки
		if(CheckType.equals("Add")) Mnn = new Grids_Elements().new Mnn().new Values().Mnn;
		else if(CheckType.equals("Edit")) Mnn = new Grids_Elements().new Mnn().new Values().editedMnn;
		
		// Определение ожидаемых значений
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {Mnn};
		
		// Вытянуть значения из грида
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(new Grids_Elements().new Mnn().getGridBody());
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	private void manufacturerPart_check()
	{
		// Определение значений ячеек
		String manufacturerName = new Grids_Elements().new Manufacturer().new Values().manufactererName;
		String manufacturerCountry = new Grids_Elements().new Manufacturer().new Values().manufactererCountry;
		String manufacturerAdress = new Grids_Elements().new Manufacturer().new Values().manufactererAdress;
		
		// Определение ожидаемых значений
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {manufacturerName, manufacturerCountry, manufacturerAdress};
		
		// Вытянуть значения из грида
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(new Grids_Elements().new Manufacturer().getGridBody());
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	private void drugStructurePart_check(String CheckType)
	{
		/*_________________________________________________ Проверка действующего вещества _________________________________________________*/
		
		// Объявление переменной
		String activeSubstance = "";
		
		// Присвоение значения переменной в зависимости от типа проверки
		if(CheckType.equals("Add")) activeSubstance = new Grids_Elements().new Drug_Structure().new Values().acitveSubstance;
		else if(CheckType.equals("Edit")) activeSubstance = new Grids_Elements().new Drug_Structure().new Values().editedActiveSubstance;
		
		// Определение ожидаемых значений
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {activeSubstance};
		
		// Вытянуть значения из грида
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(new Grids_Elements().new Drug_Structure().getGridBody());
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
		/*__________________________________________________________________________________________________________________________________*/

		
		/*_________________________________________________ Проверка вспомагательного вещества _________________________________________________*/
		
		// Определение названия и значения поля вспомагательного вещества
		String additionalSubstanceTitle = new CustomMethods().StringSpacesCut(new Grids_Elements().new Drug_Structure().new Values().additionalSubstanceTitle);
		String additionalSubstance = new CustomMethods().StringSpacesCut(new Grids_Elements().new Drug_Structure().new Values().additionalSubstance);
		
		// Проверка подтягивания запланированной даты предоставления документов на перерегистрацию
		assertThat(new Grids_Elements().new Drug_Structure().getAdditionalSubstanceTitle().getText(), is(equalTo(additionalSubstanceTitle)));
		assertThat(new Grids_Elements().new Drug_Structure().getAdditionalSubstanceField().getText(), is(equalTo(additionalSubstance)));
	}
	
	private void declarerPart_Check()
	{
		// Определение значений ячеек
		
		// Определение ожидаемых значений
		String[][] ExpectedValues = new String [4][];
		ExpectedValues[0] = new String[] {new Declarer_Part_Elements().new Values().DeclarerTitle, ""};
		ExpectedValues[1] = new String[] {new Declarer_Part_Elements().new Values().Declarer};
		ExpectedValues[2] = new String[] {new Declarer_Part_Elements().new Values().DeclarerCountryTitle, new Declarer_Part_Elements().new Values().DeclarerAdressTitle};
		ExpectedValues[3] = new String[] {new Declarer_Part_Elements().new Values().DeclarerCountry, new Declarer_Part_Elements().new Values().DeclarerAdress};
		
		// Вытянуть значения из грида
		String[][] ActualValues = new Declarer_Part_Elements().getValuesForDeclarerPart(new Declarer_Part_Elements().getGridBody());
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
		
	private void atcPart_Check()
	{	
		// Определение значений ячеек
		String atcCodeTitle = new AtcPart_Elements().new Values().atcCodeTitle;
		String atcCode = new AtcPart_Elements().new Values().atcCode;
		String atcGroupTitle = new AtcPart_Elements().new Values().atcGroupTitle;
		String atcGroup = new AtcPart_Elements().new Values().atcGroup;
		String birthdayTitle = new AtcPart_Elements().new Values().birthdayTitle;
		String birthday = new AtcPart_Elements().new Values().birthday;
		
		// Определение ожидаемых значений
		String[][] ExpectedValues = new String [2][];
		ExpectedValues[0] = new String[] {atcCodeTitle, atcGroupTitle, birthdayTitle};
		ExpectedValues[1] = new String[] {atcCode, atcGroup, birthday};
		
		// Вытянуть значения из грида
		String[][] ActualValues = new AtcPart_Elements().getValuesFor_AtcPart(new AtcPart_Elements().getGridBody());
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	private void drugRegistrationPart_check()
	{
		/*___________________________________________ Проверка ссылок в блоке 'Регистрация ЛС' ___________________________________________*/
		
		// Определение ожидаемых значений
		String appTypeExpected = new DrugRegistration_Elements().new Values().appType;
		String regDateExpected = new DrugRegistration_Elements().new Values().regDate;
		String regNumExpected = new DrugRegistration_Elements().new Values().regNum;
		String instructionsExpected = new DrugRegistration_Elements().new Values().instructionsLink;
		String psurExpected = new DrugRegistration_Elements().new Values().psurLink;
		String prExpected = new DrugRegistration_Elements().new Values().prLink;
		String changesExpected = new DrugRegistration_Elements().new Values().changesLink;
		String refDrugExpected = new DrugRegistration_Elements().new Values().refDrugLink;
		
		// Определение реальных значений
		String appTypeActual = new CustomMethods().StringSpacesCut(new DrugRegistration_Elements().getAppType_Link().getText());
		String regDateActual = new CustomMethods().StringSpacesCut(new DrugRegistration_Elements().getRegDate_Link().getText());
		String regNumActual = new CustomMethods().StringSpacesCut(new DrugRegistration_Elements().getRegNum_Link().getText());
		String instructionsActual = new CustomMethods().StringSpacesCut(new DrugRegistration_Elements().getInstructions_Link().getText());
		String psurActual = new CustomMethods().StringSpacesCut(new DrugRegistration_Elements().getPsur_Link().getText());
		String prActual = new CustomMethods().StringSpacesCut(new DrugRegistration_Elements().getPrMessages_Link().getText());
		String changesActual = new CustomMethods().StringSpacesCut(new DrugRegistration_Elements().getChanges_Link().getText());
		String refDrugActual = new CustomMethods().StringSpacesCut(new DrugRegistration_Elements().getRefDrug_Link().getText());

		// Проверка значений
		assertThat(appTypeActual, is(equalTo(appTypeExpected)));
		assertThat(regDateActual, is(equalTo(regDateExpected)));
		assertThat(regNumActual, is(equalTo(regNumExpected)));
		assertThat(instructionsActual, is(equalTo(instructionsExpected)));
		assertThat(psurActual, is(equalTo(psurExpected)));
		assertThat(prActual, is(equalTo(prExpected)));
		assertThat(changesActual, is(equalTo(changesExpected)));
		assertThat(refDrugActual, is(equalTo(refDrugExpected)));
	}
	
	private void drugReRegistrationPart_check()
	{
		// Проверка отсутствия значений в гриде 'Перерегистрация ЛС'
		new CustomMethods().elementExistenceCheck(new Grids_Elements().new Drug_Reregistration().getGridBody() , false);
	}
	
	private void drugChanges_check()
	{
		// Проверка отсутствия значений в гриде 'Изменения'
		new CustomMethods().elementExistenceCheck(new Grids_Elements().new Drug_Changes().getGridBody() , false);
	}
	
	private void LastPart_check()
	{
		// Определение значений ячеек грида
		String plannedDateTitle = new LastPart_Elements().new Values().plannedDateTitle;
		String plannedDate = new LastPart_Elements().new Values().plannedDate;
		String actualDateTitle = new LastPart_Elements().new Values().actualDateTitle;
		String actualDate = new LastPart_Elements().new Values().actualDate;
		String endRuDateTitle = new LastPart_Elements().new Values().endRuDateTitle;
		String endRuDate = new LastPart_Elements().new Values().endRuDate;
		String drugConditionTitle = new LastPart_Elements().new Values().drugConditionTitle;
		String drugCondition = new LastPart_Elements().new Values().drugCondition;
		
		// Определение ожидаемых значений
		String[][] ExpectedValues = new String [4][];
		ExpectedValues[0] = new String[] {plannedDateTitle, actualDateTitle};
		ExpectedValues[1] = new String[] {plannedDate, actualDate};
		ExpectedValues[2] = new String[] {endRuDateTitle, drugConditionTitle};
		ExpectedValues[3] = new String[] {endRuDate, drugCondition};
		
		// Вытянуть значения из грида
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(new LastPart_Elements().getGridBody());
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	private void employeesPart_check(String CheckType)
	{
		// Определение значений ячеек грида
		String FIO = new Grids_Elements().new Employees().new Values().fio;
		
		// Объявление переменной
		String Position = "";
		
		// Присвоение значения переменной в зависимости от типа проверки
		if(CheckType.equals("Add")) Position = new Grids_Elements().new Employees().new Values().position;
		else if(CheckType.equals("Edit")) Position = new Grids_Elements().new Employees().new Values().editedPosition;
		
		// Определение ожидаемых значений
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {FIO, Position};
		
		// Вытянуть значения из грида
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(new Grids_Elements().new Employees().getGridBody());
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	private void drugOtherCountries_check()
	{
		// Проверка отсутствия значений в гриде 'Изменения'
		new CustomMethods().elementExistenceCheck(new Grids_Elements().new Drug_OtherCountries().getGridBody() , false);
	}
	
	/*================================================= Проверки удаленных записей в грида ================================================*/
	private class Deletion_Check
	{
		public void deletedMnn_check()
		{
			// Проверка отсутствия значений в гриде 'МНН'
			new CustomMethods().elementExistenceCheck(new Grids_Elements().new Mnn().getGridBody(), false);
		}
		
		public void deletedManufacturer_check()
		{
			// Проверка отсутствия значений в гриде 'Действущие вещества'
			new CustomMethods().elementExistenceCheck(new Grids_Elements().new Manufacturer().getGridBody(), false);
		}
		
		public void deletedSubstance_check()
		{
			// Проверка отсутствия значений в гриде 'Действущие вещества'
			new CustomMethods().elementExistenceCheck(new Grids_Elements().new Drug_Structure().getGridBody(), false);
		}
		
		public void deletedEmployee_check()
		{
			// Проверка отсутствия значений в гриде 'Действущие вещества'
			new CustomMethods().elementExistenceCheck(new Grids_Elements().new Employees().getGridBody(), false);
		}
	}
	
	/*================================================= Проверки связанные с другими тестами ==============================================*/
	public class For_Other_Tests
	{
		public class DrugChanges_Test
		{
			public void added_drugChanges_Check()
			{	
				// Определение значений ячеек
				String orderNumber = new Values().orderNumber;
				String orderDate = new Values().orderDate;
				String changeType = new Values().changeType;
				
				// Определение ожидаемых значений
				String[][] ExpectedValues = new String [1][];
				ExpectedValues[0] = new String[] {"",
												  orderNumber, 
												  orderDate, 
												  changeType};
				
				// Вытянуть значения из грида
				String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(new Grids_Elements().new Drug_Changes().getGridBody());
				
				// Проверка значений грида
				new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
			}
			
			public DrugChangeAddEditPage drugChange_Edit()
			{
				new Grids_Elements().new Drug_Changes().getEdit_Button().click();
				simpleWait(1);
				return new DrugChangeAddEditPage(driver).waitUntilAvailable();
			}
			
			private class Values
			{
				private String orderNumber = "111222";	  						  		// № приказа
				private String orderDate = "02.01.2012";								// Дата приказа
				private String changeType = "Административные изменения";				// Тип изменения
			}
		}
	}
	/*============================================== Используемые элементы и действия с ними ==============================================*/

	private Button getDrugPassportButton()
	{
		return new Button(driver, By.id("PasportPreparata"));
	}
	
	private Text getDrugPassport_Title()
	{
		return new Text(driver, By.xpath("//div[@class='content_page table_cell']/h1"));
	}
	
	private Button getDrugEdit_Button()
	{
		return new Button(driver, By.xpath("//input[@type='button' and contains(@onclick, 'EditFirstDrug')]"));
	}
	
	//___________________________________________________ Элементы первого блока ___________________________________________________//
	
	private class First_Part_Elements
	{
		private WebElement getGridBody()
		{
			return driver.findElement(By.xpath("//div[@class='content_page table_cell']/div[@class='max_width1010px']/table[1]/tbody"));
		}
		
		private String[][] getValuesForFirstPart (WebElement table)
		{
			// Определение количества рядов
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			
			// Перебираем ряды - удаляем лишние <tr>
			for(int rnum= (rows.size() - 1); rnum > 0; rnum--)
			{
				// Определение текста, класса и роли для конкретного <tr>
				WebElement tempElement = rows.get(rnum);
				String ElText = tempElement.getText();
				String ElClass = tempElement.getAttribute("class");
				String ElRole = tempElement.getAttribute("role");
				
				// Отсеять <tr> без текста, с наличием класса или роли(блок 'МНН', в общем)
				if((ElText.equals("")) || (ElClass != null && !ElClass.isEmpty()) || (ElRole != null && !ElRole.isEmpty()))
				{
					rows.remove(rnum);
				}
			}
			
			// Удаление лишних элементов(Блок 'МНН')
			rows.remove(4);
			
			// Удаление лишних элементов(Блок 'Способ введения')
			rows.remove(12);
			rows.remove(11);
			
			// Определение количества рядов в возвращаемом массиве
			String[][] GridValues = new String[rows.size()][];
			
			// Перебираем ряды - записываем значения в одномерные массивы, которые потом помещаем в двухмерный массив(нужно для универсальной проверки)
			for(int rnum = 0; rnum < rows.size(); rnum++)
			{			
				// Определение размера одномерного массива
				String[] ColumnValues = new String[1];
			
				// Взять текст из <tr>
				ColumnValues[0] = new CustomMethods().StringSpacesCut(rows.get(rnum).getText());
		
				// Положить значение в двухмерные массив
				GridValues[rnum] = ColumnValues;
			}
			return GridValues;
		}
		
		private class Values
		{
			private String DeclarerCountry = "Катар";     								// Страна заявителя
			private String DrugTradeName = "Тестовый препарат";     				    // Торговое название препарата
			private String DrugOutputForm = "Таблетки";     							// Форма выпуска препарата
			private String Packing = "5г";     											// Фасовка
			private String DrugLeaveCondition = "Нет";     							    // Условия отпуска
			private String InjectionWay = "в ухо";     									// Способ введения
		}
	}
	//________________________________________________________________________________________________________________________________//
	
	
	//___________________________________________________ Элементы блока заявителя ___________________________________________________//

	private class Declarer_Part_Elements
	{
		private WebElement getGridBody()
		{
			return driver.findElement(By.xpath("//div[@class='content_page table_cell']/div[@class='max_width1010px']/table[2]/tbody"));
		}
		
		private String[][] getValuesForDeclarerPart (WebElement table)
		{
			// Определение количества рядов
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			
			// Определение количества рядов в возвращаемом массиве
			String[][] GridValues = new String[rows.size()][];
			
			// Перебираем ряды
			for(int rnum=0; rnum < rows.size(); rnum++)
			{
				// Определяем количество колонок
				List<WebElement> columns=rows.get(rnum).findElements(By.tagName("td"));

				// Определение размера массива с значениями ячеек
				String[] ColumnValues = new String[columns.size()];
				
				// Записываем значения ячеек в массив
				for(int cnum=0; cnum < columns.size(); cnum++)
				{					
					ColumnValues[cnum] = new CustomMethods().StringSpacesCut(columns.get(cnum).getText());
				}
				
				// Положить значения колонок в ряд 
				GridValues[rnum] = ColumnValues;
			}
			return GridValues;
		}
		
		private class Values
		{
			private String DeclarerTitle = "Заявитель:";      							// Названия поля 'Заявитель'
			private String Declarer = "Заявитель для препарата";     					// Заявитель
			private String DeclarerCountryTitle = "Страна заявителя:";     				// Названия поля 'Страна заявителя'
			private String DeclarerCountry = "Катар";     								// Страна заявителя
			private String DeclarerAdressTitle = "Адрес заявителя:";     				// Названия поля 'Адрес заявителя'
			private String DeclarerAdress = "ул. Тестовая, д. 2";     					// Адрес заявителя
		}
	}
	//________________________________________________________________________________________________________________________________//
	
	
	//__________________________________________________ Элементы блоков с гридами ___________________________________________________//
	
	private class Grids_Elements
	{
		// Грид 'МНН'
		private class Mnn
		{
			private WebElement getGridBody()
			{
				return driver.findElement(By.xpath("//table[@id='list_Mnn']/tbody"));
			}
			
			private class Values
			{
				private String Mnn = "11122233344";      								// МНН
				private String editedMnn = "111222333444";								// МНН после редактирования
			}
		}
		
		// Грид 'Производители'
		private class Manufacturer
		{
			private WebElement getGridBody()
			{
				return driver.findElement(By.xpath("//table[@id='list_manufacturer']/tbody"));
			}
			
			private class Values
			{
				private String manufactererName = "Производитель для препарата";	  	// Название производителя
				private String manufactererCountry = "Катар";	  						// Страна производителя
				private String manufactererAdress = "ул. Тестовая, д. 1";	  			// Адрес производителя
			}
		}
		
		// Блок 'Состав ЛС'
		private class Drug_Structure
		{
			private WebElement getGridBody()
			{
				return driver.findElement(By.xpath("//table[@id='list_Substances']/tbody"));
			}
			
			private Text getAdditionalSubstanceTitle()
			{
				return new Text(driver, By.xpath("//div[@class='content_page table_cell']/div[@class='max_width1010px']/div[5]/table/tbody/tr[2]"));
			}
			
			private Text getAdditionalSubstanceField()
			{
				return new Text(driver, By.xpath("//div[@class='content_page table_cell']/div[@class='max_width1010px']/div[5]/table/tbody/tr[3]"));
			}
			
			private class Values
			{
				private String acitveSubstance = "Тест";	  									// Действующее вещество
				private String editedActiveSubstance = "Тестинин";						     	// Действующее вещество после редактирования
				private String additionalSubstanceTitle = "Вспомогательные вещества:";	  	    // Название поля для 'Вспомагательное вещество'
				private String additionalSubstance = "Вода";						     		// Вспомагательное вещество
			}
		}
		
		// Грид 'Перерегистрация ЛС'
		private class Drug_Reregistration
		{
			private WebElement getGridBody()
			{
				return driver.findElement(By.xpath("//table[@id='list_re_registering']/tbody"));
			}
		}
		
		// Грид 'Изменения'
		private class Drug_Changes
		{
			private WebElement getGridBody()
			{
				return driver.findElement(By.xpath("//table[@id='list_drug_changes']/tbody"));
			}
			
			private Button getEdit_Button()
			{
				return new Button(driver, By.xpath("//td[@aria-describedby='list_drug_changes_edit']/input"));
			}
		}
		
		// Грид 'Сотрудники'
		private class Employees
		{
			private WebElement getGridBody()
			{
				return driver.findElement(By.xpath("//table[@id='list_staff']/tbody"));
			}
			
			private class Values
			{
				private String fio = "Автоматичний Петро Васильович";	  						// ФИО
				private String position = "Начальникэ";						     				// Сфера ответственности
				private String editedPosition = "Начальник";									// Отредактированная сфера ответственности
			}
		}
		
		// Грид 'Страны маркетирования'
		private class Drug_OtherCountries
		{
			private WebElement getGridBody()
			{
				return driver.findElement(By.xpath("//table[@id='list_countries']/tbody"));
			}
		}
	}
	//________________________________________________________________________________________________________________________________//
	
	
	//_______________________________________Элементы блока 'АТС' + международная дата рождения_______________________________________//
	private class AtcPart_Elements
	{
		private WebElement getGridBody()
		{
			return driver.findElement(By.xpath("//div[@class='content_page table_cell']/div[@class='max_width1010px']/table[@class='max_width400px']/tbody"));
		}
	
		public String[][] getValuesFor_AtcPart(WebElement tbody)
		{
			// Определение количества рядов
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
						
			// Определение размера возвращаемого массива
			String[][] GridValues = new String[rows.size()][];
			
			// Перебираем ряды
			for(int rnum=0; rnum < rows.size(); rnum++)
			{
				// Определяем количество колонок
				List<WebElement> columns=rows.get(rnum).findElements(By.tagName("td"));
				int sizeBeforeCut = columns.size();
							
				// Отсеевание скрытых ячеек
				for(int cnum= (sizeBeforeCut - 1); cnum >= 0; cnum--)
				{
					if(columns.get(cnum).getCssValue("display").equals("none"))
					{
						columns.remove(cnum);
					}
				}
				
				// Определение размера массива с значениями ячеек
				String[] ColumnValues = new String[columns.size()];
				
				// Записываем значения ячеек в массив
				for(int cnum=0; cnum<columns.size(); cnum++)
				{
					ColumnValues[cnum] = columns.get(cnum).getText();
				}
				
				// Положить значения колонок в ряд 
				GridValues[rnum] = ColumnValues;
			}
			return GridValues;
		}
		
		private class Values
		{
			private String atcCodeTitle = "ATC код:";	  						    // Название поля 'АТС код'
			private String atcCode = "111";	  										// Значение поля 'АТС код'
			private String atcGroupTitle = "ATC группа:";	  						// Название поля 'АТС группа'
			private String atcGroup = "Тестовая";	  								// Значение поля 'АТС группа'
			private String birthdayTitle = "Международная дата рождения:";	  		// Название поля 'Международная дата рождения'
			private String birthday = "01.01.2012";	  								// Значение поля 'Международная дата рождения'
		}
	}
	//________________________________________________________________________________________________________________________________//
	
	
	//____________________________________________________ Блок 'Регистрация ЛС' _____________________________________________________//
	private class DrugRegistration_Elements
	{		
		private Link getAppType_Link()
		{
			return new Link(driver, By.xpath("//a[contains(@onclick, 'ForEditRegistrations')]"));
		}
		
		private Link getRegDate_Link()
		{
			return new Link(driver, By.xpath("(//a[contains(@onclick, 'ForEditRegistrations')])[2]"));
		}
		
		private Link getRegNum_Link()
		{
			return new Link(driver, By.xpath("(//a[contains(@onclick, 'ForEditRegistrations')])[3]"));
		}
		
		private Link getInstructions_Link()
		{
			return new Link(driver, By.xpath("//span[contains(@onclick, '/Instructions/List')]"));
		}
		
		private Link getPsur_Link()
		{
			return new Link(driver, By.xpath("//span[contains(@onclick, '?m=3')]"));
		}
		
		private Link getPrMessages_Link()
		{
			return new Link(driver, By.xpath("//span[contains(@onclick, '?m=4')]"));
		}
		
		private Link getChanges_Link()
		{
			return new Link(driver, By.xpath("//span[contains(@onclick, '/ChangesDrug/List')]"));
		}
		
		private Link getRefDrug_Link()
		{
			return new Link(driver, By.xpath("//span[contains(@onclick, '/ReferenceDrug/List')]"));
		}
		
		private class Values
		{
			private String appType = "Тестовый";	  										// Тип заявления при регистрации
			private String regDate = "02.01.2012";	  										// Дата регистрации
			private String regNum = "222";	  												// Номер РУ
			private String instructionsLink = "Инструкции";	  								// Инструкции
			private String psurLink = "Отчеты по безопасности";	  							// Отчеты по безопасности
			private String prLink = "Сообщения о ПР/ОЭ";	  								// Сообщения о ПР/ОЭ
			private String changesLink = "Изменения";	  									// Изменения
			private String refDrugLink = "Референтные ЛС";	  							    // Референтные ЛС
		}
	}
	//________________________________________________________________________________________________________________________________//
	
	
	//_______________________________ Элементы блок 'Дата предоставления документов на перерегистрацию' ______________________________//
	
	private class LastPart_Elements
	{
		private WebElement getGridBody()
		{
			return driver.findElement(By.xpath("(//div[@class='content_page table_cell']/div[@class='max_width1010px']/table)[4]/tbody"));
		}
		
		private class Values
		{
			private String plannedDateTitle = "Запланированная:";	  			    // Название поля 'Запланированная дата'
			private String plannedDate = "01.01.2013";	  							// Значение поля 'Запланированная дата'
			private String actualDateTitle = "Фактическая:";	  					// Название поля 'Фактическая дата'
			private String actualDate = "";	  										// Значение поля 'Фактическая дата'
			private String endRuDateTitle = "Дата окончания РУ:";	  				// Название поля 'Дата окончания РУ'
			private String endRuDate = "01.01.2014";	  							// Значение поля 'Дата окончания РУ'
			private String drugConditionTitle = "Состояние по препарату:";	  		// Название поля 'Состояние по препарату'
			private String drugCondition = "в стадии разработки";	  				// Значение поля 'Состояние по препарату'
		}
	}
	//________________________________________________________________________________________________________________________________//
	
	
	/*=====================================================================================================================================*/
}