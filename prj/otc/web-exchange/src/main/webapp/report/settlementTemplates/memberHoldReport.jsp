<%@ page pageEncoding="GBK"%>
<%@ include file="/public/reportCss.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
		<div class="report_w16_titlemin">贵金属交易所</div>
		<div class="report_w16_title">会员持仓汇总表</div>
<table width="200%" border="0" align="center" cellpadding="0" cellspacing="0">
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
			<s:set var="customerbuyqty" value="0"></s:set>
			<s:set var="customersellqty" value="0"></s:set>
			<s:set var="customernetqty" value="0"></s:set>
			<s:set var="memberbuyqty" value="0"></s:set>
			<s:set var="membersellqty" value="0"></s:set>
			<s:set var="membernetqty" value="0"></s:set>
			<s:set var="netqty" value="0"></s:set>
			<s:set var="maxnethold" value="0"></s:set>
			<s:set var="memberdelayfee" value="0"></s:set>
			<s:set var="customerdelayfee" value="0"></s:set>
			<s:set var="delayfee" value="0"></s:set>
			<s:set var="capital" value="0"></s:set>
			<s:set var="num" value="1"></s:set>
			<tr class="report_w14">
				<td width="4%" rowspan="2">
					序号
				</td>
				<td width="6%" rowspan="2">
					结算日期
				</td>
				<td width="7%" rowspan="2">
					会员编号
				</td>
				<td width="7%" rowspan="2">
					会员名称
				</td>
				<td width="6%" rowspan="2">
					商品
				</td>
				<td width="15%" colspan="3">
					客户
				</td>
				<td width="15%" colspan="3">
					会员
				</td>
				<td width="7%" rowspan="2">
					净持仓合计
				</td>
				<td width="5%" rowspan="2">
					限制量
				</td>
				<td width="20%" colspan="3">
					延期费
				</td>
				<td width="8%" rowspan="2">
					期末权益
				</td>
			</tr>
			<tr class="report_w14">
				<td>
					买持仓
				</td>
				<td>
					卖持仓
				</td>
				<td>
					净持仓
				</td>
				<td>
					买持仓
				</td>
				<td>
					卖持仓
				</td>
				<td>
					净持仓
				</td>
				<td>
					交特别会员
				</td>
				<td>
					收客户
				</td>
				<td>
					综合会员留存
				</td>
			</tr>
			<s:iterator value="dataList()" var="dataMap">
				<tr class="report_w14_neirong">
					<td>
						<s:property value="#num" />
						<s:set var="num" value="#num+1"></s:set>
					</td>
					<td>
						<s:date name="#dataMap.clearDate" format="yyyy-MM-dd"></s:date>
					</td>
					<td>
						<s:property value="#dataMap.memberno" />&nbsp;
					</td>
					<td>
						<s:property value="#dataMap.membername" />
					</td>
					<td>
						<s:property value="#dataMap.commodityname" />
					</td>
					<td align="right">
						<s:set value="#dataMap.customerbuyqty" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.customerbuyqty!=null">
							<s:set var="customerbuyqty"
								value="#dataMap.customerbuyqty+#customerbuyqty"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="#dataMap.customersellqty" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.customersellqty!=null">
							<s:set var="customersellqty"
								value="#dataMap.customersellqty+#customersellqty"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="#dataMap.customernetqty" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.customernetqty!=null">
							<s:set var="customernetqty"
								value="#dataMap.customernetqty+#customernetqty"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="#dataMap.memberbuyqty" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.memberbuyqty!=null">
							<s:set var="memberbuyqty"
								value="#dataMap.memberbuyqty+#memberbuyqty"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="#dataMap.membersellqty" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.membersellqty!=null">
							<s:set var="membersellqty"
								value="#dataMap.membersellqty+#membersellqty"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="#dataMap.membernetqty" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.membernetqty!=null">
							<s:set var="membernetqty"
								value="#dataMap.membernetqty+#membernetqty"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="#dataMap.netqty" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.netqty!=null">
							<s:set var="netqty" value="#dataMap.netqty+#netqty"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="#dataMap.maxnethold" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.maxnethold!=null">
							<s:set var="maxnethold" value="#dataMap.maxnethold+#maxnethold"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.memberdelayfee)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.memberdelayfee!=null">
							<s:set var="memberdelayfee"
								value="#dataMap.memberdelayfee+#memberdelayfee"></s:set>
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
						<s:set value="format(#dataMap.delayfee)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.delayfee!=null">
							<s:set var="delayfee" value="#dataMap.delayfee+#delayfee"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.capital)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.capital!=null">
							<s:set var="capital" value="#dataMap.capital+#capital"></s:set>
						</s:if>
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
				<td>
					&nbsp;
				</td>
				<td align="right">
					<s:set value="#customerbuyqty" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td align="right">
					<s:set value="#customersellqty" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td align="right">
					<s:set value="#customernetqty" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td align="right">
					<s:set value="#memberbuyqty" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td align="right">
					<s:set value="#membersellqty" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td align="right">
					<s:set value="#membernetqty" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td align="right">
					<s:set value="#netqty" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td align="right">
					<s:set value="#maxnethold" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td align="right">
					<s:set value="format(#memberdelayfee)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set value="format(#customerdelayfee)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set value="format(#delayfee)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set value="format(#capital)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
			</tr>
		</table>
		</td>
		</tr>
		</table>
	</body>
</html>
