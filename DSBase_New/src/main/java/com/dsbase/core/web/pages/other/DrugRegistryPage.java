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
		// ����� ������� ��� ������������
		String actualUserName = getUserName().getText();
		
		// �������� ��������� ���������� �������� � ��������� ��������
		assertThat(actualUserName, is(equalTo(expectedUserName)));
	}
	
	public void WaitForPageReady()
	{
		// �������� ��������� �����
		waitForBlockStatus(getGridDownload_Div(), false);
		simpleWait(1);
	}
	
	public void drug_Search()
	{
		// ������� ���������
		new filtration_Elements().getSearch_Accordion().click();
		simpleWait(2);
		
		// ������� �������� � ����� ��������
		new filtration_Elements().getFiltrationValue_Input().inputText("�������� ��������");
		new filtration_Elements().getSearch_Button().click();
		simpleWait(2);
		
		waitForBlockStatus(getGridDownload_Div(), false);
		simpleWait(1);
	}
	
	public void foundDrug_Check()
	{
		// ����������� ���������� � ����������
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
		
		
		// ����������� ������� ��������� ��������
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"1", drugTradeName, drugOutputForm, packing, mnn, editedActiveSubstance, atcCode, atcGroup, manufactererName,
										  manufactererCountry, declarer, declarerCountry, regDate, regNum, reRegDate, reRegNum, orderNum, endRuDate, drugCondition, ""};
		
		// �������� �������� �� �����
		String[][] ActualValues = new CustomMethods(). new Grid().GetAllRows(getGridBody());
		
		// �������� �������� �����
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	public void excelUnload_Check()
	{
		String expectedFileName = "RegisterDrugs_" + new CustomMethods().getCurrentDate() + ".xls";
		new CustomMethods().new WorkWith_TextFiles().fileDownload_Check(getExcelButton(), expectedFileName);
	}
	
	public void wholeFiltration_Check()
	{	
		// ������� ���������
		new filtration_Elements().getSearch_Accordion().click();
		simpleWait(2);
		
		// ����������� �������� ��� ����������
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
		
		// ����������� ���������� �������� � ���������� ��������� ����������
		int namesCount = nameValues.length;
		int optionsCount = new filtration_Elements().getFiltrationName_Select().getOptions().size();
		
		// �������� ����������� ���������� �������� � ���������� ��������� ����������
		if(optionsCount != namesCount)
		{
    		// ������� ������
    		System.err.println("������ ��� �������� �������� ����� � Excel. ���������� ��������� �������� != ���������� ��������� ����������." +
 		   		   			   "\r\n��������� ���������� = " + namesCount + 
 		   		   			   "\r\n���������� ��������� ������ = " + optionsCount);
    		Assert.fail();
		}
		
		// ���������� ��������� ����������
		for(int i=0; i < optionsCount; i++)
		{	
			// ������� �������� � ����� ��������
			new filtration_Elements().getFiltrationName_Select().selectByIndex(i);
			new filtration_Elements().getFiltrationValue_Input().clear();
			new filtration_Elements().getFiltrationValue_Input().inputText(nameValues[i]);
			new filtration_Elements().getSearch_Button().click();
			simpleWait(2);
			
			waitForBlockStatus(getGridDownload_Div(), false);
			simpleWait(1);
			
		// �������� ��������(�����) �� �����
		String[][] TopPartValues = new CustomMethods(). new WorkWith_Excel().GetHeadPart_ForExcel(getTopGridBody());

		// �������� ��������(������) �� �����
		String[][] GridValues = new CustomMethods(). new WorkWith_Excel().GetAllRows_ForExcel(getGridBody());
		
		// ����� ��� ������� � ����
		String[][] ExpectedValues = new String[TopPartValues.length + GridValues.length][];
		for(int j = 0; j < TopPartValues.length; j++) ExpectedValues[j] = TopPartValues[j]; 
		for(int j = 0; j < GridValues.length; j++) ExpectedValues[TopPartValues.length + j] = GridValues[j]; 
		
		String expectedFileName = "RegisterDrugs_" + new CustomMethods().getCurrentDate() + ".xls";
		String[][] ActualValues = new CustomMethods().new WorkWith_Excel().get_ExcelValues(getExcelButton(), expectedFileName);
		
		// �������� �������� �����
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
		}
	}
	
	public void foundBeforeDeletionDrug_Check()
	{
		// ����������� ���������� � ����������
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
		
		
		// ����������� ������� ��������� ��������
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"1", drugTradeName, drugOutputForm, packing, mnn, editedActiveSubstance, atcCode, atcGroup, manufactererName,
										  manufactererCountry, declarer, declarerCountry, regDate, regNum, reRegDate, reRegNum, orderNum, endRuDate, drugCondition, ""};
		
		// �������� �������� �� �����
		String[][] ActualValues = new CustomMethods(). new Grid().GetAllRows(getGridBody());
		
		// �������� �������� �����
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	public void drug_Delete()
	{
		// �������� ���-��� �������� '���������'
		new Deletion_PopUp().getDelete_Button().click();
		simpleWait(2);
		waitUntilUnblocked((new Deletion_PopUp().getDeletion_PopUp()));
		simpleWait(2);
			
		// ������������� �������� ���������
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
		// �������� ���������� �������� � ����� '���������� ��������'
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
		// ����� �� �������
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
	
	// "������������"
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
		// ������ ��������
		private Button getDelete_Button()
		{
			return new Button(driver, By.xpath("//td[@aria-describedby='list_search_del']/input"));
		}
		
		// ���-�� ��������
		private Custom getDeletion_PopUp()
		{
			return new Custom(driver, By.id("attention_delete"));
		}
		
		// ������ '��'
		private Button getDeletionYes_Button()
		{
			return new Button(driver, By.xpath("//span[text() = '��']"));
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
		private String drugTradeName = "�������� ��������";     				    // �������� �������� ���������
		private String drugOutputForm = "��������";     							// ����� ������� ���������
		private String packing = "5�";     											// �������
		private String mnn = "111222333444";     									// ���
		private String editedActiveSubstance = "��������";						    // ����������� �������� ����� ��������������
		private String atcCode = "111";	  											// ��� ���
		private String atcGroup = "��������";	  									// ��� ������
		private String manufactererName = "������������� ��� ���������";	  		// �������� �������������
		private String manufactererCountry = "�����";	  							// ������ �������������
		private String declarer = "��������� ��� ���������";     					// ���������
		private String declarerCountry = "�����";     								// ������ ���������
		private String regDate = "02.01.2012";	  									// ���� �����������
		private String regNum = "222";	  											// ����� 1-�� ��
		private String reRegDate = " ";	  											// ���� ���������������
		private String reRegNum = " ";	  											// ����� ��
		private String orderNum = "333";	  										// ����� �������
		private String endRegDate = "01.01.2014";	  							    // ���� �������� �����������
		private String drugCondition = "� ������ ����������";	  				    // ��������� �� ���������
	}
}

