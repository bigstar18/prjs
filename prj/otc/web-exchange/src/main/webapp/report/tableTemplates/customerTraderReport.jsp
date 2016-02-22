<%@ page pageEncoding="GBK"%>
<%@ include file="/public/reportCss.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
	<br>
	<br>
	<div class="report_w16_title">���ױ���</div>
	<table width="100%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#000000" style='border-collapse: collapse;'>
  <tr class="report_w14">
    <td width="9%">���</td>
    <td width="9%">��������</td>
    <td width="9%">����</td>
    <td width="9%">�˺�</td>
    <td width="8%">��Ʒ</td>   
    <td  width="7%">����</td>
    <td width="7%">����/����</td>
    <td  width="7%">���ּ�</td>
    <td  width="7%">ƽ�ּ�</td>
    <td  width="7%">�ֲּ�</td>
    <td  width="7%">������</td>
    <td  width="7%">ӯ��</td>
  </tr>
  	<s:set var="qty" value="0"></s:set>
  	<s:set var="tradefee" value="0"></s:set>
  	<s:set var="close_pl" value="0"></s:set>
  	
	<s:iterator value="dataList()" var="dataMap">
		<tr class="report_w14_neirong">
			<td>
				<!--<s:property value="#dataMap." />-->
			</td>
			<td>
				<s:date name="#dataMap.atcleardate" format="yyyy-mm-dd" />
			</td>
			<td>
				<s:property value="#dataMap.tradeno" />&nbsp;
			</td>
			<td>
				<s:property value="#dataMap.customerno" />&nbsp;
			</td>
			<td>
				<s:property value="#dataMap.commodityName" />
			</td>
			<td>
				<s:property value="#dataMap.qty" />&nbsp;
				<s:set var="qty" value="#dataMap.qty+#qty"></s:set>
			</td>
			<td>
				<s:set var="bs" value="#dataMap.bs_flag" />
				<s:property value="flagMap[#bs]" />
			</td>
			<td>
				<s:property value="format(#dataMap.openPrice)"></s:property>&nbsp;
			</td>
			<td>
				<s:property value="format(#dataMap.closeprice)" />&nbsp;
			</td>
			<td>
				<s:property value="format(#dataMap.holdprice)" />&nbsp;
			</td>
			<td>
				<s:property value="#dataMap.tradefee" />&nbsp;
				<s:set var="tradefee" value="#dataMap.tradefee+#tradefee"></s:set>
			</td>
			<td>
				<s:property value="#dataMap.close_pl" />&nbsp;
				<s:set var="close_pl" value="#dataMap.close_pl+#close_pl"></s:set>
			</td>
			
		</tr>
		</s:iterator>
		<tr class="report_w14">
			<td>�ϼ�:
			</td>
			<td colspan="5" align="right">&nbsp;
				<s:property value="#qty"/>&nbsp;
			</td>
			<td colspan="5" align="right">&nbsp;
				<s:property value="#tradefee"/>&nbsp;
			</td>
			<td align="right">
				<s:property value="#close_pl"/>&nbsp;
			</td>
		</tr>
		</table>
		<br>
		<br>
	</body>
</html>

