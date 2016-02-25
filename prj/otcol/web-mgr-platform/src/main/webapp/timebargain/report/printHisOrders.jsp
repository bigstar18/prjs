<%@ page pageEncoding="GBK"%>
<%@ page import="java.util.Map"  %>
<%@ page import="java.math.*"  %>
<%@ page import="gnnt.MEBS.timebargain.manage.util.DateUtil"  %>
<%@ page import="java.util.*"  %>
<html>
<head>
<title>委托单</title>
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
        <table class=C0 width="100%" height="50" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td><div align="center"><font size="3"><B><%=DateUtil.getDate((Date)map.get("dat1")) %>摊位代码委托单</B></font></div></td>
          </tr>
        </table>
        <table class=C0 width="70%" height="35" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td><div align="right"><font size="2"><B>日 期：</B><%=DateUtil.getDate((Date)map.get("dat1"))%></font></div></td>
          </tr>
         
        </table>
        <table class=C1 width="100%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#999999">
          <tr>
            <td  nowrap width="10%" height="30" valign="middle"><div align="center"><font size="2"><B>委托单号</B></font></div></td>           
            <td  nowrap width="10%" height="30" valign="middle"><div align="center"><font size="2"><B>交易商ID</B></font></div></td>          
            <td  nowrap width="10%" height="30" valign="middle"><div align="center"><font size="2"><B>交货时间</B></font></div></td>
            <td  nowrap width="10%" height="30" valign="middle"><div align="center"><font size="2"><B>价格</B></font></div></td>
            <td  nowrap width="10%" height="30" valign="middle"><div align="center"><font size="2"><B>买/卖</B></font></div></td>
            <td  nowrap width="10%" height="30" valign="middle"><div align="center"><font size="2"><B>委托类型</B></font></div></td>
            <td  nowrap width="10%" height="30" valign="middle"><div align="center"><font size="2"><B>数量</B></font></div></td>
            <td  nowrap width="10%" height="30" valign="middle"><div align="center"><font size="2"><B>报单时间</B></font></div></td>
            <td  nowrap width="10%" height="30" valign="middle"><div align="center"><font size="2"><B>撤单时间</B></font></div></td>
           
       <!--      <td class=T2 nowrap width="30%" valign="center"><div align="right"><font size="2"><%=""%></font></div></td> -->
          </tr>
          <%for(int i = 0 ; i < list.size(); i++){ %>
          <tr>
            <td  nowrap width="10%" valign="center"><div align="right"><font size="2"><%=((BigDecimal)((Map) list.get(i)).get("A_OrderNo")).setScale(0,BigDecimal.ROUND_HALF_UP)%></font></div></td>                   
           
            <td  nowrap width="10%" valign="center"><div align="right"><font size="2"><%=(String)((Map) list.get(i)).get("FirmID")%></font></div></td>          
        
            <td  nowrap width="10%" valign="center"><div align="right"><font size="2"><%=(String)((Map) list.get(i)).get("commodityid")%></font></div></td>
       
            <td  nowrap width="10%" valign="center"><div align="right"><font size="2"><%if(((Map) list.get(i)).get("Price")!=null){out.print(((BigDecimal)((Map) list.get(i)).get("Price")).setScale(2,BigDecimal.ROUND_HALF_UP));}else{out.print("0.00");}%></font></div></td>
       
            <td  nowrap width="10%" valign="center"><div align="right"><font size="2"><%if(((Map) list.get(i)).get("sBS_Flag")!=null){out.print((String)((Map) list.get(i)).get("sBS_Flag"));}else{out.print(" ");}%></font></div></td>
       
            <td  nowrap width="10%" valign="center"><div align="right"><font size="2"><%=(String)((Map) list.get(i)).get("sOrderType")%></font></div></td>
      
            <td  nowrap width="10%" valign="center"><div align="right"><font size="2"><%if(((Map) list.get(i)).get("Quantity")!=null){out.print(((BigDecimal)((Map) list.get(i)).get("Quantity")).setScale(2,BigDecimal.ROUND_HALF_UP));}else{out.print("");}%></font></div></td>
      
            <td  nowrap width="10%" valign="center"><div align="right"><font size="2"><%=(Date)((Map) list.get(i)).get("OrderTime")%></font></div></td>
         
            <td  nowrap width="10%" valign="center"><div align="right"><font size="2"><%if(((Map) list.get(i)).get("WithdrawTime")!=null){out.print((Date)((Map) list.get(i)).get("WithdrawTime"));}else{out.print(" ");}%></font></div></td>
      
           
      
	      </tr>
          <%} %>
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
