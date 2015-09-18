package luceneActionForm;

import org.apache.struts.action.*;
import javax.servlet.http.*;

public class searchActionForm extends ActionForm {
	private String maxresults;

	private String query;

	public String getMaxresults() {
		return maxresults;
	}

	public void setMaxresults(String maxresults) {
		this.maxresults = maxresults;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public ActionErrors validate(ActionMapping actionMapping,
			HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping,
			HttpServletRequest httpServletRequest) {
	}
}