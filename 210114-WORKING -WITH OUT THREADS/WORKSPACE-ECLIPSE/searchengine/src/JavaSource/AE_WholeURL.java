/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaSource;

/**
 *
 * @author M. Gopal
 */
public class AE_WholeURL {
    
    public String getWholeURL(String ParentURL,String RelativeURL)  
//    public static void main(String args[])
    {
        String WholeURL="";
       
        String op="";
        
        if(RelativeURL.equals("")||RelativeURL.equals(" "))
        {
        	WholeURL=ParentURL;
            return WholeURL;
        }
        
        else if(RelativeURL.length()>0&&RelativeURL.charAt(0)=='/')
        {
        	if((RelativeURL.charAt(0)=='/') && (RelativeURL.length()==1))
        	{
        		WholeURL=ParentURL;
                return WholeURL;
        	}
        int i=0;
        op = op+RelativeURL.charAt(i++);
//        System.out.println("Parent URL : "+ParentURL);
//        System.out.println("Relative URL : "+RelativeURL);
        while((i<RelativeURL.length()-1)&&(RelativeURL.charAt(i)!='/'))
        {
            op=op+RelativeURL.charAt(i++);
//            System.out.println(" i = "+i+" "+op);
        }
                
        	op=op+RelativeURL.charAt(i);
        
        
        //System.out.println(" op = "+op);
        
        int offset =ParentURL.indexOf(op);   
        //System.out.println("offset ="+offset);
        for(i=0;i<offset;i++)
        {
            WholeURL=WholeURL+ParentURL.charAt(i);            
        }
        int j=0;
        for(i=offset;i<(offset+RelativeURL.length()-1);i++)
        {
            WholeURL=WholeURL+RelativeURL.charAt(j++);
        }       
                    WholeURL=WholeURL+RelativeURL.charAt(j);
        //System.out.println(WholeURL);
        }
        else
            WholeURL=RelativeURL;
        return WholeURL;
    }
    
}
