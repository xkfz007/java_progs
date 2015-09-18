package luceneAction;

import org.apache.struts.action.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.File;
import org.bit.demo.*;
import luceneActionForm.*;

public class indexDocAction
    extends Action {
  public ActionForward execute(ActionMapping actionMapping,
                               ActionForm actionForm,
                               HttpServletRequest httpServletRequest,
                               HttpServletResponse httpServletResponse) {

    indexDocActionForm iForm = (indexDocActionForm) actionForm;
    String tab = iForm.getUnindexedCollection();
    int index = Integer.valueOf(tab).intValue();

    ServletContext application = httpServletRequest.getSession().
        getServletContext();
    InitialCollection ic = (InitialCollection) application.getAttribute("collection");
    DocCollection d = (DocCollection) ic.get(index);

    String url = d.getUrl();
    String collectionname = d.getName();
    boolean create = false;

    if (!new File(url).exists()) {
      ic.delete(index);
      application.setAttribute("collection", ic);
      return actionMapping.findForward("indexError");
    }

    if (!ic.getEverIndexed())
      create = true;

    String serverurl = null;
    serverurl = httpServletRequest.getSession().getServletContext().getRealPath("/");
    
    IndexFiles.beginIndex(url, create, serverurl, collectionname);

    d.setToIndexed();
    ic.set(index, d);
    ic.everIndexed();
    application.setAttribute("collection", ic);

    return (actionMapping.findForward("indexOk"));

  }
}