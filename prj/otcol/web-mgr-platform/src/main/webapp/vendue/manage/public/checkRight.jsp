<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../globalDef.jsp"%>

<!--Ȩ���ж�-->
<%
  String id=request.getParameter("comRight");
  String userRight=request.getParameter("userRight");
  CheckRight checkRight=new CheckRight();
  checkRight.JNDI=JNDI;
  boolean flag=true;
  if("".equals(userRight)||id==null){//�û�session����
%>
 <script language="javascript">
   parent.window.location.href="<%=request.getContextPath()%>/vendue/manage/logon.jsp";
 </script>
<%}else{  
  flag=checkRight.checkRight(Long.parseLong(userRight),Integer.parseInt(id));
  if(flag==false){
%>
 <script>
 	 alert("û��Ȩ��,�������Ա��ϵ!");
 	 window.history.back();
 	 window.close();
 </script>
<%}
}
%>
