<%@ page pageEncoding="GBK"%>
<%@ page import="java.util.Map"  %>
<%@ page import="java.math.*"  %>
<%@ page import="gnnt.MEBS.timebargain.manage.util.DateUtil"  %>
<%@ page import="java.util.*"  %>
<html>
<head>
<title>手续费统计</title>
</head>
<style>
.C0 {
	BORDER-TOP: 0px; 
	BORDER-LEFT: 0px; 
	BORDER-BOTTOM: 0px;
}

.C1 {
	border-bottom: 1px solid; 
	border-left: 1px solid; 
	border-right: 1px solid; 
	border-top: 1px solid;	
}

.T1 {
	border-right: 0px;
	color: #000000;
}

.T2 {
	color: #000000;
}

.T3 {
	border-top: 0px;
	border-right: 0px;
	color: #000000;
}

.T4 {
	border-top: 0px;
	color: #000000;
}
</style>

<%
    Map map=(Map)request.getAttribute("parameter");
    List list = (List)request.getAttribute("lst");
%>
<body topmargin="0">
<center>
  <table class=C0 width="595"  height="842" border="0" cellpadding="0" cellspacing="0">
    <tr> 
      <td width="780" height="400" valign="top"><table class=C0 width="98%" height="35" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td><div align="center"><font size="6"><B></B></font></div></td>
          </tr>
        </table>
        <table class=C0 width="98%" height="50" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td><div align="center"><font size="3"><B><%=DateUtil.getDate((Date)map.get("queryStart")) %>至<%=DateUtil.getDate((Date)map.get("queryEnd")) %>手续费统计</B></font></div></td>
          </tr>
        </table>
        
        <table class=C1 width="100%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#999999">
          <tr>
            <td class=T1 nowrap width="30%" height="30" valign="middle"><div align="center"><font size="2"><B>交易商ID</B></font></div></td>
            <td class=T1 nowrap width="30%" height="30" valign="middle"><div align="center"><font size="2"><B>交易商名称</B></font></div></td>
            <td class=T1 nowrap width="30%" height="30" valign="middle"><div align="center"><font size="2"><B>手续费</B></font></div></td>
       <!--      <td class=T2 nowrap width="30%" valign="center"><div align="right"><font size="2"><%=""%></font></div></td> -->
          </tr>
          <%
          double sum1 = 0;
           for(int i = 0 ; i < list.size() ; i ++){
         %>
          <tr>
            <td class=T4 nowrap width="30%" valign="center"><div align="right"><font size="2"><%=(String)((Map) list.get(i)).get("FirmID")%></font></div></td>
            <td class=T4 nowrap width="30%" valign="center"><div align="right"><font size="2"><%=(String)((Map) list.get(i)).get("FirmName")%></font></div></td>
            <td class=T4 nowrap width="30%" valign="center"><div align="right"><font size="2"><%=((BigDecimal)((Map) list.get(i)).get("TradeFee")).setScale(2,BigDecimal.ROUND_HALF_UP)%></font></div></td>
          </tr>
          <%
          sum1 = sum1+Double.parseDouble(((Map) list.get(i)).get("TradeFee").toString());
          
          } %>
          <tr>
            <td class=T4 nowrap width="10%" height="30" valign="middle"><div align="center"><font size="2"><B>合计</B></font></div></td>
            <td class=T4 nowrap width="10%" valign="center"><div align="right"><font size="2"><%=""%></font></div></td>
            <td class=T4 nowrap width="10%" valign="center"><div align="right"><font size="2"><%=sum1%></font></div></td>
          </tr> 
        </table>
        <table class=C0 width="70%" height="35" border="0" align="center" cellpadding="0" cellspacing="0">
          <%
          if(list.size()>2){
          %>
          <tr>
            <td><div align="right"><font size="2"><B>打印日期：</B><%=DateUtil.getDate((Date)((Map) list.get(1)).get("sysdate"))%></font></div></td>
          </tr>
          <%}%>
        </table>
      <p align="center">&nbsp;</p></td> 
    
  </table>
  <br>
</center>
</body>
</html>
