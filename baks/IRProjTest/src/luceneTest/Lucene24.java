package luceneTest;

import java.io.File;   
import org.apache.lucene.analysis.Analyzer;   
import org.apache.lucene.analysis.standard.StandardAnalyzer;   
import org.apache.lucene.document.Document;   
import org.apache.lucene.document.Field;   
import org.apache.lucene.index.IndexWriter;   
import org.apache.lucene.queryParser.QueryParser;   
import org.apache.lucene.search.IndexSearcher;   
import org.apache.lucene.search.Query;   
import org.apache.lucene.search.ScoreDoc;   
import org.apache.lucene.search.Searcher;   
import org.apache.lucene.store.Directory;   
import org.apache.lucene.store.FSDirectory;   
import org.apache.lucene.util.Version;
  
  
public class Lucene24 {   
  
    public static void createIndex(Directory dir, Analyzer analyzer) {   
        try {   
    //参数依次：索引目录、分词工具、是否清空目录、字段值的最大长度（UNLImited即Interger.MaxValue）   
            IndexWriter writer = new IndexWriter(dir, analyzer, true,   
                    IndexWriter.MaxFieldLength.UNLIMITED);   
            Document doc = new Document();   
            doc.add(new Field("name", "jack lucy", Field.Store.YES,   
                    Field.Index.ANALYZED));   
            doc.add(new Field("country", "中国 美国", Field.Store.YES,   
                    Field.Index.ANALYZED));   
            writer.addDocument(doc);   
  
            doc = new Document();   
            doc.add(new Field("name", "lucy lily", Field.Store.YES,   
                    Field.Index.ANALYZED));   
            doc.add(new Field("country", "美国 日本", Field.Store.YES,   
                    Field.Index.ANALYZED));   
            writer.addDocument(doc);   
  
            doc = new Document();   
            doc.add(new Field("name", "lily john", Field.Store.YES,   
                    Field.Index.ANALYZED));   
            doc.add(new Field("country", "日本 韩国", Field.Store.YES,   
                    Field.Index.ANALYZED));   
            writer.addDocument(doc);   
  
            doc = new Document();   
            doc.add(new Field("name", "john tom", Field.Store.YES,   
                    Field.Index.ANALYZED));   
            doc.add(new Field("country", "韩国 法国", Field.Store.YES,   
                    Field.Index.ANALYZED));   
            writer.addDocument(doc);   
  
            doc = new Document();   
            doc.add(new Field("name", "tom july", Field.Store.YES,   
                    Field.Index.ANALYZED));   
            doc.add(new Field("country", "法国 德国", Field.Store.YES,   
                    Field.Index.ANALYZED));   
            writer.addDocument(doc);   
  
            doc = new Document();   
            doc.add(new Field("name", "july kate", Field.Store.YES,   
                    Field.Index.ANALYZED));   
            doc.add(new Field("country", "德国 英国", Field.Store.YES,   
                    Field.Index.ANALYZED));   
            writer.addDocument(doc);   
            // writer.optimize();   
            writer.close();   
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
    }   
  
    public static void search(Directory dir, Analyzer analyzer) {   
        try {   
            Searcher searcher = new IndexSearcher(dir);   
            Query query = new QueryParser("country", analyzer).parse("美国");   
  //此处在2.0基础上有改动，此处必须传入一个返回条数，这里用searcher.maxDoc()表示返回所有条数。   
            ScoreDoc[] docs = searcher.search(query, searcher.maxDoc()).scoreDocs;   
            System.out.println(docs.length);;   
            Document doc;   
            for (int i = 0; i < docs.length; i++) {   
                doc = searcher.doc(docs[i].doc);   
                System.out.println(doc.get("country"));   
            }   
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
    }   
  
    public static void main(String[] args) {   
        try {   
            Analyzer analyzer = new StandardAnalyzer();   
            FSDirectory dir = FSDirectory.open(new File("d:\\Lucene_index"));   
            createIndex(dir, analyzer);   
            search(dir, analyzer);   
            dir.close();   
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
    }   
}  
