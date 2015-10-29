package htmlParserTest;

import java.io.*;
import org.htmlparser.filters.*;
import org.htmlparser.*;
import org.htmlparser.nodes.*;
import org.htmlparser.tags.*;
import org.htmlparser.util.*;
import org.htmlparser.visitors.*;


public class HTMLParserTest
{
 public static void main(String args[]) throws Exception
 {
 // String path = "D:\\Webdup\\MyWebsites\\biti\\download\\latest\\cisco.biti.edu.cn\\index.html";
		String path = "F:\\hfz\\�ִ���Ϣ����\\����ҵ\\����\\papers\\dff.htm";

	 StringBuffer sbStr = new StringBuffer();
  BufferedReader reader  = new BufferedReader(new FileReader(new File(path)));
  String temp = "";
  while((temp=reader.readLine())!=null)
  {
   sbStr.append(temp);
   sbStr.append("\r\n");
  }
  reader.close();
 
 
  String result = sbStr.toString();
   //readAll(result);
        readTextAndLink(result);
  //    readByHtml(result);
    // readTextAndTitle(result);
 }
 

 //��ҳ�淽ʽ����.������׼��htmlҳ��
 public static void readByHtml(String content) throws Exception
    {
        Parser myParser;
        myParser = Parser.createParser(content, "utf-8");

        HtmlPage visitor = new HtmlPage(myParser);

        myParser.visitAllNodesWith(visitor);

        String textInPage = visitor.getTitle();
        System.out.println("============Result1 readbyHtml============");
        System.out.println(textInPage);
        NodeList nodelist ;
        nodelist = visitor.getBody();
        System.out.print(nodelist.asString().trim());
 
 
    }
   
    //��ȡ�ı����ݺͱ���
 public static void readTextAndTitle(String result) throws Exception
 {
	 System.out.println("============Result2 TextAndTitle============");
  Parser parser ;
  NodeList nodelist ;
  parser = Parser.createParser(result,"utf-8");
  NodeFilter textFilter = new NodeClassFilter(TextNode.class);
  NodeFilter titleFilter = new NodeClassFilter(TitleTag.class);
  OrFilter lastFilter = new OrFilter();
  lastFilter.setPredicates(new NodeFilter[]{textFilter,titleFilter});
  nodelist = parser.parse(lastFilter);
  Node[] nodes = nodelist.toNodeArray();
  String line ="";
  for(int i=0;i<nodes.length;i++)
  {
   Node node = nodes[i];
   if(node instanceof TextNode)
   {
    TextNode textnode = (TextNode) node;
    line = textnode.getText();
   }
   else
   if(node instanceof TitleTag)
   {
    TitleTag titlenode = (TitleTag) node;
    line = titlenode.getTitle();
   }
   if (isTrimEmpty(line))
                continue;
            System.out.println(line);
  }
 }
 
 //�ֱ�����ı�������
 
 public static void readTextAndLink(String result) throws Exception
 {
	 System.out.println("============Result3 readTextAndLink============");
  Parser parser;
  NodeList nodelist;
  parser = Parser.createParser(result,"utf-8");
  NodeFilter textFilter = new NodeClassFilter(TextNode.class);
  NodeFilter linkFilter = new NodeClassFilter(LinkTag.class);
  OrFilter lastFilter = new OrFilter();
  lastFilter.setPredicates(new NodeFilter[] { textFilter, linkFilter });
  nodelist = parser.parse(lastFilter);
  Node[] nodes = nodelist.toNodeArray();
  String line ="";
  for(int i=0;i<nodes.length;i++)
  {
   Node node = nodes[i];
   if(node instanceof TextNode)
   {
    TextNode textnode = (TextNode) node;
    line = textnode.getText();
   }
   else
   if(node instanceof LinkTag)
   {
    LinkTag link = (LinkTag)node;
    line = link.getLink();
   }
   if (isTrimEmpty(line))
                continue;
            System.out.println(line);
  }
 }
 
 
 
 
 
 public static void readAll(String result) throws Exception
 {
  Parser parser;
  Node[] nodes ;
  parser = Parser.createParser(result,"utf-8");
 // nodes = parser.extractAllNodesThatAre(TextNode.class);
  System.out.println("============Result4 readALL============");
  nodes = parser.extractAllNodesThatMatch(new NodeClassFilter(TextNode.class)).toNodeArray();
  //��ȡ���е����ݽڵ�
  for (int i = 0; i < nodes.length; i++)
        {
            TextNode textnode = (TextNode) nodes[i];
            String line = textnode.toPlainTextString().trim();
            if (line.equals(""))
                continue;
            System.out.println(line);
        }
 }
 
 
 /**
     * ȥ�����ҿո���ַ����Ƿ�Ϊ��
     */
    public static boolean isTrimEmpty(String astr)
    {
        if ((null == astr) || (astr.length() == 0))
        {
            return true;
        }
        if (isBlank(astr.trim()))
        {
            return true;
        }
        return false;
    }

    /**
     * �ַ����Ƿ�Ϊ��:null���߳���Ϊ0.
     */
    public static boolean isBlank(String astr)
    {
        if ((null == astr) || (astr.length() == 0))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

 
}