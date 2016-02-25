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
    applyForm.applyType.value = "5";
    applyForm.submit();
}
//query_onclick
function applyType_onchange(){
	if (applyForm.applyType.value == "") {
		alert("申请种类不能为空！");
		applyForm.applyType.focus();
		return false;
	}else {
		applyForm.submit();
	}
}


</script>
	</head>

	<body leftmargin="2" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<c:import url="/timebargain/common/waitbar.jsp" charEncoding="GBK"/>
		<table border="0" height="80%" width="100%" cellpadding="0" cellspacing="0" class="common" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/apply/apply.do?funcflg=applyNewForm"
						method="POST" styleClass="form" target="ListFrame1">
						<fieldset class="pickList" >
							<legend class="common">
								<b>选择类型
								</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0"
								class="common">

								<tr>
									<td align="left">
											申请类型：
									</td>
									<td>
										<html:select property="applyType" style="width:150" onchange="return applyType_onchange()">
											<html:option value=""></html:option>
											<html:option value="5">抵顶</html:option>
				    						<html:option value="1">撤销抵顶</html:option>
											<html:option value="3">抵顶转提前交收</html:option>
											<html:option value="6">提前交收</html:option>
											<html:option value="8">延期交收</html:option>
											<html:option value="9">撤销延期交收</html:option>
			   							</html:select> 
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
