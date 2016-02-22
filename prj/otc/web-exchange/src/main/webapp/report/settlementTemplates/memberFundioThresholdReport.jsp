<%@ page pageEncoding="GBK"%>
<%@ include file="/public/reportCss.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
	<div class="report_w16_titlemin"> �����������</div>
	<div class="report_w16_title">��Ա������ֵ�����</div>
<table width="150%" border="0" align="center" cellpadding="0" cellspacing="0">
<tr>
  <td>
	<table width="50%" border="0" align="left" cellpadding="0" cellspacing="0" bordercolor="#000000" style='border-collapse: collapse;padding-left: 20px;' height="20">
	  <tr>
	    <td align="left">��ʼ���ڣ�${oldParams["trunc(primary.cleardate)[>=][date]"]}</td>
	    <td align="left">�������ڣ�${oldParams["trunc(primary.cleardate)[<=][date]"]}</td>
	    <td align="left">�����ˣ�${CURRENUSERID }</td>
	  </tr>
	</table>
	</td>
	</tr>
	<tr>
	<td>
	<table width="100%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#000000" style='table-layout:fixed;border-collapse: collapse;'>
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
				<tr class="report_w14_neirong">
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
						<s:set value="format(#dataMap.customerbalancesum)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.customerbalancesum!=null">
						<s:set var="customerbalancesum" value="#dataMap.customerbalancesum+#customerbalancesum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.endcapital)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.endcapital!=null">
						<s:set var="endcapital" value="#dataMap.endcapital+#endcapital"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.minriskfund)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.minriskfund!=null">
						<s:set var="minriskfund" value="#dataMap.minriskfund+#minriskfund"></s:set>
						</s:if>
					</td>
				</tr>
			</s:iterator>
			<tr class="report_w14">
			   <td>�ϼ�</td>
			   <td>&nbsp;</td>
			   <td>&nbsp;</td>
			   <td>&nbsp;</td>
			   <td>&nbsp;</td>
			   <td align="right"><s:set value="format(#customerbalancesum)" var="a"/>
			   <fmt:formatNumber value="${a }" pattern="#,##0.00"/>&nbsp;
			   </td>
			   <td align="right">&nbsp;</td>
			   <td align="right"><s:set value="format(#minriskfund)" var="a"/>
			   <fmt:formatNumber value="${a }" pattern="#,##0.00"/>
			   </td> 
			</tr>
		</table>
		</td>
		</tr>
		</table>
	</body>
</html>
