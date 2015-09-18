package cn.itcast.lucene.helloworld;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.junit.Test;

import cn.itcast.lucene.utils.File2DocumentUtils;

public class HelloWorld {

	String filePath = "F:\\hfz\\现代信息检索\\大作业\\程序\\LuceneDemoSrc\\luceneDatasource\\IndexWriter addDocument's a javadoc .txt";

	String indexPath = "F:\\hfz\\现代信息检索\\大作业\\程序\\LuceneDemoSrc\\luceneIndex";

	Analyzer analyzer = new StandardAnalyzer();

	/**
	 * 创建索引
	 * 
	 * IndexWriter 是用来操作（增、删、改）索引库的
	 */
	@Test
	public void createIndex() throws Exception {
		// file --> doc
		Document doc = File2DocumentUtils.file2Document(filePath);

		// 建立索引
		IndexWriter indexWriter = new IndexWriter(indexPath, analyzer, true,
				MaxFieldLength.LIMITED);
		indexWriter.addDocument(doc);
		indexWriter.close();
	}

	/**
	 * 搜索
	 * 
	 * IndexSearcher 是用来在索引库中进行查询的
	 */
	@Test
	public void search() throws Exception {
//		String queryString = "document";
		String queryString = "adddocument";

		// 1，把要搜索的文本解析为 Query
		String[] fields = { "name", "content" };
		QueryParser queryParser = new MultiFieldQueryParser(fields, analyzer);
		Query query = queryParser.parse(queryString);

		// 2，进行查询
		IndexSearcher indexSearcher = new IndexSearcher(indexPath);
		Filter filter = null;
		TopDocs topDocs = indexSearcher.search(query, filter, 10000);
		System.out.println("总共有【" + topDocs.totalHits + "】条匹配结果");

		// 3，打印结果
		for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
			int docSn = scoreDoc.doc; // 文档内部编号
			Document doc = indexSearcher.doc(docSn); // 根据编号取出相应的文档
			File2DocumentUtils.printDocumentInfo(doc); // 打印出文档信息
		}
	}
}
