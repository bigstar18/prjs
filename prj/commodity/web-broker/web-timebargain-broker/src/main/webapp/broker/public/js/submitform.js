

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

function clearSubmitCount(){
	submitCount=0;
}
/* ��֤�޸�ҳ���ύʱ�Ƿ������ݸ��� */
function isFormChanged(fm,exceptObjName)
{
		if(fm==null) fm=document.forms[0];
		if(exceptObjName==null) exceptObjName=="";
		var selectObjs=fm.getElementsByTagName("SELECT");// For Select Obj
		for(var i=0;i<selectObjs.length;i++)
			{
				if((selectObjs[i].name=="")||(eval("/(^|,)"+selectObjs[i].name+"(,|$)/g").test(exceptObjName))) continue;
				for(var j=1;j<selectObjs[i].length;j++)
				{
					if(selectObjs[i].options[j].defaultSelected!=selectObjs[i].options[j].selected){
						return true;
					}
				}
			}
		
		var inputObjs=fm.getElementsByTagName("INPUT");// For Input Obj
		for(var i=0;i<inputObjs.length;i++)
		{
			if((inputObjs[i].name=="")||(eval("/(^|,)"+inputObjs[i].name+"(,|$)/g").test(exceptObjName))) continue;
			if((inputObjs[i].type.toUpperCase()=="TEXT")&&(inputObjs[i].defaultValue!=inputObjs[i].value)){
				return true;
			} else if(((inputObjs[i].type.toUpperCase()=="RADIO")||(inputObjs[i].type.toUpperCase()=="CHECKBOX"))
					&&(inputObjs[i].defaultChecked!=inputObjs[i].checked)) {
				return true;
			}
		}

		var textareaObjs=fm.getElementsByTagName("TEXTAREA");// For Textarea
																// Obj
		for(var i=0;i<textareaObjs.length;i++)
		{
			
			if((textareaObjs[i].name=="")||(eval("/(^|,)"+textareaObjs[i].name+"(,|$)/g").test(exceptObjName))) continue;
			if(textareaObjs[i].defaultValue!=textareaObjs[i].value)
			return true;
		}
	return false;
}
/*
 * ��ȡ������Ϣ ֻҪ�е�������Ϣ ��ô�����ȷ��ʱ ��Ϣ�Լ���ҳ��ر�
 */
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
/* ���õ���ҳ��Ĵ�С */
function showDialog(url, args, width, height){
	if (!width) width = 600;
	if (!height) height = 400;
	if(checkie()){
		height=height+50;
	}
	if (!args) args='';
	var urlArray=url.split("?");
	if(urlArray.length==1){
		url=url+'?t='+Math.random();
	}else if(urlArray.length==2){
		url=url+'&t='+Math.random();
	}
	var flag=false;
	var result=window.showModalDialog(url, args, "dialogWidth=" + width + "px; dialogHeight=" + height + "px; status=yes;scroll=yes;help=no;");
	
	if(result>0&&result!=undefined)
	{
	  flag=true;
	}
	return flag;
}

/* ���õ���ҳ��Ĵ�С */
function showDialogRes(url, args, width, height){
	if (!width) width = 600;
	if (!height) height = 400;
	if(checkie()){
		height=height+50;
	}
	if (!args) args='';
	var urlArray=url.split("?");
	if(urlArray.length==1){
		url=url+'?t='+Math.random();
	}else if(urlArray.length==2){
		url=url+'&t='+Math.random();
	}
	var result=window.showModalDialog(url, args, "dialogWidth=" + width + "px; dialogHeight=" + height + "px; status=yes;scroll=yes;help=no;");
	return result;
}
/* �ж���������� */
function checkie(){
    if(navigator.userAgent.indexOf("MSIE")>0){ 
       if(navigator.userAgent.indexOf("MSIE 6.0")>0){ 
         return true;
      } 
     }
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
/* ���� ��ղ�ѯ���ֵ */

function myReset() {
	// frm.reset();
	var pathRrl=frm.action.toString();
	window.location.href=pathRrl;
}
/*ɾ������ ����true ���� false*/
function checkDelete(collCheck,url)
{
	if(isSelNothing(collCheck) == -1)
	{
		alert ( "û�п��Բ��������ݣ�" );
		return false;
	}
	if(isSelNothing(collCheck))
	{
		alert ( "��ѡ����Ҫ���������ݣ�" );
		return false;
	}
	if(confirm("��ȷ��Ҫɾ����"))
	{
		return true;
	}
	return false;
}
