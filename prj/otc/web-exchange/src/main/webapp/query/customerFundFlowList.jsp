
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>
<html xmlns:MEBS>
	<head>
		<title>客户资金流水</title>
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
								action="${basePath}/query/queryCustomerFundFlowSearch/list.action?sortName=primary.fundFlowId&sortOrder=true"
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
														<td>
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;流水号:
															<label>
																<input type="text"
																	name="${GNNT_}primary.fundFlowId[=][int]"
																	id="operateId"
																	value="${oldParams['primary.fundFlowId[=][int]'] }"
																	class="input_textmin" />
															</label>
														</td>
														<tr>
															<td class="table3_td_1mid" align="left">
																开始日期:
																<label>
																	<input type="text" style="width: 120px" id="beginDate"
																		class="wdate" maxlength="10"
																		name="${GNNT_}clearDate[>=][date]"
																		value='${oldParams["clearDate[>=][date]"]}'
																		onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
																</label>
															</td>

															<td class="table3_td_1tjcx_1" align="left">
																结束日期:
																<label>
																	<input type="text" style="width: 100px" id="endDate"
																		class="wdate" maxlength="10"
																		name="${GNNT_}clearDate[<=][date]"
																		value='${oldParams["clearDate[<=][date]"]}'
																		onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
																</label>
															</td>
															<td class="table3_td_1tjcx" align="left">
																&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
											var="customerFundFlow"
											action="${basePath}/query/queryCustomerFundFlowSearch/list.action?sortName=primary.fundFlowId&sortOrder=true"
											title="" minHeight="345" listWidth="120%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" csvFileName="导出列表.csv"
											style="table-layout:fixed">
											<ec:row ondblclick="">
												<ec:column width="3%" property="_0" title="序号"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<c:if test="${queryType=='H'}">
													<ec:column property="clearDate[=][date]" title="结算日期"
														width="6%" style="text-align:left; " ellipsis="true">
														<fmt:formatDate value="${customerFundFlow.clearDate}"
															pattern="yyyy-MM-dd" />
													</ec:column>
												</c:if>
												<ec:column property="memberNo[like]" title="综合会员编号" width="6%"
													style="text-align:left; "
													value="${customerFundFlow.memberNo}" ellipsis="true" />
												<ec:column property="memberName[like]" title="综合会员名称"
													width="8%" style="text-align:left; "
													value="${customerFundFlow.memberName}" ellipsis="true" />
												<ec:column property="organizationName[like]" title="机构"
													width="6%" style="text-align:left; "
													value="${customerFundFlow.organizationName}" ellipsis="true" />
												<ec:column property="brokerageName[like]" title="居间"
													width="6%" style="text-align:left; "
													value="${customerFundFlow.brokerageName}" ellipsis="true" />
												<ec:column property="customerNo[like]" title="交易账号"
													width="8%" style="text-align:left; "
													value="${customerFundFlow.customerNo}"
													ellipsis="true" />
												<ec:column property="customerName[like]" title="客户名称"
													width="8%" style="text-align:left; "
													value="${customerFundFlow.customerName}" ellipsis="true" />
												<ec:column property="fundFlowId[=][int]" title="流水号"
													width="6%" style="text-align:left; "
													value="${customerFundFlow.fundFlowId}" ellipsis="true" />
												<ec:column property="oprcode[=][int]" title="业务名称"
													width="7%" style="text-align:left; "
													editTemplate="esc_codeType" ellipsis="true">
													<c:set var="code">
														<c:out value="${customerFundFlow.oprcode}"></c:out>
													</c:set>
													${firstMap[code]}
												</ec:column>
												<ec:column property="createTime[=][date]" title="发生时间"
													width="9%" style="text-align:left; " ellipsis="true">
													<fmt:formatDate value="${customerFundFlow.createTime}"
														pattern="yyyy-MM-dd HH:mm:ss" />
												</ec:column>
												<ec:column property="changeAmount[=][double]" title="变动资金"
													width="7%" style="text-align:right; ">
													<fmt:formatNumber value="${customerFundFlow.changeAmount}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="afterAmount[=][double]" title="变后资金"
													width="7%" style="text-align:right; ">
													<fmt:formatNumber value="${customerFundFlow.afterAmount}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="contractNo[=][String]" title="关联单号"
													width="6%" style="text-align:left;"
													value="${customerFundFlow.contractNo}" ellipsis="true" />
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
		<textarea id="esc_codeType" rows="" cols="" style="display: none">
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
			 // ec.action="${basePath}/query/queryCustomerFundFlowSearch/list.action?queryType=D&sortName=primary.customer_tradeId";
			  //frm.action="${basePath}/query/queryCustomerFundFlowSearch/list.action?queryType=D&sortName=primary.customer_tradeId";
			}
			else if(value=='H')
			{
			   frm.beginDate.disabled=false;
			   frm.endDate.disabled=false;
			   frm.beginDate.style.backgroundColor="#ffffff";
			  frm.endDate.style.backgroundColor="#ffffff";
			   frm.queryType.value='H';
			  //  ec.action="${basePath}/query/queryCustomerFundFlowSearch/list.action?queryType=H&sortName=primary.customer_tradeId";
			  //  frm.action="${basePath}/query/queryCustomerFundFlowSearch/list.action?queryType=H&sortName=primary.customer_tradeId";
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