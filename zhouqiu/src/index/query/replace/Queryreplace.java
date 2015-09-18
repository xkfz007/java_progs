package index.query.replace;

public class Queryreplace {
	/*
	 * 替换一行中".."的字符串成"http://www.ecice06.com"
	 */

public String replace(String line)
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
			 line =line.replace(pass, "http://www.ecice06.com");
			 
			// System.out.println(line) ;
			 
			
		  }
	 }
	return line;
 }
}
