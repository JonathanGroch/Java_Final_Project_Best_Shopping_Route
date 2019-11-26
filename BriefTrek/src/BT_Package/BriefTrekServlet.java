package BT_Package;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import BT_Package.DirectionsStore;
import BT_Package.Graph;
import javafx.util.Pair;
/**
 * Servlet implementation class BriefTrekServlet
 */
//@WebServlet("/BriefTrekServlet")
public class BriefTrekServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public Graph tsp;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BriefTrekServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    Enumeration<String> paramNames = request.getParameterNames();
	  	ArrayList<String> products = new ArrayList<String>();
	  	
	      while(paramNames.hasMoreElements()) {
	         String paramName = (String)paramNames.nextElement();
	         String[] paramValues = request.getParameterValues(paramName);
	         
	         if(paramValues.length != 0) {
	        	 for(int i = 0; i < paramValues.length; i++) {
	        		 products.add(paramValues[i]);
	        	 }
	         }

	      }
	      /*response.setContentType("text/html");
	      PrintWriter out = response.getWriter();
	      out.println("<ol>");
	      for(int i = 0; i < products.size(); i++) {
	    	  out.println("<li>" + products.get(i) + "<li>");
	      }
	      out.println("</o>"); */
		Graph tsp = new Graph(products.size());
		try {
			tsp.read(products, 2);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Pair<ArrayList<String>, ArrayList<String>> temp = tsp.findShortestPath();
		DirectionsStore ds = new DirectionsStore();
		ds.reader(temp);
		ds.splitArrays();
		ds.categories();
		ds.ListOfDirections();
		
		
		
		ArrayList<String> directions  = ds.getListOfDirections();
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<ol>");
		for(int i = 0; i < directions.size(); i++) {
			out.println("<li>" + directions.get(i) + "</li>");
		}
		out.println("</ol>"); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	

}
