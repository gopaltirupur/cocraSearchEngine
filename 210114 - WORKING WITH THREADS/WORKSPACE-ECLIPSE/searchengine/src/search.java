

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

import JavaSource.MetaData;

/**
 * Servlet implementation class search
 */
@WebServlet("/search")
public class search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public search() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String dbURL="jdbc:mysql://localhost/searchengine";             
        Class.forName("com.mysql.jdbc.Driver");            
        Connection con1 = DriverManager.getConnection(dbURL,"root","jit");                        
                
        
        System.out.println("FROM * SEARCH.JAVA");
        
        String search_query = request.getParameter("search");
        String sq_for_obj = search_query;
        if(search_query.equals(""))
        {
        	out.println("EMPTY QUERY");        	
        	return;
        }
        	
        System.out.println("RECEIVED "+search_query);
        
        String[] search_query_str_arr = search_query.split(" ");
        int search_query_length = search_query_str_arr.length;
        
        int[] search_query_count_arr = new int[search_query_length];
        int i;
        
        for(i=0;i<search_query_length;i++)
        {
        	search_query_count_arr[i]=0;        	
        }
        
        String sql_query="";
        
        for(i=0;i<search_query_length;i++)
        {
        	if(search_query_str_arr[i].length()<2)
        	{
        		System.out.println("EACH WORD SHOULD HAVE MORE THAN ONE LETTER");
        		continue;
        	}
        	int row_count=0;
        	String search_word=search_query_str_arr[i];
        	String table_name = ""+search_word.charAt(0)+search_word.charAt(1)+"_page_wise_index";
        	
        	//CHECK AVAILABILITY OF TABLE
        	sql_query="select count(*) from information_schema.tables where table_name='"+table_name+"';";
        	System.out.println(sql_query);
        	Statement stmt1 = con1.createStatement();
        	ResultSet rs1 = stmt1.executeQuery(sql_query);
        	rs1.next();
        	row_count=rs1.getInt(1);        	
        	if(row_count==0)
        	{
        		System.out.println("Table Not Available");
        		continue; //TABLE NOT AVAILABLE        		        		
        	}
        	System.out.println("Table Available");
        	
        	//CHECK AVAILABILITY OF COLUMN (TABLE AVAILABLE)
        	sql_query="select count(*) from information_schema.columns where table_name='"+table_name+"' and column_name='"+search_word+"';";
        	rs1=stmt1.executeQuery(sql_query);
        	rs1.next();
        	row_count=rs1.getInt(1);
        	if(row_count==0)
        	{
        		System.out.println("Column Not Available");
        		continue; //COLUMN NOT AVAILABLE
        	}
        	System.out.println("Column Available");
        	
        	//TABLE AND COLUMN AVAILABLE - ACQUIRE NO_OF_RECORDS
        	sql_query="select count(*) from "+table_name+" where "+search_word+"=1;";
        	rs1=stmt1.executeQuery(sql_query);
        	rs1.next();
        	row_count=rs1.getInt(1);
        	System.out.println(search_query_str_arr[i]+" "+row_count);
        	search_query_count_arr[i]=row_count;
        	System.out.println("Word :"+search_word+" Count :"+row_count);
        }
        
        int availability_count=0;
        for(i=0;i<search_query_length;i++)
        {
        	if(search_query_count_arr[i]>0)
        		availability_count++;
        }
        
        String[] rev_search_query_str_arr = new String[availability_count]; 
        int[] rev_search_query_count_arr = new int[availability_count];
        
        //CONSTRUCT STRING ARRAY ON ONLY AVAILABLE WORDS FOR QUERY GENERATION
        int j=0;
        for(i=0;i<availability_count;i++)
        {
        	if(search_query_count_arr[j]>0)
        	{
        		rev_search_query_str_arr[i]=search_query_str_arr[j];
        		rev_search_query_count_arr[i]=search_query_count_arr[j];
        		j++;
        	}
        	else
        		continue;
        }
        
        search_query="";
        String search_query_attributes="";
        String search_query_tables="";
        String search_query_where1="";
        String search_query_where2="";
        
        int process_count=1;
        String prev_part_table="";
        String first_part_table="";
        
        for(i=0;i<rev_search_query_str_arr.length;i++)
        {
        	String current_table_name=""+rev_search_query_str_arr[i].charAt(0)+rev_search_query_str_arr[i].charAt(1)+"_page_wise_index";
        	String current_word = rev_search_query_str_arr[i];        	
        	
        	if(rev_search_query_str_arr.length==1)
        	{       		
        		search_query="select SNo,URL from "+current_table_name+" where "+current_word+"=1;";
        	}        	
        	
        	if(rev_search_query_count_arr[i]>0)
        	{
        		String part_table = current_table_name.substring(0, 2);        		
        		
        		if(process_count==1)
        		{
        			first_part_table = part_table;        			
        		}        		
        		
        		if(!search_query_tables.contains(current_table_name))
        		{  
        			if(process_count==1)      			
        				search_query_tables = search_query_tables + " "+current_table_name + " as " + part_table;
        			else
        				search_query_tables = search_query_tables + ", "+current_table_name + " as " + part_table;
        		}
        		if(!search_query_attributes.contains(part_table+"."+current_word))
        			search_query_attributes = search_query_attributes + " "+part_table+"."+current_word+" ";
        		if(!search_query_where1.contains(part_table+"."+current_word))
        		{	
        			if(search_query_where1.equals(""))
        					search_query_where1 = " "+part_table+"."+current_word+"=1";
        			else
        					search_query_where1=search_query_where1+" and "+ part_table+"."+current_word+"=1";        			
        		}
        		if(process_count>1)
        		{
        			if(i==1)
        				search_query_where2 = prev_part_table+".SNo="+part_table+".SNo";
        			else
        				search_query_where2 = search_query_where2+" and "+prev_part_table+".SNo="+part_table+".SNo";        			
        		}
        		prev_part_table=part_table;
        		process_count++;
        	}        	
        }
        
        if(rev_search_query_str_arr.length!=1)
        {
        	search_query="select "+first_part_table+".SNo,"+first_part_table+".URL from "+search_query_tables;
        	search_query=search_query+" where "+search_query_where1+" and "+search_query_where2+";";        	
        }      	
        
        System.out.println(search_query);
        Statement stmt2 = con1.createStatement();
        ResultSet rs2 = stmt2.executeQuery(search_query);
		rs2.last();
		
		MetaData obj_Meta = new MetaData();
		obj_Meta.URL_count=rs2.getRow();
		rs2.first();
		
		obj_Meta.search_query = sq_for_obj;		
		obj_Meta.search_query_length = search_query_length;
		
		obj_Meta.search_query_count_arr = new int[obj_Meta.search_query_length];
		for(i=0;i<obj_Meta.search_query_length ;i++)
		{
			obj_Meta.search_query_count_arr[i] = search_query_count_arr[i];			
		}
		
		// GET SNIPPED LINES 
		for(i=0;i<50;i++)
		{
			obj_Meta.URLs[i]=rs2.getString(2);
			obj_Meta.SNo[i]=rs2.getInt(1);
			obj_Meta.snipped_lines[i]=" SNIPPED LINE TEST SNIPPED LINE TEST SNIPPED LINE TEST SNIPPED LINE TEST ";
			//out.println(obj_Meta.SNo[i]+"  "+obj_Meta.URLs[i]);
			if(rs2.next())
				continue;
			else 
				break;			
		}
		
		//ADDING DIGITAL SIGNATURE WITH THE obj_Meta Object
		
		int[][] Digital_Signature = new int[50][25];
		Statement stmt3 = con1.createStatement();
		ResultSet rs3=null;
		for(i=0;(i<50)&&(i<obj_Meta.URL_count);i++)
		{
			String query = "select *from url_index where SNo="+obj_Meta.SNo[i]+";";
			rs3 = stmt3.executeQuery(query);
			rs3.next();
			//if(!rs3.wasNull())
			for(int k=0;k<25;k++)
			{
				Digital_Signature[i][k]=rs3.getInt(5+k);
			}
		}
		obj_Meta.digital_signature=Digital_Signature;	
		
		
		for(i=0;i<50;i++)
		{
			for(int k=0;k<25;k++)
			{
				if(k==0)
				{
					obj_Meta.digital_signature_str[i]=""+obj_Meta.digital_signature[i][k]+" ";
				}
				else if(k<25-1)
				{
					obj_Meta.digital_signature_str[i]=obj_Meta.digital_signature_str[i]+obj_Meta.digital_signature[i][k]+" ";
				}
				else
				{
					obj_Meta.digital_signature_str[i]=obj_Meta.digital_signature_str[i]+obj_Meta.digital_signature[i][k];
				}				
			}
		}
		
		TEMP_GetSnippet obj_TEMP_GetSnippet = new TEMP_GetSnippet();
				
		for(i=0;(i<50)&&(i<obj_Meta.URL_count);i++)
		{
			obj_Meta.snipped_lines[i]=obj_TEMP_GetSnippet.GiveSnippet(obj_Meta.SNo[i], obj_Meta.search_query);
						
		}	
		
		search obj_search = new search();
		obj_search.print_search_result(out, obj_Meta);		
    }
    void print_search_result(PrintWriter out,MetaData obj_Meta)
    {
    	out.println("<html>");
        out.println("<head>");
        out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"csstemp.css\">");
        out.println("</head>");
        
        out.println("<body bgcolor=\"#E0E0D1\">");
        out.println("<img src=\"logo.png\" alt=\"logo missing\" width=\"200\" height=\"65\" align=\"absmiddle\">");
        out.println("<form method=\"post\" action=\"search\" name=\"f1\" >");        
        out.println("<input type=\"text\" value='"+obj_Meta.search_query+"' size=\"50\" name=\"search\">");
        out.println("<input type=\"submit\" name=\"searchbt\" value=\"Search\" align=\"left\">");
        out.println("</form>");        
        
        //String[] words=obj_Meta.search_query.split(" ");        
       
        for(int i=0;i<obj_Meta.search_query_count_arr.length;i++)
        {
        	if(obj_Meta.search_query_count_arr[i]==0)
             {
        		out.print("<h1 class=strike> "+obj_Meta.search_query.split(" ")[i]+"</h1>");
             }
             else
             {
            	 out.print("<h1 class=avail> "+obj_Meta.search_query.split(" ")[i]+"</h1>");
             }             
         }
         
        out.println("<br><hr>");        
        out.println("<p class=pcount>About "+obj_Meta.URL_count+" results</p>");
        
        out.println("<table border='0' width=\"1000\">");
        //  out.println("<tr rowspan=2><td><img src=\"C:\\Users\\cynete-lap\\Desktop\\cocra files\\netbean files\\WebApplication2\\web\\logo.png\" /></td></tr>");
        if(obj_Meta.URL_count==0)
        {
        	out.println("No Matching Records Found in the Search Engine");
        }
        else
          for(int i=0;(i<50)&&(i<obj_Meta.URL_count);i++)
          {              
               out.println("<tr >");
               out.println("<td width=\"300\">"+(i+1)+"  -----------</td>");
               out.println("<td width=\"933\">");               
                   out.println("<table class=tab border='0' bgcolor=\"#FFFFFF\" width=800>");          //creating nested table
                   out.println("<tr>");                   
                       out.println("<td  width=\"400\">");
                       out.println("<form action='Link_Open' method='post'>");
                       
                       out.println("<input type='hidden' name='val1' value='"+obj_Meta.SNo[i]+"' >");
                       out.println("<input type='hidden' name='val2' value='"+obj_Meta.URLs[i]+"' >");
                       out.println("<input type='hidden' name='val3' value='"+obj_Meta.digital_signature_str[i]+"' >");
                       
                       out.print("<a class='link' href='javascript:;' onclick='parentNode.submit();' >"+obj_Meta.URLs[i]+"</a>");
                       //out.print("<a class=link href='"+obj_Meta.URLs[i]+"'>"+obj_Meta.URLs[i]+"</a>");
                       //out.println("<br>");
                       out.print("<p class=slink>"+obj_Meta.URLs[i]+"</p>");
                       //out.println("<br>");
                       out.print("<p class=desc>"+obj_Meta.snipped_lines[i]+"</p>");
                       out.println("</form>");
                       out.println("</td>");                  
                   out.println("</tr>");
                   out.println("</table>");                   
               out.println("</td>");
               out.println("</tr>");          
          }          
          out.println("</table>");
          out.println("<a href=\"/WebApplication2/client\"> >> </a>");
          out.println("</body>");
         // out.println("</center>");
          out.println("</html>");          
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
