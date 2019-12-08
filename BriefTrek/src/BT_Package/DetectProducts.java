package BT_Package;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DetectProducts {
	ArrayList<String> detectErrList;
	boolean error = false;
	DetectProducts() {
		detectErrList = new ArrayList<String>();
	}
	DetectProducts(ArrayList<String> input, int storeId) {
		detectErrList = new ArrayList<String>();
		try {
			Connection conn = JDBCConnect.getConnection();
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			for(int i = 0; i < input.size(); i++) {
				String query = "select productName from Product join Category on Category.categoryID = Product.categoryID where productName = " +
						"\"" + input.get(i) + "\"" + "and storeID = " + storeId;
				ResultSet rs = stmt.executeQuery(query);
				if(!rs.next()) {
					detectErrList.add("Item not found: " + input.get(i));
					error = true;
				}
				else {
					detectErrList.add("Item found. " + input.get(i));
				}
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	ArrayList<String> getDetectErrList() {
		return detectErrList;
	}
	boolean getErrorBoolean() {
		return error;
	}
}
