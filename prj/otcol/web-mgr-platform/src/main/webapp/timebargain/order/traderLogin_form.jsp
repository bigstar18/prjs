<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<title>
		��Ϊί��Ա��¼
		</title>
		<script type="text/javascript"> 
function window_onload()
{
  highlightFormElements();
  ordersForm.traderID.focus();
}

function ok_onclick()
{
  if(trim(ordersForm.traderID.value) == "")
  {
    alert("��Ϊί��Ա���벻��Ϊ�գ�");
    ordersForm.traderID.focus();
    return false;
  }
  if(trim(ordersForm.password.value)=="")
  {
    alert("��Ϊί��Ա�����Ϊ�գ�");
    ordersForm.password.focus();
    return false;
  }  
  ordersForm.submit();
  ordersForm.ok.disabled = true;
}
//cancel
function cancel_onclick()
{
   window.close();
}
// after save success,close
function closewindow()
{
    window.dialogArguments.chkOk();
    window.close();
}
</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="100%" width="100%" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/order/orders.do?funcflg=login" target="HiddFrame" method="post">
						<fieldset class="pickList" >
							<legend class="common">
								<b>��Ϊί��Ա��¼
								</b>
							</legend>
							<table border="0" align="center" cellpadding="5" cellspacing="5"
								class="common">
								<tr>
									<td align="right">
											��Ϊί��Ա���룺
									</td>
									<td>
										<input type="text" name="traderID" maxlength="10"
											class="text" />
										<span class="req">*</span>
									</td>
								</tr>
								<tr>
									<td align="right">
											��Ϊί��Ա���
									</td>
									<td>
										<input type="password" name="password" maxlength="64" style="width:150;height:20" class="text"/>
										<span class="req">*</span>
									</td>
								</tr>
								<tr>
									<td colspan="2" align="center">
										<html:button property="ok" styleClass="button"
											onclick="javascript:return ok_onclick();">
											ȷ��
										</html:button>
										<html:button property="cancel" styleClass="button"
											onclick="javascript:return cancel_onclick();">
											�˳�
										</html:button>
									</td>
								</tr>	
							</table>
						</fieldset>
					</html:form>
				</td>
			</tr>
		</table>

	</body>
</html>
