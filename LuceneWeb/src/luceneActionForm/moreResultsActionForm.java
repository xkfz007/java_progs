package luceneActionForm;

import org.apache.struts.action.*;
import javax.servlet.http.*;

public class moreResultsActionForm extends ActionForm {
	private int maxresults;
	private int startindex;

	public int getMaxresults() {
		return maxresults;
	}

	public void setMaxresults(int maxresults) {
		this.maxresults = maxresults;
	}

	public int getStartindex() {
		return startindex;
	}

	public void setStartindex(int startindex) {
		this.startindex = startindex;
	}

	public ActionErrors validate(ActionMapping actionMapping,
			HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping,
			HttpServletRequest httpServletRequest) {
	}
}