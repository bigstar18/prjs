<%@ page pageEncoding="GBK"%>
<%@ include file="/public/session.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<link href="${basePath }/report/report_css.css" rel="stylesheet" type="text/css" />
	</head>
	<body class="report_body">
	<br><br><br>
	<div class="report_w16_title">交易报表</div>
	<table width="1031" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#000000" style='border-collapse: collapse;' bgcolor="#ffbfc9" height="8">
  <tr class="report_w14">
    <td  width="7%">数量</td>
  </tr>
  	<s:set var="qty" value="0"></s:set>
  	
	<s:iterator value="list" var="dataMap">
		<tr class="report_w12">
			<td>
				<s:property value="#dataMap.qty" />
				<s:set var="qty" value="#dataMap.qty+#qty"></s:set>
			</td>
		</tr>
		</s:iterator>
		<tr>
			<td>
				<s:property value="#qty"/>
			</td>
		</tr>
		</table>
		<br><br><br>
	</body>
</html>

