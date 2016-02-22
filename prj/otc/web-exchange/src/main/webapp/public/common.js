function closeDialog(parentReload){
	if(!parentReload)
		parentReload=0;
	if(isNaN(parentReload))
	{
	   	parentReload=parseInt(parentReload);
	}
	if(parentReload>0)
	{
	   window.returnValue = parentReload;
	   window.close();
	}else{
		if(parent)
		   parent.clearSubmitCount();
	}
	
}


function isFormChanged(fm,exceptObjName)
{
		if(fm==null) fm=document.forms[0];
		if(exceptObjName==null) exceptObjName=="";
		var selectObjs=fm.getElementsByTagName("SELECT");//For Select Obj
		for(var i=0;i<selectObjs.length;i++)
			{
				if((selectObjs[i].name=="")||(eval("/(^|,)"+selectObjs[i].name+"(,|$)/g").test(exceptObjName))) continue;
				for(var j=1;j<selectObjs[i].length;j++)
				{
					if(selectObjs[i].options[j].defaultSelected!=selectObjs[i].options[j].selected){
						return true;
					}
				}
			}
		
		var inputObjs=fm.getElementsByTagName("INPUT");//For Input Obj
		for(var i=0;i<inputObjs.length;i++)
		{
			if((inputObjs[i].name=="")||(eval("/(^|,)"+inputObjs[i].name+"(,|$)/g").test(exceptObjName))) continue;
			if((inputObjs[i].type.toUpperCase()=="TEXT")&&(inputObjs[i].defaultValue!=inputObjs[i].value)){
				return true;
			} else if(((inputObjs[i].type.toUpperCase()=="RADIO")||(inputObjs[i].type.toUpperCase()=="CHECKBOX"))
					&&(inputObjs[i].defaultChecked!=inputObjs[i].checked)) {
				return true;
			}
		}

		var textareaObjs=fm.getElementsByTagName("TEXTAREA");//For Textarea Obj
		for(var i=0;i<textareaObjs.length;i++)
		{
			
			if((textareaObjs[i].name=="")||(eval("/(^|,)"+textareaObjs[i].name+"(,|$)/g").test(exceptObjName))) continue;
			if(textareaObjs[i].defaultValue!=textareaObjs[i].value)
			return true;
		}
	return false;
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

