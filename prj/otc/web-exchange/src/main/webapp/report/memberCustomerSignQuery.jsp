<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/session.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>会员客户开户统计表</title>
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
								action="${basePath}/report/memberCustomerSign/memberCustomerSignReportQuary.action"
								name="frm" id="frm" method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_widthmax">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0" style="table-layout: fixed">
													<tr>
														<td class="table3_td_1tjcx" align="left">
															综合会员编号：
															<input type="text" name="${GNNT_}primary.memberNo[=][String]"
																id="memberNo"
																value="${oldParams['primary.memberNo[=][String]'] }"
																class="input_textmin">
														</td>
														<td class="table3_td_1tjcx" align="left">
															综合会员名称：
															<input type="text" id="memberNames"
																name="${ORIGINAL_}memberNames"
																value="${original_memberNames}" onclick="clickText()"
																readonly=true size="8" class="input_textmin">
															<input type="hidden" name="${ORIGINAL_}memberIds"
																id="memberIds" value="${original_memberIds}"
																class="input_text">
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
											var="memberCustomerSign"
											action="${basePath}/report/memberCustomerSign/memberCustomerSignReportQuary.action"
											title="" minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" csvFileName="导出列表.csv"
											style="table-layout:fixed">
											<ec:row recordKey="">
												<ec:column width="3%" property="_0" title="序号"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<ec:column property="cleardate[=][date]" title="结算日期"
													width="5%" style="text-align:left; " ellipsis="true">
													<fmt:formatDate value="${memberCustomerSign.clearDate}"
														pattern="yyyy-MM-dd" />
												</ec:column>
												<ec:column property="primary.memberNo[like]" title="综合会员编号"
													width="6%" style="text-align:left; "
													value="${memberCustomerSign.memberNo}" ellipsis="true" />
												<ec:column property="primary.memberName[like]"
													title="综合会员名称" width="8%" style="text-align:left; "
													value="${memberCustomerSign.memberName}" ellipsis="true" />
												<ec:column property="primary.contact[like]" title="综合会员交易账号"
													width="6%" style="text-align:left; "
													value="${memberCustomerSign.contact}" ellipsis="true" />
												<ec:column property="primary.bankName[like]" title="签约银行"
													width="6%" style="text-align:left; "
													value="${memberCustomerSign.bankName}" ellipsis="true" />
												<ec:column property="primary.signcount[=][int]"
													title="今日签约客户数" width="6%" style="text-align:right; "
													value="${memberCustomerSign.signcount}" />
												<ec:column property="primary.totalsigncount[=][int]"
													title="签约总计" width="5%" style="text-align:right; "
													value="${memberCustomerSign.totalsigncount}" />
												<ec:column property="primary.designcount[=][int]"
													title="今日解约客户数" width="6%" style="text-align:right; "
													value="${memberCustomerSign.designcount}" />
												<ec:column property="primary.totaldesigncount[=][int]"
													title="解约总计" width="5%" style="text-align:right; "
													value="${memberCustomerSign.totaldesigncount}"/>
											</ec:row>
											<c:if test="${ifHas eq 1}">		
												<ec:extendrow>
													<td> 合计:</td>
													<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
													<td align="right" style="font-weight:bold">${signcountAll}</td>
													<td align="right" style="font-weight:bold">${totalsigncountAll}</td>
													<td align="right" style="font-weight:bold">${designcountAll}</td>
													<td align="right" style="font-weight:bold">${totaldesigncountAll}</td>
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
	frm.action = "${basePath}/report/memberCustomerSign/memberCustomerSignReportQuary.action";
	checkQueryDate(frm.startDate.value, frm.endDate.value);
	frm.action = action;
}
function xls() {
	frm.type.value = "xls";
	select1();
	frm.type.value = "";
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

function init() {
	if (frm.startDate.value == "" && frm.endDate.value == "") {
		frm.startDate.value = '${date}';
		frm.endDate.value = '${date}';
	}

}
</script>