function changeOrder(sign)
		{
		  if(sign)
		  {
		   var orderField=frm.orderField.value;
		   var orderDesc=frm.orderDesc.value;
		   if(orderField!=''&&orderDesc!='')
		   {
		   var direction="";
		   if(orderDesc=="false")
           {
                   direction="↑";
           }
            else 
           { 
                   direction="↓";
           }

		   var tbHead=document.getElementById("tableList");
		   var location;
		   //tbHead.rows(0).onclick="dtquery()";
		   tbHead.rows(0).attachEvent("onclick", new   Function("dtquery()")); 
		   //alert(tbHead.rows(0).attributes);
		   //alert(frm.tr);
		   for(var i=0;i<tbHead.rows(0).cells.length;i++)
           {                 
                 if(tbHead.rows(0).cells[i].abbr==orderField)
                 {
                     location=i;
                     var text=tbHead.rows(0).cells[i].innerText;
                     tbHead.rows(0).cells[i].innerText=text+direction;
                     tbHead.rows(0).cells[i].style.color="blue";
                     tbHead.rows(0).cells[i].style.cursor="hand";
                  }
                  else if(tbHead.rows(0).cells[i].abbr!="")
                  {
                    tbHead.rows(0).cells[i].style.cursor="hand";
                  }
            }
            
            /*for(var i=1;i<tbHead.rows.length;i++)
            {
               if(tbHead.rows(i).cells[location])
               {
               if(tbHead.rows(i).cells[location].innerText!="")
               {
               tbHead.rows(i).cells[location].style.background="#E8E8E8";
               }
               }
            }*/

		   }
		   }
		}
		function dtquery()   
       {
          o   =   event.srcElement;  
         
         if(o.tagName!="TD")   return;
         var orderField=frm.orderField.value;
		 var orderDesc=frm.orderDesc.value; 
		 if(o.abbr!='')
		 {
		 if(orderField==o.abbr)
         {
           if(orderDesc=="true")
          {
               frm.orderDesc.value="false";
          }
           else 
          {
              frm.orderDesc.value="true"; 
          }
         }
         else
         {
             frm.orderDesc.value="false";
             frm.orderField.value=o.abbr;
         }
		 doQuery('false');
          }
       }
       
    //用于展示列表中某条信息详情后返回时保存列表分页信息的onload方法  
    function initBody(returnValue){
		changeOrder(sign);
		//var returnValue='${returnRefresh}';
		if( frm.pageSize.value == "" || frm.pageSize.value == "null"||returnValue!='')
		{
			doQuery('false');
		}
	}
	
	function doQuery(savePageNo){
	
		if(savePageNo=='true')
		{
			frm.pageNo.value = 1;
		}
		
		queryInfo();
	}