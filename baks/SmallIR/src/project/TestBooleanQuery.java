package project;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

/**
 * 与或非布尔查询——Lucene中的BooleanQuery
 * @author USER
 *
 */
public class TestBooleanQuery {
   /**
    * 主函数，运行测试程序
    * @param args
    * @throws Exception
    */
 public static void main(String[] args) throws Exception {   
     //建索引
  createIndex();   
      
  //多个TermQuery构建BooleanQuery检索
      searchIndex4TermQuery();
    
     //一个MultiFieldQueryParser构建BooleanQuery多个域的检索
     searchIndex4MultiFieldQueryParser();
   }   
    
 /**
  * 建索引
  * @throws Exception
  */
   public static void createIndex() throws Exception {   
     Document doc1 = new Document();   
     Field field = null;   
     field = new Field("name", "word1 word2 word3", Field.Store.YES,   
         Field.Index.TOKENIZED);   
     doc1.add(field);   
     field = new Field("title", "doc1", Field.Store.YES, Field.Index.TOKENIZED);   
     doc1.add(field);   
   
     Document doc2 = new Document();   
     field = new Field("name", "word4 word5", Field.Store.YES,   
         Field.Index.TOKENIZED);   
     doc2.add(field);   
     field = new Field("title", "doc2", Field.Store.YES, Field.Index.TOKENIZED);   
     doc2.add(field);   
   
     Document doc3 = new Document();   
     field = new Field("name", "word1 word2 word6", Field.Store.YES,   
         Field.Index.TOKENIZED);   
     doc3.add(field);   
     field = new Field("title", "doc3", Field.Store.YES, Field.Index.TOKENIZED);   
     doc3.add(field);   
   
     /**
      * 为测试MultiFieldQueryParser而添加的文档
      */
     Document doc4 = new Document();   
     field = new Field("name", "word1 word2 word3", Field.Store.YES,   
          Field.Index.TOKENIZED);   
     doc4.add(field);   
     field = new Field("title", "doc1 word1", Field.Store.YES, Field.Index.TOKENIZED);   
     doc4.add(field);
    
     /**
      * 对MultiFieldQueryParser更深理解
      */
     Document doc5 = new Document();   
     field = new Field("title", "北京2008年奥运会", Field.Store.YES,   
          Field.Index.TOKENIZED);   
     doc5.add(field);   
     field = new Field("name", "这是一届创造奇迹、超越梦想的…….", Field.Store.YES, Field.Index.TOKENIZED);   
     doc5.add(field);
    
     Document doc6 = new Document();   
     field = new Field("title", "北京2008年奥运会", Field.Store.YES,   
          Field.Index.TOKENIZED);   
     doc6.add(field);   
     field = new Field("name", "这是一届创造奇迹、超越梦想的奥运会…….", Field.Store.YES, Field.Index.TOKENIZED);   
     doc6.add(field);
    
     IndexWriter writer = new IndexWriter("e:\\java\\index",   
         new StandardAnalyzer(), true);   
     writer.addDocument(doc1);   
     writer.addDocument(doc2);   
     writer.addDocument(doc3);
    
     writer.addDocument(doc4);
    
     writer.addDocument(doc5);
     writer.addDocument(doc6);
    
     writer.close();   
   } 
  
   /**
    * 由TermQuery和BooleanQuery构建的多个域检索
    * @throws Exception
    */
   public static void searchIndex4TermQuery() throws Exception{
    TermQuery query1 = null;   
      TermQuery query2 = null;   
      TermQuery query3 = null;   
      TermQuery query4 = null;   
      TermQuery query5 = null; 
      TermQuery query6 = null; 
      BooleanQuery bquerymain = null;   
      BooleanQuery bquery1 = null;   
      BooleanQuery bquery2 = null;   
      BooleanQuery bquery3 = null;   
      Hits hits = null;   
    
      IndexSearcher searcher = new IndexSearcher("e:\\java\\index");   
    
      query1 = new TermQuery(new Term("name", "word1"));   
      query2 = new TermQuery(new Term("name", "word2"));   
      
      query3 = new TermQuery(new Term("name", "word3"));
     
      query4 = new TermQuery(new Term("name", "word4"));   
      query5 = new TermQuery(new Term("name", "word5"));   
         
      query6 = new TermQuery(new Term("name", "word6"));   
         
         
    
      // 构造布尔查询（可根据你的要求随意组合）   
      bquerymain = new BooleanQuery();   
      bquery1 = new BooleanQuery();   
      bquery2 = new BooleanQuery();   
      bquery3 = new BooleanQuery();   
    
      bquery1.add(query1, BooleanClause.Occur.MUST);   
      bquery1.add(query3, BooleanClause.Occur.MUST);   
         
      bquery2.add(query3, BooleanClause.Occur.MUST);   
      bquery2.add(query4, BooleanClause.Occur.MUST);   
         
      bquery3.add(query5, BooleanClause.Occur.MUST);
      bquery3.add(query6, BooleanClause.Occur.MUST_NOT);
         
      bquerymain.add(bquery1, BooleanClause.Occur.SHOULD);   
      bquerymain.add(bquery2, BooleanClause.Occur.SHOULD);   
      bquerymain.add(bquery3, BooleanClause.Occur.MUST); 
     
      /**
       * 根据你的要求建一个BooleanQuery对象，然后来查询
       */
      hits = searcher.search(bquery3);   
      printResult(hits, bquery1.toString());   
    
   }
   
   /**
    * 由MultiFieldQueryParser和BooleanQuery构建的多个域检索
    * @throws Exception
    */
   public static void searchIndex4MultiFieldQueryParser() throws Exception{
      Hits hits = null;   
    
      IndexSearcher searcher = new IndexSearcher("e:\\java\\index");   
    
      // 构造布尔查询（可根据你的要求随意组合） 
      BooleanClause.Occur[] flags = new BooleanClause.Occur[] {
     BooleanClause.Occur.MUST, BooleanClause.Occur.MUST};
    
      Query query = MultiFieldQueryParser.parse("word1", new String[] {
     "name", "title"}, flags, new StandardAnalyzer());
    
    /*  //加深对MultiFieldQueryParser的理解（注意看建索引的文档doc5,doc6与检索后的结果）
      Query query = MultiFieldQueryParser.parse("北京 奥运会", new String[] {
     "name", "title"}, flags, new StandardAnalyzer());    */
   
      hits = searcher.search(query);   
      printResult(hits, query.toString());   
    
   }
  
   /**
    * 打印输出检索出的文档，并输出检索的布尔语句
    * @param hits
    * @param key
    * @throws Exception
    */
   public static void printResult(Hits hits, String key) throws Exception {   
     System.out.println("查询 " + key);   
     if (hits != null) {   
       if (hits.length() == 0) {   
         System.out.println("没有找到任何结果");   
       } else {   
         System.out.println("找到" + hits.length() + "个结果");   
         for (int i = 0; i < hits.length(); i++) {   
           Document d = hits.doc(i);   
           String dname = d.get("title");   
           System.out.print(dname + "   ");   
         }   
         System.out.println();   
         System.out.println();   
       }   
     }   
   }   
}