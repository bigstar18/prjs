<%@ page contentType="text/html;charset=GBK"%>
<%@page import="gnnt.MEBS.common.front.common.Global"%>
<%@ include file="/front/public/includefiles/path.jsp" %>
<body>
	<form id="frm" action="${preUrl}" method="post"></form>
</body>
<script>
if('<%=request.getAttribute(Global.TOLOGINPREURL)%>' == '' || '<%=request.getAttribute(Global.TOLOGINPREURL)%>' == 'null'){
	frm.action="${frontPath}/frame/index.jsp";
}else if('<%=request.getAttribute(Global.TOLOGINPREURL)%>'.indexOf('logon')>0){
	frm.action="${frontPath}/frame/index.jsp";
}else if('<%=request.getAttribute(Global.TOLOGINPREURL)%>'.indexOf('loading')>0){
	frm.action='${frontPath}/frame/index.jsp';
}else if('<%=request.getAttribute(Global.TOLOGINPREURL)%>'.indexOf('error')>0){
	frm.action='${frontPath}/frame/index.jsp';
}else if('<%=request.getAttribute(Global.TOLOGINPREURL)%>'.indexOf('noright')>0){
	frm.action='${frontPath}/frame/index.jsp';
}else if('<%=request.getAttribute(Global.TOLOGINPREURL)%>'.indexOf('nomoduleright')>0){
	frm.action='${frontPath}/frame/index.jsp';
}else if('<%=request.getAttribute(Global.TOLOGINPREURL)%>'.indexOf('/checkneedless')>0){
	frm.action='${preUrl}';
}else if('<%=request.getAttribute(Global.TOLOGINPREURL)%>'=='${basePath}'){
	frm.action='${preUrl}';
}else if('${preUrl}'!=''){
	frm.action='${preUrl}';
}else{
	frm.action="${frontPath}/frame/index.jsp";
}
frm.submit();
</script>