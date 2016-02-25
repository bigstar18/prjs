<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="java.util.*"%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/Date.js"/>"></script>	
		<title>
		</title>
		<style type="text/css">
<!--
.yin {
	visibility:hidden;
	position:absolute;
	
}
.xian{
	visibility:visible;
}
-->
</style>
		
		<script type="text/javascript"> 
function window_onload()
{
    highlightFormElements();
}
//联动


//save
function save_onclick()
{		

	if (confirm("您确定要提交吗？")) {
		if (!tmp_baseinfo_up) {
			baseinfo_onclick();
		}
		if (document.forms(0).startTime.value == "") {
			alert("交收开始时间不能为空！");
			document.forms(0).startTime.focus();
			return false;
		}
		if (document.forms(0).endTime.value == "") {
			alert("交收结束时间不能为空！");
			document.forms(0).endTime.focus();
			return false;
		}
		if (document.forms(0).startTime.value.indexOf("：") != "-1") {
			alert("时间不能输入中文冒号！");
			return false;
		}
		if (document.forms(0).endTime.value.indexOf("：") != "-1") {
			alert("时间不能输入中文冒号！");
			return false;
		}
		
		if (!isTime(document.forms(0).startTime.value)) {
			alert("交收开始时间格式不正确！");
			document.forms(0).startTime.focus();
			return false;
		}
		if (!isTime(document.forms(0).endTime.value)) {
			alert("交收结束时间格式不正确！");
			document.forms(0).endTime.focus();
			return false;
		}
		
		if (document.forms(0).endTime.value <= document.forms(0).startTime.value) {
			alert("交收结束时间应大于交收开始时间！");
			return false;
		}
		
		var inputs = document.getElementsByTagName("input");
		for(var i = 0; i < inputs.length; i++){
			if(inputs[i].type == "checkbox" && inputs[i].checked){
				if (document.forms(0).startMiddleTime.value == "") {
					alert("中立仓开始时间不能为空！");
					document.forms(0).startMiddleTime.focus();
					return false;
				}
				if (document.forms(0).endMiddleTime.value == "") {
					alert("中立仓结束时间不能为空！");
					document.forms(0).endMiddleTime.focus();
					return false;
				}
				if (document.forms(0).startMiddleTime.value.indexOf("：") != "-1") {
					alert("时间不能输入中文冒号！");
					return false;
				}
				if (document.forms(0).endMiddleTime.value.indexOf("：") != "-1") {
					alert("时间不能输入中文冒号！");
					return false;
				}
				
				if (!isTime(document.forms(0).startMiddleTime.value)) {
					alert("中立仓开始时间格式不正确！");
					document.forms(0).startMiddleTime.focus();
					return false;
				}
				if (!isTime(document.forms(0).endMiddleTime.value)) {
					alert("中立仓结束时间格式不正确！");
					document.forms(0).endMiddleTime.focus();
					return false;
				}
				if (document.forms(0).endTime.value >= document.forms(0).startMiddleTime.value) {
					alert("中立仓开始时间应大于交收结束时间！");
					return false;
				}
				if (document.forms(0).endMiddleTime.value <= document.forms(0).startMiddleTime.value) {
					alert("中立仓结束时间应大于中立仓开始时间！");
					return false;
				}
			}
		}
		document.forms(0).submit();
    	document.forms(0).save.disabled = false;
	}
		
	
    
 }

//delete
function delete_onclick(){
	if (confirm("您确定要操作吗？")) {
		document.forms(0).action = "<c:url value="/timebargain/delay/delay.do?funcflg=deleteDelaySection"/>";
		document.forms(0).submit();
	}
}

//cancel
function cancel_onclick()
{
   document.location.href = "<c:url value="/timebargain/baseinfo/tradeTime.do?funcflg=search"/>";
}


var tmp_baseinfo;
var tmp_baseinfo_up = true;
function baseinfo_onclick()
{
  if (tmp_baseinfo_up)
  {
    tmp_baseinfo_up = false;
    document.forms(0).baseinfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_baseinfo = baseinfo.innerHTML;
    baseinfo.innerHTML = "";
  }
  else
  {
    tmp_baseinfo_up = true;
    document.forms(0).baseinfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
    baseinfo.innerHTML = tmp_baseinfo;
  }
}



 function suffixNamePress()
{
	
  if (event.keyCode<=47 || event.keyCode>58)
  {
    event.returnValue=false;
  }
  else
  {
    event.returnValue=true;
  }
}
function doIt(){
	var s = false;
	var inputs = document.getElementsByTagName("input");
	for(var i = 0; i < inputs.length; i++){
		if(inputs[i].type == "checkbox" && inputs[i].checked){
			s = true;
		}
	} 	
	if(s){
		document.getElementById("one").style.display = "";
		document.getElementById("two").style.display = "";
	}else{
		document.getElementById("one").style.display = "none";
		document.getElementById("two").style.display = "none";
	}
}

</script>
	</head>
	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="80%" width="100%" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/delay/delay.do?funcflg=saveDelaySection"
						method="POST" styleClass="form" >
						
						<fieldset class="pickList" style="width:70%" align="center">
							<legend class="common">
								<b>延期交收节信息
								</b>
							</legend>

<table width="100%" border="0" align="center"  class="common" cellpadding="0" cellspacing="2">
<!-- 基本信息 -->
        <tr class="common">
          <td colspan="4">
              
<span id="baseinfo">
<table cellSpacing="0" cellPadding="0" width="100%" border="0" align="center" class="common">
								<tr>
									<td align="right" width="40%">
											交收开始时间：
									</td>
									<td>
										<html:text property="startTime" maxlength="8" style="ime-mode:disabled" 
											styleClass="text" onkeypress="return suffixNamePress()"/>
										<span class="req">* HH:MM:SS</span>
									</td>
								</tr>
								<tr>
									<td align="right">
											交收结束时间： 
									</td>
									<td>
										<html:text property="endTime" maxlength="8" style="ime-mode:disabled" 
											styleClass="text" onkeypress="return suffixNamePress()"/>
										<span class="req">* HH:MM:SS</span>
									</td>
								</tr>
								<tr>
									<td align="right" width="40%">
											是否启用中立仓交易节：
									</td>
									<td>
										<input id = "isNotDo" type = "checkbox" name = "isNotDo" value = "yes" onclick="doIt();" />
									</td>
								</tr>
								<tr id = "one" style="display:none;">
									<td align="right" width="40%">
											中立仓开始时间：
									</td>
									<td>
										<html:text property="startMiddleTime" maxlength="8" style="ime-mode:disabled" 
											styleClass="text" onkeypress="return suffixNamePress()"/>
										<span class="req">* HH:MM:SS</span>
									</td>
								</tr>
								<tr id = "two" style="display:none;">
									<td align="right">
											中立仓结束时间： 
									</td>
									<td>
										<html:text property="endMiddleTime" maxlength="8" style="ime-mode:disabled" 
											styleClass="text" onkeypress="return suffixNamePress()"/>
										<span class="req">* HH:MM:SS</span>
									</td>
								</tr>
								
													
</table >
</span>
          </td>
        </tr>					
								<tr>
									<td colspan="4" height="3">	
								</td>
								</tr>																																										
								<tr>
									<td colspan="4" align="center">
										<html:button property="save" styleClass="button"
											onclick="javascript:return save_onclick();">
											提交
										</html:button>
										<html:button property="cancel" styleClass="button"
											onclick="javascript:return delete_onclick();">
											删除
										</html:button>
									</td>
								</tr>
							</table>
						</fieldset>
						
						
						<html:hidden property="crud" />
						<html:hidden property="sectionID" />
					</html:form>
				</td>
			</tr>
			
		</table>
		
		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
<%
	String gouxuan = (String)request.getAttribute("gouxuan");
	if(gouxuan!=null&&gouxuan.equals("gou")){
		%>
			<script type="text/javascript"> 
				document.getElementById("isNotDo").checked=true;
				document.getElementById("one").style.display = "";
				document.getElementById("two").style.display = "";
			</script>
		<%
	}
 %>