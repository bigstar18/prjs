function isNull(obj,msg) //��ֵ���
{
	if(obj==""){
		return false;
	}else{
		return true;
	}
}

function myReset() {//���
	//frm.reset();
	var pathRrl=frm.action.toString();
	var au = '111111';
	if (typeof (AUsessionId) != "undefined") {
		au = AUsessionId;
	}
	var urlArray=pathRrl.split("?");
  if(urlArray.length==1){
		pathRrl=pathRrl+'?AUsessionId='+au+"&noQuery=true";
	}else if(urlArray.length==2){
		pathRrl=pathRrl+'&AUsessionId='+au+"&noQuery=true";
	}
	window.location.href=pathRrl;
}