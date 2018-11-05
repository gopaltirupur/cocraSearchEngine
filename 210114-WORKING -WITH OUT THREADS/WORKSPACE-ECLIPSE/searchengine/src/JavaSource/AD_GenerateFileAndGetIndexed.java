package JavaSource;

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
 * Servlet implementation class AD_GenerateFileAndGetIndexed
 */
@WebServlet("/AD_GenerateFileAndGetIndexed")
public class AD_GenerateFileAndGetIndexed extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AD_GenerateFileAndGetIndexed() {
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
            Statement stmt2 = con1.createStatement();
            Statement stmt3 = con1.createStatement();
            Statement stmt4 = con1.createStatement();
            Statement stmt5 = con1.createStatement();
            
            Class.forName("com.mysql.jdbc.Driver");            
            String query_delete_urlindex = "select *from url_index where FileGen='0' or FileGen is NULL order by sno;";            
            ResultSet rs;
            rs = stmt1.executeQuery(query_delete_urlindex);
            AD_GenerateFileAndGetIndexed obj_AD = new AD_GenerateFileAndGetIndexed();
            String[] list_of_new_URLs;
             
            while(rs.next())
            {
                String parent_URL = rs.getString(2);
                list_of_new_URLs = obj_AD.GenerateFileGiveNewURLsDoIndex(rs.getString(1),parent_URL,stmt2);
                obj_AD.InsertNewURLs(parent_URL,list_of_new_URLs,stmt3);            
            }
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AD_GenerateFileAndGetIndexed</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AD_GenerateFileAndGetIndexed at " + request.getContextPath() + "</h1>");
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
    
    public String[] GenerateFileGiveNewURLsDoIndex(String SNo_FileName,String URL,Statement stmt1) throws SQLException, IOException, NumberFormatException, ClassNotFoundException
    {
        String list_of_new_urls="";
        AD_GenerateFileAndGetIndexed obj_AD = new AD_GenerateFileAndGetIndexed();
        
        
        try {
            URL g = new URL(URL);
            BufferedReader in = new BufferedReader(new InputStreamReader(g.openStream()));
            String inputLine;  
            boolean Tag_switch=false;
            boolean Style_switch=false;
            boolean Script_switch=false;
                        
            FileWriter fo = new FileWriter("E:/v5/FILES/"+SNo_FileName+".txt");
            BufferedWriter bw = new BufferedWriter(fo);
            
            while(((inputLine)=in.readLine())!=null)
                bw.write(inputLine);
            
            bw.close(); fo.close(); in.close();
            
            FileReader fi = new FileReader("E:/v5/FILES/"+SNo_FileName+".txt");
            BufferedReader br = new BufferedReader(fi);
            
            fo = new FileWriter("E:/v5/FILES/"+SNo_FileName+"_pro.txt");
            bw = new BufferedWriter(fo);
            
            String q = "update url_index set FileGen=1 where SNo="+SNo_FileName+";";
            stmt1.execute(q);
            
            SEPARATE.InSertSignatures obj_separate_insert = new SEPARATE.InSertSignatures();
            obj_separate_insert.Generate_Signature_Insert(Integer.parseInt(SNo_FileName));
            
            
            
            while( (inputLine=br.readLine())!=null)            
            {
                 //out.println(inputLine);
                String inputLine_OnlyText="";
                for(int i=0;i<inputLine.length();i++)
                {
                    if(inputLine.charAt(i)=='<')
                    {
                        Tag_switch = true;                       
                        if( (inputLine.charAt(i+1)=='s') && (inputLine.charAt(i+2)=='t') && (inputLine.charAt(i+3)=='y') )                        
                        { 
                            //out.println("STYLE - TRUE");
                            Style_switch=true; 
                        } 
                        
                        if( (inputLine.charAt(i+1)=='s') && (inputLine.charAt(i+2)=='c') && (inputLine.charAt(i+3)=='r') )                       
                        {
                            //out.println("SCRIPT - TRUE");  
                            Script_switch=true;                                           
                        }
                        if((inputLine.charAt(i+1)=='a'))
                        {
                            i+=3;
                            while((inputLine.charAt((i++))!='h'));
                            while(inputLine.charAt(i++)!='"');
                            while(inputLine.charAt(i)!='"')
                                list_of_new_urls = list_of_new_urls+inputLine.charAt(i++);
                            list_of_new_urls=list_of_new_urls+";";                        
                        }                        
                        continue;                       
                     }                     
                    else if(inputLine.charAt(i)=='>')
                    {
                        Tag_switch=false;
                        if((inputLine.charAt(i-6)=='/') && (inputLine.charAt(i-5)=='s') && (inputLine.charAt(i-4)=='t'))
                        {
                            //out.println("STYLE-FALSE");    
                            Style_switch=false;
                        }
                        if( (inputLine.charAt(i-7)=='/') &&(inputLine.charAt(i-6)=='s') && (inputLine.charAt(i-5)=='c'))
                        {
                            //out.println("SCRIPT - FALSE");
                            Script_switch=false;   
                        }   
                        continue;
                    }
                    if(!Tag_switch&&!Style_switch&&!Script_switch)
                        bw.write(inputLine.charAt(i));
                 
                }
//                    out.println(inputLine);
//                    out.println("Switches Status "+Tag_switch+" "+Style_switch+" "+Script_switch);                    
            }
            bw.close(); fo.close(); 
            fi.close(); br.close();
            
            in.close();
        } catch (MalformedURLException me) {
           // out.println(me); 
            String q = "update url_index set FileGen='URLInvalid' where SNo="+SNo_FileName+";";
            stmt1.execute(q);
        } catch (IOException ioe) {
            String q = "update url_index set FileGen='IOProb' where SNo="+SNo_FileName+";";
            stmt1.execute(q);
            //out.println(ioe);
        }
        
        obj_AD.DoIndex(SNo_FileName,URL,stmt1);
        String q = "update url_index set Indexing=1 where SNo="+SNo_FileName+";";
        stmt1.execute(q);
        
        FileWriter fo_url = null;
		try {
			fo_url = new FileWriter("E:/v5/FILES/"+SNo_FileName+"_url.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
        BufferedWriter bw_url = new BufferedWriter(fo_url);
        String[] output =list_of_new_urls.split(";");
        for(int i=0;i<output.length;i++)
        {
        	bw_url.write(output[i]+"\n"+"*****");
        	
        }
        bw_url.close(); fo_url.close();
        
        return output;
    }
    public void DoIndex(String SNo_FileName,String URL,Statement stmt1) throws IOException, SQLException
    {
    	int SNo_int = Integer.parseInt(SNo_FileName);
    	String table_name="";
    	String q;
//    	out.println("test **");
    	/*
    	q = "insert into page_wise_index(SNo,URL) values("+SNo_int+",'"+URL+"');";
    	try {
			stmt1.execute(q);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  */  	
    	 
    	FileReader fi = null;
		try {
			fi = new FileReader("E:/v5/FILES/"+SNo_FileName+"_pro.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
        BufferedReader br = new BufferedReader(fi);
        String words;
        String[] words_arr = null;
        ResultSet rs = null;
        while( (words=br.readLine())!=null)
        {
        	words_arr = words.split("\\s+");
        
        
        for(int i=0;i<words_arr.length;i++)
        {
        	words_arr[i]=words_arr[i].toLowerCase();
        //	words_arr[i]=words_arr[i].replaceAll("[-,+.:ª~!@#$×¢®•_±¡§/%®1234567890&*)(_+`-}{|;<>?']", "");
        /*	words_arr[i]=words_arr[i].replaceAll("\"", "");
        	words_arr[i]=words_arr[i].replaceAll("\\\\", "");
        	words_arr[i]=words_arr[i].replaceAll("]", "");
        	words_arr[i]=words_arr[i].replaceAll("\\[", "");
        	words_arr[i]=words_arr[i].replaceAll("\\n", "");
        	words_arr[i]=words_arr[i].replaceAll("\\,", "");
        	words_arr[i]=words_arr[i].replaceAll("\\^", "");
        	words_arr[i]=words_arr[i].replaceAll("\\;", "");
        	words_arr[i]=words_arr[i].replaceAll("\\-", "");
        	*/
//        	System.out.println(">="+words_arr[i].length());
       	if((words_arr[i].length()>=2)&&(!words_arr[i].equals("to"))&&(!words_arr[i].equals("with"))&&(!words_arr[i].equals("and"))&&(!words_arr[i].equals("for"))&&(!words_arr[i].equals("by"))&&(!words_arr[i].equals("  ")))
       	{
//     		out.println(">="+words_arr[i].length());
//       	out.println("* "+words_arr[i]+" *");
        	table_name="";        	
        	table_name=words_arr[i].substring(0, 2)+"_page_wise_index";   
        	
//        	out.println("TN * *:"+table_name);
//        	out.println("//");
        	
        	//System.out.println("** WORD "+i+"**"+words_arr[i]);
        	try
        	{
        	//rs = null; 
       // 	String qq = "select *from information_schema.COLUMNS where TABLE_name='page_wise_index' and COLUMN_NAME='"+words_arr[i]+"';";
        	String qq="select *from information_schema.tables where table_name='"+table_name+"';";        	
        	rs = stmt1.executeQuery(qq);
        	rs.last();
        	int row_count = rs.getRow();
        	
//        	out.println(qq+"row_count ="+row_count);        	
        	
        	if(row_count==0) // NO TABLE EXISTS XX_PAGE_WISE_INDEX
        	{
        		//qq = "alter table page_wise_index add "+words_arr[i]+" bit;";
        		qq="create table "+table_name+"(SNo int(10),URL varchar(500),"+words_arr[i]+" bit);";
//        		out.println(qq);
        		stmt1.execute(qq);
        		
        		q = "insert into "+table_name+"(SNo,URL) values("+SNo_int+",'"+URL+"');";
//        		out.println(q);
            	try {
        			stmt1.execute(q);
        		} catch (MySQLSyntaxErrorException e) {
        			// TODO Auto-generated catch block
        			//e.printStackTrace();
        		} 
        	}        
        	else //TABLE EXISTS XX_PAGE_WISE_INDEX
        	{
        		q="select *from "+table_name+" where SNo="+SNo_int;
        		rs=stmt1.executeQuery(q);
        		rs.last();
        		row_count=rs.getRow();
//        		out.println(q+"row_count ="+row_count);
        		
        		if(row_count==0)
        		{
        			q = "insert into "+table_name+"(SNo,URL) values("+SNo_int+",'"+URL+"');";
//        			out.println(q);
                	try {
            			stmt1.execute(q);
            		} catch (SQLException e) {
            			// TODO Auto-generated catch block
//            			e.printStackTrace();
            		} 
        			
        		}
        		
        		qq="select *from information_schema.columns where table_name='"+table_name+"' and column_name='"+words_arr[i]+"';";
        		rs=stmt1.executeQuery(qq);
        		rs.last();
        		row_count = rs.getRow();
//        		out.println(qq+"row_count ="+row_count);
        		
        		if(row_count==0)
        		{
        			qq="alter table "+table_name+" add "+words_arr[i]+" bit;";
//        			System.out.println(qq);
//        			out.println(qq);
        			stmt1.execute(qq);
        		}
        	}
        	
        	qq="update "+table_name+" set "+words_arr[i]+"=1 where SNo="+SNo_int+";";
//    		out.println(qq);
        	stmt1.execute(qq);
        	}
        	catch(Exception e)
        	{
        		//e.printStackTrace();
        	}
        }
        }
        }        
    }
    public void InsertNewURLs(String parentURL,String[] list_of_new_URLs,Statement stmt) throws SQLException
    {
        ResultSet rs = stmt.executeQuery("select count(*) from url_index;");
        rs.next();
        int count = rs.getInt(1);
        //System.out.println("count "+count);
        AE_WholeURL obj_AE = new AE_WholeURL();
        String wholeURL="test";
        
        String q;
        
        for(int i=0;i<list_of_new_URLs.length;i++)
        {
        	System.out.println("Parent URL : "+parentURL);
        	System.out.println("Relative URL : "+list_of_new_URLs[i]);
        	
            wholeURL = obj_AE.getWholeURL(parentURL,list_of_new_URLs[i]);
            System.out.println("Whole URL : "+wholeURL);
            
            if((!wholeURL.contains(".php")) && (!wholeURL.contains(".pdf")) && (!wholeURL.contains("%")) && (!wholeURL.contains("="))&& (!wholeURL.contains("&")) && (!wholeURL.contains(".zip"))&& (!wholeURL.contains(".doc")) && (!wholeURL.contains("facebook")))
            {
            if( (wholeURL.charAt(0)=='h') && (wholeURL.charAt(1)=='t') && (wholeURL.charAt(2)=='t') && (wholeURL.charAt(3)=='p'))
            {
            	q="";            
            	q="insert into url_index(SNo,URL) values("+(++count)+",'"+wholeURL+"');";
            	try{
            	stmt.execute(q);
            	}
            	catch(Exception e)
            	{
            	}
            	
            }
            }
            else
            	continue;
        
        
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
