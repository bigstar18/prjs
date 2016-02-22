<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/session.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>�ͻ��ʽ�״��</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
		<link href="${basePath }/report/report_css.css" rel="stylesheet"
			type="text/css" />
		<import namespace="MEBS"
			implementation="${basePath}/report/public/calendar.htc">
	</head>

	<body id="main_body" onload="init();">
		<table width="99%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<table width="98%" border="0" align="center" cellpadding="0"
						cellspacing="3">
						<tr>
							<td>
								<div class="div_tj">
									<form
										action="${basePath}/report/customerFunds/customerFundsReportQuery.action"
										name="frm" id="frm" method="post">
										<table border="0" cellpadding="0" cellspacing="0"
											class="table2_style">
											<tr>
												<td class="table2_td_widthcdmax">
													<div class="div2_top">
														<table border="0" cellspacing="0" cellpadding="0">
															<tr>
																<input type="hidden" name="${GNNT_}isRelated"
																	id="isRelated" value="${oldParams['isRelated']}"
																	class="input_text">
																<input type="hidden" name="${GNNT_}memberInfoIds"
																	id="memberInfoIds"
																	value="${oldParams['memberInfoIds']}"
																	class="input_text">
																<input type="hidden" name="${GNNT_}organizationIds"
																	id="organizationIds"
																	value="${oldParams['organizationIds']}"
																	class="input_text">
																<input type="hidden" name="${GNNT_}managerIds"
																	id="managerIds" value="${oldParams['managerIds']}"
																	class="input_text">
																<input type="hidden" name="${GNNT_}brokerageIds"
																	id="brokerageIds" value="${oldParams['brokerageIds']}"
																	class="input_text">
																<input type="hidden" name="tree" id="tree"
																	value="${tree}" class="input_text">
																<td class="table3_td_1tjcx" align="left">
																	�ͻ�������
																	<input type="text" id="memberNames"
																		name="${GNNT_}memberNames"
																		value="${oldParams['memberNames']}" readonly=true
																		class="input_textmin">
																	<a href="javascript:clickText();"><img
																			align="absmiddle" src="<%=skinPath%>/cssimg/kh.gif">
																	</a>
																</td>
																<td class="table3_td_1tjcx" align="left">
																	�����˺ţ�
																	<input type="text" name="${GNNT_}customerno[like]"
																		id="customerNo"
																		value="${oldParams['customerno[like]'] }"
																		class="input_text">
																</td>
																<td class="table3_td_1tjcx" align="left">
																	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�ͻ����ƣ�
																	<input type="text"
																		name="${GNNT_}primary.customerName[like]"
																		id="customerName"
																		value="${oldParams['primary.customerName[like]'] }"
																		class="input_textmin">
																</td>

															</tr>
															<tr>
																<td class="table3_td_1tjcx" align="left">
																	��ʼ���ڣ�
																	<input type="text" style="width: 100px" id="startDate"
																		class="wdate" maxlength="10"
																		name="${GNNT_}primary.clearDate[>=][date]"
																		value='${oldParams["primary.clearDate[>=][date]"]}'
																		onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
																</td>

																<td class="table3_td_1tjcx" align="left">
																	�������ڣ�
																	<input type="text" style="width: 120px" id="endDate"
																		class="wdate" maxlength="10"
																		name="${GNNT_}primary.clearDate[<=][date]"
																		value='${oldParams["primary.clearDate[<=][date]"]}'
																		onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
																</td>
																<td class="table3_td_1tjcx" align="left">
																	�ۺϻ�Ա��ţ�
																	<input type="text" name="${GNNT_}memberno[=][String]"
																		id="memberNo" value="${oldParams['memberno[=][String]'] }"
																		class="input_textmin">
																</td>
																<td align="left" colspan="2" class="table3_td_anniu">
																	<input type="button" class="button_02"
																		onclick="select1()" value="��ѯ" />
																	&nbsp;
																	<input type="button" class="button_03"
																		onclick="mySelfReset()" value="����" />
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
													var="customerFund"
													action="${basePath}/report/customerFunds/customerFundsReportQuery.action"
													title="" minHeight="345" listWidth="210%"
													retrieveRowsCallback="limit" sortRowsCallback="limit"
													filterRowsCallback="limit" csvFileName="�����б�.csv"
													style="table-layout:fixed">
													<ec:row ondblclick="">
														<ec:column width="2%" property="_0" title="���"
															value="${GLOBALROWCOUNT}" sortable="false"
															filterable="false" />
														<ec:column property="clearDate[=][date]" title="��������"
															width="3%" style="text-align:left; " ellipsis="true">
															<fmt:formatDate value="${customerFund.clearDate}"
																pattern="yyyy-MM-dd" />
														</ec:column>
														<ec:column property="memberNo[like]" title="�ۺϻ�Ա���"
															width="4%" style="text-align:left; "
															value="${customerFund.memberNo}" ellipsis="true" />
														<ec:column property="memberName[like]" title="�ۺϻ�Ա����"
															width="5%" style="text-align:left; "
															value="${customerFund.memberName}" ellipsis="true" />
														<ec:column property="organizationName[=][String]"
															title="����" width="5%"
															value="${customerFund.organizationName}"
															style="text-align:right; " ellipsis="true">
														</ec:column>
														<ec:column property="primary.brokerName[=][String]"
															title="�Ӽ�" width="5%" value="${customerFund.brokerName}"
															style="text-align:right; " ellipsis="true">
														</ec:column>
														<ec:column property="customerNo[=][String]" title="�����˺�"
															width="5%" style="text-align:right; "
															value="${customerFund.customerNo}" ellipsis="true">
														</ec:column>
														<ec:column property="customerName[like]" title="�ͻ�����"
															width="5%" style="text-align:left; "
															value="${customerFund.customerName}" ellipsis="true" />
														<ec:column property="begincapital[=][double]" title="�ڳ�Ȩ��"
															width="5%" style="text-align:right; ">
															<fmt:formatNumber value="${customerFund.begincapital}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="fundio[=][double]" title="�����"
															width="5%" style="text-align:right; ">
															<fmt:formatNumber value="${customerFund.fundio}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="closepl[=][double]" title="ƽ��ӯ��"
															width="4%" style="text-align:right; ">
															<fmt:formatNumber value="${customerFund.closepl}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="holdpl[=][double]" title="�ֲ�ӯ��"
															width="4%" style="text-align:right; ">
															<fmt:formatNumber value="${customerFund.holdpl}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="plsum[=][double]" title="ӯ���ϼ�"
															width="4%" style="text-align:right;">
															<fmt:formatNumber value="${customerFund.plsum}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="mktfee[=][double]" title="����������������"
															width="5%" style="text-align:right; ">
															<fmt:formatNumber value="${customerFund.mktfee}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="memberfee[=][double]"
															title="�ۺϻ�Ա����������" width="5%" style="text-align:right; ">
															<fmt:formatNumber value="${customerFund.memberfee}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="customerfee[=][double]"
															title="�տͻ�������" width="4%" style="text-align:right;">
															<fmt:formatNumber value="${customerFund.customerfee}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="delayfee[=][double]" title="�տͻ����ڷ�"
															width="4%" style="text-align:right;">
															<fmt:formatNumber value="${customerFund.delayfee}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="mktdelayfee[=][double]" title="�������������ڷ�"
															width="5%" style="text-align:right;">
															<fmt:formatNumber value="${customerFund.mktdelayfee}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="memberdelayfee[=][double]" title="��Ա�������ڷ�"
															width="4%" style="text-align:right;">
															<fmt:formatNumber value="${customerFund.memberdelayfee}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="margin[=][double]" title="ռ�ñ�֤��"
															width="4%" style="text-align:right;">
															<fmt:formatNumber value="${customerFund.margin}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="endcapital[=][double]" title="��ĩȨ��"
															width="5%" style="text-align:right">
															<fmt:formatNumber value="${customerFund.endcapital}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="risk_log[=][String]" title="������"
															width="2%" value="${customerFund.risk_log}"
															filterable="false" sortable="false"
															style="text-align:right" />
													</ec:row>
													<c:if test="${ifHas eq 1}">		
														<ec:extendrow>
															<td>
															          �ϼ�:
															</td>
															<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
															<td align="right" style="font-weight:bold" >${begincapitalAll}</td>
															<td align="right" style="font-weight:bold">${fundioAll}</td>
															<td align="right" style="font-weight:bold">${closeplAll}</td>
															<td align="right" style="font-weight:bold">${holdplAll}</td>
															<td align="right" style="font-weight:bold">${plsumAll}</td>
															<td align="right" style="font-weight:bold">${mktfeeAll}</td>
															<td align="right" style="font-weight:bold">${memberfeeAll}</td>
															<td align="right" style="font-weight:bold">${customerfeeAll}</td>
															<td align="right" style="font-weight:bold">${delayfeeAll }</td>
															<td align="right" style="font-weight:bold">${mktdelayfeeAll}</td>
															<td align="right" style="font-weight:bold">${memberdelayfeeAll}</td>
															<td align="right" style="font-weight:bold">${marginAll }</td>
															<td align="right" style="font-weight:bold">${endcapitalAll }</td>
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
				</td>
			</tr>
		</table>
		</form>


		<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value=""
				onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="" />
		</textarea>
	</body>
</html>
<script type="text/javascript">

function select1() {
	var action = frm.action;
	frm.action = "${basePath}/report/customerFunds/customerFundsReportQuery.action";
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
	var isRelated = document.getElementById("isRelated").value;
	var url = "${basePath}/broke/memberInfoTree/forTree.action?isRelated="
			+ isRelated;
	var result = window
			.showModalDialog(url, window,
					"dialogWidth=400px; dialogHeight=570px; status=yes;scroll=yes;help=no;");

}

function mySelfReset() {//���
	window.parent.frames["topFrame1"].document.getElementById('tree').value = "";
	var pathRrl = frm.action.toString();
	var au = '111111';
	if (typeof (AUsessionId) != "undefined") {
		au = AUsessionId;
	}
	var urlArray = pathRrl.split("?");
	if (urlArray.length == 1) {
		pathRrl = pathRrl + '?AUsessionId=' + au + "&noQuery=true";
	} else if (urlArray.length == 2) {
		pathRrl = pathRrl + '&AUsessionId=' + au + "&noQuery=true";
	}
	window.location.href = pathRrl;
}
</script>