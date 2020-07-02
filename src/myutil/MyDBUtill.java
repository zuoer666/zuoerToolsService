package myutil;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.junit.Test;

public class MyDBUtill {
	private static String DRIVER = null;
	public static String URL = null;
	public static String databasename;
	static {
		Properties props = new Properties();
		try {
			props.load(MyDBUtill.class.getResourceAsStream("/jdbc.properties"));
			DRIVER = props.getProperty("driverClass");
			StringBuilder stringBuilder=new StringBuilder();
			String databasename=props.getProperty("databaseSpace");
			stringBuilder.append(props.getProperty("databaseUrl"));
			stringBuilder.append(databasename);
			stringBuilder.append("?user=");
			stringBuilder.append(props.getProperty("databaseuser"));
			stringBuilder.append("&password=");
			stringBuilder.append(props.getProperty("databasepassword"));
			stringBuilder.append("&");
			stringBuilder.append(props.getProperty("dataencoding"));
			URL = stringBuilder.toString();
			//System.out.println(URL);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public static Connection getConnection() {
		if (DRIVER == null) {
			return null;
		}
		Connection conn = null;
		try {
			//Class.forName("com.mysql.cj.jdbc.Driver");
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return conn;
	}
	

	public static void close(ResultSet rs, Statement sta, Connection conn,InputStream is) {
		try {
			if (rs != null)
				rs.close();
			if (sta != null)
				sta.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void close(ResultSet rs, Statement sta, Connection conn) {
		try {
			if (rs != null)
				rs.close();
			if (sta != null)
				sta.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
