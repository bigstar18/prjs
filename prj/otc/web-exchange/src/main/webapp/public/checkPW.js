function checkPW(password1, password2) {
	 var re=/^[0-9]+[a-zA-Z]+[a-zA-Z0-9]*|[a-zA-Z]+[0-9]+[a-zA-Z0-9]*$/;//��֤����ֻ������ĸ������
	if (Trim(password1) == "") {
		alert("���벻��Ϊ�գ�");
		frm.password.focus();
		return false;
	} else if (Trim(password1) == "") {
		alert("ȷ�����벻��Ϊ�գ�");
		frm.password1.focus();
		return false;
	} else if (password1 != password2) {
		alert("������ȷ�����벻һ�£�");
		frm.password.focus();
		return false;
	} else if (!chkLen(Trim(password1), 6)) {
		alert("���벻��С��6λ��");
		return false;
	} else if (Trim(password1).length>16) {
		alert("���벻�ܴ���16λ��");
		return false;
	}else if (!isStr(password1,false,new Array('-','_','!','@','#','$','%','^','&','*',',','.','?'))) {
		alert("������������ַ������������룡");
		return false;
	}
	
	/*else if (password1.replace(re, "")) {
		alert("����ֻ��Ϊ��ĸ�����ֵ���ϣ�");
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
	 //var re=/^[0-9]+[a-zA-Z]+[a-zA-Z0-9]*|[a-zA-Z]+[0-9]+[a-zA-Z0-9]*$/;//��֤����ֻ������ĸ������
	if (Trim(password) == "") {
		return "���벻��Ϊ��";
	}  else if (!chkLen(Trim(password), 6)) {
		return "���벻��С��6λ��";
	} else if (Trim(password).length>16) {
		return "���벻�ܴ���16λ��";
		//password.replace(re, "")
	}else if (!isStr(password,false,new Array('-','_','!','@','#','$','%','^','&','*',',','.','?'))) {
		return "������������ַ������������룡";
	}else{
		return "";
	}
}
