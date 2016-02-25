<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../globalDef.jsp"%>

<!--权限判断-->
<%
  String id=request.getParameter("comRight");
  String userRight=request.getParameter("userRight");
  CheckRight checkRight=new CheckRight();
  checkRight.JNDI=JNDI;
  boolean flag=true;
  if("".equals(userRight)||id==null){//用户session过期
%>
 <script language="javascript">
   parent.window.location.href="<%=request.getContextPath()%>/vendue/manage/logon.jsp";
 </script>
<%}else{  
  flag=checkRight.checkRight(Long.parseLong(userRight),Integer.parseInt(id));
  if(flag==false){
%>
 <script>
 	 alert("没有权限,请与管理员联系!");
 	 window.history.back();
 	 window.close();
 </script>
<%}
}
%>
