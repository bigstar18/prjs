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
			�ر��Ա����ͳ�Ʊ�
		</div>
<table width="150%" border="0" align="center" cellpadding="0" cellspacing="0">
<tr>
  <td>
		<table width="70%" border="0" align="left" cellpadding="0"
			cellspacing="0" bordercolor="#000000"
			style='border-collapse: collapse;padding-left: 20px;' height="20">
			<tr>
			<td align="left">
					�ر��Ա��ţ�${s_memberNo}
				</td>
				<td align="left">
					�ر��Ա���ƣ�${s_memberName}
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
			style='border-collapse: collapse;'>
			<s:set var="sm_holdqtysum" value="0"></s:set>
			<s:set var="sm_holderfundsum" value="0"></s:set>
			<s:set var="netqyt" value="0"></s:set>
			<s:set var="fundio" value="0"></s:set>
			<s:set var="delayfee" value="0"></s:set>
			<s:set var="closepl" value="0"></s:set>
			<s:set var="holdpl" value="0"></s:set>
			<s:set var="sm_buyqtysum" value="0"></s:set>
			<s:set var="sm_sellqtysum" value="0"></s:set>
			<s:set var="num" value="1"></s:set>
			<tr class="report_w14">
				<td width="4%" rowspan="2">
					���
				</td>
				<td width="6%" rowspan="2">
					��������
				</td>
				<td width="5%" rowspan="2">
					��Ʒ
				</td>
				<td width="7%" rowspan="2">
					�ɽ���
				</td>
				<td width="8%" rowspan="2">
					�ɽ����
				</td>
				<td width="5%" rowspan="2">
					��
				</td>
				<td width="5%" rowspan="2">
					����
				</td>
				<td width="5%" rowspan="2">
					��ͷ��
				</td>
				<td width="7%" rowspan="2">
					�����ڷ�
				</td>
				<td width="25%" colspan="3">
					����ӯ��
				</td>
			<tr class="report_w14">
				<td>
					ƽ��
				</td>
				<td>
					�ֲ�
				</td>
				<td>
					�ϼ�
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
						<s:property value="#dataMap.commodityName" />
					</td>
					<td align="right">
						<s:set var="a" value="#dataMap.sm_holdqtysum" />
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.sm_holdqtysum!=null">
							<s:set var="sm_holdqtysum"
								value="#dataMap.sm_holdqtysum+#sm_holdqtysum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.sm_holderfundsum)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.sm_holderfundsum!=null">
							<s:set var="sm_holderfundsum"
								value="#dataMap.sm_holderfundsum+#sm_holderfundsum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="#dataMap.sm_buyqtysum" />
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.sm_buyqtysum!=null">
							<s:set var="sm_buyqtysum"
								value="#dataMap.sm_buyqtysum+#sm_buyqtysum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="#dataMap.sm_sellqtysum" />
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.sm_sellqtysum!=null">
							<s:set var="sm_sellqtysum"
								value="#dataMap.sm_sellqtysum+#sm_sellqtysum"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="#dataMap.netqyt" />
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.netqyt!=null">
							<s:set var="netqyt" value="#dataMap.netqyt+#netqyt"></s:set>
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
						<s:set var="a" value="format(#dataMap.closepl)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.closepl!=null">
							<s:set var="closepl" value="#dataMap.closepl+#closepl"></s:set>
						</s:if>
					</td>

					<td align="right">
						<s:set var="a" value="format(#dataMap.holdpl)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.holdpl!=null">
							<s:set var="holdpl" value="#dataMap.holdpl+#holdpl"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.closepl+#dataMap.holdpl)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
					</td>
				</tr>
			</s:iterator>
			<tr class="report_w14">
				<td>
					�ܼƣ�
				</td>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
				<td align="right">
					<s:set var="a" value="#sm_holdqtysum" />
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td align="right">
					<s:set var="a" value="format(#sm_holderfundsum)" />
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set var="a" value="#sm_buyqtysum" />
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td align="right">
					<s:set var="a" value="#sm_sellqtysum" />
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td align="right">
					<s:set var="a" value="#netqyt" />
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>

				<td align="right">
					<s:set var="a" value="format(#delayfee)" />
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set var="a" value="format(#closepl)" />
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set var="a" value="format(#holdpl)" />
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					&nbsp;
				</td>
			</tr>
		</table>
		</td>
		</tr>
		</table>
	</body>
</html>
