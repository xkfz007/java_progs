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
    <td width="163" height="20" align="center"><a href="/zhouqiu/query.jsp" title="返回首页"><img src="Image/back.gif"/></a></td>
    <td width="192" height="20" align="right"><font size="4" color="blue">你搜索的关键词</font></td>
    <td width="220" height="20" align="left">
	
	  <%
		  	//获取输入的关键字   
		  	out.print("<font size=\"5\" color=\"orange\">");
		  	String name = (String) session.getAttribute("name");//查询为作者1的名字

		  	out.print(name);

		  	String name1 = (String) session.getAttribute("name1");//查询为作者2的名字

		  	out.print(name1);

		  	String name2 = (String) session.getAttribute("name2");//查询为作者单位的内容

		  	out.print(name2);

		  	String name3 = (String) session.getAttribute("name3");//查询为题目的内容

		  	out.print(name3);

		  	String name4 = (String) session.getAttribute("name4");//查询为摘要题目的内容

		  	out.print(name4);

		  	String name5 = (String) session.getAttribute("name5");//查询为关键词的内容

		  	out.print(name5);

		  	String name6 = (String) session.getAttribute("name6");//查询分类号的内容

		  	out.print(name6);
		  	out.print("</font>");
		  %>
     <font size="4" color="blue">
	 的结果是 
	 </font>
	 </td>
    <td width="169" height="20"><font size="4" color="blue"></font></td>
  </tr>
 <tr>
 <td  colspan="4" align="center" valign="top">&nbsp;    
     <%
         	String sumnu = (String) session.getAttribute("nub"); //总页数
         	int sum = Integer.parseInt(sumnu); //将字符串转换成整形 
         	int Pagenub = 1; //初始为第一页
         	int Page = 1;
         	//假设页数为空
         	if (request.getParameter("Pagenub") == null
         			|| request.getParameter("Pagenub").equals("")) {
         		Page = 1;

         	} else {
         		Page = Integer.parseInt(request.getParameter("Pagenub"));//将页数赋值给变量Page 

         	}
         	//假设获得的页数小于1即点上一页时出现的情况
         	if (Pagenub < 1 || Page < 1) {
         		Page = 1;
         	}
         	//假设大于最后一页即点下一页时出现的情况
         	if (Pagenub > sum || Page > sum) {
         		Page = sum;
         	}
         	List list = (List) session.getAttribute("list");
         	//遍历数据   

         	ListIterator listiterator = list.listIterator();
         	while (listiterator.hasNext()) {

         		ArticlecontentDTO dto = (ArticlecontentDTO) listiterator.next();
         		int a = dto.getArtnuber();
         		if (a == Page) {
         			out.print(a);
                     if(dto.getArticlecontent().trim().equals("") || dto.getArticlecontent().trim()==null)
                     {
                      out.print("你搜索的内容为0");
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
        	out.print("一共为");
        	out.print(sum);
        	out.print("页");
        %>
</td>
        <td width="80" align="center"><font color="blue" size="3"><a href="suc.jsp?Pagenub=<%out.print(1);%>">第一页</a></font></td>
        <td width="80" align="center"><font color="blue" size="3"><a href="suc.jsp?Pagenub=<%out.print(Page-1);%>">上一页</a></font></td>
        <td width="80" align="center"><font color="blue" size="3"><a href="suc.jsp?Pagenub=<%out.print(Page+1);%>">下一页</a></font></td>
        <td width="80" align="center"><font color="blue" size="3"><a href="suc.jsp?Pagenub=<%out.print(sum);%>">尾&nbsp;&nbsp;页</a></font></td>
        <td width="174"><%
        	out.print("当前页为");
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
