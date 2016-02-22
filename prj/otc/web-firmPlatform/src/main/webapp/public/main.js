function isNull(obj,msg) //ø’÷µºÏ≤È
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