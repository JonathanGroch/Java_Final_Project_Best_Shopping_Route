
import java.io.*;
import java.util.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class results
 */
@WebServlet("/results")
public class results extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public results() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");

		PrintWriter out = response.getWriter();
	      
	      
		// ??????WHAT DOES THIS DO
	    Enumeration<String> paramNames = request.getParameterNames();

	    while(paramNames.hasMoreElements()) {
	    	String paramName = (String)paramNames.nextElement();
	        String[] paramValues = request.getParameterValues(paramName);

	        // Read single valued data
	        if (paramValues.length == 1) {
	        	String paramValue = paramValues[0];
	            if (paramValue.length() == 0)
	              out.println("<i>No Value</i>");
	            else
	              out.println(paramValue);
	        }
	        else {
	        	for(int i = 0; i < paramValues.length; i++) {
	        		out.println("<li>" + paramValues[i]);
	            }
	            out.println("</ul>");
	        }
	    }
	    // ???????? WHAT IS THIS
	    // ???????? CURRENTLY NOT RETURNING CORRECT STUFF
	    out.println("</tr>\n</table>\n</body></html>");
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
