
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>
<html xmlns:MEBS>
	<head>
		<title>客户平仓单查询</title>
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
								action="${basePath}/query/queryCustomerTradeSearch/list.action?sortName=primary.de_buildNo&sortOrder=true"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_widthmax">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0" style="table-layout: fixed">
													<tr>
														<td class="table3_td_1" align="left">
															查询范围:&nbsp;
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
														<td class="table3_td_1" align="left">
															商品名称:&nbsp;
															<span class="right_03zi"><select id="commodityId"
																	name="${GNNT_}primary.commodityName[like]"
																	class="input_textmin">
																	<option value="">
																		请选择
																	</option>
																	<c:forEach items="${commodityList}" var="commodit">
																		<option value="${commodit.name}">
																			${commodit.name }
																		</option>
																	</c:forEach>
																</select>
															</span>
															<script type="text/javascript">
frm.commodityId.value = '${oldParams["primary.commodityName[like]"] }';
</script>
														</td>
														<td class="table3_td_1" align="left">
															开始日期:&nbsp;
															<label>
																<input type="text" style="width: 100px" id="beginDate"
																	class="wdate" maxlength="10"
																	name="${GNNT_}atClearDate[>=][date]"
																	value='${oldParams["atClearDate[>=][date]"]}'
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															</label>
														</td>
														<td class="table3_td_1" align="left">
															结束日期:&nbsp;
															<label>
																<input type="text" style="width: 100px" id="endDate"
																	class="wdate" maxlength="10"
																	name="${GNNT_}atClearDate[<=][date]"
																	value='${oldParams["atClearDate[<=][date]"]}'
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															</label>
														</td>
													</tr>
													<tr>
														<td class="table3_td_1" align="left">
															买入/卖出:
															<label>
																<span class="right_03zi"> <select
																		name="${GNNT_}build_flag[=][int]" size="1" id="flag"
																		style="width: 100">
																		<option value="">
																			请选择
																		</option>
																		<option value="1">
																			买入
																		</option>
																		<option value="2">
																			卖出
																		</option>
																	</select> </span>
															</label>
															<script type="text/javascript">
document.getElementById("flag").value = '${oldParams["build_flag[=][int]"] }';</script>
														</td>
														<td class="table3_td_1" align="left">
														交易账号:&nbsp;
																<label>
																	<input type="text" name="${GNNT_}customerId[like]"
																		id="operateId" value="${oldParams['customerId[like]'] }"
																		class="input_textmin" />
																</label>
														</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" onClick="search1()">
																查询
															</button>
															&nbsp;&nbsp;
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
											var="customerTrade"
											action="${basePath}/query/queryCustomerTradeSearch/list.action"
											title="" minHeight="345" listWidth="250%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="导出列表.csv" style="table-layout:fixed">
											<ec:row ondblclick="">
												<ec:column width="2%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<c:if test="${queryType=='H'}">
												<ec:column property="atClearDate[=][date]" title="结算日期"
													width="5%" style="text-align:left; "
													value="${datefn:formatdate(customerTrade.atClearDate)}" ellipsis="true"/>
												</c:if>
												<ec:column property="de_buildNo[=][int]" title="平仓单号"
													width="4%" style="text-align:left; "
													value="${customerTrade.de_buildNo}" ellipsis="true"/>
												<ec:column property="tradeType[=][String]" title="平仓类型" width="5%"
													style="text-align:left; " editTemplate="esc_tradeMap" ellipsis="true">
		  											${tradeMap[customerTrade.tradeType]}
		  											</ec:column>
												<ec:column property="customerId[like]" title="交易账号"
													width="5%" style="text-align:left; "
													value="${customerTrade.customerId}" ellipsis="true" />
												<ec:column property="commodityName[like]" title="商品名称"
													width="5%" style="text-align:left; "
													value="${customerTrade.commodityName}" ellipsis="true" />
												<ec:column property="memberNo[like]" title="会员编号" width="4%"
													style="text-align:left; " value="${customerTrade.memberNo}"
													ellipsis="true" />
												<ec:column property="holdQty[=][int]" title="数量" width="4%"
													style="text-align:right; "  >
												<fmt:formatNumber value="${customerTrade.holdQty}"
														pattern="#,##0" />
												</ec:column>
												<ec:column property="buildNo[=][int]" title="委托单号" width="5%"
													style="text-align:left; " value="${customerTrade.buildNo}"
													ellipsis="true" />
												<ec:column property="build_flag[=][int]" title="建仓方向"
													width="5%" style="text-align:left; "
													editTemplate="esc_bsFlag" ellipsis="true">
													<c:set var="typeKey">
														<c:out value="${customerTrade.build_flag}"></c:out>
													</c:set>
		  											${bsFlagMap[typeKey]}
		  											</ec:column>
												<ec:column property="openPrice[=][double]" title="建仓价" width="5%"
													style="text-align:right; " >
													<fmt:formatNumber value="${customerTrade.openPrice}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="holdPrice[=][double]" title="持仓价" width="5%"
													style="text-align:right; " >
													<fmt:formatNumber value="${customerTrade.holdPrice}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="buildDate[=][date]" title="建仓时间"
													width="7%" style="text-align:left; "
													value="${datefn:formatdate(customerTrade.buildDate)}"
													ellipsis="true" />
												<ec:column property="de_buildPrice[=][double]" title="平仓价"
													width="5%" style="text-align:right; " >
													<fmt:formatNumber value="${customerTrade.de_buildPrice}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="de_buildDate[=][date]" title="平仓时间"
													width="6%" style="text-align:left; "
													value="${datefn:formatdate(customerTrade.de_buildDate)}"
													ellipsis="true" />
												<ec:column property="floatingLoss[=][double]" title="盈亏"
													width="4%" style="text-align:right; " >
													<fmt:formatNumber value="${customerTrade.floatingLoss}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="netFloatingLoss[=][double]" title="净盈亏"
													width="5%" style="text-align:right; " >
													<fmt:formatNumber value="${customerTrade.netFloatingLoss}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="tradeFee[=][double]" title="手续费"
													width="5%" style="text-align:right; " >
													<fmt:formatNumber value="${customerTrade.tradeFee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="de_builderId[like]" title="平仓者"
													width="5%" style="text-align:right; "
													value="${customerTrade.de_builderId}" >


												</ec:column>
												<ec:column property="builderId[like]" title="建仓者" width="5%"
													style="text-align:right;"
													value="${customerTrade.builderId}" ellipsis="true">


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
		<!-- 编辑和过滤所使用的 通用的文本框模板 -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value=""
				onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="" />
		</textarea>
		<!-- 编辑和过滤所使用的操作模板 -->
		<textarea id="esc_tradeType" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="buildTradeType[=]">
			<ec:options items="tradeTypeMap" />
		</select>
	    </textarea>


		<!-- 编辑和过滤所使用的建仓方向标志模板 -->
		<textarea id="esc_bsFlag" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="build_flag[=]">
			<ec:options items="bsFlagMap" />
		</select>
	    </textarea>

		<!-- 编辑和过滤所使用的平仓方向标志模板 -->
		<textarea id="esc_de_bsFlag" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="de_build_flag[=]">
			<ec:options items="bsFlagMap" />
		</select>
	    </textarea>
	    <!-- 编辑和过滤所使用的平仓类型标志模板 -->
		<textarea id="esc_tradeMap" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="tradeType[=][String]">
			<ec:options items="tradeMap" />
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