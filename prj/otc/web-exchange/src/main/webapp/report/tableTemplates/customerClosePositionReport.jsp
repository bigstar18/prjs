<%@ page pageEncoding="GBK"%>
<%@ include file="/public/reportCss.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
	<br>
	<br>
		<div class="report_w16_title">平仓明细</div>
		<table width="200%" border="1" align="center" cellpadding="0"
			cellspacing="0" bordercolor="#000000"
			style='border-collapse: collapse;'>
			<s:set var="num" value="1"></s:set>
			<s:set var="quantity" value="0"></s:set>
			<s:set var="close_price" value="0"></s:set>
			<s:set var="holdprice" value="0"></s:set>
			<s:set var="close_pl" value="0"></s:set>
			<s:set var="tradefee" value="0"></s:set>
			<s:set var="openprice" value="0"></s:set>
			<s:set var="delayfee" value="0"></s:set>
			<tr class="report_w14">
				<td width="3%">
					序号
				</td>
				<td width="9%">
					平仓时间
				</td>
				<td width="4%">
					会员编号
				</td>
				<td width="5%">
					会员名称
				</td>
				<td width="5%">
					机构
				</td>
				<td width="5%">
					机构代码
				</td>
				<td width="5%">
					交易账号
				</td>
				<td width="4%">
					商品
				</td>
				<td width="5%">
					平仓单号
				</td>
				<td width="5%">
					平仓量
				</td>
				<td width="4%">
					平仓价
				</td>
				<td width="4%">
					持仓价
				</td>
				<td width="5%">
					平仓盈亏
				</td>
				<td width="5%">
					平仓手续费
				</td>
				<td width="5%">
					建仓单号
				</td>
				<td width="4%">
					建仓价
				</td>
				<td width="9%">
					建仓时间
				</td>
				<td width="3%">
					延期费
				</td>
				<td width="4%">
					委托下单
				</td>
				<td width="3%">
					斩仓
				</td>
				<td width="4%">
					买卖方向
				</td>
			</tr>
			<s:iterator value="dataList()" var="dataMap">
				<tr class="report_w14_neirong">
					<td>
						<s:property value="#num" />
						<s:set var="num" value="#num+1"></s:set>
					</td>
					<td>
						<s:date name="#dataMap.tradeTime" format="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>
						<s:property value="#dataMap.memberno" />&nbsp;
					</td>
					<td>
						<s:property value="#dataMap.membername" />
					</td>
					<td>
						<s:property value="#dataMap.organizationname" />
					</td>
					<td>
						<s:property value="#dataMap.organizationno" />&nbsp;
					</td>
					<td>
						<s:property value="#dataMap.customerno" />&nbsp;
					</td>
					<td>
						<s:property value="#dataMap.commodityname" />
					</td>
					<td>
						<s:property value="#dataMap.tradeno" />
					</td>
					<td align="right">
						<s:set value="#dataMap.quantity" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.quantity!=null">
							<s:set var="quantity" value="#dataMap.quantity+#quantity"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.price)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.price!=null">
							<s:set var="close_price"
								value="#dataMap.price+#close_price"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.holdprice)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.holdprice!=null">
							<s:set var="holdprice" value="#dataMap.holdprice+#holdprice"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.close_pl)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.close_pl!=null">
							<s:set var="close_pl" value="#dataMap.close_pl+#close_pl"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.tradefee)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.tradefee!=null">
							<s:set var="tradefee" value="#dataMap.tradefee+#tradefee"></s:set>
						</s:if>
					</td>
					<td>
						<s:property value="#dataMap.opentradeno" />&nbsp;
					</td>
					<td align="right">
						<s:set value="format(#dataMap.openprice)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.openprice!=null">
							<s:set var="openprice" value="#dataMap.openprice+#openprice"></s:set>
						</s:if>
					</td>
					<td>
						<s:date name="#dataMap.holdtime" format="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.delayfee)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.delayfee!=null">
							<s:set var="delayfee" value="#dataMap.delayfee+#delayfee"></s:set>
						</s:if>
					</td>
					<td>
						<s:set var="mapKey" value="#dataMap.isDelegate"></s:set>
						<s:property value="whetherMap[#mapKey]" />
					</td>
					<td>
						<s:set var="mapKey" value="#dataMap.isSwitching"></s:set>
						<s:property value="whetherMap[#mapKey]" />
					</td>
					<td>
						<s:set var="mapKey" value="#dataMap.bs_flag"></s:set>
						<s:property value="flagMap[#mapKey]" />
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
					<s:set value="#quantity" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td align="right">
					&nbsp;
				</td>
				<td align="right">
					&nbsp;
				</td>
				<td align="right">
					<s:set value="format(#close_pl)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set value="format(#tradefee)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td>
					&nbsp;
				</td>
				<td align="right">
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
				<td align="right">
					<s:set value="format(#delayfee)" var="a"/>
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
		<br>
	</body>
</html>
