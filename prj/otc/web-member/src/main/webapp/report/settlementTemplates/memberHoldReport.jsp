<%@ page pageEncoding="GBK"%>
<%@ include file="/public/reportCss.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
		<div class="report_w16_titlemin">�����������</div>
		<div class="report_w16_title">��Ա�ֲֻ��ܱ�</div>
		<table width="180%" border="0" align="center" cellpadding="0" cellspacing="0">
<tr>
  <td>
		<table width="50%" border="0" align="left" cellpadding="0"
			cellspacing="0" bordercolor="#000000"
			style='border-collapse: collapse;padding-left: 20px;' height="20">
			<tr>
				<td align="left">
					��Ա��ţ�${memberNo}
				</td>
				<td align="left">
					��Ա���ƣ�${memberName}
				</td>
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
		</td>
		</tr>
		<tr>
		<td>
		<table width="100%" border="1" align="center" cellpadding="0"
			cellspacing="0" bordercolor="#000000"
			style='table-layout: fixed; border-collapse: collapse;'>
			<s:set var="customerbuyqty" value="0"></s:set>
			<s:set var="customersellqty" value="0"></s:set>
			<s:set var="customernetqty" value="0"></s:set>
			<s:set var="memberbuyqty" value="0"></s:set>
			<s:set var="membersellqty" value="0"></s:set>
			<s:set var="membernetqty" value="0"></s:set>
			<s:set var="netqty" value="0"></s:set>
			<s:set var="maxnethold" value="0"></s:set>
			<s:set var="memberdelayfee" value="0"></s:set>
			<s:set var="customerdelayfee" value="0"></s:set>
			<s:set var="delayfee" value="0"></s:set>
			<s:set var="capital" value="0"></s:set>
			<s:set var="num" value="1"></s:set>
			<tr class="report_w14">
				<td width="4%" rowspan="2">
					���
				</td>
				<td width="5%" rowspan="2">
					��������
				</td>
				<td width="5%" rowspan="2">
					��Ʒ
				</td>
				<td width="15%" colspan="3">
					�ͻ�
				</td>
				<td width="15%" colspan="3">
					��Ա
				</td>
				<td width="6%" rowspan="2">
					���ֲֺϼ�
				</td>
				<td width="5%" rowspan="2">
					������
				</td>
				<td width="23%" colspan="3">
					���ڷ�
				</td>
				<td width="8%" rowspan="2">
					��ĩȨ��
				</td>
			</tr>
			<tr class="report_w14">
				<td>
					��ֲ�
				</td>
				<td>
					���ֲ�
				</td>
				<td>
					���ֲ�
				</td>
				<td>
					��ֲ�
				</td>
				<td>
					���ֲ�
				</td>
				<td>
					���ֲ�
				</td>
				<td>
					���ر��Ա
				</td>
				<td>
					�տͻ�
				</td>
				<td>
					�ۺϻ�Ա����
				</td>
			</tr>
			<s:iterator value="dataList()" var="dataMap">
				<tr class="report_w14_neirong">
					<td>
						<s:property value="#num" />
						<s:set var="num" value="#num+1"></s:set>
					</td>
					<td>
						<s:date name="#dataMap.clearDate" format="yyyy-MM-dd"></s:date>
					</td>
					<td>
						<s:property value="#dataMap.commodityname" />
					</td>
					<td align="right">
						<s:set var="a" value="#dataMap.customerbuyqty" />
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.customerbuyqty!=null">
							<s:set var="customerbuyqty"
								value="#dataMap.customerbuyqty+#customerbuyqty"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="#dataMap.customersellqty" />
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.customersellqty!=null">
							<s:set var="customersellqty"
								value="#dataMap.customersellqty+#customersellqty"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="#dataMap.customernetqty" />
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.customernetqty!=null">
							<s:set var="customernetqty"
								value="#dataMap.customernetqty+#customernetqty"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="#dataMap.memberbuyqty" />
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.memberbuyqty!=null">
							<s:set var="memberbuyqty"
								value="#dataMap.memberbuyqty+#memberbuyqty"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="#dataMap.membersellqty" />
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.membersellqty!=null">
							<s:set var="membersellqty"
								value="#dataMap.membersellqty+#membersellqty"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="#dataMap.membernetqty" />
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.membernetqty!=null">
							<s:set var="membernetqty"
								value="#dataMap.membernetqty+#membernetqty"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="#dataMap.netqty" />
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.netqty!=null">
							<s:set var="netqty" value="#dataMap.netqty+#netqty"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="#dataMap.maxnethold" />
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.maxnethold!=null">
							<s:set var="maxnethold" value="#dataMap.maxnethold+#maxnethold"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.memberdelayfee)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.memberdelayfee!=null">
							<s:set var="memberdelayfee"
								value="#dataMap.memberdelayfee+#memberdelayfee"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.customerdelayfee)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.customerdelayfee!=null">
							<s:set var="customerdelayfee"
								value="#dataMap.customerdelayfee+#customerdelayfee"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.delayfee)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.delayfee!=null">
							<s:set var="delayfee" value="#dataMap.delayfee+#delayfee"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.capital)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.capital!=null">
							<s:set var="capital" value="#dataMap.capital+#capital"></s:set>
						</s:if>
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
					<s:set var="a" value="#customerbuyqty" />
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td align="right">
					<s:set var="a" value="#customersellqty" />
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td align="right">
					<s:set var="a" value="#customernetqty" />
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td align="right">
					<s:set var="a" value="#memberbuyqty" />
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td align="right">
					<s:set var="a" value="#membersellqty" />
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td align="right">
					<s:set var="a" value="#membernetqty" />
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td align="right">
					<s:set var="a" value="#netqty" />
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td align="right">
					<s:set var="a" value="#maxnethold" />
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td align="right">
					<s:set var="a" value="format(#memberdelayfee)" />
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set var="a" value="format(#customerdelayfee)" />
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set var="a" value="format(#delayfee)" />
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					&nbsp;
				</td>
			</tr>
		</table>
	</body>
</html>