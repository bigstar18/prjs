<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/session.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>客户资金状况</title>
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
										action="${basePath}/report/customerFunds/customerFundsReportQuery.action"
										name="frm" id="frm" method="post">
										<table border="0" cellpadding="0" cellspacing="0"
											class="table2_style">
											<tr>
												<td class="table2_td_widthcdmax">
													<div class="div2_top">
														<table border="0" cellspacing="0" cellpadding="0">
															<tr>
																<input type="hidden" name="${GNNT_}isRelated"
																	id="isRelated" value="${oldParams['isRelated']}"
																	class="input_text">
																<input type="hidden" name="${GNNT_}memberInfoIds"
																	id="memberInfoIds"
																	value="${oldParams['memberInfoIds']}"
																	class="input_text">
																<input type="hidden" name="${GNNT_}organizationIds"
																	id="organizationIds"
																	value="${oldParams['organizationIds']}"
																	class="input_text">
																<input type="hidden" name="${GNNT_}managerIds"
																	id="managerIds" value="${oldParams['managerIds']}"
																	class="input_text">
																<input type="hidden" name="${GNNT_}brokerageIds"
																	id="brokerageIds" value="${oldParams['brokerageIds']}"
																	class="input_text">
																<input type="hidden" name="tree" id="tree"
																	value="${tree}" class="input_text">
																<td class="table3_td_1tjcx" align="left">
																	客户归属：
																	<input type="text" id="memberNames"
																		name="${GNNT_}memberNames"
																		value="${oldParams['memberNames']}" readonly=true
																		class="input_textmin">
																	<a href="javascript:clickText();"><img
																			align="absmiddle" src="<%=skinPath%>/cssimg/kh.gif">
																	</a>
																</td>
																<td class="table3_td_1tjcx" align="left">
																	交易账号：
																	<input type="text" name="${GNNT_}customerno[like]"
																		id="customerNo"
																		value="${oldParams['customerno[like]'] }"
																		class="input_text">
																</td>
																<td class="table3_td_1tjcx" align="left">
																	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;客户名称：
																	<input type="text"
																		name="${GNNT_}primary.customerName[like]"
																		id="customerName"
																		value="${oldParams['primary.customerName[like]'] }"
																		class="input_textmin">
																</td>

															</tr>
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
																	结束日期：
																	<input type="text" style="width: 120px" id="endDate"
																		class="wdate" maxlength="10"
																		name="${GNNT_}primary.clearDate[<=][date]"
																		value='${oldParams["primary.clearDate[<=][date]"]}'
																		onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
																</td>
																<td class="table3_td_1tjcx" align="left">
																	综合会员编号：
																	<input type="text" name="${GNNT_}memberno[=][String]"
																		id="memberNo" value="${oldParams['memberno[=][String]'] }"
																		class="input_textmin">
																</td>
																<td align="left" colspan="2" class="table3_td_anniu">
																	<input type="button" class="button_02"
																		onclick="select1()" value="查询" />
																	&nbsp;
																	<input type="button" class="button_03"
																		onclick="mySelfReset()" value="重置" />
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
													var="customerFund"
													action="${basePath}/report/customerFunds/customerFundsReportQuery.action"
													title="" minHeight="345" listWidth="210%"
													retrieveRowsCallback="limit" sortRowsCallback="limit"
													filterRowsCallback="limit" csvFileName="导出列表.csv"
													style="table-layout:fixed">
													<ec:row ondblclick="">
														<ec:column width="2%" property="_0" title="序号"
															value="${GLOBALROWCOUNT}" sortable="false"
															filterable="false" />
														<ec:column property="clearDate[=][date]" title="结算日期"
															width="3%" style="text-align:left; " ellipsis="true">
															<fmt:formatDate value="${customerFund.clearDate}"
																pattern="yyyy-MM-dd" />
														</ec:column>
														<ec:column property="memberNo[like]" title="综合会员编号"
															width="4%" style="text-align:left; "
															value="${customerFund.memberNo}" ellipsis="true" />
														<ec:column property="memberName[like]" title="综合会员名称"
															width="5%" style="text-align:left; "
															value="${customerFund.memberName}" ellipsis="true" />
														<ec:column property="organizationName[=][String]"
															title="机构" width="5%"
															value="${customerFund.organizationName}"
															style="text-align:right; " ellipsis="true">
														</ec:column>
														<ec:column property="primary.brokerName[=][String]"
															title="居间" width="5%" value="${customerFund.brokerName}"
															style="text-align:right; " ellipsis="true">
														</ec:column>
														<ec:column property="customerNo[=][String]" title="交易账号"
															width="5%" style="text-align:right; "
															value="${customerFund.customerNo}" ellipsis="true">
														</ec:column>
														<ec:column property="customerName[like]" title="客户名称"
															width="5%" style="text-align:left; "
															value="${customerFund.customerName}" ellipsis="true" />
														<ec:column property="begincapital[=][double]" title="期初权益"
															width="5%" style="text-align:right; ">
															<fmt:formatNumber value="${customerFund.begincapital}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="fundio[=][double]" title="出入金"
															width="5%" style="text-align:right; ">
															<fmt:formatNumber value="${customerFund.fundio}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="closepl[=][double]" title="平仓盈亏"
															width="4%" style="text-align:right; ">
															<fmt:formatNumber value="${customerFund.closepl}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="holdpl[=][double]" title="持仓盈亏"
															width="4%" style="text-align:right; ">
															<fmt:formatNumber value="${customerFund.holdpl}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="plsum[=][double]" title="盈亏合计"
															width="4%" style="text-align:right;">
															<fmt:formatNumber value="${customerFund.plsum}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="mktfee[=][double]" title="交易所存留手续费"
															width="5%" style="text-align:right; ">
															<fmt:formatNumber value="${customerFund.mktfee}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="memberfee[=][double]"
															title="综合会员存留手续费" width="5%" style="text-align:right; ">
															<fmt:formatNumber value="${customerFund.memberfee}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="customerfee[=][double]"
															title="收客户手续费" width="4%" style="text-align:right;">
															<fmt:formatNumber value="${customerFund.customerfee}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="delayfee[=][double]" title="收客户延期费"
															width="4%" style="text-align:right;">
															<fmt:formatNumber value="${customerFund.delayfee}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="mktdelayfee[=][double]" title="交易所存留延期费"
															width="5%" style="text-align:right;">
															<fmt:formatNumber value="${customerFund.mktdelayfee}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="memberdelayfee[=][double]" title="会员存留延期费"
															width="4%" style="text-align:right;">
															<fmt:formatNumber value="${customerFund.memberdelayfee}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="margin[=][double]" title="占用保证金"
															width="4%" style="text-align:right;">
															<fmt:formatNumber value="${customerFund.margin}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="endcapital[=][double]" title="期末权益"
															width="5%" style="text-align:right">
															<fmt:formatNumber value="${customerFund.endcapital}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="risk_log[=][String]" title="风险率"
															width="2%" value="${customerFund.risk_log}"
															filterable="false" sortable="false"
															style="text-align:right" />
													</ec:row>
													<c:if test="${ifHas eq 1}">		
														<ec:extendrow>
															<td>
															          合计:
															</td>
															<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
															<td align="right" style="font-weight:bold" >${begincapitalAll}</td>
															<td align="right" style="font-weight:bold">${fundioAll}</td>
															<td align="right" style="font-weight:bold">${closeplAll}</td>
															<td align="right" style="font-weight:bold">${holdplAll}</td>
															<td align="right" style="font-weight:bold">${plsumAll}</td>
															<td align="right" style="font-weight:bold">${mktfeeAll}</td>
															<td align="right" style="font-weight:bold">${memberfeeAll}</td>
															<td align="right" style="font-weight:bold">${customerfeeAll}</td>
															<td align="right" style="font-weight:bold">${delayfeeAll }</td>
															<td align="right" style="font-weight:bold">${mktdelayfeeAll}</td>
															<td align="right" style="font-weight:bold">${memberdelayfeeAll}</td>
															<td align="right" style="font-weight:bold">${marginAll }</td>
															<td align="right" style="font-weight:bold">${endcapitalAll }</td>
															<td>&nbsp;</td>
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
	frm.action = "${basePath}/report/customerFunds/customerFundsReportQuery.action";
	checkQueryDate(frm.startDate.value, frm.endDate.value);
	frm.action = action;

}
function init() {
	if (frm.startDate.value == "" && frm.endDate.value == "") {
		frm.startDate.value = '${date}';
		frm.endDate.value = '${date}';
	}

}

function clickText() {
	var isRelated = document.getElementById("isRelated").value;
	var url = "${basePath}/broke/memberInfoTree/forTree.action?isRelated="
			+ isRelated;
	var result = window
			.showModalDialog(url, window,
					"dialogWidth=400px; dialogHeight=570px; status=yes;scroll=yes;help=no;");

}

function mySelfReset() {//清空
	window.parent.frames["topFrame1"].document.getElementById('tree').value = "";
	var pathRrl = frm.action.toString();
	var au = '111111';
	if (typeof (AUsessionId) != "undefined") {
		au = AUsessionId;
	}
	var urlArray = pathRrl.split("?");
	if (urlArray.length == 1) {
		pathRrl = pathRrl + '?AUsessionId=' + au + "&noQuery=true";
	} else if (urlArray.length == 2) {
		pathRrl = pathRrl + '&AUsessionId=' + au + "&noQuery=true";
	}
	window.location.href = pathRrl;
}
</script>