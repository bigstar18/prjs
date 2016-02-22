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
						<ec:column property="commodity.name[like]" title="商品名称"	width="17%"
							 style="text-align:center;" filterable="false"
								sortable="false">
								<a href="#" class="blank_a"
								onclick="return update('${quotation.commodityId}');"><font
								color="#880000">${quotation.commodityName}</font>
								</a>
							</ec:column>
							<ec:column property="curPrice[=][Double]" title="收盘价" width="17%"
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
							<ec:column property="highPrice[=][Double]" title="最高价" width="17%"
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
							<ec:column property="lowPrice[=][bigdecimal]" title="最低价"
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
								title="结算价" width="17%" style="text-align:center;"
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
							<ec:column property="openPrice[=][Double]" title="开盘价" width="17%"
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
		<div><font color="red">此结算价为最近一次结算后生成的价格，待有新的结算价时会自动更新。</font></div>
		<!-- 编辑和过滤所使用的 通用的文本框模板 -->
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
