package mainPacket;

import java.awt.geom.Point2D;
import java.lang.Math;
import java.sql.*;
import java.util.SortedSet; 
import java.util.TreeSet; 
import java.util.Iterator;
import java.util.ArrayList;
import javafx.util.Pair;
import mainPacket.JDBCConnect;

public class Graph {
	private double[][] table; //dynamically stores the total distance for any path taken
	private double pos; //position in the path
	private ArrayList<Double> perPath; //permanent shortest path
	private ArrayList<Double> tempPath; //temporary shortest path
	private double[][] adjMatrix; //stores distance from all waypoints to all other waypoints
	private int numVertices; //number of items in the list
	private ArrayList<Point2D> waypointCoords; //List of coordinates for each item
	private ArrayList<String> waypointName; //List of waypoint name
	private ArrayList<String> productName; //List of product
	private double INF = Double.MAX_VALUE; //Max value
	private final int SET_VERTICES = 1; //Extra vertices. Set to 1 since Entrance will always be in the calculation
	private int storeId; //Which store tsp will be dealin with

	//calculates the distance between two items and stores it into the adjMatrix table for findShortestPath()
	private void addAllWeight() { 
		for(int i = 1; i < numVertices + 1; i++) { // +1 since skip [0][0]
			for(int j = 1; j < numVertices + 1; j++) {
				adjMatrix[i][j] = waypointCoords.get(i - 1).distance(waypointCoords.get(j - 1));
			}
		}
	}
	
	//default constructor
	Graph()	{}
	
	//constructor with paramater n, which is the size of the item list
	Graph(int n) { 
		//setting up private variables
		numVertices = n + SET_VERTICES; 
		double[][] temp1 = new double[numVertices+1][numVertices+1]; //One for starting at [1][1]
		
		//creates the size of the permanent and temporary path and puts a temporary value of 1 in each position
		perPath = new ArrayList<Double>(numVertices+1);
		tempPath = new ArrayList<Double>(numVertices+1);
		perPath.add(0, INF - 10000);
		tempPath.add(0,INF - 10000);
		for (int i = 1; i <= numVertices; ++i) {
			perPath.add(i, (double) 1);
			tempPath.add(i, (double) 1 );
		}
		pos = 2;
		
		//creates size of table
		double[][] tempTable = new double[numVertices + 1][(int) Math.pow(2, numVertices)];
		table = tempTable;
		
		//fills in the adjMatrix with all -1, which means that it is empty
		for (int i =1; i <= numVertices; i++) {
			for (int j =1; j <= numVertices; j++) {
				temp1[i][j] = -1;
			}
		}
		adjMatrix = temp1;		
	}
	
	public int getStoreId() {
		return storeId;
	}
	 //function to convert an array fo 32 filled with 1 and 0 into a decimal number
	private int bitArrayToInt(int[] tempBit) {
		int value = 0;
		for(int i = 0; i < 32; i++) {
			if (tempBit[i] == 1) {
				value += Math.pow(2, i);
			}
		}
		return value;
	}
	
	//finds the shortest path of all possible paths and prints it out
	Pair<ArrayList<String>,ArrayList<String>> findShortestPath() {
		SortedSet<Integer> tempArray = new TreeSet<Integer>(); 
		ArrayList<String> tempWaypointName = new ArrayList<String>();
		ArrayList<String> tempProductName = new ArrayList<String>();
		
		//fills the temporary array with the positions in graph (start at 2 since position 1 is used in the g() function)
		for (int i = 2; i < numVertices + 1; i++) {
			tempArray.add(i);
		}
		
		//calls on the g() function to find the minimum value for the path
		double minVal = g(1, tempArray);
		
		//if the minVal is higher than our INF, then there is no shortest path
		if(minVal>= INF-10000) {
			tempWaypointName.add("Empty");
			tempProductName.add("Empty");
			return new Pair<ArrayList<String>,ArrayList<String>>(tempWaypointName, tempProductName);
		}
		//prints out shortest path by item name
		else {
			int i;
			//gets the value in position i in permanent path and find the corresponding item in product name
			for(i = 1; i < numVertices; i++) {
				tempWaypointName.add(waypointName.get(perPath.get(i).intValue() - 1));
				tempProductName.add(productName.get(perPath.get(i).intValue() - 1));
			}
			tempWaypointName.add(waypointName.get(perPath.get(i).intValue() - 1));
			tempProductName.add(productName.get(perPath.get(i).intValue() - 1));
			tempWaypointName.add(waypointName.get(waypointName.size() - 1));
			tempProductName.add(productName.get(productName.size() - 1));
			
		}
		return new Pair<ArrayList<String>,ArrayList<String>>(tempWaypointName, tempProductName);
	}
	
	//takes in (position, path) and returns the minimum value. Dynamically stores the value of each path into the table
	private double g(int begin, SortedSet<Integer> temp) {
		ArrayList<Double> pair1 = new ArrayList<Double>(); //to be put into the pair minHold
		ArrayList<ArrayList<Double>> pair2 = new ArrayList<ArrayList<Double>>(); //to be put into the pair minHold
		//the pair minHold is used to store the arraylist of minimum value of the path and and arraylist of arraylist for all the path
		Pair <ArrayList<Double>, ArrayList<ArrayList<Double>>> minHold = new Pair <ArrayList<Double>, ArrayList<ArrayList<Double>>>(pair1, pair2);
		double value = 0;
		int[] tempBit = new int[32]; //array of 32, which are made up of bits (either 1 or 0)
		SortedSet<Integer> backUp = new TreeSet<Integer>(); //backUp is used to change the temporary path without effecting the actual pahting
		
		//puts a 1 in the bit array to symbolize that the items are in this path
		Iterator<Integer> itr = temp.iterator();
		while(itr.hasNext()) {
			tempBit[itr.next().intValue() - 1] = 1;
		}

		//converts the bit array into a decimal number
		int bitValue = bitArrayToInt(tempBit);
		
		//If the path is already in the table, then just return the value
		if (table[begin][bitValue] != 0) {
			return table[begin][bitValue];
		}
		//else finds the value of the path and stores it into the table
		else {
			//If the path is empty, then return the value and put it into the table
			if(temp.isEmpty()) {
				table[begin][bitValue] = adjMatrix[begin][1];
				return adjMatrix[begin][1];
			}
			//else iterate through the path and find all possible value for all possible paths
			Iterator<Integer> itr1 = temp.iterator();
			while(itr1.hasNext()) {
				Double itrVal = (itr1.next()).doubleValue(); //stores the next number in iteration into itrVal
				//Change Path
				tempPath.set((int) pos, itrVal); //set the tempPath with the itrVal in the position given
				pos++; //moves forward one position
				backUp = (TreeSet<Integer>)((TreeSet<Integer>)temp).clone(); //backup stores the temp path, so it can make changes to it without effecting the temp path
				value = adjMatrix[begin][itrVal.intValue()];
				backUp.remove(itrVal.intValue()); //remove the value in backup since already calculated
				//Pass new path
				value += g(itrVal.intValue(), backUp); //uses recursion to find all possible paths and their values, adds the value of g() for the distance between each items
				// Save cost and path
				table[begin][bitValue] = value; //stores the value in the table
				minHold.getKey().add(value); //first part stores the value of the path
				ArrayList<Double> tempPathClone = (ArrayList<Double>)tempPath.clone();
				minHold.getValue().add(tempPathClone); //second part stores the path
				temp.add(itrVal.intValue()); //adds back the value it removed earlier
				pos--; //moves back into orignal poisition
			}
			table[begin][bitValue] = min(minHold); //calls on the function min() to find the minimum value for all possible paths
			System.out.println(table[begin][bitValue]);
			return table[begin][bitValue]; //returns value in the shartes path
		}
	}
	
	//takes in a pair with an array list of minValues and paths and returns the min value for all paths given
	private Double min(Pair<ArrayList<Double>, ArrayList<ArrayList<Double>>> temp) {
		Double minValue = INF;
		//loops through the array list of pairs until it reaches the last path
		for (int i = 0; i < temp.getKey().size(); i++) {
			 //if the paths minVal is less than the current minVal, then change it to make it the new minVal
			if (temp.getKey().get(i) < minValue) {
				minValue = temp.getKey().get(i);
				perPath = temp.getValue().get(i);
				tempPath = temp.getValue().get(i);
			}
		}
		return minValue;
	}
	
	//Queries data for findShortedPath()
	void read(ArrayList<String> products, int storeID) throws SQLException, ClassNotFoundException{//void read(ArrayList<String> products) throws SQLException, ClassNotFoundException{ // Works
				// Get Connection
				Connection connection = JDBCConnect.getConnection();
				// Create Statement
				Statement statement = connection.createStatement();
				
				productName = new ArrayList<String>();
				waypointName = new ArrayList<String>();
				waypointCoords = new ArrayList<Point2D>();
				

				for(int i = 0; i < products.size(); ++i) {
					ResultSet waypoint = statement.executeQuery("select productName, waypoint from Product join Category on (Product.productName = " + "'" + products.get(i) + "') and (Product.categoryID = Category.categoryID) and (Category.storeID = " + String.valueOf(storeID) + ")");
					waypoint.next(); //Make sure to point at the correct item

		            productName.add(waypoint.getString(1)); //Get product name from query
		           
		            ResultSet coord = statement.executeQuery("select * from Location where (waypoint = " + "'" + waypoint.getString(2) + "')"); //Query correct coordinates
		            coord.first();
		            waypointName.add(coord.getString(1)); //Add waypoint name
		            waypointCoords.add(new Point2D.Double(coord.getDouble(2), coord.getDouble(3))); //Add coordinates	
				}
				
				//Getting entrance from correct store
				ResultSet beginAndEnd = null;
				switch(storeID) {
					case 1:
						beginAndEnd = statement.executeQuery("select * from Location where (waypoint = 'Mejier94_Entrance') or (waypoint = 'Mejier96_CashRegister')");
						break;
					case 2:
						beginAndEnd = statement.executeQuery("select * from Location where (waypoint = 'Mernards99_Entrance') or (waypoint = 'Mernards100_CashRegister')");
						break;
					case 3:
						beginAndEnd = statement.executeQuery("select * from Location where (waypoint = 'Target85_Entrance') or (waypoint = 'Target86_CashRegister')");
						break;
					default:
						System.out.println("No Store Selected");
						break;
						
				}
		
				//Add entrance in front of list to be part of calculation
				beginAndEnd.next();
				productName.add(0, "Entrance");
				waypointName.add(0, beginAndEnd.getString(1));
				waypointCoords.add(0, new Point2D.Double(beginAndEnd.getDouble(2), beginAndEnd.getDouble(3)));
				
				//Get cashier to be added at the end of calculation
				beginAndEnd.next();
				productName.add("Cashier");
				waypointName.add(beginAndEnd.getString(1));
				
				connection.close();
				addAllWeight(); //Initialize adjacency matrix with appropriate weight of the graph
	}
}
