
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>
<html xmlns:MEBS>
	<head>
		<title>客户交割申报查询</title>
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
								action="${basePath}/query/queryCustomerApplySettleSearch/list.action?sortName=primary.customerNo"
								method="post">
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
																<input type="hidden" name="${GNNT_}memberIds"
																id=memberIds value="${oldParams['memberIds']}"
																class="input_text">
																<input type="hidden" id="brokerageIds"
																	name="${GNNT_}brokerageIds"
																	value="${oldParams['brokerageIds'] }"
																	class="input_text" />
																<input type="hidden" id="organizationIds"
																	name="${GNNT_}organizationIds"
																	value="${oldParams['organizationIds'] }"
																	class="input_text" />
																<input type="hidden" id="managerIds"
																	name="${GNNT_}managerIds"
																	value="${oldParams['managerIds'] }" class="input_text" />
																<td class="table3_td_1tjcx" align="left">
																	客户归属：
																	<input type="text" id="selectIds"
																	name="${GNNT_}selectIds"
																	value="${oldParams['selectIds'] }" class="input_textmin" readonly='readonly'/>
																	<a href="javascript:clickText();">
																	<img align="absmiddle" src="<%=skinPath%>/cssimg/kh.gif">
																</td>
														<td class="table3_td_1mid" align="left">
															交易账号:
															<label>
																<input type="text" class="input_text" id="customerId"
																	name="${GNNT_}customerNo[like]" size="14"
																	value="${oldParams['customerNo[like]'] }" />
															</label>
														</td>
														<td class="table3_td_1" align="left">
															客户名称:
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
															</button>&nbsp;
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
											var="customerFund"
											action="${basePath}/query/queryCustomerApplySettleSearch/list.action?sortName=primary.customerNo"
											title="" minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" csvFileName="导出列表.csv"
											style="table-layout:fixed">
											<ec:row ondblclick="">
												<ec:column width="3%" property="_0" title="序号"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
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
													value="${customerFund.customerName}" ellipsis="true" />
												<ec:column property="commodityName[like]" title="商品"
													width="5%" style="text-align:left; "
													value="${customerFund.commodityName}" ellipsis="true" />
												<ec:column property="price[=][double]" title="交收价格"
													width="5%" style="text-align:right; ">
													<fmt:formatNumber value="${customerFund.price}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="quantity[=][int]" title="交收数量"
													width="3%" style="text-align:left; ">
													<fmt:formatNumber value="${customerFund.quantity}"
														pattern="#,##0" />
												</ec:column>
												<ec:column property="bs_flag[=][int]" title="买卖标志"
													width="3%" style="text-align:left; "
													editTemplate="esc_bsFlag" ellipsis="true">
													<c:set var="typeKey">
														<c:out value="${customerFund.bs_flag}"></c:out>
													</c:set>
		  											${firstMap[typeKey]}
												</ec:column>
												<ec:column property="updateTime[=][date]" title="更新时间"
													width="7%" style="text-align:left; "
													value="${datefn:formatdate(customerFund.updateTime)}"
													ellipsis="true" />
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
function search1(){
			frm.submit();
		}
function init(queryType){
			 document.getElementById("beginDate").disabled=true;
			document.getElementById("endDate").disabled=true;
			change(queryType);
		}
	function clickText() {
	var url = "${basePath}/broke/treeForMemberInfo/forTree.action";
	ecsideDialog(url, window, 500,650);

}
		</SCRIPT>