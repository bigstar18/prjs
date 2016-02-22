<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/session.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>结算价查询</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
		<link href="${basePath }/report/report_css.css" rel="stylesheet"
			type="text/css" />
		<import namespace="MEBS"
			implementation="${basePath}/report/public/calendar.htc">
	</head>

	<body id="main_body" onload="init();">
		<table width="99%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<table width="98%" border="0" align="center" cellpadding="0"
						cellspacing="3">
						<tr>
							<td>
								<div class="div_tj">
									<form
										action="${basePath}/report/quotationReport/quotationReportQuery.action"
										name="frm" id="frm" method="post">
										<table border="0" cellpadding="0" cellspacing="0"
											class="table2_style">
											<tr>
												<td height="40" width="775">
													<div class="div2_top">
														<table width="800" border="0" cellspacing="0"
															cellpadding="0">
															<tr>
																<td class="table3_td_1tjcx" align="left">
																	开始日期：
																	<input type="text" style="width: 100px" id="startDate"
																		class="wdate" maxlength="10"
																		name="${GNNT_}primary.clearDate[>=][date]"
																		value='${oldParams["primary.clearDate[>=][date]"]}'
																		onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
																</td>
																<td class="table3_td_1tjcx" align="left">
																	&nbsp;结束日期：
																	<input type="text" style="width: 100px" id="endDate"
																		class="wdate" maxlength="10"
																		name="${GNNT_}primary.clearDate[<=][date]"
																		value='${oldParams["primary.clearDate[<=][date]"]}'
																		onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
																</td>
																<td class="table3_td_1tjcx_1" align="left">
																	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;商品：
																	<select id="commodityId"
																		name="${GNNT_}primary.commodityId[=][String]"
																		class="input_textmin">
																		<option value="">
																			请选择
																		</option>
																		<c:forEach items="${commodityList}" var="commodit">
																			<option value="${commodit.id}">
																				${commodit.name }
																			</option>
																		</c:forEach>
																	</select>
																	<script type="text/javascript">
frm.commodityId.value='${oldParams['primary.commodityId[=][String]'] }';
													</script>
																</td>
																<td align="left" colspan="2">
																	<input type="button" class="button_02"
																		onclick="select1()" value="查询" />
																	&nbsp;
																	<input type="button" class="button_03"
																		onclick="myReset()" value="重置" />
																	&nbsp;
																</td>
															</tr>
														</table>
													</div>
												</td>
											</tr>
										</table>
									</form>
								</div>
								<div class="div_list">
									<table id="tb" border="0" cellspacing="0" cellpadding="0"
										width="100%">
										<tr>
											<td>
												<ec:table items="list"
													autoIncludeParameters="${empty param.autoInc}"
													var="quotation"
													action="${basePath}/report/quotationReport/quotationReportQuery.action"
													title="" minHeight="345" listWidth="100%"
													retrieveRowsCallback="limit" sortRowsCallback="limit"
													filterRowsCallback="limit" csvFileName="导出列表.csv"
													style="table-layout:fixed">
													<ec:row ondblclick="">
														<ec:column width="3%" property="_0" title="序号"
															value="${GLOBALROWCOUNT}" sortable="false"
															filterable="false" />
														<ec:column property="clearDate[=][date]" title="结算日期"
															width="4%" style="text-align:left; " ellipsis="true">
															<fmt:formatDate value="${quotation.clearDate}"
																pattern="yyyy-MM-dd" />
														</ec:column>
														<ec:column property="commodity.name[=][String]" title="商品"
															width="4%" style="text-align:left; "
															value="${quotation.commodityName}" ellipsis="true" />
														<ec:column property="primary.price[=][double]" title="结算价"
															width="5%" style="text-align:right; ">
															<fmt:formatNumber value="${quotation.price}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="primary.lowPrice[=][double]"
															title="最低价" width="4%" style="text-align:right; ">
															<fmt:formatNumber value="${quotation.lowPrice}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="primary.highPrice[=][double]"
															title="最高价" width="5%" style="text-align:right; ">
															<fmt:formatNumber value="${quotation.highPrice}"
																pattern="#,##0.00" />
														</ec:column>
													</ec:row>
												</ec:table>
											</td>
										</tr>
									</table>
								</div>

							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		</form>


		<!-- 编辑和过滤所使用的 通用的文本框模板 -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value=""
				onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="" />
		</textarea>
	</body>
</html>
<script type="text/javascript">

function select1() {
	var action = frm.action;
	frm.action = "${basePath}/report/quotationReport/quotationReportQuery.action";
	checkQueryDate(frm.startDate.value, frm.endDate.value);
	frm.action = action;

}
function init() {
	if (frm.startDate.value == "" && frm.endDate.value == "") {
		frm.startDate.value = '${date}';
		frm.endDate.value = '${date}';
	}
}
</script>