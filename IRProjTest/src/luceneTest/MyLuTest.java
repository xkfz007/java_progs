package luceneTest;

import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.ChineseAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

public class MyLuTest {
	//���Էִ���
public static void printKeywords(Analyzer analyzer,String content)throws Exception
{
	System.out.println("------->�ִ���:"+analyzer.getClass());
	Reader reader=new StringReader(content);
	TokenStream tokenStream=analyzer.tokenStream("content", reader);
	
	Token token=new Token();
	while((token=tokenStream.next(token))!=null)
	{
		System.out.println(token);
	}
}
//��������
public void createIndex()throws Exception
{

}

//������
public static void main(String args[])
{
	Analyzer luceneAnalyzer = new StandardAnalyzer();
	String s="�����й���";
	try
	{
		System.out.println("======��׼�ִ���=======");
	printKeywords(luceneAnalyzer,s);
	
	System.out.println("======���ķִ���=======");
	luceneAnalyzer=new ChineseAnalyzer();
	printKeywords(luceneAnalyzer,s);
	
	
	}catch(Exception e){
		
	}
}
}
