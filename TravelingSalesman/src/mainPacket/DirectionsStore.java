package mainPacket;

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
	DirectionsStore(Pair<ArrayList<String>, ArrayList<String>> input, int storeID) {
		optimalPath = input;
		categories = new ArrayList<String>();
		waypoints = new ArrayList<String>();
		products = new ArrayList<String>();
		listOfDirections = new ArrayList<String>();
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
			categories.add(products.get(0));
			Connection conn = mainPacket.JDBCConnect.getConnection();
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			for(int i = 1; i < (products.size() - 1); i++) {
				String query = "select categoryName from Product join Category on (Product.productName = " + "\"" + products.get(i).toString() + "\"" + ") and (Product.categoryID = Category.categoryID)";
				ResultSet category = stmt.executeQuery(query);
				if(!category.next()) {
					System.out.println("Result Set is empty");
				}
				else {
					String temp = category.getString(1);
					categories.add(temp);
				}
				
				
			}
			conn.close();
		}
		catch(Exception e) {
			System.out.print("Exception caught, exiting out of code.");
		}
	}
	//Constructs an arraylist of directions 
 	public void ListOfDirections() {
 		this.listOfDirections.add("You arrive at the store, ready to start shopping!");
 		for(int i = 1; i < categories.size(); i++) {
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
 		this.listOfDirections.add("Now you're done! Huzzah, you are ready to checkout with your items!");
	}
 	//Returns the listOfDirections
 	public ArrayList<String> getListOfDirections() {
 		return listOfDirections;
 	}
 	
	
}