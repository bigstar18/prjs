
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>
<html xmlns:MEBS>
	<head>
		<title>�ͻ��ʽ��ѯ</title>
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
								action="${basePath}/query/queryCustomerFundSearch/list.action?sortName=primary.customerNo"
								method="post">
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
															�����˺�:
															<label>
																<input type="text" class="input_text" id="customerId"
																	name="${GNNT_}customerNo[like]" size="14"
																	value="${oldParams['customerNo[like]'] }" />
															</label>
														</td>
														<td class="table3_td_1" align="left">
															�ͻ�����:
															<label>
																<input type="text" name="${GNNT_}customerName[like]"
																	id="customerName"
																	value="${oldParams['customerName[like]'] }"
																	class="input_textmin" />
															</label>
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
											action="${basePath}/query/queryCustomerFundSearch/list.action?sortName=primary.customerNo"
											title="" minHeight="345" listWidth="150%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" csvFileName="�����б�.csv"
											style="table-layout:fixed">
											<ec:row ondblclick="">
												<ec:column width="3%" property="_0" title="���"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<ec:column property="organizationname[like]" title="����"
													width="5%" style="text-align:left; "
													value="${customerFund.organizationname}" ellipsis="true" />
												<ec:column property="brokeragename[like]" title="�Ӽ�"
													width="5%" style="text-align:left; "
													value="${customerFund.brokeragename}" ellipsis="true" />
												<ec:column property="customerNo[like]" title="�����˺�"
													width="7%" style="text-align:left; "
													value="${customerFund.customerNo}" ellipsis="true" />
												<ec:column property="customerName[like]" title="�ͻ�����"
													width="6%" style="text-align:left; "
													value="${customerFund.customerName}"  ellipsis="true"/>
												<ec:column property="beginningcaptical[=][double]" title="�ڳ�Ȩ��"
													width="7%" style="text-align:right; ">
													<fmt:formatNumber value="${customerFund.beginningcaptical}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="runtimefundio[=][double]" title="�����"
													width="6%" style="text-align:right; ">
													<fmt:formatNumber value="${customerFund.runtimefundio}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="runtimeclosepl[=][double]" title="ƽ��ӯ��"
													width="6%" style="text-align:right; ">
													<fmt:formatNumber value="${customerFund.runtimeclosepl}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="floatingloss[=][double]" title="�ֲ�ӯ��"
													width="6%" style="text-align:right; ">
													<fmt:formatNumber value="${customerFund.floatingloss}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="runtimefee[=][double]" title="������"
													width="5%" style="text-align:right; ">
													<fmt:formatNumber value="${customerFund.runtimefee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="margin[=][double]" title="ռ�ñ�֤��"
													width="6%" style="text-align:right; ">
													<fmt:formatNumber value="${customerFund.margin}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="livemargin[=][double]" title="���ñ�֤��"
													width="6%" style="text-align:right; ">
													<fmt:formatNumber value="${customerFund.livemargin}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="frozenmargin[=][double]" title="���ᱣ֤��"
													width="6%" style="text-align:right; ">
													<fmt:formatNumber value="${customerFund.frozenmargin}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="frozenfee[=][double]" title="����������"
													width="5%" style="text-align:right; ">
													<fmt:formatNumber value="${customerFund.frozenfee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="presentcaptical[=][double]"
													title="��ǰȨ��" width="7%" style="text-align:right; ">
													<fmt:formatNumber value="${customerFund.presentcaptical}"
														pattern="#,##0.00"></fmt:formatNumber>
												</ec:column>

												<ec:column property="riskRate_log[=][String]" title="������"
													width="4%" style="text-align:right;"
													value="${customerFund.riskRate_log}" ellipsis="true"
													sortable="false" filterable="false" />
											</ec:row>
											<c:if test="${ifHas eq 1}">		
												<ec:extendrow>
													<td>
													          �ϼ�:
													</td>
													<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
													<td align="right" style="font-weight:bold">${beginningcapticalAll}</td>													
													<td align="right" style="font-weight:bold">${runtimefundioAll}</td>													
													<td align="right" style="font-weight:bold">${runtimecloseplAll}</td>													
													<td align="right" style="font-weight:bold">${floatinglossAll}</td>													
													<td align="right" style="font-weight:bold">${runtimefeeAll}</td>													
													<td align="right" style="font-weight:bold">${marginAll}</td>													
													<td align="right" style="font-weight:bold">${livemarginAll}</td>
													<td align="right" style="font-weight:bold">${frozenmarginAll}</td>
													<td align="right" style="font-weight:bold">${frozenfeeAll}</td>
													<td align="right" style="font-weight:bold">${presentcapticalAll}</td>
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
	function clickText() {
	var url = "${basePath}/broke/treeForMemberInfo/forTree.action";
	ecsideDialog(url, window, 500,650);

}
		</SCRIPT>