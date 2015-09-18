package luceneTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;


/**
* author lighter date 2006-8-7
*/
public class TextFileIndexer {
public static void main(String[] args) throws Exception {
	String dataS="F:\\hfz\\�ִ���Ϣ����\\����ҵ\\����\\IRProjTest\\datasource";
	String indexS="F:\\hfz\\�ִ���Ϣ����\\����ҵ\\����\\IRProjTest\\indexes";
   /* ָ��Ҫ�����ļ��е�λ��,������C�̵�S�ļ����� */
   File fileDir = new File(dataS);

   /* ����������ļ���λ�� */
   File indexDir = new File(indexS);
   Analyzer luceneAnalyzer = new StandardAnalyzer();
   IndexWriter indexWriter = new IndexWriter(indexDir, luceneAnalyzer,true,IndexWriter.MaxFieldLength.UNLIMITED);
   File[] textFiles = fileDir.listFiles();
   long startTime = new Date().getTime();
  
   //����document������ȥ
   for (int i = 0; i < textFiles.length; i++) {
    if (textFiles[i].isFile()
      && textFiles[i].getName().endsWith(".txt")) {
     System.out.println("File " + textFiles[i].getCanonicalPath()
       + "���ڱ�����....");
     String temp = FileReaderAll(textFiles[i].getCanonicalPath(),
       "utf-8");
     System.out.println(temp);
     Document document = new Document();
     Field FieldPath = new Field("path", textFiles[i].getPath(),
       Field.Store.YES, Field.Index.NO);
     Field FieldBody = new Field("body", temp, Field.Store.YES,
       Field.Index.ANALYZED);
     document.add(FieldPath);
     document.add(FieldBody);
     System.out.println("==========�����ĵ���Ϣ=============");
     System.out.println(document);
     indexWriter.addDocument(document);
    }
   }
   //optimize()�����Ƕ����������Ż�
   indexWriter.optimize();
   indexWriter.close();
  
   //����һ��������ʱ��
   long endTime = new Date().getTime();
   System.out
     .println("�⻨����"
       + (endTime - startTime)
       + " ���������ĵ����ӵ���������ȥ!"
       + fileDir.getPath());
   //���в���
   Analyzer analyzer = new StandardAnalyzer();
   IndexSearcher searcher = new IndexSearcher(indexS);
   ScoreDoc[] hits = null;
   String queryString = "PCA";
   Query query = null;
   try {
    //QueryParser qp = new QueryParser("body", analyzer); 
            //query = qp.parse(queryString);
            query = new QueryParser("body", analyzer).parse(queryString);
            } catch (ParseException e) {
   }
   if (searcher != null) {
    hits = searcher.search(query, searcher.maxDoc()).scoreDocs;
    //hits = collector.topDocs().scoreDocs; // �õ����
    if (hits.length > 0) {
     System.out.println("�ҵ�:" + hits.length + " �����!");
    }
    else
    	System.out.println("Sorry,no results!");
    Document doc;
    for (int i = 0; i < hits.length; i++) {
     doc = searcher.doc(hits[i].doc);
     System.out.println(doc.get("body"));
    }
   }

}

public static String FileReaderAll(String FileName, String charset)
    throws IOException {
   BufferedReader reader = new BufferedReader(new InputStreamReader(
     new FileInputStream(FileName), charset));
   String line = new String();
   String temp = new String();
  
   while ((line = reader.readLine()) != null) {
    temp += line;
   }
   reader.close();
   return temp;
}
}
