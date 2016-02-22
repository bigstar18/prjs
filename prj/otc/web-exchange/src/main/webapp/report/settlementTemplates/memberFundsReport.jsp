<%@ page pageEncoding="GBK"%>
<%@ include file="/public/reportCss.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
		<div class="report_w16_titlemin">贵金属交易所</div>
		<div class="report_w16_title">会员资金状况表</div>
<table width="300%" border="0" align="center" cellpadding="0" cellspacing="0">
<tr>
  <td>
		<table width="40%" border="0" align="left" cellpadding="0"
			cellspacing="0" bordercolor="#000000"
			style='border-collapse: collapse;padding-left: 20px;' height="20">
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
		</td>
		</tr>
		<tr>
		<td>
		<table width="100%" border="1" align="center" cellpadding="0"
			cellspacing="0" bordercolor="#000000"
			style='table-layout: fixed; border-collapse: collapse;'>
			<s:set var="num" value="1"></s:set>
			<s:set var="begincapital" value="0"></s:set>
			<s:set var="fundio" value="0"></s:set>
			<s:set var="memberfee" value="0"></s:set>
			<s:set var="mktfee" value="0"></s:set>
			<s:set var="customerfee" value="0"></s:set>
			<s:set var="customercloseplsum" value="0"></s:set>
			<s:set var="memberclosepl" value="0"></s:set>
			<s:set var="closeplsum" value="0"></s:set>
			<s:set var="customerholdpl" value="0"></s:set>
			<s:set var="memberholdpl" value="0"></s:set>
			<s:set var="holdplsum" value="0"></s:set>
			<s:set var="customerdelayfee" value="0"></s:set>
			<s:set var="asmemberdelayfee" value="0"></s:set>
			<s:set var="delayfeesum" value="0"></s:set>
			<s:set var="endcapital" value="0"></s:set>
			<tr class="report_w14">
				<td width="3%" rowspan="2">
					序号
				</td>
				<td width="5%" rowspan="2">
					结算日期
				</td>
				<td width="4%" rowspan="2">
					会员编号
				</td>
				<td width="6%" rowspan="2">
					会员名称
				</td>
				<td width="7%" rowspan="2">
					期初权益
				</td>
				<td width="7%" rowspan="2">
					期末权益
				</td>
				<td width="7%" rowspan="2">
					出入金
				</td>
				<td width="6%" rowspan="2">
					会员留存手续费
				</td>
				<td width="7%" rowspan="2">
					交易所留存手续费
				</td>
				<td width="4%" rowspan="2">
					客户手续费
				</td>
				<td width="15%" colspan="3">
					平仓盈亏
				</td>
				<td width="12%" colspan="3">
					持仓盈亏
				</td>
				<td width="12%" colspan="3">
					延期费
				</td>
				<td width="4%" rowspan="2">
					风险率
				</td>
			</tr>
			<tr class="report_w14">
				<td width="5%" align="center">
					客户
				</td>
				<td width="4%" align="center">
					会员
				</td>
				<td width="4%" align="center">
					净盈亏
				</td>
				<td width="5%" align="center">
					客户
				</td>
				<td width="4%" align="center">
					会员
				</td>
				<td width="4%" align="center">
					净盈亏
				</td>
				<td width="5%" align="center">
					客户
				</td>
				<td width="4%" align="center">
					会员
				</td>
				<td width="4%" align="center">
					差额
				</td>
			</tr>
			<s:iterator value="dataList()" var="dataMap">
				<tr class="report_w14_neirong">
					<td>
						<s:property value="#num" />
						<s:set var="num" value="#num+1"></s:set>
					</td>
					<td>
						<s:date name="#dataMap.cleardate" format="yyyy-MM-dd" />
					</td>
					<td>
						<s:property value="#dataMap.memberno" />&nbsp;
					</td>
					<td>
						<s:property value="#dataMap.membername" />
					</td>
					<td align="right">
						<s:set value="format(#dataMap.begincapital)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.begincapital!=null">
							<s:set var="begincapital"
								value="#dataMap.begincapital+#begincapital"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.endcapital)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.endcapital!=null">
							<s:set var="endcapital" value="#dataMap.endcapital+#endcapital"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.fundio)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.fundio!=null">
							<s:set var="fundio" value="#dataMap.fundio+#fundio"></s:set>
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
						<s:set value="format(#dataMap.customerfee)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.customerfee!=null">
							<s:set var="customerfee"
								value="#dataMap.customerfee+#customerfee"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.customercloseplsum)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.customercloseplsum!=null">
							<s:set var="customercloseplsum"
								value="#dataMap.customercloseplsum+#customercloseplsum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.memberclosepl)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.memberclosepl!=null">
							<s:set var="memberclosepl"
								value="#dataMap.memberclosepl+#memberclosepl"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.closeplsum)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.closeplsum!=null">
							<s:set var="closeplsum" value="#dataMap.closeplsum+#closeplsum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.customerholdpl)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.customerholdpl!=null">
							<s:set var="customerholdpl"
								value="#dataMap.customerholdpl+#customerholdpl"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.memberholdpl)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.memberholdpl!=null">
							<s:set var="memberholdpl"
								value="#dataMap.memberholdpl+#memberholdpl"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.holdplsum)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.holdplsum!=null">
							<s:set var="holdplsum" value="#dataMap.holdplsum+#holdplsum"></s:set>
						</s:if>
					</td>

					<td align="right">
						<s:set value="format(#dataMap.customerdelayfee)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.customerdelayfee!=null">
							<s:set var="customerdelayfee"
								value="#dataMap.customerdelayfee+#customerdelayfee"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.asmemberdelayfee)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.asmemberdelayfee!=null">
							<s:set var="asmemberdelayfee"
								value="#dataMap.asmemberdelayfee+#asmemberdelayfee"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.delayfeesum)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.delayfeesum!=null">
							<s:set var="delayfeesum"
								value="#dataMap.delayfeesum+#delayfeesum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:property value="format(#dataMap.risk)" />
						%
					</td>
				</tr>
			</s:iterator>
			<tr class="report_w14">
				<td>
					合计：
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
				<td >
					&nbsp;
				</td>
				<td >
					&nbsp;
				</td>
				<td align="right">
					<s:set value="format(#fundio)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set value="format(#memberfee)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set value="format(#mktfee)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set value="format(#customerfee)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set value="format(#customercloseplsum)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set value="format(#memberclosepl)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set value="format(#closeplsum)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set value="format(#customerholdpl)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set value="format(#memberholdpl)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set value="format(#holdplsum)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set value="format(#customerdelayfee)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set value="format(#asmemberdelayfee)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set value="format(#delayfeesum)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td>
					&nbsp;
				</td>
			</tr>
		</table>
		</td>
		</tr>
		</table>
	</body>
</html>
