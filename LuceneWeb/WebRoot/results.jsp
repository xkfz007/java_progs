<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page contentType="text/html; charset=gb2312" import=" java.io.*, java.util.Date, org.bit.demo.*, org.apache.lucene.analysis.*, org.apache.lucene.document.*, org.apache.lucene.index.*, org.apache.lucene.search.*, org.apache.lucene.queryParser.*, org.apache.lucene.demo.*, org.apache.lucene.demo.html.Entities, java.text.SimpleDateFormat"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>无标题文档</title>
<link rel="stylesheet" type="text/css" href="style.css">
</head>
<%
		Hits hits=(Hits)application.getAttribute("hits");
		SearchPara searchpara=(SearchPara)application.getAttribute("searchpara");
		int maxresults=searchpara.getMaxresults();
		int startindex=searchpara.getStartindex();
		int thispage=searchpara.getThispage();
		int pagenum=searchpara.getPage();
%>
<body>
<%
		if (pagenum==0){
%>
			<p>对不起，没有找到您查询的东东，换一个试试吧:)</p>
<%
		}
		else
                {
			for(int i=startindex;i<=startindex+thispage;i++)
			{
				Document doc = hits.doc(i);
				String docRealurl = doc.get("url");
				String httpurl=request.getContextPath()+"/"+FileOperation.getLinkUrl(docRealurl,request.getSession().getServletContext().getRealPath("/"));
				Date docmodified=DateField.stringToDate(doc.get("modified"));
				
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String docmodified_str = sf.format(docmodified);
				
				String doctitle=doc.get("title");
				String docsummary=doc.get("summary");
%>
		<table border="0">
		<tr>
			<td><a href="<%=httpurl%>"><%=doctitle%></a></td>
		</tr>
		<tr>
                	<td></td>
		</tr>
		<tr>
			<td><font color="#CC0066" font-family="Verdana, Arial, Helvetica, sans-serif" font-size="16px"><%=docsummary%></font></td>
		</tr>
		<tr>
			<td><font color="#9933CC" font-family="Verdana, Arial, Helvetica, sans-serif;" font-size="14px"><%=docmodified_str%></font></td>
		</tr>
		</table>
<%
			}
%>
<table>
	<tr>
<%
		int NEXTstartindex;

		for(int i=1;i<=pagenum;i++)
		{
                	NEXTstartindex=(i-1)*maxresults;
                	
                	String m = "/luceneAction/moreResultsAction.do?maxresults="+maxresults+"&startindex="+NEXTstartindex;
                	System.out.println("href is :" + m );
%>
		<td><html:link page="<%=m%>"><%=i%></html:link></td>
<%
		}
%>
	</tr>
</table>
<%
		}
%>
</body>
</html>
