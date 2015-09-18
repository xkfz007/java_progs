package luceneActionForm;

import org.apache.struts.action.*;
import javax.servlet.http.*;

public class indexDocActionForm extends ActionForm {
	private String unindexedCollection;

	public String getUnindexedCollection() {
		return unindexedCollection;
	}

	public void setUnindexedCollection(String unindexedCollection) {
		this.unindexedCollection = unindexedCollection;
	}

	public ActionErrors validate(ActionMapping actionMapping,
			HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping,
			HttpServletRequest httpServletRequest) {
	}
}