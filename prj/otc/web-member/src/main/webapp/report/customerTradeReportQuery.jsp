<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/session.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>客户成交汇总表</title>
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
										action="${basePath}/report/customerTrade/customerTradeReportQuery.action"
										name="frm" id="frm" method="post" target="report">
										<table width="820" border="0" cellspacing="0" class="table2_td_widthmax" cellpadding="0">
											<tr>
											   <td class="table3_td_1tjcx" align="left">&nbsp;&nbsp;交易账号： 
													<input type="text" name="${GNNT_}customerno[like]" id="customerNo"
																value="${oldParams['customerno[like]'] }" class="input_textmin">
												</td>
											    <td class="table3_td_1tjcx" align="left">&nbsp;&nbsp;客户名称： 
													<input type="text" name="${GNNT_}primary.customername[like]" id="customerName"
																value="${oldParams['primary.customername[like]'] }" class="input_textmin">
												</td>
												
												
											</tr>
											<tr>
											<td align="left" class="table3_td_1tjcx">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;商品：
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
												<td class="table3_td_1tjcx" align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机构： 
													<input type="text" name="${GNNT_}primary.organizationname[like]" id="organizationName"
																value="${oldParams['primary.organizationname[like]'] }" class="input_textmin">
												</td>
												<td width="180" align="left">
													&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id='noqty' name="noqty" value=""
														>
													显示无成交客户
													<input type="hidden"
														name="${GNNT_}primary.customerqtysum[>][int]" value="0"
														disabled="disabled" id="qtysum">
												</td>
											 
												</tr>
												<tr>
												  <td class="table3_td_1tjcx" align="left">
													&nbsp;&nbsp;开始日期：
													<input type="text" style="width: 100px" id="startDate"
															class="wdate" maxlength="10"
															name="${GNNT_}trunc(primary.cleardate)[>=][date]"
															value='${oldParams["trunc(primary.cleardate)[>=][date]"]}'
															onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
												</td>
												<td class="table3_td_1tjcx" align="left">
													&nbsp;&nbsp;结束日期：
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
	frm.action="${basePath}/report/customerTrade/customerTradeReport.action";
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