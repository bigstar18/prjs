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
			持仓明细
		</div>
		<table width="130%" border="1" align="center" cellpadding="0"
			cellspacing="0" bordercolor="#000000"
			style='border-collapse: collapse;' bgcolor="#ffbfc9" height="8">
			<s:set var="num" value="1"></s:set>
			<s:set var="holdqty" value="0"></s:set>
			<s:set var="holdprice" value="0"></s:set>
			<s:set var="openprice" value="0"></s:set>
			<s:set var="clearprice" value="0"></s:set>
			<s:set var="floatingloss" value="0"></s:set>
			<s:set var="holdmargin" value="0"></s:set>
			<s:set var="delayfee" value="0"></s:set>
			</td>
			<tr class="report_w14">
				<td width="4%">
					序号
				</td>
				<td width="5%">
					交易账号
				</td>
				<td width="8%">
					客户名称
				</td>
				<td width="6%">
					商品
				</td>
				<td width="7%">
					持仓量
				</td>
				<td width="7%">
					持仓价
				</td>
				<td width="7%">
					建仓价
				</td>
				<td width="7%">
					结算价
				</td>
				<td width="7%">
					持仓盈亏
				</td>
				<td width="8%">
					占用保证金
				</td>
				<td width="6%">
					延期费
				</td>
				<td width="5%">
					建仓时间
				</td>
				<td width="5%">
					建仓单号
				</td>
				<td width="5%">
					买卖方向
				</td>
			</tr>
			<s:iterator value="dataList()" var="dataMap">
				<tr class="report_w12">
					<td>
						<s:property value="#num" />
						<s:set var="num" value="#num+1"></s:set>
					</td>
					<td>
						<s:property value="#dataMap.customerno" />&nbsp;
					</td>
					<td>
						<s:property value="#dataMap.customerName" />
					</td>
				
					<td>
						<s:property value="#dataMap.commodityName" />
					</td>
					<td align="right">
						<s:property value="#dataMap.holdqty" />&nbsp;
						<s:if test="#dataMap.holdqty!=null">
							<s:set var="holdqty" value="#dataMap.holdqty+#holdqty"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:property value="format(#dataMap.holdprice)" />&nbsp;
						<s:if test="#dataMap.holdprice!=null">
							<s:set var="holdprice" value="#dataMap.holdprice+#holdprice"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:property value="format(#dataMap.openprice)" />&nbsp;
						<s:if test="#dataMap.openprice!=null">
							<s:set var="openprice" value="#dataMap.openprice+#openprice"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:property value="format(#dataMap.clearprice)" />&nbsp;
						<s:if test="#dataMap.clearprice!=null">
							<s:set var="clearprice" value="#dataMap.clearprice+#clearprice"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:property value="format(#dataMap.floatingloss)" />&nbsp;
						<s:if test="#dataMap.floatingloss!=null">
							<s:set var="floatingloss"
								value="#dataMap.floatingloss+#floatingloss"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:property value="format(#dataMap.holdmargin)" />&nbsp;
						<s:if test="#dataMap.holdmargin!=null">
							<s:set var="holdmargin" value="#dataMap.holdmargin+#holdmargin"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:property value="format(#dataMap.delayfee)" />&nbsp;
						<s:if test="#dataMap.delayfee!=null">
							<s:set var="delayfee" value="#dataMap.delayfee+#delayfee"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:date name="#dataMap.holeTime" format="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td >
						<s:property value="#dataMap.opentradeno" />
					</td>
					<td>
						<s:set var="bs" value="#dataMap.bs_flag" />
						<s:property value="flagMap[#bs]" />
					</td>
				</tr>
			</s:iterator>
			<tr class="report_w12">
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
						<s:property value="#holdqty" />&nbsp;
					</td>
					<td align="right">
						<s:property value="format(#holdprice)" />&nbsp;
					</td>
					<td align="right">
						<s:property value="format(#openprice)" />&nbsp;
					</td>
					<td align="right">
						<s:property value="format(#clearprice)" />&nbsp;
					</td>
					<td align="right">
						<s:property value="format(#floatingloss)" />&nbsp;
					</td>
					<td align="right">
						<s:property value="format(#holdmargin)" />&nbsp;
					</td>
					<td align="right">
						<s:property value="format(#delayfee)" />
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
