<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<html>
	<head>
	    <%@ include file="/timebargain/common/meta.jsp" %>
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<c:import url="/timebargain/common/dblCustomerID.jsp"/>
		<title>
		</title>
		<script type="text/javascript"> 
function window_onload()
{
    highlightFormElements();
    setReadOnly(broadcastForm.title);
    setReadOnly(broadcastForm.content);
}
function cancel_onclick(){
	document.location.href = "<c:url value="/timebargain/tradecontrol/hisBroadcast.jsp"/>";
}

</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="80%" width="70%" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/tradecontrol/broadcast.do?funcflg=saveWait"
						method="POST" styleClass="form">
						<fieldset class="pickList" >
							<legend class="common">
								<b>
								  历史广播消息
								</b>
							</legend>
							<table border="0" align="center" cellpadding="5" cellspacing="5"
								class="common">
								<tr>
									<td align="right">
											标题：
									</td>
									<td>
										<html:text property="title" maxlength="32" size="52" styleClass="text"/>
										<span class="req">*</span>
									</td>
									
								</tr>
								<tr>
									<td align="right">
											内容：
									</td>
									<td>
									    <html:textarea property="content" cols="50" rows="5" styleClass="text"></html:textarea>
										<span class="req">*</span>
									</td>
								</tr>
								<tr>
									<td colspan="2" align="center">
										<html:button property="cancel" styleClass="button"
											onclick="javascript:return cancel_onclick();">
											返回
										</html:button>
									</td>
								</tr>
							</table>
						</fieldset>
						
					</html:form>
				</td>
			</tr>
		</table>
		
		<script type="text/javascript"
			src="<html:rewrite page="/timebargain/common/validator.jsp"/>"></script>
		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
