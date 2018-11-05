package JavaSource;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class AF_DigitalSignature
{
	public static void main(String args[])
	{
		AF_DigitalSignature obj_AF = new AF_DigitalSignature();
		obj_AF.createSignature(1);
	}
	public int[] createSignature(int n)
	{		
		String SNo_FileName = Integer.toString(n);
		int i,j;
		
		FileReader fi = null;
		try {
			fi = new FileReader("E:/v5/FILES/"+SNo_FileName+".txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        BufferedReader br = new BufferedReader(fi);
		int[] output_int_arr=new int[25];
		
		for(i=0;i<25;i++)
		{
			output_int_arr[i]=0;
		}
		
		String line;
		
		try {
			while( (line=br.readLine())!=null)
			{
				for(j=0;j<line.length();j++)
				{
					for(i=0;i<=(j%25);i++)
					{
						//System.out.println(i);
						output_int_arr[i]=(output_int_arr[i]+( (int)line.charAt(j)))%(2147483647);					
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		//System.out.println("********");
//		for(i=0;i<25;i++)
//		{			
//			System.out.print(output_int_arr[i]+" * ");
//		}		
		return output_int_arr;
	}
	
	public int[] createSignature_temp(String SNo_FileName)
	{		
		int i,j;
		
		FileReader fi = null;
		try {
			fi = new FileReader(SNo_FileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        BufferedReader br = new BufferedReader(fi);
		int[] output_int_arr=new int[25];
		
		for(i=0;i<25;i++)
		{
			output_int_arr[i]=0;
		}
		
		String line;
		
		try {
			while( (line=br.readLine())!=null)
			{
				for(j=0;j<line.length();j++)
				{
					for(i=0;i<=(j%25);i++)
					{
						//System.out.println(i);
						output_int_arr[i]=(output_int_arr[i]+( (int)line.charAt(j)))%(2147483647);					
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		//System.out.println("********");
		for(i=0;i<25;i++)
		{			
			System.out.print(output_int_arr[i]+" * ");
		}		
		return output_int_arr;
	}
}
