<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>客户列表</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
							<form name="frm"
								action="${basePath}/account/customer/customerList.action"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td width="775" height="40">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0" style="table-layout: fixed">
													<tr>
														<td width="220" align="left">
															客户归属:
															<label>
																<input type="text" id="selectIds"
																	name="${GNNT_}selectIds"
																	value="${oldParams['selectIds'] }" class="input_text" readonly='readonly'/><a href="javascript:clickText();"><img
																	align="absmiddle" src="<%=skinPath%>/cssimg/kh.gif">
															</a>
															</label>
															<input type="hidden" name="${GNNT_}isRelated"
																id="isRelated" value="${oldParams['isRelated']}"
																class="input_text">
															<input type="hidden" name="${GNNT_}memberIds"
																id=memberIds value="${oldParams['memberIds']}"
																class="input_text">
															<input type="hidden" id="brokerageIds"
																name="${GNNT_}brokerageIds"
																value="${oldParams['brokerageIds'] }" class="input_text" />
															<input type="hidden" id="organizationIds"
																name="${GNNT_}organizationIds"
																value="${oldParams['organizationIds'] }"
																class="input_text" />
															<input type="hidden" id="managerIds"
																name="${GNNT_}managerIds"
																value="${oldParams['managerIds'] }" class="input_text" />
															
														</td>
														<td class="table3_td_1" align="left">
															交易账号:
															<label>
																<input type="text" id="customerId"
																	name="${GNNT_}primary.customerNo[like]"
																	value="${oldParams['primary.customerNo[like]'] }"
																	class="input_text" />
															</label>
														</td>
														<td class="table3_td_1" align="left">
															客户名称:
															<label>
																<input type="text" id="customerName"
																	name="${GNNT_}primary.name[like]"
																	value="${oldParams['primary.name[like]'] }"
																	class="input_text" />
															</label>
														</td>
														<td class="table3_td_1" align="left">
															客户状态:
															<label>
																<select id="customerStatus"
																	name="${GNNT_}customerStatus.status[=]"
																	style="width: 120px;">
																	<option value="">
																		请选择
																	</option>
																	<c:forEach items="${firmStatusMap}" var="maps">
																		<option value="${maps.key}">
																			${maps.value}
																		</option>
																	</c:forEach>
																</select>
																<script type="text/javascript">
																	document.getElementById('customerStatus').value = "${oldParams['customerStatus.status[=]']}";
																</script>
															</label>
														</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" onClick="select112()">
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
						<div class="div_gn">
							<button class="anniu_btn" onClick="addCustomer()"
								id="addCustomer">
								添加
							</button>
						</div>

						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="resultList"
											autoIncludeParameters="${empty param.autoInc}" var="customer"
											action="${basePath}/account/customer/customerList.action"
											title="" minHeight="345" listWidth="200%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											toolbarContent="navigation|pagejump |pagesize|refresh|extend|status"
											style="table-layout:fixed">
											
											<ec:row recordKey="${customer.customerNo}">
												<ec:column width="2%" property="_0" title="序号"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<ec:column property="primary.customerNo[like]" width="6%"
													title="交易账号" style="text-align:left; ">
													<a href="#" class="blank_a"
														onclick="forwardUpdate('${customer.customerNo}');"><font
														color="#880000">${customer.customerNo}</font> </a>
												</ec:column>
												
												<ec:column property="primary.name[Like]" width="4%"
													title="客户名称"
													style="text-align:left;overflow:hidden;text-overflow:ellipsis"
													tipTitle="${customer.name}">
													<nobr>
														${customer.name}
													</nobr>
												</ec:column>
												<ec:column property="primary.organizationName[like]" width="5%"
													title="所属机构"
													style="text-align:left;overflow:hidden;text-overflow:ellipsis"
													value="${customer.organizationName}"
													tipTitle="${customer.organizationName}" />
												<ec:column property="primary.brokerageName[like]" width="5%"
													title="所属居间"
													style="text-align:left;overflow:hidden;text-overflow:ellipsis"
													value="${customer.brokerageName}"
													tipTitle="${customer.brokerageName}" />
												<ec:column property="customerStatus.status[=]" width="3%"
													title="客户状态" style="text-align:center;"
													editTemplate="ecs_t_statusType">
		  												${firmStatusMap[customer.status]}
												</ec:column>
												<ec:column property="primary.createTime[=][Date]" width="6%"
													title="开户时间" style="text-align:left;" filterable="false"
													value="${datefn:formatdate(customer.createTime)}">
												</ec:column>

												<ec:column property="primary.signTime[=][Date]" width="6%"
													title="签约时间" style="text-align:left;" filterable="false"
													value="${datefn:formatdate(customer.signTime)}">
												</ec:column>
												<ec:column property="primary.activateTime[=][Date]"
													width="6%" title="激活时间" style="text-align:left;" filterable="false"
													value="${datefn:formatdate(specialMemberInfo.demiseTime)}">
													<fmt:formatDate value="${customer.activateTime}"
														type="both" />
												</ec:column>
												<ec:column property="primary.freezeTime[=][Date]" width="6%"
													title="冻结时间" style="text-align:left;" filterable="false"
													value="${datefn:formatdate(customer.freezeTime)}">
												</ec:column>
												<ec:column property="primary.demiseTime[=][Date]" width="6%"
													title="注销时间" style="text-align:left;" filterable="false"
													value="${datefn:formatdate(customer.demiseTime)}">
												</ec:column>
												<ec:column property="primary.useFunds[=][bigdecimal]"
													width="5%" title="可用资金" style="text-align:right;">
													<fmt:formatNumber value="${customer.useFunds}" pattern="#,##0.00"/>
												</ec:column>
												<ec:column property="primary.papersType[=][int]" width="3%"
													title="证件类型" style="text-align:left;"
													editTemplate="ecs_t_papersType">
													<c:set var="typeKey">
														<c:out value="${customer.papersType}"></c:out>
													</c:set>
							  						${papersTypeMap[typeKey]}
							  						</ec:column>
												<ec:column property="primary.papersName[like]" width="7%"
													title="证件号码"
													style="text-align:left;overflow:hidden;text-overflow:ellipsis"
													value="${customer.papersName}"
													tipTitle="${customer.papersName}" />
												<ec:column property="primary.email[like]" width="6%"
													title="电子邮箱" style="text-align:left;overflow:hidden;text-overflow:ellipsis"
													value="${customer.email}" filterable="false" tipTitle="${customer.email}"/>
												<ec:column property="primary.phone[like]" width="4%"
													title="电话" style="text-align:left;" tipTitle="${customer.phone}"
													value="${customer.phone}" filterable="false"/>
												<ec:column property="primary.fax[like]" width="4%" tipTitle="${customer.fax}"
													title="传真" style="text-align:left;" value="${customer.fax}" filterable="false"/>
												<ec:column property="primary.postCode[like]" width="3%" tipTitle="${customer.fax}"
													title="邮编" style="text-align:left;"
													value="${customer.postCode}" filterable="false"/>
												
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

		<!-- 编辑和过滤所使用的证件类型模板 -->
		<textarea id="ecs_t_papersType" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="customer.papersType[=][int]">
			<ec:options items="papersTypeMap" />
		</select>
	    </textarea>
		<!-- 编辑和过滤所使用的客户状态模板 -->
		<textarea id="ecs_t_statusType" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="customer.status[=][int]">
			<ec:options items="firmStatusMap" />
		</select>
	    </textarea>
		<textarea id="ecs_t_openstatus" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="openstatus[=][int]">
			<ec:options items="openstatusMap" />
		</select>
	    </textarea>
		<script type="text/javascript">

function clickText() {
	var isRelated=document.getElementById("isRelated").value;
	var url = "${basePath}/broke/treeForMemberInfo/forTree.action?isRelated="+isRelated;
	if (checkie()) {
		result = ecsideDialog(url, window, 500, 650);
	} else {
		result = ecsideDialog(url, window, 500, 650);
	}
}
function forwardUpdate(id) {
	var url = "${basePath}/account/customer/forwardUpdate.action?obj.customerNo="
			+ id;
	ecsideDialog(url, "", 680, 550);
}

function addCustomer() {
	var url = "${basePath}/account/customer/forwardAdd.action";
	ecsideDialog(url, "", 680, 580);
}
function select112() {
	var url = "${basePath}/account/customer/list.action";
	frm.submit();
}
function resetSelect() {
	var url = "${basePath}/account/customer/list.action";
	frm.customerId.value = "";
	frm.customerName.value = "";
	frm.submit();
}

function deleteByCheckBox() {
	var url = "${basePath}/account/customer/delete.action";
	deleteEcside(ec.ids, url);
}

function forwardUpdatePW(id) {
	var url = "${basePath}/account/customer/forwardUpdatePassward.action?obj.customerNo="
			+ id;
	ecsideDialog(url, "", 580, 280);
}
function forwardUpdateTradePW(id) {
	var url = "${basePath}/account/customer/forwardUpdatePasswardTrade.action?obj.traderID="
			+ id;
	ecsideDialog(url, "", 580, 280);
}
</SCRIPT>
	</body>
</html>