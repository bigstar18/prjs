<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/common.jsp"%>
<%@ include file="/public/ecsideLoad.jsp"%>
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
<form name="frm" method="post" action="<%=commonRoleControllerPath %>commonRoleList" callback="closeDialog(1);" targetType="hidden">
    	<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo" value="<c:out value="${pageInfo.pageNo}"/>">
		<fieldset width="100%">
		<legend>系统角色信息查询</legend>
		<span>
			<table border="0" cellspacing="0" cellpadding="0" width="40%">
			  <tr height="35">
            	<td align="right"> 角色代码 ：</td>
                <td align="left">
                	<input name="_id[=]" type="text" class="text" style="width: 150px;" value="${oldParams['id[=]'] }">
                </td>
				<td align="left">
                	<input type="button" onclick="queryInfo();" class="btn" value="查询">&nbsp;&nbsp;
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
			    <td class="panel_tHead_MB" align=left>角色代码 </td>
				<td class="panel_tHead_MB" align=left>角色名称 </td>
			    <td class="panel_tHead_MB" align=left>角色描述</td>
			    <td class="panel_tHead_MB" align=left>权限设置</td>
			    <td class="panel_tHead_MB" align=left>用户管理</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
			</tHead>
			<tBody>
			<c:forEach items="${rolesList }" var="role">
				<tr onclick="selectTr();" align=center height="25">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="underLine" align=left>
					<input name="ck" type="checkbox" value="${role.id }">
				</td>
				<td class="underLine" align=left>
				<a href="javascript:editRoleInfo('${role.id }');" class="normal">${role.id }</a>
				</td>
				<td class="underLine" align=left>${role.name }</td>
				<td class="underLine" align=left>${role.description }</td>
				<td class="underLine" align=left>
				<a href="javascript:addRight('${role.id }');" class="nomal">设置</a>  
				</td>
				<td class="underLine" align=left>
				<a href="javascript:mngUser('${role.id }');" class="nomal">管理</a>
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			</c:forEach>
		  	</tBody>
			<tFoot>
				<tr height="100%">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td colspan="6">&nbsp;</td>
					<td class="panel_tBody_RB">&nbsp;</td>
				</tr>
				<tr height="22">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td colspan="6" align="right" class="pagerext">
						<%@ include file="../public/pagerInc.jsp" %>
					</td>
					<td class="panel_tBody_RB">&nbsp;</td>
				</tr>
				<tr height="22">
					<td class="panel_tFoot_LB">&nbsp;</td>
					<td class="panel_tFoot_MB" colspan="6"></td>
					<td class="panel_tFoot_RB">&nbsp;</td>
				</tr>
			</tFoot>			
		</table>
     <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr height="35">
          	<td width="40%"><div align="center">
			    <input type="button" name="btn" onclick="javascript:addRole();" class="btn" value="添加">&nbsp;&nbsp;
  			    <input type="button" onclick="deleteRole();" class="btn" value="删除">&nbsp;&nbsp;
          </div>
          <input type="hidden" name="opt">
          </td>
          </tr>
     </table>	 
</form>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">

	//添加新角色
	function addRole()
	{
		var url="<%=commonRoleControllerPath %>commonRoleAdd&d="+Date();
		var result = ecsideDialog(url,"",600,350);
		if(result)
		{
			queryInfo();
	  	}
	}
	
	//修改角色信息
	function editRoleInfo(roleId)
	{
		var url="<%=commonRoleControllerPath %>commonRoleView&roleId="+roleId+"&d="+Date();
		var result = ecsideDialog(url,"",400,350);
		if(result)
		{
			queryInfo();
	  	}
	}
	
	function deleteRole()
	{
	  frm.action="<%=commonRoleControllerPath %>commonRoleDelete";
	  var checkIds=document.getElementsByName("ids");
	  for(var i=0;i < checkIds.length;i++ )
		{
			if( checkIds[i].checked == true &checkIds[i].value=="-1")
			{
				alert("不能删除默认角色，请重新选择！");
				return false;
			}
		
		}
	  deleteRec(frm,tb,'ck');
	}
	
	function queryInfo()
	{
	  frm.action="<%=commonRoleControllerPath %>commonRoleList";
	  frm.submit();	
	}
	
	//添加用户权限
	function addRight(roleId)
	{
		var url="<%=commonRightControllerPath %>commonRightRoleList&roleId="+roleId+"&d="+Date();
		var result = ecsideDialog(url,"",400,600);
	  	if(result)
		{		
			queryInfo();
		}
		//frm.action="<%//=serverPath %>/commonRightUserList.cmn?userId="+userId;
	  //frm.submit();
	}
	
	function mngUser(roleId)
	{
		window.location="<%=commonRoleControllerPath %>commonRoleUserAssociate&roleId="+roleId;
	}
</SCRIPT>