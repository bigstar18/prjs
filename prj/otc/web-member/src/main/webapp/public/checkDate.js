function checkQueryDate(startDate,endDate) {
	var now = new Date();
	var   s   =   new   Date(Date.parse(startDate.replace(/-/g,   "/")));
	var   e   =   new   Date(Date.parse(endDate.replace(/-/g,   "/")));
	if(startDate==""){
		alert("开始日期不能为空");
		return false;
	}
	else if(endDate==""){
		alert("结束日期不能为空");
		return false;
	}
	else if (s!="" && s > now ) {
		alert("开始日期不能大于当前日期");
		return false;
	}else if(e!="" &&　e>now){
		alert("结束日期不能大于当前日期");
		return false;
	}
	else if(s>e){
		alert("开始日期不能大于结束日期");
		return false;
	} 
	else {
		frm.submit();
	}
}
function checkTotalQueryDate(startDate,endDate,queryType) {
	var now = new Date();
	var   s   =   new   Date(Date.parse(startDate.replace(/-/g,   "/")));
	var   e   =   new   Date(Date.parse(endDate.replace(/-/g,   "/")));
	 if(startDate=="" && queryType=='H'){
		alert("开始日期不能为空");
		return false;
	} 
	else if(endDate=="" && queryType=='H'){
		alert("结束日期不能为空");
		return false;
	} 
	else if (s > now && queryType=='H') {
		alert("开始日期不能大于当前日期");
		return false;
	}else if(e>now && queryType=='H'){
		alert("结束日期不能大于当前日期");
		return false;
	}
	else if(s>e && queryType=='H'){
		alert("开始日期不能大于结束日期");
		return false;
	} 
	else {
		frm.submit();
	}
}