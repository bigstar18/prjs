<%@ page pageEncoding="GBK"%>
<%@ include file="/public/reportCss.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
		<div class="report_w16_title">
			�Ӽ�ǩԼ�ͻ���ϸ
		</div>
		<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" bordercolor="#000000">
	 	 <tr>
	    <td>
		<table width="60%" border="0" align="left" cellpadding="0" cellspacing="0" bordercolor="#000000" style='border-collapse: collapse;' height="26">
	 	 <tr>
	    <td align="left">ǩԼ��ʼ���ڣ�${oldParams["primary.opentime[>=][date]"]}</td>
	    <td align="left">ǩԼ�������ڣ�${oldParams["primary.opentime[<=][date]"]}</td>
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
			<s:set var="num" value="0"></s:set>
			<tr class="report_w14">
				<td width="5%" >
					���
				</td>
				<td width="8%" >
					�����˺�
				</td>
				<td width="8%" >
					�ͻ�����
				</td>
				<td width="10%" >
					�ͻ�״̬
				</td>
				<td width="15%" >
					ǩԼ����
				</td>
				<td width="15%" >
					ǩԼ����
				</td>
				<td width="15%" >
					����¼����
				</td>
			</tr>
			<s:iterator value="dataList()" var="dataMap">
				<tr class="report_w14_neirong">
					<td>
						<s:set var="num" value="#num+1"></s:set>
						<s:property value="#num" />
					</td>
					<td>
						<s:property value="#dataMap.customerno" />&nbsp;
					</td>
					<td>
						<s:property value="#dataMap.customername" />
						
					</td>
					<td>
						<s:set var='status' value="#dataMap.status" />
						<s:property value="customerStatusMap[#status]" />
						
					</td>
					<td>
						<s:date name="#dataMap.opentime" format="yyyy-MM-dd"/>
					</td>
					<td>
						<s:property value="#dataMap.bankname" />
						
					</td>
					<td>
						<s:date name="#dataMap.logintime" format="yyyy-MM-dd"/>
						
					</td>
				
			</s:iterator>
		
		</table>
		</td>
		</tr>
		</table>
	</body>
</html>
