<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page contentType="text/html; charset=gb2312" import="org.bit.demo.*"%>
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>��������</title>
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
		    <td width="500" height="30" align="center" valign="middle"><span class="style1">��ѡ��Ҫ�����������ĵ�����</span></td>
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
	<table width="700" border="0">
	    <tr>
		    <td width="100" height="30">&nbsp;</td>
		    <td width="500" height="30" align="center" valign="middle">
			<html:form action="/luceneAction/indexDocAction.do" method="post">
				<html:select property="unindexedCollection" style="width:100" size="1">
<%
	InitialCollection ic=(InitialCollection)application.getAttribute("collection");

	for (int i=0;i<ic.size();i++)
	{
		DocCollection d=(DocCollection)ic.get(i);
		if (!d.isIndexed())
		{
%>
	  				<html:option value="<%=String.valueOf(i)%>"><%=d.getName()%></html:option>
<%
		}
	}
%>
				</html:select>
				<html:submit value="��ʼ����" property="startIndex"/>

			</html:form>
                    </td>
		    <td width="100" height="30">&nbsp;</td>
	    </tr>
	 </table>
    </td>
  </tr>

  <tr>
   <td>
	  <table width="700" border="0">
	    <tr>
		    <td width="100" height="30">&nbsp;</td>
		    <td width="500" height="30" align="center" valign="middle"><span class="style1">���������ĵ������б�</span></td>
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
	<font color="red">Ŀǰû���ĵ�����</font>
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
			if (d.isIndexed())
			{
%>
	<tr>
		<td width="10"><%=i+1%></td>
		<td width="100"><%=d.getName()%></td>
		<td width="300"><%=d.getUrl()%></td>
		<td width="100"><%=d.getfileNum()%></td>
<%
		String delurl = "/luceneAction/delCollectionAction.do?index=" + i;
%>
		<td width="50"><html:link page="<%=delurl%>">Delete</html:link></td>
	</tr>
<%
			}
		}
%>
     </table>
<%
	}
%>
	</td>
  </tr>

</table>
</body>
</html:html>
