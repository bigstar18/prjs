<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/session.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>�ۺϻ�Ա�ֲֻ��ܱ�</title>
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
								action="${basePath}/report/memberHold/memberHoldReportQuary.action"
								name="frm" id="frm" method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_widthmid">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0" style="table-layout: fixed">
													<tr>
														<td align="left" class="table3_td_1tjcx">
															�ۺϻ�Ա��ţ�
															<input type="text" id="customerNo"
																name="${GNNT_}memberno[=][String]"
																value="${oldParams['memberno[=][String]'] }" size="14"
																class="input_textmin" />
														</td>
														<td align="left" class="table3_td_1tjcx">
															�ۺϻ�Ա���ƣ�
															<input type="text" id="memberNames"
																name="${ORIGINAL_}memberNames"
																value="${original_memberNames}" onclick="clickText()"
																readonly=true size="8" class="input_textmin">
															<input type="hidden" name="${ORIGINAL_}memberIds"
																id="memberIds" value="${original_memberIds}"
																class="input_text">
														</td>
														<td align="left" class="table3_td_1">
															��Ʒ��
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
														<td align="left">
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
											var="memberTrade"
											action="${basePath}/report/memberHold/memberHoldReportQuary.action"
											title="" minHeight="345" listWidth="140%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" csvFileName="�����б�.csv"
											style="table-layout:fixed">
											<ec:row ondblclick="">
												<ec:column width="3%" property="_0" title="���"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<ec:column property="clearDate[=][date]" title="��������"
													width="5%" style="text-align:left; " ellipsis="true">
													<fmt:formatDate value="${memberTrade.clearDate}"
														pattern="yyyy-MM-dd" />
												</ec:column>
												<ec:column property="memberNo[like]" title="�ۺϻ�Ա���"
													width="5%" style="text-align:left; "
													value="${memberTrade.memberNo}" ellipsis="true" />
												<ec:column property="memberName[like]" title="�ۺϻ�Ա����"
													width="8%" style="text-align:left; "
													value="${memberTrade.memberName}" ellipsis="true" />
												<ec:column property="commodityName[like]" title="��Ʒ"
													width="6%" style="text-align:left; "
													value="${memberTrade.commodityName}" ellipsis="true" />
												<ec:column property="customerbuyqty[=][int]" title="�ͻ���ֲ�"
													width="5%" style="text-align:right; " format="#,##0">
													<fmt:formatNumber value="${memberTrade.customerbuyqty}"
														pattern="#,##0" />
												</ec:column>
												<ec:column property="customersellqty[=][int]" title="�ͻ����ֲ�"
													width="5%" style="text-align:right; ">
													<fmt:formatNumber value="${memberTrade.customersellqty}"
														pattern="#,##0" />
												</ec:column>
												<ec:column property="customernetqty[=][int]" title="�ͻ����ֲ�"
													width="5%" style="text-align:right; ">
													<fmt:formatNumber value="${memberTrade.customernetqty}"
														pattern="#,##0" />
												</ec:column>
												<ec:column property="memberbuyqty[=][int]" title="�ۺϻ�Ա��ֲ�"
													width="6%" style="text-align:right; ">
													<fmt:formatNumber value="${memberTrade.memberbuyqty}"
														pattern="#,##0" />
												</ec:column>
												<ec:column property="membersellqty[=][int]" title="�ۺϻ�Ա���ֲ�"
													width="6%" style="text-align:right; ">
													<fmt:formatNumber value="${memberTrade.membersellqty}"
														pattern="#,##0" />
												</ec:column>
												<ec:column property="membernetqty[=][double]"
													title="�ۺϻ�Ա���ֲ�" width="6%" style="text-align:right; ">
													<fmt:formatNumber value="${memberTrade.membernetqty}"
														pattern="#,##0" />
												</ec:column>
												<ec:column property="netqty[=][double]" title="���ֲֺϼ�"
													width="5%" style="text-align:right; ">
													<fmt:formatNumber value="${memberTrade.netqty}"
														pattern="#,##0" />
												</ec:column>
												<ec:column property="memberdelayfee[=][double]"
													title="�ر��Ա�������ڷ�" width="7%" style="text-align:right; ">
													<fmt:formatNumber value="${memberTrade.memberdelayfee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="mktdelayfee[=][double]"
													title="�������������ڷ�" width="7%" style="text-align:right; ">
													<fmt:formatNumber value="${memberTrade.mktdelayfee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="customerdelayfee[=][double]"
													title="�տͻ����ڷ�" width="6%" style="text-align:right; ">
													<fmt:formatNumber value="${memberTrade.customerdelayfee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="delayfee[=][double]" title="�ۺϻ�Ա�������ڷ�"
													width="7%" style="text-align:right; ">
													<fmt:formatNumber value="${memberTrade.delayfee}"
														pattern="#,##0.00" />
												</ec:column>
											</ec:row>
											<c:if test="${ifHas eq 1}">		
												<ec:extendrow>
													<td>
													          �ϼ�:
													</td>
													<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
													<td align="right" style="font-weight:bold">${customerbuyqtyAll}</td>
													<td align="right" style="font-weight:bold">${customersellqtyAll}</td>
													<td align="right" style="font-weight:bold">${customernetqtyAll}</td>
													<td align="right" style="font-weight:bold">${memberbuyqtyAll}</td>
													<td align="right" style="font-weight:bold">${membersellqtyAll}</td>
													<td align="right" style="font-weight:bold"><fmt:formatNumber value="${membernetqtyAll}" pattern="0"/></td>
													<td align="right" style="font-weight:bold"><fmt:formatNumber value="${netqtyAll}" pattern="0"/></td>
													<td align="right" style="font-weight:bold">${memberdelayfeeAll}</td>
													<td align="right" style="font-weight:bold">${mktdelayfeeAll}</td>
													<td align="right" style="font-weight:bold">${customerdelayfeeAll}</td>
													<td align="right" style="font-weight:bold">${delayfeeAll}</td>
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
	frm.action = "${basePath}/report/memberHold/memberHoldReportQuary.action";
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