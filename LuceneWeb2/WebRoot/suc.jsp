<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="java.util.*" %>
<%@ page import="index.qurey.DTO.*" %>
<%@ page import="index.query.DAO.*" %>


<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>


<html:html lang="true">
<head>
<title>��ѯ���</title>

<style type="text/css">
body,td,th {
	font-size: 13px;
	color: #000000;
	font-family: ����;
}
a:hover {
	text-decoration: underline;
	color: #FF6600;
}
a:active {
	text-decoration: none;
	color: #FF6600;
}
</style>
</head>

<body background="Image/bg2.jpg">
<table width="750" height="600" border="0" cellpadding="0" cellspacing="0" align="center">
  <tr>
    <td height="100" colspan="4" align="left"><img src="Image/daqiao.jpg" align="middle" width="750" height="100"/></td>
  </tr>
  <tr>
    <td width="163" height="20" align="center"><a href="/zhouqiu/query.jsp" title="������ҳ"><img src="Image/back.gif"/></a></td>
    <td width="192" height="20" align="right"><font size="4" color="blue">�������Ĺؼ���</font></td>
    <td width="220" height="20" align="left">
	
	  <%
		  	//��ȡ����Ĺؼ���   
		  	out.print("<font size=\"5\" color=\"orange\">");
		  	String name = (String) session.getAttribute("name");//��ѯΪ����1������

		  	out.print(name);

		  	String name1 = (String) session.getAttribute("name1");//��ѯΪ����2������

		  	out.print(name1);

		  	String name2 = (String) session.getAttribute("name2");//��ѯΪ���ߵ�λ������

		  	out.print(name2);

		  	String name3 = (String) session.getAttribute("name3");//��ѯΪ��Ŀ������

		  	out.print(name3);

		  	String name4 = (String) session.getAttribute("name4");//��ѯΪժҪ��Ŀ������

		  	out.print(name4);

		  	String name5 = (String) session.getAttribute("name5");//��ѯΪ�ؼ��ʵ�����

		  	out.print(name5);

		  	String name6 = (String) session.getAttribute("name6");//��ѯ����ŵ�����

		  	out.print(name6);
		  	out.print("</font>");
		  %>
     <font size="4" color="blue">
	 �Ľ���� 
	 </font>
	 </td>
    <td width="169" height="20"><font size="4" color="blue"></font></td>
  </tr>
 <tr>
 <td  colspan="4" align="center" valign="top">&nbsp;    
     <%
         	String sumnu = (String) session.getAttribute("nub"); //��ҳ��
         	int sum = Integer.parseInt(sumnu); //���ַ���ת�������� 
         	int Pagenub = 1; //��ʼΪ��һҳ
         	int Page = 1;
         	//����ҳ��Ϊ��
         	if (request.getParameter("Pagenub") == null
         			|| request.getParameter("Pagenub").equals("")) {
         		Page = 1;

         	} else {
         		Page = Integer.parseInt(request.getParameter("Pagenub"));//��ҳ����ֵ������Page 

         	}
         	//�����õ�ҳ��С��1������һҳʱ���ֵ����
         	if (Pagenub < 1 || Page < 1) {
         		Page = 1;
         	}
         	//����������һҳ������һҳʱ���ֵ����
         	if (Pagenub > sum || Page > sum) {
         		Page = sum;
         	}
         	List list = (List) session.getAttribute("list");
         	//��������   

         	ListIterator listiterator = list.listIterator();
         	while (listiterator.hasNext()) {

         		ArticlecontentDTO dto = (ArticlecontentDTO) listiterator.next();
         		int a = dto.getArtnuber();
         		if (a == Page) {
         			out.print(a);
                     if(dto.getArticlecontent().trim().equals("") || dto.getArticlecontent().trim()==null)
                     {
                      out.print("������������Ϊ0");
                      }
         			out.print(dto.getArticlecontent());
         			break;
         		} else {
         			continue;
         		}

         	}
         %>
</td>
	</tr>
  <tr>
    <td height="30" colspan="4"><table width="750" height="35" border="0" cellpadding="0" cellspacing="0" >
      <tr>
        <td width="179"><%
        	out.print("һ��Ϊ");
        	out.print(sum);
        	out.print("ҳ");
        %>
</td>
        <td width="80" align="center"><font color="blue" size="3"><a href="suc.jsp?Pagenub=<%out.print(1);%>">��һҳ</a></font></td>
        <td width="80" align="center"><font color="blue" size="3"><a href="suc.jsp?Pagenub=<%out.print(Page-1);%>">��һҳ</a></font></td>
        <td width="80" align="center"><font color="blue" size="3"><a href="suc.jsp?Pagenub=<%out.print(Page+1);%>">��һҳ</a></font></td>
        <td width="80" align="center"><font color="blue" size="3"><a href="suc.jsp?Pagenub=<%out.print(sum);%>">β&nbsp;&nbsp;ҳ</a></font></td>
        <td width="174"><%
        	out.print("��ǰҳΪ");
        	out.print(Page);
        	out.print("/");
        	out.print(sum);
        %></td>
      </tr>
    </table>
    <hr color="orange">
    </td>
  </tr>
</table>
</body>

</html:html>
