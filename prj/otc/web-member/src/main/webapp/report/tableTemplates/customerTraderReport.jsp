<%@ page pageEncoding="GBK"%>
<%@ include file="/public/reportCss.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
	<br><br>
	<div class="report_w16_title">���ױ���</div>
	<table width="180%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#000000" style='border-collapse: collapse;'>
  <tr class="report_w14">
    <td width="4%">���</td>
    <td width="11%">��������</td>
    <td width="10%">����</td>
    <td width="10%">�˺�</td>
    <td width="8%">��Ʒ</td>   
    <td  width="7%">����</td>
    <td width="8%">����/����</td>
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
				<s:property value="#dataMap.tradeno" />
			</td>
			<td>
				<s:property value="#dataMap.customerno" />
			</td>
			<td>
				<s:property value="#dataMap.commodityName" />
			</td>
			<td>
				<s:set var="a" value="#dataMap.qty" />
				<fmt:formatNumber value="${a }" pattern="#,##0"/>
				<s:set var="qty" value="#dataMap.qty+#qty"></s:set>
			</td>
			<td>
				<s:set var="bs" value="#dataMap.bs_flag" />
				<s:property value="flagMap[#bs]" />
			</td>
			<td>
				<s:set var="a" value="format(#dataMap.openPrice)">
				<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</s:property>
			</td>
			<td>
				<s:set var="a" value="format(#dataMap.closeprice)" />
				<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
			</td>
			<td>
				<s:set var="a" value="format(#dataMap.holdprice)" />
				<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
			</td>
			<td>
				<s:set var="a" value="format(#dataMap.tradefee)" />
				<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				<s:set var="tradefee" value="#dataMap.tradefee+#tradefee"></s:set>
			</td>
			<td>
				<s:set var="a" value="format(#dataMap.close_pl)" />
				<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				<s:set var="close_pl" value="#dataMap.close_pl+#close_pl"></s:set>
			</td>
			
		</tr>
		</s:iterator>
		<tr class="report_w14">
			<td>�ϼ�:
			</td>
			<td colspan="5" align="right">
				<s:set var="a" value="#qty"/>
				<fmt:formatNumber value="${a }" pattern="#,##0"/>
			</td>
			<td colspan="5" align="right">
				<s:set var="a" value="format(#tradefee)"/>
				<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
			</td>
			<td align="right">
				<s:set var="a" value="format(#close_pl)"/>
				<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
			</td>
		</tr>
		</table>
		<br><br>
	</body>
</html>