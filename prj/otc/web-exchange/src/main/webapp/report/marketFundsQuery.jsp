<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/session.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>交易所台账</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
		<link href="${basePath }/report/report_css.css" rel="stylesheet"
			type="text/css" />
		<import namespace="MEBS"
			implementation="${basePath}/report/public/calendar.htc">
	</head>

	<body class="report_body" onload="init();">
		<table width="99%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<table width="98%" border="0" align="center" cellpadding="0"
						cellspacing="3">
						<tr>
							<td >
								<div class="div_tj">
									<form
										action="${basePath}/report/marketFunds/marketFundsReportQuery.action"
										name="frm" id="frm" method="post">
										<table border="0" cellpadding="0" cellspacing="0"
											class="table2_style">
											<tr>
												<td  class="table2_td_widthmax">
													<div class="div2_top">
														<table border="0" cellspacing="0" cellpadding="0">
															<tr>
																<td class="table3_td_1" align="left">
																	&nbsp;&nbsp;开始日期：&nbsp;
																	<input type="text" style="width: 100px" id="startDate"
																		class="wdate" maxlength="10"
																		name="${GNNT_}primary.clearDate[>=][date]"
																		value='${oldParams["primary.clearDate[>=][date]"]}'
																		onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
																</td>
																<td class="table3_td_1" align="left">
																	结束日期：&nbsp;
																	<input type="text" style="width: 100px" id="endDate"
																		class="wdate" maxlength="10"
																		name="${GNNT_}primary.clearDate[<=][date]"
																		value='${oldParams["primary.clearDate[<=][date]"]}'
																		onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
																</td>
																<td class="table3_td_1tjcx_1" align="left">
															银行：
															<span class="right_03zi"><select id="bank"
																	name="${GNNT_}primary.bankCode[=][String]"
																	class="input_textmin">
																	<option value="">
																		请选择
																	</option>
																	<c:forEach items="${bankList}" var="bank">
																		<option value="${bank.bankId}">
																			${bank.bankName }
																		</option>
																	</c:forEach>
																</select> </span>
															<script type="text/javascript">
															frm.bank.value = '${oldParams["primary.bankCode[=][String]"] }';
															</script>
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
													var="marketFunds"
													action="${basePath}/report/marketFunds/marketFundsReportQuery.action"
													title="" minHeight="345" listWidth="160%"
													retrieveRowsCallback="limit" sortRowsCallback="limit"
													filterRowsCallback="limit" csvFileName="导出列表.csv"
													style="table-layout:fixed">
													<ec:row ondblclick="">
														<ec:column width="3%" property="_0" title="序号"
															value="${GLOBALROWCOUNT}" sortable="false"
															filterable="false" />
														<ec:column property="clearDate[=][date]" title="结算日期"
															width="5%" style="text-align:left; " ellipsis="true">
															<fmt:formatDate value="${marketFunds.clearDate}"
																pattern="yyyy-MM-dd" />
														</ec:column>
														<ec:column property="bankName[=][String]" title="银行名称"
															width="5%" style="text-align:left; " ellipsis="true"
															sortable="false" value="${marketFunds.bankName}" />
														<ec:column property="lastbankbalance[=][double]" title="上日余额"
															width="7%" style="text-align:right; " >
															<fmt:formatNumber value="${marketFunds.lastbankbalance}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="outcount[=][int]" title="出金笔数" width="4%"
															style="text-align:right; "
															>
															<fmt:formatNumber value="${marketFunds.outcount}"
																pattern="#,##0" />
														</ec:column>
														<ec:column property="outfund[=][double]" title="出金金额" width="6%"
															style="text-align:right; " >
															<fmt:formatNumber value="${marketFunds.outfund}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="infund[=][double]" title="入金金额" width="6%"
															style="text-align:right; "
															>
															<fmt:formatNumber value="${marketFunds.infund}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="incount[=][int]" title="入金笔数"
															width="4%" style="text-align:right; "
															>
															<fmt:formatNumber value="${marketFunds.incount}"
																pattern="#,##0" />
														</ec:column>
														<ec:column property="totaltradefee[=][double]" title="银行汇总手续费"
															width="5%" style="text-align:right; ">
															<fmt:formatNumber value="${marketFunds.totaltradefee}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="totaldelayfee[=][double]" title="银行汇总延期费" width="5%"
															style="text-align:right; ">
															<fmt:formatNumber value="${marketFunds.totaldelayfee}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="marketfeenew[=][double]" title="交易所产生手续费"
															width="6%" style="text-align:right; ">
															<fmt:formatNumber value="${marketFunds.marketfeenew}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="marketdelayfeenew[=][double]" title="交易所产生延期费"
															width="6%" style="text-align:right; ">
															<fmt:formatNumber value="${marketFunds.marketdelayfeenew}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="marketfeeout[=][double]" title="交易所转出费用"
															width="5%" style="text-align:right; ">
															<fmt:formatNumber value="${marketFunds.marketfeeout}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="marketfeebalance[=][double]" title="交易所留存费用"
															width="5%" style="text-align:right; " >
															<fmt:formatNumber value="${marketFunds.marketfeebalance}"
																pattern="#,##0.00" />
														</ec:column>
														
														<ec:column property="totalpl[=][double]" title="盈亏" width="5%"
															style="text-align:right; " >
															<fmt:formatNumber value="${marketFunds.totalpl}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="netbalance[=][double]" title="净增长" width="6%"
															style="text-align:right; ">
															<fmt:formatNumber value="${marketFunds.netbalance}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="bankbalance[=][double]" title="今日余额" width="7%"
															style="text-align:right; " >
															<fmt:formatNumber value="${marketFunds.bankbalance}"
																pattern="#,##0.00" />
														</ec:column>
													</ec:row>
													<c:if test="${ifHas eq 1}">		
														<ec:extendrow>
															<td> 合计:</td>
															<td>&nbsp;</td><td>&nbsp;</td>
															<td align="right" style="font-weight:bold">${lastbankbalanceAll}</td>
															<td align="right" style="font-weight:bold">${outcountAll}</td>
															<td align="right" style="font-weight:bold">${outfundAll}</td>
															<td align="right" style="font-weight:bold">${infundAll}</td>
															<td align="right" style="font-weight:bold">${incountAll}</td>
															<td align="right" style="font-weight:bold">${totaltradefeeAll}</td>
															<td align="right" style="font-weight:bold">${totaldelayfeeAll}</td>
															<td align="right" style="font-weight:bold">${marketfeenewAll}</td>
															<td align="right" style="font-weight:bold">${marketdelayfeenewAll}</td>
															<td align="right" style="font-weight:bold">${marketfeeoutAll}</td>
															<td align="right" style="font-weight:bold">${marketfeebalanceAll}</td>
															<td align="right" style="font-weight:bold">${totalplAll}</td>
															<td align="right" style="font-weight:bold">${netbalanceAll}</td>
															<td align="right" style="font-weight:bold">${bankbalanceAll}</td>
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
				</td>
			</tr>
		</table>
		</form>
	</body>
</html>
 <!-- 编辑和过滤所使用的 通用的文本框模板-->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value=""
				onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="" />
		</textarea>
<script type="text/javascript">

function select1() {
	var action = frm.action;
	frm.action = "${basePath}/report/marketFunds/marketFundsReportQuery.action";
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
	var url = "${basePath}/report/customerTrader/memberInfoList.action?oldMemberIds="
			+ memberIds;
	var result = window.showModalDialog(url);
	if (result != null && result != '') {
		var result1 = result.split('####');
		frm.memberIds.value = result1[0];
		frm.memberNames.value = result1[1];
	}
}
</script>