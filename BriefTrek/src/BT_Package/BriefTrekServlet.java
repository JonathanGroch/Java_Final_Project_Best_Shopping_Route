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
	  	String hidden = request.getParameter("hiddenValue");
	  	int storeId = Integer.parseInt(hidden);
	  	
	  	// paramName is initiated and advanced once before the execution of the while loop
	  	// so that the storeID doesn't get passed in as part of the list of products
	  	String paramName = (String)paramNames.nextElement();
	  	
	    while(paramNames.hasMoreElements()) {
	    	paramName = (String)paramNames.nextElement();
	    	String[] paramValues = request.getParameterValues(paramName);
	        	    	
		    if(paramValues.length != 0) {
	        	 for(int i = 0; i < paramValues.length; i++) {
	        		 products.add(paramValues[i]);
	        	 }
	        }
	    }
	    
	    DetectProducts dp = new DetectProducts(products, storeId);
	    if(dp.getErrorBoolean()) {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<html><head><title>Your Route</title>" + 
						"<link rel='stylesheet' type='text/css' href='styles.css'>" +
						"<body><div class='navbar'><button class='navtab'> " +
						"YOUR ROUTE</button><a href='/BriefTrek'><button class='navtab'>" +
						"GO BACK</button></a></div>" +
						"<div id='Home' class='tabcontent' style='display: grid'>" +
						"<div class='maparea'>");
			out.println("<ul style='text-align:center; list-style-position:inside;'>");
			for(int i = 0; i < dp.getDetectErrList().size(); i++) {
				out.println("<li>" + dp.getDetectErrList().get(i) + "</li>");
			}
			out.println("</ul>");
			out.println("</div><div class='tabside'>" +
						"</div></div></body>" +
						"<footer>Created by Hummus Squad</footer></html>");
	    }
	    else {
		    System.out.println(products);
			Graph tsp = new Graph(products.size());
			try {
				tsp.read(products, storeId);
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
			out.println("<html><head><title>Your Route</title>" + 
						"<link rel='stylesheet' type='text/css' href='styles.css'>" +
						"<body><div class='navbar'><button class='navtab'> " +
						"YOUR ROUTE</button><a href='/BriefTrek'><button class='navtab'>" +
						"GO BACK</button></a></div>" +
						"<div id='Home' class='tabcontent' style='display: grid'>" +
						"<div class='maparea'>");
			out.println("<ol style='text-align:center; list-style-position:inside;'>");
			for(int i = 0; i < directions.size(); i++) {
				out.println("<li>" + directions.get(i) + "</li>");
			}
			out.println("</ol>");
			out.println("</div><div class='tabside'>" +
						"</div></div></body>" +
						"<footer>Created by Hummus Squad</footer></html>");
	    }

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	

}
