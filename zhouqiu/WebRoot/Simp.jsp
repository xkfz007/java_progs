<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="java.util.*" %>
<%@ page import="index.simp.DTO.*" %>
<%@ page import="index.simp.DAO.*" %>

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
    <td width="163" height="20" align="center"><a href="/zhouqiu/index.jsp" title="������ҳ"><img src="Image/back.gif"/></a></td>
    <td width="192" height="20" align="right"><font size="4" color="blue">�������Ĺؼ���</font></td>
    <td width="220" height="20" align="center">
	
	  <%
	  out.print("<font size=\"5\" color=\"orange\">");
	  String name = (String)session.getAttribute("text");//��ѯ������
        byte b1[] = null;
        b1 = name.getBytes("ISO-8859-1");
        name = new String(b1);
        out.print(name);
        out.print("</font>");
      %>
       <font size="4" color="blue">
	 �Ľ���� 
	 </font>
      
      </td>
    <td width="169" height="20"><font size="4" color="blue" ></font></td>
  </tr>
 
 <tr>
 <td  colspan="4" align="left" 
 >&nbsp;        <% 
       String pagenub = null;//��ȡҳ��
       int pagenu = 1;//��ҳ��ת��������
       if(session.getAttribute("pa")!=null)//ȡ��ҳ��
       {
         pagenub = (String)session.getAttribute("pa");
         session.removeAttribute("pa");
         
         pagenu = Integer.parseInt(pagenub);//��ҳ��ת��������
        }
        //����ҳ��С��1�����¸�ֵΪ��һҳ
       if(pagenu < 1)
       {
         pagenu = 1;
         }
         
         if(pagenu > 1000)
         {
         pagenu = 1000;
         }
        out.print(pagenu);//�����ǰҳ��
        String text = (String)session.getAttribute("text");//��ѯ������
        byte b[] = null;
        b = text.getBytes("ISO-8859-1");
        text = new String(b);
        
        List list = null; 
        //�����ѯ���������ݲ�Ϊ��     
        if(session.getAttribute("list")!= null)
        {
         list=(List)session.getAttribute("list");
         session.removeAttribute("list");
        }
        //��������
        ListIterator listiterator = list.listIterator();
        while(listiterator.hasNext())     
         {
          
          SarticlecontentDTO dto = (SarticlecontentDTO)listiterator.next();
         // int a = dto.getArtnuber();//ҳ��
          
         // out.print("<td>"+a+"</td>");//���ҳ��
          out.print(dto.getArticlecontent());   //������ĵ�����      
          
          
         }
%></td>
	</tr>
 
  <tr>
    <td height="30" colspan="4"><table width="750" height="35" border="0" cellpadding="0" cellspacing="0" >
       <tr>
        <td width="179">&nbsp;</td>

       <% 
       if(pagenu==1)
       {
       %>
        <td width="80" align="center"><font color="blue" size="3"><a href="/zhouqiu/activeQuery.do?pa=1&value=<%out.print(text);%>">��һҳ</a></font></td> 
        <td width="80" align="center"><font color="blue" size="3"><a href="/zhouqiu/activeQuery.do?pa=<%=(pagenu+1)%>&value=<%out.print(text);%>">��һҳ</a></font></td>
                <td width="80" align="center"><font color="blue" size="3"><a href="/zhouqiu/activeQuery.do?pa=185&value=<%out.print(text);%>">β&nbsp;&nbsp;ҳ</a></font></td>
      <%
        }  
        else
        {
         if( request.getAttribute("lastpage") == null || request.getAttribute("lastpage").equals("") == true)
         {
         %> 
        <td width="80" align="center"><font color="blue" size="3"><a href="/zhouqiu/activeQuery.do?pa=1&value=<%out.print(text);%>">��һҳ</a></font></td>              
        <td width="80" align="center"><font color="blue" size="3"><a href="/zhouqiu/activeQuery.do?pa=<%=(pagenu-1)%>&value=<%out.print(text);%>">��һҳ</a></font></td>
        <td width="80" align="center"><font color="blue" size="3"><a href="/zhouqiu/activeQuery.do?pa=<%=(pagenu+1)%>&value=<%out.print(text);%>">��һҳ</a></font></td>
                <td width="80" align="center"><font color="blue" size="3"><a href="/zhouqiu/activeQuery.do?pa=185&value=<%out.print(text);%>">β&nbsp;&nbsp;ҳ</a></font></td>
        
       <%
       }
       else
       {%> 
        <td width="80" align="center"><font color="blue" size="3"><a href="/zhouqiu/activeQuery.do?pa=1&value=<%out.print(text);%>">��һҳ</a></font></td>              
        <td width="80" align="center"><font color="blue" size="3"><a href="/zhouqiu/activeQuery.do?pa=<%=(pagenu-1)%>&value=<%out.print(text);%>">��һҳ</a></font></td>
        <td width="80" align="center"><font color="blue" size="3"><a href="/zhouqiu/activeQuery.do?pa=185&value=<%out.print(text);%>">β&nbsp;&nbsp;ҳ</a></font></td>
        <%
        }
      }
       %>
        <td width="174">&nbsp;</td>
      </tr>
      
    </table>
    <hr color="orange">
    </td>
  </tr>
</table>
</body>

</html:html>
