<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/session.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>��Ա�ͻ�����ͳ�Ʊ�</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
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
								action="${basePath}/report/memberCustomerAccount/memberCustomerAccountReportQuary.action"
								name="frm" id="frm" method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_widthmax">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0" style="table-layout: fixed">
													<tr>
														<td class="table3_td_1tjcx" align="left">
															�ۺϻ�Ա��ţ�
															<input type="text" name="${GNNT_}primary.memberNo[=][String]"
																id="memberNo"
																value="${oldParams['primary.memberNo[=][String]'] }"
																class="input_textmin">
														</td>
														<td class="table3_td_1tjcx" align="left">
															�ۺϻ�Ա���ƣ�
															<input type="text" id="memberNames"
																name="${ORIGINAL_}memberNames"
																value="${original_memberNames}" onclick="clickText()"
																readonly=true size="8" class="input_textmin">
															<input type="hidden" name="${ORIGINAL_}memberIds"
																id="memberIds" value="${original_memberIds}"
																class="input_text">
														</td>
														<td class="table3_td_1tjcx_1" align="left">
															��ʼ���ڣ�
															<input type="text" style="width: 100px" id="startDate"
																class="wdate" maxlength="10"
																name="${GNNT_}primary.clearDate[>=][date]"
																value='${oldParams["primary.clearDate[>=][date]"]}'
																onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
														</td>
														<td class="table3_td_1tjcx_1" align="left">
															�������ڣ�
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
															<button class="btn_cz" onclick="myReset();">
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
											var="memberCustomerCount"
											action="${basePath}/report/memberCustomerAccount/memberCustomerAccountReportQuary.action"
											title="" minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" csvFileName="�����б�.csv"
											style="table-layout:fixed">
											<ec:row recordKey="">
												<ec:column width="3%" property="_0" title="���"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<ec:column property="cleardate[=][date]" title="��������"
													width="5%" style="text-align:left; " ellipsis="true">
													<fmt:formatDate value="${memberCustomerCount.clearDate}"
														pattern="yyyy-MM-dd" />
												</ec:column>
												<ec:column property="primary.memberNo[like]" title="�ۺϻ�Ա���"
													width="5%" style="text-align:left; "
													value="${memberCustomerCount.memberNo}" ellipsis="true" />
												<ec:column property="primary.memberName[like]"
													title="�ۺϻ�Ա����" width="9%" style="text-align:left; "
													value="${memberCustomerCount.memberName}" ellipsis="true" />
												<ec:column property="primary.contact[like]" title="�ۺϻ�Ա�����˺�"
													width="6%" style="text-align:left; "
													value="${memberCustomerCount.contact}" ellipsis="true" />
												<ec:column property="primary.createcount[=][int]"
													title="���տͻ�������" width="6%" style="text-align:right; "
													value="${memberCustomerCount.createcount}" />
												<ec:column property="primary.totalcreatecount[=][int]"
													title="�����ܼ�" width="5%" style="text-align:right; "
													value="${memberCustomerCount.totalcreatecount}" />
												<ec:column property="primary.demisecount[=][int]"
													title="���������ͻ���" width="6%" style="text-align:right; "
													value="${memberCustomerCount.demisecount}" />
												<ec:column property="primary.totaldemisecount[=][int]"
													title="�����ܼ�" width="5%" style="text-align:right; "
													value="${memberCustomerCount.totaldemisecount}" />
												<ec:column property="primary.dealcount[=][int]"
													title="���տɽ��׿ͻ���" width="6%" style="text-align:right; "
													value="${memberCustomerCount.dealcount}"/>
											</ec:row>
											<c:if test="${ifHas eq 1}">		
												<ec:extendrow>
													<td> �ϼ�:</td>
													<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
													<td align="right" style="font-weight:bold">${createcountAll}</td>
													<td align="right" style="font-weight:bold">${totalcreatecountAll}</td>
													<td align="right" style="font-weight:bold">${demisecountAll}</td>
													<td align="right" style="font-weight:bold">${totaldemisecountAll}</td>
													<td align="right" style="font-weight:bold">${dealcountAll}</td>
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
	frm.action = "${basePath}/report/memberCustomerAccount/memberCustomerAccountReportQuary.action";
	checkQueryDate(frm.startDate.value, frm.endDate.value);
	frm.action = action;
}
function xls() {
	frm.type.value = "xls";
	select1();
	frm.type.value = "";
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

function init() {
	if (frm.startDate.value == "" && frm.endDate.value == "") {
		frm.startDate.value = '${date}';
		frm.endDate.value = '${date}';
	}

}
</script>