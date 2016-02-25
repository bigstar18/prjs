<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>" />
		<c:import url="/timebargain/statquery/customerID_change.jsp"/>
		<c:import url="/timebargain/common/date.jsp"/>
		<script language="VBScript" src="<c:url value="/timebargain/scripts/vbfunction.vbs"/>"></script>
		<title>
		</title>
		<script type="text/javascript"> 
function window_onload()
{
    highlightFormElements();
    applyForm.crud.value = "<c:out value="${param['applyType']}"/>";
}
//query_onclick
function query_onclick()
{	
  if (applyForm.billID.value == "") {
  	alert("查询条件不能为空！");
  	applyForm.billID.focus();
  	return false;
  }
  applyForm.submit();
}



</script>
	</head>

	<body leftmargin="2" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<c:import url="/timebargain/common/waitbar.jsp" charEncoding="GBK"/>
		<table border="0" height="100%" width="100%" cellpadding="0" cellspacing="0" class="common" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/apply/apply.do?funcflg=applyGiveUpDelayQuery"
						method="POST" styleClass="form" target="ListFrame2">
						<fieldset class="pickList" >
							<legend class="common">
								<b>撤销延期交收
								</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0"
								class="common">

								<tr>
									<td align="left">
											仓单号：
									</td>
									<td>
										<html:text property="billID" styleClass="text" maxlength="16" size="12"></html:text>
										
									</td>
									<td align="left">
										&nbsp;&nbsp;<html:button property="query" style="width:60" styleClass="button"
											onclick="javascript:return query_onclick();">
											查询
										</html:button>
										
									</td>  	                                                          							
								</tr>
								
							</table>
						</fieldset>
						<html:hidden property="crud"/>
					</html:form>
				</td>
			</tr>
		</table>

		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
