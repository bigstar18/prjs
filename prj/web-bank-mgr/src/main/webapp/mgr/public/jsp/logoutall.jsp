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
    <td align="center"><!-- ���ĵ�¼��ʧЧ�����˳�ϵͳ���µ�¼�� --></td>
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
    	prompt="�û���ϢΪ�գ������µ�¼";
    }
    else if("AUOVERTIME".equals(tologinURLReason)){
    	prompt="��¼����ʱ������������µ�¼";
    }else if("NOPURVIEW".equals(tologinURLReason)){
    	prompt="�˴ε�¼��Ȩ�ޣ������µ�¼";
    }else if("AUUSERKICK".equals(tologinURLReason)){
    	prompt="�����˺�����һ�ص��½����������";
    }
    else{
    	prompt="�����µ�¼";
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
	  //����ǵ����������÷���ֵΪ 1111 ���غ���ҳ�����ˢ��ʱ���ִ����Ӧ�Ĳ���
	  window.returnValue = 1111;
	  //�رյ���ҳ
	  window.close();
   }
	  
//-->
</SCRIPT>