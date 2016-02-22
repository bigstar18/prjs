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
			��Ա�����֤����ϸ��
		</div>
		<table width="60%" border="0" align="center" cellpadding="0"
			cellspacing="0" bordercolor="#000000"
			style='border-collapse: collapse;' height="26">
			<tr>
				<td align="left">
					��ʼ���ڣ�${oldParams["trunc(primary.cleardate)[>=][date]"]}
				</td>
				<td align="left">
					�������ڣ�${oldParams["trunc(primary.cleardate)[<=][date]"]}
				</td>
				<td align="left">
					�����ˣ�${CURRENUSERID }
				</td>
			</tr>
		</table>
		<table width="100%" border="1" align="center" cellpadding="0"
			cellspacing="0" bordercolor="#000000"
			style='table-layout: fixed; border-collapse: collapse;'
			bgcolor="#ffbfc9" height="26">
			<s:set var="num" value="1"></s:set>
			<s:set var="fundin" value="0"></s:set>
			<s:set var="fundout" value="0"></s:set>
			<s:set var="MemberFee" value="0"></s:set>
			<s:set var="todaybalance" value="0"></s:set>
			<tr class="report_w14">
				<td width="8%">
					���
				</td>
				<td width="15%">
					��������
				</td>
				<td width="15%">
					�������
				</td>
				<td width="15%">
					���ճ���
				</td>
				<td width="10%">
					��������
				</td>
				<td width="10%">
					���
				</td>
				<td width="12%">
					��Ա���
				</td>
				<td width="15%">
					��Ա����
				</td>
			</tr>
			<s:iterator value="dataList()" var="dataMap">
				<tr>
					<td>
						<s:property value="#num" />
						<s:set var="num" value="#num+1"></s:set>
					</td>
					<td>
						<s:date name="#dataMap.cleardate" format="yyyy-MM-dd" />
					</td>
					<td align="right">
						<s:property value="format(#dataMap.fundin)" />&nbsp;
						<s:if test="#dataMap.fundin!=null">
							<s:set var="fundin" value="#dataMap.fundin+#fundin"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:property value="format(#dataMap.fundout)" />&nbsp;
						<s:if test="#dataMap.fundout!=null">
							<s:set var="fundout" value="#dataMap.fundout+#fundout"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:property value="format(#dataMap.MemberFee)" />&nbsp;
						<s:if test="#dataMap.MemberFee!=null">
							<s:set var="MemberFee" value="#dataMap.MemberFee+#MemberFee"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:property value="format(#dataMap.todaybalance)" />&nbsp;
						<s:if test="#dataMap.todaybalance!=null">
							<s:set var="todaybalance"
								value="#dataMap.todaybalance+#todaybalance"></s:set>
						</s:if>
					</td>
					<td>
						<s:property value="#dataMap.memberno" />&nbsp;
					</td>
					<td>
						<s:property value="#dataMap.membername" />
					</td>
				</tr>
			</s:iterator>
			<tr>
				<td>
					�ϼ�:
				</td>
				<td>
					&nbsp;
				</td>
				<td align="right">
					<s:property value="format(#fundin)" />&nbsp;
				</td>
				<td align="right">
					<s:property value="format(#fundout)" />&nbsp;
				</td>
				<td align="right">
					<s:property value="format(#MemberFee)" />&nbsp;
				</td>
				<td align="right" >
					<s:property value="format(#todaybalance)" />&nbsp;
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
		<br>
	</body>
</html>
