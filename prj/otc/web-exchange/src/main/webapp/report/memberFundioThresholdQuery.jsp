<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/session.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>�ۺϻ�Ա������ֵ�����</title>
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
								action="${basePath}/report/memberFundioThreshold/memberFundioThresholdReportQuery.action"
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
															<input type="text" name="${GNNT_}memberno[=][String]"
																id="memberNo"
																value="${oldParams['memberno[=][String]'] }"
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
														<td class="table3_td_1tjcx" align="left">
															��Ա���
															<select name="${GNNT_}membertype[=][String]"
																name="membertype" id="membertype" style="width: 100">
																<option value="">
																	ȫ��
																</option>
																<option value="���ͻ�Ա">
																	���ͻ�Ա
																</option>
																<option value="�ۺϻ�Ա">
																	�ۺϻ�Ա
																</option>
															</select>
															<script type="text/javascript">
frm.membertype.value = '${oldParams["membertype[=][String]"]}';
</script>
														</td>
														<td class="table3_td_1tjcx_1" align="left">
															&nbsp;
														</td>
													</tr>
													<tr>
														<td class="table3_td_1tjcx" align="left">
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��ʼ���ڣ�
															<input type="text" style="width: 100px" id="startDate"
																class="wdate" maxlength="10"
																name="${GNNT_}primary.clearDate[>=][date]"
																value='${oldParams["primary.clearDate[>=][date]"]}'
																onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
														</td>
														<td class="table3_td_1tjcx" align="left">
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�������ڣ�
															<input type="text" style="width: 100px" id="endDate"
																class="wdate" maxlength="10"
																name="${GNNT_}primary.clearDate[<=][date]"
																value='${oldParams["primary.clearDate[<=][date]"]}'
																onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
														</td>
														<td align="left" colspan="2">
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
											var="memberFundio"
											action="${basePath}/report/memberFundioThreshold/memberFundioThresholdReportQuery.action"
											title="" minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" csvFileName="�����б�.csv"
											style="table-layout:fixed">
											<ec:row recordKey="${customer.customerNo}">
												<ec:column width="6%" property="_0" title="���"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<ec:column property="clearDate[=][date]" title="��������"
													width="11%" style="text-align:left; " ellipsis="true">
													<fmt:formatDate value="${memberFundio.clearDate}"
														pattern="yyyy-MM-dd" />
												</ec:column>
												<ec:column property="memberNo[like]" title="�ۺϻ�Ա���"
													width="11%" style="text-align:left; "
													value="${memberFundio.memberNo}" ellipsis="true" />
												<ec:column property="memberName[like]" title="�ۺϻ�Ա����"
													width="11%" style="text-align:left; "
													value="${memberFundio.memberName}" ellipsis="true" />
												<ec:column property="membertype[like]" title="�ۺϻ�Ա����"
													width="9%" value="${memberFundio.membertype}"
													style="text-align:left; " ellipsis="true">
												</ec:column>
												<ec:column property="customerendcapital[=][double]" title="�����ͻ���ĩȨ�棨Ԫ��"
													width="15%" style="text-align:right; " >
													<fmt:formatNumber
														value="${memberFundio.customerendcapital}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="endcapital[=][double]" title="��ĩȨ�棨Ԫ��"
													 width="14%" style="text-align:right; ">
													<fmt:formatNumber value="${memberFundio.endcapital}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="minriskfund[=][double]" title="��ֵ����ֵ��Ԫ��"
													width="14%" style="text-align:right; " >
													<fmt:formatNumber value="${memberFundio.minriskfund}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="netfund[=][double]" title="��ֵ"
													width="11%" style="text-align:right; " >
													<fmt:formatNumber value="${memberFundio.netfund}"
														pattern="#,##0.00" />
												</ec:column>
											</ec:row>
										</ec:table>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ��-->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value=""
				onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="" />
		</textarea>

		<script type="text/javascript">
function select1() {
	var action = frm.action;
	frm.action = "${basePath}/report/memberFundioThreshold/memberFundioThresholdReportQuery.action";
	checkQueryDate(frm.startDate.value, frm.endDate.value);
	frm.action = action;
}
function init() {
	if (frm.startDate.value == "" && frm.endDate.value == "") {
		frm.startDate.value = '${date}';
		frm.endDate.value = '${date}';
	}
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
</script>