           var dispalyContent = new Array('<font color=yellow>��</font>','<font color=green>��</font>');
			var displayCount = 0;
			function display()
			{
				netStatus.innerHTML = dispalyContent[displayCount%2];
				displayCount++;
			}
			
			//ҳ����ת
			function goToPage(page) 
			{
				clearTimeout(timer);//��ֹͣ��ǰ������
				if(htFilter.ContainsKey("pageIndex"))//�������pageIndex��ѯ�������������÷������
					htFilter.SetValue("pageIndex",page);
				else 
					htFilter.Add("pageIndex",page);
				sendRequest();	
			}
			//����timer
			var timer;
			//ˢ��ʱ����
			var timerInterval=5000;
			//�����ַ
			var requestUrl="";
			//��ǰ�����ַ
			var currentUrl = "";
			//��ѯ����
			var  htFilter=new HashTable(); 
			
			
			var XMLHttpReq; 
			//����XMLHttpRequest���� 
			function createXMLHttpRequest() 
			{ 
				if(window.XMLHttpRequest)
				{ //Mozilla ����� 
					XMLHttpReq = new XMLHttpRequest(); 
				} 
				else if (window.ActiveXObject) 
				{ // IE����� 
					try 
					{ 
						XMLHttpReq = new ActiveXObject("Msxml2.XMLHTTP"); 
					} 
					catch (e)
				    { 
						try 
						{ 
							XMLHttpReq = new ActiveXObject("Microsoft.XMLHTTP"); 
						} catch (e) {} 
					} 
				} 
			} 
			
			//���������ַ����������
			function setRequestUrl(url)
			{
				requestUrl=url;
				
				sendRequest();
			}
		
			
			
			//���������� 
			function sendRequest()
			{ 	
				currentUrl=requestUrl;
				for(var i in htFilter.Items) //������ѯ����hashtable��ӵ�get������
        		{ 
					if(currentUrl.indexOf("?")>0){
						currentUrl+="&"+htFilter.Items[i].Key+"="+htFilter.Items[i].Value;
					}else{
						currentUrl+="?"+htFilter.Items[i].Key+"="+htFilter.Items[i].Value;
					}
             		//alert(currentUrl); 
        		}
				createXMLHttpRequest(); 
				XMLHttpReq.open("GET", currentUrl, true); 
				XMLHttpReq.onreadystatechange = processResponse;//ָ����Ӧ���� 
				XMLHttpReq.setRequestHeader("If-Modified-Since","0");    
				XMLHttpReq.send(null); // �������� 
			} 

			// ��������Ϣ���� 
			function processResponse()
			{ 
				
				if (XMLHttpReq.readyState == 4) 
				{ // �ж϶���״̬ 
					
					if (XMLHttpReq.status == 200) 
					{ // ��Ϣ�Ѿ��ɹ����أ���ʼ������Ϣ 
						//updateTable();
						refreshTable();
						display();
						timer=setTimeout("sendRequest()", timerInterval); 
					} 
					else
					{ //ҳ�治���� 
						window.alert("���������ҳ�����쳣��"); 
					} 
				} 
			} 
			
			function refreshTable()
			{
				var doc = XMLHttpReq.responseXML;
				//��ȡˢ��ʱ���� ���С��һ����Ĭ������Ϊһ��
				if(doc.getElementsByTagName("timerInterval")[0]!=null && doc.getElementsByTagName("timerInterval")[0].firstChild.data>1000)
				{
					timerInterval=doc.getElementsByTagName("timerInterval")[0].firstChild.data;
				}
				else
				{
					timerInterval=1000;
				}
				
				//��ǰҳ
				var pageIndex = doc.getElementsByTagName("pageIndex")[0].firstChild.data;
				//��ҳ��
				var totalPage = doc.getElementsByTagName("totalPage")[0].firstChild.data;
				//������
				var totalCount = doc.getElementsByTagName("totalCount")[0].firstChild.data;
				
				var prePage = parseInt(pageIndex) - 1;
				var nextPage = parseInt(pageIndex) + 1;
				
				
				var datalist = doc.getElementsByTagName("data");
					
				var tableObj=tb;  
  				clearTable(tableObj);  
  				
  				
  				var str=new Array(); 	
				var colsCount=tbHead.rows(0).cells.length;//��ͷ����
				
  				for(var j=0;j<datalist.length;j++)
				{
					if(datalist[j].hasChildNodes())
					{				
						var data = datalist[j];
					    var rownum = data.getAttribute("rownum");
					    if(rownum!=null) str[0]=rownum;
					    else str[0]="--";
						for(var i=1;i<colsCount;i++)
						{
							var colAbbr=tbHead.rows(0).cells[i].abbr.toUpperCase();//��Ԫ��ժҪ
							if(colAbbr!=null && colAbbr.length>0)
							{
								var node=data.getElementsByTagName(colAbbr)[0];//��ȡ�ڵ�����ΪcolAbbr��ֵ
								if(node!=null && node.hasChildNodes())
									str[i]=node.firstChild.data;
								else str[i]='--';
							}
							else str[i]='--';	
						}
					}	
					
					addnew(str,tableObj);
					
				} 
				
				document.getElementById("pageIndex").innerHTML=pageIndex;
				document.getElementById("totalPage").innerHTML=totalPage;
				document.getElementById("totalCount").innerHTML=totalCount;
				if(pageIndex>1)
				{
					document.getElementById("firstPage").innerHTML="<span style='cursor:hand' onClick=goToPage(1)>��һҳ</span>";
				}
				else
				{
					document.getElementById("firstPage").innerHTML="<span><font color='#C8C8C8'>��һҳ</font></span>";
				}
				if(prePage>0)
				{
					document.getElementById("prePage").innerHTML="<span style='cursor:hand' onClick=\"goToPage('"+prePage+"')\">��һҳ</span>";
				}
				else
				{
					document.getElementById("prePage").innerHTML="<span><font color='#C8C8C8'>��һҳ</font></span>";
				}
				if(nextPage<=totalPage)
				{
					document.getElementById("nextPage").innerHTML="<span style='cursor:hand' onClick=\"goToPage('"+nextPage+"')\">��һҳ</span>";
				}
				else
				{
					document.getElementById("nextPage").innerHTML="<span><font color='#C8C8C8'>��һҳ</font></span>";
				}
				if(pageIndex<totalPage)
				{
					document.getElementById("lastPage").innerHTML="<span style='cursor:hand' onClick=\"goToPage('"+totalPage+"')\">��ĩҳ</span>";
				}
				else
				{
					document.getElementById("lastPage").innerHTML="<span><font color='#C8C8C8'>��ĩҳ</font></span>";
				}		
			}
			

			//����������    		  
			function clearTable(tableObj)
			{    	  
				for(var i=tableObj.rows.length-1;i>=0;i--)    	  
				{      
				   tableObj.deleteRow(i);    		  
				}    		  
			}    
			
			//���
			function addnew(str,tableObj)
			{
				var newRow = tableObj.insertRow(tableObj.rows.length);					
		        newRow.className="list1";
		        
		        
		        //������ɵ��ַ���
		        var primaryKeyStr="";
		        //���� ҳ�涨������� �ԡ������ָ�
		       
		        if(document.getElementById("primaryKey"))
		        {
			        var primaryKey=document.getElementById("primaryKey").innerHTML;
			        var arr=new Array(); 	
			        arr=primaryKey.split(";");
			        for(var i=0;i<arr.length;i++)
			        {
			        	//������ ����С����������ĳ���
			        	if(!isNaN(arr[i]) && arr[i]<str.length)
			        	{
			        		primaryKeyStr+=str[arr[i]];
			        	}
			        }
		        }
		        
		        //����onclick�¼�
		        newRow.attachEvent("onclick",function(){changeActiveRow(newRow,primaryKeyStr);});
		       	
		      
 ����������������   //�����ַ����������0 && ��ǰ�����ַ������� && �����ַ������ڵ�ǰ�����ַ��� 
		        if(primaryKeyStr.length>0 && curPrimaryKeyStr &&��curPrimaryKeyStr==primaryKeyStr)
		        {
		        	currentActiveRow=newRow;  
		        	newRow.style.backgroundColor=selRowColor; 
		        	//alert(primaryKeyStr);
		        }
		        
		        
				var newCell;
				//newCell.className ="style1";
				for(i=0;i<str.length;i++)
				{			
				       newCell =newRow.insertCell(i)
				       newCell.align = "center"; 
					   newCell.width =tbHead.rows(0).cells[i].width;
					   newCell.style.display =tbHead.rows(0).cells[i].style.display;
				       newCell.innerHTML=str[i];
				       
			    }
			}
			
			
		
		  
		 //�ı���ѡ���е���ɫ
		  var   currentActiveRow; 
		  //������ɵ��ַ��� 
		  var   curPrimaryKeyStr; 
		  //ѡ���е���ɫ
		  var selRowColor="Blue";
		  function   changeActiveRow(obj,primaryKeyStr)   
		  {   
		  	 curPrimaryKeyStr= primaryKeyStr;
		  	 if(currentActiveRow)   currentActiveRow.style.backgroundColor="";   
		 	 currentActiveRow=obj;   
		 	 currentActiveRow.style.backgroundColor=selRowColor;
		  } 
		  
		 
		 	
		//����	
		function dtquery()   
		{   
			o   =   event.srcElement;  
			if(o.tagName!="TD")   return;
	        var orderName=document.getElementById("orderName").innerHTML;
	        var orderType=document.getElementById("orderType").innerHTML;
	        var orderTypes = orderType.split(",");
	        orderType = orderTypes[0];
	        if(orderName==o.abbr)
	        {
	        	var direction="";
	        	if(orderType=="DESC")
	        	{
	        	    direction="��";
	        	    document.getElementById("orderType").innerHTML="ASC";//������������
	        	}
	        	else if (orderType=="ASC")
	        	{
	        		direction="��";
	        		document.getElementById("orderType").innerHTML="DESC";//������������
	        	}
	        	//���������������ͷ
	        	var text=o.innerText;
	        	o.innerText = text.substring(0,text.length-1)+direction;
	        }
	        else
	        {
	        	//����һ�������е��������ͷ��ͷȥ��
	        	for(var i=0;i<tbHead.rows(0).cells.length;i++)
	        	{		
	        		if(tbHead.rows(0).cells[i].abbr==orderName)
	        		{
	        			var text=tbHead.rows(0).cells[i].innerText;
	        			tbHead.rows(0).cells[i].innerText=text.substring(0,text.length-1);
	        			break;
	        		}
	        	}
	        	document.getElementById("orderName").innerHTML=o.abbr;//������������
	        	document.getElementById("orderType").innerHTML="DESC"//������������
	        	//����������ͷ
	        	o.innerText=o.innerText+"��";
	        }
			clearTimeout(timer);//��ֹͣ��ǰ������
			//�����ַ���
			var orderby=document.getElementById("orderName").innerHTML+"  "+document.getElementById("orderType").innerHTML;
			
			if(htFilter.ContainsKey("orderby"))//�������oderby��ѯ�������������÷������
				htFilter.SetValue("orderby",orderby);
			else 
				htFilter.Add("orderby",orderby);
					
			//��������
			sendRequest();	
			//alert(o.parentElement.rowIndex   +o.cellIndex+":"+   "="   +o.innerText)   
		}
		
		 
		
		//JavaScript hashTable
		function HashTable() 
        {         
                this.Items=[]; 
                this.Count=function(){return this.Items.length;};        //����                 
                this.DictionaryEntry=function(key,value) 
                { 
                        this.Key=key||null; 
                        this.Value=value||null; 
                } 
                this.Add=function(key,value){
                    if(this.ContainsKey(key)){
                        return false;
                    }else{
                        this.Items.push(new this.DictionaryEntry(key,value));
                        return true;
                        }
                    } 
                this.Clear=function(){this.Items.length=0;} 
                this.Remove=function(key) 
                { 
                        var index=this.GetIndexWithKey(key); 
                        if(index>-1)
                            this.Items.splice(index,1); 
                } 
                this.GetValue=function(key) 
                { 
                        var index=this.GetIndexWithKey(key); 
                        if(index>-1)
                            return this.Items[index].Value; 
                } 
				this.SetValue=function(key,value) 
                { 
                        var index=this.GetIndexWithKey(key); 
                        if(index>-1)
						{
							this.Items[index].Value=value;
							return true;
						}
                        return false; 
                }
                this.ContainsKey=function(key) 
                { 
                        if(this.GetIndexWithKey(key)>-1)
                            return true; 
                        return false; 
                } 
                this.ContainsValue=function(value) 
                { 
                        if(this.GetIndexWithValue(value)>-1)
                            return true; 
                        return false; 
                } 
                this.Keys=function() 
                { 
                        var iLen=this.Count(); 
                        var resultArr=[]; 
                        for(var i=0;i<iLen;i++)
                            resultArr.push(this.Items[i].Key); 
                        return resultArr; 
                } 
                this.Values=function() 
                { 
                        var iLen=this.Count(); 
                        var resultArr=[]; 
                        for(var i=0;i<iLen;i++) 
                            resultArr.push(this.Items[i].Value); 
                        return resultArr; 
                } 
                this.IsEmpty=function(){return this.Count()==0;} 
                this.GetIndexWithKey=function(key) 
                { 
                        var iLen=this.Count(); 
                        for(var i=0;i<iLen;i++)
                            if(this.Items[i].Key===key)
                                return i; 
                        return -1; 
                } 
                this.GetIndexWithValue=function(value) 
                { 
                        var iLen=this.Count(); 
                        for(var i=0;i<iLen;i++)
                            if(this.Items[i].Value===value)
                                return i; 
                        return -1; 
                } 
        } 
        //var my=new HashTable(); 
        //my.Add("name","blueKnight"); 
        //my.Add("age",'24'); 
        //my.Add("sex","boy"); 
		//alert(my.GetValue("sex"));//����ӹ��ķ���fals
		//my.SetValue("sex","gielre");	
		// alert(my.GetValue("sex"));//����ӹ��ķ���fals
       // alert(my.Add("sex","sex"));//����ӹ��ķ���false
       // alert(my.Count());
       // alert(my.ContainsValue("boy"));
        //alert(my.GetValue("name"))
       // for(var i in my.Items) //����
       // { 
          //   alert("Key:"+my.Items[i].Key+"--Value:"+my.Items[i].Value); 
       // } 
      //  my.Remove("age"); //ɾ��
       // alert(my.Keys()+'-'+my.Values()+'\n\r');  