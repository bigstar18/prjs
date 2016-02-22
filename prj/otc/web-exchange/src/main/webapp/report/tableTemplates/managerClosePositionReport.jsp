<%@ page pageEncoding="GBK"%>
<%@ include file="/public/reportCss.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
		<br>
		<br>
		<br>
		<div class="report_w16_title">
			平仓明细
		</div>
		<table width="130%" border="1" align="center" cellpadding="0"
			cellspacing="0" bordercolor="#000000"
			style='border-collapse: collapse;' bgcolor="#ffbfc9" height="26">
			<s:set var="num" value="1"></s:set>
			<s:set var="quantity" value="0"></s:set>
			<s:set var="close_price" value="0"></s:set>
			<s:set var="holdprice" value="0"></s:set>
			<s:set var="close_pl" value="0"></s:set>
			<s:set var="tradefee" value="0"></s:set>
			<s:set var="openprice" value="0"></s:set>
			<s:set var="delayfee" value="0"></s:set>
			<tr class="report_w14">
				<td width="4%">
					序号
				</td>
				<td width="4%">
					交易账号
				</td>
				<td width="4%">
					客户名称
				</td>
				<td width="5%">
					商品
				</td>
				<td width="5%">
					平仓量
				</td>
				<td width="5%">
					平仓价
				</td>
				<td width="5%">
					持仓价
				</td>
				<td width="5%">
					平仓盈亏
				</td>
				<td width="5%">
					平仓手续费
				</td>
				<td width="5%">
					建仓价
				</td>
				<td width="5%">
					延期费
				</td>
				<td width="4%">
					委托下单
				</td>
				<td width="4%">
					斩仓
				</td>
				<td width="6%">
					平仓时间
				</td>
				<td width="4%">
					平仓单号
				</td>
				<td width="4%">
					买卖方向
				</td>
				<td width="4%">
					建仓单号
				</td>
				<td width="6%">
					建仓时间
				</td>
			</tr>
			<s:iterator value="dataList()" var="dataMap">
				<tr>
					<td>
						<s:set var="num" value="#num+1"></s:set>
						<s:property value="#num" />
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
						<s:property value="#dataMap.quantity" />&nbsp;
						<s:if test="#dataMap.quantity!=null">
							<s:set var="quantity" value="#dataMap.quantity+#quantity"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:property value="format(#dataMap.close_price)" />&nbsp;
						<s:if test="#dataMap.close_price!=null">
							<s:set var="close_price"
								value="#dataMap.close_price+#close_price"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:property value="format(#dataMap.holdprice)" />&nbsp;
						<s:if test="#dataMap.holdprice!=null">
							<s:set var="holdprice" value="#dataMap.holdprice+#holdprice"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:property value="format(#dataMap.close_pl)" />&nbsp;
						<s:if test="#dataMap.close_pl!=null">
							<s:set var="close_pl" value="#dataMap.close_pl+#close_pl"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:property value="format(#dataMap.tradefee)" />&nbsp;
						<s:if test="#dataMap.tradefee!=null">
							<s:set var="tradefee" value="#dataMap.tradefee+#tradefee"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:property value="format(#dataMap.openprice)" />&nbsp;
						<s:if test="#dataMap.openprice!=null">
							<s:set var="openprice" value="#dataMap.openprice+#openprice"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:property value="format(#dataMap.delayfee)" />&nbsp;
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
						<s:date name="#dataMap.tradetime" format="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>
						<s:property value="#dataMap.tradeno" />&nbsp;
					</td>
					<td>
						<s:set var="mapKey" value="#dataMap.bs_flag"></s:set>
						<s:property value="flagMap[#mapKey]" />
					</td>
					<td>
						<s:property value="#dataMap.opentradeno" />&nbsp;
					</td>
					<td>
						<s:date name="#dataMap.holdtime" format="yyyy-MM-dd HH:mm:ss"/>
					</td>
				</tr>
			</s:iterator>
			<tr>
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
					<s:property value="#quantity" />&nbsp;
				</td>
				<td align="right">
					<s:property value="format(#close_price)" />&nbsp;
				</td>
				<td align="right">
					<s:property value="format(#holdprice)" />&nbsp;
				</td>
				<td align="right">
					<s:property value="format(#close_pl)" />&nbsp;
				</td>
				<td align="right">
					<s:property value="format(#tradefee)" />&nbsp;
				</td>
				<td align="right">
					<s:property value="format(#openprice)" />&nbsp;
				</td>
				<td align="right">
					<s:property value="format(#delayfee)" />&nbsp;
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
			</tr>
		</table>
		<br>
		<br>
		<br>
	</body>
</html>
