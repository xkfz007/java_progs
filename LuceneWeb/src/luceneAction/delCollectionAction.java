package luceneAction;

import org.apache.struts.action.*;
import javax.servlet.http.*;
import javax.servlet.ServletContext;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.bit.demo.*;
import luceneActionForm.delCollectionActionForm;

public class delCollectionAction extends Action {
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		delCollectionActionForm dForm = (delCollectionActionForm) actionForm;
		int index = dForm.getIndex();

		ServletContext application = httpServletRequest.getSession()
				.getServletContext();
		InitialCollection ic = (InitialCollection) application
				.getAttribute("collection");
		DocCollection d = (DocCollection) ic.get(index);

		if (d.isIndexed()) {
			String projectPath = httpServletRequest.getSession()
					.getServletContext().getRealPath("/");
			StringBuffer delurl = new StringBuffer();
			delurl.append(projectPath).append("/collection/").append(
					d.getName());

			try {
				IndexReader reader = IndexReader
						.open(Configuration.INDEX_STORE_PATH);
				DeleteCollection.delete(reader, delurl.toString());
				reader.close();

				IndexWriter writer = new IndexWriter(
						Configuration.INDEX_STORE_PATH, new StandardAnalyzer(),
						false);
				writer.maxFieldLength = 1000000;
				writer.optimize();
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		ic.delete(index);
		return actionMapping.findForward("collection");
	}
}