<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/common.jsp"%>
<%@ page import="gnnt.MEBS.member.ActiveUser.ActiveUserManager"%>
<body>
<form name="frm" action="<%=commonUserControllerPath %>commonUserForcedOffline" method="post">
       <input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo" value="<c:out value="${pageInfo.pageNo}"/>">
       <fieldset width="100%">
       <legend>在线系统用户查询</legend>
       <span>
         <table border="0" cellspacing="0" cellpadding="0" width="40%">
         <tr height="35">
         	<td align="left"> 用户代码 ：</td>
             <td align="left">
             <input name="userId[like]" id="userId" type="text" class="text" style="width: 100px;" value="<%= request.getParameter("userId[like]")== null ? "": request.getParameter("userId[like]")%>" onkeypress="onlyNumberAndCharInput()" maxlength="16">
             </td>
             <td align="left">
            	<input type="button" onclick="doQuery()" class="btn" value="查询">&nbsp;
            	<!-- add by yangpei 2011-11-22 增加重置功能 -->
            	<input type="button" onclick="resetForm()" class="btn" value="重置">
            	<script>
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
		<c:forEach items="${onLineUsersMap }" var="onLineUsers">
			<c:set var="totalNumber" value="${onLineUsers.key }"/>
			<c:set var="usersList" value="${onLineUsers.value }"/>
		</c:forEach>
		<font style="font-size:12px;">在线管理员总数:${totalNumber }</font>
		<br><br>
		<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%" height="300">
	  		<tHead>
	  			<tr height="25"  align=center>
	  		    <td class="panel_tHead_LB">&nbsp;</td>
	  		    <td class="panel_tHead_MB" align=left><input class=checkbox_1 type="checkbox" id="checkAll" onclick="selectAll(tb,'ck')"></td>
			    <td class="panel_tHead_MB" align=left>用户代码</td>
			    <td class="panel_tHead_MB" align=left>登录时间</td>
			    <td class="panel_tHead_MB" align=left>登录IP </td>
				<td class="panel_tHead_RB">&nbsp;</td>
				</tr>
			</tHead>
			<tBody>
				<c:forEach items="${usersList }" var="user">
					<tr onclick="selectTr();" align=center height="25">
				  		<td class="panel_tBody_LB">&nbsp;</td>
				  		<td class="underLine" align=left>
				  			<input name="ck" type="checkbox" value="${user.sessionId }">
				  		</td>
				  		<td class="underLine" align=left>${user.userId }</td>
				  		<td class="underLine" align=left>${user.logonTime }</td>
				  		<td class="underLine" align=left>${user.logonIp }</td>
				  		<td class="panel_tBody_RB">&nbsp;</td>
			  		</tr>
				</c:forEach>
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
						<%//@ include file="../public/pagerInc.jsp" %>
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
          	<td width="40%"><div align="right">
			   <input type="button" onclick="return deleteRec(frm,tb,'ck');" class="btn" value="强制下线">&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          </div>
          <input type="hidden" name="opt">
          </td>
          </tr>
     </table>	
</form>
</body>
<script>
	function doQuery()
	{
		frm.action="<%=commonUserControllerPath %>commonUserOnLineList";
		frm.submit();
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
</script>