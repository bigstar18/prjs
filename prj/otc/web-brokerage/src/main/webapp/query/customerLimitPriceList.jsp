
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>
<html xmlns:MEBS>
	<head>
		<title>�ͻ�ָ�۵���ѯ</title>
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
								action="${basePath}/query/queryCustomerLimitPriceSearch/list.action?sortName=primary.orderno&sortOrder=true"
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
														
														<td>
															&nbsp;
														</td>
														<tr>
															
															<td class="table3_td_1tjcx" align="left">
																ί�е���:
																<label>
																	<input type="text"
																		name="gnnt_primary.orderno[=][int]"
																		id="operateId"
																		value="${oldParams['primary.orderno[=][int]'] }"
																		class="input_text" />
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
											var="customerLimitPrice"
											action="${basePath}/query/queryCustomerLimitPriceSearch/list.action"
											title="" minHeight="345" listWidth="145%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" csvFileName="�ͻ�ί�е���ѯ.csv"
											style="table-layout:fixed">
											<ec:row ondblclick="">
												<ec:column width="2%" property="_0" title="���"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<c:if test="${queryType=='H'}">
													<ec:column property="clearDate[=][date]" title="��������"
														width="4%" style="text-align:left; " ellipsis="true">
														<fmt:formatDate value="${customerLimitPrice.clearDate}"
															pattern="yyyy-MM-dd" />
													</ec:column>
												</c:if>
												<ec:column property="orderTime[=][date]" title="ί��ʱ��"
													width="6%" style="text-align:left; "
													value="${datefn:formatdate(customerLimitPrice.orderTime)}"
													ellipsis="true" />
												<ec:column property="customerNo[like]" title="�����˺�"
													width="6%" style="text-align:left; "
													value="${customerLimitPrice.customerNo}" ellipsis="true" />
												<ec:column property="customerName[like]" title="�ͻ�����"
													width="4%" style="text-align:left; "
													value="${customerLimitPrice.customerName}" ellipsis="true" />
												<ec:column property="orderno[=][int]" title="ί�е���" width="3%"
													style="text-align:left; "
													value="${customerLimitPrice.orderno}" ellipsis="true" />
												<ec:column property="holdno[=][int]" title="�ֲֵ���" width="3%"
													style="text-align:left; "
													value="${customerLimitPrice.holdno}" ellipsis="true" />
												<ec:column property="ordertype[like]" title="ί������"
													width="3%" style="text-align:left; " editTemplate="esc_ordertype"
													ellipsis="true" >
													<c:set var="ordertypeKey">
														<c:out value="${customerLimitPrice.ordertype}"></c:out>
													</c:set>
		  											${firstMap[ordertypeKey]}
		  										</ec:column>
												<ec:column property="commodityName[like]" title="��Ʒ����"
													width="5%" style="text-align:left; "
													value="${customerLimitPrice.commodityName}" ellipsis="true" />
												<ec:column property="bs_flag[=][int]" title="��������"
													width="3%" style="text-align:left; "
													editTemplate="esc_bsFlag" ellipsis="true">
													<c:set var="typeKey">
														<c:out value="${customerLimitPrice.bs_flag}"></c:out>
													</c:set>
		  											${secondMap[typeKey]}
												</ec:column>
												<ec:column property="oc_flag[=][String]" title="��ƽ��"
													width="3%" style="text-align:left; "
													editTemplate="esc_mold" ellipsis="true">
													<c:set var="ocKey">
														<c:out value="${customerLimitPrice.oc_flag}"></c:out>
													</c:set>
		  											${thirdMap[ocKey]}
												</ec:column>
												<ec:column property="stopLossPrice[=][double]" title="ֹ���"
													width="4%" style="text-align:right; ">
													<fmt:formatNumber
														value="${customerLimitPrice.stopLossPrice}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="stopProfitPrice[=][double]" title="ֹӯ��"
													width="4%" style="text-align:right; ">
													<fmt:formatNumber
														value="${customerLimitPrice.stopProfitPrice}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="qty[=][int]" title="����" width="3%"
													style="text-align:right; ">
													<fmt:formatNumber value="${customerLimitPrice.qty}"
														pattern="#,##0" />
												</ec:column>
												<ec:column property="price[=][double]" title="ί�м�"
													width="4%" style="text-align:right; ">
													<fmt:formatNumber value="${customerLimitPrice.price}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="tradeprice[=][double]" title="�ɽ���"
													width="4%" style="text-align:right; ">
													<fmt:formatNumber value="${customerLimitPrice.tradeprice}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="status[=][int]" title="ί��״̬"
													width="3%" style="text-align:left; "
													editTemplate="esc_processState" ellipsis="true">
													<c:set var="typeKey">
														<c:out value="${customerLimitPrice.status}"></c:out>
													</c:set>
		  											${fourthMap[typeKey]}
		  											</ec:column>
												<ec:column property="withdrawtime[=][date]" title="����ʱ��"
													width="6%" style="text-align:left; "
													value="${datefn:formatdate(customerLimitPrice.withdrawtime)}"
													ellipsis="true" />
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

<!-- �༭�͹�����ʹ�õ�ί������ģ�� -->
		<textarea id="esc_ordertype" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="ordertype[like]">
			<ec:options items="firstMap" />
		</select>
	    </textarea>

		<!-- �༭�͹�����ʹ�õ�������־ģ�� -->
		<textarea id="esc_bsFlag" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="bs_Flag[like]">
			<ec:options items="secondMap" />
		</select>
	    </textarea>

		<!-- �༭�͹�����ʹ�õ�����ģ�� -->
		<textarea id="esc_mold" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="mold[like]">
			<ec:options items="thirdMap" />
		</select>
	    </textarea>

		<!-- �༭�͹�����ʹ�õĴ���״̬ģ�� -->
		<textarea id="esc_processState" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="tradeDirection[=][int]">
			<ec:options items="fourthMap" />
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
			  //ec.action="${basePath}/query/queryCustomerLimitPriceSearch/list.action?queryType=D&sortName=limitPriceId";
			 // frm.action="${basePath}/query/queryCustomerLimitPriceSearch/list.action?queryType=D&sortName=limitPriceId";
			}
			else if(value=='H')
			{
			   frm.beginDate.disabled=false;
			   frm.endDate.disabled=false;
			   frm.beginDate.style.backgroundColor="#ffffff";
frm.endDate.style.backgroundColor="#ffffff";
			   frm.queryType.value='H';
			    //ec.action="${basePath}/query/queryCustomerLimitPriceSearch/list.action?queryType=H&sortName=limitPriceId";
			   //  frm.action="${basePath}/query/queryCustomerLimitPriceSearch/list.action?queryType=H&sortName=limitPriceId";
			}
		}
			function clickText() {
	var url = "${basePath}/broke/memberInfoTree/forTree.action";
	ecsideDialog(url, window, 400, 570);
}
		</SCRIPT>