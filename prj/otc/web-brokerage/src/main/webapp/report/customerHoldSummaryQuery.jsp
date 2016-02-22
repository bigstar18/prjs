<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/session.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>客户持仓汇总表</title>
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
								action="${basePath}/report/customerHoldSummary/customerHoldSummaryReportQuery.action"
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
															<input type="text" name="gnnt_customerno[=][String]"
																id="customerNo"
																value="${oldParams['customerno[=][String]'] }"
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
															</button>
															&nbsp;
															<button class="btn_cz" onclick="myReset();">
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
											var="customerHold"
											action="${basePath}/report/customerHoldSummary/customerHoldSummaryReportQuery.action"
											title="" minHeight="345" listWidth="115%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" csvFileName="客户持仓汇总表.csv"
											style="table-layout:fixed">
											<ec:row recordKey="${customer.customerNo}">
												<ec:column width="2%" property="_0" title="序号"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<ec:column property="clearDate[=][date]" title="结算日期"
													width="4%" style="text-align:left; " ellipsis="true">
													<fmt:formatDate value="${customerHold.clearDate}"
														pattern="yyyy-MM-dd" />
												</ec:column>
												<ec:column property="customerNo[=][String]" title="交易账号"
													width="6%" style="text-align:right; "
													value="${customerHold.customerNo}" ellipsis="true">
												</ec:column>
												<ec:column property="customerName[like]" title="客户名称"
													width="5%" style="text-align:left; "
													value="${customerHold.customerName}" ellipsis="true" />
												<ec:column property="commodityName[like]" title="商品"
													width="4%" style="text-align:left; "
													value="${customerHold.commodityName}" ellipsis="true" />
												<ec:column property="buyqty[=][int]" title="买单持仓量" width="3%"
													style="text-align:right; ">
													<fmt:formatNumber value="${customerHold.buyqty}"
														pattern="#,##0" />
												</ec:column>
												<ec:column property="sellqty[=][int]" title="卖单持仓量" width="3%"
													style="text-align:right; ">
													<fmt:formatNumber value="${customerHold.sellqty}"
														pattern="#,##0" />
												</ec:column>
												<ec:column property="qtysum[=][int]" title="持仓量合计" width="3%"
													style="text-align:right; " >
													<fmt:formatNumber value="${customerHold.qtysum}"
														pattern="#,##0" />
												</ec:column>
												<ec:column property="floatingloss[=][double]" title="持仓盈亏" width="6%"
													style="text-align:right;">
													<fmt:formatNumber value="${customerHold.floatingloss}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="delayfee[=][double]" title="延期费" width="4%"
													style="text-align:right;">
													<fmt:formatNumber value="${customerHold.delayfee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="mktdelayfee[=][double]" title="市场存留延期费"
													width="5%" style="text-align:right;">
													<fmt:formatNumber value="${customerHold.mktdelayfee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="memberdelayfee[=][double]" title="会员存留延期费"
													width="5%" style="text-align:right;">
													<fmt:formatNumber value="${customerHold.memberdelayfee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="margin[=][double]" title="占用保证金" width="6%"
													style="text-align:right; " >
													<fmt:formatNumber value="${customerHold.margin}"
														pattern="#,##0.00" />
												</ec:column>
											</ec:row>
											<c:if test="${ifHas eq 1}">		
												<ec:extendrow>
													<td>
														合计:
													</td>
													<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
													<td align="right" style="font-weight:bold">${buyqtyAll}</td>
													<td align="right" style="font-weight:bold">${sellqtyAll}</td>
													<td align="right" style="font-weight:bold">${qtysumAll}</td>
													<td align="right" style="font-weight:bold">${floatinglossAll}</td>
													<td align="right" style="font-weight:bold">${delayfeeAll}</td>
													<td align="right" style="font-weight:bold">${mktdelayfeeAll}</td>
													<td align="right" style="font-weight:bold">${memberdelayfeeAll}</td>
													<td align="right" style="font-weight:bold">${marginAll }</td>
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
	frm.action = "${basePath}/report/customerHoldSummary/customerHoldSummaryReportQuery.action";
	checkQueryDate(frm.startDate.value, frm.endDate.value);
	frm.action = action;
}
function xls() {
	frm.type.value = "xls";
	select1();
	frm.type.value = "";
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