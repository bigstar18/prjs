<%@ page pageEncoding="GBK"%>
<%@ include file="/public/reportCss.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
		<br>
		<br>
		<div class="report_w16_title">
			资金流水明细
		</div>
		<table width="180%" border="1" align="center" cellpadding="0"
			cellspacing="0" bordercolor="#000000"
			style='border-collapse: collapse;'>
			<s:set var="num" value="1"></s:set>
			<s:set var="amount" value="0"></s:set>
			<s:set var="balance" value="0"></s:set>
			<tr class="report_w14">
				<td width="3%">
					序号
				</td>
				<td width="12%">
					变动金额
				</td>
				<td width="12%">
					变后金额
				</td>
				<td width="12%">
					关联单号
				</td>
				<td width="12%">
					类型
				</td>
				<td width="12%">
					时间
				</td>
				<td width="12%">
					备注
				</td>
			</tr>
			<s:iterator value="dataList()" var="dataMap">
				<tr class="report_w14_neirong">
					<td>
						<s:property value="#num" />
						<s:set var="num" value="#num+1"></s:set>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.amount)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.amount!=null">
							<s:set var="amount" value="#dataMap.amount+#amount"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.balance)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.balance!=null">
							<s:set var="balance" value="#dataMap.balance+#balance"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:property value="#dataMap.voucherno" />&nbsp;
					</td>
					<td>
						<s:set var="mapkey" value="#dataMap.oprcode" ></s:set>
						<s:property value="codeMap[#mapkey]"/>
					</td>
					<td>
						<s:date name="#dataMap.createtime" format="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
			</s:iterator>
			<tr class="report_w14">
				<td>
					总计：
				</td>
				<td align="right">
					<s:set var="a" value="format(#amount)" />
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					&nbsp;
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
				<td>
					&nbsp;
				</td>
			</tr>
		</table>
		<br>
		<br>
	</body>
</html>
