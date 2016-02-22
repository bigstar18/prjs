<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/session.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>客户成交汇总表</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
		<import namespace="MEBS"
			implementation="${basePath}/report/public/calendar.htc">
	</head>
	<body id="main_body" onload="init();">
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
							<form
								action="${basePath}/report/customerTrade/customerTradeReportQuery.action"
								name="frm" id="frm" method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_widthcdmin">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0" style="table-layout: fixed">
													<tr>
														
														<td class="table3_td_1tjcx" align="left">
															交易账号:
															<input type="text" name="gnnt_customerNo[=][String]"
																id="customerNo"
																value="${oldParams['customerNo[=][String]'] }"
																class="input_text">
														</td>
														<td class="table3_td_1tjcx" align="left">
															客户名称:
															<input type="text"
																name="gnnt_primary.customerName[like]"
																id="customerName"
																value="${oldParams['primary.customerName[like]'] }"
																class="input_textmin">
														</td>
													</tr>
													<tr>
														<td class="table3_td_1tjcx_1" align="left">
															开始日期:
															<input type="text" style="width: 100px" id="startDate"
																class="wdate" maxlength="10"
																name="gnnt_primary.clearDate[>=][date]"
																value='${oldParams["primary.clearDate[>=][date]"]}'
																onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
														</td>
														<td class="table3_td_1tjcx" align="left">
															结束日期:
															<input type="text" style="width: 100px" id="endDate"
																class="wdate" maxlength="10"
																name="gnnt_primary.clearDate[<=][date]"
																value='${oldParams["primary.clearDate[<=][date]"]}'
																onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
														</td>
														<td align="left" colspan="3">
															<button class="btn_sec" onclick="select1()">
																查询
															</button>&nbsp;
															<button class="btn_cz" onclick="myReset()">
																重置
															</button>
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
											var="customerTrade"
											action="${basePath}/report/customerTrade/customerTradeReportQuery.action"
											title="" minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" csvFileName="客户成交汇总表.csv"
											style="table-layout:fixed">
											<ec:row recordKey="${customer.customerNo}">
												<ec:column width="2%" property="_0" title="序号"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<ec:column property="clearDate[=][date]" title="结算日期"
													width="4%" style="text-align:left; " ellipsis="true">
													<fmt:formatDate value="${customerTrade.clearDate}"
														pattern="yyyy-MM-dd" />
												</ec:column>
												<ec:column property="customerNo[=][String]" title="交易账号"
													width="6%" style="text-align:right; "
													value="${customerTrade.customerNo}" ellipsis="true">
												</ec:column>
												<ec:column property="customerName[like]" title="客户名称"
													width="5%" style="text-align:left; "
													value="${customerTrade.customerName}" ellipsis="true" />
												<ec:column property="commodityName[like]" title="商品"
													width="4%" style="text-align:left; "
													value="${customerTrade.commodityName}" ellipsis="true" />
												<ec:column property="customerqtysum[=][int]" title="成交量" width="3%"
													style="text-align:right; ">
													<fmt:formatNumber value="${customerTrade.customerqtysum}"
														pattern="#,##0" />
												</ec:column>
												<ec:column property="customerfundsum[=][double]" title="成交金额"
													width="7%" style="text-align:right; ">
													<fmt:formatNumber value="${customerTrade.customerfundsum}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="customercloseplsum[=][double]" title="平仓盈亏"
													width="6%" style="text-align:right;">
													<fmt:formatNumber
														value="${customerTrade.customercloseplsum}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="mktfee[=][double]" title="交易所存留手续费" width="6%"
													style="text-align:right; ">
													<fmt:formatNumber value="${customerTrade.mktfee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="memberfee[=][double]" title="综合会员存留手续费" width="6%"
													style="text-align:right; ">
													<fmt:formatNumber value="${customerTrade.memberfee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="customerfee[=][double]" title="收客户手续费" width="5%"
													style="text-align:right; ">
													<fmt:formatNumber value="${customerTrade.customerfee}"
														pattern="#,##0.00" />
												</ec:column>
											</ec:row>
											<c:if test="${ifHas eq 1}">		
												<ec:extendrow>
													<td>
														合计:
													</td>
													<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
													<td align="right" style="font-weight:bold">${customerqtysumAll}</td>
													<td align="right" style="font-weight:bold">${customerfundsumAll}</td>
													<td align="right" style="font-weight:bold">${customercloseplsumAll }</td>
													<!-- 
													<td align="right" style="font-weight:bold">${customerholdplAll}</td>
													<td align="right" style="font-weight:bold">${customerplsumAll}</td> -->
													<td align="right" style="font-weight:bold">${mktfeeAll}</td>
													<td align="right" style="font-weight:bold">${memberfeeAll }</td>
													<td align="right" style="font-weight:bold">${customerfeeAll}</td>
												</ec:extendrow>
											</c:if>
										</ec:table>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<!-- 编辑和过滤所使用的 通用的文本框模板 -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value=""
				onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="" />
		</textarea>
		<script type="text/javascript">

function select1() {
	var action = frm.action;
	frm.action = "${basePath}/report/customerTrade/customerTradeReportQuery.action";
	checkQueryDate(frm.startDate.value, frm.endDate.value);
	frm.action = action;
}
function xls() {
	frm.type.value = "xls";
	select1();
	frm.type.value = "";
}
function getDate() {
	var date = new Date();
	var thisYear = date.getYear();
	var thisMonth = date.getMonth() + 1;
	if (thisMonth < 10) {
		thisMonth = "0" + thisMonth;
	}
	var thisDay = date.getDate();
	if (thisDay < 10) {
		thisDay = "0" + thisDay;
	}
	return thisYear + "-" + thisMonth + "-" + thisDay;
}
function init() {
	if (frm.startDate.value == "" && frm.endDate.value == "") {
		frm.startDate.value = '${date}';
		frm.endDate.value = '${date}';
	}
}

function clickText() {
	var url = "${basePath}/broke/memberInfoTree/forTree.action";
	ecsideDialog(url, window, 400, 570);
}
</script>