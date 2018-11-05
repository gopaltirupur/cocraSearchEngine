

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import JavaSource.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servlet implementation class crowlstart
 */
@WebServlet("/crowlstart")
public class crowlstart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public crowlstart() {
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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet crowlstart</title>");            
            out.println("</head>");
            
            
            String dbURL="jdbc:mysql://localhost/searchengine";
            Class.forName("com.mysql.jdbc.Driver");            
            Connection con = DriverManager.getConnection(dbURL,"root","jit");            
            Statement stmt = con.createStatement();
            String query_count = "select count(*) from url_index;";
            ResultSet rs;
            rs = stmt.executeQuery(query_count);
            rs.next();
            int noOfURLEntries = Integer.parseInt(rs.getString(1));
            
            /* TODO output your page here. You may use following sample code. */
            String received_url = request.getParameter("url");                      
            
            out.println("<body>");
            out.println("<h1>Servlet crowlstart " + "</h1><br/>");
            out.println(received_url+"<br/>");
            String query_insert = "insert into url_index(SNo,URL,FileGen,Indexing) values("+(++noOfURLEntries)+",'"+received_url+"',"+0+","+0+");";
            stmt.execute(query_insert);
            rs = stmt.executeQuery(query_count);
            rs.next();
            
            out.println("No. of Existing Entries in the URL Table :"+rs.getString(1)+"<br/>");
            AA_Crowl obj_AA = new AA_Crowl();
            obj_AA.startCrowlandGenerateMasterIndex(out,stmt,rs);
                        
            out.println("</body>");
            out.println("</html>");
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(crowlstart.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(crowlstart.class.getName()).log(Level.SEVERE, null, ex);
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
