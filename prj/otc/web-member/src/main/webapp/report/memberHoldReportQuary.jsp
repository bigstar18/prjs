<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/session.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>��Ա�ֲֻ��ܱ�</title>
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
						<tr height="7%">
							<td>
								<div class="report_bor01">
									<form
										action="${basePath}/report/memberHold/memberHoldReportQuary.action"
										name="frm" id="frm" method="post" target="report">
										<table border="0" width="790" 
											cellspacing="0" cellpadding="0" height="40"> 

											<tr class="report_w12h">
												<td align="left" width="180">&nbsp;&nbsp;
													��Ʒ��
													<select id="commodityId"
														name="${GNNT_}primary.commodityId[like]"
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
frm.commodityId.value='${oldParams['primary.commodityId[like]'] }';
													</script>
												</td>
												<td width="190" align="left">��ʼ���ڣ�
													<input type="text" style="width: 100px" id="startDate"
														class="wdate" maxlength="10"
														name="${GNNT_}trunc(primary.cleardate)[>=][date]"
														value='${oldParams["trunc(primary.cleardate)[>=][date]"]}'
														onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
												</td>
												<td width="190" align="left">
													�������ڣ�
													<input type="text" style="width: 100px" id="endDate"
														class="wdate" maxlength="10"
														name="${GNNT_}trunc(primary.cleardate)[<=][date]"
														value='${oldParams["trunc(primary.cleardate)[<=][date]"]}'
														onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
												</td>
												<td align="left">
													<input type="button" class="button_02" onclick="select1()"
														value="��ѯ" />
													&nbsp;
													<input type="button" class="button_03" onclick="myReset()"
														value="����" />
													&nbsp;
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
						<tr height="93%">
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