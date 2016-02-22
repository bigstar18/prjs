
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>
<html xmlns:MEBS>
	<head>
		<title>客户成交明细查询</title>
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
							<form name="frm" method="post" action="${basePath}/query/queryCustomerTransactionSearch/list.action?sortName=primary.tradeNo&sortOrder=true">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_widthmax">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0" style="table-layout: fixed">
													
													<tr>
														<td class="table3_td_1tjcx" align="left">
															交易账号:
															<label>
																<input type="text" name="gnnt_customerNo[=][String]"
																	id="operateId"
																	value="${oldParams['customerNo[=][String]'] }"
																	class="input_text" />
															</label>
														</td>
														<td class="table3_td_1tjcx_1" align="left">
															客户名称:
															<label>
																<input type="text"
																	name="gnnt_primary.customerName[=][String]"
																	id="operateId"
																	value="${oldParams['primary.customerName[=][String]'] }"
																	class="input_textmin" />
															</label>
														</td>

														<td class="table3_td_1tjcx_1" align="left">
															查询范围:
															<label>
																<select name="queryType" size="1" id="queryType"
																	style="width: 100" onchange="changeOn()">
																	<option value="D">
																		当前
																	</option>
																	<option value="H">
																		历史
																	</option>
																</select>
															</label>
														</td>
														<td>
															&nbsp;
														</td>
														<tr>
															<td class="table3_td_1tjcx" align="left">
																成交单号:
																<label>
																	<input type="text"
																		name="gnnt_primary.tradeNo[=][int]" id="operateId"
																		value="${oldParams['primary.tradeNo[=][int]'] }"
																		class="input_text" />
																</label>
															</td>

															<td class="table3_td_1tjcx_1" align="left">
																开始日期:
																<label>
																	<input type="text" style="width: 100px" id="beginDate"
																		class="wdate" maxlength="10"
																		name="gnnt_clearDate[>=][date]"
																		value='${oldParams["clearDate[>=][date]"]}'
																		onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
																</label>
															</td>

															<td class="table3_td_1tjcx_1" align="left">
															结束日期:
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
																	查询
																</button>&nbsp;
																<button class="btn_cz" onClick="myReset()">
																	重置
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
											action="${basePath}/query/queryCustomerTransactionSearch/list.action?sortName=primary.tradeNo&sortOrder=true"
											title="" minHeight="345" listWidth="180%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" csvFileName="客户成交查询.csv"
											style="table-layout:fixed">
											<ec:row ondblclick="">
												<ec:column width="3%" property="_0" title="序号"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<c:if test="${queryType=='H'}">
													<ec:column property="clearDate[=][date]" title="结算日期"
														width="4%" style="text-align:left; " ellipsis="true">
														<fmt:formatDate value="${customerTrans.clearDate}"
															pattern="yyyy-MM-dd" />
													</ec:column>
												</c:if>
												<ec:column property="customerNo[like]" title="交易账号"
													width="6%" style="text-align:left; "
													value="${customerTrans.customerNo}" ellipsis="true" />
												<ec:column property="customerName[like]" title="客户名称"
													width="4%" style="text-align:left; "
													value="${customerTrans.customerName}" ellipsis="true" />
												<ec:column property="oc_Flag[like]" title="建/平仓" width="3%"
													style="text-align:left; " editTemplate="esc_modmap"
													ellipsis="true">
													<c:set var="typeKey">
														<c:out value="${customerTrans.oc_Flag}"></c:out>
													</c:set>
		  											${firstMap[typeKey]}
												    
												   </ec:column>
												  <ec:column property="tradeNo[=][int]" title="成交单号" width="3%"
													style="text-align:left; " value="${customerTrans.tradeNo}" ellipsis="true"/>
												 <ec:column property="orderno[=][int]" title="委托单号" width="3%"
													style="text-align:left; " value="${customerTrans.orderno}" ellipsis="true"/>
												<ec:column property="holdNo[=][int]" title="持仓单号" width="3%"
													style="text-align:left; " value="${customerTrans.holdNo}"
													ellipsis="true" />
												<ec:column property="tradeTime[=][date]" title="成交时间"
													width="6%" style="text-align:left; " ellipsis="true">
													<fmt:formatDate value="${customerTrans.tradeTime}"
														pattern="yyyy-MM-dd HH:mm:ss" />
												</ec:column>
												<ec:column property="commodityName[=][String]" title="商品"
													width="4%" style="text-align:left; "
													value="${customerTrans.commodityName}" ellipsis="true" />
												<ec:column property="quanTity[=][int]" title="成交量"
													width="3%" style="text-align:right; " ellipsis="true">
													<fmt:formatNumber value="${customerTrans.quanTity}"
														pattern="#,##0" />
												</ec:column>
												<ec:column property="tradefunds[=][double]" title="成交金额"
													width="5%" style="text-align:right; ">
													<fmt:formatNumber value="${customerTrans.tradefunds}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="bs_Flag[=][int]" title="买卖方向"
													width="3%" style="text-align:left; "
													editTemplate="esc_bsFlag" ellipsis="true">
													<c:set var="typeKey">
														<c:out value="${customerTrans.bs_Flag}"></c:out>
													</c:set>
		  											${secondMap[typeKey]}
		  											</ec:column>
												<ec:column property="openprice[=][double]" title="建仓价"
													width="4%" style="text-align:right; ">
													<fmt:formatNumber value="${customerTrans.openprice}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="holdprice[=][double]" title="持仓价"
													width="4%" style="text-align:right; ">
													<fmt:formatNumber value="${customerTrans.holdprice}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="closeprice[=][double]" title="平仓价"
													width="4%" style="text-align:right; ">
													<fmt:formatNumber value="${customerTrans.closeprice}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="close_pl[=][double]" title="平仓盈亏"
													width="4%" style="text-align:right; ">
													<fmt:formatNumber value="${customerTrans.close_pl}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="actualpl[=][double]" title="实际盈亏"
													width="4%" style="text-align:right; ">
													<fmt:formatNumber value="${customerTrans.actualpl}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="tradefee[=][double]" title="手续费"
													width="4%" style="text-align:right; ">
													<fmt:formatNumber value="${customerTrans.tradefee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="tradetype[=][int]" title="成交类型"
													width="3%" editTemplate="esc_tradeMap"
													style="text-align:left; " ellipsis="true">
													<c:set var="key">
														<c:out value="${customerTrans.tradetype}"></c:out>
													</c:set>
													${thirdMap[key]}
												</ec:column>
												<ec:column property="operatetype[=][int]" title="操作类型"
													width="3%" editTemplate="esc_operateQueryMap"
													style="text-align:left; " ellipsis="true">
													<c:set var="key">
														<c:out value="${customerTrans.operatetype}"></c:out>
													</c:set>
													${fourthMap[key]}
												</ec:column>
												<ec:column property="traderId[=][String]" title="操作人"
													width="6%" style="text-align:right;"
													value="${customerTrans.traderId}" ellipsis="true">
												</ec:column>
											</ec:row>
											<c:if test="${ifHas eq 1}">		
												<ec:extendrow>
													<td>
													          合计:
													</td>
													<c:if test="${queryType=='H'}">
														<td>&nbsp;</td>
													</c:if>
													<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
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
		<!-- 编辑和过滤所使用的 通用的文本框模板 -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value=""
				onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="" />
		</textarea>
		<!-- 编辑和过滤所使用的建平仓模板 -->
		<textarea id="esc_modmap" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="oc_Flag[like]">
			<ec:options items="firstMap" />
		</select>
	    </textarea>

		<!-- 编辑和过滤所使用的建仓方向标志模板 -->
		<textarea id="esc_bsFlag" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="build_flag[=]">
			<ec:options items="secondMap" />
		</select>
	    </textarea>
		<!-- 编辑和过滤所使用的平仓类型标志模板 -->
		<textarea id="esc_tradeMap" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="tradeType[=][String]">
			<ec:options items="thirdMap" />
		</select>
	    </textarea>
	      <!-- 编辑和过滤所使用的平仓类型标志模板 -->
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
	var url = "${basePath}/broke/memberInfoTree/forTree.action";
	ecsideDialog(url, window, 400, 570);
}
		</SCRIPT>