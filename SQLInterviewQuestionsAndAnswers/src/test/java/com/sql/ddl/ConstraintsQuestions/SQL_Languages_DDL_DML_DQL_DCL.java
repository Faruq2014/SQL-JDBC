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
 *this class is executing different type or sql Languages
 *Four types of sql language>>>>>>>>>>>
 *1.data definition language
 *2.data manipulation language
 *3.data query language
 *4.data control language. 
 */
public class SQL_Languages_DDL_DML_DQL_DCL {
	Connection connection;
	Statement statement;
	ResultSet rset;
	
	/**
	 * using for teting all the methods.
	 */
	@Test
	public void testAll() {
		dataDefinationLanguage();
	    dataManipulationLanguage();
	    dataQueryLanguage();
	    dataControlLanguage();
	
	}
/**
 * this arguments is explaining how to connect the sql server data base.
 * we must add bellow dependency.
 *  <dependency>
    <groupId>com.microsoft.sqlserver</groupId>
    <artifactId>mssql-jdbc</artifactId>
    <version>8.2.2.jre8</version>
</dependency>
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


/**There are three commands for data definition language
 * 1. create table
 * 2. alter table
 * 3.drop table 
 * @return
 */
public String dataDefinationLanguage() {
	try {
		//Ignore below two lines, in case a Teacher table already exist.
		String DropPreexistTable="DROP TABLE Teacher";
		statement.execute(DropPreexistTable);
		
		//Create Command
	String createTable="CREATE TABLE Teacher (\r\n" + 
			"    TeacherID int,\r\n" + 
			"    LastName varchar(255),\r\n" + 
			"    FirstName varchar(255),\r\n" + 
			"    Address varchar(255),\r\n" + 
			"    City varchar(255),\r\n" + 
			"    Salary int\r\n" + 
			")";
	statement.execute(createTable);
	System.out.println("table created");
	
	//<<<<<<<<<<Alter Command>>>>>>>>>>>>>>>>>
	
	// alter a table>>>> add a column
	String addColumn="ALTER TABLE Teacher \r\n" + 
			"	ADD Email varchar(255)";
	statement.execute(addColumn);
	System.out.println("table altered by creating new column");
	
	// alter a table>>>> drop a column
	String droupColumn="ALTER TABLE Teacher \r\n" + 
			"	DROP COLUMN City";
	statement.execute(droupColumn);
	System.out.println("table altered by droping a column");
	
	// alter a table>>>> modify a column
	String modifyColumn="ALTER TABLE Teacher \r\n" + 
			"ALTER  COLUMN  Salary varchar(255) ";
	statement.execute(modifyColumn);
	System.out.println("table altered by modifying salary column");
	
	// <<<<<<<<<Drop Command>>>>>>>>>>>
	
	String DropTable="DROP TABLE Teacher";
	statement.execute(DropTable);
	System.out.println("droped the Teacher table");
	
	
	// creating table to execute dataManipulationLanguage() method 
	statement.execute(createTable);
	System.out.println("table created again");
	
	} catch (SQLException e) {
	
		e.printStackTrace();
	}
	return null;
	
	
}
/**Ther are three commands for data manipulation language.
 * 1. insert
 * 2. update
 * 3.delete
 * @return
 */
public String dataManipulationLanguage() {
	try {
		//<<<<<<<<<<Insert Command>>>>>>>>>>>>>>>
	String insert="insert into Teacher"
			+
			"(TeacherID,LastName,FirstName,Address,City,Salary)"
			+
			"values(1,'Molla','Faruq','Pinelake CT','Alexandria',5000)";
	
		statement.executeUpdate(insert);
		System.out.println("insert complete");
		
		//<<<<<<<<<<<Update Command>>>>>>>>>>>
		
		 String update="update Teacher set FirstName= 'Faiza' where TeacherID=1";
		 statement.executeUpdate(update);
		 System.out.println("update complete");
		 
		 //<<<<<<<<<<<Delete Command>>>>>>>>>>>>>
		 String delete="delete from Teacher where TeacherID=1";
		 statement.executeUpdate(delete);
		 System.out.println("delete complete");
		 
		 //insering again to execute next method, dataQueryLanguage() 
		 statement.executeUpdate(insert);
		 System.out.println("insert complete again");    
	} catch (SQLException e) {
		e.printStackTrace();
	}
	System.out.println("insert complete");
	
	return null;
	
}


/**
 * @return
 * select is the only command of Data Query Language.
 */
public String dataQueryLanguage() {
	try {
	//<<<<<<<<<<<<<Select Command>>>>>>>>>>>
		String query= "SELECT * FROM [FaruqAcademy].[dbo].[Teacher] ";
		rset=statement.executeQuery(query);
		while(rset.next()) {
			System.out.println(rset.getString("FirstName")+" "+rset.getString("LastName"));
		}
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return null;

}

/**there are two commands for data control language
 * 1. grant
 * 2. rvoke
 * @return
 */
public String dataControlLanguage() {
	System.out.println(" grant and revoke is reading, writing, editing and deleting permission which\r\n" + 
			"	  is handled by the data base administrator.");

	 
	
	return null;
	
}

/**
 * this method is responsible to close the connection.
 * it is recommended to close connection after every use.
 */
@AfterClass
public void closeConnection() {
	try {
		connection.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}

}
