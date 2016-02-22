<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@ include file="/public/ecsideLoad.jsp"%>
<html>
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
								action="${basePath}/account/memberUser/list.action"
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
														用户代码:&nbsp;
													</td>
													<td class="table3_td2">
														<label>
															<input name="${GNNT_}id[like]" id="userId" type="text"
																class="input_text" 
																value="<c:out value="${oldParams['id[like]']}"/>">
														</label>
													</td>
													<td class="table3_td" align="right">
														用户名称:&nbsp;
													</td>
													<td class="table3_td2">
														<label>
															<input name="${GNNT_}name[like]" id="userName" type="text"
																class="input_text"
																value="<c:out value="${oldParams['name[like]']}"/>">
														</label>
													</td>
													<td class="table3_td" align="right">
														<button  class="btn_sec" onclick="queryInfo()">查询</button>
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
							<button  class="anniu_btn" onclick="addUser()" id="add">添加</button>
							&nbsp;&nbsp;
							<button  class="anniu_btn" onclick="deleteUsers()" id="delete">删除</button>
						</div>

						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="resultList"
											autoIncludeParameters="${empty param.autoInc}" var="user"
											action="${basePath}/account/specialMemberUser/list.action"
											title="" minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="导出列表.csv"   style="table-layout:fixed">

											<ec:row recordKey="${user.userId}"
												ondblclick="return forwardUpdate('${user.userId}');">
												<ec:column cell="checkbox" headerCell="checkbox" alias="ids"
													value="${user.userId }" style="text-align:center; "
													width="2%" viewsAllowed="html" />
												<ec:column property="userId[=]" width="5%" title="用户代码"
													style="text-align:center; " value="${user.userId }" />
												<ec:column property="name[Like]" width="5%" title="用户名称"
													style="text-align:center;overflow:hidden;text-overflow:ellipsis" value="${user.name}" tipTitle="${user.name}"/>
													<c:set var="roleNames" value="" />
													<c:forEach items="${user.roleSet }" var="role">
														<c:set var="roleNames" value="${roleNames }${role.name }," />
													</c:forEach>
												<ec:column property="memberType[=][String]" width="5%"
													title="所属角色" style="text-align:center;overflow:hidden;text-overflow:ellipsis"
													editTemplate="ecs_memberType" filterable="false" sortable="false" tipTitle="${fn:substring(roleNames, 0, fn:length(roleNames)-1) }">
													<%--<c:set var="roleNames" value="" />
													<c:forEach items="${user.roleSet }" var="role">
														<c:set var="roleNames" value="${roleNames }${role.name }," />
													</c:forEach>--%>
													${fn:substring(roleNames, 0, fn:length(roleNames)-1) }&nbsp;
												</ec:column>
												<ec:column property="user.userId[=]" width="7%" title="用户描述"
													style="text-align:center;overflow:hidden;text-overflow:ellipsis" value="${user.description }" tipTitle="${user.description }">
												</ec:column>
												<ec:column property="user.userId[=]" width="5%" title="权限信息"
													style="text-align:center;">
													<a href="javascript:addRight('${user.userId }');"
														class="nomal">查看</a>
												</ec:column>
												<ec:column property="user.userId[=]" width="8%" title="密码修改"
													style="text-align:center;">
													<a href="javascript:modPwd('${user.userId }');"
														class="nomal">修改</a>
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
		var url="${basePath}/account/specialMemberUser/forwardAdd.action";
		ecsideDialog(url);
	}
	
	//修改系统用户信息
	function forwardUpdate(userid){
		var url="${basePath}/account/specialMemberUser/forwardUpdate.action?obj.userId="+userid;
		ecsideDialog(url);
	}
	
	function queryInfo(){
	  frm.action="<%=basePath%>/account/specialMemberUser/list.action";
	  frm.submit();	
	}
	
	function deleteUsers(){
	  var url="${basePath}/account/specialMemberUser/delete.action";
	  deleteEcside(ec.ids,url);
	}
	
	//添加用户权限
	function addRight(userId){
		var url = "<%=basePath%>/account/specialMemberUser/commonRightUserList.action?userId="+userId+"&d="+Date();
	  	var result = result=ecsideDialog(url,"",500,600);
	  	if(result)
		{		
			queryInfo();
		}
	}
	
	//修改密码
	function modPwd(userId){
		var url = "<%=basePath%>/account/specialMemberUser/forwardUpdatePassword.action?obj.userId="+userId+"&d="+Date();
	  	var result = result=ecsideDialog(url,"",600,500);
	    if(result)
		{		
			queryInfo();
		}
	}
</SCRIPT>