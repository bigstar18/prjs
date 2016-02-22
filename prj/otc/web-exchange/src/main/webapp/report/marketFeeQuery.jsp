<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/session.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>����������</title>
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
										action="${basePath}/report/marketFee/marketFeeReportQuery.action"
										name="frm" id="frm" method="post">
										<table border="0" cellpadding="0" cellspacing="0"
											class="table2_style">
											<tr>
												<td class="table2_td_widthcdmax">
													<div class="div2_top">
														<table border="0" cellspacing="0" cellpadding="0">
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
																<td class="table3_td_1tjcx" align="left">
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
													var="marketFee"
													action="${basePath}/report/marketFee/marketFeeReportQuery.action"
													title="" minHeight="345" listWidth="100%"
													retrieveRowsCallback="limit" sortRowsCallback="limit"
													filterRowsCallback="limit" csvFileName="�����б�.csv"
													style="table-layout:fixed">
													<ec:row ondblclick="">
														<ec:column width="5%" property="_0" title="���"
															value="${GLOBALROWCOUNT}" sortable="false"
															filterable="false" />
														<ec:column property="clearDate[=][date]" title="��������"
															width="10%" style="text-align:left; " ellipsis="true">
															<fmt:formatDate value="${marketFee.clearDate}"
																pattern="yyyy-MM-dd" />
														</ec:column>
														<ec:column property="bankName[like]" title="��������"
															width="15%" style="text-align:left; "
															value="${marketFee.bankName}" ellipsis="true" />
														<ec:column property="lastmarketfeebalance[=][double]" title="������ȡ�������"
															width="15%" style="text-align:right; ">
															<fmt:formatNumber
																value="${marketFee.lastmarketfeebalance}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="marketfeenew[=][double]" title="�������Ӷ�"
															width="10%" style="text-align:right; ">
															<fmt:formatNumber value="${marketFee.marketfeenew}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="marketfeeout[=][double]" title="���ջ�ת��"
															width="10%" style="text-align:right; ">
															<fmt:formatNumber value="${marketFee.marketfeeout}"
																pattern="#,##0.00" />
														</ec:column>
														<ec:column property="marketfeebalance[=][double]" title="������ȡ�������"
															width="10%" style="text-align:right; ">
															<fmt:formatNumber value="${marketFee.marketfeebalance}"
																pattern="#,##0.00" />
														</ec:column>
													</ec:row>
													<c:if test="${ifHas eq 1}">		
														<ec:extendrow>
															<td>
															          �ϼ�:
															</td>
															<td>&nbsp;</td><td>&nbsp;</td>
															<td align="right" style="font-weight:bold">${lastmarketfeebalanceAll}</td>
															<td align="right" style="font-weight:bold">${marketfeenewAll}</td>
															<td align="right" style="font-weight:bold">${marketfeeoutAll}</td>
															<td align="right" style="font-weight:bold">${marketfeebalanceAll}</td>
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
	frm.action = "${basePath}/report/marketFee/marketFeeReportQuery.action";
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