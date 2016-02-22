
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>
<html xmlns:MEBS>
	<head>
		<title>客户指价单查询</title>
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
														<input type="hidden" name="${GNNT_}isRelated"
															id="isRelated" value="${oldParams['isRelated']}"
															class="input_text">
														<input type="hidden" name="${GNNT_}memberInfoIds"
															id="memberInfoIds" value="${oldParams['memberInfoIds']}"
															class="input_text">
														<input type="hidden" name="${GNNT_}organizationIds"
															id="organizationIds"
															value="${oldParams['organizationIds']}"
															class="input_text">
														<input type="hidden" name="${GNNT_}managerIds"
															id="managerIds" value="${oldParams['managerIds']}"
															class="input_text">
														<input type="hidden" name="${GNNT_}brokerageIds"
															id="brokerageIds" value="${oldParams['brokerageIds']}"
															class="input_text">
														<input type="hidden" name="tree" id="tree" value="${tree}"
															class="input_text">
														<td class="table3_td_1mid" align="left">
															客户归属:
															<input type="text" id="memberNames"
																name="${GNNT_}memberNames"
																value="${oldParams['memberNames']}" readonly=true
																class="input_text">
															<a href="javascript:clickText();"><img
																	align="absmiddle" src="<%=skinPath%>/cssimg/kh.gif">
															</a>
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
														<td class="table3_td_1tjcx" align="left">
															综合会员编号:
															<label>
																<input type="text" name="${GNNT_}memberNo[=][String]"
																	id="memberName"
																	value="${oldParams['memberNo[=][String]'] }"
																	class="input_textmin" />
															</label>
														</td>
														<td>
															&nbsp;
														</td>
													</tr>
													<tr>
														<td class="table3_td_1mid" align="left">
															交易账号:
															<label>
																<input type="text"
																	name="${GNNT_}primary.customerNo[like]"
																	id="operateId"
																	value="${oldParams['primary.customerNo[like]'] }"
																	class="input_text" />
															</label>
														</td>
														<td class="table3_td_1tjcx_1" align="left">
															客户名称:
															<label>
																<input type="text"
																	name="${GNNT_}primary.customerName[like]"
																	id="operateId"
																	value="${oldParams['primary.customerName[like]'] }"
																	class="input_textmin" />
															</label>
														</td>

														<td class="table3_td_1tjcx" align="left">
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;商品名称:
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
														<td>
															&nbsp;
														</td>
														<tr>
															<!-- 	<td class="table3_td_1tjcx" align="left">
																操作类型:&nbsp;
																<label>
																	<span class="right_03zi"> <select
																			name="${GNNT_}tradeType[=][int]" size="1"
																			id="tradeType" style="width: 100">
																			<option value="" selected="selected">
																				请选择
																			</option>
																			<option value="1">
																				正常交易
																			</option>
																			<option value="2">
																				对冲或强平
																			</option>
																		</select> </span>
																</label>
															</td>
															<script type="text/javascript">
frm.tradeType.value = '${oldParams["tradeType[=][int]"] }';
</script> -->
															<td class="table3_td_1mid" align="left">
																委托单号:
																<label>
																	<input type="text"
																		name="${GNNT_}primary.orderno[=][int]"
																		id="operateId"
																		value="${oldParams['primary.orderno[=][int]'] }"
																		class="input_text" />
																</label>
															</td>
															<td class="table3_td_1tjcx_1" align="left">
																开始日期:
																<label>
																	<input type="text" style="width: 100px" id="beginDate"
																		class="wdate" maxlength="10"
																		name="${GNNT_}clearDate[>=][date]"
																		value='${oldParams["clearDate[>=][date]"]}'
																		onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
																</label>
															</td>
															<td class="table3_td_1tjcx" align="left">
																&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;结束日期:
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
																<button class="btn_cz" onClick="mySelfReset()">
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
											var="customerLimitPrice"
											action="${basePath}/query/queryCustomerLimitPriceSearch/list.action"
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
														<fmt:formatDate value="${customerLimitPrice.clearDate}"
															pattern="yyyy-MM-dd" />
													</ec:column>
												</c:if>
												<ec:column property="orderTime[=][date]" title="委托时间"
													width="6%" style="text-align:left; "
													value="${datefn:formatdate(customerLimitPrice.orderTime)}"
													ellipsis="true" />
												<ec:column property="memberNo[like]" title="综合会员编号"
													width="4%" style="text-align:left; "
													value="${customerLimitPrice.memberNo}" ellipsis="true" />
												<ec:column property="memberName[like]" title="综合会员名称"
													width="7%" style="text-align:left; "
													value="${customerLimitPrice.memberName}" ellipsis="true" />
												<ec:column property="organizationnname[like]" title="机构"
													width="4%" style="text-align:left; "
													value="${customerLimitPrice.organizationnname}"
													ellipsis="true" />
												<ec:column property="brokeragename[like]" title="居间"
													width="4%" style="text-align:left; "
													value="${customerLimitPrice.brokeragename}" ellipsis="true" />
												<ec:column property="customerNo[like]" title="交易账号"
													width="6%" style="text-align:left; "
													value="${customerLimitPrice.customerNo}" ellipsis="true" />
												<ec:column property="customerName[like]" title="客户名称"
													width="4%" style="text-align:left; "
													value="${customerLimitPrice.customerName}" ellipsis="true" />
												<ec:column property="orderno[=][int]" title="委托单号" width="3%"
													style="text-align:left; "
													value="${customerLimitPrice.orderno}" ellipsis="true" />
												<ec:column property="holdno[=][int]" title="持仓单号" width="3%"
													style="text-align:left; "
													value="${customerLimitPrice.holdno}" ellipsis="true" />
												<ec:column property="ordertype[like]" title="委托类型"
													width="3%" style="text-align:left; " editTemplate="esc_ordertype"
													ellipsis="true" >
													<c:set var="ordertypeKey">
														<c:out value="${customerLimitPrice.ordertype}"></c:out>
													</c:set>
		  											${firstMap[ordertypeKey]}
		  										</ec:column>
												<ec:column property="commodityName[like]" title="商品名称"
													width="4%" style="text-align:left; "
													value="${customerLimitPrice.commodityName}" ellipsis="true" />
												<ec:column property="bs_flag[=][int]" title="买卖方向"
													width="3%" style="text-align:left; "
													editTemplate="esc_bsFlag" ellipsis="true">
													<c:set var="typeKey">
														<c:out value="${customerLimitPrice.bs_flag}"></c:out>
													</c:set>
		  											${secondMap[typeKey]}
												</ec:column>
												<ec:column property="oc_flag[=][String]" title="建/平仓"
													width="3%" style="text-align:left; "
													editTemplate="esc_mold" ellipsis="true">
													<c:set var="ocKey">
														<c:out value="${customerLimitPrice.oc_flag}"></c:out>
													</c:set>
		  											${thirdMap[ocKey]}
												</ec:column>
												<ec:column property="stopLossPrice[=][double]" title="止损价"
													width="4%" style="text-align:right; ">
													<fmt:formatNumber
														value="${customerLimitPrice.stopLossPrice}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="stopProfitPrice[=][double]" title="止盈价"
													width="4%" style="text-align:right; ">
													<fmt:formatNumber
														value="${customerLimitPrice.stopProfitPrice}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="qty[=][int]" title="数量" width="3%"
													style="text-align:right; ">
													<fmt:formatNumber value="${customerLimitPrice.qty}"
														pattern="#,##0" />
												</ec:column>
												<ec:column property="price[=][double]" title="委托价"
													width="4%" style="text-align:right; ">
													<fmt:formatNumber value="${customerLimitPrice.price}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="tradeprice[=][double]" title="成交价"
													width="4%" style="text-align:right; ">
													<fmt:formatNumber value="${customerLimitPrice.tradeprice}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="status[=][int]" title="委托状态"
													width="3%" style="text-align:left; "
													editTemplate="esc_processState" ellipsis="true">
													<c:set var="typeKey">
														<c:out value="${customerLimitPrice.status}"></c:out>
													</c:set>
		  											${fourthMap[typeKey]}
		  											</ec:column>
												<ec:column property="withdrawtime[=][date]" title="撤单时间"
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
			<ec:options items="secondMap" />
		</select>
	    </textarea>
	    
	    <!-- 编辑和过滤所使用的委托类型模板 -->
		<textarea id="esc_ordertype" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="ordertype[like]">
			<ec:options items="firstMap" />
		</select>
	    </textarea>

		<!-- 编辑和过滤所使用的类型模板 -->
		<textarea id="esc_mold" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="mold[like]">
			<ec:options items="thirdMap" />
		</select>
	    </textarea>

		<!-- 编辑和过滤所使用的处理状态模板 -->
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
    var isRelated=document.getElementById("isRelated").value;
	var url = "${basePath}/broke/memberInfoTree/forTree.action?isRelated="+isRelated;
	var result=window.showModalDialog(url, window, "dialogWidth=400px; dialogHeight=570px; status=yes;scroll=yes;help=no;");
}

function mySelfReset() {//清空
		window.parent.frames["topFrame1"].document.getElementById('tree').value="";
		var pathRrl=frm.action.toString();
		var au = '111111';
		if (typeof (AUsessionId) != "undefined") {
			au = AUsessionId;
		}
		var urlArray=pathRrl.split("?");
	  	if(urlArray.length==1){
			pathRrl=pathRrl+'?AUsessionId='+au+"&noQuery=true";
		}else if(urlArray.length==2){
			pathRrl=pathRrl+'&AUsessionId='+au+"&noQuery=true";
		}
		window.location.href=pathRrl;
	}
		</SCRIPT>