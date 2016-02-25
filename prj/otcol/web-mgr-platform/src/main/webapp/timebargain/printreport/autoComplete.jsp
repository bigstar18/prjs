<script>
/*
 * 交易商列表ajax自动补全功能
 */
 var tempValue="";
function autoComplete(t){
	 temp=t;
	 var urlStr;
	 var brokerId=document.getElementById("brokerId").value;
	 if(temp==1){
	 	urlStr="getFirm.jsp?firmId="+txtInput.value+"&brokerId="+brokerId;
	 }
	 if(temp==2){
	 	urlStr="getFirm.jsp?firmId="+txtInputEnd.value+"&brokerId="+brokerId;
	 }
	 if(temp==1 && event.keyCode !=13 && event.keyCode !=38 && event.keyCode !=40 ){
	 	tempValue=txtInput.value;
	 }
	 if(temp==2 && event.keyCode !=13 && event.keyCode !=38 && event.keyCode !=40 ){
	 	tempValue=txtInputEnd.value;
	 }
     process(temp,urlStr);
}   
function process(temp,urlStr){
	setPosition();
     //如果按下 向上, 向下 或 回车
     if (event.keyCode == 38 || event.keyCode == 40 || event.keyCode == 13){ 
        //选择当前项 
        selItemByKey(temp);
     }else{
     //向服务器发送请求 
       //如果值为空 
       if(temp==1){
        	if (txtInput.value == ""){
        		divContent.style.display='none'; 
        		return;
        	} 
        }
        if(temp==2){
        	if(txtInputEnd.value==""){
        		divContentEnd.style.display='none';
        		return;
        	}
        }
        //恢复下拉选择项为 -1 
        currentIndex = -1; 
        //开始请求
        requestObj = CreateAjaxObject();
        requestObj.open('GET',urlStr, true);
		requestObj.onreadystatechange = function displayResult(){
			 //显示结果 
             if (requestObj.readyState == 4)
             {
             	if(requestObj.status==200){
                     showData(temp);
                     if(temp==1){
                     	divContent.style.display = "";
                     }
                     if(temp==2){
                     	divContentEnd.style.display = "";
                     }
                }
             } 
        } 
	    requestObj.send(null);
    } 
}
/*
*  ajax自动补全
*/
function CreateAjaxObject(){
  if (window.XMLHttpRequest&&!(window.ActiveXObject)) 
	{ 	
		req = new XMLHttpRequest(); 
	}
	else if (window.ActiveXObject)
	{	
		req = new ActiveXObject("Microsoft.XMLHTTP"); 
	} 
   return req;
}
		//输入信息的文本框
        var txtInput;
        var txtInputEnd;
        //下拉表当前选中项的索引 
        var currentIndex = -1;
        var requestObj;
        var fId =[];
        
        //初始化参数,和下拉表位置
        function initPar()
        {
             txtInput = document.getElementById("zStart");
             txtInputEnd=document.getElementById("zEnd");
             //设置下拉表 相对于 文本输入框的位置 
             setPosition();
        } 
        
        //设置下拉表 相对于 文本输入框的位置
        function setPosition()
        {
            var width = txtInput.offsetWidth;
            var left = getLength("offsetLeft",1)+2;
            var top = getLength("offsetTop",1) + txtInput.offsetHeight;
			divContent.style.left = left;
			divContent.style.top = top; 
			divContent.style.width = width + "px";
			
			var widthEnd = txtInputEnd.offsetWidth;
            var leftEnd = getLength("offsetLeft",2)+2;
            var topEnd = getLength("offsetTop",2) + txtInputEnd.offsetHeight;
			divContentEnd.style.left = leftEnd;
			divContentEnd.style.top = topEnd; 
			divContentEnd.style.width = widthEnd + "px";
        } 
        
        function initList()
        {	
            divContent.style.display='none'; 
            divContent.innerHTML = "";
            
            divContentEnd.style.display='none';
            divContentEnd.innerHTML="";
            
            currentIndex = -1;
            
        }
        
       //获取对应属性的长度 
        function getLength(attr,num)
        {
            var offset = 0;
            var item;
            if(num==1){
            	item= txtInput;
            }
            if(num==2){
            	item=txtInputEnd;
            }
            while (item)
            {
                offset += item[attr];
                item = item.offsetParent;
            } 
            return offset; 
        }     
          
        //显示服务器返回的结果 ,并形成下拉表
        function showData(temp)
        {
        	
             //获取数据 
             var data = requestObj.responseText;
             //显示数据
            //var pictures = null;
            	//pictures = xmlData.getElementsByTagName("item");
            	//alert(pictures);
			   //for (var i=0;i<pictures.length;i++)
			   //{
			     //fId[i]= pictures[i].getElementsByTagName("fId")[0].firstChild.nodeValue;
			   //}
			   
			   var res='<table id="tblContent" width="100%" style="background-color:#ffffff">';
			   var txtValue;
			   if(temp==1)
			      txtValue = txtInput.value;
			   if(temp==2){
			   	  txtValue=txtInputEnd.value;
			   }
			   //var len = pictures.length;
			   var datas=data.split("-");
			   	for (var i=0;i<datas.length-1;i++)
				   {	
			   			var str=datas[i].split("=")[1].substring(0,datas[i].split("=")[1].length-1);
				   		//if(fId[i].substr(0,txtValue.length).toLowerCase()==txtValue.toLowerCase()){
				   			res+="<tr>";
						    res+="<td onclick='selValue("+temp+")' style=cursor:hand onmouseover=\"clearColor();this.parentElement.style.backgroundColor='#ccc000';\" onmouseout=\"clearColor()\">"
						    res+=str+"</td></tr>";
					  // }			
				   }
			   res+="</table>";
             //显示转后后的结果
             if(temp==1){
             	divContent.innerHTML = res;
             }
             if(temp==2){
             	divContentEnd.innerHTML=res;
             }
        } 
                
        //通过键盘选择下拉项 
        function selItemByKey(temp)
        {
            //下拉表 
            var tbl = document.getElementById("tblContent"); 
            if (!tbl)
            {
                return; 
            } 
            //下拉表的项数
            var maxRow = tbl.rows.length; 
            //向上 
            if (event.keyCode == 38 && currentIndex >= 0)
            {
                 currentIndex--;
                 if(currentIndex==-1){
                    if(temp==1){
                 		txtInput.value=tempValue;
                 	}else if(temp==2){
                 		txtInputEnd.value=tempValue;
                 	}
                 }
            } 
            else if (event.keyCode == 38 && currentIndex == -1)
            {
                 currentIndex=maxRow-1;
            } 
            //向下 
            else if (event.keyCode == 40 && currentIndex <= maxRow-1)
            {
                 currentIndex++;
                 if(currentIndex==maxRow){
                    if(temp==1){
                 		txtInput.value=tempValue;
                 	}else if(temp==2){
                 		txtInputEnd.value=tempValue;
                 	}
                 }
            }
            else if (event.keyCode == 40 && currentIndex==maxRow){
            	 currentIndex=0;
            }
            //回车 
            else if (event.keyCode == 13)
            {
                selValue(temp);
                return;
            } 
            
            clearColor();
            if(temp==1){
            	txtInput.value = tbl.rows[currentIndex].innerText;
            }
            if(temp==2){
            	txtInputEnd.value=tbl.rows[currentIndex].innerText;
            } 
            //设置当前项背景颜色为blue 标记选中 
            tbl.rows[currentIndex].style.backgroundColor = "#ccc000"; 
        } 
        
        //清除下拉项的背景颜色 
        function clearColor()
        {
             var tbl = document.getElementById("tblContent");
             for (var i = 0; i < tbl.rows.length; i++)
             {
                    tbl.rows[i].style.backgroundColor = ""; 
             } 
        } 
        
        function clearTab() 
        {
        	//清除数据
        	var tbl = document.getElementById("tblContent");
			for(var i=0;i<tbl.rows.length;i++)
			{						
				tbl.deleteRow(i);
			}
        }
        
        //选择下拉表中当前项的值 ,用于按回车或鼠标单击选中当前项的值
        function selValue(temp)
        {
            if (event.keyCode != 13&&temp==1)
            { 
                var text = event.srcElement.innerText;
                txtInput.value = text; 
            } 
            if(event.keyCode!=13&&temp==2){
            	var text=event.srcElement.innerText;
            	txtInputEnd.value=text;
            }
            initList(); 
        } 
       //文本框失去焦点时 设置下拉表可见性 
        function setDisplay()
        {
            //获取当前活动td的表格 
            if (document.activeElement.tagName == "TD")
            {
                 var tbl = document.activeElement.parentElement.parentElement.parentElement; 
                //如果不是下拉表,则隐藏 下拉表 
                if (tbl.id != "tblContent")
                {
                    initList();
                }
                return;
            } 
            
            initList();
            
        } 
</script>


