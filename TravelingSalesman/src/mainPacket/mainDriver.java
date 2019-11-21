package mainPacket;

import mainPacket.Graph;

import java.sql.SQLException;
import java.util.ArrayList;

import javafx.util.Pair;


public class mainDriver {
	public static void main(String[] arg) throws ClassNotFoundException, SQLException {
		ArrayList<String> t = new ArrayList<String>();
		int storeID = 3;
		
		switch(storeID) {
			case 1:
				t.add("Apple");
				t.add("Advil");
				t.add("Wine");
				t.add("Chocolate");
				t.add("Butter");
				t.add("Popcorn");
				t.add("Shoe");
				break;
			case 2:
				t.add("Tile");
				t.add("Blender");
				t.add("Green Paint");
				break;
			case 3:
				t.add("Necklace");
				t.add("Bag");
				t.add("Shoe");
				break;
		}
			
			//constructs Graph with user input
			Graph t1 = new Graph(t.size());
			//read in data from database and initialize table
			t1.read(t, storeID);
			//finds shortest path
			Pair<ArrayList<String>,ArrayList<String>> list = t1.findShortestPath();
			//t1.printMatrix();
		}	
}

