package project;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.Field.TermVector;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasChildFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.StringFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.junit.Test;

public class HIMLIndex {

	// String dataS="F:\\hfz\\�ִ���Ϣ����\\����ҵ\\����\\IRProjTest\\datasource";
	// IndexWriter indexWriter = new IndexWriter(indexDir,
	// luceneAnalyzer,true,IndexWriter.MaxFieldLength.UNLIMITED);
	String path1 = "E:\\hfz\\�ִ���Ϣ����\\TopConferences\\SIGIR\\HTML\\SIGIR";

	String indexpath = "E:\\hfz\\�ִ���Ϣ����\\����ҵ\\����2\\SmallIR\\sigirIndexes";

	// String indexpath = "E:\\hfz\\�ִ���Ϣ����\\����ҵ\\����2\\SmallIR\\sigirIndexes2";
	// �ַ�����ʽ������
	public String strOp(String str) {
		String s1 = str.trim().replace("&nbsp;", "").replace("&amp;", "");
		s1 = s1.replaceAll("\\(.+\\)", "");
		return s1;
	}

	// �ڱ���л�ȡ��������������Ϣ
	public Document getTitleAndAuthor(String urlStr, Document doc) {
		try {
			NodeFilter filter = new NodeClassFilter(TableTag.class);

			Parser parser = new Parser();
			NodeList nodelist = null;

			// ����url��ַ
			parser.setURL(urlStr);
			parser.setEncoding(parser.getEncoding());

			// ��ȡ���⣬���ߺ����߹�����λ��Ϣ
			nodelist = parser.extractAllNodesThatMatch(filter);
			if (nodelist.size() > 0) {
				// ������0-14�еĵ�7��
				TableTag tableTitle = (TableTag) nodelist.elementAt(7);
				// System.out.println(table);
				TableRow rowTitle = tableTitle.getRow(0);
				String title = strOp(rowTitle.toPlainTextString());

				// ���title
				doc.add(new Field("title", title, Store.YES, Index.ANALYZED));// ,TermVector.WITH_POSITIONS_OFFSETS));
				// System.out.println("����Ϊ��" + title);

				// ������Ϣʱ�ڵ�8��
				TableTag tableAuthor = (TableTag) nodelist.elementAt(8);
				// System.out.println(tableAuthor.toPlainTextString());
				TableRow[] rowAuthor = tableAuthor.getRows();
				int rowcount = rowAuthor.length;
				// ��ʼ��
				String[] authors = new String[rowcount];
				String[] workplace = new String[rowcount];
				for (int i = 0; i < rowcount; i++) {
					authors[i] = "";
					workplace[i] = "";
				}
				TableRow tmprow = null;
				TableColumn authorCol = null, workCol = null;
				for (int i = 0; i < rowcount; i++) {
					tmprow = rowAuthor[i];
					// ����ط�Ҫע��
					if (tmprow.getColumnCount() > 1) {
						authorCol = tmprow.getColumns()[0];
						workCol = tmprow.getColumns()[1];
						authors[i] = strOp(authorCol.toPlainTextString());
						workplace[i] = strOp(workCol.toPlainTextString());// .trim().replace("&nbsp;",
						// "");
					}
					// ������Termvertor����
					doc.add(new Field("author", authors[i], Store.YES, Index.ANALYZED));// ,TermVector.WITH_POSITIONS_OFFSETS));
					doc.add(new Field("workplace", workplace[i], Store.YES, Index.ANALYZED));// ,TermVector.WITH_POSITIONS_OFFSETS));

					// System.out.println("��" + (i + 1) + "������Ϊ��" + authors[i]
					// + ",���ߵĹ�����λΪ��" + workplace[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return doc;
	}

	// ��ȡժҪ��Ϣ��û�еĻ��������ַ���
	public Document getAbstract(String urlStr, Document doc) {
		Parser parser = new Parser();
		// ����url��ַ
		try {
			parser.setURL(urlStr);
			parser.setEncoding(parser.getEncoding());
			// NodeFilter classAbstract=new
			// HasAttributeFilter("class","abstract");
			NodeFilter nameAbstract = new HasAttributeFilter("name", "abstract");
			// NodeFilter filter2And=new AndFilter(classAbstract,nameAbstract);

			// NodeFilter childFilter1=new HasChildFilter(classAbstract);
			NodeFilter childFilter2 = new HasChildFilter(nameAbstract);
			NodeFilter divFilter = new TagNameFilter("DIV");

			NodeFilter pFilter = new TagNameFilter("p");
			NodeFilter spanFilter = new TagNameFilter("span");
			NodeFilter spchild = new AndFilter(spanFilter, childFilter2);

			NodeFilter childFilter3 = new HasChildFilter(spchild);

			NodeList nodelist = null;

			nodelist = parser.extractAllNodesThatMatch(new AndFilter(divFilter, childFilter3));

			String abt = "", tmps;
			int len = 0;
			// System.out.println(nodelist.size());
			Node divNode = nodelist.elementAt(0);
			NodeList nodelist2 = new NodeList();
			if (divNode != null) {
				divNode.collectInto(nodelist2, pFilter);
				for (int i = 0; i < nodelist2.size(); i++) {

					Node node = nodelist2.elementAt(i);

					if (node != null) {

						// paraList.add(tag.getText());
						// System.out.println(node.getText());
						// System.out.println(tag.toPlainTextString().trim());
						// System.out.println((i+1)+"***********************************");
						tmps = node.toPlainTextString().trim();
						// tmps=strOp(tmps);
						int tmpn = tmps.length();
						if (tmpn >= len) {
							len = tmpn;
							// K=i;
							abt = tmps;
						}
					}

				}

			}

			// ��ժҪ�����ַ����任
			abt = abt.replace("&nbsp;", "").replace("&amp;", "").replace("&rdquo;", "");
			// ������Termvertor����
			doc.add(new Field("abstract", abt, Store.YES, Index.ANALYZED));// ,TermVector.WITH_POSITIONS_OFFSETS));
			// System.out.println("ժҪ��ϢΪ��" + abt);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}

	//��ȡ���ķ���ʱ��
	public Document getYearofPublication(String urlStr, Document doc) {
		Parser parser = new Parser();
		// ����url��ַ
		try {
			parser.setURL(urlStr);
			parser.setEncoding(parser.getEncoding());
			NodeFilter yearFilter = new StringFilter("Year of Publication:");
			NodeList nodelist = null;
			nodelist = parser.extractAllNodesThatMatch(yearFilter);
			Node yearNode = nodelist.elementAt(0);
			String year = yearNode.toPlainTextString().trim();
			year = strOp(year);
			int ind = year.indexOf(":");
			year = year.substring(ind + 1, ind + 5);
			doc.add(new Field("year", year, Store.YES, Index.ANALYZED));
			System.out.println(year);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}

	// ���������ķ���
	@Test
	public void createIndex() {
		// String
		// path="F:\\hfz\\�ִ���Ϣ����\\TopConferences\\SIGIR\\HTML\\SIGIR2002";
		String path = "";

		// Document document=new Document();
		File indexDir = new File(indexpath);
		try {
			Analyzer luceneAnalyzer = new StandardAnalyzer();
			// Ϊ�����٣�׼���ý��������ڴ��н�����
			Directory fsDir = FSDirectory.getDirectory(indexpath);
			// 1������ʱ��ȡ
			Directory ramDir = new RAMDirectory(fsDir);
			// ���г���ʱ���� ramDir
			IndexWriter ramIndexWriter = new IndexWriter(ramDir, luceneAnalyzer, MaxFieldLength.LIMITED);
			// IndexWriter indexWriter = new IndexWriter(indexDir,
			// luceneAnalyzer,
			// true, IndexWriter.MaxFieldLength.UNLIMITED);

			int[] No = new int[33];
			No[0] = 1971;
			No[1] = 1978;
			for (int i = 2; i <= 32; i++)
				No[i] = No[i - 1] + 1;

			Document doc = null;
			long startTime = new Date().getTime();
			for (int j = 0; j < 33; j++) {

				path = path1 + Integer.toString(No[j]);
				File fileDir = new File(path);
				File[] htmlFiles = fileDir.listFiles();
				for (int i = 0; i < htmlFiles.length; i++) {
					String name = htmlFiles[i].getName();
					String fpath = htmlFiles[i].getAbsolutePath();
					// System.out.println(htmlFiles[i].getAbsolutePath());
					if (htmlFiles[i].isFile() && name.endsWith(".html") && name.startsWith("P")) {
						System.out.println("===========��" + (i + 1) + "ƪ������Ϣ��=============");
						System.out.println(fpath);
						// getInfo(fpath);
						// tagTest2(fpath);
						// ��������
						doc = new Document();
						doc = getTitleAndAuthor(fpath, doc);
						doc = getAbstract(fpath, doc);
						doc = getYearofPublication(fpath, doc);
						System.out.println(doc);
						ramIndexWriter.addDocument(doc);
					}

				}
			}

			// optimize()�����Ƕ����������Ż�
			ramIndexWriter.optimize();
			ramIndexWriter.close();
			// 2���˳�ʱ����
			IndexWriter fsIndexWriter = new IndexWriter(indexpath, luceneAnalyzer, true, MaxFieldLength.LIMITED);
			fsIndexWriter.addIndexesNoOptimize(new Directory[] { ramDir });

			fsIndexWriter.flush();
			fsIndexWriter.optimize();
			fsIndexWriter.close();
			// ����һ��������ʱ��
			long endTime = new Date().getTime();

			System.out.println("�⻨����" + (endTime - startTime) + " ���������ĵ����ӵ���������ȥ!");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
