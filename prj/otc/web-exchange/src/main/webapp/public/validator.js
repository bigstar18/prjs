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
 * ��֤�������nΪС�������� s ҪУ����ַ�����n С��λ��
 * true ��֤�ɹ�
 * ��֤ʧ��
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
	//var matchs='\^\\+\?([1-9]{1}[0-9]\*|0)(\\.[0-9]{1,'+n+'})\?\$';
	var matchs='\^\\+\?([0-9]\*|0)(\\.[0-9]{1,'+n+'})\?\$';
	var patrn = new RegExp(matchs,"ig");
	if (patrn.exec(s)) {
		return true ;
	}
	return false;
}
/**
 * �ж��ַ����Ƿ��������ַ����
 * true ���������
 * false ��ֻ���������
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
 * �ж��Ƿ�Ϊ��������
 * true ����������
 * false ������������
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
 * �Ƿ�������ʱ������
 * true ������ʱ������
 * false ��������ʱ������
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
 * ��֤d1��d2���������ĸ���
 * true d1С�ڵ���d2
 * false d1����d2���������ڸ�ʽ����ȷ
 */
function compareDate(d1,d2) {
	if(isEmpty(d1) || isEmpty(d2)){
		return true;
	}
	return ((new Date(d1.replace(/-/g,"\/"))) <= (new Date(d2.replace(/-/g,"\/"))));   
}
/**
 * ��֤d1��d2���������ĸ���
 * true d1С�ڵ���d2
 * false d1����d2���������ڸ�ʽ����ȷ
 */
function compareWithToday(d1) {
	if(isEmpty(d1)){
		return true;
	}
	return ((new Date(d1.replace(/-/g,"\/"))) <= (new Date()));   
}
/**
 * �ж��Ƿ���������ַ�
 * s ����֤���ַ���
 * ch true���ֲ��������ַ�����false ���������ַ���
 * vec �����ַ����飬�����������еĲ��������ַ�
 * true �����������ַ�
 * false ���������ַ�
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
	}//"����������֤���Ȼ��ʽ����"; 
	sId=sId.replace(/x$/i,"a"); 
	if(aCity[parseInt(sId.substr(0,2))]==null) {
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
}
/**������֤
 * var re = /^\w{1,15}(?:@(?!-))(?:(?:[a-z0-9-]*)(?:[a-z0-9](?!-))(?:\.(?!-)))+[a-z]{2,4}$/;
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
	var re = /^[0-9\-\,]*$/; ///^(1[3,5,8][0-9]{9}|[0-9]{7,8}|0[0-9]{2,3}\-[0-9]{7,8}(\-[0-9]{0-4})?)$/;
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
            //outputFloat        = outputFloat.substring(0,formatFloat.length);
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

