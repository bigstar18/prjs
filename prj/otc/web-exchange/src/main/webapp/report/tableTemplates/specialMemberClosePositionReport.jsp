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
			平仓明细
		</div>
		<table width="170%" border="1" align="center" cellpadding="0"
			cellspacing="0" bordercolor="#000000"
			style='border-collapse: collapse;'>
			<tr class="report_w14">
				<td width="3%">
					序号
				</td>
				<td width="6%">
					会员编号
				</td>
				<td width="6%">
					会员名称
				</td>
				<td width="5%">
					交易账号
				</td>
				<td width="4%">
					商品
				</td>
				<td width="5%">
					买卖方向
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
					建仓单号
				</td>
				<td width="5%">
					建仓价
				</td>
				<td width="5%">
					收延期费
				</td>
				<td width="11%">
					平仓时间
				</td>
				
				<td width="5%">
					平仓单号
				</td>
			
				<td width="11%">
					建仓时间
				</td>
				
				
				<td width="5%">
					委托下单
				</td>
				<td width="4%">
					斩仓
				</td>
			</tr>
			<s:set var="num" value="1"></s:set>
			<s:set var="quantity" value="0"></s:set>
			<s:set var="price" value="0"></s:set>
			<s:set var="holdprice" value="0"></s:set>
			<s:set var="close_pl" value="0"></s:set>
			<s:set var="openprice" value="0"></s:set>
			<s:set var="tradefee" value="0"></s:set>
			<s:set var="delayfee" value="0"></s:set>
			<s:iterator value="dataList()" var="dataMap">
				<tr class="report_w14_neirong">
					<td>
						<s:property value="#num" />
						<s:set var="num" value="#num+1"></s:set>
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
						<s:set var="mapKey" value="#dataMap.bs_flag"></s:set>
						<s:property value="flagMap[#mapKey]" />
					</td>
					<td align="right">
						<s:set value="#dataMap.quantity" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.quantity!=null">
							<s:set var="quantity" value="#dataMap.quantity+#quantity"></s:set>
						</s:if>
					</td>
					<td  align="right">
						<s:set value="format(#dataMap.price)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.price!=null">
							<s:set var="price"
								value="#dataMap.price+#price"></s:set>
						</s:if>
					</td>
					<td  align="right">
						<s:set value="format(#dataMap.holdprice)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.holdprice!=null">
							<s:set var="holdprice" value="#dataMap.holdprice+#holdprice"></s:set>
						</s:if>
					</td>
					<td  align="right">
						<s:set value="format(#dataMap.close_pl)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.close_pl!=null">
							<s:set var="close_pl" value="#dataMap.close_pl+#close_pl"></s:set>
						</s:if>
					</td>
					<td>
						<s:property value="#dataMap.opentradeno" />&nbsp;
					</td>
					<td  align="right">
						<s:set value="format(#dataMap.openprice)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.openprice!=null">
							<s:set var="openprice" value="#dataMap.openprice+#openprice"></s:set>
						</s:if>
					</td>
					<td  align="right">
						<s:set value="format(#dataMap.delayfee)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.delayfee!=null">
							<s:set var="delayfee" value="#dataMap.delayfee+#delayfee"></s:set>
						</s:if>
					</td>
					<td>
						<s:date name="#dataMap.closetime" format="yyyy-MM-dd HH:mm:ss"/>
					</td> 
					<td>
						<s:property value="#dataMap.tradeno" />&nbsp;
					</td>
					<td>
						<s:date name="#dataMap.holdtime" format="yyyy-MM-dd HH:mm:ss"/>
					</td>
					
					
					<td>
						<s:set var="isDelegate" value="#dataMap.isDelegate"></s:set>
						<s:property value="whetherMap[#isDelegate]" />
					</td>
					<td>
						<s:set var="isSwitching" value="#dataMap.isSwitching"></s:set>
						<s:property value="whetherMap[#isSwitching]" />
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
				<td  align="right">
					<s:set value="#quantity" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td  align="right">
					&nbsp;
				</td>
				<td  align="right">
					&nbsp;
				</td>
				<td  align="right">
					<s:set value="format(#close_pl)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td>
					&nbsp;
				</td>
				<td  align="right">
					&nbsp;
				</td>
				<td  align="right">
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
