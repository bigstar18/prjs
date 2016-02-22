<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/session.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>客户持仓汇总表</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
		<import namespace="MEBS"
			implementation="${basePath}/report/public/calendar.htc">
	</head>
	<body id="main_body" onload="init();">
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
							<form
								action="${basePath}/report/customerHoldSummary/customerHoldSummaryReportQuery.action"
								name="frm" id="frm" method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_widthcdmax">
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
														<td class="table3_td_1tjcx" align="left">
															客户归属：
															<input type="text" id="memberNames"
																name="${GNNT_}memberNames"
																value="${oldParams['memberNames']}" readonly=true
																class="input_textmin">
															<a href="javascript:clickText();"><img
																	align="absmiddle" src="<%=skinPath%>/cssimg/kh.gif">
															</a>
														</td>
														<td class="table3_td_1tjcx" align="left">
															综合会员编号：
															<input type="text" name="${GNNT_}memberno[=][String]"
																id="memberNo"
																value="${oldParams['memberno[=][String]'] }"
																class="input_textmin">
														</td>
														<td class="table3_td_1tjcx" align="left">
															交易账号：
															<input type="text" name="${GNNT_}customerno[like]"
																id="customerNo"
																value="${oldParams['customerno[like]'] }"
																class="input_text">
														</td>
														<td class="table3_td_1tjcx_1" align="left">
															客户名称：
															<input type="text"
																name="${GNNT_}primary.customerName[like]"
																id="customerName"
																value="${oldParams['primary.customerName[like]'] }"
																class="input_textmin">
														</td>
													</tr>
													<tr>
														<td class="table3_td_1tjcx_1" align="left">
															开始日期：
															<input type="text" style="width: 100px" id="startDate"
																class="wdate" maxlength="10"
																name="${GNNT_}primary.clearDate[>=][date]"
																value='${oldParams["primary.clearDate[>=][date]"]}'
																onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
														</td>
														<td class="table3_td_1tjcx" align="left">
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;结束日期：
															<input type="text" style="width: 100px" id="endDate"
																class="wdate" maxlength="10"
																name="${GNNT_}primary.clearDate[<=][date]"
																value='${oldParams["primary.clearDate[<=][date]"]}'
																onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
														</td>
														<td class="table3_td_1tjcx" align="left">
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;商品：
															<select id="commodityId"
																name="${GNNT_}primary.commodityId[=][String]"
																class="input_text">
																<option value="">
																	请选择
																</option>
																<c:forEach items="${commodityList}" var="commodit">
																	<option value="${commodit.id}">
																		${commodit.name }
																	</option>
																</c:forEach>
															</select>
															<script type="text/javascript">
															frm.commodityId.value='${oldParams['primary.commodityId[=][String]'] }';
															</script>
														</td>
															<td align="left" colspan="3">
															<button class="btn_sec" onclick="select1()">
																查询
															</button>
															&nbsp;
															<button class="btn_cz" onclick="mySelfReset();">
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
										<ec:table items="list"
											autoIncludeParameters="${empty param.autoInc}"
											var="customerHold"
											action="${basePath}/report/customerHoldSummary/customerHoldSummaryReportQuery.action"
											title="" minHeight="345" listWidth="152%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" csvFileName="导出列表.csv"
											style="table-layout:fixed">
											<ec:row recordKey="${customer.customerNo}">
												<ec:column width="3%" property="_0" title="序号"
													value="${GLOBALROWCOUNT}"  
													  />
												<ec:column property="clearDate[=][date]" title="结算日期"
													width="5%" style="text-align:left; " ellipsis="true">
													<fmt:formatDate value="${customerHold.clearDate}"
														pattern="yyyy-MM-dd" />
												</ec:column>
												<ec:column property="memberNo[like]" title="综合会员编号" width="5%"
													style="text-align:left; " value="${customerHold.memberNo}"
													ellipsis="true" />
												<ec:column property="memberName[like]" title="综合会员名称"
													width="8%" style="text-align:left; "
													value="${customerHold.memberName}" ellipsis="true" />
												<ec:column property="organizationName[like]" title="机构"
													width="6%" value="${customerHold.organizationName}"
													style="text-align:left; " ellipsis="true">
												</ec:column>
												<ec:column property="brokerName[like]" title="居间" width="6%"
													value="${customerHold.brokerName}"
													style="text-align:right; " ellipsis="true">
												</ec:column>
												<ec:column property="customerNo[=][String]" title="交易账号"
													width="7%" style="text-align:right; "
													value="${customerHold.customerNo}" ellipsis="true">
												</ec:column>
												<ec:column property="customerName[like]" title="客户名称"
													width="6%" style="text-align:left; "
													value="${customerHold.customerName}" ellipsis="true" />
												<ec:column property="commodityName[like]" title="商品"
													width="6%" style="text-align:left; "
													value="${customerHold.commodityName}" ellipsis="true" />
												<ec:column property="buyqty[=][int]" title="买单持仓量" width="5%"
													style="text-align:right; " >
													<fmt:formatNumber value="${customerHold.buyqty}"
														pattern="#,##0" />
												</ec:column>
												<ec:column property="sellqty[=][int]" title="卖单持仓量" width="5%"
													style="text-align:right; " >
													<fmt:formatNumber value="${customerHold.sellqty}"
														pattern="#,##0" />
												</ec:column>
												<ec:column property="qtysum[=][int]" title="持仓量合计" width="5%"
													style="text-align:right; " >
													<fmt:formatNumber value="${customerHold.qtysum}"
														pattern="#,##0" />
												</ec:column>
												<ec:column property="floatingloss[=][double]" title="持仓盈亏" width="7%"
													style="text-align:right;">
													<fmt:formatNumber value="${customerHold.floatingloss}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="delayfee[=][double]" title="收客户延期费" width="6%"
													style="text-align:right;">
													<fmt:formatNumber value="${customerHold.delayfee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="mktdelayfee[=][double]" title="交易所存留延期费" width="6%"
													style="text-align:right;">
													<fmt:formatNumber value="${customerHold.mktdelayfee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="memberdelayfee[=][double]" title="会员存留延期费" width="6%"
													style="text-align:right;">
													<fmt:formatNumber value="${customerHold.memberdelayfee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="margin[=][double]" title="占用保证金" width="7%"
													style="text-align:right; " >
													<fmt:formatNumber value="${customerHold.margin}"
														pattern="#,##0.00" />
												</ec:column>

											</ec:row>
											<c:if test="${ifHas eq 1}">		
												<ec:extendrow>
													<td>
													          合计:
													</td>
													<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
													<td>&nbsp;</td><td>&nbsp;</td>
													<td align="right" style="font-weight:bold">${buyqtyAll}</td>
													<td align="right" style="font-weight:bold">${sellqtyAll}</td>
													<td align="right" style="font-weight:bold">${qtysumAll}</td>
													<td align="right" style="font-weight:bold">${floatinglossAll}</td>
													<td align="right" style="font-weight:bold">${delayfeeAll}</td>
													<td align="right" style="font-weight:bold">${mktdelayfeeAll}</td>
													<td align="right" style="font-weight:bold">${memberdelayfeeAll}</td>
													<td align="right" style="font-weight:bold">${marginAll}</td>
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
		<script type="text/javascript">

function select1() {
	var action = frm.action;
	frm.action = "${basePath}/report/customerHoldSummary/customerHoldSummaryReportQuery.action";
	checkQueryDate(frm.startDate.value, frm.endDate.value);
	frm.action = action;
}
function xls() {
	frm.type.value = "xls";
	select1();
	frm.type.value = "";
}
function init() {
	if (frm.startDate.value == "" && frm.endDate.value == "") {
		frm.startDate.value = '${date}';
		frm.endDate.value = '${date}';
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
</script>