package luceneTest;

import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.ChineseAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

public class MyLuTest {
	//测试分词器
public static void printKeywords(Analyzer analyzer,String content)throws Exception
{
	System.out.println("------->分词器:"+analyzer.getClass());
	Reader reader=new StringReader(content);
	TokenStream tokenStream=analyzer.tokenStream("content", reader);
	
	Token token=new Token();
	while((token=tokenStream.next(token))!=null)
	{
		System.out.println(token);
	}
}
//创建索引
public void createIndex()throws Exception
{

}

//主程序
public static void main(String args[])
{
	Analyzer luceneAnalyzer = new StandardAnalyzer();
	String s="我是中国人";
	try
	{
		System.out.println("======标准分词器=======");
	printKeywords(luceneAnalyzer,s);
	
	System.out.println("======中文分词器=======");
	luceneAnalyzer=new ChineseAnalyzer();
	printKeywords(luceneAnalyzer,s);
	
	
	}catch(Exception e){
		
	}
}
}
