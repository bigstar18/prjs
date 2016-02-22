
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>
<html xmlns:MEBS>
	<head>
		<title>特别会员平仓查询</title>
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
								action="${basePath}/query/querySpecialMemberTradeSearch/list.action?sortName=primary.tradeNo&sortOrder=true"
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
														<td class="table3_td_1tjcx" align="left">
															综合会员编号:&nbsp;
															<label>
																<input type="text" name="${GNNT_}memberNo[like]"
																	id="memberName" value="${oldParams['memberNo[like]'] }"
																	class="input_text" />
															</label>
														</td>
														<td class="table3_td_1tjcx" align="left">
															特别会员:&nbsp;
															<label>
																<span class="right_03zi"> <select
																		name="${GNNT_}s_memberNo[like]" size="1"
																		id="specialId" style="width: 100">
																		<option value="">
																			请选择
																		</option>
																		<c:forEach items="${specialMemberList}" var="list">
																			<option value="${list.id }">
																				${list.name }
																			</option>
																		</c:forEach>
																	</select> </span>
															</label>
														</td>
														<script type="text/javascript">
frm.specialId.value = '${oldParams["s_memberNo[=][String]"] }';
</script>
													<td class="table3_td_1tjcx" align="left">
															买入/卖出:
															<label>
																<span class="right_03zi"> <select
																		name="${GNNT_}bs_flag[like]" size="1" id="flag"
																		value="${oldParams['bs_flag[like]'] }"
																		 class="input_textmin">
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
																<script type="text/javascript">
frm.flag.value = '${oldParams["bs_flag[like]"] }';
</script>
															</label>
														</td>
													</tr>
													<tr>
														<td class="table3_td_1" align="left">
															开始日期:&nbsp;
															<label>
																<input type="text" style="width: 100px" id="beginDate"
																	class="wdate" maxlength="10"
																	name="${GNNT_}clearDate[>=][date]"
																	value='${oldParams["clearDate[>=][date]"]}'
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															</label>
														</td>
														<td class="table3_td_1" align="left">
															结束日期:&nbsp;
															<label>
																<input type="text" style="width: 100px" id="endDate"
																	class="wdate" maxlength="10"
																	name="${GNNT_}clearDate[<=][date]"
																	value='${oldParams["clearDate[<=][date]"]}'
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															</label>
														</td>
														<td class="table3_td_1tjcx" align="left">
															商品名称:&nbsp;
															<span class="right_03zi"><select id="commodityId"
																	name="${GNNT_}primary.commodityId[like]"
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
frm.commodityId.value = '${oldParams["primary.commodityId[like]"] }';
</script>
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
											var="specialMemberTrade"
											action="${basePath}/query/querySpecialMemberTradeSearch/list.action?sortName=primary.tradeNo&sortOrder=true"
											title="" minHeight="345" listWidth="200%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="导出列表.csv" style="table-layout:fixed">
											<ec:row>
												<ec:column width="2%" property="_0" title="序号"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<c:if test="${queryType=='H'}">
												<ec:column property="clearDate[=][date]" title="结算日期"
													width="5%" style="text-align:left; "
													value="${datefn:formatdate(specialMemberTrade.clearDate)}" ellipsis="true"/>
												</c:if>
												<ec:column property="tradeNo[=][int]" title="平仓单号"
													width="4%" style="text-align:right; " value="${specialMemberTrade.tradeNo}" ellipsis="true">
												</ec:column>
												<ec:column property="s_memberNo[like]" title="特别会员编号"
													width="5%" style="text-align:left; "
													value="${specialMemberTrade.s_memberNo}" ellipsis="true"/>

												<ec:column property="s_name[like]" title="特别会员名称"
													width="5%" style="text-align:right;" value="${specialMemberTrade.s_name}"  ellipsis="true">
													
												</ec:column>
												<ec:column property="s_signno[like]" title="特别会员交易账号"
													width="5%" style="text-align:right;" value="${specialMemberTrade.s_signno}"  ellipsis="true">
													
												</ec:column>
												<ec:column property="memberNo[like]" title="综合会员编号"
													width="5%" style="text-align:left; "
													value="${specialMemberTrade.memberNo}" ellipsis="true"/>

												<ec:column property="memberName[like]" title="综合会员名称"
													width="5%" style="text-align:right;" value="${specialMemberTrade.memberName}"  ellipsis="true">
													
												</ec:column>
												<ec:column property="memberSignNo[like]" title="综合会员交易账号"
													width="5%" style="text-align:right;"  value="${specialMemberTrade.memberSignNo}" ellipsis="true">
													
												</ec:column>
												<ec:column property="closetime[=][date]" title="平仓日期"
													width="6%" style="text-align:right; " value="${datefn:formatdate(specialMemberTrade.closetime)}" ellipsis="true">
													
												</ec:column>
												<ec:column property="commodityName[like]" title="商品名称"
													width="5%" style="text-align:right; " value="${specialMemberTrade.commodityName}" ellipsis="true">
													
												</ec:column>
												<ec:column property="bs_flag[=][String]" title="买卖方向" width="3%"
													style="text-align:left; " editTemplate="esc_bsFlag" ellipsis="true">
													<c:set var="typeKey">
														<c:out value="${specialMemberTrade.bs_flag}"></c:out>
													</c:set>
		  											${bsFlagMap[typeKey]}
												</ec:column>
												<ec:column property="quantity[=][int]" title="平仓量"
													width="3%" style="text-align:right;  ">
													<fmt:formatNumber value="${specialMemberTrade.quantity}"
														pattern="#,##0" />
												</ec:column>
												<ec:column property="price[=][double]" title="平仓价"
													width="4%" style="text-align:right; ">
													<fmt:formatNumber value="${specialMemberTrade.price}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="holdPrice[=][double]" title="持仓价"
													width="4%" style="text-align:right; ">
													<fmt:formatNumber value="${specialMemberTrade.holdPrice}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="close_pl[=][double]" title="平仓盈亏"
													width="5%" style="text-align:right; ">
													<fmt:formatNumber value="${specialMemberTrade.close_pl}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="opentradeno[like]" title="建仓单号"
													width="5%" style="text-align:right; " value="${specialMemberTrade.opentradeno}" ellipsis="true">
													
												</ec:column>
												<ec:column property="openPrice[=][double]" title="建仓价"
													width="4%" style="text-align:right; " >
													<fmt:formatNumber value="${specialMemberTrade.openPrice}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="holdTime[=][date]" title="建仓时间"
													width="7%" style="text-align:left; "
													value="${datefn:formatdate(specialMemberTrade.holdTime)}" ellipsis="true"/>
												<ec:column property="delayFee[=][double]" title="延期费"
													width="4%" style="text-align:right; ">
													<fmt:formatNumber value="${specialMemberTrade.delayFee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="holdNo[like]" title="持仓单号"
													width="4%" style="text-align:left; "
													value="${specialMemberTrade.holdNo}" ellipsis="true"/>
												<ec:column property="tradeType[=][String]" title="委托类型" width="3%"
													style="text-align:left; " editTemplate="esc_tradeMap" ellipsis="true">
													<c:set var="tradetype">
														<c:out value="${specialMemberTrade.tradeType}"></c:out>
													</c:set>
		  											${tradeMap[tradetype]}
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
		<!-- 编辑和过滤所使用的建仓方向标志模板 -->
		<textarea id="esc_bsFlag" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="build_flag[=]">
			<ec:options items="bsFlagMap" />
		</select>
	    </textarea>
	    <!-- 编辑和过滤所使用的建仓方向标志模板 -->
		<textarea id="esc_tradeMap" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="tradeMap[=]">
			<ec:options items="tradeMap" />
		</select>
	    </textarea>
	</body>
</html>

<SCRIPT type="text/javascript">
	function search1(){
		checkTotalQueryDate(frm.beginDate.value,frm.endDate.value);
	}
		function init(queryType){
			 document.getElementById("beginDate").disabled=true;
			document.getElementById("endDate").disabled=true;
			change(queryType);
		}
			function changeOn(){
		var todayHis=document.getElementById("queryType").value;
		change(todayHis);
	}
		function change(value){
			if(value=='D')
			{
			  frm.beginDate.disabled=true;
			  frm.endDate.disabled=true;
			  frm.beginDate.style.backgroundColor="#d0d0d0";
				frm.endDate.style.backgroundColor="#d0d0d0";
			   frm.queryType.value='D';
			 // ec.action="${basePath}/query/queryMemberHoldSearch/list.action?queryType=D";
			 // frm.action="${basePath}/query/queryMemberHoldSearch/list.action?queryType=D";
			}
			else if(value=='H')
			{
			   frm.beginDate.disabled=false;
			   frm.endDate.disabled=false;
			   frm.beginDate.style.backgroundColor="#ffffff";
				frm.endDate.style.backgroundColor="#ffffff";
			   frm.queryType.value='H';
			  //  ec.action="${basePath}/query/queryMemberHoldSearch/list.action?queryType=H";
			  //  frm.action="${basePath}/query/queryMemberHoldSearch/list.action?queryType=H";
			}
		}
</SCRIPT>