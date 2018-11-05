

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class update
 */
@WebServlet("/update")
public class update extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
	 protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException, ClassNotFoundException, SQLException {
	        response.setContentType("text/html;charset=UTF-8");
	        PrintWriter out = response.getWriter();
	        
	        String class_name = request.getParameter("class_name_select");
	        String staff_name = request.getParameter("staff_name_select");
	        String absent_string= request.getParameter("absent_string");
	        
	        out.println("<html>");
	        out.println("<head><title></ABSENTEES TODAY></title></head>");
	        out.println("<body>");
	        out.println(class_name+"<br/>");
	        out.println(staff_name+"<br/>");
	        out.println("<font size='200'>"+absent_string+"</font>");
	        out.println("</body>");        
	        
	        String dbURL="jdbc:mysql://localhost/absentupdate";             
	        Class.forName("com.mysql.jdbc.Driver");            
	        Connection con1 = DriverManager.getConnection(dbURL,"root","jit");                        
	        Statement stmt1 = con1.createStatement();
	        
	        Date date = new Date();
	        
	        String query = "select count(*) from absent_entry;";
	        ResultSet rs1 = stmt1.executeQuery(query);
	        rs1.next();
	        int new_SNo = rs1.getInt(1)+1;
	        query = "insert into absent_entry values("+new_SNo+",'"+date+"','"+class_name+"','"+staff_name+"','"+absent_string+"');";
	        stmt1.execute(query);
	        out.println("<br/>ENTRY SUCCESS");     
	        
	        
	        out.println("</html>");
	        
	        
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
