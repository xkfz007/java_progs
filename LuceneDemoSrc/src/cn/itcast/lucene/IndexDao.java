package cn.itcast.lucene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jeasy.analysis.MMAnalyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.NumberTools;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.RangeFilter;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;

public class IndexDao {

	String indexPath = "F:\\hfz\\现代信息检索\\大作业\\程序\\LuceneDemoSrc\\luceneIndex";

	// Analyzer analyzer = new StandardAnalyzer();
	Analyzer analyzer = new MMAnalyzer();// 词库分词

	/**
	 * 添加/创建索引
	 * 
	 * @param doc
	 */
	public void save(Document doc) {
		IndexWriter indexWriter = null;
		try {
			indexWriter = new IndexWriter(indexPath, analyzer, MaxFieldLength.LIMITED);
			indexWriter.addDocument(doc);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				indexWriter.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Term是搜索的最小单位，代表某个 Field 中的一个关键词，如：<title, lucene>
	 * 
	 * new Term( "title", "lucene" );
	 * 
	 * new Term( "id", "5" );
	 * 
	 * new Term( "id", UUID );
	 * 
	 * @param term
	 */
	public void delete(Term term) {
		IndexWriter indexWriter = null;
		try {
			indexWriter = new IndexWriter(indexPath, analyzer, MaxFieldLength.LIMITED);
			indexWriter.deleteDocuments(term);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				indexWriter.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 更新索引
	 * 
	 * <pre>
	 * indexWriter.deleteDocuments(term);
	 * indexWriter.addDocument(doc);
	 * </pre>
	 * 
	 * @param term
	 * @param doc
	 */
	public void update(Term term, Document doc) {
		IndexWriter indexWriter = null;
		try {
			indexWriter = new IndexWriter(indexPath, analyzer, MaxFieldLength.LIMITED);
			indexWriter.updateDocument(term, doc);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				indexWriter.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * <pre>
	 * totalPage = recordCount / pageSize;
	 * if (recordCount % pageSize &gt; 0)
	 * 	totalPage++;
	 * </pre>
	 * 
	 * @param queryString
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public QueryResult search(String queryString, int firstResult, int maxResults) {
		try {
			// 1，把要搜索的文本解析为 Query
			String[] fields = { "name", "content" };
			Map<String, Float> boosts = new HashMap<String, Float>();
			boosts.put("name", 3f);
			// boosts.put("content", 1.0f); 默认为1.0f

			QueryParser queryParser = new MultiFieldQueryParser(fields, analyzer, boosts);
			Query query = queryParser.parse(queryString);

			return search(query, firstResult, maxResults);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public QueryResult search(Query query, int firstResult, int maxResults) {
		IndexSearcher indexSearcher = null;

		try {
			// 2，进行查询
			indexSearcher = new IndexSearcher(indexPath);
			Filter filter = new RangeFilter("size", NumberTools.longToString(200)
					, NumberTools.longToString(1000), true, true);

			// ========== 排序
			Sort sort = new Sort();
			sort.setSort(new SortField("size")); // 默认为升序
			// sort.setSort(new SortField("size", true));
			// ==========

			TopDocs topDocs = indexSearcher.search(query, filter, 10000, sort);

			int recordCount = topDocs.totalHits;
			List<Document> recordList = new ArrayList<Document>();

			// ============== 准备高亮器
			Formatter formatter = new SimpleHTMLFormatter("<font color='red'>", "</font>");
			Scorer scorer = new QueryScorer(query);
			Highlighter highlighter = new Highlighter(formatter, scorer);

			Fragmenter fragmenter = new SimpleFragmenter(50);
			highlighter.setTextFragmenter(fragmenter);
			// ==============

			// 3，取出当前页的数据
			int end = Math.min(firstResult + maxResults, topDocs.totalHits);
			for (int i = firstResult; i < end; i++) {
				ScoreDoc scoreDoc = topDocs.scoreDocs[i];

				int docSn = scoreDoc.doc; // 文档内部编号
				Document doc = indexSearcher.doc(docSn); // 根据编号取出相应的文档

				// =========== 高亮
				// 返回高亮后的结果，如果当前属性值中没有出现关键字，会返回 null
				String hc = highlighter.getBestFragment(analyzer, "content", doc.get("content"));
				if (hc == null) {
					String content = doc.get("content");
					int endIndex = Math.min(50, content.length());
					hc = content.substring(0, endIndex);// 最多前50个字符
				}
				doc.getField("content").setValue(hc);
				// ===========

				recordList.add(doc);
			}

			// 返回结果
			return new QueryResult(recordCount, recordList);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				indexSearcher.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
