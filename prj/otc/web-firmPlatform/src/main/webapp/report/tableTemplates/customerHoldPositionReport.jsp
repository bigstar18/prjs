<%@ page pageEncoding="GBK"%>
<%@ include file="/public/reportCss.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
		<br>
		<div class="report_w16_title">
			持仓明细
		</div>
		<table width="200%" border="1" align="center" cellpadding="0"
			cellspacing="0" bordercolor="#000000"
			style='border-collapse: collapse;' bgcolor="#eeeeee" height="8">
			<s:set var="num" value="1"></s:set>
			<s:set var="holdqty" value="0"></s:set>
			<s:set var="floatingloss" value="0"></s:set>
			<s:set var="holdmargin" value="0"></s:set>
			<s:set var="delayfee" value="0"></s:set>
			</td>
			<tr class="report_w14">
				<td width="4%">
					序号
				</td>
				<td width="5%">
					结算日期
				</td>
				<td width="8%">
					商品
				</td>
				<td width="7%">
					持仓单号
				</td>
				<td width="7%">
					持仓量
				</td>
				<td width="7%">
					持仓价
				</td>
				<td width="7%">
					建仓价
				</td>
				<td width="7%">
					结算价
				</td>
				<td width="8%">
					持仓盈亏
				</td>
				<td width="8%">
					占用保证金
				</td>
				<td width="7%">
					延期费
				</td>
				<td width="7%">
					建仓时间
				</td>
				<td width="7%">
					建仓单号
				</td>
				<td width="8%">
					买卖方向
				</td>
			</tr>
			<s:iterator value="dataList()" var="dataMap">
				<tr class="report_w14_tdheight">
					<td>
						<s:property value="#num" />
						<s:set var="num" value="#num+1"></s:set>
					</td>
					<td>
						<s:date name="#dataMap.clearDate" format="yyyy-MM-dd"/>
					</td>
					<td>
						<s:property value="#dataMap.commodityName" />
					</td>
					<td align="right">
						<s:property value="#dataMap.holdno" />&nbsp;
					</td>
					<td align="right">
						<s:set var="a" value="#dataMap.holdqty" />
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.holdqty!=null">
							<s:set var="holdqty" value="#dataMap.holdqty+#holdqty"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.holdprice)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.openprice)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.clearprice)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.floatingloss)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.floatingloss!=null">
							<s:set var="floatingloss"
								value="#dataMap.floatingloss+#floatingloss"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.holdmargin)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.holdmargin!=null">
							<s:set var="holdmargin" value="#dataMap.holdmargin+#holdmargin"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.delayfee)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.delayfee!=null">
							<s:set var="delayfee" value="#dataMap.delayfee+#delayfee"></s:set>
						</s:if>
					</td>
					<td>
						<s:date name="#dataMap.holdTime" format="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>
						<s:property value="#dataMap.opentradeno" />&nbsp;
					</td>
					<td>
						<s:set var="bs" value="#dataMap.bs_flag" />
						<s:property value="flagMap[#bs]" />
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
					<td align="right">
						<s:set var="a" value="#holdqty" />
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
					</td>
					<td align="right">
						&nbsp;
					</td>
					<td align="right">
					&nbsp;
					</td>
					<td align="right">
						&nbsp;
					</td>
					<td align="right">
						<s:set var="a" value="format(#floatingloss)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
					</td>
					<td align="right">
						<s:set var="a" value="format(#holdmargin)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
					</td>
					<td align="right">
						<s:set var="a" value="format(#delayfee)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
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
	</body>
</html>
