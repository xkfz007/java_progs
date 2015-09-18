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

	String filePath = "F:\\hfz\\�ִ���Ϣ����\\����ҵ\\����\\LuceneDemoSrc\\luceneDatasource\\IndexWriter addDocument's a javadoc .txt";

	String indexPath = "F:\\hfz\\�ִ���Ϣ����\\����ҵ\\����\\LuceneDemoSrc\\luceneIndex";

	Analyzer analyzer = new StandardAnalyzer();

	/**
	 * ��������
	 * 
	 * IndexWriter ����������������ɾ���ģ��������
	 */
	@Test
	public void createIndex() throws Exception {
		// file --> doc
		Document doc = File2DocumentUtils.file2Document(filePath);

		// ��������
		IndexWriter indexWriter = new IndexWriter(indexPath, analyzer, true,
				MaxFieldLength.LIMITED);
		indexWriter.addDocument(doc);
		indexWriter.close();
	}

	/**
	 * ����
	 * 
	 * IndexSearcher ���������������н��в�ѯ��
	 */
	@Test
	public void search() throws Exception {
//		String queryString = "document";
		String queryString = "adddocument";

		// 1����Ҫ�������ı�����Ϊ Query
		String[] fields = { "name", "content" };
		QueryParser queryParser = new MultiFieldQueryParser(fields, analyzer);
		Query query = queryParser.parse(queryString);

		// 2�����в�ѯ
		IndexSearcher indexSearcher = new IndexSearcher(indexPath);
		Filter filter = null;
		TopDocs topDocs = indexSearcher.search(query, filter, 10000);
		System.out.println("�ܹ��С�" + topDocs.totalHits + "����ƥ����");

		// 3����ӡ���
		for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
			int docSn = scoreDoc.doc; // �ĵ��ڲ����
			Document doc = indexSearcher.doc(docSn); // ���ݱ��ȡ����Ӧ���ĵ�
			File2DocumentUtils.printDocumentInfo(doc); // ��ӡ���ĵ���Ϣ
		}
	}
}
