<%@ page pageEncoding="GBK"%>
<%@ include file="/public/reportCss.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
		<div class="report_w16_titlemin">
			�����������
		</div>
		<div class="report_w16_title">
			�ر��Ա�ֲֻ��ܱ�
		</div>
<table width="150%" border="0" align="center" cellpadding="0" cellspacing="0">
<tr>
  <td>
		<table width="75%" border="0" align="left" cellpadding="0"
			cellspacing="0" bordercolor="#000000"
			style='border-collapse: collapse;padding-left: 20px;' height="20">
			<tr>
			<td align="left">
					�ر��Ա��ţ�${s_memberNo}
				</td>
				<td align="left">
					�ر��Ա���ƣ�${s_memberName}
				</td>
				<td align="left">
					��ʼ���ڣ�${oldParams["trunc(primary.cleardate)[>=][date]"]}
				</td>
				<td align="left">
					�������ڣ�${oldParams["trunc(primary.cleardate)[<=][date]"]}
				</td>
				<td align="left">
					�����ˣ�${CURRENUSERID }
				</td>
			</tr>
		</table>
		</td>
		</tr>
		<tr>
		<td>
		<table width="100%" border="1" align="center" cellpadding="0"
			cellspacing="0" bordercolor="#000000"
			style='border-collapse: collapse;'>
			<s:set var="netqty" value="0"></s:set>
			<s:set var="sellqty" value="0"></s:set>
			<s:set var="netqyt" value="0"></s:set>
			<s:set var="buyqty" value="0"></s:set>
			<s:set var="maxnethold" value="0"></s:set>
			<s:set var="delayfee" value="0"></s:set>
			<s:set var="endcapital" value="0"></s:set>
			<s:set var="num" value="1"></s:set>
			<tr class="report_w14">
				<td width="6%">
					���
				</td>
				<td width="10%">
					��������
				</td>
				<td width="9%">
					��Ʒ
				</td>
				<td width="9%">
					��ֲ���
				</td>
				<td width="9%">
					���ֲ���
				</td>
				<td width="10%">
					���ֲ���
				</td>
				<td width="11%">
					�����ڷ�
				</td>
			</tr>
			<s:iterator value="dataList()" var="dataMap">
				<tr class="report_w14_neirong">
					<td>
						<s:property value="#num" />
						<s:set var="num" value="#num+1"></s:set>
					</td>
					<td>
						<s:date name="#dataMap.clearDate" format="yyyy-MM-dd"></s:date>
					</td>
					<td>
						<s:property value="#dataMap.commodityName" />
					</td>
					<td align="right">
						<s:set var="a" value="#dataMap.buyqty" />
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.buyqty!=null">
							<s:set var="buyqty" value="#dataMap.buyqty+#buyqty"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="#dataMap.sellqty" />
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.sellqty!=null">
							<s:set var="sellqty" value="#dataMap.sellqty+#sellqty"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="#dataMap.netqty" />
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.netqty!=null">
							<s:set var="netqty" value="#dataMap.netqty+#netqty"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.delayfee)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.delayfee!=null">
							<s:set var="delayfee" value="#dataMap.delayfee+#delayfee"></s:set>
						</s:if>
					</td>
				</tr>
			</s:iterator>
			<tr class="report_w14">
				<td>
					�ܼƣ�
				</td>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
				<td align="right">
					<s:set var="a" value="#buyqty" />
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td align="right">
					<s:set var="a" value="#sellqty" />
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>

				<td align="right">
					<s:set var="a" value="#netqyt" />
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td align="right">
					<s:set var="a" value="format(#delayfee)" />
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
			</tr>
		</table>
		</td>
		</tr>
		</table>
	</body>
</html>
