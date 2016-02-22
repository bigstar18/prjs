<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/session.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>�ر��Ա�ʽ���ܱ�</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
		<link href="${basePath }/report/report_css.css" rel="stylesheet"
			type="text/css" />
		<import namespace="MEBS"
			implementation="${basePath}/report/public/calendar.htc">
	</head>
	<body id="main_body" onload="init();">
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
							<form
								action="${basePath}/report/specialMemberFunds/fundsReportQuery.action"
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
															&nbsp;��ʼ���ڣ�
															<input type="text" style="width: 100px" id="startDate"
																class="wdate" maxlength="10"
																name="${GNNT_}primary.clearDate[>=][date]"
																value='${oldParams["primary.clearDate[>=][date]"]}'
																onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
														</td>
														<td class="table3_td_1tjcxmid" align="left">
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�������ڣ�
															<input type="text" style="width: 100px" id="endDate"
																class="wdate" maxlength="10"
																name="${GNNT_}primary.clearDate[<=][date]"
																value='${oldParams["primary.clearDate[<=][date]"]}'
																onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">

														</td>
														<td class="table3_td_1tjcx_1" align="left">
															<button class="btn_sec" onclick="select1()">
																��ѯ
															</button>
															&nbsp;
															<button class="btn_cz" onclick="myReset()">
																����
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
											var="specialMemberFunds"
											action="${basePath}/report/specialMemberFunds/fundsReportQuery.action"
											title="" minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" csvFileName="�����б�.csv"
											style="table-layout:fixed">
											<ec:row ondblclick="">
												<ec:column width="4%" property="_0" title="���"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<ec:column property="clearDate[=][date]" title="��������"
													width="7%" style="text-align:left; " ellipsis="true">
													<fmt:formatDate value="${specialMemberFunds.clearDate}"
														pattern="yyyy-MM-dd" />
												</ec:column>
												<ec:column property="begincapital[=][double]"
													title="�ڳ�Ȩ�棨Ԫ��" width="11%" 
													style="text-align:right; ">
													<fmt:formatNumber
														value="${specialMemberFunds.begincapital}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="fundio[=][double]" title="�����" width="10%"
													style="text-align:right; ">
													<fmt:formatNumber value="${specialMemberFunds.fundio}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="delayfee[=][double]" title="�����ڷ�" width="8%"
													style="text-align:right; " >
													<fmt:formatNumber value="${specialMemberFunds.delayfee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="closepl[=][double]" title="ƽ�ֽ���ӯ��" width="8%"
													style="text-align:right; " >
													<fmt:formatNumber value="${specialMemberFunds.closepl}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="holdpl[=][double]" title="�ֲֽ���ӯ��" width="8%"
													style="text-align:right;" >
													<fmt:formatNumber value="${specialMemberFunds.holdpl}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="plsum[=][double]" title="����ӯ���ϼ�" width="8%"
													style="text-align:right; " >
													<fmt:formatNumber value="${specialMemberFunds.plsum}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="endcapital[=][double]" title="��ĩȨ��"
													width="11%" style="text-align:right; ">
													<fmt:formatNumber value="${specialMemberFunds.endcapital}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="risk_log[=][String]" title="������"
													width="7%" style="text-align:right; " filterable="false"
													sortable="false"  format="#,##0.00" value="${specialMemberFunds.risk_log}"/>
											</ec:row>
											<c:if test="${ifHas eq 1}">		
												<ec:extendrow>
													<td>
													          �ϼ�:
													</td>
													<td>&nbsp;</td>
													<td align="right" style="font-weight:bold">${begincapitalAll}</td>													
													<td align="right" style="font-weight:bold">${fundioAll}</td>													
													<td align="right" style="font-weight:bold">${delayfeeAll}</td>													
													<td align="right" style="font-weight:bold">${closeplAll}</td>													
													<td align="right" style="font-weight:bold">${holdplAll}</td>													
													<td align="right" style="font-weight:bold">${plsumAll}</td>													
													<td align="right" style="font-weight:bold">${endcapitalAll}</td>
													<td>&nbsp;</td>
												</ec:extendrow>
											</c:if>
										</ec:table>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value=""
				onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="" />
		</textarea>
		<script type="text/javascript">
function select1() {
	var action = frm.action;
	frm.action = "${basePath}/report/specialMemberFunds/fundsReportQuery.action";
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
	if(frm.startDate.value=="" && frm.endDate.value ==""){
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