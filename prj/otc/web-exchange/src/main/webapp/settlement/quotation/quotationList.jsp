<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<%@ include file="/public/ecsideLoad.jsp"%>
		<style type="text/css">
</style>
	</head>
	<body>
		<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
			<tr>
				<td>
					<ec:table items="resultList"
						autoIncludeParameters="${empty param.autoInc}" var="quotation"
						action="${basePath}/settlement/quotation/list.action" title=""
						minHeight="345" listWidth="100%" retrieveRowsCallback="limit"
						sortRowsCallback="limit" filterRowsCallback="limit"
						style="table-layout:fixed" toolbarContent="refresh|extend">
						<ec:row recordKey="${quotation.commodityId}">
						<ec:column property="commodity.name[like]" title="��Ʒ����"	width="17%"
							 style="text-align:center;" filterable="false"
								sortable="false">
								<a href="#" class="blank_a"
								onclick="return update('${quotation.commodityId}');"><font
								color="#880000">${quotation.commodityName}</font>
								</a>
							</ec:column>
							<ec:column property="curPrice[=][Double]" title="���̼�" width="17%"
								style="text-align:center;" filterable="false" sortable="false">
								<c:if test="${quotation.minPriceMove==1}">
									<fmt:formatNumber value="${quotation.closePrice}" pattern="#" />
								</c:if>
								<c:if test="${quotation.minPriceMove=='0.01'}">
									<fmt:formatNumber value="${quotation.closePrice}" pattern="#0.00" />
								</c:if>
								<c:if test="${quotation.minPriceMove!='0.01' && quotation.minPriceMove!=1}">
									<fmt:formatNumber value="${quotation.closePrice}" pattern="#0.00" />
								</c:if>
							</ec:column>
							<ec:column property="highPrice[=][Double]" title="��߼�" width="17%"
								style="text-align:center;" filterable="false" sortable="false">
								<c:if test="${quotation.minPriceMove==1}">
									<fmt:formatNumber value="${quotation.highPrice}" pattern="#" />
								</c:if>
								<c:if test="${quotation.minPriceMove=='0.01'}">
									<fmt:formatNumber value="${quotation.highPrice}" pattern="#0.00" />
								</c:if>
								<c:if test="${quotation.minPriceMove!='0.01' && quotation.minPriceMove!=1}">
									<fmt:formatNumber value="${quotation.highPrice}" pattern="#0.00" />
								</c:if>
							</ec:column>
							<ec:column property="lowPrice[=][bigdecimal]" title="��ͼ�"
								width="17%" style="text-align:center;" filterable="false"
								sortable="false">
								<c:if test="${quotation.minPriceMove==1}">
									<fmt:formatNumber value="${quotation.lowPrice}" pattern="#" />
								</c:if>
								<c:if test="${quotation.minPriceMove=='0.01'}">
									<fmt:formatNumber value="${quotation.lowPrice}" pattern="#0.00" />
								</c:if>
								<c:if test="${quotation.minPriceMove!='0.01' && quotation.minPriceMove!=1}">
									<fmt:formatNumber value="${quotation.lowPrice}" pattern="#0.00" />
								</c:if>
							</ec:column>
							<ec:column property="price[=][bigdecimal]"
								title="�����" width="17%" style="text-align:center;"
								filterable="false" sortable="false">
								<c:if test="${quotation.minPriceMove==1}">
									<fmt:formatNumber value="${quotation.price}"
										type="number" pattern="#" />
								</c:if>
								<c:if test="${quotation.minPriceMove=='0.01'}">
									<fmt:formatNumber value="${quotation.price}"
										type="number" pattern="#0.00" />
								</c:if>
								<c:if test="${quotation.minPriceMove!='0.01' && quotation.minPriceMove!=1}">
									<fmt:formatNumber value="${quotation.price}"
										type="number" pattern="#0.00" />
								</c:if>
							</ec:column>
							<ec:column property="openPrice[=][Double]" title="���̼�" width="17%"
								style="text-align:center;" filterable="false" sortable="false">
								<c:if test="${quotation.minPriceMove==1}">
									<fmt:formatNumber value="${quotation.openPrice}" type="number"
										pattern="#" />
								</c:if>
								<c:if test="${quotation.minPriceMove=='0.01'}">
									<fmt:formatNumber value="${quotation.openPrice}" type="number"
										pattern="#0.00" />
								</c:if>
								<c:if test="${quotation.minPriceMove!='0.01' && quotation.minPriceMove!=1}">
									<fmt:formatNumber value="${quotation.openPrice}" type="number" pattern="#0.00" />
								</c:if>
							</ec:column>

						</ec:row>
					</ec:table>
				</td>
			</tr>
		</table>
		<div><font color="red">�˽����Ϊ���һ�ν�������ɵļ۸񣬴����µĽ����ʱ���Զ����¡�</font></div>
		<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value=""
				onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="" />
		</textarea>
		<SCRIPT type="text/javascript">
	function settle1() {
		frm.action = "${basePath}/settlement/quotation/settlement.action";
		frm.submit();
	}
	function update(id) {
	var url = "${basePath}/settlement/quotation/forwardUpdate.action?obj.commodityId="+id;
	ecsideDialog(url,"",580,360);
}
</SCRIPT>
	</body>
</html>
