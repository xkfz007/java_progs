package luceneAction;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.ServletContext;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.standard.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryParser.*;
import org.apache.lucene.search.*;
import org.apache.struts.action.*;
import org.bit.demo.*;
import luceneActionForm.*;

public class searchAction extends Action {
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		searchActionForm sForm = (searchActionForm) actionForm;

		String queryString = null;

		try {
			queryString = new String(sForm.getQuery().getBytes("ISO8859_1"),
					"GB2312");
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
		}

		String max = sForm.getMaxresults();

		int maxresults = Integer.parseInt(max);
		int startindex = 0;
		int thispage = maxresults;

		IndexSearcher searcher = null;
		Query query = null;
		Hits hits = null;

		try {
			searcher = new IndexSearcher(IndexReader
					.open(Configuration.INDEX_STORE_PATH));
		} catch (Exception e) {
			e.printStackTrace();
		}

		Analyzer analyzer = new StandardAnalyzer();
		try {
			query = QueryParser.parse(queryString, "contents", analyzer);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		try {
			hits = searcher.search(query);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ServletContext application = httpServletRequest.getSession()
				.getServletContext();
		application.setAttribute("hits", hits);

		int length = hits.length();
		int page = 0;

		if (length != 0)
			if (length / maxresults != 0 && length % maxresults != 0)
				page = length / maxresults + 1;
			else if (length / maxresults == 0 && length % maxresults != 0)
				page = 1;
			else if (length / maxresults != 0 && length % maxresults == 0)
				page = length / maxresults;

		if (startindex + maxresults >= length)
			thispage = thispage - (1 + maxresults + startindex - length);

		SearchPara searchpara = new SearchPara(startindex, maxresults,
				thispage, page);
		application.setAttribute("searchpara", searchpara);

		return actionMapping.findForward("showresults");

	}
}