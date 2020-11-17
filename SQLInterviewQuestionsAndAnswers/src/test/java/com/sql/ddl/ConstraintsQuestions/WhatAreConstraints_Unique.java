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
 *7. index.
 */
public class WhatAreConstraints_Unique {
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
		UniqueConstraints();
	
	}
	
	/**The UNIQUE constraint ensures that all values in a column are different.
	 * @return
	 */
	public String UniqueConstraints() {
		
		try {
			
			//Ignore below two lines, in case a Teacher table already exist.
			String DropPreexistTable="DROP TABLE Faruq";
			statement.execute(DropPreexistTable);
			
			String UniqueConstrain="CREATE TABLE Faruq (\r\n" + 
					"    ID int NOT NULL UNIQUE,\r\n" + 
					"    LastName varchar(255) NOT NULL,\r\n" + 
					"    FirstName varchar(255) NOT NULL,\r\n" + 
					"    Age varchar(255)\r\n" + 
					")";
			
			
	
	
			statement.execute(UniqueConstrain);
			
		String insert=" insert into Faruq (ID,LastName,FirstName,Age)values (1,'Molla','Fabiha',null)";
		// it should not allow null value for ID,LastName,FirstName.
		// ID must be unique means, can not insert duplicate.
		// Age should allow null value.
		statement.execute(insert);
		String select="SELECT * FROM Faruq";
		
		
		 rset=statement.executeQuery(select);
		while (rset.next()) {
			System.out.println(rset.getString("LastName") + " " + rset.getString("FirstName")+ " " + rset.getInt("Age"));

		}
		// alter a table>>>> argument for creating combine unique key.
		String addUnique="ALTER TABLE Faruq \r\n" + 
				"ADD CONSTRAINT ID_LN UNIQUE (ID,LastName)";
		statement.execute(addUnique);

		
		String insert1=" insert into Faruq (ID,LastName,FirstName,Age)values (3,'Molla','Faiza','seven')";
		
		statement.execute(insert1);
		String select1="SELECT * FROM Faruq";
		 rset=statement.executeQuery(select1);
		while (rset.next()) {
			System.out.println(rset.getString("LastName") + " " + rset.getString("FirstName")+ " " + rset.getString("Age"));

		}
		
		// alter a table>>>> drop a existing unique key.
				String dropUnique="ALTER TABLE Faruq \r\n" + 
						"DROP CONSTRAINT ID_LN";
				statement.execute(dropUnique);
			
		System.out.println("<<<<<<<<<<<<<<Done>>>>>>>>>>>>>>>>>>>>");
			
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
