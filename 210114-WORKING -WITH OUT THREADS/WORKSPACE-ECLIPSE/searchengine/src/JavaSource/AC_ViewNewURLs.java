package JavaSource;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

/**
 * Servlet implementation class AC_ViewNewURLs
 */
@WebServlet("/AC_ViewNewURLs")
public class AC_ViewNewURLs extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AC_ViewNewURLs() {
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
            Connection con2 = DriverManager.getConnection(dbURL,"root","jit");            
            out.println("test");
            Statement stmt2 = con2.createStatement();
            
            Class.forName("com.mysql.jdbc.Driver");            
            String query_delete_urlindex = "select *from url_index where FileGen='0' or FileGen is NULL order by sno;";            
            ResultSet rs;
            rs = stmt2.executeQuery(query_delete_urlindex);  
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AC_ViewNewURLs</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>New URLS Pending for Approval to Get Indexed</h1>");
            out.println("<form action='AD_GenerateFileAndGetIndexed' method='post'>");
            out.println("<input type='submit' value='GO AHEAD AND START INDEXING'>");
            out.println("</form>");
            out.println("<table border=1>");
            out.println("<thead><tr><th>S.No.</th><th>URL</th></tr></thead>");
            out.println("<tbody>");
            while(rs.next())
            {
                out.println("<tr><td>"+rs.getString(1)+"</td><td>"+rs.getString(2));
            }            
            out.println("</tbody>");
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
            
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AC_ViewNewURLs.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AC_ViewNewURLs.class.getName()).log(Level.SEVERE, null, ex);
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
