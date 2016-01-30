

/*限制连续按钮点击*/
function affirm(msg){
	if(checkSubmitCount()){
		if(!msg){//如果传入的判断信息为空，则自动返回成功
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
/* 验证修改页面提交时是否有数据改正 */
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
 * 获取返回信息 只要有弹出的信息 那么当点击确定时 信息以及其页面关闭
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
/* 设置弹出页面的大小 */
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

/* 设置弹出页面的大小 */
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
/* 判断浏览器类型 */
function checkie(){
    if(navigator.userAgent.indexOf("MSIE")>0){ 
       if(navigator.userAgent.indexOf("MSIE 6.0")>0){ 
         return true;
      } 
     }
   }
var HIDDEN_FRAME_INDEX = 0;
function initForm() {
	/* 拿到所有的form对象（里面包含所有的元素） */
	var collFrm = document.all.tags("FORM");
	if (collFrm) {
		for ( var i = 0; i < collFrm.length; i++) {
			setTarget(collFrm[i]);
		}

	}
}
/*
 * 在页面中 添加一个隐藏的ifame action提交转向此iframe form 的target属性指向此iframe
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
/* 重置 清空查询块的值 */

function myReset() {
	// frm.reset();
	var pathRrl=frm.action.toString();
	window.location.href=pathRrl;
}
/*删除操作 返回true 或者 false*/
function checkDelete(collCheck,url)
{
	if(isSelNothing(collCheck) == -1)
	{
		alert ( "没有可以操作的数据！" );
		return false;
	}
	if(isSelNothing(collCheck))
	{
		alert ( "请选择需要操作的数据！" );
		return false;
	}
	if(confirm("你确定要删除吗？"))
	{
		return true;
	}
	return false;
}
