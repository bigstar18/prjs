<%@ page pageEncoding="GBK"%>
<%@ page import="java.util.Map"  %>
<%@ page import="java.math.*"  %>
<%@ page import="gnnt.MEBS.timebargain.manage.util.QueryConditions"  %>
<%@ page import="gnnt.MEBS.timebargain.manage.webapp.util.QueryUtil"  %>
<%@ page import="gnnt.MEBS.timebargain.manage.util.DateUtil"  %>
<%@ page import="java.util.*"  %>
<%@ page import="gnnt.MEBS.timebargain.manage.service.ReportManager"  %>
<%@ page import="gnnt.MEBS.timebargain.manage.model.*"  %>
<%@ page import="gnnt.MEBS.timebargain.manage.util.SysData" %>
<html>
<head>
<title>订货明细表</title>
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
	//QueryConditions qc = QueryUtil.getQueryConditionsFromRequest(request);
	StatQuery stat = (StatQuery)request.getAttribute("stat");
    List list = (List)request.getAttribute("lst");
    Map map=(Map)request.getAttribute("parameter");
    String day = "";
    if (map != null && map.get("dat1") != null) {
    	day = map.get("dat1").toString();
    }
    ReportManager mgr = (ReportManager) SysData.getBean("reportManager"); 
    System.out.println("--------------mgr"+list.size());
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
         <%
         
          
           for(int j = 0 ; j < list.size() ; j ++){
         %>
        <table class=C0 width="98%" height="50" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td><div align="center"><font size="3"><B>订货明细表</B></font></div></td>
          </tr>
        </table>
        <table class=C0 width="70%" height="35" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td><div align="left"><font size="2"><B>交易用户ID：</B><%=(String)((Map) list.get(j)).get("FirmID")%></font></div></td>
            <td><div align="center"><font size="2"><B>单位：元</B><%=""%></font></div></td>
            <td><div align="right"><font size="2"><B>日 期：</B><%=day%></font></div></td>
          </tr>
        </table>
        <table class=C1 width="100%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#999999">
          <tr>
            <td class=T1 nowrap width="30%" height="30" valign="middle"><div align="center"><font size="2"><B>商品代码</B></font></div></td>
           
            <td class=T1 nowrap width="10%" height="30" valign="middle"><div align="center"><font size="2"><B>买/卖</B></font></div></td>
            <td class=T1 nowrap width="10%" height="30" valign="middle"><div align="center"><font size="2"><B>价格</B></font></div></td>
            <td class=T1 nowrap width="10%" height="30" valign="middle"><div align="center"><font size="2"><B>数量</B></font></div></td>
            <td class=T2 nowrap width="10%" height="30" valign="middle"><div align="center"><font size="2"><B>浮亏</B></font></div></td>
       <!--      <td class=T2 nowrap width="30%" valign="center"><div align="right"><font size="2"><%=""%></font></div></td> -->
          </tr>
          
          <% List list1 = mgr.getHoldDetailOther(stat,"'"+(String)((Map) list.get(j)).get("FirmID")+"'");
             double sum1 = 0;
          		double sum2 = 0;
          		double sum3 = 0;
          		System.out.println("----99");
              for(int i = 0;i<list1.size();i++){
        
          %>
          <tr>
            <td class=T3 nowrap width="30%" valign="center"><div align="center"><font size="2"><%=(String)((Map) list1.get(i)).get("CommodityId")%></font></div></td>
             
            
        
            <td class=T3 nowrap width="10%" valign="center"><div align="center"><font size="2"><%=(String)((Map)list1.get(i)).get("sBS_Flag")%></font></div></td>
           
            <td class=T3 nowrap width="10%" valign="center"><div align="right"><font size="2"><%=((BigDecimal)((Map) list1.get(i)).get("price")).setScale(2,BigDecimal.ROUND_HALF_UP)%></font></div></td>
           
            <td class=T3 nowrap width="10%" valign="center"><div align="right"><font size="2"><%=((BigDecimal)((Map) list1.get(i)).get("holdQty")).setScale(2,BigDecimal.ROUND_HALF_UP)%></font></div></td>
            <td class=T4 nowrap width="10%" valign="center"><div align="right"><font size="2"><%=((BigDecimal)((Map) list1.get(i)).get("loss")).setScale(2,BigDecimal.ROUND_HALF_UP)%></font></div></td>
          
          </tr>
          <%
          sum1=sum1+Double.parseDouble(((Map) list1.get(i)).get("price").toString());
          sum2=sum2+Double.parseDouble(((Map) list1.get(i)).get("holdQty").toString());
          sum3=sum3+Double.parseDouble(((Map) list1.get(i)).get("loss").toString());
          System.out.println("----119");
           }
           %>
          <tr>
            <td class=T3 nowrap width="20%" height="30" valign="middle"><div align="center"><font size="2"><B>合计</B></font></div></td>
           
            <td class=T3 nowrap width="10%" valign="center"><div align="right"><font size="2">&nbsp;</font></div></td>
            <td class=T3 nowrap width="10%" valign="center"><div align="right"><font size="2"><%=sum1 %></font></div></td>
            <td class=T3 nowrap width="10%" valign="center"><div align="right"><font size="2"><%=sum2 %></font></div></td>
            <td class=T4 nowrap width="10%" valign="center"><div align="right"><font size="2"><%=sum3 %></font></div></td>
          </tr> 
        </table>
       <table class=C0 width="70%" height="35" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td><div align="right"><font size="2"><B>打印日期：</B><%=DateUtil.getDate((Date)((Map) list.get(j)).get("sysdate"))%></font></div></td>
          </tr>
        </table>
        
        <%} System.out.println("----137");%>
      <p align="center">&nbsp;</p></td>    
    
  </table>
  <br>
</center>
</body>
</html>
