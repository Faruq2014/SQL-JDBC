package com.sql.ddl.AutoIndex;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


/**The CREATE INDEX statement is used to create indexes in tables.
 * Indexes are used to retrieve data from the database more quickly than otherwise. 
 * The users cannot see the indexes, they are just used to speed up searches/queries.
 * @author Faruq
 */
public class Whatis_Index {
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
	   // we are not provinding ID because of auto increment
		
		statement.execute(insert);
		statement.execute(insert1);
		
		String index="CREATE UNIQUE INDEX index_id ON Faruq (ID)";
		//index_id=naming the index, Faruq=table name, ID=column name 
		statement.execute(index);
		
		String select="SELECT * FROM Faruq";
		 rset=statement.executeQuery(select);
	while (rset.next()) {
		System.out.println(rset.getInt("ID")+" "+rset.getString("LastName") + " " + rset.getString("FirstName")+ " "
	+ rset.getInt("Age")+" "+rset.getString("City") + " " + rset.getString("RegestrationDate"));

	}
	
	String dropIndex="DROP INDEX Faruq.index_id"; // table.index name, Faruq=table, index_id=index name.
	statement.execute(dropIndex);
	
	String select1="SELECT * FROM Faruq";
	 rset=statement.executeQuery(select1);
	
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
