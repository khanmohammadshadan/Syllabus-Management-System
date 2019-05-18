package com.sk.dbfiles;

import java.sql.*;

public class JDBCUtil {

	public static Connection getOraConnection() throws SQLException,ClassNotFoundException{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection = DriverManager.getConnection(
		"jdbc:oracle:thin:@localhost:1521:ORCL", "SYSTEM", "mypassword");
		return connection;
	}
	
	public static Connection getMySQLConnection() throws SQLException,ClassNotFoundException{
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/jdbc";
		Connection connection = DriverManager.getConnection(url, "SYSTEM", "mypassword");
		return connection;
	}
	
	public static void cleanup(Statement st,Connection con) {
		try {
			if(st!=null)
				st.close();
			if(con!=null)
				con.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}//end of statement cleanup

	public static void cleanup(ResultSet rs,Statement st, Connection con) {
		try {
			if(rs!=null) rs.close();
			if(st!=null) st.close();
			if(con!=null) con.close();
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}// end of resultset cleanup
	
}
