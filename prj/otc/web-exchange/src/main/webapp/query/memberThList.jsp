
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>
<html xmlns:MEBS>
	<head>
		<title>综合会员阈值查询</title>
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
								action="${basePath}/query/queryMemberThSearch/list.action?sortName=primary.name&sortOrder=true"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_width">
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
														<td class="table3_td_1tjcx" align="left">
															综合会员编号:&nbsp;
															<label>
																<input type="text" name="${GNNT_}memberNo[like]"
																	id="memberNo" value="${oldParams['memberNo[like]'] }"
																	class="input_textmin" />
															</label>
														</td>
														<td class="table3_td_1tjcx" align="left">
															综合会员名称:&nbsp;
															<input type="text" id="memberNames"
																name="${ORIGINAL_}memberNames"
																value="${original_memberNames}" onclick="clickText()"
																readonly=true size="8" class="input_textmin">
															<input type="hidden" name="${ORIGINAL_}memberIds"
																id="memberIds" value="${original_memberIds}"
																class="input_text">
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
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;结束日期:&nbsp;
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
											autoIncludeParameters="${empty param.autoInc}" var="memberTh"
											action="${basePath}/query/queryMemberThSearch/list.action"
											title="" minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="导出列表.csv" style="table-layout:fixed">
											<ec:row ondblclick="">
												<ec:column width="4%" property="_0" title="序号"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<c:if test="${queryType=='H'}">
													<ec:column property="clearDate[=][date]" title="结算日期"
														width="12%" style="text-align:left; "
														value="${datefn:formatdate(memberTh.clearDate)}"
														ellipsis="true" />
												</c:if>
												<ec:column property="memberNo[like]" title="综合会员编号" width="8%"
													style="text-align:left; " value="${memberTh.memberNo}"
													ellipsis="true" />
												<ec:column property="name[like]" title="综合会员名称" width="8%"
													style="text-align:left; " value="${memberTh.name}"
													ellipsis="true" />
												<ec:column property="memberMarginTh[=][double]"
													title="综合会员资金预警阈值" width="12%" style="text-align:right; ">
													<fmt:formatNumber value="${memberTh.memberMarginTh}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="customerMarginTh[=][double]"
													title="客户资金预警阈值" width="12%" style="text-align:right; ">
													<fmt:formatNumber value="${memberTh.customerMarginTh}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="memberFreezeTh[=][double]"
													title="综合会员冻结阈值比例" width="12%" style="text-align:right; ">
													<fmt:formatNumber value="${memberTh.memberFreezeTh_v}"
														pattern="0.00" />%
												</ec:column>
												<ec:column property="memberNetHoldTh[=][double]"
													title="综合会员出金阈值比例" width="12%" style="text-align:right; ">
													<fmt:formatNumber value="${memberTh.memberNetHoldTh_v}"
														pattern="0.00" />%
												</ec:column>
												<ec:column property="traderate[=][double]" title="综合会员非客户头寸交易比例"
													width="10%" style="text-align:right; " >
													<fmt:formatNumber value="${memberTh.traderate_v}"
														pattern="0.00" />%
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
		<textarea id="esc_statusType" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="state[=]">
			<ec:options items="statusMap" />
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
			 // ec.action="${basePath}/query/queryMemberThSearch/list.action?queryType=D&sortName=memberNo";
			//frm.action="${basePath}/query/queryMemberThSearch/list.action?queryType=D&sortName=memberNo";
			}
			else if(value=='H')
			{
			   frm.beginDate.disabled=false;
			   frm.endDate.disabled=false;
			   
frm.beginDate.style.backgroundColor="#ffffff";
frm.endDate.style.backgroundColor="#ffffff";
			   frm.queryType.value='H';
			 //   ec.action="${basePath}/query/queryMemberThSearch/list.action?queryType=H&sortName=memberNo";
			//	frm.action="${basePath}/query/queryMemberThSearch/list.action?queryType=H&sortName=memberNo";
			}
		}
function clickText() {
	var memberIds = frm.memberIds.value;
	var url = "${basePath}/report/customer/memberInfoList.action?oldMemberIds="
			+ memberIds;
	var result = window.showModalDialog(url, '',
			"dialogWidth=350px;dialogHeight=520px");
	if (result != null && result != '') {
		var result1 = result.split('####');
		frm.memberIds.value = result1[0];
		frm.memberNames.value = result1[1];
	}
}
		</SCRIPT>