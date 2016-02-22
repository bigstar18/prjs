<%@ page pageEncoding="GBK"%>
<%@ include file="/public/reportCss.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
		<div class="report_w16_titlemin">
			�����������
		</div>
		<div class="report_w16_title">
			���������ʽ�����
		</div>
		<table width="150%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td>
					<table width="40%" border="0" align="left" cellpadding="0"
						cellspacing="0" bordercolor="#000000"
						style='border-collapse: collapse; padding-left: 20px;' height="20">
						<tr>
							<td align="center">
								�����ˣ�${CURRENUSERID }
							</td>
							<%
								java.util.Date d = new java.util.Date();
								java.text.SimpleDateFormat dformat = new java.text.SimpleDateFormat(
										"yyyy-MM-dd");
								String datetime = dformat.format(d);
							%>

							<td align="center">
								��ѯ���ڣ�<%=datetime%>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table width="100%" border="1" align="center" cellpadding="0"
						cellspacing="0" bordercolor="#000000"
						style='border-collapse: collapse;'>
						<s:set var="transferfund" value="0"></s:set>
						<s:set var="fundio" value="0"></s:set>
						<s:set var="tradediff" value="0"></s:set>
						<s:set var="todaybalance" value="0"></s:set>
						<s:set var="margin" value="0"></s:set>
						<s:set var="capital" value="0"></s:set>
						<s:set var="num" value="1"></s:set>
						<tr class="report_w14">
							<td width="5%">
								���
							</td>
							<td width="9%">
								��������
							</td>
							<td width="11%">
								����
							</td>
							<td width="11%">
								�����
							</td>
							<td width="10%">
								����ת��
							</td>
							<td width="11%">
								���ױ䶯
							</td>

							<td width="11%">
								�������
							</td>
							<td width="11%">
								���ձ�֤��
							</td>
							<td width="10%">
								����Ȩ��
							</td>
						</tr>
						<s:iterator value="dataList()" var="dataMap">
							<tr class="report_w14_neirong">
								<td>
									<s:property value="#num" />
									<s:set var="num" value="#num+1"></s:set>
								</td>
								<td>
									<s:date name="#dataMap.b_date" format="yyyy-MM-dd"></s:date>
									&nbsp;
								</td>
								<td>
									<s:property value="#dataMap.bankName" />
									&nbsp;
								</td>
								<td align="right">
									<s:set var="a" value="#dataMap.fundio" />
									<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
									<s:if test="#dataMap.fundio!=null">
										<s:set var="fundio" value="#fundilo+#dataMap.fundio"></s:set>
									</s:if>
								</td>
								<td align="right">
									<s:set var="a" value="#dataMap.transferfund" />
									<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
									<s:if test="#dataMap.transferfund!=null">
										<s:set var="transferfund" value="#transferfund+#dataMap.transferfund"></s:set>
									</s:if>
								</td>
								<td align="right">
									<s:set var="a" value="#dataMap.tradediff" />
									<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
									<s:if test="#dataMap.tradediff!=null">
										<s:set var="tradediff" value="#tradediff+#dataMap.tradediff"></s:set>
									</s:if>
								</td>

								<td align="right">
									<s:set var="a" value="#dataMap.todaybalance" />
									<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
									<s:if test="#dataMap.todaybalance!=null">
										<s:set var="todaybalance" value="#todaybalance+#dataMap.todaybalance"></s:set>
									</s:if>
								</td>
								<td align="right">
									<s:set var="a" value="#dataMap.margin" />
									<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
									<s:if test="#dataMap.margin!=null">
										<s:set var="margin" value="#margin+#dataMap.margin"></s:set>
									</s:if>
								</td>
								<td  align="right">
									<s:property value="#dataMap.capital" />
							</tr>
						</s:iterator>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
