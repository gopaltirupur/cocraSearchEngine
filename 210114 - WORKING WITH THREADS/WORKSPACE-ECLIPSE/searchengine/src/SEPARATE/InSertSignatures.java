package SEPARATE;

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

public class InSertSignatures 
{
	public static void main(String args[]) throws ClassNotFoundException, SQLException
	{
		 String dbURL="jdbc:mysql://localhost/searchengine";             
         Class.forName("com.mysql.jdbc.Driver");            
         Connection con1 = DriverManager.getConnection(dbURL,"root","jit");                        
         Statement stmt1 = con1.createStatement();
         Statement stmt2 = con1.createStatement();
         
         String query = "select SNo,URL from url_index where FileGen=1;";
         ResultSet rs1 = stmt1.executeQuery(query);
         ResultSet rs2;
         
         int[] DigitalSignature = new int[25];
         
         AF_DigitalSignature obj_AF = new AF_DigitalSignature();       
         
                  
         while(rs1.next())
         {
        	 DigitalSignature = obj_AF.createSignature(rs1.getInt(1));
        	 for(int i=0;i<25;i++)
        	 {
        		 query = "update url_index set DigiSig"+(i+1)+" = "+DigitalSignature[i]+" where SNo="+rs1.getInt(1)+";";
        		 System.out.println(query);
        		 stmt2.execute(query);        		 
        	 }        	 
         }
	}
	
	public void Generate_Signature_Insert(int File_Name_int) throws ClassNotFoundException, SQLException
	{
		 String dbURL="jdbc:mysql://localhost/searchengine";             
         Class.forName("com.mysql.jdbc.Driver");            
         Connection con1 = DriverManager.getConnection(dbURL,"root","jit");                        
         Statement stmt1 = con1.createStatement();
         Statement stmt2 = con1.createStatement();
         
         int[] DigitalSignature = new int[25];
         
         AF_DigitalSignature obj_AF = new AF_DigitalSignature();
         
         {
        	 DigitalSignature = obj_AF.createSignature(File_Name_int);
        	 for(int i=0;i<25;i++)
        	 {
        		 String query = "update url_index set DigiSig"+(i+1)+" = "+DigitalSignature[i]+" where SNo="+File_Name_int+";";
        		 System.out.println(query);
        		 stmt2.execute(query);        		 
        	 }        	 
         }
		
	}
}
