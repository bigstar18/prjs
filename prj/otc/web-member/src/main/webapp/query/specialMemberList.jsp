
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>
<html xmlns:MEBS>
	<head>
		<title>�ر��Ա��ѯ</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
		<IMPORT namespace="MEBS"
			implementation="${basePath}/common/jslib/calendar.htc">
	</head>
	<body onload="init('${queryType}')">
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
							<form name="frm"
								action="${basePath}/query/querySpecialMemberSearch/list.action?sortName=primary.specialMemberName"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_widthmax">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0" style="table-layout:fixed">
													<tr>
														<td class="table3_td_1" align="left">
															��ʼ����:&nbsp;
															<label>
																<input type="text" style="width: 100px" id="beginDate" class="wdate"
																	maxlength="10" name="${GNNT_}tradeDate[>=][date]"
																	value='${oldParams["tradeDate[>=][date]"]}'
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															</label>
														</td>
														<td class="table3_td_1" align="left">
															��������:&nbsp;
															<label>
																<input type="text" style="width: 100px" id="endDate" class="wdate"
																	maxlength="10" name="${GNNT_}tradeDate[<=][date]"
																	value='${oldParams["tradeDate[<=][date]"]}'
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															</label>
														</td>
														<td class="table3_td_1" align="left">
															�ر��Ա:&nbsp;
															<label>
																<span class="right_03zi"> <select
																		name="${GNNT_}s_memberNo[like]" size="1"
																		id="specialMemberName" style="width: 100">
																		<option value="">
																			��ѡ��
																		</option>
																		<c:forEach items="${specialMemberList}" var="list">
																			<option value="${list.s_memberNo }">
																				${list.name }
																			</option>
																		</c:forEach>
																	</select> </span>
															</label>
														</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" onClick="search1()">
																��ѯ
															</button>&nbsp;&nbsp;
															<button class="btn_cz" onClick="myReset()">
																����
															</button>
														</td>
														<script type="text/javascript">
frm.specialMemberName.value = '${oldParams["s_memberNo[like]"] }';
</script>
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
											var="specialMember"
											action="${basePath}/query/querySpecialMemberSearch/list.action"
											title="" minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="�����б�.csv"   style="table-layout:fixed">
											<ec:row ondblclick="">
												<ec:column width="4%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												
												<ec:column property="tradeDate[=][date]" title="����ʱ��"
													width="15%" style="text-align:left; "
													value="${datefn:formatdate(specialMember.tradeDate)}" />
												<ec:column property="s_memberNo[like]" title="�ر��Ա�˺�"
													width="12%" style="text-align:left; "
													value="${specialMember.s_memberNo}" />
												<ec:column property="specialMemberName[like]" title="�ر��Ա����"
													width="12%" style="text-align:left; "
													value="${specialMember.specialMemberName}" />
												<ec:column property="riskMargin[=][double]" title="���ձ�֤��"
													width="12%" style="text-align:right; ">
													<fmt:formatNumber value="${specialMember.riskMargin}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="specialLossPeak[=][double]" title="�ر��Ա����"
													width="12%" style="text-align:right; ">
													<fmt:formatNumber value="${specialMember.specialLossPeak}"
														pattern="#,##0.00" />
												</ec:column>

												<ec:column property="specialLossPeakPro[[=][double]" title="ӯ������"
													width="14%" style="text-align:right; ">
													<fmt:formatNumber
														value="${specialMember.specialLossPeakPro}" pattern="0.00" />
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
	</body>
</html>

<SCRIPT type="text/javascript">
	function search1(){
		checkTotalQueryDate(frm.beginDate.value,frm.endDate.value);
	}
</SCRIPT>