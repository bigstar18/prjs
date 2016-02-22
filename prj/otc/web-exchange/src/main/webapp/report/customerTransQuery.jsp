<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/session.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>�ͻ�����ͳ�Ʊ�</title>
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
							<form action="${basePath}/report/customerTrans/customerTransactionQuery.action"
										name="frm" id="frm" method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_widthmax">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0" style="table-layout:fixed">
													<tr>
														<td class="table3_td_1tjcx_1" align="left">
															��Ա��ţ�
															<input type="text"
														name="${GNNT_}primary.memberno[like]" id="memberno"
														value="${oldParams['primary.memberno[like]'] }"
														class="input_textmin">
														</td>
														<td class="table3_td_1tjcx_1" align="left">
															��Ա���ƣ�
													<input type="text" id="memberNames"
														name="${ORIGINAL_}memberNames"
														value="${original_memberNames}" onclick="clickText()"
														readonly=true size="8" class="input_textmin">
														<input type="hidden" name="${ORIGINAL_}memberIds"
														id="memberIds" value="${original_memberIds}"
														class="input_text">
														</td>
														<td class="table3_td_1tjcx_1" align="left">
														�����˺ţ�
													<input type="text" name="${GNNT_}customerno[like]"
														id="customerNo"
														value="${oldParams['customerno[like]'] }"
														class="input_textmin">
														</td>
														<td class="table3_td_1tjcx_1" align="left">
														�ͻ����ƣ�
													<input type="text"
														name="${GNNT_}primary.customername[like]"
														id="customerName"
														value="${oldParams['primary.customername[like]'] }"
														class="input_textmin">
														</td>
													</tr>
													<tr>
													<td class="table3_td_1tjcx_1" align="left">
														��ʼ���ڣ�
													<input type="text" style="width: 100px" id="startDate"
														class="wdate" maxlength="10"
														name="${GNNT_}trunc(primary.cleardate)[>=][date]"
														value='${oldParams["trunc(primary.atClearDate)[>=][date]"]}'
														onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
														</td>
														<td class="table3_td_1tjcx_1" align="left">
															�������ڣ�
													<input type="text" style="width: 100px" id="endDate"
														class="wdate" maxlength="10"
														name="${GNNT_}trunc(primary.cleardate)[<=][date]"
														value='${oldParams["trunc(primary.atClearDate)[<=][date]"]}'
														onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
														</td>
														<td class="table3_td_1tjcx_1" align="left">
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;������
													<input type="text"
														name="${GNNT_}primary.organizationname[like]"
														id="organizationName"
														value="${oldParams['primary.organizationname[like]'] }"
														class="input_textmin">
														</td>
														<td class="table3_td_1tjcx_1" align="left">
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��Ʒ��
													<select id="commodityId" name="${GNNT_}primary.commodityId[like]"
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
														frm.commodityId.value='${oldParams['primary.commodityId[like]'] }';
													</script>
														</td>
													</tr>
													<tr>
														<td class="table3_td_1tjcx_1" align="left">
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id='noqty' name="noqty" value=""
														>��ʾ�޲ֵ��ͻ�
													<input type="hidden"
														name="${GNNT_}primary.customerqtysum[>][int]" value="0"
														disabled="disabled" id="qtysum">
														</td>
														<td align="left" colspan="3">
															<button class="btn_sec" onclick="select1()">
																��ѯ
															</button>&nbsp;
															<button class="btn_cz" onclick="myReset();">
																����
															</button>
													<input type="hidden" id="type" name="type">
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
													var="customerTrans"
													action="${basePath}/report/customerTrans/customerTransactionQuery.action"
													title="" minHeight="345" listWidth="200%"
													retrieveRowsCallback="limit" sortRowsCallback="limit"
													filterRowsCallback="limit" csvFileName="�����б�.csv"
													style="table-layout:fixed">
											<ec:row recordKey="${customer.customerNo}">
												<ec:column width="2%" property="_0" title="���"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<ec:column property="clearDate[=][date]" title="��������"
															width="5%" style="text-align:left; "
															ellipsis="true">
												<fmt:formatDate value="${customerTrans.clearDate}" pattern="yyyy-MM-dd"/>
												</ec:column>
												<ec:column property="memberNo[like]" title="��Ա���"
															width="5%" style="text-align:left; "
															value="${customerTrans.memberNo}" ellipsis="true" />
												<ec:column property="memberName[like]" title="��Ա����"
															width="5%" style="text-align:left; "
															value="${customerTrans.memberName}" ellipsis="true" />
														<ec:column property="organizationname[like]" title="����"
															width="5%" value="${customerTrans.organizationname}"
															style="text-align:left; " ellipsis="true">
														</ec:column>
														<ec:column property="organizationno[=][String]" title="��������"
															width="5%" value="${customerTrans.organizationno}"
															style="text-align:right; " ellipsis="true">
														</ec:column>
														<ec:column property="customerNo[=][double]" title="�����˺�"
															width="5%" style="text-align:right; "
															value="${customerTrans.customerNo}" ellipsis="true">
														</ec:column>
														<ec:column property="customerName[like]" title="�ͻ�����"
															width="5%" style="text-align:left; "
															value="${customerTrans.customerName}" ellipsis="true" />
														<ec:column property="commodityName[like]" title="��Ʒ"
															width="4%" style="text-align:left; "
															value="${customerTrans.commodityName}" ellipsis="true" />
														<ec:column property="customerqtysum" title="�ɽ���"
															width="4%" calcTitle="�ϼƣ�" style="text-align:right; " filterable="false" ellipsis="true"
															>
															<fmt:formatNumber value="${customerTrans.customerqtysum}"
																pattern="#,##0" />
														</ec:column>
														<ec:column property="customerfundsum" title="�ɽ����"
															width="6%" style="text-align:right; " filterable="false" ellipsis="true"
															>
															<fmt:formatNumber value="${customerTrans.customerfundsum}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="customercloseplsum" title="�ͻ�ƽ��ӯ��"
															width="6%" style="text-align:right; " filterable="false" ellipsis="true"
															>
															<fmt:formatNumber value="${customerTrans.customercloseplsum}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="customerholdpl" title="�ͻ��ֲ�ӯ��"
															width="6%" style="text-align:right;" filterable="false" ellipsis="true"
															>
															<fmt:formatNumber value="${customerTrans.customerholdpl}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="plsum" title="�ϼ�"
															width="6%" style="text-align:right; " filterable="false" ellipsis="true"
															>
															<fmt:formatNumber value="${customerTrans.plsum}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="mktfee" title="����������������"
															width="8%" style="text-align:right; " filterable="false" ellipsis="true"
															>
															<fmt:formatNumber value="${customerTrans.mktfee}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="MemberFee"
															title="��Ա����������" width="7%" style="text-align:right;"
															ellipsis="true" filterable="false">
															<fmt:formatNumber value="${customerTrans.MemberFee}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="customerfee"
															title="�տͻ�" width="6%" style="text-align:right;"
															ellipsis="true" filterable="false">
															<fmt:formatNumber value="${customerTrans.customerfee}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="customerdelayfee" title="���ڷ�"
															width="6%" style="text-align:right;" filterable="false" ellipsis="true"
															>
															<fmt:formatNumber value="${customerTrans.customerdelayfee}"
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
		<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value=""
				onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="" />
		</textarea>
		
<script type="text/javascript">
function select1() {
	var action = frm.action;
	var ckBox = document.getElementById('noqty');
	if (ckBox.checked) {
		frm.qtysum.disabled = true;
	} else {
		frm.qtysum.disabled = false;
	}
	frm.action = "${basePath}/report/customerTrans/customerTransactionQuery.action";
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
	frm.startDate.value ='${date}';
	frm.endDate.value ='${date}' ;
}
function clickText() {
	var memberIds = frm.memberIds.value;
	var url = "${basePath}/report/customer/memberInfoList.action?oldMemberIds="
			+ memberIds;
	var result = window.showModalDialog(url,'',"dialogWidth=350px;dialogHeight=520px");
	if (result != null && result != '') {
		var result1 = result.split('####');
		frm.memberIds.value = result1[0];
		frm.memberNames.value = result1[1];
	}
}
</script>