package luceneAction;

import org.apache.struts.action.*;
import javax.servlet.http.*;
import javax.servlet.ServletContext;
import java.io.File;
import luceneActionForm.insertCollectionActionForm;
import org.bit.demo.*;

public class insertCollectionAction extends Action {
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		insertCollectionActionForm iForm = (insertCollectionActionForm) actionForm;
		String cName = iForm.getCollectionName();
		String cPath = iForm.getCollectionPath();

		ServletContext application = httpServletRequest.getSession()
				.getServletContext();

		InitialCollection is = (InitialCollection) application
				.getAttribute("collection");

		if (!new File(cPath).exists())
			return actionMapping.findForward("insertError");

		is.add(new DocCollection(cName, cPath, false));
		application.setAttribute("collection", is);
		return (actionMapping.findForward("insertOk"));

	}
}