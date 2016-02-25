  /**
   * ȥ���ַ�����߿ո�
   * @param source Դ�ַ���
   * @return
   */
function LTrim(source)
{
	if (source.length == 0)
	{
	  return "";
	}
	if (source.charAt(0) != ' ' && source.charAt(0) != '��')
	{
	  return source;
	}
	else
	{
	  var i;
	  for (i = 0; i < source.length; i++)
	  {
		if (source.charAt(i) != ' ' && source.charAt(0) != '��')
		{
		  break;
		}
	  }
	  return source.substr(i);
	}
  }

  /**
   * ȥ���ַ����ұ߿ո�
   * @param source
   * @return
   */
function RTrim(source)
{
	if (source.length == 0)
	{
	  return "";
	}
	if (source.charAt(source.length - 1) != ' ' && source.charAt(source.length - 1) != '��')
	{
	  return source;
	}
	else
	{
	  var i;
	  for (i = source.length - 1; i > 0; i--)
	  {
		if (source.charAt(i) != ' ' && source.charAt(0) != '��')
		{
		  break;
		}
	  }
	  return source.substr(0, i + 1);
	}
  }

  /**
   * ȥ���ַ���ǰ��ո�
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
���֤��֤
����ֵ��0--���֤��Ϊ�գ�1--���Ȳ��ԣ�2--��ʽ����
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
	    if(d_sfzh.length != 15)  //��ֹ����ĸ
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
	    if(d_sfzh.length != 17)  //��ֹ����ĸ
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
"��֤ͨ��!", 
"���֤����λ������!", 
"���֤����������ڳ�����Χ���зǷ��ַ�!", 
"���֤����У�����!", 
"���֤�����Ƿ�!" 
); 
var area={11:"����",12:"���",13:"�ӱ�",14:"ɽ��",15:"���ɹ�",21:"����",22:"����",23:"������",31:"�Ϻ�",32:"����",33:"�㽭",34:"����",35:"����",36:"����",37:"ɽ��",41:"����",42:"����",43:"����",44:"�㶫",45:"����",46:"����",50:"����",51:"�Ĵ�",52:"����",53:"����",54:"����",61:"����",62:"����",63:"�ຣ",64:"����",65:"�½�",71:"̨��",81:"���",82:"����",91:"����"}  
var idcard,Y,JYM; 
var S,M; 
var idcard_array = new Array(); 
idcard_array = idcard.split(""); 
//�������� 
if(area[parseInt(idcard.substr(0,2))]==null) return Errors[4]; 
//��ݺ���λ������ʽ���� 
switch(idcard.length){ 
case 15: 
if ( (parseInt(idcard.substr(6,2))+1900) % 4 == 0 || ((parseInt(idcard.substr(6,2))+1900) % 100 == 0 && (parseInt(idcard.substr(6,2))+1900) % 4 == 0 )){ 
ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;//���Գ������ڵĺϷ��� 
} else { 
ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;//���Գ������ڵĺϷ��� 
} 
if(ereg.test(idcard)) return ""; 
else return Errors[2]; 
break; 
case 18: 
//18λ��ݺ����� 
//�������ڵĺϷ��Լ��  
//��������:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9])) 
//ƽ������:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8])) 
if ( parseInt(idcard.substr(6,4)) % 4 == 0 || (parseInt(idcard.substr(6,4)) % 100 == 0 && parseInt(idcard.substr(6,4))%4 == 0 )){ 
ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;//����������ڵĺϷ���������ʽ 
} else { 
ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;//ƽ��������ڵĺϷ���������ʽ 
} 
if(ereg.test(idcard)){//���Գ������ڵĺϷ��� 
//����У��λ 
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
M = JYM.substr(Y,1);//�ж�У��λ 
M1 = "F"; 
JYM1 = "10x98765432"; 
M1 = JYM1.substr(Y,1);//�ж�У��λ 
if(M == idcard_array[17] || M1 == idcard_array[17]) return ""; //���ID��У��λ 
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
