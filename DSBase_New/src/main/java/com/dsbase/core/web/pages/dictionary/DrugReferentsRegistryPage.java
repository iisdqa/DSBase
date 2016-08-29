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
		// ���� 1 ���
		simpleWait(1);
		
		// �������� ��������� �����
		waitForBlockStatus(getGridDownload_Div(), false);
		simpleWait(1);
	}
	
	public void refDrugAdd()
	{		
		// �������� ���-��� ���������� ���. ��
		getReferentAdd_Button().click();
		simpleWait(1);
		//waitUntilUnblocked(new AddEditPage_Elements().getDrugAdd_Button());
		new AddEditPage_Elements().getDrugAdd_Button().waitUntilAvailable();
		
		// �������� �����������
		drug_validatorsCheck();
		
		// ������� ��
		drug_Set();
		
		// ��������� ������ ����
		otherFields_Set();
	
		// ���������� ���. ��
		new AddEditPage_Elements().getSave_Button().click();
		getReferentAdd_Button().waitUntilAvailable();
		waitForGridReady();
	}
	
	private void drug_validatorsCheck()
	{
		// ����������� �������� �����������
		String tradeName = new AddEditPage_Elements().new Validators().new Values().tradeName;
		String releaseForm = new AddEditPage_Elements().new Validators().new Values().releaseForm;
		String link = new AddEditPage_Elements().new Validators().new Values().link;
		String activeSubstance = new AddEditPage_Elements().new Validators().new Values().activeSubstance;	
		
		// ������ �� �������� ������
		new AddEditPage_Elements().getLink().inputText("123");
		
		// ������ �� ������ '���������'
		new AddEditPage_Elements().getSave_Button().click();
		simpleWait(2);
		
		// �������� �����������
		assertThat(new AddEditPage_Elements().new Validators().getTradeName_Validator().getText(), is(equalTo(tradeName)));	
		assertThat(new AddEditPage_Elements().new Validators().getReleaseForm_Validator().getText(), is(equalTo(releaseForm)));
		assertThat(new AddEditPage_Elements().new Validators().getLink_Validator().getText(), is(equalTo(link)));	
		assertThat(new AddEditPage_Elements().new Validators().getSubstance_Validator().getText(), is(equalTo(activeSubstance)));
	}
	
	private void drug_Set()
	{		
		// �������� ���-��� ���������� ��
		new AddEditPage_Elements().getDrugAdd_Button().click();
		simpleWait(1);
		waitUntilUnblocked(new AddEditPage_Elements().new DrugAdd_PopUp().getDrugAdd_PopUp());
		simpleWait(2);
		
		// ����� ��
		String drugName = new AddEditPage_Elements().new DrugAdd_PopUp().new Values().drugName;
		new AddEditPage_Elements().new DrugAdd_PopUp().getDrugName().inputText(drugName);
		new AddEditPage_Elements().new DrugAdd_PopUp().getDrugSearchButton().click();
		simpleWait(1);
		
		// �������� ��������� �����
		waitForBlockStatus(new AddEditPage_Elements().new DrugAdd_PopUp().getGridDownload_Div(), false);
		simpleWait(1);
		
		// �������� �� ������ '������'(���� �������� � �������)
		Actions action = new Actions(driver);
		action.click((new AddEditPage_Elements().new DrugAdd_PopUp().getDrugNameCell())).perform();
		simpleWait(2);
		
		// ���������� ��
		new AddEditPage_Elements().new DrugAdd_PopUp().getDrugAcceptButton().click();
		simpleWait(2);
	}
	
	private void otherFields_Set()
	{
		String tradeName = new AddEditPage_Elements().new Values().tradeName;
		String releaseForm = new AddEditPage_Elements().new Values().releaseForm;
		String manufacturer = new AddEditPage_Elements().new Values().manufacturer;
		String link = new AddEditPage_Elements().new Values().link;
		
		// �������� �������� 
		new AddEditPage_Elements().getTradeName().inputText(tradeName);
		
		// ����� �������
		new AddEditPage_Elements().getReleaseForm().inputText(releaseForm);
		
		// �������������
		new AddEditPage_Elements().getManufacturer().inputText(manufacturer);
		
		// ������ �� ���� ���. ��
		new AddEditPage_Elements().getLink().clear();
		new AddEditPage_Elements().getLink().inputText(link);
	}
	
	public void addedRefDrug_Check()
	{
		// ����������� ��������� ��������
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  "",
										  new AddEditPage_Elements().new Values().tradeName, 
										  new AddEditPage_Elements().new Values().releaseForm, 
										  new AddEditPage_Elements().new Values().manufacturer, 
								  		  "��������",
								  		  "��������",
								  		  new AddEditPage_Elements().new Values().link,
										  ""};
		
		// �������� ��������� �������� �� �����
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(getGridBody());
		
		// �������� �������� �����
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	public void refDrug_Search()
	{
	
	}
	
	public void refDrug_Edit()
	{
		// �������� ����� �������������� ���. ��
		getReferentEdit_Button().click();
		simpleWait(1);
		new AddEditPage_Elements().getDrugAdd_Button().waitUntilAvailable();
		
		// ����� �������
		new AddEditPage_Elements().getReleaseForm().clear();
		simpleWait(1);
		new AddEditPage_Elements().getReleaseForm().inputText(new AddEditPage_Elements().new Values().editedReleaseForm);
		
		// ���������� ���. ��
		new AddEditPage_Elements().getSave_Button().click();
		getReferentAdd_Button().waitUntilAvailable();
		waitForGridReady();
	}

	/*
	public void editedDeclarer_Check()
	{
		// ����������� ��������� ��������
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  "�������� ���������", 
								  		  "For automation", 
								  		  "�����", 
								  		  "��. ��������, �. 2",
										  ""};
		
		// �������� ��������� �������� �� �����
		String[][] ActualValues = getLastRowInGrid();
		
		// �������� �������� �����
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
		// ����������� ��������� ��������
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  "�������� �������������", 
								  		  "For automation", 
								  		  "�����", 
								  		  "��. ��������, �. 1",
										  ""};
		
		// �������� ��������� �������� �� �����
		String[][] ActualValues = getLastRowInGrid();
		
		// �������� �������� �����
		gridValuesUnequalityCheck(ExpectedValues, ActualValues);
	}*/
	
	public LogInPage userOut()
	{
		// ����� �� �������
		return new CommonActions().userOut(driver);
	}
	
	// "������������"
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
			// ���-�� ����������
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
			
			// "������������"
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
				private String drugAddEditPopUpName = "����� ��";   			// �������� ���-��� ���������� ��
				private String drugName = "�������� ��������";	  				// �������� ��
			}
		}
		
		// ����������
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
				private String tradeName = "������ ���� ������ ���� �� ���� ��������.";	  											// �������� ���. ��
				private String releaseForm = "���� ��������� �������� ������������ ��� ����������.";	  							// ����� �������
				private String link = "���� ������ ������� ������������ ��� ����������.";	  										// ������ �� ���� ���. ��
				private String activeSubstance = "�������� ������ ������. ������ ������ ���������� � \"http://\" ��� \"https://\"";	// ������ �� ���� ���. ��
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
		
		// ���� ���������� 
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
			private String tradeName = "�������� ���. ��";	  			// �������� ���. ��
			private String releaseForm = "����";	  					// ����� �������
			private String editedReleaseForm = "��������";	  			// ����� �������(��� ��������������)
			private String manufacturer = "��������";	  				// �������������
			private String link = "http://google.com";	  				// ������ �� ���� ���. ��
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
		return new Custom(driver, By.xpath("//span[text() = '��']"));
	}
}
