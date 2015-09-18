package luceneAction;

import org.apache.struts.action.*;
import javax.servlet.http.*;
import javax.servlet.ServletContext;
import org.apache.lucene.search.Hits;
import org.bit.demo.*;
import luceneActionForm.*;

public class moreResultsAction extends Action {
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		moreResultsActionForm mForm = (moreResultsActionForm) actionForm;
		int maxresults = mForm.getMaxresults();
		int startindex = mForm.getStartindex();
		int thispage = maxresults;
		int length = 0;

		ServletContext application = httpServletRequest.getSession()
				.getServletContext();
		Hits hits = (Hits) application.getAttribute("hits");
		length = hits.length();

		if (startindex + maxresults >= length)
			thispage = thispage - (1 + maxresults + startindex - length);

		SearchPara searchpara = (SearchPara) application
				.getAttribute("searchpara");
		searchpara.setMaxresults(maxresults);
		searchpara.setStartindex(startindex);
		searchpara.setThispage(thispage);
		application.setAttribute("searchpara", searchpara);

		return actionMapping.findForward("showresults");
	}
}