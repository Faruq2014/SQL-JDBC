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
 *we will learn about Foreign key in this class.
 */
public class WhatAreConstraints_ForeignKey {
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
		FOREIGNConstraints();
	
	}
	
	
	
	
	/**A FOREIGN KEY is a key used to link two tables together.
	 * A FOREIGN KEY is a field (or collection of fields) in one table
	 *  that refers to the PRIMARY KEY in another table.
	 *  The table containing the foreign key is called the child table, 
	 *  and the table containing the candidate key is called the referenced or parent table.
	 * @return
	 */
	public String FOREIGNConstraints() {
		
		try {
			
			//Ignore below two lines, in case a Teacher table already exist.
			String DropChildTable="DROP TABLE Education";
			//statement.execute(DropChildTable); // must delete child table first in order to delete parent
			
			//Ignore below two lines, in case a Teacher table already exist.
			String DropParentTable="DROP TABLE Molla";
			statement.execute(DropParentTable); // Drooping parent table
			
			String ParentTable="CREATE TABLE Molla (\r\n" + 
					"    ID int PRIMARY KEY,\r\n" + //simple primary key
					"    LastName varchar(255) NOT NULL  DEFAULT 'Molla',\r\n" +  
					"    FirstName varchar(255) NOT NULL,\r\n" + 
					"    City varchar(255) DEFAULT 'Alexandria',\r\n" + 
					"    RegestrationDate date DEFAULT GETDATE(),\r\n" +  
					"    Age int CHECK (Age>=7) \r\n" + 
					")";
			statement.execute(ParentTable);// must create parent table first in order to create child table
			
			String insert=" insert into Molla (ID,FirstName,Age)values (101,'Fabiha',10)"; // by default last name is Molla
			String insert1=" insert into Molla (ID,LastName,FirstName,Age)values (202,'Siddiqua','Libi',28)"; // user input
		     
			statement.execute(insert);
			statement.execute(insert1);
			String select="SELECT * FROM Molla";
			
			// data must exist in the parent table in order to insert to the child table.
			
			 rset=statement.executeQuery(select);
			while (rset.next()) {
				System.out.println(rset.getInt("ID")+" "+rset.getString("LastName") + " " + rset.getString("FirstName")+ " "
			+ rset.getInt("Age")+" "+rset.getString("City") + " " + rset.getString("RegestrationDate"));

			}
			System.out.println("<<<<<<<<<<<<<<<child Table>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				
			String ChildTable="CREATE TABLE Education (\r\n" + 
					"    ID int NOT NULL,\r\n" + //primary key
					"    Graduation varchar(255) NOT NULL,\r\n" +  
					"    FirstName varchar(255) NOT NULL,\r\n" + 
					"    City varchar(255),\r\n" +  
					"    Year int FOREIGN KEY REFERENCES Molla(ID)\r\n" + // foriegn key.
					")";
			statement.execute(ChildTable);
			
	
	// we can use one multiple foreign key in the ChildTable;
			
		String insert2=" insert into Education (ID,Graduation,FirstName, Year)values (101,'Elementary','Fabiha',2020)"; // by default last name is Molla
		String insert3=" insert into Education (ID,FirstName,Graduation,City)values (202,'Libi','Bechalor','Tongi')"; // user input
	    
		statement.execute(insert2);
		statement.execute(insert3);
		String select1="SELECT * FROM Education";
		
		
		 rset=statement.executeQuery(select1);
		while (rset.next()) {
			System.out.println(rset.getInt("ID")+ rset.getString("FirstName")+ " "
		+ rset.getString("Graduation")+" "+rset.getString("City") + " " + rset.getInt("Year"));

		}
		

		
		String insert4=" insert into Education (ID,FirstName,Graduation,City)values (303,'Faruq','Bechalor','Dhaka')";

		String insert5=" insert into Education (ID,FirstName,Graduation,City,year)values (404,'Faiza','Kinder Garden','Alexandria',2020)";;
		// we did not put last name, city and registration date, but they will print because of default constraint.
		statement.execute(insert4);
		statement.execute(insert5);
		
		String select2="SELECT * FROM Education";
		 rset=statement.executeQuery(select2);
		while (rset.next()) {
			System.out.println(rset.getInt("ID")+ rset.getString("FirstName")+ " "
					+ rset.getString("Graduation")+" "+rset.getString("City") + " " + rset.getInt("Year"));

		}
		
		// alter a table>>>> drop a existing FK key. 
				String dropFK="ALTER TABLE Education \r\n" + 
						"DROP CONSTRAINT FOREIGN KEY ";
			//	statement.execute(dropFK);
			
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
