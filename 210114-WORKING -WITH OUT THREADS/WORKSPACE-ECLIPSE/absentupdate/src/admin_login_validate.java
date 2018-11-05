

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
 * Servlet implementation class admin_login_validate
 */
@WebServlet("/admin_login_validate")
public class admin_login_validate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public admin_login_validate() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String received_password = request.getParameter("password");        
        String dbURL="jdbc:mysql://localhost/absentupdate";             
        Class.forName("com.mysql.jdbc.Driver");            
        Connection con1 = DriverManager.getConnection(dbURL,"root","jit");                        
        Statement stmt1 = con1.createStatement();
        
        String query = "select *from admin_password;";
        ResultSet rs1 = stmt1.executeQuery(query);        
        rs1.next();
        
        String database_password = rs1.getString(1);
        if(received_password.equals(database_password))
        {
        	out.println("<html><head><title>ADMIN MAIN</title></head>");
        	out.println("<body>");
        	out.println("<form name='f1' action='update_staff_list' method='post'>");
        	out.println("<input type='submit' name='submit' value='UPDATE_STAFF_LIST'>");        	
        	out.println("</form></br>");
        	
//        	out.println("<form name='f3' action='update_class_list' method='post'>");
//        	out.println("<input type='submit' name='submit' value='UPDATE_CLASS_LIST'>");        	
//        	out.println("</form></br>");
        	
        	out.println("<form name='f4' action='display_report' method='post'>");
        	out.println("<input type='text' name='department' value='CSE'>");
        	out.println("<input type='text' name='date' value='Jan 01'>");        	
        	out.println("<input type='submit' name='submit' value='VIEW ENTRIES'>");        	
        	out.println("</form></br>");
        	
        	out.println("<form name='f4' action='display_report_recent_500' method='post'>");
        	out.println("<input type='submit' name='submit' value='VIEW RECENT 500 ENTRIES'>");        	
        	out.println("</form></br>");
        	
        	out.println("</body>");
        	out.println("</html>");
        	
        	
        }
        else
        	out.println("WRONG PASSWORD");
        
        
        
 }
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
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
