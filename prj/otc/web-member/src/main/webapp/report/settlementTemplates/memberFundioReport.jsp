<%@ page pageEncoding="GBK"%>
<%@ include file="/public/reportCss.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
		<br>
		<div class="report_w16_titlemin">
			贵金属交易所
		</div>
		<div class="report_w16_title">
			会员出入金保证金明细表
		</div>
		<table width="60%" border="0" align="center" cellpadding="0"
			cellspacing="0" bordercolor="#000000"
			style='border-collapse: collapse;' height="26">
			<tr>
				<td align="left">
					开始日期：${oldParams["trunc(primary.cleardate)[>=][date]"]}
				</td>
				<td align="left">
					结束日期：${oldParams["trunc(primary.cleardate)[<=][date]"]}
				</td>
				<td align="left">
					操作人：${CURRENUSERID }
				</td>
			</tr>
		</table>
		<table width="80%" border="1" align="center" cellpadding="0"
			cellspacing="0" bordercolor="#000000"
			style='table-layout: fixed; border-collapse: collapse;'
			bgcolor="#ffbfc9" height="26">
			<s:set var="num" value="1"></s:set>
			<s:set var="fundin" value="0"></s:set>
			<s:set var="fundout" value="0"></s:set>
			<s:set var="MemberFee" value="0"></s:set>
			<s:set var="todaybalance" value="0"></s:set>
			<tr class="report_w14">
				<td width="8%">
					序号
				</td>
				<td width="15%">
					结算日期
				</td>
				<td width="15%">
					当日入金
				</td>
				<td width="15%">
					当日出金
				</td>
				<td width="10%">
					收手续费
				</td>
				<td width="10%">
					余额
				</td>
			</tr>
			<s:iterator value="dataList()" var="dataMap">
				<tr>
					<td>
						<s:property value="#num" />
						<s:set var="num" value="#num+1"></s:set>
					</td>
					<td>
						<s:date name="#dataMap.cleardate" format="yyyy-MM-dd" />
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.fundin)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.fundin!=null">
							<s:set var="fundin" value="#dataMap.fundin+#fundin"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.fundout)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.fundout!=null">
							<s:set var="fundout" value="#dataMap.fundout+#fundout"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.MemberFee)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.MemberFee!=null">
							<s:set var="MemberFee" value="#dataMap.MemberFee+#MemberFee"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.todaybalance)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.todaybalance!=null">
							<s:set var="todaybalance"
								value="#dataMap.todaybalance+#todaybalance"></s:set>
						</s:if>
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
				<td align="right">
					<s:set var="a" value="format(#fundin)" />
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set var="a" value="format(#fundout)" />
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set var="a" value="format(#MemberFee)" />
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set var="a" value="format(#todaybalance)" />
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
			</tr>
		</table>
		<br>
		<br>
		<br>
	</body>
</html>
