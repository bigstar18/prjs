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
    cmdtySortForm.sortName.focus();
}
//save
function save_onclick()
{ 
	  if(trim(cmdtySortForm.sortName.value) == "")
	  {
	    alert("品种分类名称不能为空！");
	    cmdtySortForm.sortName.focus();
	    return false;
	  }   
	  cmdtySortForm.submit();
	  cmdtySortForm.save.disabled = true;
}
//cancel
function cancel_onclick()
{
   document.location.href = "<c:url value="/timebargain/baseinfo/cmdtySort.do"/>";
}

</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="300" width="300" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/baseinfo/cmdtySort.do?funcflg=save"
						method="POST" styleClass="form">
						<fieldset>
							<legend class="common">
								<b>设置商品分类信息
								</b>
							</legend>
							<table border="0" align="center" cellpadding="5" cellspacing="5"
								class="common">
								<tr>
									<td align="right">
											品种分类名称：
									</td>
									<td>
										<html:text property="sortName" maxlength="15"
											styleClass="text" />
										<span class="req">*</span>
									</td>
								</tr>
							
								 <html:hidden property="maxHoldQty" value="99999999"/>																																																										
								<tr>
									<td colspan="2" align="center">
										<html:button property="save" styleClass="button"
											onclick="javascript:return save_onclick();">
											提交
										</html:button>
										<html:button property="cancel" styleClass="button"
											onclick="javascript:return cancel_onclick();">
											返回
										</html:button>
									</td>
								</tr>
							</table>
						</fieldset>
						<html:hidden property="crud" />
						<html:hidden property="sortID"/>
					</html:form>
				</td>
			</tr>
		</table>

		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
