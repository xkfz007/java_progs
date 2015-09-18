package cn.itcast.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.junit.Test;

import cn.itcast.lucene.utils.File2DocumentUtils;

public class IndexDaoTest {

	String filePath = "F:\\hfz\\现代信息检索\\大作业\\程序\\LuceneDemoSrc\\luceneDatasource\\IndexWriter addDocument's a javadoc .txt";
	String filePath2 = "F:\\hfz\\现代信息检索\\大作业\\程序\\LuceneDemoSrc\\luceneDatasource\\小笑话_总统的房间 Room .txt";

	IndexDao indexDao = new IndexDao();

	@Test
	public void testSave() {
		Document doc = File2DocumentUtils.file2Document(filePath);
		doc.setBoost(3f);
		indexDao.save(doc);

		Document doc2 = File2DocumentUtils.file2Document(filePath2);
		// doc2.setBoost(1.0f);
		indexDao.save(doc2);
	}

	@Test
	public void testDelete() {
		Term term = new Term("path", filePath);
		indexDao.delete(term);
	}

	@Test
	public void testUpdate() {
		Term term = new Term("path", filePath);

		Document doc = File2DocumentUtils.file2Document(filePath);
		doc.getField("content").setValue("这是更新后的文件内容");

		indexDao.update(term, doc);
	}

	@Test
	public void testSearch() {
		// String queryString = "IndexWriter";
		// String queryString = "房间";
		// String queryString = "笑话";
		String queryString = "room";
		// String queryString = "content:绅士";
		QueryResult qr = indexDao.search(queryString, 0, 10);

		System.out.println("总共有【" + qr.getRecordCount() + "】条匹配结果");
		for (Document doc : qr.getRecordList()) {
			File2DocumentUtils.printDocumentInfo(doc);
		}
	}

}
