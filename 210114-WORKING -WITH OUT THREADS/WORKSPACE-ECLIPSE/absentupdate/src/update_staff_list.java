

import java.io.BufferedReader;
import java.io.FileReader;
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
 * Servlet implementation class update_staff_list
 */
@WebServlet("/update_staff_list")
public class update_staff_list extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public update_staff_list() {
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
        
        FileReader fi = new FileReader("E:/ABSENTUPDATE/staff_list.txt");
        BufferedReader br = new BufferedReader(fi);
        String inputline;
        String query;
        
        
        while( (inputline=br.readLine())!=null)
        {        	
        	String[] inputline_arr = inputline.split(";");
        	query = "insert into staff_list values("+inputline_arr[0]+",'"+inputline_arr[1]+"','"+inputline_arr[2]+"','"+inputline_arr[3]+"');";
        	stmt1.execute(query);
        }
        out.println("<h1>UPDATE SUCCESS</h1>");
        
        br.close();	fi.close();
        
        stmt1.close();	con1.close();
        
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
