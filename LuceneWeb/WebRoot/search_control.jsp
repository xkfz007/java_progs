<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page contentType="text/html; charset=gb2312" %>
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>开始搜索</title>
<style type="text/css">
<!--
.style1 {color: #6699CC}
-->
</style>
</head>

<body>
<table border="0" width="700">
        <html:form action="/luceneAction/searchAction.do" method="post">
<tr>
	<td align="center"><span class="style1">请输入你要查找的字符串</span></td>
</tr>

<tr>
   <td align="center">
			<html:text property="query" size="44"/>
			<html:submit value="搜搜看" property="sosokan"/></td>
</tr>

<tr>
   <td>
   </td>
</tr>
<tr>
   <td align="center">
			<span class="style1">每页显示</span>
			<html:select property="maxresults" size="1">
				<html:option value="10">10</html:option>
				<html:option value="20">20</html:option>
				<html:option value="50">50</html:option>
			</html:select>
			<span class="style1">个记录</span>
    </html:form>
   </td>
</tr>
</table>
</body>
</html:html>
