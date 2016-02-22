
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>
<html xmlns:MEBS>
	<head>
		<title>会员持仓单查询</title>
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
								action="${basePath}/query/queryMemberHoldSearch/list.action?sortName=primary.holdNo&sortOrder=true"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_widthmid">
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
																</select>
															</span>
															<script type="text/javascript">
															frm.commodityId.value = '${oldParams["primary.commodityId[=][String]"] }';
															</script>
														</td>
														<td class="table3_td_1" align="left">
															持仓单号:&nbsp;
															<label>
																<input type="text" name="${GNNT_}primary.holdNo[=][int]"
																	id="traderId" value="${oldParams['primary.holdNo[=][int]'] }"
																	class="input_textmin" />
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
											var="memberHoldSearch"
											action="${basePath}/query/queryMemberHoldSearch/list.action"
											title="" minHeight="345" listWidth="140%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="导出列表.csv" style="table-layout:fixed">
											<ec:row ondblclick="">
												<ec:column width="2%" property="_0" title="序号"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<c:if test="${queryType=='H'}">
													<ec:column property="clearDate[=][date]" title="结算日期"
														width="4%" style="text-align:left; "
														
														ellipsis="true" >
														<fmt:formatDate value="${memberHoldSearch.clearDate}" pattern="yyyy-MM-dd"/>
													</ec:column>
												</c:if>
												<ec:column property="holdNo[=][int]" title="持仓单号" width="3%"
													style="text-align:center; "
													value="${memberHoldSearch.holdNo}" ellipsis="true"/>
													<ec:column property="holdTime[=][date]" title="建仓时间"
													width="6%" style="text-align:left; "
													value="${datefn:formatdate(memberHoldSearch.holdTime)}" ellipsis="true"/>
												<ec:column property="commodityName[like]" title="商品"
													width="4%" style="text-align:left; "
													value="${memberHoldSearch.commodityName}" ellipsis="true"/>
												<ec:column property="holdQty[=][int]" title="持仓数量" width="3%"
													style="text-align:right; " >
													<fmt:formatNumber value="${memberHoldSearch.holdQty}"
														pattern="#,##0" />
												</ec:column>
												<ec:column property="bs_flag[=][int]" title="买卖方向"
													width="3%" style="text-align:left; "
													editTemplate="esc_bsFlag" ellipsis="true">
													<c:set var="typeKey">
														<c:out value="${memberHoldSearch.bs_flag}"></c:out>
													</c:set>
		  											${firstMap[typeKey]}
												</ec:column>
												<ec:column property="openPrice[=][double]" title="建仓单价"
													width="4%" style="text-align:right; ">
													<fmt:formatNumber value="${memberHoldSearch.openPrice}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="holdPrice[=][double]" title="持仓单价"
													width="4%" style="text-align:right; " >
													<fmt:formatNumber value="${memberHoldSearch.holdPrice}"
														pattern="#,##0.00" />
												</ec:column>
												<c:if test="${queryType=='D'}">
												<ec:column property="curprice[=][double]" title="平仓价" width="4%"
													style="text-align:right; " >
													<fmt:formatNumber value="${memberHoldSearch.curprice}"
														pattern="#,##0.00" />
												</ec:column>
												</c:if>
												<c:if test="${queryType=='H'}">
												<ec:column property="clearprice[=][double]" title="结算价" width="4%"
													style="text-align:right; " >
													<fmt:formatNumber value="${memberHoldSearch.clearprice}"
														pattern="#,##0.00" />
												</ec:column>
												</c:if>
												<c:if test="${queryType=='D'}">
												<ec:column property="floatingLoss[=][double]" title="浮动盈亏"
													width="4%" style="text-align:right; ">
													<fmt:formatNumber value="${memberHoldSearch.floatingLoss}"
														pattern="#,##0.00" />
												</ec:column>
												</c:if>
												<c:if test="${queryType=='H'}">
												<ec:column property="floatingLoss[=][double]" title="盈亏"
													width="4%" style="text-align:right; ">
													<fmt:formatNumber value="${memberHoldSearch.floatingLoss}"
														pattern="#,##0.00" />
												</ec:column>
												</c:if>
												
												<ec:column property="delayFee[=][double]" title="延期费"
													width="4%" style="text-align:right; " >
													<fmt:formatNumber value="${memberHoldSearch.delayFee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="mktdelayFee[=][double]" title="交易所存留延期费"
													width="5%" style="text-align:right; ">
													<fmt:formatNumber value="${memberHoldSearch.mktdelayFee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="memberdelayFee[=][double]" title="特别会员存留延期费"
													width="5%" style="text-align:right; ">
													<fmt:formatNumber value="${memberHoldSearch.memberdelayFee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="traderId[like]" title="操作人"
													width="4%" style="text-align:left; "
													value="${memberHoldSearch.traderId}" ellipsis="true" />
												<ec:column property="s_memberName[like]" title="特别会员名称"
													width="5%" style="text-align:left; "
													value="${memberHoldSearch.s_memberName}" ellipsis="true" />
											</ec:row>
											<c:if test="${ifHas eq 1}">		
												<ec:extendrow>
													<td>
													          合计:
													</td>
													<c:if test="${queryType=='H'}">
														<td>&nbsp;</td>
													</c:if>
													<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
													<td align="right" style="font-weight:bold">${holdQtyAll}</td>
													<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
													<c:if test="${queryType=='D'}">
														<td>&nbsp;</td>
													</c:if>
													<c:if test="${queryType=='H'}">
														<td>&nbsp;</td>
													</c:if>
													<c:if test="${queryType=='D'}">
														<td align="right" style="font-weight:bold">${floatingLossAll}</td>
													</c:if>
													<c:if test="${queryType=='H'}">
														<td align="right" style="font-weight:bold">${floatingLossAll}</td>
													</c:if>
													<td align="right" style="font-weight:bold">${delayFeeAll}</td>
													<td align="right" style="font-weight:bold">${mktdelayFeeAll}</td>
													<td align="right" style="font-weight:bold">${memberdelayFeeAll}</td>
													<td>&nbsp;</td><td>&nbsp;</td>
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