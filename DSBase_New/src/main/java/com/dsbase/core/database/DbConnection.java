package com.dsbase.core.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.annotations.Parameters;

public class DbConnection 
{	
/*	private String environment;
	
	protected Connection connection;
	protected static Statement st;
	protected ResultSet rs;
	
	@Parameters({"environment"})
	private DbConnection(String environment)
	{
		this.environment = environment;
	}
	
	public DbConnection()
	{
		// ����������� URL, ������ � ������ ��� �����������
		String conUrl = defineConnection(environment);
		String Login = "dsbase_web_user";
		String Password = "dsbase_web_user";
		
		try 
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager.getConnection(conUrl, Login, Password);
			st = connection.createStatement();
		} 
		
		catch (Exception e) 
		{
			System.out.println("������ ��� ����������� � ��: " + e);
		}
	}
	
	private String defineConnection(String environment)
	{
		// ���������� ����������
		String conUrl = null;
		
		// ���� 'dev' ��������
		if(environment.equals("dev"))
		{
			conUrl = "jdbc:sqlserver://PAIS\\SQLEXPRESS;databaseName=DSBase";
		}
		
		// ���� 'release' ��������
		else if(environment.equals("release"))
		{
			conUrl = "jdbc:sqlserver://DSBASE\\SQLEXPRESS;databaseName=DSBase";
		}
		
		// ���� ������, �� ������
		else
		{
			System.out.println("������ ��� ������� ����������� URL ��� ����������� � ��. ���������� 'environment' = " + environment);
		}
		
		// ������� URL �����������
		return conUrl;
	}*/
	
	protected static Connection connection;
	protected static Statement st;
	
	@Parameters({"environment"})
	public static Connection setDbConnection(String environment)
	{
		// ����������� URL, ������ � ������ ��� �����������
		String conUrl = defineConnection(environment);
		String Login = "dsbase_web_user";
		String Password = "dsbase_web_user";
		
		try 
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager.getConnection(conUrl, Login, Password);
			st = connection.createStatement();
			st.close();
		} 
		
		catch (Exception e) 
		{
			System.out.println("������ ��� ����������� � ��: " + e);
		}
		
		return connection;
	}
	
	public static void closeConnection(Connection sqlConnection)
	{
		try 
		{
			sqlConnection.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	private static String defineConnection(String environment)
	{
		// ���������� ����������
		String conUrl = null;
		
		// ���� 'dev' ��������
		if(environment.equals("dev"))
		{
			conUrl = "jdbc:sqlserver://PAIS\\SQLEXPRESS;databaseName=DSBase";
			////conUrl = "jdbc:sqlserver://PAIS\\SQLEXPRESS;databaseName=DSBase?useUnicode=true&characterEncoding=utf8";
		}
		
		// ���� 'release' ��������
		else if(environment.equals("release"))
		{
			conUrl = "jdbc:sqlserver://DSBASE\\SQLEXPRESS;databaseName=DSBase";
		}
		
		// ���� ������, �� ������
		else
		{
			System.out.println("������ ��� ������� ����������� URL ��� ����������� � ��. ���������� 'environment' = " + environment);
		}
		
		// ������� URL �����������
		return conUrl;
	}
}
