<%@ page contentType="text/html;charset=GBK" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title>��������Ʒ������(ģ��)</title>
</head>
<body style="background-color:#fff6f4;">
</br>
</br>
</br>
</br>
<table align="center">
  <tr>
    <td align="center"><!-- ���ĵ�¼��ʧЧ�����˳�ϵͳ���µ�¼�� --></td>
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
    	prompt="���˺����ڱ𴦵�¼";
    }
    else if("Y".equals(invalidationSign)){
    	prompt="��¼����ʱ������������µ�¼";
    }else if("K".equals(invalidationSign)){
    	prompt="�˴ε�¼��ʧЧ������ʹ�������µ�¼";
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