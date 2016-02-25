<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<title>
default
</title>
<%@ include file="/timebargain/common/messages.jsp" %>
<script type="text/javascript">
	function alertMsg(){
	var msg1='${msg}';
	if(msg1!=""){
		alert(msg1);
	}

	window.location.href="<c:url value="/timebargain/delay/settleprivilege.jsp"/>";
}
</script>
</head>
<body leftmargin="2" topmargin="0"  onload="alertMsg()">

</body>


</html>
