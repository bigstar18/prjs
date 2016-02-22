<%@ page pageEncoding="GBK"%>
<%@ include file="/public/reportCss.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
		<div class="report_w16_titlemin">贵金属交易所</div>
		<div class="report_w16_title">会员交易统计表</div>
<table width="280%" border="0" align="center" cellpadding="0" cellspacing="0">
<tr>
  <td>
		<table width="35%" border="0" align="left" cellpadding="0"
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
			style='table-layout: fixed; border-collapse: collapse;'
			height="26">
			<tr class="report_w14">
				<td width="3%" rowspan="2">
					序号
				</td>
				<td width="4%" rowspan="2">
					结算日期
				</td>
				<td width="4%" rowspan="2">
					商品
				</td>
				<td width="3%" rowspan="2">
					会员编号
				</td>
				<td width="4%" rowspan="2">
					会员名称
				</td>
				<td width="9%" colspan="3">
					成交量
				</td>
				<td width="15%" colspan="3">
					成交额
				</td>
				<td width="13%" colspan="3">
					平仓盈亏
				</td>
				<td width="13%" colspan="3">
					持仓盈亏
				</td>
				<td width="13%" colspan="3">
					手续费
				</td>
				<td width="13%" colspan="3">
					延期费
				</td>
			</tr>
			<s:set var="num" value="0"></s:set>
			<s:set var="customerfundsum" value="0"></s:set>
			<s:set var="memberfundsum" value="0"></s:set>
			<s:set var="fundsum" value="0"></s:set>
			<s:set var="customercloseplsum" value="0"></s:set>
			<s:set var="memberclosepl" value="0"></s:set>
			<s:set var="closeplsum" value="0"></s:set>
			<s:set var="customerholdpl" value="0"></s:set>
			<s:set var="memberholdpl" value="0"></s:set>
			<s:set var="holdplsum" value="0"></s:set>
			<s:set var="mktfee" value="0"></s:set>
			<s:set var="memberfee" value="0"></s:set>
			<s:set var="customerfee" value="0"></s:set>
			<s:set var="asmemberdelayfee" value="0"></s:set>
			<s:set var="customerdelayfee" value="0"></s:set>
			<s:set var="membernetdelayfee" value="0"></s:set>
			<s:set var="customerqtysum" value="0"></s:set>
			<s:set var="memberqtysum" value="0"></s:set>
			<s:set var="qtysum" value="0"></s:set>
			<tr class="report_w14">
				<td width="4%">
					客户
				</td>
				<td width="4%">
					会员
				</td>
				<td width="4%">
					总计
				</td>
				<td width="4%">
					客户
				</td>
				<td width="4%">
					会员
				</td>
				<td width="4%">
					总计
				</td>
				<td width="4%">
					客户
				</td>
				<td width="4%">
					会员
				</td>
				<td width="4%">
					总计
				</td>
				<td width="4%">
					客户
				</td>
				<td width="4%">
					会员
				</td>
				<td width="4%">
					总计
				</td>
				<td width="4%">
					交易所留存
				</td>
				<td width="4%">
					会员留存
				</td>
				<td width="4%">
					收客户
				</td>
				<td width="4%">
					交特别会员
				</td>
				<td width="4%">
					收客户
				</td>
				<td width="4%">
					会员留存
				</td>
			</tr>
			<s:iterator value="dataList()" var="dataMap">
				<tr class="report_w14_neirong">
					<td>
						<s:set var="num" value="#num+1"></s:set>
						<s:property value="#num" />
					</td>
					<td>
						<s:date name="#dataMap.clearDate" format="yyyy-MM-dd" />
					</td>
					<td>
						<s:property value="#dataMap.commodityName" />
					</td>
					<td>
						<s:property value="#dataMap.memberNo" />
						&nbsp;
					</td>
					<td>
						<s:property value="#dataMap.memberName" />
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
						<s:set value="format(#dataMap.fundsum)" var="a" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.fundsum!=null">
							<s:set var="fundsum" value="#dataMap.fundsum+#fundsum"></s:set>
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
						<s:set value="format(#dataMap.memberclosepl)" var="a" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.memberclosepl!=null">
							<s:set var="memberclosepl"
								value="#dataMap.memberclosepl+#memberclosepl"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.closeplsum)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.closeplsum">
							<s:set var="closeplsum" value="#dataMap.closeplsum+#closeplsum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.customerholdpl)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.customerholdpl">
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
						<s:set value="format(#dataMap.mktfee)" var="a" />
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
						<s:set value="format(#dataMap.asmemberdelayfee)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.asmemberdelayfee!=null">
							<s:set var="asmemberdelayfee"
								value="#dataMap.asmemberdelayfee+#asmemberdelayfee"></s:set>
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
						<s:set value="format(#dataMap.membernetdelayfee)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.membernetdelayfee!=null">
							<s:set var="membernetdelayfee"
								value="#dataMap.membernetdelayfee+#membernetdelayfee"></s:set>
						</s:if>
					</td>
				</tr>
			</s:iterator>
			<tr class="report_w14">
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
					<s:set value="format(#asmemberdelayfee)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set value="format(#customerdelayfee)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set value="format(#membernetdelayfee)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
			</tr>
		</table>
		</td>
		</tr>
		</table>
	</body>
</html>
