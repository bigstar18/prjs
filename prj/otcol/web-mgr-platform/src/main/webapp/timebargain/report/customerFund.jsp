

<%@ page pageEncoding="GBK"%>
<%@ page import="gnnt.MEBS.timebargain.manage.service.ReportManager"  %>
<%@ page import="gnnt.MEBS.timebargain.manage.util.QueryConditions"  %>
<%@ page import="gnnt.MEBS.timebargain.manage.webapp.util.QueryUtil"  %>
<%@ page import="gnnt.MEBS.timebargain.manage.util.DateUtil"  %>
<%@ page import="java.util.Date"  %>
<%@ page import="java.util.List"  %>
<%@ page import="java.util.Map"  %>
<%@ page import="java.math.*"  %>
<%@ page import="gnnt.MEBS.timebargain.manage.util.*"  %>

<%
try{
ReportManager mgr = (ReportManager) SysData.getBean("reportManager");
QueryConditions conditions = QueryUtil.getQueryConditionsFromRequest(request);
       String reportType = null;
		Date clearDate = (Date)conditions.getConditionValue("a.ClearDate");
		String p_ClearDate = null;
		System.out.println("clearDate:"+clearDate);
       if(request.getParameter("type") == null || request.getParameter("type").equals(""))
		{ 
			
		}
		else if(request.getParameter("type").equals("day") || request.getParameter("type").equals("hisDay"))
		{
			reportType = "day";
			p_ClearDate = DateUtil.formatDate(clearDate, "yyyy-MM-dd") + "日";
			
		}
		else if(request.getParameter("type").equals("week"))
		{
			reportType = "week";
			p_ClearDate = DateUtil.formatDate(clearDate, "yyyy-MM-dd") + "周";
		}
		else if(request.getParameter("type").equals("month"))
		{
			reportType = "month";
			p_ClearDate = DateUtil.formatDate(clearDate, "yyyy-MM") + "月";
		}
		String in="";
		if(request.getParameterValues("itemlist")!=null)
		{
		String[] init=request.getParameterValues("itemlist");
		
		for(int i=0;i<init.length;i++)
		{
		   in=in+init[i]+",";
		}
		in=in.substring(0,in.length()-1);
		}
		//out.println("reportType:"+reportType);
		//out.println("clearDate:"+clearDate);
		if(request.getParameter("id")!=null)
		{
		  in=request.getParameter("id");
		}
		//out.println(in);
		
		%>
<html>
<head>
<title><%=p_ClearDate%><span style="font-size:14px;">报表</span></title>
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
<body topmargin="0">
		<%
		List lst = mgr.getReportByTypeView(conditions,reportType,in);
		System.out.println("lst:"+lst.size());
		
		if(lst != null && lst.size()>0)
		{
		   for(int i=0;i<lst.size();i++)
		   {
		      String FirmID = (String)((Map) lst.get(i)).get("FirmID");
			  String FirmName = (String)((Map) lst.get(i)).get("FirmName");
			  %>
			  <center>
             <table class=C0 width="90%" height="842" border="0" cellpadding="0" cellspacing="0">
		    <tr>
		      <td valign="top">
		      	<table class=C0 width="100%" height="35" border="0" align="center" cellpadding="0" cellspacing="0">
		          <tr>
		            <td><div align="center"><font size="6"><B></B></font></div></td>
		          </tr>
		        </table> 
		        <table class=C0 width="100%" height="35" border="0" cellpadding="0" cellspacing="0">
		          <tr>
		            <td><div align="center"><font size="4"><B><span style="font-size:14px;"><%=p_ClearDate%> 报表</span></B></font></div></td>
		          </tr>
		        </table>
		        <table class=C0 width="100%" height="35" border="0" align="center" cellpadding="0" cellspacing="0">
		          <tr> 
		            <td nowrap width="12%" height="35"><font size="3"><B><span style="font-size:14px;">二级代码：</span></B></font></td>
		            <td nowrap width="38%"><font size=3><span style="font-size:14px;"><%=FirmID%></span></font></td>
		            <td nowrap width="38%" align="right"><font size="3"><B><span style="font-size:14px;">用户名：</span></B></font></td>
		            <td nowrap width="12%"><font size="3"><span style="font-size:14px;"><%=FirmName%></span></font></td>
		          </tr>
		        </table>
		        <table class=C0 width="100%" height="35" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td valign="middle"> 
              <div align="center"><font size="3"><b><span style="font-size:14px;">--成交明细表--</span></b></font></div></td>
          </tr>
        </table>
        <table class=C0 width="100%" border=0 cellpadding=0 cellspacing=0>
          <tr>
        	<td colspan=8>
		        <table class=C1 width="100%" height="30"  border="1" cellpadding="0" cellspacing="0" bordercolor="#999999">
		          <tr> 
		            <td class=T1 nowrap width="5%"><div align="center"><font size="3"><B><span style="font-size:14px;">序 号</span></B></font></div></td>
		            <td class=T1 nowrap width="10%"><div align="center"><font size="3"><B><span style="font-size:14px;">交货时间</span></B></font></div></td>
		            <td class=T1 nowrap width="10%"><div align="center"><font size="3"><B><span style="font-size:14px;">品种</span></B></font></div></td>
		           
		            <td class=T1 nowrap width="10%"><div align="center"><font size="3"><B><span style="font-size:14px;">邀约时间</span></B></font></div></td>
		            <td class=T1 nowrap width="10%"><div align="center"><font size="3"><B><span style="font-size:14px;">成交时间</span></B></font></div></td>
		            <td class=T1 nowrap width="10%"><div align="center"><font size="3"><B><span style="font-size:14px;">成交价格</span></B></font></div></td>
		            <td class=T1 nowrap width="10%"><div align="center"><font size="3"><B><span style="font-size:14px;">订货价</span></B></font></div></td>
		            <td class=T1 nowrap width="10%"><div align="center"><font size="3"><B><span style="font-size:14px;">订货时间</span></B></font></div></td>
		            <td class=T1 nowrap width="10%"><div align="center"><font size="3"><B><span style="font-size:14px;">数 量</span></B></font></div></td>
		            <td class=T1 nowrap width="10%"><div align="center"><font size="3"><B><span style="font-size:14px;">买/卖</span></B></font></div></td>
		            <td class=T1 nowrap width="10%"><div align="center"><font size="3"><B><span style="font-size:14px;">交易类型</span></B></font></div></td>
		            <td class=T2 nowrap width="10%"><div align="center"><font size="3"><B><span style="font-size:14px;">盈 亏</span></B></font></div></td>
		          </tr>
			  <%
			  List lst1 = mgr.getReportByTypeTrade(conditions,reportType,FirmID);
			  long tradeCountAll=0;
			  if(lst1.size()>0)
			  {
			      
			      for(int a=0;a<lst1.size();a++)
			      {
			          %>
			         <tr> 
		            <td class=T3><div align="center"><font size=3><span style="font-size:14px;"><%=((BigDecimal)((Map) lst1.get(a)).get("A_TradeNo")).setScale(0,BigDecimal.ROUND_HALF_UP)%></span></font></div></td>
		            <td class=T3><div align="center"><font size=3><span style="font-size:14px;"><%=(String)((Map) lst1.get(a)).get("CommodityID")%></span></font></div></td>
		            <td class=T3><div align="center"><font size=3><span style="font-size:14px;"><%=(String)((Map) lst1.get(a)).get("BreedName")%></span></font></div></td>
		           
		            <td class=T3><div align="center"><font size=3><span style="font-size:14px;"><%=(String)((Map) lst1.get(a)).get("OrderTime")%></span></font></div></td>
		            <td class=T3><div align="center"><font size=3><span style="font-size:14px;"><%=(String)((Map) lst1.get(a)).get("TradeTime")%></span></font></div></td>
		            <td class=T3><div align="right"><font size=3><span style="font-size:14px;"><%=((BigDecimal)((Map) lst1.get(a)).get("aPrice")).setScale(2,BigDecimal.ROUND_HALF_UP)%></span></font></div></td>
		            <td class=T3><div align="right"><font size=3><span style="font-size:14px;"><%=((BigDecimal)((Map) lst1.get(a)).get("bPrice")).setScale(2,BigDecimal.ROUND_HALF_UP)%></span></font></div></td>
		            <td class=T3><div align="center"><font size=3><span style="font-size:14px;"><%=(String)((Map) lst1.get(a)).get("HoldTime")%></span></font></div></td>
		            <td class=T3><div align="center"><font size=3><span style="font-size:14px;"><%=((BigDecimal)((Map) lst1.get(a)).get("Quantity")).setScale(0,BigDecimal.ROUND_HALF_UP)%></span></font></div></td>
		            <td class=T3><div align="center"><font size=3><span style="font-size:14px;"><%=(String)((Map) lst1.get(a)).get("BS_Flag")%></span></font></div></td>
		            <td class=T3><div align="center"><font size=3><span style="font-size:14px;"><%=(String)((Map) lst1.get(a)).get("OrderType")%></span></font></div></td>
		            <td class=T4><div align="right"><font size=3><span style="font-size:14px;"><%=((BigDecimal)((Map) lst1.get(a)).get("Close_PL")).setScale(2,BigDecimal.ROUND_HALF_UP)%></span></font></div></td>
		              </tr>
		              
			          <%
			          tradeCountAll=tradeCountAll+ ((BigDecimal)((Map) lst1.get(a)).get("Quantity")).longValue();
			     
			      }
			  }
			  %>
			   </table>
		    </td>
		  </tr>
          <tr> 
          <td colspan=8>
          <table class=C0 width="100%" height="35" border="0" align="center" cellpadding="0" cellspacing="0">
          	<td height="30" width="70%"><div align="left"><font size="3"><B><span style="font-size:14px;">合 计</span></B></font></div></td>
            <td nowrap width="10%"><div align=center><font size=3><span style="font-size:14px;"><%=Arith.format(tradeCountAll,0)%></span></font></div></td>
            <td nowrap width="20%" colspan=2>&nbsp;</td>
          </table>
          </td>
          </tr>
        </table>
        <br>
        <table class=C0 width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td colspan=7>
			<table class=C0 width="100%" height="35" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td><div align="center"><font size="3"><b>--<span style="font-size:14px;">订货情况汇总表</span>--</b></font></div></td>
          </tr>
        </table>
		        <table class=C1 width="100%" height="30" border="1" cellpadding="0" cellspacing="0" bordercolor="#999999">
		          <tr> 
		            <td class=T1 width="14%"><div align="center"><font size="3"><B><span style="font-size:14px;">交货月份</span></B></font></div></td>
		            <td class=T1 width="10%"><div align="center"><font size="3"><B><span style="font-size:14px;">品种</span></B></font></div></td>
		            
		            <td class=T1 width="10%"><div align="center"><font size="3"><B><span style="font-size:14px;">买/卖</span></B></font></div></td>
		            <td class=T1 width="14%"><div align="center"><font size="3"><B><span style="font-size:14px;">当日订货价格</span></B></font></div></td>
		            <td class=T1 width="14%"><div align="center"><font size="3"><B><span style="font-size:14px;">本日平均价格</span></B></font></div></td>
		            <td class=T1 width="14%"><div align="center"><font size="3"><B><span style="font-size:14px;">数 量</span></B></font></div></td>
		            <td class=T2 width="16%"><div align="center"><font size="3"><B><span style="font-size:14px;">应交货款</span></B></font></div></td>
		          </tr>
			  <%
			   List lst2 = mgr.getReportByTypeHold(conditions,reportType,FirmID);
			  long holdCountAll=0;
			  double holdMoneyAll=0;
			  if(lst2.size()>0)
			  {
			      
			      for(int b=0;b<lst2.size();b++)
			      {
			       %>
			       <tr> 
		            <td class=T3 nowrap><div align="center"><font size=3><span style="font-size:14px;"><%=(String)((Map) lst2.get(b)).get("CommodityID")%></span></font></div></td>
		            <td class=T3 nowrap><div align="center"><font size=3><span style="font-size:14px;"><%=(String)((Map) lst2.get(b)).get("BreedName")%></span></font></div></td>
		            
		            <td class=T3 nowrap><div align="center"><font size=3><span style="font-size:14px;"><%=(String)((Map) lst2.get(b)).get("BS_Flag")%></span></font></div></td>
		            <td class=T3 nowrap><div align="right"><font size=3><span style="font-size:14px;"><%=((BigDecimal)((Map) lst2.get(b)).get("EvenPrice")).setScale(2,BigDecimal.ROUND_HALF_UP)%></span></font></div></td>
		            <td class=T3 nowrap><div align="right"><font size=3><span style="font-size:14px;"><%=((BigDecimal)((Map) lst2.get(b)).get("Price")).setScale(2,BigDecimal.ROUND_HALF_UP)%></span></font></div></td>
		            <td class=T3 nowrap><div align="center"><font size=3><span style="font-size:14px;"><%=((BigDecimal)((Map) lst2.get(b)).get("HoldQty")).setScale(0,BigDecimal.ROUND_HALF_UP)%></span></font></div></td>
		            <td class=T4 nowrap><div align="right"><font size=3><span style="font-size:14px;"><%=((BigDecimal)((Map) lst2.get(b)).get("ShouldFee")).setScale(2,BigDecimal.ROUND_HALF_UP)%></span></font></div></td>
		          </tr>
			       <%
			         holdCountAll=holdCountAll+((BigDecimal)((Map) lst2.get(b)).get("HoldQty")).longValue();
			         holdMoneyAll=holdMoneyAll+((BigDecimal)((Map) lst2.get(b)).get("ShouldFee")).doubleValue();
			      }
			  }
			  %>
			    </table>
	        </td>
          </tr>
          <tr> 
          	<td nowrap colspan=4 width="52%" height="30"><div align="left"><font size="3"><B><span style="font-size:14px;">合 计</span></B></font></div></td>
          	<td nowrap width="16%"><div align="center"><font size=3>&nbsp;</font></div></td>            
            <td nowrap width="16%"><div align="center"><font size=3><span style="font-size:14px;"><%=Arith.format(holdCountAll,0)%></span></font></div></td>            
            <td nowrap width="16%"><div align="center"><font size=3><span style="font-size:14px;"><%=Arith.format(holdMoneyAll,2)%></span></font></div></td>            
          </tr>
        </table>
        <br>
		<table class=C0 width="100%" height="35" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td valign="middle"> 
              <div align="center"><font size="3"><b>--<span style="font-size:14px;">资金情况</span>--</b></font></div></td>
          </tr>
        </table>
			  <%
			  
			  List lst3 = mgr.getReportByTypeMoney(conditions,reportType,FirmID);
			  if(lst3 != null && lst3.size()>0)
			  {
			     %>
			     <table width="100%" height="150" border="1" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC" class=C1>
          <tr> 
            <td height="30" class=T1 width="50%" valign="top"> <div align="center">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="45%" height="28"><div align="left"><B><font size="3"><span style="font-size:14px;">上日结算准备金余额：</span></font></B></div></td>
                    <td width="55%" align="right"><font size=3><span style="font-size:14px;"><%=((BigDecimal)((Map) lst3.get(0)).get("InitFund")).setScale(2,BigDecimal.ROUND_HALF_UP)%></span></font></td>
                  </tr>
                </table>
                <font size="3"></font></div></td>
            <td class=T2 width="50%" valign="bottom"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td width="45%" height="28"><div align="left"><font size="3"><B><span style="font-size:14px;">当日结算准备金余额：</span></B></font></div></td>
                  <td width="55%" align="right"><div align="right"><font size=3><span style="font-size:14px;"><%=((BigDecimal)((Map) lst3.get(0)).get("LastFund")).setScale(2,BigDecimal.ROUND_HALF_UP)%></span></font></div></td>
                </tr>
              </table></td>
          </tr>
          <tr> 
            <td height="30" class=T3 valign="bottom"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td width="45%" height="28"><div align="left"><font size="3"><B><span style="font-size:14px;">+上日已交交易保证金：</span></B></font></div></td>
                  <td width="55%" align="right"><font size=3><span style="font-size:14px;"><%=((BigDecimal)((Map) lst3.get(0)).get("ClearFee")).setScale(2,BigDecimal.ROUND_HALF_UP)%></span></font></td>
                </tr>
              </table></td>
            <td class=T4 valign="bottom"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td width="45%" height="28"><div align="left"><font size="3"><B><span style="font-size:14px;">-当日应交交易保证金：</span></B></font></div></td>
                  <td width="55%" align="right"><font size=3><span style="font-size:14px;"><%=((BigDecimal)((Map) lst3.get(0)).get("RuntimeFee")).setScale(2,BigDecimal.ROUND_HALF_UP)%></span></font></td>
                </tr>
              </table></td>
          </tr>
          <tr> 
            <td height="30" class=T3 valign="bottom"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td width="35%" height="28"><div align="left"><font size="3"><B><span style="font-size:14px;">+当日存入：</span></B></font></div></td>
                  <td width="65%" align="right"><font size=3><span style="font-size:14px;"><%=((BigDecimal)((Map) lst3.get(0)).get("InFund")).setScale(2,BigDecimal.ROUND_HALF_UP)%></span></font></td>
                </tr>
              </table></td>
            <td class=T4 align="bottom"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td width="35%" height="28"><div align="left"><B><font size="3"><span style="font-size:14px;">-当日支出：</span></font></B></div></td>
                  <td width="65%" align="right"><font size=3><span style="font-size:14px;"><%=((BigDecimal)((Map) lst3.get(0)).get("OutFund")).setScale(2,BigDecimal.ROUND_HALF_UP)%></span></font></td>
                </tr>
              </table></td>
          </tr>
          <tr> 
            <td height="30" class=T3 valign="bottom"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td width="35%" height="28"><div align="left"><font size="3"><B><span style="font-size:14px;">+当日交易盈亏：</span></B></font></div></td>
                  <td width="65%" align="right"><font size=3><span style="font-size:14px;"><%=((BigDecimal)((Map) lst3.get(0)).get("Close_PL")).setScale(2,BigDecimal.ROUND_HALF_UP)%></span></font></td>
                </tr>
              </table></td>
            <td class=T4 align="bottom"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td width="35%" height="28"><div align="left"><B><font size="3"><span style="font-size:14px;">-交易手续费：</span></font></B></div></td>
                  <td width="65%" align="right"><font size=3><span style="font-size:14px;"><%=((BigDecimal)((Map) lst3.get(0)).get("TradeFee")).setScale(2,BigDecimal.ROUND_HALF_UP)%></span></font></td>
                </tr>
              </table></td>
          </tr>
            <tr> 
            <td height="30" class=T3 valign="bottom"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td width="35%" height="28"><div align="left"><font size="3"><B><span style="font-size:14px;">+交收盈亏：</span></B></font></div></td>
                  <td width="65%" align="right"><font size=3><span style="font-size:14px;"><%=((BigDecimal)((Map) lst3.get(0)).get("Settle_PL")).setScale(2,BigDecimal.ROUND_HALF_UP)%></span></font></td>
                </tr>
              </table></td>
            <td class=T4 align="bottom"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td width="35%" height="28"><div align="left"><B><font size="3"><span style="font-size:14px;">-交收手续费：</span></font></B></div></td>
                  <td width="65%" align="right"><font size=3><span style="font-size:14px;"><%=((BigDecimal)((Map) lst3.get(0)).get("SettleFee")).setScale(2,BigDecimal.ROUND_HALF_UP)%></span></font></td>
                </tr>
              </table></td>
          </tr>
           <tr> 
            <td height="30" class=T3 valign="bottom"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td width="35%" height="28"><div align="left"><font size="3"><B><span style="font-size:14px;">+销售收入：</span></B></font></div></td>
                  <td width="65%" align="right"><font size=3><span style="font-size:14px;"><%=((BigDecimal)((Map) lst3.get(0)).get("Sales")).setScale(2,BigDecimal.ROUND_HALF_UP)%></span></font></td>
                </tr>
              </table></td>
            <td class=T4 align="bottom"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td width="35%" height="28"><div align="left"><B><font size="3"><span style="font-size:14px;">-购货支出：</span></font></B></div></td>
                  <td width="65%" align="right"><font size=3><span style="font-size:14px;"><%=((BigDecimal)((Map) lst3.get(0)).get("SettleMargin")).setScale(2,BigDecimal.ROUND_HALF_UP)%></span></font></td>
                </tr>
              </table></td>
          </tr>

          <tr>
            <td height="30" valign="bottom" class=T3><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td width="35%" height="28"><div align="left"><font size="3"><B><span style="font-size:14px;">+当日担保金：</span></B></font></div></td>
                  <td width="65%" align="right"><font size=3><span style="font-size:14px;"><%=((BigDecimal)((Map) lst3.get(0)).get("ClearAssure")).setScale(2,BigDecimal.ROUND_HALF_UP)%></span></font></td>
                </tr>
                 </table></td>
            <td class=T4 valign="bottom"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td width="35%" height="28"><div align="left"><font size="3"><B><span style="font-size:14px;">-上日担保金：</span></B></font></div></td>
                  <td width="65%" align="right"><font size=3><span style="font-size:14px;"><%=((BigDecimal)((Map) lst3.get(0)).get("RuntimeAssure")).setScale(2,BigDecimal.ROUND_HALF_UP)%></span></font></td>
                </tr>
                </table></td> 
          </tr>
          
         
          
          <tr>
            <td height="30" valign="bottom" class=T3><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td width="35%" height="28"><div align="left"><font size="3"><B><span style="font-size:14px;">+上日交收保证金：</span></B></font></div></td>
                  <td width="65%" align="right"><font size=3><span style="font-size:14px;"><%=((BigDecimal)((Map) lst3.get(0)).get("ClearSettleMargin")).setScale(2,BigDecimal.ROUND_HALF_UP)%></span></font></td>
                </tr>
                 </table></td>
            <td class=T4 valign="bottom"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td width="35%" height="28"><div align="left"><font size="3"><B><span style="font-size:14px;">-当日交收保证金：</span></B></font></div></td>
                  <td width="65%" align="right"><font size=3><span style="font-size:14px;"><%=((BigDecimal)((Map) lst3.get(0)).get("RuntimeSettleMargin")).setScale(2,BigDecimal.ROUND_HALF_UP)%></span></font></td>
                </tr>
                </table></td> 
          </tr>
          
          <tr>
           
            <td class=T4 valign="bottom"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td width="35%" height="28"><div align="left"><font size="3"><B><span style="font-size:14px;">+当日其它项：</span></B></font></div></td>
                  <td width="65%" align="right"><font size=3><span style="font-size:14px;"><%=((BigDecimal)((Map) lst3.get(0)).get("OtherItem")).setScale(2,BigDecimal.ROUND_HALF_UP)%></span></font></td>
                </tr>
                </table>
                </td> 
                <td class=T4>&nbsp;</td>
          </tr>
          <tr>
          	 <td height="30" valign="bottom" class=T3>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td width="35%" height="28"><div align="left"><font size="3"><B><span style="font-size:14px;">会员权益：</span></B></font></div></td>
                  <td width="65%" align="right"><font size=3><span style="font-size:14px;"><%=((BigDecimal)((Map) lst3.get(0)).get("FirmRights")).setScale(2,BigDecimal.ROUND_HALF_UP)%></span></font></td>
                </tr>
             </table>
             </td>
             <td class=T3>&nbsp;</td>
          </tr>
        </table>
			     <%
			  }
			  
			  %>
			    <table class=C0 width="100%" height="85" border="0" cellpadding="0" cellspacing="0">
				          <tr>
				            <td nowrap width="85%" height="85">
				<div align="right"><font size="2"><B><span style="font-size:14px;">确认签字：</span></B></font></div></td>
				            <td nowrap width="15%">&nbsp;</td>
				          </tr>
				        </table> </td>
				</tr>
				  </table>
				</center>
			  <%
		   }
		}
		else
		{
		%>
		<center>
			<table class=C0 width="595"  height="842" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align=middle vlign=middle><font size=2 color=#FF0000><strong><span style="font-size:14px;">没有数据 / 数据库错误，请稍候再试！</span></strong></font></td>
			</tr>
			</table>
		 </center>
		<%
		}
%>
</body>
</html>
<%
}catch(Exception e)
{
  out.println(e);
}
%>