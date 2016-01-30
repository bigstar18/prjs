package org.hxx.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DBUtil {
	public static Connection getConnection() {
		Connection con = null;
		String username = PropertiesUtil.getProp().getProperty("username");
		String password = PropertiesUtil.getProp().getProperty("password");
		String url = PropertiesUtil.getProp().getProperty("url");
		String driverName = PropertiesUtil.getProp().getProperty("driver");
		try {
			Class.forName(driverName);   
			con = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public static void close(Connection con) {
		try {
			if(con!=null) con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(java.sql.PreparedStatement ps) {
		try {
			if(ps!=null) ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet rs) {
		try {
			if(rs!=null) rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
