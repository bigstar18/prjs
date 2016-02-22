<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/session.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>�ͻ��ֲֻ��ܱ�</title>
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
								action="${basePath}/report/customerHoldSummary/customerHoldSummaryReportQuery.action"
								name="frm" id="frm" method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_widthmax">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0" style="table-layout: fixed">
													<tr>
														<input type="hidden" name="${GNNT_}isRelated"
																id="isRelated" value="${oldParams['isRelated']}"
																class="input_text">
																<input type="hidden" name="${GNNT_}memberIds"
																id=memberIds value="${oldParams['memberIds']}"
																class="input_text">
																<input type="hidden" id="brokerageIds"
																	name="${GNNT_}brokerageIds"
																	value="${oldParams['brokerageIds'] }"
																	class="input_text" />
																<input type="hidden" id="organizationIds"
																	name="${GNNT_}organizationIds"
																	value="${oldParams['organizationIds'] }"
																	class="input_text" />
																<input type="hidden" id="managerIds"
																	name="${GNNT_}managerIds"
																	value="${oldParams['managerIds'] }" class="input_text" />
																<td class="table3_td_1tjcx" align="left">
																	�ͻ�������
																	<input type="text" id="selectIds"
																	name="${GNNT_}selectIds"
																	value="${oldParams['selectIds'] }" class="input_textmin" readonly='readonly'/><a href="javascript:clickText();"><img
																	align="absmiddle" src="<%=skinPath%>/cssimg/kh.gif">
																</td>
														<td class="table3_td_1mid" align="left">
															�����˺ţ�
															<input type="text" name="${GNNT_}customerno[like]"
																id="customerNo"
																value="${oldParams['customerno[like]'] }"
																class="input_text">
														</td>
														<td class="table3_td_1tjcx_1" align="left">
															�ͻ����ƣ�
															<input type="text"
																name="${GNNT_}primary.customerName[like]"
																id="customerName"
																value="${oldParams['primary.customerName[like]'] }"
																class="input_textmin">
														</td>
													</tr>
													<tr>
														<td class="table3_td_1tjcx_1" align="left">
															��ʼ���ڣ�
															<input type="text" style="width: 100px" id="startDate"
																class="wdate" maxlength="10"
																name="${GNNT_}primary.clearDate[>=][date]"
																value='${oldParams["primary.clearDate[>=][date]"]}'
																onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
														</td>
														<td class="table3_td_1mid" align="left">
															�������ڣ�
															<input type="text" style="width: 120px" id="endDate"
																class="wdate" maxlength="10"
																name="${GNNT_}primary.clearDate[<=][date]"
																value='${oldParams["primary.clearDate[<=][date]"]}'
																onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
														</td>
														<td class="table3_td_1tjcx_1" align="left">
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��Ʒ��
															<select id="commodityId"
																name="${GNNT_}primary.commodityId[=][String]"
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
														<td align="left" colspan="3">
															<button class="btn_sec" onclick="select1()">
																��ѯ
															</button>&nbsp;
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
											var="customerHold"
											action="${basePath}/report/customerHoldSummary/customerHoldSummaryReportQuery.action"
											title="" minHeight="345" listWidth="127%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" csvFileName="�����б�.csv"
											style="table-layout:fixed">
											<ec:row recordKey="${customer.customerNo}">
												<ec:column width="2%" property="_0" title="���"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<ec:column property="clearDate[=][date]" title="��������"
													width="5%" style="text-align:left; " ellipsis="true">
													<fmt:formatDate value="${customerHold.clearDate}"
														pattern="yyyy-MM-dd" />
												</ec:column>
												<ec:column property="organizationName[like]" title="����"
													width="6%" value="${customerHold.organizationName}"
													style="text-align:left; " ellipsis="true">
												</ec:column>
												<ec:column property="brokerName[like]" title="�Ӽ�" width="6%"
													value="${customerHold.brokerName}"
													style="text-align:right; " ellipsis="true">
												</ec:column>
												<ec:column property="customerNo[=][String]" title="�����˺�"
													width="6%" style="text-align:right; "
													value="${customerHold.customerNo}" ellipsis="true">
												</ec:column>
												<ec:column property="customerName[like]" title="�ͻ�����"
													width="5%" style="text-align:left; "
													value="${customerHold.customerName}" ellipsis="true" />
												<ec:column property="commodityName[like]" title="��Ʒ"
													width="5%" style="text-align:left; "
													value="${customerHold.commodityName}" ellipsis="true" />
												<ec:column property="buyqty[=][int]" title="�򵥳ֲ���" width="4%"
													style="text-align:right; " >
													<fmt:formatNumber value="${customerHold.buyqty}"
														pattern="#,##0" />
												</ec:column>
												<ec:column property="sellqty[=][int]" title="�����ֲ���" width="4%"
													style="text-align:right; " >
													<fmt:formatNumber value="${customerHold.sellqty}"
														pattern="#,##0" />
												</ec:column>
												<ec:column property="qtysum[=][int]" title="�ֲ����ϼ�" width="4%"
													style="text-align:right; " >
													<fmt:formatNumber value="${customerHold.qtysum}"
														pattern="#,##0" />
												</ec:column>
												<ec:column property="floatingloss[=][double]" title="�ֲ�ӯ��" width="7%"
													style="text-align:right;">
													<fmt:formatNumber value="${customerHold.floatingloss}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="delayfee[=][double]" title="�տͻ����ڷ�" width="5%"
													style="text-align:right;" >
													<fmt:formatNumber value="${customerHold.delayfee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="mktdelayfee[=][double]" title="�������������ڷ�" width="6%"
													style="text-align:right;">
													<fmt:formatNumber value="${customerHold.mktdelayfee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="memberdelayfee[=][double]" title="��Ա�������ڷ�" width="6%"
													style="text-align:right;">
													<fmt:formatNumber value="${customerHold.memberdelayfee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="margin[=][double]" title="ռ�ñ�֤��" width="7%"
													style="text-align:right; ">
													<fmt:formatNumber value="${customerHold.margin}"
														pattern="#,##0.00" />
												</ec:column>
											</ec:row>
											<c:if test="${ifHas eq 1}">		
														<ec:extendrow>
															<td>
															          �ϼ�:
															</td>
															<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
															<td align="right" style="font-weight:bold">${buyqtyAll}</td>
															<td align="right" style="font-weight:bold">${sellqtyAll}</td>
															<td align="right" style="font-weight:bold">${qtysumAll}</td>
															<td align="right" style="font-weight:bold">${floatinglossAll}</td>
															<td align="right" style="font-weight:bold">${delayfeeAll}</td>													
															<td align="right" style="font-weight:bold">${mktdelayfeeAll}</td>
															<td align="right" style="font-weight:bold">${memberdelayfeeAll}</td>
															<td align="right" style="font-weight:bold">${marginAll}</td>
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
	frm.action = "${basePath}/report/customerHoldSummary/customerHoldSummaryReportQuery.action";
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
function clickText() {
	var url = "${basePath}/broke/treeForMemberInfo/forTree.action";
	ecsideDialog(url, window, 500, 650);

}
</script>