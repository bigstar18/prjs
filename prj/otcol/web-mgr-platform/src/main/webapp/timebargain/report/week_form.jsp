<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<%@ include file="/timebargain/widgets/calendar/calendar.jsp" %>
		<%@ include file="/timebargain/report/groupID_change.jsp" %>
		<script language="VBScript" src="<c:url value="/timebargain/scripts/vbfunction.vbs"/>"></script>	
		<title>
		</title>
		<script type="text/javascript"> 
function window_onload()
{
    highlightFormElements();
    reportForm.mkName.value = "<c:out value="${param['mkName']}"/>";  
    if(reportForm.mkName.value == "day")
    {
      setReadOnly(reportForm.clearDate);
      reportForm.selDate.disabled = true;
    }
    reportForm.groupID.focus();
}
//
function required()
{
/*
  if(trim(reportForm.groupID.value) == "")
  {
    alert("<fmt:message key="report.groupID"/><fmt:message key="prompt.required"/>");
    reportForm.groupID.focus();
    return false;
  }
*/  
  if(trim(reportForm.beginCustomerID.value) == "")
  {
    alert("<fmt:message key="report.startCustomerID"/><fmt:message key="prompt.required"/>");
    reportForm.beginCustomerID.focus();
    return false;
  }
  if(trim(reportForm.endCustomerID.value) == "")
  {
    alert("<fmt:message key="report.endCustomerID"/><fmt:message key="prompt.required"/>");
    reportForm.endCustomerID.focus();
    return false;
  }
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
  //alert(reportForm.mkName.value);
  //reportForm.exportTo.value = "pdf";
  //reportForm.submit();  
  reportForm.action = "<c:url value="/timebargain/report/customerFund.jsp"/>";
  reportForm.submit();   
}
//export
function doExport()
{
  if(!required())
  {
    return false;
  } 
  /*reportForm.exportTo.value = "excel";
  reportForm.submit();   */
  reportForm.action = "<c:url value="/timebargain/report/customerFundImp.jsp"/>";
  reportForm.submit();   
}
//qryFund
function qryFund()
{
  if(!required())
  {
    return false;
  } 
  reportForm.action = "<c:url value="/timebargain/report/customerFundReport.jsp"/>";
  reportForm.submit();   
  reportForm.action = "<c:url value="/timebargain/report/report.do?funcflg=printWeek"/>";
}
</script>
	</head>

	<body leftmargin="0" topmargin="0" onload="return window_onload()" onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="80%" width="50%" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/report/report.do?funcflg=printWeek"
						method="POST" styleClass="form" target="_blank">
						<fieldset class="pickList" >
							<legend class="common">
								<b><fmt:message key="query.condition.title"/>
								</b>
							</legend>
							<table border="0" align="center" cellpadding="5" cellspacing="5"
								class="common">
						<%
						if(request.getParameter("mkName").equals("day") || request.getParameter("mkName").equals("hisDay"))
						{
						%>
								<tr>
									<td align="right">
											<fmt:message key="report.groupID" />
									</td>
									<td>
										<select id="groupID" name="_c.GroupID[=]" onchange="groupID_onchange()" style="width:150">
										  <c:forEach items="${customerGroupSelect}" var="customerGroup">
                                              <option value='<c:out value="${customerGroup.value}"/>'><c:out value="${customerGroup.label}"/></option>
                                          </c:forEach>
                                        </select>
									</td>
								</tr>
								<tr>
									<td align="right">
											<fmt:message key="report.startCustomerID" />
									</td>
									<td>
										<input type="text" id="beginCustomerID" name="_a.CustomerID[>=]" value="<c:out value='${minCustomerID}'/>"  maxlength="16" class="text">
										<span class="req">*</span>
									</td>
								</tr>
								<tr>
									<td align="right">
											<fmt:message key="report.endCustomerID" />
									</td>
									<td>
										<input type="text" id="endCustomerID" name="_a.CustomerID[<=]" value="<c:out value='${maxCustomerID}'/>" maxlength="16" class="text">
										<span class="req">*</span>
									</td>
								</tr>
								<tr>
									<td align="right">
										<fmt:message key="query.time" />
									</td>
									<td>
									    <input type="text" id="clearDate" name="_a.ClearDate[=][date]" styleId="clearDate" maxlength="10" size="10" value="<c:out value='${today}'/>" style="ime-mode:disabled" onkeypress="return numberPass()" class="text"/><input type="button" id="selDate" style="width:25" class="button" value="..." onclick="return showCalendar('clearDate','%Y-%m-%d', '24', true);">
										<span class="req">*</span>
									</td>
							     </tr>	
								<tr>
									<td colspan="2" align="center">
										<input type="button" class="button" onclick="doPreview();" value="<fmt:message key="button.preview"/>">
						                <input type="button" class="button" onclick="doExport();" value="<fmt:message key="button.export"/>">
						                <input type="reset" class="button" value="<fmt:message key="button.reset"/>">
						                <input type="button" class="button" onclick="qryFund()" value="×Ê½ðÔ¤ÀÀ">
									</td>
								</tr>							     					
						<%
						}
						else
						{						
						%>							
								<tr>
									<td align="right">
											<fmt:message key="report.groupID" />
									</td>
									<td>
										<select id="groupID" name="_GroupID[=]" onchange="groupID_onchange()" style="width:150">
										  <c:forEach items="${customerGroupSelect}" var="customerGroup">
                                              <option value='<c:out value="${customerGroup.value}"/>'><c:out value="${customerGroup.label}"/></option>
                                          </c:forEach>
                                        </select>
									</td>
								</tr>
								<tr>
									<td align="right">
											<fmt:message key="report.startCustomerID" />
									</td>
									<td>
										<input type="text" id="beginCustomerID" name="_a.CustomerID[>=]" value="<c:out value='${minCustomerID}'/>"  maxlength="16" class="text">
										<span class="req">*</span>
									</td>
								</tr>
								<tr>
									<td align="right">
											<fmt:message key="report.endCustomerID" />
									</td>
									<td>
										<input type="text" id="endCustomerID" name="_a.CustomerID[<=]" value="<c:out value='${maxCustomerID}'/>" maxlength="16" class="text">
										<span class="req">*</span>
									</td>
								</tr>
								<tr>
									<td align="right">
										<fmt:message key="query.time" />
									</td>
									<td>
									    <input type="text" id="clearDate" name="_a.ClearDate[=][date]" styleId="clearDate" maxlength="10" size="10" value="<c:out value='${today}'/>" style="ime-mode:disabled" onkeypress="return numberPass()" class="text"/><input type="button" id="selDate" style="width:25" class="button" value="..." onclick="return showCalendar('clearDate','%Y-%m-%d', '24', true);">
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
					<%} %>

							</table>
						</fieldset>
						<input type="hidden" name="exportTo">
						<input type="hidden" name="mkName">
					</html:form>
				</td>
			</tr>
		</table>

		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
