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
	String dataS="F:\\hfz\\现代信息检索\\大作业\\程序\\IRProjTest\\datasource";
	String indexS="F:\\hfz\\现代信息检索\\大作业\\程序\\IRProjTest\\indexes";
   /* 指明要索引文件夹的位置,这里是C盘的S文件夹下 */
   File fileDir = new File(dataS);

   /* 这里放索引文件的位置 */
   File indexDir = new File(indexS);
   Analyzer luceneAnalyzer = new StandardAnalyzer();
   IndexWriter indexWriter = new IndexWriter(indexDir, luceneAnalyzer,true,IndexWriter.MaxFieldLength.UNLIMITED);
   File[] textFiles = fileDir.listFiles();
   long startTime = new Date().getTime();
  
   //增加document到索引去
   for (int i = 0; i < textFiles.length; i++) {
    if (textFiles[i].isFile()
      && textFiles[i].getName().endsWith(".txt")) {
     System.out.println("File " + textFiles[i].getCanonicalPath()
       + "正在被索引....");
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
     System.out.println("==========这是文档信息=============");
     System.out.println(document);
     indexWriter.addDocument(document);
    }
   }
   //optimize()方法是对索引进行优化
   indexWriter.optimize();
   indexWriter.close();
  
   //测试一下索引的时间
   long endTime = new Date().getTime();
   System.out
     .println("这花费了"
       + (endTime - startTime)
       + " 毫秒来把文档增加到索引里面去!"
       + fileDir.getPath());
   //进行查找
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
    //hits = collector.topDocs().scoreDocs; // 拿到结果
    if (hits.length > 0) {
     System.out.println("找到:" + hits.length + " 个结果!");
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
