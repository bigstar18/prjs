/**去掉左右空格*/
String.prototype.trim = function(){
	return this.replace(/(^\s*)|(\s*$)/g, "");
};
/**判断是否是数字 true 是数字 false 不是数字*/
function fudianshu(s){
	var patrn=/^\+?([1-9]{1}[0-9]*|0)(\.[0-9]{1,2})?$/;
	if (patrn.exec(s)) {
		return true ;
	}
	return false ;
}
/**判断是否不包含特殊字符 true 不包含 false 包含*/
function isStr(str){
	var patrn=/^\w*$/;
	if (patrn.exec(str)) {
		return true ;
	}
	return false ;
}
/**
 * 判断 value 是否符合约束
 * type = double 则判断 value是否为正的 double 并且最多两位小数
 * type = str 则判断 value 是否为普通字符串，即是否不包含特殊字符
 */
function calibration(type,value){
	var str = value.trim();
	var flag = true;
	if(str != ""){
		if(type == "double"){
			if(fudianshu(str)){
				flag=true;
			}else{
				flag=false;
			}
		}else if(type == "str"){
			if(isStr(str)){
				flag=true;
			}else{
				flag=false;
			}
		}
	}
	return flag;
}