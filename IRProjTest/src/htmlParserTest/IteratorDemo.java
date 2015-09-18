package htmlParserTest;



import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;


public class IteratorDemo {

	/**
	 * ����һ��Node�������и��ӽ��
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
		String urlStr = "F:\\hfz\\�ִ���Ϣ����\\����ҵ\\����\\papers\\P105-SIGIR1971.html";
		//String urlStr = "F:\\hfz\\�ִ���Ϣ����\\����ҵ\\����\\papers\\htmlparser.html";
                //HtmlParserUtils��Ϊ�Լ�������һ�������࣬��ϸ���뽫���ں����ṩ��
		Parser parser = HtmlParserUtils.getParserWithUrlStr(urlStr, "utf-8");
		IteratorDemo it = new IteratorDemo();
		it.listAll(parser);
	}
}
