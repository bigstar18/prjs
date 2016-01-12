<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<script language="javascript">

if(${prompt!=null}){
	alert('<%=(String)request.getAttribute("prompt")%>');
}else{
	alert("µÇÂ¼³É¹¦");
	parent.ListFrame.closewindow();
}
</script>

