package com.dsbase.core.web.pages.administration;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dsbase.core.web.CustomMethods;
import com.dsbase.core.web.WebPage;
import com.dsbase.core.web.elements.Button;
import com.dsbase.core.web.elements.Custom;
import com.dsbase.core.web.elements.TextInput;

public class AuditPage extends WebPage<AuditPage> 
{
private static final String PAGE_URL = BASE_URL + "/User";
	
	public AuditPage(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public AuditPage load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return getLoginInput().isAvailable() && 
			   getSearchButton().isAvailable();
	}
	
	public void operationsSearch()
	{
		getLoginInput().inputText("auto_user");
		getSearchButton().click();
		simpleWait(1);
		
		// �������� ��������� �����
		waitUntilUnblocked(getGridTable());
	}
	
	public void operationsCheck()
	{
		// ����������� ������� ����
		String currentDate = new CustomMethods().getCurrentDate();
		
		// ����������� IP
		String myIp = getLocalAddress().toString().replace("/", "");
		
		// ����������� ��������� ��������
		String[][] ExpectedValues = new String [2][];
		ExpectedValues[0] = new String[] {currentDate, 
								  		  "����� ������ ������������", 
								  		  "������������ �������� ����� �� �������", 
								  		  "auto_user", 
								  		  "�������� ������� ��������",
								  		  myIp};
		
		ExpectedValues[1] = new String[] {currentDate, 
				  						  "������ ������ ������������", 
				  						  "�������� ���� � �������", 
				  						  "auto_user", 
				  						  "�������� ������� ��������",
				  						  myIp};
		
		// �������� �������� �� �����
		String[][] ActualValues = new CustomMethods().new Grid().GetSpecificRows(getGridBody(), 2);
		
		// �������� �������� �����
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	public AdministrationPage BackToAdministrationPage()
	{
		getBackToAdministrationButton().click();
		return new AdministrationPage(driver).waitUntilAvailable();
	}
	
	private InetAddress getLocalAddress(){
        try {
            Enumeration<NetworkInterface> b = NetworkInterface.getNetworkInterfaces();
            while( b.hasMoreElements()){
                for ( InterfaceAddress f : b.nextElement().getInterfaceAddresses())
                    if ( f.getAddress().isSiteLocalAddress())
                        return f.getAddress();
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }
	
	private TextInput getLoginInput()
	{
		return new TextInput(driver, By.id("Login"));
	}
	
	private Button getSearchButton()
	{
		return new Button(driver, By.id("btnSearch"));
	}
	
	private Button getBackToAdministrationButton()
	{
		return new Button(driver, By.xpath("//a[@href='/User/']"));
	}
	
	private Custom getGridTable()
	{
		return new Custom(driver, By.id("list"));
	}
	
	private WebElement getGridBody()
	{
		return driver.findElement(By.xpath("//*[@id='list']/tbody"));
	}
}
