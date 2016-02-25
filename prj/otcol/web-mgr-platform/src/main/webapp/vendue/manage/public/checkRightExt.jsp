<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../globalDef.jsp"%>

<!--权限判断-->
<!--判断两个权限有其中一个时可以访问-->
<%
  String id=request.getParameter("comRight");
  String id1=request.getParameter("comRight1");
  String userRight=request.getParameter("userRight");
  CheckRight checkRight=new CheckRight();
  checkRight.JNDI=JNDI;
  boolean flag=true;
  boolean flag1=true;
  if(userRight==null||"".equals(userRight)||id==null||id1==null){//用户session过期
%>
 <script language="javascript">
   parent.window.location.href="<%=request.getContextPath()%>/vendue/manage/logon.jsp";
 </script>
<%}else{  
  flag=checkRight.checkRight(Long.parseLong(userRight),Integer.parseInt(id));
  flag1=checkRight.checkRight(Long.parseLong(userRight),Integer.parseInt(id1));
  if(flag==false&&flag1==false){
%>
 <script>
 	  alert("没有权限,请与管理员联系!");
 	  window.history.back();
 	  window.close();
 </script>
<%}
}
%>
