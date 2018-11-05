package JavaSource;

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


public class Test 
{
	public static void main(String args[]) throws ClassNotFoundException, SQLException
	{
	
		String dbURL="jdbc:mysql://localhost/searchengine";             
        Class.forName("com.mysql.jdbc.Driver");            
        Connection con1 = DriverManager.getConnection(dbURL,"root","jit");                        
                
        
        System.out.println("FROM * SEARCH.JAVA");
        
        String search_query = "the";
        if(search_query.equals(""))
        {
        	System.out.println("EMPTY QUERY");
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
		
		obj_Meta.search_query = search_query;
		
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
		}
	}	
}