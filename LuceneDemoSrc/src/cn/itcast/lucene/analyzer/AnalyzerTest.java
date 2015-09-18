package cn.itcast.lucene.analyzer;

import java.io.StringReader;

import jeasy.analysis.MMAnalyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.junit.Test;

public class AnalyzerTest {

	String enText = "IndexWriter addDocument's a javadoc.txt";
	// String zhText = "�������й���";
	// String zhText = "СЦ��_��ͳ�ķ��� Room .txt";
	String zhText = "һλ��ʿ������ʤ�ص�һ�ҷ���Ҫ��������";

	Analyzer en1 = new StandardAnalyzer(); // ���ִַ�
	Analyzer en2 = new SimpleAnalyzer();

	Analyzer zh1 = new CJKAnalyzer(); // ���ַ��ִ�
	Analyzer zh2 = new MMAnalyzer(); // �ʿ�ִ�

	@Test
	public void test() throws Exception {
		// analyze(en2, enText);
		// analyze(en1, zhText);

		// analyze(zh1, zhText);
		analyze(zh2, zhText);
	}

	public void analyze(Analyzer analyzer, String text) throws Exception {
		System.out.println("-------------> �ִ�����" + analyzer.getClass());
		TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(text));
		for (Token token = new Token(); (token = tokenStream.next(token)) != null;) {
			System.out.println(token);
		}
	}
}
