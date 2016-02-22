//纠正PNG图片的透明背景
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

//格式化日期（日期格式 yyyy-mm-dd）
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

//格式化时间（时间格式 HH:MM:SS）
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

//页面跳转
function gotoPage(sUrl)
{
    window.location = sUrl;
}

//页面跳转
function returnPage(sUrl)
{
    window.location = basePath + sUrl;
}

//根据给定的参数打开ModalDialog
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

function refresh() 
{ 
this.location = this.location; 
}

/*当用window.open打开窗口时,保存后关闭子窗口,并刷新父窗口
  frm form 名称
*/

function closeRefreshDialog(){
  window.opener.frm.submit();
  window.close();
}

//当用window.open打开窗口时,保存并刷新父窗口.
function refreshDialog(){
  location.reload();
  window.opener.frm.submit();
  //window.opener.location.reload();
}

//根据给定的参数打开窗口
function openWin(sURL, target, width, height)
{
	if (!width) width = 600;
	if (!height) height = 400;
	var padLeft = (window.screen.width - width)/2;
	var padTop  = (window.screen.height - height)/2;
	var win = window.open(sURL, target, "width=" + width + ",height=" + height + ", top=" + padTop + " , left=" + padLeft + " ,scrollbars=yes , resizable = yes ,status=yes,toolbar=no,menubar=no,location=no");
}

//实现页面打印功能
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
		objImg.alt = "收缩";
	}
	else
	{
		objElt.style.display = "none";
		objImg.src = skinPath + "/images/expand.gif";
		objImg.alt = "展开";
		
	}
}