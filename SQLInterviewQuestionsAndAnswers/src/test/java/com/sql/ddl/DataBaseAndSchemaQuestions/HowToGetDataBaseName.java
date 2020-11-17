package com.sql.ddl.DataBaseAndSchemaQuestions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class HowToGetDataBaseName {
	
	Connection connection;
	Statement statement;
	ResultSet rset;
	
	/**
	 *Connection to the data base. 
	 */
	@BeforeClass
	public void getConnection() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String userName="Faruq";
			String Password="1234";
			String url="jdbc:sqlserver://DESKTOP-INQUNRP\\MSSQLSERVER;"+"databaseName=FaruqAcademy;";
			connection=DriverManager.getConnection(url, userName, Password);
			statement=connection.createStatement();
			System.out.println("connected to the data base");
			
		} catch (ClassNotFoundException | SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	/**This testng testing unit.
	 * 
	 */
	@Test
	public void testMethods() {
		getDataBaseName();
	}
	
	public String getDataBaseName() {
		String nameOfDataBase= "USE master\r\n" + 
				"GO\r\n" + 
				"EXEC sp_databases";
		try {
			ResultSet rset=statement.executeQuery("name");
			
			while (rset.next()) {
				System.out.println(rset.getString(nameOfDataBase));

		}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nameOfDataBase;
		
	}
	
	@AfterClass
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
