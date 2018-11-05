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


public class AGZ_Drop 
{
	public static void main(String args[])
	{
		AGZ_Drop obj_AGZ = new AGZ_Drop();
		try {
			obj_AGZ.dropTables();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void dropTables() throws ClassNotFoundException, SQLException
	{
		String table_name="";
		String q="";
		
		String dbURL="jdbc:mysql://localhost/searchengine";             
        Class.forName("com.mysql.jdbc.Driver");            
        Connection con1 = DriverManager.getConnection(dbURL,"root","jit");                        
        Statement stmt1 = con1.createStatement();
        Statement stmt2 = con1.createStatement();
        
        Class.forName("com.mysql.jdbc.Driver");            
        String list_of_page_wise_index = "select table_name from information_schema.tables where table_name regexp '.._page';";
        System.out.println(list_of_page_wise_index);
        ResultSet rss;
        rss = stmt1.executeQuery(list_of_page_wise_index);
        System.out.println("OK");		
		
        try {
			while(rss.next())
			{            		
			    table_name = rss.getString(1);	    
			    
			    q="drop table "+table_name+";";
			    
			    stmt2.execute(q);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
