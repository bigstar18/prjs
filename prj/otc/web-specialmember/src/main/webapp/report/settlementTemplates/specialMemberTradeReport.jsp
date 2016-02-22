<%@ page pageEncoding="GBK"%>
<%@ include file="/public/reportCss.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
	
		<br>
		<div class="report_w16_titlemin">
		      �����������
		</div>
		<div class="report_w16_title">
			�ر��Ա�ɽ����ܱ�
		</div>
		<table width="60%" border="0" align="center" cellpadding="0" cellspacing="0" bordercolor="#000000" style='border-collapse: collapse;' height="26">
	 	 <tr>
	    <td align="left">��ʼ���ڣ�${oldParams["trunc(primary.cleardate)[>=][date]"]}</td>
	    <td align="left">�������ڣ�${oldParams["trunc(primary.cleardate)[<=][date]"]}</td>
	    <td align="left">�����ˣ�${CURRENUSERID }</td>
	 	 </tr>
	 	 <tr><td height="10">&nbsp;</td></tr>
		</table>
		<table width="100%" border="1" align="center" cellpadding="0"
			cellspacing="0" bordercolor="#000000"
			style='border-collapse: collapse;' bgcolor="#ffbfc9" height="26">
			<s:set var="qtysum" value="0"></s:set>
			<s:set var="fundsum" value="0"></s:set>
			
			<s:set var="endcapital" value="0"></s:set>
			<s:set var="num" value="1"></s:set>
			<tr class="report_w14">
				<td width="8%">
					���
				</td>
				<td width="16%">
					��������
				</td>
				<td width="10%">
					��Ʒ
				</td>
				<td width="12%">
					�ر��Ա���
				</td>
				<td width="16%">
					�ر��Ա����
				</td>
				<td width="10%">
					�ɽ���
				</td>
				<td width="14%">
					�ɽ����
				</td>
				<td width="14%">
					��ĩȨ��
				</td>
			</tr>
			<s:iterator value="dataList()" var="dataMap">
				<tr>
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
					<td>
						<s:property value="#dataMap.s_memberno" />&nbsp;
					</td>
					<td>
						<s:property value="#dataMap.s_membername" />
					</td>
					<td align="right">
						<s:set var="a" value="#dataMap.qtysum" />
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.qtysum!=null">
						<s:set var="qtysum" value="#dataMap.qtysum+#qtysum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.fundsum)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.fundsum!=null">
						<s:set var="fundsum" value="#dataMap.fundsum+#fundsum"></s:set>
						</s:if>
					</td>
					
					<td align="right">
						<s:set var="a" value="format(#dataMap.endcapital)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.endcapital!=null">
						<s:set var="endcapital" value="#dataMap.endcapital+#endcapital"></s:set>
						</s:if>
					</td>
				</tr>
			</s:iterator>
			<tr>
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
				<td>
					&nbsp;
				</td>
				<td align="right">
					<s:set var="a" value="#qtysum" />
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td align="right">
					<s:set var="a" value="format(#fundsum)" />
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set var="a" value="format(#endcapital)" />
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
			</tr>
		</table>
		<br>
		<br>
		<br>
	</body>
</html>
