package com.dsbase.core.database;

import java.sql.Connection;
import java.sql.Statement;

public class DbStatements
{
	private Statement st;
	
	public void SimpleStatement(Connection sqlConnection, String myQuery, String errorMessage) throws Exception
	{
		try 
		{
			// Insert statement
			st = sqlConnection.createStatement();
			st.setQueryTimeout(30);
			st.executeUpdate(myQuery);
		} 
		catch(Exception e)
		{			
			throw new Exception(errorMessage + e.getMessage() + "\r\n\r\nСтэк:\r\n" + e.getStackTrace());
		}
		finally
		{
			st.close();
		}
	}
}
