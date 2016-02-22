<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>
<html xmlns:MEBS>
	<head>
		<title>�ɳ��ʽ��ѯ</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
		<IMPORT namespace="MEBS"
			implementation="${basePath}/common/jslib/calendar.htc">
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
							<form name="frm"
								action="${basePath}/query/queryCustomerFundOutInSearch/list.action"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0" style="table-layout: fixed">
													<tr>
														<td class="table3_td_1tjcx" align="left">
															�����˺�:
															<input type="text" class="input_text" id="firmId"
																	name="${GNNT_}primary.firmId[like]" size="14"
																	value="${oldParams['primary.firmId[like]'] }" />
														</td>
														<td class="table3_td_1tjcx" align="left">
															�ͻ�����:
															<input type="text" class="input_text" id="firmId"
																	name="${GNNT_}primary.firmName[like]" size="14"
																	value="${oldParams['primary.firmName[like]'] }" />
														</td>
														<td class="table3_td_1" align="left">
															&nbsp;��������:&nbsp;
															<span class="right_03zi"><select id="bankName"
																	name="${GNNT_}primary.bankCode[=][String]"
																	class="input_textmin">
																	<option value="">
																		��ѡ��
																	</option>
																	<c:forEach items="${bankList}" var="bank">
																		<option value="${bank.bankId}">
																			${bank.bankName }
																		</option>
																	</c:forEach>
																</select>
															</span>
															<script type="text/javascript">
																frm.bankName.value = '${oldParams["primary.bankCode[=][String]"] }';
															</script>
														</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" onClick="search1()">
																��ѯ
															</button>&nbsp;
															<button class="btn_cz" onClick="myReset()">
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
										<ec:table items="resultList"
											autoIncludeParameters="${empty param.autoInc}"
											var="customerFund"
											action="${basePath}/query/queryCustomerFundOutInSearch/list.action"
											title="" minHeight="345" listWidth="155%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" csvFileName="�����б�.csv"
											style="table-layout:fixed">
											<ec:row ondblclick="">
												<ec:column width="3%" property="_0" title="���"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<ec:column property="primary.firmId[like]" title="�����˺�"
													width="6%" style="text-align:left; "
													value="${customerFund.firmId}" ellipsis="true" />
												<ec:column property="primary.firmName[like]" title="�ͻ�����"
													width="5%" style="text-align:left; "
													value="${customerFund.firmName}" ellipsis="true" />
												<ec:column property="primary.bankName[like]" title="��������"
													width="5%" style="text-align:left; "
													value="${customerFund.bankName}" ellipsis="true" />
												<ec:column property="primary.lastcapital[=][double]" title="�����˻��ڳ�Ȩ��"
													width="6%" style="text-align:right; ">
													<fmt:formatNumber value="${customerFund.lastcapital}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="primary.fundoutbank[=][double]" title="���г���ϼ�"
													width="5%" style="text-align:right; "
													value="${customerFund.fundoutbank}">
													<fmt:formatNumber value="${customerFund.fundoutbank}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="v_lastcapital[=][double]" title="�����˺��ڳ�Ȩ��"
													width="6%" style="text-align:right; ">
													<fmt:formatNumber value="${customerFund.v_lastcapital}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="primary.v_balancebegin[=][double]" title="�����˺��ڳ����ñ�֤��"
													width="8%" style="text-align:right; " >
													<fmt:formatNumber value="${customerFund.v_balancebegin}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="primary.v_balance[=][double]"
													title="�����˺ŵ�ǰ���ñ�֤��" width="7%" style="text-align:right; ">
													<fmt:formatNumber value="${customerFund.v_balance}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="primary.unfrozenmargen[=][double]" title="�����ͷű�֤��"
													width="6%" style="text-align:right; ">
													<fmt:formatNumber value="${customerFund.unfrozenmargen}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="primary.v_fundin[=][double]" title="�����˺����"
													width="6%" style="text-align:right; ">
													<fmt:formatNumber value="${customerFund.v_fundin}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="primary.v_fundout[=][double]" title="�����˺ų���"
													width="6%" style="text-align:right; ">
													<fmt:formatNumber value="${customerFund.v_fundout}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="primary.minriskfund[=][double]" title="��ͳ�����ֵ"
													width="6%" style="text-align:right; ">
													<fmt:formatNumber value="${customerFund.minriskfund}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="primary.v_canoutfund[=][double]" title="�����˺ſɳ��ʽ�"
													width="6%" style="text-align:right; ">
													<fmt:formatNumber value="${customerFund.v_canoutfund}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="primary.bank_canoutfund[=][double]" title="���пɳ��ʽ�"
													width="6%" style="text-align:right; ">
													<fmt:formatNumber value="${customerFund.bank_canoutfund}"
														pattern="#,##0.00" />
												</ec:column>
											</ec:row>
											<c:if test="${ifHas eq 1}">		
												<ec:extendrow>
													<td> �ϼ�:</td>
													<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
													<td align="right" style="font-weight:bold">${lastcapitalAll}</td>
													<td align="right" style="font-weight:bold">${fundoutbankAll}</td>
													<td align="right" style="font-weight:bold">${v_lastcapitalAll}</td>
													<td align="right" style="font-weight:bold">${v_balancebeginAll}</td>
													<td align="right" style="font-weight:bold">${v_balanceAll}</td>
													<td align="right" style="font-weight:bold">${unfrozenmargenAll}</td>
													<td align="right" style="font-weight:bold">${v_fundinAll}</td>
													<td align="right" style="font-weight:bold">${v_fundoutAll}</td>
													<td>&nbsp;</td>
													<td align="right" style="font-weight:bold">${v_canoutfundAll}</td>
													<td align="right" style="font-weight:bold">${bank_canoutfundAll}</td>
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
	</body>
</html>

<SCRIPT type="text/javascript">
		function search1(){
			frm.submit();
		}
		function init(queryType){
			 document.getElementById("beginDate").disabled=true;
			document.getElementById("endDate").disabled=true;
			change(queryType);
		}
		</SCRIPT>