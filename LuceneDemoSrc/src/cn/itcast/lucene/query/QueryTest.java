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
		System.out.println("对应的查询字符串：" + query);
		QueryResult qr = indexDao.search(query, 0, 100);
		System.out.println("总共有【" + qr.getRecordCount() + "】条匹配结果");
		for (Document doc : qr.getRecordList()) {
			File2DocumentUtils.printDocumentInfo(doc);
		}
	}

	/**
	 * 关键词查询
	 * 
	 * name:room
	 */
	@Test
	public void testTermQuery() {
		// Term term = new Term("name", "房间");
		// Term term = new Term("name", "Room"); // 英文关键词全是小写字符
		Term term = new Term("name", "room");
		Query query = new TermQuery(term);

		queryAndPrintResult(query);
	}

	/**
	 * 范围查询
	 * 
	 * 包含边界：size:[0000000000001e TO 000000000000rs]
	 * 
	 * 不包含边界：size:{0000000000001e TO 000000000000rs}
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
	 * 通配符查询
	 * 
	 * '?' 代表一个字符， '*' 代表0个或多个字符
	 * 
	 * name:房*
	 * 
	 * name:*o*
	 * 
	 * name:roo?
	 */
	@Test
	public void testWildcardQuery() {
		Term term = new Term("name", "roo?");
		// Term term = new Term("name", "ro*"); // 前缀查询 PrefixQuery
		// Term term = new Term("name", "*o*");
		// Term term = new Term("name", "房*");
		Query query = new WildcardQuery(term);

		queryAndPrintResult(query);
	}

	/**
	 * 短语查询
	 * 
	 * content:"? 绅士 ? ? 饭店"
	 * 
	 * content:"绅士 饭店"~2
	 */
	@Test
	public void testPhraseQuery() {
		PhraseQuery phraseQuery = new PhraseQuery();
		// phraseQuery.add(new Term("content", "绅士"), 1);
		// phraseQuery.add(new Term("content", "饭店"), 4);

		phraseQuery.add(new Term("content", "绅士"));
		phraseQuery.add(new Term("content", "饭店"));
		phraseQuery.setSlop(2);

		queryAndPrintResult(phraseQuery);
	}

	/**
	 * +content:"绅士 饭店"~2 -size:[000000000000dw TO 000000000000rs]
	 * 
	 * +content:"绅士 饭店"~2 +size:[000000000000dw TO 000000000000rs]
	 * 
	 * content:"绅士 饭店"~2 size:[000000000000dw TO 000000000000rs]
	 * 
	 * +content:"绅士 饭店"~2 size:[000000000000dw TO 000000000000rs]
	 */
	@Test
	public void testBooleanQuery() {
		// 条件1
		PhraseQuery query1 = new PhraseQuery();
		query1.add(new Term("content", "绅士"));
		query1.add(new Term("content", "饭店"));
		query1.setSlop(2);

		// 条件2
		Term lowerTerm = new Term("size", NumberTools.longToString(500));
		Term upperTerm = new Term("size", NumberTools.longToString(1000));
		Query query2 = new RangeQuery(lowerTerm, upperTerm, true);

		// 组合
		BooleanQuery boolQuery = new BooleanQuery();
		boolQuery.add(query1, Occur.MUST);
		boolQuery.add(query2, Occur.SHOULD);

		queryAndPrintResult(boolQuery);
	}

	@Test
	public void testQueryString() {
		// String queryString = "+content:\"绅士 饭店\"~2 -size:[000000000000dw TO 000000000000rs]";
		// String queryString = "content:\"绅士 饭店\"~2 AND size:[000000000000dw TO 000000000000rs]";
		// String queryString = "content:\"绅士 饭店\"~2 OR size:[000000000000dw TO 000000000000rs]";
		// String queryString = "(content:\"绅士 饭店\"~2 NOT size:[000000000000dw TO 000000000000rs])";
//		String queryString = "-content:\"绅士 饭店\"~2 AND -size:[000000000000dw TO 000000000000rs]";
//		String queryString = "-content:\"绅士 饭店\"~2 OR -size:[000000000000dw TO 000000000000rs]";
		String queryString = "-content:\"绅士 饭店\"~2 NOT -size:[000000000000dw TO 000000000000rs]";

		QueryResult qr = indexDao.search(queryString, 0, 10);
		System.out.println("总共有【" + qr.getRecordCount() + "】条匹配结果");
		for (Document doc : qr.getRecordList()) {
			File2DocumentUtils.printDocumentInfo(doc);
		}
	}

}
