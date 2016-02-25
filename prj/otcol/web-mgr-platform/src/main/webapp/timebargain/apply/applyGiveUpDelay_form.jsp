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
	if (confirm("��ȷ��Ҫ�ύ��")) {
		if (applyForm.commodityID.value == "") {
			alert("��Ʒ���벻��Ϊ�գ�");
			applyForm.commodityID.focus();
			return false;
		}
		
		if (applyForm.billID.value == "") {
			alert("�ֵ��Ų���Ϊ�գ�");
			applyForm.billID.focus();
			return false;
		}
		if (applyForm.quantity.value == "") {
			alert("�ֵ���������Ϊ�գ�");
			applyForm.quantity.focus();
			return false;
		}
		if (applyForm.firmID_S.value == "") {
			alert("���������̴��벻��Ϊ�գ�");
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
								  �������ڽ���
								</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0"
								class="common">
								<tr>
									<td align="right">
											�ֵ��ţ�
									</td>
									<td>
										<html:text property="billID" maxlength="15"  style="ime-mode:disabled" />
										<span class="req">*</span>
									</td>
									
									<td align="right">
											��Ʒ���룺
									</td>
									<td>
										<html:text property="commodityID" maxlength="10"  style="ime-mode:disabled" />
										<span class="req">*</span>
									</td>
								</tr>	
								
								<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
									
								<tr>
									<td align="right">
											�ֵ�������
									</td>
									<td>
										<html:text property="quantity" maxlength="15"  style="ime-mode:disabled" />
										<span class="req">*</span>
									</td>
									<td align="right">
											���������̴��룺
									</td>
									<td>
										<html:text property="firmID_S" maxlength="15" style="ime-mode:disabled" />
										<span class="req">&nbsp;</span>
									</td>
									
									
								</tr>	
									
								<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>							
							
								<tr>
									<td align="right">
											��ע��
									</td>
									<td colspan="3">
										<html:textarea property="remark1" rows="3" cols="75"  style="width:470" styleClass="text" />
										
									</td>
								</tr>																																				
								<tr>
									<td colspan="4" align="center">
										<html:button property="save" styleClass="button"
											onclick="javascript:return save_onclick();">
											�ύ
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
