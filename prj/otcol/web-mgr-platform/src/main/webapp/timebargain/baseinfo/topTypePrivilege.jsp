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
  customerForm.submit();
  //customerForm.query.disabled = true;  
}


</script>
	</head>

	<body leftmargin="14" topmargin="0" onLoad="return window_onload()" onkeypress="keyEnter(event.keyCode);">
		<c:import url="/common/waitbar.jsp" charEncoding="GBK"/>
		<table border="0" height="100%" width="100%" cellpadding="0" cellspacing="0" class="common" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/baseinfo/firm.do?funcflg=typePrivilegeList" method="POST" styleClass="form" target="ListFrame">
						<fieldset class="pickList" >
							<legend class="common">
								<b>权限查询
								</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0"
								class="common">

								<tr>
									<td align="left">
											品种/商品代码：
									</td>
									<td>
										<html:text property="kindID" styleClass="text" maxlength="16" size="12" onkeypress="onlyNumberAndCharInput()"></html:text>
										
									</td>
									<td>&nbsp;</td>
						            <td align="left">交易码：</td>
						            <td >
						            	<html:text property="typeID" styleClass="text" maxlength="16" size="12"  onkeypress="onlyNumberAndCharInput()"></html:text>
						            </td>  
						              	
						            <td align="left">
										&nbsp;&nbsp;<html:button property="query" style="width:60" styleClass="button"
											onclick="javascript:return query_onclick();">
											查询
										</html:button>
										
									</td> 
									<td align="left">
										&nbsp;&nbsp;<html:reset property="reset" style="width:60" styleClass="button">
											重置
										</html:reset>
										
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
