<%@ page pageEncoding="GBK"%>
<%@ include file="/public/reportCss.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
		<div class="report_w16_titlemin">�����������</div>
		<div class="report_w16_title">�ͻ��ɽ����ܱ�</div>
<table width="220%" border="0" align="center" cellpadding="0" cellspacing="0">
<tr>
  <td>
		<table width="40%" border="0" align="left" cellpadding="0"
			cellspacing="0" bordercolor="#000000"
			style='border-collapse: collapse;padding-left: 20px;' height="20">
			<tr>
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
			style='table-layout: fixed; border-collapse: collapse;'>
			<tr class="report_w14">
				<td width="4%" rowspan="2">
					���
				</td>
				<td width="5%" rowspan="2">
					��������
				</td>
				<td width="4%" rowspan="2">
					��Ա���
				</td>
				<td width="6%" rowspan="2">
					��Ա����
				</td>
				<td width="6%" rowspan="2">
					����
				</td>
				<td width="6%" rowspan="2">
					��������
				</td>
				<td width="8%" rowspan="2">
					�����˺�
				</td>
				<td width="7%" rowspan="2">
					�ͻ�����
				</td>
				<td width="6%" rowspan="2">
					��Ʒ
				</td>
				<td width="5%" rowspan="2">
					�ɽ���
				</td>
				<td width="8%" rowspan="2">
					�ɽ����
				</td>
				<td width="6%" rowspan="2">
					������
				</td>
				<td width="20%" colspan="3">
					����ӯ��
				</td>
				<td width="9%" rowspan="2">
					��ĩȨ��
				</td>
			</tr>
			<tr class="report_w14">
				<td width="5%" align="center">
					ƽ��
				</td>
				<td width="5%" align="center">
					�ֲ�
				</td>
				<td width="5%" align="center">
					�ϼ�
				</td>
			</tr>
			<s:set var="num" value="1"></s:set>
			<s:set var="customerqtysum" value="0"></s:set>
			<s:set var="customerfundsum" value="0"></s:set>
			<s:set var="customerclosetradefee" value="0"></s:set>
			<s:set var="customercloseplsum" value="0"></s:set>
			<s:set var="customerholdpl" value="0"></s:set>
			<s:set var="customerplsum" value="0"></s:set>
			<s:set var="endcapital" value="0"></s:set>
			<s:set var="customertradefeesum" value="0"></s:set>

			<s:iterator value="dataList()" var="dataMap">
				<tr class="report_w14_neirong">
					<td>
						<s:property value="#num" />
						<s:set var="num" value="#num+1"></s:set>
					</td>
					<td>
						<s:date name="#dataMap.cleardate" format="yyyy-MM-dd" />
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
						<s:set value="#dataMap.customerqtysum" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.customerqtysum!=null">
							<s:set var="customerqtysum"
								value="#dataMap.customerqtysum+#customerqtysum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.customerfundsum)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.customerfundsum!=null">
							<s:set var="customerfundsum"
								value="#dataMap.customerfundsum+#customerfundsum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.customertradefeesum)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.customertradefeesum!=null">
							<s:set var="customertradefeesum"
								value="#dataMap.customertradefeesum+#customertradefeesum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.customercloseplsum)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.customercloseplsum!=null">
							<s:set var="customercloseplsum"
								value="#dataMap.customercloseplsum+#customercloseplsum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.customerholdpl)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.customerholdpl!=null">
							<s:set var="customerholdpl"
								value="#dataMap.customerholdpl+#customerholdpl"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.customerplsum)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.customerplsum!=null">
							<s:set var="customerplsum"
								value="#dataMap.customerplsum+#customerplsum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.endcapital)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.endcapital!=null">
							<s:set var="endcapital" value="#dataMap.endcapital+#endcapital"></s:set>
						</s:if>
					</td>
				</tr>
			</s:iterator>
			<tr class="report_w14">
				<td>
					�ϼ�:
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
					<s:set value="#customerqtysum" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td align="right">
					<s:set value="format(#customerfundsum)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set value="format(#customertradefeesum)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set value="format(#customercloseplsum)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set value="format(#customerholdpl)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set value="format(#customerplsum)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set value="format(#endcapital)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
			</tr>
		</table>
		</td>
		</tr>
		</table>
	</body>
</html>

