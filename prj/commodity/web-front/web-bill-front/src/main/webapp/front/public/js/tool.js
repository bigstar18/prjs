/**
 * 文本说明：本文档中所有公用方法均以大写字母开头
 * LTrim(str) 将字符串去掉左空格
 * RTrim(str) 将字符串去掉右空格
 * Trim(str) 将字符串去掉两边空格，相当于str.trim();
 * ISEmpty(str) 判断字符串是否为空，为空返回true，不为空返回false
 * ISInteger(num) 判断是否是整数，是返回true，不是返回false
 * ISFlote(num,n) 判断num是否为最多n位小数的浮点数，是返回true，不是返回false
 * ISNumber(str) 判断字符串是否由数字组成，是返回true，不是返回false
 * ISDate(str) 判断字符串是否是日期类型，是返回true，不是返回false(年月日之间可以以"-"或"/"分割)
 * ISDateTime(str) 判断字符串是否是日期时间类型，是返回true，不是返回false(年月日之间可以以"-"或"/"分割，时分秒用":"分割)
 * CompareDate(d1,d2) 判断d1是否小于等于d2，是返回true，不是返回false(如果d1或d2非日期格式则抛出异常，用 try{}catch(error){error.name,error.message}finally{}来处理异常)
 * ISCardID(sId) 是否是合法身份证号，是返回true，不是返回false
 * ISEmail(email) 是否是合法邮箱，是返回true，不是返回false
 * ISPhone(phone) 是否是合法电话号码，是返回true，不是返回false
 * HasNoSpecialStr(s,ch,vec) 判断是否不包含特殊字符，不包含返回true，包含返回false(s：要验证的字符串；ch：true汉字不是特殊字符，false汉字是特殊字符；vec：其他不包含在特殊字符中的字符)
 * RemoveComma(money) 将包含","的资金修改为数值型资金信息
 * IncreaseComma(money) 把数值型资金信息加上","
 * IFrameAuto(ifmid,autoHeight,autoWidth) 通过iframe的id号使得iframe高度、宽度自适应
 */
/**
 * 去掉左右空格
 */
String.prototype.trim = function(){
	return this.replace(/(^\s*)|(\s*$)/g, "");
};
var Util={
	/**
	 * 去掉左空格
	 * @param str
	 * @return String
	 */
	LTrim:function (str){
		if(str){
			var i;
			for(i=0;i<str.length;i++){
				if(str.charAt(i)!=" "&&str.charAt(i)!="　")break;
			}
			str=str.substring(i,str.length);
		}
		return str;
	},
	/**
	 * 去掉右空格
	 * @param str
	 * @return String
	 */
	RTrim:function (str){
		if(str){
			var i;
			for(i=str.length-1;i>=0;i--){
				if(str.charAt(i)!=" "&&str.charAt(i)!="　")break;
			}
			str=str.substring(0,i+1);
		}
		return str;
	},
	/**
	 * 去掉左右空格
	 * @param str
	 * @return String
	 */
	Trim:function (str){
		return this.LTrim(this.RTrim(str));
	},
	/**
	 * 判断是否为空字符串
	 * @param str
	 * @return boolean
	 */
	ISEmpty:function (str){
		if(this.Trim(str+'').length<=0){
			return true;
		}
		return false;
	},
	/**
	 * 判断是否是整数
	 * @param num
	 * @return boolean
	 */
	ISInteger:function (num){
		if(this.ISEmpty(num)){
			return true;
		}
		var patrn=/^([1-9]{1}[0-9]*|0)$/;
		if (patrn.exec(num)) {
			return true ;
		}
		return false ;
	},
	/**
	 * 验证正的最多n为小数的数字 num 要校验的字符串，n 小数位数
	 * @param num,n
	 * @return boolean
	 */
	ISFlote:function (num,n){
		if(this.ISEmpty(num)){
			return true;
		}
		if(!n){
			n=2;
		}
		if(!this.ISInteger(n)){
			return false;
		}else if(n<0){
			return false;
		}else if(n==0){
			return this.ISInteger(num);
		}
		//var matchs='\^\\+\?([1-9]{1}[0-9]\*|0)(\\.[0-9]{1,'+n+'})\?\$';
		var matchs='\^\\+\?([0-9]\*|0)(\\.[0-9]{1,'+n+'})\?\$';
		var patrn = new RegExp(matchs,"ig");
		if (patrn.exec(num)) {
			return true ;
		}
		return false;
	},
	/**
	 * 判断字符串是否由数字字符组成
	 * @param str
	 * @return boolean
	 */
	ISNumber:function (str){
		if(this.ISEmpty(str)){
			return true;
		}
		var patrn=/^\d*$/;
		if (patrn.exec(str)) {
			return true ;
		}
		return false ;
	},
	/**
	 * 判断是否为日期类型
	 * @param str
	 * @return boolean
	 */
	ISDate:function (str){
		if(this.ISEmpty(str)){
			return true;
		}
		if(/^\d{4}[\/-]\d{1,2}[\/-]\d{1,2}$/.test(str)){
			var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 
			if(r==null)return false; 
			var d= new Date(r[1], r[3]-1, r[4]); 
			return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]);
		}else{
			return false;
		}
	},
	/**
	 * 是否是日期时间类型
	 * @param str
	 * @return boolean
	 */
	ISDateTime:function (str){
		if(this.ISEmpty(str)){
			return true;
		}
		var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/; 
		var r = str.match(reg); 
		if(r==null) return false; 
		var d= new Date(r[1], r[3]-1,r[4],r[5],r[6],r[7]); 
		return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]&&d.getHours()==r[5]&&d.getMinutes()==r[6]&&d.getSeconds()==r[7]);
	},
	/**
	 * 验证d1是否小于等于d2
	 * @param d1,d2
	 * @return boolean
	 * @throw TypeException
	 */
	CompareDate:function (d1,d2) {
		if(this.ISEmpty(d1) || this.ISEmpty(d2)){
			return true;
		}
		if(!this.ISDate(d1) || !this.ISDate(d2)){
			throw new TypeError("日期格式为 yyyy-MM-dd 或 yyyy/MM/dd");
		}
		return ((new Date(d1.replace(/-/g,"\/"))) <= (new Date(d2.replace(/-/g,"\/"))));   
	},
	/**
	 * 判断身份证合法
	 * @param sId
	 * @return boolean
	 */
	aCity:{11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"}, 
	ISCardID:function (sId){
		var iSum=0 ;
		var info="" ;
		if(!/^\d{17}(\d|x)$/i.test(sId)) {
			return false;
		}//"你输入的身份证长度或格式错误"; 
		sId=sId.replace(/x$/i,"a"); 
		if(this.aCity[parseInt(sId.substr(0,2))]==null) {
			return false;
		}//"你的身份证地区非法"; 
		sBirthday=sId.substr(6,4)+"-"+Number(sId.substr(10,2))+"-"+Number(sId.substr(12,2)); 
		var d=new Date(sBirthday.replace(/-/g,"/")) ;
		if(sBirthday!=(d.getFullYear()+"-"+ (d.getMonth()+1) + "-" + d.getDate())){
			return false;
		}//"身份证上的出生日期非法"; 
		for(var i = 17;i>=0;i --) iSum += (Math.pow(2,i) % 11) * parseInt(sId.charAt(17 - i),11) ;
		if(iSum%11!=1) {
			return false;
		}//"你输入的身份证号非法"; 
		return true;//aCity[parseInt(sId.substr(0,2))]+","+sBirthday+","+(sId.substr(16,1)%2?"男":"女") 
	},
	/**
	 * 邮箱验证
	 * var re = /^\w{1,15}(?:@(?!-))(?:(?:[a-z0-9-]*)(?:[a-z0-9](?!-))(?:\.(?!-)))+[a-z]{2,4}$/;
	 * @param email
	 * @return boolean
	 */
	ISEmail:function (email){
		if(this.ISEmpty(email)){
			return true;
		}
		var re = /^\w{1,64}(?:@(?!-))(?:(?:[a-z0-9-]*)(?:[a-z0-9](?!-))(?:\.(?!-)))+[a-z]{2,8}$/;
		if(!re.exec(email)){
			return false;
		}
		return true;
	},
	/**
	 * 验证电话号码
	 * @param phone
	 * @return boolean
	 */
	ISPhone:function (phone){
		if(this.ISEmpty(phone)){
			return true;
		}
		var re = /^[0-9\-\,]*$/; ///^(1[3,5,8][0-9]{9}|[0-9]{7,8}|0[0-9]{2,3}\-[0-9]{7,8}(\-[0-9]{0-4})?)$/;
		return re.exec(phone)
	},
	/**
	 * 判断是否包含特殊字符
	 * @param s 被验证的字符串
	 * @param ch true汉字不算特殊字符串，false 汉字算是字符串
	 * @param vec 特殊字符数组，包含在数组中的不算特殊字符
	 * @return boolean
	 */
	HasNoSpecialStr:function (s,ch,vec){
		if(this.ISEmpty(s)){
			return true;
		}
		var china = "";
		var strs = "";
		if(ch){
			china = "\\u4e00-\\u9fa5";
		}
		if(vec){
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
	},
	/**
	 * 钱，去掉逗号，小数点后面自动补全
	 * @param money
	 * @return String
	 */
	RemoveComma:function (money){
		if(this.ISEmpty(money)){
			return "";
		}
		money=this.Trim(money);
		money  = money.replace(/,/gi,"");
		money  = money.replace(/，/gi,"");
		var after ="";
		if(money.indexOf(".")>0){
			after = money.substring(money.indexOf("."),money.length);
		}
		if(!after==""){
			if(after.length<2){
				money += "00";
			}else if(after.length==2){
				money += "0";
			}
		}else{
			money += ".00";
		}
		if(!this.ISFlote(money)){
			return "";
		}
		return money;
	},
	/**
	 * 钱,千分位格式化
	 * @param money
	 * @return String
	 */
	IncreaseComma:function (money){
		var begin ="";
		var begin2="";
		var after =".00";
		var n = 2;
		if(!money){
			money = "";
		}
		money = this.Trim(money);
		if(money.indexOf(".")<0){
			begin = money;
		}else{
			begin = money.substring(0,money.indexOf("."));
			after = money.substring(money.indexOf("."),money.length);
		}
		if(this.Trim(begin).length<=0){
			begin="0";
		}
		if(after.length<n+1){
			for(i=n+1;i>after.length;){
				after+="0";
			}
		}
		after = after.substring(0,n+1);
		if(!this.ISFlote(begin+after,n)){
			return "";
		}
		while(begin.length>3){
			begin2 = ","+begin.substring(begin.length-3,begin.length)+begin2;
			begin=begin.substring(0,begin.length-3);
		}
		return begin+begin2+after;
	},
	/**
	 * 设置iframe自适应
	 * @param ifmid
	 * @param autoHeight
	 * @param autoWidth
	 * @param addHeight
	 */
	IFrameAuto:function (ifmid,autoHeight,autoWidth,addHeight){
		var h = 14;
		var minheight=563;
		var ifm= document.getElementById(ifmid);
		if(ifm != null) {
			if(autoHeight){
				ifm.height=0;//防止其高度无限增长高度，所以每次都先将高度清零
				if(!addHeight)addHeight=0;
				if(ifm.contentWindow.document.documentElement.scrollHeight<minheight){
					ifm.height=minheight+h+addHeight;
				}else{
					ifm.height=ifm.contentWindow.document.documentElement.scrollHeight+h+addHeight;
				}
			}
			if(autoWidth){
				ifm.width = subWeb.body.scrollWidth;
			}
		}
	},
	/**
	 * encode转码
	 * @param str
	 * @return string
	 */
	Encode:function (str){
		return encodeURI(str,"utf-8");
	},
	/**
	 * 还原 encode
	 * @param str
	 * @return string
	 */
	Decode:function (str){
		return decodeURI(str,"utf-8");
	},
	getString:function(a,d){
		if(!typeof d=="object"&&!d.length){
			return;
		}
		var e,c;
		for(var b=0;b<d.length;b++){
			c=new RegExp("\\{"+b+"\\}");//格式为{n}
			e=e?e.replace(c,d[b]):a.replace(c,d[b]);//如果e不为空则将e的{n}替换为d的第n个元素否则e为a将{n}替换为d的第n个元素
		}
		return e;
	}
}