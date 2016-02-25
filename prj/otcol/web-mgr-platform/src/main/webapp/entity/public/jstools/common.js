//����PNGͼƬ��͸������
function correctPNG()
{
	for(var i=0; i<document.images.length; i++)
	{
		var img = document.images[i];
		var imgName = img.src;
		if ( imgName.substring(imgName.length-3, imgName.length).toUpperCase() == "PNG" )
		{
			var imgID = (img.id) ? "id='" + img.id + "' " : "";
			var imgTitle = (img.title) ? "title='" + img.alt + "' " : "";
			var imgStyle = "display:inline;" + img.style.cssText ;
			if (img.align == "left") imgStyle = "float:left;" + imgStyle;
			if (img.align == "right") imgStyle = "float:right;" + imgStyle;
			if (img.parentElement.href) imgStyle = "cursor:hand;" + imgStyle;
			var strNewHTML = "<span " + imgID + imgTitle	+ " style=\"" + "width: 30px;height:30px;" + imgStyle + ";" + "filter:progid:DXImageTransform.Microsoft.AlphaImageLoader" + "(src=\'" + img.src + "\', sizingMethod='scale');\"></span>";
			img.outerHTML = strNewHTML;
			i = i-1;
		}
	}
}

//��ʽ�����ڣ����ڸ�ʽ yyyy-mm-dd��
function calDate( value )
{
    if ( value == "" || value == undefined )
    {
	    var oDate = new Date();
	    sCurYear = oDate.getFullYear().toString();
	    sCurMonth = (oDate.getMonth() + 1).toString();
	    if ( sCurMonth.length < 2 )
	        sCurMonth = "0" + sCurMonth;
	    sCurDay = oDate.getDate().toString();
	    if ( sCurDay.length < 2 )
	        sCurDay = "0" + sCurDay;
	    sDate = sCurYear + "-" + sCurMonth + "-" + sCurDay;
	    return sDate;
	}
	else
	{
		return value.substr( 0, 16 );
	}
}

//��ʽ��ʱ�䣨ʱ���ʽ HH:MM:SS��
function calTime( value )
{
	var oDate = new Date();
	sCurHours = oDate.getHours().toString();
	if ( sCurHours.length < 2 )
		sCurHours = "0" + sCurHours;
	
	sCurMinutes = parseInt( oDate.getMinutes() );
	if ( ( sCurMinutes % 5 ) != 0 )
		sCurMinutes = ( parseInt(sCurMinutes / 5) + 1 ) * 5;
	

	if ( sCurMinutes == 60 )
	{
		sCurMinutes = "00";
		sCurHours++;
		if ( sCurHours == 24 )
			sCurHours = "00";
	}
	if ( sCurMinutes.toString().length < 2 )
	{
		sCurMinutes = "0" + sCurMinutes.toString();
	}
	
	sTime = sCurHours + ":" + sCurMinutes;
	return sTime;
}

//ҳ����ת
function gotoPage(sUrl)
{
    window.location = sUrl;
}

//ҳ����ת
function returnPage(sUrl)
{
    window.location = basePath + sUrl;
}

//���ݸ����Ĳ�����ModalDialog
function openDialog(url, args, width, height) {
	if (!width) width = 600;
	if (!height) height = 400;
    var returnVal = window.showModalDialog(url, args, "dialogWidth=" + width + "px; dialogHeight=" + height + "px; status=yes;scroll=yes;help=no;");

    return returnVal;
}

function closeDialog(parentReload){
	window.returnValue = parentReload;
	window.close();
}
//���ݸ����Ĳ����򿪴���
function openWin(sURL, target, width, height)
{
	if (!width) width = 600;
	if (!height) height = 400;
	var padLeft = (window.screen.width - width)/2;
	var padTop  = (window.screen.height - height)/2;
	var win = window.open(sURL, target, "width=" + width + ",height=" + height + ", top=" + padTop + " , left=" + padLeft + " ,scrollbars=yes , resizable = yes ,status=yes,toolbar=no,menubar=no,location=no");
}

//ʵ��ҳ���ӡ����
function printWin()
{
	window.print();
}

function hideContent( eltID )
{
	var objImg = event.srcElement;
	var objElt = document.getElementById( eltID );
	if ( objElt.style.display == "none" )
	{
		objElt.style.display = "inline";
		objImg.src = skinPath + "/images/shrink.gif";
		objImg.alt = "����";
	}
	else
	{
		objElt.style.display = "none";
		objImg.src = skinPath + "/images/expand.gif";
		objImg.alt = "չ��";
		
	}
}
function initBody(returnValue){
		changeOrder(sign);
		
	}
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
                   direction="��";
           }
            else 
           { 
                   direction="��";
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
	//����չʾ�б���ĳ����Ϣ����� ����ʱ�����б��ҳ��Ϣ��onload����      
	
	
	function doQuery(savePageNo){
	
		if(savePageNo=='true')
		{
			frm.pageNo.value = 1;
		}
		
		frm.submit();
	}
	
	//��������  yyyy-MM-dd
	function onlyDate()
	{
	  if (event.keyCode<45 || event.keyCode>57 || event.keyCode == 47 || event.keyCode == 46)
	  {
	    event.returnValue=false;
	  }
	}
	//��������ʱ��  yyyy-MM-dd HH:ss:mm
	function onlyDateTime()
	{
	  if ((event.keyCode<45 && event.keyCode!=32) || event.keyCode>58 || event.keyCode == 47 || event.keyCode == 46)
	  {
	    event.returnValue=false;
	  }
	}
	//���Ʋ�����ո�
	function notSpace()
	{
	  if(event.keyCode == 32)
	  {
	    event.returnValue=false;
	  }
	}
	//�����뺺�ֵĿ���
	function onlyChineseCharInput()
	{
	  if (event.keyCode>=20 || event.keyCode<=128)
	  {
	    event.returnValue=false;
	  }
	}
	//���������ֺ�.
	function onlyNumberInput()
	{
	  if (event.keyCode<46 || event.keyCode>57 || event.keyCode == 47)
	  {
	    event.returnValue=false;
	  }
	}
	//���������ֺ���ĸ
	function onlyNumberAndCharInput()
	{
	  if ((event.keyCode>=48 && event.keyCode<=57) || (event.keyCode>=65 && event.keyCode<=90) || (event.keyCode>=97 && event.keyCode<=122))
	  {
	    event.returnValue=true;
	  }
	  else
	  {
	    event.returnValue=false;
	  }
	}