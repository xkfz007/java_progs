package htmlParserTest;



import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.htmlparser.Parser;
import org.htmlparser.http.ConnectionManager;
import org.htmlparser.lexer.Page;
import org.htmlparser.util.ParserException;

public class HtmlParserUtils {

	public static Parser getParserWithUrlStr(String urlStr, String encoding) {
		Parser parser = new Parser();
		try {
			parser.setURL(urlStr);
			parser.setEncoding(encoding);
		} catch (ParserException e) {
			e.printStackTrace();
			return null;
		}
		return parser;
	}
	public static Parser getParserWithUrlConn(String urlStr, String encoding) {
		Parser parser = null;
		try {
			URL url = new URL(urlStr);
			HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
			parser = new Parser(urlConn);
			parser.setEncoding(encoding);
			return parser;
		} catch (ParserException e1) {
			e1.printStackTrace();
			return null;
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
			return null;
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		} 
	}
        //推荐使用下面这个方法，效率比较高。
	public static Parser getParserWithUrlConn2(String urlStr, String encoding) {
		Parser parser = null;
		try {
			ConnectionManager manager = Page.getConnectionManager(); 
			parser = new Parser(manager.openConnection(urlStr));
			parser.setEncoding(encoding); 
			return parser;
		} catch (ParserException e) {
			e.printStackTrace();
			return null;
		}   
	}
	
	public static Parser createParser(String htmlContent, String encoding){
		Parser parser = Parser.createParser(htmlContent,encoding); 
		return parser;
	}

}

