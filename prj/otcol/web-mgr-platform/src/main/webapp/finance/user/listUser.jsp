<%@ page contentType="text/html;charset=GBK" %>
<html>
  <head>
    <%@ include file="../public/headInc.jsp" %>
    <title>用户列表</title>
    <script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="JavaScript">
		function createNew(){
			var returnValue = openDialog("<%=basePath%>/user/createUser.jsp","_blank",500,350);
			if(returnValue)
				window.location.reload();
		}
		function doQuery(){
			frm_query.submit();
		}
		function resetForm(){
			frm_query.userId.value = "";
			frm_query.userName.value = "";
			frm_query.enabled.value = "";
		}
		function editInfo(vCode){
			var returnValue = openDialog("<%=basePath%>/user/editUser.jsp?userId="+vCode ,"_blank",500,350);
			if(returnValue)
				window.location.reload();
		}
	</script>
  </head>
  <c:if test="${!roleAdmin}">
		<c:redirect url="../logon.jsp"/>
  </c:if>
  <body>
  	<form id="frm_query" action="<%=basePath%>/userList.spr" method="post">
		<input type="hidden" name="orderField" value="userId">
		<input type="hidden" name="orderDesc" value="false">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
		<fieldset width="95%">
			<legend>用户查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="90%" height="35">
				<tr height="35">
					<td align="right"><%=USERID%>&nbsp;</td>
					<td align="left">
						<input id="userId" name="_userId[like]" value="<c:out value='${oldParams["userId[like]"]}'/>" type=text class="text" style="width: 100px">
					</td>
					<td align="right"><%=USERNAME%>&nbsp;</td>
					<td align="left">
						<input id="userName" name="_userName[like]" value="<c:out value='${oldParams["userName[like]"]}'/>" type=text  class="text" style="width: 100px">
					</td>
					<td align="right">状态&nbsp;</td>
					<td align="left">
						<select id="enabled" name="_enabled[=]" class="normal" style="width: 100px">
							<OPTION value="">全部</OPTION>
							<option value="0">禁用</option>
              				<option value="1">可用</option>
						</select>
					</td>
					<script>
						frm_query.enabled.value = "<c:out value='${oldParams["enabled[=]"]}'/>"
					</script>
					<td align="left">
						<button type="button" class="smlbtn" onclick="doQuery();">查询</button>&nbsp;
						<button type="button" class="smlbtn" onclick="resetForm();">重置</button>&nbsp;
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
	<form id="frm_delete" name="frm_delete" action="<%=basePath%>/userDelete.spr" method="post" targetType="hidden" callback="doQuery();">
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25">
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB"><input type="checkbox" id="checkAll" onclick="selectAll(tableList,'delCheck')"></td>
				<td class="panel_tHead_MB"><%=USERID%></td>
				<td class="panel_tHead_MB"><%=USERNAME%></td>
				<td class="panel_tHead_MB">用户状态</td>
				<td class="panel_tHead_MB">权限</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
	  		<c:forEach items="${resultList}" var="result">
	  		<tr height="22" onclick="selectTr();">
	  			<td class="panel_tBody_LB">&nbsp;</td>
	  			<td class="underLine">
	  				<input name="delCheck" type="checkbox" value="<c:out value="${result.userId}"/>">
	  			</td>
	  			<td class="underLine">
	  				<span onclick="editInfo('<c:out value="${result.userId}"/>')" style="cursor:hand;color:blue">
	  				<c:out value="${result.userId}"/></span></td>
	  			<td class="underLine"><c:out value="${result.userName}"/></td>
	  			<td class="underLine"><c:out value="${result.enabled}"/></td>
	  			<td class="underLine"><c:out value="${result.authoritiesJion}"/>&nbsp;</td>
	  			<td class="panel_tBody_RB">&nbsp;</td>
	  		</tr>
	  		</c:forEach>
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="5">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="5">
					<%@ include file="../public/pagerInc.jsp" %>
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="5"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>
	</from>
   	<table border="0" cellspacing="0" cellpadding="0" width="80%">
	    <tr height="35">
            <td><div align="right">
              <button class="smlbtn" type="button" onclick="createNew()">创建用户</button>&nbsp;&nbsp;
  			  <button class="smlbtn" type="button" onclick="deleteRec(frm_delete,tableList,'delCheck')">删除用户</button>
            </div></td>
        </tr>
    </table>
  </body>
</html>
<%@ include file="../public/footInc.jsp" %>