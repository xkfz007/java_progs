<%@ page language="java" pageEncoding="GBK"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>


<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>集成论文检索系统</title>
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
<SCRIPT language=javascript>
function connect()
 {
 window.open("query.jsp");
  }
</SCRIPT>


<SCRIPT language=javascript>

Phrase="欢迎光临集成论文检索系统"
Balises="";
Taille=30;
Midx=350;
Decal=0.5;
Nb=Phrase.length;
y=-5;
for (x=0;x<Nb;x++){
  Balises=Balises + '<DIV Id=L' + x + ' STYLE="width:5;font-family: Courier New;font-weight:bold;position:absolute;top:20;left:50;z-index:0">' + Phrase.charAt(x) + '</DIV>'
}
document.write (Balises);
Time=window.setInterval("Alors()",10);
Alpha=5;
I_Alpha=0.05;

function Alors(){
        Alpha=Alpha-I_Alpha;
        for (x=0;x<Nb;x++){
                Alpha1=Alpha+Decal*x;
                Cosine=Math.cos(Alpha1);
                Ob=document.all("L"+x);
                Ob.style.posLeft=Midx+170*Math.sin(Alpha1)+50;
                Ob.style.zIndex=20*Cosine;
                Ob.style.fontSize=Taille+25*Cosine;
                Ob.style.color="rgb("+ (127+Cosine*80+50) + ","+ (127+Cosine*80+50) + ",0)";
        }
}

</SCRIPT>




</head>

<body bgcolor="#fef4d2"  onLoad="Timeclock(),setNum(),setInterval('timer()',100);setInterval('runTimeClock()',100)">
<center>
<font color="ffaafa">
  <h2><font color="#0000FF"></font></h2>
  </font> 
 
 

<table border="0">
<tr>
<td>

<!-- 案例代码1开始 -->

<SCRIPT language=javascript>
<!-- [Step1]:这里可以改变时钟的位置 -->
pX=80;pY=60
Timeclocks = new Array(13)

function Timeclock () {
for (i=0; i<13; i++) {
    if (document.all) Timeclocks[i]=new Array (eval('Timeclock'+i).style,-100,-100)
    else Timeclocks[i] = new Array (eval('document.Timeclock'+i),-100,-100)
    }
}

function runTimeClock() {
    for (i=0; i<13; i++) {
        Timeclocks[i][0].left=Timeclocks[i][1]+pX
        Timeclocks[i][0].top=Timeclocks[i][2]+pY
    }
}

function cl(a,b,c){
    if (document.all) {
        if (a!=0) b+=-1
        eval('c'+a+'.style.pixelTop='+(pY+(c)))
        eval('c'+a+'.style.pixelLeft='+(pX+(b)))
        }
else{
    if (a!=0) b+=10
    eval('document.c'+a+'.top='+(pY+(c)))
    eval('document.c'+a+'.left='+(pX+(b)))
}
if (document.all) c0.style.pixelLeft=26
}

function setNum(){
cl (0,-67,-65);
cl (1,10,-51);
cl (2,28,-33);
cl (3,35,-8);
cl (4,28,17);
cl (5,10,35);
cl (6,-15,42);
cl (7,-40,35);
cl (8,-58,17);
cl (9,-65,-8);
cl (10,-58,-33);
cl (11,-40,-51);
cl (12,-16,-56);
}

var lastsec
function timer() {
    time = new Date ()
    sec = time.getSeconds()
    if (sec!=lastsec) {
        lastsec = sec
        sec=Math.PI*sec/30
        min=Math.PI*time.getMinutes()/30
        hr =Math.PI*((time.getHours()*60)+time.getMinutes())/360
        for (i=1;i<6;i++) {
            Timeclocks[i][1] = Math.sin(sec) * (44 - (i-1)*11)-16;
            if (document.layers)Timeclocks[i][1]+=10;
            Timeclocks[i][2] = -Math.cos(sec) * (44 - (i-1)*11)-27;
        }
        for (i=6;i<10;i++) {
            Timeclocks[i][1] = Math.sin(min) * (40 - (i-6)*10)-16;
            if (document.layers)Timeclocks[i][1]+=10;
            Timeclocks[i][2] = -Math.cos(min) * (40 - (i-6)*10)-27;
        }
        for (i=10;i<13;i++) {
            Timeclocks[i][1] = Math.sin(hr) * (37 - (i-10)*11)-16;
            if (document.layers)Timeclocks[i][1]+=10;
            Timeclocks[i][2] = -Math.cos(hr) * (37 - (i-10)*11)-27;
        }   }}
		
		
		
		
		
</SCRIPT>


<br>
<br>
<br>
<br>


<!-- 案例代码1结束 -->


<!-- 案例代码2开始 -->
<div id="c0" style="position:absolute;right:6;top:6; z-index:2;">    </div>
<!-- [Step2]: 这里可以更该时钟数的大小、字体 -->
        <div id="c1" style="position:absolute;left:20;top:-20; z-index:5;font-size:11px;"><b>1</b></div>
        <div id="c2" style="position:absolute;left:20;top:-20; z-index:5;font-size:11px;"><b>2</b></div>
        <div id="c3" style="position:absolute;left:20;top:-20; z-index:5;font-size:11px;"><b>3</b></div>
        <div id="c4" style="position:absolute;left:20;top:-20; z-index:5;font-size:11px;"><b>4</b></div>
        <div id="c5" style="position:absolute;left:20;top:-20; z-index:5;font-size:11px;"><b>5</b></div>
        <div id="c6" style="position:absolute;left:20;top:-20; z-index:5;font-size:11px;"><b>6</b></div>
        <div id="c7" style="position:absolute;left:20;top:-20; z-index:5;font-size:11px;"><b>7</b></div>
        <div id="c8" style="position:absolute;left:20;top:-20; z-index:5;font-size:11px;"><b>8</b></div>
        <div id="c9" style="position:absolute;left:20;top:-20; z-index:5;font-size:11px;"><b>9</b></div>
        <div id="c10" style="position:absolute;left:20;top:-20; z-index:5;font-size:11px;"><b>10</b></div>
        <div id="c11" style="position:absolute;left:20;top:-20; z-index:5;font-size:11px;"><b>11</b></div>
        <div id="c12" style="position:absolute;left:20;top:-20; z-index:5;font-size:11px;"><b>12</b></div>
        <div id="Timeclock0" style="position:absolute;left:-20;top:-20;z-index:1">  </div>
<!-- [Step3]: 这里可以更该秒针的大小、颜色、字体 -->
        <div id="Timeclock1" style="position:absolute;left:-20;top:-20;z-index:8"> <font size="+3" color="#0000FF"><b>.</b></font></div>
        <div id="Timeclock2" style="position:absolute;left:-20;top:-20;z-index:8"> <font size="+3" color="#0000FF"><b>.</b></font></div>
        <div id="Timeclock3" style="position:absolute;left:-20;top:-20;z-index:8"> <font size="+3" color="#0000FF"><b>.</b></font></div>
        <div id="Timeclock4" style="position:absolute;left:-20;top:-20;z-index:8"> <font size="+3" color="#0000FF"><b>.</b></font></div>
        <div id="Timeclock5" style="position:absolute;left:-20;top:-20;z-index:8"> <font size="+3" color="#0000FF"><b>.</b></font></div>
<!-- [Step4]: 这里可以更该分针的大小、颜色、字体 -->
        <div id="Timeclock6" style="position:absolute;left:-20;top:-20;z-index:7"> <font size="+3" color="#00FFFF"><b>.</b></font></div>
        <div id="Timeclock7" style="position:absolute;left:-20;top:-20;z-index:7"> <font size="+3" color="#00FFFF"><b>.</b></font></div>
        <div id="Timeclock8" style="position:absolute;left:-20;top:-20;z-index:7"> <font size="+3" color="#00FFFF"><b>.</b></font></div>
        <div id="Timeclock9" style="position:absolute;left:-20;top:-20;z-index:7"> <font size="+3" color="#00FFFF"><b>.</b></font></div>
<!-- [Step5]: 这里可以更该时针的大小、颜色、字体 -->
        <div id="Timeclock10" style="position:absolute;left:-20;top:-20;z-index:6"> <font size="+3" color="#F30000"><b>.</b></font></div>
        <div id="Timeclock11" style="position:absolute;left:-20;top:-20;z-index:6"> <font size="+3" color="#F30000"><b>.</b></font></div>
        <div id="Timeclock12" style="position:absolute;left:-20;top:-20;z-index:6"> <font size="+3" color="#F30000"><b>.</b></font></div>

<!-- 案例代码2结束 -->
<html:errors/>
<html:form action="simpleAction.do" method="post">

<hr color="#0099CC">

<tr>
<td><html:text size = "30" property = "text1" /></td>
<td>&nbsp;<html:submit  value="检索"/></td>
<td><input type="button" size="30" value="高级检索" onclick="connect()"></td>
</tr>
</html:form>
</table>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<center><font color="#006699" face="宋体" size="3">
努力才会成功
</font>
</center>
<img src="img/22.jpg">
<br>
<center>
<font color="#006699" face="宋体" size="4">


南昌航空大学04201509周秋文
</font>
</center>
<img src="img/22.jpg">
<hr>
<center>
<font color="#006699" face="宋体" size="4">
2007-2008版权南昌航空大学
</font>
</center>
</body>
</html:html>
