  /**
   * 去除字符串左边空格
   * @param source 源字符串
   * @return
   */
function LTrim(source)
{
	if (source.length == 0)
	{
	  return "";
	}
	if (source.charAt(0) != ' ' && source.charAt(0) != '　')
	{
	  return source;
	}
	else
	{
	  var i;
	  for (i = 0; i < source.length; i++)
	  {
		if (source.charAt(i) != ' ' && source.charAt(0) != '　')
		{
		  break;
		}
	  }
	  return source.substr(i);
	}
  }

  /**
   * 去除字符串右边空格
   * @param source
   * @return
   */
function RTrim(source)
{
	if (source.length == 0)
	{
	  return "";
	}
	if (source.charAt(source.length - 1) != ' ' && source.charAt(source.length - 1) != '　')
	{
	  return source;
	}
	else
	{
	  var i;
	  for (i = source.length - 1; i > 0; i--)
	  {
		if (source.charAt(i) != ' ' && source.charAt(0) != '　')
		{
		  break;
		}
	  }
	  return source.substr(0, i + 1);
	}
  }

  /**
   * 去除字符串前后空格
   * @param source
   * @return
   */
function Trim(source)
{
	return LTrim(RTrim(source));
}

function repeatString(str, number)
{
	if (number < 0)
	{
	  return "";
	}
	var s = "";
	var i;
	for (i = 0; i < number; i++)
	{
	  s += str;
	}
	return s;
}

function contains(arr, str)
{
	if (arr.length <= 0)
	{
		return false;
	}
	for(i = 0; i < arr.length; i++)
	{
		if (arr[i] == str)
		{
			return true;
		}
	}
	return false;
}

/*
身份证验证
返回值：0--身份证号为空；1--长度不对；2--格式不对
*/
function validateSfzh(obj)
{
  var ret;
  var s_sfzh = Trim(obj.value);
  if(s_sfzh == "")
  {
	ret = 0;
  }
  else
  {
	if(s_sfzh.length == 15)
	{
	    var d_sfzh = new String(parseFloat(s_sfzh));
	    if(d_sfzh.length != 15)  //防止有字母
	    {
	      ret = 2; 
	    }
		else if( (s_sfzh.substr(8,2) >= "01") && (s_sfzh.substr(8,2) <= "12") && (s_sfzh.substr(10,2) >= "01") &&(s_sfzh.substr(10,2) <= "31") )
		{
		}
		else
		{
		  ret = 2;
		}
	}
	else if(s_sfzh.length == 18)
	{
	    var d_sfzh = new String(parseFloat(s_sfzh.substr(0,s_sfzh.length-1)));
	    if(d_sfzh.length != 17)  //防止有字母
	    {
	      ret = 2; 
	    }
		else if( (s_sfzh.substr(10,2) >= "01") && (s_sfzh.substr(10,2) <= "12") && (s_sfzh.substr(12,2) >= "01") &&(s_sfzh.substr(12,2) <= "31") )
		{
		}
		else
		{
		  ret = 2;
		}
	}
	else
	{
      ret = 1;
	}
  }
  return ret;
}
//
function checkIdcard(idcard){ 
idcard = Trim(idcard);
if(idcard == "")
{
  return "";
}  
var Errors=new Array( 
"验证通过!", 
"身份证号码位数不对!", 
"身份证号码出生日期超出范围或含有非法字符!", 
"身份证号码校验错误!", 
"身份证地区非法!" 
); 
var area={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"}  
var idcard,Y,JYM; 
var S,M; 
var idcard_array = new Array(); 
idcard_array = idcard.split(""); 
//地区检验 
if(area[parseInt(idcard.substr(0,2))]==null) return Errors[4]; 
//身份号码位数及格式检验 
switch(idcard.length){ 
case 15: 
if ( (parseInt(idcard.substr(6,2))+1900) % 4 == 0 || ((parseInt(idcard.substr(6,2))+1900) % 100 == 0 && (parseInt(idcard.substr(6,2))+1900) % 4 == 0 )){ 
ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;//测试出生日期的合法性 
} else { 
ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;//测试出生日期的合法性 
} 
if(ereg.test(idcard)) return ""; 
else return Errors[2]; 
break; 
case 18: 
//18位身份号码检测 
//出生日期的合法性检查  
//闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9])) 
//平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8])) 
if ( parseInt(idcard.substr(6,4)) % 4 == 0 || (parseInt(idcard.substr(6,4)) % 100 == 0 && parseInt(idcard.substr(6,4))%4 == 0 )){ 
ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;//闰年出生日期的合法性正则表达式 
} else { 
ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;//平年出生日期的合法性正则表达式 
} 
if(ereg.test(idcard)){//测试出生日期的合法性 
//计算校验位 
S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7 
+ (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9 
+ (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10 
+ (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5 
+ (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) * 8 
+ (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4 
+ (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) * 2 
+ parseInt(idcard_array[7]) * 1  
+ parseInt(idcard_array[8]) * 6 
+ parseInt(idcard_array[9]) * 3 ; 
Y = S % 11; 
M = "F"; 
JYM = "10X98765432"; 
M = JYM.substr(Y,1);//判断校验位 
M1 = "F"; 
JYM1 = "10x98765432"; 
M1 = JYM1.substr(Y,1);//判断校验位 
if(M == idcard_array[17] || M1 == idcard_array[17]) return ""; //检测ID的校验位 
else return Errors[3]; 
} 
else return Errors[2]; 
break; 
default: 
return Errors[1]; 
break; 
} 
} 


function setReadOnly(obj)
{
  obj.readOnly = true;
  obj.style.backgroundColor = "#C0C0C0";
}
function setReadWrite(obj)
{
  obj.readOnly = false;
  obj.style.backgroundColor = "white";
}
