<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/session.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>会员持仓汇总表</title>
		<link href="${basePath }/report/report_css.css" rel="stylesheet"
			type="text/css" />
		<import namespace="MEBS"
			implementation="${basePath}/report/public/calendar.htc">
	</head>

	<body class="report_body" onload="init();">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="2"></td>
			</tr>
			<tr>
				<td>
					<table width="98%" border="0" align="center" cellpadding="0"
						cellspacing="3">
						<tr>
							<td>
								<div class="report_bor01">
									<form
										action="${basePath}/report/memberHold/memberHoldReportQuary.action"
										name="frm" id="frm" method="post" target="report">
										<table width="820" border="0" class="table2_td_widthmax"
											cellspacing="0" cellpadding="0">

											<tr class="report_w12h">
												<td align="left" class="table3_td_1">
													&nbsp;&nbsp;&nbsp;&nbsp;会员编号：&nbsp;
													<input type="text" id="customerNo"
														name="${GNNT_}memberno[like]"
														value="${oldParams['memberno[like]'] }" size="14"
														class="input_textmin" />
												</td>
												<td align="left" class="table3_td_1">
													会员名称：&nbsp;
													<input type="hidden" name="${ORIGINAL_}memberIds"
														id="memberIds" value="${original_memberIds}"
														class="input_text">
													<input type="text" id="memberNames"
														name="${ORIGINAL_}memberNames"
														value="${original_memberNames}" onclick="clickText()"
														readonly=true size="8" class="input_textmin">
												</td>
												<td align="left" class="table3_td_1">
													商品：&nbsp;
													<select id="commodityId"
														name="${GNNT_}primary.commodityId[like]"
														class="input_textmin">
														<option value="">
															请选择
														</option>
														<c:forEach items="${commodityList}" var="commodit">
															<option value="${commodit.id}">
																${commodit.name }
															</option>
														</c:forEach>
													</select>
													<script type="text/javascript">
frm.commodityId.value='${oldParams['primary.commodityId[like]'] }';
													</script>
												</td>
											</tr>
											<tr>
												<td class="table3_td_1" align="left">
													&nbsp;&nbsp;&nbsp;&nbsp;开始日期：&nbsp;
													<input type="text" style="width: 100px" id="startDate"
														class="wdate" maxlength="10"
														name="${GNNT_}trunc(primary.cleardate)[>=][date]"
														value='${oldParams["trunc(primary.cleardate)[>=][date]"]}'
														onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
												</td>
												<td class="table3_td_1" align="left">
													结束日期：&nbsp;
													<input type="text" style="width: 100px" id="endDate"
														class="wdate" maxlength="10"
														name="${GNNT_}trunc(primary.cleardate)[<=][date]"
														value='${oldParams["trunc(primary.cleardate)[<=][date]"]}'
														onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
												</td>
												<td align="left">
													<input type="button" class="button_02" onclick="select1()"
														value="查询" />
													&nbsp;
													<input type="button" class="button_03" onclick="myReset()"
														value="重置" />
													&nbsp;
													<input type="button" class="button_02" value="导出"
														onclick="xls()" />
													<input type="hidden" id="type" name="type">
												</td>
											</tr>
										</table>
									</form>
								</div>
							</td>
						</tr>
						<tr>
							<td class="report_bor01">
								<iframe name="report" frameborder="0"
									src="${basePath}/report/noDateReport.jsp" scrolling="auto"
									width="100%" height="400"></iframe>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		</form>
	</body>
</html>
<script type="text/javascript">
function select1() {
	var action = frm.action;
	frm.action = "${basePath}/report/memberHold/memberHoldReport.action";
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
	frm.startDate.value = '${date}';
	frm.endDate.value ='${date}';
}
function clickText() {
	var memberIds = frm.memberIds.value;
	var url = "${basePath}/report/customer/memberInfoList.action?oldMemberIds="
			+ memberIds;
	var result = window.showModalDialog(url);
	if (result != null && result != '') {
		var result1 = result.split('####');
		frm.memberIds.value = result1[0];
		frm.memberNames.value = result1[1];
	}
}
</script>