package BT_Package;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.cj.jdbc.MysqlDataSource;

public class JDBCConnect {

	JDBCConnect() throws ClassNotFoundException{
		//Initialize connection to database
		// Load the JDBC driver
		Class.forName("com.mysql.cj.jdbc.Driver");
		System.out.println("Driver loaded");
	}
	static Connection getConnection() throws SQLException {
		// Establish a connection
		String myUrl = "jdbc:mysql://localhost/StoreNavigator?useSSL=false";
		DriverManager.getDrivers();
		Connection connection = DriverManager.getConnection(myUrl, "root", "sesame");
		System.out.println("Database connected");
		return connection;
	}
}