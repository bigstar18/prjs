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
			��������ǩԼ��¼��
		</div>
		<table width="60%" border="0" align="center" cellpadding="0" cellspacing="0" bordercolor="#000000" style='border-collapse: collapse;' height="26">
	 	 <tr>
	    <td align="center">�����ˣ�${CURRENUSERID }</td>
	  <%   java.util.Date d = new java.util.Date(); 
			java.text.SimpleDateFormat dformat = new java.text.SimpleDateFormat("yyyy-MM-dd"); 
			String datetime = dformat.format(d); 
	  %>
	   <td align="center">��ѯ���ڣ�<%=datetime%> </td>
	 	 </tr>
	 	 <tr><td height="10">&nbsp;</td></tr>
		</table>
		<table width="100%" border="1" align="center" cellpadding="0"
			cellspacing="0" bordercolor="#000000"
			style='border-collapse: collapse;' bgcolor="#ffbfc9" height="26">
			<s:set var="begincapital" value="0"></s:set>
			<s:set var="fundio" value="0"></s:set>
			<s:set var="closepl" value="0"></s:set>
			<s:set var="delayfee" value="0"></s:set>
			<s:set var="endcapital" value="0"></s:set>
			<s:set var="plsum" value="0"></s:set>
			<s:set var="holdpl" value="0"></s:set>
			<s:set var="num" value="1"></s:set>
			<tr class="report_w14">
				<td width="5%">
					���
				</td>
				<td width="11%">
					���
				</td>
				<td width="13%">
					�����˻�
				</td>
				<td width="13%">
					����
				</td>
				<td width="13%">
					ǩԼ����
				</td>
				<td width="9%">
					ǩԼ����
				</td>
				
				<td width="8%">
					ǩԼʱ��
				</td>
				<td width="9%">
					��Լ����
				</td>
				<td width="8%">
					��Լʱ��
				</td>
				<td width="8%">
					�˻�����
				</td>
			</tr>
			<s:iterator value="dataList()" var="dataMap">
				<tr>
					<td>
						<s:property value="#num" />
						<s:set var="num" value="#num+1"></s:set>
					</td>
					<td>
						<s:property value="#dataMap.firmid"/>&nbsp;
					</td>
					<td>
						<s:property value="#dataMap.contact" />
						
					</td>
					<td>
						<s:property value="#dataMap.name" />
						
					</td>
					<td>
						<s:property value="#dataMap.bankname" />
						
					</td>
					<td>
						<s:date name="#dataMap.signtime"  format="yyyy-MM-dd"></s:date>
					</td>
					
					<td>
						<s:date name="#dataMap.signtime"  format="HH:mm:ss"></s:date>
					</td>
					<td>
						<s:date name="#dataMap.cancletime"  format="yyyy-MM-dd"></s:date>
					</td>
					<td>
						<s:date name="#dataMap.cancletime"  format="HH:mm:ss"></s:date>
					</td>
					<td>
						<s:set var="mapKey" value="#dataMap.firmtype"></s:set>
						<s:property value="bankMap[#mapKey]" />
					</td>
				</tr>
			</s:iterator>
		
		</table>
		<br>
		<br>
		<br>
	</body>
</html>
