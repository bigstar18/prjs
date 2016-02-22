
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>
<html xmlns:MEBS>
	<head>
		<title>�ͻ��ʽ���ˮ</title>
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
								action="${basePath}/query/queryCustomerFundFlowSearch/list.action?sortName=primary.fundFlowId&sortOrder=true"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_widthmax">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0" style="table-layout: fixed">
													<tr>
														<td class="table3_td_1tjcx" align="left">
															�����˺�:
															<label>
																<input type="text"
																	name="gnnt_primary.customerNo[=][String]"
																	id="operateId"
																	value="${oldParams['primary.customerNo[=][String]'] }"
																	class="input_text" />
															</label>
														</td>
														<td class="table3_td_1tjcx_1" align="left">
															�ͻ�����:
															<label>
																<input type="text"
																	name="gnnt_primary.customerName[=][String]"
																	id="operateId"
																	value="${oldParams['primary.customerName[=][String]'] }"
																	class="input_textmin" />
															</label>
														</td>

														<td class="table3_td_1tjcx_1" align="left">
															&nbsp;&nbsp;&nbsp;��ˮ��:
															<label>
																<input type="text"
																	name="gnnt_primary.fundFlowId[=][int]" id="operateId"
																	value="${oldParams['primary.fundFlowId[=][int]'] }"
																	class="input_textmin" />
															</label>
														</td>
														<td>
															&nbsp;
														</td>
														<tr>
															<td class="table3_td_1tjcx" align="left">
																��ѯ��Χ:
																<label>
																	<select name="queryType" size="1" id="queryType"
																		style="width: 100" onchange="changeOn()">
																		<option value="D">
																			��ǰ
																		</option>
																		<option value="H">
																			��ʷ
																		</option>
																	</select>
																</label>
															</td>

															<td class="table3_td_1tjcx_1" align="left">
																��ʼ����:
																<label>
																	<input type="text" style="width: 100px" id="beginDate"
																		class="wdate" maxlength="10"
																		name="gnnt_clearDate[>=][date]"
																		value='${oldParams["clearDate[>=][date]"]}'
																		onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
																</label>
															</td>

															<td class="table3_td_1tjcx_1" align="left">
																��������:
																<label>
																	<input type="text" style="width: 100px" id="endDate"
																		class="wdate" maxlength="10"
																		name="gnnt_clearDate[<=][date]"
																		value='${oldParams["clearDate[<=][date]"]}'
																		onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
																</label>
															</td>
															<td class="table3_td_anniutjcx" align="left">
																<button class="btn_sec" onClick="search1()">
																	��ѯ
																</button>
																&nbsp;
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
											var="customerFundFlow"
											action="${basePath}/query/queryCustomerFundFlowSearch/list.action?sortName=primary.fundFlowId&sortOrder=true"
											title="" minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" csvFileName="�ͻ��ʽ���ˮ��ѯ.csv"
											style="table-layout:fixed">
											<ec:row ondblclick="">
												<ec:column width="3%" property="_0" title="���"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<c:if test="${queryType=='H'}">
													<ec:column property="clearDate[=][date]" title="��������"
														width="5%" style="text-align:left; " ellipsis="true">
														<fmt:formatDate value="${customerFundFlow.clearDate}"
															pattern="yyyy-MM-dd" />
													</ec:column>
												</c:if>
												<ec:column property="customerNo[like]" title="�����˺�"
													width="8%" style="text-align:left; "
													value="${customerFundFlow.customerNo}" ellipsis="true" />
												<ec:column property="customerName[like]" title="�ͻ�����"
													width="8%" style="text-align:left; "
													value="${customerFundFlow.customerName}" ellipsis="true" />
												<ec:column property="fundFlowId[=][int]" title="��ˮ��"
													width="6%" style="text-align:left; "
													value="${customerFundFlow.fundFlowId}" ellipsis="true" />
												<ec:column property="oprcode[=][int]" title="ҵ������"
													width="9%" style="text-align:left; "
													editTemplate="esc_codeType" ellipsis="true">
													<c:set var="code">
														<c:out value="${customerFundFlow.oprcode}"></c:out>
													</c:set>
													${firstMap[code]}
												</ec:column>
												<ec:column property="createTime[=][date]" title="����ʱ��"
													width="9%" style="text-align:left; " ellipsis="true">
													<fmt:formatDate value="${customerFundFlow.createTime}"
														pattern="yyyy-MM-dd HH:mm:ss" />
												</ec:column>
												<ec:column property="changeAmount[=][double]" title="�䶯�ʽ�"
													width="9%" style="text-align:right; ">
													<fmt:formatNumber value="${customerFundFlow.changeAmount}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="afterAmount[=][double]" title="����ʽ�"
													width="9%" style="text-align:right; ">
													<fmt:formatNumber value="${customerFundFlow.afterAmount}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="contractNo[=][String]" title="��������"
													width="6%" style="text-align:left;"
													value="${customerFundFlow.contractNo}" ellipsis="true" />
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
			<ec:options items="firstMap" />
		</select>
	    </textarea>
	</body>
</html>

<SCRIPT type="text/javascript">
		function changeOn(){
			var todayHis=document.getElementById("queryType").value;
			change(todayHis);
		}
		function search1(){
			checkTotalQueryDate(frm.beginDate.value,frm.endDate.value,frm.queryType.value);
			changeOn();
		}
		 function init(queryType){
			  if(frm.beginDate.value=="" && frm.endDate.value==""){
				 frm.beginDate.value='${date}';
				 frm.endDate.value='${date}';
			 }
			 document.getElementById("beginDate").disabled=true;
			document.getElementById("endDate").disabled=true;
			change(queryType);
		}
		function change(value){
			if(value=='D')
			{
			  frm.beginDate.disabled=true;
			  frm.endDate.disabled=true;
			  frm.beginDate.style.backgroundColor="#d0d0d0";
			  frm.endDate.style.backgroundColor="#d0d0d0";
			   frm.queryType.value='D';
			 // ec.action="${basePath}/query/queryCustomerFundFlowSearch/list.action?queryType=D&sortName=primary.customer_tradeId";
			  //frm.action="${basePath}/query/queryCustomerFundFlowSearch/list.action?queryType=D&sortName=primary.customer_tradeId";
			}
			else if(value=='H')
			{
			   frm.beginDate.disabled=false;
			   frm.endDate.disabled=false;
			   frm.beginDate.style.backgroundColor="#ffffff";
			  frm.endDate.style.backgroundColor="#ffffff";
			   frm.queryType.value='H';
			  //  ec.action="${basePath}/query/queryCustomerFundFlowSearch/list.action?queryType=H&sortName=primary.customer_tradeId";
			  //  frm.action="${basePath}/query/queryCustomerFundFlowSearch/list.action?queryType=H&sortName=primary.customer_tradeId";
			}
		}
function clickText() {
	var url = "${basePath}/broke/memberInfoTree/forTree.action";
	ecsideDialog(url, window, 400, 570);
}
</SCRIPT>