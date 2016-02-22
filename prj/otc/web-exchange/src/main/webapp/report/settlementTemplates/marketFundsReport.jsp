<%@ page pageEncoding="GBK"%>
<%@ include file="/public/reportCss.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
	<div class="report_w16_titlemin">贵金属交易所</div>
	<div class="report_w16_title">交易所台账</div>
<table width="160%" border="0" align="center" cellpadding="0" cellspacing="0">
<tr>
  <td>
	<table width="50%" border="0" align="left" cellpadding="0" cellspacing="0" bordercolor="#000000" style='border-collapse: collapse;padding-left: 20px;' height="20">
	 	 <tr>
	    <td align="left">开始日期：${oldParams["trunc(primary.cleardate)[>=][date]"]}</td>
	    <td align="left">结束日期：${oldParams["trunc(primary.cleardate)[<=][date]"]}</td>
	    <td align="left">操作人：${CURRENUSERID }</td>
	 	 </tr>
		</table>
		</td>
	</tr>
	<tr>
		<td>
	<table width="100%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#000000" style='border-collapse: collapse;'>
	<s:set var="num" value="1"></s:set>
	<s:set var="lastbankbalance" value="0"></s:set>
	<s:set var="outfund" value="0"></s:set>
	<s:set var="outcount" value="0"></s:set>
	<s:set var="infund" value="0"></s:set>
	<s:set var="incount" value="0"></s:set>
	<s:set var="tradefee" value="0"></s:set>
	<s:set var="memberfee" value="0"></s:set>
	<s:set var="mktfee" value="0"></s:set>
	<s:set var="bankbalance" value="0"></s:set>
  <tr class="report_w14">
    <td width="5%" rowspan="2">序号</td>
    <td width="8%" rowspan="2">结算日期</td>
    <td width="12%" rowspan="2">上日余额</td>
    <td width="12%" rowspan="2">出金金额</td>
    <td width="10%" rowspan="2">出金笔数</td>
    <td width="11%" rowspan="2">入金金额</td>
    <td width="8%" rowspan="2">入金笔数</td>
    <td width="22%" colspan="3">交易所手续费</td>
    <td width="12%" rowspan="2">今日余额</td>
  </tr>
  <tr class="report_w14">
    <td width="7%" align="center">今日产生</td>
	<td width="8%" align="center">今日应转出</td>
	<td width="7%" align="center">交易所留存</td>
  </tr>
			<s:iterator value="dataList()" var="dataMap">
				<tr class="report_w14_neirong">
					<td>
						<s:property value="#num" />
						<s:set var="num" value="#num+1"></s:set>
					</td>
					<td>
						<s:date name="#dataMap.cleardate" format="yyyy-MM-dd"/>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.lastbankbalance)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.lastbankbalance!=null">
						<s:set var="lastbankbalance" value="#dataMap.lastbankbalance+#lastbankbalance"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.outfund)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.outfund!=null">
						<s:set var="outfund" value="#dataMap.outfund+#outfund"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="#dataMap.outcount" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.outcount!=null">
						<s:set var="outcount" value="#dataMap.outcount+#outcount"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.infund)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.infund!=null">
						<s:set var="infund" value="#dataMap.infund+#infund"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="#dataMap.incount" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.incount!=null">
						<s:set var="incount" value="#dataMap.incount+#incount"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.tradefee)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.tradefee!=null">
						<s:set var="tradefee" value="#dataMap.tradefee+#tradefee"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.memberfee)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.memberfee!=null">
						<s:set var="memberfee" value="#dataMap.memberfee+#memberfee"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.mktfee)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.mktfee!=null">
						<s:set var="mktfee" value="#dataMap.mktfee+#mktfee"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.bankbalance)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.bankbalance!=null">
						<s:set var="bankbalance" value="#dataMap.bankbalance+#bankbalance"></s:set>
						</s:if>
					</td>
				</tr>
			</s:iterator>
			<tr class="report_w14">
			   <td>合计：</td>
			   <td>&nbsp;</td>
			   <td align="right"><s:set value="format(#lastbankbalance)" var="a"/>
			   <fmt:formatNumber value="${a }" pattern="#,##0.00"/>
               </td>
			   <td align="right"><s:set value="format(#outfund)" var="a"/>
			   <fmt:formatNumber value="${a }" pattern="#,##0.00"/>
			   </td>
			   <td align="right"><s:set value="#outcount" var="a"/>
			   <fmt:formatNumber value="${a }" pattern="#,##0"/>
			   </td>
			   <td align="right"><s:set value="format(#infund)" var="a"/>
			   <fmt:formatNumber value="${a }" pattern="#,##0.00"/>
			   </td>
			   <td align="right"><s:set value="#incount" var="a"/>
			   <fmt:formatNumber value="${a }" pattern="#,##0"/>
			   </td>
			   <td align="right"><s:set value="format(#tradefee)" var="a"/>
			   <fmt:formatNumber value="${a }" pattern="#,##0.00"/>
			   </td>
			   <td align="right"><s:set value="format(#memberfee)" var="a"/>
			   <fmt:formatNumber value="${a }" pattern="#,##0.00"/>
			   </td>
			   <td align="right"><s:set value="format(#mktfee)" var="a"/>
			   <fmt:formatNumber value="${a }" pattern="#,##0.00"/>
			   </td>
			   <td align="right"><s:set value="format(#bankbalance)" var="a"/>
			   <fmt:formatNumber value="${a }" pattern="#,##0.00"/>
			   </td>
			</tr>
		</table>
		</td>
		</tr>
		</table>
	</body>
</html>
