import JavaSource.*;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;

/**
 * Servlet implementation class PRI_Finish_Priority_Indexing
 */
@WebServlet("/PRI_Finish_Priority_Indexing")
public class PRI_Finish_Priority_Indexing extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PRI_Finish_Priority_Indexing() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String dbURL="jdbc:mysql://localhost/searchengine";             
        Class.forName("com.mysql.jdbc.Driver");            
        Connection con1 = DriverManager.getConnection(dbURL,"root","jit");                        
        Statement stmt1 = con1.createStatement();
        Statement stmt2 = con1.createStatement();        
        ResultSet rs1;
        ResultSet rs2 = null;
        String query = "select *From index_on_priority;";       
        
        rs1 = stmt1.executeQuery(query);
        PRI_Finish_Priority_Indexing obj_Pri_Finish = new PRI_Finish_Priority_Indexing();
        int SNo;
        while(rs1.next())
        {
        	String url = rs1.getString(2);
        	SNo = obj_Pri_Finish.ensure_availability_of_url(url,stmt2,rs2);
        	AH_Start_File_Generation_And_File_Indexing_Now obj_AH = new AH_Start_File_Generation_And_File_Indexing_Now();
        	obj_AH.getParameters(SNo, url);
        	obj_AH.quick_start();
        }
    }
    
    public int  ensure_availability_of_url(String url,Statement stmt2,ResultSet rs2) throws SQLException
    {
    	int output;
    	String query = "select count(*) from url_index where url='"+url+"';";
    	try {
			rs2 = stmt2.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	if(rs2.getInt(1)==0)
    	{
    		query = "select count(*) from url_index;";
    		rs2 = stmt2.executeQuery(query);
    		int new_SNo = rs2.getInt(1)+1;
    		output = new_SNo;
    		query = "insert into url_index(SNo,URL) values("+new_SNo+",'"+url+"');";
    		stmt2.execute(query);
    	}    
    	else
    	{
    		query = "select SNo from url_index where URL='"+url+"';";
    		rs2 = stmt2.executeQuery(query);
    		output = rs2.getInt(1);
    	}
    	return output;
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
