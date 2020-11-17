package com.sql.ddl.AutoIndex;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


/**
 * Auto-increment allows a unique number to be generated automatically 
 * when a new record is inserted into a table.Often this is the primary key field that 
 * we would like to be created automatically every time a new record is inserted.
 * @author Faruq
 *
 */
public class Whatis_AutoIncrement {
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
		autoIncrement();
	
	}
	

	public String autoIncrement() {
		
		try {
			
			//Ignore below two lines, in case a Teacher table already exist.
			String DropPreexistTable="DROP TABLE Faruq";
			statement.execute(DropPreexistTable);
				
			String AutoIncrement="CREATE TABLE Faruq (\r\n" + 
					"    ID int IDENTITY(1,1) PRIMARY KEY,\r\n" + //primary key wuth auto increment
					"    LastName varchar(255) NOT NULL  DEFAULT 'Molla',\r\n" +  
					"    FirstName varchar(255) NOT NULL,\r\n" + 
					"    City varchar(255) DEFAULT 'Alexandria',\r\n" + 
					"    RegestrationDate date DEFAULT GETDATE(),\r\n" +  
					"    Age int CHECK (Age>=7) \r\n" + 
					")";	
	// IDENTITY(1,1)== it will start from 1 and auto increment by 1. 
	//IDENTITY(100,10)==it will start from 100 and auto increment by 10.
			// you do not have to provide ID number for insertion.
			statement.execute(AutoIncrement);
			System.out.println(">>>>>>>Table Creatrd>>>>>>>>>>>");
			
		String insert=" insert into Faruq (FirstName,Age)values ('Faiza',7)"; // by default last name is Molla
		String insert1=" insert into Faruq (LastName,FirstName,Age)values ('Siddiqua','Libi',28)"; // user input
	   // we are not provinding ID because of auto increment
		statement.execute(insert);
		statement.execute(insert1);
		String select="SELECT * FROM Faruq";
		
		
		 rset=statement.executeQuery(select);
		while (rset.next()) {
			System.out.println(rset.getInt("ID")+" "+rset.getString("LastName") + " " + rset.getString("FirstName")+ " "
		+ rset.getInt("Age")+" "+rset.getString("City") + " " + rset.getString("RegestrationDate"));

		}
			
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
