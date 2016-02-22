<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/session.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>交易所手续费划转表</title>
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
								action="${basePath}/report/marketCollectDelimitDetail/marketCollectDelimitDetailQuery.action"
								name="frm" id="frm" method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_widthmax">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0" style="table-layout: fixed">
													<tr>
														<td class="table3_td_1tjcx_1" align="left">
															划入银行：
															<span class="right_03zi"><select id="bank"
																	name="${GNNT_}desBankCode[=][String]"
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
															frm.bank.value = '${oldParams["desBankCode[=][String]"] }';
															</script>
														</td>
														<td class="table3_td_1tjcx_1" align="left">
															划出银行：
															<span class="right_03zi"><select id="bank1"
																	name="${GNNT_}srcBankCode[=][String]"
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
															frm.bank1.value = '${oldParams["srcBankCode[=][String]"] }';
															</script>
														</td>
														<td class="table3_td_1tjcx_1" align="left">
															开始日期：
															<input type="text" style="width: 100px" id="startDate"
																class="wdate" maxlength="10"
																name="${GNNT_}primary.clearDate[>=][date]"
																value='${oldParams["primary.clearDate[>=][date]"]}'
																onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
														</td>
														<td class="table3_td_1tjcx_1" align="left">
															结束日期：
															<input type="text" style="width: 100px" id="endDate"
																class="wdate" maxlength="10"
																name="${GNNT_}primary.clearDate[<=][date]"
																value='${oldParams["primary.clearDate[<=][date]"]}'
																onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
														</td>
														<td class="table3_td_1tjcx_1" align="left">
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
											var="collectDelimit"
											action="${basePath}/report/marketCollectDelimitDetail/marketCollectDelimitDetailQuery.action"
											title="" minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" csvFileName="导出列表.csv"
											style="table-layout:fixed">
											<ec:row recordKey="">
												<ec:column width="3%" property="_0" title="序号"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<ec:column property="cleardate[=][date]" title="结算日期"
													width="4%" style="text-align:left; " ellipsis="true">
													<fmt:formatDate value="${collectDelimit.clearDate}"
														pattern="yyyy-MM-dd" />
												</ec:column>
												<ec:column property="desBankName[like]" title="划入行名称"
													width="7%" style="text-align:left; "
													value="${collectDelimit.desBankName}" ellipsis="true" />
												<ec:column property="desBankAccount[=][String]"
													title="划入行账号" width="8%" style="text-align:left; "
													value="${collectDelimit.desBankAccount}"  ellipsis="true"/>
												<ec:column property="desBankAccountName[=][String]"
													title="划入行账号名称" width="8%" style="text-align:left; "
													value="${collectDelimit.desBankAccountName}"  ellipsis="true"/>
												<ec:column property="srcBankName[like]" title="划出行名称"
													width="6%" style="text-align:left; "
													value="${collectDelimit.srcBankName}" ellipsis="true" />
												<ec:column property="srcBankAccount[=][String]"
													title="划出行账号" width="8%" style="text-align:left; "
													value="${collectDelimit.srcBankAccount}"  ellipsis="true"/>
												<ec:column property="srcBankAccountName[=][String]"
													title="划出行账号名称" width="8%" style="text-align:left; "
													value="${collectDelimit.srcBankAccountName}"  ellipsis="true"/>
												<ec:column property="offsetbalance[=][double]" title="划转金额"
													width="8%" style="text-align:right; ">
													<fmt:formatNumber value="${collectDelimit.offsetbalance}"
														pattern="#,##0.00" />
												</ec:column>
											</ec:row>
											<c:if test="${ifHas eq 1}">		
												<ec:extendrow>
													<td> 合计:</td>
													<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
													<td align="right" style="font-weight:bold">${offsetbalanceAll}</td>
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
	frm.action = "${basePath}/report/marketCollectDelimitDetail/marketCollectDelimitDetailQuery.action";
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