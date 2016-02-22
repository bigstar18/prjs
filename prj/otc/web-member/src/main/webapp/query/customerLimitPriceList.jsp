
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
										<td class="table2_td_widthmid">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0" style="table-layout: fixed">
													<tr>
														<input type="hidden" name="${GNNT_}isRelated"
															id="isRelated" value="${oldParams['isRelated']}"
															class="input_text">
														<input type="hidden" name="${GNNT_}memberIds" id=memberIds
															value="${oldParams['memberIds']}" class="input_text">
														<input type="hidden" id="brokerageIds"
															name="${GNNT_}brokerageIds"
															value="${oldParams['brokerageIds'] }" class="input_text" />
														<input type="hidden" id="organizationIds"
															name="${GNNT_}organizationIds"
															value="${oldParams['organizationIds'] }"
															class="input_text" />
														<input type="hidden" id="managerIds"
															name="${GNNT_}managerIds"
															value="${oldParams['managerIds'] }" class="input_text" />
														<td class="table3_td_1mid" align="left">
															�ͻ�����:
															<input type="text" id="selectIds"
																name="${GNNT_}selectIds"
																value="${oldParams['selectIds'] }" class="input_text"
																readonly='readonly' />
															<a href="javascript:clickText();"><img
																	align="absmiddle" src="<%=skinPath%>/cssimg/kh.gif">
														</td>
														<td class="table3_td_1min" align="left">
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
														<td class="table3_td_1tjcx">
															ί�е���:
															<label>
																<input type="text"
																	name="${GNNT_}primary.orderno[=][int]" id="operateId"
																	value="${oldParams['primary.orderno[=][int]'] }"
																	class="input_textmin" />
															</label>
														</td>
													</tr>
													<tr>
														<td class="table3_td_1mid">
															�����˺�:
															<label>
																<input type="text"
																	name="${GNNT_}primary.customerNo[like]" id="operateId"
																	value="${oldParams['primary.customerNo[like]'] }"
																	class="input_text" />
															</label>
														</td>
														<td class="table3_td_1min">
															�ͻ�����:
															<label>
																<input type="text"
																	name="${GNNT_}primary.customerName[like]"
																	id="operateId"
																	value="${oldParams['primary.customerName[like]'] }"
																	class="input_textmin" />
															</label>
														</td>

														<td class="table3_td_1tjcx" align="left">
															��Ʒ����:
															<span class="right_03zi"><select id="commodityId"
																	name="${GNNT_}primary.commodityId[=][String]"
																	class="input_textmin">
																	<option value="">
																		��ѡ��
																	</option>
																	<c:forEach items="${commodityList}" var="commodit">
																		<option value="${commodit.id}">
																			${commodit.name }
																		</option>
																	</c:forEach>
																</select> </span>
															<script type="text/javascript">
															frm.commodityId.value = '${oldParams["primary.commodityId[=][String]"] }';
															</script>
														</td>
														<tr>


															<td class="table3_td_1mid" align="left">
																��ʼ����:
																<label>
																	<input type="text" style="width: 100px" id="beginDate"
																		class="wdate" maxlength="10"
																		name="${GNNT_}clearDate[>=][date]"
																		value='${oldParams["clearDate[>=][date]"]}'
																		onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
																</label>
															</td>

															<td class="table3_td_1min" align="left">
																��������:
																<label>
																	<input type="text" style="width: 100px" id="endDate"
																		class="wdate" maxlength="10"
																		name="${GNNT_}clearDate[<=][date]"
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
											var="customerLimitPrice"
											action="${basePath}/query/queryCustomerLimitPriceSearch/list.action"
											title="" minHeight="345" listWidth="170%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" csvFileName="�����б�.csv"
											style="table-layout:fixed">
											<ec:row ondblclick="">
												<ec:column width="2%" property="_0" title="���"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<c:if test="${queryType=='H'}">
													<ec:column property="clearDate[=][date]" title="��������"
														width="4%" style="text-align:left; ">
														<fmt:formatDate value="${customerLimitPrice.clearDate}"
															pattern="yyyy-MM-dd" />
													</ec:column>
												</c:if>
												<ec:column property="orderTime[=][date]" title="ί��ʱ��"
													width="6%" style="text-align:left; "
													value="${datefn:formatdate(customerLimitPrice.orderTime)}" />
												<ec:column property="organizationnname[like]" title="����"
													width="4%" style="text-align:left; "
													value="${customerLimitPrice.organizationnname}" />
												<ec:column property="brokeragename[like]" title="�Ӽ�"
													width="4%" style="text-align:left; "
													value="${customerLimitPrice.brokeragename}" />
												<ec:column property="customerNo[like]" title="�����˺�"
													width="6%" style="text-align:left; "
													value="${customerLimitPrice.customerNo}" />
												<ec:column property="customerName[like]" title="�ͻ�����"
													width="4%" style="text-align:left; "
													value="${customerLimitPrice.customerName}" />
												<ec:column property="orderno[=][int]" title="ί�е���"
													width="3%" style="text-align:left; "
													value="${customerLimitPrice.orderno}" />
												<ec:column property="holdno[=][int]" title="�ֲֵ���" width="3%"
													style="text-align:left; "
													value="${customerLimitPrice.holdno}" />
												<ec:column property="ordertype[like]" title="ί������"
													width="4%" style="text-align:left; "
													editTemplate="esc_ordertype">
													<c:set var="ordertypeKey">
														<c:out value="${customerLimitPrice.ordertype}"></c:out>
													</c:set>
		  											${firstMap[ordertypeKey]}
		  										</ec:column>
												<ec:column property="commodityName[like]" title="��Ʒ����"
													width="5%" style="text-align:left; "
													value="${customerLimitPrice.commodityName}" />
												<ec:column property="bs_flag[=][int]" title="��������"
													width="3%" style="text-align:left; "
													editTemplate="esc_bsFlag">
													<c:set var="typeKey">
														<c:out value="${customerLimitPrice.bs_flag}"></c:out>
													</c:set>
		  											${secondMap[typeKey]}
												</ec:column>
												<ec:column property="oc_flag[like]" title="��ƽ��" width="3%"
													style="text-align:left; " editTemplate="esc_mold">
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
												<ec:column property="status[=][int]" title="ί��״̬" width="3%"
													style="text-align:left; " editTemplate="esc_processState">
													<c:set var="typeKey">
														<c:out value="${customerLimitPrice.status}"></c:out>
													</c:set>
		  											${fourthMap[typeKey]}
		  											</ec:column>
												<ec:column property="withdrawtime[=][date]" title="����ʱ��"
													width="6%" style="text-align:left; "
													value="${datefn:formatdate(customerLimitPrice.withdrawtime)}" />
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
	var url = "${basePath}/broke/treeForMemberInfo/forTree.action";
	ecsideDialog(url, window,  500,650);

}
		</SCRIPT>