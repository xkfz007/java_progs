package project;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasChildFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.junit.Test;


public class CreateIndex {

	// String dataS="F:\\hfz\\现代信息检索\\大作业\\程序\\IRProjTest\\datasource";
	// IndexWriter indexWriter = new IndexWriter(indexDir,
	// luceneAnalyzer,true,IndexWriter.MaxFieldLength.UNLIMITED);
	// 字符串格式化处理
	public String strOp(String str) {
		String s1 = str.trim().replace("&nbsp;", "").replace("&amp;", "");
		s1 = s1.replaceAll("\\(.+\\)", "");
		return s1;
	}

	// 在表格中获取标题和作者相关信息
	public Document getTitleAndAuthor(String urlStr, Document doc) {
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

				// 添加title
				doc.add(new Field("title", title, Store.YES, Index.ANALYZED));
				// System.out.println("标题为：" + title);

				// 作者信息时在第8个
				TableTag tableAuthor = (TableTag) nodelist.elementAt(8);
				// System.out.println(tableAuthor.toPlainTextString());
				TableRow[] rowAuthor = tableAuthor.getRows();
				int rowcount = rowAuthor.length;
				// 初始化
				String[] authors = new String[rowcount];
				String[] workplace = new String[rowcount];
				for (int i = 0; i < rowcount; i++) {
					authors[i] = "";
					workplace[i] = "";
				}
				TableRow tmprow = null;
				TableColumn authorCol = null, workCol = null;
				for (int i = 0; i < rowcount; i++) {
					tmprow = rowAuthor[i];
					// 这个地方要注意
					if (tmprow.getColumnCount() > 1) {
						authorCol = tmprow.getColumns()[0];
						workCol = tmprow.getColumns()[1];
						authors[i] = strOp(authorCol.toPlainTextString());
						workplace[i] = strOp(workCol.toPlainTextString());// .trim().replace("&nbsp;",
						// "");
					}
					doc.add(new Field("author", authors[i], Store.YES, Index.ANALYZED));
					doc.add(new Field("workplace", workplace[i], Store.YES, Index.ANALYZED));

					// System.out.println("第" + (i + 1) + "个作者为：" + authors[i]
					// + ",作者的工作单位为：" + workplace[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return doc;
	}

	// 获取摘要信息，没有的话反话空字符串
	public Document getAbstract(String urlStr, Document doc) {
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

			nodelist = parser.extractAllNodesThatMatch(new AndFilter(divFilter, childFilter3));

			String abt = "", tmps;
			int len = 0;
			// System.out.println(nodelist.size());
			Node divNode = nodelist.elementAt(0);
			NodeList nodelist2 = new NodeList();
			if (divNode != null) {
				divNode.collectInto(nodelist2, pFilter);
				for (int i = 0; i < nodelist2.size(); i++) {

					Node node = nodelist2.elementAt(i);

					if (node != null) {

						// paraList.add(tag.getText());
						// System.out.println(node.getText());
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
			abt = abt.replace("&nbsp;", "").replace("&amp;", "").replace("&rdquo;", "");
			doc.add(new Field("abstract", abt, Store.YES, Index.ANALYZED));
			// System.out.println("摘要信息为：" + abt);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}

	@Test
	public void createIndexes() {
		// String
		// path="F:\\hfz\\现代信息检索\\TopConferences\\SIGIR\\HTML\\SIGIR2002";
		String path = "";
		String path1 = "E:\\hfz\\现代信息检索\\TopConferences\\SIGIR\\HTML\\SIGIR";
		String indexpath = "E:\\hfz\\现代信息检索\\大作业\\程序\\IRProjTest\\sigirIndexes";
		// Document document=new Document();
		File indexDir = new File(indexpath);
		try {
			Analyzer luceneAnalyzer = new StandardAnalyzer();
			// 为了提速，准备用将索引在内存中进行先
			Directory fsDir = FSDirectory.getDirectory(indexpath);
			// 1，启动时读取
			Directory ramDir = new RAMDirectory(fsDir);
			// 运行程序时操作 ramDir
		//	IndexWriter ramIndexWriter = new IndexWriter(ramDir, luceneAnalyzer, MaxFieldLength.LIMITED);
			IndexWriter fsIndexWriter = new IndexWriter(fsDir, luceneAnalyzer, MaxFieldLength.LIMITED);
			// IndexWriter indexWriter = new IndexWriter(indexDir,
			// luceneAnalyzer,
			// true, IndexWriter.MaxFieldLength.UNLIMITED);

			int[] No = new int[33];
			No[0] = 1971;
			No[1] = 1978;
			for (int i = 2; i <= 32; i++)
				No[i] = No[i - 1] + 1;

			Document doc = null;
			long startTime = new Date().getTime();
			for (int j = 0; j < 33; j++) {

				path = path1 + Integer.toString(No[j]);
				File fileDir = new File(path);
				File[] htmlFiles = fileDir.listFiles();
				for (int i = 0; i < htmlFiles.length; i++) {
					// String name=htmlFiles[i].getName();
					String fpath = htmlFiles[i].getAbsolutePath();
					// System.out.println(htmlFiles[i].getAbsolutePath());
					System.out.println("===========第" + (i + 1) + "篇文章信息：=============");
					System.out.println(fpath);
					// getInfo(fpath);
					// tagTest2(fpath);
					doc = new Document();
					doc = getTitleAndAuthor(fpath, doc);
					doc = getAbstract(fpath, doc);
					System.out.println(doc);
					fsIndexWriter.addDocument(doc);

				}
			}

			// optimize()方法是对索引进行优化
		//	ramIndexWriter.optimize();
		//	ramIndexWriter.close();
			// 2，退出时保存
		//	IndexWriter fsIndexWriter = new IndexWriter(indexpath, luceneAnalyzer, true, MaxFieldLength.LIMITED);
			fsIndexWriter.addIndexesNoOptimize(new Directory[] { ramDir });

		//	fsIndexWriter.flush();
			fsIndexWriter.optimize();
			fsIndexWriter.close();
			// 测试一下索引的时间
			long endTime = new Date().getTime();

			System.out.println("这花费了" + (endTime - startTime) + " 毫秒来把文档增加到索引里面去!");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 进行查找
	@Test
	public void searchQuery() {
		// 进行查找
		String indexpath = "E:\\hfz\\现代信息检索\\大作业\\程序\\IRProjTest\\sigirIndexes";
		try {
			Analyzer analyzer = new StandardAnalyzer();
			IndexSearcher searcher = new IndexSearcher(indexpath);
			ScoreDoc[] hits = null;
			String queryString = "Outline of a dynamic self-tuning and adaptive information retrieval system";
			// Term term = new Term("title", "content");
			// Query query = new TermQuery(term);
			Query query = null;

			query=new FuzzyQuery(new Term("title","modle"));
			
			searcher.search(query);
			// QueryParser qp = new QueryParser("body", analyzer);
			// query = qp.parse(queryString);
			//query = new QueryParser("title", analyzer).parse(queryString);

			if (searcher != null) {
				// hits = searcher.search(query, searcher.maxDoc()).scoreDocs;
				hits = searcher.search(query, 1000).scoreDocs;
				// hits = collector.topDocs().scoreDocs; // 拿到结果
				if (hits.length > 0) {
					System.out.println("找到:" + hits.length + " 个结果!");
				} else
					System.out.println("Sorry,no results!");
				Document doc;
				for (int i = 0; i < hits.length; i++) {
					doc = searcher.doc(hits[i].doc);
					System.out.println("==========第" + (i + 1) + "个结果=================");
					System.out.println("标题为：" + doc.get("title"));
					System.out.println("作者为：" + doc.get("author") + "   作者单位为：" + doc.get("workplace"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	@Test
	public void searchQuery2() {
		// 进行查找
		String indexpath = "E:\\hfz\\现代信息检索\\大作业\\程序\\IRProjTest\\sigirIndexes";
		try {
			Analyzer analyzer = new StandardAnalyzer();
			//IndexSearcher searcher = new IndexSearcher(indexpath);
			//要查询的字符串
		//	String queryString = "Outline of a dynamic self-tuning and adaptive information retrieval system";
			String queryString = "Outline  dynamic system ";
			//设置查询字段
			String[] fields = { "title", "author","abstract" };
			//设置各个字段对应的权重，在排序时使用
			Map<String, Float> boosts = new HashMap<String, Float>();
			boosts.put("title", 3f);
			boosts.put("abstract", 2f);
			//boosts.put("content", 1.0f); 默认为1.0f
			
			//设置查询器
			QueryParser queryParser = new MultiFieldQueryParser(fields, analyzer, boosts);
			
			Query query = queryParser.parse(queryString);
			
			IndexSearcher indexSearcher =  new IndexSearcher(indexpath);
			
			// ========== 排序
			//Sort sort = new Sort();
			//sort.setSort(new SortField("size")); // 默认为升序
			//设置topk的大小
			int topk=10;
			TopDocs topDocs = indexSearcher.search(query,null,topk);//, sort);
			
			int recordCount = topDocs.totalHits;
			List<Document> recordList = new ArrayList<Document>();
			
			// ============== 准备高亮器
			Formatter formatter = new SimpleHTMLFormatter("<font color='red'>", "</font>");
			Scorer scorer = new QueryScorer(query);
			Highlighter highlighter = new Highlighter(formatter, scorer);

			Fragmenter fragmenter = new SimpleFragmenter(50);
			highlighter.setTextFragmenter(fragmenter);
			
			// 3，取出当前页的数据
			int firstResult=1;
			int maxResults=8;
			int end = Math.min(firstResult + maxResults, topDocs.totalHits);
			for (int i = firstResult; i < end; i++) {
				ScoreDoc scoreDoc = topDocs.scoreDocs[i];

				int docSn = scoreDoc.doc; // 文档内部编号
				Document doc = indexSearcher.doc(docSn); // 根据编号取出相应的文档

				// =========== 高亮
				// 返回高亮后的结果，如果当前属性值中没有出现关键字，会返回 null
				System.out.println("====="+doc.get("abstract"));
				String hc = highlighter.getBestFragment(analyzer, "abstract", doc.get("abstract"));
				if (hc == null) {
					String content = doc.get("abstract");
					int endIndex = Math.min(100, content.length());
					hc = content.substring(0, endIndex);// 最多前50个字符
				}
				doc.getField("abstract").setValue(hc);
				// ===========

				recordList.add(doc);
			}
			
			QueryResult qr=new QueryResult(recordCount, recordList);
			int cnt=1;
			for (Document doc : qr.getRecordList()) {
				
				//File2DocumentUtils.printDocumentInfo(doc);
				System.out.println("==========第" + (cnt) + "个结果=================");
				System.out.println("标题为：" + doc.get("title"));
				System.out.println("作者为：" + doc.get("author") + "   作者单位为：" + doc.get("workplace"));
				System.out.println("摘要为：" + doc.get("abstract"));
				cnt++;
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String searchQuery(String queryString,String fields) {
		// 进行查找
		String indexpath = "E:\\hfz\\现代信息检索\\大作业\\程序\\IRProjTest\\sigirIndexes";
		String result="";
		try {
			Analyzer analyzer = new StandardAnalyzer();
			//IndexSearcher searcher = new IndexSearcher(indexpath);
			//要查询的字符串
		//	String queryString = "Outline of a dynamic self-tuning and adaptive information retrieval system";
			//String queryString = "Outline  dynamic system ";
			//设置查询字段
			//String[] fields = { "title", "author","abstract" };
			//设置各个字段对应的权重，在排序时使用
			Map<String, Float> boosts = new HashMap<String, Float>();
			boosts.put("title", 3f);
			boosts.put("abstract", 2f);
			//boosts.put("content", 1.0f); 默认为1.0f
			
			//设置查询器
			//QueryParser queryParser = new MultiFieldQueryParser(fields, analyzer, boosts);
			QueryParser queryParser = new QueryParser(fields, analyzer);
			
			Query query = queryParser.parse(queryString);
			
			IndexSearcher indexSearcher =  new IndexSearcher(indexpath);
			
			// ========== 排序
			//Sort sort = new Sort();
			//sort.setSort(new SortField("size")); // 默认为升序
			//设置topk的大小
			int topk=10;
			TopDocs topDocs = indexSearcher.search(query,null,topk);//, sort);
			
			int recordCount = topDocs.totalHits;
			List<Document> recordList = new ArrayList<Document>();
			
			// ============== 准备高亮器
			Formatter formatter = new SimpleHTMLFormatter("<font color='red'>", "</font>");
			Scorer scorer = new QueryScorer(query);
			Highlighter highlighter = new Highlighter(formatter, scorer);

			Fragmenter fragmenter = new SimpleFragmenter(50);
			highlighter.setTextFragmenter(fragmenter);
			
			// 3，取出当前页的数据
			int firstResult=1;
			int maxResults=8;
			int end = Math.min(firstResult + maxResults, topDocs.totalHits);
			for (int i = firstResult; i < end; i++) {
				ScoreDoc scoreDoc = topDocs.scoreDocs[i];

				int docSn = scoreDoc.doc; // 文档内部编号
				Document doc = indexSearcher.doc(docSn); // 根据编号取出相应的文档

				// =========== 高亮
				// 返回高亮后的结果，如果当前属性值中没有出现关键字，会返回 null
				System.out.println("====="+doc.get("abstract"));
				String hc = highlighter.getBestFragment(analyzer, "abstract", doc.get("abstract"));
				if (hc == null) {
					String content = doc.get("abstract");
					int endIndex = Math.min(100, content.length());
					hc = content.substring(0, endIndex);// 最多前50个字符
				}
				doc.getField("abstract").setValue(hc);
				// ===========

				recordList.add(doc);
			}
			
			QueryResult qr=new QueryResult(recordCount, recordList);
			int cnt=1;
			
			for (Document doc : qr.getRecordList()) {
				
				//File2DocumentUtils.printDocumentInfo(doc);
				/*System.out.println("==========第" + (cnt) + "个结果=================");
				System.out.println("标题为：" + doc.get("title"));
				System.out.println("作者为：" + doc.get("author") + "   作者单位为：" + doc.get("workplace"));
				System.out.println("摘要为：" + doc.get("abstract"));*/
				result+="==========第" + (cnt) + "个结果=================\n";
				result+="标题为：" + doc.get("title")+"\n";
				result+="作者为：" + doc.get("author") + "   作者单位为：" + doc.get("workplace")+"\n";
				result+="摘要为：" + doc.get("abstract")+"\n";
				cnt++;
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}
}
