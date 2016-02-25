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
	document.all.firmID.value =""
    document.all.commodityID.value =""
    highlightFormElements();
   query_onclick();
}
//query_onclick
function query_onclick()
{
  wait.style.visibility = "visible";
  applyGageForm.submit();
  applyGageForm.query.disabled = true;  
}
//重置数据
function resetData()
{
	document.getElementsByName("firmID").value="";
	document.getElementsByName("commodityID").value="";
	
}


</script>
	</head>
	<body leftmargin="13" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<c:import url="/timebargain/common/waitbar.jsp" charEncoding="GBK"/>
		<table border="0" height="100%" width="100%" cellpadding="0" cellspacing="0" class="common" align="center">
			<tr>
				<td>
					<html:form  action="/timebargain/gageAudit/gageAudit.do?funcflg=listAuditGage" method="POST" styleClass="form" target="ListFrame1"  >
						<html:hidden property="status" value="1"/>
						<fieldset class="pickList" >
							<legend class="common">
								<b>待审核</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0"
								class="common">

								<tr>
									<td align="left">交易商代码：</td>
									<td>
										<html:text property="firmID"  styleClass="text" maxlength="16" size="12"onkeypress="onlyNumberAndCharInput()"></html:text> 
										
									</td>
									<td>&nbsp;</td>
						            <td align="left">商品代码：</td>
						            <td >
						             <html:text  property="commodityID" styleClass="text" maxlength="16" size="12"onkeypress="onlyNumberAndCharInput()"></html:text>
						            	
									</td>  
						              	
						            <td align="left">
										&nbsp;&nbsp;<html:button property="query" style="width:60" styleClass="button"
											onclick="javascript:return query_onclick();">
											查询
										</html:button>
										<input type="reset"  style="width:60" class="button" value="重置" onclick="resetData()" />
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
