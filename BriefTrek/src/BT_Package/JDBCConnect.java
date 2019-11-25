package BT_Package;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnect {
	JDBCConnect() throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		//Initialize connection to database
		// Load the JDBC driver
		Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		System.out.println("Driver loaded");
		
	}
	static Connection getConnection() throws SQLException {
		// Establish a connection
		String myUrl = "jdbc:mysql://localhost:3306/StoreNavigator";
		DriverManager.getDrivers();
		Connection connection = DriverManager.getConnection(myUrl, "root", "sesame");
		System.out.println("Database connected");
		return connection;
	}
}