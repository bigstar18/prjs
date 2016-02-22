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
	<div class="report_w16_title">��Ա������ֵ�����</div>
	<table width="60%" border="0" align="center" cellpadding="0" cellspacing="0" bordercolor="#000000" style='border-collapse: collapse;' height="26">
	  <tr>
	    <td align="left">��ʼ���ڣ�${oldParams["trunc(primary.cleardate)[>=][date]"]}</td>
	    <td align="left">�������ڣ�${oldParams["trunc(primary.cleardate)[<=][date]"]}</td>
	    <td align="left">�����ˣ�${CURRENUSERID }</td>
	  </tr>
	</table>
	<table width="100%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#000000" style='table-layout:fixed;border-collapse: collapse;' bgcolor="#ffbfc9" height="26">
  <s:set var="num" value="1"></s:set>
  <s:set var="customerbalancesum" value="0"></s:set>
  <s:set var="endcapital" value="0"></s:set>
  <s:set var="minriskfund" value="0"></s:set>
  <tr class="report_w14">
    <td width="8%">���</td>
    <td width="8%">��������</td>
    <td width="10%">��Ա���</td>
    <td width="20%">��Ա����</td>
    <td width="8%">��Ա����</td>
    <td width="18%">�����ͻ��ʽ���Ԫ��</td>
    <td width="14%">��ĩȨ�棨Ԫ��</td>
    <td width="14%">��ֵ����ֵ����Ԫ��</td>
    
  </tr>
			<s:iterator value="dataList()" var="dataMap">
				<tr>
				    <td>
						<s:property value="#num" />
						<s:set var="num" value="#num+1"></s:set>
					</td>
					<td>
						<s:date name="#dataMap.cleardate" format="yyyy-MM-dd"/>
					</td>
					<td>
						<s:property value="#dataMap.memberno" />&nbsp;
					</td>
					<td>
						<s:property value="#dataMap.membername" />
					</td>
					<td>
						<s:property value="#dataMap.membertype" />
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.customerbalancesum)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.customerbalancesum!=null">
						<s:set var="customerbalancesum" value="#dataMap.customerbalancesum+#customerbalancesum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.endcapital)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.endcapital!=null">
						<s:set var="endcapital" value="#dataMap.endcapital+#endcapital"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.minriskfund)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.minriskfund!=null">
						<s:set var="minriskfund" value="#dataMap.minriskfund+#minriskfund"></s:set>
						</s:if>
					</td>
				</tr>
			</s:iterator>
			<tr>
			   <td>�ϼ�</td>
			   <td>&nbsp;</td>
			   <td>&nbsp;</td>
			   <td>&nbsp;</td>
			   <td>&nbsp;</td>
			   <td align="right"><s:set var="a" value="format(#customerbalancesum)"/><fmt:formatNumber value="${a }" pattern="#,##0.00"/></td>
			   <td align="right"><s:set var="a" value="format(#endcapital)"/><fmt:formatNumber value="${a }" pattern="#,##0.00"/></td>
			   <td align="right"><s:set var="a" value="format(#minriskfund)"/><fmt:formatNumber value="${a }" pattern="#,##0.00"/></td> 
			</tr>
		</table>
	</body>
</html>