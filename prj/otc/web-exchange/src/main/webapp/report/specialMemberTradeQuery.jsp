<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/session.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>�ر��Ա�ɽ����ܱ�</title>
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
								action="${basePath}/report/specialMemberTrade/specialMemberTradeReportQuary.action"
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
															�ر��Ա��ţ�
															<input type="text" id="customerNo"
																name="${GNNT_}s_memberNo[=][String]"
																value="${oldParams['s_memberNo[=][String]'] }" size="14"
																class="input_textmin" />
														</td>
														<td class="table3_td_1tjcxmid" align="left">
															�ر��Ա���ƣ�
															<input type="text" id="memberNames"
																name="${ORIGINAL_}memberNames"
																value="${original_memberNames}" onclick="clickText()"
																readonly=true size="8" class="input_textmin">
															<input type="hidden" name="${ORIGINAL_}smemberIds"
																id="memberIds" value="${original_smemberIds}"
																class="input_text">
														</td>
														<td class="table3_td_1tjcx_1" align="left">
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
														<td class="table3_td_1tjcxmid" align="left">
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��ʼ���ڣ�
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
											var="specialMemberTrade"
											action="${basePath}/report/specialMemberTrade/specialMemberTradeReportQuary.action"
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
													<fmt:formatDate value="${specialMemberTrade.clearDate}"
														pattern="yyyy-MM-dd" />
												</ec:column>
												<ec:column property="s_memberNo[like]" title="�ر��Ա���"
													width="9%" style="text-align:left; "
													value="${specialMemberTrade.s_memberNo}" ellipsis="true" />
												<ec:column property="s_memberName[like]" title="�ر��Ա����"
													width="13%" style="text-align:left; "
													value="${specialMemberTrade.s_memberName}" ellipsis="true" />
												<ec:column property="commodityName[like]" title="��Ʒ"
													width="10%" style="text-align:left; "
													value="${specialMemberTrade.commodityName}" ellipsis="true" />
												<ec:column property="qtysum[=][int]" title="�ɽ���" width="8%"
													style="text-align:right; " format="#,##0">
													<fmt:formatNumber value="${specialMemberTrade.qtysum}"
														pattern="#,##0" />
												</ec:column>
												<ec:column property="fundsum[=][double]" title="�ɽ����" width="13%"
													style="text-align:right; ">
													<fmt:formatNumber value="${specialMemberTrade.fundsum}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="closepl[=][double]" title="ƽ��ӯ��" width="11%"
													style="text-align:right; " >
													<fmt:formatNumber value="${specialMemberTrade.closepl}"
														pattern="#,##0.00" />
												</ec:column>
											</ec:row>
											<c:if test="${ifHas eq 1}">		
												<ec:extendrow>
													<td>
													          �ϼ�:
													</td>
													<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
													<td align="right" style="font-weight:bold">${qtysumAll}</td>
													<td align="right" style="font-weight:bold">${fundsumAll}</td>
													<td align="right" style="font-weight:bold">${closeplAll}</td>
													<!-- 
													<td align="right" style="font-weight:bold">${holdplAll}</td>
													<td align="right" style="font-weight:bold">${totalplAll}</td> -->
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
	</body>
	<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
	<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value=""
			onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;" name="" />
		</textarea>
	<script type="text/javascript">
function select1() {
	var action = frm.action;
	frm.action = "${basePath}/report/specialMemberTrade/specialMemberTradeReportQuary.action";
	checkQueryDate(frm.startDate.value, frm.endDate.value);
	frm.action = action;
}
function xls() {
	frm.type.value = "xls";
	select1();
	frm.type.value = "";
}
function init() {
	if (frm.startDate.value == "" && frm.endDate.value == "") {
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