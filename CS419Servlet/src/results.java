
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");

	      PrintWriter out = response.getWriter();
	      
	      
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
	         } else {

	            for(int i = 0; i < paramValues.length; i++) {
	               out.println("<li>" + paramValues[i]);
	            }
	            out.println("</ul>");
	         }
	      }
	      out.println("</tr>\n</table>\n</body></html>");
	   }
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		doGet(request, response);
	}

}
