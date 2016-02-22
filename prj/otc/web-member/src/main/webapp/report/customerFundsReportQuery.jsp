<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/session.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>客户资金状况</title>
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
						<tr height="18%">
							<td>
								<div class="report_bor01">
									<form
										action="${basePath}/report/customerFunds/customerFundsReportQuery.action"
										name="frm" id="frm" method="post" target="report">
										<table width="800" border="0" cellspacing="0" cellpadding="0">
											<tr class="report_w12h">
												 <td class="table3_td_1tjcx" align="left">&nbsp;&nbsp;交易账号： 
													<input type="text" name="${GNNT_}customerno[=][String]" id="customerNo"
																value="${oldParams['customerno[=][String]'] }" class="input_textmin">
												</td>
												 <td class="table3_td_1tjcx" align="left">客户名称： 
													<input type="text" name="${GNNT_}primary.customername[like]" id="customerName"
																value="${oldParams['primary.customername[like]'] }" class="input_textmin">
												</td>
												
											<td class="table3_td_1tjcx" align="left">机构： 
													<input type="text" name="${GNNT_}primary.organizationname[=][String]" id="organizationName"
																value="${oldParams['primary.organizationname[=][String]'] }" class="input_textmin">
												</td>
											</tr>
											<tr>
	
											
											<td  align="left" >&nbsp;&nbsp;风险率>=： 
													<input type="text" name="${GNNT_}primary.risk[>=][Double]" id="risk1"
																value="${oldParams['primary.risk[>=][Double]'] }" class="input_textmin">%
												</td>
											  <td align="left">风险率<=： 
													<input type="text" name="${GNNT_}primary.risk[<=][Double]" id="risk2"
																value="${oldParams['primary.risk[<=][Double]'] }" class="input_textmin">%
												</td>
												<td align="left">
													<input type="checkbox" id='noqty' name="noqty" value=""
														>
													显示无资金客户
													<input type="hidden"
														name="${GNNT_}primary.endcapital[>][int]" value="0"
														disabled="disabled" id="qtysum">
												</td>
											</tr>
											<tr>
											<td class="table3_td_1tjcx" align="left">&nbsp;&nbsp;开始日期：
													<input type="text" style="width: 100px" id="startDate"
															class="wdate" maxlength="10"
															name="${GNNT_}trunc(primary.cleardate)[>=][date]"
															value='${oldParams["trunc(primary.cleardate)[>=][date]"]}'
															onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
												</td>
												<td class="table3_td_1tjcx" align="left">
													结束日期：
													<input type="text" style="width: 100px" id="endDate"
															class="wdate" maxlength="10"
															name="${GNNT_}trunc(primary.cleardate)[<=][date]"
															value='${oldParams["trunc(primary.cleardate)[<=][date]"]}'
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
						<tr height="82%">
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
	var ckBox = document.getElementById('noqty');
	if (ckBox.checked) {
		frm.qtysum.disabled = true;
	} else {
		frm.qtysum.disabled = false;
	}
	var risk1= document.getElementById("risk1").value;
	var risk2= document.getElementById("risk2").value;
	if(!flote(risk1,2) || !flote(risk2,2)){
		alert('风险率必须为数字且最多为两位小数！');
		return false;
	}
	frm.action="${basePath}/report/customerFunds/fundsReport.action";
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
	frm.startDate.value = '${date}';
	frm.endDate.value = '${date}';
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