<%@ page pageEncoding="GBK"%>
<%@ include file="/public/reportCss.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
		<div class="report_w16_titlemin">�����������</div>
		<div class="report_w16_title">�������л�ת�����¼��</div>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
<tr>
  <td>
		<table width="40%" border="0" align="center" cellpadding="0" cellspacing="0" bordercolor="#000000" style='border-collapse: collapse;padding-left: 20px;' height="20">
	 	 <tr>
	    <td align="left">��ʼ���ڣ�${oldParams["trunc(primary.b_date)[>=][date]"]}&nbsp;</td>
	    <td align="left">�������ڣ�${oldParams["trunc(primary.b_date)[<=][date]"]}&nbsp;</td>
	    <td align="left">�����ˣ�${CURRENUSERID }&nbsp;</td>
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
				<td width="10%">
					���
				</td>
				<td width="18%">
					��������
				</td>
				<td width="18%">
					��������
				</td>
				<td width="18%">
					ת���ܺ�
				</td>
			</tr>
			<s:iterator value="dataList()" var="dataMap">
				<tr class="report_w14_neirong">
					<td>
						<s:property value="#num" />
						<s:set var="num" value="#num+1"></s:set>
					</td>
					<td>
						<s:date name="#dataMap.b_date" format="yyyy-MM-dd"/>
					</td>
					<td>
						<s:property value="#dataMap.bankName"/>
					</td>
					<td>
						<s:property value="format(#dataMap.sumTransferFund)" />
						
					</td>
				
					
				</tr>
			</s:iterator>
		</table>
		</td>
		</tr>
		</table>
	</body>
</html>
