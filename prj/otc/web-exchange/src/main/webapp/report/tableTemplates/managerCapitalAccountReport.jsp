<%@ page pageEncoding="GBK"%>
<%@ include file="/public/reportCss.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
		<br>
		<br>
		<br>
		<div class="report_w16_title">
			资金状况
		</div>
		<table width="100%" border="1" align="center" cellpadding="0"
			cellspacing="0" bordercolor="#000000"
			style='border-collapse: collapse;' bgcolor="#ffbfc9" height="26">
			<s:set var="lastbalance" value="0"></s:set>
			<s:set var="fundi" value="0"></s:set>
			<s:set var="fundo" value="0"></s:set>
			<s:set var="tradefee" value="0"></s:set>
			<s:set var="delayfee" value="0"></s:set>
			<s:set var="closepl" value="0"></s:set>
			<s:set var="holdpl" value="0"></s:set>
			<s:set var="todaybalance" value="0"></s:set>
			<s:set var="margin" value="0"></s:set>
			<s:set var="riskrate" value="0"></s:set>
			<s:set var="num" value="1"></s:set>
			<tr class="report_w14">
				<td width="4%">
					序号
				</td>
				<td width="7%">
					结算日期
				</td>
				<td width="7%">
					交易账号
				</td>
				<td width="9%">
					客户名称
				</td>
				<td width="7%">
					上日余额
				</td>
				<td width="7%">
					入金
				</td>
				<td width="7%">
					出金
				</td>
				<td width="7%">
					手续费
				</td>
				<td width="7%">
					延期费
				</td>
				<td width="8%">
					平仓盈亏
				</td>
				<td width="8%">
					持仓盈亏
				</td>
				<td width="8%">
					本日余额
				</td>
				<td width="8%">
					占用保证金
				</td>
				<td width="6%">
					风险率
				</td>
			</tr>
			<s:iterator value="dataList()" var="dataMap">
				<tr>
					<td>
						<s:property value="#num" />
						<s:set var="num" value="#num+1"></s:set>
					</td>
					<td>
						<s:date name="#dataMap.clearDate" format="yyyy-MM-dd"/>
					</td>
					<td>
						<s:property value="#dataMap.customerno" />&nbsp;
					</td>
					<td>
						<s:property value="#dataMap.customername" />
					</td>
					<td align="right">
						<s:property value="format(#dataMap.lastbalance)" />
						<s:if test="#dataMap.lastbalance!=null">
							<s:set var="lastbalance" value="#dataMap.lastbalance+#lastbalance"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:property value="format(#dataMap.fundi)" />
						<s:if test="#dataMap.fundi!=null">
							<s:set var="fundi" value="#dataMap.fundi+#fundi"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:property value="format(#dataMap.fundo)" />
						<s:if test="#dataMap.fundo!=null">
							<s:set var="fundo" value="#dataMap.fundo+#fundo"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:property value="format(#dataMap.tradefee)" />
						<s:if test="#dataMap.tradefee!=null">
							<s:set var="tradefee" value="#dataMap.tradefee+#tradefee"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:property value="format(#dataMap.delayfee)" />
						<s:if test="#dataMap.delayfee!=null">
							<s:set var="delayfee" value="#dataMap.delayfee+#delayfee"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:property value="format(#dataMap.closepl)" />
						<s:if test="#dataMap.closepl!=null">
							<s:set var="closepl" value="#dataMap.closepl+#closepl"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:property value="format(#dataMap.holdpl)" />
						<s:if test="#dataMap.holdpl!=null">
							<s:set var="holdpl" value="#dataMap.holdpl+#holdpl"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:property value="format(#dataMap.todaybalance)" />
						<s:if test="#dataMap.todaybalance!=null">
							<s:set var="todaybalance" value="#dataMap.todaybalance+#todaybalance"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:property value="format(#dataMap.margin)" />
						<s:if test="#dataMap.margin!=null">
							<s:set var="margin" value="#dataMap.margin+#margin"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:property value="format(#dataMap.c_riskrate)" />
					</td>
				</tr>
			</s:iterator>
			<tr>
					<td>
						合计:
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
					<td align="right">
						<s:property value="format(#lastbalance)" />
					</td>
					<td align="right">
						<s:property value="format(#fundi)" />
					</td>
					<td align="right">
						<s:property value="format(#fundo)" />
					</td>
					<td align="right">
						<s:property value="format(#tradefee)" />
					</td>
					<td align="right">
						<s:property value="format(#delayfee)" />
					</td>
					<td align="right">
						<s:property value="format(#closepl)" />
					</td>
					<td align="right">
						<s:property value="format(#holdpl)" />
					</td>
					<td align="right">
						<s:property value="format(#todaybalance)" />
					</td>
					<td align="right">
						<s:property value="format(#margin)" />
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
		</table>
		<br>
		<br>
		<br>
	</body>
</html>
