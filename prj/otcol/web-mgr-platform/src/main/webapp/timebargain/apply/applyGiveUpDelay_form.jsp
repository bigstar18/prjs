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
    applyForm.crud.value = "<c:out value="${param['applyType']}"/>";
    setReadOnly(applyForm.billID);
    setReadOnly(applyForm.commodityID);
    setReadOnly(applyForm.firmID_S);
    setReadOnly(applyForm.quantity);
}
//save
function save_onclick()
{
	if (confirm("您确定要提交吗？")) {
		if (applyForm.commodityID.value == "") {
			alert("商品代码不能为空！");
			applyForm.commodityID.focus();
			return false;
		}
		
		if (applyForm.billID.value == "") {
			alert("仓单号不能为空！");
			applyForm.billID.focus();
			return false;
		}
		if (applyForm.quantity.value == "") {
			alert("仓单数量不能为空！");
			applyForm.quantity.focus();
			return false;
		}
		if (applyForm.firmID_S.value == "") {
			alert("卖方交易商代码不能为空！");
			applyForm.firmID_S.focus();
			return false;
		}
		
		applyForm.submit();
		applyForm.save.disabled = true;
	}
	
  
}



</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="300" width="640" align="center">
			<tr>
				<td>
				
				
					
					
					
					
					<html:form action="/timebargain/apply/apply.do?funcflg=applySaveGiveUp"
						method="POST" styleClass="form" target="HiddFrame">
						<fieldset class="pickList" >
							<legend class="common">
								<b>
								  撤销延期交收
								</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0"
								class="common">
								<tr>
									<td align="right">
											仓单号：
									</td>
									<td>
										<html:text property="billID" maxlength="15"  style="ime-mode:disabled" />
										<span class="req">*</span>
									</td>
									
									<td align="right">
											商品代码：
									</td>
									<td>
										<html:text property="commodityID" maxlength="10"  style="ime-mode:disabled" />
										<span class="req">*</span>
									</td>
								</tr>	
								
								<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
									
								<tr>
									<td align="right">
											仓单数量：
									</td>
									<td>
										<html:text property="quantity" maxlength="15"  style="ime-mode:disabled" />
										<span class="req">*</span>
									</td>
									<td align="right">
											卖方交易商代码：
									</td>
									<td>
										<html:text property="firmID_S" maxlength="15" style="ime-mode:disabled" />
										<span class="req">&nbsp;</span>
									</td>
									
									
								</tr>	
									
								<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>							
							
								<tr>
									<td align="right">
											备注：
									</td>
									<td colspan="3">
										<html:textarea property="remark1" rows="3" cols="75"  style="width:470" styleClass="text" />
										
									</td>
								</tr>																																				
								<tr>
									<td colspan="4" align="center">
										<html:button property="save" styleClass="button"
											onclick="javascript:return save_onclick();">
											提交
										</html:button>
									</td>
								</tr>
							</table>
						</fieldset>
						<html:hidden property="crud"/>
						<html:hidden property="applyType"/>
					</html:form>
					
					
					
					
				</td>
				
			</tr>
		</table>
		
		<%@ include file="/timebargain/common/messages.jsp"%>
<script type="text/javascript">
	<%
  if(request.getAttribute("applyGiveUpDelayQuery") != null)
  {
%>
    parent.TopFrame2.applyForm.query.disabled = false;
    parent.TopFrame2.wait.style.visibility = "hidden";
<%
  }
%>

</script>

	</body>
</html>
