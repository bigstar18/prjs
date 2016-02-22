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
 * 判断是否为空字符串
 */
function isEmpty(s){
	if(s.trim().length<=0){
		return true;
	}
	return false;
}
/**
 * 判断是否是整数
 */
function integer(s){
	if(isEmpty(s)){
		return true;
	}
	var patrn=/^([1-9]{1}[0-9]*|0)$/;
	if (patrn.exec(s)) {
		return true ;
	}
	return false ;
}
/**
 * 验证正的最多n为小数的数字 s 要校验的字符串，n 小数位数
 * true 验证成功
 * 验证失败
 */
function flote(s,n){
	if(isEmpty(s)){
		return true;
	}
	if(!integer(n)){
		return false;
	}else if(isEmpty(n)){
	}else if(n<0){
		return false;
	}else if(n==0){
		return integer(s);
	}
	var matchs='\^\\+\?([1-9]{1}[0-9]\*|0)(\\.[0-9]{1,'+n+'})\?\$';
	var patrn = new RegExp(matchs,"ig");
	if (patrn.exec(s)) {
		return true ;
	}
	return false;
}
/**
 * 判断字符串是否由数字字符组成
 * true 由数字组成
 * false 不只由数字组成
 */
function number(s){
	if(isEmpty(s)){
		return true;
	}
	var patrn=/^\d*$/;
	if (patrn.exec(s)) {
		return true ;
	}
	return false ;
}
/**
 * 判断是否为日期类型
 * true 是日期类型
 * false 不是日期类型
 */
function isDate(s){
	if(isEmpty(s)){
		return true;
	}
	if(/^\d{1,4}[-|\/]\d{1,2}[-|\/]\d{1,2}$/.test(s)){
		var r = s.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 
		if(r==null)return false; 
		var d= new Date(r[1], r[3]-1, r[4]); 
		return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]);
	}else{
		return false;
	}
}
/**
 * 是否是日期时间类型
 * true 是日期时间类型
 * false 不是日期时间类型
 */
function isDateTime(str)
{
	var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/; 
	var r = str.match(reg); 
	if(r==null) return false; 
	var d= new Date(r[1], r[3]-1,r[4],r[5],r[6],r[7]); 
	return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]&&d.getHours()==r[5]&&d.getMinutes()==r[6]&&d.getSeconds()==r[7]);
}
/**
 * 验证d1、d2两个日期哪个大
 * true d1小于等于d2
 * false d1大于d2，或者日期格式不正确
 */
function compareDate(d1,d2) {
	if(isEmpty(d1) || isEmpty(d2)){
		return true;
	}
	return ((new Date(d1.replace(/-/g,"\/"))) <= (new Date(d2.replace(/-/g,"\/"))));   
}
/**
 * 验证d1、d2两个日期哪个大
 * true d1小于等于d2
 * false d1大于d2，或者日期格式不正确
 */
function compareWithToday(d1) {
	if(isEmpty(d1)){
		return true;
	}
	return ((new Date(d1.replace(/-/g,"\/"))) <= (new Date()));   
}
/**
 * 判断是否包含特殊字符
 * s 被验证的字符串
 * ch true汉字不算特殊字符串，false 汉字算是字符串
 * vec 特殊字符数组，包含在数组中的不算特殊字符
 * true 不包含特殊字符
 * false 包含特殊字符
 */
function is_Str(s,ch,vec){
	var china = "";
	var strs = "";
	if(ch){
		china = "\\u4e00-\\u9fa5";
	}
	if(vec != null){
		for(var i=0;i<vec.length;i++){
			strs += "|\\"+vec[i];
		}
	}
	var matchs='\^[0-9A-Za-z'+china+strs+']{1,}\$';
	var patrn = new RegExp(matchs,"ig");
	if (patrn.exec(s)) {
		return true ;
	}
	return false;
}
var aCity={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"}; 

function is_CardID(sId){
	var iSum=0 ;
	var info="" ;
	if(!/^\d{17}(\d|x)$/i.test(sId)) {
		alert("你输入的身份证长度或格式错误");
		return false;
	}//"你输入的身份证长度或格式错误"; 
	sId=sId.replace(/x$/i,"a"); 
	if(aCity[parseInt(sId.substr(0,2))]==null) {
		alert("你的身份证地区非法");
		return false;
	}//"你的身份证地区非法"; 
	sBirthday=sId.substr(6,4)+"-"+Number(sId.substr(10,2))+"-"+Number(sId.substr(12,2)); 
	var d=new Date(sBirthday.replace(/-/g,"/")) ;
	if(sBirthday!=(d.getFullYear()+"-"+ (d.getMonth()+1) + "-" + d.getDate())){
		alert("身份证上的出生日期非法");
		return false;
	}//"身份证上的出生日期非法"; 
	for(var i = 17;i>=0;i --) iSum += (Math.pow(2,i) % 11) * parseInt(sId.charAt(17 - i),11) ;
	if(iSum%11!=1) {
		alert("你输入的身份证号非法");
		return false;
	}//"你输入的身份证号非法"; 
	alert("返回成功");
	return true;//aCity[parseInt(sId.substr(0,2))]+","+sBirthday+","+(sId.substr(16,1)%2?"男":"女") 
}