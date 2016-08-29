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
		// �������� ������
		drugPassportTitle_Check();
		
		// �������� ������ ����� ��������� �����
		firstPart_Check();
		
		// �������� ����� ���
		mnnPart_check("Add");
		
		// �������� ����� �������������
		manufacturerPart_check();
		
		// �������� ����� ���������
		declarerPart_Check();
		
		// �������� ����� '������ ��'
		drugStructurePart_check("Add");
		
		// �������� ����� '���'
		atcPart_Check();
		
		// �������� ����� '����������� ��'
		drugRegistrationPart_check();
		
		// �������� ����� '��������������� ��'
		drugReRegistrationPart_check();
		
		// �������� ����� '���������'
		drugChanges_check();
		
		// �������� ����� '���� �������������� ���������� �� ���������������' � �����������
		LastPart_check();
	
		// �������� ����� '����������'
		employeesPart_check("Add");
		
		// �������� ����� '������ ��������������'
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
		// �������� ������
		drugPassportTitle_Check();
		
		// �������� ������ ����� ��������� �����
		firstPart_Check();
		
		// �������� ����� ���
		mnnPart_check("Edit");
		
		// �������� ����� �������������
		manufacturerPart_check();
		
		// �������� ����� ���������
		declarerPart_Check();
		
		// �������� ����� '������ ��'
		drugStructurePart_check("Edit");
		
		// �������� ����� '���'
		atcPart_Check();
		
		// �������� ����� '����������� ��'
		drugRegistrationPart_check();
		
		// �������� ����� '��������������� ��'
		drugReRegistrationPart_check();
		
		// �������� ����� '���������'
		drugChanges_check();
		
		// �������� ����� '���� �������������� ���������� �� ���������������' � �����������
		LastPart_check();
	
		// �������� ����� '����������'
		employeesPart_check("Edit");
		
		// �������� ����� '������ ��������������'
		drugOtherCountries_check();
	}
	
	public void deletedParts_Check()
	{
		// �������� ����� ���
		new Deletion_Check().deletedMnn_check();
		
		// �������� ����� '�������������'
		new Deletion_Check().deletedManufacturer_check();
		
		// �������� ����� '���������� ��������'
		new Deletion_Check().deletedSubstance_check();
		
		// �������� ����� '����������'
		new Deletion_Check().deletedEmployee_check();
	}
	
	private void drugPassportTitle_Check()
	{
		// ����������� ������
		String expectedTitle = "������� ���������: �������� ��������, ��������, 02.01.2012, 222";
		String actualTitle = new CustomMethods().StringSpacesCut(getDrugPassport_Title().getText());
		
		// �������� ������������
		assertThat(actualTitle, is(equalTo(expectedTitle)));
	}
	
	private void firstPart_Check()
	{
		// ����������� ��������� ��������
		String[][] ExpectedValues = new String [12][];
		ExpectedValues[0] = new String[] {"������ ��������������:"};
		ExpectedValues[1] = new String[] {new First_Part_Elements().new Values().DeclarerCountry};
		ExpectedValues[2] = new String[] {"�������� �������� ���������:"};
		ExpectedValues[3] = new String[] {new First_Part_Elements().new Values().DrugTradeName};
		ExpectedValues[4] = new String[] {"����� ������� ���������:"};
		ExpectedValues[5] = new String[] {new First_Part_Elements().new Values().DrugOutputForm};
		ExpectedValues[6] = new String[] {"�������:"};
		ExpectedValues[7] = new String[] {new First_Part_Elements().new Values().Packing};
		ExpectedValues[8] = new String[] {"������� �������:"};
		ExpectedValues[9] = new String[] {new First_Part_Elements().new Values().DrugLeaveCondition};
		ExpectedValues[10] = new String[] {"������ ��������"};
		ExpectedValues[11] = new String[] {new First_Part_Elements().new Values().InjectionWay};
		
		// �������� �������� �� �����
		String[][] ActualValues = new First_Part_Elements().getValuesForFirstPart(new First_Part_Elements().getGridBody());
		
		// �������� �������� �����
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	private void mnnPart_check(String CheckType)
	{
		// ���������� ����������
		String Mnn = "";
		
		// ���������� �������� ���������� � ����������� �� ���� ��������
		if(CheckType.equals("Add")) Mnn = new Grids_Elements().new Mnn().new Values().Mnn;
		else if(CheckType.equals("Edit")) Mnn = new Grids_Elements().new Mnn().new Values().editedMnn;
		
		// ����������� ��������� ��������
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {Mnn};
		
		// �������� �������� �� �����
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(new Grids_Elements().new Mnn().getGridBody());
		
		// �������� �������� �����
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	private void manufacturerPart_check()
	{
		// ����������� �������� �����
		String manufacturerName = new Grids_Elements().new Manufacturer().new Values().manufactererName;
		String manufacturerCountry = new Grids_Elements().new Manufacturer().new Values().manufactererCountry;
		String manufacturerAdress = new Grids_Elements().new Manufacturer().new Values().manufactererAdress;
		
		// ����������� ��������� ��������
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {manufacturerName, manufacturerCountry, manufacturerAdress};
		
		// �������� �������� �� �����
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(new Grids_Elements().new Manufacturer().getGridBody());
		
		// �������� �������� �����
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	private void drugStructurePart_check(String CheckType)
	{
		/*_________________________________________________ �������� ������������ �������� _________________________________________________*/
		
		// ���������� ����������
		String activeSubstance = "";
		
		// ���������� �������� ���������� � ����������� �� ���� ��������
		if(CheckType.equals("Add")) activeSubstance = new Grids_Elements().new Drug_Structure().new Values().acitveSubstance;
		else if(CheckType.equals("Edit")) activeSubstance = new Grids_Elements().new Drug_Structure().new Values().editedActiveSubstance;
		
		// ����������� ��������� ��������
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {activeSubstance};
		
		// �������� �������� �� �����
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(new Grids_Elements().new Drug_Structure().getGridBody());
		
		// �������� �������� �����
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
		/*__________________________________________________________________________________________________________________________________*/

		
		/*_________________________________________________ �������� ���������������� �������� _________________________________________________*/
		
		// ����������� �������� � �������� ���� ���������������� ��������
		String additionalSubstanceTitle = new CustomMethods().StringSpacesCut(new Grids_Elements().new Drug_Structure().new Values().additionalSubstanceTitle);
		String additionalSubstance = new CustomMethods().StringSpacesCut(new Grids_Elements().new Drug_Structure().new Values().additionalSubstance);
		
		// �������� ������������ ��������������� ���� �������������� ���������� �� ���������������
		assertThat(new Grids_Elements().new Drug_Structure().getAdditionalSubstanceTitle().getText(), is(equalTo(additionalSubstanceTitle)));
		assertThat(new Grids_Elements().new Drug_Structure().getAdditionalSubstanceField().getText(), is(equalTo(additionalSubstance)));
	}
	
	private void declarerPart_Check()
	{
		// ����������� �������� �����
		
		// ����������� ��������� ��������
		String[][] ExpectedValues = new String [4][];
		ExpectedValues[0] = new String[] {new Declarer_Part_Elements().new Values().DeclarerTitle, ""};
		ExpectedValues[1] = new String[] {new Declarer_Part_Elements().new Values().Declarer};
		ExpectedValues[2] = new String[] {new Declarer_Part_Elements().new Values().DeclarerCountryTitle, new Declarer_Part_Elements().new Values().DeclarerAdressTitle};
		ExpectedValues[3] = new String[] {new Declarer_Part_Elements().new Values().DeclarerCountry, new Declarer_Part_Elements().new Values().DeclarerAdress};
		
		// �������� �������� �� �����
		String[][] ActualValues = new Declarer_Part_Elements().getValuesForDeclarerPart(new Declarer_Part_Elements().getGridBody());
		
		// �������� �������� �����
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
		
	private void atcPart_Check()
	{	
		// ����������� �������� �����
		String atcCodeTitle = new AtcPart_Elements().new Values().atcCodeTitle;
		String atcCode = new AtcPart_Elements().new Values().atcCode;
		String atcGroupTitle = new AtcPart_Elements().new Values().atcGroupTitle;
		String atcGroup = new AtcPart_Elements().new Values().atcGroup;
		String birthdayTitle = new AtcPart_Elements().new Values().birthdayTitle;
		String birthday = new AtcPart_Elements().new Values().birthday;
		
		// ����������� ��������� ��������
		String[][] ExpectedValues = new String [2][];
		ExpectedValues[0] = new String[] {atcCodeTitle, atcGroupTitle, birthdayTitle};
		ExpectedValues[1] = new String[] {atcCode, atcGroup, birthday};
		
		// �������� �������� �� �����
		String[][] ActualValues = new AtcPart_Elements().getValuesFor_AtcPart(new AtcPart_Elements().getGridBody());
		
		// �������� �������� �����
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	private void drugRegistrationPart_check()
	{
		/*___________________________________________ �������� ������ � ����� '����������� ��' ___________________________________________*/
		
		// ����������� ��������� ��������
		String appTypeExpected = new DrugRegistration_Elements().new Values().appType;
		String regDateExpected = new DrugRegistration_Elements().new Values().regDate;
		String regNumExpected = new DrugRegistration_Elements().new Values().regNum;
		String instructionsExpected = new DrugRegistration_Elements().new Values().instructionsLink;
		String psurExpected = new DrugRegistration_Elements().new Values().psurLink;
		String prExpected = new DrugRegistration_Elements().new Values().prLink;
		String changesExpected = new DrugRegistration_Elements().new Values().changesLink;
		String refDrugExpected = new DrugRegistration_Elements().new Values().refDrugLink;
		
		// ����������� �������� ��������
		String appTypeActual = new CustomMethods().StringSpacesCut(new DrugRegistration_Elements().getAppType_Link().getText());
		String regDateActual = new CustomMethods().StringSpacesCut(new DrugRegistration_Elements().getRegDate_Link().getText());
		String regNumActual = new CustomMethods().StringSpacesCut(new DrugRegistration_Elements().getRegNum_Link().getText());
		String instructionsActual = new CustomMethods().StringSpacesCut(new DrugRegistration_Elements().getInstructions_Link().getText());
		String psurActual = new CustomMethods().StringSpacesCut(new DrugRegistration_Elements().getPsur_Link().getText());
		String prActual = new CustomMethods().StringSpacesCut(new DrugRegistration_Elements().getPrMessages_Link().getText());
		String changesActual = new CustomMethods().StringSpacesCut(new DrugRegistration_Elements().getChanges_Link().getText());
		String refDrugActual = new CustomMethods().StringSpacesCut(new DrugRegistration_Elements().getRefDrug_Link().getText());

		// �������� ��������
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
		// �������� ���������� �������� � ����� '��������������� ��'
		new CustomMethods().elementExistenceCheck(new Grids_Elements().new Drug_Reregistration().getGridBody() , false);
	}
	
	private void drugChanges_check()
	{
		// �������� ���������� �������� � ����� '���������'
		new CustomMethods().elementExistenceCheck(new Grids_Elements().new Drug_Changes().getGridBody() , false);
	}
	
	private void LastPart_check()
	{
		// ����������� �������� ����� �����
		String plannedDateTitle = new LastPart_Elements().new Values().plannedDateTitle;
		String plannedDate = new LastPart_Elements().new Values().plannedDate;
		String actualDateTitle = new LastPart_Elements().new Values().actualDateTitle;
		String actualDate = new LastPart_Elements().new Values().actualDate;
		String endRuDateTitle = new LastPart_Elements().new Values().endRuDateTitle;
		String endRuDate = new LastPart_Elements().new Values().endRuDate;
		String drugConditionTitle = new LastPart_Elements().new Values().drugConditionTitle;
		String drugCondition = new LastPart_Elements().new Values().drugCondition;
		
		// ����������� ��������� ��������
		String[][] ExpectedValues = new String [4][];
		ExpectedValues[0] = new String[] {plannedDateTitle, actualDateTitle};
		ExpectedValues[1] = new String[] {plannedDate, actualDate};
		ExpectedValues[2] = new String[] {endRuDateTitle, drugConditionTitle};
		ExpectedValues[3] = new String[] {endRuDate, drugCondition};
		
		// �������� �������� �� �����
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(new LastPart_Elements().getGridBody());
		
		// �������� �������� �����
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	private void employeesPart_check(String CheckType)
	{
		// ����������� �������� ����� �����
		String FIO = new Grids_Elements().new Employees().new Values().fio;
		
		// ���������� ����������
		String Position = "";
		
		// ���������� �������� ���������� � ����������� �� ���� ��������
		if(CheckType.equals("Add")) Position = new Grids_Elements().new Employees().new Values().position;
		else if(CheckType.equals("Edit")) Position = new Grids_Elements().new Employees().new Values().editedPosition;
		
		// ����������� ��������� ��������
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {FIO, Position};
		
		// �������� �������� �� �����
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(new Grids_Elements().new Employees().getGridBody());
		
		// �������� �������� �����
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	private void drugOtherCountries_check()
	{
		// �������� ���������� �������� � ����� '���������'
		new CustomMethods().elementExistenceCheck(new Grids_Elements().new Drug_OtherCountries().getGridBody() , false);
	}
	
	/*================================================= �������� ��������� ������� � ����� ================================================*/
	private class Deletion_Check
	{
		public void deletedMnn_check()
		{
			// �������� ���������� �������� � ����� '���'
			new CustomMethods().elementExistenceCheck(new Grids_Elements().new Mnn().getGridBody(), false);
		}
		
		public void deletedManufacturer_check()
		{
			// �������� ���������� �������� � ����� '���������� ��������'
			new CustomMethods().elementExistenceCheck(new Grids_Elements().new Manufacturer().getGridBody(), false);
		}
		
		public void deletedSubstance_check()
		{
			// �������� ���������� �������� � ����� '���������� ��������'
			new CustomMethods().elementExistenceCheck(new Grids_Elements().new Drug_Structure().getGridBody(), false);
		}
		
		public void deletedEmployee_check()
		{
			// �������� ���������� �������� � ����� '���������� ��������'
			new CustomMethods().elementExistenceCheck(new Grids_Elements().new Employees().getGridBody(), false);
		}
	}
	
	/*================================================= �������� ��������� � ������� ������� ==============================================*/
	public class For_Other_Tests
	{
		public class DrugChanges_Test
		{
			public void added_drugChanges_Check()
			{	
				// ����������� �������� �����
				String orderNumber = new Values().orderNumber;
				String orderDate = new Values().orderDate;
				String changeType = new Values().changeType;
				
				// ����������� ��������� ��������
				String[][] ExpectedValues = new String [1][];
				ExpectedValues[0] = new String[] {"",
												  orderNumber, 
												  orderDate, 
												  changeType};
				
				// �������� �������� �� �����
				String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(new Grids_Elements().new Drug_Changes().getGridBody());
				
				// �������� �������� �����
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
				private String orderNumber = "111222";	  						  		// � �������
				private String orderDate = "02.01.2012";								// ���� �������
				private String changeType = "���������������� ���������";				// ��� ���������
			}
		}
	}
	/*============================================== ������������ �������� � �������� � ���� ==============================================*/

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
	
	//___________________________________________________ �������� ������� ����� ___________________________________________________//
	
	private class First_Part_Elements
	{
		private WebElement getGridBody()
		{
			return driver.findElement(By.xpath("//div[@class='content_page table_cell']/div[@class='max_width1010px']/table[1]/tbody"));
		}
		
		private String[][] getValuesForFirstPart (WebElement table)
		{
			// ����������� ���������� �����
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			
			// ���������� ���� - ������� ������ <tr>
			for(int rnum= (rows.size() - 1); rnum > 0; rnum--)
			{
				// ����������� ������, ������ � ���� ��� ����������� <tr>
				WebElement tempElement = rows.get(rnum);
				String ElText = tempElement.getText();
				String ElClass = tempElement.getAttribute("class");
				String ElRole = tempElement.getAttribute("role");
				
				// ������� <tr> ��� ������, � �������� ������ ��� ����(���� '���', � �����)
				if((ElText.equals("")) || (ElClass != null && !ElClass.isEmpty()) || (ElRole != null && !ElRole.isEmpty()))
				{
					rows.remove(rnum);
				}
			}
			
			// �������� ������ ���������(���� '���')
			rows.remove(4);
			
			// �������� ������ ���������(���� '������ ��������')
			rows.remove(12);
			rows.remove(11);
			
			// ����������� ���������� ����� � ������������ �������
			String[][] GridValues = new String[rows.size()][];
			
			// ���������� ���� - ���������� �������� � ���������� �������, ������� ����� �������� � ���������� ������(����� ��� ������������� ��������)
			for(int rnum = 0; rnum < rows.size(); rnum++)
			{			
				// ����������� ������� ����������� �������
				String[] ColumnValues = new String[1];
			
				// ����� ����� �� <tr>
				ColumnValues[0] = new CustomMethods().StringSpacesCut(rows.get(rnum).getText());
		
				// �������� �������� � ���������� ������
				GridValues[rnum] = ColumnValues;
			}
			return GridValues;
		}
		
		private class Values
		{
			private String DeclarerCountry = "�����";     								// ������ ���������
			private String DrugTradeName = "�������� ��������";     				    // �������� �������� ���������
			private String DrugOutputForm = "��������";     							// ����� ������� ���������
			private String Packing = "5�";     											// �������
			private String DrugLeaveCondition = "���";     							    // ������� �������
			private String InjectionWay = "� ���";     									// ������ ��������
		}
	}
	//________________________________________________________________________________________________________________________________//
	
	
	//___________________________________________________ �������� ����� ��������� ___________________________________________________//

	private class Declarer_Part_Elements
	{
		private WebElement getGridBody()
		{
			return driver.findElement(By.xpath("//div[@class='content_page table_cell']/div[@class='max_width1010px']/table[2]/tbody"));
		}
		
		private String[][] getValuesForDeclarerPart (WebElement table)
		{
			// ����������� ���������� �����
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			
			// ����������� ���������� ����� � ������������ �������
			String[][] GridValues = new String[rows.size()][];
			
			// ���������� ����
			for(int rnum=0; rnum < rows.size(); rnum++)
			{
				// ���������� ���������� �������
				List<WebElement> columns=rows.get(rnum).findElements(By.tagName("td"));

				// ����������� ������� ������� � ���������� �����
				String[] ColumnValues = new String[columns.size()];
				
				// ���������� �������� ����� � ������
				for(int cnum=0; cnum < columns.size(); cnum++)
				{					
					ColumnValues[cnum] = new CustomMethods().StringSpacesCut(columns.get(cnum).getText());
				}
				
				// �������� �������� ������� � ��� 
				GridValues[rnum] = ColumnValues;
			}
			return GridValues;
		}
		
		private class Values
		{
			private String DeclarerTitle = "���������:";      							// �������� ���� '���������'
			private String Declarer = "��������� ��� ���������";     					// ���������
			private String DeclarerCountryTitle = "������ ���������:";     				// �������� ���� '������ ���������'
			private String DeclarerCountry = "�����";     								// ������ ���������
			private String DeclarerAdressTitle = "����� ���������:";     				// �������� ���� '����� ���������'
			private String DeclarerAdress = "��. ��������, �. 2";     					// ����� ���������
		}
	}
	//________________________________________________________________________________________________________________________________//
	
	
	//__________________________________________________ �������� ������ � ������� ___________________________________________________//
	
	private class Grids_Elements
	{
		// ���� '���'
		private class Mnn
		{
			private WebElement getGridBody()
			{
				return driver.findElement(By.xpath("//table[@id='list_Mnn']/tbody"));
			}
			
			private class Values
			{
				private String Mnn = "11122233344";      								// ���
				private String editedMnn = "111222333444";								// ��� ����� ��������������
			}
		}
		
		// ���� '�������������'
		private class Manufacturer
		{
			private WebElement getGridBody()
			{
				return driver.findElement(By.xpath("//table[@id='list_manufacturer']/tbody"));
			}
			
			private class Values
			{
				private String manufactererName = "������������� ��� ���������";	  	// �������� �������������
				private String manufactererCountry = "�����";	  						// ������ �������������
				private String manufactererAdress = "��. ��������, �. 1";	  			// ����� �������������
			}
		}
		
		// ���� '������ ��'
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
				private String acitveSubstance = "����";	  									// ����������� ��������
				private String editedActiveSubstance = "��������";						     	// ����������� �������� ����� ��������������
				private String additionalSubstanceTitle = "��������������� ��������:";	  	    // �������� ���� ��� '��������������� ��������'
				private String additionalSubstance = "����";						     		// ��������������� ��������
			}
		}
		
		// ���� '��������������� ��'
		private class Drug_Reregistration
		{
			private WebElement getGridBody()
			{
				return driver.findElement(By.xpath("//table[@id='list_re_registering']/tbody"));
			}
		}
		
		// ���� '���������'
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
		
		// ���� '����������'
		private class Employees
		{
			private WebElement getGridBody()
			{
				return driver.findElement(By.xpath("//table[@id='list_staff']/tbody"));
			}
			
			private class Values
			{
				private String fio = "������������ ����� ����������";	  						// ���
				private String position = "����������";						     				// ����� ���������������
				private String editedPosition = "���������";									// ����������������� ����� ���������������
			}
		}
		
		// ���� '������ ��������������'
		private class Drug_OtherCountries
		{
			private WebElement getGridBody()
			{
				return driver.findElement(By.xpath("//table[@id='list_countries']/tbody"));
			}
		}
	}
	//________________________________________________________________________________________________________________________________//
	
	
	//_______________________________________�������� ����� '���' + ������������� ���� ��������_______________________________________//
	private class AtcPart_Elements
	{
		private WebElement getGridBody()
		{
			return driver.findElement(By.xpath("//div[@class='content_page table_cell']/div[@class='max_width1010px']/table[@class='max_width400px']/tbody"));
		}
	
		public String[][] getValuesFor_AtcPart(WebElement tbody)
		{
			// ����������� ���������� �����
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
						
			// ����������� ������� ������������� �������
			String[][] GridValues = new String[rows.size()][];
			
			// ���������� ����
			for(int rnum=0; rnum < rows.size(); rnum++)
			{
				// ���������� ���������� �������
				List<WebElement> columns=rows.get(rnum).findElements(By.tagName("td"));
				int sizeBeforeCut = columns.size();
							
				// ���������� ������� �����
				for(int cnum= (sizeBeforeCut - 1); cnum >= 0; cnum--)
				{
					if(columns.get(cnum).getCssValue("display").equals("none"))
					{
						columns.remove(cnum);
					}
				}
				
				// ����������� ������� ������� � ���������� �����
				String[] ColumnValues = new String[columns.size()];
				
				// ���������� �������� ����� � ������
				for(int cnum=0; cnum<columns.size(); cnum++)
				{
					ColumnValues[cnum] = columns.get(cnum).getText();
				}
				
				// �������� �������� ������� � ��� 
				GridValues[rnum] = ColumnValues;
			}
			return GridValues;
		}
		
		private class Values
		{
			private String atcCodeTitle = "ATC ���:";	  						    // �������� ���� '��� ���'
			private String atcCode = "111";	  										// �������� ���� '��� ���'
			private String atcGroupTitle = "ATC ������:";	  						// �������� ���� '��� ������'
			private String atcGroup = "��������";	  								// �������� ���� '��� ������'
			private String birthdayTitle = "������������� ���� ��������:";	  		// �������� ���� '������������� ���� ��������'
			private String birthday = "01.01.2012";	  								// �������� ���� '������������� ���� ��������'
		}
	}
	//________________________________________________________________________________________________________________________________//
	
	
	//____________________________________________________ ���� '����������� ��' _____________________________________________________//
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
			private String appType = "��������";	  										// ��� ��������� ��� �����������
			private String regDate = "02.01.2012";	  										// ���� �����������
			private String regNum = "222";	  												// ����� ��
			private String instructionsLink = "����������";	  								// ����������
			private String psurLink = "������ �� ������������";	  							// ������ �� ������������
			private String prLink = "��������� � ��/��";	  								// ��������� � ��/��
			private String changesLink = "���������";	  									// ���������
			private String refDrugLink = "����������� ��";	  							    // ����������� ��
		}
	}
	//________________________________________________________________________________________________________________________________//
	
	
	//_______________________________ �������� ���� '���� �������������� ���������� �� ���������������' ______________________________//
	
	private class LastPart_Elements
	{
		private WebElement getGridBody()
		{
			return driver.findElement(By.xpath("(//div[@class='content_page table_cell']/div[@class='max_width1010px']/table)[4]/tbody"));
		}
		
		private class Values
		{
			private String plannedDateTitle = "���������������:";	  			    // �������� ���� '��������������� ����'
			private String plannedDate = "01.01.2013";	  							// �������� ���� '��������������� ����'
			private String actualDateTitle = "�����������:";	  					// �������� ���� '����������� ����'
			private String actualDate = "";	  										// �������� ���� '����������� ����'
			private String endRuDateTitle = "���� ��������� ��:";	  				// �������� ���� '���� ��������� ��'
			private String endRuDate = "01.01.2014";	  							// �������� ���� '���� ��������� ��'
			private String drugConditionTitle = "��������� �� ���������:";	  		// �������� ���� '��������� �� ���������'
			private String drugCondition = "� ������ ����������";	  				// �������� ���� '��������� �� ���������'
		}
	}
	//________________________________________________________________________________________________________________________________//
	
	
	/*=====================================================================================================================================*/
}