<%@ page contentType="text/html;charset=GBK" %>
<%@ page import='gnnt.MEBS.finance.manager.UserManager' %>
<%@ page import='gnnt.MEBS.finance.unit.User' %>
<%
	String userId = request.getParameter("userId");
	User user = null;
	if(userId != null){
		user = UserManager.getUserById( userId );
		pageContext.setAttribute("user", user);
	}
%>
<html> 
  <head>
    <%@ include file="../public/headInc.jsp" %>
	<title>修改用户</title>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script>
		function doSubmit() 
		{
			if(!checkValue("formNew"))
				return;
			if(formNew.password.value!=formNew.confirmPwd.value){
				alert("口令与确认口令不一致，请重新输入！");
				return;
			}
			formNew.submit();
		}
	</script> 
</head>
<body>
        <form id="formNew" action="<%=basePath%>/userUpdate.spr" method="POST" targetType="hidden" callback="closeDialog(1);">
		<fieldset width="100%">
		<legend>用户基本信息</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
                <td align="right"> <%=USERID%> ：</td>
                <td align="left">
                	<input class="readonly" id="userId" name="userId" value="<c:out value='${user.userId}'/>" style="width: 150px;" reqfv="required;用户ID" readonly>
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> <%=USERNAME%> ：</td>
                <td align="left">
                	<input name="userName" type="text" value="<c:out value='${user.userName}'/>" class="text" style="width: 150px;" reqfv="required;用户名称">
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> 用户口令 ：</td>
                <td align="left">
                	<input id="password" name="password" type="password" value="<c:out value='${user.password}'/>" class="text" style="width: 150px;" reqfv="required;用户口令">
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> <%=CONFORMPWD%> ：</td>
                <td align="left">
                	<input id="confirmPwd" type="password" value="<c:out value='${user.password}'/>" class="text" style="width: 150px;" reqfv="required;用户口令">
                </td>
              </tr>
              <tr height="35">
                <td align="right"> 用户状态 ：</td>
                <td align="left">
                	<input type="radio" name="enabled" value="true" <c:if test="${user.enabled}">checked</c:if> >可用
                	<input type="radio" name="enabled" value="false" <c:if test="${!user.enabled}">checked</c:if>>禁用
                </td>
              </tr>
              <tr height="35">
                <td align="right"> 用户权限 ：</td>
                <td align="left">
                	<input type="checkbox" name="role" value="roleAdmin" <%if(user!=null&&user.hasAuthority("roleAdmin")){ out.println("checked"); }%> >管理员
                	<input type="checkbox" name="role" value="roleInput" <%if(user!=null&&user.hasAuthority("roleInput")){ out.println("checked"); }%> >录入员
                	<input type="checkbox" name="role" value="roleConfirm" <%if(user!=null&&user.hasAuthority("roleConfirm")){ out.println("checked"); }%> >确认员
                	<input type="checkbox" name="role" value="roleAudit" <%if(user!=null&&user.hasAuthority("roleAudit")){ out.println("checked"); }%> >审核员
                	<input type="checkbox" name="role" value="roleQuery" <%if(user!=null&&user.hasAuthority("roleQuery")){ out.println("checked"); }%> >查询用户 
                </td>
              </tr>
              <tr height="35">
                <td colspan="2"><div align="center">
                  <button class="smlbtn" type="button" onclick="doSubmit();">提交</button>&nbsp;
      			  <button class="smlbtn" type="button" onclick="window.close()">关闭窗口</button>
                </div></td>
              </tr>
          </table>
		</fieldset>
        </form>
</body>
</html>
<%@ include file="../public/footInc.jsp" %>