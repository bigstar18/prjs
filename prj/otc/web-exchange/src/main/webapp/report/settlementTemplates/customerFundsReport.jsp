<%@ page pageEncoding="GBK"%>
<%@ include file="/public/reportCss.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
	<div class="report_w16_titlemin">
		      贵金属交易所
	</div>
	<div class="report_w16_title">客户资金状况</div>

<table width="280%" border="0" align="center" cellpadding="0" cellspacing="0" bordercolor="#000000" style='border-collapse: collapse;'>
<tr>
  <td>
<table width="40%" border="0" align="left" cellpadding="0" cellspacing="0" bordercolor="#000000" style='border-collapse: collapse;padding-left: 20px;' height="20">
	  <tr>
	    <td align="left">开始日期：${oldParams["trunc(primary.cleardate)[>=][date]"]}&nbsp;</td>
	    <td align="left">结束日期：${oldParams["trunc(primary.cleardate)[<=][date]"]}&nbsp;</td>
	    <td align="left">操作人：${CURRENUSERID }&nbsp;</td>
	  </tr>
	</table>
	</td>
	</tr>
	<tr>
	 <td>
	 <table width="100%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#000000" style='border-collapse: collapse;' height="26">
  <s:set var="num" value="1"></s:set>
  <s:set var="begincapital" value="0"></s:set>
  <s:set var="fundio" value="0"></s:set>
  <s:set var="closepl" value="0"></s:set>
  <s:set var="holdpl" value="0"></s:set>
  <s:set var="plsum" value="0"></s:set>
  <s:set var="mktfee" value="0"></s:set>
  <s:set var="memberfee" value="0"></s:set>
  <s:set var="customerfee" value="0"></s:set>
  <s:set var="tradefee" value="0"></s:set>
  <s:set var="delayfee" value="0"></s:set>
  <s:set var="margin" value="0"></s:set>
  <s:set var="endcapital" value="0"></s:set>
  <s:set var="c_holdmargin" value="0"></s:set>
  <s:set var="lastcapital" value="0"></s:set>
  <tr class="report_w14">
    <td width="3%" rowspan="2">序号</td>
    <td width="5%" rowspan="2">结算日期</td>
    <td width="4%" rowspan="2">会员编号</td>
    <td width="5%" rowspan="2">会员名称</td>
     <td width="4%" rowspan="2">机构</td>
    <td width="4%" rowspan="2">机构代码</td>
    <td width="5%" rowspan="2">交易账号</td>
    <td width="5%" rowspan="2">客户名称</td>
    <td width="5%" rowspan="2">期初权益</td>
    <td width="6%" rowspan="2">出入金</td>
    <td colspan="3" width="15%">交易盈亏</td>
    <td colspan="3" width="18%">手续费</td>
    <td width="5%" rowspan="2">延期费</td>
    <td width="5%" rowspan="2">占用保证金</td>
    <td width="5%" rowspan="2">期末权益</td>
    <td width="5%" rowspan="2">风险率</td>
  </tr>
  <tr class="report_w14">
    <td width="5%">平仓盈亏</td>
    <td width="5%">持仓盈亏</td>
    <td width="5%">合计</td>
    <td width="7%">交易所留存手续费</td>
    <td width="7%">会员留存手续费</td>
    <td width="4%">收客户</td>
  </tr>
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
					<td align="right">
						<s:set value="format(#dataMap.begincapital)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.begincapital!=null">
						<s:set var="begincapital" value="#dataMap.begincapital+#begincapital"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.fundio)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.fundio!=null">
						<s:set var="fundio" value="#dataMap.fundio+#fundio"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.closepl)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.closepl!=null">
						<s:set var="closepl" value="#dataMap.closepl+#closepl"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.holdpl)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.holdpl!=null">
						<s:set var="holdpl" value="#dataMap.holdpl+#holdpl"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.plsum)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.plsum!=null">
						<s:set var="plsum" value="#dataMap.plsum+#plsum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.mktfee)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.mktfee!=null">
						<s:set var="mktfee" value="#dataMap.mktfee+#mktfee"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.memberfee)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.memberfee!=null">
						<s:set var="memberfee" value="#dataMap.memberfee+#memberfee"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.customerfee)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.customerfee!=null">
						<s:set var="customerfee" value="#dataMap.customerfee+#customerfee"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.delayfee)" var="a" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.delayfee!=null">
						<s:set var="delayfee" value="#dataMap.delayfee+#delayfee"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.margin)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.margin!=null">
						<s:set var="margin" value="#dataMap.margin+#margin"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.endcapital)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.endcapital!=null">
						<s:set var="endcapital" value="#dataMap.endcapital+#endcapital"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:property value="format(#dataMap.risk)" />%
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
				<td>
					&nbsp;
				</td>
				<td align="right">
					<s:set var="a" value="format(#begincapital)"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set var="a" value="format(#fundio)"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set var="a" value="format(#closepl)" />
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set var="a" value="format(#holdpl)" />
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
					<s:set var="a" value="format(#memberfee)" />
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set var="a" value="format(#customerfee)" />
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set var="a" value="format(#delayfee)" />
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set var="a" value="format(#margin)" />
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set var="a" value="format(#endcapital)"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td>
					&nbsp;
				</td>
			</tr>
		</table>
		</td>
		</tr>
		</table>
	</body>
</html>
