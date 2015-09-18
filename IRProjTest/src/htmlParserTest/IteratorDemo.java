package htmlParserTest;



import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;


public class IteratorDemo {

	/**
	 * 迭代一个Node结点的所有根子结点
	 * @param parser 
	 */
	public void listAll(Parser parser) {
		try {
			NodeIterator nodeIterator = parser.elements();
			while( nodeIterator.hasMoreNodes()) {
				System.out.println("======================================");
				Node node = nodeIterator.nextNode();
				System.out.println("getText():" + node.getText());
				System.out.println("toHtml():" + node.toHtml());
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String urlStr = "F:\\hfz\\现代信息检索\\大作业\\测试\\papers\\P105-SIGIR1971.html";
		//String urlStr = "F:\\hfz\\现代信息检索\\大作业\\测试\\papers\\htmlparser.html";
                //HtmlParserUtils类为自己提炼的一个公用类，详细代码将会在后面提供。
		Parser parser = HtmlParserUtils.getParserWithUrlStr(urlStr, "utf-8");
		IteratorDemo it = new IteratorDemo();
		it.listAll(parser);
	}
}
