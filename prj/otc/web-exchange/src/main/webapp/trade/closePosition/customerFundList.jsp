
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>
<html xmlns:MEBS>
	<head>
		<title>客户资金查询</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
		<IMPORT namespace="MEBS"
			implementation="${basePath}/common/jslib/calendar.htc">
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
							<form name="frm"
								action="${basePath}/customerClosePosition/customerList/list.action?sortName=primary.riskRate"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0" style="table-layout:fixed">
													<tr>
														<td class="table3_td_1" align="left">
															交易账号:&nbsp;
															<label>
																<input type="text" class="input_textmin" id="customerId"
																	name="${GNNT_}customerNo[like]" size="14"
																	value="${oldParams['customerNo[like]'] }" />
															</label>
														</td>
														<td class="table3_td_1" align="left">
															&nbsp;客户名称:&nbsp;
															<label>
																<input type="text" name="${GNNT_}customerName[like]"
																	id="customerName"
																	value="${oldParams['customerName[like]'] }"
																	class="input_textmin" />
															</label>
														</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" onClick="search1()">
																查询
															</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onClick="myReset2()">
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
						<div class="div_gn">
							<button class="anniu_btn" onclick="closePosition()" id="update">
								客户强平
							</button>
							<button class="anniu_btn" onclick="refreshHandle()" >
								手动刷新
							</button>
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="resultList"
											autoIncludeParameters="${empty param.autoInc}"
											var="customerFund"
											action="${basePath}/customerClosePosition/customerList/list.action?sortName=primary.riskRate"
											title="" minHeight="345" listWidth="155%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 	style="table-layout:fixed" >
											<ec:row ondblclick="">
												<ec:column width="2%" property="_0" title="序号"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<ec:column property="memberNo[like]" title="综合会员编号"
													width="5%" style="text-align:left; "
													value="${customerFund.memberNo}" ellipsis="true" />
												<ec:column property="memberName[like]" title="综合会员名称"
													width="7%" style="text-align:left; "
													value="${customerFund.memberName}" ellipsis="true" />
												<ec:column property="organizationname[like]" title="机构"
													width="5%" style="text-align:left; "
													value="${customerFund.organizationname}" ellipsis="true" />
												<ec:column property="brokeragename[like]" title="居间"
													width="5%" style="text-align:left; "
													value="${customerFund.brokeragename}" ellipsis="true" />
												<ec:column property="customerNo[like]" title="交易账号"
													width="7%" style="text-align:left; "
													value="${customerFund.customerNo}" ellipsis="true" />
												<ec:column property="customerName[like]" title="客户名称"
													width="5%" style="text-align:left; "
													value="${customerFund.customerName}"  ellipsis="true"/>
												<ec:column property="beginningcaptical[=][double]" title="期初权益"
													width="6%" style="text-align:right; ">
													<fmt:formatNumber value="${customerFund.beginningcaptical}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="runtimefundio[=][double]" title="出入金"
													width="6%" style="text-align:right; ">
													<fmt:formatNumber value="${customerFund.runtimefundio}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="runtimeclosepl[=][double]" title="平仓盈亏"
													width="5%" style="text-align:right; ">
													<fmt:formatNumber value="${customerFund.runtimeclosepl}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="floatingloss[=][double]" title="持仓盈亏"
													width="5%" style="text-align:right; ">
													<fmt:formatNumber value="${customerFund.floatingloss}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="runtimefee[=][double]" title="手续费"
													width="4%" style="text-align:right; ">
													<fmt:formatNumber value="${customerFund.runtimefee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="margin[=][double]" title="占用保证金"
													width="6%" style="text-align:right; ">
													<fmt:formatNumber value="${customerFund.margin}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="livemargin[=][double]" title="可用保证金"
													width="6%" style="text-align:right; ">
													<fmt:formatNumber value="${customerFund.livemargin}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="frozenmargin[=][double]" title="冻结保证金"
													width="6%" style="text-align:right; ">
													<fmt:formatNumber value="${customerFund.frozenmargin}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="frozenfee[=][double]" title="冻结手续费"
													width="5%" style="text-align:right; ">
													<fmt:formatNumber value="${customerFund.frozenfee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="presentcaptical[=][double]"
													title="当前权益" width="6%" style="text-align:right; ">
													<fmt:formatNumber value="${customerFund.presentcaptical}"
														pattern="#,##0.00"></fmt:formatNumber>
												</ec:column>

												<ec:column property="riskRate[=][String]" title="风险率"
													width="3%" style="text-align:right;"
													value="${customerFund.riskRate_log}" ellipsis="true"
													/>
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
		<textarea id="esc_fundFlowType" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="fundFlowType[=]">
			<ec:options items="fundFlowMap" />
		</select>
	    </textarea>
	</body>
</html>

<SCRIPT type="text/javascript">
function search1(){
	frm.submit();
}
function myReset2() {//清空
	//frm.reset();
	var pathRrl=frm.action.toString();
	var au = '111111';
	if (typeof (AUsessionId) != "undefined") {
		au = AUsessionId;
	}
	var urlArray=pathRrl.split("?");
  if(urlArray.length==1){
		pathRrl=pathRrl+'?AUsessionId='+au+"";
	}else if(urlArray.length==2){
		pathRrl=pathRrl+'&AUsessionId='+au+"";
	}
	window.location.href=pathRrl;
}
function closePosition(){
	var url = '${basePath}/customerClosePosition/customerList/forwardUpdate.action';
	ecsideDialog(url,null,600,500);
}
function refreshHandle(){
	ec.submit();	
}
</SCRIPT>