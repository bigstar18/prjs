<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<%@ include file="/timebargain/widgets/calendar/calendar.jsp" %>
		<script language="VBScript" src="<c:url value="/timebargain/scripts/vbfunction.vbs"/>"></script>
		<title>
		</title>
		<script type="text/javascript"> 
function window_onload()
{
    highlightFormElements();
    reportForm.clearDate.focus();
}
//
function required()
{
  if(reportForm.clearDate.value=="")
  {
    alert("<fmt:message key="query.tradeDay"/><fmt:message key="prompt.required"/>");
    reportForm.clearDate.focus();
    return false;
  }
  if(!isDateFormat(reportForm.clearDate.value))
  {
    alert("<fmt:message key="query.tradeDay"/><fmt:message key="formatError"/>");
    reportForm.clearDate.focus();
    return false;
  }
  return true;   
}
//preview
function doPreview()
{
  if(!required())
  {
    return false;
  } 
  reportForm.exportTo.value = "pdf";
  reportForm.submit();   
}
//export
function doExport()
{
  if(!required())
  {
    return false;
  } 
  reportForm.exportTo.value = "excel";
  reportForm.submit(); 
}

</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="80%" width="50%" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/report/report.do?funcflg=printAccount"
						method="POST" styleClass="form" target="_blank">
						<fieldset class="pickList" >
							<legend class="common">
								<b><fmt:message key="report.account.title" />
								</b>
							</legend>
							<table border="0" align="center" cellpadding="5" cellspacing="5"
								class="common">

								<tr>
									<td align="right">
										<fmt:message key="query.tradeDay" />
									</td>
									<td>
									    <input type="text" id="clearDate" name="_a.ClearDate[=][date]" styleId="clearDate" maxlength="10" size="10" value="<c:out value='${today}'/>" style="ime-mode:disabled" onkeypress="return numberPass()" class="text"/><input type="button" style="width:25" class="button" value="..." onclick="return showCalendar('clearDate','%Y-%m-%d', '24', true);">
										<span class="req">*</span>
									</td>	
																
								</tr>
								<tr>
									<td colspan="2" align="center">
										<input type="button" class="button" onclick="doPreview();" value="<fmt:message key="button.preview"/>">
						                <input type="button" class="button" onclick="doExport();" value="<fmt:message key="button.export"/>">
						                <input type="reset" class="button" value="<fmt:message key="button.reset"/>">
									</td>
								</tr>
							</table>
						</fieldset>
						<input type="hidden" name="exportTo">
					</html:form>
				</td>
			</tr>
		</table>

		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
