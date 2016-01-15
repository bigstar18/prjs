<%@ page contentType="text/html;charset=GBK" %>
<%@page import="gnnt.MEBS.common.mgr.common.Global"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title></title>
</head>
<body >
</br>
</br>
</br>
</br>
<table align="center">
  <tr>
    <td align="center"><!-- 您的登录已失效，请退出系统重新登录！ --></td>
  </tr>
</table>
</body>
</html>
<%
    String tologinURLReason="USERISNULL";
    if(request.getParameter(Global.TOLOGINURLREASON)!=null){
    	tologinURLReason=(String)request.getParameter(Global.TOLOGINURLREASON);
    }
    String prompt="";
    
    if("USERISNULL".equals(tologinURLReason)){
    	prompt="用户信息为空，请重新登录";
    }
    else if("AUOVERTIME".equals(tologinURLReason)){
    	prompt="登录闲置时间过长，请重新登录";
    }else if("NOPURVIEW".equals(tologinURLReason)){
    	prompt="此次登录无权限，请重新登录";
    }else if("AUUSERKICK".equals(tologinURLReason)){
    	prompt="您的账号在另一地点登陆，被迫下线";
    }
    else{
    	prompt="请重新登录";
    }
%>
<SCRIPT LANGUAGE="JavaScript">
<!--
  if(window.dialogArguments==undefined){
	  try{
          var a = top.document.getElementById("logout");
          if(a){
			a.click();
          }else{
        	  top.location.href="<%=request.getContextPath()%>";
          }
      }catch(e){
    	  top.location.href="<%=request.getContextPath()%>";
      }
   }else{
	  //如果是弹出框，则设置返回值为 1111 返回后，主页面进行刷新时则会执行相应的操作
	  window.returnValue = 1111;
	  //关闭弹出页
	  window.close();
   }
	  
//-->
</SCRIPT>