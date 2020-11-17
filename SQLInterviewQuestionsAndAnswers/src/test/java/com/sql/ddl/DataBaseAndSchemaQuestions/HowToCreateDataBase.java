package com.sql.ddl.DataBaseAndSchemaQuestions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * this class is about how to 
 * 1. create database with regular statement, if exist statement, if not exist statement
 * 2. rename data base with store procedure
 * 3. drop data base with is exist statement. 
 * @author Faruq
 *
 */
public class HowToCreateDataBase {
	
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
		createDataBaseIfExist();
	System.out.println("created");
	reNameDataBase();
	System.out.println("Renamed");
	dropDataBaseIfExist();
	System.out.println("Deleted");
	
	
	}
	
	/**there is basically two statement
	 * regularCreateStatement= just a regular statement to create data base
	 * create= if data base does not exist than create a data base.
	 * ifNotExist= this is the execution of create statement. 
	 * @return
	 */
	public String createDataBaseIfNotExist() {
		String regularCreateStatement="CREATE DATABASE Database_Name";// this is just a regular statement to create. 
String create=
"IF NOT EXISTS (SELECT name FROM master.dbo.sysdatabases  WHERE name = N'New_Database') CREATE DATABASE [New_Database]";
// full statement just for record.

		String ifNotExist="IF NOT EXISTS \r\n" + 
				"   (\r\n" + 
				"     SELECT name FROM master.dbo.sysdatabases \r\n" + 
				"     WHERE name = N'Student'\r\n" + 
				"    )\r\n" + 
				"CREATE DATABASE [N'Student]";

		try {
			statement.execute(ifNotExist);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ifNotExist;
		
	}
	
	
	/** if database exist with the same name do not create 
	 * if it does not exist create. 
	 * @return
	 */
	public String createDataBaseIfExist() {
		String ifExist= "IF EXISTS \r\n" + 
				"   (\r\n" + 
				"     SELECT name FROM master.dbo.sysdatabases \r\n" + 
				"    WHERE name = N'Student'\r\n" + 
				"    )\r\n" + 
				"BEGIN\r\n" + 
				"    SELECT 'Database Name already Exist' AS Message\r\n" + 
				"END\r\n" + 
				"ELSE\r\n" + 
				"BEGIN\r\n" + 
				"    CREATE DATABASE [N'Student]\r\n" + 
				"    SELECT 'New Database is Created'\r\n" + 
				"END";
		try {
			statement.execute(ifExist);
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		
		
		return ifExist;
	}
	
	
	/**
	 * There is always store procedure for rename a data base =SP_RENAMEDB 
	 * we are using that store procedure and inputing old name and new name.
	 * @return
	 */
	public String reNameDataBase() {
		String rename="SP_RENAMEDB  [N'Student],[Student]";
		
		try {
			statement.execute(rename);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rename;
		
	}
	
	
	/**we only can delete if data base is exist.
	 * we are writing the query to drop the data base. 
	 * @return
	 */
	public String dropDataBaseIfExist() {
		String dropDataBaseIsExist="IF EXISTS \r\n" + 
				"   (\r\n" + 
				"     SELECT name FROM master.dbo.sysdatabases \r\n" + 
				"     WHERE name = 'Student' \r\n" + 
				"    )\r\n" + 
				"DROP DATABASE [Student]";
		
		
		try {
			statement.execute(dropDataBaseIsExist);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dropDataBaseIsExist;
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
