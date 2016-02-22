<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@ include file="/public/ecsideLoad.jsp"%>
<html>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
						<form name="frm"
								action="${basePath}/account/memberUser/list.action"
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
														用户代码:&nbsp;
														<label>
															<input name="gnnt_id[like]" id="userId" type="text"
																class="input_text"
																value="<c:out value="${oldParams['id[like]']}"/>">
														</label>
													</td>
													<td class="table3_td_1" align="left">
														用户名称:&nbsp;
														<label>
															<input name="gnnt_name[like]" id="userName" type="text" style="text-align:left;overflow:hidden;text-overflow:ellipsis"
																class="input_text" title="<c:out value="${oldParams['name[like]']}"/>"
																value="<c:out value="${oldParams['name[like]']}"/>">
														</label>
													</td>
													<td class="table3_td_anniu" align="left">
														<button  class="btn_sec" onclick="queryInfo()">查询</button>&nbsp;&nbsp;
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
											autoIncludeParameters="${empty param.autoInc}" var="user"
											action="${basePath}/account/memberUser/list.action" title=""
											minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="导出列表.csv"  style="table-layout:fixed">

											<ec:row recordKey="${user.userId}">
												<ec:column width="4%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="userId[=]" width="10%" title="用户代码"
													style="text-align:left; ">
													<a href="#" class="blank_a"
														onclick="return forwardUpdate('${user.userId}');"><font
														color="#880000">${user.userId }</font>
													</a>
												</ec:column>
												<ec:column property="name[Like]" width="16%" title="用户名称"
													style="text-align:left;overflow:hidden;text-overflow:ellipsis" value="${user.name}" tipTitle="${user.name}"/>
													<c:set var="roleNames" value="" />
													<c:forEach items="${user.roleSet }" var="role">
														<c:set var="roleNames" value="${roleNames }${role.name }," />
													</c:forEach>
												<ec:column property="memberType[=][String]" width="25%"
													title="所属角色" style="text-align:left;overflow:hidden;text-overflow:ellipsis"
													editTemplate="ecs_memberType" filterable="false" sortable="false" tipTitle="${fn:substring(roleNames, 0, fn:length(roleNames)-1) }">
													${fn:substring(roleNames, 0, fn:length(roleNames)-1) }&nbsp;
												</ec:column>
												<ec:column property="description[=]" width="16%" title="用户描述"
													style="text-align:left;overflow:hidden;text-overflow:ellipsis" value="${user.description}" tipTitle="${user.description}">
												</ec:column>
												<ec:column property="_1" width="16%" title="权限信息"
													style="text-align:center;" filterable="false" sortable="false">
													<a href="javascript:addRight('${user.userId }');" class="blank_a"><font color="#880000">查看</font></a>
												</ec:column>
												<ec:column property="_2" width="16%" title="密码修改"
													style="text-align:center;" filterable="false" sortable="false">
													<a href="javascript:modPwd('${user.userId }');" class="blank_a"><font color="#880000">修改</font></a>
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
	</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
	//添加新用户
	function addUser()
	{
		var url="${basePath}/account/memberUser/forwardAdd.action";
		ecsideDialog(url);
	}
	
	//修改系统用户信息
	function forwardUpdate(userid){
		var url="${basePath}/account/memberUser/forwardUpdate.action?obj.userId="+userid;
		ecsideDialog(url,"",580,255);
		
	}
	
	function queryInfo(){
	  frm.action="<%=basePath%>/account/memberUser/list.action";
	  frm.submit();	
	}
	
	function deleteUsers(){
	  var url="${basePath}/account/memberUser/delete.action";
	  deleteEcside(ec.ids,url);
	}
	
	//添加用户权限
	function addRight(userId){
	  	var result = "";
	  	if(checkie()){
	  		var url="<%=basePath%>/account/memberUser/commonRightUserList.action?userId="+userId+"&d="+Date();
	  		result=ecsideDialog(url,"",450,670);
	  	}else{
	  		var url="<%=basePath%>/account/memberUser/commonRightUserList.action?userId="+userId+"&d="+Date();
	  		result=ecsideDialog(url,"",450,620);
	  	}
	  	if(result)
		{		
			queryInfo();
		}
	}
	
	//修改密码
	function modPwd(userId){
	  var result ="";
	  if(checkie()){
		  	var url="<%=basePath%>/account/memberUser/forwardUpdatePassword.action?obj.userId="+userId+"&d="+Date();
	  		result=ecsideDialog(url,"",580,310);
	  	}else{
	  		var url="<%=basePath%>/account/memberUser/forwardUpdatePassword.action?obj.userId="+userId+"&d="+Date()
	  		result=ecsideDialog(url,"",580,260);
	  	}
	  if(result)
		{		
			queryInfo();
		}
	  
	//  var url="<%=basePath%>/account/memberUser/forwardUpdatePassword.action?obj.userId="+userId;
	 // ec.action=url;
	 // ec.submit();
	}
</SCRIPT>