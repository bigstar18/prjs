<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="gnnt.MEBS.member.ActiveUser.ActiveUserManager"%>
<%@ include file="../public/session.jsp"%>
<body>
<form name="frm" action="<%=basePath%>firmPermissionController.zcjs?funcflg=commonUserOnLineList" method="post">
       <input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo" value="<c:out value="${pageInfo.pageNo}"/>">
       <fieldset width="100%">
       <legend>在线系统用户列表</legend>
       
		
		<br>
		<c:forEach items="${onLineUsersMap }" var="onLineUsers">
			<c:set var="totalNumber" value="${onLineUsers.key }"/>
			<c:set var="usersList" value="${onLineUsers.value }"/>
		</c:forEach>
		<font style="font-size:12px;">在线交易商总数:${totalNumber }</font>
		<br><br>
		<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%" height="300">
	  		<tHead>
	  			<tr height="25"  align=center>
	  		    <td class="panel_tHead_LB">&nbsp;</td>
			    <td class="panel_tHead_MB" align="center">用户代码</td>
			    <td class="panel_tHead_MB" align="center">登录时间</td>
			    <td class="panel_tHead_MB" align="center">登录IP </td>
				<td class="panel_tHead_RB">&nbsp;</td>
				</tr>
			</tHead>
			<tBody>
				<c:forEach items="${usersList }" var="user">
					<tr onclick="selectTr();" align=center height="25">
				  		<td class="panel_tBody_LB">&nbsp;</td>
				  		<td class="underLine" align="center">${user.userId }</td>
				  		<td class="underLine" align="center">${user.logonTime }</td>
				  		<td class="underLine" align="center">${user.logonIp }</td>
				  		<td class="panel_tBody_RB">&nbsp;</td>
			  		</tr>
				</c:forEach>
		  	</tBody>
			<tFoot>
				<tr height="100%">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td colspan="3">&nbsp;</td>
					<td class="panel_tBody_RB">&nbsp;</td>
				</tr>
				<tr height="22">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td colspan="3" align="right" class="pagerext">
						<%//@ include file="../public/pagerInc.jsp" %>
					</td>
					<td class="panel_tBody_RB">&nbsp;</td>
				</tr>
				<tr height="22">
					<td class="panel_tFoot_LB">&nbsp;</td>
					<td class="panel_tFoot_MB" colspan="3"></td>
					<td class="panel_tFoot_RB">&nbsp;</td>
				</tr>
			</tFoot>			
		</table>
		</fieldset>
</form>
</body>
<script>
	function doQuery() {
		frm.submit();
	}
	
	function resetForm() {
		frm.userId.value = "";
		frm.submit();
	}
</script>