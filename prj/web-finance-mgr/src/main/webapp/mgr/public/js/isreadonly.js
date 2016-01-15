
/*设置form表单中的控件是否只读*/
function setReadOnly() {
		var regexTests = document.getElementsByTagName("input");
		var len = regexTests.length;
		for (i = 0; i < len; i++) {
			regexTests[i].readOnly = 'readOnly';
		}
		var regexselect = document.getElementsByTagName("select");
		var len2 = regexselect.length;
		for (i = 0; i < len2; i++) {
			setSelectReadOnly(regexselect[i]);
		}
}

function setSelectReadOnly(obj){
    obj.onfocus = function(){
        obj.defOpt=obj.selectedIndex;
    }
    obj.onchange = function(){
        obj.selectedIndex=obj.defOpt;
    }
}