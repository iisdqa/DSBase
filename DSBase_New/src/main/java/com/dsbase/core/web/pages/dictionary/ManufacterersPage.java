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
import com.dsbase.core.web.elements.Button;
import com.dsbase.core.web.elements.Custom;
import com.dsbase.core.web.elements.Link;
import com.dsbase.core.web.elements.TextInput;
import com.dsbase.core.web.pages.other.LogInPage;

public class ManufacterersPage extends WebPage<ManufacterersPage> 
{
private static final String PAGE_URL = BASE_URL + "/Manufacturer/List";
	
	public ManufacterersPage(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public ManufacterersPage load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return getAddButton().isAvailable();
	}
	
	public void waitForGridUnblocked()
	{
		// ���� 1 ���
		simpleWait(1);
		
		// �������� ��������� �����
		waitForBlockStatus(getGridDownload_Div(), false);
		simpleWait(1);
	}
	
	public void manufactererAdd()
	{		
		// �������� ���-��� ���������� �������������
		getManufactererAddButton().click();
		simpleWait(2);
		waitUntilUnblocked(new AddPopUpElements().getAddPopUp());
		simpleWait(2);
		
		// ���������� �����
		manufactererInfo_FillUp();
		
		// ���������� �������������
		new AddPopUpElements().getManufactererSaveButton().click();
		waitForGridUnblocked();
	}
	
	public void drugManufactererAdd()
	{		
		// �������� ���-��� ���������� �������������
		getManufactererAddButton().click();
		simpleWait(2);
		waitUntilUnblocked(new AddPopUpElements().getAddPopUp());
		simpleWait(2);
		
		// ���������� �����
		drugManufactererInfo_FillUp();
		
		// ���������� �������������
		new AddPopUpElements().getManufactererSaveButton().click();
		waitForGridUnblocked();
	}
	
	private void manufactererInfo_FillUp()
	{
		// ����������� ������
		String country = "�����";
		
		// �������� �������������
		new AddPopUpElements().getManufacterer().inputText("��������");
		
		// �������� ������������� �� ����������
		new AddPopUpElements().getManufactererEn().inputText("For automation");
		
		// ����� ������
		getCountryFromDictionary(country);
		
		// �������� ������ ������ � ���������� ������
		String chosenCountry = new AddPopUpElements().getManufactererCountry().getAttribute("value");
		assertThat(chosenCountry, is(equalTo(country)));
		
		new AddPopUpElements().getManufactererAdress().inputText("��. ��������, �. 1");
	}
	
	private void drugManufactererInfo_FillUp()
	{
		// ����������� ������
		String country = "�����";
		
		// �������� �������������
		new AddPopUpElements().getManufacterer().inputText("������������� ��� ���������");
		
		// �������� ������������� �� ����������
		new AddPopUpElements().getManufactererEn().inputText("For drugAdd");
		
		// ����� ������
		getCountryFromDictionary(country);
		
		// �������� ������ ������ � ���������� ������
		String chosenCountry = new AddPopUpElements().getManufactererCountry().getAttribute("value");
		assertThat(chosenCountry, is(equalTo(country)));
		
		new AddPopUpElements().getManufactererAdress().inputText("��. ��������, �. 1");
	}
	
	private void getCountryFromDictionary(String country)
	{
		// ������� ������� �����
		new AddPopUpElements().getCountryDicOpener().click();
		simpleWait(2);
		
		// �������� ��������� �����
		waitForBlockStatus(new AddPopUpElements().new CountriesPopUp().getGridDownload_Div(), false);
		simpleWait(1);
		
		// ����� ������
		new AddPopUpElements().new CountriesPopUp().getCountryName().inputText(country);
		new AddPopUpElements().new CountriesPopUp().getCountrySearchButton().click();
		simpleWait(1);
		
		// �������� ��������� �����
		waitForBlockStatus(new AddPopUpElements().new CountriesPopUp().getGridDownload_Div(), false);
		simpleWait(1);
		
		// �������� �� ������ '�����'(���� �������� � �������)
		Actions action = new Actions(driver);
		action.click((new AddPopUpElements().new CountriesPopUp().getQatarCell())).perform();
		simpleWait(2);
		
		// �������
		new AddPopUpElements().new CountriesPopUp().getCountryAcceptButton().click();
		simpleWait(2);
		waitUntilUnblocked(new AddPopUpElements().getAddPopUp());
	}
	
	public void addedManufacterer_Check()
	{
		// ����������� ��������� ��������
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  "��������", 
								  		  "For automation", 
								  		  "�����", 
								  		  "��. ��������, �. 1",
										  ""};
		
		// �������� ��������� �������� �� �����
		String[][] ActualValues = getLastRowInGrid();
		
		// �������� �������� �����
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	public void addedDrugManufacterer_Check()
	{
		// ����������� ��������� ��������
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  "������������� ��� ���������", 
								  		  "For drugAdd", 
								  		  "�����", 
								  		  "��. ��������, �. 1",
										  ""};
		
		// �������� ��������� �������� �� �����
		String[][] ActualValues = getLastRowInGrid();
		
		// �������� �������� �����
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	public void manufactererEdit()
	{
		// �������� ���-��� �������������� �������������
		getLastEditButton().click();
		simpleWait(2);
		waitUntilUnblocked(new AddPopUpElements().getAddPopUp());
		simpleWait(2);
		// �������� �������������
		new AddPopUpElements().getManufacterer().clear();
		simpleWait(1);
		new AddPopUpElements().getManufacterer().inputText("�������� �������������");
		
		// ���������� �������������
		new AddPopUpElements().getManufactererSaveButton().click();
		waitForGridUnblocked();
	}
	
	public void editedManufacterer_Check()
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
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	public void manufactererDelete()
	{
		getLastDeleteButton().click();
		waitUntilUnblocked(getDeletionPopUp());
		getDeletionAcceptButton().click();
		waitForGridUnblocked();
	}
	
	public void deletedManufacterer_Check()
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
	}
	
	public LogInPage userOut()
	{
		// ����� �� �������
		return new CommonActions().userOut(driver);
	}
	
	private String[][] getLastRowInGrid()
	{
		// ���������� ����� ���������� ������� �����
		int gridPagesCount = Integer.parseInt(getGridPagesCount().getText());
		
		// ���� ������� ������, ��� ����, �� ������� � ���������
		if(gridPagesCount > 1)
		{
			getGridLastPage().click();
			waitForGridUnblocked();
		}
		
		// �������� ��� �������� �� �����
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(getGridBody());
		List<String[]> valuesList = Arrays.asList(ActualValues);
		int listSize = valuesList.size();
		
		
		// ����������� ���������� ����
		String[][] LastRowValues = new String [1][];
		LastRowValues[0] = valuesList.get(listSize - 1);
		return LastRowValues;
	}
	
	private void gridValuesUnequalityCheck(String[][] ActualValues, String[][] ExpectedValues)
	{	
		// �������� ����������� ���������� �������� � ��������� ��������
		assertThat(ActualValues[0][1], not(equalTo((ExpectedValues[0][1]))));
	}
	
	private Link getAddButton()
	{
		return new Link(driver, By.xpath("//input[@title='�������� �������������']"));
	}
	
	// "������������"
	private Custom getGridDownload_Div()
	{
		return new Custom(driver, By.id("load_list_manufacturer"));
	}
	
	private Button getManufactererAddButton()
	{
		return new Button(driver, By.xpath("//input[@title='�������� �������������']"));
	}
		
	private class AddPopUpElements
	{
		private Custom getAddPopUp()
		{
			return new Custom(driver, By.xpath("//span[text() = '�������� ������������� ���������']"));
		}
		
		private TextInput getManufacterer()
		{
			return new TextInput(driver, By.id("manufacturer"));
		}
		
		private TextInput getManufactererEn()
		{
			return new TextInput(driver, By.id("manufacturer_en"));
		}
		
		private Button getCountryDicOpener()
		{
			return new Button(driver, By.id("countries_dict_open"));
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
		private TextInput getManufactererCountry()
		{
			return new TextInput(driver, By.id("country"));
		}
		
		private TextInput getManufactererAdress()
		{
			return new TextInput(driver, By.id("manufacturer_address"));
		}
		
		private Button getManufactererSaveButton()
		{
			return new Button(driver, By.xpath("//input[@value='���������']"));
		}
	}
	
	private WebElement getGridBody()
	{
		return driver.findElement(By.xpath("//*[@id='list_manufacturer']/tbody"));
	}
	
	private Custom getGridPagesCount()
	{
		return new Custom(driver, By.id("sp_1_pager_manufacturer"));
	}
	
	private Custom getGridLastPage()
	{
		return new Custom(driver, By.id("last_pager_manufacturer"));
	}
	
	private WebElement getLastEditButton()
	{
		// ���������� ����� ���������� ������� �����
		int gridPagesCount = Integer.parseInt(getGridPagesCount().getText());
		
		// ���� ������� ������, ��� ����, �� ������� � ���������
		if(gridPagesCount > 1)
		{
			getGridLastPage().click();
			waitForGridUnblocked();
		}
		
		// ����������� ���� ������ �������������� �� ���������
		List<WebElement> buttons = driver.findElements(By.xpath("//input[@title='��������������']"));
		
		// ����������� ������� ������
		int listSize = buttons.size();
		
		// ����� ��������� ������� ��������������
		WebElement lastButton = buttons.get(listSize-1);
		return lastButton;
	}
	
	private WebElement getLastDeleteButton()
	{
		// ���������� ����� ���������� ������� �����
		int gridPagesCount = Integer.parseInt(getGridPagesCount().getText());
		
		// ���� ������� ������, ��� ����, �� ������� � ���������
		if(gridPagesCount > 1)
		{
			getGridLastPage().click();
			waitForGridUnblocked();
		}
		
		// ����������� ���� ������ �������������� �� ���������
		List<WebElement> buttons = driver.findElements(By.xpath("//input[@title='��������']"));
		
		// ����������� ������� ������
		int listSize = buttons.size();
		
		// ����� ��������� ������� ��������������
		WebElement lastButton = buttons.get(listSize-1);
		return lastButton;
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
