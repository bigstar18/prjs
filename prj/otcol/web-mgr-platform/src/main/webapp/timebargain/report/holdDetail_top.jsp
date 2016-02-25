<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
		<c:import url="/timebargain/report/customerID_change.jsp"/>
		<c:import url="/timebargain/common/date.jsp"/>
		<script language="VBScript" src="<c:url value="/timebargain/scripts/vbfunction.vbs"/>"></script>
		<title>
		</title>
		<script type="text/javascript"> 
function window_onload()
{
    highlightFormElements();
    reportForm.mkName.value = "<c:out value="${param['mkName']}"/>";
   	<c:if test="${param['mkName'] == 'ls'}">
   		setReadWrite(reportForm.clearDate);
   	</c:if>
    query_onclick();
}
//query_onclick
function query_onclick()
{
  if(reportForm.clearDate.value=="")
  {
    alert("日期不能为空！");
    reportForm.clearDate.focus();
    return false;
  }
  if(!isDateFormat(reportForm.clearDate.value))
  {
    alert("日期格式不正确！");
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
  //reportForm.action = "<c:url value="/timebargain/report/report.do?funcflg=printHoldDetailOther"/>";
  //reportForm.target = "_blank";
  //reportForm.exportTo.value = "pdf";
  //reportForm.submit();
  //alert(reportForm.firmID.value);
  //alert(reportForm.clearDate.value);
  openDialogLess("<c:url value="/timebargain/report/report.do?funcflg=printHoldDetailOther&firmID="/>" + reportForm.firmID.value + "&clearDate=" + reportForm.clearDate.value,"_blank",800,700);
  //p("<c:url value="/timebargain/report/report.do?funcflg=printHoldDetailOther&firmID="/>" + reportForm.firmID.value + "&clearDate=" + reportForm.clearDate.value,600,700);
  reportForm.action = "<c:url value="/timebargain/report/report.do?funcflg=listHoldDetail"/>";
  reportForm.target = "ListFrame";
  
}

function openDialogLess(url, args, width, height) {
	if (!width) width = 600;
	if (!height) height = 400;
    window.showModelessDialog(url, args, "dialogWidth=" + width + "px; dialogHeight=" + height + "px; dialogTop:150px;center:yes;help:yes;resizable:yes;status:yes");
}

</script>
	</head>

	<body leftmargin="6" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<c:import url="/timebargain/common/waitbar.jsp" charEncoding="GBK"/>
		<table border="0" height="100%" width="100%" cellpadding="0" cellspacing="0" class="common" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/report/report.do?funcflg=listHoldDetail"
						method="POST" styleClass="form" target="ListFrame">
						<fieldset class="pickList" >
							<legend class="common">
								<b>查询条件
								</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0"
								class="common">

								<tr>
									<td align="right">
											&nbsp;&nbsp;交易商ID：
									</td>
									<td>
									<html:text property="firmID" styleClass="text" maxlength="16" size="12"></html:text>
									
										</td>
								
									<td align="right">
										日期：
									</td>
									<td>
									<html:text property="clearDate" styleClass="text" maxlength="16" size="12" ondblclick="if(!this.readOnly){setRq(this);}" value="${today}" title="双击选择日期" readonly="true" styleId="clearDate"></html:text>
									<!--  	
									<input type="text" id="clearDate" name="clearDate" ondblclick="if(!this.readOnly){setRq(this);}" value="<c:out value='${today}'/>" title="双击选择日期" readonly="true" styleId="clearDate" maxlength="10" style="ime-mode:disabled;width:111" onkeypress="return numberPass()" class="ReadOnlyString"/>
									-->
									</td>
									<td>
										
									</td>
									<td align="right">
										<html:button property="query" style="width:60" styleClass="button"
											onclick="javascript:return query_onclick();">
											查询
										</html:button>
										<html:button property="dy" style="width:60" styleClass="button"
											onclick="javascript:return dy_onclick();" disabled="true">
											打印
										</html:button>
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
