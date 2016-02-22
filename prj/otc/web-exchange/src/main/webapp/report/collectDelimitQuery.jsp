<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/session.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>�����������ѻ�ת��</title>
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
								action="${basePath}/report/marketCollectDelimit/marketCollectDelimitQuery.action"
								name="frm" id="frm" method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_widthmax">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0" style="table-layout: fixed">
													<tr>
														<td class="table3_td_1tjcx_1" align="left">
															���У�
															<span class="right_03zi"><select id="bank"
																	name="${GNNT_}bankCode[=][String]"
																	class="input_textmin">
																	<option value="">
																		��ѡ��
																	</option>
																	<c:forEach items="${bankList}" var="bank">
																		<option value="${bank.bankId}">
																			${bank.bankName }
																		</option>
																	</c:forEach>
																</select> </span>
															<script type="text/javascript">
frm.bank.value = '${oldParams["bankCode[=][String]"] }';
</script>
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
											var="collectDelimit"
											action="${basePath}/report/marketCollectDelimit/marketCollectDelimitQuery.action"
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
													<fmt:formatDate value="${collectDelimit.clearDate}"
														pattern="yyyy-MM-dd" />
												</ec:column>
												<ec:column property="bankName[like]" title="��������" width="6%"
													style="text-align:left; "
													value="${collectDelimit.bankName}" ellipsis="true" />
												<ec:column property="ReceivablesorPayables[=][String]"
													title="Ӧ��/Ӧ��" width="8%" style="text-align:left; "
													value="${collectDelimit.receivablesorPayables}" />
												<ec:column property="funds[=][double]"
													title="�ʽ���" width="8%" style="text-align:right; ">
													<fmt:formatNumber value="${collectDelimit.funds}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="marketfeebalance[=][double]"
													title="������������" width="8%" style="text-align:right; ">
													<fmt:formatNumber value="${collectDelimit.marketfeebalance}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="marketdelayfeebalance[=][double]"
													title="���������ڷ�" width="8%" style="text-align:right; ">
													<fmt:formatNumber value="${collectDelimit.marketdelayfeebalance}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="offsetbalance[=][double]"
													title="�ϼ�" width="8%" style="text-align:right; ">
													<fmt:formatNumber value="${collectDelimit.offsetbalance}"
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
	frm.action = "${basePath}/report/marketCollectDelimit/marketCollectDelimitQuery.action";
	checkQueryDate(frm.startDate.value, frm.endDate.value);
	frm.action = action;
}

function init() {
	if (frm.startDate.value == "" && frm.endDate.value == "") {
		frm.startDate.value = '${date}';
		frm.endDate.value = '${date}';
	}

}
</script>