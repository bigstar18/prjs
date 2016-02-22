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
			持仓明细
		</div>
		<table width="170%" border="1" align="center" cellpadding="0"
			cellspacing="0" bordercolor="#000000"
			style='border-collapse: collapse;'>
			<tr class="report_w14">
				<td width="3%">
					序号
				</td>
				<td width="6%">结算日期</td>
				<td width="8%">
					会员编号
				</td>
				<td width="7%">
					会员名称
				</td>
				<td width="6%">
					交易账号
				</td>
				<td width="5%">
					商品
				</td>
				<td width="5%">
					买卖方向
				</td>
				<td width="7%">
					建仓价
				</td>
				<td width="7%">
					持仓量
				</td>
				<td width="7%">
					持仓价
				</td>
				<td width="7%">
					结算价
				</td>
				<td width="7%">
					持仓盈亏
				</td>
				<td width="7%">
					收延期费
				</td>
				<td width="7%">
					建仓单号
				</td>
				<td width="12%">
					建仓时间
				</td>
				
				
			</tr>
			<s:set var="num" value="1"></s:set>
			<s:set var="openprice" value="0"></s:set>
			<s:set var="holdqty" value="0"></s:set>
			<s:set var="holdprice" value="0"></s:set>
			<s:set var="price" value="0"></s:set>
			<s:set var="holdpl" value="0"></s:set>
			<s:set var="delayFee" value="0"></s:set>
			<s:iterator value="dataList()" var="dataMap">
				<tr class="report_w14_neirong">
					<td>
						<s:property value="#num" />
						<s:set var="num" value="#num+1"></s:set>
					</td>
					<td>
						<s:date name="#dataMap.cleardate" format="yyyy-MM-dd"/>
					</td>
					<td>
						<s:property value="#dataMap.memberno" />&nbsp;
					</td>
					<td>
						<s:property value="#dataMap.membername" />
					</td>
					<td>
						<s:property value="#dataMap.membersignno" />&nbsp;
					</td>
					<td>
						<s:property value="#dataMap.commodityName" />
					</td>
					<td>
						<s:set var="bs" value="#dataMap.bs_flag" />
						<s:property value="flagMap[#bs]" />
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.openprice)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.openprice!=null">
							<s:set var="openprice" value="#openprice+#dataMap.openprice"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="#dataMap.holdqty" />
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.holdqty!=null">
							<s:set var="holdqty" value="#holdqty+#dataMap.holdqty"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.holdprice)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.holdprice!=null">
							<s:set var="holdprice" value="#holdprice+#dataMap.holdprice"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.price)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.price!=null">
							<s:set var="price" value="#price+#dataMap.price"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.holdpl)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.holdpl!=null">
							<s:set var="holdpl" value="#holdpl+#dataMap.holdpl"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.delayFee)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.delayFee!=null">
							<s:set var="delayFee" value="#delayFee+#dataMap.delayFee"></s:set>
						</s:if>
					</td>
					<td >
						<s:property value="#dataMap.opentradeno" />&nbsp;
					</td>
					<td>
						<s:date name="#dataMap.holdtime" format="yyyy-MM-dd HH:mm:ss"/>
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
					<s:set var="a" value="format(#holdpl)" />
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set var="a" value="format(#delayFee)" />
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
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
