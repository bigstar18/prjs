function checkQueryDate(startDate,endDate) {
	var now = new Date();
	var   s   =   new   Date(Date.parse(startDate.replace(/-/g,   "/")));
	var   e   =   new   Date(Date.parse(endDate.replace(/-/g,   "/")));
	if (s!="" && s > now ) {
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
