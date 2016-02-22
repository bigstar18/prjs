<%@ page pageEncoding="GBK"%>
<%@ include file="/public/reportCss.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
	<br><br>
	<div class="report_w16_title">���ڷ�</div>
	<table width="180%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#000000" style='border-collapse: collapse;'>
  <tr class="report_w14">
    <td width="17%">��ˮ��</td>
    <td width="17%">ҵ�����</td>
     <td width="17%">��ʼ���</td>
    <td width="17%">�䶯���</td>
    <td width="16%">�����</td>
    <td width="17%">����ʱ��</td>
  </tr>
			<s:iterator value="dataList()" var="dataMap">
				<tr class="report_w14_neirong">
					<td>
						<s:property value="#dataMap.fundflowId" />
					</td>
					<td>
						<s:property value="#dataMap.oprcode" />
					</td>
					<td>
						<s:set var="a" value="format(#dataMap.beginbalance)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
					</td>
					<td>
						<s:set var="a" value="format(#dataMap.amount)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
					</td>
					<td>
						<s:set var="a" value="format(#dataMap.balance)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
					</td>
					<td>
						<s:date name="#dataMap.creatTime" format="yyyy-MM-dd HH:mm:ss"/>
					</td>
				</tr>
			</s:iterator>
		</table>
		<br><br>
	</body>
</html>
