<%@ page pageEncoding="GBK"%>
<%@ include file="/public/reportCss.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
		<div class="report_w16_titlemin">贵金属交易所</div>
		<div class="report_w16_title">会员成交汇总表</div>
<table width="280%" border="0" align="center" cellpadding="0" cellspacing="0">
<tr>
  <td>
		<table width="30%" border="0" align="left" cellpadding="0"
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
			<s:set var="membercloseplsum" value="0"></s:set>
			<s:set var="memberholdplsum" value="0"></s:set>
			<s:set var="memberplsum" value="0"></s:set>
			<s:set var="endcapital" value="0"></s:set>
			<tr class="report_w14">
				<td width="3%" rowspan="2">
					序号
				</td>
				<td width="4%" rowspan="2">
					结算日期
				</td>
				<td width="4%" rowspan="2">
					会员编号
				</td>
				<td width="5%" rowspan="2">
					会员名称
				</td>
				<td width="4%" rowspan="2">
					商品
				</td>
				<td width="10%" colspan="3">
					成交量
				</td>
				<td width="18%" colspan="3">
					成交金额
				</td>
				<td width="13%" colspan="3">
					手续费
				</td>
				<td width="13%" colspan="3">
					客户交易盈亏
				</td>
				<td width="15%" colspan="3">
					会员交易盈亏
				</td>
				<td width="7%" rowspan="2">
					期末权益
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
				<td width="4%">
					客户
				</td>
				<td width="4%">
					会员
				</td>
				<td width="4%">
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
						<s:property value="#dataMap.memberno" />&nbsp;
					</td>
					<td>
						<s:property value="#dataMap.memberName" />
					</td>
					<td>
						<s:property value="#dataMap.commodityname" />
					</td>
					<td align="right">
						<s:set value="#dataMap.customerqtysum" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.customerqtysum!=null">
							<s:set var="customerqtysum"
								value="#dataMap.customerqtysum+#customerqtysum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="#dataMap.memberqtysum" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.memberqtysum!=null">
							<s:set var="memberqtysum"
								value="#dataMap.memberqtysum+#memberqtysum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="#dataMap.qtysum" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.qtysum!=null">
							<s:set var="qtysum" value="#dataMap.qtysum+#qtysum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.customerfundsum)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.customerfundsum!=null">
							<s:set var="customerfundsum"
								value="#dataMap.customerfundsum+#customerfundsum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.memberfundsum)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.memberfundsum!=null">
							<s:set var="memberfundsum"
								value="#dataMap.memberfundsum+#memberfundsum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.fundsum)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.fundsum!=null">
							<s:set var="fundsum" value="#dataMap.fundsum+#fundsum"></s:set>
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
						<s:set value="format(#dataMap.memberfee)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.memberfee!=null">
							<s:set var="memberfee" value="#dataMap.memberfee+#memberfee"></s:set>
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
							<s:set var="closeplsum" value="#dataMap.customercloseplsum+#closeplsum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.customerholdpl)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.customerholdpl!=null">
							<s:set var="holdplsum" value="#dataMap.customerholdpl+#holdplsum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.customertotalpl)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.customertotalpl!=null">
							<s:set var="plsum" value="#dataMap.customertotalpl+#plsum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.memberclosepl)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.memberclosepl!=null">
							<s:set var="membercloseplsum" value="#dataMap.memberclosepl+#membercloseplsum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.memberholdpl)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.memberholdpl!=null">
							<s:set var="memberholdplsum" value="#dataMap.memberholdpl+#memberholdplsum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.membertotalpl)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.membertotalpl!=null">
							<s:set var="memberplsum" value="#dataMap.membertotalpl+#memberplsum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.endcapital)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.endcapital!=null">
							<s:set var="endcapital" value="#dataMap.endcapital+#endcapital"></s:set>
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
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
				<td align="right">
					<s:set value="#customerqtysum" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td align="right">
					<s:set value="#memberqtysum" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td align="right">
					<s:set value="#qtysum" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td align="right">
					<s:set value="format(#customerfundsum)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set value="format(#memberfundsum)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set value="format(#fundsum)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set value="format(#mktfee)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set value="format(#memberfee)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set value="format(#customerfee)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set value="format(#closeplsum)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set value="format(#holdplsum)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set value="format(#plsum)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				
				<td align="right">
					<s:set value="format(#membercloseplsum)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set value="format(#memberholdplsum)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set value="format(#memberplsum)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set value="format(#endcapital)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
			</tr>
		</table>
		</td>
		</tr>
		</table>
	</body>
</html>
