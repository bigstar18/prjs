<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ page import="gnnt.trade.bank.dao.*"%>
<%@ page import="gnnt.trade.bank.vo.*"%>
<%@ page import="gnnt.trade.bank.util.*"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
BankDAO dao = BankDAOFactory.getDAO();
String filter="";

int[] pageinfo=new int[3];
//pageinfo[0]:int totalCount��������
//pageinfo[1]:int pageNo,ҳ��
//pageinfo[2]:int pageSize ÿҳ����
pageinfo[0]=-2;
pageinfo[1]=1;
pageinfo[2]=10;

Vector logList = dao.interfaceLogList(filter,pageinfo);


System.out.println("pageinfo0="+pageinfo[0]);
System.out.println("pageinfo1="+pageinfo[1]);
System.out.println("pageinfo2="+pageinfo[2]);

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'MyJsp.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <table>
    	<%
			for(int i=0;i<logList.size();i++){
				InterfaceLog log = (InterfaceLog)logList.get(i);
				//out.print(log.bankName);
				%>
				<tr height="22" align=center onclick="selectTr();">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine_last" align="center"><%=Tool.fmtTime(log.createtime)%>&nbsp;</td>
					<td class="underLine" align="center"><%
						if(log.launcher==0){
							out.print("�г�");
						}else if(log.launcher==1){
							out.print("����");
						}else{
							out.print("����");
						}
					%>&nbsp;</td>
					<td class="underLine" align="center"><%
						if(log.type==1){
							out.print("ǩ��");
						}else if(log.type==2){
							out.print("ǩ��");
						}else if(log.type==3){
							out.print("ǩԼ");
						}else if(log.type==4){
							out.print("��Լ");
						}else if(log.type==5){
							out.print("��ѯ���");
						}else if(log.type==6){
							out.print("����");
						}else if(log.type==7){
							out.print("���");
						}else if(log.type==8){
							out.print("����");
						}else{
							out.print("����");
						}
					%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.delNull(log.bankName)%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.delNull(log.firmID)%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.delNull(log.contact)%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.delNull(log.account)%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.delNull(log.endMsg)%>&nbsp;</td>
					
					
					<td class="panel_tBody_RB">&nbsp;</td>
	  			</tr>	
				<%
			}
			%>
    
    
    
    
    </table>
    
    
    
  </body>
</html>
