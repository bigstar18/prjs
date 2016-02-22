<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>销户回收站</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
						<form name="frm"
								action="${basePath}/account/customerRecycle/list.action"
								method="post">
							<table border="0" cellpadding="0" cellspacing="0"
								class="table2_style">
								<tr>
									<td class="table2_td_width">
										<div class="div2_top">
											<table class="table3_style" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td class="table3_td_1" align="left">
														交易账号:&nbsp;
														<label>
															<input type="text" class="input_text"
																id="customerId"
																name="${GNNT_}primary.customerNo[like]"
																value="${oldParams['primary.customerNo[like]'] }" />
														</label>
													</td>
													<td class="table3_td_1" align="left">
														客户名称:&nbsp;
														<label>
															<input type="text" class="input_text"
																id="customerName"
																name="${GNNT_}primary.name[like]"
																value="${oldParams['primary.name[like]'] }" />
														</label>
													</td>
													<td class="table3_td_anniu" align="left">
														<button  class="btn_sec" onclick="select1()">查询</button>&nbsp;&nbsp;
														<button class="btn_cz" onclick="myReset()">重置</button>
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
											autoIncludeParameters="${empty param.autoInc}" var="customer"
											action="${basePath}/account/customerRecycle/list.action"
											title="" minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											toolbarContent="navigation|pagejump |pagesize|refresh|extend|status"
											style="table-layout:fixed">
											<ec:row recordKey="${customer.customerNo}">
												<ec:column width="3%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="primary.customerNo[like]" width="7%" title="交易账号"
													style="text-align:left; ">
													<a href="#" class="blank_a"
														onclick="return showCustomer('${customer.customerNo}');"><font
														color="#880000">${customer.id}</font>
													</a>
												</ec:column>
												<ec:column property="primary.name[Like]" width="6%" title="客户名称"
													style="text-align:left;overflow:hidden;text-overflow:ellipsis" value="${customer.name}" tipTitle="${customer.name}"/>
												<ec:column property="memberInfo.name[like]" width="9%"
													title="所属会员" style="text-align:left;overflow:hidden;text-overflow:ellipsis"
													value="${customer.memberName}" tipTitle="${customer.memberName}"/>
												<ec:column property="primary.papersType[=][int]" width="5%"
													title="证件类型" style="text-align:left;"
													editTemplate="ecs_t_papersType">
													<c:set var="papersTypeKey">
														<c:out value="${customer.papersType}"></c:out>
													</c:set>
							  							${papersTypeMap[papersTypeKey]}
												</ec:column>
												<ec:column property="primary.papersName[like]" width="9%" title="证件号码"
													style="text-align:left;overflow:hidden;text-overflow:ellipsis" value="${customer.papersName}" tipTitle="${customer.papersName}"/>
												<ec:column property="primary.phone[like]" width="6%" title="电话"
													style="text-align:left;" value="${customer.phone}" />
												<ec:column property="primary.fax[like]" width="6%" title="传真"
													style="text-align:left;" value="${customer.fax}" />
												<ec:column property="primary.postCode[like]" width="5%" title="邮编"
													style="text-align:left;" value="${customer.postCode}" />
												<ec:column property="primary.email[like]" width="8%" title="电子邮箱"
													style="text-align:left;overflow:hidden;text-overflow:ellipsis" value="${customer.email}" tipTitle="${customer.email}"/>
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

		<SCRIPT type="text/javascript">
		function showCustomer(id){
			var url="${basePath}/account/customerRecycle/forwardUpdate.action?obj.customerNo="+id;
			ecsideDialog(url,"",580,220);
		}
		function select1(){
			var url="${basePath}/account/customerRecycle/list.action";
			frm.submit();
		}
		</SCRIPT>
	</body>
</html>