package htmlParserTest;

import java.net.URL;

import junit.framework.TestCase;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.beans.LinkBean;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.HeadTag;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.InputTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.OptionTag;
import org.htmlparser.tags.SelectTag;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.tags.TitleTag;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.HtmlPage;
import org.htmlparser.visitors.NodeVisitor;
import org.htmlparser.visitors.ObjectFindingVisitor;
import org.junit.Test;

public class ParserTestCase extends TestCase {
	private static final String taokeUrl = "http://pindao.huoban.taobao.com/tms/channel/channelcode.htm?pid=mm_17386592_0_0&eventid=101329";

	// private static final Logger logger =
	// Logger.getLogger(ParserTestCase.class);

	public ParserTestCase(String name) {
		super(name);
	}

	/*
	 * 测试ObjectFindVisitor的用法
	 */
	@Test
	public void testImageVisitor() {
		try {
			ImageTag imgLink;
			ObjectFindingVisitor visitor = new ObjectFindingVisitor(
					ImageTag.class);
			Parser parser = new Parser();
			parser.setURL("http://www.baidu.com");
			parser.setEncoding(parser.getEncoding());
			parser.visitAllNodesWith(visitor);
			Node[] nodes = visitor.getTags();
			System.out.println("===========在测试testImageVisitor=============");
			for (int i = 0; i < nodes.length; i++) {
				imgLink = (ImageTag) nodes[i];
				System.out.println("testImageVisitor() ImageURL = "
						+ imgLink.getImageURL());
				System.out.println("testImageVisitor() ImageLocation = "
						+ imgLink.extractImageLocn());
				System.out.println("testImageVisitor() SRC = "
						+ imgLink.getAttribute("SRC"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 测试TagNameFilter用法
	 */
	@Test
	public void testNodeFilter() {
		try {
			NodeFilter filter = new TagNameFilter("a");
			Parser parser = new Parser();
			parser.setURL("http://www.baidu.com");
			parser.setEncoding(parser.getEncoding());
			NodeList list = parser.extractAllNodesThatMatch(filter);
			for (int i = 0; i < list.size(); i++) {
				System.out.println("testNodeFilter() "
						+ list.elementAt(i).toHtml());
				System.out.println("testNodeFilter-text: "
						+ list.elementAt(i).getFirstChild().toHtml());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * 测试NodeClassFilter用法
	 */
	public void testLinkTag() {
		try {

			NodeFilter filter = new NodeClassFilter(LinkTag.class);
			Parser parser = new Parser();
			parser.setURL(taokeUrl);
			parser.setEncoding(parser.getEncoding());
			NodeList list = parser.extractAllNodesThatMatch(filter);
			for (int i = 0; i < list.size(); i++) {
				LinkTag node = (LinkTag) list.elementAt(i);
				System.out.println("testLinkTag() getLinkText is :"
						+ node.getChildrenHTML());

				System.out.println("testLinkTag() Link is :"
						+ node.extractLink());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * 测试<link href=" text=’text/css’ rel=’stylesheet’ />用法
	 */
	public void testLinkCSS() {
		try {

			Parser parser = new Parser();
			parser
					.setInputHTML("<head><title>Link Test</title>"
							+ "<link href=’/test01/css.css’ text=’text/css’ rel=’stylesheet’ />"
							+ "<link href=’/test02/css.css’ text=’text/css’ rel=’stylesheet’ />"
							+ "</head>" + "<body>");
			parser.setEncoding(parser.getEncoding());
			NodeList nodeList = null;

			for (NodeIterator e = parser.elements(); e.hasMoreNodes();) {
				Node node = e.nextNode();
				System.out.println("testLinkCSS()" + node.getText()
						+ node.getClass());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**//*
		 * 测试OrFilter的用法
		 */
	public void testOrFilter() {
		NodeFilter inputFilter = new NodeClassFilter(InputTag.class);
		NodeFilter selectFilter = new NodeClassFilter(SelectTag.class);
		NodeList nodeList = null;
		try {
			Parser parser = new Parser();
			parser
					.setInputHTML("<head><title>OrFilter Test</title>"
							+ "<link href=’/test01/css.css’ text=’text/css’ rel=’stylesheet’ />"
							+ "<link href=’/test02/css.css’ text=’text/css’ rel=’stylesheet’ />"
							+ "</head>"
							+ "<body>"
							+ "<input type=’text’ value=’text1′ name=’text1′/>"
							+ "<input type=’text’ value=’text2′ name=’text2′/>"
							+ "<select><option id=’1′>1</option><option id=’2′>2</option><option id=’3′></option></select>"
							+ "<a href=’http://www.yeeach.com’>yeeach.com</a>"
							+ "</body>");
			parser.setEncoding(parser.getEncoding());
			OrFilter lastFilter = new OrFilter();
			lastFilter.setPredicates(new NodeFilter[] { selectFilter,
					inputFilter });
			nodeList = parser.parse(lastFilter);
			for (int i = 0; i <= nodeList.size(); i++) {
				if (nodeList.elementAt(i) instanceof InputTag) {
					InputTag tag = (InputTag) nodeList.elementAt(i);
					System.out.println("OrFilter tag name is :"
							+ tag.getTagName() + " ,tag value is:"
							+ tag.getAttribute("value"));
				}
				if (nodeList.elementAt(i) instanceof SelectTag) {
					SelectTag tag = (SelectTag) nodeList.elementAt(i);
					NodeList list = tag.getChildren();
					for (int j = 0; j < list.size(); j++) {
						OptionTag option = (OptionTag) list.elementAt(j);
						System.out.println("OrFilter Option"
								+ option.getOptionText());
					}
				}
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
	}

	/**//*
		 * 测试对<table><tr><td></td></tr></table>的解析
		 */
	public void testTable() {
		Parser myParser;
		NodeList nodeList = null;
		myParser = Parser.createParser("<body>" + "<table id=’table1′ >"
				+ "<tr><td>1-11</td><td>1-12</td><td>1-13</td>"
				+ "<tr><td>1-21</td><td>1-22</td><td>1-23</td>"
				+ "<tr><td>1-31</td><td>1-32</td><td>1-33</td></table>"
				+ "<table id=’table2′ >"
				+ "<tr><td>2-11</td><td>2-12</td><td>2-13</td>"
				+ "<tr><td>2-21</td><td>2-22</td><td>2-23</td>"
				+ "<tr><td>2-31</td><td>2-32</td><td>2-33</td></table>"
				+ "</body>", "GBK");
		NodeFilter tableFilter = new NodeClassFilter(TableTag.class);
		OrFilter lastFilter = new OrFilter();
		lastFilter.setPredicates(new NodeFilter[] { tableFilter });
		try {
			nodeList = myParser.parse(lastFilter);
			for (int i = 0; i <= nodeList.size(); i++) {
				if (nodeList.elementAt(i) instanceof TableTag) {
					TableTag tag = (TableTag) nodeList.elementAt(i);
					TableRow[] rows = tag.getRows();
					for (int j = 0; j < rows.length; j++) {
						TableRow tr = (TableRow) rows[j];
						TableColumn[] td = tr.getColumns();
						for (int k = 0; k < td.length; k++) {
							System.out.println("<td>"
									+ td[k].toPlainTextString());
						}
					}
					// System.out.println(nodeList.elementAt(i)+ " "+ i);
				}
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
	}

	/**//*
		 * 测试NodeVisitor的用法，遍历所有节点
		 */
	public void testVisitorAll() {
		try {
			Parser parser = new Parser();
			parser.setURL("http://www.baidu.com");
			parser.setEncoding(parser.getEncoding());
			NodeVisitor visitor = new NodeVisitor() {
				public void visitTag(Tag tag) {
					System.out.println("testVisitorAll()  Tag name is :"
							+ tag.getTagName() + " \n Class is :"
							+ tag.getClass());
				}
			};
			parser.visitAllNodesWith(visitor);
		} catch (ParserException e) {
			e.printStackTrace();
		}
	}

	/**//*
		 * 测试对指定Tag的NodeVisitor的用法
		 */
	public void testTagVisitor() {
		try {
			Parser parser = new Parser(
					"<head><title>dddd</title>"
							+ "<link href=’/test01/css.css’ text=’text/css’ rel=’stylesheet’ />"
							+ "<link href=’/test02/css.css’ text=’text/css’ rel=’stylesheet’ />"
							+ "</head>" + "<body>"
							+ "<a href=’http://www.yeeach.com’>yeeach.com</a>"
							+ "</body>");
			NodeVisitor visitor = new NodeVisitor() {
				public void visitTag(Tag tag) {
					if (tag instanceof HeadTag) {
						System.out.println("visitTag() HeadTag : Tag name is :"
								+ tag.getTagName() + " \n Class is :"
								+ tag.getClass() + "\n Text is :"
								+ tag.getText());
					} else if (tag instanceof TitleTag) {
						System.out
								.println("visitTag() TitleTag : Tag name is :"
										+ tag.getTagName() + " \n Class is :"
										+ tag.getClass() + "\n Text is :"
										+ tag.getText());
					} else if (tag instanceof LinkTag) {
						System.out.println("visitTag() LinkTag : Tag name is :"
								+ tag.getTagName() + " \n Class is :"
								+ tag.getClass() + "\n Text is :"
								+ tag.getText() + " \n getAttribute is :"
								+ tag.getAttribute("href"));
					} else {
						System.out.println("visitTag() : Tag name is :"
								+ tag.getTagName() + " \n Class is :"
								+ tag.getClass() + "\n Text is :"
								+ tag.getText());
					}
				}
			};
			parser.visitAllNodesWith(visitor);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 测试HtmlPage的用法, 遍历节点

	public void testHtmlPage() {
		Parser parser = null;
		HtmlPage htmlPage = null;
		NodeList list = null;
		try {
			parser = new Parser();
			String inputHTML = "<html>" + "<head>"
					+ "<title>Welcome to the HTMLParser website</title>"
					+ "</head><body>Welcome to HTMLParser"
					+ "<table id=’table1′ >"
					+ "<tr><td>1-11</td><td>1-12</td><td>1-13</td>"
					+ "<tr><td>1-21</td><td>1-22</td><td>1-23</td>"
					+ "<tr><td>1-31</td><td>1-32</td><td>1-33</td></table>"
					+ "<table id=’table2′ >"
					+ "<tr><td>2-11</td><td>2-12</td><td>2-13</td>"
					+ "<tr><td>2-21</td><td>2-22</td><td>2-23</td>"
					+ "<tr><td>2-31</td><td>2-32</td><td>2-33</td></table>"
					+ "</body></html>";

			parser.setInputHTML(inputHTML);
			htmlPage = new HtmlPage(parser);
			parser.visitAllNodesWith(htmlPage);
			System.out.println("Title:" + htmlPage.getTitle());

			list = htmlPage.getBody();

			for (NodeIterator iterator = list.elements(); iterator
					.hasMoreNodes();) {
				Node node = iterator.nextNode();
				System.out.println(node.toHtml());
			}

			TableTag[] tables = htmlPage.getTables();

			for (int i = 0; i < tables.length; i++) {
				TableRow[] rows = tables[i].getRows();
				for (int r = 0; r < rows.length; r++) {
					TableColumn[] cols = rows[r].getColumns();
					for (int c = 0; c < cols.length; c++) {
						System.out.print(cols[c].toPlainTextString() + " ");
					}
					System.out.println();
				}
			}

		} catch (ParserException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 测试LinkBean的用法
	 */
	public void testLinkBean() {
		Parser parser = new Parser();

		LinkBean linkBean = new LinkBean();
		linkBean.setURL("http://www.baidu.com");
		URL[] urls = linkBean.getLinks();

		for (int i = 0; i < urls.length; i++) {
			URL url = urls[i];
			System.out.println("testLinkBean() -url is :" + url);
		}

	}

	// 又新写了两个测试方法
	/*
	 * 测试DIV用法
	 */
	public void testDivCSS() {
		try {
			Parser parser = new Parser();
			parser
					.setInputHTML("<html><head><title>Link Test</title>"
							+ "<link href=http://www.yeeach.com/’/test01/css.css’ text=’text/css’ rel=’stylesheet’ />"
							+ "<link href=http://www.yeeach.com/’/test02/css.css’ text=’text/css’ rel=’stylesheet’ />"
							+ "</head><body>" + "<div id=AA>dafafda</div>"
							+ "<div id=A2>CCC</div>" + "</body></html>");
			NodeFilter textFilter = new NodeClassFilter(Div.class);
			OrFilter lastFilter = new OrFilter();
			lastFilter.setPredicates(new NodeFilter[] { textFilter });
			NodeList nodeList = parser.parse(lastFilter);

			for (int i = 0; i < nodeList.size(); i++) {
				Node node = nodeList.elementAt(i);
				Div div = (Div) node;
				Tag a = null;
				System.out.println("my--->" + node.getText() + node.toHtml()
						+ node.toPlainTextString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取A里面的内容
	 */
	public void testAincludeImg() {
		try {
			Parser parser = new Parser();
			parser
					.setInputHTML("<html><head><title>Link Test</title></head><body><a href=http://wpa.qq.com/msgrd?V=1&amp;Uin=410145132&amp;Site=华奥星空论坛&amp;Menu=yes target=’_blank’>"
							+ "<img src=’http://wpa.qq.com/pa?p=1:410145132:4′  border=’0′ alt=’QQ’ />410145132</a></body></html>");
			NodeFilter textFilter = new NodeClassFilter(LinkTag.class);
			OrFilter lastFilter = new OrFilter();
			lastFilter.setPredicates(new NodeFilter[] { textFilter });
			NodeList nodeList = parser.parse(lastFilter);
			for (int i = 0; i < nodeList.size(); i++) {
				Node node = nodeList.elementAt(i);
				LinkTag div = (LinkTag) node;
				System.out.println("my--->" + node.toPlainTextString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 以StartWith的方式构建过虑器
	 */
	private NodeFilter createStartWithFilter(final String filterStr) {
		NodeFilter myFilter = new NodeFilter() { // 自定义过虑器
			public boolean accept(Node node) {
				if (node.getText().startsWith(filterStr)) {
					return true;
				} else {
					return false;
				}
			}
		};
		return myFilter;
	}

	/**
	 * 以endWith的方式构建过虑器
	 */
	private NodeFilter createEndWithFilter(final String filterStr) {
		NodeFilter myFilter = new NodeFilter() { // 自定义过虑器
			public boolean accept(Node node) {
				if (node.getText().endsWith(filterStr)) {
					return true;
				} else {
					return false;
				}
			}
		};
		return myFilter;
	}

	public static void main(String args[])
	{
		ParserTestCase pt=new ParserTestCase("testImageVisitor");
	}
}