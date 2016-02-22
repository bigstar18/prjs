<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/session.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>交易银行资金结算表</title>
		<link href="${basePath }/report/report_css.css" rel="stylesheet"
			type="text/css" />
		<import namespace="MEBS"
			implementation="${basePath}/report/public/calendar.htc">
	</head>

	<body class="report_body" onload="init();">
		<table width="100%" height="100%" border="0" cellspacing="0"
			cellpadding="0">
			<tr height="100%">
				<td>
					<table width="98%" height="100%" border="0" align="center"
						cellpadding="0" cellspacing="0">
						<tr height="10%">
							<td>
								<div class="report_bor01">
									<form
										action="${basePath}/report/settlementBankFund/settlementBankFundReportQuery.action?LOGINID=${LOGINID}&username=${username}"
										name="frm" id="frm" method="post" target="report">
										<table border="0" width="100%" cellpadding="0" class="table2_td_width">
											<tr class="report_w12h">
												<td class="table3_td_1tjcxmid" align="left">
													&nbsp;解约开始日期：
													<input type="text" style="width: 100px" id="startDate2"
														class="wdate" maxlength="10"
														name="${GNNT_}primary.b_date[>=][date]"
														value='${oldParams["primary.b_date[>=][date]"]}'
														onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
												</td>
												<td class="table3_td_1tjcxmid" align="left">
													解约结束日期：
													<input type="text" style="width: 100px" id="endDate2"
														class="wdate" maxlength="10"
														name="${GNNT_}primary.b_date[<=][date]"
														value='${oldParams["primary.b_date[<=][date]"]}'
														onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
												</td>
											</tr>
											<tr>
												<td align="left" class="table3_td_1tjcx">
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;签约银行：
													<select name="${GNNT_}primary.bankcode[=][String]" style="width: 100px">
														<option value="">
															全部
														</option>
														<option value="102">
															中国农业银行
														</option>
														<option value="S">
															中国光大银行
														</option>
														<option value="S">
															中国交通银行
														</option>
													</select>
												</td>
												<td align="left">
													<button class="btn_anniu" onclick="select1()">
														查询
													</button>&nbsp;
													<button class="btn_anniu" onclick="myReset()">
														重置
													</button>&nbsp;
													<button class="btn_anniu" onclick="xls()">
														导出
													</button>
													<input type="hidden" id="type" name="type">
												</td>

											</tr>

										</table>
									</form>
								</div>
							</td>
						</tr>
						<tr height="90%">
							<td class="report_bor01">
								<iframe name="report" frameborder="0"
									src="${basePath}/report/noDateReport.jsp" scrolling="auto"
									width="100%" height="100%"></iframe>
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
	frm.action = "${basePath}/report/settlementBankFund/settlementBankFundReport.action?LOGINID=${LOGINID}&username=${username}";

	//alert(!checkReportSDate(frm.startDate1.value,frm.endDate1.value,"签约开始日期","签约结束日期"));
	if (checkReportSDate(frm.startDate2.value, frm.endDate2.value,
					"解约开始日期", "解约结束日期")) {
		frm.submit();

	}
	;

	//alert(!checkReportSDate(frm.startDate2.value,frm.endDate2.value,"解约开始日期","解约结束日期"));

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
	frm.startDate2.value = '${date}';
	frm.endDate2.value = '${date}';
}
function clickText() {
	var memberIds = frm.memberIds.value;
	var url = "${basePath}/report/customer/memberInfoList.action?LOGINID=${LOGINID}&username=${username}&oldMemberIds="
			+ memberIds;
	var result = window.showModalDialog(url, '',
			"dialogWidth=350px;dialogHeight=520px");
	if (result != null && result != '') {
		var result1 = result.split('####');
		frm.memberIds.value = result1[0];
		frm.memberNames.value = result1[1];
	}
}
function checkReportSDate(startDate, endDate, startName, endName) {

	var now = new Date();
	var s = new Date(Date.parse(startDate.replace(/-/g, "/")));
	var e = new Date(Date.parse(endDate.replace(/-/g, "/")));
if (s!="" && s > now ) {
		alert(startName+"不能大于当前日期");
		return false;
	}else if(e!="" &&　e>now){
		alert(endName+"不能大于当前日期");
		return false;
	}
	else if(s>e){
		alert(startName+"不能大于"+endName);
		return false;
	}else{
		return true;
	}
	
}
</script>