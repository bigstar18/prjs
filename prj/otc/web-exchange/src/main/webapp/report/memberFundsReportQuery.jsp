<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/session.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>会员资金状况</title>
		<link href="${basePath }/report/report_css.css" rel="stylesheet"
			type="text/css" />
		<import namespace="MEBS"
			implementation="${basePath}/report/public/calendar.htc">
	</head>

	<body class="report_body" onload="init();">
		<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
			<tr height="100%">
				<td>
					<table width="98%" height="100%" border="0" align="center" cellpadding="0"
						cellspacing="3">
						<tr height="13%">
							<td>
								<div class="report_bor01">
									<form
										action="${basePath}/report/memberFunds/memberFundsReportQuery.action"
										name="frm" id="frm" method="post" target="report">
										<table width="100%" border="0" cellspacing="0" class="table2_td_widthmax" cellpadding="0">
											<tr class="report_w12h">
												
											   <td class="table3_td_1" align="left">&nbsp;&nbsp;会员编号： 
													<input type="text" name="${GNNT_}memberno[=][String]" id="memberNo"
																value="${oldParams['memberno[=][String]'] }" class="input_textmin">
												</td>
												<td class="table3_td_1" align="left">会员名称： 
													<input type="text" id="memberNames"
														name="${ORIGINAL_}memberNames"
														value="${original_memberNames}" onclick="clickText()"
														readonly=true size="8" class="input_textmin">
														<input type="hidden" name="${ORIGINAL_}memberIds"
														id="memberIds" value="${original_memberIds}"
														class="input_text">
												</td>
												<td class="table3_td_1" align="left">风险率>=： 
													<input type="text" name="${GNNT_}primary.risk[>=][Double]" id="risk1"
																value="${oldParams['primary.risk[>=][Double]'] }"  style="width:90;background-color: #FFFFFF;border: 1px solid #7f9db9;">%
												</td>
												<td class="table3_td_1" align="left">&nbsp;&nbsp;风险率<=： 
													<input type="text" name="${GNNT_}primary.risk[<=][Double]" id="risk2"
																value="${oldParams['primary.risk[<=][Double]'] }" style="width:90;background-color: #FFFFFF;border: 1px solid #7f9db9;">%
												</td>
												
											</tr>
											<tr>
												<td class="table3_td_1" align="left">
													&nbsp;&nbsp;开始日期：
													<input type="text" style="width: 100px" id="startDate"
															class="wdate" maxlength="10"
															name="${GNNT_}trunc(primary.cleardate)[>=][date]"
															value='${oldParams["trunc(primary.atClearDate)[>=][date]"]}'
															onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
												</td>
												<td class="table3_td_1" align="left">
													结束日期：
													<input type="text" style="width: 100px" id="endDate"
															class="wdate" maxlength="10"
															name="${GNNT_}trunc(primary.cleardate)[<=][date]"
															value='${oldParams["trunc(primary.atClearDate)[<=][date]"]}'
															onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
												</td>
												
												<td align="left" colspan="2">
													<input type="button" class="button_02" onclick="select1()"
														value="查询" />&nbsp;
													<input type="button" class="button_03"
														onclick="myReset()" value="重置" />&nbsp;
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
						<tr height="87%">
							<td class="report_bor01">
								<iframe name="report" src="${basePath }/report/noDateReport.jsp" frameborder="0" scrolling="auto"
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
	var action=frm.action;
	var risk1= document.getElementById("risk1").value;
	var risk2= document.getElementById("risk2").value;
	if(!flote(risk1,2) || !flote(risk2,2)){
		alert('风险率必须为数字且最多为两位小数');
		return false;
	}
	frm.action="${basePath}/report/memberFunds/memberfundsReport.action";
	checkQueryDate(frm.startDate.value,frm.endDate.value);
	frm.action=action;
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
	frm.startDate.value ='${date}';
	frm.endDate.value ='${date}';
}

function clickText() {
	var memberIds = frm.memberIds.value;
	var url = "${basePath}/report/customer/memberInfoList.action?oldMemberIds="
			+ memberIds;
	var result = window.showModalDialog(url,'',"dialogWidth=350px;dialogHeight=520px");
	if (result != null && result != '') {
		var result1 = result.split('####');
		frm.memberIds.value = result1[0];
		frm.memberNames.value = result1[1];
	}
}
</script>