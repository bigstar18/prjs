<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../globalDef.jsp"%>

<!--Ȩ���ж�-->
<!--�ж�����Ȩ��������һ��ʱ���Է���-->
<%
  String id=request.getParameter("comRight");
  String id1=request.getParameter("comRight1");
  String userRight=request.getParameter("userRight");
  CheckRight checkRight=new CheckRight();
  checkRight.JNDI=JNDI;
  boolean flag=true;
  boolean flag1=true;
  if(userRight==null||"".equals(userRight)||id==null||id1==null){//�û�session����
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
 	  alert("û��Ȩ��,�������Ա��ϵ!");
 	  window.history.back();
 	  window.close();
 </script>
<%}
}
%>
