<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="gnnt.MEBS.servlet.Login_syn"/>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	String sid = (String)request.getParameter("sid");
	String fid = (String)request.getParameter("fid");
	long sessionID=-1;
	
	if(sid==null||sid.equals("")||sid.toUpperCase().equals("NULL")){
		//��һ�ε�½
		sid = ""+session.getAttribute("LOGINID");
		fid = ""+session.getAttribute("FIRMID");
	}else{
		//���ǵ�һ�ε�½
		String username = (String)session.getAttribute("FIRMID");
		//username=null;
		if(username==null||username.equals("")||username.toUpperCase().equals("NULL")){
			System.out.println("��ʱ");
			//��ʱ
			sessionID=Login_syn.checkUser(fid,Long.valueOf(sid),request,response);
			if(sessionID>0){
				sid = ""+session.getAttribute("LOGINID");
				fid = ""+session.getAttribute("FIRMID");
			}else{
				request.getRequestDispatcher("main_1.jsp").forward(request,response);
			}
		}
	}
%>

<%
	long lid = -1000;
	try{
		lid = ((Long)session.getAttribute("LOGINID")).longValue();	
	}catch(Exception se){
		
	}
%>
