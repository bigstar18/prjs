<%@ page pageEncoding="GBK"%>
<%@ include file="/public/reportCss.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
		<div class="report_w16_titlemin">�����������</div>
		<div class="report_w16_title">�ر��Ա�ʽ�״����</div>
<table width="180%" border="0" align="center" cellpadding="0" cellspacing="0">
<tr>
  <td>
		<table width="50%" border="0" align="left" cellpadding="0" cellspacing="0" bordercolor="#000000" style='border-collapse: collapse;padding-left: 20px;' height="20">
	 	 <tr>
	    <td align="left">��ʼ���ڣ�${oldParams["trunc(primary.cleardate)[>=][date]"]}</td>
	    <td align="left">�������ڣ�${oldParams["trunc(primary.cleardate)[<=][date]"]}</td>
	    <td align="left">�����ˣ�${CURRENUSERID }</td>
	 	 </tr>
		</table>
		</td>
		</tr>
		<tr>
		<td>
		<table width="100%" border="1" align="center" cellpadding="0"
			cellspacing="0" bordercolor="#000000"
			style='border-collapse: collapse;'>
			<s:set var="begincapital" value="0"></s:set>
			<s:set var="fundio" value="0"></s:set>
			<s:set var="closepl" value="0"></s:set>
			<s:set var="delayfee" value="0"></s:set>
			<s:set var="endcapital" value="0"></s:set>
			<s:set var="plsum" value="0"></s:set>
			<s:set var="holdpl" value="0"></s:set>
			<s:set var="num" value="1"></s:set>
			<tr class="report_w14">
				<td width="4%" rowspan="2">
					���
				</td>
				<td width="9%" rowspan="2">
					��������
				</td>
				<td width="8%" rowspan="2">
					�ر��Ա���
				</td>
				<td width="10%" rowspan="2">
					�ر��Ա����
				</td>
				<td width="9%" rowspan="2">
					�ڳ�Ȩ��
				</td>
				<td width="11%" rowspan="2">
					�����
				</td>
				<td width="7%" rowspan="2">
					�����ڷ�
				</td>
				<td width="27%" colspan="3">
					����ӯ��
				</td>
				<td width="9%" rowspan="2">
					��ĩȨ��
				</td>
				<td width="6%" rowspan="2">
					������
				</td>
			</tr>
			<tr class="report_w14">
				<td>
					ƽ��
				</td>
				<td>
					�ֲ�
				</td>
				<td>
					�ϼ�
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
						<s:property value="#dataMap.s_memberNo" />&nbsp;
					</td>
					<td>
						<s:property value="#dataMap.s_memberName"/>
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
						<s:set value="format(#dataMap.delayfee)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.delayfee!=null">
						<s:set var="delayfee" value="#dataMap.delayfee+#delayfee"></s:set>
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
					�ܼƣ�
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
				<td >
					&nbsp;
				</td>
				<td align="right">
					<s:set value="format(#fundio)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set value="format(#delayfee)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set value="format(#closepl)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set value="format(#holdpl)" var="a"/>
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set value="format(#plsum)" var="a"/>
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
		</td>
		</tr>
		</table>
	</body>
</html>
