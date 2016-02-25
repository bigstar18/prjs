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
    tradePropsForm.maxHoldQty.focus();
}
//save
function save_onclick()
{
	if (confirm("��ȷ��Ҫ�ύ��")) {
		if(validateTradePropsForm(tradePropsForm))
  		{
  		tradePropsForm.crud.value='n';
    		tradePropsForm.submit();
    		tradePropsForm.save.disabled = true;
  		}
	}
  
}
function save_onclick1()
{
	if (confirm("��ȷ��Ҫ�ύ��")) {
		if(validateTradePropsForm(tradePropsForm))
  		{
  			tradePropsForm.crud.value='s';
    		tradePropsForm.submit();
    		tradePropsForm.save.disabled = true;
  		}
	}
  
}

function set_save_disabled(bVal)
{
  tradePropsForm.save.disabled = bVal;
  //clearForm(tradePropsForm);
  tradePropsForm.maxHoldQty.focus();
}

</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="80%" width="50%" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/baseinfo/tradeProps.do?funcflg=saveDefProps&retLocation=parent.ListFrame.set_save_disabled(false)"
						method="POST" styleClass="form" target="HiddFrame">
						<fieldset class="pickList" >
							<legend class="common">
								<b>
								  ���ý��׿ͻ�ȱʡ��������
								</b>
							</legend>
							<table border="0" align="center" cellpadding="5" cellspacing="5"
								class="common">
								<tr>
									<td align="right">
											���ֲ���
									</td>
									<td>
										<html:text property="maxHoldQty" maxlength="10" onkeypress="return onlyNumberInput()" style="ime-mode:disabled" styleClass="Number"/>
										<span class="req">*</span>
									</td>
									<td></td>
								</tr>
								<tr>
									<td align="right">
											��ͽ��㱣֤��
									</td>
									<td>
										<html:text property="minClearDeposit" maxlength="15" onkeypress="return onlyNumberInput()" style="ime-mode:disabled" styleClass="Number"/>
										<span class="req">*</span>
									</td>
									<td></td>
								</tr>								
								<tr>
									<td align="right">
											���͸֧���
									</td>
									<td>
										<html:text property="maxOverdraft" maxlength="15" onkeypress="return onlyNumberInput()" style="ime-mode:disabled" styleClass="Number"/>
										<span class="req">*</span>
									</td>
									<td></td>
								</tr>																																							
								<tr>
									<td colspan="3" align="center">
										<html:button property="save" styleClass="button"
											onclick="javascript:return save_onclick();">
											�ύ
										</html:button>
										<html:button property="save" styleClass="button"
											onclick="javascript:return save_onclick1();">
											�ύ��Ч
										</html:button>
										<html:reset  property="reset" onclick="javascript:tradePropsForm.maxHoldQty.focus();" styleClass="button">����</html:reset>
									</td>
								</tr>
							</table>
						</fieldset>
						<html:hidden property="crud"/>
					</html:form>
				</td>
			</tr>
		</table>
		<html:javascript formName="tradePropsForm" cdata="false"
			dynamicJavascript="true" staticJavascript="false" />
		<script type="text/javascript"
			src="<html:rewrite page="/timebargain/common/validator.jsp"/>"></script>
		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
