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
								action="${basePath}/account/customerDivert/list.action"
								method="post">
							<table border="0" cellpadding="0" cellspacing="0"
								class="table2_style">
								<tr>
									<td class="table2_td_widthmax">
										<div class="div2_top">
											<table class="table3_style" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td class="table3_td_1" align="left">
														所属会员:&nbsp;
														<label>
															<input type="hidden" name="${ORIGINAL_}memberIds" id="memberIds"
																value="${original_memberIds}" class="input_text">
															<input type="text" id="memberNames" name="${ORIGINAL_}memberNames" value="${original_memberNames}"
																onclick="clickText()" readonly=true size="8" class="input_text">
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
											action="${basePath}/account/customerDivert/list.action"
											title="" minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											toolbarContent="navigation|pagejump |pagesize|refresh|extend|status"
											style="table-layout:fixed">

											<ec:row recordKey="${customer.customerNo}">
												<ec:column width="4%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="primary.customerNo[like]" width="8%"
													title="交易账号" style="text-align:left; ">
													<a href="#" class="blank_a"
														onclick="return forwardUpdate('${customer.customerNo}');"><font
														color="#880000">${customer.customerNo}</font>
													</a>
												</ec:column>
												<ec:column property="primary.name[Like]" width="8%"
													title="客户名称" style="text-align:left;overflow:hidden;text-overflow:ellipsis"
													value="${customer.name}" tipTitle="${customer.name}"/>
												<ec:column property="memberInfo.name[like]" width="30%"
													title="所属会员" style="text-align:left;overflow:hidden;text-overflow:ellipsis"
													value="${customer.memberName}" tipTitle="${customer.memberName}"/>
												<ec:column property="primary.phone[like]" width="19%"
													title="电话" style="text-align:left;"
													value="${customer.phone}" />
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
		<SCRIPT type="text/javascript">
		function forwardUpdate(id){
			var url="${basePath}/account/customerDivert/forwardUpdate.action?obj.customerNo="+id;
			ecsideDialog(url,"",580,250);
		}
		function select1(){
			frm.submit();
		}
		
		function clickText(){
			var memberIds = frm.memberIds.value;
			var url="${basePath}/account/customerDivert/memberList.action?original_oldMemberIds="+memberIds;
			var result=dialog(url, "",400,510);
			if(result!=null&&result!=''){
				var result1=result.split('####');
				frm.memberIds.value=result1[0];
				frm.memberNames.value=result1[1];
			}
		}
		
		</SCRIPT>
	</body>
</html>