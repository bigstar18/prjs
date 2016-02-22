<%@ page pageEncoding="GBK"%>
<%@ include file="/public/reportCss.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
		<div class="report_w16_titlemin">贵金属交易所</div>
		<div class="report_w16_title">会员成交汇总表</div>
		<table width="200%" border="0" align="center" cellpadding="0" cellspacing="0">
<tr>
  <td>
		<table width="60%" border="0" align="left" cellpadding="0"
			cellspacing="0" bordercolor="#000000"
			style='border-collapse: collapse;padding-left: 20px;' height="20">
			<tr>
				<td align="left">
					会员编号：${memberNo}
				</td>
				<td align="left">
					会员名称：${memberName}
				</td>
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
			<s:set var="num" value="0"></s:set>
			<s:set var="customerqtysum" value="0"></s:set>
			<s:set var="memberqtysum" value="0"></s:set>
			<s:set var="qtysum" value="0"></s:set>
			<s:set var="customerfundsum" value="0"></s:set>
			<s:set var="memberfundsum" value="0"></s:set>
			<s:set var="fundsum" value="0"></s:set>
			<s:set var="mktfee" value="0"></s:set>
			<s:set var="memberfee" value="0"></s:set>
			<s:set var="customerfee" value="0"></s:set>
			<s:set var="closeplsum" value="0"></s:set>
			<s:set var="holdplsum" value="0"></s:set>
			<s:set var="plsum" value="0"></s:set>
			<s:set var="endcapital" value="0"></s:set>
			<s:set var="membercloseplsum" value="0"></s:set>
			<s:set var="memberholdplsum" value="0"></s:set>
			<s:set var="memberplsum" value="0"></s:set>
			<tr class="report_w14">
				<td width="5%" rowspan="2">
					序号
				</td>
				<td width="4%" rowspan="2">
					结算日期
				</td>
				<td width="3%" rowspan="2">
					商品
				</td>
				<td width="12%" colspan="3">
					成交量
				</td>
				<td width="18%" colspan="3">
					成交金额
				</td>
				<td width="17%" colspan="3">
					手续费
				</td>
				<td width="15%" colspan="3">
					客户交易盈亏
				</td>
				<td width="15%" colspan="3">
					会员交易盈亏
				</td>
			</tr>
			<tr class="report_w14">
				<td width="5%">
					客户
				</td>
				<td width="5%">
					会员
				</td>
				<td width="5%">
					合计
				</td>
				<td width="5%">
					客户
				</td>
				<td width="5%">
					会员
				</td>
				<td width="7%">
					合计
				</td>
				<td width="7%">
					交易所留存
				</td>
				<td width="6%">
					会员留存
				</td>
				<td width="4%">
					收客户
				</td>
				<td width="6%">
					平仓盈亏
				</td>
				<td width="6%">
					持仓盈亏
				</td>
				<td width="6%">
					合计
				</td>
				<td width="6%">
					平仓盈亏
				</td>
				<td width="6%">
					持仓盈亏
				</td>
				<td width="6%">
					合计
				</td>
			</tr>
			<s:iterator value="dataList()" var="dataMap">
				<tr class="report_w14_neirong">
					<td>
						<s:set var="num" value="#num+1"></s:set>
						<s:property value="#num" />
					</td>
					<td>
						<s:date name="#dataMap.cleardate" format="yyyy-MM-dd" />
					</td>
					<td>
						<s:property value="#dataMap.commodityname" />
					</td>
					<td align="right">
						<s:set var="a" value="#dataMap.customerqtysum" />
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.customerqtysum!=null">
							<s:set var="customerqtysum"
								value="#dataMap.customerqtysum+#customerqtysum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="#dataMap.memberqtysum" />
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.memberqtysum!=null">
							<s:set var="memberqtysum"
								value="#dataMap.memberqtysum+#memberqtysum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="#dataMap.qtysum" />
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.qtysum!=null">
							<s:set var="qtysum" value="#dataMap.qtysum+#qtysum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.customerfundsum)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.customerfundsum!=null">
							<s:set var="customerfundsum"
								value="#dataMap.customerfundsum+#customerfundsum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.memberfundsum)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.memberfundsum!=null">
							<s:set var="memberfundsum"
								value="#dataMap.memberfundsum+#memberfundsum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.fundsum)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.fundsum!=null">
							<s:set var="fundsum" value="#dataMap.fundsum+#fundsum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.mktfee)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.mktfee!=null">
							<s:set var="mktfee" value="#dataMap.mktfee+#mktfee"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.memberfee)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.memberfee!=null">
							<s:set var="memberfee" value="#dataMap.memberfee+#memberfee"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.customerfee)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.customerfee!=null">
							<s:set var="customerfee"
								value="#dataMap.customerfee+#customerfee"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.customercloseplsum)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.customercloseplsum!=null">
							<s:set var="closeplsum" value="#dataMap.customercloseplsum+#closeplsum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.customerholdpl)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.customerholdpl!=null">
							<s:set var="holdplsum" value="#dataMap.customerholdpl+#holdplsum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.customertotalpl)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.customertotalpl!=null">
							<s:set var="plsum" value="#dataMap.customertotalpl+#plsum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.memberclosepl)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.memberclosepl!=null">
							<s:set var="membercloseplsum" value="#dataMap.memberclosepl+#membercloseplsum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.memberholdpl)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.memberholdpl!=null">
							<s:set var="memberholdplsum" value="#dataMap.memberholdpl+#memberholdplsum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.membertotalpl)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.membertotalpl!=null">
							<s:set var="memberplsum" value="#dataMap.memberplsum+#membertotalpl"></s:set>
						</s:if>
					</td>
				</tr>
			</s:iterator>
			<tr class="report_w14">
				<td>
					总计：
				</td>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
				<td align="right">
					<s:set var="a" value="#customerqtysum" />
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td align="right">
					<s:set var="a" value="#memberqtysum" />
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td align="right">
					<s:set var="a" value="#qtysum" />
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td align="right">
					<s:set var="a" value="format(#customerfundsum)" />
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set var="a" value="format(#memberfundsum)" />
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set var="a" value="format(#fundsum)" />
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set var="a" value="format(#mktfee)" />
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set var="a" value="format(#memberfee)" />
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set var="a" value="format(#customerfee)" />
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set var="a" value="format(#closeplsum)" />
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set var="a" value="format(#holdplsum)" />
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set var="a" value="format(#plsum)" />
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set var="a" value="format(#membercloseplsum)" />
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set var="a" value="format(#memberholdplsum)" />
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set var="a" value="format(#memberplsum)" />
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
			</tr>
		</table>
	</body>
</html>