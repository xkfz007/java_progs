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
		// ���в���
		String indexpath = "E:\\hfz\\�ִ���Ϣ����\\����ҵ\\����\\IRProjTest\\sigirIndexes";
		String result = "";
		try {
			Analyzer analyzer = new StandardAnalyzer();
			//IndexSearcher searcher = new IndexSearcher(indexpath);
			//Ҫ��ѯ���ַ���
			//	String queryString = "Outline of a dynamic self-tuning and adaptive information retrieval system";
			//String queryString = "Outline  dynamic system ";
			//���ò�ѯ�ֶ�
			//String[] fields = { "title", "author","abstract" };
			//���ø����ֶζ�Ӧ��Ȩ�أ�������ʱʹ��
		//	Map<String, Float> boosts = new HashMap<String, Float>();
			//boosts.put("title", 3f);
		//	boosts.put("abstract", 2f);
			//boosts.put("content", 1.0f); Ĭ��Ϊ1.0f

			//���ò�ѯ��
			//QueryParser queryParser = new MultiFieldQueryParser(fields, analyzer, boosts);
			QueryParser queryParser = new QueryParser(field, analyzer);

			Query query = queryParser.parse(queryString);

			IndexSearcher indexSearcher = new IndexSearcher(indexpath);

			// ========== ����
			//Sort sort = new Sort();
			//sort.setSort(new SortField("size")); // Ĭ��Ϊ����
			//����topk�Ĵ�С
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

			// ============== ׼��������
			Formatter formatter = new SimpleHTMLFormatter("<font color='red'>", "</font>");
			Scorer scorer = new QueryScorer(query);
			Highlighter highlighter = new Highlighter(formatter, scorer);
            //��Ҫ�������ֶεĳ���
			Fragmenter fragmenter = new SimpleFragmenter(200);
			highlighter.setTextFragmenter(fragmenter);

			// ȡ����ǰҳ������
			//int firstResult = 0;
			//int maxResults = 8;
			int end = Math.min(firstResult +topk, topDocs.totalHits);
		//	int end=topDocs.totalHits;
			for (int i = firstResult; i < end; i++) {
				ScoreDoc scoreDoc = topDocs.scoreDocs[i];

				int docSn = scoreDoc.doc; // �ĵ��ڲ����
				Document doc = indexSearcher.doc(docSn); // ���ݱ��ȡ����Ӧ���ĵ�

				// =========== ����
				// ���ظ�����Ľ���������ǰ����ֵ��û�г��ֹؼ��֣��᷵�� null
				System.out.println("=====" + doc.get("abstract"));
				//����������Ǳ��⣬��ô�Ա������
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
						abstHc = content.substring(0, endIndex);// ���ǰ50���ַ�
					}
					doc.getField("abstract").setValue(abstHc);
				}
				//������������(���ߵ�λ���������ߣ����ߵ�λ������
				else if(field.equalsIgnoreCase("author")||field.equalsIgnoreCase("workplace"))
				{
					String[] authorStr = doc.getValues(field);
					int tmp=authorStr.length;
					String authorHc="";
					doc.removeFields(field);
					for(int j=0;j<tmp;j++)
					{
					authorHc= highlighter.getBestFragment(analyzer, field, authorStr[j]);
				//	System.out.println("���߸���"+authorHc);
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
				/*System.out.println("==========��" + (cnt) + "�����=================");
				 System.out.println("����Ϊ��" + doc.get("title"));
				 System.out.println("����Ϊ��" + doc.get("author") + "   ���ߵ�λΪ��" + doc.get("workplace"));
				 System.out.println("ժҪΪ��" + doc.get("abstract"));*/
				//result += "<p>==========��" + (cnt) + "�����=================</p>";
				result += "<p><font size=\"4\">"+cnt+".</font>"+"&nbsp;<strong><a href=\"\">" + doc.get("title") + "</a></strong></p>";
				//�п����ж�����ߣ�����ȡ���������
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
