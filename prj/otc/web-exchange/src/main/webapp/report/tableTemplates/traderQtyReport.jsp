<%@ page pageEncoding="GBK"%>
<%@ include file="/public/reportCss.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
	<br><br><br>
	<div class="report_w16_title">����������������</div>
	<table width="1031" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#000000" style='border-collapse: collapse;' bgcolor="#ffbfc9" height="8">
  <tr class="report_w14">
    <td width="14%">����</td>
    <td width="14%">��Ա���</td>
    <td width="14%">��Ա����</td>
    <td width="14%">���������׶�</td>
    <td width="14%">������������</td>   
    <td  width="14%">����������������</td>
    <td width="16%">���������ջ�ת������</td>
  </tr>
  	<s:set var="tradeFunds" value="0"></s:set>
  	<s:set var="tradeQty" value="0"></s:set>
  	<s:set var="mktfee" value="0"></s:set>
  	<s:set var="memberfee" value="0"></s:set>
	<s:iterator value="dataList()" var="dataMap">
		<tr class="report_w12">
			<td>
				<s:date name="#dataMap.clearDate" format="yyyy-MM-dd"/>
				
			</td>
			<td>
				<s:property value="#dataMap.memberNo" />
			</td>
			<td>
				<s:property value="#dataMap.memberName" />
			</td>
			<td>
				<s:property value="format(#dataMap.tradeFunds)" />
				<s:set var="tradeFunds" value="#dataMap.tradeFunds+#tradeFunds"></s:set>
			</td>
			<td>
				<s:property value="#dataMap.tradeQty" />
				<s:set var="tradeQty" value="#dataMap.tradeQty+#tradeQty"></s:set>
			</td>
			<td>
				<s:property value="format(#dataMap.mktfee)" />
				<s:set var="mktfee" value="#dataMap.mktfee+#mktfee"></s:set>
			</td>
			<td>
				<s:property value="format(#dataMap.memberfee)" />
				<s:set var="memberfee" value="#dataMap.memberfee+#memberfee"></s:set>
			</td>
			
		</tr>
		</s:iterator>
		<tr>
			<td>�ϼ�:
			</td>
			<td colspan="3" align="right">
				<s:property value="format(#tradeFunds)"/>
			</td>
			<td align="right">
				<s:property value="#tradeQty"/>
			</td>
			<td align="right">
				<s:property value="format(#mktfee)"/>
			</td>
			<td align="right">
				<s:property value="format(#memberfee)"/>
			</td>
		</tr>
		</table>
		<br><br><br>
	</body>
</html>

