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
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
						<form name="frm" action="${basePath}/user/list.action"
								method="post">
								<input type="hidden" name="orderField" value="${orderFiled}">
								<input type="hidden" name="orderDesc" value="${orderType}">
								<input type="hidden" name="pageSize"
									value="<c:out value="${pageInfo.pageSize}"/>">
								<input type="hidden" id="pageNo" name="pageNo"
									value="<c:out value="${pageInfo.pageNo}"/>">
							<table border="0" cellpadding="0" cellspacing="0"
								class="table2_style">
								<tr>
									<td class="table2_td_width">
										<div class="div2_top">
											<table class="table3_style" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td class="table3_td_1" align="left">
														�û�����:&nbsp;
														<label>
															<input name="${GNNT_}id[like]" id="userId" type="text"
																class="input_text" 
																value="<c:out value="${oldParams['id[like]']}"/>">
														</label>
													</td>
													<td class="table3_td_1" align="left">
														�û�����:&nbsp;
														<label>
															<input name="${GNNT_}name[like]" id="userName" type="text"
																class="input_text"
																value="${oldParams['name[like]']}">
														</label>
													</td>
													<td class="table3_td_anniu" align="left">
														<button class="btn_sec" onclick="queryInfo()">��ѯ</button>&nbsp;&nbsp;
														<button class="btn_cz" onclick="myReset()">����</button>
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
							<button  class="anniu_btn" onclick="addUser()" id="add">���</button>
						</div>

						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="resultList"
											autoIncludeParameters="${empty param.autoInc}" var="user"
											action="${basePath}/user/list.action" title=""
											minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="�����б�.csv"   style="table-layout:fixed">

											<ec:row recordKey="${user.userId}">
												<ec:column width="4%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="userId[=]" width="10%" title="�û�����"
													style="text-align:left; ">
													<a href="#" class="blank_a"
														onclick="forwardUpdate('${user.userId}');"><font
														color="#880000">${user.userId }</font>
													</a>
												</ec:column>
												<ec:column property="name[Like]" width="13%" title="�û�����"
													style="text-align:left;overflow:hidden;text-overflow:ellipsis" value="${user.name}" tipTitle="${user.name}"/>
													<c:set var="roleNames" value="" />
													<c:forEach items="${user.roleSet }" var="role">
														<c:set var="roleNames" value="${roleNames }${role.name }," />
													</c:forEach>
												<ec:column property="memberType[=][String]" width="13%"
													title="������ɫ" style="text-align:left;overflow:hidden;text-overflow:ellipsis"
													editTemplate="ecs_memberType" filterable="false" sortable="false" tipTitle="${fn:substring(roleNames, 0, fn:length(roleNames)-1) }">
													<%--<c:set var="roleNames" value="" />
													<c:forEach items="${user.roleSet }" var="role">
														<c:set var="roleNames" value="${roleNames }${role.name }," />
													</c:forEach>--%>
													${fn:substring(roleNames, 0, fn:length(roleNames)-1) }&nbsp;
												</ec:column>
												<ec:column property="description[=]" width="13%" title="�û�����"
													style="text-align:left;overflow:hidden;text-overflow:ellipsis" value="${user.description }" tipTitle="${user.description }">
												</ec:column>
												<ec:column property="isForbid[=]" width="13%" title="�Ƿ����"
													style="text-align:left;" editTemplate="ecs_t_status">
													<c:set var="statusKey">
														<c:out value="${user.isForbid}"></c:out>
													</c:set>
											  		${isForbidMap[statusKey]}
												</ec:column>
												<ec:column property="_1" width="13%" title="������ɫ"
													style="text-align:center;" filterable="false" sortable="false">
													<a href="javascript:relatedRight('${user.userId }');"
														class="blank_a"><font color="#880000">����</font></a>
												</ec:column>
												<ec:column property="_2" width="12%" title="�����޸�"
													style="text-align:center;" filterable="false" sortable="false">
													<a href="javascript:modPwd('${user.userId }');"
														class="blank_a"><font color="#880000">�޸�</font></a>
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
		<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value=""
				onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="" />
		</textarea>
		<textarea id="ecs_t_status" rows="" cols="" style="display: none">
			<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="isForbid[=]">
				<ec:options items="isForbidMap" />
			</select>
	    </textarea>
	</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
	//������û�
	function addUser()
	{
		var url="<%=basePath%>/user/forwardAdd.action";
		ecsideDialog(url,"",600,350);
	}
	
	//�޸�ϵͳ�û���Ϣ
	function forwardUpdate(userid){
		var url="<%=basePath%>/user/forwardUpdate.action?obj.userId="+userid;
		ecsideDialog(url,"",600,285);
	}
	
	function queryInfo(){
	  frm.action="<%=basePath%>/user/list.action";
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
					alert('����ɾ��Ĭ�Ϲ���Ա��');
					return false;
				}
			}
		}
	  deleteEcside(ec.ids,url);
	}
	
	//����û�Ȩ��
	function addRight(userId){
	  	var result = "";
	  	var url = "<%=basePath%>/right/commonRightUserList.action?userId="+userId+"&d="+Date();
	  	if(checkie()){
	  		result=ecsideDialog(url,"",500,670);
	  	}else{
	  		result=ecsideDialog(url,"",500,620);
	  	}
	  	if(result)
		{		
			queryInfo();
		}
	}
	
	//�޸�����
	function modPwd(userId){
	  var result = "";
	  var url = "<%=basePath%>/user/forwardUpdatePassword.action?obj.userId="+userId+"&d="+Date();
	  	if(checkie()){
	  		result=ecsideDialog(url,"",600,335);
	  	}else{
	  		result=ecsideDialog(url,"",600,285);
	  	}
	  if(result)
		{		
			queryInfo();
		}
	}
	
	//�����û���ɫ
	function relatedRight(userId){
	  	var result = "";
	  	var url="${basePath}/user/forwardRelatedRight.action?userId="+userId;
	  	ecsideDialog(url,"",500,480);
	}
</SCRIPT>