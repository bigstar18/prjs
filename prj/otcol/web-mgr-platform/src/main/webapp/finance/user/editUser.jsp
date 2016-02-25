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
	<title>�޸��û�</title>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script>
		function doSubmit() 
		{
			if(!checkValue("formNew"))
				return;
			if(formNew.password.value!=formNew.confirmPwd.value){
				alert("������ȷ�Ͽ��һ�£����������룡");
				return;
			}
			formNew.submit();
		}
	</script> 
</head>
<body>
        <form id="formNew" action="<%=basePath%>/userUpdate.spr" method="POST" targetType="hidden" callback="closeDialog(1);">
		<fieldset width="100%">
		<legend>�û�������Ϣ</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
                <td align="right"> <%=USERID%> ��</td>
                <td align="left">
                	<input class="readonly" id="userId" name="userId" value="<c:out value='${user.userId}'/>" style="width: 150px;" reqfv="required;�û�ID" readonly>
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> <%=USERNAME%> ��</td>
                <td align="left">
                	<input name="userName" type="text" value="<c:out value='${user.userName}'/>" class="text" style="width: 150px;" reqfv="required;�û�����">
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> �û����� ��</td>
                <td align="left">
                	<input id="password" name="password" type="password" value="<c:out value='${user.password}'/>" class="text" style="width: 150px;" reqfv="required;�û�����">
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> <%=CONFORMPWD%> ��</td>
                <td align="left">
                	<input id="confirmPwd" type="password" value="<c:out value='${user.password}'/>" class="text" style="width: 150px;" reqfv="required;�û�����">
                </td>
              </tr>
              <tr height="35">
                <td align="right"> �û�״̬ ��</td>
                <td align="left">
                	<input type="radio" name="enabled" value="true" <c:if test="${user.enabled}">checked</c:if> >����
                	<input type="radio" name="enabled" value="false" <c:if test="${!user.enabled}">checked</c:if>>����
                </td>
              </tr>
              <tr height="35">
                <td align="right"> �û�Ȩ�� ��</td>
                <td align="left">
                	<input type="checkbox" name="role" value="roleAdmin" <%if(user!=null&&user.hasAuthority("roleAdmin")){ out.println("checked"); }%> >����Ա
                	<input type="checkbox" name="role" value="roleInput" <%if(user!=null&&user.hasAuthority("roleInput")){ out.println("checked"); }%> >¼��Ա
                	<input type="checkbox" name="role" value="roleConfirm" <%if(user!=null&&user.hasAuthority("roleConfirm")){ out.println("checked"); }%> >ȷ��Ա
                	<input type="checkbox" name="role" value="roleAudit" <%if(user!=null&&user.hasAuthority("roleAudit")){ out.println("checked"); }%> >���Ա
                	<input type="checkbox" name="role" value="roleQuery" <%if(user!=null&&user.hasAuthority("roleQuery")){ out.println("checked"); }%> >��ѯ�û� 
                </td>
              </tr>
              <tr height="35">
                <td colspan="2"><div align="center">
                  <button class="smlbtn" type="button" onclick="doSubmit();">�ύ</button>&nbsp;
      			  <button class="smlbtn" type="button" onclick="window.close()">�رմ���</button>
                </div></td>
              </tr>
          </table>
		</fieldset>
        </form>
</body>
</html>
<%@ include file="../public/footInc.jsp" %>