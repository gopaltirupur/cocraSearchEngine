

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import JavaSource.AF_DigitalSignature;
import JavaSource.AH_Start_File_Generation_And_File_Indexing_Now;

/**
 * Servlet implementation class Link_Open
 */
@WebServlet("/Link_Open")
public class Link_Open extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Link_Open() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
			processRequest(request, response);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException 
     * @throws ClassNotFoundException 
     * @throws NumberFormatException 
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NumberFormatException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String SNo_str = request.getParameter("val1");
        int SNo = Integer.parseInt(SNo_str);
        String URL = request.getParameter("val2");
        String Digital_Signature_Str = request.getParameter("val3");       
        
        URL g = new URL(URL);
        BufferedReader in = new BufferedReader(new InputStreamReader(g.openStream()));
        String inputLine;  
        
        FileWriter fo = new FileWriter("E:/v5/FILES/temp/"+SNo+".txt");
        BufferedWriter bw = new BufferedWriter(fo);
        
        while(((inputLine)=in.readLine())!=null)
            bw.write(inputLine);
        
        bw.close(); fo.close(); in.close();
        
        int[] Digital_Signature_int = new int[25];
        
        AF_DigitalSignature obj_AF = new AF_DigitalSignature();
        Digital_Signature_int = obj_AF.createSignature_temp("E:/v5/FILES/temp/"+SNo+".txt");
        
        String Generated_Digital_Signature_str="";
        for(int k=0;k<25;k++)
		{
			if(k==0)
			{
				Generated_Digital_Signature_str=""+Digital_Signature_int[k]+" ";
			}
			else if(k<25-1)
			{
				Generated_Digital_Signature_str=Generated_Digital_Signature_str+Digital_Signature_int[k]+" ";
			}
			else
			{
				Generated_Digital_Signature_str=Generated_Digital_Signature_str+Digital_Signature_int[k];
			}				
		}
        
        System.out.println("Digital Signature :"+Digital_Signature_Str);
        System.out.println("Generated Digital Signature :"+Generated_Digital_Signature_str);
        
        if(!Digital_Signature_Str.equals(Generated_Digital_Signature_str))
        {
        	AH_Start_File_Generation_And_File_Indexing_Now obj_AH = new AH_Start_File_Generation_And_File_Indexing_Now();
        	obj_AH.getParameters(SNo, URL);
        	obj_AH.quick_start();
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
			processRequest(request, response);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
