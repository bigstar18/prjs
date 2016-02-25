<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../globalDef.jsp"%>
<%
  ActiveUserManager au = new ActiveUserManager(SPACE,EXPIRETIME,MODE);
  String username=session.getAttribute("LOGINID").toString();
  String remark="系统用户"+username+"退出";
  OptLog log=new OptLog(JNDI);
  log.log(LOGOUT,username,remark);
  Long lid=(Long)session.getAttribute("LOGINIDS");
  au.logoff(lid.longValue());
  session.removeAttribute("LOGINIDS");
  session.removeAttribute("LOGINID");
  session.removeAttribute("USERRIGHT");
  session.removeAttribute("USERPAR");
  session.invalidate();
%>
<script language="javascript">
	parent.window.location.href="<%=request.getContextPath()%>/vendue/manage/logon.jsp";
</script>
