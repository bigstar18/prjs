/**
 * ������
 * url ����·��,args ,width ���,height �߶�
 */
function showDialog(url,args,width,height){
	if(!args) args='';
	if(!width) width=600;
	if(!height) height=400;
	if(checkie()) height+=50;
	if(url.indexOf("?")>0){
		url+="&t="+Math.random();
	}else{
		url+="?t="+Math.random();
	}
	var result = window.showModalDialog(url, args, "dialogWidth=" + width + "px; dialogHeight=" + height + "px; status=yes;scroll=yes;help=no;");
	return result;
}
//�ж�������Ƿ�Ϊ IE
function checkie(){
	if(navigator.userAgent.indexOf("MSIE")>0)
		if(navigator.userAgent.indexOf("MSIE 6.0")>0)
			return true;
	return false;
}
//�ύ����ֵ���رնԻ���
function closeDialog(parentReload){
	if(!parentReload)
		parentReload=0;
	if(isNaN(parentReload))
	{
	   	parentReload=parseInt(parentReload);
	}
	if(parentReload>0)
	{
	   window.returnValue = parentReload;
	   window.close();
	}else{
		if(parent)
		   parent.clearSubmitCount();
	}
	
}
/*����������ť���*/
function affirm(msg){
	if(checkSubmitCount()){
		if(!msg){//���������ж���ϢΪ�գ����Զ����سɹ�
			return true;
		}
		var confirmMark= window.confirm(msg);
			if(!confirmMark){
				submitCount--;
			}
			return confirmMark;
	}else{
		return false;
	}
} 
var submitCount=0;    
function checkSubmitCount(){
    if(++submitCount>1){
    	return false;    
    }else{
      return true;	
    }
        
}

function affirms(msg){
	if(!msg){//���������ж���ϢΪ�գ����Զ����سɹ�
		return true;
	}
	var confirmMark= window.confirm(msg);
		if(!confirmMark){
			submitCount--;
		}
		return confirmMark;
	
} 
function clearSubmitCount(){
	submitCount=0;
}
var HIDDEN_FRAME_INDEX = 0;
function initForm() {
	/* �õ����е�form��������������е�Ԫ�أ� */
	var collFrm = document.all.tags("FORM");
	if (collFrm) {
		for ( var i = 0; i < collFrm.length; i++) {
			setTarget(collFrm[i]);
		}

	}
}
/*
 * ��ҳ���� ���һ�����ص�ifame action�ύת���iframe form ��target����ָ���iframe
 * 
 */
function setTarget(objForm) {
	if (objForm.targetType == "hidden") {
		var iframeName = objForm.name + "_" + HIDDEN_FRAME_INDEX++;
		var iframeStr;
		if (objForm.callback) {
			iframeStr = "<iframe id='"
					+ iframeName
					+ "' name='"
					+ iframeName
					+ "' width=0 height=0 style='display:none' src='' application='yes' callback='"
					+ objForm.callback + "'></iframe>";
		} else {
			iframeStr = "<iframe id='"
					+ iframeName
					+ "' name='"
					+ iframeName
					+ "' width=0 height=0 style='display:none' src='' application='yes'></iframe>";
		}
		document.body.insertAdjacentHTML("beforeEnd", iframeStr);
		objForm.target = iframeName;

	}
}