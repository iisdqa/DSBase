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
		
		// ������ ��������������
		new Main_Elements().getMarketingCoutry().selectByVisibleText("<������ ������>");
		
		// �������� ��������� �����
		waitForBlockStatus(new Main_Elements().new CountriesPopUp().getGridDownload_Div(), false);
		simpleWait(1);
		
		// ����� ������
		new Main_Elements().new CountriesPopUp().getCountryName().inputText(country);
		new Main_Elements().new CountriesPopUp().getCountrySearchButton().click();
		simpleWait(1);
		
		// �������� ��������� �����
		waitForBlockStatus(new Main_Elements().new CountriesPopUp().getGridDownload_Div(), false);
		simpleWait(1);
		
		// �������� �� ������ '������'(���� �������� � �������)
		Actions action = new Actions(driver);
		action.click((new Main_Elements().new CountriesPopUp().getQatarCell())).perform();
		simpleWait(2);
		
		// �������
		new Main_Elements().new CountriesPopUp().getCountryAcceptButton().click();
		simpleWait(2);
		
		// �������� ������ ������ � ���������� ������
		String chosenCountry = new Main_Elements().getMarketingCoutry().getFirstSelectedOption().getText();
		assertThat(chosenCountry, is(equalTo(country)));
	}
	
	public void mainFields_FillUp()
	{
		// ������ ��������������
		marketingCountry_Set();
		
	    // ������������� ���� ��������
		new Main_Elements().getBirthday().click();
		new Main_Elements().getBirthday().inputText(new Main_Elements().new Values().Birthday);
		
		// �������� ������������ ������ � ������ ��������� ��� ������ ���������
		new Main_Elements().getDeclarer().inputText(new Main_Elements().new Values().Declarer);
		simpleWait(3);
		new Main_Elements().getAutocompletedDeclarer().click();
		simpleWait(2);
		assertThat(new Main_Elements().getDeclarerCountry().getAttribute("value"), is(equalTo(new Main_Elements().new Values().DeclarerCountry)));
		assertThat(new Main_Elements().getDeclarerAdress().getAttribute("value"), is(equalTo(new Main_Elements().new Values().DeclarerAdress)));

		// �������� �������� ���������
		new Main_Elements().getTradeName().inputText(new Main_Elements().new Values().DrugTradeName);
		
		// ����� ������� ���������
		new Main_Elements().getOutputForm().inputText(new Main_Elements().new Values().DrugOutputForm);
		
		// �������
		new Main_Elements().getPacking().inputText(new Main_Elements().new Values().Packing);
		
		// ������� �������
		new Main_Elements().getLeaveCondition().inputText(new Main_Elements().new Values().DrugLeaveCondition);
		
		// ������� �������
		new Main_Elements().getInjectionWay().selectByVisibleText(new Main_Elements().new Values().InjectionWay);
		
		// ��������������� ��������
		new Main_Elements().getAdditionalSubstance().inputText(new Main_Elements().new Values().AdditionalSubstance);
		
		// ��� ���
		new Main_Elements().getAtcCode().inputText(new Main_Elements().new Values().AtcCode);
		
		// ��� ������
		new Main_Elements().getAtcGroup().inputText(new Main_Elements().new Values().AtcGroup);
		
		// ��� ������ ��� �����������
		new Main_Elements().getApplicationType().inputText(new Main_Elements().new Values().ApplicationType);
		
		// ���� �����������
		new Main_Elements().getRegistrationDate().click();
		new Main_Elements().getRegistrationDate().inputText(new Main_Elements().new Values().RegistrationDate);
		
		// � ��
		new Main_Elements().getRegistrationNumber().inputText(new Main_Elements().new Values().RegistrationNumber);
		
		// ���� ��������� ��
		new Main_Elements().getRegistrationEndDate().click();
		new Main_Elements().getRegistrationEndDate().inputText(new Main_Elements().new Values().RegistrationEndDate);
		
		// ��������� �� ���������
		new Main_Elements().getDrugCondition().selectByVisibleText(new Main_Elements().new Values().DrugCondition);
		
		// � �������
		new Main_Elements().getOrderNumber().inputText(new Main_Elements().new Values().OrderNumber);
		
		// �������� ������������ ��������������� ���� �������������� ���������� �� ���������������
		assertThat(new Main_Elements().getReRegPlannedDate().getAttribute("value"), is(equalTo(new Main_Elements().new Values().ReRegPlannedDate)));
	}
	//________________________________________________________________________________________________________________________________//
	
	
	//___________________________________________________________ ��� ________________________________________________________________//
	
	public void mnn_Add()
	{		
		// �������� ���-��� ���������� ���
		new Mnn_Elements().getAddButton().click();
		simpleWait(2);
		waitUntilUnblocked((new Mnn_Elements().getAddEditPopUp()));
		simpleWait(2);
		
		// ������ ���
		new Mnn_Elements().getMnnField().inputText(new Mnn_Elements().new Values().mnn);
		
		// ���������� ���
		new Mnn_Elements().getSaveButton().click();
		simpleWait(1);
		
		// �������� ��������� �����
		waitForBlockStatus(new Mnn_Elements().getGridDownload_Div(), false);
		simpleWait(1);
	}
	
	public void addedMnn_check()
	{
		// ����������� ��������� ��������
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  new Mnn_Elements().new Values().mnn,
										  ""};
		
		// �������� ��������� �������� �� �����
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(new Mnn_Elements().getMnnGridBody());
		
		// �������� �������� �����
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	//________________________________________________________________________________________________________________________________//
	
	
	//____________________________________________________ ������������� _____________________________________________________________//
	
	public void manufacturer_Add()
	{		
		// �������� ���-��� ���������� �������������
		new Manufacterer_Elements().getAddButton().click();
		simpleWait(2);
		waitUntilUnblocked((new Manufacterer_Elements().getAddEditPopUp()));
		simpleWait(2);
		
		// ������� ������������� + �������� ������������ ������ � ������ �������������
		new Manufacterer_Elements().getManufacturerField().inputText(new Manufacterer_Elements().new Values().manufactererName);
		simpleWait(2);
		new Manufacterer_Elements().getAutocompletedManufacturer().click();
		simpleWait(2);
		assertThat(new Manufacterer_Elements().getManufacturerCountryField().getAttribute("value"), is(equalTo(new Manufacterer_Elements().new Values().manufactererCountry)));
		assertThat(new Manufacterer_Elements().getManufacturerAdressField().getAttribute("value"), is(equalTo(new Manufacterer_Elements().new Values().manufactererAdress)));
		
		// ���������� �������������
		new Manufacterer_Elements().getSaveButton().click();
		simpleWait(1);
		
		// �������� ��������� �����
		waitForBlockStatus(new Manufacterer_Elements().getGridDownload_Div(), false);
		simpleWait(1);
	}
	
	public void addedManufacturer_check()
	{
		// ����������� ��������� ��������
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
				  						  new Manufacterer_Elements().new Values().manufactererName,
										  new Manufacterer_Elements().new Values().manufactererCountry,
										  new Manufacterer_Elements().new Values().manufactererAdress,
										  ""};
		
		// ����������� ���������� ��������
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(new Manufacterer_Elements().getManufacturerGridBody());
		
		// �������� �������� �����
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	//________________________________________________________________________________________________________________________________//

	
	//___________________________________________________ ����������� �������� _______________________________________________________//
	
	public void substance_Add()
	{		
		// �������� ���-��� ���������� ������������ ��������
		new Substance_Elements().getAddButton().click();
		simpleWait(2);
		waitUntilUnblocked((new Substance_Elements().getAddEditPopUp()));
		simpleWait(2);
		
		// ������ ����������� ��������
		new Substance_Elements().getSubstanceField().inputText(new Substance_Elements().new Values().substance);
		
		// ���������� ����������� ��������
		new Substance_Elements().getSaveButton().click();
		simpleWait(1);
	
		// �������� ��������� �����
		waitForBlockStatus(new Substance_Elements().getGridDownload_Div(), false);
		simpleWait(1);
	}
	
	public void addedSubstance_check()
	{
		// ����������� ��������� ��������
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  new Substance_Elements().new Values().substance,
										  ""};
		
		// ����������� ���������� ��������
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(new Substance_Elements().getSubstancesGridBody());
		
		// �������� �������� �����
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	//________________________________________________________________________________________________________________________________//
	
	
	//________________________________________________________ ��������� _____________________________________________________________//
	
	public void doc_Add()
	{		
		// �������� ���-��� ���������� ������������ ��������
		new Docs_Elements().getAddButton().click();
		simpleWait(2);
		waitUntilUnblocked((new Docs_Elements().getAddEditPopUp()));
		simpleWait(2);
		
		// ����
		new Docs_Elements().getDate_Field().click();
		simpleWait(1);
		new Docs_Elements().getDate_Field().inputText(new Docs_Elements().new Values().date);
		
		// �������� ���������
		new Docs_Elements().getName_Field().inputText(new Docs_Elements().new Values().name);
		
		// �������� ���������
		new Docs_Elements().getDescription_Field().inputText(new Docs_Elements().new Values().docDescription);
		
		// ��� ���������
		new Docs_Elements().getDocType_Field().selectByVisibleText(new Docs_Elements().new Values().docType);
		
		// ���������� ���� + �������� ����������� � ��������� ����
		new Docs_Elements().getFileUpload_Button().inputText(new Docs_Elements().new Values().fileWay);
		simpleWait(2);
		assertThat(new Docs_Elements().getFile_Field().getAttribute("value"), is(equalTo(new Docs_Elements().new Values().fileName)));
		
		// ������ �� ����
		new Docs_Elements().getFileLink_Field().inputText(new Docs_Elements().new Values().fileLink);
		
		// ���������� ����������� ��������
		new Docs_Elements().getSaveButton().click();
		simpleWait(1);
		
		// �������� ��������� �����
		waitForBlockStatus(new Docs_Elements().getGridDownload_Div(), false);
		simpleWait(1);
	}
	
	public void addedDoc_check()
	{
		// ����������� ��������� ��������
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
				  						  new Docs_Elements().new Values().date,
										  new Docs_Elements().new Values().name,
										  new Docs_Elements().new Values().docDescription,
										  new Docs_Elements().new Values().docType,
										  new Docs_Elements().new Values().fileLink,
										  "",
										  ""};
		
		// ����������� ���������� ��������
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(new Docs_Elements().getDocsGridBody());
		
		// �������� �������� �����
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	public void fileUnload_check()
	{
		new CustomMethods().new WorkWith_TextFiles().fileDownload_Check(new Docs_Elements().getFileDownloadButton(), "ForDocAdd.txt");
	}
	//________________________________________________________________________________________________________________________________//
	
	
	//___________________________________________________ ���������� _______________________________________________________//
	
	public void employee_Add()
	{		
		// �������� ���-��� ���������� ������������ ��������
		new Employee_Elements().getAddButton().click();
		simpleWait(2);
		waitUntilUnblocked((new Employee_Elements().getAddEditPopUp()));
		simpleWait(2);
		
		// ������ ���
		new Employee_Elements().getFioField().inputText(new Employee_Elements().new Values().fio);
		
		// ������ ���������
		new Employee_Elements().getPositionField().inputText(new Employee_Elements().new Values().position);
		
		// ���������� ����������� ��������
		new Employee_Elements().getSaveButton().click();
		simpleWait(1);
		
		// �������� ��������� �����
		waitForBlockStatus(new Employee_Elements().getGridDownload_Div(), false);
		simpleWait(1);
	}
	
	public void addedEmployee_check()
	{
		// ����������� ��������� ��������
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  new Employee_Elements().new Values().fio,
										  new Employee_Elements().new Values().position,
										  ""};
		
		// ����������� ���������� ��������
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(new Employee_Elements().getEmployeeGridBody());
		
		// �������� �������� �����
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
		// '������ ��������������'
		private Select getMarketingCoutry()
		{
			return new Select(driver.findElement(By.id("Country_drug_identification_marketing_country_id")));
		}
		
		// '������������� ���� ��������'
		private TextInput getBirthday()
		{
			return new TextInput(driver, By.id("World_drug_identification_drug_birthday"));
		}
		
		// '���������'
		private TextInput getDeclarer()
		{
			return new TextInput(driver,By.id("Dic_declarer_declarer"));
		}
		
		// '���������' �����������
		private Text getAutocompletedDeclarer()
		{
			return new Text(driver,By.xpath("//strong[contains(text(), '" + new Values().Declarer + "')]"));
		}
		
		// '������ ���������'
		private TextInput getDeclarerCountry()
		{
			return new TextInput(driver,By.id("Dic_declarer_declarer_country"));
		}
		
		// '������ ���������'
		private TextInput getDeclarerAdress()
		{
			return new TextInput(driver, By.id("Dic_declarer_declarer_address"));
		}
		
		// '�������� �������� ���������'
		private TextInput getTradeName()
		{
			return new TextInput(driver, By.id("Drug_registration_trade_name"));
		}
		
		// '����� ������� ���������'
		private TextInput getOutputForm()
		{
			return new TextInput(driver, By.id("Drug_registration_form"));
		}
		
		// '�������'
		private TextInput getPacking()
		{
			return new TextInput(driver, By.id("Drug_registration_packing"));
		}
		
		// '������� �������'
		private TextInput getLeaveCondition()
		{
			return new TextInput(driver, By.id("Drug_registration_conditions"));
		}
		
		// '������ ��������'
		private Select getInjectionWay()
		{
			return new Select(driver.findElement(By.id("Drug_registration_introduction_mode")));
		}
		
		// '��������������� ��������'
		private TextInput getAdditionalSubstance()
		{
			return new TextInput(driver, By.id("Drug_registration_excipient"));
		}
		
		// 'ATC ���'
		private TextInput getAtcCode()
		{
			return new TextInput(driver, By.id("Drug_registration_atc_raw"));
		}
		
		// 'ATC ������'
		private TextInput getAtcGroup()
		{
			return new TextInput(driver, By.id("Drug_registration_cf_group"));
		}
		
		// '��� ������ ��� �����������'
		private TextInput getApplicationType()
		{
			return new TextInput(driver, By.id("Country_drug_identification_application_type"));
		}
		
		// '���� �����������'
		private TextInput getRegistrationDate()
		{
			return new TextInput(driver, By.id("Country_drug_identification_first_reg_date"));
		}
		
		// '� ��'
		private TextInput getRegistrationNumber()
		{
			return new TextInput(driver, By.id("Country_drug_identification_first_reg_number"));
		}
		
		// '���� ��������� ��'
		private TextInput getRegistrationEndDate()
		{
			return new TextInput(driver, By.id("Drug_registration_reg_date_end"));
		}
		
		// '��������� �� ���������'
		private Select getDrugCondition()
		{
			return new Select(driver.findElement(By.id("Drug_registration_dic_drug_state_id")));
		}
		
		// '� �������'
		private TextInput getOrderNumber()
		{
			return new TextInput(driver, By.id("Drug_registration_order_number"));
		}
		
		// '���� �������������� ���������� �� ���������������' -> '���������������'
		private TextInput getReRegPlannedDate()
		{
			return new TextInput(driver, By.id("planned_date_reregistration"));
		}
		
		// ������ '���������'
		private Button getSaveButton()
		{
			return new Button(driver, By.xpath("//input[contains(@onclick,'Save()')]"));
		}
		
		private class Values
		{
			private String marketingCountry = "�����";     								// ������������� ���� ��������
			private String Birthday = "01.01.2012";     								// ������������� ���� ��������
			private String Declarer = "��������� ��� ���������";     					// ���������
			private String DeclarerCountry = "�����";     								// ������ ���������
			private String DeclarerAdress = "��. ��������, �. 2";     					// ����� ���������
			private String DrugTradeName = "�������� ��������";     				    // �������� �������� ���������
			private String DrugOutputForm = "��������";     							// ����� ������� ���������
			private String Packing = "5�";     											// �������
			private String DrugLeaveCondition = "���";     							    // ������� �������
			private String InjectionWay = "� ���";     									// ������ ��������
			private String AdditionalSubstance = "����";     			        		// ��������������� ��������
			private String AtcCode = "111";     										// ��� ���
			private String AtcGroup = "��������";     									// ��� ������
			private String ApplicationType = "��������";     				    		// ��� ������ ��� �����������
			private String RegistrationDate = "02.01.2012";     	    			    // ���� �����������
			private String RegistrationNumber = "222";     							    // � ��
			private String RegistrationEndDate = "01.01.2014";     					    // ���� ��������� �����������
			private String DrugCondition = "� ������ ����������";     				    // ��������� �� ���������
			private String OrderNumber = "333";     							    	// � �������		
			private String ReRegPlannedDate = "01.01.2013";     						// ��������������� ���� �������������� ���������� �� ���������������	
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
			
			// "������������"
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
	
	// �������� ����� '���'
	private class Mnn_Elements
	{
		// ������ ����������
		private Button getAddButton()
		{
			return new Button(driver, By.xpath("//input[@onclick='AddEditMnn()']"));
		}
		
		// ���-�� ����������
		private Custom getAddEditPopUp()
		{
			return new Custom(driver, By.xpath("//span[text() = '" + new Values().addEditPopUpName + "']"));
		}
		
		// "������������"
		private Custom getGridDownload_Div()
		{
			return new Custom(driver, By.id("load_list_Mnn"));
		}
		
		// <tbody> �����
		private WebElement getMnnGridBody()
		{
			return driver.findElement(By.xpath("//*[@id='list_Mnn']/tbody"));
		}
		
		// ���
		private TextInput getMnnField()
		{
			return new TextInput(driver, By.id("mnn_text"));
		}
		
		// ������ '���������'
		private Button getSaveButton()
		{
			return new Button(driver, By.xpath("//input[contains(@onclick,'SaveMnn()')]"));
		}
		
		private class Values
		{
			private String addEditPopUpName = "�������� ���";						// �������� ���-��� ����������/�������������� ���
			private String mnn = "11122233344";	  									// ���
		}
	}
	//________________________________________________________________________________________________________________________________//
	
	// �������� ����� '������������� ���������'
	private class Manufacterer_Elements
	{
		// ������ ����������
		private Button getAddButton()
		{
			return new Button(driver, By.xpath("//input[@onclick='AddEditManufacturer()']"));
		}
		
		// ���-�� ����������
		private Custom getAddEditPopUp()
		{
			return new Custom(driver, By.xpath("//span[text() = '" + new Values().addEditPopUpName + "']"));
		}
		
		// "������������"
		private Custom getGridDownload_Div()
		{
			return new Custom(driver, By.id("load_list_manufacturer"));
		}
		
		// <tbody> �����
		private WebElement getManufacturerGridBody()
		{
			return driver.findElement(By.xpath("//*[@id='list_manufacturer']/tbody"));
		}
		
		// ������������� ��������
		private TextInput getManufacturerField()
		{
			return new TextInput(driver, By.id("manufacturer"));
		}
		
		// '���������' �����������
		private Text getAutocompletedManufacturer()
		{
			return new Text(driver,By.xpath("//strong[contains(text(), '" + new Values().manufactererName + "')]"));
		}
		
		// ������ �������������
		private TextInput getManufacturerCountryField()
		{
			return new TextInput(driver, By.id("manufacturer_country"));
		}
		
		// ����� �������������
		private TextInput getManufacturerAdressField()
		{
			return new TextInput(driver, By.id("manufacturer_address"));
		}
		
		// ������ '���������'
		private Button getSaveButton()
		{
			return new Button(driver, By.xpath("//input[contains(@onclick,'OnSave()')]"));
		}
		
		private class Values
		{
			private String addEditPopUpName = "�������� ������������� ���������";   // �������� ���-��� ����������/�������������� �������������
			private String manufactererName = "������������� ��� ���������";	  	// �������� �������������
			private String manufactererCountry = "�����";	  						// ������ �������������
			private String manufactererAdress = "��. ��������, �. 1";	  			// ����� �������������
		}
	}
	
	//________________________________________________________________________________________________________________________________//
	
	// �������� ����� '����������� ��������'
	private class Substance_Elements
	{
		// ������ ����������
		private Button getAddButton()
		{
			return new Button(driver, By.xpath("//input[@onclick='AddEditSubstances()']"));
		}
		
		// ���-�� ����������
		private Custom getAddEditPopUp()
		{
			return new Custom(driver, By.xpath("//span[text() = '" + new Values().addEditPopUpName + "']"));
		}
		
		// "������������"
		private Custom getGridDownload_Div()
		{
			return new Custom(driver, By.id("load_list_Substances"));
		}
		
		// <tbody> �����
		private WebElement getSubstancesGridBody()
		{
			return driver.findElement(By.xpath("//*[@id='list_Substances']/tbody"));
		}
		
		// ����������� ��������
		private TextInput getSubstanceField()
		{
			return new TextInput(driver, By.id("active_substance"));
		}
		
		// ������ '���������'
		private Button getSaveButton()
		{
			return new Button(driver, By.xpath("//input[contains(@onclick,'SaveSubstances()')]"));
		}
		
		private class Values
		{
			private String addEditPopUpName = "�������� ����������� ��������";		// �������� ���-��� ����������/�������������� ������������ ��������
			private String substance = "����";	  									// ����������� ��������
		}
	}
	//________________________________________________________________________________________________________________________________//
	
	
	// �������� ����� '���������'
		private class Docs_Elements
		{
			// ������ ����������
			private Button getAddButton()
			{
				return new Button(driver, By.xpath("//input[@onclick='AddEditFile()']"));
			}
			
			// ���-�� ����������
			private Custom getAddEditPopUp()
			{
				return new Custom(driver, By.xpath("//span[text() = '" + new Values().addEditPopUpName + "']"));
			}
			
			// "������������"
			private Custom getGridDownload_Div()
			{
				return new Custom(driver, By.id("load_list_file_load"));
			}
			
			// <tbody> �����
			private WebElement getDocsGridBody()
			{
				return driver.findElement(By.xpath("//*[@id='list_file_load']/tbody"));
			}
			
			// ����
			private TextInput getDate_Field()
			{
				return new TextInput(driver, By.id("Date"));
			}
			
			// �������� ���������
			private TextInput getName_Field()
			{
				return new TextInput(driver, By.id("Title"));
			}
			
			// �������� ���������
			private TextInput getDescription_Field()
			{
				return new TextInput(driver, By.id("Description"));
			}
			
			// �������� ���������
			private Select getDocType_Field()
			{
				return new Select(driver.findElement(By.id("TypeId")));
			}
			
			// ����
			private TextInput getFile_Field()
			{
				return new TextInput(driver, By.id("FileName"));
			}
			
			private TextInput getFileUpload_Button()
			{
				return new TextInput(driver, By.id("file_source"));
			}
			
			// ������ �� ����
			private TextInput getFileLink_Field()
			{
				return new TextInput(driver, By.id("FileLink"));
			}
			
			// ������ '���������'
			private Button getSaveButton()
			{
				return new Button(driver, By.id("save_file_btn"));
			}
			
			// ������ �������� �����
			private Button getFileDownloadButton()
			{
				return new Button(driver, By.xpath("//td[@aria-describedby='list_file_load_load']/input"));
			}
			
			private class Values
			{
				private String addEditPopUpName = "���������� ���������";				// �������� ���-��� ����������/�������������� ���������
				private String date = "05.01.2012";	  									// ����
				private String name = "���";						     				// �������� ���������
				private String docDescription = "��������";						    	// �������� ���������
				private String docType = "����������";						     		// ����������
				private String fileWay = "C:\\Selenium_TestData\\Other\\ForDocAdd.txt";	// ������ �� ����(��������)
				private String fileName = "ForDocAdd.txt";								// �������� �����
				private String fileLink = "www.getFile.com/get";			     		// ������ �� ����
			}
		}
		
		// �������� ����� '����������'
		private class Employee_Elements
		{
			// ������ ����������
			private Button getAddButton()
			{
				return new Button(driver, By.xpath("//input[@onclick='AddEditPerson()']"));
			}
			
			// ���-�� ����������
			private Custom getAddEditPopUp()
			{
				return new Custom(driver, By.xpath("//span[text() = '" + new Values().addEditPopUpName + "']"));
			}
			
			// "������������"
			private Custom getGridDownload_Div()
			{
				return new Custom(driver, By.id("load_list_staff"));
			}
			
			// <tbody> �����
			private WebElement getEmployeeGridBody()
			{
				return driver.findElement(By.xpath("//*[@id='list_staff']/tbody"));
			}
			
			// ���
			private TextInput getFioField()
			{
				return new TextInput(driver, By.id("full_name"));
			}
			
			// ����� ���������������
			private TextInput getPositionField()
			{
				return new TextInput(driver, By.id("responsibilities"));
			}
			
			// ������ '���������'
			private Button getSaveButton()
			{
				return new Button(driver, By.xpath("//input[contains(@onclick,'SavePerson()')]"));
			}
			
			private class Values
			{
				private String addEditPopUpName = "�������� �����������, ������� ����� ��";		// �������� ���-��� ����������/�������������� ����������
				private String fio = "������������ ����� ����������";	  						// ���
				private String position = "����������";						     				// ����� ���������������
			}
		}
		//________________________________________________________________________________________________________________________________//
}