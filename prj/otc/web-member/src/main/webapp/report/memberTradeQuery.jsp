<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/session.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>综合会员成交汇总表</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
		<link href="${basePath }/report/report_css.css" rel="stylesheet"
			type="text/css" />
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
								action="${basePath}/report/memberTrade/memberTradeReportQuery.action"
								name="frm" id="frm" method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_widthmax">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0" style="table-layout: fixed">
													<tr>
														<td class="table3_td_1" align="left">
															开始日期：
															<input type="text" style="width: 100px" id="startDate"
																class="wdate" maxlength="10"
																name="${GNNT_}primary.clearDate[>=][date]"
																value='${oldParams["primary.clearDate[>=][date]"]}'
																onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
														</td>
														<td class="table3_td_1" align="left">
															结束日期：
															<input type="text" style="width: 100px" id="endDate"
																class="wdate" maxlength="10"
																name="${GNNT_}primary.clearDate[<=][date]"
																value='${oldParams["primary.clearDate[<=][date]"]}'
																onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
														</td>
														<td align="left" class="table3_td_1min">
															商品：
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
														<td align="left" class="table3_td_1min">
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
											var="memberTrade"
											action="${basePath}/report/memberTrade/memberTradeReportQuery.action"
											title="" minHeight="345" listWidth="145%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" csvFileName="导出列表.csv"
											style="table-layout:fixed">
											<ec:row ondblclick="">
												<ec:column width="2%" property="_0" title="序号"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<ec:column property="clearDate[=][date]" title="结算日期"
													width="4%" style="text-align:left; " ellipsis="true">
													<fmt:formatDate value="${memberTrade.clearDate}"
														pattern="yyyy-MM-dd" />
												</ec:column>
												<ec:column property="commodityName[like]" title="商品"
													width="5%" style="text-align:left; "
													value="${memberTrade.commodityName}" ellipsis="true" />
												<ec:column property="customerqtysum[=][int]" title="客户成交量"
													width="3%" style="text-align:right; " format="#,##0">
													<fmt:formatNumber value="${memberTrade.customerqtysum}"
														pattern="#,##0" />
												</ec:column>
												<ec:column property="memberqtysum[=][int]" title="综合会员成交量" width="4%"
													style="text-align:right; " format="#,##0">
													<fmt:formatNumber value="${memberTrade.memberqtysum}"
														pattern="#,##0" />
												</ec:column>
												<ec:column property="qtysum[=][int]" title="成交量合计" width="3%"
													style="text-align:right; " format="#,##0">
													<fmt:formatNumber value="${memberTrade.qtysum}"
														pattern="#,##0" />
												</ec:column>
												<ec:column property="customerfundsum[=][double]" title="客户成交金额"
													width="5%" style="text-align:right; "  format="#,##0.00">
													<fmt:formatNumber value="${memberTrade.customerfundsum}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="memberfundsum[=][double]" title="综合会员成交金额"
													width="5%" style="text-align:right; " format="#,##0.00">
													<fmt:formatNumber value="${memberTrade.memberfundsum}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="fundsum[=][double]" title="成交金额合计" width="5%"
													style="text-align:right; "  format="#,##0.00">
													<fmt:formatNumber value="${memberTrade.fundsum}"
														pattern="#,##0.00" />
												</ec:column>

												<ec:column property="customercloseplsum[=][double]" title="客户平仓盈亏"
													width="5%" style="text-align:right; " format="#,##0.00">
													<fmt:formatNumber value="${memberTrade.customercloseplsum}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="memberclosepl[=][double]" title="综合会员平仓盈亏"
													width="5%" style="text-align:right; " format="#,##0.00">
													<fmt:formatNumber value="${memberTrade.memberclosepl}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="closeplsum[=][double]" title="平仓净盈亏" width="5%"
													style="text-align:right; " format="#,##0.00">
													<fmt:formatNumber value="${memberTrade.closeplsum}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="mktfee[=][double]" title="交易所存留手续费" width="5%"
													style="text-align:right; " format="#,##0.00">
													<fmt:formatNumber value="${memberTrade.mktfee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="memberfee[=][double]" title="综合会员存留手续费" width="5%"
													style="text-align:right; "  format="#,##0.00">
													<fmt:formatNumber value="${memberTrade.memberfee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="customerfee[=][double]" title="收客户手续费" width="4%"
													style="text-align:right; " format="#,##0.00">
													<fmt:formatNumber value="${memberTrade.customerfee}"
														pattern="#,##0.00" />
												</ec:column>
											</ec:row>
											<c:if test="${ifHas eq 1}">		
												<ec:extendrow>
													<td>
														合计:
													</td>
													<td>&nbsp;</td><td>&nbsp;</td>
													<td align="right" style="font-weight:bold">${customerqtysumAll}</td>													
													<td align="right" style="font-weight:bold">${memberqtysumAll}</td>													
													<td align="right" style="font-weight:bold">${qtysumAll}</td>													
													<td align="right" style="font-weight:bold">${customerfundsumAll}</td>													
													<td align="right" style="font-weight:bold">${memberfundsumAll}</td>													
													<td align="right" style="font-weight:bold">${fundsumAll}</td>													
													<td align="right" style="font-weight:bold">${customercloseplsumAll}</td>													
													<td align="right" style="font-weight:bold">${membercloseplAll}</td>																							
													<td align="right" style="font-weight:bold">${closeplsumAll}</td>
													<!--  													
													<td align="right" style="font-weight:bold">${customerholdplAll}</td>													
													<td align="right" style="font-weight:bold">${memberholdplAll}</td>													
													<td align="right" style="font-weight:bold">${holdplsumAll}</td>													
													<td align="right" style="font-weight:bold">${netplsumAll}</td>-->
													<td align="right" style="font-weight:bold">${mktfeeAll}</td>															
													<td align="right" style="font-weight:bold">${memberfeeAll}</td>
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
	frm.action = "${basePath}/report/memberTrade/memberTradeReportQuery.action";
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
	var memberIds = frm.memberIds.value;
	var url = "${basePath}/report/customer/memberInfoList.action?oldMemberIds="
			+ memberIds;
	var result = window.showModalDialog(url, '',
			"dialogWidth=350px;dialogHeight=520px");
	if (result != null && result != '') {
		var result1 = result.split('####');
		frm.memberIds.value = result1[0];
		frm.memberNames.value = result1[1];
	}
}
</script>