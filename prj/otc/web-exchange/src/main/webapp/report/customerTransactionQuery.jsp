<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/session.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>客户交易统计表</title>
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
						<tr height="20%">
							<td>
								<div class="report_bor01">
									<form
										action="${basePath}/report/customerTrans/customerTransactionQuery.action"
										name="frm" id="frm" method="post" target="report">
										<table border="0" width="800"
											cellspacing="0" cellpadding="0">

											<tr height="35">
												<td class="table3_td_1tjcx" align="left">&nbsp;&nbsp;会员编号：
													<input type="text"
														name="${GNNT_}primary.memberno[like]" id="memberno"
														value="${oldParams['primary.memberno[like]'] }"
														class="input_textmin">
												</td>
												<td class="table3_td_1tjcx" align="left">会员名称：
													<input type="text" id="memberNames"
														name="${ORIGINAL_}memberNames"
														value="${original_memberNames}" onclick="clickText()"
														readonly=true size="8" class="input_textmin">
														<input type="hidden" name="${ORIGINAL_}memberIds"
														id="memberIds" value="${original_memberIds}"
														class="input_text">
												</td>
												<td width="190" align="left" colspan="2">
													交易账号：
													<input type="text" name="${GNNT_}customerno[like]"
														id="customerNo"
														value="${oldParams['customerno[like]'] }"
														class="input_textmin">
												</td>
											</tr>
											<tr height="35">
												<td class="table3_td_1tjcx" align="left">
													&nbsp;&nbsp;客户名称：
													<input type="text"
														name="${GNNT_}primary.customername[like]"
														id="customerName"
														value="${oldParams['primary.customername[like]'] }"
														class="input_textmin">
												</td>
												<td class="table3_td_1tjcx" align="left">&nbsp;&nbsp;&nbsp;&nbsp;机构：
													<input type="text"
														name="${GNNT_}primary.organizationname[like]"
														id="organizationName"
														value="${oldParams['primary.organizationname[like]'] }"
														class="input_textmin">
												</td>
												<td align="left" width="220" colspan="2">
													&nbsp;&nbsp;&nbsp;&nbsp;商品：
													<select id="commodityId" name="${GNNT_}primary.commodityId[like]"
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
											<tr height="35">
												<td class="table3_td_1tjcx" align="left">
													&nbsp;&nbsp;开始日期：
													<input type="text" style="width: 100px" id="startDate"
														class="wdate" maxlength="10"
														name="${GNNT_}trunc(primary.cleardate)[>=][date]"
														value='${oldParams["trunc(primary.atClearDate)[>=][date]"]}'
														onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
												</td>
												<td class="table3_td_1tjcx" align="left">
													结束日期：
													<input type="text" style="width: 100px" id="endDate"
														class="wdate" maxlength="10"
														name="${GNNT_}trunc(primary.cleardate)[<=][date]"
														value='${oldParams["trunc(primary.atClearDate)[<=][date]"]}'
														onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
												</td>
												<td width="190" align="left">
													<input type="checkbox" id='noqty' name="noqty" value=""
														>
													显示无仓单客户
													<input type="hidden"
														name="${GNNT_}primary.customerqtysum[>][int]" value="0"
														disabled="disabled" id="qtysum">
												</td>
												<td align="left">
													<input type="button" class="button_02" onclick="select1()"
														value="查询" />&nbsp;
													<input type="button" class="button_03" onclick="myReset()"
														value="重置" />&nbsp;
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
						<tr height="80%">
							<td class="report_bor01">
								<iframe name="report" frameborder="0"
									src="${basePath }/report/noDateReport.jsp" scrolling="auto"
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
	var ckBox = document.getElementById('noqty');
	if (ckBox.checked) {
		frm.qtysum.disabled = true;
	} else {
		frm.qtysum.disabled = false;
	}
	frm.action = "${basePath}/report/customerTrans/customerTransactionReport.action";
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
	frm.startDate.value ='${date}';
	frm.endDate.value ='${date}' ;
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