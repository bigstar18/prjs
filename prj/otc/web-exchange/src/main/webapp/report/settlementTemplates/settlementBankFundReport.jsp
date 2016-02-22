<%@ page pageEncoding="GBK"%>
<%@ include file="/public/reportCss.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
		<div class="report_w16_titlemin">
			贵金属交易所
		</div>
		<div class="report_w16_title">
			交易银行资金结算表
		</div>
		<table width="150%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td>
					<table width="40%" border="0" align="left" cellpadding="0"
						cellspacing="0" bordercolor="#000000"
						style='border-collapse: collapse; padding-left: 20px;' height="20">
						<tr>
							<td align="center">
								操作人：${CURRENUSERID }
							</td>
							<%
								java.util.Date d = new java.util.Date();
								java.text.SimpleDateFormat dformat = new java.text.SimpleDateFormat(
										"yyyy-MM-dd");
								String datetime = dformat.format(d);
							%>

							<td align="center">
								查询日期：<%=datetime%>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table width="100%" border="1" align="center" cellpadding="0"
						cellspacing="0" bordercolor="#000000"
						style='border-collapse: collapse;'>
						<s:set var="transferfund" value="0"></s:set>
						<s:set var="fundio" value="0"></s:set>
						<s:set var="tradediff" value="0"></s:set>
						<s:set var="todaybalance" value="0"></s:set>
						<s:set var="margin" value="0"></s:set>
						<s:set var="capital" value="0"></s:set>
						<s:set var="num" value="1"></s:set>
						<tr class="report_w14">
							<td width="5%">
								序号
							</td>
							<td width="9%">
								结算日期
							</td>
							<td width="11%">
								银行
							</td>
							<td width="11%">
								出入金
							</td>
							<td width="10%">
								主次转账
							</td>
							<td width="11%">
								交易变动
							</td>

							<td width="11%">
								当日余额
							</td>
							<td width="11%">
								当日保证金
							</td>
							<td width="10%">
								当日权益
							</td>
						</tr>
						<s:iterator value="dataList()" var="dataMap">
							<tr class="report_w14_neirong">
								<td>
									<s:property value="#num" />
									<s:set var="num" value="#num+1"></s:set>
								</td>
								<td>
									<s:date name="#dataMap.b_date" format="yyyy-MM-dd"></s:date>
									&nbsp;
								</td>
								<td>
									<s:property value="#dataMap.bankName" />
									&nbsp;
								</td>
								<td align="right">
									<s:set var="a" value="#dataMap.fundio" />
									<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
									<s:if test="#dataMap.fundio!=null">
										<s:set var="fundio" value="#fundilo+#dataMap.fundio"></s:set>
									</s:if>
								</td>
								<td align="right">
									<s:set var="a" value="#dataMap.transferfund" />
									<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
									<s:if test="#dataMap.transferfund!=null">
										<s:set var="transferfund" value="#transferfund+#dataMap.transferfund"></s:set>
									</s:if>
								</td>
								<td align="right">
									<s:set var="a" value="#dataMap.tradediff" />
									<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
									<s:if test="#dataMap.tradediff!=null">
										<s:set var="tradediff" value="#tradediff+#dataMap.tradediff"></s:set>
									</s:if>
								</td>

								<td align="right">
									<s:set var="a" value="#dataMap.todaybalance" />
									<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
									<s:if test="#dataMap.todaybalance!=null">
										<s:set var="todaybalance" value="#todaybalance+#dataMap.todaybalance"></s:set>
									</s:if>
								</td>
								<td align="right">
									<s:set var="a" value="#dataMap.margin" />
									<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
									<s:if test="#dataMap.margin!=null">
										<s:set var="margin" value="#margin+#dataMap.margin"></s:set>
									</s:if>
								</td>
								<td  align="right">
									<s:property value="#dataMap.capital" />
							</tr>
						</s:iterator>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
