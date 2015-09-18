package irui;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.Field.TermVector;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermFreqVector;
import org.apache.lucene.index.TermPositionVector;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.TokenSources;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.junit.Test;

public class TermVectorTest {
	
	Analyzer analyzer = new SimpleAnalyzer();
	Directory ramDir = new RAMDirectory();
	
	public void createRamIndex() throws CorruptIndexException, LockObtainFailedException, IOException{
		
		IndexWriter writer = new IndexWriter(ramDir,analyzer,IndexWriter.MaxFieldLength.LIMITED);
		
		Document doc1 = new Document();
		doc1.add(new Field("title","java",Store.YES,Index.ANALYZED));
		doc1.add(new Field("author","callan",Store.YES,Index.ANALYZED));
		doc1.add(new Field("subject","java一门编程语言,用java的人很多，编程语言也不少，但是java最流行",Store.YES,Index.ANALYZED,TermVector.WITH_POSITIONS_OFFSETS));
		
		Document doc2 = new Document();
		doc2.add(new Field("title","english",Store.YES,Index.ANALYZED));
		doc2.add(new Field("author","wcq",Store.YES,Index.ANALYZED));
		doc2.add(new Field("subject","英语用的人很多",Store.YES,Index.ANALYZED,TermVector.WITH_POSITIONS_OFFSETS));
	
		Document doc3 = new Document();
		doc3.add(new Field("title","asp",Store.YES,Index.ANALYZED));
		doc3.add(new Field("author","ca",Store.YES,Index.ANALYZED));
		doc3.add(new Field("subject","英语用的人很多",Store.YES,Index.ANALYZED,TermVector.WITH_POSITIONS_OFFSETS));
		
		writer.addDocument(doc1);
		writer.addDocument(doc2);
		writer.addDocument(doc3);
		
		writer.optimize();
		writer.close();
	}
	
	public void search() throws CorruptIndexException, IOException{
		IndexReader reader = IndexReader.open(ramDir);
		IndexSearcher searcher = new IndexSearcher(reader);
		Term term = new Term("title","java");	//在title里查询java词条
		TermQuery query = new TermQuery(term);
		Hits hits = searcher.search(query);
		for (int i = 0; i < hits.length(); i++)
		{
			Document doc = hits.doc(i);
			System.out.println(doc.get("title"));
			System.out.println(doc.get("subject"));
			System.out.println("moreLike search: ");
			
			morelikeSearch(reader,hits.id(i));
		}
	}

	private void morelikeSearch(IndexReader reader,int id) throws IOException
	{
		//根据这个document的id获取这个field的Term Vector 信息，就是这个field分词之后在这个field里的频率、位置、等信息
		TermFreqVector vector = reader.getTermFreqVector(id, "subject");
		
		BooleanQuery query = new BooleanQuery();  
		
		for (int i = 0; i < vector.size(); i++)
		{
			 TermQuery tq = new TermQuery(new Term("subject",   
		                vector.getTerms()[i]));   //获取每个term保存的Token
		           
		         query.add(tq, BooleanClause.Occur.SHOULD);   

		}
		
		IndexSearcher searcher = new IndexSearcher(ramDir);   
	       
	    Hits hits = searcher.search(query);   
	    
	    //显示代码，略

		
	}

//Lucene使用TermVector提高高亮显示性能
	@Test
	public void highterLightSearch() throws CorruptIndexException, IOException{
		String indexpath = "E:\\hfz\\现代信息检索\\大作业\\程序2\\SmallIR\\sigirIndexes";
		IndexReader reader = IndexReader.open(indexpath);   
        
        IndexSearcher searcher = new IndexSearcher(reader);   
           
        TermQuery query = new TermQuery(new Term("title","model"));   
           
        Hits hits = searcher.search(query);   
           
        //高亮显示设置   
        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<font color='red'>","</font>");
           
        Highlighter highlighter =new Highlighter(simpleHTMLFormatter,new QueryScorer(query));   
        
         // 这个100是指定关键字字符串的context的长度，你可以自己设定，因为不可能返回整篇正文内容   
        highlighter.setTextFragmenter(new SimpleFragmenter(100));   
  
        for(int i = 0; i < hits.length(); i++){   
               
            Document doc = hits.doc(i);   
            System.out.println(hits.id(i));
               System.out.println(reader==null);
            TermPositionVector termFreqVector = (TermPositionVector)reader.getTermFreqVector(hits.id(i), "title");   
             
            TermFreqVector vector = reader.getTermFreqVector(hits.id(i), "title");
            TokenStream tokenStream = TokenSources.getTokenStream(termFreqVector);   
               
            String result = null;
			try {
				result = highlighter.getBestFragment(tokenStream, doc.get("title"));
			} catch (InvalidTokenOffsetsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}   
  
            System.out.println(doc.get("title"));   
               
            System.out.println(result);   
           // morelikeSearch(reader, id);
               
        }   

		
	}
	
	public static void main(String[] args) throws CorruptIndexException, IOException
	{
		TermVectorTest  t = new TermVectorTest();
		t.createRamIndex();
		t.search();
		t.highterLightSearch();
	}

}