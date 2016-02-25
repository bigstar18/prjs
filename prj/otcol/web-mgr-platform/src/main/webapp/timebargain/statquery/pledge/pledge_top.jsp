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
    query_onclick();
}
//query_onclick
function query_onclick()
{
  wait.style.visibility = "visible";
  statQueryForm.submit();
  statQueryForm.query.disabled = true;  
}
//requery_onclick

</script>
	</head>

	<body leftmargin="6" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<c:import url="/timebargain/common/waitbar.jsp" charEncoding="GBK"/>
		<table border="0" height="100%" width="100%" cellpadding="0" cellspacing="0" class="common" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/statquery/statQuery.do?funcflg=listPledge"
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
											仓单号：
									</td>
									<td>
										<html:text property="billID" maxlength="10" style="ime-mode:disabled" styleClass="text" size="10"onkeypress="onlyNumberAndCharInput()" />
									</td>
			                       <td>&nbsp;</td><td>&nbsp;</td>
			                       <td align="right">
											品种名称：
									</td>
									<td>
										<html:text property="commodityName" maxlength="10" styleClass="text" size="10" onkeypress="onlyNumberAndCharInput()"/>
									</td>
									
									<td>&nbsp;</td><td>&nbsp;</td>
									<td align="right">
										<html:button property="query" style="width:60" styleClass="button"
											onclick="javascript:return query_onclick();">
											查询
										</html:button>
										<input type="reset"  style="width:60" class="button" value="重置" />
									</td>
									<td>
										
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
