<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/Date.js"/>"></script>
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<c:import url="/timebargain/report/customerID_change.jsp"/>
		<c:import url="/timebargain/common/date.jsp"/>
		<script language="VBScript" src="<c:url value="/timebargain/scripts/vbfunction.vbs"/>"></script>
		<title>
		</title>
		<script type="text/javascript"> 
function window_onload()
{
    highlightFormElements();
    //reportForm.groupID.focus();
    var a=reportForm.beginDate.value;
    var b=goMon(a,-1);
    reportForm.beginDate.value=b;
    query_onclick();
    reportForm.beginDate.value=a;
}
//query_onclick
function query_onclick()
{
  
  if(reportForm.beginDate.value=="")
  {
    alert("<fmt:message key="statQuery.beginDate"/><fmt:message key="prompt.required"/>");
    reportForm.beginDate.focus();
    return false;
  }
  if(reportForm.endDate.value=="")
  {
    alert("<fmt:message key="statQuery.endDate"/><fmt:message key="prompt.required"/>");
    reportForm.endDate.focus();
    return false;
  }
  if(!isDateFormat(reportForm.beginDate.value))
  {
    alert("<fmt:message key="statQuery.beginDate"/><fmt:message key="formatError"/>");
    reportForm.beginDate.focus();
    return false;
  }
  if(!isDateFormat(reportForm.endDate.value))
  {
    alert("<fmt:message key="statQuery.endDate"/><fmt:message key="formatError"/>");
    reportForm.endDate.focus();
    return false;
  }

  wait.style.visibility = "visible";
  reportForm.submit();
  reportForm.query.disabled = true;  
}
//dy_onclick
function dy_onclick()
{
   reportForm.action = "<c:url value="/timebargain/report/report.do?funcflg=printFeeMonth1"/>";
  reportForm.target = "_blank";
  reportForm.exportTo.value = "pdf";
  reportForm.submit();   
  reportForm.action = "<c:url value="/timebargain/report/report.do?funcflg=listFeeMonth2"/>";
  reportForm.target = "ListFrame";
}

</script>
	</head>

	<body leftmargin="2" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<c:import url="/timebargain/common/waitbar.jsp" charEncoding="GBK"/>
		<table border="0" height="100%" width="100%" cellpadding="0" cellspacing="0" class="common" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/report/report.do?method=listAccount"
						method="POST" styleClass="form" target="ListFrame">
						<fieldset class="pickList" >
							<legend class="common">
								<b><fmt:message key="query.condition.title" />
								</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0"
								class="common">
								<tr>
								    <td align="right">
								        
								    </td>
									<td align="right">
										<fmt:message key="statQuery.beginDate" />£º
									</td>
									<td>
										<input type="text" id="beginDate" name="_a.ClearDate[>=][date]" ondblclick="if(!this.readOnly){setRq(this);}" value="<c:out value='${today}'/>" title="<fmt:message key="title.dblclick.seldate" />"  styleId="beginDate" maxlength="10" style="ime-mode:disabled;width:111" onkeypress="return numberPass()" />
									</td>
									<td align="right">
										&nbsp;&nbsp;<fmt:message key="statQuery.endDate" />£º
									</td>
									<td>
										<input type="text" id="endDate" name="_a.ClearDate[<=][date]" ondblclick="if(!this.readOnly){setRq(this);}" value="<c:out value='${today}'/>" title="<fmt:message key="title.dblclick.seldate" />"  styleId="endDate" maxlength="10" style="ime-mode:disabled;width:111" onkeypress="return numberPass()" />
									</td>	
									<td align="right">
										&nbsp;&nbsp;<html:button property="query" style="width:60" styleClass="button"
											onclick="javascript:return query_onclick();">
											<fmt:message key="button.query" />
										</html:button>
										<!--<html:button property="dy" style="width:60" styleClass="button"
											onclick="javascript:return dy_onclick();" disabled="true">
											<fmt:message key="button.print" />
										</html:button>-->
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
