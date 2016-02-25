<%@ page pageEncoding="GBK"%>
<%@ page import="java.util.Map"  %>
<%@ page import="java.math.*"  %>
<%@ page import="gnnt.MEBS.timebargain.manage.util.DateUtil"  %>
<%@ page import="java.util.*"  %>

<html>
<head>
<title>总帐户统计</title>
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
%>
<body topmargin="0">
<center>

    <tr> 
      <td width="780" height="400" valign="top"><table  width="780" height="35" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td><div align="center"><font size="6"><B></B></font></div></td>
          </tr>
        </table>
        <table  width="780" height="50" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td><div align="center"><font size="3"><B><span style="font-size:14px;">总帐户统计</span></B></font></div></td>
          </tr>
        </table>
        <table  width="780" height="35" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr><%String clearDate =(String) request.getAttribute("clearDate"); %>
            <td><div align="center"><font size="2"><B><span style="font-size:14px;">日 期：</span></B><span style="font-size:14px;"><%=clearDate%></span></font></div></td>
          </tr>
        </table>
        <table  width="600" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#999999">
          <tr>
            <td  nowrap width="50%" height="30" valign="middle"><div align="center"><font size="2"><B><span style="font-size:14px;">上日资金余额</span></B></font></div></td>
            <td  nowrap width="50%" valign="center"><div align="right"><font size="2"><span style="font-size:14px;"><%=map.get("initFunds")%></span></font></div></td>
          </tr>
          <tr>
            <td  nowrap width="50%" height="30" valign="middle"><div align="center"><font size="2"><B><span style="font-size:14px;">（+）单日入金</span></B></font></div></td>
            <td  nowrap width="50%" valign="center"><div align="right"><font size="2"><span style="font-size:14px;"><%=map.get("inFunds")%></span></font></div></td>
          </tr>
          <tr>
            <td  nowrap width="50%" height="30" valign="middle"><div align="center"><font size="2"><B><span style="font-size:14px;">（-）单日出金</span></B></font></div></td>
            <td  nowrap width="50%" valign="center"><div align="right"><font size="2"><span style="font-size:14px;"><%=map.get("outFunds")%></span></font></div></td>
          </tr>
          <tr>
            <td  nowrap width="50%" height="30" valign="middle"><div align="center"><font size="2"><B><span style="font-size:14px;">（+）上日占用交易保证金</span></B></font></div></td>
            <td nowrap width="50%" valign="center"><div align="right"><font size="2"><span style="font-size:14px;"><%=map.get("clearMargin")%></span></font></div></td>
          </tr>
          <tr>
            <td  nowrap width="50%" height="30" valign="middle"><div align="center"><font size="2"><B><span style="font-size:14px;">（-）上日占用担保金</span></B></font></div></td>
            <td  nowrap width="50%" valign="center"><div align="right"><font size="2"><span style="font-size:14px;"><%=map.get("clearAssure")%></span></font></div></td>
          </tr>
          <tr>
            <td  nowrap width="50%" height="30" valign="middle"><div align="center"><font size="2"><B><span style="font-size:14px;">（+）上日订货亏损</span></B></font></div></td>
            <td  nowrap width="50%" valign="center"><div align="right"><font size="2"><span style="font-size:14px;"><%=map.get("clearFL")%></span></font></div></td>
          </tr>
          <tr>
            <td  nowrap width="50%" height="30" valign="middle"><div align="center"><font size="2"><B><span style="font-size:14px;">（+）上日占用交收保证金</span></B></font></div></td>
            <td  nowrap width="50%" valign="center"><div align="right"><font size="2"><span style="font-size:14px;"><%=map.get("clearSettleMargin")%></span></font></div></td>
          </tr>
           <tr>
            <td  nowrap width="50%" height="30" valign="middle"><div align="center"><font size="2"><B><span style="font-size:14px;">（-）当日占用交易保证金</span></B></font></div></td>
            <td  nowrap width="50%" valign="center"><div align="right"><font size="2"><span style="font-size:14px;"><%=map.get("runtimeMargin")%></span></font></div></td>
          </tr>
          <tr>
            <td  nowrap width="50%" height="30" valign="middle"><div align="center"><font size="2"><B><span style="font-size:14px;">（+）当日占用担保金<%=""%></span></B></font></div></td>
            <td  nowrap width="50%" valign="center"><div align="right"><font size="2">
            <span style="font-size:14px;"><%=map.get("runtimeMargin")%></span>
            </font></div></td>
          </tr>
          <tr>
            <td  nowrap width="50%" height="30" valign="middle"><div align="center"><font size="2"><B><span style="font-size:14px;">（-）当日订货亏损</span></B></font></div></td>
            <td  nowrap width="50%" valign="center"><div align="right"><font size="2"><span style="font-size:14px;"><%=map.get("runtimeFL")%></span></font></div></td>
          </tr>
          <tr>
            <td  nowrap width="50%" height="30" valign="middle"><div align="center"><font size="2"><B><span style="font-size:14px;">（-）当日占用交收保证金</span></B></font></div></td>
            <td  nowrap width="50%" valign="center"><div align="right"><font size="2"><span style="font-size:14px;"><%=map.get("runtimeSettleMargin")%></span></font></div></td>
          </tr>
          
          <tr>
            <td  nowrap width="50%" height="30" valign="middle"><div align="center"><font size="2"><B><span style="font-size:14px;">（-）当日交易服务费</span></B></font></div></td>
            <td  nowrap width="50%" valign="center"><div align="right"><font size="2"><span style="font-size:14px;"><%=map.get("tradeFee")%></span></font></div></td>
          </tr>
          <tr>
            <td  nowrap width="50%" height="30" valign="middle"><div align="center"><font size="2"><B><span style="font-size:14px;">（-）当日交收服务费</span></B></font></div></td>
            <td  nowrap width="50%" valign="center"><div align="right"><font size="2"><span style="font-size:14px;"><%=map.get("settleFee")%></span></font></div></td>
          </tr>
          <tr>
            <td  nowrap width="50%" height="30" valign="middle"><div align="center"><font size="2"><B><span style="font-size:14px;">（+）转让盈亏</span></B></font></div></td>
            <td  nowrap width="50%" valign="center"><div align="right"><font size="2"><span style="font-size:14px;"><%=map.get("close_PL")%></span></font></div></td>
          </tr>
          <tr>
            <td  nowrap width="50%" height="30" valign="middle"><div align="center"><font size="2"><B><span style="font-size:14px;">（+）交收盈亏</span></B></font></div></td>
            <td  nowrap width="50%" valign="center"><div align="right"><font size="2"><span style="font-size:14px;"><%=map.get("settle_PL")%></span></font></div></td>
          </tr>
          
          <tr>
            <td  nowrap width="50%" height="30" valign="middle"><div align="center"><font size="2"><B><span style="font-size:14px;">（-）购货支出</span></B></font></div></td>
            <td  nowrap width="50%" valign="center"><div align="right"><font size="2"><span style="font-size:14px;"><%=map.get("settleMargin")%></span></font></div></td>
          </tr>
          <tr>
            <td  nowrap width="50%" height="30" valign="middle"><div align="center"><font size="2"><B><span style="font-size:14px;">（-）销售收入</span></B></font></div></td>
            <td  nowrap width="50%" valign="center"><div align="right"><font size="2"><span style="font-size:14px;"><%=map.get("sales")%></span></font></div></td>
          </tr>
          <tr>
            <td  nowrap width="50%" height="30" valign="middle"><div align="center"><font size="2"><B><span style="font-size:14px;">（+）当日其他项</span></B></font></div></td>
            <td  nowrap width="50%" valign="center"><div align="right"><font size="2"><span style="font-size:14px;"><%=map.get("otherItem")%></span></font></div></td>
          </tr>
          <tr>
            <td  nowrap width="50%" height="30" valign="middle"><div align="center"><font size="2"><B><span style="font-size:14px;">当日资金余额</span></B></font></div></td>
            <td  nowrap width="50%" valign="center"><div align="right"><font size="2"><span style="font-size:14px;"><%=map.get("balance")%></span></font></div></td>
          </tr>
          <tr>
            <td  nowrap width="50%" height="30" valign="middle"><div align="center"><font size="2"><B><span style="font-size:14px;">需追加资金</span></B></font></div></td>
            <td  nowrap width="50%" valign="center"><div align="right"><font size="2"><span style="font-size:14px;"><%=map.get("shouldAddFunds")%></span></font></div></td>
          </tr>
          <tr>
            <td  nowrap width="50%" height="30" valign="middle"><div align="center"><font size="2"><B><span style="font-size:14px;">会员利益</span></B></font></div></td>
            <td  nowrap width="50%" valign="center"><div align="right"><font size="2"><span style="font-size:14px;"><%=map.get("firmRights")%></span></font></div></td>
          </tr>
     
    </table>
     <!--  <h1 align="center">———————————————</h1>-->
     <hr>
  
    <table  border=1 align="" cellpadding="0" cellspacing="0" bordercolor="#999999" width="514" height="122">
        <tr>
          <td  nowrap width="40%" height="30" valign="middle"><div align="center"><font size="2"><span style="font-size:14px;">类别</span></font></div></td>
          <td  nowrap width="30%" valign="center"><div align="center"><font size="2"><span style="font-size:14px;">金额</span></font></div></td>
          <td  nowrap width="30%" valign="center"><div align="center"><font size="2"><span style="font-size:14px;">订货数</span></font></div></td>
        </tr>
        <tr>
            <td  nowrap width="40%" height="30" valign="middle"><div align="center"><font size="2"><B><span style="font-size:14px;">买单占用资金 <%=""%></span></B></font></div></td>
            <td  nowrap width="30%" valign="center"><div align="right"><font size="2"><span style="font-size:14px;"><%=map.get("mjzyzj")%></span></font></div></td>
            <td  nowrap width="30%" valign="center"><div align="right"><font size="2"><span style="font-size:14px;"><%=map.get("mjccs")%></span></font></div></td>
          </tr>
          <tr>
            <td  nowrap width="40%" height="30" valign="middle"><div align="center"><font size="2"><B><span style="font-size:14px;">卖单占用资金 <%=""%></span></B></font></div></td>
            <td  nowrap width="30%" valign="center"><div align="right"><font size="2"><span style="font-size:14px;"><%=map.get("mczyzj")%></span></font></div></td>
            <td  nowrap width="30%" valign="center"><div align="right"><font size="2"><span style="font-size:14px;"><%=map.get("mcccs")%></span></font></div></td>
          </tr>
          <tr>
            <td  nowrap width="40%" height="30" valign="middle"><div align="center"><font size="2"><B><span style="font-size:14px;">（买仓 + 卖仓）占用资金 <%=""%></span></B></font></div></td>
            <td  nowrap width="30%" valign="center"><div align="right"><font size="2">
            <span style="font-size:14px;"><%
            Double b=(Double)map.get("mjzyzj");
            Double bb=(Double)map.get("mczyzj");
            Double bbb = new Double(b.doubleValue()+bb.doubleValue());
            out.println(new BigDecimal(bbb.toString()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
            %></span></font></div></td>
            <td class=T4 nowrap width="30%" valign="center"><div align="right"><font size="2">
            <span style="font-size:14px;"><%
            Long c=(Long)map.get("mjccs");
            Long cc=(Long)map.get("mcccs");
            Long ccc=new Long(c.longValue()+cc.longValue());
            out.println(new BigDecimal(ccc.toString()).setScale(0,BigDecimal.ROUND_HALF_UP));
            %></span></font></div></td>
          </tr>
    </table>

  <br>
</center>
</body>
</html>
