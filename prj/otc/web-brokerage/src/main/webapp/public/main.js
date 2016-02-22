function isNull(obj,msg) //¿ÕÖµ¼ì²é
{
	if(obj==""){
		return false;
	}else{
		return true;
	}
}

function myReset() {//Çå¿Õ
	//frm.reset();
	var pathRrl=frm.action.toString();
	var urlArray=pathRrl.split("?");
	
  if(urlArray.length==1){
		pathRrl=pathRrl+"?noQuery=true";
	}else if(urlArray.length==2){
		pathRrl=pathRrl+"&noQuery=true";
	}
	window.location.href=pathRrl;
}