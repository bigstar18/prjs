<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/session.jsp"%>
<%@ include file="/public/ecsideLoad.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>结算银行签约记录表</title>
			
		<link href="${basePath }/report/report_css.css" rel="stylesheet" type="text/css" />
		<import namespace="MEBS"
			implementation="${basePath}/report/public/calendar.htc">
	</head>

	<body class="report_body">
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
							<form name="frm"
								method="post" action="${basePath}/report/settlementBank/settlementBankReportQuary.action?sortName=primary.signtime&sortOrder=true">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_widthmax">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0" style="table-layout:fixed">
													<tr>
														<td class="table3_td_1" align="left">
															签约开始日期：&nbsp;
															<input type="text" style="width: 100px" id="startDate1"
															class="wdate" maxlength="10"
															name="${GNNT_}primary.signtime[>=][date]"
															value='${oldParams["primary.signtime[>=][date]"]}'
															onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"/>
														</td>
														<td class="table3_td_1" align="left">
															签约结束日期：
													<input type="text" style="width: 100px" id="endDate1"
															class="wdate" maxlength="10"
															name="${GNNT_}primary.signtime[<=][date]"
															value='${oldParams["primary.signtime[<=][date]"]}'
															onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">&nbsp;
															
														</td>
														<td class="table3_td_1" align="left">
															编号：&nbsp;
															<input type="text" id="firmid"
																name="${GNNT_}primary.firmId[like]"
																value="${oldParams['primary.firmId[like]'] }" size="14"
																class="input_textmin" />
														</td>
														</tr>
														<tr>
														<td class="table3_td_1" align="left">
															解约开始日期：
													<input type="text" style="width: 100px" id="startDate2"
															class="wdate" maxlength="10"
															name="${GNNT_}primary.cancletime[>=][date]"
															value='${oldParams["primary.cancletime[>=][date]"]}'
															onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"/>
														</td>
														<td class="table3_td_1" align="left">
															解约结束日期：
													<input type="text" style="width: 100px" id="endDate2"
															class="wdate" maxlength="10"
															name="${GNNT_}primary.cancletime[<=][date]"
															value='${oldParams["primary.cancletime[<=][date]"]}'
															onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"/>
														</td>
														<td class="table3_td_1" align="left">
															名称：
															<input type="text" id="firmid"
																name="${GNNT_}primary.name[like]"
																value="${oldParams['primary.name[like]'] }" size="14"
																class="input_textmin" />
														</td>
														</tr>
													<tr>
														<td class="table3_td_1" align="left">
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;账户类型： 
												 	<span class="right_03zi"><select id="firmtype" name="${GNNT_}firmtype[=][String]" style="width: 100px">
												 		<option value="">全部</option>
												 		<option value="M">综合会员</option>
												 		<option value="B">经纪会员</option>
												 		<option value="S">特别会员</option>
												 		<option value="C">交易客户</option>
												 	</select></span>
															<script type="text/javascript">
																frm.firmtype.value='${oldParams["firmtype[=][String]"] }';
															</script>
														</td>
														<td class="table3_td_1" align="left">
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;签约银行：
												 	<span class="right_03zi"><select id="bank"
																name="${GNNT_}bankId[=][String]"
																class="input_textmin">
																<option value="">
																	请选择
																</option>
																<c:forEach items="${bankList}" var="bank">
																	<option value="${bank.bankId}">
																		${bank.bankName }
																	</option>
																</c:forEach>
															</select></span>
															<script type="text/javascript">
																frm.bank.value='${oldParams["bankId[=][String]"] }';
															</script>
														</td>
														<td class="table3_td_anniu" align="left">
															<input type="button" class="button_02" onclick="select1()"
														value="查询" />&nbsp;
															<input type="button" class="button_03"
																onclick="myReset()" value="重置" />&nbsp;
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
											var="settlementBank"
											action="${basePath}/report/settlementBank/settlementBankReportQuary.action?sortName=primary.signtime&sortOrder=true"
											title="" minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="导出列表.csv" 
											style="table-layout:fixed">
											<ec:row ondblclick="">
												<ec:column width="3%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												
												<ec:column property="firmId[=][String]" title="编号"
													width="5%" style="text-align:left; "
													value="${settlementBank.firmId}" ellipsis="true">
												</ec:column>
												
												<ec:column property="contact[like]" title="交易账号"
													width="6%" style="text-align:left; "
													value="${settlementBank.contact}" ellipsis="true"/>
												<ec:column property="name[like]" title="名称"
												    width="6%" style="text-align:left; "
												    value="${settlementBank.name}" ellipsis="true"/>
												<ec:column property="bankname[like]" title="签约银行"
													width="4%" style="text-align:left; "
													value="${settlementBank.bankname}" ellipsis="true"/>
												<ec:column property="signtime[=][date]" title="签约时间"
												    width="6%" style="text-align:left; " ellipsis="true">
												    <fmt:formatDate value="${settlementBank.signtime}"    pattern="yyyy-MM-dd HH:mm:ss" />
												 </ec:column>
												 <ec:column property="cancletime[=][date]" title="解约时间" width="6%"
													style="text-align:left; "  ellipsis="true">
													<fmt:formatDate value="${settlementBank.cancletime}"   pattern="yyyy-MM-dd HH:mm:ss" />
												</ec:column>
												<ec:column property="firmtype[=][String]" title="账户类型"
												    width="3%" style="text-align:left; "
												     ellipsis="true">
													<c:set var="key">
														<c:out value="${settlementBank.firmtype}"></c:out>
													</c:set>
													${bankMap[key]}
													
													</ec:column>
											</ec:row>
										</ec:table>
									</td>
								</tr>
							</table>
						</div>
						<!-- 编辑和过滤所使用的建平仓模板 -->
						<textarea id="esc_bankMap" rows="" cols="" style="display: none">
						<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
								name="firmtype[=][String]">
							<ec:options items="bankMap" />
						</select>
					    </textarea>
					    
					    <!-- 编辑和过滤所使用的 通用的文本框模板-->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value=""
				onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="" />
		</textarea>
					</td>
				</tr>
					</table>
				</td>
			</tr>
		</table>
		</form>
	</body>
</html>
<script type="text/javascript">
	function select1(){
		var action=frm.action;
		frm.action="${basePath}/report/settlementBank/settlementBankReportQuary.action?sortName=primary.signtime&sortOrder=true";
		if(checkReportSDate(frm.startDate1.value,frm.endDate1.value,"签约开始日期","签约结束日期")&&checkReportSDate(frm.startDate2.value,frm.endDate2.value,"解约开始日期","解约结束日期")){
			frm.submit();
		};
		frm.action=action;
	}
function clickText() {
	var memberIds = frm.memberIds.value;
	var url = "${basePath}/report/customer/memberInfoList.action?oldMemberIds="
			+ memberIds;
	var result = window.showModalDialog(url,'',"dialogWidth=350px;dialogHeight=520px");
	if (result != null && result != '') {
		var result1 = result.split('####');
		frm.memberIds.value = result1[0];
		frm.memberNames.value = result1[1];
	}
}
function checkReportSDate(startDate,endDate,startName,endName) {
	
	
	var now = new Date();
	var   s   =   new   Date(Date.parse(startDate.replace(/-/g,   "/")));
	var   e   =   new   Date(Date.parse(endDate.replace(/-/g,   "/")));
	if (s!="" && s > now ) {
		alert(startName+"不能大于当前日期");
		return false;
	}else if(e!="" &&　e>now){
		alert(endName+"不能大于当前日期");
		return false;
	}
	else if(s>e){
		alert(startName+"不能大于"+endName);
		return false;
	}else{
		return true;
	}
	
}
</script>