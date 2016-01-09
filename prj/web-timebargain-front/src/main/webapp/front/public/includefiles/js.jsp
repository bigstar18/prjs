<%@ page contentType="text/html;charset=GBK"%>
<script type="text/javascript" src="${basePath }/front/public/js/submitcount.js"></script>
<script>
//页面获取焦点
function getFocusID(getID){
	document.getElementById(getID).focus();
}
//特殊字符过滤
function showKeyPress(evt) {
	evt = (evt) ? evt : window.event
	return checkSpecific(String.fromCharCode(evt.keyCode));
}
function checkSpecific(realkey){
	var specialKey = "!<>#$()?%\&\^*\'\"\+\|";//特殊字符列表
	var flg = false;
	flg = (specialKey.indexOf(realkey) >= 0);
	if (flg) {
		return false;
	}
	return true;
}
function documentpaste(){
	var specialchar=['!','@','#','%','&','<','>','\'','\\\\','[?]','[(]','[)]'];
	var text = clipboardData.getData("text");
	for(var i=0;i<specialchar.length;i++){
		var str=specialchar[i];
		var patrn = new RegExp(str,'g');
		text=text.replace(patrn,'');
	}
	clipboardData.setData('text',text);
}
function disableBackspace(e){
	var ev = e || window.event;
    var obj = ev.target || ev.srcElement;
    var t = obj.type || obj.getAttribute('type');
    var vReadOnly = obj.getAttribute('readonly');
    var vEnabled = obj.getAttribute('enabled');
    vReadOnly = (vReadOnly == null) ? false : vReadOnly;
    vEnabled = (vEnabled == null) ? true : vEnabled;
    var flag1=(ev.keyCode == 8 && (t=="password" || t=="text" || t=="textarea") && (vReadOnly=='readonly' || vEnabled!=true))?true:false;
    var flag2=(ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea")?true:false;
    if(flag2){
        return false;
    }
    if(flag1){
        return false;
    }
	return true;
}
document.onkeypress=showKeyPress;
document.onkeydown=disableBackspace;
document.documentElement.onpaste=documentpaste;
</script>