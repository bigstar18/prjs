<%@ page pageEncoding="GBK"%>
<%@ include file="/public/reportCss.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
		<div class="report_w16_titlemin">贵金属交易所</div>
		<div class="report_w16_title">特别会员交易统计表</div>
<table width="180%" border="0" align="center" cellpadding="0" cellspacing="0">
<tr>
  <td>
		<table width="50%" border="0" align="left" cellpadding="0" cellspacing="0" bordercolor="#000000" style='border-collapse: collapse;padding-left: 20px;' height="20">
	 	 <tr>
	    <td align="left">开始日期：${oldParams["trunc(primary.cleardate)[>=][date]"]}</td>
	    <td align="left">结束日期：${oldParams["trunc(primary.cleardate)[<=][date]"]}</td>
	    <td align="left">操作人：${CURRENUSERID }</td>
	 	 </tr>
		</table>
		</td>
		</tr>
		<tr>
		<td>
		<table width="100%" border="1" align="center" cellpadding="0"
			cellspacing="0" bordercolor="#000000"
			style='border-collapse: collapse;'>
			<s:set var="sm_holdqtysum" value="0"></s:set>
			<s:set var="sm_holderfundsum" value="0"></s:set>
			<s:set var="netqyt" value="0"></s:set>
			<s:set var="fundio" value="0"></s:set>
			<s:set var="delayfee" value="0"></s:set>
			<s:set var="closepl" value="0"></s:set>
			<s:set var="holdpl" value="0"></s:set>
			<s:set var="sm_buyqtysum" value="0"></s:set>
			<s:set var="sm_sellqtysum" value="0"></s:set>
			<s:set var="num" value="1"></s:set>
			<tr class="report_w14">
				<td width="4%" rowspan="2">
					序号
				</td>
				<td width="8%" rowspan="2">
					结算日期
				</td>
				<td width="6%" rowspan="2">
					商品
				</td>
				<td width="7%" rowspan="2">
					特别会员编号
				</td>
				<td width="7%" rowspan="2">
					特别会员名称
				</td>
				<td width="5%" rowspan="2">
					成交量
				</td>
				<td width="9%" rowspan="2">
					成交金额
				</td>
				<td width="5%" rowspan="2">
					买单
				</td>
				<td width="5%" rowspan="2">
					卖单
				</td>
				<td width="5%" rowspan="2">
					净头寸
				</td>
				<td width="7%" rowspan="2">
					收延期费
				</td>
				<td width="25%" colspan="3">
					交易盈亏
				</td>
			</tr>
			<tr class="report_w14">
				<td>平仓</td>
				<td>持仓</td>
				<td>合计</td>					
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
						<s:property value="#dataMap.commodityName" />
					</td>
					<td>
						<s:property value="#dataMap.s_memberno" />&nbsp;
					</td>
					<td>
						<s:property value="#dataMap.s_membername" />
					</td>
					<td align="right">
						<s:set value="#dataMap.sm_holdqtysum" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.sm_holdqtysum!=null">
						<s:set var="sm_holdqtysum" value="#dataMap.sm_holdqtysum+#sm_holdqtysum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.sm_holderfundsum)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.sm_holderfundsum!=null">
						<s:set var="sm_holderfundsum" value="#dataMap.sm_holderfundsum+#sm_holderfundsum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="#dataMap.sm_buyqtysum" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.sm_buyqtysum!=null">
						<s:set var="sm_buyqtysum" value="#dataMap.sm_buyqtysum+#sm_buyqtysum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="#dataMap.sm_sellqtysum" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.sm_sellqtysum!=null">
						<s:set var="sm_sellqtysum" value="#dataMap.sm_sellqtysum+#sm_sellqtysum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="#dataMap.netqyt" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.netqyt!=null">
						<s:set var="netqyt" value="#dataMap.netqyt+#netqyt"></s:set>
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
						<s:set value="format(#dataMap.closepl)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.closepl!=null">
						<s:set var="closepl" value="#dataMap.closepl+#closepl"></s:set>
						</s:if>
					</td>
								
					<td align="right">
						<s:set value="format(#dataMap.holdpl)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.holdpl!=null">
						<s:set var="holdpl" value="#dataMap.holdpl+#holdpl"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.closepl+#dataMap.holdpl)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
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
					<s:set value="#sm_holdqtysum" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td align="right">
					<s:set value="format(#sm_holderfundsum)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set value="#sm_buyqtysum" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td align="right">
				<s:set value="#sm_sellqtysum" var="a"/>
				<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td align="right">
					<s:set value="#netqyt" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td align="right">
					<s:set value="format(#delayfee)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set value="format(#closepl)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set value="format(#holdpl)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					&nbsp;
				</td>
			</tr>
		</table>
		</td>
		</tr>
		</table>
	</body>
</html>
