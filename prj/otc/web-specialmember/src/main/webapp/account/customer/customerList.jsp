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
						&nbsp;
						<div class="div_cxtj">
							<img src="<%=skinPath%>/cssimg/13.gif" />
							&nbsp;&nbsp;查询条件
						</div>
						<div class="div_tj">
						<form name="frm"
								action="${basePath}/account/customer/customerList.action"
								method="post">
							<table border="0" cellpadding="0" cellspacing="0"
								class="table2_style">
								<tr>
									<td height="60">
										<div class="div2_top">
											<table class="table3_style" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td class="table3_td" align="right">
														会员编号:&nbsp;
													</td>
													<td class="table3_td2">
														<label>
															<input type="hidden" name="${GNNT_}primary.memberNo in (select memberNo from Customer where memberNo in(%composite%))[composite]" id="memberIds"
																value="${oldParams['primary.memberNo in (select memberNo from Customer where memberNo in(%composite%))[composite]'] }" class="input_text">
															<input type="text" id="memberNames" name="memberNames" value="${memberNames}"
																onclick="clickText()" readonly=true size="8" class="input_text">
														</label>
													</td>
													<td class="table3_td" align="right">
														交易账号:&nbsp;
													</td>
													<td class="table3_td2">
														<label>
															<input type="text" id="customerId"
																name="${GNNT_}primary.customerNo[like]"
																value="${oldParams['primary.customerNo[like]'] }"
																class="input_text" />
														</label>
													</td>
													<td class="table3_td" align="right">
														客户名称:&nbsp;
													</td>
													<td class="table3_td2">
														<label>
															<input type="text" id="customerName"
																name="${GNNT_}primary.name[like]"
																value="${oldParams['primary.name[like]'] }"
																class="input_text" />
														</label>
													</td>
												</tr>
												<tr>
													<td class="table3_td" align="right">
														&nbsp;
													</td>
													<td class="table3_td2">
													&nbsp;
													</td>
													<td class="table3_td" align="right">
														&nbsp;
													</td>
													<td class="table3_td2">
														&nbsp;
													</td>
													<td class="table3_td" align="right">
														<button  class="btn_sec" onclick="select1()">查询</button>
													</td>
													<td class="table3_td2" align="left">
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
						<div class="div_gn">
							<button class="anniu_btn" onclick="addCustomer()" id="addCustomer">添加</button>
						</div>

						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="resultList"
											autoIncludeParameters="${empty param.autoInc}" var="customer"
											action="${basePath}/account/customer/customerList.action"
											title="" minHeight="345" listWidth="150%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="导出列表.csv"   style="table-layout:fixed">

											<ec:row recordKey="${customer.customerNo}"
												ondblclick="return forwardUpdate('${customer.customerNo}');">
												<ec:column cell="checkbox" headerCell="checkbox" alias="ids"
													value="${customer.customerNo}" width="2%" viewsAllowed="html" />
												<ec:column property="primary.customerNo[like]" width="7%"
													title="交易账号" style="text-align:center; "
													value="${customer.customerNo}" />
												<ec:column property="primary.name[Like]" width="7%"
													title="客户名称" style="text-align:center;overflow:hidden;text-overflow:ellipsis"
													value="${customer.name}" tipTitle="${customer.name}"/>
												<ec:column property="memberInfo.name[like]" width="7%"
													title="所属会员" style="text-align:center;overflow:hidden;text-overflow:ellipsis"
													value="${customer.memberName}" tipTitle="${customer.memberName}"/>
												<ec:column property="primary.papersType[=][int]" width="7%"
													title="证件类型" style="text-align:center;"
													editTemplate="ecs_t_papersType">
													<c:set var="papersTypeKey">
														<c:out value="${customer.papersType}"></c:out>
													</c:set>
		  							${papersTypeMap[papersTypeKey]}
							</ec:column>
												<ec:column property="primary.papersName[like]" width="7%"
													title="证件号码" style="text-align:center;overflow:hidden;text-overflow:ellipsis"
													value="${customer.papersName}" tipTitle="${customer.papersName}"/>
												<ec:column property="primary.address[like]" width="7%"
													title="地址" style="text-align:center;overflow:hidden;text-overflow:ellipsis"
													value="${customer.address}" tipTitle="${customer.address}"/>
												<ec:column property="primary.phone[like]" width="7%"
													title="电话" style="text-align:center;"
													value="${customer.phone}" />
												<ec:column property="primary.fax[like]" width="7%"
													title="传真" style="text-align:center;"
													value="${customer.fax}" />
												<ec:column property="primary.postCode[like]" width="7%"
													title="邮编" style="text-align:center;"
													value="${customer.postCode}" />
												<ec:column property="primary.email[like]" width="7%"
													title="电子邮箱" style="text-align:center;"
													value="${customer.email}" />
												<ec:column property="primary.status[=]" width="7%"
													title="客户状态" style="text-align:center;"
													editTemplate="ecs_t_statusType">
		  												${firmStatusMap[customer.status]}
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
		<SCRIPT type="text/javascript">
		function clickText(){
		var memberIds = frm.memberIds.value;
		var url="${basePath}/account/customer/memberList.action?oldMemberIds="+memberIds;
		var result=ecsideDialog(url,"");
		if(result!=null&&result!=''){
		var result1=result.split('####');
		frm.memberIds.value=result1[0];
		frm.memberNames.value=result1[1];
			
		}
		}
		function forwardUpdate(id){
			var url="${basePath}/account/customer/forwardUpdate.action?obj.customerNo="+id;
			var result=ecsideDialog(url,window,800,800);
			if(result>0)
			{
	 			  ec.submit();	
			}
		}
		
		function addCustomer(){
			var url="${basePath}/account/customer/forwardAdd.action";
			var result=ecsideDialog(url,"",600,500);
			if(result>0)
			{
			   ec.submit();	
			}
		}
		function select1(){
			var url="${basePath}/account/customer/list.action";
			frm.submit();
		}
		function deleteByCheckBox(){
			var url="${basePath}/account/customer/delete.action";
			deleteEcside(ec.ids,url);
		}
		</SCRIPT>
	</body>
</html>