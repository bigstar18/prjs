function checkQueryDate(startDate,endDate) {
	var now = new Date();
	var   s   =   new   Date(Date.parse(startDate.replace(/-/g,   "/")));
	var   e   =   new   Date(Date.parse(endDate.replace(/-/g,   "/")));
	if (s!="" && s > now ) {
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
