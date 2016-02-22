<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>客户列表</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
						<form name="frm"
								action="${basePath}/broke/customerTransfer/customerTransferList.action"
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
													<td class="table3_td_anniu" align="left">
														<button class="btn_sec" onclick="select1()">查询</button>&nbsp;&nbsp;
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
											action="${basePath}/broke/customerTransfer/customerTransferList.action"
											title="" minHeight="345" listWidth="100%" height="460"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="导出列表.csv"   style="table-layout:fixed">

											<ec:row recordKey="${customer.customerNo}">
											    <ec:column width="6%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="primary.customerNo[like]" width="15%"
													title="交易账号" style="text-align:left; ">
													<a href="#" class="blank_a"
														onclick="forwardUpdate('${customer.customerNo}');"><font
														color="#880000">${customer.customerNo}</font>
													</a>
												</ec:column>
												<ec:column property="primary.name[Like]" width="19%"
													title="客户名称" style="text-align:left;overflow:hidden;text-overflow:ellipsis"
													value="${customer.name}" tipTitle="${customer.name}"/>
												<ec:column property="memberInfo.name[like]" width="15%"
													title="所属会员" style="text-align:left;overflow:hidden;text-overflow:ellipsis"
													value="${customer.memberName}" tipTitle="${customer.memberName}"/>
												<ec:column property="primary.organizationName[like]" width="15%"
													title="所属机构" style="text-align:left;overflow:hidden;text-overflow:ellipsis"
													value="${customer.organizationName}" tipTitle="${customer.organizationName}"/>
												<ec:column property="primary.brokerageName[like]" width="15%"
													title="所属居间" style="text-align:left;overflow:hidden;text-overflow:ellipsis"
													value="${customer.brokerageName}" tipTitle="${customer.brokerageName}"/>
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
		<SCRIPT type="text/javascript">
		function clickText(){
			var memberIds = frm.memberIds.value;
			var url="${basePath}/broke/customer/memberList.action?oldMemberIds="+memberIds;
			var result=ecsideDialog(url,null);
			if(result!=null&&result!=''){
				var result1=result.split('####');
				frm.memberIds.value=result1[0];
				frm.memberNames.value=result1[1];
			}
		}
		function forwardUpdate(id){
			var url="${basePath}/broke/customerTransfer/forwardCustomerTransfer.action?obj.customerNo="+id;
			ecsideDialog(url,"",600,300);
		}
		
		function addCustomer(){
			var url="${basePath}/broke/customer/forwardAdd.action";
			var result=ecsideDialog(url,null,600,500);
			if(result>0)
			{
	 			  ec.submit();	
			}
		}
		function select1(){
			var url="${basePath}/broke/customer/list.action";
			frm.submit();
		}
		function resetSelect(){
			var url="${basePath}/broke/customer/list.action";
			frm.customerId.value="";
			frm.customerName.value="";
			frm.submit();
		}
		
		function deleteByCheckBox(){
			var url="${basePath}/broke/customer/delete.action";
			deleteEcside(ec.ids,url);
		}
		</SCRIPT>
	</body>
</html>