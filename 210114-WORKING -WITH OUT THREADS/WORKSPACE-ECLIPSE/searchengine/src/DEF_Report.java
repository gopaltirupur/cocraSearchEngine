

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

import JavaSource.AD_GenerateFileAndGetIndexed;

/**
 * Servlet implementation class DEF_Report
 */
@WebServlet("/DEF_Report")
public class DEF_Report extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DEF_Report() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            String dbURL="jdbc:mysql://localhost/searchengine";             
            Class.forName("com.mysql.jdbc.Driver");            
            Connection con1 = DriverManager.getConnection(dbURL,"root","jit");                        
            Statement stmt1 = con1.createStatement();
            
            out.println("<!DOCTYPE html>");
            out.println("<html><head><meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1'><title>Report</title><style type='text/css'>body {	background-image: url(back2.jpg);}</style></head>");
            out.println("<body><center><br><br><br><br><table width='603' border='0' cellspacing='10' background='back.png'>");
                        
            
            String query = "select count(*) from url_index where FileGen=1;";            
            ResultSet rs;
            rs = stmt1.executeQuery(query);
            rs.next();
            int report_data;
            report_data = rs.getInt(1);
            out.println("<tr><td width='581' height='245'>");
                        
            
            out.println("NO. OF SITES INDEXED \t\t: "+report_data+"<br/>");
            
            query="select count(*) from url_index where FileGen is Null or FileGen=0;";
            rs = stmt1.executeQuery(query);
            rs.next();
            report_data = rs.getInt(1);
            out.println("NO. OF SITES IDENTIFIED AND TO BE INDEXED : "+report_data+"<br/>");
            
            query="select count(*) from information_schema.tables where table_name REGEXP '.._page';";
            rs = stmt1.executeQuery(query);
            rs.next();
            report_data = rs.getInt(1);
            out.println("NO. OF TABLES CREATED\t\t: "+report_data+"<br/>");
            
            Statement stmt2 = con1.createStatement();
            ResultSet rs2;
            
            query="select table_name from information_schema.tables where table_name regexp '.._page';";
//            out.println(query+" ** ");
            rs = stmt1.executeQuery(query);
            int no_of_words=0;
            while(rs.next())
            {
            	
            	String table_name = rs.getString(1);
//            	out.println(table_name+" ** ");
            	query="select count(*) from information_schema.columns where table_name='"+table_name+"';";
//            	out.println(query+" ** ");
            	rs2 = stmt2.executeQuery(query);
            	rs2.next();
            	no_of_words=no_of_words+(rs2.getInt(1))-2;
            }
            out.println("NO. OF WORDS CONSIDERED\t\t: "+no_of_words+"<br/>");
            
            out.println("</td></tr>");
            out.println("<tr><td height='46'><center><hr>");
            out.println("<form name='f1' action='DEF_Report' method='post'><input type='submit' value='REFRESH' name='submit'>");
            out.println("</form></center> </td></tr></table>");            
            out.println("</body>");
            out.println("</html>");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AD_GenerateFileAndGetIndexed.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
           // Logger.getLogger(AD_GenerateFileAndGetIndexed.class.getName()).log(Level.SEVERE, null, ex);
        } finally {            
            out.close();
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
