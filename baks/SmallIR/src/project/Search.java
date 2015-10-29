package project;

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
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;

public class Search {
	
public 	long time=0L;
public 	int recordCount=0;
	public int firstResult=0;
	public int lastResult=0;
	
	
	
	public String searchSingleField(String queryString, String field,int topk) {
		// 进行查找
		String indexpath = "E:\\hfz\\现代信息检索\\大作业\\程序\\IRProjTest\\sigirIndexes";
		String result = "";
		try {
			Analyzer analyzer = new StandardAnalyzer();
			//IndexSearcher searcher = new IndexSearcher(indexpath);
			//要查询的字符串
			//	String queryString = "Outline of a dynamic self-tuning and adaptive information retrieval system";
			//String queryString = "Outline  dynamic system ";
			//设置查询字段
			//String[] fields = { "title", "author","abstract" };
			//设置各个字段对应的权重，在排序时使用
		//	Map<String, Float> boosts = new HashMap<String, Float>();
			//boosts.put("title", 3f);
		//	boosts.put("abstract", 2f);
			//boosts.put("content", 1.0f); 默认为1.0f

			//设置查询器
			//QueryParser queryParser = new MultiFieldQueryParser(fields, analyzer, boosts);
			QueryParser queryParser = new QueryParser(field, analyzer);

			Query query = queryParser.parse(queryString);

			IndexSearcher indexSearcher = new IndexSearcher(indexpath);

			// ========== 排序
			//Sort sort = new Sort();
			//sort.setSort(new SortField("size")); // 默认为升序
			//设置topk的大小
		//	int topk = 100;
		//	ScoreDoc[] hits=indexSearcher.search(query,indexSearcher.maxDoc()).scoreDocs;
		//	docCount=hits.length;
		//	System.out.println(docCount);
			 // score;//hits.scoreDocs;
			long stime=(new Date()).getTime();
			TopDocs topDocs = indexSearcher.search(query, null, topk);//, sort);
			long etime=(new Date()).getTime();
			time=etime-stime;

			 recordCount = topDocs.totalHits;
			List<Document> recordList = new ArrayList<Document>();

			// ============== 准备高亮器
			Formatter formatter = new SimpleHTMLFormatter("<font color='red'>", "</font>");
			Scorer scorer = new QueryScorer(query);
			Highlighter highlighter = new Highlighter(formatter, scorer);
            //需要高亮的字段的长度
			Fragmenter fragmenter = new SimpleFragmenter(200);
			highlighter.setTextFragmenter(fragmenter);

			// 取出当前页的数据
			//int firstResult = 0;
			//int maxResults = 8;
			int end = Math.min(firstResult +topk, topDocs.totalHits);
		//	int end=topDocs.totalHits;
			for (int i = firstResult; i < end; i++) {
				ScoreDoc scoreDoc = topDocs.scoreDocs[i];

				int docSn = scoreDoc.doc; // 文档内部编号
				Document doc = indexSearcher.doc(docSn); // 根据编号取出相应的文档

				// =========== 高亮
				// 返回高亮后的结果，如果当前属性值中没有出现关键字，会返回 null
				System.out.println("=====" + doc.get("abstract"));
				//如果搜索的是标题，那么对标题高亮
				if(field.equalsIgnoreCase("title")||field.equalsIgnoreCase("abstract"))
				{
					String titleHc=highlighter.getBestFragment(analyzer, "title", doc.get("title"));
					
					if(titleHc!=null)
					{
						doc.getField("title").setValue(titleHc);
					}
					String abstHc = highlighter.getBestFragment(analyzer, "abstract", doc.get("abstract"));
					if (abstHc == null) {
						String content = doc.get("abstract");
						int endIndex = Math.min(500, content.length());
						abstHc = content.substring(0, endIndex);// 最多前50个字符
					}
					doc.getField("abstract").setValue(abstHc);
				}
				//搜索的是作者(作者单位），对作者（作者单位）高亮
				else if(field.equalsIgnoreCase("author")||field.equalsIgnoreCase("workplace"))
				{
					String[] authorStr = doc.getValues(field);
					int tmp=authorStr.length;
					String authorHc="";
					doc.removeFields(field);
					for(int j=0;j<tmp;j++)
					{
					authorHc= highlighter.getBestFragment(analyzer, field, authorStr[j]);
				//	System.out.println("作者高亮"+authorHc);
					if(authorHc!=null)
					doc.add(new Field(field,authorHc,Store.YES, Index.ANALYZED));
					else
						doc.add(new Field(field,authorStr[j],Store.YES, Index.ANALYZED));
					}
				}

				recordList.add(doc);
			}

			QueryResult qr = new QueryResult(recordCount, recordList);
			int cnt = 1;

			for (Document doc : qr.getRecordList()) {

				//File2DocumentUtils.printDocumentInfo(doc);
				/*System.out.println("==========第" + (cnt) + "个结果=================");
				 System.out.println("标题为：" + doc.get("title"));
				 System.out.println("作者为：" + doc.get("author") + "   作者单位为：" + doc.get("workplace"));
				 System.out.println("摘要为：" + doc.get("abstract"));*/
				//result += "<p>==========第" + (cnt) + "个结果=================</p>";
				result += "<p><font size=\"4\">"+cnt+".</font>"+"&nbsp;<strong><a href=\"\">" + doc.get("title") + "</a></strong></p>";
				//有可能有多个作者，从中取出多个作者
				String[] authorStr = doc.getValues("author");
				String[] workplaceStr = doc.getValues("workplace");
				int authorNum = authorStr.length;
				for (int i = 0; i < authorNum; i++) {
					if(!authorStr[i].equalsIgnoreCase(""))
					{
					result += "<a href=\"\"><font size=\"3\">" + authorStr[i] +"</font></a>" ;
				
					}
					if(!workplaceStr[i].equalsIgnoreCase(""))
					result+="&nbsp;&nbsp;&nbsp;<a href=\"\"><font size=\"4\">" + workplaceStr[i] + "</font></a><br>";
					else
						result+="<br>";
					//result+="<br>";
				}
				result += "<p><font size=\"3\">" + doc.get("abstract") + "</font></p>";
				cnt++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}
}
