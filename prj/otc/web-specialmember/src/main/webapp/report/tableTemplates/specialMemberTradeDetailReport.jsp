<%@ page pageEncoding="GBK"%>
<%@ include file="/public/reportCss.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
		<br>
		<br>
		<div class="report_w16_title">
			特别会员成交明细
		</div>
		<table width="170%" border="1" align="center" cellpadding="0"
			cellspacing="0" bordercolor="#000000"
			style='border-collapse: collapse;'>
			<s:set var="num" value="0"></s:set>
			<s:set var="quantity" value="0"></s:set>
			<s:set var="turnover" value="0"></s:set>
			<tr class="report_w14">
				<td width="3%">
					序号
				</td>
				<td width="12%">
					成交时间
				</td>
				<td width="7%">
					会员编号
				</td>
				<td width="9%">
					会员名称
				</td>	
				<td width="7%">
					成交单号
				</td>
				<td width="6%">
					商品
				</td>
				<td width="5%">
					成交量
				</td>
				<td width="7%">
					成交价
				</td>
				<td width="7%">
					成交金额
				</td>
				<td width="7%">
					买卖方向
				</td>	
				<td width="8%">
					交易账号
				</td>
				<td width="6%">
					建/平仓
				</td>
			</tr>
			<s:iterator value="dataList()" var="dataMap">
				<tr class="report_w14_neirong">
					<td>
						<s:set var="num" value="#num+1" ></s:set>
						<s:property value="#num" />
					</td>
					<td>
						<s:date name="#dataMap.tradetime" format="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>
						<s:property value="#dataMap.memberno" />&nbsp;
					</td>
					<td>
						<s:property value="#dataMap.membername" />
					</td>
					<td>
						<s:property value="#dataMap.tradeno" />&nbsp;
					</td>
					<td>
						<s:property value="#dataMap.commodityname" />
					</td>
					<td align="right">
						<s:set var="a" value="#dataMap.quantity" />
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.quantity!=null">
						<s:set var="quantity" value="#dataMap.quantity+#quantity"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.price)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.turnover)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.turnover!=null">
						<s:set var="turnover" value="#dataMap.turnover+#turnover"></s:set>
						</s:if>
					</td>
					<td>
						<s:set var="mapKey" value="#dataMap.bs_flag"></s:set>
						<s:property value="flagMap[#mapKey]" />
					</td>
					
					<td>
						<s:property value="#dataMap.s_signno" />&nbsp;
					</td>
					<td>
						<s:set var="moldKey" value="#dataMap.oc_flag"></s:set>
						<s:property value="moldMap[#moldKey]" />
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
				<td >
					&nbsp;
				</td>
				<td >
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
				<td align="right">
					<s:set var="a" value="#quantity"/>
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td align="right">
					&nbsp;
				</td>
				<td align="right">
					<s:set var="a" value="format(#turnover)"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td >
					&nbsp;
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
	</body>
</html>
