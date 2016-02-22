<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/session.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>�ۺϻ�Ա�ʽ�״��</title>
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
								action="${basePath}/report/memberFunds/memberFundsReportQuery.action"
								name="frm" id="frm" method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_widthmid">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0" style="table-layout: fixed">
													<tr>
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
											var="memberFunds"
											action="${basePath}/report/memberFunds/memberFundsReportQuery.action"
											title="" minHeight="345" listWidth="245%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" csvFileName="�����б�.csv"
											style="table-layout:fixed">
											<ec:row recordKey="${customer.customerNo}">
												<ec:column width="2%" property="_0" title="���"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<ec:column property="clearDate[=][date]" title="��������"
													width="3%" style="text-align:left; " ellipsis="true">
													<fmt:formatDate value="${memberFunds.clearDate}"
														pattern="yyyy-MM-dd" />
												</ec:column>
												<ec:column property="total_begincapital[=][double]" title="�ͻ��ڳ�Ȩ��"
													width="4%" style="text-align:right; ">
													<fmt:formatNumber value="${memberFunds.total_begincapital}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="begincapital[=][double]" title="�ۺϻ�Ա�ڳ�Ȩ��"
													width="5%" style="text-align:right; ">
													<fmt:formatNumber value="${memberFunds.begincapital}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="total_fundio[=][double]" title="�ͻ������" width="4%"
													style="text-align:right; ">
													<fmt:formatNumber value="${memberFunds.total_fundio}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="fundio[=][double]" title="�ۺϻ�Ա�����" width="4%"
													style="text-align:right; " >
													<fmt:formatNumber value="${memberFunds.fundio}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="customercloseplsum[=][double]" title="�ͻ�ƽ��ӯ��"
													width="4%" style="text-align:right; ">
													<fmt:formatNumber value="${memberFunds.customercloseplsum}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="memberclosepl[=][double]" title="�ۺϻ�Աƽ��ӯ��"
													width="4%" style="text-align:right;">
													<fmt:formatNumber value="${memberFunds.memberclosepl}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="closeplsum[=][double]" title="ƽ�־�ӯ��" width="4%"
													style="text-align:right; " >
													<fmt:formatNumber value="${memberFunds.closeplsum}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="customerholdpl[=][double]" title="�ͻ��ֲ�ӯ��"
													width="4%" style="text-align:right; ">
													<fmt:formatNumber value="${memberFunds.customerholdpl}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="memberholdpl[=][double]" title="�ۺϻ�Ա�ֲ�ӯ��" width="4%"
													style="text-align:right; " >
													<fmt:formatNumber value="${memberFunds.memberholdpl}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="holdplsum[=][double]" title="�ֲ־�ӯ��" width="4%"
													style="text-align:right; ">
													<fmt:formatNumber value="${memberFunds.holdplsum}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="mktfee[=][double]" title="����������������" width="5%"
													style="text-align:right; ">
													<fmt:formatNumber value="${memberFunds.mktfee}"
														pattern="#,##0.00" />
												</ec:column>

												<ec:column property="memberfee[=][double]" title="�ۺϻ�Ա����������" width="4%"
								style="text-align:right; " >
													<fmt:formatNumber value="${memberFunds.memberfee}"
														pattern="#,##0.00" />
												</ec:column>

												<ec:column property="customerfee[=][double]" title="�տͻ�������" width="4%"
													style="text-align:right; ">
													<fmt:formatNumber value="${memberFunds.customerfee}"
														pattern="#,##0.00" />
												</ec:column>

												<ec:column property="smemberdelayfee[=][double]" title="�ر��Ա�������ڷ�"
													width="4%" style="text-align:right; ">
													<fmt:formatNumber value="${memberFunds.smemberdelayfee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="mktdelayfee[=][double]" title="�������������ڷ�"
													width="4%" style="text-align:right; ">
													<fmt:formatNumber value="${memberFunds.mktdelayfee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="customerdelayfee[=][double]" title="�տͻ����ڷ�"
													width="4%" style="text-align:right; ">
													<fmt:formatNumber value="${memberFunds.customerdelayfee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="delayfeesum[=][double]" title="�ۺϻ�Ա�������ڷ�" width="4%"
													style="text-align:right; ">
													<fmt:formatNumber value="${memberFunds.delayfeesum}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="total_endcapital[=][double]" title="�ͻ���ĩȨ��"
													width="4%" style="text-align:right; ">
													<fmt:formatNumber value="${memberFunds.total_endcapital}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="endcapital[=][double]" title="�ۺϻ�Ա��ĩȨ��" width="5%"
													style="text-align:right; " >
													<fmt:formatNumber value="${memberFunds.endcapital}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="total_margin[=][double]" title="�ͻ�ռ�ñ�֤��" width="4%"
													style="text-align:right; ">
													<fmt:formatNumber value="${memberFunds.total_margin}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="total_livemargin[=][double]" title="�ͻ����ñ�֤��" width="4%"
													style="text-align:right; ">
													<fmt:formatNumber value="${memberFunds.total_livemargin}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="risk_log" title="������" width="3%"
													style="text-align:right; " filterable="false"
													value="${memberFunds.risk_log}" sortable="false" />
											</ec:row>
											<c:if test="${ifHas eq 1}">		
												<ec:extendrow>
													<td>
														�ϼ�:
													</td><td>&nbsp;</td>
													<td align="right" style="font-weight:bold">${total_begincapitalAll}</td>													
													<td align="right" style="font-weight:bold">${begincapitalAll}</td>													
													<td align="right" style="font-weight:bold">${total_fundioAll}</td>													
													<td align="right" style="font-weight:bold">${fundioAll}</td>													
													<td align="right" style="font-weight:bold">${customercloseplsumAll}</td>													
													<td align="right" style="font-weight:bold">${membercloseplAll}</td>													
													<td align="right" style="font-weight:bold">${closeplsumAll}</td>													
													<td align="right" style="font-weight:bold">${customerholdplAll}</td>
													<td align="right" style="font-weight:bold">${memberholdplAll}</td>													
													<td align="right" style="font-weight:bold">${holdplsumAll}</td>													
													<td align="right" style="font-weight:bold">${mktfeeAll}</td>													
													<td align="right" style="font-weight:bold">${memberfeeAll}</td>													
													<td align="right" style="font-weight:bold">${customerfeeAll}</td>																								
													<td align="right" style="font-weight:bold">${smemberdelayfeeAll}</td>													
													<td align="right" style="font-weight:bold">${mktdelayfeeAll}</td>													
													<td align="right" style="font-weight:bold">${customerdelayfeeAll}</td>													
													<td align="right" style="font-weight:bold">${delayfeesumAll}</td>
													<td align="right" style="font-weight:bold">${total_endcapitalAll}</td>
													<td align="right" style="font-weight:bold">${endcapitalAll}</td>															
													<td align="right" style="font-weight:bold">${total_marginAll}</td>
													<td align="right" style="font-weight:bold">${total_livemarginAll}</td>
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
	frm.action = "${basePath}/report/memberFunds/memberFundsReportQuery.action";
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