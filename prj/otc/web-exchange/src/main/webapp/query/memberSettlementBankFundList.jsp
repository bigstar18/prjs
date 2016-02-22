
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>
<html xmlns:MEBS>
	<head>
		<title>综合会员银行资金结算</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
		<IMPORT namespace="MEBS"
			implementation="${basePath}/common/jslib/calendar.htc">
	</head>
	<body onload="init();">
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
							<form name="frm"
								action="${basePath}/query/queryMemberSettlementBankFundSearch/list.action?sortName=primary.b_date&sortOrder=true"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_widthmid">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0" style="table-layout: fixed">
													<tr>
														<td class="table3_td_1mid" align="left">
															综合会员编号:
															<label>
																<input type="text" name="${GNNT_}firm.firmId[=][String]"
																	id="firmId" value="${oldParams['firm.firmId[=][String]'] }"
																	class="input_textmin" />
															</label>
														</td>
														<td class="table3_td_1mid" align="left">
															综合会员名称:
															<label>
																<!-- <input type="text" name="${GNNT_}firm.firmName[like]"
																	id="firmName"
																	value="${oldParams['firm.firmName[like]'] }" onclick="clickText()"
																	readonly=true size="8" class="input_textmin" /> -->
																<input type="text" id="memberNames"
																name="${ORIGINAL_}memberNames"
																value="${original_memberNames}" onclick="clickText()"
																readonly=true size="8" class="input_textmin">
																<input type="hidden" name="${ORIGINAL_}memberIdsTwo"
																id="memberIds" value="${original_memberIdsTwo}"
																class="input_text">
															</label>
														</td>
														<td class="table3_td_1" align="left">
															银行名称:
															<span class="right_03zi"><select id="bankName"
																	name="${GNNT_}bank.bankId[=][String]"
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
																frm.bankName.value = '${oldParams["bank.bankId[=][String]"] }';
															</script>

														</td>

													</tr>
													<tr>
														<td class="table3_td_1" align="left">
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;开始日期:
															<label>
																<input type="text" style="width: 100px" id="beginDate"
																	class="wdate" maxlength="10"
																	name="${GNNT_}b_date[>=][date]"
																	value='${oldParams["b_date[>=][date]"]}'
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															</label>
														</td>
														<td class="table3_td_1" align="left">
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;结束日期:
															<label>
																<input type="text" style="width: 100px" id="endDate"
																	class="wdate" maxlength="10"
																	name="${GNNT_}b_date[<=][date]"
																	value='${oldParams["b_date[<=][date]"]}'
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															</label>
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
											var="settlementBankFund"
											action="${basePath}/query/queryMemberSettlementBankFundSearch/list.action"
											title="" minHeight="345" listWidth="110%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" csvFileName="导出列表.csv"
											style="table-layout:fixed">
											<ec:row ondblclick="">
												<ec:column width="2%" property="_0" title="序号"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<ec:column property="b_date[=][date]" title="结算日期"
													width="4%" style="text-align:left; " ellipsis="true">
													<fmt:formatDate value="${settlementBankFund.b_date}"
														pattern="yyyy-MM-dd" />
												</ec:column>
												<ec:column property="firm.firmId[like]" title="综合会员编号"
													width="4%" style="text-align:left; "
													value="${settlementBankFund.firmId}" ellipsis="true" />
												<ec:column property="firm.firmName[like]" title="综合会员名称"
													width="5%" style="text-align:left; "
													value="${settlementBankFund.firmName}" ellipsis="true" />
												<ec:column property="bank.bankName[like]" title="银行名称"
													width="4%" style="text-align:left; "
													value="${settlementBankFund.bankName}" ellipsis="true" />
												<ec:column property="fundio[=][double]" title="出入金"
													width="5%" style="text-align:right; ">
													<fmt:formatNumber value="${settlementBankFund.fundio}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="lastcapital[=][double]" title="期初权益"
													width="6%" style="text-align:right; ">
													<fmt:formatNumber value="${settlementBankFund.lastcapital}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="closepl[=][double]" title="平仓盈亏"
													width="4%" style="text-align:right; ">
													<fmt:formatNumber value="${settlementBankFund.closepl}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="holdpl[=][double]" title="持仓盈亏"
													width="4%" style="text-align:right; ">
													<fmt:formatNumber value="${settlementBankFund.holdpl}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="tradefee[=][double]" title="手续费"
													width="4%" style="text-align:right; ">
													<fmt:formatNumber value="${settlementBankFund.tradefee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="delayfee[=][double]" title="延期费"
													width="4%" style="text-align:right; ">
													<fmt:formatNumber value="${settlementBankFund.delayfee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="capital[=][double]" title="期末权益"
													width="6%" style="text-align:right; ">
													<fmt:formatNumber value="${settlementBankFund.capital}"
														pattern="#,##0.00" />
												</ec:column>
											</ec:row>
											<c:if test="${ifHas eq 1}">		
												<ec:extendrow>
													<td> 合计:</td>
													<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
													<td align="right" style="font-weight:bold">${fundioAll}</td>
													<td align="right" style="font-weight:bold">${lastcapitalAll}</td>
													<td align="right" style="font-weight:bold">${closeplAll}</td>
													<td align="right" style="font-weight:bold">${holdplAll}</td>
													<td align="right" style="font-weight:bold">${tradefeeAll}</td>
													<td align="right" style="font-weight:bold">${delayfeeAll}</td>
													<td align="right" style="font-weight:bold">${capitalAll}</td>
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
		<!-- 编辑和过滤所使用的操作模板 -->
		<textarea id="esc_tradeType" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="buildTradeType[=]">
			<ec:options items="tradeTypeMap" />
		</select>
	    </textarea>
		<!-- 编辑和过滤所使用的建仓方向标志模板 -->
		<textarea id="esc_bsFlag" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="build_flag[=]">
			<ec:options items="bsFlagMap" />
		</select>
	    </textarea>

		<!-- 编辑和过滤所使用的平仓方向标志模板 -->
		<textarea id="esc_de_bsFlag" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="de_build_flag[=]">
			<ec:options items="bsFlagMap" />
		</select>
	    </textarea>
	</body>
</html>

<SCRIPT type="text/javascript">
		function init(){
			if(frm.beginDate.value=="" && frm.endDate.value==""){
				 frm.beginDate.value='${date}';
				 frm.endDate.value='${date}';
			 }
		}
		function search1(){
			checkTotalQueryDate(frm.beginDate.value,frm.endDate.value,"H");
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
		</SCRIPT>