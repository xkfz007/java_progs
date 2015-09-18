package index.query.replace;

public class Queryjyreplace {
	/*
	 * 替换一行中".."的字符串成"http://www.computerapplications.com.cn/"
	 */

public static String replace(String line)
 {
	
	for(int i =0;i<line.length()-1;i++)
	 {
		if(i == line.length()-1)
		  {
			  break;
		  }
		String pass = line.substring(i,i+2);
		 if(pass.equals(".."))
		  {
			 line =line.replace(pass, "http://www.computerapplications.com.cn/");			 			 
			
		  }
	 }
	return line;
 }
}
