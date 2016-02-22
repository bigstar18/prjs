
//弹出信息框且取焦错误输入框
function warningValue(msg, obj) {
        obj.value="";
	alert(msg);
	if ( obj.disabled != true && obj.type!="hidden") {
		obj.focus();
		//obj.select();
	}
}

//清除前后空格
function trim ( str ) {
	regExp = /\S/
	
	if ( regExp.test(str) == false )
		return "";
	
	regExp = /(^\s*)(.*\S)(\s*$)/
	regExp.exec(str);
	
	return RegExp.$2;
}


//检验日期格式
function checkDate( aYearValue, aMonthValue, aDayValue )
{
    if(aYearValue == null || aYearValue.length != 4)
    	return false;
    var yearValue = parseInt(aYearValue);
    var monthValue; 
    if( aMonthValue.substring(0,1) == "0")
    	monthValue = parseInt( aMonthValue.substring(1,2) );
    else monthValue = parseInt(aMonthValue);
    var dayValue;
    if( aDayValue.substring(0,1) == "0")
    	dayValue = parseInt( aDayValue.substring(1,2) );
    else dayValue = parseInt(aDayValue);
		
	if ( isNaN(yearValue) || isNaN(monthValue) || isNaN(dayValue) ) {
		return false;
	}
		
	monthValue--;
		
	if ( yearValue >= 1900 && yearValue < 2000 )
		yearValue = yearValue-1900;
	
	var dateValue = new Date( yearValue, monthValue, dayValue );
	//alert(dateValue);
	if ( dateValue.getDate() != dayValue || 
	     dateValue.getMonth() != monthValue ||
	     dateValue.getYear() != yearValue )
		return false;
	
	return true;
}

function parseDate( aDateStr, aDateSeq )
{
    var aYearValue = "";
    var aMonthValue = "";
    var aDayValue = "";
    var aHourValue = "";
    var aMinuteValue = "";
    var aSecondValue = "";

	if ( aDateSeq == "" ) {	
		aYearValue = aDateStr.substring(0,4);
		aMonthValue = aDateStr.substring(4,6);
		aDayValue = aDateStr.substring(6,8);
	}
	else if ( aDateStr.indexOf(" ") < 0 ) {
		var regPattern = "(" + "^[0-9]+"+")("+aDateSeq +")";
		regExp = new RegExp(regPattern);
		if ( regExp.exec(aDateStr) != null )
			aYearValue = RegExp.$1;

		regPattern = "("+aDateSeq+")("+"[0-9]+"+")("+aDateSeq+")"
		regExp = new RegExp(regPattern);
		if ( regExp.exec(aDateStr) != null )
			aMonthValue = RegExp.$2;

		regPattern = "("+aDateSeq+")("+"[0-9]+$"+")";
		regExp = new RegExp(regPattern);
		if ( regExp.exec(aDateStr) != null )
			aDayValue = RegExp.$2;
	}
	else {
		var regPattern = "(^[0-9]+)-([0-9]+)-([0-9]+) ([0-9]+):([0-9]+)";
		regExp = new RegExp(regPattern);
		if ( regExp.exec(aDateStr) != null ) {
			aYearValue = RegExp.$1;
			aMonthValue = RegExp.$2;
			aDayValue = RegExp.$3;
			aHourValue = RegExp.$4;
			aMinuteValue = RegExp.$5;
			aSecondValue = 0;
		}
	}
	
	if (aHourValue == "")
		return new Date( aYearValue, aMonthValue-1, aDayValue );
	else
		return new Date( aYearValue, aMonthValue-1, aDayValue, aHourValue, aMinuteValue, aSecondValue );
}



//清除选择框中选中的项
function removeOption( name )
{
	for ( i = 0; i < name.options.length; i++)
	{
		if ( name.options[i].selected == true )
			name.remove( name.selectedIndex );
	}
}

//增加选择框中的项
function addOption( name, value, text )
{
	var oOption = document.createElement( "OPTION" );
	oOption.text = text;
	oOption.value = value;
	name.add( oOption );
}
//设置选择框中的选择项
function setOption( srcObj, aryOption )
{
	var aryOptValue = aryOption[0];
	var aryOptText = aryOption[1];
	
	var srcObj = document.getElementById( srcObj );
	
	for ( i = 0; i < aryOptValue.length; i++ )
	{
		addOption( srcObj, aryOptValue[i], aryOptText[i] );
	}
}


//对一个表格中的多选框执行全选/全反选的操作
//tblObj:表格对象
//childName:checkbox对象的ID
function selectAll( tblObj , childName )
{
  var objCheck = event.srcElement ;
  var collCheck;
  
  if ( tblObj != null )
  	var collCheck = tblObj.children[1].all.namedItem(childName);
  else
  	var collCheck = document.all.namedItem(childName);
  	
  if(collCheck)
  {
	  if(!collCheck.length)
	  	collCheck.checked = (objCheck.checked==true)? true:false;
	  else
	  {
	  	for(var i=0;i < collCheck.length;i++ )
		{
			collCheck[i].checked = (objCheck.checked==true)? true:false;
		}
	  }  
  }
  else
  {
  	objCheck.disabled = true;
  }
}

//确定一个表格中的有被选中的记录
//tblObj:表格对象
//childName:checkbox的ID
function isSelNothing( tblObj , chkId )
{
	var collCheck = tblObj.children[1].all.namedItem(chkId);
    if(!collCheck)
        return -1;
    if(collCheck.checked)
	{
		if(collCheck.checked == true)
		 return false
		else
		  return true
	}
	if( collCheck.length < 1 )
	{
		return -1;
	}
	var noSelect = true;
	if(collCheck.checked == true)
	{
			noSelect = false;
	}
	else
	{
		for(var i=0;i < collCheck.length;i++ )
		{
			if( collCheck[i].checked == true )
			{
				noSelect = false;			
			}
		
		}
	}
	return noSelect;	
}


//从模式对话框页面获取数据(单选模式)
function getReturnValue_S( spanObj , sUrl , sFeatures , callback )
{
	if(!sFeatures)
		sFeatures = "dialogWidth:600px;dialogHeight:460px;center:yes;dialogHide: no ;edge:raised; resizable:no;status:no;unadorned:no;scroll:yes; ";
	
	var objArr = new Array();
	var objColl = spanObj.all;
	var j = 0;
	for(var i=0;i<objColl.length;i++)
	{
		if( objColl[i].mapId )
		{
			var obj = new Object();
			obj.id = objColl[i].id;
			obj.mapId = objColl[i].mapId;
			obj.text = "";
			if(objColl[i].tagName == "SPAN")
			{
				obj.value = objColl[i].innerText;	
			}
			if(objColl[i].tagName == "INPUT")
			{
				obj.value = objColl[i].value;			
			}
			if(objColl[i].tagName == "SELECT")
			{
				obj.value = objColl[i].value;
				obj.text = objColl[i].options[objColl[i].selectedIndex].value;	
			}
			if(objColl[i].tagName == "TEXTAREA")
			{
				obj.value = objColl[i].innerText;	
			}								
			objArr[j] = obj;
			j++;
		}	
	}
	var sReturnValue = window.showModalDialog( sUrl , objArr , sFeatures );
	if(sReturnValue)
	{
		setReturnObj ( spanObj, sReturnValue );
		if ( callback )
			callback();
	}
}

function getReturnObj( obj, returnObj )
{
	var valueColl = obj.all;
	for( var i = 0 ; i < valueColl.length ; i++ )
	{
		 if(valueColl[i].mapId)
		 {
	 		 for( var j = 0 ; j < returnObj.length ; j++ )
			 {
			 	if( returnObj[j].mapId == valueColl[i].mapId )
			 	{
			 			if( valueColl[i].tagName == "SPAN" )
			 				returnObj[j].value =  valueColl[i].innerText;
			 			if( valueColl[i].tagName == "INPUT" )
			 				returnObj[j].value =  valueColl[i].value;
			 			if( valueColl[i].tagName == "SELECT" )
			 			{
			 				if(valueColl[i].value)
			 				{
				 				returnObj[j].value =  valueColl[i].value;
				 				returnObj[j].text =  valueColl[i].options[valueColl[i].selectedIndex].text;			 				
			 				}
			 			}
			 			if( valueColl[i].tagName == "TEXTAREA" )
			 				returnObj[j].value =  valueColl[i].innerText;
			 				
			 	}
			 }
		 }
	}
	return returnObj;
}

function setReturnObj( obj, returnObj )
{
	var valueColl = obj.all;
	for( var i = 0 ; i < valueColl.length ; i++ )
	{
		 if(valueColl[i].mapId)
		 {
	 		 for( var j = 0 ; j < returnObj.length ; j++ )
			 {
			 	if( returnObj[j].mapId == valueColl[i].mapId )
			 	{
 					setTagValue( returnObj[j] , valueColl[i] )	 				
			 	}
			 }
		 }
	}
	return returnObj;
}

function setTagValue( srcObj , tagObj)
{
	if( tagObj.tagName == "SPAN" || tagObj.tagName == "TEXTAREA" ||  tagObj.tagName == "DIV")
	{
		if(tagObj.mapText)
			tagObj.innerText =  srcObj.text;
		else
			tagObj.innerText =  srcObj.value;
											
	}
	if( tagObj.tagName == "INPUT"   )
	{
		if(tagObj.mapText)
			tagObj.value =  srcObj.text;
		else
			tagObj.value =  srcObj.value;
	}
	if( tagObj.tagName == "SELECT")
	{
		var collopt = tagObj.options
		if(srcObj.value)
		{
			if(tagObj.mapText)
			{

			tagObj.value =  srcObj.text;
			}
			else
			{
				
			
				tagObj.value =  srcObj.value;	
			}
				
		}

	}
}

function gotoPage(sUrl)
{
	window.location = sUrl;
}


function deleteRec(frm_delete,tableList,checkName)
{
	if(isSelNothing(tableList,checkName) == -1)
	{
		alert ( "没有可以操作的数据！" );
		return false;
	}
	if(isSelNothing(tableList,checkName))
	{
		alert ( "请选择需要操作的数据！" );
		return false;
	}
	if(confirm("您确实要操作选中数据吗？"))
	{
		frm.opt.value="del";
		frm.submit();
		//return true;
	}
	else
	{
		return false;
	}
}

function selectTr()
{
	var objTr=event.srcElement;
	while(objTr.tagName!="TR")
	{
		objTr=objTr.parentNode;
	}
	
	var objRadio = objTr.all.namedItem("selectRadio");
	if(objRadio){
		objRadio.checked = true;
		var valueColl = objTr.all;
		for( var i = 0 ; i < valueColl.length ; i++ )
		{
			 if(valueColl[i].mapId)
			 {
		 		 for( var j = 0 ; j < returnObj.length ; j++ )
					 {
					 	if( returnObj[j].mapId == valueColl[i].mapId )
					 	{
					 		
					 			if( valueColl[i].tagName == "SPAN" )
					 				returnObj[j].value =  valueColl[i].innerText;
					 			if( valueColl[i].tagName == "INPUT" )
					 				returnObj[j].value =  valueColl[i].value;
					 	}
					 }
			 }
		}
	}
	var objTbl = objTr
	while(objTbl.tagName!="TABLE")
	{
		objTbl=objTbl.parentNode;
	}
	
	for ( var i=0; i < objTbl.children[1].children.length ; i++) //>
	{
			objTbl.children[1].children[i].className = "";
	}
	
	objTr.className = "hilite";
}

function LTrim(str)
{
    var i;
    for(i=0;i<str.length;i++)
    {
        if(str.charAt(i)!=" "&&str.charAt(i)!="　")break;
    }
    str=str.substring(i,str.length);
    return str;
}
function RTrim(str)
{
    var i;
    for(i=str.length-1;i>=0;i--)
    {
        if(str.charAt(i)!=" "&&str.charAt(i)!="　")break;
    }
    str=str.substring(0,i+1);
    return str;
}
function Trim(str)
{
    return LTrim(RTrim(str));
}
  
 //让用户做确认操作
 function userConfirm(){
   if(confirm("您确实要操作这些数据吗？"))
   { 
     return true;
   }else{
     return false;
   }
 }

//验证字符串长度
function chkLen(v,c){
  if(v==null||v==""||c==null||c==""){
    return false;
  }else if(Trim(v).length<parseInt(c)){
    return false;
  }else{
    return true;
  }
}
