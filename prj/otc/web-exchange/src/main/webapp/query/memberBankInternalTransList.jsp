
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>
<html xmlns:MEBS>
	<head>
		<title>��Ա��������ת�˼�¼</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
		<IMPORT namespace="MEBS"
			implementation="${basePath}/common/jslib/calendar.htc">
	</head>
	<body >
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td >
						<div class="div_tj">
							<form name="frm"
								action="${basePath}/query/queryMemberBankInternalTransSearch/list.action?sortName=primary.transId&sortOrder=true"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td  class="table2_td_widthmax">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0" style="table-layout:fixed">
													<tr>
													<td class="table3_td_1" align="left">
															&nbsp;&nbsp;�����˺�:&nbsp;
															<label>
																<input type="text" class="input_textmin" id="firmName"
																	name="${GNNT_}firm.firmId[like]" size="14"
																	value="${oldParams['firm.firmId[like]'] }" />
															</label>
														</td>
														
														<td class="table3_td_1" align="left">
															 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�˻�����:&nbsp;
															<label>
																<input type="text" class="input_textmin" id="firmName"
																	name="${GNNT_}firm.firmName[like]" size="14"
																	value="${oldParams['firm.firmName[like]'] }" />
															</label>
														</td>
														<td class="table3_td_1" align="left">
															&nbsp;��������:&nbsp;
															<span class="right_03zi"><select id="bankName"
																	name="${GNNT_}bank.bankName[like]"
																	class="input_textmin">
																	<option value="">
																		��ѡ��
																	</option>
																	<c:forEach items="${bankList}" var="bank">
																		<option value="${bank.bankName}">
																			${bank.bankName }
																		</option>
																	</c:forEach>
																</select>
															</span>
															<script type="text/javascript">
frm.bankName.value = '${oldParams["bank.bankName[like]"] }';
</script>
														</td>
														<td class="table3_td_1" align="left">
															&nbsp;Ŀ����������:&nbsp;
															<span class="right_03zi"><select id="bankCode_target"
																	name="${GNNT_}bankCode_target[like]"
																	class="input_textmin">
																	<option value="">
																		��ѡ��
																	</option>
																	<c:forEach items="${bankList}" var="bank">
																		<option value="${bank.bankName}">
																			${bank.bankName }
																		</option>
																	</c:forEach>
																</select>
															</span>
															<script type="text/javascript">
frm.bankCode_target.value = '${oldParams["bankCode_target[like]"] }';
</script>
														</td>
														<td>&nbsp;</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" onClick="search1()">
																��ѯ
															</button>&nbsp;&nbsp;
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
											var="bankInternalTrans"
											action="${basePath}/query/queryMemberBankInternalTransSearch/list.action?sortName=primary.transId&sortOrder=true"
											title="" minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="�����б�.csv" style="table-layout:fixed">
											<ec:row ondblclick="">
												<ec:column width="3%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												
												<ec:column property="transId[=][long]" title="ת�˼�¼��"
													width="10%" style="text-align:left; "
													value="${bankInternalTrans.transId}"  ellipsis="true"/>
												<ec:column property="firm.firmId[like]" title="�����˺�"
													width="10%" style="text-align:left; "
													value="${bankInternalTrans.firmId}"  ellipsis="true" />
												<ec:column property="firm.firmName[like]" title="�˻�����"
													width="10%" style="text-align:left; "
													value="${bankInternalTrans.firmName}"  ellipsis="true" />
												<ec:column property="bank.bankName[like]" title="��������"
													width="10%" style="text-align:left; "
													value="${bankInternalTrans.bankName}" ellipsis="true"/>
												<ec:column property="bankTarget.bankName[=][String]" title="Ŀ������"
													width="10%" style="text-align:left; "
													value="${bankInternalTrans.bankTargetName}" ellipsis="true"/>
												<ec:column property="amount[=][double]" title="���"
													width="8%" style="text-align:right; ">
													<fmt:formatNumber value="${bankInternalTrans.amount}"
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
		<!-- �༭�͹�����ʹ�õĲ���ģ�� -->
		<textarea id="esc_codeType" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="oprcode[=]">
			<ec:options items="codeMap" />
		</select>
	    </textarea>
	</body>
</html>

<SCRIPT type="text/javascript">
		function search1(){
			//checkTotalQueryDate(frm.beginDate.value,frm.endDate.value);
			frm.submit();
		}
		</SCRIPT>