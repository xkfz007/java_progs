package htmlParserTest;

import java.io.File;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasChildFilter;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.ParagraphTag;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.junit.Test;

public class MyHtmlparser {

	//String urlStr2 = "F:\\hfz\\现代信息检索\\大作业\\测试\\papers\\dff.htm";
	String urlStr2 = "F:\\hfz\\现代信息检索\\大作业\\测试\\papers\\P105-SIGIR1971.html";
	@Test
	public void getTitle()
	{
		try {
			NodeFilter filter = new TagNameFilter("td");
			Parser parser = new Parser();
            //设置url地址
			parser.setURL(urlStr2);
			parser.setEncoding(parser.getEncoding());
			
			NodeList list = parser.extractAllNodesThatMatch(filter);
			for (int i = 0; i < list.size(); i++) {
				Tag tag=(Tag)list.elementAt(i);
				if(tag.getAttribute("class")!=null)
					if(tag.getAttribute("class").equalsIgnoreCase("medium-text"))
				{
					//	System.out.println("TagID:"+tag.getIds());
				System.out.println("testNodeFilter() "
						+ list.elementAt(i).toHtml());
				if(list.elementAt(i).getFirstChild()!=null)
				{System.out.println("testNodeFilter-text: "
						+ list.elementAt(i).getFirstChild().toHtml());
				//Node nc=tag.getFirstChild();
		 		System.out.println("This is what I want!!!!:"+tag.toPlainTextString());
				}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void getTitle2()
	{
		try {
			NodeFilter filter = new HasAttributeFilter("class","medium-text");    

			Parser parser = new Parser();
            //设置url地址
			parser.setURL(urlStr2);
			parser.setEncoding(parser.getEncoding());
			
			NodeList list = parser.extractAllNodesThatMatch(filter);
			
			if(list.size()>0)
			{
				System.out.println(list.size());
				System.out.println(list);
				NodeFilter filter1 = new TagNameFilter("td");
				list=list.extractAllNodesThatMatch(filter1, true);
				System.out.println(list.size());
				if(list.size()>0)
				{	 
                
                    for(int i=0;i<list.size();i++)    
                    {   
                    	Tag tg=(Tag) list.elementAt(i);   
                           
                        System.out.println(tg.getTagName()+" "+tg.toPlainTextString());    
                    }  
                
				}

			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void getAbstract()
	{
	//	ArrayList <String> paraList=new ArrayList <String>();   
        
        Parser myParser=new Parser(); 
       
        NodeList nodeList = null;   
        
        NodeFilter paraFilter = new NodeClassFilter(ParagraphTag.class); 
		try
		{
			myParser.setURL(urlStr2);
			myParser.setEncoding(myParser.getEncoding());
			//myParser.setInputHTML(content);   
            nodeList = myParser.parse(paraFilter);  
            //NodeFilter filter1=new HasAttributeFilter("class","abstract");
            //nodeList=nodeList.extractAllNodesThatMatch(filter1, true);
            
            int K=0;
            String abt="",tmps;
            int len=0;
            for (int i = 0; i <= nodeList.size(); i++)
            {   
                   
             Tag tag = (Tag) nodeList.elementAt(i);   
                
             if(tag!=null){ 
            	 System.out.println((i+1)+"***********************************");  
              //  System.out.println(tag.getParent());   
                  
                 //paraList.add(tag.getText()); 
                 System.out.println(tag.toString());
                 System.out.println(tag.toPlainTextString());
                 System.out.println((i+1)+"***********************************");  
                 tmps=tag.toPlainTextString();
                 int tmpn=tmps.length();
                 if(tmpn>=len)
                 {
                	len=tmpn;
                	//K=i;
                	abt=tmps;
                 }
             }       
  
            }
            
            System.out.println("===============================");
            System.out.println(abt);

		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void getAuthors()
	{
		
	}
 @Test
	public void getTables()
	{
		 NodeFilter filter=new NodeClassFilter(TableTag.class);    

		 Parser parser = new Parser();
         //设置url地址
			try {
				parser.setURL(urlStr2);
				parser.setEncoding(parser.getEncoding());
			 NodeList nodelist=parser.extractAllNodesThatMatch(filter); 
			 if(nodelist.size()>0)
			 {
				 for(int i=0;i<nodelist.size();i++)
				 {
					 Tag tg=(Tag)nodelist.elementAt(i);
					 System.out.println((i+1)+"=============");
					 System.out.println(tg.toTagHtml());
				 }
			 }

				
			} catch (ParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
 @Test
 public void getInfo()
 {
	 NodeFilter filter=new NodeClassFilter(TableTag.class);    

	 Parser parser = new Parser();
     //设置url地址
		try {
			parser.setURL(urlStr2);
			parser.setEncoding(parser.getEncoding());
		 NodeList nodelist=parser.extractAllNodesThatMatch(filter); 
		 if(nodelist.size()>0)
		 {
			 //标题是0-14中的第7个
			 TableTag tableTitle=(TableTag)nodelist.elementAt(7);
			// System.out.println(table);
			 TableRow rowTitle=tableTitle.getRow(0);
			
			 System.out.println("标题为："+rowTitle.toPlainTextString().trim());
			 //作者信息时在第8个
			 TableTag tableAuthor=(TableTag)nodelist.elementAt(8);
			// System.out.println(tableAuthor.toPlainTextString());
			 TableRow[] rowAuthor=tableAuthor.getRows();
			 int rowcount=rowAuthor.length;
			 String[] authors=new String[rowcount];
			 String[] workplace=new String[rowcount];
			 TableRow tmprow=null;
			 TableColumn author=null,work=null;
			 for(int i=0;i<rowcount;i++)
			 {
				 tmprow=rowAuthor[i];
				 author=tmprow.getColumns()[0];
				 work=tmprow.getColumns()[1];
				 authors[i]=author.toPlainTextString().trim();
				 workplace[i]=work.toPlainTextString().trim().replace("&nbsp;", "");
				 System.out.println("第"+(i+1)+"个作者为："+authors[i]+",作者的工作单位为："+workplace[i]);
			 }
		 }

			
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 }
 
 public void getInfo(String urlStr)
 {
	 
     
		try 
		{
			NodeFilter filter=new NodeClassFilter(TableTag.class);    
			 
			 Parser parser = new Parser();
			  NodeList nodelist=null;
			  
			//设置url地址
			parser.setURL(urlStr);
			parser.setEncoding(parser.getEncoding());
			
			//提取标题，作者和作者工作单位信息
		 nodelist=parser.extractAllNodesThatMatch(filter); 
		 if(nodelist.size()>0)
		 {
			 //标题是0-14中的第7个
			 TableTag tableTitle=(TableTag)nodelist.elementAt(7);
			// System.out.println(table);
			 TableRow rowTitle=tableTitle.getRow(0);
			String title=strOp(rowTitle.toPlainTextString());
			 System.out.println("标题为："+title);
			 //作者信息时在第8个
			 TableTag tableAuthor=(TableTag)nodelist.elementAt(8);
			// System.out.println(tableAuthor.toPlainTextString());
			 TableRow[] rowAuthor=tableAuthor.getRows();
			 int rowcount=rowAuthor.length;
			 String[] authors=new String[rowcount];
			 String[] workplace=new String[rowcount];
			 TableRow tmprow=null;
			 TableColumn author=null,work=null;
			 for(int i=0;i<rowcount;i++)
			 {
				 tmprow=rowAuthor[i];
				 //这个地方要注意
				 if(tmprow.getColumnCount()>1)
				 {
					 author=tmprow.getColumns()[0];
					 work=tmprow.getColumns()[1];
				 authors[i]=strOp(author.toPlainTextString());
				 workplace[i]=strOp(work.toPlainTextString());//.trim().replace("&nbsp;", "");
				 }
				 
				 System.out.println("第"+(i+1)+"个作者为："+authors[i]+",作者的工作单位为："+workplace[i]);
			 }
		 }

		 NodeFilter paraFilter = new NodeClassFilter(ParagraphTag.class);
		 Parser parser2=new Parser();
		 
		 parser2.setURL(urlStr);
		parser2.setEncoding(parser2.getEncoding());
		 nodelist=parser2.extractAllNodesThatMatch(paraFilter);  
             
         int K=0;
         String abt="",tmps;
         int len=0;
         for (int i = 0; i <= nodelist.size(); i++)
         {   
                
          Tag tag = (Tag) nodelist.elementAt(i);   
             
          if(tag!=null&&(!tag.getAttribute("class").equalsIgnoreCase("keywords"))
        		  &&(!tag.getAttribute("class").equalsIgnoreCase("Categories")))
          { 
         	// System.out.println((i+1)+"***********************************");  
           //  System.out.println(tag.getParent());   Categories
               
              //paraList.add(tag.getText()); 
              System.out.println(tag.getAttribute("class"));
            //  System.out.println(tag.toPlainTextString().trim());
          //    System.out.println((i+1)+"***********************************");  
              tmps=tag.toPlainTextString().trim();
             // tmps=strOp(tmps);
              int tmpn=tmps.length();
              if(tmpn>=len)
              {
             	len=tmpn;
             	//K=i;
             	abt=tmps;
              }
          }       

         }
         
       //  System.out.println("===============================");
         abt=strOp(abt);
         System.out.println("摘要信息为："+abt);
		}
		catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 }
 public String strOp(String str)
 {
	 String s1=str.trim().replace("&nbsp;", "").replace("&amp;", "");
	 s1=s1.replaceAll("\\(.+\\)","");
	 return s1;
 }
 @Test
 public void noTest()
 {
	 String str = "aaaaa(bbbb)cccc"; 
	 String s=str.replaceAll("\\(",""); 
	 s=s.replaceAll("\\)","");
	 System.out.println("s="+s);
String	 s2=str.replaceAll("\\(.+\\)","");
	 System.out.println(s2);
	 
	str = "111.3.22.11"; 
	 str=str.replaceAll("(^|\\.)(\\d)(\\.|$)","$100$2$3"); 
	 str=str.replaceAll("(^|\\.)(\\d{2})(\\.|$)","$10$2$3"); 
	 str=str.replaceAll("(^|\\.)(\\d{2})(\\.|$)","$10$2$3"); 
	 str=str.replaceAll("(^|\\.)(\\d{1})(\\.|$)","$100$2$3"); 
	         
	 System.out.println(str); 

 }
 @Test
 public void tagTest()
 {
	 Parser parser = new Parser();
     //设置url地址
		try {
			parser.setURL(urlStr2);
			parser.setEncoding(parser.getEncoding());
			NodeFilter classAbstract=new HasAttributeFilter("class","abstract");
			NodeFilter nameAbstract=new HasAttributeFilter("name","abstract");
			NodeFilter filter2And=new AndFilter(classAbstract,nameAbstract);
			
			NodeFilter childFilter=new HasChildFilter(filter2And);

			NodeFilter divFilter = new TagNameFilter ("DIV");
			
			NodeFilter pFilter=new TagNameFilter ("p");
			
			NodeFilter parFilter=new HasParentFilter(new AndFilter(divFilter,childFilter));
			
			NodeFilter myFilter=new AndFilter(pFilter,parFilter);
			NodeList nodelist=null;
			
			nodelist=parser.extractAllNodesThatMatch(myFilter);  
			
			
		         String abt="",tmps;
		         int len=0;
		         System.out.println(nodelist.size());
		         for (int i = 0; i <= nodelist.size(); i++)
		         {   
		                
		          Tag tag = (Tag) nodelist.elementAt(i);   
		             
		          if(tag!=null)//&&(!tag.getAttribute("class").equalsIgnoreCase("keywords"))
		        		//  &&(!tag.getAttribute("class").equalsIgnoreCase("Categories")))
		          { 
		         	// System.out.println((i+1)+"***********************************");  
		           //  System.out.println(tag.getParent());   Categories
		               
		              //paraList.add(tag.getText()); 
		              System.out.println(tag.getTagName());
		            //  System.out.println(tag.toPlainTextString().trim());
		          //    System.out.println((i+1)+"***********************************");  
		              tmps=tag.toPlainTextString().trim();
		             // tmps=strOp(tmps);
		              int tmpn=tmps.length();
		              if(tmpn>=len)
		              {
		             	len=tmpn;
		             	//K=i;
		             	abt=tmps;
		              }
		          }       

		         }
		         System.out.println("摘要信息为："+abt);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
 }
 
 
 public void tagTest2(String urlStr)
 {
	 Parser parser = new Parser();
     //设置url地址
		try {
			parser.setURL(urlStr);
			parser.setEncoding(parser.getEncoding());
			NodeFilter classAbstract=new HasAttributeFilter("class","abstract");
			NodeFilter nameAbstract=new HasAttributeFilter("name","abstract");
		//	NodeFilter filter2And=new AndFilter(classAbstract,nameAbstract);
			
			NodeFilter childFilter1=new HasChildFilter(classAbstract);
			NodeFilter childFilter2=new HasChildFilter(nameAbstract);
			NodeFilter divFilter = new TagNameFilter ("DIV");
			
			NodeFilter pFilter=new TagNameFilter ("p");
			NodeFilter spanFilter=new TagNameFilter("span");
			NodeFilter spchild=new AndFilter(spanFilter,childFilter2);
			
			NodeFilter childFilter3=new HasChildFilter(spchild);
			
			NodeFilter parFilter=new HasParentFilter(new AndFilter(divFilter,childFilter3));
			
			NodeFilter myFilter=new AndFilter(pFilter,parFilter);
			NodeList nodelist=null;
			
			nodelist=parser.extractAllNodesThatMatch(new AndFilter(divFilter,childFilter3));  
			
			
		         String abt="",tmps;
		         int len=0;
		         System.out.println(nodelist.size());
		         Node divNode=nodelist.elementAt(0);
		        NodeList nodelist2= new NodeList();
		        	if(divNode!=null)
		        	{
		        		divNode.collectInto(nodelist2, pFilter);
		        	}
		        
		         for (int i = 0; i <= nodelist.size(); i++)
		         {   
		                
		          Node tag = nodelist2.elementAt(i);   
		             
		          if(tag!=null)//&&(!tag.getAttribute("class").equalsIgnoreCase("keywords"))
		        		//  &&(!tag.getAttribute("class").equalsIgnoreCase("Categories")))
		          { 
		         	// System.out.println((i+1)+"***********************************");  
		           //  System.out.println(tag.getParent());   Categories
		               
		              //paraList.add(tag.getText()); 
		              System.out.println(tag.getText());
		            //  System.out.println(tag.toPlainTextString().trim());
		          //    System.out.println((i+1)+"***********************************");  
		              tmps=tag.toPlainTextString().trim();
		             // tmps=strOp(tmps);
		              int tmpn=tmps.length();
		              if(tmpn>=len)
		              {
		             	len=tmpn;
		             	//K=i;
		             	abt=tmps;
		              }
		          }       

		         }
		         //对摘要进行字符串变换
		        abt=abt.replace("&nbsp;", "").replace("&amp;", "");
		         System.out.println("摘要信息为："+abt);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
 }
 //在表格中获取标题和作者相关信息
 public void getTitleAndAuthor(String urlStr)
 {
		try {
			NodeFilter filter = new NodeClassFilter(TableTag.class);

			Parser parser = new Parser();
			NodeList nodelist = null;

			// 设置url地址
			parser.setURL(urlStr);
			parser.setEncoding(parser.getEncoding());

			// 提取标题，作者和作者工作单位信息
			nodelist = parser.extractAllNodesThatMatch(filter);
			if (nodelist.size() > 0) {
				// 标题是0-14中的第7个
				TableTag tableTitle = (TableTag) nodelist.elementAt(7);
				// System.out.println(table);
				TableRow rowTitle = tableTitle.getRow(0);
				String title = strOp(rowTitle.toPlainTextString());
				System.out.println("标题为：" + title);
				// 作者信息时在第8个
				TableTag tableAuthor = (TableTag) nodelist.elementAt(8);
				// System.out.println(tableAuthor.toPlainTextString());
				TableRow[] rowAuthor = tableAuthor.getRows();
				int rowcount = rowAuthor.length;
				String[] authors = new String[rowcount];
				String[] workplace = new String[rowcount];
				TableRow tmprow = null;
				TableColumn author = null, work = null;
				for (int i = 0; i < rowcount; i++) {
					tmprow = rowAuthor[i];
					// 这个地方要注意
					if (tmprow.getColumnCount() > 1) {
						author = tmprow.getColumns()[0];
						work = tmprow.getColumns()[1];
						authors[i] = strOp(author.toPlainTextString());
						workplace[i] = strOp(work.toPlainTextString());// .trim().replace("&nbsp;",
																		// "");
					}

					System.out.println("第" + (i + 1) + "个作者为：" + authors[i]
							+ ",作者的工作单位为：" + workplace[i]);
				}
			}
		} catch (Exception e) {
	 e.printStackTrace();
	 }
 }
 //获取摘要信息，没有的话反话空字符串
 public void getAbstract(String urlStr)
 {
		Parser parser = new Parser();
		// 设置url地址
		try {
			parser.setURL(urlStr);
			parser.setEncoding(parser.getEncoding());
			// NodeFilter classAbstract=new
			// HasAttributeFilter("class","abstract");
			NodeFilter nameAbstract = new HasAttributeFilter("name", "abstract");
			// NodeFilter filter2And=new AndFilter(classAbstract,nameAbstract);

			// NodeFilter childFilter1=new HasChildFilter(classAbstract);
			NodeFilter childFilter2 = new HasChildFilter(nameAbstract);
			NodeFilter divFilter = new TagNameFilter("DIV");

			NodeFilter pFilter = new TagNameFilter("p");
			NodeFilter spanFilter = new TagNameFilter("span");
			NodeFilter spchild = new AndFilter(spanFilter, childFilter2);

			NodeFilter childFilter3 = new HasChildFilter(spchild);

			NodeList nodelist = null;

			nodelist = parser.extractAllNodesThatMatch(new AndFilter(divFilter,
					childFilter3));

			String abt = "", tmps;
			int len = 0;
			System.out.println(nodelist.size());
			Node divNode = nodelist.elementAt(0);
			NodeList nodelist2 = new NodeList();
			if (divNode != null) {
				divNode.collectInto(nodelist2, pFilter);
				for (int i = 0; i <nodelist2.size(); i++) {

					Node node = nodelist2.elementAt(i);

					if (node != null) {

						// paraList.add(tag.getText());
						System.out.println(node.getText());
						// System.out.println(tag.toPlainTextString().trim());
						// System.out.println((i+1)+"***********************************");
						tmps = node.toPlainTextString().trim();
						// tmps=strOp(tmps);
						int tmpn = tmps.length();
						if (tmpn >= len) {
							len = tmpn;
							// K=i;
							abt = tmps;
						}
					}

				}

			}

			// 对摘要进行字符串变换
			abt = abt.replace("&nbsp;", "").replace("&amp;", "");
			System.out.println("摘要信息为：" + abt);

		} catch (Exception e) {
			e.printStackTrace();
		}
 }
 
 @Test
 public void getAllInfo()
 {
		// String
		// path="F:\\hfz\\现代信息检索\\TopConferences\\SIGIR\\HTML\\SIGIR2002";
		String path = "";
		String path1 = "F:\\hfz\\现代信息检索\\TopConferences\\SIGIR\\HTML\\SIGIR";
		int[] No = new int[33];
		No[0] = 1971;
		No[1] = 1978;
		for (int i = 2; i <= 32; i++)
			No[i] = No[i - 1] + 1;
		for (int j = 0; j < 33; j++) {

			path = path1 + Integer.toString(No[j]);
			File fileDir = new File(path);
			File[] htmlFiles = fileDir.listFiles();
			for (int i = 0; i < htmlFiles.length; i++) {
				// String name=htmlFiles[i].getName();
				String fpath = htmlFiles[i].getAbsolutePath();
				// System.out.println(htmlFiles[i].getAbsolutePath());
				System.out.println("===========第" + (i + 1)
						+ "篇文章信息：=============");
				System.out.println(fpath);
				// getInfo(fpath);
				//tagTest2(fpath);
				getTitleAndAuthor(fpath);
				getAbstract(fpath);
			}
		}
	}
	
}
