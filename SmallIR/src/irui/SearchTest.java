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
				 * 8. * 创建索引 9. * 10. *
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
	        
	      // 按词条搜索
	      public void termSearcher() throws IOException{   
	              
	          IndexSearcher searcher = new IndexSearcher(directory);   
	             
	          // 查询title中包含aaa
	          Term term = new Term("title","aaa");   
	            
	          TermQuery query = new TermQuery(term);   
	            
	           searcher.search(query);   
	        
	           searcher.close();   
	       }   
	    
	     // 短语搜索
	     public void phraseSearcher() throws IOException{   
	             
	            IndexSearcher searcher = new IndexSearcher(directory);   
	            
	         PhraseQuery phraseQuery = new PhraseQuery();   
	             
	          // slop 两个项的位置之间允许的最大间隔,这里would和help中间只隔了like(to会被去掉),所以最大间隔设为1就能找到值
	          // 对于两个相连的关键词来说,无论坡度设为多少,都可以找到
	           // 对于不相连的词来说,当两个关键词相隔数小于坡度都可以找到,否则找不到
	          phraseQuery.setSlop(1);   
	             
	           phraseQuery.add(new Term("content","would"));   
	             
	           phraseQuery.add(new Term("content","help"));   
	             
	           // 如果将help放前面,would放后面,需要将would向后移动3个位置才能到help后面(to不算),所以要设为slop最少要设为3
	          // phraseQuery.add(new Term("content","help"));
	              
	          // phraseQuery.add(new Term("content","would"));
	              
	        // phraseQuery.setSlop(3);
	             
	         // 短语的评分是根据项之间距离越小,评分越高,否则越小
	          Hits hits =   searcher.search(phraseQuery);   
	           printResult(hits);   
	             
	           searcher.close();   
	      }   
	          
	       // 通配符搜索 WildcardQuery
	       // 通配符包括’?’匹配一个任意字符和’*’匹配零个或多个任意字符，例如你搜索’use*’，你可能找到’user’或者’uses’：
	      public void wildcardSearcher() throws IOException{   
	            
	                IndexSearcher searcher = new IndexSearcher(directory);   
	                 
	             // 与正则一样,*代表0个或多个字母,?代表0个或一个字母
	               // WildcardQuery与QueryParser不同的是:WildcardQuery的前缀可以为*,而QueryParser不行
	               WildcardQuery query = new WildcardQuery(new Term("content","a?bbb*"));   
	                
	              Hits hits = searcher.search(query);   
	                
	               printResult(hits);   
	                
	               searcher.close();   
	      }   
	     
	    // 模糊搜索 FuzzyQuery
	      @Test 
	     public void fuzzySearcher() throws IOException{   
	                 
	             IndexSearcher search = new IndexSearcher(directory);   
	                 
	             // OpenOffica虽然没被索引,但能找到相近的OpenOffice
	              FuzzyQuery query = new FuzzyQuery(new Term("content","OpenOffica"));   
	                
	              Hits hits = search.search(query);   
	                
	             printResult(hits);   
	              
	               search.close();   
	      }   
	         
	    // 使用前缀PrefixQuery
	      public void prefixSearcher() throws IOException{   
	             
	              IndexSearcher search = new IndexSearcher(directory);   
	                 
	            // 全部title前缀为a
	             PrefixQuery query = new PrefixQuery(new Term("title","b"));   
	                
	             Hits hits = search.search(query);   
	               
	            printResult(hits);   
	              search.close();   
	                 
	      }   
	   
	     // 范围搜索 RangeQuery
	    public void rangeSearcher() throws IOException{   
	           IndexSearcher search = new IndexSearcher(directory);   
	             
	          // RangeQuery query = new RangeQuery(beginTime, endTime, false);
	        // 开始时间,结束时间,最后一个参数表示是否包含边界条件本身,如果为false
	         RangeQuery query = new RangeQuery(new Term("time","2005"),new Term("time","2007"),true);   
	            
	          Hits hits = search.search(query);   
	          printResult(hits);   
	             
	         search.close();   
	     }   
	       
	      
	     // 与或搜索BooleanQuery
	      // BooleanClause用于表示布尔查询子句关系的类，包括：BooleanClause.Occur.MUST，BooleanClause.Occur.MUST_NOT，BooleanClause.Occur.SHOULD。有以下6种组合：
	     // 1．MUST和MUST：取得连个查询子句的交集。
	    
	      // 2．MUST和MUST_NOT：表示查询结果中不能包含MUST_NOT所对应得查询子句的检索结果。
	   // 3．MUST_NOT和MUST_NOT：无意义，检索无结果。
	   
	    // 4．SHOULD与MUST、SHOULD与MUST_NOT：SHOULD与MUST连用时，无意义，结果为MUST子句的检索结果。与MUST_NOT连用时，功能同MUST。
	   
	     // 5．SHOULD与SHOULD：表示“或”关系，最终检索结果为所有检索子句的并集。
	   
	     public void booleanSearcher() throws IOException, ParseException{   
	            
	         IndexSearcher search = new IndexSearcher(directory);   
	           
	          QueryParser qp1 = new QueryParser("title",new StandardAnalyzer());   
	                 
	         Query query1 = qp1.parse("aa*");   
	               
	         QueryParser qp2 = new QueryParser("title",new StandardAnalyzer());   
	                
	         Query query2 = qp2.parse("bb*");   
	               
	          BooleanQuery query = new BooleanQuery();   
	        // 搜索结果的title的前双缀可以是aa,或bb
	          query.add(query1, BooleanClause.Occur.SHOULD);   
	                
	       // BooleanClause.Occur.MUST 必须
	         // BooleanClause.Occur.MUST_NOT 必须不是
	           query.add(query2, BooleanClause.Occur.SHOULD);   
	           
	         Hits hits = search.search(query);   
	               
	         printResult(hits);   
	            
	         search.close();   
	            
	       }   
	      
	     // 多关键的搜索 PhrasePrefixQuery
	    /*public void phrasePrefixSearcher() throws IOException{   
	           
	           IndexSearcher search = new IndexSearcher(directory);   
	           
	         PhrasePrefixQuery query = new PhrasePrefixQuery();   
	            
	          // 这里两项都有可能首先被匹配
	         query.add(new Term[]{new Term("content","would"),new Term("content","can")});   
	           
	          // 只有一项必须匹配
	          query.add(new Term("content","help"));   
	             
	         // If you would like to help promote OpenOffice
	        // can I help you
	          // slop因子的作用域为查询中的所有短语
	           query.setSlop(1);   
	             
	        // 匹配第一项为 would 或 can 第二项为help
	          // solp设置为1
	          // If you would like to help promote OpenOffice 除去if to 外,would与help的距离=1
	         // can I help you 的距离也=1 所以可以搜索出两条数据
	             
	         Hits hits = search.search(query);   
	             
	         printResult(hits);   
	             
	           search.close();   
	      }   */
	         
	      // 在多个域上查询 MultiFieldQueryParser
	    public void multiFieldSearcher() throws IOException, ParseException{   
	             
	          IndexSearcher search = new IndexSearcher(directory);   
	            
	        // 默认情况下的方式为Occur.SHOULD
	         // titile可以匹配bb,content可以匹配you
	  // MultiFieldQueryParser.parse(new String[]{"bb","you"},new String[]{"title","content"}, analyzer);
	            
	          // titile必须匹配bb,content不能匹配
	          Query query = MultiFieldQueryParser.parse( new String[]{"bb","you"},new String[]{"title","content"},new BooleanClause.Occur[]{Occur.MUST,Occur.MUST_NOT}, analyzer);   
	            
	          // title中必须包含bb content不能有bb
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