package com.dsbase.core.web.pages.other;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.dsbase.core.web.CommonActions;
import com.dsbase.core.web.CustomMethods;
import com.dsbase.core.web.WebPage;
import com.dsbase.core.web.elements.Button;
import com.dsbase.core.web.elements.Custom;
import com.dsbase.core.web.elements.Text;
import com.dsbase.core.web.elements.TextInput;
import com.dsbase.core.web.pages.administration.AdministrationPage;
import com.dsbase.core.web.pages.drug.DrugPassportPage;

public class DrugRegistryPage extends WebPage<DrugRegistryPage> 
{
	private static final String PAGE_URL = BASE_URL + "/Drugs/List";
	
	public DrugRegistryPage(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public DrugRegistryPage load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return getDrugRegistrationButton().isAvailable();
	}
	
	public void userNameCheck(String expectedUserName)
	{
		// Найти текущее имя пользователя
		String actualUserName = getUserName().getText();
		
		// Проверка равенства ожидаемого значения и реального значения
		assertThat(actualUserName, is(equalTo(expectedUserName)));
	}
	
	public void WaitForPageReady()
	{
		// Ожидание прогрузки грида
		waitForBlockStatus(getGridDownload_Div(), false);
		simpleWait(1);
	}
	
	public void drug_Search()
	{
		// Открыть аккордеон
		new filtration_Elements().getSearch_Accordion().click();
		simpleWait(2);
		
		// Указать название и найти препарат
		new filtration_Elements().getFiltrationValue_Input().inputText("Тестовый препарат");
		new filtration_Elements().getSearch_Button().click();
		simpleWait(2);
		
		waitForBlockStatus(getGridDownload_Div(), false);
		simpleWait(1);
	}
	
	public void foundDrug_Check()
	{
		// Определение переменных с значениями
		String drugTradeName = new Grid_Values().drugTradeName;
		String drugOutputForm = new Grid_Values().drugOutputForm;
		String packing = new Grid_Values().packing;
		String mnn = new Grid_Values().mnn;
		String editedActiveSubstance = new Grid_Values().editedActiveSubstance;
		String atcCode = new Grid_Values().atcCode;
		String atcGroup = new Grid_Values().atcGroup;
		String manufactererName = new Grid_Values().manufactererName;
		String manufactererCountry = new Grid_Values().manufactererCountry;
		String declarer = new Grid_Values().declarer;
		String declarerCountry = new Grid_Values().declarerCountry;
		String regDate = new Grid_Values().regDate;
		String regNum = new Grid_Values().regNum;
	    String reRegDate = new Grid_Values().reRegDate;
		String reRegNum = new Grid_Values().reRegNum;
	    String orderNum = new Grid_Values().orderNum;
		String endRuDate = new Grid_Values().endRegDate;
		String drugCondition = new Grid_Values().drugCondition;
		
		
		// Определение массива ожидаемых значений
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"1", drugTradeName, drugOutputForm, packing, mnn, editedActiveSubstance, atcCode, atcGroup, manufactererName,
										  manufactererCountry, declarer, declarerCountry, regDate, regNum, reRegDate, reRegNum, orderNum, endRuDate, drugCondition, ""};
		
		// Вытянуть значения из грида
		String[][] ActualValues = new CustomMethods(). new Grid().GetAllRows(getGridBody());
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	public void excelUnload_Check()
	{
		String expectedFileName = "RegisterDrugs_" + new CustomMethods().getCurrentDate() + ".xls";
		new CustomMethods().new WorkWith_TextFiles().fileDownload_Check(getExcelButton(), expectedFileName);
	}
	
	public void wholeFiltration_Check()
	{	
		// Открыть аккордеон
		new filtration_Elements().getSearch_Accordion().click();
		simpleWait(2);
		
		// Определение значений для фильтрации
		String[] nameValues = {new Grid_Values().drugTradeName,
							   new Grid_Values().drugOutputForm,
							   //new Grid_Values().packing,
							   new Grid_Values().mnn,
							   new Grid_Values().editedActiveSubstance,
							   new Grid_Values().atcCode,
							   new Grid_Values().atcGroup,
							   new Grid_Values().manufactererName,
							   new Grid_Values().manufactererCountry,
							   new Grid_Values().declarer,
							   new Grid_Values().declarerCountry,
							   new Grid_Values().regDate,
							   new Grid_Values().regNum,
							   new Grid_Values().reRegDate,
							   new Grid_Values().reRegNum,
							   new Grid_Values().orderNum,
							   new Grid_Values().endRegDate,
							   new Grid_Values().drugCondition};
		
		// Определение количества значений и количества вариантов фильтрации
		int namesCount = nameValues.length;
		int optionsCount = new filtration_Elements().getFiltrationName_Select().getOptions().size();
		
		// Проверка соответсвия количества значений и количества вариантов фильтрации
		if(optionsCount != namesCount)
		{
    		// Вывести ошибку
    		System.err.println("Ошибка при проверке выгрузки грида в Excel. Количество ожидаемых значений != количеству вариантов фильтрации." +
 		   		   			   "\r\nОжидаемое количество = " + namesCount + 
 		   		   			   "\r\nКоличество найденных файлов = " + optionsCount);
    		Assert.fail();
		}
		
		// Перебираем реквизиты фильтрации
		for(int i=0; i < optionsCount; i++)
		{	
			// Указать название и найти препарат
			new filtration_Elements().getFiltrationName_Select().selectByIndex(i);
			new filtration_Elements().getFiltrationValue_Input().clear();
			new filtration_Elements().getFiltrationValue_Input().inputText(nameValues[i]);
			new filtration_Elements().getSearch_Button().click();
			simpleWait(2);
			
			waitForBlockStatus(getGridDownload_Div(), false);
			simpleWait(1);
			
		// Вытянуть значения(шапка) из грида
		String[][] TopPartValues = new CustomMethods(). new WorkWith_Excel().GetHeadPart_ForExcel(getTopGridBody());

		// Вытянуть значения(записи) из грида
		String[][] GridValues = new CustomMethods(). new WorkWith_Excel().GetAllRows_ForExcel(getGridBody());
		
		// Слить два массива в один
		String[][] ExpectedValues = new String[TopPartValues.length + GridValues.length][];
		for(int j = 0; j < TopPartValues.length; j++) ExpectedValues[j] = TopPartValues[j]; 
		for(int j = 0; j < GridValues.length; j++) ExpectedValues[TopPartValues.length + j] = GridValues[j]; 
		
		String expectedFileName = "RegisterDrugs_" + new CustomMethods().getCurrentDate() + ".xls";
		String[][] ActualValues = new CustomMethods().new WorkWith_Excel().get_ExcelValues(getExcelButton(), expectedFileName);
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
		}
	}
	
	public void foundBeforeDeletionDrug_Check()
	{
		// Определение переменных с значениями
		String drugTradeName = new Grid_Values().drugTradeName;
		String drugOutputForm = new Grid_Values().drugOutputForm;
		String packing = new Grid_Values().packing;
		String mnn = " ";
		String editedActiveSubstance = "";
		String atcCode = new Grid_Values().atcCode;
		String atcGroup = new Grid_Values().atcGroup;
		String manufactererName = "";
		String manufactererCountry = "";
		String declarer = new Grid_Values().declarer;
		String declarerCountry = new Grid_Values().declarerCountry;
		String regDate = new Grid_Values().regDate;
		String regNum = new Grid_Values().regNum;
	    String reRegDate = new Grid_Values().reRegDate;
		String reRegNum = new Grid_Values().reRegNum;
	    String orderNum = new Grid_Values().orderNum;
		String endRuDate = new Grid_Values().endRegDate;
		String drugCondition = new Grid_Values().drugCondition;
		
		
		// Определение массива ожидаемых значений
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"1", drugTradeName, drugOutputForm, packing, mnn, editedActiveSubstance, atcCode, atcGroup, manufactererName,
										  manufactererCountry, declarer, declarerCountry, regDate, regNum, reRegDate, reRegNum, orderNum, endRuDate, drugCondition, ""};
		
		// Вытянуть значения из грида
		String[][] ActualValues = new CustomMethods(). new Grid().GetAllRows(getGridBody());
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	public void drug_Delete()
	{
		// Открытие поп-апа удаления 'Препарата'
		new Deletion_PopUp().getDelete_Button().click();
		simpleWait(2);
		waitUntilUnblocked((new Deletion_PopUp().getDeletion_PopUp()));
		simpleWait(2);
			
		// Подтверждение удаления прапарата
		new Deletion_PopUp().getDeletionYes_Button().click();
		simpleWait(2);
		waitUntilUnblocked((new Deletion_PopUp().getDeletion_PopUp()));
		simpleWait(2);
		new Deletion_PopUp().getDeletionYes_Button().click();
		simpleWait(2);
		waitForBlockStatus(getGridDownload_Div(), false);
	}
	
	public void deletedDrug_check()
	{
		// Проверка отсутствия значений в гриде 'Действущие вещества'
		new CustomMethods().elementExistenceCheck(getGridBody(), false);
	}
	
	public DrugPassportPage passport_Open()
	{
		Actions action = new Actions(driver);
		action.doubleClick(getDrugName_Cell()).perform();
		simpleWait(2);
		return new DrugPassportPage(driver).waitUntilAvailable();
	}
	
	public LogInPage userOut()
	{
		// Выход из системы
		return new CommonActions().userOut(driver);
	}
	
	public class goTo
	{		
		public AdministrationPage administrationPage()
		{
			getAdministrationButton().click();
			return new AdministrationPage(driver).waitUntilAvailable();
		}
	}
	
	private WebElement getTopGridBody()
	{
		return driver.findElement(By.xpath("//table[@aria-labelledby='gbox_list_search']/thead"));
	}
	
	private WebElement getGridBody()
	{
		return driver.findElement(By.xpath("//table[@id='list_search']/tbody"));
	}
	
	// "Завантаження"
	private Custom getGridDownload_Div()
	{
		return new Custom(driver, By.id("load_list_search"));
	}
	
	private WebElement getDrugName_Cell()
	{
		return driver.findElement(By.xpath("//td[@aria-describedby='list_search_TradeName']"));
	}
	
	private Button getDrugRegistrationButton()
	{
		return new Button(driver, By.xpath("//a[@href='/Drugs/Registration']"));
	}
	
	private Button getAdministrationButton()
	{
		return new Button(driver, By.xpath("//a[@href='/User']"));
	}
	
	private Button getExcelButton()
	{
		return new Button(driver, By.id("export_report_btn"));
	}
	
	private class Deletion_PopUp
	{
		// Кнопка удаления
		private Button getDelete_Button()
		{
			return new Button(driver, By.xpath("//td[@aria-describedby='list_search_del']/input"));
		}
		
		// Поп-ап удаления
		private Custom getDeletion_PopUp()
		{
			return new Custom(driver, By.id("attention_delete"));
		}
		
		// Кнопка 'Да'
		private Button getDeletionYes_Button()
		{
			return new Button(driver, By.xpath("//span[text() = 'Да']"));
		}
	}
	
	private Text getUserName()
	{
		return new Text(driver, By.xpath("//*[@class='log']/span"));
	}
	
	private class filtration_Elements
	{
		private Custom getSearch_Accordion()
		{
			return new Custom(driver, By.xpath("//div[@id='accordFilter']/h3"));
		}
		
		private Select getFiltrationName_Select()
		{
			return new Select(driver.findElement(By.xpath("//select[contains(@id, 'name')]")));
		}
		
		private TextInput getFiltrationValue_Input()
		{
			return new TextInput(driver, By.xpath("//input[contains(@id, 'value')]"));
		}
		
		private Button getSearch_Button()
		{
			return new Button(driver, By.id("buttonSearch"));
		}
	}
	
	private class Grid_Values
	{
		private String drugTradeName = "Тестовый препарат";     				    // Торговое название препарата
		private String drugOutputForm = "Таблетки";     							// Форма выпуска препарата
		private String packing = "5г";     											// Фасовка
		private String mnn = "111222333444";     									// Мнн
		private String editedActiveSubstance = "Тестинин";						    // Действующее вещество после редактирования
		private String atcCode = "111";	  											// АТС код
		private String atcGroup = "Тестовая";	  									// АТС группа
		private String manufactererName = "Производитель для препарата";	  		// Название производителя
		private String manufactererCountry = "Катар";	  							// Страна производителя
		private String declarer = "Заявитель для препарата";     					// Заявитель
		private String declarerCountry = "Катар";     								// Страна заявителя
		private String regDate = "02.01.2012";	  									// Дата регистрации
		private String regNum = "222";	  											// Номер 1-го РУ
		private String reRegDate = " ";	  											// Дата перерегистрации
		private String reRegNum = " ";	  											// Номер РУ
		private String orderNum = "333";	  										// Номер приказа
		private String endRegDate = "01.01.2014";	  							    // Срок действия регистрации
		private String drugCondition = "в стадии разработки";	  				    // Состояние по препарату
	}
}

