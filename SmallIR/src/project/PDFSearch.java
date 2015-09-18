package project;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;

public class PDFSearch {
	
	public 	long time=0L;
	public 	int recordCount=0;
		public int firstResult=0;
		public int lastResult=0;
		String indexpath="E:\\hfz\\�ִ���Ϣ����\\����ҵ\\����2\\SmallIR\\pdfIndexes";
		Analyzer analyzer = new StandardAnalyzer();
	// Directory ramDir = new RAMDirectory(); 
		private  int highlightLength=1000;//����ʱ�ĳ���
		private int minLength=500;//ѡȡժҪʱ����̳���
		//		
		//��ȡ�������
		public TopDocs getTopDocs(String queryString, String field,boolean Fuzzy)
		{
           //	 ���в���
			TopDocs topDocs=null;
			IndexSearcher indexSearcher=null;
			try {
				//���ò�ѯ��
				//QueryParser queryParser = new MultiFieldQueryParser(fields, analyzer, boosts);
				QueryParser queryParser = new QueryParser(field, analyzer);

				Query query=null;
				if(Fuzzy)
					query=new FuzzyQuery(new Term(field,queryString));
				else
					query = queryParser.parse(queryString);

				indexSearcher = new IndexSearcher(indexpath);

				long stime=(new Date()).getTime();
				 topDocs = indexSearcher.search(query,indexSearcher.maxDoc());//, sort);
				long etime=(new Date()).getTime();
				time=etime-stime;

				 recordCount = topDocs.totalHits;
				 indexSearcher.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return topDocs;
		}
		
		//��ȡ����������ĵ���doc���б�
		public List<Document> getHighlightResult(String queryString, String field,TopDocs topDocs)
		{
	
			List<Document> recordList = new ArrayList<Document>();
			IndexSearcher indexSearcher=null;
			
			try
			{
				QueryParser queryParser = new QueryParser(field, analyzer);
				Query query=null;
					query= queryParser.parse(queryString);
				
				indexSearcher = new IndexSearcher(indexpath);
				
//				 ============== ׼��������
				Formatter formatter = new SimpleHTMLFormatter("<font color='red'><strong>", "</strong></font>");
				Scorer scorer = new QueryScorer(query);
				Highlighter highlighter = new Highlighter(formatter, scorer);
	            //��Ҫ�������ֶεĳ���
				Fragmenter fragmenter = new SimpleFragmenter(highlightLength);
				highlighter.setTextFragmenter(fragmenter);
//				 ȡ����ǰҳ������
				//int firstResult = 0;
				//int maxResults = 8;
				int end = Math.min(lastResult, topDocs.totalHits);
			//	int end=topDocs.totalHits;
				for (int i = firstResult; i < end; i++) {
					ScoreDoc scoreDoc = topDocs.scoreDocs[i];

					int docSn = scoreDoc.doc; // �ĵ��ڲ����
					Document doc = indexSearcher.doc(docSn); // ���ݱ��ȡ����Ӧ���ĵ�

					// =========== ����
					// ���ظ�����Ľ���������ǰ����ֵ��û�г��ֹؼ��֣��᷵�� null
				//	System.out.println("=====" + doc.get("abstract"));
					//����������Ǳ��⣬��ô�Ա������
					
					String hcStr="";
				//	if(field.equalsIgnoreCase("title")||field.equalsIgnoreCase("abstract")||field.equalsIgnoreCase("year"))
					//{
						//String titleHc=highlighter.getBestFragment(analyzer, "title", doc.get("title"));
						 hcStr = highlighter.getBestFragment(analyzer, field, doc.get(field));
						 if(hcStr!=null)
							{
								doc.getField(field).setValue(hcStr);
								
							}
							if(hcStr==null&&(field.equalsIgnoreCase("abstract")||field.equalsIgnoreCase("content")))
							{
								String content = doc.get(field);
								int endIndex = Math.min(minLength, content.length());
								//
								doc.getField(field).setValue(content.substring(0, endIndex));
							}
				//	}
//					//������������(���ߵ�λ���������ߣ����ߵ�λ������
//					else if(field.equalsIgnoreCase("author")||field.equalsIgnoreCase("workplace"))
//					{
//						String[] fieldValue = doc.getValues(field);
//						//int tmp=fieldValue.length;
//						//String authorHc="";
//						doc.removeFields(field);
//						for(int j=0;j<fieldValue.length;j++)
//						{
//						hcStr= highlighter.getBestFragment(analyzer, field, fieldValue[j]);
//					//	System.out.println("���߸���"+authorHc);
//						if(hcStr!=null)
//						doc.add(new Field(field,hcStr,Store.YES, Index.ANALYZED));
//						else
//							doc.add(new Field(field,fieldValue[j],Store.YES, Index.ANALYZED));
////						ѡȡ���ʴ�С��ժҪ
//						String content = doc.get("abstract");
//						int endIndex = Math.min(minLength, content.length());
//						//
//						doc.getField("abstract").setValue(content.substring(0, endIndex));
//						}
//					}

					recordList.add(doc);
				}
				indexSearcher.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}

			return recordList;
			
		}
		
		//��ȡת��Ϊhtml��Ľ��
		public String getHtmlResult(List<Document>recordList)
		{
			String result="";
			
			int cnt = 1;

			for (Document doc : recordList) {

				//��һ����ʾ����
				result += "<p><font size=\"4\">"+cnt+".</font>"+"&nbsp;&nbsp;&nbsp;&nbsp;"+
				"<strong><a href=\"\">"+ doc.get("title") + "</a></strong></p>";
				result += "<p><font size=\"2\" color=\"green\">" + doc.get("abstract") + "</font></p>";
				result += "<p><font size=\"3\" >" + doc.get("content") + "</font></p>";
				cnt++;
			}
			
			return result;
		}

		//������������
		public TopDocs getBooleanTopDocs(String[]queryString,String[]field)
		{
//			 ���в���
			TopDocs topDocs=null;
			IndexSearcher indexSearcher=null;
			try {
				//���ò�ѯ��
				indexSearcher = new IndexSearcher(indexpath);
				QueryParser queryParser=null;//= new QueryParser(field, analyzer);
				BooleanQuery boolQuery=new BooleanQuery();
				Query query=null;
			//		query = queryParser.parse(queryString);
				//����������ѯ
				for(int i=0;i<queryString.length;i++)
				{
					if(!queryString[i].equalsIgnoreCase(""))
					{
						queryParser=new QueryParser(field[i], analyzer);
						query = queryParser.parse(queryString[i]);
						boolQuery.add(query, Occur.MUST);
					}
							
				}

				

				long stime=(new Date()).getTime();
				 topDocs = indexSearcher.search(boolQuery,indexSearcher.maxDoc());//, sort);
				long etime=(new Date()).getTime();
				time=etime-stime;

				 recordCount = topDocs.totalHits;
				 indexSearcher.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}

			return topDocs;
		}
		

//		��ȡ����������ĵ���doc���б�
		public List<Document> getBooleanHighlightResult(String[] queryString, String[] field,TopDocs topDocs)
		{
	
			List<Document> recordList = new ArrayList<Document>();
			IndexSearcher indexSearcher=null;
			
			try
			{
				indexSearcher = new IndexSearcher(indexpath);
				QueryParser queryParser=null;//= new QueryParser(field, analyzer);
				BooleanQuery boolQuery=new BooleanQuery();
				Query query=null;
			//		query = queryParser.parse(queryString);
				//����������ѯ
				for(int i=0;i<queryString.length;i++)
				{
					if(!queryString[i].equalsIgnoreCase(""))
					{
						queryParser=new QueryParser(field[i], analyzer);
						query = queryParser.parse(queryString[i]);
						boolQuery.add(query, Occur.MUST);
					}
							
				}
			//	QueryParser queryParser = new QueryParser(field, analyzer);
//				 ============== ׼��������
				Formatter formatter = new SimpleHTMLFormatter("<font color='red'><strong>", "</strong></font>");
				Scorer scorer = new QueryScorer(boolQuery);
				Highlighter highlighter = new Highlighter(formatter, scorer);
	            //��Ҫ�������ֶεĳ���
				Fragmenter fragmenter = new SimpleFragmenter(highlightLength);
				highlighter.setTextFragmenter(fragmenter);
//				 ȡ����ǰҳ������
				//int firstResult = 0;
				//int maxResults = 8;
				int end = Math.min(lastResult, topDocs.totalHits);
			//	int end=topDocs.totalHits;
				for (int i = firstResult; i < end; i++) {
					ScoreDoc scoreDoc = topDocs.scoreDocs[i];

					int docSn = scoreDoc.doc; // �ĵ��ڲ����
					Document doc = indexSearcher.doc(docSn); // ���ݱ��ȡ����Ӧ���ĵ�

					// =========== ����
					// ���ظ�����Ľ���������ǰ����ֵ��û�г��ֹؼ��֣��᷵�� null
				//	System.out.println("=====" + doc.get("abstract"));
					//����������Ǳ��⣬��ô�Ա������
					String hcStr="";
					for(int j=0;j<queryString.length;j++)
					{
						if(!queryString[j].equalsIgnoreCase(""))
						{
							
							//if(field[j].equalsIgnoreCase("title")||field[j].equalsIgnoreCase("abstract")||field[j].equalsIgnoreCase("year"))
							//{
								//String titleHc=highlighter.getBestFragment(analyzer, "title", doc.get("title"));
								 hcStr = highlighter.getBestFragment(analyzer, field[j], doc.get(field[j]));
								 if(hcStr!=null)
									{
										doc.getField(field[j]).setValue(hcStr);
										
									}
									if(hcStr==null&&(field[j].equalsIgnoreCase("abstract")||field[j].equalsIgnoreCase("content")))
									{
										String content = doc.get(field[j]);
										int endIndex = Math.min(minLength, content.length());
										//
										doc.getField(field[j]).setValue(content.substring(0, endIndex));
									}
						//	}
//							//������������(���ߵ�λ���������ߣ����ߵ�λ������
//							else if(field[j].equalsIgnoreCase("author")||field[j].equalsIgnoreCase("workplace"))
//							{
//								String[] fieldValue = doc.getValues(field[j]);
//								//int tmp=authorStr.length;
//								//String authorHc="";
//								doc.removeFields(field[j]);
//								for(int k=0;k<fieldValue.length;k++)
//								{
//								hcStr= highlighter.getBestFragment(analyzer, field[j], fieldValue[k]);
//							//	System.out.println("���߸���"+authorHc);
//								if(hcStr!=null)
//									doc.add(new Field(field[j],hcStr,Store.YES, Index.ANALYZED));
//								else
//									doc.add(new Field(field[j],fieldValue[k],Store.YES, Index.ANALYZED));
////								ѡȡ���ʴ�С��ժҪ
//								String content = doc.get("abstract");
//								int endIndex = Math.min(minLength, content.length());
//								//
//								doc.getField("abstract").setValue(content.substring(0, endIndex));
//								}
//							}

						}
					}
					
					
					recordList.add(doc);
				}
				indexSearcher.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}

			return recordList;
			
		}
		
//
		public TopDocs getMultiFieldTopDocs(String queryString,String[]field,float[]weight)
		{
			TopDocs topDocs=null;
			IndexSearcher indexSearcher=null;
			try {
				indexSearcher = new IndexSearcher(indexpath);
				
				Map<String, Float> boosts = new HashMap<String, Float>();
				for(int i=0;i<field.length;i++)
					boosts.put(field[i], weight[i]);
				//���ò�ѯ��
				QueryParser queryParser = new MultiFieldQueryParser(field, analyzer, boosts);
				//QueryParser queryParser = new QueryParser(field, analyzer);

				Query query=null;
				query = queryParser.parse(queryString);

				long stime=(new Date()).getTime();
				 topDocs = indexSearcher.search(query,indexSearcher.maxDoc());//, sort);
				long etime=(new Date()).getTime();
				time=etime-stime;

				recordCount = topDocs.totalHits;
				indexSearcher.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}

			return topDocs;
		}
		
		
//		��ȡ����������ĵ���doc���б�
		public List<Document> getMultiFieldHighlightResult(String queryString, String[] field,float[] weight,TopDocs topDocs)
		{
	
			List<Document> recordList = new ArrayList<Document>();
			IndexSearcher indexSearcher=null;
			
			try
			{
				indexSearcher = new IndexSearcher(indexpath);
				//QueryParser queryParser=null;//= new QueryParser(field, analyzer);
				//BooleanQuery boolQuery=new BooleanQuery();
				Map<String, Float> boosts = new HashMap<String, Float>();
				for(int i=0;i<field.length;i++)
					boosts.put(field[i], weight[i]);
				//���ò�ѯ��
				QueryParser queryParser = new MultiFieldQueryParser(field, analyzer, boosts);
				
				Query query=null;
				query = queryParser.parse(queryString);

//				 ============== ׼��������
				Formatter formatter = new SimpleHTMLFormatter("<font color='red'><strong>", "</strong></font>");
				Scorer scorer = new QueryScorer(query);
				Highlighter highlighter = new Highlighter(formatter, scorer);
	            //��Ҫ�������ֶεĳ���
				Fragmenter fragmenter = new SimpleFragmenter(highlightLength);
				highlighter.setTextFragmenter(fragmenter);
//				 ȡ����ǰҳ������
				//int firstResult = 0;
				//int maxResults = 8;
				int end = Math.min(lastResult, topDocs.totalHits);
			//	int end=topDocs.totalHits;
				for (int i = firstResult; i < end; i++) {
					ScoreDoc scoreDoc = topDocs.scoreDocs[i];

					int docSn = scoreDoc.doc; // �ĵ��ڲ����
					Document doc = indexSearcher.doc(docSn); // ���ݱ��ȡ����Ӧ���ĵ�

					// =========== ����
					// ���ظ�����Ľ���������ǰ����ֵ��û�г��ֹؼ��֣��᷵�� null
				//	System.out.println("=====" + doc.get("abstract"));
					//����������Ǳ��⣬��ô�Ա������
					String hcStr="";
					if(!queryString.equalsIgnoreCase(""))
					{
					    for(int j=0;j<field.length;j++)
					    {
						
							//if(field[j].equalsIgnoreCase("title")||field[j].equalsIgnoreCase("abstract")||field[j].equalsIgnoreCase("year"))
							//{
								//String titleHc=highlighter.getBestFragment(analyzer, "title", doc.get("title"));
								 hcStr = highlighter.getBestFragment(analyzer, field[j], doc.get(field[j]));
								 if(hcStr!=null)
									{
										doc.getField(field[j]).setValue(hcStr);
										
									}
									if(hcStr==null&&(field[j].equalsIgnoreCase("abstract")||field[j].equalsIgnoreCase("content")))
									{
										String content = doc.get(field[j]);
										int endIndex = Math.min(minLength, content.length());
										//
										doc.getField(field[j]).setValue(content.substring(0, endIndex));
									}
						//	}
//							//������������(���ߵ�λ���������ߣ����ߵ�λ������
//							else if(field[j].equalsIgnoreCase("author")||field[j].equalsIgnoreCase("workplace"))
//							{
//								String[] fieldValue = doc.getValues(field[j]);
//								//int tmp=authorStr.length;
//								//String authorHc="";
//								doc.removeFields(field[j]);
//								for(int k=0;k<fieldValue.length;k++)
//								{
//								hcStr= highlighter.getBestFragment(analyzer, field[j], fieldValue[k]);
//							//	System.out.println("���߸���"+authorHc);
//								if(hcStr!=null)
//									doc.add(new Field(field[j],hcStr,Store.YES, Index.ANALYZED));
//								else
//									doc.add(new Field(field[j],fieldValue[k],Store.YES, Index.ANALYZED));
////								ѡȡ���ʴ�С��ժҪ
//								String content = doc.get("abstract");
//								int endIndex = Math.min(minLength, content.length());
//								//
//								doc.getField("abstract").setValue(content.substring(0, endIndex));
//								}
//							}

						}
					}
					
					
					recordList.add(doc);
				}
				indexSearcher.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}

			return recordList;
			
		}
}
