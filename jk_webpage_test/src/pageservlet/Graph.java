package mainPacket;

import java.awt.geom.Point2D;
import java.lang.Math;
import java.sql.*;
import java.util.SortedSet; 
import java.util.TreeSet; 
import java.util.Iterator;
import java.util.ArrayList;
import javafx.util.Pair;

public class Graph {
	private double[][] table;
	private double pos;
	private ArrayList<Double> perPath;
	private ArrayList<Double> tempPath;
	private double[][] adjMatrix;
	private int numVertices;
	private ArrayList<Point2D> waypointCoords;
	private ArrayList<String> waypointName;
	private ArrayList<ResultSet> pairOfNameAndWaypoint;
	private double INF = Double.MAX_VALUE;
	private final int SET_VERTICES = 1;

	private void addAllWeight() { // Works
		for(int i = 1; i < numVertices + 1; i++) { // +1 since skip [0][0]
			for(int j = 1; j < numVertices + 1; j++) {
				adjMatrix[i][j] = waypointCoords.get(i - 1).distance(waypointCoords.get(j - 1));
			}
		}
	}
	
	
	Graph()	{}
	Graph(int n) { // Works
		//setting up private variables
		numVertices = n + SET_VERTICES; 
		//ArrayList<String> temp = new ArrayList;
		//waypointProductName = temp;
		double[][] temp1 = new double[numVertices+1][numVertices+1]; //One for starting at [1][1]
		//for g() function
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
		//double[] t1 = new double[(int) Math.pow(2.0, (double)numVertices)];
		double[][] tempTable = new double[numVertices + 1][(int) Math.pow(2, numVertices)];
		table = tempTable;
		
		//sets up adjMatrix -1 means it is empty
		for (int i =1; i <= numVertices; i++) {
			for (int j =1; j <= numVertices; j++) {
				temp1[i][j] = -1;
			}
		}
		adjMatrix = temp1;		
	}
	
	void addEdge(int begin, int end, int value) {
		if (begin > numVertices || end > numVertices || begin < 0 || end < 0) {
			System.out.println("Not possible to Add Edge\n");
		}
		else {
			adjMatrix[begin][end] = value;
		}
	}
	
	//true if the value is in the row and column of the adjMatrix
	boolean findInRow(int row, int column, int value) {
		if (adjMatrix[row][column] == value) {
			return true;
		}
		else {
			return false;
		}
	}
	
	void printMatrix() {
		for (int i = 1; i <= numVertices; i++) {
			System.out.print(waypointName.get(i) + " ");
			for (int j = 1; j <= numVertices; j++) {
				if (adjMatrix[i][j] >= INF) {
					System.out.print("INF ");
				}
				else {
					System.out.print(adjMatrix[i][j] + " ");
				}
			}
			System.out.println("\n");
		}
	}
	
	//void addProductName(int position, String temp) {
	//	waypointProductName.get(position) = temp;
	//}
	
	private int bitArrayToInt(int[] tempBit) {
		int value = 0;
		for(int i = 0; i < 32; i++) {
			if (tempBit[i] == 1) {
				value += Math.pow(2, i);
			}
		}
		return value;
	}
	
	//finds the shortest path of graph and prints it out
	void findShortestPath() {
		SortedSet<Integer> tempArray = new TreeSet<Integer>();
		
		for (int i = 2; i < numVertices + 1; i++) {
			tempArray.add(i);
		}
		double minVal = g(1, tempArray);
		
		if(minVal>= INF-10000) {
			System.out.println("No Hamiltonian Cycle");
		}
		else {
			System.out.println("Optimal Shopping Path = [ " + minVal + ", <");
			int i;
			for(i = 1; i < numVertices; i++) {
				System.out.print(waypointName.get(perPath.get(i).intValue() - 1) + ", ");
			}
			System.out.println(waypointName.get(perPath.get(i).intValue() - 1) + "> ]");
		}
	}
	
	private double g(int begin, SortedSet<Integer> temp) {
		ArrayList<Double> pair1 = new ArrayList<Double>();
		ArrayList<ArrayList<Double>> pair2 = new ArrayList<ArrayList<Double>>();
		Pair <ArrayList<Double>, ArrayList<ArrayList<Double>>> minHold = new Pair <ArrayList<Double>, ArrayList<ArrayList<Double>>>(pair1, pair2);
		double value = 0;
		int[] tempBit = new int[32];
		SortedSet<Integer> backUp = new TreeSet<Integer>();
	
		Iterator<Integer> itr = temp.iterator();
		while(itr.hasNext()) {
			tempBit[itr.next().intValue() - 1] = 1;
		}

		
		int bitValue = bitArrayToInt(tempBit);
		if (table[begin][bitValue] != 0) {
			return table[begin][bitValue];
		}
		else {
			if(temp.isEmpty()) {
				table[begin][bitValue] = adjMatrix[begin][1];
				return adjMatrix[begin][1];
			}
			Iterator<Integer> itr1 = temp.iterator();
			while(itr1.hasNext()) {
				Double itrVal = (itr1.next()).doubleValue();
				tempPath.set((int) pos, itrVal);
				pos++;
				backUp = (TreeSet<Integer>)((TreeSet<Integer>)temp).clone();
				value = adjMatrix[begin][itrVal.intValue()];
				backUp.remove(itrVal.intValue());
				value += g(itrVal.intValue(), backUp);
				table[begin][bitValue] = value;
				minHold.getKey().add(value);
				minHold.getValue().add(tempPath);
				temp.add(itrVal.intValue());
				pos--;
			}
			table[begin][bitValue] = min(minHold);
			System.out.println(table[begin][bitValue]);
			return table[begin][bitValue];
		}
	}
	
	private Double min(Pair<ArrayList<Double>,ArrayList<ArrayList<Double>>> temp) {
		Double minValue = INF;

		for (int i = 0; i < temp.getKey().size(); i++) {
			if (temp.getKey().get(i) < minValue) {
				minValue = temp.getKey().get(i);
				perPath = temp.getValue().get(i);
				tempPath = temp.getValue().get(i);
			}
		}
		return minValue;
	}
	
	void read(ArrayList<String> products) throws SQLException, ClassNotFoundException{ // Works
		//Initialize connection to database
				// Load the JDBC driver
				Class.forName("com.mysql.cj.jdbc.Driver");
				System.out.println("Driver loaded");
				
				// Establish a connection
				String myUrl = "jdbc:mysql://localhost/StoreNavigator?useSSL=false";
				Connection connection = DriverManager.getConnection(myUrl, "root", "sesame");
				System.out.println("Database connected");
				// Create Statment
				Statement statement = connection.createStatement();
				
				pairOfNameAndWaypoint = new ArrayList<ResultSet>();
				waypointName = new ArrayList<String>();
				waypointCoords = new ArrayList<Point2D>();
				
				for(int i = 0; i < products.size(); ++i) {
		            ResultSet waypoint = statement.executeQuery("select productName, waypoint from Product where (productName = " + "'" + products.get(i) + "')");
		            waypoint.next(); //Make sure to point at the correct item
		           // while(waypoint.next()) {
		            	//System.out.println(waypoint.getString(1) + " " + waypoint.getString(2));
		            //}
		            pairOfNameAndWaypoint.add(waypoint);//Pair of productName and waypoint name
		           
		            ResultSet coord = statement.executeQuery("select * from Location where (waypoint = " + "'" + waypoint.getString(2) + "')");
		            coord.first();
		            waypointName.add(coord.getString(1));
		            waypointCoords.add(new Point2D.Double(coord.getDouble(2), coord.getDouble(3)));			
				}
				
				ResultSet beginAndEnd = statement.executeQuery("select * from Location where (waypoint = 'Mejier94_Entrance') or (waypoint = 'Mejier96_CashRegister')");
				beginAndEnd.next();
				waypointName.add(0, beginAndEnd.getString(1));
				waypointCoords.add(0, new Point2D.Double(beginAndEnd.getDouble(2), beginAndEnd.getDouble(3)));
				beginAndEnd.next();
				waypointName.add(beginAndEnd.getString(1));
				waypointCoords.add(new Point2D.Double(beginAndEnd.getDouble(2), beginAndEnd.getDouble(3)));

				
				connection.close();
				addAllWeight();

	}
	
	
	
	
}
