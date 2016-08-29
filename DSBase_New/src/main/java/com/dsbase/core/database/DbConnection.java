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
		// Определение URL, логина и пароля для подключения
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
			System.out.println("Ошибка при подключении к БД: " + e);
		}
	}
	
	private String defineConnection(String environment)
	{
		// Объявление переменной
		String conUrl = null;
		
		// Если 'dev' площадка
		if(environment.equals("dev"))
		{
			conUrl = "jdbc:sqlserver://PAIS\\SQLEXPRESS;databaseName=DSBase";
		}
		
		// Если 'release' площадка
		else if(environment.equals("release"))
		{
			conUrl = "jdbc:sqlserver://DSBASE\\SQLEXPRESS;databaseName=DSBase";
		}
		
		// Если другое, то ошибка
		else
		{
			System.out.println("Ошибка при попытке определения URL для подключения к БД. Переменная 'environment' = " + environment);
		}
		
		// Вернуть URL подключения
		return conUrl;
	}*/
	
	protected static Connection connection;
	protected static Statement st;
	
	@Parameters({"environment"})
	public static Connection setDbConnection(String environment)
	{
		// Определение URL, логина и пароля для подключения
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
			System.out.println("Ошибка при подключении к БД: " + e);
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
		// Объявление переменной
		String conUrl = null;
		
		// Если 'dev' площадка
		if(environment.equals("dev"))
		{
			conUrl = "jdbc:sqlserver://PAIS\\SQLEXPRESS;databaseName=DSBase";
			////conUrl = "jdbc:sqlserver://PAIS\\SQLEXPRESS;databaseName=DSBase?useUnicode=true&characterEncoding=utf8";
		}
		
		// Если 'release' площадка
		else if(environment.equals("release"))
		{
			conUrl = "jdbc:sqlserver://DSBASE\\SQLEXPRESS;databaseName=DSBase";
		}
		
		// Если другое, то ошибка
		else
		{
			System.out.println("Ошибка при попытке определения URL для подключения к БД. Переменная 'environment' = " + environment);
		}
		
		// Вернуть URL подключения
		return conUrl;
	}
}
