<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<script language="javascript">

if(${prompt!=null}){
	alert('<%=(String)request.getAttribute("prompt")%>');
}else{
	alert("��¼�ɹ�");
	parent.ListFrame.closewindow();
}
</script>

