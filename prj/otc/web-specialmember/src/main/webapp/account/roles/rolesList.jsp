<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@ include file="/public/ecsideLoad.jsp"%>
<html>
	<head>
		<title>客户列表</title>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
						<form name="frm" method="post"
								action="<%=basePath%>/role/commonRoleList.action">
							<table border="0" cellpadding="0" cellspacing="0"
								class="table2_style">
								<tr>
									<td class="table2_td_widthmin">
										<div class="div2_top">
											<table class="table3_style" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td class="table3_td_1" align="left">
														角色名称:&nbsp;
														<label>
															<input type="text" class="input_text"
																name="${GNNT_}name[like]" id="roleName"
																value="${oldParams['name[like]'] }" />
														</label>
													</td>
													<td class="table3_td_anniu" align="left">
														<button class="btn_sec" onclick="queryInfo()">查询</button>&nbsp;&nbsp;
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
							<button  class="anniu_btn" onclick="addRole()" id="add">添加</button>
							&nbsp;&nbsp;
							<button  class="anniu_btn" onclick="deleteRole()" id="delete">删除</button>
						</div>

						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="resultList"
											autoIncludeParameters="${empty param.autoInc}" var="role"
											action="${basePath}/role/commonRoleList.action" title=""
											minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="导出列表.csv"   style="table-layout:fixed">
											<ec:row recordKey="${role.id }">
												<ec:column cell="checkbox" headerCell="checkbox" alias="ids"
													style="text-align:center; " value="${role.id},${role.type}" width="2%" viewsAllowed="html" />
												<ec:column width="4%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="name[Like]" width="13%" title="角色名称"
													style="text-align:left;">
													<a href="#" class="blank_a"
														onclick="editRoleInfo('${role.id }')"><font
														color="#880000">${role.name }</font>
													</a>
												</ec:column>
												<ec:column property="description[like]" width="13%"
													title="角色描述" style="text-align:left;overflow:hidden;text-overflow:ellipsis"
													value="${role.description }" tipTitle="${role.description }"/>
												<ec:column property="id[=][long]" width="13%" title="权限设置"
													style="text-align:center;" filterable="false" sortable="false">
													<a href="javascript:addRight('${role.id }');"
														class="blank_a"><font color="#880000">设置</font></a>
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
		var url = "<%=basePath%>/role/commonRoleAdd.action?d="+Date();
		ecsideDialog(url,"",600,210);
	}
	
	//修改角色信息
	function editRoleInfo(roleId)
	{
	  	var url="<%=basePath%>/role/forwardUpdate.action?obj.id="+roleId+"&d="+Date();
	  	//ec.action=url;
	  	//ec.submit()
		ecsideDialog(url,"",600,240);
	}
	
	//复制用户权限
	function copyRight(roleId) {
		var url = "<%=basePath%>/role/commonRoleCopy.action?original_roleId="+roleId+"&d="+Date();
		ecsideDialog(url,"",500,215);
	}
	
	function deleteRole()
	{
	  var url="<%=basePath%>/role/deleteRule.action";
	  var collCheck=document.getElementsByName("ids");
	  for(var i=0;i < collCheck.length;i++ )
		{ 
			if( collCheck[i].checked == true )
			{
				var reg =/^.*DEFAULT_+.*$/; 
				var id=collCheck[i].value;
				if(id.match(reg)!=null){
					alert('不能删除默认角色！');
					return false;
				}
			}
		}
		deleteEcside(ec.ids,url);
	}
	function queryInfo()
	{
	  frm.action="<%=basePath%>/role/commonRoleList.action";
	  frm.submit();	
	}
	
	//添加用户权限
	function addRight(roleId)
	{
		var url = "<%=basePath%>/right/forwardUpdateUser.action?roleId="+roleId+"&d="+Date();
		ecsideDialog(url,"",400,620);
	}
	
	function mngUser(roleId)
	{
		window.location="<%=basePath%>/role/forwardUpdateRoleUserAssociate.action?roleId="+roleId;
	}
	function queryInfo(){
	  frm.submit();	
	}
</SCRIPT>