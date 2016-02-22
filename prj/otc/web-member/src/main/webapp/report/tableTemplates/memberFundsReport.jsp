<%@ page pageEncoding="GBK"%>
<%@ include file="/public/reportCss.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
	<br><br>
	<div class="report_w16_title">交易资金状况</div>
	<table width="200%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#000000" style='border-collapse: collapse;'>
	<s:set var="lastbalance" value="0"></s:set>
	<s:set var="fundi" value="0"></s:set>
	<s:set var="fundo" value="0"></s:set>
	<s:set var="tradefee" value="0"></s:set>
	<s:set var="closepl" value="0"></s:set>
	<s:set var="holdpl" value="0"></s:set>
	<s:set var="m_tradefee" value="0"></s:set>
	<s:set var="s_tradefee" value="0"></s:set>
	<s:set var="m_delayfee" value="0"></s:set>
	<s:set var="s_delayfee" value="0"></s:set>
	<s:set var="todaybalance" value="0"></s:set>
	<s:set var="margin" value="0"></s:set>
	<s:set var="num" value="1"></s:set>
  <tr class="report_w14">
  	 <td width="4%" rowspan="2">序号</td>
    <td width="8%" rowspan="2">上日余额</td>
    <td width="7%" rowspan="2">入金</td>
    <td width="7%" rowspan="2">出金</td>
    <td width="7%" rowspan="2">手续费</td>
    <td width="8%" rowspan="2">平仓盈亏</td>
    <td width="7%" rowspan="2">持仓盈亏</td>
    <td width="9%" rowspan="2">手续费收入</td>
    <td width="9%" rowspan="2">上交手续费</td>
    <td width="15%" colspan="2">延期费</td>
    <td width="8%" rowspan="2">本日余额</td>
    <td width="7%" rowspan="2">占用保证金</td>
    <td width="8%" rowspan="2">风险率</td>
  </tr>
  <tr class="report_w14">
    <td width="7%" align="center">收客户</td>
	<td width="9%" align="center">交特别会员</td>
  </tr>
			<s:iterator value="dataList()" var="dataMap">
				<tr class="report_w14_neirong">
					<td>
						<s:property value="#num" />
						<s:set var="num" value="#num+1"></s:set>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.lastbalance)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.lastbalance!=null">
						<s:set var="lastbalance" value="#dataMap.lastbalance+#lastbalance"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.fundi)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.fundi!=null">
						<s:set var="fundi" value="#dataMap.fundi+#fundi"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.fundo)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.fundo!=null">
						<s:set var="fundo" value="#dataMap.fundo+#fundo"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.tradefee)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.tradefee!=null">
						<s:set var="tradefee" value="#dataMap.tradefee+#tradefee"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.closepl)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.closepl!=null">
						<s:set var="closepl" value="#dataMap.closepl+#closepl"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.holdpl)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.holdpl!=null">
						<s:set var="holdpl" value="#dataMap.holdpl+#holdpl"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.m_tradefee)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.m_tradefee!=null">
						<s:set var="m_tradefee" value="#dataMap.m_tradefee+#m_tradefee"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.s_tradefee)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.s_tradefee!=null">
						<s:set var="s_tradefee" value="#dataMap.s_tradefee+#s_tradefee"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.m_delayfee)"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.m_delayfee!=null">
						<s:set var="m_delayfee" value="#dataMap.m_delayfee+#m_delayfee"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.s_delayfee)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.s_delayfee!=null">
						<s:set var="s_delayfee" value="#dataMap.s_delayfee+#s_delayfee"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.todaybalance)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.todaybalance!=null">
						<s:set var="todaybalance" value="#dataMap.todaybalance+#todaybalance"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.margin)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>&nbsp;
						<s:if test="#dataMap.margin!=null">
						<s:set var="margin" value="#dataMap.margin+#margin"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:property value="#dataMap.m_riskrate" />&nbsp;
					</td>
				</tr>
			</s:iterator>
			<tr class="report_w14">
				<td>合计：</td>
			   <td align="right">&nbsp;</td>
			   <td align="right"><s:set var="a" value="format(#fundi)"/><fmt:formatNumber value="${a }" pattern="#,##0.00"/></td>
			   <td align="right"><s:set var="a" value="format(#fundo)"/><fmt:formatNumber value="${a }" pattern="#,##0.00"/></td>
			   <td align="right"><s:set var="a" value="format(#tradefee)"/><fmt:formatNumber value="${a }" pattern="#,##0.00"/></td>
			   <td align="right"><s:set var="a" value="format(#closepl)"/><fmt:formatNumber value="${a }" pattern="#,##0.00"/></td>
			   <td align="right"><s:set var="a" value="format(#holdpl)"/><fmt:formatNumber value="${a }" pattern="#,##0.00"/></td>
			   <td align="right"><s:set var="a" value="format(#m_tradefee)"/><fmt:formatNumber value="${a }" pattern="#,##0.00"/></td>
			   <td align="right"><s:set var="a" value="format(#s_tradefee)"/><fmt:formatNumber value="${a }" pattern="#,##0.00"/></td>
			   <td align="right"><s:set var="a" value="format(#m_delayfee)"/><fmt:formatNumber value="${a }" pattern="#,##0.00"/></td>
			   <td align="right"><s:set var="a" value="format(#s_delayfee)"/><fmt:formatNumber value="${a }" pattern="#,##0.00"/></td>
			   <td align="right">&nbsp;</td>
			   <td align="right"><s:set var="a" value="format(#margin)"/><fmt:formatNumber value="${a }" pattern="#,##0.00"/></td>
			   <td>&nbsp;</td>
			</tr>
		</table>
		<br><br>
	</body>
</html>
