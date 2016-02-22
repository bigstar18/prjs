<%@ page pageEncoding="GBK"%>
<%@ include file="/public/reportCss.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
	</head>
	<body class="report_body">
		<br>
		<br>
		<div class="report_w16_title">
			ƽ����ϸ
		</div>
		<table width="240%" border="1" align="center" cellpadding="0"
			cellspacing="0" bordercolor="#000000"
			style='border-collapse: collapse;'>
			<s:set var="num" value="1"></s:set>
			<s:set var="quantity" value="0"></s:set>
			<s:set var="close_price" value="0"></s:set>
			<s:set var="holdprice" value="0"></s:set>
			<s:set var="close_pl" value="0"></s:set>
			<s:set var="tradefee" value="0"></s:set>
			<s:set var="openprice" value="0"></s:set>
			<s:set var="delayfee" value="0"></s:set>
			<tr class="report_w14">
				<td width="3%">
					���
				</td>
				<td width="7%">
					�����˺�
				</td>
				<td width="8%">
					�ͻ�����
				</td>
				
				<td width="5%">
					��Ʒ
				</td>
				<td width="5%">
					ƽ����
				</td>
				<td width="5%">
					ƽ�ּ�
				</td>
				<td width="5%">
					�ֲּ�
				</td>
				<td width="5%">
					ƽ��ӯ��
				</td>
				<td width="5%">
					ƽ��������
				</td>
				<td width="5%">
					���ּ�
				</td>
				<td width="5%">
					���ڷ�
				</td>
				<td width="5%">
					ί���µ�
				</td>
				<td width="3%">
					ն��
				</td>
				<td width="9%">
					ƽ��ʱ��
				</td>
				<td width="5%">
					ƽ�ֵ���
				</td>
				<td width="5%">
					��������
				</td>
				<td width="5%">
					���ֵ���
				</td>
				<td width="10%">
					����ʱ��
				</td>
			</tr>
			<s:iterator value="dataList()" var="dataMap">
				<tr class="report_w14_neirong">
					<td>
						<s:set var="num" value="#num"></s:set>
						<s:property value="#num" />
					</td>
					<td>
						<s:property value="#dataMap.customerno" />&nbsp;
					</td>
					<td>
						<s:property value="#dataMap.customername" />
					</td>
					<td>
						<s:property value="#dataMap.commodityname" />
					</td>
					<td align="right"> 
						<s:set var="a" value="#dataMap.quantity" />
						<fmt:formatNumber value="${a }" pattern="#,##0"/>
						<s:if test="#dataMap.quantity!=null">
							<s:set var="quantity" value="#dataMap.quantity+#quantity"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.price)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.close_price!=null">
							<s:set var="close_price"
								value="#dataMap.price+#close_price"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.holdprice)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.holdprice!=null">
							<s:set var="holdprice" value="#dataMap.holdprice+#holdprice"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.close_pl)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.close_pl!=null">
							<s:set var="close_pl" value="#dataMap.close_pl+#close_pl"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.tradefee)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.tradefee!=null">
							<s:set var="tradefee" value="#dataMap.tradefee+#tradefee"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.openprice)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.openprice!=null">
							<s:set var="openprice" value="#dataMap.openprice+#openprice"></s:set>
						</s:if>
					</td>
					<td align="right">
						<s:set var="a" value="format(#dataMap.delayfee)" />
						<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
						<s:if test="#dataMap.delayfee!=null">
							<s:set var="delayfee" value="#dataMap.delayfee+#delayfee"></s:set>
						</s:if>
					</td>
					<td>
						<s:set var="mapKey" value="#dataMap.isDelegate"></s:set>
						<s:property value="whetherMap[#mapKey]" />
					</td>
					<td>
						<s:set var="mapKey" value="#dataMap.isSwitching"></s:set>
						<s:property value="whetherMap[#mapKey]" />
					</td>
					<td>
						<s:date name="#dataMap.tradetime" format="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>
						<s:property value="#dataMap.tradeno" />&nbsp;
					</td>
					<td>
						<s:set var="mapKey" value="#dataMap.bs_flag"></s:set>
						<s:property value="flagMap[#mapKey]" />
					</td>
					<td>
						<s:property value="#dataMap.opentradeno" />&nbsp;
					</td>
					<td>
						<s:date name="#dataMap.holdtime" format="yyyy-MM-dd HH:mm:ss"/>
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
				<td>
					&nbsp;
				</td>
				<td align="right">
					<s:set var="a" value="#quantity" />
					<fmt:formatNumber value="${a }" pattern="#,##0"/>
				</td>
				<td align="right">
					&nbsp;
				</td>
				<td align="right">
					&nbsp;
				</td>
				<td align="right">
					<s:set var="a" value="format(#close_pl)" />
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					<s:set var="a" value="format(#tradefee)" />
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
				</td>
				<td align="right">
					&nbsp;
				</td>
				<td align="right">
					<s:set var="a" value="format(#delayfee)" />
					<fmt:formatNumber value="${a }" pattern="#,##0.00"/>
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
				<td>
					&nbsp;
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
	</body>
</html>
