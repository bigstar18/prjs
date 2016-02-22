<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@ include file="/public/ecsideLoad.jsp"%>
<style>
checkbox_1 {
	border-width: 0px;
	border-left-style: none;
	border-right-style: none;
	border-top-style: none;
	border-bottom-style: none;
	font-family: SimSun;
	font-size: 9pt;
	color: #333333;
	background-color: transparent;
}
</style>
<html>
	<body>
		<form name="frm" method="post"
			action="<%=basePath%>/role/forwardUpdateRoleUserAssociate.action?roleId=${role.id}">
			<div id="main_body">
				<table class="table1_style" border="0" cellspacing="0"
					cellpadding="0">
					<tr>
						<td>
							&nbsp;<img src="<%=skinPath%>/cssimg/xr.gif" align="absmiddle" />&nbsp;<span style="color:#920808;font-weight:bold;font-size:13px;">角色${role.name }[${role.id }]所辖管理员列表</span>
							<div class="div_gn">
							   <button id="addAs" class="anniu_btnmax" type="button"
									name="addbtn" onclick="addAssociation('${role.id}')">
									添加关联管理员
								</button>
								<button id="deleteAss" class="anniu_btnmax"
									type="button" name="bigbtn"
									onclick="deleteAssociation()">
									删除关联管理员
								</button>
								<button name="btn" class="anniu_btn"
									onclick="returnRolesList('${role.id }')">
									返回
								</button>
						    </div>
							<div class="div_tj">
								<input type="hidden" name="roleId" value="${role.id }">
											<!-- <div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td>
															<table border="0" cellspacing="0" cellpadding="0"
																width="100%">
																<tr>
																	<td class="table3_td" align="right">
																		管理员代码 ：
																	</td>
																	<td class="table3_td2">
																		<input name="${GNNT_ }userId[like]" type="text"
																			class="input_text" style="width: 150px;"
																			value="${oldParams['userId[like]'] }">
																	</td>
																	<td class="table3_td" align="right">
																		&nbsp;
																	</td>
																	<td class="table3_td2">
																		&nbsp;
																	</td>
																	<td class="table3_td" align="right">
																		<button class="btn_sec" onclick="queryInfo();">
																			查询
																		</button>
																	</td>
																	<td class="table3_td2" align="left">
																		&nbsp;
																	</td>
																</tr>
															</table>
														</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
								</table>
							</div> -->
							</form>
							
							<div class="div_list">
								<table id="tb" border="0" cellspacing="0" cellpadding="0"
									width="100%">
									<tr>
										<td>
											<ec:table items="userSet"
												autoIncludeParameters="${empty param.autoInc}" var="user"
												action="${basePath}/role/forwardUpdateRoleUserAssociate.action?roleId=${role.id}"
												title="" minHeight="400" listWidth="100%" height="500px"
												retrieveRowsCallback="limit" sortRowsCallback="limit"
												filterRowsCallback="limit" 
												csvFileName="导出列表.csv"  style="table-layout:fixed">

												<ec:row recordKey="${user.userId }">
													<ec:column cell="checkbox" headerCell="checkbox" alias="ids"
							value="${user.userId}" width="2%" viewsAllowed="html" style="text-align:center; " />
													<ec:column property="userId[like]" width="7%" title="用户代码"
														style="text-align:left; " value="${user.userId }">
													</ec:column>
													<ec:column property="name[Like]" width="7%" title="用户姓名"
														style="text-align:left;" value="${user.name }" />
													<ec:column property="description[like]" width="7%"
														title="用户描述" style="text-align:left;"
														value="${user.description }" />
												</ec:row>

											</ec:table>
										</td>
									</tr>
								</table>
							</div>
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
	function addAssociation(id)
	{
		var result="";
		if(checkie()){
			var url = "${basePath}/role/forwardAddAssociation.action?id="+id;
			result = ecsideDialog(url, null, 550, 600);
		}else{
			var url = "${basePath}/role/forwardAddAssociation.action?id="+id;
			result = ecsideDialog(url, null, 550, 550);
		}
		if(result>0)
		{
			queryInfo();
		}
	}
	
	function deleteAssociation()
	{
		var ckValues = document.getElementsByName("ids");
		var doOperate = false;
		var checkedNum = 0;
		for(var i=0;i<ckValues.length;i++)
		{
			if(ckValues[i].checked==true)
			{
				checkedNum ++ ;
			}
		}
		if(checkedNum>0)
		{
			doOperate = true;
		}
		
		if(!doOperate)
		{
			alert("没有可操作的数据!");
		}
		else
		{
			confirm("确认解除关联关系?")
			{
				ec.action="<%=basePath%>/role/deleteAssociation.action";
				ec.submit();
			}
		}
	}

	function returnRolesUserList(v)
	{
		window.location="<%=basePath%>/role/forwardUpdateRoleUserAssociate.action?roleId="+v;
	}
	
	function returnRolesList()
	{
		window.location="<%=basePath%>/role/commonRoleList.action";
	}
	
	function queryInfo()
	{
	  frm.submit();	
	}
</SCRIPT>