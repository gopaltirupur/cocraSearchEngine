

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class display_report
 */
@WebServlet("/display_report")
public class display_report extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public display_report() {
        super();
        // TODO Auto-generated constructor stub
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        

        String department = request.getParameter("department");        
        String date = request.getParameter("date");
        
        String dbURL="jdbc:mysql://localhost/absentupdate";             
        Class.forName("com.mysql.jdbc.Driver");            
        Connection con1 = DriverManager.getConnection(dbURL,"root","jit");                        
        Statement stmt1 = con1.createStatement();
        
        String query = "select *from absent_entry where date regexp '"+date+"' and class regexp '"+department+"' order by date;";
        ResultSet rs1 = stmt1.executeQuery(query);
        out.println("<table border='1'>");
        out.println("<thead>");
        out.println("<tr><td>SNo</td><td>Date</td><td>Class</td><td>Staff_name</td><td>Absentees</td></tr>");        
        out.println("</thead>");
        
        out.println("<tbody>");
        while(rs1.next())
        {	
        	out.println("<tr>");
        	out.println("<td>"+rs1.getInt(1)+"</td>");
        	out.println("<td>"+rs1.getString(2)+"</td>");
        	out.println("<td>"+rs1.getString(3)+"</td>");
        	out.println("<td>"+rs1.getString(4)+"</td>");
        	out.println("<td>"+rs1.getString(5)+"</td>");
        	out.println("</tr>");
        }        
        out.println("</tbody>");       
        
        
        out.println("</table>");
        
    }
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
			processRequest(request, response);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
			processRequest(request, response);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
