<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/session.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>�ͻ��ɽ����ܱ�</title>
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
								action="${basePath}/report/customerTrade/customerTradeReportQuery.action"
								name="frm" id="frm" method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_widthcdmax">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0" style="table-layout: fixed">
													<tr>
														<input type="hidden" name="${GNNT_}isRelated"
															id="isRelated" value="${oldParams['isRelated']}"
															class="input_text">
														<input type="hidden" name="${GNNT_}memberInfoIds"
															id="memberInfoIds" value="${oldParams['memberInfoIds']}"
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
														<input type="hidden" name="tree" id="tree" value="${tree}"
															class="input_text">
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
															�ۺϻ�Ա��ţ�
															<input type="text" name="${GNNT_}memberNo[=][String]"
																id="memberNo"
																value="${oldParams['memberNo[=][String]'] }"
																class="input_textmin">
														</td>
														<td class="table3_td_1tjcx" align="left">
															�����˺ţ�
															<input type="text" name="${GNNT_}customerNo[like]"
																id="customerNo"
																value="${oldParams['customerNo[like]'] }"
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
														<td class="table3_td_1tjcx" align="left">
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�������ڣ�
															<input type="text" style="width: 100px" id="endDate"
																class="wdate" maxlength="10"
																name="${GNNT_}primary.clearDate[<=][date]"
																value='${oldParams["primary.clearDate[<=][date]"]}'
																onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
														</td>
														<td class="table3_td_1tjcx" align="left">
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��Ʒ��
															<select id="commodityId"
																name="${GNNT_}primary.commodityId[=][String]"
																class="input_text">
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
															</button>
															&nbsp;
															<button class="btn_cz" onclick="mySelfReset()">
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
											var="customerTrade"
											action="${basePath}/report/customerTrade/customerTradeReportQuery.action"
											title="" minHeight="345" listWidth="140%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" csvFileName="�����б�.csv"
											style="table-layout:fixed">
											<ec:row recordKey="${customer.customerNo}">
												<ec:column width="3%" property="_0" title="���"
													value="${GLOBALROWCOUNT}" 
													 />
												<ec:column property="clearDate[=][date]" title="��������"
													width="4%" style="text-align:left; " ellipsis="true">
													<fmt:formatDate value="${customerTrade.clearDate}"
														pattern="yyyy-MM-dd" />
												</ec:column>
												<ec:column property="memberNo[like]" title="�ۺϻ�Ա���"
													width="4%" style="text-align:left; "
													value="${customerTrade.memberNo}" ellipsis="true" />
												<ec:column property="memberName[like]" title="�ۺϻ�Ա����"
													width="7%" style="text-align:left; "
													value="${customerTrade.memberName}" ellipsis="true" />
												<ec:column property="organizationName[like]" title="����"
													width="5%" value="${customerTrade.organizationName}"
													style="text-align:left; " ellipsis="true">
												</ec:column>
												<ec:column property="brokerName[=][String]" title="�Ӽ�"
													width="5%" value="${customerTrade.brokerName}"
													style="text-align:right; " ellipsis="true">
												</ec:column>
												<ec:column property="customerNo[=][String]" title="�����˺�"
													width="6%" style="text-align:right; "
													value="${customerTrade.customerNo}" ellipsis="true">
												</ec:column>
												<ec:column property="customerName[like]" title="�ͻ�����"
													width="5%" style="text-align:left; "
													value="${customerTrade.customerName}" ellipsis="true" />
												<ec:column property="commodityName[like]" title="��Ʒ"
													width="4%" style="text-align:left; "
													value="${customerTrade.commodityName}" ellipsis="true" />
												<ec:column property="customerqtysum[=][int]" title="�ɽ���" width="3%"
													style="text-align:right; " >
													<fmt:formatNumber value="${customerTrade.customerqtysum}"
														pattern="#,##0" />
												</ec:column>
												<ec:column property="customerfundsum[=][double]" title="�ɽ����"
													width="6%" style="text-align:right; " >
													<fmt:formatNumber value="${customerTrade.customerfundsum}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="customercloseplsum[=][double]" title="ƽ��ӯ��"
													width="5%" style="text-align:right;">
													<fmt:formatNumber
														value="${customerTrade.customercloseplsum}"
														pattern="#,##0.00" />
												</ec:column>
												
												<ec:column property="mktfee[=][double]" title="����������������" width="6%"
													style="text-align:right; " >
													<fmt:formatNumber value="${customerTrade.mktfee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="memberfee[=][double]" title="�ۺϻ�Ա����������" width="6%"
													style="text-align:right; ">
													<fmt:formatNumber value="${customerTrade.memberfee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="customerfee[=][double]" title="�տͻ�������" width="5%"
													style="text-align:right; ">
													<fmt:formatNumber value="${customerTrade.customerfee}"
														pattern="#,##0.00" />
												</ec:column>

											</ec:row>
											<c:if test="${ifHas eq 1}">		
												<ec:extendrow>
													<td>
													          �ϼ�:
													</td>
													<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
													<td>&nbsp;</td>
													<td align="right" style="font-weight:bold">${customerqtysumAll}</td>
													<td align="right" style="font-weight:bold">${customerfundsumAll}</td>
													<td align="right" style="font-weight:bold">${customercloseplsumAll}</td>
													<!-- 
													<td align="right" style="font-weight:bold">${customerholdplAll}</td>
													<td align="right" style="font-weight:bold">${customerplsumAll}</td> -->
													<td align="right" style="font-weight:bold">${mktfeeAll}</td>
													<td align="right" style="font-weight:bold">${memberfeeAll}</td>
													<td align="right" style="font-weight:bold">${customerfeeAll}</td>
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
	frm.action = "${basePath}/report/customerTrade/customerTradeReportQuery.action";
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
    var isRelated=document.getElementById("isRelated").value;
	var url = "${basePath}/broke/memberInfoTree/forTree.action?isRelated="+isRelated;
	var result=window.showModalDialog(url, window, "dialogWidth=400px; dialogHeight=570px; status=yes;scroll=yes;help=no;");
}
function mySelfReset() {//���
		window.parent.frames["topFrame1"].document.getElementById('tree').value="";
		var pathRrl=frm.action.toString();
		var au = '111111';
		if (typeof (AUsessionId) != "undefined") {
			au = AUsessionId;
		}
		var urlArray=pathRrl.split("?");
	  	if(urlArray.length==1){
			pathRrl=pathRrl+'?AUsessionId='+au+"&noQuery=true";
		}else if(urlArray.length==2){
			pathRrl=pathRrl+'&AUsessionId='+au+"&noQuery=true";
		}
		window.location.href=pathRrl;
	}
</script>