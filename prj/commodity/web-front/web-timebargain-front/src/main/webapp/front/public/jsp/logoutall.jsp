<%@ page contentType="text/html;charset=GBK"%>
<%@page import="gnnt.MEBS.common.front.common.Global"%>
<%
String serverInterface = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
String serverName = request.getContextPath();
String basePath = serverInterface + serverName;

request.setAttribute("modelContextMap",Global.modelContextMap);
request.setAttribute("selfModuleId",Global.getSelfModuleID());
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
		<title></title>
	</head>
	<body style="background-color: #fff6f4;">
		</br>
		</br>
		</br>
		</br>
		<table align="center">
			<tr>
				<td align="center">
					<!-- ���ĵ�¼��ʧЧ�����˳�ϵͳ���µ�¼�� -->
				</td>
			</tr>
		</table>
	</body>
</html>
<%
	String msg =request.getParameter("errorMsg");
    if(msg == null){msg = "";}
    String tologinURLReason="USERISNULL";
    String tologinPreUrl="";
    if(request.getParameter(Global.TOLOGINURLREASON)!=null){
    	tologinURLReason=(String)request.getParameter(Global.TOLOGINURLREASON);
    }
    
    String prompt="";
    
    if("USERISNULL".equals(tologinURLReason)){
    	prompt="����δ��¼�����¼��ִ��" + msg + "����!";
    }
    else if("AUOVERTIME".equals(tologinURLReason)){
    	prompt="��¼����ʱ������������µ�¼";
    }else if("NOPURVIEW".equals(tologinURLReason)){
    	prompt="�˴ε�¼��Ȩ�ޣ������µ�¼";
    }else if("AUUSERKICK".equals(tologinURLReason)){
    	prompt="�����˺�����һ�ص��¼����������";
    }
    else{
    	prompt="�����µ�¼";
    }
%>
<form id="frm" action="" method="post" target="_top">
<input type="hidden" name="errorMsg" value="<%=msg%>">
<%
if(request.getParameter(Global.TOLOGINPREURL)!=null){
	tologinPreUrl=(String)request.getParameter(Global.TOLOGINPREURL);
	out.print("<input type='hidden' name='"+Global.TOLOGINPREURL+"' value='"+tologinPreUrl+"'>");
}else if(request.getAttribute(Global.TOLOGINPREURL) != null){
	tologinPreUrl=(String)request.getAttribute(Global.TOLOGINPREURL);
	out.print("<input type='hidden' name='"+Global.TOLOGINPREURL+"' value='"+tologinPreUrl+"'>");
}
%>
</form>
<SCRIPT LANGUAGE="JavaScript">
<!--
  if(window.dialogArguments==undefined){
      
      try{
          var a = top.document.getElementById("logout");
          if(a){
			a.click();
          }else{
        	  frm.action="<%=basePath%>/front/logon/logon.jsp";
              frm.submit();
          }
      }catch(e){
    	  frm.action="<%=basePath%>/front/logon/logon.jsp";
          frm.submit();
      }
   }else{
	  window.returnValue = 1111;
	  window.close();
   }
	   
//-->
</SCRIPT>