<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/broker/public/includefiles/allincludefiles.jsp"%>
<script language="javascript">

if(${prompt!=null}){
	alert('<%=(String)request.getAttribute("prompt")%>');
}else{
	alert("��½�ɹ�");
	parent.ListFrame.closewindow();
}
</script>

