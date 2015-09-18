package cn.itcast.lucene.directory;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.junit.Test;

import cn.itcast.lucene.utils.File2DocumentUtils;

public class DirectoryTest {
	String filePath = "F:\\hfz\\现代信息检索\\大作业\\程序\\LuceneDemoSrc\\luceneDatasource\\IndexWriter addDocument's a javadoc .txt";
	String indexPath = "F:\\hfz\\现代信息检索\\大作业\\程序\\LuceneDemoSrc\\luceneIndex";
	Analyzer analyzer = new StandardAnalyzer();

	@Test
	public void test1()throws Exception {
		// Directory dir = FSDirectory.getDirectory(indexPath);
		Directory dir = new RAMDirectory();
		
		Document doc = File2DocumentUtils.file2Document(filePath);
		IndexWriter indexWriter = new IndexWriter(dir, analyzer, MaxFieldLength.LIMITED);
		indexWriter.addDocument(doc);
		indexWriter.close();
	}
	
	@Test
	public void test2() throws Exception{
		Directory fsDir = FSDirectory.getDirectory(indexPath);
	
		// 1，启动时读取
		Directory ramDir = new RAMDirectory(fsDir);
		
		// 运行程序时操作 ramDir
		IndexWriter ramIndexWriter = new IndexWriter(ramDir, analyzer, MaxFieldLength.LIMITED);
		// 添加 Document
		Document doc = File2DocumentUtils.file2Document(filePath);
		ramIndexWriter.addDocument(doc);
		ramIndexWriter.close();
		
		// 2，退出时保存
		IndexWriter fsIndexWriter = new IndexWriter(fsDir, analyzer,true, MaxFieldLength.LIMITED);
		fsIndexWriter.addIndexesNoOptimize(new Directory[]{ramDir});
		//	fsIndexWriter.flush();
		//	fsIndexWriter.optimize();
		fsIndexWriter.close();
	}
	
	
	@Test
	public void test3() throws Exception{
		Directory fsDir = FSDirectory.getDirectory(indexPath);
		IndexWriter fsIndexWriter = new IndexWriter(fsDir, analyzer, MaxFieldLength.LIMITED);
		
		fsIndexWriter.optimize();
		fsIndexWriter.close();
	}
}
