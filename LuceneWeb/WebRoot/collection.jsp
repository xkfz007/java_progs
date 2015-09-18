<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page contentType="text/html; charset=gb2312" import="org.bit.demo.*"%>
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>文档集合</title>
<style type="text/css">
<!--
.style1 {color: #6699CC}
-->
</style>
</head>

<body>
<table width="700" border="0">
  <tr>
    <td>
	  <table width="700" border="0">
	    <tr>
		    <td width="100" height="30">&nbsp;</td>
		    <td width="500" height="30" align="center" valign="middle"><span class="style1">文档集合列表</span></td>
		    <td width="100" height="30">&nbsp;</td>
	    </tr>
	  </table>
	</td>
  </tr>

  <tr>
    <td width="700" height="1" bgcolor="#6699CC">&nbsp;</td>
  </tr>

  <tr>
  	<td>
<%
	InitialCollection ic=(InitialCollection)application.getAttribute("collection");
	if (ic.size()==0)
	{
%>
	<font color="red">目前没有文档集合</font>
<%
	}
	else
	{
%>
     <table border="0" bgcolor="#FFFF99">
<%
		for(int i=0;i<ic.size();i++)
		{
			DocCollection d=(DocCollection)ic.get(i);
%>
	<tr>
		<td width="10"><%=i+1%></td>
		<td width="100"><%=d.getName()%></td>
		<td width="300"><%=d.getUrl()%></td>
		<td width="100"><%=d.getfileNum()%></td>
		
<%
		String dellink = "/luceneAction/delCollectionAction.do?index="+i;
%>
		<td width="50"><html:link page="<%=dellink%>">Delete</html:link></td>
		<td></td>
	</tr>
<%
		}
%>
     </table>
<%
	}
%>
	</td>
  </tr>

  <tr>
  	<td>
		<table>
		<tr>
			<td width="100" height="30">&nbsp;</td>
			<td width="500" height="30" align="center" valign="middle"><span class="style1">添加文档集合</span></td>
			<td width="100" height="30">&nbsp;</td>
		</tr>
		</table>
	</td>
  </tr>

  <tr>
    <td width="700" height="1" bgcolor="#6699CC">&nbsp;</td>
  </tr>

  <tr>
    <td>
		<table width="700" height="100" border="0">
			<html:form action="/luceneAction/insertCollectionAction.do" method="post">
			<tr><td><span class="style1">输入集合的名字：</span><html:text property="collectionName" value=""/></td></tr>
			<tr><td><span class="style1">输入集合的位置：</span><html:text property="collectionPath" value=""/></td></tr>
			<tr><td><html:submit value="添加" property="confirm1"/></td></tr>
			</html:form>
	    </table>
	</td>
  </tr>
</table>
</body>
</html:html>
