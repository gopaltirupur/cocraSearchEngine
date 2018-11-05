
public class MetaData 
{
	public String search_query;
	String[] search_query_str_arr = search_query.split(" ");
    int search_query_length = search_query_str_arr.length;
    
    int[] search_query_count_arr = new int[search_query_length];
    int[][] digital_signature = new int[50][25];
    public String[] digital_signature_str = new String[50];
    
    String[] snipped_lines = new String[50];
    String[] URLs = new String[50];
    int[] SNo = new int[50];
    int URL_count;
}
