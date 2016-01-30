<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<body onload="getFocus('userId');">
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_cx">
						<form name="frm" action="${basePath}/user/list.action?sortColumns=order+by+userId"
								method="post">
							<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style" >
									<tr>
										<td class="table2_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
													<td class="table3_td_1" align="left">
														管理员代码:&nbsp;
														<label>
															<input name="${GNNT_}id[allLike]" id="userId" type="text"
																class="input_text" 
																value="<c:out value="${oldParams['id[allLike]']}"/>"  maxLength="<fmt:message key='userID_q' bundle='${PropsFieldLength}'/>">
														</label>
													</td>
													<td class="table3_td_1" align="left">
														管理员名称:&nbsp;
														<label>
															<input name="${GNNT_}name[allLike]" id="userName" type="text"
																class="input_text" 
																value="${oldParams['name[allLike]']}"  maxLength="<fmt:message key='userName_q' bundle='${PropsFieldLength}'/>">
														</label>
													</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" onclick=queryInfo();>
																查询
															</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onclick=myReset();>
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
							<rightButton:rightButton name="添加" onclick="addUser()" className="anniu_btn" action="/user/addForward.action" id="add"></rightButton:rightButton>
						</div>

						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="user"
											action="${basePath}/user/list.action?sortColumns=order+by+userId"
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="customer.xls" csvFileName="customer.csv"
											showPrint="true"  listWidth="100%"
											minHeight="345" style="table-layout:fixed;">
											

											<ec:row recordKey="${user.userId}">
												<ec:column width="6%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="userId" width="11%" title="管理员代码"
													style="text-align:left; ">
													<rightHyperlink:rightHyperlink href="#" className="blank_a" action="/user/forwardUpdate.action" id="detail" text="<font color='#880000'>${user.userId}</font>" onclick="return forwardUpdate('${user.userId}');"/>
												</ec:column>
												<ec:column property="name" width="11%" title="管理员名称"
													style="text-align:left;" ellipsis="true">
													${user.name}
												</ec:column>
												<c:set var="roleNames" value="" />
												<c:forEach items="${user.roleSet }" var="role">
													<c:set var="roleNames" value="${roleNames }${role.name }," />
												</c:forEach>
												<ec:column property="type" width="15%"
													title="所属角色" style="text-align:left;overflow:hidden;text-overflow:ellipsis" columnId="${user.userId}"
													editTemplate="ecs_memberType" filterable="false" sortable="false" tipTitle="${fn:substring(roleNames, 0, fn:length(roleNames)-1) }">
													<!--<c:set var="roleNames" value="" />
													<c:forEach items="${user.roleSet }" var="role">
														<c:set var="roleNames" value="${roleNames }${role.name }," />
													</c:forEach>-->
													<c:if test="${user.type=='DEFAULT_SUPER_ADMIN'}">
														超级管理员角色
													</c:if>
													<c:if test="${user.type=='DEFAULT_ADMIN'}">
														高级管理员角色
													</c:if>
													<c:if test="${!(user.type=='DEFAULT_SUPER_ADMIN' or user.type=='DEFAULT_ADMIN')}">
														${fn:substring(roleNames, 0, fn:length(roleNames)-1) }&nbsp;
													</c:if>
												</ec:column>
												<ec:column property="description" width="25%" title="管理员描述"
													style="text-align:left;overflow:hidden;text-overflow:ellipsis" value="${user.description }" tipTitle="${user.description }">
												</ec:column>
												<ec:column property="isForbid" width="10%" title="是否禁用"
													style="text-align:left;" editTemplate="ecs_t_status">
													<c:set var="statusKey">
														<c:out value="${user.isForbid}"></c:out>
													</c:set>
											  		${com_isForbidMap[statusKey]}
												</ec:column>
												<ec:column property="23" width="10%" title="关联角色"
													style="text-align:center;" filterable="false" sortable="false">
													<c:if test="${user.type!='DEFAULT_SUPER_ADMIN'&& user.type!='DEFAULT_ADMIN'}">
														<rightHyperlink:rightHyperlink href="#" className="blank_a" action="/user/forwardRelatedRight.action" id="relatedRight" text="<font color='#880000'>设置</font>" onclick="relatedRight('${user.userId }');"/>
													</c:if>
												</ec:column>
												<ec:column property="12" width="14%" title="密码修改"
													style="text-align:center;" filterable="false" sortable="false">
													<rightHyperlink:rightHyperlink href="#" className="blank_a" action="/user/forwardUpdatePassword.action" id="modPwd" text="<font color='#880000'>修改</font>" onclick="modPwd('${user.userId }');"/>
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
		<textarea id="ecs_t_status" rows="" cols="" style="display: none">
			<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="isForbid[=]">
				<ec:options items="com_isForbidMap" />
			</select>
	    </textarea>
	</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
	//添加新管理员
	function addUser()
	{
		var a=document.getElementById('add').action;
		var url="<%=basePath%>"+a;
		ecsideDialog(url,"",550,450);
	}
	
	//修改系统管理员信息--传的参数是：管理员代码（userid）
	function forwardUpdate(userid){
		var a=document.getElementById("detail").action;
		var url="<%=basePath%>"+a+"?entity.userId="+userid;
		ecsideDialog(url,"",550,420);
	}
	function queryInfo(){2
	  frm.submit();	
	}
	
	function deleteUsers(){
	  var url="${basePath}/user/delete.action";
	  var collCheck=document.getElementsByName("ids");
	  for(var i=0;i < collCheck.length;i++ )
		{ 
			if( collCheck[i].checked == true)
			{
				var reg =/^.*DEFAULT_+.*$/; 
				var id=collCheck[i].value;
				if(id.match(reg)!=null){
					alert('不能删除默认管理员！');
					return false;
				}
			}
		}
	  deleteEcside(ec.ids,url);
	}
	
	//关联管理员角色
	function relatedRight(userId){
	  	var result = "";
	  	var a=document.getElementById("relatedRight").action;
	  	var url="${basePath}"+a+"?entity.userId="+userId;
	  //ec.action=url;
	  	//ec.submit();
		 ecsideDialog(url,"",550,400);
	}
	
	//修改密码
	function modPwd(userId){
	  var result = "";
	  var a=document.getElementById("modPwd").action;
	  if(checkie()){
		  	var url = "<%=basePath%>"+a+"?entity.userId="+userId+"&d="+Date();
			result = ecsideDialog(url, null, 480,350);
	  	}else{
		  	var url = "<%=basePath%>"+a+"?entity.userId="+userId+"&d="+Date();
			result = ecsideDialog(url, null, 480,350);
	  	}
	  if(result)
		{		
			queryInfo();
		}
	}
	
	function forView(id) {
			//document.getElementById(id).title = document.getElementById(id).value;
		}
</SCRIPT>