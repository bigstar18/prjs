
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>
<html xmlns:MEBS>
	<head>
		<title>会员资金流水查询</title>
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
								action="${basePath}/query/queryMemberFundFlowSearch/list.action?sortName=primary.fundFlowId&sortOrder=true"
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
											var="memberFundFlow"
											action="${basePath}/query/queryMemberFundFlowSearch/list.action"
											title="" minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" csvFileName="导出列表.csv"
											style="table-layout:fixed">
											<ec:row ondblclick="">
												<ec:column width="3%" property="_0" title="序号"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<c:if test="${queryType=='H'}">
													<ec:column property="clearDate[=][date]" title="结算日期"
														width="5%" style="text-align:left; " ellipsis="true">
														<fmt:formatDate value="${memberFundFlow.clearDate}"
															pattern="yyyy-MM-dd" />
													</ec:column>
												</c:if>
												<ec:column property="fundFlowId[=][int]" title="流水号"
													width="5%" style="text-align:left; "
													value="${memberFundFlow.fundFlowId}" ellipsis="true" />
												<ec:column property="oprcode[=][int]" title="业务名称"
													width="8%" style="text-align:left; "
													editTemplate="esc_bsFlag" ellipsis="true">
													<c:set var="typeKey">
														<c:out value="${memberFundFlow.oprcode}"></c:out>
													</c:set>
		  											${firstMap[typeKey]}
		  											</ec:column>
												<ec:column property="createtime[=][date]" title="发生时间"
													width="6%" style="text-align:left; "
													value="${datefn:formatdate(memberFundFlow.createtime)}"
													ellipsis="true" />

												<ec:column property="amount[=][double]" title="变动金额"
													width="6%" style="text-align:right; ">
													<fmt:formatNumber value="${memberFundFlow.amount}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="balance[=][double]" title="变后金额"
													width="6%" style="text-align:right; ">
													<fmt:formatNumber value="${memberFundFlow.balance}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="voucherno[=][long]" title="关联单号"
													width="5%" style="text-align:right; "
													value="${memberFundFlow.voucherno}" ellipsis="true">
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
				name="oprcode[=]">
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
			  //ec.action="${basePath}/query/queryMemberTradeSearch/list.action?queryType=D&sortName=memberNo";
			  //frm.action="${basePath}/query/queryMemberTradeSearch/list.action?queryType=D&sortName=memberNo";
			}
			else if(value=='H')
			{
			   frm.beginDate.disabled=false;
			   frm.endDate.disabled=false;
			   
frm.beginDate.style.backgroundColor="#ffffff";
frm.endDate.style.backgroundColor="#ffffff";
			   frm.queryType.value='H';
			    //ec.action="${basePath}/query/queryMemberTradeSearch/list.action?queryType=H&sortName=memberNo";
			     //frm.action="${basePath}/query/queryMemberTradeSearch/list.action?queryType=H&sortName=memberNo";
			}
		}
		
		</SCRIPT>