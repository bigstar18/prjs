<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
		<title>
		</title>
		<script type="text/javascript"> 
function window_onload()
{
    highlightFormElements();
    monitorSetForm.refreshTime.focus();
}

function save_onclick()
{
	if (confirm("��ȷ��Ҫ�ύ��"))
    {	
    	if (monitorSetForm.refreshTime.value == "") 
    	{
    		alert("ˢ��ʱ�䲻��Ϊ�գ�");
    		monitorSetForm.refreshTime.focus();
    		return false;
    	}
    	else if(monitorSetForm.refreshTime.value<3)
    	{
    		alert("ˢ��ʱ�䲻��С��3��");
    		monitorSetForm.refreshTime.focus();
    		return false;
    	}
    	if (monitorSetForm.pageSize.value == "") 
    	{
    		alert("ÿҳ��ʾ�ļ�¼������Ϊ�գ�");
    		monitorSetForm.pageSize.focus();
    		return false;
    	}
	    monitorSetForm.submit();
	    monitorSetForm.save.disabled = true;
	}
}
</script>
	</head>
	<body onLoad="return window_onload()" onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="400" width="600" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/tradeMonitor/monitorSet.do?funcflg=save" method="POST" styleClass="form">
						<fieldset class="pickList">
							<legend class="common">
								<b>
									���ռ�ز���
								</b>
							</legend>

							<table width="100%" border="0" align="center" class="common" cellpadding="0" cellspacing="2">
								<!-- ������Ϣ -->
								<tr class="common">
									<td colspan="4">
										<span id="paraminfo">
											<table cellSpacing="0" cellPadding="0" width="300" border="0" align="center" class="common">
												<tr>
													<td align="right" width="150">
														���ˢ��ʱ�䣺
													</td>
													<td align="left" width="150">
														<html:text property="refreshTime" maxlength="5" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" size="10" />
													</td>
												</tr>
												<tr>
													<td align="right" width="150">
														���ռ��ÿҳ��ʾ��¼����
													</td>
													<td align="left" width="150">
														<html:text property="pageSize" maxlength="5" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" size="10" />
													</td>
												</tr>
											</table>
										</span>
									</td>
								</tr>

								<tr>
									<td colspan="4" height="3">
									</td>
								</tr>
								<tr>
									<td colspan="4" align="center">
										<html:button property="save" styleClass="button" onclick="javascript:return save_onclick();">
											�ύ
										</html:button>
										<!--	<html:button property="cancel" styleClass="button"
											onclick="javascript:return cancel_onclick();">
											<fmt:message key="button.return" />
										</html:button> -->
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
