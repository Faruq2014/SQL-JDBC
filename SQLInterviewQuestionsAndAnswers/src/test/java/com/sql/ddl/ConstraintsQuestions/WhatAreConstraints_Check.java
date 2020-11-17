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
public class WhatAreConstraints_Check {
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
		CheckConstraints();
	
	}
	
	/**The CHECK constraint is used to limit the value range that can be placed in a column.
	 * @return
	 */
	public String CheckConstraints() {
		
		try {
			
			//Ignore below two lines, in case a Teacher table already exist.
			String DropPreexistTable="DROP TABLE Faruq";
			statement.execute(DropPreexistTable);
			
			String checkConstrain="CREATE TABLE Faruq (\r\n" + 
					"    ID int NOT NULL UNIQUE,\r\n" + 
					"    LastName varchar(255) NOT NULL,\r\n" + 
					"    FirstName varchar(255) NOT NULL,\r\n" + 
					"    City varchar(255) NOT NULL,\r\n" + 
					"    Age int CHECK (Age>=18)\r\n" + 
					")";
			statement.execute(checkConstrain);
			
		String insert=" insert into Faruq (ID,LastName,FirstName,City,Age)values (1,'Molla','Faruq','New York',null)";
		// it should not allow null value for ID,LastName,FirstName.
		// ID must be unique means, can not insert duplicate.
		// Age should allow null value but not less than 18.
		statement.execute(insert);
		String select="SELECT * FROM Faruq";
		 rset=statement.executeQuery(select);
		while (rset.next()) {
			System.out.println(rset.getString("LastName") + " " + rset.getString("FirstName")+ " " + rset.getString("Age"));

		}
		// alter a table>>>> argument for creating combine check key.
		String addCheck="ALTER TABLE Faruq \r\n" + 
				"ADD CONSTRAINT City_LN CHECK (LastName='Molla' AND City='Alexandria')";
		//statement.execute(addCheck);

		
		String insert1=" insert into Faruq (ID,LastName,FirstName,City,Age)values (3,'Molla','Libi','Alexandria',28)";
		
		statement.execute(insert1);
		String select1="SELECT * FROM Faruq";
		 rset=statement.executeQuery(select1);
		while (rset.next()) {
			System.out.println(rset.getString("LastName") + " " + rset.getString("FirstName")+ " " + rset.getString("Age"));

		}
		
		// alter a table>>>> drop a existing check key.
				String dropCheck="ALTER TABLE Faruq \r\n" + 
						"DROP CONSTRAINT City_LN ";
				//statement.execute(dropCheck);
			
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
