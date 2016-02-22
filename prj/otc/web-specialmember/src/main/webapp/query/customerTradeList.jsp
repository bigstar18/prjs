
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>
<html xmlns:MEBS>
	<head>
		<title>�ͻ�ƽ�ֵ���ѯ</title>
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
								action="${basePath}/query/queryCustomerTradeSearch/list.action?sortName=primary.holdNo"
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
															��ѯ��Χ:&nbsp;
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
														<td class="table3_td_1" align="left">
															��Ʒ����:&nbsp;
															<label>
																<input type="text" name="${GNNT_}commodityName[like]"
																	id="commodityName"
																	value="${oldParams['commodityName[like]'] }"
																	class="input_textmin" />
															</label>
														</td>
														<td class="table3_td_1" align="left">
															��ʼ����:&nbsp;
															<label>
																<input type="text" style="width: 100px" id="beginDate"
																	class="wdate" maxlength="10"
																	name="${GNNT_}atClearDate[>=][date]"
																	value='${oldParams["atClearDate[>=][date]"]}'
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															</label>
														</td>
														<td class="table3_td_1" align="left">
															��������:&nbsp;
															<label>
																<input type="text" style="width: 100px" id="endDate"
																	class="wdate" maxlength="10"
																	name="${GNNT_}atClearDate[<=][date]"
																	value='${oldParams["atClearDate[<=][date]"]}'
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															</label>
														</td>
														<script type="text/javascript">
															frm.flag.value = '${oldParams["build_flag[=][int]"] }';
														</script>
													</tr>
													<tr>
														<td class="table3_td_1" align="left">
															����/����:
															<label>
																<span class="right_03zi"> <select
																		name="${GNNT_}build_flag[=][int]" size="1" id="flag"
																		style="width: 100">
																		<option value="">
																			��ѡ��
																		</option>
																		<option value="1">
																			����
																		</option>
																		<option value="2">
																			����
																		</option>
																	</select> </span>
															</label>
														</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" onClick="search1()">
																��ѯ
															</button>
															&nbsp;&nbsp;
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
											var="customerTrade"
											action="${basePath}/query/queryCustomerTradeSearch/list.action"
											title="" minHeight="345" listWidth="250%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="�����б�.csv"  
											style="table-layout:fixed">
											<ec:row>
												<ec:column width="3%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="holdNo[like]" title="�ֲֵ���"
													width="5%" style="text-align:left; "
													value="${customerHoldSearch.holdNo}" />
												<ec:column property="customer_TradeId[like]" title="�����˺�"
													width="7%" style="text-align:left; "
													value="${customerHoldSearch.customer_TradeId}" />
												<ec:column property="traderName[like]" title="�ͻ�����"
													width="6%" style="text-align:left; "
													value="${customerHoldSearch.traderName}" />
												<ec:column property="commodityId[like]" title="��Ʒ����"
													width="7%" style="text-align:left; "
													value="${customerHoldSearch.commodityId}" />
												<ec:column property="commodityName[like]" title="��Ʒ����"
													width="7%" style="text-align:left; "
													value="${customerHoldSearch.commodityName}" />
												<ec:column property="memberNo[like]" title="��Ա���"
													width="6%" style="text-align:left; "
													value="${customerHoldSearch.memberNo}" />
												<ec:column property="traderId[like]" title="����Ա" width="4%"
													style="text-align:left; "
													value="${customerHoldSearch.traderId}" />
												<ec:column property="tradeType[=][String]" title="����" width="5%"
													style="text-align:left; " editTemplate="esc_tradeType">
													<c:set var="typeKey">
														<c:out value="${customerHoldSearch.tradeType}"></c:out>
													</c:set>
		  											${tradeTypeMap[typeKey]}
												</ec:column>
												<ec:column property="holdqty[=][int]" title="����" width="5%"
													style="text-align:left; "
													value="${customerHoldSearch.holdQty}" />
												<ec:column property="bs_flag[=][int]" title="������־" width="4%"
													style="text-align:left; " editTemplate="esc_bsFlag">
													<c:set var="typeKey">
														<c:out value="${customerHoldSearch.bs_flag}"></c:out>
													</c:set>
		  											${bsFlagMap[typeKey]}
												</ec:column>
												<ec:column property="holdTime[=][date]" title="����ʱ��" width="9%"
													style="text-align:left; "
													value="${datefn:formatdate(customerHoldSearch.holdTime)}" />
												<ec:column property="openPrice[=][double]" title="���ּ�" width="5%"
													style="text-align:right; ">
													<fmt:formatNumber value="${customerHoldSearch.openPrice}"
														pattern="0.00" />
												</ec:column>
												<ec:column property="holdPrice[=][double]" title="�ֲּ�" width="5%"
													style="text-align:right; ">
													<fmt:formatNumber value="${customerHoldSearch.holdPrice}"
														pattern="0.00" />
												</ec:column>
												<ec:column property="price[=][double]" title="�м��" width="5%"
													style="text-align:right; ">
													<fmt:formatNumber value="${customerHoldSearch.price}"
														pattern="0.00" />
												</ec:column>
												<ec:column property="floatingLoss[=][double]" title="ӯ��" width="5%"
													style="text-align:right; ">
													<fmt:formatNumber
														value="${customerHoldSearch.floatingLoss}" pattern="0.00" />
												</ec:column>
												<ec:column property="tradeFee[=][double]" title="������" width="6%"
													style="text-align:right; ">
													<fmt:formatNumber value="${customerHoldSearch.tradeFee}"
														pattern="0.00" />
												</ec:column>
												<ec:column property="delayFee[=][double]" title="���ڷ�" width="6%"
													style="text-align:right; ">
													<fmt:formatNumber value="${customerHoldSearch.delayFee}"
														pattern="0.00" />
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
		<textarea id="esc_tradeType" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="buildTradeType[=]">
			<ec:options items="tradeTypeMap" />
		</select>
	    </textarea>


		<!-- �༭�͹�����ʹ�õĽ��ַ����־ģ�� -->
		<textarea id="esc_bsFlag" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="build_flag[=]">
			<ec:options items="bsFlagMap" />
		</select>
	    </textarea>

		<!-- �༭�͹�����ʹ�õ�ƽ�ַ����־ģ�� -->
		<textarea id="esc_de_bsFlag" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="de_build_flag[=]">
			<ec:options items="bsFlagMap" />
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
			checkTotalQueryDate(frm.beginDate.value,frm.endDate.value);
			changeOn();
		}
		function init(queryType){
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
		
		</SCRIPT>