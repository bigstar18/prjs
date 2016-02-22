<%@ page pageEncoding="GBK"%>
<%@ include file="/public/reportCss.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
	<div class="report_w16_titlemin">贵金属交易所</div>
	<div class="report_w16_title">客户交易统计表</div>
<table width="250%" border="0" align="center" cellpadding="0" cellspacing="0">
<tr>
  <td>
<table width="40%" border="0" align="left" cellpadding="0" cellspacing="0" bordercolor="#000000" style='border-collapse: collapse;padding-left: 20px;' height="20">
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
	<table width="100%" border="1" align="left" cellpadding="0" cellspacing="0" bordercolor="#000000" style='table-layout:fixed;border-collapse: collapse;' height="26">
  <tr class="report_w14">
  	 <td width="3%" rowspan="2">序号</td>
  	 <td width="4%" rowspan="2">结算日期</td>
     <td width="4%" rowspan="2">会员编号</td>
     <td width="6%" rowspan="2">会员名称</td>
    <td width="5%" rowspan="2">机构</td>
    <td width="5%" rowspan="2">机构代码</td>
    <td width="7%" rowspan="2">交易账号</td>
    <td width="7%" rowspan="2">客户名称</td>
    <td width="4%" rowspan="2">商品</td>
    <td width="3%" rowspan="2">成交量</td>
    <td width="8%" rowspan="2">成交金额</td>
    <td width="20%" colspan="3">交易盈亏</td>
    <td width="23%" colspan="3">手续费</td>
    <td width="6%" rowspan="2">延期费</td>
  </tr>
  <tr class="report_w14">
   <td>平仓盈亏</td>
    <td>持仓盈亏</td>
    <td>合计</td>
    <td>交易所留存手续费</td>
    <td>会员留存手续费</td>
    <td>收客户</td>
  </tr>
  <s:set var="customerqtysum" value="0"></s:set>
  <s:set var="customerfundsum" value="0"></s:set>
   <s:set var="customercloseplsum" value="0"></s:set>
    <s:set var="customerholdpl" value="0"></s:set>
    <s:set var="plsum" value="0"></s:set>
    <s:set var="customerfee" value="0"></s:set>
    <s:set var="MemberFee" value="0"></s:set>
    <s:set var="mktfee" value="0"></s:set>
    <s:set var="customerdelayfee" value="0"></s:set>
     <s:set var="num" value="1"></s:set>
      <s:set var="customerfee" value="0"></s:set>
			<s:iterator value="dataList()" var="dataMap">
				<tr class="report_w14_neirong">
					<td>
						<s:property value="#num" />
						<s:set var="num" value="#num+1"></s:set>
					</td>
					<td>
						<s:date name="#dataMap.clearDate" format="yyyy-MM-dd"/>
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
						<s:property value="#dataMap.organizationno" />
					</td>
					<td>
						<s:property value="#dataMap.customerno" />&nbsp;
					</td>
					<td>
						<s:property value="#dataMap.customername" />
					</td>
					<td>
						<s:property value="#dataMap.commodityname" />
					</td>
					<td align="right">
						<s:set value="#dataMap.customerqtysum" var="a" />
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.customerqtysum!=null">
						<s:set var="customerqtysum" value="#dataMap.customerqtysum+#customerqtysum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="#dataMap.customerfundsum" var="a"></s:set>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.customerfundsum!=null">
						<s:set var="customerfundsum" value="#dataMap.customerfundsum+#customerfundsum"></s:set>
						</s:if>
						
					</td>
					<td align="right">
						<s:set value="format(#dataMap.customercloseplsum)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.customercloseplsum!=null">
						<s:set var="customercloseplsum" value="#dataMap.customercloseplsum+#customercloseplsum"></s:set>
						</s:if>
					</td align="right">
					<td align="right">
						<s:set var="a" value="format(#dataMap.customerholdpl)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.customerholdpl!=null">
						<s:set var="customerholdpl" value="#dataMap.customerholdpl+#customerholdpl"></s:set>
						</s:if>
					</td align="right">
					<td align="right">
						<s:set var="a" value="format(#dataMap.plsum)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.plsum!=null">
						<s:set var="plsum" value="#dataMap.plsum+#plsum"></s:set>
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
						<s:set var="a" value="format(#dataMap.MemberFee)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.MemberFee!=null">
						<s:set var="MemberFee" value="#dataMap.MemberFee+#MemberFee"></s:set>
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
						<s:set var="a" value="format(#dataMap.customerdelayfee)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.customerdelayfee!=null">
						<s:set var="customerdelayfee" value="#dataMap.customerdelayfee+#customerdelayfee"></s:set>
						</s:if>
					</td>
				</tr>
			</s:iterator>
			<tr class="report_w14">
					<td>
						合计:
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
						<s:property value="#customerqtysum"/>
					</td>
					<td align="right">
						<s:set var="a" value="format(#customerfundsum)"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
					</td>
					<td align="right">
						<s:set var="a" value="format(#customercloseplsum)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
					</td>
					<td align="right">
						<s:set var="a" value="format(#customerholdpl)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
					</td>
					<td align="right">
						<s:set var="a" value="format(#plsum)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
					</td>
					<td align="right">
						<s:set var="a" value="format(#mktfee)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
					</td>
					<td align="right">
						<s:set var="a" value="format(#MemberFee)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
					</td>
					<td align="right">
						<s:set var="a" value="format(#customerfee)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
					</td>
					<td align="right">
						<s:set var="a" value="format(#customerdelayfee)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
					</td>
				</tr>
		</table>
		</td>
		</tr>
		</table>
	</body>
</html>
