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
												<td class="table2_td_widthmid1">
													<div class="div2_top">
														<table border="0" cellspacing="0" cellpadding="0">
															<tr>
																<td class="table3_td_1tjcx" align="left">
																	�����˺ţ�
																	<input type="text" name="gnnt_customerno[=][String]"
																		id="customerNo"
																		value="${oldParams['customerno[=][String]'] }"
																		class="input_text">
																</td>
																<td class="table3_td_1tjcx" align="left">
																	�ͻ����ƣ�
																	<input type="text"
																		name="gnnt_primary.customerName[like]"
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
																		name="gnnt_primary.clearDate[>=][date]"
																		value='${oldParams["primary.clearDate[>=][date]"]}'
																		onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
																</td>

																<td class="table3_td_1tjcx" align="left">
																�������ڣ�
																	<input type="text" style="width: 100px" id="endDate"
																		class="wdate" maxlength="10"
																		name="gnnt_primary.clearDate[<=][date]"
																		value='${oldParams["primary.clearDate[<=][date]"]}'
																		onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
																</td>
																<td align="left" colspan="2">
																	<input type="button" class="button_02"
																		onclick="select1()" value="��ѯ" />
																	&nbsp;
																	<input type="button" class="button_03"
																		onclick="myReset()" value="����" />
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
													title="" minHeight="345" listWidth="170%"
													retrieveRowsCallback="limit" sortRowsCallback="limit"
													filterRowsCallback="limit" csvFileName="�ͻ��ʽ�״����.csv"
													style="table-layout:fixed">
													<ec:row ondblclick="">
														<ec:column width="3%" property="_0" title="���"
															value="${GLOBALROWCOUNT}" sortable="false"
															filterable="false" />
														<ec:column property="clearDate[=][date]" title="��������"
															width="4%" style="text-align:left; " ellipsis="true">
															<fmt:formatDate value="${customerFund.clearDate}"
																pattern="yyyy-MM-dd" />
														</ec:column>
														<ec:column property="customerNo[=][String]" title="�����˺�"
															width="5%" style="text-align:right; "
															value="${customerFund.customerNo}" ellipsis="true">
														</ec:column>
														<ec:column property="customerName[like]" title="�ͻ�����"
															width="5%" style="text-align:left; "
															value="${customerFund.customerName}" ellipsis="true" />
														<ec:column property="begincapital[=][double]" title="�ڳ�Ȩ��" width="5%"
															style="text-align:right; ">
															<fmt:formatNumber value="${customerFund.begincapital}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="fundio[=][double]" title="�����" width="5%"
															style="text-align:right; ">
															<fmt:formatNumber value="${customerFund.fundio}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="closepl[=][double]" title="ƽ��ӯ��" width="4%"
															style="text-align:right; ">
															<fmt:formatNumber value="${customerFund.closepl}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="holdpl[=][double]" title="�ֲ�ӯ��" width="4%"
															style="text-align:right; ">
															<fmt:formatNumber value="${customerFund.holdpl}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="plsum[=][double]" title="ӯ���ϼ�" width="4%"
															style="text-align:right;" >
															<fmt:formatNumber value="${customerFund.plsum}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="mktfee[=][double]" title="����������������" width="5%"
															style="text-align:right; ">
															<fmt:formatNumber value="${customerFund.mktfee}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="memberfee[=][double]" title="�ۺϻ�Ա����������"
															width="5%" style="text-align:right; ">
															<fmt:formatNumber value="${customerFund.memberfee}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="customerfee[=][double]" title="�տͻ�������"
															width="4%" style="text-align:right;" >
															<fmt:formatNumber value="${customerFund.customerfee}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="delayfee[=][double]" title="���ڷ�" width="4%"
															style="text-align:right;">
															<fmt:formatNumber value="${customerFund.delayfee}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="mktdelayfee[=][double]" title="�г��������ڷ�"
															width="4%" style="text-align:right;">
															<fmt:formatNumber value="${customerFund.mktdelayfee}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="memberdelayfee[=][double]" title="��Ա�������ڷ�"
															width="4%" style="text-align:right;">
															<fmt:formatNumber value="${customerFund.memberdelayfee}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="margin[=][double]" title="ռ�ñ�֤��" width="5%"
															style="text-align:right;">
															<fmt:formatNumber value="${customerFund.margin}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="endcapital[=][double]" title="��ĩȨ��" width="5%"
															style="text-align:right" >
															<fmt:formatNumber value="${customerFund.endcapital}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="risk_log[=][String]"
															filterable="false" sortable="false" title="������"
															width="3%" value="${customerFund.risk_log}"
															style="text-align:right" />
													</ec:row>
													<c:if test="${ifHas eq 1}">		
														<ec:extendrow>
															<td>
															          �ϼ�:
															</td>
															<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
															<td align="right" style="font-weight:bold">${begincapitalAll}</td>
															<td align="right" style="font-weight:bold">${fundioAll}</td>
															<td align="right" style="font-weight:bold">${closeplAll}</td>
															<td align="right" style="font-weight:bold">${holdplAll }</td>
															<td align="right" style="font-weight:bold">${plsumAll}</td>	
															<td align="right" style="font-weight:bold">${mktfeeAll}</td>
															<td align="right" style="font-weight:bold">${memberfeeAll}</td>
															<td align="right" style="font-weight:bold">${customerfeeAll }</td>															
															<td align="right" style="font-weight:bold">${delayfeeAll}</td>															
															<td align="right" style="font-weight:bold">${mktdelayfeeAll}</td>															
															<td align="right" style="font-weight:bold">${memberdelayfeeAll}</td>
															<td align="right" style="font-weight:bold">${marginAll}</td>
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
	var url = "${basePath}/broke/memberInfoTree/forTree.action";
	ecsideDialog(url, window, 400, 570);

}
</script>