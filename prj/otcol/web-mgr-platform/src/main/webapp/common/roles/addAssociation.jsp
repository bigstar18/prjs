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
<base target="_self">
<c:if test="${not empty saveAssociationSuccess }">
	<SCRIPT LANGUAGE="JavaScript">
		window.returnValue="1";
		window.close();
	</SCRIPT>
</c:if>
<body>
<form name="frm" action="<%=commonRoleControllerPath %>commonRoleSaveAssociation" method="post">
		<fieldset width="100%">
		<legend>可关联用户信息</legend>
		<input type="hidden" name="roleId" value="${role.id }">
		<span>
			<table border="0" cellspacing="0" cellpadding="0" width="50%">
			  <tr height="35">
            	<td align="right"> 角色代码 ：</td>
                <td align="left"> ${role.id }</td>
                <td align="right"> 角色名称 ：</td>
                <td align="left"> ${role.name }</td>
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
			<c:set var="showDelBtn" value="true"/>
			<c:choose>
				<c:when test="${not empty userList }">
					<c:forEach items="${userList }" var="user">
						<tr onclick="selectTr();" align=center height="25">
							<td class="panel_tBody_LB">&nbsp;</td>
							<td class="underLine" align=left><input name="ck" type="checkbox" value="${user.userId }"></td>
							<td class="underLine" align=left>${user.userId }</td>
							<td class="underLine" align=left>&nbsp;${user.name }</td>
							<td class="underLine" align=left>&nbsp;${user.description }</td>
							<td class="panel_tBody_RB">&nbsp;</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<c:set var="showDelBtn" value="false"/>
					<tr align=center height="25">
						<td class="panel_tBody_LB">&nbsp;</td>
						<td class="underLine" colspan="4" style="text-align: center;">该角色已关联所有管理员</td>
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
  			    <c:if test="${showDelBtn }">
			    	<input type="button" name="btn" onclick="javascript:addAssociation();" class="btn" value="提交">
			    	&nbsp;&nbsp;
  			    </c:if>
  			    <input type="button" onclick="javascript:window.close();" class="btn" value="关闭">&nbsp;&nbsp;
          </td>
          </tr>
     </table>
	</fieldset>	 
</form>
</body>
<SCRIPT LANGUAGE="JavaScript">
	//添加新用户
	function addAssociation()
	{
		var ckValues = document.getElementsByName("ck");
		var subMark = false;
		var recordNum = 0;
		for(var i=0;i<ckValues.length;i++)
		{
			if(ckValues[i].checked == true)
			{
				recordNum ++;
			}
		}
		if(recordNum>0)
		{
			subMark = true;
		}
		
		if(subMark)
		{
			if(confirm("确认提交?"))
			{
				frm.submit();
			}
		}
		else
		{
			alert("没有可操作数据!");
		}
	}
</SCRIPT>