(function initLogonObj(){
	var object = '<OBJECT classid=clsid:0023145A-18C6-40C7-9C99-1DB6C3288C3A id="ePass" STYLE="LEFT: 0px; TOP: 0px" width=0 height=0 CODEBASE="/GnntKey.cab#Version=1,0,0,5"></OBJECT>';
	document.body.insertAdjacentHTML("beforeEnd", object);
})();
/**
 * @param marketID �г����
 * @param userName �����û���
 * @param logonfrm ��¼�ı�
 * @return object{str:keyֵ,passed:�Ƿ�ͨ��,errorCode:������,msg:������Ϣ}
 */
function checkKeyCode(marketID,userName,logonfrm){
	var result = {str:"",passed:false,errorCode:0,msg:''};
	var ifInstalled = true;
	try{
		result.str = ePass.VerifyUser(marketID,userName);
	}catch(err){
		ifInstalled = false;
		result.msg='key����֤��������';
	}
	if(isNaN(result.str)){
		result.passed=true;
	}else{
		ifInstalled = true;
		result.errorCode = parseInt(result.str);
		if(result.errorCode==-1){
			result.passed=true;
		}else if (result.errorCode==-2){
			result.msg="�Ƿ�USB�����֤�̣�";
		}else if (result.errorCode==-3){
			result.msg="USB�����֤�̲���ȷ��";
		}else if (result.errorCode==-4){
			result.msg="USB�����֤���Ѿ��𻵣�����ϵ�����ߣ�";
		}else if(result.errorCode==-10){
			result.msg="��Key�����������";
		}else{
			result.passed=true;
		}
	}
	if(!ifInstalled){
		result.msg="�밲װ���׿ؼ���������¼ϵͳ��";
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
 * @param marketID �г����
 * @param userName �����û���
 * @param logonfrm ��¼�ı�
 * @return object{str:keyֵ,passed:�Ƿ�ͨ��,errorCode:������,msg:������Ϣ}
 */
function initKeyCode(marketID,userName,logonfrm){
	var result = {str:"",passed:false,errorCode:0,msg:''};
	var ifInstalled = true;
	try{
		result.str = ePass.VerifyUser(marketID,userName);
	}catch(err){
		ifInstalled = false;
		result.msg='key����֤��������';
	}
	if(isNaN(result.str)){
		result.passed=true;
	}else{
		ifInstalled = true;
		result.errorCode = parseInt(result.str);
		if(result.errorCode==-1){
			result.msg="�����USB�����֤�̣�";
		}else if (result.errorCode==-2){
			result.msg="�Ƿ�USB�����֤�̣�";
		}else if (result.errorCode==-3){
			result.msg="USB�����֤�̲���ȷ��";
		}else if (result.errorCode==-4){
			result.msg="USB�����֤���Ѿ��𻵣�����ϵ�����ߣ�";
		}else if(result.errorCode==-10){
			result.msg="��Key�����������";
		}else{
			result.passed=true;
		}
	}
	if(!ifInstalled){
		result.passed=false;
		result.msg="�밲װ���׿ؼ���������¼ϵͳ��";
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