package JavaSource;

public class MetaData 
{
	public String search_query;	
    public int search_query_length;
    
    public int[] search_query_count_arr;
    public int[][] digital_signature = new int[50][25];
    public String[] digital_signature_str = new String[50];
    
    public String[] snipped_lines = new String[50];
    public String[] URLs = new String[50];
    public int[] SNo = new int[50];
    public int URL_count;
}
