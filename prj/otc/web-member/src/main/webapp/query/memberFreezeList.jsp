
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>
<html xmlns:MEBS>
	<head>
		<title>综合会员冻结查询</title>
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
								action="${basePath}/query/queryMemberFreezeSearch/list.action?sortName=primary.memberName"
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
																	style="width: 120" onchange="changeOn()">
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
																<input type="text" style="width: 120px" id="beginDate"
																	class="wdate" maxlength="10"
																	name="${GNNT_}primary.h_date[>=][date]"
																	value='${oldParams["primary.h_date[>=][date]"]}'
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															</label>
														</td>
														<td class="table3_td_1" align="left">
															结束日期:&nbsp;
															<label>
																<input type="text" style="width: 120px" id="endDate"
																	class="wdate" maxlength="10"
																	name="${GNNT_}primary.h_date[<=][date]"
																	value='${oldParams["primary.h_date[<=][date]"]}'
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															</label>
														</td>
														<td class="table3_td_1tjcx" align="left">
															商品名称:&nbsp;
															<lable>
															<select id="commodityId"
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
															</lable>
															<script type="text/javascript">
frm.commodityId.value = '${oldParams["primary.commodityName[like]"] }';
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
											var="memberFreeze"
											action="${basePath}/query/queryMemberFreezeSearch/list.action"
											title="" minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" csvFileName="导出列表.csv"
											style="table-layout:fixed">
											<ec:row ondblclick="">
												<ec:column width="4%" property="_0" title="序号"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<c:if test="${queryType=='H'}">
													<ec:column property="h_date[=][date]" title="结算日期"
														width="12%" style="text-align:left; "
														value="${datefn:formatdate(memberFreeze.h_date)}"
														ellipsis="true" />
												</c:if>
												<ec:column property="primary.commodityName[like]"
													title="商品名称" width="10%" style="text-align:left; "
													value="${memberFreeze.commodityName}" ellipsis="true" />
												<ec:column property="primary.memberNo[like]" title="综合会员编号"
													width="10%" style="text-align:left; "
													value="${memberFreeze.memberNo}" ellipsis="true" />
												<ec:column property="primary.memberName[like]"
													title="综合会员名称" width="10%" style="text-align:left; "
													value="${memberFreeze.memberName}" ellipsis="true" />
												<ec:column property="primary.freeze_date[=][date]"
													title="冻结日期" width="12%" style="text-align:left; "
													ellipsis="true">
													<fmt:formatDate value="${memberFreeze.freeze_date}"
														pattern="yyyy-MM-dd" />
												</ec:column>
												<ec:column property="primary.trade_date[=][date]"
													title="交易日期" width="12%" style="text-align:left; "
													ellipsis="true">
													<fmt:formatDate value="${memberFreeze.trade_date}"
														pattern="yyyy-MM-dd" />
												</ec:column>
												<ec:column property="primary.b_price[=][double]" title="买价"
													width="10%" style="text-align:right; ">
													<fmt:formatNumber value="${memberFreeze.b_price}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="primary.s_price[=][double]" title="卖价"
													width="10%" style="text-align:right; ">
													<fmt:formatNumber value="${memberFreeze.s_price}"
														pattern="#,##0.00" />
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
			  //ec.action="${basePath}/query/queryMemberFreezeSearch/list.action?queryType=D&sortName=memberNo";
			 // frm.action="${basePath}/query/queryMemberFreezeSearch/list.action?queryType=D&sortName=memberNo";
			}
			else if(value=='H')
			{
			   frm.beginDate.disabled=false;
			   frm.endDate.disabled=false;
			   frm.beginDate.style.backgroundColor="#ffffff";
frm.endDate.style.backgroundColor="#ffffff";
			   frm.queryType.value='H';
			  //  ec.action="${basePath}/query/queryMemberFreezeSearch/list.action?queryType=H&sortName=memberNo";
			   //  frm.action="${basePath}/query/queryMemberFreezeSearch/list.action?queryType=H&sortName=memberNo";
			}
		}
		
		</SCRIPT>