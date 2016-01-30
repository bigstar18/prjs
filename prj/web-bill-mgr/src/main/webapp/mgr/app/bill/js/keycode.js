(function initLogonObj(){
	var object = '<OBJECT classid=clsid:0023145A-18C6-40C7-9C99-1DB6C3288C3A id="ePass" STYLE="LEFT: 0px; TOP: 0px" width=0 height=0 CODEBASE="/GnntKey.cab#Version=1,0,0,5"></OBJECT>';
	document.body.insertAdjacentHTML("beforeEnd", object);
})();
/**
 * @param marketID 市场编号
 * @param userName 输入用户名
 * @param logonfrm 登录的表单
 * @return object{str:key值,passed:是否通过,errorCode:错误码,msg:错误信息}
 */
function checkKeyCode(marketID,userName,logonfrm){
	var result = {str:"",passed:false,errorCode:0,msg:''};
	var ifInstalled = true;
	try{
		result.str = ePass.VerifyUser(marketID,userName);
	}catch(err){
		ifInstalled = false;
		result.msg='key盘验证其他错误！';
	}
	if(isNaN(result.str)){
		result.passed=true;
	}else{
		ifInstalled = true;
		result.errorCode = parseInt(result.str);
		if(result.errorCode==-1){
			result.passed=true;
		}else if (result.errorCode==-2){
			result.msg="非法USB身份验证盘！";
		}else if (result.errorCode==-3){
			result.msg="USB身份验证盘不正确！";
		}else if (result.errorCode==-4){
			result.msg="USB身份验证盘已经损坏，请联系发放者！";
		}else if(result.errorCode==-10){
			result.msg="读Key驱动程序错误！";
		}else{
			result.passed=true;
		}
	}
	if(!ifInstalled){
		result.msg="请安装交易控件以正常登录系统！";
	}else{
		var kcode = document.getElementById("kcode");
		if(!kcode){
			kcode = document.createElement("<input type='hidden' id='kcode' name='entity.keyCode'/>");
			logonfrm.appendChild(kcode);
		}
		if(result.str == -1){
			kcode.value = "0123456789ABCDE";
		}else{
			kcode.value = result.str;
		}
	}
	return result;
}
/**
 * @param marketID 市场编号
 * @param userName 输入用户名
 * @param logonfrm 登录的表单
 * @return object{str:key值,passed:是否通过,errorCode:错误码,msg:错误信息}
 */
function initKeyCode(marketID,userName,logonfrm){
	var result = {str:"",passed:false,errorCode:0,msg:''};
	var ifInstalled = true;
	try{
		result.str = ePass.VerifyUser(marketID,userName);
	}catch(err){
		ifInstalled = false;
		result.msg='key盘验证其他错误！';
	}
	if(isNaN(result.str)){
		result.passed=true;
	}else{
		ifInstalled = true;
		result.errorCode = parseInt(result.str);
		if(result.errorCode==-1){
			result.msg="请插入USB身份验证盘！";
		}else if (result.errorCode==-2){
			result.msg="非法USB身份验证盘！";
		}else if (result.errorCode==-3){
			result.msg="USB身份验证盘不正确！";
		}else if (result.errorCode==-4){
			result.msg="USB身份验证盘已经损坏，请联系发放者！";
		}else if(result.errorCode==-10){
			result.msg="读Key驱动程序错误！";
		}else{
			result.passed=true;
		}
	}
	if(!ifInstalled){
		result.passed=false;
		result.msg="请安装交易控件以正常登录系统！";
	}else{
		var kcode = document.getElementById("kcode");
		if(!kcode){
			kcode = document.createElement("<input type='hidden' id='kcode' name='entity.keyCode'/>");
			logonfrm.appendChild(kcode);
		}
		if(result.errorCode >= 0){
			kcode.value = result.str;
		}
	}
	return result;
}