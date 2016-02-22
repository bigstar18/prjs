document.onreadystatechange = subSomething;// 当页面加载状态改变的时候执行这个方法.
function subSomething() {
	if (document.readyState == "complete") // 当页面加载状态为完全结束时进入
	{
		var regexTests = document.getElementsByTagName("button");
		var len = regexTests.length;

		//		var key = rightMap.keySet();
		//        for (var i in key){
		//           alert(map.get(key[i]));
		//        }
		// 用正则表达式
		// var regex = /^add/;//以add开头
		/**
		 * for (i = 0; i < len; i++) { var str =regexTests[i].id;
		 * if(regex.test(str)) { alert("正则表达式:"+regexTests[i].value); } }
		 */
		// 字符串方法
		var sign=false;
		for (i = 0; i < len; i++) {
			var flag=false;
			var str = regexTests[i].id + '';
			//			if (str.substr(0, "add".length) == "add")// 以add开头
         
			//			{
			//				if (!sessionAdd) {
			//					regexTests[i].disabled = true;
			//				}
			//			}
			//			if (str.substr(0, "delete".length) == "delete")// 以delete开头
			//			{
			//				if (!sessionDelete) {
			//					regexTests[i].disabled = true;
			//				}
			//			}
			//			if (str.substr(0, "update".length) == "update")// 以update开头
			//			{
			//				if (!sessionUpdate) {
			//					regexTests[i].disabled = true;
			//				}
			//			}
			if(str&&str!=''){
			if (rightMap) {
				for ( var o in rightMap) {
					if (str.substr(0, o.length) == o) {
						//alert('str:'+str+'   o:'+o);
						 if(navigator.userAgent.indexOf("MSIE")>0) {
				 			 }else{
				 		      regexTests[i].setAttribute("type","button");
				 			 }
						if (!rightMap[o]) {
							//regexTests[i].style.display = 'none';
						}else{
						//	alert('sign:'+sign);
							sign=true;
							flag=true;
							//regexTests[i].style.display = '';
						}
					}
				}
				if(flag){
					regexTests[i].style.display = '';
				}else{
					regexTests[i].style.display = 'none';
				}
			}
			}
		}
		
//		var sign=false;
//		for(var o in rightMap){
//			if(rightMap[o]){
//				sign=true;
//				alert(o);
//			}
//		}
		if(!sign){
			var elements = getAll("obj.");
			RemoveControl(elements);
			//elementsDisabled(elements);
			var elements = getAll("specialforAudit.");
			RemoveControl(elements);
		 //elementsDisabled(elements);
		}
		/**
		 * 只对特殊页面处理
		 * @param {Object} str
		 * @return {TypeName} 
		 */
		for(var o in rightMap){
			if(!rightMap['update']){
				var elements = getAll("special.");
				RemoveControl(elements);
				//elementsDisabled(elements);
			}
		}
		
		
		 var collFrm = document.all.tags("FORM");
	    if ( collFrm )
	    {
	        for( var i = 0 ; i < collFrm.length ; i++ )
	        {
	        	var au='111111';
	        	if(typeof(AUsessionId)!= "undefined"){
	        		au=AUsessionId;
	        	}
	            var e= document.createElement("input");
		        e.setAttribute("type","hidden");
		        e.setAttribute("id",'AUsessionId');
		        e.setAttribute("name",'AUsessionId');
		        e.setAttribute("value",au);
		        collFrm[i].appendChild(e);
	        }
	        
	    }
		
	}
}

/**
 * 取得界面上所有 name 以 str 开头的元素
 */
function getAll(str){
	var result = new Array();
	var elements = document.all;
	var n=0;
	for(var i=0;i<elements.length;i++){
		var element = elements(i);
		if(element.name != null &&typeof(element.name)=="string"&& element.name.substr(0,str.length)==str){
			result[n++]=element;
		}
	}
	return result;
}

/**
 * 将所有元素灰掉，不可选
 */
 function elementsDisabled(elements){
	var length = elements.length;
	for(var i=0;i<length;i++){
		var element = elements[i];
		element.readonly=true;
		element.disabled='disabled';
	}
 }
 function RemoveControl(elements)
 {
  var arrObj = new Array();
  
  var count = elements.length;
  for(var i=0;i<count;i++)
  {
   if(elements[i] == undefined)
    continue;
    
   var obj = document.createElement('label');
   switch(elements[i].type)
   {
    case "text" : 
      obj.style.width=elements[i].style.width;
      obj.className="word_warpbreak";
      obj.setAttribute("innerHTML",elements[i].value);
      break;
    case "textarea" :
      obj.style.width=elements[i].style.width;
      obj.className="word_warpbreak";
      obj.setAttribute("innerHTML",elements[i].innerHTML);
      elements[i].innerHTML = '';
      break;
    case "select-one" :
      for(var j=0;j<elements[i].length;j++)
      {
       if(elements[i][j].selected)
       {
        obj.style.width=elements[i].style.width;
        obj.className="word_warpbreak";
        obj.setAttribute("innerHTML",elements[i][j].text);
        break;
       }
      }
      elements[i].options.length = 0;
      break;      
   }
   //elements[i].parentNode.appendChild(obj);
   elements[i].parentNode.insertBefore(obj,elements[i]);
   arrObj[arrObj.length] = elements[i];
  }
  
  //删除表单原控件
  for(var i=0;i<arrObj.length;i++)
  {
   arrObj[i].removeNode();
  }
 }

