<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>客户列表</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
	</head>
	<IMPORT namespace="MEBS"
		implementation="${basePath}/common/jslib/calendar.htc">

	<body xmlns:MEBS>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
							<form name="frm"
								action="${basePath}/account/customer/customerList.action"
								method="post">
								<input type="hidden" name="${GNNT_}isRelated" id="isRelated"
									value="${oldParams['isRelated']}" class="input_text">
								<input type="hidden" name="${GNNT_}memberInfoIds"
									id="memberInfoIds" value="${oldParams['memberInfoIds']}"
									class="input_text">
								<input type="hidden" name="${GNNT_}organizationIds"
									id="organizationIds" value="${oldParams['organizationIds']}"
									class="input_text">
								<input type="hidden" name="${GNNT_}managerIds" id="managerIds"
									value="${oldParams['managerIds']}" class="input_text">
								<input type="hidden" name="${GNNT_}brokerageIds"
									id="brokerageIds" value="${oldParams['brokerageIds']}"
									class="input_text">
								<input type="hidden" name="tree" id="tree" value="${tree}"
									class="input_text">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td height="40" width="775">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0" style="table-layout: fixed">
													<tr>
														<td width="220" align="left">
															客户归属:&nbsp;
															<label>
																<input type="text" id="memberNames"
																	name="${GNNT_}selectIds"
																	value="${oldParams['selectIds']}" readonly=true
																	size="8" class="input_text">
																<a href="javascript:clickText();"><img
																		align="absmiddle" src="<%=skinPath%>/cssimg/kh.gif">
																</a>
															</label>
														</td>
														<td class="table3_td_1" align="left">
															交易账号:&nbsp;
															<label>
																<input type="text" id="customerId"
																	name="${GNNT_}primary.customerNo[like]"
																	value="${oldParams['primary.customerNo[like]'] }"
																	class="input_text" />
															</label>
														</td>
														<td class="table3_td_1" align="left">
															客户名称:&nbsp;
															<label>
																<input type="text" id="customerName"
																	name="${GNNT_}primary.name[like]"
																	value="${oldParams['primary.name[like]'] }"
																	class="input_text" />
															</label>
														</td>
														<td class="table3_td_1" align="left">
															客户状态:&nbsp;
															<label>
																<select id="customerStatus"
																	name="${GNNT_}primary.customerStatus.status[=]"
																	style="width: 100px;">
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
																	document.getElementById('customerStatus').value = "${oldParams['primary.customerStatus.status[=]']}";
																</script>
															</label>
														</td>
														<td class="table3_td_1" align="left">
															激活日期:&nbsp;
															<label>
																<input type="text" style="width: 100px" id="beginDate"
																	class="wdate" maxlength="10"
																	name="${GNNT_}primary.activateTime[=][date]"
																	value='${oldParams["primary.activateTime[=][date]"]}'
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															</label>
														</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" onclick="select1();">
																查询
															</button>
															&nbsp;
															<button class="btn_cz" onclick="myReset()">
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
							<button class="anniu_btn" onclick="addCustomer()"
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
												<ec:column property="primary.customerNo[like]" width="5%"
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
												<ec:column property="memberInfo.name[like]" width="5%"
													title="所属会员"
													style="text-align:left;overflow:hidden;text-overflow:ellipsis"
													value="${customer.memberName}"
													tipTitle="${customer.memberName}" />
												<ec:column property="primary.customerStatus.status[=]" width="3%"
													title="客户状态" style="text-align:center;"
													editTemplate="ecs_t_statusType">
		  												${firmStatusMap[customer.status]}
												</ec:column>
												<ec:column property="primary.createTime[=][Date]" width="5%"
													title="开户时间" style="text-align:left;"
													value="${datefn:formatdate(customer.createTime)}"
													filterable="false">
												</ec:column>
												<ec:column property="primary.activateTime[=][Date]"
													width="5%" title="激活时间" style="text-align:left;"
													value="${datefn:formatdate(customer.activateTime)}"
													filterable="false">
												</ec:column>
												<ec:column property="primary.papersType[=][int]" width="3%"
													title="证件类型" style="text-align:left;"
													editTemplate="ecs_t_papersType">
													<c:set var="typeKey">
														<c:out value="${customer.papersType}"></c:out>
													</c:set>
							  						${papersTypeMap[typeKey]}
							  						</ec:column>
												<ec:column property="primary.papersName[like]" width="5%"
													title="证件号码"
													style="text-align:left;overflow:hidden;text-overflow:ellipsis"
													value="${customer.papersName}"
													tipTitle="${customer.papersName}" />
												<ec:column property="primary.organizationName[like]"
													width="5%" title="所属机构"
													style="text-align:left;overflow:hidden;text-overflow:ellipsis"
													value="${customer.organizationName}"
													tipTitle="${customer.organizationName}" />
												<ec:column property="primary.brokerageName[like]" width="5%"
													title="所属服务中心"
													style="text-align:left;overflow:hidden;text-overflow:ellipsis"
													value="${customer.brokerageName}"
													tipTitle="${customer.brokerageName}" />

												<ec:column property="primary.signTime[=][Date]" width="5%"
													title="签约时间" style="text-align:left;"
													value="${datefn:formatdate(customer.signTime)}"
													filterable="false">
												</ec:column>
												<ec:column property="primary.freezeTime[=][Date]" width="5%"
													title="冻结时间" style="text-align:left;"
													value="${datefn:formatdate(customer.freezeTime)}"
													filterable="false">
												</ec:column>
												<ec:column property="primary.demiseTime[=][Date]" width="5%"
													title="注销时间" style="text-align:left;"
													value="${datefn:formatdate(customer.demiseTime)}"
													filterable="false">
												</ec:column>
												<ec:column property="primary.useFunds[>=][bigdecimal]"
													width="5%" title="可用资金" style="text-align:right;">
													<fmt:formatNumber value="${customer.useFunds}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="primary.email[like]" width="5%"
													title="电子邮箱"
													style="text-align:left;overflow:hidden;text-overflow:ellipsis"
													value="${customer.email}" filterable="false"
													tipTitle="${customer.email}" />
												<ec:column property="primary.phone[like]" width="4%"
													title="电话" style="text-align:left;" tipTitle="${customer.phone}"
													value="${customer.phone}" filterable="false" />
												<ec:column property="primary.fax[like]" width="4%"
													title="传真" style="text-align:left;"
													tipTitle="${customer.fax}" value="${customer.fax}"
													filterable="false" />
												<ec:column property="primary.postCode[like]" width="4%"
													title="邮编" style="text-align:left;" tipTitle="${customer.postCode}"
													value="${customer.postCode}" filterable="false" />
												<ec:column property="3" title="电话密码" filterable="false"
													sortable="false" style="text-align:center;" width="3%">
													<a
														href="javascript:forwardUpdatePhonePW('${customer.customerNo}');"
														class="blank_a"><font color="#880000">修改</font> </a>
												</ec:column>
												<ec:column property="5" title="交易密码" filterable="false"
													sortable="false" style="text-align:center;" width="3%">
													<a
														href="javascript:forwardUpdatePW('${customer.customerNo}');"
														class="blank_a"><font color="#880000">修改</font> </a>
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
		<!-- 编辑和过滤所使用的证件类型模板 -->
		<textarea id="ecs_t_papersType" rows="" cols="" style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="customer.papersType[=][int]">
			<ec:options items="papersTypeMap" />
		</select>
	    </textarea>
		<!-- 编辑和过滤所使用的客户状态模板 -->
		<textarea id="ecs_t_statusType" rows="" cols="" style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="customer.status[=][int]">
			<ec:options items="firmStatusMap" />
		</select>
	    </textarea>
		<textarea id="ecs_t_openstatus" rows="" cols="" style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="openstatus[=][int]">
			<ec:options items="openstatusMap" />
		</select>
	    </textarea>
		<SCRIPT type="text/javascript">
	function clickText() {
		var isRelated=document.getElementById("isRelated").value;
		var url = "${basePath}/broke/memberInfoTree/forTree.action?isRelated="+isRelated;
		if (checkie()) {
			result = ecsideDialog(url, window, 400, 570);
		} else {
			result = ecsideDialog(url, window, 400, 570);
		}
	}
	function forwardUpdate(id) {
		var url = "${basePath}/account/customer/forwardUpdate.action?obj.customerNo="
				+ id;
		ecsideDialog(url, "", 710, 530);
	}

	function addCustomer() {
		var url = "${basePath}/account/customer/forwardAdd.action";
		//ec.action=url;
		//ec.submit();
		ecsideDialog(url, "", 710, 580);
	}
	function select1() {
		frm.action="${basePath}/account/customer/customerList.action";
		frm.submit();
	}

	function deleteByCheckBox() {
		var url = "${basePath}/account/customer/delete.action";
		deleteEcside(ec.ids, url);
	}
	function forwardUpdatePW(id) {
		var url = "${basePath}/account/customer/forwardUpdatePassward.action?obj.traderID="
				+ id;
		ecsideDialog(url, "", 580, 260);
	}
	function forwardUpdatePhonePW(id) {
		var url = "${basePath}/account/customer/forwardUpdatePasswardPhone.action?obj.customerNo="
				+ id;
		ecsideDialog(url, "", 580, 260);
	}
</SCRIPT>
	</body>
</html>