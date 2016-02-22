<%@ page pageEncoding="GBK"%>
<%@ include file="/public/reportCss.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
		<br>
		
		<div class="report_w16_title">
			成交明细
		</div>
		<table width="200%" border="1" align="center" cellpadding="0"
			cellspacing="0" bordercolor="#000000"
			style='border-collapse: collapse;' bgcolor="#eeeeee" height="26">
			<s:set var="num" value="0"></s:set>
			<s:set var="quantity" value="0"></s:set>
			<s:set var="price" value="0"></s:set>
			<s:set var="tradefunds" value="0"></s:set>
			<s:set var="tradefee" value="0"></s:set>
			<tr class="report_w14">
				<td width="3%">
					序号
				</td>
				<td width="5%">
					结算日期
				</td>
				<td width="10%">
					成交时间
				</td>
				<td width="8%">
					买卖方向
				</td>
				<td width="10%">
					交易账号
				</td>
				<td width="8%">
					成交单号
				</td>
				<td width="7%">
					商品
				</td>
				<td width="9%">
					成交量
				</td>
				<td width="10%">
					成交价
				</td>
				<td width="10%">
					成交金额
				</td>
				<td width="10%">
					手续费
				</td>
				<td width="10%">
					特别会员
				</td>
			</tr>
			<s:iterator value="dataList()" var="dataMap">
				<tr class="report_w14_tdheight">
					<td>
						<s:set var="num" value="#num+1"></s:set>
						<s:property value="#num" />
					</td>
					<td>
						<s:date name="#dataMap.clearDate" format="yyyy-MM-dd"/>
					</td>
					<td>
						<s:date name="#dataMap.tradetime" format="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>
						<s:set var="mapKey" value="#dataMap.bs_flag"></s:set>
						<s:property value="flagMap[#mapKey]" />
					</td>
					<td>
						<s:property value="#dataMap.membersignno" />&nbsp;
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
						<s:if test="#dataMap.price!=null">
						<s:set var="price" value="#dataMap.price+#price"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.tradefunds)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.tradefunds!=null">
						<s:set var="tradefunds" value="#dataMap.tradefunds+#tradefunds"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.tradefee)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.tradefee!=null">
						<s:set var="tradefee" value="#dataMap.tradefee+#tradefee"></s:set>
						</s:if>
					</td>
					<td>
						<s:property value="#dataMap.s_memberno" />&nbsp;
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
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
				<td align="right">
					<s:set var="a" value="#quantity" />
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td align="right">
					&nbsp;
				</td>
				<td align="right">
					<s:set var="a" value="format(#tradefunds)" />
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set var="a" value="format(#tradefee)" />
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td>
					&nbsp;
				</td>
			</tr>
		</table>
		<br>
		
	</body>
</html>
