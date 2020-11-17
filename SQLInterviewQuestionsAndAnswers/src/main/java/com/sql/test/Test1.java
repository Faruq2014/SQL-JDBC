package com.sql.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Test1 {

	public static void main(String[] args) {
	System.out.println("Bismillah");
	Connection con;
	try {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		String username="Faruq";
		String password="1234";
		System.out.println("command completed successfuly");
String connString ="jdbc:sqlserver://DESKTOP-INQUNRP\\MSSQLSERVER;"+"databaseName=FaruqAcademy;";
		System.out.println("server connected");
	 con=	DriverManager.getConnection(connString,username,password);
	System.out.println("connect completed successfuly");
	Statement stmt=con.createStatement();
	String query="SELECT * FROM [FaruqAcademy].[dbo].[Students] ";
	String query1="SELECT * FROM Students";
	
	ResultSet rs=stmt.executeQuery(query1);
	while (rs.next()) {
		System.out.println(rs.getString("firstname")+""+rs.getString("lastname"));
	
	}
          con.close();
	} catch (Exception e) {
	e.printStackTrace();
	
	}
	
	
	}

}
