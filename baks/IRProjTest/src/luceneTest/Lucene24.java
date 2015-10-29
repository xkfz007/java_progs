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
    //�������Σ�����Ŀ¼���ִʹ��ߡ��Ƿ����Ŀ¼���ֶ�ֵ����󳤶ȣ�UNLImited��Interger.MaxValue��   
            IndexWriter writer = new IndexWriter(dir, analyzer, true,   
                    IndexWriter.MaxFieldLength.UNLIMITED);   
            Document doc = new Document();   
            doc.add(new Field("name", "jack lucy", Field.Store.YES,   
                    Field.Index.ANALYZED));   
            doc.add(new Field("country", "�й� ����", Field.Store.YES,   
                    Field.Index.ANALYZED));   
            writer.addDocument(doc);   
  
            doc = new Document();   
            doc.add(new Field("name", "lucy lily", Field.Store.YES,   
                    Field.Index.ANALYZED));   
            doc.add(new Field("country", "���� �ձ�", Field.Store.YES,   
                    Field.Index.ANALYZED));   
            writer.addDocument(doc);   
  
            doc = new Document();   
            doc.add(new Field("name", "lily john", Field.Store.YES,   
                    Field.Index.ANALYZED));   
            doc.add(new Field("country", "�ձ� ����", Field.Store.YES,   
                    Field.Index.ANALYZED));   
            writer.addDocument(doc);   
  
            doc = new Document();   
            doc.add(new Field("name", "john tom", Field.Store.YES,   
                    Field.Index.ANALYZED));   
            doc.add(new Field("country", "���� ����", Field.Store.YES,   
                    Field.Index.ANALYZED));   
            writer.addDocument(doc);   
  
            doc = new Document();   
            doc.add(new Field("name", "tom july", Field.Store.YES,   
                    Field.Index.ANALYZED));   
            doc.add(new Field("country", "���� �¹�", Field.Store.YES,   
                    Field.Index.ANALYZED));   
            writer.addDocument(doc);   
  
            doc = new Document();   
            doc.add(new Field("name", "july kate", Field.Store.YES,   
                    Field.Index.ANALYZED));   
            doc.add(new Field("country", "�¹� Ӣ��", Field.Store.YES,   
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
            Query query = new QueryParser("country", analyzer).parse("����");   
  //�˴���2.0�������иĶ����˴����봫��һ������������������searcher.maxDoc()��ʾ��������������   
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
