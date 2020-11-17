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
 * In SQL, a view is a virtual table based on the result-set of an SQL statement.
 * A view contains rows and columns, just like a real table. The fields in a view are fields from
 *  one or more real tables in the database.You can add SQL functions, WHERE, and JOIN statements to a view
 *  and present the data as if the data were coming from one single table.
 * @author Faruq
 *
 */
public class Whatis_Views {
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
		views();
	
	}
	

	public String views() {
		
		try {
			
			//Ignore below two lines, in case a Teacher table already exist.
			String DropPreexistTable="DROP TABLE Faruq";
			statement.execute(DropPreexistTable);
				
			String createTable="CREATE TABLE Faruq (\r\n" + 
					"    ID int IDENTITY(1,1) PRIMARY KEY,\r\n" + //primary key wuth auto increment
					"    LastName varchar(255) NOT NULL  DEFAULT 'Molla',\r\n" +  
					"    FirstName varchar(255) NOT NULL,\r\n" + 
					"    City varchar(255) DEFAULT 'Alexandria',\r\n" + 
					"    RegestrationDate date DEFAULT GETDATE(),\r\n" +  
					"    Age int CHECK (Age>=7) \r\n" + 
					")";	
	
			statement.execute(createTable);
			System.out.println(">>>>>>>Table Creatrd>>>>>>>>>>>");
			
		String insert=" insert into Faruq (FirstName,Age)values ('Faiza',7)"; 
		String insert1=" insert into Faruq (LastName,FirstName,Age)values ('Siddiqua','Libi',28)"; 
 	    statement.execute(insert);
		statement.execute(insert1);
		String select="SELECT * FROM Faruq";
		 rset=statement.executeQuery(select);
	while (rset.next()) {
		System.out.println(rset.getInt("ID")+" "+rset.getString("LastName") + " " + rset.getString("FirstName")+ " "
	+ rset.getInt("Age")+" "+rset.getString("City") + " " + rset.getString("RegestrationDate"));

	}
	
	// creating views. with where statement. 
		
		String View="CREATE VIEW [From Alexandria] AS\r\n" + 
				"SELECT FirstName, LastName,ID\r\n" + 
				"FROM Faruq\r\n" + 
				"WHERE City = 'Alexandria'";
	
		statement.execute(View);
		
		String select1="SELECT * FROM [From Alexandria]"; // using views and we can reuse it.
		 rset=statement.executeQuery(select1);
		
		while (rset.next()) {
			System.out.println(rset.getInt("ID")+" "+rset.getString("LastName") + " " + rset.getString("FirstName"));

		}
		
		
	String Dropview="DROP VIEW [From Alexandria]"; // table.index name, Faruq=table, index_id=index name.
	statement.execute(Dropview);
	
	
			
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
