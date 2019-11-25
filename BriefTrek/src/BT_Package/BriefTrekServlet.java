package BT_Package;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import BT_Package.DirectionsStore;
import BT_Package.Graph;

/**
 * Servlet implementation class BriefTrekServlet
 */
@WebServlet("/BriefTrek")
public class BriefTrekServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BriefTrekServlet() {
        super();
        // TODO Auto-generated constructor stub
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
		Graph tsp = new Graph();
		try {
			tsp.read(products, 2);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DirectionsStore ds = new DirectionsStore(tsp.findShortestPath());
		ds.splitArrays();
		ds.categories();
		ArrayList<String> directions  = ds.getListOfDirections();
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<ol>");
		for(int i = 0; i < directions.size(); i++) {
			out.println("<li>" + directions.get(i) + "</li>");
		}
		out.println("</ol>");
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
