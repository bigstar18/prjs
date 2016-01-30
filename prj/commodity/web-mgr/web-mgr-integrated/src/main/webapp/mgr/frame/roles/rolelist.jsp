<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>客户列表</title>
	</head>
	<body onload="getFocus('roleName');">
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_cx">
							<form name="frm" method="post"
								action="<%=basePath%>/role/commonRoleList.action?sortColumns=order+by+id">
						<table border="0" cellpadding="0" cellspacing="0"
								class="table2_style">
								<tr>
									<td class="table2_td_width">
										<div class="div2_top">
											<table class="table3_style" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td class="table3_td_1" align="left">
														角色名称:&nbsp;
															<label>
																<input type="text" class="input_text" maxlength="32"
																	name="${GNNT_}name[allLike]" id="roleName"
																	value="${oldParams['name[allLike]'] }" maxLength="<fmt:message key='roleName_q' bundle='${PropsFieldLength}'/>"/>
															</label>
													</td>
													<td class="table3_td_anniu" align="left">
														<button  class="btn_sec" onclick="queryInfo()">查询</button>&nbsp;&nbsp;&nbsp;
														
														<button class="btn_cz" onclick="myReset()">重置</button>
													</td>
													<td class="table3_td_1" align="left">
														&nbsp;
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
							<rightButton:rightButton name="添加" onclick="addRole()" className="anniu_btn" action="/role/addForwardRole.action?d=" id="add"></rightButton:rightButton>
							&nbsp;&nbsp;
							<rightButton:rightButton name="删除" onclick="deleteRole()" className="anniu_btn" action="/role/deleteRole.action" id="delete"></rightButton:rightButton>
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result"
											autoIncludeParameters="${empty param.autoInc}" var="role"
											action="${basePath}/role/commonRoleList.action?sortColumns=order+by+id" title=""
											minHeight="345" listWidth="100%" 
											 csvFileName="导出列表.csv"
											style="table-layout:fixed">
											<ec:row recordKey="${role.id }">
												<ec:column cell="checkbox" headerCell="checkbox" alias="ids"
													style="text-align:center; " value="${role.id}" width="4%"
													viewsAllowed="html" />
												<ec:column width="4%" property="_0" title="序号"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<ec:column property="name" width="33%" title="角色名称"
													style="text-align:left;">
													<rightHyperlink:rightHyperlink href="#" className="blank_a" action="/role/updateForwardRole.action" id="detail" text="<font color='#880000'>${role.name}</font>" onclick="editRoleInfo('${role.id }')"/>
												</ec:column>
												<ec:column property="description" width="26%"
													title="角色描述" style="text-align:left;overflow:hidden;text-overflow:ellipsis"
													/>
												<ec:column property="123" width="10%" title="权限设置"
													style="text-align:center;" filterable="false" sortable="false">
													<rightHyperlink:rightHyperlink href="#" className="blank_a" action="/role/updateForwardRoleRight.action" id="addRight" text="<font color='#880000'>设置</font>" onclick="addRight('${role.id }');"/>
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
<SCRIPT type="text/javascript">

	//添加新角色
	function addRole()
	{
		var a =document.getElementById('add').action;
		var url = "<%=basePath%>"+a+Date();
		ecsideDialog(url, null, 480, 350);
	}
	
	//修改角色信息
	function editRoleInfo(roleId)
	{
		var a=document.getElementById("detail").action;
	  	var url = "<%=basePath%>"+a+"?entity.id="+roleId+"&d="+Date();
		ecsideDialog(url, null, 480, 350);
	}
	function deleteRole()
	{
		var a =document.getElementById('delete').action;
	  var url="${basePath}"+a+"?autoInc=false";
	  var collCheck=document.getElementsByName("ids");
	  deleteEcside(collCheck,url);
	}
	function queryInfo()
	{
	  frm.submit();	
	}
	
	//添加用户权限
	function addRight(roleId)
	{
		var a=document.getElementById("addRight").action;
		var url="<%=basePath%>"+a+"?roleId="+roleId+"&d="+Date();
		ecsideDialog(url,"",450,600);
	}
	
	//复制用户权限
	function copyRight(roleId) {
		var url = "<%=basePath%>/role/commonRoleCopy.action?original_roleId="+roleId+"&d="+Date();
		ecsideDialog(url, null, 500, 230);
	}
	
	function mngUser(roleId)
	{
		window.location="<%=basePath%>/role/forwardUpdateRoleUserAssociate.action?roleId="+roleId;
	}
</SCRIPT>