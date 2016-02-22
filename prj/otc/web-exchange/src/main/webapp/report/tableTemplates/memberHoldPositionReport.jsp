<%@ page pageEncoding="GBK"%>
<%@ include file="/public/reportCss.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
	<br><br>
	<div class="report_w16_title">�ֲ���ϸ</div>
	<table width="200%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#000000" style='border-collapse: collapse;'>
  <tr class="report_w14">
  	<td width="3%">���</td>
  	<td width="6%">��������</td>
    <td width="10%">����ʱ��</td>
    <td width="6%">���ּ�</td>
    <td width="7%">�����˺�</td>
    <td width="7%">���ֵ���</td>
    <td width="5%">��Ʒ</td>
    <td width="7%">��������</td>
    <td  width="6%">�ֲ���</td>
    <td  width="7%">�ֲּ�</td>
    <td  width="6%">�����</td>
    <td  width="7%">�ֲ�ӯ��</td>
    <td  width="7%">ռ�ñ�֤��</td>
    <td  width="7%">�����ڷ�</td>
    <td width="7%">�ر��Ա</td>
  </tr>
  <s:set var="num" value="1"></s:set>
  <s:set var="openprice" value="0"></s:set>
  <s:set var="holdqty" value="0"></s:set>
  <s:set var="holdprice" value="0"></s:set>
  <s:set var="clearprice" value="0"></s:set>
  <s:set var="holdpl" value="0"></s:set>
  <s:set var="holdmargin" value="0"></s:set>
  <s:set var="delayfee" value="0"></s:set>
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
						<s:date name="#dataMap.holdtime" format="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.openprice)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.openprice!=null">
							<s:set var="openprice" value="#openprice+#dataMap.openprice"></s:set>
						</s:if>
					</td>
					<td>
						<s:property value="#dataMap.membersignno" />&nbsp;
					</td>
					<td>
						<s:property value="#dataMap.opentradeno" />&nbsp;
					</td>
					<td>
						<s:property value="#dataMap.commodityname" />
					</td>
					<td>
						<s:set var="bs" value="#dataMap.bs_flag" />
						<s:property value="flagMap[#bs]" />
					</td>
					<td align="right">
						<s:set value="#dataMap.holdqty" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.holdqty!=null">
							<s:set var="holdqty" value="#holdqty+#dataMap.holdqty"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.holdprice)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.holdprice!=null">
							<s:set var="holdprice" value="#holdprice+#dataMap.holdprice"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.clearprice)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.clearprice!=null">
							<s:set var="clearprice" value="#clearprice+#dataMap.clearprice"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.holdpl)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.holdpl!=null">
							<s:set var="holdpl" value="#holdpl+#dataMap.holdpl"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.holdmargin)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.holdmargin!=null">
							<s:set var="holdmargin" value="#holdmargin+#dataMap.holdmargin"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set value="format(#dataMap.delayFee)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.delayFee!=null">
							<s:set var="delayFee" value="#delayFee+#dataMap.delayFee"></s:set>
						</s:if>
					</td>
					<td>
						<s:property value="#dataMap.s_memberno" />&nbsp;
					</td>
				</tr>
			</s:iterator>
			<tr class="report_w14">
					<td>
						�ϼƣ�
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
					<td align="right">
						&nbsp;
						
					</td>
					<td>
						&nbsp;
					</td>
					
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
						
					</td>
					<td align="right">
						<s:set value="#holdqty" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
					</td>
					<td align="right">
						&nbsp;
					</td>
					<td align="right">
						&nbsp;
					</td>
					<td align="right">
						<s:set value="format(#holdpl)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
					</td>
					<td align="right">
						<s:set value="format(#holdmargin)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
					</td>
					<td align="right">
						<s:set value="format(#delayFee)" var="a"/>
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
		</table>
		<br><br>
	</body>
</html>
