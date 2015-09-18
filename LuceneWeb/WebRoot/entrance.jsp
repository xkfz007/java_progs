<%@ page contentType="text/html; charset=GB2312" import="org.bit.demo.*"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<html>
<head>
<title>Struts与Lucene</title>
<style type="text/css">
<!--
.style1 {color: #6699CC}
-->
</style>
<link rel="stylesheet" type="text/css" href="style.css">
</head>

<body bgcolor="#ffffff" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<%@include file="title.jsp" %>
<%
	InitialCollection ic;
	ic=(InitialCollection)application.getAttribute("collection");
	if (ic==null)
        {
		ic=new InitialCollection();
		application.setAttribute("collection",ic);
        }
%>
<table width="1024" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>

    <td width="130" valign="top" height="306" bgcolor="#ffffff">


      <table width="150" border="0" cellspacing="0" cellpadding="0">

	<tr>
          <td width="30" height="8"></td>
          <td width="100" height="8"></td>
        </tr>
        <tr>
          <td width="30" height="22" bgcolor="#ffffff" align="center"><html:img width="10" height="10" src="image/VOD_elite.gif"/></td>
          <td width="100" height="22" bgcolor="#ffffff">
          	<html:link href="collection.jsp" target="managefrm"><span class="style1">文档集合</span></html:link></td>
        </tr>
	<tr>
          <td width="30" height="8"></td>
          <td width="100" height="8"></td>
        </tr>
        <tr>
          <td width="30" height="22" bgcolor="#ffffff" align="center"><html:img width="10" height="10" src="image/VOD_elite.gif"/></td>
          <td width="100" height="22" bgcolor="#ffffff">
          	<html:link href="index_control.jsp" target="managefrm"><span class="style1">建立索引</span></html:link></td>
        </tr>
	<tr>
          <td width="30" height="8"></td>
          <td width="100" height="8"></td>
        </tr>
        <tr>
          <td width="30" height="22" bgcolor="#ffffff" align="center"><html:img width="10" height="10" src="image/VOD_elite.gif"/></td>
          <td width="100" height="22" bgcolor="#ffffff">
          	<html:link href="search_control.jsp" target="managefrm"><span class="style1">开始搜索</span></html:link></td>
        </tr>
        <tr>
          <td width="30" height="8"></td>
          <td width="100" height="8"></td>
        </tr>
        <tr>
          <td width="30" height="22" bgcolor="#ffffff" align="center"><html:img width="10" height="10" src="image/VOD_elite.gif"/></td>
          <td width="100" height="22" bgcolor="#ffffff">
          	<html:link href="configuration.jsp" target="managefrm"><span class="style1">设置信息</span></html:link></td>
        </tr>
        <tr>
          <td width="30" height="8"></td>
          <td width="100" height="8"></td>
        </tr>
        <tr>
        <tr bgcolor="#ffffff">
          <td width="30" height="22" align="center" bgcolor="#ffffff"><html:img width="10" height="10" src="image/VOD_elite.gif"/></td>
          <td height="22" bgcolor="#ffffff" >
          	<html:link href="welcome.jsp" target="managefrm"><span class="style1">帮助</span></html:link></td>
        </tr>
      </table>

    </td>


    <td width="874" valign="top" align="center">
      <table border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#ffffff">
          <td>
          	<iframe name="managefrm" frameborder="0" width="874" height="800" src="welcome.jsp">
      		</iframe>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<%@include file="footer.jsp" %>
</body>
</html>
