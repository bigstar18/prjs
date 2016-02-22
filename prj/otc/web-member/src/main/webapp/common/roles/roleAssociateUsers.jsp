<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/common.jsp"%>
<%@ include file="/public/ecsideLoad.jsp"%>
<c:if test="${not empty updateSuccess }">
	<script defer="defer">
		returnRolesUserList('${roleId }');
	</script>	
</c:if>
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
<form name="frm" method="post" action="<%=commonRoleControllerPath %>commonRoleUserAssociate">
		<fieldset width="100%">
		<legend>角色${role.name }[${role.id }]所辖管理员列表</legend>
		<input type="hidden" name="roleId" value="${role.id }">
		<span style="display: none">
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
            	<td align="left"> 管理员代码 ：</td>
                <td align="left">
                	<input name="_userId[=]" type="text" class="text" style="width: 150px;" value="${oldParams['userId[=]'] }">
                </td>
				<td align="left">
                	<input type="button" onclick="queryInfo();" class="btn" value="查询">&nbsp;&nbsp;
                </td>
				<td width=60%>&nbsp;</td>
              </tr>
        	</table>
        </span>  
		
		<br>
		<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%" height="300">
	  		<tHead>
	  			<tr height="25"  align=center>
	  				<td class="panel_tHead_LB">&nbsp;</td>
					<td class="panel_tHead_MB" align=left><input class=checkbox_1 type="checkbox" id="checkAll" onclick="selectAll(tb,'ck')"></td>
				    <td class="panel_tHead_MB" align=left>用户代码 </td>
					<td class="panel_tHead_MB" align=left>用户姓名 </td>
				    <td class="panel_tHead_MB" align=left>用户描述</td>
					<td class="panel_tHead_RB">&nbsp;</td>
				</tr>
			</tHead>
			<tBody>
			<c:choose>
				<c:when test="${not empty userSet }">
					<c:forEach items="${userSet }" var="user">
						<tr onclick="selectTr();" align=center height="25">
							<td class="panel_tBody_LB">&nbsp;</td>
							<td class="underLine" align=left><input name="ck" type="checkbox" value="${user.userId }"></td>
							<td class="underLine" align=left><b>${user.userId }</b></td>
							<td class="underLine" align=left>${user.name }</td>
							<td class="underLine" align=left>${user.description }</td>
							<td class="panel_tBody_RB">&nbsp;</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr align=center height="25">
						<td class="panel_tBody_LB">&nbsp;</td>
						<td class="underLine" colspan="4" style="text-align: center;">该角色暂无与之关联的管理员</td>
						<td class="panel_tBody_RB">&nbsp;</td>
					</tr>
				</c:otherwise>
			</c:choose>
				
		  	</tBody>
		<tFoot>
				<tr height="100%">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td colspan="4">&nbsp;</td>
					<td class="panel_tBody_RB">&nbsp;</td>
				</tr>
				<tr height="22">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td colspan="4" align="right" class="pagerext">
					</td>
					<td class="panel_tBody_RB">&nbsp;</td>
				</tr>
				<tr height="22">
					<td class="panel_tFoot_LB">&nbsp;</td>
					<td class="panel_tFoot_MB" colspan="4"></td>
					<td class="panel_tFoot_RB">&nbsp;</td>
				</tr>
			</tFoot>			
		</table>
     <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr height="35">
          	<td width="40%"><div align="center">
          		<input type="button" name="addbtn" onclick="javaScript:addAssociation();" class="bigbtn" value="添加关联管理员">
  			    &nbsp;&nbsp;
  			    <input type="button" name="delbtn" onclick="javaScript:deleteAssociation();" class="bigbtn" value="删除关联管理员">
  			    &nbsp;&nbsp;
				<input type="button" onclick="javaScript:returnRolesList('${role.id }');" class="btn" value="返回">&nbsp;&nbsp;
          </div>
          <input type="hidden" name="opt">
          </td>
          </tr>
     </table>
	</fieldset>
</form>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
	function addAssociation()
	{
		var url = "<%=commonRoleControllerPath %>commonRoleAddAssociation&roleId="+(frm.roleId.value);
		var result=ecsideDialog(url,null,550,500);
		if(result)
		{
			queryInfo();
		}
	}
	
	function deleteAssociation()
	{
		var ckValues = document.getElementsByName("ck");
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
				frm.action="<%=commonRoleControllerPath %>commonRoleDeleteAssociation";
				frm.submit();
			}
		}
	}

	function returnRolesUserList(v)
	{
		window.location="<%=commonRoleControllerPath %>commonRoleUserAssociate&roleId="+v;
	}
	
	function returnRolesList()
	{
		window.location="<%=commonRoleControllerPath %>commonRoleList";
	}
	
	function queryInfo()
	{
	  frm.submit();	
	}
</SCRIPT>