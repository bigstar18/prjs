<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<title>
		</title>
		<script type="text/javascript"> 
function window_onload()
{
    highlightFormElements();
    
}
//save
function save_onclick()
{
   if(trim(customerForm.firmID.value) == "")
   {
       alert("交易商ID不能为空！");
       customerForm.firmID.focus();
 	   return false;
   }
   if (trim(customerForm.virtualFunds.value) == "") {
   	   alert("虚拟资金不能为空！");
   	   customerForm.virtualFunds.focus();
 	   return false;
   }
   customerForm.submit();	
   customerForm.save.disabled = true;
}
//cancel

</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="80%" width="50%" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/baseinfo/virtualFund.do?funcflg=saveNewApp"
						method="POST" styleClass="form" target="ListFrame">
						<fieldset class="pickList" >
							<legend class="common">
								<b>填写新申请
								</b>
							</legend>
							<table border="0" align="center" cellpadding="5" cellspacing="5"
								class="common">
								<tr>
									<td align="right">
											交易商ID：
									</td>
									<td>
										<html:text property="firmID"></html:text>
										<span class="req">*</span>
									</td>
								</tr>
								<tr>
									<td align="right">
											虚拟资金：
									</td>
									<td>
										<html:text property="virtualFunds"></html:text>
										<span class="req">*</span>
									</td>
								</tr>									
								<tr>
									<td align="right">
											备注：
									</td>
									<td>
										<html:textarea property="remark1" cols="19"></html:textarea>
										
									</td>
								</tr>
								<tr>
									<td colspan="2" align="center">
										<html:button property="save" styleClass="button"
											onclick="javascript:return save_onclick();">
											提交
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
