package index.query.regex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
	
	/*
	 *通过正则表达式对网页内容进行过滤 
	 */
	
	public static String regex( String line)
	{   
		
	   //StringBuffer htm = new StringBuffer();//存储所有的字符串
		
	    String reline = null;//作为返回字符串变量
   /*
    * 封装字符串可以使用readerLine()方法进行读取每一行
    */
		
	/*	BufferedReader sb = new BufferedReader(new StringReader(line));
		
		try {
			 reline = sb.readLine();
		
			while(line!= null)
			{htm.append(line+'\n');
			 reline = sb.readLine();
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	
	    String tegexpAttrib = "(<table width=\"100%\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\">.*<span class=\"mag\\_style5\">)";
		Pattern p = Pattern.compile(tegexpAttrib,Pattern.MULTILINE|Pattern.DOTALL);
		Matcher m = p.matcher(line);
		boolean result = m.find();
		int     cout = m.groupCount();
		for(int i = 0; i<cout;i++)
		{ 
			reline =m.group(i);
		
		}
		
		return reline;	
	}

}
