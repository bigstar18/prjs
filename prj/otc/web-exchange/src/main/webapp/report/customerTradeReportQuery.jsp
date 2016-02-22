<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/session.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>�ͻ��ɽ����ܱ�</title>
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
										action="${basePath}/report/customerTrade/customerTradeReportQuery.action"
										name="frm" id="frm" method="post" target="report">
										<table border="0" cellspacing="0" class="table2_td_widthcdmax" cellpadding="0">
											<tr class="report_w12h">
											   <td class="table3_td_1tjcx" align="left">&nbsp;&nbsp;��Ա��ţ� 
													<input type="text" name="${GNNT_}memberno[=][String]" id="memberNo"
																value="${oldParams['memberno[=][String]'] }" class="input_textmin">
												</td>
												<td class="table3_td_1tjcx" align="left">��Ա���ƣ�
													<input type="text" id="memberNames"
														name="${ORIGINAL_}memberNames"
														value="${original_memberNames}" onclick="clickText()"
														readonly=true size="8" class="input_textmin">
														<input type="hidden" name="${ORIGINAL_}memberIds"
														id="memberIds" value="${original_memberIds}"
														class="input_text">
												</td>
												 <td class="table3_td_1tjcx" align="left">�����˺ţ� 
													<input type="text" name="${GNNT_}customerno[=][String]" id="customerNo"
																value="${oldParams['customerno[=][String]'] }" class="input_textmin">
												</td>
											</tr>
											<tr>
											    <td class="table3_td_1tjcx" align="left">&nbsp;&nbsp;�ͻ����ƣ� 
													<input type="text" name="${GNNT_}primary.customername[like]" id="customerName"
																value="${oldParams['primary.customername[like]'] }" class="input_textmin">
												</td>
												<td class="table3_td_1tjcx" align="left">&nbsp;&nbsp;&nbsp;&nbsp;������ 
													<input type="text" name="${GNNT_}primary.organizationname[=][String]" id="organizationName"
																value="${oldParams['primary.organizationname[=][String]'] }" class="input_textmin">
												</td>
												<td align="left" class="table3_td_1tjcx" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;��Ʒ��
													<select id="commodityId" name="${GNNT_}primary.commodityId[=][String]"
														class="input_textmin">
														<option value="">
															��ѡ��
														</option>
														<c:forEach items="${commodityList}" var="commodit">
															<option value="${commodit.id}">
																${commodit.name }
															</option>
														</c:forEach>
													</select>
													<script type="text/javascript">
														frm.commodityId.value='${oldParams['primary.commodityId[=][String]'] }';
													</script>
												</td>
												
												
											</tr>
											<tr>
											
											   <td class="table3_td_1tjcx" align="left">
													&nbsp;&nbsp;��ʼ���ڣ�
													<input type="text" style="width: 100px" id="startDate"
															class="wdate" maxlength="10"
															name="${GNNT_}trunc(primary.cleardate)[>=][date]"
															value='${oldParams["trunc(primary.cleardate)[>=][date]"]}'
															onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
												</td>
												<td class="table3_td_1tjcx" align="left">
													�������ڣ�
													<input type="text" style="width: 100px" id="endDate"
															class="wdate" maxlength="10"
															name="${GNNT_}trunc(primary.cleardate)[<=][date]"
															value='${oldParams["trunc(primary.cleardate)[<=][date]"]}'
															onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
												</td>
												<td width="180" align="left">
													<input type="checkbox" id='noqty' name="noqty" value=""
														>
													��ʾ�޳ɽ��ͻ�
													<input type="hidden"
														name="${GNNT_}primary.customerqtysum[>][int]" value="0"
														disabled="disabled" id="qtysum">
												</td>
												<td align="left" colspan="2">
													<input type="button" class="button_02" onclick="select1()"
														value="��ѯ" />&nbsp;
													<input type="button" class="button_03"
														onclick="myReset()" value="����" />&nbsp;
													<input type="button" class="button_02" value="����"
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
	var result = window.showModalDialog(url,'',"dialogWidth=350px;dialogHeight=520px");
	if (result != null && result != '') {
		var result1 = result.split('####');
		frm.memberIds.value = result1[0];
		frm.memberNames.value = result1[1];
	}
}
</script>