<%@ page contentType="text/html;charset=GBK" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title>长三角商品交易所(模拟)</title>
</head>
<body style="background-color:#fff6f4;">
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
    String invalidationSign="S";
    if(session.getAttribute("invalidationSign")!=null){
    	invalidationSign=(String)session.getAttribute("invalidationSign");
    }
    String prompt="";
    
    if("N".equals(invalidationSign)){
    	prompt="此账号已在别处登录";
    }
    else if("Y".equals(invalidationSign)){
    	prompt="登录闲置时间过长，请重新登录";
    }else if("K".equals(invalidationSign)){
    	prompt="此次登录已失效，如需使用请重新登录";
    }
%>
<SCRIPT LANGUAGE="JavaScript">
<!--
  if(window.dialogArguments==undefined){
	  if('<%=prompt%>'!=''){
		alert('<%=prompt%>');  
	  }
      top.location.href='<%=request.getContextPath()%>/logon.jsp';
   }else{
	  /*if('<%=prompt%>'!=''){
		alert('<%=prompt%>');  
	  }*/
	  window.returnValue = 1111;
	  window.close();
   }
	   
//-->
</SCRIPT>