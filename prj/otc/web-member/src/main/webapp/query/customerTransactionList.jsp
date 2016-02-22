
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>
<html xmlns:MEBS>
	<head>
		<title>�ͻ��ɽ���ϸ��ѯ</title>
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
							<form name="frm" action="${basePath}/query/queryCustomerTransactionSearch/list.action" method="post">
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
															�ɽ�����:
															<label>
																<input type="text"
																	name="${GNNT_}primary.tradeNo[=][int]" id="operateId"
																	value="${oldParams['primary.tradeNo[=][int]'] }"
																	class="input_textmin" />
															</label>
														</td>
													</tr>
													<tr>
														<td class="table3_td_1mid">
															�����˺�:
															<label>
																<input type="text" name="${GNNT_}customerNo[like]"
																	id="operateId"
																	value="${oldParams['customerNo[like]'] }"
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
											var="customerTrans"
											action="${basePath}/query/queryCustomerTransactionSearch/list.action"
											title="" minHeight="345" listWidth="200%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" csvFileName="�����б�.csv"
											style="table-layout:fixed">
											<ec:row ondblclick="">
												<ec:column width="2%" property="_0" title="���"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<c:if test="${queryType=='H'}">
													<ec:column property="clearDate[=][date]" title="��������"
														width="4%" style="text-align:left; " ellipsis="true">
														<fmt:formatDate value="${customerTrans.clearDate}"
															pattern="yyyy-MM-dd" />
													</ec:column>
												</c:if>
												<ec:column property="organizationNo[like]" title="����"
													width="4%" style="text-align:left; "
													value="${customerTrans.organizationName}" ellipsis="true" />
												<ec:column property="brokerageNo[like]" title="�Ӽ�"
													width="4%" style="text-align:left; "
													value="${customerTrans.brokerageName}" ellipsis="true" />
												<ec:column property="customerNo[like]" title="�����˺�"
													width="6%" style="text-align:left; "
													value="${customerTrans.customerNo}" ellipsis="true" />
												<ec:column property="customerName[like]" title="�ͻ�����"
													width="4%" style="text-align:left; "
													value="${customerTrans.customerName}" ellipsis="true" />
												<ec:column property="oc_Flag[like]" title="��/ƽ��" width="3%"
													style="text-align:left; " editTemplate="esc_modmap"
													ellipsis="true">
													<c:set var="typeKey">
														<c:out value="${customerTrans.oc_Flag}"></c:out>
													</c:set>
		  											${firstMap[typeKey]}
												    
												   </ec:column>
												<ec:column property="tradeNo[=][int]" title="�ɽ�����"
													width="3%" style="text-align:left; "
													value="${customerTrans.tradeNo}" ellipsis="true" />
												<ec:column property="orderno[=][int]" title="ί�е���"
													width="3%" style="text-align:left; "
													value="${customerTrans.orderno}" ellipsis="true" />
												<ec:column property="holdNo[=][int]" title="�ֲֵ���" width="3%"
													style="text-align:left; " value="${customerTrans.holdNo}"
													ellipsis="true" />
												<ec:column property="tradeTime[=][timestamp]" title="�ɽ�ʱ��"
													width="6%" style="text-align:left; " ellipsis="true">
													<fmt:formatDate value="${customerTrans.tradeTime}"
														pattern="yyyy-MM-dd HH:mm:ss" />
												</ec:column>
												<ec:column property="commodityName[=][String]" title="��Ʒ"
													width="4%" style="text-align:left; "
													value="${customerTrans.commodityName}" ellipsis="true" />
												<ec:column property="quanTity[=][int]" title="�ɽ���"
													width="3%" style="text-align:right; ">
													<fmt:formatNumber value="${customerTrans.quanTity}"
														pattern="#,##0" />
												</ec:column>
												<ec:column property="tradefunds[=][double]" title="�ɽ����"
													width="6%" style="text-align:right; ">
													<fmt:formatNumber value="${customerTrans.tradefunds}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="bs_Flag[=][int]" title="��������"
													width="3%" style="text-align:left; "
													editTemplate="esc_bsFlag" ellipsis="true">
													<c:set var="typeKey">
														<c:out value="${customerTrans.bs_Flag}"></c:out>
													</c:set>
		  											${secondMap[typeKey]}
		  											</ec:column>
												<ec:column property="openprice[=][double]" title="���ּ�"
													width="4%" style="text-align:right; ">
													<fmt:formatNumber value="${customerTrans.openprice}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="holdprice[=][double]" title="�ֲּ�"
													width="4%" style="text-align:right; ">
													<fmt:formatNumber value="${customerTrans.holdprice}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="closeprice[=][double]" title="ƽ�ּ�"
													width="4%" style="text-align:right; " format="#,##0.00">
													<fmt:formatNumber value="${customerTrans.closeprice}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="close_pl[=][double]" title="ƽ��ӯ��"
													width="5%" format="#,##0.00" style="text-align:right; ">
													<fmt:formatNumber value="${customerTrans.close_pl}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="actualpl[=][double]" title="ʵ��ӯ��"
													width="5%" style="text-align:right; " format="#,##0.00">
													<fmt:formatNumber value="${customerTrans.actualpl}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="tradefee[=][double]" title="������"
													width="5%" style="text-align:right; " format="#,##0.00">
													<fmt:formatNumber value="${customerTrans.tradefee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="tradetype[=][int]" title="�ɽ�����"
													width="3%" editTemplate="esc_tradeMap"
													style="text-align:left; " ellipsis="true">
													<c:set var="key">
														<c:out value="${customerTrans.tradetype}"></c:out>
													</c:set>
													${thirdMap[key]}
												</ec:column>
												<ec:column property="operatetype[=][int]" title="��������"
													width="3%" editTemplate="esc_operateQueryMap"
													style="text-align:left; " ellipsis="true">
													<c:set var="key">
														<c:out value="${customerTrans.operatetype}"></c:out>
													</c:set>
													${fourthMap[key]}
												</ec:column>
												<ec:column property="traderId[=][String]" title="������"
													width="6%" style="text-align:right;"
													value="${customerTrans.traderId}" ellipsis="true">
												</ec:column>
											</ec:row>
											<c:if test="${ifHas eq 1}">		
												<ec:extendrow>
													<td>
													          �ϼ�:
													</td>
													<c:if test="${queryType=='H'}">
														<td>&nbsp;</td>
													</c:if>
													<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
													<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
													<td align="right" style="font-weight:bold">${quanTityAll}</td>
													<td align="right" style="font-weight:bold">${tradefundsAll}</td>
													<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
													<td align="right" style="font-weight:bold">${close_plAll}</td>													
													<td align="right" style="font-weight:bold">${actualplAll}</td>													
													<td align="right" style="font-weight:bold">${tradefeeAll}</td>
													<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
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
		<!-- �༭�͹�����ʹ�õĽ�ƽ��ģ�� -->
		<textarea id="esc_modmap" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="oc_Flag[like]">
			<ec:options items="firstMap" />
		</select>
	    </textarea>

		<!-- �༭�͹�����ʹ�õĽ��ַ����־ģ�� -->
		<textarea id="esc_bsFlag" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="build_flag[=]">
			<ec:options items="secondMap" />
		</select>
	    </textarea>
		<!-- �༭�͹�����ʹ�õ�ƽ�����ͱ�־ģ�� -->
		<textarea id="esc_tradeMap" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="tradeType[=][String]">
			<ec:options items="thirdMap" />
		</select>
	    </textarea>
	     <!-- �༭�͹�����ʹ�õ�ƽ�����ͱ�־ģ�� -->
		<textarea id="esc_operateQueryMap" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="operatetype[=][String]">
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
			  //ec.action="${basePath}/query/queryCustomerTradeSearch/list.action?queryType=D";
			  //frm.action="${basePath}/query/queryCustomerTradeSearch/list.action?queryType=D";
			}
			else if(value=='H')
			{
			   frm.beginDate.disabled=false;
			   frm.endDate.disabled=false;
			   frm.beginDate.style.backgroundColor="#ffffff";
				frm.endDate.style.backgroundColor="#ffffff";
			   frm.queryType.value='H';
			    //ec.action="${basePath}/query/queryCustomerTradeSearch/list.action?queryType=H";
			    //frm.action="${basePath}/query/queryCustomerTradeSearch/list.action?queryType=H";
			}
		}
function clickText() {
	var url = "${basePath}/broke/treeForMemberInfo/forTree.action";
	ecsideDialog(url, window,  500,650);

}
		</SCRIPT>