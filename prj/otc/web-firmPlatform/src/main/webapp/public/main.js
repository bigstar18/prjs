function isNull(obj,msg) //��ֵ���
{
	if(obj==""){
		return false;
	}else{
		return true;
	}
}

function myReset() {
	//frm.reset();
	var action=frm.action.toString();
	window.location.href=action;
}