package cn.itcast.lucene.query;

import java.util.Date;

import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.NumberTools;
import org.apache.lucene.document.DateTools.Resolution;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.RangeQuery;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.search.BooleanClause.Occur;
import org.junit.Test;

import cn.itcast.lucene.IndexDao;
import cn.itcast.lucene.QueryResult;
import cn.itcast.lucene.utils.File2DocumentUtils;

public class QueryTest {

	IndexDao indexDao = new IndexDao();

	public void queryAndPrintResult(Query query) {
		System.out.println("��Ӧ�Ĳ�ѯ�ַ�����" + query);
		QueryResult qr = indexDao.search(query, 0, 100);
		System.out.println("�ܹ��С�" + qr.getRecordCount() + "����ƥ����");
		for (Document doc : qr.getRecordList()) {
			File2DocumentUtils.printDocumentInfo(doc);
		}
	}

	/**
	 * �ؼ��ʲ�ѯ
	 * 
	 * name:room
	 */
	@Test
	public void testTermQuery() {
		// Term term = new Term("name", "����");
		// Term term = new Term("name", "Room"); // Ӣ�Ĺؼ���ȫ��Сд�ַ�
		Term term = new Term("name", "room");
		Query query = new TermQuery(term);

		queryAndPrintResult(query);
	}

	/**
	 * ��Χ��ѯ
	 * 
	 * �����߽磺size:[0000000000001e TO 000000000000rs]
	 * 
	 * �������߽磺size:{0000000000001e TO 000000000000rs}
	 */
	@Test
	public void testRangeQuery() {
		Term lowerTerm = new Term("size", NumberTools.longToString(50));
		Term upperTerm = new Term("size", NumberTools.longToString(1000));
		Query query = new RangeQuery(lowerTerm, upperTerm, false);

		queryAndPrintResult(query);
	}

	// public static void main(String[] args) {
	// System.out.println(Long.MAX_VALUE);
	// System.out.println(NumberTools.longToString(1000));
	// System.out.println(NumberTools.stringToLong("000000000000rs"));
	//
	// System.out.println(DateTools.dateToString(new Date(), Resolution.DAY));
	// System.out.println(DateTools.dateToString(new Date(), Resolution.MINUTE));
	// System.out.println(DateTools.dateToString(new Date(), Resolution.SECOND));
	// }

	/**
	 * ͨ�����ѯ
	 * 
	 * '?' ����һ���ַ��� '*' ����0�������ַ�
	 * 
	 * name:��*
	 * 
	 * name:*o*
	 * 
	 * name:roo?
	 */
	@Test
	public void testWildcardQuery() {
		Term term = new Term("name", "roo?");
		// Term term = new Term("name", "ro*"); // ǰ׺��ѯ PrefixQuery
		// Term term = new Term("name", "*o*");
		// Term term = new Term("name", "��*");
		Query query = new WildcardQuery(term);

		queryAndPrintResult(query);
	}

	/**
	 * �����ѯ
	 * 
	 * content:"? ��ʿ ? ? ����"
	 * 
	 * content:"��ʿ ����"~2
	 */
	@Test
	public void testPhraseQuery() {
		PhraseQuery phraseQuery = new PhraseQuery();
		// phraseQuery.add(new Term("content", "��ʿ"), 1);
		// phraseQuery.add(new Term("content", "����"), 4);

		phraseQuery.add(new Term("content", "��ʿ"));
		phraseQuery.add(new Term("content", "����"));
		phraseQuery.setSlop(2);

		queryAndPrintResult(phraseQuery);
	}

	/**
	 * +content:"��ʿ ����"~2 -size:[000000000000dw TO 000000000000rs]
	 * 
	 * +content:"��ʿ ����"~2 +size:[000000000000dw TO 000000000000rs]
	 * 
	 * content:"��ʿ ����"~2 size:[000000000000dw TO 000000000000rs]
	 * 
	 * +content:"��ʿ ����"~2 size:[000000000000dw TO 000000000000rs]
	 */
	@Test
	public void testBooleanQuery() {
		// ����1
		PhraseQuery query1 = new PhraseQuery();
		query1.add(new Term("content", "��ʿ"));
		query1.add(new Term("content", "����"));
		query1.setSlop(2);

		// ����2
		Term lowerTerm = new Term("size", NumberTools.longToString(500));
		Term upperTerm = new Term("size", NumberTools.longToString(1000));
		Query query2 = new RangeQuery(lowerTerm, upperTerm, true);

		// ���
		BooleanQuery boolQuery = new BooleanQuery();
		boolQuery.add(query1, Occur.MUST);
		boolQuery.add(query2, Occur.SHOULD);

		queryAndPrintResult(boolQuery);
	}

	@Test
	public void testQueryString() {
		// String queryString = "+content:\"��ʿ ����\"~2 -size:[000000000000dw TO 000000000000rs]";
		// String queryString = "content:\"��ʿ ����\"~2 AND size:[000000000000dw TO 000000000000rs]";
		// String queryString = "content:\"��ʿ ����\"~2 OR size:[000000000000dw TO 000000000000rs]";
		// String queryString = "(content:\"��ʿ ����\"~2 NOT size:[000000000000dw TO 000000000000rs])";
//		String queryString = "-content:\"��ʿ ����\"~2 AND -size:[000000000000dw TO 000000000000rs]";
//		String queryString = "-content:\"��ʿ ����\"~2 OR -size:[000000000000dw TO 000000000000rs]";
		String queryString = "-content:\"��ʿ ����\"~2 NOT -size:[000000000000dw TO 000000000000rs]";

		QueryResult qr = indexDao.search(queryString, 0, 10);
		System.out.println("�ܹ��С�" + qr.getRecordCount() + "����ƥ����");
		for (Document doc : qr.getRecordList()) {
			File2DocumentUtils.printDocumentInfo(doc);
		}
	}

}
