<script>
/*
 * �������б�ajax�Զ���ȫ����
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
     //������� ����, ���� �� �س�
     if (event.keyCode == 38 || event.keyCode == 40 || event.keyCode == 13){ 
        //ѡ��ǰ�� 
        selItemByKey(temp);
     }else{
     //��������������� 
       //���ֵΪ�� 
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
        //�ָ�����ѡ����Ϊ -1 
        currentIndex = -1; 
        //��ʼ����
        requestObj = CreateAjaxObject();
        requestObj.open('GET',urlStr, true);
		requestObj.onreadystatechange = function displayResult(){
			 //��ʾ��� 
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
*  ajax�Զ���ȫ
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
		//������Ϣ���ı���
        var txtInput;
        var txtInputEnd;
        //������ǰѡ��������� 
        var currentIndex = -1;
        var requestObj;
        var fId =[];
        
        //��ʼ������,��������λ��
        function initPar()
        {
             txtInput = document.getElementById("zStart");
             txtInputEnd=document.getElementById("zEnd");
             //���������� ����� �ı�������λ�� 
             setPosition();
        } 
        
        //���������� ����� �ı�������λ��
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
        
       //��ȡ��Ӧ���Եĳ��� 
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
          
        //��ʾ���������صĽ�� ,���γ�������
        function showData(temp)
        {
        	
             //��ȡ���� 
             var data = requestObj.responseText;
             //��ʾ����
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
             //��ʾת���Ľ��
             if(temp==1){
             	divContent.innerHTML = res;
             }
             if(temp==2){
             	divContentEnd.innerHTML=res;
             }
        } 
                
        //ͨ������ѡ�������� 
        function selItemByKey(temp)
        {
            //������ 
            var tbl = document.getElementById("tblContent"); 
            if (!tbl)
            {
                return; 
            } 
            //�����������
            var maxRow = tbl.rows.length; 
            //���� 
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
            //���� 
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
            //�س� 
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
            //���õ�ǰ�����ɫΪblue ���ѡ�� 
            tbl.rows[currentIndex].style.backgroundColor = "#ccc000"; 
        } 
        
        //���������ı�����ɫ 
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
        	//�������
        	var tbl = document.getElementById("tblContent");
			for(var i=0;i<tbl.rows.length;i++)
			{						
				tbl.deleteRow(i);
			}
        }
        
        //ѡ���������е�ǰ���ֵ ,���ڰ��س�����굥��ѡ�е�ǰ���ֵ
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
       //�ı���ʧȥ����ʱ ����������ɼ��� 
        function setDisplay()
        {
            //��ȡ��ǰ�td�ı�� 
            if (document.activeElement.tagName == "TD")
            {
                 var tbl = document.activeElement.parentElement.parentElement.parentElement; 
                //�������������,������ ������ 
                if (tbl.id != "tblContent")
                {
                    initList();
                }
                return;
            } 
            
            initList();
            
        } 
</script>


