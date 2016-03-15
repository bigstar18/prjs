// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi
// Source File Name: DerbyDBGen.java

package cn.com.agree.eteller.generic.utils;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

public class DerbyDBGen {

	private String framework;
	private String driver;
	private String protocol;

	public DerbyDBGen() {
		framework = "embedded";
		driver = "org.apache.derby.jdbc.EmbeddedDriver";
		protocol = "jdbc:derby:directory:E:/Workspace/eteller3_pingan/WebRoot/WEB-INF/db/derbyDB";
	}

	public static void main(String args[]) throws IOException {
		(new DerbyDBGen()).excuteSql(FileUtils.readFileToString(new File("E:/Workspace/eteller3_pingan/sql/create_table.sql")));
	}

	void go(String args[]) {
		Connection conn;
		ArrayList statements;
		ResultSet rs;
		parseArguments(args);
		System.out.println((new StringBuilder("SimpleApp starting in ")).append(framework).append(" mode").toString());
		loadDriver();
		conn = null;
		statements = new ArrayList();
		PreparedStatement psInsert = null;
		PreparedStatement psUpdate = null;
		Statement s = null;
		rs = null;
		try {
			Properties props = new Properties();
			props.put("user", "pingan");
			props.put("password", "pa888888");
			String dbName = "derbyDB";
			conn = DriverManager.getConnection((new StringBuilder(String.valueOf(protocol))).append(dbName).append(";create=true").toString(), props);
			System.out.println((new StringBuilder("Connected to and created database ")).append(dbName).toString());
			conn.setAutoCommit(false);
			s = conn.createStatement();
			statements.add(s);
			s.execute("create table location(num int, addr varchar(40))");
			System.out.println("Created table location");
			psInsert = conn.prepareStatement("insert into location values (?, ?)");
			statements.add(psInsert);
			psInsert.setInt(1, 1956);
			psInsert.setString(2, "Webster St.");
			psInsert.executeUpdate();
			System.out.println("Inserted 1956 Webster");
			psInsert.setInt(1, 1910);
			psInsert.setString(2, "Union St.");
			psInsert.executeUpdate();
			System.out.println("Inserted 1910 Union");
			psUpdate = conn.prepareStatement("update location set num=?, addr=? where num=?");
			statements.add(psUpdate);
			psUpdate.setInt(1, 180);
			psUpdate.setString(2, "Grand Ave.");
			psUpdate.setInt(3, 1956);
			psUpdate.executeUpdate();
			System.out.println("Updated 1956 Webster to 180 Grand");
			psUpdate.setInt(1, 300);
			psUpdate.setString(2, "Lakeshore Ave.");
			psUpdate.setInt(3, 180);
			psUpdate.executeUpdate();
			System.out.println("Updated 180 Grand to 300 Lakeshore");
			rs = s.executeQuery("SELECT num, addr FROM location ORDER BY num");
			boolean failure = false;
			if (!rs.next()) {
				failure = true;
				reportFailure("No rows in ResultSet");
			}
			int number;
			if ((number = rs.getInt(1)) != 300) {
				failure = true;
				reportFailure((new StringBuilder("Wrong row returned, expected num=300, got ")).append(number).toString());
			}
			if (!rs.next()) {
				failure = true;
				reportFailure("Too few rows");
			}
			if ((number = rs.getInt(1)) != 1910) {
				failure = true;
				reportFailure((new StringBuilder("Wrong row returned, expected num=1910, got ")).append(number).toString());
			}
			if (rs.next()) {
				failure = true;
				reportFailure("Too many rows");
			}
			if (!failure)
				System.out.println("Verified the rows");
			s.execute("drop table location");
			System.out.println("Dropped table location");
			conn.commit();
			System.out.println("Committed the transaction");
			if (framework.equals("embedded"))
				try {
					DriverManager.getConnection("jdbc:derby:;shutdown=true");
				} catch (SQLException se) {
					if (se.getErrorCode() == 50000 && "XJ015".equals(se.getSQLState())) {
						System.out.println("Derby shut down normally");
					} else {
						System.err.println("Derby did not shut down normally");
						printSQLException(se);
					}
				}
		} catch (SQLException sqle) {
			printSQLException(sqle);
		}

		finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
			} catch (SQLException sqle) {
				printSQLException(sqle);
			}
			int i = 0;
			while (!statements.isEmpty()) {
				Statement st = (Statement) statements.remove(i);
				try {
					if (st != null) {
						st.close();
						st = null;
					}
				} catch (SQLException sqle) {
					printSQLException(sqle);
				}
			}
		}

	}

	private void loadDriver() {
		try {
			Class.forName(driver).newInstance();
			System.out.println("Loaded the appropriate driver");
		} catch (ClassNotFoundException cnfe) {
			System.err.println((new StringBuilder("\nUnable to load the JDBC driver ")).append(driver).toString());
			System.err.println("Please check your CLASSPATH.");
			cnfe.printStackTrace(System.err);
		} catch (InstantiationException ie) {
			System.err.println((new StringBuilder("\nUnable to instantiate the JDBC driver ")).append(driver).toString());
			ie.printStackTrace(System.err);
		} catch (IllegalAccessException iae) {
			System.err.println((new StringBuilder("\nNot allowed to access the JDBC driver ")).append(driver).toString());
			iae.printStackTrace(System.err);
		}
	}

	private void reportFailure(String message) {
		System.err.println("\nData verification failed:");
		System.err.println((new StringBuilder(String.valueOf('\t'))).append(message).toString());
	}

	public static void printSQLException(SQLException e) {
		for (; e != null; e = e.getNextException()) {
			System.err.println("\n----- SQLException -----");
			System.err.println((new StringBuilder("  SQL State:  ")).append(e.getSQLState()).toString());
			System.err.println((new StringBuilder("  Error Code: ")).append(e.getErrorCode()).toString());
			System.err.println((new StringBuilder("  Message:    ")).append(e.getMessage()).toString());
		}

	}

	private void parseArguments(String args[]) {
		if (args.length > 0 && args[0].equalsIgnoreCase("derbyclient")) {
			framework = "derbyclient";
			driver = "org.apache.derby.jdbc.ClientDriver";
			protocol = "jdbc:derby://localhost:1527/";
		}
	}

	void excuteSql(String sql) {
		Connection conn;
		ArrayList statements;
		ResultSet rs;
		parseArguments(new String[0]);
		System.out.println((new StringBuilder("SimpleApp starting in ")).append(framework).append(" mode").toString());
		loadDriver();
		conn = null;
		statements = new ArrayList();
		Statement s = null;
		rs = null;
		try {
			Properties props = new Properties();
			props.put("user", "pingan");
			props.put("password", "pa888888");
			String dbName = "derbyDB";
			conn = DriverManager.getConnection((new StringBuilder(String.valueOf(protocol))).append(dbName).append(";create=true").toString(), props);
			System.out.println((new StringBuilder("Connected to and created database ")).append(dbName).toString());
			conn.setAutoCommit(false);
			s = conn.createStatement();
			statements.add(s);
			s.execute(sql);
			conn.commit();
			System.out.println("Committed the transaction");
			if (framework.equals("embedded"))
				try {
					DriverManager.getConnection("jdbc:derby:;shutdown=true");
				} catch (SQLException se) {
					if (se.getErrorCode() == 50000 && "XJ015".equals(se.getSQLState())) {
						System.out.println("Derby shut down normally");
					} else {
						System.err.println("Derby did not shut down normally");
						printSQLException(se);
					}
				}
		} catch (SQLException sqle) {
			printSQLException(sqle);
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
			} catch (SQLException sqle) {
				printSQLException(sqle);
			}
			int i = 0;
			while (!statements.isEmpty()) {
				Statement st = (Statement) statements.remove(i);
				try {
					if (st != null) {
						st.close();
						st = null;
					}
				} catch (SQLException sqle) {
					printSQLException(sqle);
				}
			}
		}

	}
}
