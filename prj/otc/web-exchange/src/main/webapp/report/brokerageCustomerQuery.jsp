<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/session.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>居间签约客户明细</title>
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
										action="${basePath}/report/brokerageCustomer/brokerageCustomerReportQuary.action?sortName=primary.openTime"
										name="frm" id="frm" method="post">
										<table border="0" cellpadding="0" cellspacing="0"
											class="table2_style">
											<tr>
												<td height="40" width="775">
													<div class="div2_top">
														<table width="800" border="0" cellspacing="0"
															cellpadding="0">
															<tr>
																<td class="table3_td_1tjcx" align="left">
																	 &nbsp; &nbsp; &nbsp; &nbsp;居间编号： &nbsp;
																	<input type="text" id="customerNo"
																		name="${GNNT_}brokerageno[like]"
																		value="${oldParams['brokerageno[like]'] }"
																		size="14" class="input_textmin" />
																</td>
																<td class="table3_td_1tjcx" align="left">
																	 &nbsp; &nbsp; &nbsp;&nbsp;居间名称： &nbsp;
																	<input type="text" id="name"
																		name="${GNNT_}primary.brokeragename[like]"
																		value="${oldParams['primary.brokeragename[like]'] }"
																		size="14" class="input_textmin" />
																</td>
																<td class="table3_td_1tjcx" align="left">
																	&nbsp;
																</td>
															</tr>
															<tr>
																<td class="table3_td_1tjcx" align="left">
																	&nbsp;签约开始日期：&nbsp;&nbsp;
																	<input type="text" style="width: 100px" id="startDate"
																		class="wdate" maxlength="10"
																		name="${GNNT_}trunc(primary.opentime)[>=][date]"
																		value='${oldParams["trunc(primary.opentime)[>=][date]"]}'
																		onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
																</td>
																<td class="table3_td_1tjcx" align="left">
																	签约结束日期：&nbsp;&nbsp;
																	<input type="text" style="width: 100px" id="endDate"
																		class="wdate" maxlength="10"
																		name="${GNNT_}trunc(primary.opentime)[<=][date]"
																		value='${oldParams["trunc(primary.opentime)[<=][date]"]}'
																		onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
																</td>
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
													var="brokerage"
													action="${basePath}/report/brokerageCustomer/brokerageCustomerReportQuary.action?sortName=primary.openTime"
													title="" minHeight="345" listWidth="210%"
													retrieveRowsCallback="limit" sortRowsCallback="limit"
													filterRowsCallback="limit" csvFileName="导出列表.csv"
													style="table-layout:fixed">
													<ec:row ondblclick="">
														<ec:column width="2%" property="_0" title="序号"
															value="${GLOBALROWCOUNT}" sortable="false"
															filterable="false" />
														<ec:column property="brokerageNo[like]" title="居间编号"
															width="5%" style="text-align:left; "
															value="${brokerage.brokerageNo}" ellipsis="true" />
														<ec:column property="brokerageName[like]" title="居间名称"
															width="5%" style="text-align:left; "
															value="${brokerage.brokerageName}" ellipsis="true" />
														<ec:column property="customerNo[=][double]" title="交易账号"
															width="5%" style="text-align:right; "
															value="${brokerage.customerNo}" ellipsis="true">
														</ec:column>
														<ec:column property="customerName[like]" title="客户名称"
															width="5%" style="text-align:left; "
															value="${brokerage.customerName}" ellipsis="true" />
														<ec:column property="status[like]" title="客户状态" width="5%"
															style="text-align:left; " ellipsis="true"
															editTemplate="ec_status">
															<c:set var="key">
																<c:out value="${brokerage.status}"></c:out>
															</c:set>
															${customerStatusMap[key]}
														</ec:column>
														<ec:column property="openTime[like]" title="签约日期"
															width="5%" style="text-align:left; " ellipsis="true">
															<fmt:formatDate value="${brokerage.openTime}"
																pattern="yyyy-MM-dd" />
														</ec:column>
														<ec:column property="bankname[like]" title="签约银行"
															width="5%" style="text-align:left; "
															value="${brokerage.bankname}" ellipsis="true" />
													</ec:row>
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
		<!-- 编辑和过滤所使用的状态模板 -->
		<textarea id="ec_status" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="status[=]">
			<ec:options items="customerStatusMap" />
		</select>
	    </textarea>
	</body>
</html>
<script type="text/javascript">

function select1() {
	var action = frm.action;
	frm.action = "${basePath}/report/brokerageCustomer/brokerageCustomerReportQuary.action?sortName=primary.openTime";
	checkQueryDate(frm.startDate.value, frm.endDate.value);
	frm.action = action;

}
function init() {
	frm.startDate.value = '${date}';
	frm.endDate.value = '${date}';
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