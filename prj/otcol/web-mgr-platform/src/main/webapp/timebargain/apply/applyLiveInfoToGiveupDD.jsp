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
    setReadOnly(applyForm.billID);
    setReadOnly(applyForm.commodityID);
    setReadOnly(applyForm.customerID_S);
    setReadOnly(applyForm.quantity);
    applyForm.type[0].checked = true;
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
		
		if (applyForm.type[0].checked == false && applyForm.type[1].checked == false) {
			alert("��ѡ����Ϊ�գ�");
			
			return false;
		}
		var radios = applyForm.type;
		if (radios[0].checked == true) {
			applyForm.applyType.value = "1";
		}else if (radios[1].checked == true) {
			applyForm.applyType.value = "2";
		}
		applyForm.submit();
		applyForm.save.disabled = true;
	}
	
  
}

function cancle_onclick(){
	document.location.href = "<c:url value="/timebargain/menu/liveInfo.do"/>";
}

</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="300" width="640" align="center">
			<tr>
				<td>
				
				
					
					
					
					
					<html:form action="/timebargain/apply/apply.do?funcflg=applySaveLiveInfo"
						method="POST" styleClass="form">
						<fieldset class="pickList" >
							<legend class="common">
								<b>
								  �����ֶ�
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
											�����������룺
									</td>
									<td>
										<html:text property="customerID_S" maxlength="15" style="ime-mode:disabled" />
										<span class="req">&nbsp;</span>
									</td>
									
									
								</tr>	
									
								<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>							
							
								<tr>
								<td></td>
									<td align="right">
											�������ͣ�
									</td>
									<td>
										<html:radio property="type" value="1" style="border:0px;"></html:radio>��������
										<html:radio property="type" value="2" style="border:0px;"></html:radio>ǿ�Ƴ���
										
										<span class="req">*</span>
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
										<html:button property="cancle" styleClass="button"
											onclick="javascript:return cancle_onclick();">
											����
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
		
		<script type="text/javascript"
			src="<html:rewrite page="/timebargain/common/validator.jsp"/>"></script>
		<%@ include file="/timebargain/common/messages.jsp"%>
<script type="text/javascript">
	<%
  if(request.getAttribute("applyNewRelForm2") != null)
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
