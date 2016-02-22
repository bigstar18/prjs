<%@ page pageEncoding="GBK"%>
<%@ include file="/public/session.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
	<br>
	<div class="report_w16_title">交易报表</div>
	<table width="200%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#000000" style='border-collapse: collapse;' bgcolor="#eeeeee" height="8">
  <tr class="report_w14">
    <td width="9%">序号</td>
    <td width="9%">结算日期</td>
    <td width="9%">单号</td>
    <td width="9%">账号</td>
    <td width="8%">商品</td>   
    <td  width="7%">数量</td>
    <td width="7%">买入/卖出</td>
    <td  width="7%">建仓价</td>
    <td  width="7%">平仓价</td>
    <td  width="7%">持仓价</td>
    <td  width="7%">手续费</td>
    <td  width="7%">盈亏</td>
  </tr>
  	<s:set var="qty" value="0"></s:set>
  	<s:set var="tradefee" value="0"></s:set>
  	<s:set var="close_pl" value="0"></s:set>
  	
	<s:iterator value="dataList()" var="dataMap">
		<tr class="report_w14_tdheight">
			<td>
				<!--<s:property value="#dataMap." />-->
			</td>
			<td>
				<s:date name="#dataMap.atcleardate" format="yyyy-mm-dd" />
			</td>
			<td>
				<s:property value="#dataMap.tradeno" />&nbsp;
			</td>
			<td>
				<s:property value="#dataMap.customerno" />&nbsp;
			</td>
			<td>
				<s:property value="#dataMap.commodityName" />
			</td>
			<td>
				<s:property value="#dataMap.qty" />
				<s:set var="qty" value="#dataMap.qty+#qty"></s:set>
			</td>
			<td>
				<s:set var="bs" value="#dataMap.bs_flag" />
				<s:property value="flagMap[#bs]" />
			</td>
			<td>
				<s:property value="format(#dataMap.openPrice)"></s:property>
			</td>
			<td>
				<s:property value="format(#dataMap.closeprice)" />
			</td>
			<td>
				<s:property value="format(#dataMap.holdprice)" />
			</td>
			<td>
				<s:property value="#dataMap.tradefee" />
				<s:set var="tradefee" value="#dataMap.tradefee+#tradefee"></s:set>
			</td>
			<td>
				<s:property value="#dataMap.close_pl" />
				<s:set var="close_pl" value="#dataMap.close_pl+#close_pl"></s:set>
			</td>
			
		</tr>
		</s:iterator>
		<tr class="report_w14">
			<td>合计:
			</td>
			<td colspan="5" align="right">
				<s:property value="#qty"/>
			</td>
			<td colspan="5" align="right">
				<s:property value="#tradefee"/>
			</td>
			<td align="right">
				<s:property value="#close_pl"/>
			</td>
		</tr>
		</table>
		<br>
	</body>
</html>

