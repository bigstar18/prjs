<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/session.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>特别会员出金阈值计算表</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
		<import namespace="MEBS"
			implementation="${basePath}/report/public/calendar.htc">
		<link href="${basePath }/report/report_css.css" rel="stylesheet"
			type="text/css" />
	</head>
	<body onload="init();">
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
							<form
								action="${basePath}/report/specialMemberFundioThreshold/specialMemberFundioThresholdReportQuary.action"
								name="frm" id="frm" method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_widthmid">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0" style="table-layout: fixed">
													<tr>
														<td class="table3_td_1tjcxmid" align="left">
															特别会员编号：
															<input type="text" id="customerNo"
																name="${GNNT_}s_memberNo[like]"
																value="${oldParams['s_memberNo[like]'] }" size="14"
																class="input_textmin" />
														</td>
														<td class="table3_td_1tjcxmid" align="left">
															特别会员名称：
															<input type="text" id="memberNames"
																name="${ORIGINAL_}memberNames"
																value="${original_memberNames}" onclick="clickText()"
																readonly=true size="8" class="input_textmin">
															<input type="hidden" name="${ORIGINAL_}smemberIds"
																id="memberIds" value="${original_smemberIds}"
																class="input_text">
														</td>
														<td class="table3_td_1tjcx_1">
															&nbsp;
														</td>
													</tr>
													<tr>
														<td class="table3_td_1tjcxmid" align="left">
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;开始日期：
															<input type="text" style="width: 100px" id="startDate"
																class="wdate" maxlength="10"
																name="${GNNT_}primary.clearDate[>=][date]"
																value='${oldParams["primary.clearDate[>=][date]"]}'
																onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
														</td>
														<td class="table3_td_1tjcxmid" align="left">
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;结束日期：
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
											var="specialMemberFundioThresh"
											action="${basePath}/report/specialMemberFundioThreshold/specialMemberFundioThresholdReportQuary.action"
											title="" minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" csvFileName="导出列表.csv"
											style="table-layout:fixed">
											<ec:row ondblclick="">
												<ec:column width="2%" property="_0" title="序号"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<ec:column property="clearDate[=][date]" title="结算日期"
													width="5%" style="text-align:left; " ellipsis="true">
													<fmt:formatDate
														value="${specialMemberFundioThresh.clearDate}"
														pattern="yyyy-MM-dd" />
												</ec:column>
												<ec:column property="s_memberNo[like]" title="特别会员编号"
													width="5%" style="text-align:right; " ellipsis="true"
													value="${specialMemberFundioThresh.s_memberNo}" />
												<ec:column property="s_memberName[like]" title="特别会员名称"
													width="5%" style="text-align:left; "
													value="${specialMemberFundioThresh.s_memberName}"
													ellipsis="true" />
												<ec:column property="endcapital[=][double]" title="期末权益（元）"
													width="5%" style="text-align:right; " ellipsis="true"
													format="#,##0.00" filterable="false" sortable="false">
													<fmt:formatNumber
														value="${specialMemberFundioThresh.endcapital}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="minriskfund" title="阈值设置值（万元）"
													width="5%" style="text-align:right; " filterable="false"
													sortable="false" ellipsis="true"
													calcTitle="合计：">
													<fmt:formatNumber
														value="${specialMemberFundioThresh.minriskfund}"
														pattern="#,##0.00" />
												</ec:column>

											</ec:row>
										</ec:table>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</body>
	<!-- 编辑和过滤所使用的 通用的文本框模板 -->
	<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value=""
			onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;" name="" />
		</textarea>
	<script type="text/javascript">
function select1() {
	var action = frm.action;
	frm.action = "${basePath}/report/specialMemberFundioThreshold/specialMemberFundioThresholdReportQuary.action";
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
</script>