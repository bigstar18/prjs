
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>
<html xmlns:MEBS>
	<head>
		<title>客户银行资金结算</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
		<IMPORT namespace="MEBS"
			implementation="${basePath}/common/jslib/calendar.htc">
	</head>
	<body onload="init()">
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
							<form name="frm"
								action="${basePath}/query/queryCustomerSettlementBankFundSearch/list.action?sortName=primary.b_date&sortOrder=true"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_widthmid">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0" style="table-layout: fixed">
													<tr>
														<input type="hidden" name="${GNNT_}isRelated"
															id="isRelated" value="${oldParams['isRelated']}"
															class="input_text">
														<input type="hidden" name="${GNNT_}memberInfoIds"
															id="memberInfoIds" value="${oldParams['memberInfoIds']}"
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
														<input type="hidden" name="tree" id="tree" value="${tree}"
															class="input_text">
														<td class="table3_td_1tjcx" align="left">
															客户归属:
															<input type="text" id="memberNames"
																name="${GNNT_}memberNames"
																value="${oldParams['memberNames']}" readonly=true
																class="input_textmin">
															<a href="javascript:clickText();"><img
																	align="absmiddle" src="<%=skinPath%>/cssimg/kh.gif">
															</a>
														</td>
														<td class="table3_td_1mid" align="left">
															交易账号:&nbsp;
															<label>
																<input type="text" name="${GNNT_}primary.customerNo[like]"
																	id="firmno"
																	value="${oldParams['primary.customerNo[like]'] }"
																	class="input_text" />
															</label>
														</td>
														<td class="table3_td_1" align="left">
															账号名称:&nbsp;
															<label>
																<input type="text" name="${GNNT_}primary.customerName[like]"
																	id="firmName"
																	value="${oldParams['primary.customerName[like]'] }"
																	class="input_textmin" />
															</label>
														</td>
														<td class="table3_td_1" align="left">
															银行名称:&nbsp;
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
																</select> </span>
															<script type="text/javascript">
																frm.bankName.value = '${oldParams["primary.bankCode[=][String]"] }';
															</script>
														</td>

													</tr>
													<tr>
														<td class="table3_td_1" align="left">
															开始日期:&nbsp;
															<label>
																<input type="text" style="width: 100px" id="beginDate"
																	class="wdate" maxlength="10"
																	name="${GNNT_}b_date[>=][date]"
																	value='${oldParams["b_date[>=][date]"]}'
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															</label>
														</td>
														<td class="table3_td_1" align="left">
															结束日期:&nbsp;
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
															</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onClick="mySelfReset()">
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
											action="${basePath}/query/queryCustomerSettlementBankFundSearch/list.action"
											title="" minHeight="345" listWidth="110%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" csvFileName="导出列表.csv"
											style="table-layout:fixed">
											<ec:row ondblclick="">
												<ec:column width="3%" property="_0" title="序号"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />

												<ec:column property="b_date[=][date]" title="结算日期"
													width="5%" style="text-align:left; " ellipsis="true">
													<fmt:formatDate value="${settlementBankFund.b_date}"
														pattern="yyyy-MM-dd" />
												</ec:column>
												<ec:column property="primary.customerNo[like]" title="交易账号"
													width="8%" style="text-align:left; "
													value="${settlementBankFund.customerNo}" ellipsis="true" />
												<ec:column property="primary.customerName[like]" title="账号名称"
													width="7%" style="text-align:left; "
													value="${settlementBankFund.customerName}" ellipsis="true" />
												<ec:column property="primary.bankName[like]" title="银行名称"
													width="6%" style="text-align:left; "
													value="${settlementBankFund.bankName}" ellipsis="true" />
												<ec:column property="fundio[=][double]" title="出入金"
													width="8%" style="text-align:right; ">
													<fmt:formatNumber value="${settlementBankFund.fundio}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="lastcapital[=][double]" title="期初权益"
													width="8%" style="text-align:right; ">
													<fmt:formatNumber value="${settlementBankFund.lastcapital}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="closepl[=][double]" title="平仓盈亏"
													width="6%" style="text-align:right; ">
													<fmt:formatNumber value="${settlementBankFund.closepl}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="holdpl[=][double]" title="持仓盈亏"
													width="6%" style="text-align:right; ">
													<fmt:formatNumber
														value="${settlementBankFund.holdpl}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="tradefee[=][double]" title="手续费"
													width="6%" style="text-align:right; ">
													<fmt:formatNumber value="${settlementBankFund.tradefee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="delayfee[=][double]" title="延期费"
													width="6%" style="text-align:right; ">
													<fmt:formatNumber value="${settlementBankFund.delayfee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="capital[=][double]" title="期末权益"
													width="8%" style="text-align:right; ">
													<fmt:formatNumber
														value="${settlementBankFund.capital}"
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
	
		function search1(){
			checkTotalQueryDate(frm.beginDate.value,frm.endDate.value,"H");
		}
function clickText() {
    var isRelated=document.getElementById("isRelated").value;
	var url = "${basePath}/broke/memberInfoTree/forTree.action?isRelated="+isRelated;
	var result=window.showModalDialog(url, window, "dialogWidth=400px; dialogHeight=570px; status=yes;scroll=yes;help=no;");
}

function mySelfReset() {//清空
		window.parent.frames["topFrame1"].document.getElementById('tree').value="";
		var pathRrl=frm.action.toString();
		var au = '111111';
		if (typeof (AUsessionId) != "undefined") {
			au = AUsessionId;
		}
		var urlArray=pathRrl.split("?");
	  	if(urlArray.length==1){
			pathRrl=pathRrl+'?AUsessionId='+au+"&noQuery=true";
		}else if(urlArray.length==2){
			pathRrl=pathRrl+'&AUsessionId='+au+"&noQuery=true";
		}
		window.location.href=pathRrl;
	}
function init(){
	if(frm.beginDate.value=="" && frm.endDate.value==""){
				 frm.beginDate.value='${date}';
				 frm.endDate.value='${date}';
			 }
}
		</SCRIPT>