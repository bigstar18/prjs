<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
		<title>
			设置恢复交收时间
		</title>
		<script type="text/javascript"> 
function window_onload()
{
    highlightFormElements();
    
}
//save
function save_onclick()
{
   if(trim(delayForm.recoverTime.value) == "")
   {
       alert("恢复时间不能为空！");
       delayForm.recoverTime.focus();
 	   return false;
   }
   
	var str = trim(delayForm.recoverTime.value);
	var a = str.match(/^(\d{1,2})(:)(\d{1,2})(:)(\d{1,2})$/);

	if (a == null) {
		alert('输入的参数不是时间格式'); 
		delayForm.recoverTime.focus();
		return false;
	}
	if (a[1]>24 || a[3]>60 || a[5]>60){
		
		alert("输入的参数不是时间格式");
		delayForm.recoverTime.focus();
		return false
	}
   //window.returnValue = delayForm.recoverTime.value;
   delayForm.submit();
   delayForm.save.disabled = true;
   
   //window.close();
}
//时间判断
function isTime(){
	var str = trim(delayForm.recoverTime.value);
	var a = str.match(/^(\d{1,2})(:)?(\d{1,2})\2(\d{1,2})$/);
	if (a == null) {
		alert('输入的参数不是时间格式'); 
		return false;
	}
	if (a[1]>24 || a[3]>60 || a[4]>60){
		alert("时间格式不对");
		return false
	}
	return true;
}

</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="80%" width="80%" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/delay/delay.do?funcflg=operateRecoverTime"
						method="POST" styleClass="form" target="HiddFrame2">
						<fieldset class="pickList" >
							<legend class="common">
								<b>设定恢复时间
								</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0"
								class="common">
								<tr>
									
									<td>
										恢复时间：<html:text property="recoverTime"  ></html:text>
										<span class="req">*</span>
									</td>
									
								</tr>
								<tr>
									<td>
										<span class="req">时间填写格式为：24hours HH:MM:SS</span>
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
						<html:hidden property="type" value="99"/>
					</html:form>
				</td>
			</tr>
		</table>

		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
