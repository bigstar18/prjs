<%@ page pageEncoding="GBK"%>
<%@ include file="/public/reportCss.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
	<br>
	<div class="report_w16_title">交易资金状况</div>
	<table width="200%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#000000" style='border-collapse: collapse;' bgcolor="#eeeeee" height="26">
	<s:set var="num" value="1"></s:set>
	<s:set var="begincapital" value="0"></s:set>
	<s:set var="fundio" value="0"></s:set>
	<s:set var="memberfee" value="0"></s:set>
	<s:set var="mktfee" value="0"></s:set>
	<s:set var="customerfee" value="0"></s:set>
	<s:set var="memberclosepl" value="0"></s:set>
	<s:set var="memberholdpl" value="0"></s:set>
	<s:set var="customerdelayfee" value="0"></s:set>
	<s:set var="smemberdelayfee" value="0"></s:set>
	<s:set var="delayfeesum" value="0"></s:set>
  <tr class="report_w14">
  	 <td width="3%">序号</td>
  	 <td width="7%">结算日期</td>
    <td width="6%">期初权益</td>
    <td width="6%">出入金</td>
    <td width="7%">会员留存手续费</td>
    <td width="7%">交易所留存手续费</td>
    <td width="8%">收客户手续费</td>
    <td width="8%">平仓净盈亏</td>
    <td width="8%">持仓净盈亏</td>
    <td width="8%">收客户延期费</td>
    <td width="8%">交特别会员延期费</td>
    <td width="8%">会员留存延期费</td>
    <td width="8%">期末权益</td>
    <td width="6%">风险率</td>
  </tr>
			<s:iterator value="dataList()" var="dataMap">
				<tr class="report_w14_tdheight">
					<td>
						<s:property value="#num" />
						<s:set var="num" value="#num+1"></s:set>
					</td>
					<td>
						<s:date name="#dataMap.clearDate" format="yyyy-MM-dd"/>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.begincapital)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.begincapital!=null">
						<s:set var="begincapital" value="#dataMap.begincapital+#begincapital"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.fundio)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.fundio!=null">
						<s:set var="fundio" value="#dataMap.fundio+#fundio"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.memberfee)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.memberfee!=null">
						<s:set var="memberfee" value="#dataMap.memberfee+#memberfee"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.mktfee)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.mktfee!=null">
						<s:set var="mktfee" value="#dataMap.mktfee+#mktfee"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.customerfee)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.customerfee!=null">
						<s:set var="customerfee" value="#dataMap.customerfee+#customerfee"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.memberclosepl)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.memberclosepl!=null">
						<s:set var="memberclosepl" value="#dataMap.memberclosepl+#memberclosepl"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.memberholdpl)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.memberholdpl!=null">
						<s:set var="memberholdpl" value="#dataMap.memberholdpl+#memberholdpl"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.customerdelayfee)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.customerdelayfee!=null">
						<s:set var="customerdelayfee" value="#dataMap.customerdelayfee+#customerdelayfee"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.smemberdelayfee)"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.smemberdelayfee!=null">
						<s:set var="smemberdelayfee" value="#dataMap.smemberdelayfee+#smemberdelayfee"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.delayfeesum)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.delayfeesum!=null">
						<s:set var="delayfeesum" value="#dataMap.delayfeesum+#delayfeesum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.endcapital)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.endcapital!=null">
						<s:set var="endcapital" value="#dataMap.endcapital+#endcapital"></s:set>
						</s:if>
					</td>
					<td align="right">
					<s:set var="risk" value="#dataMap.status"/>
					<s:set var="rate" value="#dataMap.risk"/>
					<c:if test="${risk=='F' }">
						--
					</c:if>
					<c:if test="${risk!='F' }">
						<c:if test="${rate>='2' }">
							安全
						</c:if>
						<c:if test="${rate<'2' }">
							<s:property value="format(#rate*100)"/>%
						</c:if>
					</c:if>
					</td>
				</tr>
			</s:iterator>
			<tr class="report_w14">
				<td>合计：</td>
				<td>&nbsp;</td>
			   <td align="right">&nbsp;</td>
			   <td align="right"><s:set var="a" value="format(#fundio)"/><fmt:formatNumber value="${a }" pattern="#,##0.00"/></td>
			   <td align="right"><s:set var="a" value="format(#memberfee)"/><fmt:formatNumber value="${a }" pattern="#,##0.00"/></td>
			   <td align="right"><s:set var="a" value="format(#mktfee)"/><fmt:formatNumber value="${a }" pattern="#,##0.00"/></td>
			   <td align="right"><s:set var="a" value="format(#customerfee)"/><fmt:formatNumber value="${a }" pattern="#,##0.00"/></td>
			   <td align="right"><s:set var="a" value="format(#memberclosepl)"/><fmt:formatNumber value="${a }" pattern="#,##0.00"/></td>
			   <td align="right"><s:set var="a" value="format(#memberholdpl)"/><fmt:formatNumber value="${a }" pattern="#,##0.00"/></td>
			   <td align="right"><s:set var="a" value="format(#customerdelayfee)"/><fmt:formatNumber value="${a }" pattern="#,##0.00"/></td>
			   <td align="right"><s:set var="a" value="format(#smemberdelayfee)"/><fmt:formatNumber value="${a }" pattern="#,##0.00"/></td>
			   <td align="right"><s:set var="a" value="format(#delayfeesum)"/><fmt:formatNumber value="${a }" pattern="#,##0.00"/></td>
			   <td align="right">&nbsp;</td>
			   <td>&nbsp;</td>
			</tr>
		</table>
		<br>
	</body>
</html>
