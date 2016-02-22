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
			成交明细
		</div>
		<table width="100%" border="1" align="center" cellpadding="0"
			cellspacing="0" bordercolor="#000000"
			style='border-collapse: collapse;' bgcolor="#ffbfc9" height="26">
			<s:set var="num" value="1"></s:set>
			<s:set var="quantity" value="0"></s:set>
			<s:set var="close_price" value="0"></s:set>
			<s:set var="tradefunds" value="0"></s:set>
			<s:set var="tradefee" value="0"></s:set>
			<tr class="report_w14">
				<td width="4%">
					序号
				</td>
				<td width="8%">
					 交易账号
				</td>
				<td width="10%">
					客户名称
				</td>
				<td width="7%">
					成交单号
				</td>
				<td width="8%">
					商品
				</td>
				<td width="6%">
					成交量
				</td>
				<td width="8%">
					成交价
				</td>
				<td width="8%">
					成交金额
				</td>
				<td width="8%">
					手续费
				</td>
				<td width="8%">
					成交时间
				</td>
				<td width="8%">
					买卖方向
				</td>
			</tr>
			<s:iterator value="dataList()" var="dataMap">
				<tr>
					<td>
						<s:set var="num" value="#num+1" ></s:set>
						<s:property value="#num" />
					</td>
					<td>
						<s:property value="#dataMap.customerno" />&nbsp;
					</td>
					<td>
						<s:property value="#dataMap.customername" />
					</td>
					<td>
						<s:property value="#dataMap.tradeno" />&nbsp;
					</td>
					<td>
						<s:property value="#dataMap.commodityname" />
					</td>
					<td>
						<s:property value="#dataMap.quantity" />&nbsp;
						<s:if test="#dataMap.quantity!=null">
						<s:set var="quantity" value="#dataMap.quantity+#quantity"></s:set>
						</s:if>
					</td>
					<td>
						<s:property value="format(#dataMap.price)" />&nbsp;
						<s:if test="#dataMap.close_price!=null">
						<s:set var="close_price" value="#dataMap.price+#close_price"></s:set>
						</s:if>
					</td>
					<td>
						<s:property value="format(#dataMap.tradefunds)" />&nbsp;
						<s:if test="#dataMap.tradefunds!=null">
						<s:set var="tradefunds" value="#dataMap.tradefunds+#tradefunds"></s:set>
						</s:if>
					</td>
					<td>
						<s:property value="format(#dataMap.tradefee)" />&nbsp;
						<s:if test="#dataMap.tradefee!=null">
						<s:set var="tradefee" value="#dataMap.tradefee+#tradefee"></s:set>
						</s:if>
					</td>
					<td>
						<s:date name="#dataMap.tradetime" format="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>
						<s:set var="mapKey" value="#dataMap.close_bs_flag"></s:set>
						<s:property value="flagMap[#mapKey]" />
					</td>
			</s:iterator>
			<tr>
				<td>
					合计：
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
				<td>
					<s:property value="#quantity"/>&nbsp;
				</td>
				<td>
					<s:property value="format(#close_price)"/>&nbsp;
				</td>
				<td>
					<s:property value="format(#tradefunds)"/>&nbsp;
				</td>
				<td>
					<s:property value="format(#tradefee)"/>&nbsp;
				</td>
				<td >
					&nbsp;
				</td>
				<td >
					&nbsp;
				</td>
				
				
			</tr>
		</table>
		<br>
		<br>
		<br>
	</body>
</html>
