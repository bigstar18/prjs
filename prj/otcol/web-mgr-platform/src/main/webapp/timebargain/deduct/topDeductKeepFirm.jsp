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
    
}
//query_onclick
function deduct_onchange(){
	if (deductForm.deductDate.value == "") {
		alert("强减日期不能为空！");
		deductForm.deductDate.focus();
		return false;
	}
	deductForm.submit();
}


</script>
	</head>

	<body leftmargin="2" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<c:import url="/common/waitbar.jsp" charEncoding="GBK"/>
		<table border="0" height="90" width="710" cellpadding="0" cellspacing="0" class="common" align="left">
			<tr>
				<td>
					<html:form action="/timebargain/deduct/deduct.do?funcflg=deductKeepFirmListQuery"
						method="POST" styleClass="form" target="ListFrame">
						<fieldset class="pickList" >
							<legend class="common">
								<b>查询强减保留交易商
								</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0"
								class="common">

								<tr>
									<td align="left">
											强减日期：
									</td>
									<td>
										<html:text property="deductDate" ondblclick="if(!this.readOnly){setRq(this);}" title="双击选择日期" styleClass="text" maxlength="16" size="12"></html:text>
									</td>
									<td align="left">
										&nbsp;&nbsp;<html:button property="query" style="width:60" styleClass="button"
											onclick="javascript:return deduct_onchange();">
											<fmt:message key="button.query" />
										</html:button>
										
									</td> 		                                                           							
								</tr>
								
								
							</table>
						</fieldset>
					</html:form>
				</td>
			</tr>
		</table>

		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
