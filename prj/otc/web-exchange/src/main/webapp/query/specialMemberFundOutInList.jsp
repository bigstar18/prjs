<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>
<html xmlns:MEBS>
	<head>
		<title>可出资金查询</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
		<IMPORT namespace="MEBS"
			implementation="${basePath}/common/jslib/calendar.htc">
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
							<form name="frm"
								action="${basePath}/query/querySpecialMemberFundOutInSearch/list.action"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0" style="table-layout: fixed">
													<tr>
														<td class="table3_td_1mid" align="left">
															特别会员编号:
															<input type="text" class="input_text" id="firmId"
																	name="${GNNT_}primary.firmId[=][String]" size="14"
																	value="${oldParams['primary.firmId[=][String]'] }" />
														</td>
														<td class="table3_td_1tjcx" align="left">
															&nbsp;&nbsp; 特别会员名称:
															<input type="hidden" name="${ORIGINAL_}smemberIds"
																id="memberIds" value="${original_smemberIds}"
																class="input_text">
															<input type="text" id="memberNames"
																name="${ORIGINAL_}memberNames"
																value="${original_memberNames}" onclick="clickText()"
																readonly=true size="8" class="input_textmin">
														</td>
														<td class="table3_td_1" align="left">
															&nbsp;银行名称:&nbsp;
															<span class="right_03zi"><select id="bankName"
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
																</select>
															</span>
															<script type="text/javascript">
																frm.bankName.value = '${oldParams["primary.bankCode[=][String]"] }';
															</script>
														</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" onClick="search1()">
																查询
															</button>&nbsp;
															<button class="btn_cz" onClick="myReset()">
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
										<ec:table items="resultList"
											autoIncludeParameters="${empty param.autoInc}"
											var="customerFund"
											action="${basePath}/query/querySpecialMemberFundOutInSearch/list.action"
											title="" minHeight="345" listWidth="160%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" csvFileName="导出列表.csv"
											style="table-layout:fixed">
											<ec:row ondblclick="">
												<ec:column width="3%" property="_0" title="序号"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<ec:column property="primary.firmId[like]" title="特别会员编号"
													width="5%" style="text-align:left; "
													value="${customerFund.firmId}" ellipsis="true" />
												<ec:column property="primary.firmName[like]" title="特别会员名称"
													width="6%" style="text-align:left; "
													value="${customerFund.firmName}" ellipsis="true" />
												<ec:column property="primary.bankName[like]" title="银行名称"
													width="6%" style="text-align:left; "
													value="${customerFund.bankName}" ellipsis="true" />
												<ec:column property="primary.lastcapital[=][double]" title="银行账户期初权益"
													width="7%" style="text-align:right; ">
													<fmt:formatNumber value="${customerFund.lastcapital}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="primary.fundoutbank[=][double]" title="银行出金合计"
													width="7%" style="text-align:right; "
													value="${customerFund.fundoutbank}">
													<fmt:formatNumber value="${customerFund.fundoutbank}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="v_lastcapital[=][double]" title="交易账号期初权益"
													width="7%" style="text-align:right; ">
													<fmt:formatNumber value="${customerFund.v_lastcapital}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="primary.v_balancebegin[=][double]" title="交易账号期初可用保证金"
													width="8%" style="text-align:right; " >
													<fmt:formatNumber value="${customerFund.v_balancebegin}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="primary.v_balance[=][double]"
													title="交易账号当前可用保证金" width="8%" style="text-align:right; ">
													<fmt:formatNumber value="${customerFund.v_balance}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="primary.unfrozenmargen[=][double]" title="本日释放保证金"
													width="6%" style="text-align:right; ">
													<fmt:formatNumber value="${customerFund.unfrozenmargen}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="primary.v_fundin[=][double]" title="交易账号入金"
													width="7%" style="text-align:right; ">
													<fmt:formatNumber value="${customerFund.v_fundin}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="primary.v_fundout[=][double]" title="交易账号出金"
													width="7%" style="text-align:right; ">
													<fmt:formatNumber value="${customerFund.v_fundout}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="primary.minriskfund[=][double]" title="最低出金阈值"
													width="7%" style="text-align:right; ">
													<fmt:formatNumber value="${customerFund.minriskfund}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="primary.v_canoutfund[=][double]" title="交易账号可出资金"
													width="7%" style="text-align:right; ">
													<fmt:formatNumber value="${customerFund.v_canoutfund}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="primary.bank_canoutfund[=][double]" title="银行可出资金"
													width="7%" style="text-align:right; ">
													<fmt:formatNumber value="${customerFund.bank_canoutfund}"
														pattern="#,##0.00" />
												</ec:column>
											</ec:row>
											<c:if test="${ifHas eq 1}">		
												<ec:extendrow>
													<td> 合计:</td>
													<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
													<td align="right" style="font-weight:bold">${lastcapitalAll}</td>
													<td align="right" style="font-weight:bold">${fundoutbankAll}</td>
													<td align="right" style="font-weight:bold">${v_lastcapitalAll}</td>
													<td align="right" style="font-weight:bold">${v_balancebeginAll}</td>
													<td align="right" style="font-weight:bold">${v_balanceAll}</td>
													<td align="right" style="font-weight:bold">${unfrozenmargenAll}</td>
													<td align="right" style="font-weight:bold">${v_fundinAll}</td>
													<td align="right" style="font-weight:bold">${v_fundoutAll}</td>
													<td>&nbsp;</td>
													<td align="right" style="font-weight:bold">${v_canoutfundAll}</td>
													<td align="right" style="font-weight:bold">${bank_canoutfundAll}</td>
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
	</body>
</html>

<SCRIPT type="text/javascript">
		function search1(){
			frm.submit();
		}
function clickText() {
	var memberIds = frm.memberIds.value;
	var url = "${basePath}/report/specialMember/specialMemberList.action?original_oldMemberIds="
			+ memberIds;
	var result = window.showModalDialog(url, '',
			"dialogWidth=350px;dialogHeight=520px");
	if (result != null && result != '') {
		var result1 = result.split('####');
		frm.memberIds.value = result1[0];
		frm.memberNames.value = result1[1];
	}
}
		</SCRIPT>