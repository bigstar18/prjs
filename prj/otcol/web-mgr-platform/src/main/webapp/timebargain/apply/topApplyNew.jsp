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
		alert("�������಻��Ϊ�գ�");
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
								<b>ѡ������
								</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0"
								class="common">

								<tr>
									<td align="left">
											�������ͣ�
									</td>
									<td>
										<html:select property="applyType" style="width:150" onchange="return applyType_onchange()">
											<html:option value=""></html:option>
											<html:option value="5">�ֶ�</html:option>
				    						<html:option value="1">�����ֶ�</html:option>
											<html:option value="3">�ֶ�ת��ǰ����</html:option>
											<html:option value="6">��ǰ����</html:option>
											<html:option value="8">���ڽ���</html:option>
											<html:option value="9">�������ڽ���</html:option>
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
