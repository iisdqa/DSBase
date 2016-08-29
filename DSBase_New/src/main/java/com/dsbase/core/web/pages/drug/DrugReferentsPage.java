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
		// �������� ��������� �����
		simpleWait(1);
		waitForBlockStatus(getGridDownload_Div(), false);
		
		// ����������� ��������� ��������
		String activeSubstance = new Values().activeSubstance;								// ����������� ��������
		
		// ������ �������� � ������
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  "",
										  activeSubstance,
										  " ",
										  " ",
										  " ",
										  "",
										  ""};
		
		// �������� ��������� �������� �� �����
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(getGridBody());
		
		// �������� �������� �����
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
		
		// �������� ���������� ������ ��������� � ��������
		new CustomMethods().elementExistenceCheck(getView_Button(), false);
		new CustomMethods().elementExistenceCheck(getDelete_Button(1), false);
	}
	
	public void autoReferent_Edit()
	{
		// �������� ��������������
		getEdit_Button(1).click();
		waitUntilUnblocked(new AddEditView_PopUp().getAddEdit_PopUp(new Values().editPopUp_Name));
	
		// ������ ������ ���������(��� �������� ���� �����������)
		new AddEditView_PopUp().getSave_Button().click();
		simpleWait(1);
		
		// �������� ����� ����������� ������� + �������� ������� ���-�����
		activeSubstance_Grid_Check();
		new CustomMethods().new Attribute_Checkers().checkBoxAttribute_Check(new AddEditView_PopUp().getSelect_CheckBox(), "checked", true);
		
		// ������ ������������ ������ + ������ ���-���� � ������ ������ ���������(��� �������� �����������)
		new AddEditView_PopUp().getSelect_CheckBox().click();
		new AddEditView_PopUp().getLink_Field().inputText("s");
		new AddEditView_PopUp().getSave_Button().click();
		simpleWait(1);
		new AddEditView_PopUp().getSave_Button().click();
		simpleWait(1);
		
		// �������� �� ������� �����������
		new CustomMethods().elementExistenceCheck(new AddEditView_PopUp().getTradeName_Validator(), true);
		new CustomMethods().elementExistenceCheck(new AddEditView_PopUp().getOutForm_Validator(), true);
		new CustomMethods().elementExistenceCheck(new AddEditView_PopUp().getLink_Validator(), true);
		new CustomMethods().elementExistenceCheck(new AddEditView_PopUp().getSubstance_Validator(), true);
		
		// �������� ��������
		new AddEditView_PopUp().getTradeName_Field().inputText(new Values().firstTradeName);
		
		// ����� �������
		new AddEditView_PopUp().getOutForm_Field().inputText(new Values().outForm);
		
		// �������������
		new AddEditView_PopUp().getManufacturer_Field().inputText(new Values().manufacturer);
		
		// ������ �� ���� ���. ��
		new AddEditView_PopUp().getLink_Field().clear();
		new AddEditView_PopUp().getLink_Field().inputText(new Values().link);
		
		// ���������� ���-���� ��� ������������ ��������
		new AddEditView_PopUp().getSelect_CheckBox().click();
		simpleWait(1);
		
		// ���������
		new AddEditView_PopUp().getSave_Button().click();
		simpleWait(2);
		waitForBlockStatus(getGridDownload_Div(), false);
	}
	
	public void edited_AutoReferent_Check()
	{
		// �������� ��������� �����
		simpleWait(1);
		waitForBlockStatus(getGridDownload_Div(), false);
		
		// ����������� ��������� ��������
		String activeSubstance = new Values().activeSubstance;								// ����������� ����������
		String firstTradeName = new Values().firstTradeName;								// ����������� ��������
		String outForm = new Values().outForm;												// ����� �������
		String manufacturer = new Values().manufacturer;									// �������������
		String link = new Values().link;													// ������ �� ���� ���. ��
		
		// ������ �������� � ������
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  "",
										  activeSubstance,
										  firstTradeName,
										  outForm,
										  manufacturer,
										  link,
										  ""};
		
		// �������� ��������� �������� �� �����
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(getGridBody());
		
		// �������� �������� �����
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
		
		// �������� ������� ������
		new CustomMethods().elementExistenceCheck(getLink_Link(), true);
	}
	
	public void autoReferent_View()
	{
		// �������� ��������
		getView_Button().click();
		waitUntilUnblocked(new AddEditView_PopUp().getAddEdit_PopUp(new Values().viewPopUp_Name));
		simpleWait(1);
		
		// �������� ��������
		assertThat(new AddEditView_PopUp().getTradeName_Field().getAttribute("value"), is(equalTo(new Values().firstTradeName)));
		new CustomMethods().new Attribute_Checkers().textInput_Attribute_Check(new AddEditView_PopUp().getTradeName_Field(), "readonly", true);
		
		// ����� �������
		assertThat(new AddEditView_PopUp().getOutForm_Field().getAttribute("value"), is(equalTo(new Values().outForm)));
		new CustomMethods().new Attribute_Checkers().textInput_Attribute_Check(new AddEditView_PopUp().getOutForm_Field(), "readonly", true);
		
		// �������������
		assertThat(new AddEditView_PopUp().getManufacturer_Field().getAttribute("value"), is(equalTo(new Values().manufacturer)));
		new CustomMethods().new Attribute_Checkers().textInput_Attribute_Check(new AddEditView_PopUp().getManufacturer_Field_by_xpath(), "readonly", true);
		
		// ������ �� ���� ���. ��
		assertThat(new AddEditView_PopUp().getLink_Link().getText(), is(equalTo(new Values().link)));
		new CustomMethods().elementExistenceCheck(new AddEditView_PopUp().getLink_Field_by_xpath(), false);
		
		// �������� ����� ����������� ������� + �������� ������� � ������������� ���-�����
		activeSubstance_Grid_CheckAtView();
		new CustomMethods().new Attribute_Checkers().checkBoxAttribute_Check(new AddEditView_PopUp().getSelect_CheckBox_forView(), "checked", true);
		new CustomMethods().new Attribute_Checkers().checkBoxAttribute_Check(new AddEditView_PopUp().getSelect_CheckBox_forView(), "disabled", true);
		
		// ������� ��������
		new AddEditView_PopUp().getCancel_Button().click();
		simpleWait(2);
	}
	
	public void handReferent_Add()
	{
		// �������� ��������������
		getAdd_Button().click();
		waitUntilUnblocked(new AddEditView_PopUp().getAddEdit_PopUp(new Values().addPopUp_Name));
		
		// �������� ����� ����������� �������
		activeSubstance_Grid_Check();
			
		// �������� ��������
		new AddEditView_PopUp().getTradeName_Field().inputText(new Values().secondTradeName);
		
		// ����� �������
		new AddEditView_PopUp().getOutForm_Field().inputText(new Values().outForm);
		
		// �������������
		new AddEditView_PopUp().getManufacturer_Field().inputText(new Values().manufacturer);
		
		// ������ �� ���� ���. ��
		new AddEditView_PopUp().getLink_Field().inputText(new Values().link);
		
		// ���������� ���-���� ��� ������������ ��������
		new AddEditView_PopUp().getSelect_CheckBox().click();
		simpleWait(1);
		
		// ���������
		new AddEditView_PopUp().getSave_Button().click();
		simpleWait(2);
		waitForBlockStatus(getGridDownload_Div(), false);
	}
	
	public void added_HandReferent_Check()
	{
		// �������� ��������� �����
		simpleWait(1);
		waitForBlockStatus(getGridDownload_Div(), false);
		
		// ����������� ��������� ��������
		String activeSubstance = new Values().activeSubstance;								// ����������� ����������
		String firstTradeName = new Values().firstTradeName;								// ����������� ��������
		String secondTradeName = new Values().secondTradeName;								// ����������� ��������(��� ������� ����
		String outForm = new Values().outForm;												// ����� �������
		String manufacturer = new Values().manufacturer;									// �������������
		String link = new Values().link;													// ������ �� ���� ���. ��
		
		// ������ �������� � ������
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
		
		// �������� ��������� �������� �� �����
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(getGridBody());
		
		// �������� �������� �����
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	public void handReferent_Edit()
	{
		// �������� ��������������
		getEdit_Button(2).click();
		waitUntilUnblocked(new AddEditView_PopUp().getAddEdit_PopUp(new Values().editPopUp_Name));
				
		// �������������
		new AddEditView_PopUp().getManufacturer_Field().clear();
		new AddEditView_PopUp().getManufacturer_Field().inputText(new Values().editedManufacturer);
		
		// ���������
		new AddEditView_PopUp().getSave_Button().click();
		simpleWait(2);
		waitForBlockStatus(getGridDownload_Div(), false);
	}
	
	public void edited_HandReferent_Check()
	{
		// �������� ��������� �����
		simpleWait(1);
		waitForBlockStatus(getGridDownload_Div(), false);
		
		// ����������� ��������� ��������
		String activeSubstance = new Values().activeSubstance;								// ����������� ����������
		String firstTradeName = new Values().firstTradeName;								// ����������� ��������
		String secondTradeName = new Values().secondTradeName;								// ����������� ��������(��� ������� ����)
		String outForm = new Values().outForm;												// ����� �������
		String manufacturer = new Values().manufacturer;									// �������������
		String editedManufacturer = new Values().editedManufacturer;								// �������������(��� ������� ����)
		String link = new Values().link;													// ������ �� ���� ���. ��
		
		// ������ �������� � ������
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
		
		// �������� ��������� �������� �� �����
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(getGridBody());
		
		// �������� �������� �����
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
		// ����� �� �������
		return new CommonActions().userOut(driver);
	}
	//________________________________________________________________________________________________________________________________//
	
	
	private void activeSubstance_Grid_Check()
	{
		// ����������� ��������� ��������
		String activeSubstance = new Values().activeSubstance;								// ����������� ����������
		
		// ������ �������� � ������
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  activeSubstance};
		
		// �������� ��������� �������� �� �����
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(new AddEditView_PopUp().getSubstance_GridBody());
		
		// �������� �������� �����
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	private void activeSubstance_Grid_CheckAtView()
	{
		// ����������� ��������� ��������
		String activeSubstance = new Values().activeSubstance;								// ����������� ����������
		
		// ������ �������� � ������
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  activeSubstance};
		
		// �������� ��������� �������� �� �����
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(new AddEditView_PopUp().getSubstance_GridBody_forView());
		
		// �������� �������� �����
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	// ������ ����������
	private Button getAdd_Button()
	{
		return new Button(driver, By.xpath("//input[contains(@onclick, 'AddEditReferenceDrug')]"));
	}
	
	// ���� �������
	private WebElement getGridBody()
	{
		return driver.findElement(By.xpath("//*[@id='list_referenceDrug']/tbody"));
	}
	
	// "������������"
	private Custom getGridDownload_Div()
	{
		return new Custom(driver, By.id("load_list_referenceDrug"));
	}
	
	// ������ ���������
	private WebElement getView_Button()
	{
		return driver.findElement(By.xpath("//td[@aria-describedby='list_referenceDrug_view']/input"));
	}
	
/*	// ������ ��������������
	private Button getEdit_Button()
	{
		return new Button(driver, By.xpath("//td[@aria-describedby='list_referenceDrug_edit']/input"));
	}*/
	
	// ������ ��������������
	private Button getEdit_Button(int ButtonNumber)
	{
		return new Button(driver, By.xpath("(//td[@aria-describedby='list_referenceDrug_edit']/input)[" + Integer.toString(ButtonNumber) + "]"));
	}
	
	// �������� ��� ���������� � ��������
	private class Values
	{
		private String addPopUp_Name = "���������� ������������ ��";	  				// �������� ���-��� ����������
		private String editPopUp_Name = "��������� ������������ ��";	  				// �������� ���-��� ��������������
		private String viewPopUp_Name = "�������� ������������ ��";	  					// �������� ���-��� ���������
		
		private String activeSubstance = "��������";	  						  		// ����������� ��������
		private String firstTradeName = "������";										// ��������� ��������
		private String secondTradeName = "������";										// ��������� ��������(2 ���)
		private String outForm = "��������";											// ����� �������	
		private String manufacturer = "��������";										// �������������
		private String editedManufacturer = "�������� 2";								// �������������(2 ���)
		private String link = "http://first_drug.com";									// ������ 
	}
	
	// ������ � �����
	private WebElement getLink_Link()
	{
		return driver.findElement(By.xpath("//td[@aria-describedby='list_referenceDrug_UrlForRefDrug']/a"));
	}
	
	// ������ ��������
	private WebElement getDelete_Button(int ButtonNumber)
	{
		return driver.findElement(By.xpath("(//td[@aria-describedby='list_referenceDrug_del']/input)[" + Integer.toString(ButtonNumber) + "]"));
	}
	
	private class AddEditView_PopUp
	{
		// ���-�� ����������, �������������� � ���������
		private Custom getAddEdit_PopUp(String popUp_Name)
		{
			return new Custom(driver, By.xpath("//span[text() = '" + popUp_Name + "']"));
		}
		
		// �������� ��������
		private TextInput getTradeName_Field()
		{
			return new TextInput(driver, By.id("TradeName"));
		}
		 
		// �������� ��������(���������)
		private WebElement getTradeName_Validator()
		{
			return driver.findElement(By.id("TradeName-error"));
		}
		
		// ����� �������
		private TextInput getOutForm_Field()
		{
			return new TextInput(driver, By.id("ReleaseForm"));
		}
		
		// ����� �������(���������)
		private WebElement getOutForm_Validator()
		{
			return driver.findElement(By.id("ReleaseForm-error"));
		}
		
		// �������������
		private TextInput getManufacturer_Field()
		{
			return new TextInput(driver, By.id("Manufacturer"));
		}
		
		// �������������(��� �������� ���������)
		private TextInput getManufacturer_Field_by_xpath()
		{
			return new TextInput(driver, By.xpath("//input[@id='Manufacturer' and @type='text']"));
		}
		
		// ������ �� ���� ���. ��
		private TextInput getLink_Field()
		{
			return new TextInput(driver, By.id("UrlForRefDrug"));
		}
		
		// �������������(��� �������� ���������)
		private WebElement getLink_Field_by_xpath()
		{
			return driver.findElement(By.xpath("//input[@id='UrlForRefDrug' and @type='hidden']"));
		}
		
		// ������ �� ���� ���. ��
		private Link getLink_Link()
		{
			return new Link(driver, By.xpath("//a[@title='"+ new Values().link +"']"));
		}
		
		// ������ �� ���� ���. ��(���������)
		private WebElement getLink_Validator()
		{
			return driver.findElement(By.id("UrlForRefDrug-error"));
		}
		
		// ���� �������
		private WebElement getSubstance_GridBody()
		{
			return driver.findElement(By.xpath("//*[@id='list_activSubs']/tbody"));
		}
		
		// ���� �������(��� �������� ���������)
		private WebElement getSubstance_GridBody_forView()
		{
			return driver.findElement(By.xpath("//*[@id='list_activSubs_']/tbody"));
		}
		
		// ���-���� ������ ������������ ��������
		private CheckBox getSelect_CheckBox()
		{
			return new CheckBox(driver, By.xpath("//td[@aria-describedby='list_activSubs_SelectFlag']/input"));
		}
		
		// ���-���� ������ ������������ ��������
		private CheckBox getSelect_CheckBox_forView()
		{
			return new CheckBox(driver, By.xpath("//td[@aria-describedby='list_activSubs__SelectFlag']/input"));
		}
		
		// ����������� ��������(���������)
		private WebElement getSubstance_Validator()
		{
			return driver.findElement(By.id("ActiveSubsId-error"));
		}
		
		// ������ ����������
		private Button getSave_Button()
		{
			return new Button(driver, By.id("save_btn"));
		}
		
		// ������ ������
		private Button getCancel_Button()
		{
			return new Button(driver, By.xpath("//span[text() = 'close']"));
		}
	}
	
	// ���-�� �������� 
	private class Deletion_PopUp
	{
		private Custom getDeletionPopUp()
		{
			return new Custom(driver, By.id("attention_delete"));
		}
		
		private Custom getDeletionAcceptButton()
		{
			return new Custom(driver, By.xpath("//span[text() = '��']"));
		}
	}
}
