
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TEMP_GetSnippet
{
	public static void main(String args[]) throws IOException
	{
		TEMP_GetSnippet obj_TEMP = new TEMP_GetSnippet();
		obj_TEMP.GiveSnippet(1, "UTFI");		
	
	}
	
	String GiveSnippet(int SNo,String search_query) throws IOException
	{
		FileReader fi = new FileReader("E:/v5/FILES/"+SNo+"_pro.txt");
        BufferedReader br = new BufferedReader(fi);
        
        String inputline="";
        String output="";
        String[] search_query_arr = search_query.split(" ");
        int snippet_words_count = 0;
        
        
        while( (inputline = br.readLine())!=null)
        {
        	String[] inputline_arr = inputline.split(" ");
        	
        	for(int i=0;i<inputline_arr.length;i++)
        	{
        		for(int j=0;j<search_query_arr.length;j++)
        		{        
        			if(inputline_arr[i].toLowerCase().equals(search_query_arr[j].toLowerCase()))
        			{
        				System.out.println("EQUALS");
        				while((snippet_words_count<=25) && (i<inputline_arr.length))
        				{
        					output = output + " "+inputline_arr[i++];
        					snippet_words_count++;        					
        				}
        				System.out.println("OUTPUT : "+output);
        				return output;
        			}
        		}
        	}
        }
        output = "SNIPPET WORD SNIPPET WORD SNIPPET WORD SNIPPET WORD SNIPPET WORD SNIPPET WORD ";
        System.out.println("OUTPUT : "+output);
        return output;
	}
	
}
