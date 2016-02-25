<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/common.jsp"%>
<script language="javascript" src="<%=serverPath %>/public/jslib/pageAndOrder.js"></script>
<style>
	checkbox_1
	{
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
    <form name="frm" action="<%=commonUserControllerPath %>commonUserList" callback="closeDialog(1);" targetType="hidden" method="post">
    	<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo" >
		<fieldset width="100%">
		<legend>系统用户信息查询</legend>
		<span>
			<table border="0" cellspacing="0" cellpadding="0" width="40%">
			  <tr height="35">
            	<td align="right"> 用户代码 ：</td>
                <td align="left">
                	<input id="userId" name="_id[like]" type="text" class="text" style="width: 100px;" value="<c:out value="${oldParams['id[like]']}"/>" onkeypress=" return onlyNumberAndCharInput()" maxlength="16">
                </td>
				<td align="left">
                	<input type="button" onclick="queryInfo();" class="btn" value="查询">&nbsp;
                	<!-- add by yangpei 增加重置功能 2011-11-21 -->
                	<input type="button" onclick="resetForm();" class="btn" value="重置">
                	<script type="text/javascript">
                		function resetForm(){
                			frm.userId.value="";
                		}
                	</script>
                </td>
              </tr>
        	</table>
        </span>  
		</fieldset>
		<br>
		<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%" height="300">
	  		<tHead>
	  			<tr height="25"  align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB" align=left><input class=checkbox_1 type="checkbox" id="checkAll" onclick="selectAll(tb,'ck')"></td>
			    <td class="panel_tHead_MB" align=left>用户代码 </td>
				<td class="panel_tHead_MB" align=left>用户姓名 </td>
				<td class="panel_tHead_MB" align=left>所属角色 </td>
			    <td class="panel_tHead_MB" align=left>用户描述</td>
			    <td class="panel_tHead_MB" align=left>权限信息</td>
			    <td class="panel_tHead_MB" align=left>密码修改</td>
					<td class="panel_tHead_RB">&nbsp;</td>
				</tr>
			</tHead>
			<tBody>
			<c:set var="superManager" value="admin"/>
			<c:set var="us" value="${CURRENUSERID}"/>
			<c:forEach items="${userList }" var="user">
				<tr onclick="selectTr();" align=center height="25">
					<td class="panel_tBody_LB">&nbsp;</td>
					<c:choose>
						<c:when test="${user.userId == us}">
							<td class="underLine" align=left>
								<input name="ck_super" type="checkbox" value="${user.userId}" disabled="disabled">
							</td>
						</c:when>
						<c:when test="${user.userId == superManager }">
							<td class="underLine" align=left>
								<input name="ck_super" type="checkbox" value="${user.userId }" disabled="disabled">
							</td>
						</c:when>
						<c:otherwise>
							<td class="underLine" align=left>
								<input name="ck" type="checkbox" value="${user.userId }">
							</td>
						</c:otherwise>
					</c:choose>
					<td class="underLine" align=left>
						<a href="javascript:editUserInfo('${user.userId }');" class="normal">${user.userId }</a>
					</td>
					<td class="underLine" align=left>${user.name }&nbsp;</td>
					<td class="underLine" align=left>
						<c:set var="roleNames" value=""/>
						<c:forEach items="${user.roleSet }" var="role">
							<c:set var="roleNames" value="${roleNames }${role.name },"/>
						</c:forEach>
						${fn:substring(roleNames, 0, fn:length(roleNames)-1) }&nbsp;
					</td>
					<td class="underLine" align="left"  >${user.description }&nbsp;</td>
					<c:choose>
						<c:when test="${user.userId == us}">
							<td class="underLine" align=left>
								<a href="javascript:addRight('${user.userId }');" class="nomal">设置</a>
							</td>
						</c:when>
						<c:when test="${user.userId == superManager }">
							<td class="underLine" align=left>
								<a href="#">查看</a>
							</td>
						</c:when>
						<c:otherwise>
							<td class="underLine" align=left>
								<a href="javascript:addRight('${user.userId }');" class="nomal">设置</a>
							</td>
						</c:otherwise>
					</c:choose>
					
					<c:choose>
						<c:when test="${user.userId == us}">
							<td class="underLine" align=left>
								<a href="javascript:modPwd('${user.userId }');" class="nomal">修改</a>
							</td>
						</c:when>
						<c:when test="${user.userId == superManager }">
							<td class="underLine" align=left>
								<a href="#">修改</a>
							</td>
						</c:when>
						<c:otherwise>
							<td class="underLine" align=left>
								<a href="javascript:modPwd('${user.userId }');" class="nomal">修改</a>
							</td>
						</c:otherwise>
					</c:choose>
					<td class="panel_tBody_RB">&nbsp;</td>
				</tr>
			</c:forEach>
		</tBody>
		<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="7">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="7" align="right" class="pagerext">
					<%@ include file="../public/pagerInc.jsp" %>
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="7"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>			
	</table>
     <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr height="35">
          	<td width="40%"><div align="right">
          		<input type="hidden" name="opt">
			    <input type="button" name="btn" onclick="javascript:addUser();" class="btn" value="添加">&nbsp;
  			    <input type="button" onclick="deleteUser();" class="btn" value="删除">
  			    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          </div>
          <input type="hidden" name="opt">
          </td>
          </tr>
     </table>	 
</form>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
	//添加新用户
	function addUser()
	{
		var result = window.showModalDialog("<%=commonUserControllerPath %>commonUserAdd&d="+Date(),"","dialogWidth=600px; dialogHeight=500px; status=no;scroll=no;help=no;resizable=no");
		if(result)
		{		
			queryInfo();
		}
		
	}
	
	//修改系统用户信息
	function editUserInfo(userid){
	
		var result = window.showModalDialog("<%=commonUserControllerPath %>commonUserView&userId="+userid+"&d="+Date(),"","dialogWidth=600px; dialogHeight=400px; status=no;scroll=no;help=no;resizable=no");
		if(result)
		{		
			queryInfo();
		}
	}
	
	function queryInfo(){
	  frm.action="<%=commonUserControllerPath %>commonUserList";
	  frm.submit();	
	}
	
	function deleteUser(){
	  if(${CURRENUSERID=="admin"}){
		  frm.action="<%=commonUserControllerPath %>commonUserDelete";
		  deleteRec(frm,tb,'ck');
	  }else{
		  alert("对不起，您没有权限！");
		  return false;
	  }
	}
	
	//添加用户权限
	function addRight(userId){
	  	var result = window.showModalDialog("<%=commonRightControllerPath %>commonRightUserList&userId="+userId+"&d="+Date(),"", "dialogWidth=500px; dialogHeight=600px; status=yes;scroll=yes;help=no;"); 
	  	if(result)
		{		
			queryInfo();
		}
		//frm.action="<%//=serverPath %>/commonRightUserList.cmn?userId="+userId;
	  //frm.submit();	
	}
	
	//修改密码
	function modPwd(userId){
		var result = window.showModalDialog("<%=commonUserControllerPath %>commonUserModPasswordForward&userId="+userId+"&sign=new&d="+Date(),"", "dialogWidth=420px; dialogHeight=500px; status=yes;scroll=yes;help=no;");
	  if(result)
		{		
			queryInfo();
		}
	}
	//数字和字母
function onlyNumberAndCharInput()
{
	if ((event.keyCode>=48 && event.keyCode<=57) || (event.keyCode>=65 && event.keyCode<=90) || (event.keyCode>=97 && event.keyCode<=122))
		{
	 	event.returnValue=true;
		}
	else
		{
	event.returnValue=false;
		}
	 }
</SCRIPT>