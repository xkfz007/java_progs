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
	// String zhText = "我们是中国人";
	// String zhText = "小笑话_总统的房间 Room .txt";
	String zhText = "一位绅士到旅游胜地的一家饭店要开个房间";

	Analyzer en1 = new StandardAnalyzer(); // 单字分词
	Analyzer en2 = new SimpleAnalyzer();

	Analyzer zh1 = new CJKAnalyzer(); // 二分法分词
	Analyzer zh2 = new MMAnalyzer(); // 词库分词

	@Test
	public void test() throws Exception {
		// analyze(en2, enText);
		// analyze(en1, zhText);

		// analyze(zh1, zhText);
		analyze(zh2, zhText);
	}

	public void analyze(Analyzer analyzer, String text) throws Exception {
		System.out.println("-------------> 分词器：" + analyzer.getClass());
		TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(text));
		for (Token token = new Token(); (token = tokenStream.next(token)) != null;) {
			System.out.println(token);
		}
	}
}
