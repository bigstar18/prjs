<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>

<%
	Boolean isOK = (Boolean)request.getAttribute("isOK");
	String commodityID = (String)request.getAttribute("commodityID");
%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
		<title>
		</title>
<script type="text/javascript"> 

function isOK(){
	//alert('<%=isOK%>');
	if ('<%=isOK%>' == "true") {
		document.location.href = "<c:url value="/timebargain/statquery/statQuery.do?funcflg=settleProcessHand&commodityID="/>" + '<%=commodityID%>';
	}else {
		if (confirm("此商品已做过交收，您是否需要重做交收？")) {
			document.location.href = "<c:url value="/timebargain/statquery/statQuery.do?funcflg=settleProcessHand&commodityID="/>" + '<%=commodityID%>';
		}
		
	}
}



</script>
	</head>

	<body leftmargin="0" topmargin="0" onload="isOK()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="400" width="600" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/baseinfo/customer.do?funcflg=save"
						method="POST" styleClass="form" target="mainFrame">
						
					</html:form>
				</td>
			</tr>
		</table>

		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
