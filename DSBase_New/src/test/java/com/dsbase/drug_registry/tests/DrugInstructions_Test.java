package com.dsbase.drug_registry.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.dsbase.core.BaseTest;
import com.dsbase.core.database.DbQueries;
import com.dsbase.core.database.DbStatements;
import com.dsbase.core.web.pages.drug.DrugInstructionsPage;
import com.dsbase.core.web.pages.drug.DrugPassportPage;
import com.dsbase.core.web.pages.other.DrugRegistryPage;
import com.dsbase.core.web.pages.other.LogInPage;
import com.dsbase.core.web.pages.other.MainPage;

public class DrugInstructions_Test extends BaseTest
{	
	@Test(groups = {"DrugInstructions_Test"})
	public void DrugInstructions_TestMethod()
	{	
		// Переход на главную
		
		LogInPage authorizationPage = new MainPage(driver).redirectToLogInPage();
		MainPage mainPage = authorizationPage.logInAs("admin_auto", "123456");
		mainPage.waitForPageReady();
		
		// Поиск препарата
		
		DrugRegistryPage drugRegistryPage = mainPage.new goTo().drugRegistryPage();
		drugRegistryPage.WaitForPageReady();
		drugRegistryPage.drug_Search();
		drugRegistryPage.foundDrug_Check();

		// Переход к страничке инструкций
		
		DrugPassportPage passport = drugRegistryPage.passport_Open();
		DrugInstructionsPage instructions = passport.new GoTo().instructionsPage();
		
		// Добавление/редактирование/удаление инструкции + повторное добавление
		
		instructions.instruction_Add();
		instructions.addedInstruction_Check();
		instructions.instruction_Edit();
		instructions.editedInstruction_Check();
		instructions.instruction_Delete();
		instructions.deletedInstruction_Check();
		instructions.instruction_Add();
		instructions.addedInstruction_Check();
		
		// Добавление/редактирование/удаление документа инструкции + повторное добавление
		
		instructions.instruction_Set();
		instructions.doc_Add();
		instructions.addedDoc_Check();
		instructions.fileUnload_Check();
		instructions.doc_Edit();
		instructions.editedDoc_Check();
		instructions.doc_Delete();
		instructions.deletedDoc_check();
		instructions.doc_Add();
		instructions.addedDoc_Check();
		
		// Добавление/редактирование/удаление содержания инструкции  + повторное добавление
		
		instructions.instructionStructure_Add();
		instructions.addedInstructionStructure_Check();
		instructions.instructionStructure_Edit();
		instructions.editedInstructionStructure_Check();
		instructions.instructionStructure_Delete();
		instructions.deletedInstructionStructure_Check();
		instructions.instructionStructure_Add();
		instructions.addedInstructionStructure_Check();
		
		// Проверка недоступности кнопок добавления и пустоту гридов при отсутствии фокуса на инструкции
		
		instructions.addButtonsDisability_Check();
		instructions.deletedDoc_check();
		instructions.deletedInstructionStructure_Check();
		
		// Удаление инструкции вместе с связанными гридами
		
		instructions.instruction_Delete();
		instructions.deletedInstruction_Check();
		instructions.deletedDoc_check();
		instructions.deletedInstructionStructure_Check();
		
		instructions.userOut();
	}
	
	@AfterMethod(alwaysRun = true, groups = {"DrugInstructions_Test"})
	public void DeletionViaDatabase() throws Exception
	{
	    // Удаление инструкции(+ содержание и документ)
	    String DrugErrorMessage = DbQueries.DrugRegistryTests.DrugQueries.DeletionQueries.InstructionDeletion.ErrorMessage;	  
	    String DrugDeletionStatement = DbQueries.DrugRegistryTests.DrugQueries.DeletionQueries.InstructionDeletion.InstructionDeletionStatement;    
	    new DbStatements().SimpleStatement(sqlConnection, DrugDeletionStatement, DrugErrorMessage);
	}
}
