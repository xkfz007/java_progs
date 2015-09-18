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
<title>查询结果</title>

<style type="text/css">
body,td,th {
	font-size: 13px;
	color: #000000;
	font-family: 宋体;
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
    <td width="163" height="20" align="center"><a href="/zhouqiu/index.jsp" title="返回首页"><img src="Image/back.gif"/></a></td>
    <td width="192" height="20" align="right"><font size="4" color="blue">你搜索的关键词</font></td>
    <td width="220" height="20" align="center">
	
	  <%
	  out.print("<font size=\"5\" color=\"orange\">");
	  String name = (String)session.getAttribute("text");//查询的内容
        byte b1[] = null;
        b1 = name.getBytes("ISO-8859-1");
        name = new String(b1);
        out.print(name);
        out.print("</font>");
      %>
       <font size="4" color="blue">
	 的结果是 
	 </font>
      
      </td>
    <td width="169" height="20"><font size="4" color="blue" ></font></td>
  </tr>
 
 <tr>
 <td  colspan="4" align="left" 
 >&nbsp;        <% 
       String pagenub = null;//获取页数
       int pagenu = 1;//将页数转换成整形
       if(session.getAttribute("pa")!=null)//取出页数
       {
         pagenub = (String)session.getAttribute("pa");
         session.removeAttribute("pa");
         
         pagenu = Integer.parseInt(pagenub);//将页数转换成整形
        }
        //假设页数小于1就重新赋值为第一页
       if(pagenu < 1)
       {
         pagenu = 1;
         }
         
         if(pagenu > 1000)
         {
         pagenu = 1000;
         }
        out.print(pagenu);//输出当前页数
        String text = (String)session.getAttribute("text");//查询的内容
        byte b[] = null;
        b = text.getBytes("ISO-8859-1");
        text = new String(b);
        
        List list = null; 
        //假设查询查来的内容不为空     
        if(session.getAttribute("list")!= null)
        {
         list=(List)session.getAttribute("list");
         session.removeAttribute("list");
        }
        //遍历数据
        ListIterator listiterator = list.listIterator();
        while(listiterator.hasNext())     
         {
          
          SarticlecontentDTO dto = (SarticlecontentDTO)listiterator.next();
         // int a = dto.getArtnuber();//页数
          
         // out.print("<td>"+a+"</td>");//输出页数
          out.print(dto.getArticlecontent());   //输出论文的内容      
          
          
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
        <td width="80" align="center"><font color="blue" size="3"><a href="/zhouqiu/activeQuery.do?pa=1&value=<%out.print(text);%>">第一页</a></font></td> 
        <td width="80" align="center"><font color="blue" size="3"><a href="/zhouqiu/activeQuery.do?pa=<%=(pagenu+1)%>&value=<%out.print(text);%>">下一页</a></font></td>
                <td width="80" align="center"><font color="blue" size="3"><a href="/zhouqiu/activeQuery.do?pa=185&value=<%out.print(text);%>">尾&nbsp;&nbsp;页</a></font></td>
      <%
        }  
        else
        {
         if( request.getAttribute("lastpage") == null || request.getAttribute("lastpage").equals("") == true)
         {
         %> 
        <td width="80" align="center"><font color="blue" size="3"><a href="/zhouqiu/activeQuery.do?pa=1&value=<%out.print(text);%>">第一页</a></font></td>              
        <td width="80" align="center"><font color="blue" size="3"><a href="/zhouqiu/activeQuery.do?pa=<%=(pagenu-1)%>&value=<%out.print(text);%>">上一页</a></font></td>
        <td width="80" align="center"><font color="blue" size="3"><a href="/zhouqiu/activeQuery.do?pa=<%=(pagenu+1)%>&value=<%out.print(text);%>">下一页</a></font></td>
                <td width="80" align="center"><font color="blue" size="3"><a href="/zhouqiu/activeQuery.do?pa=185&value=<%out.print(text);%>">尾&nbsp;&nbsp;页</a></font></td>
        
       <%
       }
       else
       {%> 
        <td width="80" align="center"><font color="blue" size="3"><a href="/zhouqiu/activeQuery.do?pa=1&value=<%out.print(text);%>">第一页</a></font></td>              
        <td width="80" align="center"><font color="blue" size="3"><a href="/zhouqiu/activeQuery.do?pa=<%=(pagenu-1)%>&value=<%out.print(text);%>">上一页</a></font></td>
        <td width="80" align="center"><font color="blue" size="3"><a href="/zhouqiu/activeQuery.do?pa=185&value=<%out.print(text);%>">尾&nbsp;&nbsp;页</a></font></td>
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
