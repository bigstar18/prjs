function checkQueryDate(startDate,endDate) {
	var now = new Date();
	var   s   =   new   Date(Date.parse(startDate.replace(/-/g,   "/")));
	var   e   =   new   Date(Date.parse(endDate.replace(/-/g,   "/")));
	if(startDate==""){
		alert("��ʼ���ڲ���Ϊ��");
		return false;
	}
	else if(endDate==""){
		alert("�������ڲ���Ϊ��");
		return false;
	}
	else if (s!="" && s > now ) {
		alert("��ʼ���ڲ��ܴ��ڵ�ǰ����");
		return false;
	}else if(e!="" &&��e>now){
		alert("�������ڲ��ܴ��ڵ�ǰ����");
		return false;
	}
	else if(s>e){
		alert("��ʼ���ڲ��ܴ��ڽ�������");
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
		alert("��ʼ���ڲ���Ϊ��");
		return false;
	} 
	else if(endDate=="" && queryType=='H'){
		alert("�������ڲ���Ϊ��");
		return false;
	} 
	else if (s > now && queryType=='H') {
		alert("��ʼ���ڲ��ܴ��ڵ�ǰ����");
		return false;
	}else if(e>now && queryType=='H'){
		alert("�������ڲ��ܴ��ڵ�ǰ����");
		return false;
	}
	else if(s>e && queryType=='H'){
		alert("��ʼ���ڲ��ܴ��ڽ�������");
		return false;
	} 
	else {
		frm.submit();
	}
}