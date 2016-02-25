function isDateFormat(datestring)
	isDateFormat = isDate(datestring)
end function

function trimString(stringvalue)
	trimString = trim(stringvalue)
end function

'* description: �����ڼӼ��·�
'* datevalue:��Ҫ�Ӽ��·ݵ����ڣ���2004-09-21
'* monthvalue:�Ӽ����·�,��10��120��-13
'* ���ؼӼ��·ݺ�������ַ�������"2014-09-21"
'* ***[��Ҫע����Ǳ�������Ŀ¼�µ�Date.js�ű�����Ϊ�õ���formatDate����]
'*/
function goMon(datevalue, monthvalue)   
    goMon = formatDate(CStr(DateAdd("m", CInt(monthvalue), CDate(datevalue))))
end function
 
'* description: �����ڼӼ��·�
'* datevalue:��Ҫ�Ӽ��·ݵ����ڣ���200409
'* monthvalue:�Ӽ����·�,��1��120��-13
'* ���ؼӼ��·ݺ�������ַ�������"200410"
'* ***[��Ҫע����Ǳ�������Ŀ¼�µ�Date.js�ű�����Ϊ�õ���formatDate����]
'*/
function goMonth(ymvalue, monthvalue)   
    if isyyyyMM(ymvalue) = false then
      goMonth = ""
      exit function
    end if
    dim datevalue 
    datevalue = left(ymvalue,4) & "-" & right(ymvalue,2) & "-" & "01"
    datevalue = formatDate(CStr(DateAdd("m", CInt(monthvalue), CDate(datevalue))))
    goMonth = left(datevalue,4) & mid(datevalue,6,2)
end function

function isyyyyMM(ym)
    if len(trim(ym)) <> 6 then
      isyyyyMM = false
      exit function
    end if
    dim datestring
    datestring = left(ym,4) & "-" & right(ym,2) & "-" & "01"
	isyyyyMM = isDate(datestring)
end function

'* description: ȡĳ���İٷ�λ��100���µ�ȥ��
'* value:150.55
'* ���ص��ǣ�100
'* ***[��Ҫע����Ǳ�������Ŀ¼�µ�global.js�ű�����Ϊ�õ���formatFloat(str,dec)����]
'*/
function get100(value)  
    dim nstring,twoN
    nstring = formatFloat(cStr(value),2)
    nstring = left(nstring,len(nstring)-3)
    if len(nstring) < 3 then
      get100 = 0
    else
      twoN = right(nstring,2)
      get100 = cLng(nstring) - cLng(twoN)
    end if
end function
