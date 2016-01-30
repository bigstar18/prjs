           var dispalyContent = new Array('<font color=yellow>●</font>','<font color=green>●</font>');
			var displayCount = 0;
			function display()
			{
				netStatus.innerHTML = dispalyContent[displayCount%2];
				displayCount++;
			}
			
			//页面跳转
			function goToPage(page) 
			{
				clearTimeout(timer);//先停止以前的请求
				if(htFilter.ContainsKey("pageIndex"))//如果包含pageIndex查询条件则重新设置否则添加
					htFilter.SetValue("pageIndex",page);
				else 
					htFilter.Add("pageIndex",page);
				sendRequest();	
			}
			//发送timer
			var timer;
			//刷新时间间隔
			var timerInterval=5000;
			//请求地址
			var requestUrl="";
			//当前请求地址
			var currentUrl = "";
			//查询条件
			var  htFilter=new HashTable(); 
			
			
			var XMLHttpReq; 
			//创建XMLHttpRequest对象 
			function createXMLHttpRequest() 
			{ 
				if(window.XMLHttpRequest)
				{ //Mozilla 浏览器 
					XMLHttpReq = new XMLHttpRequest(); 
				} 
				else if (window.ActiveXObject) 
				{ // IE浏览器 
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
			
			//设置请求地址并请求数据
			function setRequestUrl(url)
			{
				requestUrl=url;
				
				sendRequest();
			}
		
			
			
			//发送请求函数 
			function sendRequest()
			{ 	
				currentUrl=requestUrl;
				for(var i in htFilter.Items) //遍历查询条件hashtable添加到get方法中
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
				XMLHttpReq.onreadystatechange = processResponse;//指定响应函数 
				XMLHttpReq.setRequestHeader("If-Modified-Since","0");    
				XMLHttpReq.send(null); // 发送请求 
			} 

			// 处理返回信息函数 
			function processResponse()
			{ 
				
				if (XMLHttpReq.readyState == 4) 
				{ // 判断对象状态 
					
					if (XMLHttpReq.status == 200) 
					{ // 信息已经成功返回，开始处理信息 
						//updateTable();
						refreshTable();
						display();
						timer=setTimeout("sendRequest()", timerInterval); 
					} 
					else
					{ //页面不正常 
						window.alert("您所请求的页面有异常。"); 
					} 
				} 
			} 
			
			function refreshTable()
			{
				var doc = XMLHttpReq.responseXML;
				//读取刷新时间间隔 如果小于一秒则默认设置为一秒
				if(doc.getElementsByTagName("timerInterval")[0]!=null && doc.getElementsByTagName("timerInterval")[0].firstChild.data>1000)
				{
					timerInterval=doc.getElementsByTagName("timerInterval")[0].firstChild.data;
				}
				else
				{
					timerInterval=1000;
				}
				
				//当前页
				var pageIndex = doc.getElementsByTagName("pageIndex")[0].firstChild.data;
				//总页数
				var totalPage = doc.getElementsByTagName("totalPage")[0].firstChild.data;
				//总条数
				var totalCount = doc.getElementsByTagName("totalCount")[0].firstChild.data;
				
				var prePage = parseInt(pageIndex) - 1;
				var nextPage = parseInt(pageIndex) + 1;
				
				
				var datalist = doc.getElementsByTagName("data");
					
				var tableObj=tb;  
  				clearTable(tableObj);  
  				
  				
  				var str=new Array(); 	
				var colsCount=tbHead.rows(0).cells.length;//表头列数
				
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
							var colAbbr=tbHead.rows(0).cells[i].abbr.toUpperCase();//单元格摘要
							if(colAbbr!=null && colAbbr.length>0)
							{
								var node=data.getElementsByTagName(colAbbr)[0];//获取节点名称为colAbbr的值
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
					document.getElementById("firstPage").innerHTML="<span style='cursor:hand' onClick=goToPage(1)>第一页</span>";
				}
				else
				{
					document.getElementById("firstPage").innerHTML="<span><font color='#C8C8C8'>第一页</font></span>";
				}
				if(prePage>0)
				{
					document.getElementById("prePage").innerHTML="<span style='cursor:hand' onClick=\"goToPage('"+prePage+"')\">上一页</span>";
				}
				else
				{
					document.getElementById("prePage").innerHTML="<span><font color='#C8C8C8'>上一页</font></span>";
				}
				if(nextPage<=totalPage)
				{
					document.getElementById("nextPage").innerHTML="<span style='cursor:hand' onClick=\"goToPage('"+nextPage+"')\">下一页</span>";
				}
				else
				{
					document.getElementById("nextPage").innerHTML="<span><font color='#C8C8C8'>下一页</font></span>";
				}
				if(pageIndex<totalPage)
				{
					document.getElementById("lastPage").innerHTML="<span style='cursor:hand' onClick=\"goToPage('"+totalPage+"')\">最末页</span>";
				}
				else
				{
					document.getElementById("lastPage").innerHTML="<span><font color='#C8C8C8'>最末页</font></span>";
				}		
			}
			

			//清除表格内容    		  
			function clearTable(tableObj)
			{    	  
				for(var i=tableObj.rows.length-1;i>=0;i--)    	  
				{      
				   tableObj.deleteRow(i);    		  
				}    		  
			}    
			
			//添加
			function addnew(str,tableObj)
			{
				var newRow = tableObj.insertRow(tableObj.rows.length);					
		        newRow.className="list1";
		        
		        
		        //主键组成的字符串
		        var primaryKeyStr="";
		        //主键 页面定义的主键 以“；”分割
		       
		        if(document.getElementById("primaryKey"))
		        {
			        var primaryKey=document.getElementById("primaryKey").innerHTML;
			        var arr=new Array(); 	
			        arr=primaryKey.split(";");
			        for(var i=0;i<arr.length;i++)
			        {
			        	//是数字 并且小于数据数组的长度
			        	if(!isNaN(arr[i]) && arr[i]<str.length)
			        	{
			        		primaryKeyStr+=str[arr[i]];
			        	}
			        }
		        }
		        
		        //增加onclick事件
		        newRow.attachEvent("onclick",function(){changeActiveRow(newRow,primaryKeyStr);});
		       	
		      
 　　　　　　　　   //主键字符串必须大于0 && 当前主键字符串存在 && 主键字符串等于当前主键字符串 
		        if(primaryKeyStr.length>0 && curPrimaryKeyStr &&　curPrimaryKeyStr==primaryKeyStr)
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
			
			
		
		  
		 //改变表格选中行的颜色
		  var   currentActiveRow; 
		  //主键组成的字符串 
		  var   curPrimaryKeyStr; 
		  //选中行的颜色
		  var selRowColor="Blue";
		  function   changeActiveRow(obj,primaryKeyStr)   
		  {   
		  	 curPrimaryKeyStr= primaryKeyStr;
		  	 if(currentActiveRow)   currentActiveRow.style.backgroundColor="";   
		 	 currentActiveRow=obj;   
		 	 currentActiveRow.style.backgroundColor=selRowColor;
		  } 
		  
		 
		 	
		//排序	
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
	        	    direction="↑";
	        	    document.getElementById("orderType").innerHTML="ASC";//设置排序类型
	        	}
	        	else if (orderType=="ASC")
	        	{
	        		direction="↓";
	        		document.getElementById("orderType").innerHTML="DESC";//设置排序类型
	        	}
	        	//重新设置排序方向箭头
	        	var text=o.innerText;
	        	o.innerText = text.substring(0,text.length-1)+direction;
	        }
	        else
	        {
	        	//将上一个排序中的排序方向箭头箭头去掉
	        	for(var i=0;i<tbHead.rows(0).cells.length;i++)
	        	{		
	        		if(tbHead.rows(0).cells[i].abbr==orderName)
	        		{
	        			var text=tbHead.rows(0).cells[i].innerText;
	        			tbHead.rows(0).cells[i].innerText=text.substring(0,text.length-1);
	        			break;
	        		}
	        	}
	        	document.getElementById("orderName").innerHTML=o.abbr;//设置排序名称
	        	document.getElementById("orderType").innerHTML="DESC"//设置排序类型
	        	//添加排序方向箭头
	        	o.innerText=o.innerText+"↓";
	        }
			clearTimeout(timer);//先停止以前的请求
			//排序字符串
			var orderby=document.getElementById("orderName").innerHTML+"  "+document.getElementById("orderType").innerHTML;
			
			if(htFilter.ContainsKey("orderby"))//如果包含oderby查询条件则重新设置否则添加
				htFilter.SetValue("orderby",orderby);
			else 
				htFilter.Add("orderby",orderby);
					
			//发送请求
			sendRequest();	
			//alert(o.parentElement.rowIndex   +o.cellIndex+":"+   "="   +o.innerText)   
		}
		
		 
		
		//JavaScript hashTable
		function HashTable() 
        {         
                this.Items=[]; 
                this.Count=function(){return this.Items.length;};        //长度                 
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
		//alert(my.GetValue("sex"));//已添加过的返回fals
		//my.SetValue("sex","gielre");	
		// alert(my.GetValue("sex"));//已添加过的返回fals
       // alert(my.Add("sex","sex"));//已添加过的返回false
       // alert(my.Count());
       // alert(my.ContainsValue("boy"));
        //alert(my.GetValue("name"))
       // for(var i in my.Items) //遍历
       // { 
          //   alert("Key:"+my.Items[i].Key+"--Value:"+my.Items[i].Value); 
       // } 
      //  my.Remove("age"); //删除
       // alert(my.Keys()+'-'+my.Values()+'\n\r');  