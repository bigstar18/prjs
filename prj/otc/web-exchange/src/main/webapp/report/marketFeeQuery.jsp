<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/session.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>交易手续费</title>
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
										action="${basePath}/report/marketFee/marketFeeReportQuery.action"
										name="frm" id="frm" method="post">
										<table border="0" cellpadding="0" cellspacing="0"
											class="table2_style">
											<tr>
												<td class="table2_td_widthcdmax">
													<div class="div2_top">
														<table border="0" cellspacing="0" cellpadding="0">
															<tr>
																<td class="table3_td_1tjcx_1" align="left">
																	银行：
																	<span class="right_03zi"><select id="bank"
																			name="${GNNT_}bankCode[=][String]"
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
frm.bank.value = '${oldParams["bankCode[=][String]"] }';
</script>
																</td>
																<td class="table3_td_1tjcx" align="left">
																	开始日期：
																	<input type="text" style="width: 100px" id="startDate"
																		class="wdate" maxlength="10"
																		name="${GNNT_}primary.clearDate[>=][date]"
																		value='${oldParams["primary.clearDate[>=][date]"]}'
																		onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
																</td>

																<td class="table3_td_1tjcx" align="left">
																	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;结束日期：
																	<input type="text" style="width: 100px" id="endDate"
																		class="wdate" maxlength="10"
																		name="${GNNT_}primary.clearDate[<=][date]"
																		value='${oldParams["primary.clearDate[<=][date]"]}'
																		onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
																</td>
																<td align="left" colspan="2">
																	<input type="button" class="button_02"
																		onclick="select1()" value="查询" />
																	&nbsp;
																	<input type="button" class="button_03"
																		onclick="myReset()" value="重置" />
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
													var="marketFee"
													action="${basePath}/report/marketFee/marketFeeReportQuery.action"
													title="" minHeight="345" listWidth="100%"
													retrieveRowsCallback="limit" sortRowsCallback="limit"
													filterRowsCallback="limit" csvFileName="导出列表.csv"
													style="table-layout:fixed">
													<ec:row ondblclick="">
														<ec:column width="5%" property="_0" title="序号"
															value="${GLOBALROWCOUNT}" sortable="false"
															filterable="false" />
														<ec:column property="clearDate[=][date]" title="结算日期"
															width="10%" style="text-align:left; " ellipsis="true">
															<fmt:formatDate value="${marketFee.clearDate}"
																pattern="yyyy-MM-dd" />
														</ec:column>
														<ec:column property="bankName[like]" title="银行名称"
															width="15%" style="text-align:left; "
															value="${marketFee.bankName}" ellipsis="true" />
														<ec:column property="lastmarketfeebalance[=][double]" title="上日收取费用余额"
															width="15%" style="text-align:right; ">
															<fmt:formatNumber
																value="${marketFee.lastmarketfeebalance}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="marketfeenew[=][double]" title="本日增加额"
															width="10%" style="text-align:right; ">
															<fmt:formatNumber value="${marketFee.marketfeenew}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="marketfeeout[=][double]" title="本日划转额"
															width="10%" style="text-align:right; ">
															<fmt:formatNumber value="${marketFee.marketfeeout}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="marketfeebalance[=][double]" title="本日收取费用余额"
															width="10%" style="text-align:right; ">
															<fmt:formatNumber value="${marketFee.marketfeebalance}"
																pattern="#,##0.00" />
														</ec:column>
													</ec:row>
													<c:if test="${ifHas eq 1}">		
														<ec:extendrow>
															<td>
															          合计:
															</td>
															<td>&nbsp;</td><td>&nbsp;</td>
															<td align="right" style="font-weight:bold">${lastmarketfeebalanceAll}</td>
															<td align="right" style="font-weight:bold">${marketfeenewAll}</td>
															<td align="right" style="font-weight:bold">${marketfeeoutAll}</td>
															<td align="right" style="font-weight:bold">${marketfeebalanceAll}</td>
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
	frm.action = "${basePath}/report/marketFee/marketFeeReportQuery.action";
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