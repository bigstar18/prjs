<%@ include file="/timebargaincommon/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>" />
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
    //reportForm.mkName.value = "<c:out value="${param['mkName']}"/>";
    //reportForm.groupID.focus();
    query_onclick();
}
//query_onclick
function query_onclick()
{
  if(reportForm.clearDate.value=="")
  {
    alert("<fmt:message key="query.time"/><fmt:message key="prompt.required"/>");
    reportForm.clearDate.focus();
    return false;
  }
  if(!isDateFormat(reportForm.clearDate.value))
  {
    alert("<fmt:message key="query.time"/><fmt:message key="formatError"/>");
    reportForm.clearDate.focus();
    return false;
  }

  wait.style.visibility = "visible";
  reportForm.submit();
  reportForm.query.disabled = true;  
}
//dy_onclick
function dy_onclick()
{
  reportForm.action = "<c:url value="/timebargain/report/customerFund.jsp"/>";
  reportForm.target = "_blank";
  reportForm.exportTo.value = "pdf";
  reportForm.submit();   
  reportForm.action = "<c:url value="/timebargain/report/report.do?funcflg=listWeek1Detail"/>";
  reportForm.target = "ListFrame";
}

function doExport()
{
  /*reportForm.exportTo.value = "excel";
  reportForm.submit();   */
  reportForm.action = "../commodityImp";
  reportForm.target = "_blank";
  reportForm.submit();   
  reportForm.action = "<c:url value="/timebargain/report/report.do?funcflg=listWeek1Detail"/>";
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
					<html:form action="/report/report.do?method=listWeek1Detail"
						method="POST" styleClass="form" target="ListFrame">
						<fieldset class="pickList" >
							<legend class="common">
								<b><fmt:message key="query.condition.title" />
								</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0"
								class="common">

								<!-- <tr>
									<td align="right">
											<fmt:message key="statQuery.groupID" />
									</td>
									<td>
										<select id="groupID" name="_a.GroupID[=]" style="width:111">
										  <c:forEach items="${customerGroupSelect}" var="customerGroup">
                                              <option value='<c:out value="${customerGroup.value}"/>'><c:out value="${customerGroup.label}"/></option>
                                          </c:forEach>
                                        </select>
									</td>
									<td align="right">
											<fmt:message key="customerForm.CustomerID" />
									</td>
									<td>
										<input type="text" id="customerID" name="_a.CustomerID[=]" style="width:111" maxlength="16" title="<fmt:message key="title.dblclick.query"/>"
											styleClass="text" onchange="customerID_onchange()" ondblclick="if(!this.readOnly){customerID_dblclick();}"/>
									</td>
			                        <td align="right">
											<fmt:message key="customerForm.CustomerName" />
									</td>
									<td>
										<input type="text" id="customerName" name="_a.CustomerName[like]" title="<fmt:message key="statQuery.like.inputmode"/>" style="width:111" maxlength="32" styleClass="text"/>
									</td>																	
								</tr>-->
								<tr>
								    <td align="right">
										<fmt:message key="query.type" />£º
									</td>
									<td>
										<select name="type">
										<option value="day"><fmt:message key="query.day" /></option>
										<option value="week"><fmt:message key="query.week" /></option>
										<option value="month"><fmt:message key="query.month" /></option>
									</td>
									<td align="right">
										&nbsp;&nbsp;<fmt:message key="query.time" />£º
									</td>
									<td>
										<input type="text" id="clearDate" name="_a.ClearDate[=][date]"  value="<c:out value='${today}'/>" title="<fmt:message key="title.dblclick.seldate" />"  styleId="clearDate" maxlength="10"  onkeypress="return numberPass()" style="ime-mode:disabled;width:111" ondblclick="if(!this.readOnly){setRq(this);}"/>
									</td>
									<td>
										
									</td>
									<td>&nbsp;</td>
									<td align="right">
										<html:button property="query" style="width:60" styleClass="button"
											onclick="javascript:return query_onclick();">
											<fmt:message key="button.query" />
										</html:button>
										<html:button property="dy" style="width:60" styleClass="button"
											onclick="javascript:return dy_onclick();" disabled="true">
											<fmt:message key="button.print" />
										</html:button>
										
									</td>
									<td>
										
									</td>	
																									
								</tr>
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
