<%@ page pageEncoding="GBK"%>
<%@ include file="/public/reportCss.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
		<div class="report_w16_titlemin">贵金属交易所</div>
		<div class="report_w16_title">客户持仓汇总表</div>
		<table width="150%" border="0" align="center" cellpadding="0" cellspacing="0">
<tr>
  <td>
		<table width="45%" border="0" align="left" cellpadding="0"
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
			<s:set var="buyqty" value="0"></s:set>
			<s:set var="sellqty" value="0"></s:set>
			<s:set var="qtysum" value="0"></s:set>
			<s:set var="buydelayfee" value="0"></s:set>
			<s:set var="buymargin" value="0"></s:set>
			<s:set var="endcapital" value="0"></s:set>
			<tr class="report_w14">
				<td width="3%">
					序号
				</td>
				<td width="8%">
					结算日期
				</td>
				<td width="6%">
					机构
				</td>
				<td width="6%">
					机构代码
				</td>
				<td width="9%">
					交易账号
				</td>
				<td width="8%">
					客户名称
				</td>
				<td width="5%">
					商品
				</td>
				<td width="5%">
					买持仓
				</td>
				<td width="5%">
					卖持仓
				</td>
				<td width="5%">
					持仓合计
				</td>
				<td width="8%">
					延期费
				</td>
				<td width="8%">
					占用保证金
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
						<s:property value="#dataMap.organizationname" />
					</td>
					<td>
						<s:property value="#dataMap.organizationno" />
					</td>
					<td>
						<s:property value="#dataMap.customerno" />&nbsp;
					</td>
					<td>
						<s:property value="#dataMap.customername" />
					</td>
					<td>
						<s:property value="#dataMap.commodityname" />
					</td>
					<td align="right">
						<s:set var="a" value="#dataMap.buyqty" />
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.buyqty!=null">
							<s:set var="buyqty" value="#dataMap.buyqty+#buyqty"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="#dataMap.sellqty" />
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.sellqty!=null">
							<s:set var="sellqty" value="#dataMap.sellqty+#sellqty"></s:set>
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
						<s:set var="a" value="format(#dataMap.buydelayfee)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.buydelayfee!=null">
							<s:set var="buydelayfee"
								value="#dataMap.buydelayfee+#buydelayfee"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.buymargin)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.buymargin!=null">
							<s:set var="buymargin" value="#dataMap.buymargin+#buymargin"></s:set>
						</s:if>
					</td>
					
				</tr>
			</s:iterator>
			<tr class="report_w14">
				<td>
					合计
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
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
				<td align="right">
					<s:set var="a" value="#buyqty" />
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td align="right">
					<s:set var="a" value="#sellqty" />
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td align="right">
					<s:set var="a" value="#qtysum" />
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td align="right">
					<s:set var="a" value="format(#buydelayfee)" />
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set var="a" value="format(#buymargin)" />
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
			</tr>
		</table>
		</td>
		</tr>
		</table>
	</body>
</html>
