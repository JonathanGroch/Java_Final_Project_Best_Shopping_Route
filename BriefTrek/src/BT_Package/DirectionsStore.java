package BT_Package;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.util.Pair;

public class DirectionsStore {
	//Return ArrayList
	private ArrayList<String> listOfDirections;
	//Input Stored
	private Pair<ArrayList<String>, ArrayList<String>> optimalPath;
	//ArrayList<String> arrays
	private ArrayList<String> waypoints;
	private ArrayList<String> products;
	private ArrayList<String> categories;
	//No Arg Constructor
	DirectionsStore() {
			
	}
	//Single Arg Constructor
	DirectionsStore(Pair<ArrayList<String>, ArrayList<String>> input) {
		optimalPath = input;
	}
	//Imports the TSP result pair arrayList, holding waypoints and products respectively
	public void scanner(Pair<ArrayList<String>, ArrayList<String>> optimalPath) {
		this.optimalPath = optimalPath;
	}
	//Splits optimal Path into waypoints and products
	public void splitArrays() {
		for(int i = 0; i < optimalPath.getKey().size(); i++) {
			waypoints.add(optimalPath.getKey().get(i));
			products.add(optimalPath.getValue().get(i));
		}
	}
	//Calls the database to get categories
	public void categories() {
		try {
			String myDriver = "org.gjt.mm.mysql.Driver";
			String myUrl = "jdbc:mysql://localhost/StoreNavigator?useSSL=false";
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, "root", "sesame");
			Statement stmt = conn.createStatement();
			String query = "SELECT Categories.categoryName FROM Categories INNER JOIN Products ON Products.categoryId = Categories.categoryId WHERE Products.productName = ";
			for(int i = 0; i < products.size(); i++) {
				String squery = query;
				squery += products.get(i);
				ResultSet rs = stmt.executeQuery(squery);
				categories.add(rs.getString("categoryName"));
			}
		}
		catch(Exception e) {
			System.out.print("Exception caught, exiting out of code.");
		}
	}
	//Constructs an arraylist of directions 
 	public void ListOfDirections() {
 		this.listOfDirections.add("You arrive at the store, ready to start shopping!");
 		for(int i = 1; i < products.size(); i++) {
 			if(i == 1) {
 				this.listOfDirections.add("First you'll go to " + categories.get(i) + " to get product " + products.get(i));
 			}
 			else if(i + 1 == products.size()) {
 				this.listOfDirections.add("Lastly you'll go to " + categories.get(i) + " to get product " + products.get(i));
 			}
 			else {
 				this.listOfDirections.add("Next you'll go to " + categories.get(i) + " to get product " + products.get(i));
 			}
 		}
 		this.listOfDirections.add("Now your done! Huzzah, you are ready to checkout with your items!");
	}
 	//Returns the listOfDirections
 	public ArrayList<String> getListOfDirections() {
 		return listOfDirections;
 	}
 	
	
}