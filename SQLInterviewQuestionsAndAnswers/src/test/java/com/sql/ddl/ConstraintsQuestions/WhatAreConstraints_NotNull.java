package com.sql.ddl.ConstraintsQuestions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author Faruq
 * 
 * SQL constraints are used to specify rules for data in a table;
 *1. not null
 *2. unique
 *3. primary key
 *4.foreign key
 *5. check
 *6. default
 */
public class WhatAreConstraints_NotNull {
	Connection connection;
	Statement statement;
	ResultSet rset;
	
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
	
	@Test
	public void testMethods() {
		notNullConstraints();
	
	}
	
	public String notNullConstraints() {
		
		try {
			
			//Ignore below two lines, in case a Teacher table already exist.
			String DropPreexistTable="DROP TABLE Faruq";
			statement.execute(DropPreexistTable);
			
		String notNull="CREATE TABLE Faruq (\r\n" + 
				"    ID int NOT NULL,\r\n" + 
				"    LastName varchar(255) NOT NULL,\r\n" + 
				"    FirstName varchar(255) NOT NULL,\r\n" + 
				"    Age varchar(255)\r\n" + 
				")";
	
			statement.execute(notNull);
			
		String insert=" insert into Faruq (ID,LastName,FirstName,Age)values (2,'Molla','Fabiha',null)";
		// it should not allow null value for ID,LastName,FirstName.
		// but it should allow null value for age.
		statement.execute(insert);
		String select="SELECT * FROM Faruq";
		ResultSet rset=statement.executeQuery(select);
		while (rset.next()) {
			System.out.println(rset.getString("LastName") + " " + rset.getString("FirstName")+ " " + rset.getString("Age"));

		}
		// alter a table>>>> modify a column
		String modifyColumn="ALTER TABLE Faruq \r\n" + 
				"ALTER  COLUMN  Age int NOT NULL";
		//statement.execute(modifyColumn);
		
		String insert1=" insert into Faruq (ID,LastName,FirstName,Age)values (3,'Molla','Faiza','7')";
		// it should not allow null value for ID,LastName,FirstName.
		// but it should allow null value for age.
		statement.execute(insert1);
		String select1="SELECT * FROM Faruq";
		ResultSet rset1=statement.executeQuery(select1);
		while (rset1.next()) {
			System.out.println(rset1.getString("LastName") + " " + rset1.getString("FirstName")+ " " + rset1.getString("Age"));

		}
		
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		
		return null;
		
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
