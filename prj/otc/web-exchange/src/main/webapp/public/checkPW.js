function checkPW(password1, password2) {
	 var re=/^[0-9]+[a-zA-Z]+[a-zA-Z0-9]*|[a-zA-Z]+[0-9]+[a-zA-Z0-9]*$/;//验证输入只能是字母或数字
	if (Trim(password1) == "") {
		alert("密码不能为空！");
		frm.password.focus();
		return false;
	} else if (Trim(password1) == "") {
		alert("确认密码不能为空！");
		frm.password1.focus();
		return false;
	} else if (password1 != password2) {
		alert("密码与确认密码不一致！");
		frm.password.focus();
		return false;
	} else if (!chkLen(Trim(password1), 6)) {
		alert("密码不能小于6位！");
		return false;
	} else if (Trim(password1).length>16) {
		alert("密码不能大于16位！");
		return false;
	}else if (!isStr(password1,false,new Array('-','_','!','@','#','$','%','^','&','*',',','.','?'))) {
		alert("密码包含特殊字符，请重新输入！");
		return false;
	}
	
	/*else if (password1.replace(re, "")) {
		alert("密码只能为字母和数字的组合！");
		return false;
	}*/
	
	else {
		if (userConfirm()) {
			frm.submit();
			//return true;
		} else {
			return false;
		}
	}
}
function checkPassword(password) {
	 //var re=/^[0-9]+[a-zA-Z]+[a-zA-Z0-9]*|[a-zA-Z]+[0-9]+[a-zA-Z0-9]*$/;//验证输入只能是字母或数字
	if (Trim(password) == "") {
		return "密码不能为空";
	}  else if (!chkLen(Trim(password), 6)) {
		return "密码不能小于6位！";
	} else if (Trim(password).length>16) {
		return "密码不能大于16位！";
		//password.replace(re, "")
	}else if (!isStr(password,false,new Array('-','_','!','@','#','$','%','^','&','*',',','.','?'))) {
		return "密码包含特殊字符，请重新输入！";
	}else{
		return "";
	}
}
