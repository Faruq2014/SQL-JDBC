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
 *we will learn about primary key in this class.
 */
public class WhatAreConstraints_PrimaryKey {
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
		DefaultConstraints();
	
	}
	
	
	
	/**The PRIMARY KEY constraint uniquely identifies each record in a table.
	 * Primary keys must contain UNIQUE values, and cannot contain NULL values.
	 * A table can have only ONE primary key; and in the table, 
	 * this primary key can consist of single or multiple columns (fields).
	 * @return
	 */
	public String DefaultConstraints() {
		
		try {
			
			//Ignore below two lines, in case a Teacher table already exist.
			String DropPreexistTable="DROP TABLE Faruq";
			statement.execute(DropPreexistTable);
				
			String UniqueConstrain="CREATE TABLE Faruq (\r\n" + 
					"    ID int NOT NULL,\r\n" +                             //primary key will  add  below 
					"    LastName varchar(255) NOT NULL  DEFAULT 'Molla',\r\n" +  
					"    FirstName varchar(255) NOT NULL,\r\n" + 
					"    City varchar(255) DEFAULT 'Alexandria',\r\n" + 
					"    RegestrationDate date DEFAULT GETDATE(),\r\n" +  
					"    Age int CHECK (Age>=7) \r\n" + 
					")";
			
			String UniqueConstrain1="CREATE TABLE Faruq (\r\n" + 
					"    ID int PRIMARY KEY,\r\n" + //simple primary key
					"    LastName varchar(255) NOT NULL  DEFAULT 'Molla',\r\n" +  
					"    FirstName varchar(255) NOT NULL,\r\n" + 
					"    City varchar(255) DEFAULT 'Alexandria',\r\n" + 
					"    RegestrationDate date DEFAULT GETDATE(),\r\n" +  
					"    Age int CHECK (Age>=7) \r\n" + 
					")";
	
	// we only can use one primary key in a table, so we will use the first one.
			statement.execute(UniqueConstrain);
			
		String insert=" insert into Faruq (ID,FirstName,Age)values (101,'Fabiha',10)"; // by default last name is Molla
		String insert1=" insert into Faruq (ID,LastName,FirstName,Age)values (2,'Siddiqua','Libi',28)"; // user input
	     // we did not provide last name and date. 
		// last name is by default Molla, but if we provide the last name then it will prefer the user input.
		// last name is also not null. 
		// city by default is Alexandria but it will allow null. but if you do not provide, it will automatecaly 
		// provide Alexandria. as a result it is basically not null.
		// get date function will collect the date from system.
		statement.execute(insert);
		statement.execute(insert1);
		String select="SELECT * FROM Faruq";
		
		
		 rset=statement.executeQuery(select);
		while (rset.next()) {
			System.out.println(rset.getInt("ID")+" "+rset.getString("LastName") + " " + rset.getString("FirstName")+ " "
		+ rset.getInt("Age")+" "+rset.getString("City") + " " + rset.getString("RegestrationDate"));

		}
		// alter a table>>>> argument for creating combine PRIMARY KEY.
		String addPK="ALTER TABLE Faruq \r\n" + 
				"ADD CONSTRAINT PK_Faruq PRIMARY KEY (ID,LastName)";//PK_Faruq
		statement.execute(addPK);
		
		// only one primary key is allow. 
		// if you use second one, first one must be blocked.

		
		String insert2=" insert into Faruq (ID,FirstName,Age)values (3,'Faiza',7)";

		String insert3=" insert into Faruq (ID,FirstName,Age)values (4,'Faruq',37)";
		// we did not put last name, city and registration date, but they will print because of default constraint.
		statement.execute(insert2);
		statement.execute(insert3);
		
		String select1="SELECT * FROM Faruq";
		 rset=statement.executeQuery(select1);
		while (rset.next()) {
			System.out.println(rset.getInt("ID")+" "+rset.getString("LastName") + " " + rset.getString("FirstName")+ " "
					+ rset.getInt("Age")+ " " + rset.getString("RegestrationDate")+" " + rset.getString("City"));

		}
		
		// alter a table>>>> drop a existing PK key. 
				String dropPK="ALTER TABLE Faruq \r\n" + 
						"DROP CONSTRAINT PK_Faruq ";
				statement.execute(dropPK);
			
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
