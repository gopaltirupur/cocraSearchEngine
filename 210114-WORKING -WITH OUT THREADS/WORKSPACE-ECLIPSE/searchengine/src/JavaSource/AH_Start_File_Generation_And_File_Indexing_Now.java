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


public class AH_Start_File_Generation_And_File_Indexing_Now 
{

	public int SNo;
	public String URL;
	
	public void getParameters(int SNo,String URL)
	{
		this.SNo = SNo;
		this.URL = URL;
	}
	
	public void quick_start() throws ClassNotFoundException, SQLException, NumberFormatException, IOException
	{
		String dbURL="jdbc:mysql://localhost/searchengine";             
        Class.forName("com.mysql.jdbc.Driver");            
        Connection con1 = DriverManager.getConnection(dbURL,"root","jit");                        
        Statement stmt1 = con1.createStatement();
        Statement stmt2 = con1.createStatement();
        Statement stmt3 = con1.createStatement();
        
        ResultSet rs1=null;
        ResultSet rs2=null;
        ResultSet rs3=null;
        
        
		this.make_all_indexing_null(stmt1,stmt2,rs1,rs2,this.SNo,this.URL);		
		String[] list_of_new_URLs;
		
		AD_GenerateFileAndGetIndexed obj_AD = new AD_GenerateFileAndGetIndexed();
		
        list_of_new_URLs = obj_AD.GenerateFileGiveNewURLsDoIndex(Integer.toString(this.SNo),this.URL,stmt3);
        obj_AD.InsertNewURLs(this.URL,list_of_new_URLs,stmt3);		
	}
	
	public void make_all_indexing_null(Statement stmt1,Statement stmt2,ResultSet rs1,ResultSet rs2,int SNo,String URL) throws SQLException
	{
		String query = "select table_name from information_schema.tables where table_name regexp '.._page';";
		rs1 = stmt1.executeQuery(query);
		while(rs1.next())
		{
			String table_name = rs1.getString(1);
			query = "delete from "+table_name+" where SNo="+SNo+";";
			stmt2.execute(query);
			query = "insert into "+table_name+"(SNo,URL) values("+SNo+",'"+URL+"');";
			stmt2.execute(query);
		}
	}
	
}
