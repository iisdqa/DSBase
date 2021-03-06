package com.dsbase.core.web.pages.drug;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dsbase.core.web.CustomMethods;
import com.dsbase.core.web.WebPage;
import com.dsbase.core.web.elements.Button;
import com.dsbase.core.web.elements.Custom;
import com.dsbase.core.web.elements.TextInput;

public class DrugEditPage extends WebPage<DrugEditPage> 
{
private static final String PAGE_URL = BASE_URL + "/Drugs/Edit";
	
	public DrugEditPage(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public DrugEditPage load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return new Main_Elements().getBirthday().isAvailable();
	}
	
	
	public void waitForPageReady()
	{
		waitForBlockStatus(new Mnn_Elements().getGridDownload_Div(), false);
	}
	
	//___________________________________________________________ ��� ________________________________________________________________//
		
	public void addedMnn_check()
	{
		// �������� ��������� �����
		waitForBlockStatus(new Mnn_Elements().getGridDownload_Div(), false);
		simpleWait(1);
		
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
	
	public void mnn_Edit()
	{
		// �������� ���-��� ���������� ���
		new Mnn_Elements().getEditButton().click();
		simpleWait(2);
		waitUntilUnblocked((new Mnn_Elements().getAddEditPopUp()));
		simpleWait(2);
		
		// ������ ���
		new Mnn_Elements().getMnnField().clear();
		simpleWait(1);
		new Mnn_Elements().getMnnField().inputText(new Mnn_Elements().new Values().editedMnn);
		
		// ���������� ���
		new Mnn_Elements().getSaveButton().click();
		simpleWait(1);
		
		// �������� ��������� �����
		waitForBlockStatus(new Mnn_Elements().getGridDownload_Div(), false);
		simpleWait(1);
	}
	
	public void editedMnn_check()
	{		
		// ����������� ��������� ��������
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  new Mnn_Elements().new Values().editedMnn,
										  ""};
		
		// �������� ��������� �������� �� �����
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(new Mnn_Elements().getMnnGridBody());
		
		// �������� �������� �����
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	public void mnn_Delete()
	{
		// �������� ���-��� �������� ���
		new Mnn_Elements().getDeleteButton().click();
		simpleWait(2);
		waitUntilUnblocked((new Main_Elements().getDeletion_PopUp()));
		simpleWait(2);
			
		// �������� ���
		new Main_Elements().getDeletionYes_Button().click();
		simpleWait(1);
		
		// �������� ��������� �����
		waitForBlockStatus(new Mnn_Elements().getGridDownload_Div(), false);
		simpleWait(1);
	}
	
	public void deletedMnn_check()
	{
		// �������� ���������� �������� � ����� '���'
		new CustomMethods().elementExistenceCheck(new Mnn_Elements().getMnnGridBody() , false);
	}
	//________________________________________________________________________________________________________________________________//
	
	
	//____________________________________________________ ������������� _____________________________________________________________//
	
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
	
	public void manufacturer_Edit()
	{
		// �������� ���-��� ���������� �������������
		new Manufacterer_Elements().getEditButton().click();
		simpleWait(2);
		waitUntilUnblocked((new Manufacterer_Elements().getAddEditPopUp()));
		simpleWait(2);
		
		// ���������� �������������
		new Manufacterer_Elements().getSaveButton().click();
		simpleWait(1);
		
		// �������� ��������� �����
		waitForBlockStatus(new Manufacterer_Elements().getGridDownload_Div(), false);
		simpleWait(1);
	}
	
	public void manufacturer_Delete()
	{
		// �������� ���-��� �������� '����������� ��������'
		new Manufacterer_Elements().getDeleteButton().click();
		simpleWait(2);
		waitUntilUnblocked((new Main_Elements().getDeletion_PopUp()));
		simpleWait(2);
			
		// �������� ���
		new Main_Elements().getDeletionYes_Button().click();
		simpleWait(1);
		
		// �������� ��������� �����
		waitForBlockStatus(new Manufacterer_Elements().getGridDownload_Div(), false);
		simpleWait(1);
	}
	
	public void deletedManufacturer_check()
	{
		// �������� ���������� �������� � ����� '���������� ��������'
		new CustomMethods().elementExistenceCheck(new Manufacterer_Elements().getManufacturerGridBody() , false);
	}
	//________________________________________________________________________________________________________________________________//

	
	//___________________________________________________ ����������� �������� _______________________________________________________//
	
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
	
	public void substance_Edit()
	{
		// �������� ���-��� ���������� ������������ ��������
		new Substance_Elements().getEditButton().click();
		simpleWait(2);
		waitUntilUnblocked((new Substance_Elements().getAddEditPopUp()));
		simpleWait(2);
		
		// ������ ����������� ��������
		new Substance_Elements().getSubstanceField().clear();
		simpleWait(1);
		new Substance_Elements().getSubstanceField().inputText(new Substance_Elements().new Values().editedSubstance);
		
		// ���������� ����������� ��������
		new Substance_Elements().getSaveButton().click();
		simpleWait(1);
		
		// �������� ��������� �����
		waitForBlockStatus(new Substance_Elements().getGridDownload_Div(), false);
		simpleWait(1);
	}
	
	public void editedSubstance_check()
	{
		// ����������� ��������� ��������
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
				  						  new Substance_Elements().new Values().editedSubstance,
										  ""};
		
		// ����������� ���������� ��������
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(new Substance_Elements().getSubstancesGridBody());
		
		// �������� �������� �����
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	public void substance_Delete()
	{
		// �������� ���-��� �������� '����������� ��������'
		new Substance_Elements().getDeleteButton().click();
		simpleWait(2);
		waitUntilUnblocked((new Main_Elements().getDeletion_PopUp()));
		simpleWait(2);
			
		// �������� ���
		new Main_Elements().getDeletionYes_Button().click();
		simpleWait(1);
		
		// �������� ��������� �����
		waitForBlockStatus(new Substance_Elements().getGridDownload_Div(), false);
		simpleWait(1);
	}
	
	public void deletedSubstance_check()
	{
		// �������� ���������� �������� � ����� '���������� ��������'
		new CustomMethods().elementExistenceCheck(new Substance_Elements().getSubstancesGridBody() , false);
	}
	//________________________________________________________________________________________________________________________________//
	
	
	//________________________________________________________ ��������� _____________________________________________________________//

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
	
	public void doc_Edit()
	{
		// �������� ���-��� ���������� ������������ ��������
		new Docs_Elements().getEditButton().click();
		simpleWait(2);
		waitUntilUnblocked((new Docs_Elements().getEditPopUp()));
		simpleWait(2);
		
		// ������ ����������� ��������
		new Docs_Elements().getName_Field().clear();
		simpleWait(1);
		new Docs_Elements().getName_Field().inputText(new Docs_Elements().new Values().editedName);
		
		// ���������� ����������� ��������
		new Docs_Elements().getSaveButton().click();
		simpleWait(1);
		
		// �������� ��������� �����
		waitForBlockStatus(new Docs_Elements().getGridDownload_Div(), false);
		simpleWait(1);
	}
	
	public void editedDoc_check()
	{
		// ����������� ��������� ��������
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
				  						  new Docs_Elements().new Values().date,
				  						  new Docs_Elements().new Values().editedName,
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
	
	public void doc_Delete()
	{
		// �������� ���-��� �������� '����������� ��������'
		new Docs_Elements().getDeleteButton().click();
		simpleWait(2);
		waitUntilUnblocked((new Main_Elements().getDeletion_PopUp()));
		simpleWait(2);
			
		// �������� ��������
		new Main_Elements().getDeletionYes_Button().click();
		simpleWait(1);

		// �������� ��������� �����
		waitForBlockStatus(new Docs_Elements().getGridDownload_Div(), false);
		simpleWait(1);
	}
	
	public void deletedDoc_check()
	{
		// �������� ���������� �������� � ����� '���������� ��������'
		new CustomMethods().elementExistenceCheck(new Docs_Elements().getDocsGridBody() , false);
	}
	//________________________________________________________________________________________________________________________________//
	
	
	//___________________________________________________ ���������� _______________________________________________________//
	
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
	
	public void employee_Edit()
	{
		// �������� ���-��� ���������� ������������ ��������
		new Employee_Elements().getEditButton().click();
		simpleWait(2);
		waitUntilUnblocked((new Employee_Elements().getAddEditPopUp()));
		simpleWait(2);
		
		// ������ ����������� ��������
		new Employee_Elements().getPositionField().clear();
		simpleWait(1);
		new Employee_Elements().getPositionField().inputText(new Employee_Elements().new Values().editedPosition);
		
		// ���������� ����������� ��������
		new Employee_Elements().getSaveButton().click();
		simpleWait(1);
		
		// �������� ��������� �����
		waitForBlockStatus(new Employee_Elements().getGridDownload_Div(), false);
		simpleWait(1);
	}
	
	public void editedEmployee_check()
	{
		// ����������� ��������� ��������
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
				  						  new Employee_Elements().new Values().fio,
				  						  new Employee_Elements().new Values().editedPosition,
				  						  ""};
		
		// ����������� ���������� ��������
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(new Employee_Elements().getEmployeeGridBody());
		
		// �������� �������� �����
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	public void employee_Delete()
	{
		// �������� ���-��� �������� '����������� ��������'
		new Employee_Elements().getDeleteButton().click();
		simpleWait(2);
		waitUntilUnblocked((new Main_Elements().getDeletion_PopUp()));
		simpleWait(2);
			
		// �������� ���
		new Main_Elements().getDeletionYes_Button().click();
		simpleWait(1);
		
		// �������� ��������� �����
		waitForBlockStatus(new Employee_Elements().getGridDownload_Div(), false);
		simpleWait(1);
	}
	
	public void deletedEmployee_check()
	{
		// �������� ���������� �������� � ����� '���������� ��������'
		new CustomMethods().elementExistenceCheck(new Employee_Elements().getEmployeeGridBody() , false);
	}
	//________________________________________________________________________________________________________________________________//
	
	public DrugPassportPage drug_Save()
	{
			new Main_Elements().getSaveButton().click();
			return new DrugPassportPage(driver).waitUntilAvailable();
	}
	
	private class Main_Elements
	{
		// '������������� ���� ��������'
		private TextInput getBirthday()
		{
			return new TextInput(driver, By.id("World_drug_identification_drug_birthday"));
		}
		
		private Custom getDeletion_PopUp()
		{
			return new Custom(driver, By.id("attention_delete"));
		}
		
		// ������ '���������'
		private Button getDeletionYes_Button()
		{
			return new Button(driver, By.xpath("//span[text() = '��']"));
		}
		
		// ������ '���������'
		private Button getSaveButton()
		{
			return new Button(driver, By.xpath("//input[contains(@onclick,'Save()')]"));
		}
	}
	//________________________________________________________________________________________________________________________________//
	
	// �������� ����� '���'
	private class Mnn_Elements
	{
		
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
		
		// ������ ��������������
		private Button getEditButton()
		{
			return new Button(driver, By.xpath("//td[@aria-describedby='list_Mnn_edit']/input"));
		}
		
		// ������ ��������
		private Button getDeleteButton()
		{
			return new Button(driver, By.xpath("//td[@aria-describedby='list_Mnn_del']/input"));
		}
		
		private class Values
		{
			private String addEditPopUpName = "�������� ���";						// �������� ���-��� ����������/�������������� ���
			private String mnn = "11122233344";	  									// ���
			private String editedMnn = "111222333444";								// ��� ����� ��������������
		}
	}
	//________________________________________________________________________________________________________________________________//
	
	// �������� ����� '������������� ���������'
	private class Manufacterer_Elements
	{
		
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
		
		// ������ '���������'
		private Button getSaveButton()
		{
			return new Button(driver, By.xpath("//input[contains(@onclick,'OnSave()')]"));
		}
		
		// ������ ��������������
		private Button getEditButton()
		{
			return new Button(driver, By.xpath("//td[@aria-describedby='list_manufacturer_edit']/input"));
		}
		
		// ������ ��������
		private Button getDeleteButton()
		{
			return new Button(driver, By.xpath("//td[@aria-describedby='list_manufacturer_del']/input"));
		}
		
		private class Values
		{
			private String addEditPopUpName = "�������� ������������� ���������";   // �������� ���-��� ����������/�������������� �������������
			private String manufactererName = "������������� ��� ���������";	    // �������� �������������
			private String manufactererCountry = "�����";	  						// ������ �������������
			private String manufactererAdress = "��. ��������, �. 1";	  			// ����� �������������
		}
	}
	
	//________________________________________________________________________________________________________________________________//
	
	// �������� ����� '����������� ��������'
	private class Substance_Elements
	{		
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
		
		// ������ ��������������
		private Button getEditButton()
		{
			return new Button(driver, By.xpath("//td[@aria-describedby='list_Substances_edit']/input"));
		}
		
		// ������ ��������
		private Button getDeleteButton()
		{
			return new Button(driver, By.xpath("//td[@aria-describedby='list_Substances_del']/input"));
		}
		
		private class Values
		{
			private String addEditPopUpName = "�������� ����������� ��������";		// �������� ���-��� ����������/�������������� ������������ ��������
			private String substance = "����";	  									// ����������� ��������
			private String editedSubstance = "��������";						     	// ����������� �������� ����� ��������������
		}
	}
	//________________________________________________________________________________________________________________________________//
	
	
	// �������� ����� '���������'
		private class Docs_Elements
		{
			
			// ���-�� ����������
			private Custom getEditPopUp()
			{
				return new Custom(driver, By.xpath("//span[text() = '" + new Values().editPopUpName + "']"));
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
			
			// �������� ���������
			private TextInput getName_Field()
			{
				return new TextInput(driver, By.id("Title"));
			}
			
			// ������ '���������'
			private Button getSaveButton()
			{
				return new Button(driver, By.id("save_file_btn"));
			}
			
			// ������ ��������������
			private Button getEditButton()
			{
				return new Button(driver, By.xpath("//td[@aria-describedby='list_file_load_edit']/input"));
			}
			
			// ������ ��������
			private Button getDeleteButton()
			{
				return new Button(driver, By.xpath("//td[@aria-describedby='list_file_load_del']/input"));
			}
			
			private class Values
			{
				private String editPopUpName = "�������������� ���������";				// �������� ���-��� ����������/�������������� ���������
				private String date = "05.01.2012";	  									// ����
				private String name = "���";						     				// �������� ���������
				private String editedName = "����";										// �������� ��������� ����� ��������������
				private String docDescription = "��������";						    	// �������� ���������
				private String docType = "����������";						     		// ����������
				private String fileLink = "www.getFile.com/get";			     		// ������ �� ����
			}
		}
		
		// �������� ����� '����������'
		private class Employee_Elements
		{	
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
			
			// ������ ��������������
			private Button getEditButton()
			{
				return new Button(driver, By.xpath("//td[@aria-describedby='list_staff_edit']/input"));
			}
			
			// ������ ��������
			private Button getDeleteButton()
			{
				return new Button(driver, By.xpath("//td[@aria-describedby='list_staff_del']/input"));
			}
			
			private class Values
			{
				private String addEditPopUpName = "�������� �����������, ������� ����� ��";		// �������� ���-��� ����������/�������������� ����������
				private String fio = "������������ ����� ����������";	  						// ���
				private String position = "����������";						     				// ����� ���������������
				private String editedPosition = "���������";									// ����������������� ����� ���������������
			}
		}
		//________________________________________________________________________________________________________________________________//
}