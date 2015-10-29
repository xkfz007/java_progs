package htmlParserTest;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;


public class FilterDemo {

	/**
	 * 使用TagNameFilter过滤所有table标签结点。
	 * @param parser 
	 */
	public void filter(Parser parser) {
		
		
		NodeList nodelist;
		try {
			/*
			 * 过滤所有table标签结点。
			 */
			NodeFilter filter = new TagNameFilter("table");
			nodelist = parser.parse(filter);
			
			Node node = nodelist.elementAt(0);
			Node firstChild = node.getFirstChild();
			Node secondChild = firstChild.getNextSibling();
			Node thirdChild = secondChild.getNextSibling();
			Node forthChild = thirdChild.getNextSibling();
			
			System.out.println("node-->" + node.getText() + "-->" + node.toHtml());
			System.out.println("firstChild-->" + firstChild.getText() + "-->" + firstChild.toHtml());//注意换行符，此结点为换行符。
			System.out.println("secondChild-->" + secondChild.getText() + "-->" + secondChild.toHtml());
			System.out.println("thirdChild-->" + thirdChild.getText() + "-->" + thirdChild.toHtml());
			System.out.println("forthChild-->" + forthChild.getText() + "-->" + forthChild.toHtml());
			
		} catch (ParserException e1) {
			e1.printStackTrace();
		}
	}
	public static void main(String[] args) {
		String urlStr = "F:\\hfz\\现代信息检索\\大作业\\测试\\papers\\P105-SIGIR1971.html";
		//String urlStr = "F:\\hfz\\现代信息检索\\大作业\\测试\\papers\\htmlparser.html";
		Parser parser = HtmlParserUtils.getParserWithUrlStr(urlStr, "utf-8");
		FilterDemo filter = new FilterDemo();
		filter.filter(parser);
	}

}