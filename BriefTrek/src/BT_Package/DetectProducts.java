package BT_Package;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DetectProducts {
	//Principle variables
	//detectErrList composes the return list of items found or not
	ArrayList<String> detectErrList;
	//error is a boolean flag that is raised true if error is detected
	boolean error = false;
	
	//No arg constructor
	DetectProducts() {
		detectErrList = new ArrayList<String>();
	}
	//Two arg constructor 
	//Principle method that tests a list of inputs
	//And whether they are in the database or not
	DetectProducts(ArrayList<String> input, int storeId) {
		detectErrList = new ArrayList<String>();
		try {
			//Establish a new connection
			Connection conn = JDBCConnect.getConnection();
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			//Iterating through the list of inputs
			for(int i = 0; i < input.size(); i++) {
				String query = "select productName from Product join Category on Category.categoryID = Product.categoryID where productName = " +
						"\"" + input.get(i) + "\"" + "and storeID = " + storeId;
				//Executing the query
				ResultSet rs = stmt.executeQuery(query);
				//Test if resultSet is empty, if it is empty then 
				//Adds string to detectErrList that says 
				//Item not found: itemName
				//As well as setting error flag to true
				if(!rs.next()) {
					detectErrList.add("Item not found: " + input.get(i));
					error = true;
				}
				//Else it just adds a string that says item found: with its name
				else {
					detectErrList.add("Item found: " + input.get(i));
				}
			}
			//Close connection
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	//Getters 
	ArrayList<String> getDetectErrList() {
		return detectErrList;
	}
	boolean getErrorBoolean() {
		return error;
	}
}
