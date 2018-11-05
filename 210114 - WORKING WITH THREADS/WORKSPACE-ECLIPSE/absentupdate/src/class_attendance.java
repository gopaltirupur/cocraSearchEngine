

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
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
 * Servlet implementation class class_attendance
 */
@WebServlet("/class_attendance")
public class class_attendance extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public class_attendance() {
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
        ResultSet rs1=null;
        
        out.println("<html>");
        out.println("<head><body>CLASS ATTENDANCE</head>");
        out.println("<body>");
        
        out.println("<form name='f1' action='update' method='post'>");
        out.println("<select name='class_name_select'>");
        
        String query = "select *from class_list;";
        rs1 = stmt1.executeQuery(query);
        while(rs1.next())
        {
        	String whole_class;
        	whole_class = rs1.getString(1)+" YEAR "+rs1.getString(2)+" "+rs1.getString(3);
        	out.println("<option name='class_name' value='"+whole_class+"'>"+whole_class+"</option>");        	
        }
        out.println("</select><br/>");
                
        
        out.println("<select name='staff_name_select'>");        
        query = "select *from staff_list;";
        rs1 = stmt1.executeQuery(query);
        while(rs1.next())
        {
        	String whole_class;
        	whole_class = rs1.getString(2)+" "+rs1.getString(3);
        	out.println("<option name='class_name' value='"+whole_class+"'>"+whole_class+"</option>");        	
        }
        out.println("</select><br/>");
        
        out.println("<input type='text' name='absent_string' value='ENTER ABSENTEE DETAILS'>");
        out.println("<input type='submit' name='submit' value='SUBMIT'>");
        
        out.println("</form>");
        out.println("</body>");
        
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
