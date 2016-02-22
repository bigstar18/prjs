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
			�ʽ���ˮ��ϸ
		</div>
		<table width="240%" border="1" align="center" cellpadding="0"
			cellspacing="0" bordercolor="#000000"
			style='border-collapse: collapse;'>
			<s:set var="num" value="1"></s:set>
			<s:set var="amount" value="0"></s:set>
			<s:set var="balance" value="0"></s:set>
			<tr class="report_w14">
				<td width="5%">
					���
				</td>
				<td width="11%">
					�����˺�
				</td>
				<td width="11%">
					�ͻ�����
				</td>
				
				<td width="11%">
					�䶯���
				</td>
				<td width="11%">
					�����
				</td>
				<td width="11%">
					��������
				</td>
				<td width="9%">
					����
				</td>
				<td width="11%">
					ʱ��
				</td>
				<td width="11%">
					��ע
				</td>
			</tr>
			<s:iterator value="dataList()" var="dataMap">
				<tr class="report_w14_neirong">
					<td>
						<s:property value="#num" />
						<s:set var="num" value="#num+1"></s:set>
					</td>
					<td>
						<s:property value="#dataMap.customerno"/>&nbsp;
					</td>
					<td>
						<s:property value="#dataMap.customerName"/>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.amount)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.amount!=null">
							<s:set var="amount" value="#dataMap.amount+#amount"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.balance)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.balance!=null">
							<s:set var="balance" value="#dataMap.balance+#balance"></s:set>
						</s:if>
					</td>
					<td>
						<s:property value="#dataMap.voucherno" />&nbsp;
					</td>
					<td>
						<s:set var="mapkey" value="#dataMap.oprcode" ></s:set>
						<s:property value="codeMap[#mapkey]"/>
					</td>
					<td>
						<s:date name="#dataMap.createtime" format="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>
						&nbsp;
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
					<s:set var="a" value="format(#amount)" />
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
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
