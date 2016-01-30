/**
 * ��һ�μ���ҳ�����ı����Զ���ý���
 */
function getFocus(inputID){
	document.getElementById(inputID).focus();
}




/**
 * ȥ�����ҿո�
 */
String.prototype.trim = function(){
	return this.replace(/(^\s*)|(\s*$)/g, "");
};
function mytrim(s){
	return s.replace(/(^\s*)|(\s*$)/g, "");
}
/**
 * �ж��Ƿ�Ϊ���ַ���
 */
function isEmpty(s){
	if(mytrim(s+'').length<=0){
		return true;
	}
	return false;
}

/**
 * �ж��Ƿ�������
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
 * ��֤�������nΪС�������� s ҪУ����ַ�����n С��λ�� true ��֤�ɹ� ��֤ʧ��
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
	// var matchs='\^\\+\?([1-9]{1}[0-9]\*|0)(\\.[0-9]{1,'+n+'})\?\$';
	var matchs='\^\\+\?([0-9]\*|0)(\\.[0-9]{1,'+n+'})\?\$';
	var patrn = new RegExp(matchs,"ig");
	if (patrn.exec(s)) {
		return true ;
	}
	return false;
}
/**
 * �ж��ַ����Ƿ��������ַ���� true ��������� false ��ֻ���������
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
 * �ж��Ƿ�Ϊ�������� true ���������� false ������������
 */
function isDate(s){
	if(isEmpty(s)){
		return true;
	}
	if(/^\d{4}[\/-]\d{1,2}[\/-]\d{1,2}$/.test(s)){
		var r = s.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 
		if(r==null)return false; 
		var d= new Date(r[1], r[3]-1, r[4]); 
		return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]);
	}else{
		return false;
	}
}
/**
 * �Ƿ�������ʱ������ true ������ʱ������ false ��������ʱ������
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
 * ��֤d1��d2���������ĸ��� true d1С�ڵ���d2 false d1����d2���������ڸ�ʽ����ȷ
 */
function compareDate(d1,d2) {
	if(isEmpty(d1) || isEmpty(d2)){
		return true;
	}
	return ((new Date(d1.replace(/-/g,"\/"))) <= (new Date(d2.replace(/-/g,"\/"))));   
}
/**
 * ��֤d1��d2���������ĸ��� true d1С�ڵ���d2 false d1����d2���������ڸ�ʽ����ȷ
 */
function compareWithToday(d1) {
	if(isEmpty(d1)){
		return true;
	}
	return ((new Date(d1.replace(/-/g,"\/"))) <= (new Date()));   
}
/**
 * �ж��Ƿ���������ַ� s ����֤���ַ��� ch true���ֲ��������ַ�����false ���������ַ��� vec �����ַ����飬�����������еĲ��������ַ�
 * true �����������ַ� false ���������ַ�
 */
function isStr(s,ch,vec){
	if(isEmpty(s)){
		return true;
	}
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
/**
 * �ж����֤�Ϸ�
 */
  var aCity={11:"����",12:"���",13:"�ӱ�",14:"ɽ��",15:"���ɹ�",21:"����",22:"����",23:"������",31:"�Ϻ�",32:"����",33:"�㽭",34:"����",35:"����",36:"����",37:"ɽ��",41:"����",42:"����",43:"����",44:"�㶫",45:"����",46:"����",50:"����",51:"�Ĵ�",52:"����",53:"����",54:"����",61:"����",62:"����",63:"�ຣ",64:"����",65:"�½�",71:"̨��",81:"���",82:"����",91:"����"}; 

function isCardID(sId){
	var iSum=0 ;
	var info="" ;
	if(!/^\d{17}(\d|x)$/i.test(sId)) {
		return false;
	}// "����������֤���Ȼ��ʽ����";
	sId=sId.replace(/x$/i,"a"); 
	if(aCity[parseInt(sId.substr(0,2))]==null) {
		return false;
	}// "������֤�����Ƿ�";
	sBirthday=sId.substr(6,4)+"-"+Number(sId.substr(10,2))+"-"+Number(sId.substr(12,2)); 
	var d=new Date(sBirthday.replace(/-/g,"/")) ;
	if(sBirthday!=(d.getFullYear()+"-"+ (d.getMonth()+1) + "-" + d.getDate())){
		return false;
	}// "���֤�ϵĳ������ڷǷ�";
	for(var i = 17;i>=0;i --) iSum += (Math.pow(2,i) % 11) * parseInt(sId.charAt(17 - i),11) ;
	if(iSum%11!=1) {
		return false;
	}// "����������֤�ŷǷ�";
	return true;// aCity[parseInt(sId.substr(0,2))]+","+sBirthday+","+(sId.substr(16,1)%2?"��":"Ů")
}
/**
 * ������֤ var re =
 * /^\w{1,15}(?:@(?!-))(?:(?:[a-z0-9-]*)(?:[a-z0-9](?!-))(?:\.(?!-)))+[a-z]{2,4}$/;
 */
  function email(txtName){
	   var re = /^\w{1,64}(?:@(?!-))(?:(?:[a-z0-9-]*)(?:[a-z0-9](?!-))(?:\.(?!-)))+[a-z]{2,8}$/;
	   var flag = false;
		if (!re.exec(txtName)) {
                flag= false;
            }else{
                flag= true;
            }
		return flag;
	}

  function checkphone(txtPhone){
	var re = /^[0-9\-\,]*$/; // /^(1[3,5,8][0-9]{9}|[0-9]{7,8}|0[0-9]{2,3}\-[0-9]{7,8}(\-[0-9]{0-4})?)$/;
	return re.exec(txtPhone)
  }
  
 /**
	 * Ǯ��ȥ�����ţ�С��������Զ���ȫ
	 */
  
  function removeStr(userID){
	var str = document.getElementById(userID).value;
	 str  = str.replace(/,/gi,"");
 	 str  = str.replace(/��/gi,"");
 	  var after ="";
 	 if(str.indexOf(".")>0){
 	 after = str.substring(str.indexOf("."),str.length);
 	}
if(!after==""){
 	 if(after.length<2){
 	  str = str+"00";
	   }else if(after.length==2){
		   str = str +"0";
	   }else{
	   str =str;
    	}
 	 }
 	document.getElementById(userID).value=str;
}
   /**
	 * Ǯ,ǧ��λ��ʽ��
	 */
function transStr(userID){
var str = document.getElementById(userID).value;
 var begin ="";
 var after ="";
 var l;
 var str2="";
 if(str.indexOf(".")<0)str=str+".00";
 if(str.indexOf(".")>0){
  begin = str.substring(0,str.indexOf("."));
  after = str.substring(str.indexOf("."),str.length);
 }else{
  begin = str;
 }
 l=begin.length/3;
  if(l>1){
   for(var i=0;i<l;){
    str2=","+begin.substring(begin.length-3,begin.length)+str2;
    begin=begin.substring(0,begin.length-3);
    l=begin.length/3;
   }
   if(after.length<3){
    str2=begin+str2+after+"0";
   }else{
    str2=begin+str2+after
    }
  str = str2.substring(1);
  }else{
   if(after.length<3){
   str = str+"0";
   }else{
   str =str;
    }
  }
  document.getElementById(userID).value=str;
}
  /**
	 * ��ʽ������
	 */
function formatNumber(number,pattern){
    var str            = number.toString();
    var strInt;
    var strFloat;
    var formatInt;
    var formatFloat;
    if(/\./g.test(pattern)){
        formatInt        = pattern.split('.')[0];
        formatFloat        = pattern.split('.')[1];
    }else{
        formatInt        = pattern;
        formatFloat        = null;
    }
    if(/\./g.test(str)){
        if(formatFloat!=null){
            var tempFloat    = Math.round(parseFloat('0.'+str.split('.')[1])*Math.pow(10,formatFloat.length))/Math.pow(10,formatFloat.length);
            strInt        = (Math.floor(number)+Math.floor(tempFloat)).toString();              
            strFloat    = /\./g.test(tempFloat.toString())?tempFloat.toString().split('.')[1]:'0';          
        }else{
            strInt        = Math.round(number).toString();
            strFloat    = '0';
        }
    }else{
        strInt        = str;
        strFloat    = '0';
    }
    if(formatInt!=null){
        var outputInt    = '';
        var zero        = formatInt.match(/0*$/)[0].length;
        var comma        = null;
        if(/,/g.test(formatInt)){
            comma        = formatInt.match(/,[^,]*/)[0].length-1;
        }
        var newReg        = new RegExp('(\\d{'+comma+'})','g');
        if(strInt.length<zero){
            outputInt        = new Array(zero+1).join('0')+strInt;
            outputInt        = outputInt.substr(outputInt.length-zero,zero)
        }else{
            outputInt        = strInt;
        }
        var
        outputInt            = outputInt.substr(0,outputInt.length%comma)+outputInt.substring(outputInt.length%comma).replace(newReg,(comma!=null?',':'')+'$1')
        outputInt            = outputInt.replace(/^,/,'');
        strInt    = outputInt;
    }
    if(formatFloat!=null){
        var outputFloat    = '';
        var zero        = formatFloat.match(/^0*/)[0].length;
        if(strFloat.length<zero){
            outputFloat        = strFloat+new Array(zero+1).join('0');
            // outputFloat = outputFloat.substring(0,formatFloat.length);
            var outputFloat1    = outputFloat.substring(0,zero);
            var outputFloat2    = outputFloat.substring(zero,formatFloat.length);
            outputFloat        = outputFloat1+outputFloat2.replace(/0*$/,'');
        }else{
            outputFloat        = strFloat.substring(0,formatFloat.length);
        }
        strFloat    = outputFloat;
    }else{
        if(pattern!='' || (pattern=='' && strFloat=='0')){
            strFloat    = '';
        }
    }
    return strInt+(strFloat==''?'':'.'+strFloat);
}


// ��֤�ַ�������
function chkLen(v,c){
if(v==null||v==""||c==null||c==""){
  return false;
}else if(Trim(v).length<parseInt(c)){
  return false;
}else{
  return true;
}
}
/* ��ʾ�ַ����ĳ��� */
function Trim(str)
{
    return LTrim(RTrim(str));
}

function LTrim(str)
{
    var i;
    for(i=0;i<str.length;i++)
    {
        if(str.charAt(i)!=" "&&str.charAt(i)!="��")break;
    }
    str=str.substring(i,str.length);
    return str;
}
function RTrim(str)
{
    var i;
    for(i=str.length-1;i>=0;i--)
    {
        if(str.charAt(i)!=" "&&str.charAt(i)!="��")break;
    }
    str=str.substring(0,i+1);
    return str;
}

// ���û���ȷ�ϲ���
function userConfirm(){
  if(confirm("��ȷʵҪ������Щ������"))
  { 
    return true;
  }else{
    return false;
  }
}
/**
 * ��֤������ȷ�������Ƿ�һ��
 * 
 * @param password1
 * @param password2
 * @return
 */
function checkPW(password1, password2) {
	 var re=/^[0-9]+[a-zA-Z]+[a-zA-Z0-9]*|[a-zA-Z]+[0-9]+[a-zA-Z0-9]*$/;// ��֤����ֻ������ĸ������
	if (Trim(password1) == "") {
		alert("���벻��Ϊ�գ�");
		frm.password.focus();
		return false;
	} else if (Trim(password1) == "") {
		alert("ȷ�����벻��Ϊ�գ�");
		frm.password1.focus();
		return false;
	} else if (password1 != password2) {
		alert("������ȷ�����벻һ�£�");
		frm.password.focus();
		return false;
	} else if (!chkLen(Trim(password1), 6)) {
		alert("���벻��С��6λ��");
		return false;
	} else if (Trim(password1).length>16) {
		alert("���벻�ܴ���16λ��");
		return false;
	}else if (!isStr(password1,false,new Array('-','_','!','@','#','$','%','^','&','*',',','.','?'))) {
		alert("������������ַ������������룡");
		return false;
	}
	
	/*
	 * else if (password1.replace(re, "")) { alert("����ֻ��Ϊ��ĸ�����ֵ���ϣ�"); return
	 * false; }
	 */
	
	else {
		if (userConfirm()) {
			return true;
		} else {
			return false;
		}
	}
}
/**
 * ��֤�����ʽ
 * 
 * @param password
 * @return
 */
function checkPassword(password) {
	 // var
		// re=/^[0-9]+[a-zA-Z]+[a-zA-Z0-9]*|[a-zA-Z]+[0-9]+[a-zA-Z0-9]*$/;//��֤����ֻ������ĸ������
	if (Trim(password) == "") {
		return "���벻��Ϊ��";
	}  else if (!chkLen(Trim(password), 6)) {
		return "���벻��С��6λ��";
	} else if (Trim(password).length>16) {
		return "���벻�ܴ���16λ��";
		// password.replace(re, "")
	}else if (!isStr(password,false,new Array('-','_','!','@','#','$','%','^','&','*',',','.','?'))) {
		return "������������ַ������������룡";
	}else{
		return "";
	}
}
/** -------------�ı�����ɫ�ı�-------------- */
function onfocusText(text){
		text.style.borderColor="blue";
}
function onblurText(text){
		text.style.borderColor="";
}
document.onreadystatechange = subSomething;//��ҳ�����״̬�ı��ʱ��ִ���������.
function subSomething() 
{ 
	if (document.readyState == "complete") // ��ҳ�����״̬Ϊ��ȫ����ʱ����
	{
		var list=document.getElementsByTagName("input"); 
		if(list.length>0){
			for(var i=0;i<list.length;i++) 
			{ 
			// �ж��Ƿ�Ϊ�ı���
			if(list[i].type=="text") 
			{ 
				if(list[i].onfocus==null){
					list[i].onfocus=function(){
						onfocusText(this);
						};
					
				}
				if(list[i].onblur==null){
					list[i].onblur=function(){
						onblurText(this);
					};
				}
				
				
			} 
			}
		}
		
		
	}
}

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


function checkQueryBirtDate(startDate,endDate) {
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

/**
 * ������С�������� �ж��������ֵ�λ��
 * @param str
 * 			�����ַ���
 * @param n
 * 			λ��
 * @return
 * 
 */
function intByNum(str,n){
	var flag=false;
	if(str.length>0){
		var strs=new Array();
		strs=str.split(".");
		if(strs.length==1){
			if(str.length>n){
				flag=true;
			}
		}else if(strs.length==2){
			var s=strs[0];
			if(s.length>n){
				flag=true;
			}
		}
	}
	return flag;
}

Date.prototype.format = function(format)
{
    var o = {
    "M+" : this.getMonth()+1, //month
    "d+" : this.getDate(),    //day
    "h+" : this.getHours(),   //hour
    "m+" : this.getMinutes(), //minute
    "s+" : this.getSeconds(), //second
    "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
    "S" : this.getMilliseconds() //millisecond
    }
    if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
    (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)if(new RegExp("("+ k +")").test(format))
    format = format.replace(RegExp.$1,
    RegExp.$1.length==1 ? o[k] :
    ("00"+ o[k]).substr((""+ o[k]).length));
    return format;
}
var Util={
		/**
		 * ȥ����ո�
		 * @param str
		 * @return String
		 */
		LTrim:function (str){
			if(str){
				var i;
				for(i=0;i<str.length;i++){
					if(str.charAt(i)!=" "&&str.charAt(i)!="��")break;
				}
				str=str.substring(i,str.length);
			}
			return str;
		},
		/**
		 * ȥ���ҿո�
		 * @param str
		 * @return String
		 */
		RTrim:function (str){
			if(str){
				var i;
				for(i=str.length-1;i>=0;i--){
					if(str.charAt(i)!=" "&&str.charAt(i)!="��")break;
				}
				str=str.substring(0,i+1);
			}
			return str;
		},
		/**
		 * ȥ�����ҿո�
		 * @param str
		 * @return String
		 */
		Trim:function (str){
			return this.LTrim(this.RTrim(str));
		},
		/**
		 * �ж��Ƿ�Ϊ���ַ���
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
		 * �ж��Ƿ�������
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
		 * ��֤�������nΪС�������� num ҪУ����ַ�����n С��λ��
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
		 * �ж��ַ����Ƿ��������ַ����
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
		 * �ж��Ƿ�Ϊ��������
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
		 * �Ƿ�������ʱ������
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
		 * ��֤d1�Ƿ�С�ڵ���d2
		 * @param d1,d2
		 * @return boolean
		 * @throw TypeException
		 */
		CompareDate:function (d1,d2) {
			if(this.ISEmpty(d1) || this.ISEmpty(d2)){
				return true;
			}
			if(!this.ISDate(d1) || !this.ISDate(d2)){
				throw new TypeError("���ڸ�ʽΪ yyyy-MM-dd �� yyyy/MM/dd");
			}
			return ((new Date(d1.replace(/-/g,"\/"))) <= (new Date(d2.replace(/-/g,"\/"))));   
		},
		/**
		 * �ж����֤�Ϸ�
		 * @param sId
		 * @return boolean
		 */
		aCity:{11:"����",12:"���",13:"�ӱ�",14:"ɽ��",15:"���ɹ�",21:"����",22:"����",23:"������",31:"�Ϻ�",32:"����",33:"�㽭",34:"����",35:"����",36:"����",37:"ɽ��",41:"����",42:"����",43:"����",44:"�㶫",45:"����",46:"����",50:"����",51:"�Ĵ�",52:"����",53:"����",54:"����",61:"����",62:"����",63:"�ຣ",64:"����",65:"�½�",71:"̨��",81:"���",82:"����",91:"����"}, 
		ISCardID:function (sId){
			var iSum=0 ;
			var info="" ;
			if(!/^\d{17}(\d|x)$/i.test(sId)) {
				return false;
			}//"����������֤���Ȼ��ʽ����"; 
			sId=sId.replace(/x$/i,"a"); 
			if(this.aCity[parseInt(sId.substr(0,2))]==null) {
				return false;
			}//"������֤�����Ƿ�"; 
			sBirthday=sId.substr(6,4)+"-"+Number(sId.substr(10,2))+"-"+Number(sId.substr(12,2)); 
			var d=new Date(sBirthday.replace(/-/g,"/")) ;
			if(sBirthday!=(d.getFullYear()+"-"+ (d.getMonth()+1) + "-" + d.getDate())){
				return false;
			}//"���֤�ϵĳ������ڷǷ�"; 
			for(var i = 17;i>=0;i --) iSum += (Math.pow(2,i) % 11) * parseInt(sId.charAt(17 - i),11) ;
			if(iSum%11!=1) {
				return false;
			}//"����������֤�ŷǷ�"; 
			return true;//aCity[parseInt(sId.substr(0,2))]+","+sBirthday+","+(sId.substr(16,1)%2?"��":"Ů") 
		},
		/**
		 * ������֤
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
		 * ��֤�绰����
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
		 * �ж��Ƿ���������ַ�
		 * @param s ����֤���ַ���
		 * @param ch true���ֲ��������ַ�����false ���������ַ���
		 * @param vec �����ַ����飬�����������еĲ��������ַ�
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
		 * Ǯ��ȥ�����ţ�С��������Զ���ȫ
		 * @param money
		 * @return String
		 */
		RemoveComma:function (money){
			if(this.ISEmpty(money)){
				return "";
			}
			money=this.Trim(money);
			money  = money.replace(/,/gi,"");
			money  = money.replace(/��/gi,"");
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
		 * Ǯ,ǧ��λ��ʽ��
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
		 * ����iframe����Ӧ
		 * @param ifmid
		 * @param autoHeight
		 * @param autoWidth
		 * @param addHeight
		 */
		IFrameAuto:function (ifmid,autoHeight,autoWidth,addHeight){
			var h = 30;
			var minheight=563;
			var ifm= document.getElementById(ifmid);
			if(ifm != null) {
				if(autoHeight){
					ifm.height=0;//��ֹ��߶����������߶ȣ�����ÿ�ζ��Ƚ��߶�����
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
		 * encodeת��
		 * @param str
		 * @return string
		 */
		Encode:function (str){
			return encodeURI(str,"utf-8");
		},
		/**
		 * ��ԭ encode
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
				c=new RegExp("\\{"+b+"\\}");//��ʽΪ{n}
				e=e?e.replace(c,d[b]):a.replace(c,d[b]);//���e��Ϊ����e��{n}�滻Ϊd�ĵ�n��Ԫ�ط���eΪa��{n}�滻Ϊd�ĵ�n��Ԫ��
			}
			return e;
		}
	}