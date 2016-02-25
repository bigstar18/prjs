function isDateFormat(datestring)
	isDateFormat = isDate(datestring)
end function

function trimString(stringvalue)
	trimString = trim(stringvalue)
end function

'* description: 对日期加减月份
'* datevalue:需要加减月份的日期，如2004-09-21
'* monthvalue:加减的月份,如10，120，-13
'* 返回加减月份后的日期字符串，如"2014-09-21"
'* ***[需要注意的是必须加入此目录下的Date.js脚本，因为用到了formatDate方法]
'*/
function goMon(datevalue, monthvalue)   
    goMon = formatDate(CStr(DateAdd("m", CInt(monthvalue), CDate(datevalue))))
end function
 
'* description: 对日期加减月份
'* datevalue:需要加减月份的日期，如200409
'* monthvalue:加减的月份,如1，120，-13
'* 返回加减月份后的日期字符串，如"200410"
'* ***[需要注意的是必须加入此目录下的Date.js脚本，因为用到了formatDate方法]
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

'* description: 取某数的百分位，100以下的去掉
'* value:150.55
'* 返回的是，100
'* ***[需要注意的是必须加入此目录下的global.js脚本，因为用到了formatFloat(str,dec)方法]
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
