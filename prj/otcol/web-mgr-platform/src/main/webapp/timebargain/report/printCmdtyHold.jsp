<%@ page pageEncoding="GBK"%>
<%@ page import="java.util.Map"  %>
<%@ page import="java.math.*"  %>
<%@ page import="gnnt.MEBS.timebargain.manage.util.DateUtil"  %>
<%@ page import="java.util.*"  %>
<html>
<head>
<title>商品订货数明细表</title>
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
    String clearDate = "";
    if (map != null) {
    	if (map.get("ClearDate") != null) {
    		clearDate = map.get("ClearDate").toString();
    	}
    }
    List list = (List)request.getAttribute("lst");
    System.out.println("3231231345");
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
            <td><div align="center"><font size="3"><B>商品订货数明细表</B></font></div></td>
          </tr>
        </table>
        <table class=C0 width="70%" height="35" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td><div align="right"><font size="2"><B>日 期：</B><%=clearDate%></font></div></td>
          </tr>
        </table>
        <table class=C1 width="100%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#999999">
          <tr>
            <td class=T1 nowrap width="10%" height="30" valign="middle"><div align="center"><font size="2"><B>交货时间</B></font></div></td>
            
            <td class=T1 nowrap width="10%" height="30" valign="middle"><div align="center"><font size="2"><B>买批数</B></font></div></td>
            <td class=T1 nowrap width="10%" height="30" valign="middle"><div align="center"><font size="2"><B>买平均价</B></font></div></td>
            <td class=T1 nowrap width="10%" height="30" valign="middle"><div align="center"><font size="2"><B>卖批数</B></font></div></td>
            <td class=T1 nowrap width="10%" height="30" valign="middle"><div align="center"><font size="2"><B>卖平均价</B></font></div></td>
            <td class=T1 nowrap width="10%" height="30" valign="middle"><div align="center"><font size="2"><B>总批数</B></font></div></td>
       <!--      <td class=T2 nowrap width="30%" valign="center"><div align="right"><font size="2"><%=""%></font></div></td> -->
          </tr>
          <%
          double sum1 = 0;
          double sum2 = 0;
          double sum3 = 0;
          double b = 0;
          
           for(int i = 0 ; i < list.size() ; i ++){
         %>
          <tr>
            <td class=T4 nowrap width="10%" valign="center"><div align="right"><font size="2"><%=(String)((Map) list.get(i)).get("commodityId")%></font></div></td>
            <%System.out.println("1"); %>
            
           <%System.out.println("2"); %>
            <td class=T4 nowrap width="10%" valign="center"><div align="right"><font size="2"><%if(((Map) list.get(i)).get("BuyQuantity")!=null){out.println(((BigDecimal)((Map) list.get(i)).get("BuyQuantity")).setScale(2,BigDecimal.ROUND_HALF_UP));}else{out.println("0.0");}%></font></div></td>
           <%System.out.println("3"); %>
            <td class=T4 nowrap width="10%" valign="center"><div align="right"><font size="2"><%if(((Map) list.get(i)).get("BuyEvenPrice")!=null){out.println(((BigDecimal)((Map) list.get(i)).get("BuyEvenPrice")).setScale(2,BigDecimal.ROUND_HALF_UP));}else{out.println("0.0");}%></font></div></td>
           <%System.out.println("4"); %>
            <td class=T4 nowrap width="10%" valign="center"><div align="right"><font size="2"><%if(((Map) list.get(i)).get("SellQuantity")!=null){out.println(((BigDecimal)((Map) list.get(i)).get("SellQuantity")).setScale(2,BigDecimal.ROUND_HALF_UP));}else{out.print("0.0");}%></font></div></td>
           <%System.out.println("5"); %>
            <td class=T4 nowrap width="10%" valign="center"><div align="right"><font size="2"><%if(((Map) list.get(i)).get("SellEvenPrice")!=null){out.println(((BigDecimal)((Map) list.get(i)).get("SellEvenPrice")).setScale(2,BigDecimal.ROUND_HALF_UP));}else{out.print("0.0");}%></font></div></td>
            <%  
            
            if(((Map) list.get(i)).get("BuyQuantity")!=null&&((Map) list.get(i)).get("SellQuantity")!=null){
             sum1=sum1+Double.parseDouble(((Map) list.get(i)).get("BuyQuantity").toString());
             sum2=sum2+Double.parseDouble(((Map) list.get(i)).get("SellQuantity").toString());
              b = Double.parseDouble(((Map) list.get(i)).get("BuyQuantity").toString())+ Double.parseDouble(((Map) list.get(i)).get("SellQuantity").toString()); 
              }else
              if(((Map) list.get(i)).get("BuyQuantity")==null){
              	b = Double.parseDouble(((Map) list.get(i)).get("SellQuantity").toString());
              }else
              if(((Map) list.get(i)).get("SellQuantity")==null){
              	b = Double.parseDouble(((Map) list.get(i)).get("BuyQuantity").toString());
              }
              
                 System.out.println("34234234");    
              
              %>
            <td class=T4 nowrap width="10%" valign="center"><div align="right"><font size="2"><%=b%></font></div></td>
          </tr>
          <%
       
          sum3=sum3+b;
            System.out.println("2222"); 
          } %>
          <tr>
            <td class=T5 nowrap width="10%" height="30" valign="middle"><div align="center"><font size="2"><B>合计</B></font></div></td>
            
            <td class=T5 nowrap width="10%" valign="center"><div align="right"><font size="2"><%=sum1%></font></div></td>
            <td class=T5 nowrap width="10%" valign="center"><div align="right"><font size="2"><%=""%></font></div></td>
            <td class=T5 nowrap width="10%" valign="center"><div align="right"><font size="2"><%=sum2%></font></div></td>
            <td class=T5 nowrap width="10%" valign="center"><div align="right"><font size="2"><%=""%></font></div></td>
            <td class=T5 nowrap width="10%" valign="center"><div align="right"><font size="2"><%=sum3%></font></div></td>
          </tr> 
        </table>
       
      <p align="center">&nbsp;</p></td> 
    
  </table>
  <br>
</center>
</body>
</html>
