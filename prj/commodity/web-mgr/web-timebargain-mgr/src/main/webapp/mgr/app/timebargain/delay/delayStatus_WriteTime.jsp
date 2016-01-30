<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<base target="_self" />
		<title>
			设置恢复交收时间
		</title>
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script type="text/javascript"> 

//save
function save_onclick()
{
   if(document.getElementById("recoverTime").value == "")
   {
       alert("恢复时间不能为空！");
       $("#recoverTime").focus();
 	   return false;
   }

   
	var str = document.getElementById("recoverTime").value;

	if (!isTime(str)) {
		alert('输入的参数不是时间格式'); 
		$("#recoverTime").focus();
		return false;
	}
	/*
	var a = str.match(/^(\d{1,2})(:)(\d{1,2})(:)(\d{1,2})$/);

	if (a == null) {
		alert('输入的参数不是时间格式'); 
		$("#recoverTime").focus();
		return false;
	}
	if (a[1]>24 || a[3]>60 || a[5]>60){
		
		alert("输入的参数不是时间格式");
		$("#recoverTime").focus();
		return false
	}*/
   //window.returnValue = delayForm.recoverTime.value;
   $("#frm").submit();
   $("#update").attr("disabled", true);
   
   //window.close();
}
//时间判断
/*
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
}*/
function isTime(val) {
	var str=val;
    
	if(str.length == 8) {
     	var j=str.split(":");
     	if(j.length == 3) {
     		var a = j[0].match(/^(\d{2})$/);
	   		if (a == null) {
				return false;
			}
			a = j[1].match(/^(\d{2})$/);
	   		if (a == null) {
				return false;
			}
	   		a = j[2].match(/^(\d{2})$/);
	   		if (a == null) {
				return false;
			}
			
			if (j[0]>24||j[1]>60||j[2]>60) {
	           	return false;
	    	}
        } else {
			return false;
	    } 	
  	} else {
       	return false;
   	}
    return true;
}
</script>
	</head>

	<body>
		<table border="0" height="80%" width="80%" align="center">
			<tr>
				<td>
					<form id="frm" name="frm" action="${basePath }/timebargain/delay/updateDelayStatusRecoverTime.action"
						method="POST">
						<fieldset class="pickList" >
							<legend class="common">
								<b>设定恢复时间
								</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0"
								class="common">
								<tr>
									
									<td>
										恢复时间：<input type="text" id="recoverTime" name="recoverTime" class="input_text" />
										<span class="required">*</span>
									</td>
									
								</tr>
								<tr>
									<td>
										<span class="required">时间填写格式为：24hours HH:MM:SS</span>
									</td>
								</tr>									
								
								<tr>
									<td colspan="2" align="center">
									    <rightButton:rightButton name="提交" onclick="javascript:return save_onclick();" className="btn_sec" action="/timebargain/delay/updateDelayStatus.action" id="update"></rightButton:rightButton>
									</td>
								</tr>
							</table>
						</fieldset>
						<input type="hidden" id="type" name="type" value="99"/>
					</form>
				</td>
			</tr>
		</table>
	</body>
</html>
