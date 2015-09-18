package irui;

import java.io.IOException;



import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.RangeQuery;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.store.RAMDirectory;
import org.junit.Test;

public class SearchTest{   
	           
	       Analyzer analyzer = new StandardAnalyzer();   
	           
	        RAMDirectory directory = new RAMDirectory();   
	        /**
				 * 8. * �������� 9. * 10. *
				 * 
				 * @throws IOException
				 *             11.
				 */  
@Test
	       public void index() throws IOException{   
	             
	         IndexWriter indexWriter = new IndexWriter(directory,analyzer,true);   
	           
	          Document doc1 = new Document();   
	           
	          doc1.add(new Field("title","aaabbb",Store.YES,Index.TOKENIZED));   
	              
	          doc1.add(new Field("content","If you would like to help promote OpenOffice",Store.YES,Index.TOKENIZED));   
	             
	           doc1.add(new Field("time","2005",Store.YES,Index.TOKENIZED));   
	             
	           indexWriter.addDocument(doc1);   
	            
	         Document doc2 = new Document();   
	              
	          doc2.add(new Field("title","bbcc",Store.YES,Index.TOKENIZED));   
	              
	          doc2.add(new Field("content","sdfsdfsdfasdds",Store.YES,Index.TOKENIZED));   
	            
	            doc2.add(new Field("time","2007",Store.YES,Index.TOKENIZED));   
	             
	          indexWriter.addDocument(doc2);   
	             
	            indexWriter.optimize();   
	            
	         indexWriter.close();   
	      }   
	        
	      // ����������
	      public void termSearcher() throws IOException{   
	              
	          IndexSearcher searcher = new IndexSearcher(directory);   
	             
	          // ��ѯtitle�а���aaa
	          Term term = new Term("title","aaa");   
	            
	          TermQuery query = new TermQuery(term);   
	            
	           searcher.search(query);   
	        
	           searcher.close();   
	       }   
	    
	     // ��������
	     public void phraseSearcher() throws IOException{   
	             
	            IndexSearcher searcher = new IndexSearcher(directory);   
	            
	         PhraseQuery phraseQuery = new PhraseQuery();   
	             
	          // slop �������λ��֮������������,����would��help�м�ֻ����like(to�ᱻȥ��),�����������Ϊ1�����ҵ�ֵ
	          // �������������Ĺؼ�����˵,�����¶���Ϊ����,�������ҵ�
	           // ���ڲ������Ĵ���˵,�������ؼ��������С���¶ȶ������ҵ�,�����Ҳ���
	          phraseQuery.setSlop(1);   
	             
	           phraseQuery.add(new Term("content","would"));   
	             
	           phraseQuery.add(new Term("content","help"));   
	             
	           // �����help��ǰ��,would�ź���,��Ҫ��would����ƶ�3��λ�ò��ܵ�help����(to����),����Ҫ��Ϊslop����Ҫ��Ϊ3
	          // phraseQuery.add(new Term("content","help"));
	              
	          // phraseQuery.add(new Term("content","would"));
	              
	        // phraseQuery.setSlop(3);
	             
	         // ����������Ǹ�����֮�����ԽС,����Խ��,����ԽС
	          Hits hits =   searcher.search(phraseQuery);   
	           printResult(hits);   
	             
	           searcher.close();   
	      }   
	          
	       // ͨ������� WildcardQuery
	       // ͨ���������?��ƥ��һ�������ַ��͡�*��ƥ��������������ַ���������������use*����������ҵ���user�����ߡ�uses����
	      public void wildcardSearcher() throws IOException{   
	            
	                IndexSearcher searcher = new IndexSearcher(directory);   
	                 
	             // ������һ��,*����0��������ĸ,?����0����һ����ĸ
	               // WildcardQuery��QueryParser��ͬ����:WildcardQuery��ǰ׺����Ϊ*,��QueryParser����
	               WildcardQuery query = new WildcardQuery(new Term("content","a?bbb*"));   
	                
	              Hits hits = searcher.search(query);   
	                
	               printResult(hits);   
	                
	               searcher.close();   
	      }   
	     
	    // ģ������ FuzzyQuery
	      @Test 
	     public void fuzzySearcher() throws IOException{   
	                 
	             IndexSearcher search = new IndexSearcher(directory);   
	                 
	             // OpenOffica��Ȼû������,�����ҵ������OpenOffice
	              FuzzyQuery query = new FuzzyQuery(new Term("content","OpenOffica"));   
	                
	              Hits hits = search.search(query);   
	                
	             printResult(hits);   
	              
	               search.close();   
	      }   
	         
	    // ʹ��ǰ׺PrefixQuery
	      public void prefixSearcher() throws IOException{   
	             
	              IndexSearcher search = new IndexSearcher(directory);   
	                 
	            // ȫ��titleǰ׺Ϊa
	             PrefixQuery query = new PrefixQuery(new Term("title","b"));   
	                
	             Hits hits = search.search(query);   
	               
	            printResult(hits);   
	              search.close();   
	                 
	      }   
	   
	     // ��Χ���� RangeQuery
	    public void rangeSearcher() throws IOException{   
	           IndexSearcher search = new IndexSearcher(directory);   
	             
	          // RangeQuery query = new RangeQuery(beginTime, endTime, false);
	        // ��ʼʱ��,����ʱ��,���һ��������ʾ�Ƿ�����߽���������,���Ϊfalse
	         RangeQuery query = new RangeQuery(new Term("time","2005"),new Term("time","2007"),true);   
	            
	          Hits hits = search.search(query);   
	          printResult(hits);   
	             
	         search.close();   
	     }   
	       
	      
	     // �������BooleanQuery
	      // BooleanClause���ڱ�ʾ������ѯ�Ӿ��ϵ���࣬������BooleanClause.Occur.MUST��BooleanClause.Occur.MUST_NOT��BooleanClause.Occur.SHOULD��������6����ϣ�
	     // 1��MUST��MUST��ȡ��������ѯ�Ӿ�Ľ�����
	    
	      // 2��MUST��MUST_NOT����ʾ��ѯ����в��ܰ���MUST_NOT����Ӧ�ò�ѯ�Ӿ�ļ��������
	   // 3��MUST_NOT��MUST_NOT�������壬�����޽����
	   
	    // 4��SHOULD��MUST��SHOULD��MUST_NOT��SHOULD��MUST����ʱ�������壬���ΪMUST�Ӿ�ļ����������MUST_NOT����ʱ������ͬMUST��
	   
	     // 5��SHOULD��SHOULD����ʾ���򡱹�ϵ�����ռ������Ϊ���м����Ӿ�Ĳ�����
	   
	     public void booleanSearcher() throws IOException, ParseException{   
	            
	         IndexSearcher search = new IndexSearcher(directory);   
	           
	          QueryParser qp1 = new QueryParser("title",new StandardAnalyzer());   
	                 
	         Query query1 = qp1.parse("aa*");   
	               
	         QueryParser qp2 = new QueryParser("title",new StandardAnalyzer());   
	                
	         Query query2 = qp2.parse("bb*");   
	               
	          BooleanQuery query = new BooleanQuery();   
	        // ���������title��ǰ˫׺������aa,��bb
	          query.add(query1, BooleanClause.Occur.SHOULD);   
	                
	       // BooleanClause.Occur.MUST ����
	         // BooleanClause.Occur.MUST_NOT ���벻��
	           query.add(query2, BooleanClause.Occur.SHOULD);   
	           
	         Hits hits = search.search(query);   
	               
	         printResult(hits);   
	            
	         search.close();   
	            
	       }   
	      
	     // ��ؼ������� PhrasePrefixQuery
	    /*public void phrasePrefixSearcher() throws IOException{   
	           
	           IndexSearcher search = new IndexSearcher(directory);   
	           
	         PhrasePrefixQuery query = new PhrasePrefixQuery();   
	            
	          // ��������п������ȱ�ƥ��
	         query.add(new Term[]{new Term("content","would"),new Term("content","can")});   
	           
	          // ֻ��һ�����ƥ��
	          query.add(new Term("content","help"));   
	             
	         // If you would like to help promote OpenOffice
	        // can I help you
	          // slop���ӵ�������Ϊ��ѯ�е����ж���
	           query.setSlop(1);   
	             
	        // ƥ���һ��Ϊ would �� can �ڶ���Ϊhelp
	          // solp����Ϊ1
	          // If you would like to help promote OpenOffice ��ȥif to ��,would��help�ľ���=1
	         // can I help you �ľ���Ҳ=1 ���Կ�����������������
	             
	         Hits hits = search.search(query);   
	             
	         printResult(hits);   
	             
	           search.close();   
	      }   */
	         
	      // �ڶ�����ϲ�ѯ MultiFieldQueryParser
	    public void multiFieldSearcher() throws IOException, ParseException{   
	             
	          IndexSearcher search = new IndexSearcher(directory);   
	            
	        // Ĭ������µķ�ʽΪOccur.SHOULD
	         // titile����ƥ��bb,content����ƥ��you
	  // MultiFieldQueryParser.parse(new String[]{"bb","you"},new String[]{"title","content"}, analyzer);
	            
	          // titile����ƥ��bb,content����ƥ��
	          Query query = MultiFieldQueryParser.parse( new String[]{"bb","you"},new String[]{"title","content"},new BooleanClause.Occur[]{Occur.MUST,Occur.MUST_NOT}, analyzer);   
	            
	          // title�б������bb content������bb
	         // Query query = MultiFieldQueryParser.parse( "bb*",new String[]{"title","content"},new
					// BooleanClause.Occur[]{BooleanClause.Occur.MUST,BooleanClause.Occur.MUST_NOT}, analyzer);
	           
	          Hits hits = search.search(query);   
	             
	          printResult(hits);   
	             
	           search.close();   
	       }   
	        
	    public void printResult(Hits hits) throws IOException{   
	          for(int i = 0; i < hits.length(); i++){   
	             Document d = hits.doc(i);   
	              System.out.println(d.get("title"));   
	              System.out.println(d.get("content"));   
	             System.out.println(d.get("time"));   
	           }   
	      } 
}