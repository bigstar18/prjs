
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>
<html xmlns:MEBS>
	<head>
		<title>客户持仓单查询</title>
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
								action="${basePath}/query/queryCustomerHoldSearch/list.action?sortName=primary.holdNo&sortOrder=true"
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
															客户归属:
															<input type="text" id="selectIds"
																name="${GNNT_}selectIds"
																value="${oldParams['selectIds'] }" class="input_text"
																readonly='readonly' />
															<a href="javascript:clickText();"><img
																	align="absmiddle" src="<%=skinPath%>/cssimg/kh.gif">
														</td>
														<td class="table3_td_1min" align="left">
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
								                        <td class="table3_td_1tjcx">
																持仓单号:
																<label>
																	<input type="text"
																		name="${GNNT_}primary.holdNo[=][int]" id="operateId"
																		value="${oldParams['primary.holdNo[=][int]'] }"
																		class="input_textmin" />
																</label>
															</td>

													</tr>
													<tr>
														<td class="table3_td_1mid">
															交易账号:
															<label>
																<input type="text" name="${GNNT_}customerno[like]"
																	id="operateId"
																	value="${oldParams['customerno[like]'] }"
																	class="input_text" />
															</label>
														</td>
														<td class="table3_td_1min">
															客户名称:
															<label>
																<input type="text"
																	name="${GNNT_}primary.traderName[like]" id="operateId"
																	value="${oldParams['primary.traderName[like]'] }"
																	class="input_textmin" />
															</label>
														</td>

														<td class="table3_td_1tjcx" align="left">
															商品名称:
															<span class="right_03zi"><select id="commodityId"
																	name="${GNNT_}primary.commodityId[=][String]"
																	class="input_textmin">
																	<option value="">
																		请选择
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
																开始日期:
																<label>
																	<input type="text" style="width: 100px" id="beginDate"
																		class="wdate" maxlength="10"
																		name="${GNNT_}clearDate[>=][date]"
																		value='${oldParams["clearDate[>=][date]"]}'
																		onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
																</label>
															</td>

															<td class="table3_td_1min" align="left">
																结束日期:
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
											var="customerHoldSearch"
											action="${basePath}/query/queryCustomerHoldSearch/list.action?sortName=primary.holdNo&sortOrder=true"
											title="" minHeight="345" listWidth="180%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" csvFileName="导出列表.csv"
											style="table-layout:fixed">
											<ec:row ondblclick="">
												<ec:column width="3%" property="_0" title="序号"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<c:if test="${queryType=='H'}">
													<ec:column property="clearDate[=][date]" title="结算日期"
														width="4%" style="text-align:left; " ellipsis="true">
														<fmt:formatDate value="${customerHoldSearch.clearDate}"
															pattern="yyyy-MM-dd" />
													</ec:column>
												</c:if>
												
												<ec:column property="organizationName[like]" title="机构"
													width="4%" style="text-align:left; "
													value="${customerHoldSearch.organizationName}" ellipsis="true" />
												<ec:column property="brokerageName[like]" title="居间"
													width="4%" style="text-align:left; "
													value="${customerHoldSearch.brokerageName}" ellipsis="true" />
												<ec:column property="customerNo[like]" title="交易账号"
													width="6%" style="text-align:left; "
													value="${customerHoldSearch.customerNo}" ellipsis="true" />
												<ec:column property="traderName[like]" title="客户名称"
													width="4%" style="text-align:left; "
													value="${customerHoldSearch.traderName}" ellipsis="true" />
												<ec:column property="holdNo[=][int]" title="持仓单号" width="3%"
													style="text-align:center; "
													value="${customerHoldSearch.holdNo}" ellipsis="true" />
												<ec:column property="holdTime[=][timestemp]" title="建仓时间"
													width="6%" style="text-align:left; "
													value="${datefn:formatdate(customerHoldSearch.holdTime)}"
													ellipsis="true" />
												<ec:column property="commodityName[like]" title="商品"
													width="4%" style="text-align:left; "
													value="${customerHoldSearch.commodityName}" ellipsis="true" />
												<ec:column property="bs_flag[=][int]" title="买卖标志"
													width="3%" style="text-align:left; "
													editTemplate="esc_bsFlag" ellipsis="true">
													<c:set var="typeKey">
														<c:out value="${customerHoldSearch.bs_flag}"></c:out>
													</c:set>
		  											${firstMap[typeKey]}
												</ec:column>
												<ec:column property="holdQty[=][int]" title="持仓数量"
													width="3%" style="text-align:left; ">
													<fmt:formatNumber value="${customerHoldSearch.holdQty}"
														pattern="#,##0" />
												</ec:column>
												<ec:column property="openPrice[=][double]" title="建仓价"
													width="4%" style="text-align:left; ">
													<fmt:formatNumber value="${customerHoldSearch.openPrice}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="holdPrice[=][double]" title="持仓价"
													width="4%" style="text-align:left; ">
													<fmt:formatNumber value="${customerHoldSearch.holdPrice}"
														pattern="#,##0.00" />
												</ec:column>
												<c:if test="${queryType=='D'}">
													<ec:column property="price[=][double]" title="平仓价"
														width="4%" style="text-align:left; ">
														<fmt:formatNumber value="${customerHoldSearch.price}"
															pattern="#,##0.00" />
													</ec:column>
													<ec:column property="floatingLoss[=][double]" title="浮动盈亏"
														width="5%" style="text-align:left; ">
														<fmt:formatNumber
															value="${customerHoldSearch.floatingLoss}"
															pattern="#,##0.00" />
													</ec:column>
												</c:if>
												<c:if test="${queryType=='H'}">
													<ec:column property="price[=][double]" title="结算价"
														width="4%" style="text-align:left; ">
														<fmt:formatNumber value="${customerHoldSearch.price}"
															pattern="#,##0.00" />
													</ec:column>
													<ec:column property="floatingLoss[=][double]" title="盈亏"
														width="5%" style="text-align:left; ">
														<fmt:formatNumber
															value="${customerHoldSearch.floatingLoss}"
															pattern="#,##0.00" />
													</ec:column>
												</c:if>
												<ec:column property="holdMargin[=][double]" title="占用保证金"
													width="5%" style="text-align:left; ">
													<fmt:formatNumber value="${customerHoldSearch.holdMargin}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="tradeFee[=][double]" title="手续费"
													width="5%" style="text-align:left; ">
													<fmt:formatNumber value="${customerHoldSearch.tradeFee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="delayFee[=][double]" title="延期费"
													width="5%" style="text-align:left; ">
													<fmt:formatNumber value="${customerHoldSearch.delayFee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="mktdelayFee[=][double]" title="交易所存留延期费"
													width="5%" style="text-align:left; ">
													<fmt:formatNumber value="${customerHoldSearch.mktdelayFee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="memberdelayFee[=][double]" title="会员存留延期费"
													width="5%" style="text-align:left; ">
													<fmt:formatNumber value="${customerHoldSearch.memberdelayFee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="oparateName[like]" title="操作员"
													width="6%" style="text-align:left; "
													value="${customerHoldSearch.oparateName}" ellipsis="true" />
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
													<td align="right" style="font-weight:bold">${holdQtyAll }</td>
													<td>&nbsp;</td><td>&nbsp;</td>
													<c:if test="${queryType=='D'}">
														<td>&nbsp;</td>
														<td align="right" style="font-weight:bold">${floatingLossAll}</td>
													</c:if>
													<c:if test="${queryType=='H'}">														
														<td>&nbsp;</td>
														<td align="right" style="font-weight:bold">${floatingLossAll}</td>
													</c:if>
													<td align="right" style="font-weight:bold">${holdMarginAll}</td>
													<td align="right" style="font-weight:bold">${tradeFeeAll}</td>
													<td align="right" style="font-weight:bold">${delayFeeAll}</td>
													<td align="right" style="font-weight:bold">${mktdelayFeeAll}</td>
													<td align="right" style="font-weight:bold">${memberdelayFeeAll}</td>
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
		<!-- 编辑和过滤所使用的 通用的文本框模板 -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value=""
				onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="" />
		</textarea>

		<!-- 编辑和过滤所使用的买卖标志模板 -->
		<textarea id="esc_bsFlag" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="bs_Flag[like]">
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
			  //ec.action="${basePath}/query/queryCustomerHoldSearch/list.action?queryType=D";
			  //frm.action="${basePath}/query/queryCustomerHoldSearch/list.action?queryType=D";
			}
			else if(value=='H')
			{
			   frm.beginDate.disabled=false;
			   frm.endDate.disabled=false;
			   frm.beginDate.style.backgroundColor="#ffffff";
frm.endDate.style.backgroundColor="#ffffff";
			   frm.queryType.value='H';
			   // ec.action="${basePath}/query/queryCustomerHoldSearch/list.action?queryType=H";
			   // frm.action="${basePath}/query/queryCustomerHoldSearch/list.action?queryType=H";
			}
		}
function clickText() {
	var url = "${basePath}/broke/treeForMemberInfo/forTree.action";
	ecsideDialog(url, window, 500, 650);

}
		</SCRIPT>